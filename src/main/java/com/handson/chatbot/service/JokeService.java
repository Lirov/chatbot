package com.handson.chatbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;
@Service
public class JokeService {

    OkHttpClient client = new OkHttpClient().newBuilder().build();
    @Autowired
    ObjectMapper om;

    public String searchJoke(String keyword) throws IOException {
        return getJokeData(getJokeId(keyword));
    }

    public String getJokeData(String jokeId) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.chucknorris.io/jokes/search?query=big"+jokeId)
                .method("GET", null)
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .addHeader("accept-language", "en-US,en;q=0.9,he;q=0.8")
                .addHeader("cache-control", "max-age=0")
                .addHeader("cookie", "_ga=GA1.2.1642823019.1721586659; _gid=GA1.2.597310573.1721586659; _ga_JRZZDJK0D7=GS1.2.1721586659.1.1.1721586671.0.0.0")
                .addHeader("dnt", "1")
                .addHeader("priority", "u=0, i")
                .addHeader("sec-ch-ua", "\"Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "none")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36")
                .build();
        Response response = client.newCall(request).execute();
        JokeData res = om.readValue((response.body().toString()), JokeData.class);
        return res.getData().getJokes().get(0).toString();

    }
    public String getJokeId(String keyword) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.chucknorris.io/jokes/search?query=big")
                .method("GET", null)
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .addHeader("accept-language", "en-US,en;q=0.9,he;q=0.8")
                .addHeader("cache-control", "max-age=0")
                .addHeader("cookie", "_ga=GA1.2.1642823019.1721586659; _gid=GA1.2.597310573.1721586659; _ga_JRZZDJK0D7=GS1.2.1721586659.1.1.1721586671.0.0.0")
                .addHeader("dnt", "1")
                .addHeader("priority", "u=0, i")
                .addHeader("sec-ch-ua", "\"Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "none")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36")
                .build();
        Response response = client.newCall(request).execute();
        JokeResponse res = om.readValue(response.body().string(),JokeResponse.class);
        return res.getResult().get(0).getId().toString();
    }

    static class JokeResponse{
        List<JokeResponseObject> result;

        public List<JokeResponseObject> getResult() {
            return result;
        }
    }

    static class JokeResponseObject{
      String id;

        public String getId() {
            return id;
        }
    }

    static class JokeData {
        Jokes data;

        public Jokes getData() {
            return data;
        }
    }

    static class Jokes {
        List<Joke> jokes;

        public List<Joke> getJokes() {
            return jokes;
        }
    }

    static class Joke {
        WObservation observation;

        public WObservation getObservation() {
            return observation;
        }
    }
    static class WObservation {
        List<DayPart> dayPartTexts;

        public List<DayPart> getDayPartTexts() {
            return dayPartTexts;
        }
    }

    static class DayPart {
        String text;

        public String getText() {
            return text;
        }
    }

}
