package com.example.restfulapi.controller;

import com.example.restfulapi.entity.DbCost;
import com.example.restfulapi.payload.CostDto;
import com.example.restfulapi.payload.result.ApiResponse;
import com.example.restfulapi.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping(value = "/api/cost")
@Validated
public class CostController {
    @Autowired
    CostService costService;

    @GetMapping
    public ResponseEntity<List<DbCost>> getCosts(@RequestParam Long categoryId,
                                                 @Pattern(regexp = "^[0-9]{4}-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])$", message = "You entered the date incorrectly") @RequestParam String startDate,
                                                 @Pattern(regexp = "^[0-9]{4}-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])$", message = "You entered the date incorrectly") @RequestParam String endDate) {
        List<DbCost> costList = costService.getCosts(categoryId, startDate, endDate);
        return (costList != null) ? ResponseEntity.status(HttpStatus.OK).body(costList) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> postCost(@Valid @RequestBody CostDto costDto) {
        ApiResponse apiResponse = costService.postCost(costDto);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> updateCost(@PathVariable Long id,@Valid @RequestBody CostDto costDto){
        ApiResponse apiResponse = costService.updateCost(id, costDto);
        return (apiResponse.isSuccess())?ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deleteCost(@PathVariable Long id){
        ApiResponse apiResponse = costService.deleteCost(id);
        return (apiResponse.isSuccess())?ResponseEntity.status(HttpStatus.OK).body(apiResponse):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
