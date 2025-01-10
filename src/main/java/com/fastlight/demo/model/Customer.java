package com.fastlight.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    private String customerNumber;
    private String name;
    private String gender;
    private Long contact;
    private String mail;
    private List<Account> accounts;
}