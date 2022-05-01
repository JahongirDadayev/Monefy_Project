package com.example.restfulapi.controller;

import com.example.restfulapi.entity.DbCategory;
import com.example.restfulapi.payload.CategoryDto;
import com.example.restfulapi.payload.result.ApiResponse;
import com.example.restfulapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping(value = "/api/category")
@Validated
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<DbCategory>> getCategories(@RequestParam Long userId,
                                                          @Pattern(regexp = "^[0-9]{4}-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])$", message = "You entered the date incorrectly") @RequestParam String startDate,
                                                          @Pattern(regexp = "^[0-9]{4}-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])$", message = "You entered the date incorrectly") @RequestParam String endDate) {
        List<DbCategory> categoryList = categoryService.getCategories(userId, startDate, endDate);
        return (categoryList != null) ? ResponseEntity.status(HttpStatus.OK).body(categoryList) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DbCategory> getCategory(@PathVariable Long id,
                                                  @Pattern(regexp = "^[0-9]{4}-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])$", message = "You entered the date incorrectly") @RequestParam String startDate,
                                                  @Pattern(regexp = "^[0-9]{4}-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])$", message = "You entered the date incorrectly") @RequestParam String endDate) {
        DbCategory category = categoryService.getCategory(id, startDate, endDate);
        return (category != null) ? ResponseEntity.status(HttpStatus.OK).body(category) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> postCategory(@RequestBody CategoryDto categoryDto) {
        ApiResponse apiResponse = categoryService.postCategory(categoryDto);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        ApiResponse apiResponse = categoryService.updateCategory(id, categoryDto);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        ApiResponse apiResponse = categoryService.deleteCategory(id);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.OK).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
