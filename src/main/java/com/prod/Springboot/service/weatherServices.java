package com.prod.Springboot.service;

import com.prod.Springboot.api.response.weatherResponse;
import com.prod.Springboot.cache.AppCache;
import com.prod.Springboot.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static jdk.javadoc.doclet.DocletEnvironment.ModuleMode.API;

@Service
public class weatherServices {

    @Value("${weather.api.key}")
    private String ApiKey;

//    private static final String ApiKey= "e57662f41d8448fe93f162558251310";


//    private static String Api= "https://api.weatherapi.com/v1/current.json?key=apiKey&q=city&aqi=no";

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    public weatherResponse getWeather(String city){
        weatherResponse weatherResponse= redisService.get("weather_of_"+city, weatherResponse.class);
        if(weatherResponse!=null){
            return weatherResponse;
        }
        else {
            String finalApi = appCache.appCache.get(AppCache.keys.weather_api.toString()).replace(Placeholders.City, city).replace(Placeholders.ApiKey, ApiKey);
            ResponseEntity<weatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, weatherResponse.class);
            weatherResponse body = response.getBody();
            if (body != null) {
                redisService.set("weather_of_" + city, body, 300l);
            }
            return body;
        }
    }


}
