package com.example.skeleton.data.mapper

import com.example.skeleton.data.remote.post.PostDto
import com.example.skeleton.domain.model.Post

fun PostDto.toDomain(): Post {
    return Post(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}
