package com.example.integratorbyip.Services;

import com.example.integratorbyip.Entity.Weather;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeatherByIPService {
    @Value("${weather.key}")
    private String key;

    public Weather getWeather(String latitude, String longitude){
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        System.out.println("weaher key: "+key);
        Request request = new Request.Builder().
                url("https://api.weatherbit.io/v2.0/current?lat="+latitude+"&lon="+longitude+
                        "&key="+key)
                .get()
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body().string());
            JsonNode data = jsonNode.get("data").get(0);
            Weather weather = new Weather();
            weather.setAqi(data.get("aqi").asText());
            weather.setApp_temp(data.get("app_temp").asText());
            weather.setCity_name(data.get("city_name").asText());
            weather.setClouds(data.get("clouds").asText());
            weather.setRh(data.get("rh").asText());
            weather.setSunrise(data.get("sunrise").asText());
            weather.setSunset(data.get("sunset").asText());
            weather.setWind_spd(data.get("wind_spd").asText());
            weather.setWind_cdir_full(data.get("wind_cdir_full").asText());
            return weather;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
