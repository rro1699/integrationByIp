package com.example.integratorbyip.Entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class IpInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;

    private Date date;

    private String location;
    private String latitude;
    private String longitude;
    private String app_temp;
    private String clouds;

    public IpInfo(String ip, Date date, String location, String latitude, String longitude, String app_temp, String clouds) {
        this.ip = ip;
        this.date = date;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.app_temp = app_temp;
        this.clouds = clouds;
    }
}
