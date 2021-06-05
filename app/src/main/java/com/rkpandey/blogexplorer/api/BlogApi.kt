package com.rkpandey.blogexplorer.api

import com.rkpandey.blogexplorer.models.Post
import com.rkpandey.blogexplorer.models.User
import retrofit2.http.GET
import retrofit2.http.Path

interface BlogApi {
    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") postId: Int): Post

    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: Int): User
}