package com.handson.chatbot.service;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EbayService {

    public static final Pattern PRODUCT_PATTERN = Pattern.compile("<div class=s-item__title><span role=heading aria-level=3><!--F#f_0-->([^<]+)<[^<]+<[^<]+<[^<]+<[^<]+<[^<]+<[^<]+<[^<]+<[^<]+<[^<]+><span class=SECONDARY_INFO>([^<]+)<[^<]+<[^<]+<[^<]+<[^<]+<[^<]+<[^<]+<[^<]+<[^<]+<[^<]+<!--F#f_0-->([^<]+)<[^<]+<[^<]+<[^<]+<[^<]+<[^<]+<[^<]+<[^<]+<[^<]+<[^<]+<[^<]+<[^<]+<[^<]+<!--F#f_0-->([^<]+)<[^<]+<[^<]+<[^<]+<[^<]+<[^<]+<!--F#f_0-->([^<]+)<");

        public String searchProducts(String keyword) throws IOException {
            return parseProductHtml(getProductHtml(keyword));
        }

    private String parseProductHtml(String html) {
        String res = "";
        Matcher matcher = PRODUCT_PATTERN.matcher(html);
        while (matcher.find()) {
            res += matcher.group(1) + ", condition:" + matcher.group(2) + ", price:" + matcher.group(3) + ", shipping:" + matcher.group(4) + ", country:" + matcher.group(5) + "\n";
        }
        return res;
    }

    private String getProductHtml(String keyword) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        Request request = new Request.Builder()
                .url("https://www.ebay.com/sch/i.html?_from=R40&_trksid=p4432023.m570.l1313&_nkw=" + keyword +"&_sacat=0")
                .method("GET", null)
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .addHeader("accept-language", "en-US,en;q=0.9,he;q=0.8")
                .addHeader("cookie", "__uzma=5be08d8f-d3a5-4b07-9d46-02c0936a6bd8; __uzmb=1721561869; __uzme=7119; AMP_MKTG_f93443b04c=JTdCJTIycmVmZXJyZXIlMjIlM0ElMjJodHRwcyUzQSUyRiUyRnd3dy5nb29nbGUuY29tJTJGJTIyJTJDJTIycmVmZXJyaW5nX2RvbWFpbiUyMiUzQSUyMnd3dy5nb29nbGUuY29tJTIyJTdE; __ssds=2; __ssuzjsr2=a9be0cd8e; s=CgAD4ACBmopzpZDUxNTY5ZDUxOTAwYTU1ZjE2MzZhMGNmZmZmZWFmNzkZh2HS; ak_bmsc=9B932E6153AF9F2A5EEC738B791A31F6~000000000000000000000000000000~YAAQpq44F2VM3eOQAQAAR5QO5hhsWNdNq7VPwflcAFefs9CB263hyxw269WdA7yAHfD8hMZQZfOOpl/9AsvdRkEf5zxF6Fn/tj9seI/OweDP8O0cmdkDqvsPWPo4L8X5313EU43QhZD6A6yQKcXsBtqEEJYiw2RluHBrFsRxYnDu0GRchgpPbaKHb+XzYZM8u5gD1QLfktmdBbJ9/wpDueq/uE+jHAxUPJrwTQqqpUkropnYi8L11n4CL5//1MDFQf8BC8NCphVvpr4LqDKn+cv81OnPTkJOUvLoSqscsLB96zeIX/n8bg117t6OSFBa9V7fGz6tywNngP4k4PVlVf4Hmakg3qj+2AsSBOMLlc4E1fFCxHFoff73XQzJCqHFr7JL0Czf6vN7; __deba=Wtt3zNS9AJ2B2_rAYQLP2Ur6JuoB7P8zxK_v4H5OwOdIqcvzmF5q5dLm5Y1IjSqIRtikgL_BPgO5nNTpNxUtBNOmIdCW19-U04Hju-p67gkD2jhNRNpdij9zv47sF4wSOoOWwgWiX925IiNx8OVPSA==; __uzmc=289631957871; __uzmd=1721846634; __uzmf=7f600079bdf97c-f856-489a-9bc2-dcbea065b4781721561869762284764880-ab59228ec066ec5c19; bm_sv=052AF219BE33D96B1EF86EFF973C7937~YAAQpq44F2xN3eOQAQAA5ZwO5hisJpuiAgxb8aq6KLOOwZ8FEH/llAwvIcZ58H+/IsdClqp6xulVmGoyLbN2axmUFK2LIM5Iwc9pZpflJ+j0IDpOw7Zm6HN1KevOfSo7LFF9S+zJXpmwgLiYY8wkN7lXHZ0iVTU/in1BxlODtgPnJLfNAK8loMWt/0c+nXryCn1gUmTgHoZQqEcRJLMBIjrCkoNCvypeNo6cWum1E61cMvcdOB66mOdOX0h+4w==~1; AMP_f93443b04c=JTdCJTIyZGV2aWNlSWQlMjIlM0ElMjI2NGI2OWNiOS01NDgxLTQyMWYtYWVhYy1iZDgwYjkwMGUzNGUlMjIlMkMlMjJzZXNzaW9uSWQlMjIlM0ExNzIxODQ2NjM2MjA3JTJDJTIyb3B0T3V0JTIyJTNBZmFsc2UlMkMlMjJsYXN0RXZlbnRUaW1lJTIyJTNBMTcyMTg0NjYzNjIyNiUyQyUyMmxhc3RFdmVudElkJTIyJTNBOCUyQyUyMnBhZ2VDb3VudGVyJTIyJTNBMSU3RA==; ds2=sotr/bGiAQzzzzzzz^; dp1=bu1p/QEBfX0BAX19AQA**6a63b26c^pbf/%23e00020000000000000000068827eec^bl/IL6a63b26c^; ns1=BAQAAAZBR14g6AAaAANgAU2iCfuxjNjl8NjAxXjE3MjE1NjE4NzE5ODFeXjFeM3wyfDV8NHw3fDEwfDQyfDQzfDExXl5eNF4zXjEyXjEyXjJeMV4xXjBeMV4wXjFeNjQ0MjQ1OTA3Nc34Ss6SjHgGFgR0MvuXwXS+daTz; nonsession=BAQAAAZBR14g6AAaAADMAB2iCfuwxNjEzMDAwAMoAIGpjsmxkNTE1NjlkNTE5MDBhNTVmMTYzNmEwY2ZmZmZlYWY3OQDLAAFmoVJ0NO3iPLHPnpXhUve410EXYeAWJjmq; ebay=%5Esbf%3D%23000000%5Ejs%3D1%5E; __deba=Wtt3zNS9AJ2B2_rAYQLP2X-nD7TcAoM7ONeeDzY6CfXSGVbYPiy9Vv4qwHbTKOEXRtikgL_BPgO5nNTpNxUtBNOmIdCW19-U04Hju-p67gkD2jhNRNpdij9zv47sF4wSOoOWwgWiX925IiNx8OVPSA==; __uzma=5be08d8f-d3a5-4b07-9d46-02c0936a6bd8; __uzmb=1721561869; __uzmc=843042881523; __uzmd=1721846727; __uzme=7119; __uzmf=7f600079bdf97c-f856-489a-9bc2-dcbea065b4781721561869762284857896-7f2d52b270836aab28; bm_sv=052AF219BE33D96B1EF86EFF973C7937~YAAQpq44F4ZZ3eOQAQAA3AQQ5hhHwGBEiEJojsqqVXxGX6/k0xeLA0QyUGqCINz/R49m91loggYS9K91tq39uX0Z913HN1gp6r7YlWdZlOwNQxlDpvZV/UoxW6QH7Y/gvLyjBrxea2aeogU7O7am25RWpZTgckyWB/dBTgN+GbQReL4yMqEtG34t+ZTp5uqnhehEKl5+lBXOyr+F3C74l/GI3Vy7c3wBDHpWLepysgQhGK8IYXa0PUYgFzqWGA==~1; dp1=bu1p/QEBfX0BAX19AQA**6a63b2c7^pbf/%23e00020000000000000000068827f47^bl/IL6a63b2c7^; ebay=%5Ejs%3D1%5Esbf%3D%23000000%5E; nonsession=BAQAAAZBR14g6AAaAADMAB2iCf0cxNjEzMDAwAMoAIGpjssdkNTE1NjlkNTE5MDBhNTVmMTYzNmEwY2ZmZmZlYWY3OQDLAAFmoVLPNSXQ+1HXRnnKBrSR11c6KN86bR6T; ns1=BAQAAAZBR14g6AAaAANgAU2iCf0djNjl8NjAxXjE3MjE1NjE4NzE5ODFeXjFeM3wyfDV8NHw3fDEwfDQyfDQzfDExXl5eNF4zXjEyXjEyXjJeMV4xXjBeMV4wXjFeNjQ0MjQ1OTA3NcqVvI6mlVOwwY4ZFDf5G+HzVmbh; s=CgAD4ACBmopzpZDUxNTY5ZDUxOTAwYTU1ZjE2MzZhMGNmZmZmZWFmNzkZh2HS")
                .addHeader("dnt", "1")
                .addHeader("priority", "u=0, i")
                .addHeader("referer", "https://www.ebay.com/")
                .addHeader("sec-ch-ua", "\"Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126\"")
                .addHeader("sec-ch-ua-full-version", "\"126.0.6478.128\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-model", "\"\"")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-ch-ua-platform-version", "\"15.0.0\"")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}