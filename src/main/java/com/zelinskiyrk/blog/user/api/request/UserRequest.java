package com.zelinskiyrk.blog.user.api.request;

import com.zelinskiyrk.blog.user.model.Address;
import com.zelinskiyrk.blog.user.model.Company;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@ApiModel(value = "UserRequest", description = "Model for update user")
public class UserRequest {
    private ObjectId id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address = new Address();
    private Company company = new Company();
}
