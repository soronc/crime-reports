package org.soronc.citydata;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.base.Joiner;
import com.socrata.exceptions.LongRunningQueryException;
import com.socrata.exceptions.SodaError;

public class CrimeDataRetrieverTest {

	private CrimeDataRetriever retriever = new CrimeDataRetriever();
	
	@Test
	public void test() throws LongRunningQueryException, SodaError, InterruptedException, JsonParseException, JsonMappingException, IOException {
		List<CrimeReport> data = retriever.getData(Month.JANUARY, Year.of(2017));
//		List<String> enumValues = data.stream().collect(Collectors.groupingBy(CrimeReport::getCrimeCode, Collectors.mapping(CrimeReport::getDescription, Collectors.toSet())))
//		.entrySet().stream().map(entry -> "CRIME_CODE_" + entry.getKey() + "(\"" + entry.getKey() + "\", \"" + entry.getValue().iterator().next() + "\")").collect(Collectors.toList());
//		System.out.println(Joiner.on(",\n").join(enumValues));
//		System.out.println(
//		        data.stream()
//		        .filter(report -> StringUtils.containsIgnoreCase(report.getLocationName(), "ABBEYVILLE"))
//		        .sorted(
//		                (a,b) -> a.getOccurred().compareTo(b.getOccurred())
//		                )
//		                .collect(Collectors.toList())
//		        );
		System.out.println(data);
		assertEquals(216, data.size());
	}
	
//	@Test
//	public void testTSL() throws IOException{
//	    URL.
//	    Stream<String> lines = Files.lines(FileSystems.getDefault().getPath("/Users", "aricheimer", "Downloads", "CrimeDataFeb2017.txt"));
//	    lines.forEach(line -> {
//	        String[] data = line.split("\\t", -1);
//	        for(int i = 0; i < data.length; i++){
//	            System.out.println(i + "=" + data[i]);
//	        }
//	    });
//	    lines.close();
//	}

}
