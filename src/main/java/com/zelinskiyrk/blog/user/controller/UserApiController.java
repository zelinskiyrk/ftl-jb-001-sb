package com.zelinskiyrk.blog.user.controller;

import com.sun.source.tree.BreakTree;
import com.zelinskiyrk.blog.base.api.request.SearchRequest;
import com.zelinskiyrk.blog.base.api.response.OkResponse;
import com.zelinskiyrk.blog.base.api.response.SearchResponse;
import com.zelinskiyrk.blog.user.api.request.RegistrationRequest;
import com.zelinskiyrk.blog.user.api.request.UserRequest;
import com.zelinskiyrk.blog.user.api.response.UserFullResponse;
import com.zelinskiyrk.blog.user.api.response.UserResponse;
import com.zelinskiyrk.blog.user.exception.UserExistException;
import com.zelinskiyrk.blog.user.exception.UserNotExistException;
import com.zelinskiyrk.blog.user.mapping.UserMapping;
import com.zelinskiyrk.blog.user.routes.UserApiRoutes;
import com.zelinskiyrk.blog.user.service.UserApiService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserApiService userApiService;

    @PostMapping(UserApiRoutes.ROOT)
    public OkResponse<UserFullResponse> registration(@RequestBody RegistrationRequest request) throws UserExistException {
        return OkResponse.of(UserMapping.getInstance().getResponseFull().convert(userApiService.registration(request)));
    }

    @GetMapping(UserApiRoutes.BY_ID)
    public OkResponse<UserFullResponse> byId(@PathVariable ObjectId id) throws ChangeSetPersister.NotFoundException {
        return OkResponse.of(UserMapping.getInstance().getResponseFull().convert(
                userApiService.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new)
        ));
    }

    @GetMapping(UserApiRoutes.ROOT)
    public OkResponse<SearchResponse<UserResponse>> search(
            @ModelAttribute SearchRequest request
            ) {
        return OkResponse.of(UserMapping.getInstance().getSearch().convert(
                userApiService.search(request)
        ));
    }

    @PutMapping(UserApiRoutes.BY_ID)
    public OkResponse<UserFullResponse> updateById(
            @PathVariable String id,
            @RequestBody UserRequest userRequest
            ) throws UserNotExistException {
        return OkResponse.of(UserMapping.getInstance().getResponseFull().convert(
                userApiService.update(userRequest)
        ));
    }

    @DeleteMapping(UserApiRoutes.BY_ID)
    public OkResponse<String> deleteById(@PathVariable ObjectId id) {
        userApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
    }

}
