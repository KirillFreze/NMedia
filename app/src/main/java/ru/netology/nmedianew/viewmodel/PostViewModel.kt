package ru.netology.nmedianew.viewmodel

import android.app.Application
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedianew.R
import ru.netology.nmedianew.activity.FeedFragment
import ru.netology.nmedianew.dto.Post
import ru.netology.nmedianew.model.FeedModel
import ru.netology.nmedianew.repository.PostRepository
import ru.netology.nmedianew.repository.PostRepositoryImpl

import ru.netology.nmedianew.util.SingleLiveEvent
import kotlin.concurrent.thread

private val empty = Post(
    0,
    "",
    "",
    "",
    "",
    false,
    0,
//    0,
//    false
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    // упрощённый вариант
    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
    val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    init {
        loadPosts()
    }

    fun loadPosts() {

        _data.value = FeedModel(loading = true)
        repository.getAllAsync(object : PostRepository.Callback<List<Post>> {
            override fun onSuccess(result: List<Post>) {
                _data.postValue(FeedModel(posts = result, empty = result.isEmpty()))
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
    }

    fun save() {
        edited.value?.let {
            repository.save(it, object : PostRepository.Callback<Post>{
                override fun onSuccess(posts: Post) {
                    _postCreated.value = (Unit)
                }

                override fun onError(e: Exception) {
                    _data.postValue(FeedModel(error = true))
                }
            })
        }
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun likeById(id: Long) {
        repository.likeById(id, object : PostRepository.Callback<Post>{
            override fun onError(e: Exception) {
                showError("Ошибка сервера")

            }

            override fun onSuccess(posts: Post) {
                _data.postValue(
                    _data.value?.copy(
                        posts = _data.value?.posts
                            ?.map {
                                if (it.id == id) posts else it
                            }
                            ?: emptyList()
                    )
                )
            }
        })



    }

    fun unlikeById(id: Long) {
        repository.unLikeByID(id, object : PostRepository.Callback<Post>{
            override fun onError(e: Exception) {
                showError("Ошибка сервера")


            }

            override fun onSuccess(posts: Post) {
                _data.postValue(
                    _data.value?.copy(
                        posts = _data.value?.posts
                            ?.map {
                                if (it.id == id) posts else it
                            }
                            ?: emptyList()
                    )
                )

            }
        })



    }

    fun removeById(id: Long) {
        repository.removeById(id, object : PostRepository.Callback<Unit>{
            override fun onError(e: Exception) {
                showError("Ошибка сервера")

            }

            override fun onSuccess(posts: Unit) {
                _data.postValue(
                    _data.value?.copy(
                        posts = _data.value?.posts
                            ?.filter {
                                it.id != id
                            }
                            ?: emptyList()
                    )
                )

            }

        })



    }





    fun clearEdit() {
        edited.value = empty
    }
    fun showError(text: String){
        Toast.makeText(getApplication(),text, Toast.LENGTH_LONG).show()
    }




}

