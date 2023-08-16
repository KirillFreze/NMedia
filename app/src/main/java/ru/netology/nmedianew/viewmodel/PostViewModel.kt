package ru.netology.nmedianew.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedianew.repository.PostRepository
import ru.netology.nmedianew.repository.PostRepositoryInMemoryImpl

class PostViewModel: ViewModel() {
    private  val repository: PostRepository = PostRepositoryInMemoryImpl()

    val data = repository.getAll()
    fun likeById(id : Long) = repository.likeById(id)

}