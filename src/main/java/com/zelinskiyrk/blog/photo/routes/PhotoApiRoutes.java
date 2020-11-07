package com.zelinskiyrk.blog.photo.routes;

import com.zelinskiyrk.blog.base.routes.BaseApiRoutes;

public class PhotoApiRoutes {
    public static final String ROOT = BaseApiRoutes.v1 + "/photo";
    public static final String BY_ID = ROOT + "/{id}";

    public static final String DOWNLOAD = "/photos/{id}";
}
