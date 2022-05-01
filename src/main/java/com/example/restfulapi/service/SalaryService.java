package com.example.restfulapi.service;

import com.example.restfulapi.entity.DbCurrency;
import com.example.restfulapi.entity.DbSalary;
import com.example.restfulapi.entity.DbUser;
import com.example.restfulapi.payload.SalaryDto;
import com.example.restfulapi.payload.result.ApiResponse;
import com.example.restfulapi.repositroy.CurrencyRepository;
import com.example.restfulapi.repositroy.SalaryRepository;
import com.example.restfulapi.repositroy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SalaryService {
    @Autowired
    SalaryRepository salaryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    public List<DbSalary> getSalaries(Long userId, String textStartDate, String textEndDate) {
        if (userRepository.existsById(userId)) {
            LocalDate startDate = LocalDate.parse(textStartDate);
            LocalDate endDate = LocalDate.parse(textEndDate);
            return salaryRepository.findAllByDbUser_IdAndDateBetween(userId, startDate, endDate);
        } else {
            return null;
        }
    }

    public ApiResponse postSalary(SalaryDto salaryDto) {
        return saveSalary(new DbSalary(), salaryDto, "Saved salary information");
    }

    public ApiResponse updateSalary(Long id, SalaryDto salaryDto) {
        Optional<DbSalary> optionalDbSalary = salaryRepository.findById(id);
        return optionalDbSalary.map(salary -> saveSalary(salary, salaryDto, "Update salary information")).orElseGet(() -> new ApiResponse("No salary matching the ID you entered", null, false));
    }

    public ApiResponse deleteSalary(Long id) {
        if (salaryRepository.existsById(id)) {
            salaryRepository.deleteById(id);
            return new ApiResponse("Delete salary information", null, true);
        } else {
            return new ApiResponse("No salary matching the ID you entered", null, false);
        }
    }

    private ApiResponse saveSalary(DbSalary salary, SalaryDto salaryDto, String message) {
        Optional<DbUser> optionalDbUser = userRepository.findById(salaryDto.getUserId());
        if (optionalDbUser.isPresent()) {
            Optional<DbCurrency> optionalDbCurrency = currencyRepository.findById(salaryDto.getCurrencyId());
            if (optionalDbCurrency.isPresent()) {
                LocalDate date = LocalDate.parse(salaryDto.getDate());
                salary.setValue(salaryDto.getValue());
                salary.setDbUser(optionalDbUser.get());
                salary.setDbCurrency(optionalDbCurrency.get());
                salary.setDate(date);
                salaryRepository.save(salary);
                return new ApiResponse(message, salary, true);
            } else {
                return new ApiResponse("No currency matching the id you entered was found", null, false);
            }
        } else {
            return new ApiResponse("No user matching the id you entered", null, false);
        }
    }
}
