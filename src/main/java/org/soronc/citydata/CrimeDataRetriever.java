package org.soronc.citydata;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.socrata.api.HttpLowLevel;
import com.socrata.api.Soda2Consumer;
import com.socrata.builders.SoqlQueryBuilder;
import com.socrata.exceptions.LongRunningQueryException;
import com.socrata.exceptions.SodaError;
import com.socrata.model.soql.OrderByClause;
import com.socrata.model.soql.SoqlQuery;
import com.socrata.model.soql.SortOrder;
import com.sun.jersey.api.client.ClientResponse;

@Component
public class CrimeDataRetriever {

    private static final String DELIMETER = "\\t";

    private static final String TEMP_FILE_NAME_EXTENSION = ".tmp";

    private static final String TEMP_FILE_NAME_PREFIX = "temp";

    private static final String LAPD_COMPSTAT_DUMP_URL = "http://www.lapdonline.org/assets/crimedata/CrimeData.zip";

    private static final ZoneId LAPD_TIME_ZONE = ZoneId.of("America/Los_Angeles");

    private static final String CROSS_STREET_DIVIDER = " / ";

    private static final char SPACE = ' ';

    private static final String MISSING_ADDRESS_PLACEHOLDER = "00";

    private static final String LAPD_DATE_FORMAT = "MM/dd/yyyyHHmm";

    private static final Logger log = LoggerFactory
            .getLogger(CrimeDataRetriever.class);

    private static final String CRIME_REPORTS_2016 = "kh8g-6365";

    private static final int OFFSET = 0;

    private static final int LIMIT = 50000;

    private Soda2Consumer consumer = Soda2Consumer
            .newConsumer("https://data.lacity.org");

    private ObjectMapper mapper = new ObjectMapper();

    private static final Set<String> SORO_REPORTING_DISTRICTS = ImmutableSet.of(
            "0886", "0897", "0887", "0898", "0895", "0896", "0859", "0849",
            "1409", "0857", "1408", "0858", "0782", "0899", "0889");

    private final String WHERE_CLAUSE = "rd in ('0886', '0897', '0887', '0898', '0895', '0896', '0859', '0849', '1409', '0857', '1408', '0858', '0782', '0899', '0889')";

    private final List<OrderByClause> orderByClause = Lists
            .newArrayList(new OrderByClause(SortOrder.Ascending, ":id"));

    private final SoqlQuery QUERY = new SoqlQueryBuilder()
            .setWhereClause(WHERE_CLAUSE)
            .setLimit(LIMIT)
            .setOffset(OFFSET)
            .setOrderByPhrase(orderByClause)
            .build();

    public List<CrimeReport> getData(Month month, Year year)
            throws LongRunningQueryException, SodaError,
            InterruptedException, JsonParseException, JsonMappingException,
            IOException {

        if (year.equals(Year.now())) {
            return getCurrentYearCrimeReports(month, year);
        }

        return get2016CrimeReports(month, year);

    }

    private List<CrimeReport> getCurrentYearCrimeReports(Month month, Year year)
            throws IOException {

        URL lapd = new URL(
                LAPD_COMPSTAT_DUMP_URL);
        File tempFile = File.createTempFile(TEMP_FILE_NAME_PREFIX + new Date().getTime(),
                TEMP_FILE_NAME_EXTENSION);
        FileUtils.copyURLToFile(lapd, tempFile);
        List<CrimeReport> crimeReports;
        try (ZipFile zipFile = new ZipFile(tempFile)){
            try (InputStream zis = zipFile.getInputStream(zipFile.entries().nextElement())){

                BufferedReader in = new BufferedReader(new InputStreamReader(zis));
                crimeReports = in.lines().parallel().map(line -> {
                    CrimeReport cr = parseRawCrimeReportLine(line);
                    return cr;
                }).collect(Collectors.toList());

            } catch (Exception e) {
                log.error("Could not read zip file.", e);
                return new ArrayList<>();
            }
        } catch (Exception e) {
            log.error("Could not open zip file.", e);
            return new ArrayList<>();
        }
        tempFile.delete();
        return crimeReports.parallelStream()
                .filter(report -> SORO_REPORTING_DISTRICTS
                        .contains(report.getReportingDistrict()))
                .filter(occuredDuring(month, year))
                .sorted()
                .collect(Collectors.toList());
    }

    private CrimeReport parseRawCrimeReportLine(String line) {

        String[] data = line.split(DELIMETER, -1);

        CrimeReport cr = new CrimeReport();
        cr.setLocationName(data[12]);
        cr.setReportingDistrict(data[7]);
        cr.setTimeOccurred(data[6]);
        cr.setCrimeCode(data[1]);
        cr.setDescription(WordUtils.capitalizeFully(Crime.getCrime(data[1]).getDescription()));
        cr.setStatus(data[19]);
        StringBuilder locationName = parseCrimeReportLocationName(data);
        cr.setLocationName(WordUtils.capitalizeFully(locationName.toString().trim()));
        try {
            Date date = new SimpleDateFormat(LAPD_DATE_FORMAT).parse(data[5] + data[6]);
            cr.setOccurred(date);
        } catch (Exception e) {
            log.warn("Could not parse date={}.", data[5], e);
        }
        return cr;
    }

    private StringBuilder parseCrimeReportLocationName(String[] data) {

        StringBuilder locationName = new StringBuilder();
        if (StringUtils.isNotBlank(data[9]) && !data[9].equals(MISSING_ADDRESS_PLACEHOLDER)) {
            locationName.append(data[9]);
            locationName.append(SPACE);
        }
        if (StringUtils.isNotBlank(data[11])) {
            locationName.append(data[11]);
            locationName.append(SPACE);
        }
        if (StringUtils.isNotBlank(data[12])) {
            locationName.append(data[12]);
            locationName.append(SPACE);
        }
        if (StringUtils.isNotBlank(data[13])) {
            locationName.append(data[13]);
            locationName.append(SPACE);
        }
        if (StringUtils.isNotBlank(data[17])) {
            locationName.append(CROSS_STREET_DIVIDER);
            locationName.append(data[17]);
        }
        return locationName;
    }

    private List<CrimeReport> get2016CrimeReports(Month month, Year year)
            throws LongRunningQueryException, SodaError, IOException,
            JsonParseException, JsonMappingException {

        ClientResponse response = consumer.query(CRIME_REPORTS_2016,
                HttpLowLevel.JSON_TYPE, QUERY);
        String payload = response.getEntity(String.class);
        List<CrimeReport> crimeReports = mapper.readValue(payload,
                CrimeReport.LIST_TYPE);

        return crimeReports.parallelStream()
                .filter(occuredDuring(month, year))
                .collect(Collectors.toList());
        
    }

    private Predicate<? super CrimeReport> occuredDuring(Month month,
            Year year) {

        return report -> {
            LocalDateTime time = LocalDateTime.ofInstant(
                    report.getOccurred().toInstant(),
                    LAPD_TIME_ZONE);
            return time.getYear() == year.getValue()
                    && month == time.getMonth();
        };
    }
    

}
