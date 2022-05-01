package com.example.restfulapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DbCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double spendMoney;

    @ManyToOne(optional = false)
    private DbCategory dbCategory;

    @Column(nullable = false)
    private LocalDate date;
}
