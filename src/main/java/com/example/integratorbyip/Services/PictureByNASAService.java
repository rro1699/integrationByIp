package com.example.integratorbyip.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;


@Service
public class PictureByNASAService {
    private String title;

    public String getTitle() {
        return title;
    }

    public URL getPicture(){
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder().
                url("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY")
                .get()
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body().string());
            String url = jsonNode.get("url").asText();
            title = jsonNode.get("title").asText();
            URL urlImage  = new URL(url);
            return urlImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
