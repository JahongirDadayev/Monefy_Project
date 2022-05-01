package com.example.restfulapi.repositroy;

import com.example.restfulapi.entity.DbCost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CostRepository extends JpaRepository<DbCost, Long> {
    List<DbCost> findAllByDbCategory_IdAndDateBetween(Long dbCategory_id, LocalDate startDate, LocalDate endDate);
}
