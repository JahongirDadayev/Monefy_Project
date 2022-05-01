package com.example.restfulapi.repositroy;

import com.example.restfulapi.entity.DbSalary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SalaryRepository extends JpaRepository<DbSalary, Long> {
    List<DbSalary> findAllByDbUser_IdAndDateBetween(Long dbUser_id, LocalDate startDate, LocalDate endDate);
}
