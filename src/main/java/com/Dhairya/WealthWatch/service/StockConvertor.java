//package com.Dhairya.WealthWatch.service;
//
//
//import org.springframework.stereotype.Service;
//import com.Dhairya.WealthWatch.entity.Stock;
//import com.Dhairya.WealthWatch.modal.StockResponse;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@Service
//public class StockConvertor {
//	
//	private static final ObjectMapper objectMapper = new ObjectMapper();
//
//    public static Stock convertJsonToStock(String json) {
//        try {
//        	StockResponse stockResponse = objectMapper.readValue(json, StockResponse.class);
//        	return stockResponse.getStock();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null; 
//        }
//    }
//}
