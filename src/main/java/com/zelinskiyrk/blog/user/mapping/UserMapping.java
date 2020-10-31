package com.zelinskiyrk.blog.user.mapping;

import com.zelinskiyrk.blog.base.mapping.BaseMapping;
import com.zelinskiyrk.blog.user.api.response.UserFullResponse;
import com.zelinskiyrk.blog.user.api.response.UserResponse;
import com.zelinskiyrk.blog.user.model.UserDoc;
import lombok.Getter;

@Getter
public class UserMapping {
    public static class ResponseMapping extends BaseMapping<UserDoc, UserResponse> {

        @Override
        public UserResponse convert(UserDoc userDoc) {
            return UserResponse.builder()
                    .id(userDoc.getId().toString())
                    .firstName(userDoc.getFirstName())
                    .lastName(userDoc.getLastName())
                    .email(userDoc.getEmail())
                    .build();
        }

        @Override
        public UserDoc unmapping(UserResponse userResponse) {
            throw new RuntimeException("dont use this");
        }
    }

    public static class ResponseFullMapping extends BaseMapping<UserDoc, UserFullResponse> {

        @Override
        public UserFullResponse convert(UserDoc userDoc) {
            return UserFullResponse.builder()
                    .id(userDoc.getId().toString())
                    .firstName(userDoc.getFirstName())
                    .lastName(userDoc.getLastName())
                    .email(userDoc.getEmail())
                    .address(userDoc.getAddress())
                    .company(userDoc.getCompany())
                    .build();
        }

        @Override
        public UserDoc unmapping(UserFullResponse response) {
            throw new RuntimeException("dont use this");
        }
    }

    private final ResponseMapping response = new ResponseMapping();
    private final ResponseFullMapping responseFull = new ResponseFullMapping();

    public static UserMapping getInstance(){
        return new UserMapping();
    }
}
