package ru.netology.nmedianew.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedianew.dto.Post

interface PostRepository {


    fun getAllAsync(callback: RepositoryCallback<List<Post>>)


    fun likeByIdAsync(id: Long, callback: RepositoryCallback<Post>)

    fun removeByIdAsync(id: Long, callback: RepositoryCallback<Unit>)
    fun save(post: Post)
    fun saveAsync(post: Post, callback: RepositoryCallback<Post>)
    fun sharsById(id: Long)

    fun unlikeByIdAsync(id: Long, callback: RepositoryCallback<Post>)

    interface RepositoryCallback<T> {
        fun onSuccess(result: T) {}
        fun onError(e: Exception) {}
    }


}