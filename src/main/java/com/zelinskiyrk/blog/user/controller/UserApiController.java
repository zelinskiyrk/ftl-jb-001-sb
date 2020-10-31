package com.zelinskiyrk.blog.user.controller;

import com.sun.source.tree.BreakTree;
import com.zelinskiyrk.blog.user.api.request.RegistrationRequest;
import com.zelinskiyrk.blog.user.api.response.UserFullResponse;
import com.zelinskiyrk.blog.user.api.response.UserResponse;
import com.zelinskiyrk.blog.user.exception.UserExistException;
import com.zelinskiyrk.blog.user.mapping.UserMapping;
import com.zelinskiyrk.blog.user.routes.UserApiRoutes;
import com.zelinskiyrk.blog.user.service.UserApiService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserApiService userApiService;

    @PostMapping(UserApiRoutes.ROOT)
    public UserResponse registration(@RequestBody RegistrationRequest request) throws UserExistException {
        return UserMapping.getInstance().getResponse().convert(userApiService.registration(request));
    }

    @GetMapping(UserApiRoutes.BY_ID)
    public UserFullResponse byId(@PathVariable ObjectId id) throws ChangeSetPersister.NotFoundException {
        return UserMapping.getInstance().getResponseFull().convert(
                userApiService.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new)
        );
    }

}
