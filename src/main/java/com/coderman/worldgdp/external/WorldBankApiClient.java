package com.coderman.worldgdp.external;

import com.coderman.worldgdp.model.CountryGDP;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

public class WorldBankApiClient {
    String GDP_URL = "http://api.worldbank.org/v2/country/%s?format=json&date=2008:2018";

    public List<CountryGDP> getGDP(String countryCode) throws ParseException {
        RestTemplate worldBankRestTmplt = new RestTemplate();
        ResponseEntity<String> response = worldBankRestTmplt.getForEntity(String.format(GDP_URL,countryCode),String.class);

        //the second element is the actual data and its an array of object
        JSONParser parser = new JSONParser();
        JSONArray responseData = (JSONArray) parser.parse(response.getBody());
        JSONArray countryDataArr = (JSONArray) responseData.get(1);

        List<CountryGDP> data = new ArrayList<CountryGDP>();
        JSONObject countryDataYearWise=null;
        for (int index=0; index < countryDataArr.size(); index++) {
            countryDataYearWise = (JSONObject) countryDataArr.get(index);

            String valueStr = "0";
            if(countryDataYearWise.get("value") !=null) {
                valueStr = countryDataYearWise.get("value").toString();
            }
            String yearStr = countryDataYearWise.get("date").toString();
            CountryGDP gdp = new CountryGDP();
            gdp.setValue(valueStr != null ? Double.valueOf(valueStr) : null);
            gdp.setYear(Short.valueOf(yearStr));
            data.add(gdp);
        }

        return data;
    }
}