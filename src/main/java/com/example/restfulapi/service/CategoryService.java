package com.example.restfulapi.service;

import com.example.restfulapi.entity.DbCategory;
import com.example.restfulapi.entity.DbCost;
import com.example.restfulapi.entity.DbUser;
import com.example.restfulapi.payload.CategoryDto;
import com.example.restfulapi.payload.result.ApiResponse;
import com.example.restfulapi.repositroy.CategoryRepository;
import com.example.restfulapi.repositroy.CostRepository;
import com.example.restfulapi.repositroy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CostRepository costRepository;

    @Autowired
    UserRepository userRepository;

    public List<DbCategory> getCategories(Long userId, String textStartDate, String textEndDate) {
        List<DbCategory> categoryList = categoryRepository.findAllByDbUser_Id(userId);
        for (int i = 0; i < categoryList.size(); i++) {
            DbCategory category = generationSpendPresent(categoryList.get(i), textStartDate, textEndDate);
            if (category != null) {
                categoryList.set(i, category);
            } else {
                return null;
            }
        }
        return categoryList;
    }

    public DbCategory getCategory(Long id, String startDate, String endDate) {
        Optional<DbCategory> optionalDbCategory = categoryRepository.findById(id);
        return optionalDbCategory.map(category -> generationSpendPresent(category, startDate, endDate)).orElse(null);
    }

    public ApiResponse postCategory(CategoryDto categoryDto) {
        if (!categoryRepository.existsByNameAndDbUser_Id(categoryDto.getName(), categoryDto.getUserId())) {
            return saveCategory(new DbCategory(), categoryDto, "Saved category information");
        } else {
            return new ApiResponse("There is a category you are entering", null, false);
        }
    }

    public ApiResponse updateCategory(Long id, CategoryDto categoryDto) {
        Optional<DbCategory> optionalDbCategory = categoryRepository.findById(id);
        if (optionalDbCategory.isPresent()) {
            if (!categoryRepository.existsByNameAndDbUser_IdAndIdNot(categoryDto.getName(), categoryDto.getUserId(), id)) {
                return saveCategory(optionalDbCategory.get(), categoryDto, "Update category information");
            } else {
                return new ApiResponse("There is a category you are entering", null, false);
            }
        } else {
            return new ApiResponse("Could not find category that matches the id you entered", null, false);
        }
    }

    public ApiResponse deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            try {
                categoryRepository.deleteById(id);
                return new ApiResponse("Delete category information", null, true);
            } catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("Unable to delete this category due to errors", null, false);
            }
        } else {
            return new ApiResponse("Could not find category that matches the id you entered", null, false);
        }
    }

    private DbCategory generationSpendPresent(DbCategory myCategory, String textStartDate, String textEndDate) {
        try {
            LocalDate startDate = LocalDate.parse(textStartDate);
            LocalDate endDate = LocalDate.parse(textEndDate);
            Double sumSpendMoney = 0D;
            List<DbCategory> categoryList = categoryRepository.findAllByDbUser_Id(myCategory.getDbUser().getId());
            for (DbCategory category : categoryList) {
                sumSpendMoney += sumCost(category, startDate, endDate);
            }
            if (sumSpendMoney != 0) {
                myCategory.setSpendPresent((short) Math.round(sumCost(myCategory, startDate, endDate) * 100 / sumSpendMoney));
            }else {
                myCategory.setSpendPresent((short) 0);
            }
            return myCategory;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Double sumCost(DbCategory category, LocalDate startDate, LocalDate endDate) {
        Double sumCategorySpendMoney = 0D;
        List<DbCost> categoryCostList = costRepository.findAllByDbCategory_IdAndDateBetween(category.getId(), startDate, endDate);
        for (DbCost categoryCost : categoryCostList) {
            sumCategorySpendMoney += categoryCost.getSpendMoney();
        }
        return sumCategorySpendMoney;
    }

    private ApiResponse saveCategory(DbCategory category, CategoryDto categoryDto, String message) {
        Optional<DbUser> optionalDbUser = userRepository.findById(categoryDto.getUserId());
        if (optionalDbUser.isPresent()) {
            category.setName(categoryDto.getName());
            category.setDbUser(optionalDbUser.get());
            categoryRepository.save(category);
            return new ApiResponse(message, category, true);
        } else {
            return new ApiResponse("No user matching the id you entered", null, false);
        }
    }
}
