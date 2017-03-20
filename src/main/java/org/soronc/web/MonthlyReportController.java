package org.soronc.web;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.text.WordUtils;
import org.soronc.citydata.CrimeDataRetriever;
import org.soronc.citydata.CrimeReport;
import org.soronc.citydata.ReportingDistrictCrimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.socrata.exceptions.LongRunningQueryException;
import com.socrata.exceptions.SodaError;

@Controller
public class MonthlyReportController {

    @Autowired private CrimeDataRetriever crimeDataRetriever;

    @GetMapping("/monthly-report")
    public String getCrimeData(Model model) throws JsonParseException, JsonMappingException, LongRunningQueryException, SodaError, InterruptedException, IOException{
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
        List<CrimeReport> reports = crimeDataRetriever.getData(lastMonth.getMonth(), Year.of(lastMonth.getYear()));
        List<ReportingDistrictCrimes> rdReports = reports.stream()
        .collect(Collectors.groupingBy(CrimeReport::getReportingDistrict))
        .entrySet().stream()
        .map(entry ->  new ReportingDistrictCrimes(entry.getKey(), entry.getValue()))
        .sorted()
        .collect(Collectors.toList());
        model.addAttribute("rdReports", rdReports);
        model.addAttribute("month", WordUtils.capitalizeFully(lastMonth.getMonth().name()));
        model.addAttribute("year", lastMonth.getYear());
        return "report";

    }
}
