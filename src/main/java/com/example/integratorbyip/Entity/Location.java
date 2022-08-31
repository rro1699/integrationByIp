package com.example.integratorbyip.Entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    private String location;

    private String latitude;

    private String longitude;

    private String ip;

    private String wind_spd;

    @Override
    public String toString() {
        return "Location{" +
                "location='" + location + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
