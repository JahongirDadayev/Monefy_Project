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
public class DbSalary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double value;

    @ManyToOne(optional = false)
    private DbUser dbUser;

    @ManyToOne(optional = false)
    private DbCurrency dbCurrency;

    @Column(nullable = false)
    private LocalDate date;
}
