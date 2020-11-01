package com.zelinskiyrk.blog.user.model;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Company", description = "Company")
public class Company {
    private String name;
    private Address address;
}
