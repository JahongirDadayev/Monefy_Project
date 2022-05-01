package com.example.restfulapi.controller;

import com.example.restfulapi.entity.DbCurrency;
import com.example.restfulapi.payload.CurrencyDto;
import com.example.restfulapi.payload.result.ApiResponse;
import com.example.restfulapi.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/currency")
public class CurrencyController {
    @Autowired
    CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<List<DbCurrency>> getCurrencies() {
        return ResponseEntity.status(HttpStatus.OK).body(currencyService.getCurrencies());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DbCurrency> getCurrency(@PathVariable Long id) {
        DbCurrency currency = currencyService.getCurrency(id);
        return (currency != null) ? ResponseEntity.status(HttpStatus.OK).body(currency) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> postCurrency(@RequestBody CurrencyDto currencyDto) {
        ApiResponse apiResponse = currencyService.postCurrency(currencyDto);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> updateCurrency(@PathVariable Long id, @RequestBody CurrencyDto currencyDto) {
        ApiResponse apiResponse = currencyService.updateCurrency(id, currencyDto);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deleteCurrency(@PathVariable Long id) {
        ApiResponse apiResponse = currencyService.deleteCurrency(id);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.OK).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
