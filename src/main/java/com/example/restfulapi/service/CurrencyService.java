package com.example.restfulapi.service;

import com.example.restfulapi.entity.DbCurrency;
import com.example.restfulapi.payload.CurrencyDto;
import com.example.restfulapi.payload.result.ApiResponse;
import com.example.restfulapi.repositroy.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {
    @Autowired
    CurrencyRepository currencyRepository;


    public List<DbCurrency> getCurrencies() {
        return currencyRepository.findAll();
    }

    public DbCurrency getCurrency(Long id) {
        Optional<DbCurrency> optionalDbCurrency = currencyRepository.findById(id);
        return optionalDbCurrency.orElse(null);
    }

    public ApiResponse postCurrency(CurrencyDto currencyDto) {
        if (!currencyRepository.existsByName(currencyDto.getName())) {
            return saveCurrency(new DbCurrency(), currencyDto, "Saved currency information");
        } else {
            return new ApiResponse("There is a currency you are entering", null, false);
        }
    }

    public ApiResponse updateCurrency(Long id, CurrencyDto currencyDto) {
        Optional<DbCurrency> optionalDbCurrency = currencyRepository.findById(id);
        if (optionalDbCurrency.isPresent()) {
            if (!currencyRepository.existsByNameAndIdNot(currencyDto.getName(), id)) {
                return saveCurrency(optionalDbCurrency.get(), currencyDto, "Update currency information");
            } else {
                return new ApiResponse("There is a currency you are entering", null, false);
            }
        } else {
            return new ApiResponse("No currency matching the id you entered was found", null, false);
        }
    }

    public ApiResponse deleteCurrency(Long id) {
        if (currencyRepository.existsById(id)) {
            try {
                currencyRepository.deleteById(id);
                return new ApiResponse("Delete currency information", null, true);
            } catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("Unable to delete this category due to errors", null, false);
            }
        } else {
            return new ApiResponse("No currency matching the id you entered was found", null, false);
        }
    }

    private ApiResponse saveCurrency(DbCurrency currency, CurrencyDto currencyDto, String message) {
        currency.setName(currencyDto.getName());
        currencyRepository.save(currency);
        return new ApiResponse(message, currency, true);
    }
}
