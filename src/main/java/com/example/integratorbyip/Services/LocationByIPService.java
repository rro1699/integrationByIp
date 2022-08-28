package com.example.integratorbyip.Services;

import java.io.*;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import com.example.integratorbyip.Entity.Location;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.server.WebBrowser;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
public class LocationByIPService {

    @Value("${ip.key}")
    private String key;

    public Location getInfoAboutLocationByIp(String ip){
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        String localIp = "194.226.199.8";
        Request request = new Request.Builder().
                url("https://api.apilayer.com/ip_to_location/"+ip)
                .addHeader("apikey", key)
                .get()
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body().string());
            String loc = jsonNode.get("region_name").asText();
            if(loc.isEmpty() || "-".equals(loc)){
                localIp = getIpApp();
                return getInfoAboutLocationByIp(localIp);
            }else{
                Location location = new Location();
                location.setLocation(loc+", "+jsonNode.get("city").asText());
                location.setLatitude(jsonNode.get("latitude").asText());
                location.setLongitude(jsonNode.get("longitude").asText());
                location.setIp(ip);
                return location;
            }
        }
        catch (SocketTimeoutException e){
            localIp = getIpApp();
            return getInfoAboutLocationByIp(localIp);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getIpApp(){
        // it's ip for application server. We don't can use him.
        // We can use him only if client ip is a localhost and after request "GET" location in response is null.
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder().
                url("https://api.ipify.org/")
                .get()
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getIpClient(WebBrowser request){
        final String LOCALHOST_IPV4 = "127.0.0.1";
        final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

        String ipAddress = request.getAddress();
        if(LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6.equals(ipAddress)) {
            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                ipAddress = inetAddress.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        if(!ipAddress.isEmpty()
                && ipAddress.length() > 15
                && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }

        return ipAddress;
    }
}
