package org.soronc.rest;

import java.io.IOException;
import java.time.Month;
import java.time.Year;
import java.util.List;

import org.soronc.citydata.CrimeDataRetriever;
import org.soronc.citydata.CrimeReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.socrata.exceptions.LongRunningQueryException;
import com.socrata.exceptions.SodaError;

@RestController
public class CrimeDataController {
    
    @Autowired private CrimeDataRetriever crimeDataRetriever;

    @RequestMapping(path="/crimes")
    public List<CrimeReport> getCrimeData(@RequestParam("month") int month, @RequestParam("year") int year) throws JsonParseException, JsonMappingException, LongRunningQueryException, SodaError, InterruptedException, IOException{
        return crimeDataRetriever.getData(Month.of(month), Year.of(year));
    }
}
