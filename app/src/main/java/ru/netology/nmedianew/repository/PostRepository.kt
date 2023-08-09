package ru.netology.nmedianew.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedianew.dto.Post

interface PostRepository {

    fun get() : LiveData<Post>

    fun like()

    fun share()
}