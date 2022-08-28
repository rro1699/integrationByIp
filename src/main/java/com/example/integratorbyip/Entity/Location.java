package com.example.integratorbyip.Entity;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Location {
    private String location;
    private String latitude;
    private String longitude;
    private String ip;

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
