package com.zelinskiyrk.blog.file.controller;

import com.zelinskiyrk.blog.auth.exceptions.AuthException;
import com.zelinskiyrk.blog.auth.exceptions.NotAccessException;
import com.zelinskiyrk.blog.base.api.request.SearchRequest;
import com.zelinskiyrk.blog.base.api.response.OkResponse;
import com.zelinskiyrk.blog.base.api.response.SearchResponse;
import com.zelinskiyrk.blog.file.api.response.FileResponse;
import com.zelinskiyrk.blog.file.exception.FileExistException;
import com.zelinskiyrk.blog.file.exception.FileNotExistException;
import com.zelinskiyrk.blog.file.mapping.FileMapping;
import com.zelinskiyrk.blog.file.routes.FileApiRoutes;
import com.zelinskiyrk.blog.file.service.FileApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Api(value = "File API")
public class FileApiController {
    private final FileApiService fileApiService;

    @GetMapping(FileApiRoutes.BY_ID)
    @ApiOperation(value = "Find file by ID", notes = "Use this when you need full info about file")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success"),
                    @ApiResponse(code = 404, message = "File not found")
            }
    )
    public OkResponse<FileResponse> byId(
            @ApiParam(value = "File ID") @PathVariable ObjectId id
    ) throws ChangeSetPersister.NotFoundException {
        return OkResponse.of(FileMapping.getInstance().getResponse().convert(
                fileApiService.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new)
        ));
    }

    @GetMapping(FileApiRoutes.ROOT)
    @ApiOperation(value = "Search file", notes = "Use this when you need find file by ?????")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success")
            }
    )
    public OkResponse<SearchResponse<FileResponse>> search(
            @ModelAttribute SearchRequest request
            ) {
        return OkResponse.of(FileMapping.getInstance().getSearch().convert(
                fileApiService.search(request)
        ));
    }

    @DeleteMapping(FileApiRoutes.BY_ID)
    @ApiOperation(value = "Delete file", notes = "Use this when you need delete file")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success")
            }
    )
    public OkResponse<String> deleteById(
            @ApiParam(value = "File ID") @PathVariable ObjectId id
    ) throws AuthException, NotAccessException, ChangeSetPersister.NotFoundException {
        fileApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
    }

}
