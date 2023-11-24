package ru.netology.nmedianew.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
//        thread {
//            // Начинаем загрузку
//            _data.postValue(FeedModel(loading = true))
//            try {
//                // Данные успешно получены
//                val posts = repository.getAll()
//                FeedModel(posts = posts, empty = posts.isEmpty())
//            } catch (e: IOException) {
//                // Получена ошибка
//                FeedModel(error = true)
//            }.also(_data::postValue)
//        }
        _data.value = FeedModel(loading = true)
        repository.getAllAsync(object : PostRepository.RepositoryCallback<List<Post>> {
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
//            thread {
//                repository.save(it)
//                _postCreated.postValue(Unit)
//            }
            repository.saveAsync(it, object  : PostRepository.RepositoryCallback<Post>{})
            _postCreated.postValue(Unit)
        }
        edited.value = empty
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


        repository.likeByIdAsync(id, object : PostRepository.RepositoryCallback<Post>{
            override fun onSuccess(result: Post) {
                _data.postValue(
                    _data.value?.copy(
                        posts = _data.value?.posts
                            ?.map {
                                if (it.id == id) result else it
                            }
                            ?: emptyList()
                    )
                )
            }
            override fun onError(e: Exception) {

            }
        })

    }

    fun unlikeById(id: Long) {

        repository.unlikeByIdAsync(id, object : PostRepository.RepositoryCallback<Post>{
            override fun onSuccess(result: Post) {
                _data.postValue(
                    _data.value?.copy(
                        posts = _data.value?.posts
                            ?.map {
                                if (it.id == id) result else it
                            }
                            ?: emptyList()
                    )
                )
            }
            override fun onError(e: Exception) {

            }
        })

    }

    fun removeById(id: Long) {
        repository.removeByIdAsync(id, object : PostRepository.RepositoryCallback<Unit>{
            override fun onSuccess(result: Unit) {
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
            override fun onError(e: Exception) {
//Обработка ошибок
            }
        })
//loadPosts()
    }

    //    private val repository: PostRepository = PostRepositoryRoomImpl(
//        AppDb.getInstance(context = application).postDao()
//    )
//
//    var data = repository.getAll()
//    val edited = MutableLiveData(empty)
//
//    fun changeContentAndSave(content: String) {
//        edited.value?.let {
//            val text = content.trim()
//            if (it.content != text) {
//                repository.save(it.copy(author = "Me", content = text, published = "Now"))
//            }
//            edited.value = empty
//        }
//    }
//
//    fun likeById(id: Long) = repository.likeById(id)
//    fun removeById(id: Long) = repository.removeById(id)
    fun sharsById(id: Long) = repository.sharsById(id)

    //    fun edit(post: Post) {
//        edited.value = post
//
//    }
//
    fun clearEdit() {
        edited.value = empty
    }


}

