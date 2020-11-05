package com.zelinskiyrk.blog.todoTask.api.request;

import com.zelinskiyrk.blog.base.api.request.SearchRequest;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
public class TodoTaskSearchRequest extends SearchRequest {
    @ApiParam(name = "OwnerId", value = "Search by user", required = true)
    private ObjectId ownerId;
}
