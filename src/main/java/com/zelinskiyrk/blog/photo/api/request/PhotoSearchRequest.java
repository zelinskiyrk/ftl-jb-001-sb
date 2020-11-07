package com.zelinskiyrk.blog.photo.api.request;

import com.zelinskiyrk.blog.base.api.request.SearchRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

@Getter
@Setter
@SuperBuilder
//@Builder
public class PhotoSearchRequest extends SearchRequest {
    private ObjectId albumId;
}
