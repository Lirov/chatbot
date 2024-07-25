package com.handson.chatbot.service;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EbayService {

        public String searchProducts(String keyword) {
            return "Searched for:" + keyword;
        }

}