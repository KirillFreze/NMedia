package ru.netology.nmedianew.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedianew.dto.Post

interface PostRepository {

//    fun getAll() : List<Post>
    fun getAllAsync(callback: RepositoryCallback<List<Post>>)

//    fun likeById(id : Long)
    fun likeByIdAsync(id: Long, callback: RepositoryCallback<Post>)
//    fun removeById(id: Long)
    fun removeByIdAsync(id : Long, callback: RepositoryCallback<Unit>)
    fun save(post: Post)
    fun saveAsync(post: Post, callback: RepositoryCallback<Post>)
    fun sharsById(id: Long)
//    fun unlikeById(id: Long)
    fun unlikeByIdAsync(id: Long,callback: RepositoryCallback<Post>)

    interface RepositoryCallback<T> {
        fun onSuccess(result: T) {}
        fun onError(e: Exception) {}
    }


}