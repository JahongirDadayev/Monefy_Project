package com.example.restfulapi.service;

import com.example.restfulapi.entity.DbCategory;
import com.example.restfulapi.entity.DbCost;
import com.example.restfulapi.payload.CostDto;
import com.example.restfulapi.payload.result.ApiResponse;
import com.example.restfulapi.repositroy.CategoryRepository;
import com.example.restfulapi.repositroy.CostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CostService {
    @Autowired
    CostRepository costRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public List<DbCost> getCosts(Long categoryId, String textStartDate, String textEndDate) {
        if (categoryRepository.existsById(categoryId)) {
            LocalDate startDate = LocalDate.parse(textStartDate);
            LocalDate endDate = LocalDate.parse(textEndDate);
            return costRepository.findAllByDbCategory_IdAndDateBetween(categoryId, startDate, endDate);
        } else {
            return null;
        }
    }

    public ApiResponse postCost(CostDto costDto) {
        return saveCost(new DbCost(), costDto, "Saved cost information");
    }

    public ApiResponse updateCost(Long id, CostDto costDto) {
        Optional<DbCost> optionalDbCost = costRepository.findById(id);
        return optionalDbCost.map(cost -> saveCost(cost, costDto, "Update cost information")).orElseGet(() -> new ApiResponse("No cost matching the id you entered was found", null, false));
    }

    public ApiResponse deleteCost(Long id) {
        if (costRepository.existsById(id)){
            costRepository.deleteById(id);
            return new ApiResponse("Delete cost information", null, true);
        }else {
            return new ApiResponse("No cost matching the id you entered was found", null, false);
        }
    }

    private ApiResponse saveCost(DbCost cost, CostDto costDto, String message){
        Optional<DbCategory> optionalDbCategory = categoryRepository.findById(costDto.getCategoryId());
        if (optionalDbCategory.isPresent()){
            LocalDate date = LocalDate.parse(costDto.getDate());
            cost.setSpendMoney(costDto.getSpendMoney());
            cost.setDbCategory(optionalDbCategory.get());
            cost.setDate(date);
            costRepository.save(cost);
            return new ApiResponse(message, cost, true);
        }else {
            return new ApiResponse("No category matching the id you entered was found", null, false);
        }
    }
}
