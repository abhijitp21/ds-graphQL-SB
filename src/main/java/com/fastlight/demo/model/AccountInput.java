package com.fastlight.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountInput {

    private Long accountNumber;
    private String accountStatus;
    private Double accountBalance;
}