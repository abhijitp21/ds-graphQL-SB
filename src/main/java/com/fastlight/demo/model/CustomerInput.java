package com.fastlight.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInput {

    private String name;
    private String gender;
    private Long contact;
    private String mail;
    private List<AccountInput> accounts;
}
