package com.handson.chatbot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class WeatherService {

    OkHttpClient client = new OkHttpClient().newBuilder()
            .build();

    @Autowired
    ObjectMapper om;

    public String searchWeather(String keyword) throws IOException {
        return getWeatherLocationData(getWeatherLocationId(keyword));
    }

    public String getWeatherLocationData(String locationId) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://data.api.cnn.io/weather/getForecast?aggregateProducts=currentConditions&units=celsius&geocode=" + locationId)
                .method("GET", null)
                .addHeader("sec-ch-ua", "\"Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126\"")
                .addHeader("Referer", "https://edition.cnn.com/")
                .addHeader("DNT", "1")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .build();
        Response response = client.newCall(request).execute();
        WeatherResponse weatherResponse = om.readValue(response.body().string(), WeatherResponse.class);
        return weatherResponse.getCurrentConditions().getDescription();
    }

    public String getWeatherLocationId(String keyword) throws IOException {
        Request request = new Request.Builder()
                .url("https://data.api.cnn.io/weather/citySearch?search_term="+keyword)
                .method("GET", null)
                .addHeader("accept", "*/*")
                .addHeader("accept-language", "en-US,en;q=0.9,he;q=0.8")
                .addHeader("dnt", "1")
                .addHeader("origin", "https://edition.cnn.com")
                .addHeader("priority", "u=1, i")
                .addHeader("referer", "https://edition.cnn.com/")
                .addHeader("sec-ch-ua", "\"Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "empty")
                .addHeader("sec-fetch-mode", "cors")
                .addHeader("sec-fetch-site", "cross-site")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36")
                .build();
        Response response = client.newCall(request).execute();
        List<WeatherLocationObject> res = om.readValue(response.body().string(), new TypeReference<List<WeatherLocationObject>>() {});
        return res.get(0).getGeocode();
    }

    static class WeatherLocationObject {
        String geocode;

        public String getGeocode() {
            return geocode;
        }
    }

    static class WeatherResponse {
        private CurrentConditions currentConditions;

        public CurrentConditions getCurrentConditions() {
            return currentConditions;
        }

        public void setCurrentConditions(CurrentConditions currentConditions) {
            this.currentConditions = currentConditions;
        }
    }
    static class CurrentConditions {
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
