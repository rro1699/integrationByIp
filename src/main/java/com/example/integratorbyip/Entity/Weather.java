package com.example.integratorbyip.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Weather {


    private String rh;
    private String clouds;
    private String city_name;
    private String wind_spd;
    private String sunrise;
    private String app_temp;
    private String aqi;
    private String sunset;
    private String wind_cdir_full;

    @Override
    public String toString() {
        return "Weather{" +
                "rh='" + rh + '\'' +
                ", clouds='" + clouds + '\'' +
                ", city_name='" + city_name + '\'' +
                ", wind_spd='" + wind_spd + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", app_temp='" + app_temp + '\'' +
                ", aqi='" + aqi + '\'' +
                ", sunset='" + sunset + '\'' +
                ", wind_cdir_full='" + wind_cdir_full + '\'' +
                '}';
    }
}
