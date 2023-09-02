package ru.netology.nmedianew.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedianew.db.AppDb
import ru.netology.nmedianew.dto.Post
import ru.netology.nmedianew.repository.PostRepository
import ru.netology.nmedianew.repository.PostRepositoryFileImpl
import ru.netology.nmedianew.repository.PostRepositorySQLiteImpl

private val empty = Post(
    0,
    "",
    "",
    "",
    false,
    0,
    0,
    false
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositorySQLiteImpl(
        AppDb.getInstance(application).postDao
    )

    var data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun changeContentAndSave(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (it.content != text) {
                repository.save(it.copy(content = text))
            }
            edited.value = empty
        }
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun removeById(id: Long) = repository.removeById(id)
    fun edit(post: Post) {
        edited.value = post

    }

    fun clearEdit() {
        edited.value = empty
    }


}

