package com.coderman.worldgdp.controller.api;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.coderman.worldgdp.dao.CountryDAO;
import com.coderman.worldgdp.external.WorldBankApiClient;
import com.coderman.worldgdp.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import lombok.extern.slf4j.Slf4j;

@RestController  //-> this is used to annotate a class as a controller with each of the RESTful methods returning the data in the response body
@RequestMapping("/api/countries") //-> This is for assigning the root URL for accessing the resources.
@Slf4j
public class CountryAPIController {

    @Autowired
    CountryDAO countryDao;
    @Autowired
    WorldBankApiClient worldBankApiClient;

    @GetMapping //-> Http verb that will be used to invoke the resource. The URL for the resources are passed withing the annotation, along with other request header that consume and produce information
    public ResponseEntity<?> getCountries(
            @RequestParam(name="search", required = false) String searchTerm,
            @RequestParam(name="continent", required = false) String continent,
            @RequestParam(name="region", required = false) String region,
            @RequestParam(name="pageNo", required = false) Integer pageNo
    ){
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("search", searchTerm);
            params.put("continent", continent);
            params.put("region", region);
            if ( pageNo != null ) {
                params.put("pageNo", pageNo.toString());
            }

            List<Country> countries = countryDao.getCountries(params);
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("list", countries);
            response.put("count", countryDao.getCountriesCount(params));
            return ResponseEntity.ok(response);
        }catch(Exception ex) {
            System.out.println("Error while getting countries"+ ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while getting countries");
        }
    }

    @PostMapping(value = "/{countryCode}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> editCountry(
            @PathVariable String countryCode, @Valid @RequestBody Country country ){
        try {
            countryDao.editCountryDetail(countryCode, country);
            Country countryFromDb = countryDao.getCountryDetail(countryCode);
            return ResponseEntity.ok(countryFromDb);
        }catch(Exception ex) {
            System.out.println("Error while editing the country: {} with data: {}"+ countryCode+ country+ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while ediiting the country");
        }
    }

    @GetMapping("/{countryCode}/gdp")
    public ResponseEntity<?> getGDP(@PathVariable String countryCode){
        try {
            return ResponseEntity.ok(worldBankApiClient.getGDP(countryCode));
        }catch(Exception ex) {
            System.out.println("Error while getting GDP for country: {}"+ countryCode+ ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while getting the GDP");
        }
    }

}