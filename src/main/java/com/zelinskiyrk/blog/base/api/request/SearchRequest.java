package com.zelinskiyrk.blog.base.api.request;

import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
//@Builder
public class SearchRequest {
    @ApiParam(name = "query", value = "Search by fields", required = false)
    protected String query = null;
    @ApiParam(name = "size", value = "List size (default 100)", required = false)
    protected Integer size = 100;
    @ApiParam(name = "skip", value = "Skip first in search (default 0)", required = false)
    protected Long skip = 0l;
}
