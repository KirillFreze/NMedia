package ru.netology.nmedianew.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedianew.dto.Post

interface PostRepository {

    fun getAll() : LiveData<List<Post>>

    fun likeById(id : Long)
    fun removeById(id: Long)
    fun save(post: Post)



}