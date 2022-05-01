package com.example.restfulapi.controller;

import com.example.restfulapi.entity.DbSalary;
import com.example.restfulapi.payload.SalaryDto;
import com.example.restfulapi.payload.result.ApiResponse;
import com.example.restfulapi.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping(value = "/api/salary")
@Validated
public class SalaryController {
    @Autowired
    SalaryService salaryService;

    @GetMapping
    public ResponseEntity<List<DbSalary>> getSalaries(@RequestParam Long userId,
                                                      @RequestParam @Pattern(regexp = "^[0-9]{4}-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])$", message = "You entered the date incorrectly") String startDate,
                                                      @RequestParam @Pattern(regexp = "^[0-9]{4}-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])$", message = "You entered the date incorrectly") String endDate) {
        List<DbSalary> salaryList = salaryService.getSalaries(userId, startDate, endDate);
        return (salaryList != null) ? ResponseEntity.status(HttpStatus.OK).body(salaryList) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> postSalary(@Valid @RequestBody SalaryDto salaryDto){
        ApiResponse apiResponse = salaryService.postSalary(salaryDto);
        return (apiResponse.isSuccess())?ResponseEntity.status(HttpStatus.CREATED).body(apiResponse):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> updateSalary(@PathVariable Long id, @Valid @RequestBody SalaryDto salaryDto){
        ApiResponse apiResponse = salaryService.updateSalary(id, salaryDto);
        return (apiResponse.isSuccess())?ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deleteSalary(@PathVariable Long id){
        ApiResponse apiResponse = salaryService.deleteSalary(id);
        return (apiResponse.isSuccess())?ResponseEntity.status(HttpStatus.OK).body(apiResponse):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
