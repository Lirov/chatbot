package com.handson.chatbot.controller;

import com.handson.chatbot.service.EbayService;
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

    @RequestMapping(value = "/ebay", method = RequestMethod.GET)
    public ResponseEntity<?> getProduct(@RequestParam String keyword) throws IOException {
        return new ResponseEntity<>(ebayService.searchProducts(keyword), HttpStatus.OK);
    }


}
