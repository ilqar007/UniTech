package com.uni.tech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CurrencyRatesServiceImpl implements CurrencyRatesService {

    private final static String CACHE_NAME="currencyRates";
    @Autowired
    private CacheManager cacheManager;
    @Override
    public Double readCurrencyRate(String currencyPair) {
       Double rate= getCurrencyPairFromCache(currencyPair);
       if(rate == null)
       {
            rate=mockCurrencyRateService(currencyPair);
           addCurrencyPairToCache(currencyPair,rate);
       }
       return rate;
    }

    private Double mockCurrencyRateService(String currencyPair)
    {
        HashMap<String,Double> rates = new HashMap<>();
        rates.put("USD/AZN",1.7d);
        rates.put("AZN/TL",8d);
      return   rates.get(currencyPair);
    }

    private void addCurrencyPairToCache(String currencyPair,Double rate) {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        cache.put(currencyPair, rate);
    }


    private Double getCurrencyPairFromCache(String currencyPair) {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        return cache.get(currencyPair, Double.class);
    }
}
