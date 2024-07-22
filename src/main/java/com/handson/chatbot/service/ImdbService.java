package com.handson.chatbot.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ImdbService {
    public static final Pattern MOVIE_PATTERN = Pattern.compile("<h3 class=\\\"ipc-title__text\\\">([^<]+)<[^<]+<[^<]+<[^<]+<[^<]+<span class=\\\"sc-b189961a-8 kLaxqf cli-title-metadata-item\\\">([^<]+)<[^<]+<[^<]+>([^<]+)<\\/span>");
    public String searchMovies(String keyword) throws IOException {
        return parseMovieHtml(getMovieHtml(keyword));
    }

    private String parseMovieHtml(String html) {
        String res = "";
        Matcher matcher = MOVIE_PATTERN.matcher(html);
        while (matcher.find()) {
            res += matcher.group(1) + ", Year:" + matcher.group(2) + ", Length:" + matcher.group(3) + "\n";
        }
        return res;
    }

    private String getMovieHtml(String keyword) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://www.imdb.com/chart/top/")
                .method("GET", null)
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .addHeader("accept-language", "en-US,en;q=0.9,he;q=0.8")
                .addHeader("cache-control", "max-age=0")
                .addHeader("cookie", "session-id=132-0273137-0339432; session-id-time=2082787201l; ad-oo=0; ci=e30; ubid-main=135-0249544-4890812; session-token=ZfwlI6W5HLuEXaWkrcV3hNRunPfPMLKRC4YIaJG5j5S/NhUJhsDZx5Z4loavpQkafaPGvyIgaP68aINW7roLWk+GT/1rXrJK9v5MVL04VrlXprSIiMeDTn3kuKRixSjDAv03J4gUkrQLncuj49h6EHB6vXznRc6hfCd4aMoI1Rh9VPcxOMlyUdk3grtLE8XoHAVK92hTlwjsb6hTULEego03oE3I659fb1zOkyLXZWzREl3c/LecmMxJkltxUnCfapdlVVC1OgqXwToIPK77qadwEqpfBCmGikChyHWLfTEgMY+YzGpfIQ1ke8fw+pRCuP0LE9Dt0FneknHx6/ALQlV5NTQaMqRv; csm-hit=tb:s-CQEJ9S3KNHV49QYETRWV|1721569440781&t:1721569441178&adb:adblk_yes; session-id=138-8088065-9972847; session-id-time=2082787201l; session-token=ttVMiVGIvO318U73xxdDiTd3Lpv0YCCoXCNwBvSn9rNK6dA/ncNUOxnMpKOJ8jP3vEvGue/CkemeUzO4BDXsk0wx5o/YODSv443JKc3p0F23rtoAuAP0An3vOtN+R5d4cwpNvsPXQUEsF/mmSTiGhR/yohOvSSxjxBMaSfLlNo0rK3W5OTwIG6NqtjEVwZ4PCyh8rz2Pxe8968lL9mRq0NaSDGRucBH7H0Q80E6nyZwBBTysVcL2teSccyp0kvqps7vsi6rEubx5/yWpMxfxa9SOhKOvI1zivM02y5o+JNrsd+rX0k+1QEVN+sYudAvZPB6xaYn94m/Kjp0Iaa0Q9q5CSexJHhyL")
                .addHeader("dnt", "1")
                .addHeader("priority", "u=0, i")
                .addHeader("referer", "https://www.google.com/")
                .addHeader("sec-ch-ua", "\"Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36")
                .addHeader("Referer", "https://www.imdb.com/")
                .addHeader("DNT", "1")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36")
                .addHeader("Origin", "https://www.imdb.com")
                .addHeader("content-type", "application/json")
                .addHeader("origin", "https://www.imdb.com")
                .addHeader("x-amzn-sessionid", "132-0273137-0339432")
                .addHeader("x-imdb-client-name", "IMDbConsumerDesktop-ConsentBanner")
                .addHeader("x-imdb-client-rid", "VG1P0V586R5R5Y5H0KHF")
                .addHeader("x-imdb-user-country", "US")
                .addHeader("x-imdb-user-language", "en-US")
                .addHeader("x-imdb-weblab-treatment-overrides", "{}")
                .addHeader("Accept", "*/*")
                .addHeader("Accept-Language", "en-US,en;q=0.9,he;q=0.8")
                .addHeader("Connection", "keep-alive")
                .addHeader("Content-Type", "text/plain;charset=UTF-8")
                .addHeader("Cookie", "aws-ubid-main=562-2680118-1855707; aws-userInfo=%7B%22arn%22%3A%22arn%3Aaws%3Aiam%3A%3A995553441267%3Auser%2Flirov%22%2C%22alias%22%3A%22995553441267%22%2C%22username%22%3A%22lirov%22%2C%22keybase%22%3A%22cmxX%2FdtaKnLjl3Nw40nUSdjFxYGr%2Bgce%2BMdmIBhzkGg%5Cu003d%22%2C%22issuer%22%3A%22http%3A%2F%2Fsignin.aws.amazon.com%2Fsignin%22%2C%22signinType%22%3A%22PUBLIC%22%7D")
                .addHeader("Sec-Fetch-Dest", "empty")
                .addHeader("Sec-Fetch-Mode", "no-cors")
                .addHeader("Sec-Fetch-Site", "cross-site")
                .addHeader("if-modified-since", "Thu, 20 Feb 2020 00:21:09 GMT")
                .build();
        Response response = client.newCall(request).execute();

        return response.body().string();

    }
}




