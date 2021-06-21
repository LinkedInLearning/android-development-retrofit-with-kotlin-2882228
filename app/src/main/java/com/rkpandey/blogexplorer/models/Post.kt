package com.rkpandey.blogexplorer.models

import com.squareup.moshi.Json
import java.io.Serializable

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    @field:Json(name = "body") val content: String): Serializable
// snake_case camelCasing