package com.zelinskiyrk.blog.file.routes;

import com.zelinskiyrk.blog.base.routes.BaseApiRoutes;

public class FileApiRoutes {
    public static final String ROOT = BaseApiRoutes.v1 + "/file";
    public static final String BY_ID = ROOT + "/{id}";

    public static final String DOWNLOAD = "/files/{id}";
}
