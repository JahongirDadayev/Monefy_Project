package com.example.restfulapi.repositroy;

import com.example.restfulapi.entity.DbCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<DbCurrency, Long> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
