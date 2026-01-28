package com.example.skeleton.data.remote.post

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class PostService(
    private val client: HttpClient
) {

    suspend fun getPosts(): List<PostDto> {
        return client
            .get("https://jsonplaceholder.typicode.com/posts")
            .body()
    }
}