package com.zelinskiyrk.blog.todoTask.api.request;

import com.zelinskiyrk.blog.base.api.request.SearchRequest;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

@Getter
@Setter
@SuperBuilder
public class TodoTaskSearchRequest extends SearchRequest {

}
