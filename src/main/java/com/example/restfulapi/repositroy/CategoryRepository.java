package com.example.restfulapi.repositroy;

import com.example.restfulapi.entity.DbCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<DbCategory, Long> {
    List<DbCategory> findAllByDbUser_Id(Long dbUser_id);

    boolean existsByNameAndDbUser_Id(String name, Long dbUser_id);

    boolean existsByNameAndDbUser_IdAndIdNot(String name, Long dbUser_id, Long id);
}
