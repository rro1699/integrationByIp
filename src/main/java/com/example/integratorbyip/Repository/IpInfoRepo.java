package com.example.integratorbyip.Repository;

import com.example.integratorbyip.Entity.IpInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Collection;

@Repository
public interface IpInfoRepo extends JpaRepository<IpInfo,Long> {
    @Query("SELECT i FROM IpInfo i WHERE i.ip=(:ip) ORDER BY i.id")
    Collection<IpInfo> findAllByIP(String ip);

    @Query("SELECT i FROM IpInfo i WHERE  i.date = (:date) ORDER BY i.id")
    Collection<IpInfo> findAllByDate(Date date);
}
