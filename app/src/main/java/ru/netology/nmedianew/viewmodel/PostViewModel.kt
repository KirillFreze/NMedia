package ru.netology.nmedianew.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedianew.repository.PostRepository
import ru.netology.nmedianew.repository.PostrepositoryInMemoryImpl

class PostViewModel: ViewModel() {
    private  val repository: PostRepository = PostrepositoryInMemoryImpl()

    val data = repository.get()
    fun like() = repository.like()
    fun share() = repository.share()
}