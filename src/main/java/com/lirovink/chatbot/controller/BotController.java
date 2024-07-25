package com.lirovink.chatbot.controller;

import com.lirovink.chatbot.service.EbayService;
import com.lirovink.chatbot.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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


}
