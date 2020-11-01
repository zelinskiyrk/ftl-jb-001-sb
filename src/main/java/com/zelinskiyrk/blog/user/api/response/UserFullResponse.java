package com.zelinskiyrk.blog.user.api.response;

import com.zelinskiyrk.blog.user.model.Address;
import com.zelinskiyrk.blog.user.model.Company;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ApiModel(value = "UserFullResponse", description = "UserFullData")
public class UserFullResponse extends UserResponse{
    private Address address;
    private Company company;
}
