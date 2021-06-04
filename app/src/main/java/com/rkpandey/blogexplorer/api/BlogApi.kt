package com.rkpandey.blogexplorer.api

import com.rkpandey.blogexplorer.models.Post
import retrofit2.http.GET

interface BlogApi {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}