package com.example.integratorbyip.Services;

import com.example.integratorbyip.Entity.IpInfo;
import com.example.integratorbyip.Repository.IpInfoRepo;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IpService {
    private IpInfoRepo ipInfoRepo;

    public IpService(IpInfoRepo ipInfoRepo) {
        this.ipInfoRepo = ipInfoRepo;
    }

    public void save(IpInfo ipInfo){
        ipInfoRepo.save(ipInfo);
    }

    public List<IpInfo> findAllRecords(String filterName, String filterValue){
        if(filterName.equals("Ip") && !(filterValue == null || filterValue.isEmpty())){
            return ipInfoRepo.findAllByIP(filterValue).stream().collect(Collectors.toList());
        }else if(filterName.equals("Date") && !(filterValue == null || filterValue.isEmpty())){
            return ipInfoRepo.findAllByDate(Date.valueOf(filterValue)).stream().collect(Collectors.toList());
        }else {
            return ipInfoRepo.findAll();
        }
    }
}
