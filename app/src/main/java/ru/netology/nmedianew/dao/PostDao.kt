package ru.netology.nmedianew.dao

import ru.netology.nmedianew.dto.Post

interface PostDao {
    fun getAll(): List<Post>
    fun save(post: Post): Post
    fun likeById(id: Long)
    fun removeById(id: Long)
}