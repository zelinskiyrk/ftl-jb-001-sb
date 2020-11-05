package com.zelinskiyrk.blog.todoTask.controller;

import com.zelinskiyrk.blog.base.api.response.OkResponse;
import com.zelinskiyrk.blog.base.api.response.SearchResponse;
import com.zelinskiyrk.blog.todoTask.api.request.TodoTaskRequest;
import com.zelinskiyrk.blog.todoTask.api.request.TodoTaskSearchRequest;
import com.zelinskiyrk.blog.todoTask.api.response.TodoTaskResponse;
import com.zelinskiyrk.blog.todoTask.exception.TodoTaskExistException;
import com.zelinskiyrk.blog.todoTask.exception.TodoTaskNotExistException;
import com.zelinskiyrk.blog.todoTask.mapping.TodoTaskMapping;
import com.zelinskiyrk.blog.todoTask.routes.TodoTaskApiRoutes;
import com.zelinskiyrk.blog.todoTask.service.TodoTaskApiService;
import com.zelinskiyrk.blog.user.exception.UserNotExistException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequiredArgsConstructor
@Api(value = "TodoTask API")
public class TodoTaskApiController {
    private final TodoTaskApiService todoTaskApiService;

    @PostMapping(TodoTaskApiRoutes.ROOT)
    @ApiOperation(value = "Create", notes = "Use this when you need create new todoTask")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "TodoTask already exist")
    })
    public OkResponse<TodoTaskResponse> create(@RequestBody TodoTaskRequest request) throws TodoTaskExistException, UserNotExistException {
        return OkResponse.of(TodoTaskMapping.getInstance().getResponse().convert(todoTaskApiService.create(request)));
    }

    @GetMapping(TodoTaskApiRoutes.BY_ID)
    @ApiOperation(value = "Find todoTask by ID", notes = "Use this when you need full info about todoTask")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success"),
                    @ApiResponse(code = 404, message = "TodoTask not found")
            }
    )
    public OkResponse<TodoTaskResponse> byId(
            @ApiParam(value = "TodoTask ID") @PathVariable ObjectId id
    ) throws ChangeSetPersister.NotFoundException {
        return OkResponse.of(TodoTaskMapping.getInstance().getResponse().convert(
                todoTaskApiService.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new)
        ));
    }

    @GetMapping(TodoTaskApiRoutes.ROOT)
    @ApiOperation(value = "Search todoTask", notes = "Use this when you need find todoTask by ?????")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success")
            }
    )
    public OkResponse<SearchResponse<TodoTaskResponse>> search(
            @ModelAttribute TodoTaskSearchRequest request
            ) throws ResponseStatusException {
        return OkResponse.of(TodoTaskMapping.getInstance().getSearch().convert(
                todoTaskApiService.search(request)
        ));
    }

    @PutMapping(TodoTaskApiRoutes.BY_ID)
    @ApiOperation(value = "Update todoTask", notes = "Use this when you need update todoTask info")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success"),
                    @ApiResponse(code = 400, message = "TodoTask ID invalid")
            }
    )
    public OkResponse<TodoTaskResponse> updateById(
            @ApiParam(value = "TodoTask ID") @PathVariable String id,
            @RequestBody TodoTaskRequest todoTaskRequest
            ) throws TodoTaskNotExistException {
        return OkResponse.of(TodoTaskMapping.getInstance().getResponse().convert(
                todoTaskApiService.update(todoTaskRequest)
        ));
    }

    @DeleteMapping(TodoTaskApiRoutes.BY_ID)
    @ApiOperation(value = "Delete todoTask", notes = "Use this when you need delete todoTask")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success")
            }
    )
    public OkResponse<String> deleteById(
            @ApiParam(value = "TodoTask ID") @PathVariable ObjectId id
    ) {
        todoTaskApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
    }

}
