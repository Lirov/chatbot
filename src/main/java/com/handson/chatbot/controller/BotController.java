package com.handson.chatbot.controller;

import com.handson.chatbot.service.EbayService;
import com.handson.chatbot.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@Service
@RestController
@RequestMapping("/bot")
public class BotController {

    @Autowired
    EbayService ebayService;
    @Autowired
    private WeatherService weatherService;

    @RequestMapping(value = "/ebay", method = RequestMethod.GET)
    public ResponseEntity<?> getProduct(@RequestParam String keyword) throws IOException {
        return new ResponseEntity<>(ebayService.searchProducts(keyword), HttpStatus.OK);
    }

    @RequestMapping(value = "/weather", method = RequestMethod.GET)
    public ResponseEntity<?> getWeather(@RequestParam String keyword) throws IOException {
        return new ResponseEntity<>(weatherService.searchWeather(keyword), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = { RequestMethod.POST})
    public ResponseEntity<?> getBotResponse(@RequestBody BotQuery query) throws IOException {
        HashMap<String, String> params = query.getQueryResult().getParameters();
        String res = "Not found";
        if (params.containsKey("city")) {
            res = weatherService.searchWeather(params.get("city"));
        } else if (params.containsKey("product")) {
            res = ebayService.searchProducts(params.get("product"));
        }
        return new ResponseEntity<>(BotResponse.of(res), HttpStatus.OK);
    }

    static class BotQuery {
        QueryResult queryResult;

        public QueryResult getQueryResult() {
            return queryResult;
        }
    }

    static class QueryResult {
        HashMap<String, String> parameters;

        public HashMap<String, String> getParameters() {
            return parameters;
        }
    }

    static class BotResponse {
        String fulfillmentText;
        String source = "BOT";

        public String getFulfillmentText() {
            return fulfillmentText;
        }

        public String getSource() {
            return source;
        }

        public static BotResponse of(String fulfillmentText) {
            BotResponse res = new BotResponse();
            res.fulfillmentText = fulfillmentText;
            return res;
        }
    }


}
