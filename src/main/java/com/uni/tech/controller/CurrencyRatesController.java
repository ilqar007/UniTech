package com.uni.tech.controller;

import com.uni.tech.service.CurrencyRatesService;
import com.uni.tech.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyRatesController {

    @Autowired
    private CurrencyRatesService currencyRatesService;

    @GetMapping("/rest/currencyRates/{currencyPair1}/{currencyPair2}/**")
    public ResponseEntity<?> readCurrencyRate(@PathVariable String currencyPair1,@PathVariable String currencyPair2)
    {
       Double rate= currencyRatesService.readCurrencyRate(currencyPair1+"/"+currencyPair2);
        return new ResponseEntity<>(ResponseUtil.createResponse(rate), HttpStatus.OK);
    }


}
