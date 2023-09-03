package ru.netology.nmedianew.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedianew.dto.Post

class PostRepositoryFileImpl(
    private val context: Context,
) : PostRepository {
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val postFileName = "posts.json"
    private val nextIdKey = "next_id.json"
    private var nextId = 1L
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        val postsFile = context.filesDir.resolve(postFileName)
        posts = if (postsFile.exists()) {
            // если файл есть - читаем
            postsFile.reader().buffered().use {
                gson.fromJson(it, type)

            }
        } else {

            emptyList()
        }

        val nextIdFile = context.filesDir.resolve(nextIdKey)
        nextId = if (nextIdFile.exists()) {

            nextIdFile.reader().buffered().use {
                gson.fromJson(it, Long::class.java)
            }

        } else {

            nextId
        }
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun save(post: Post) {
        if (post.id == 0L) {
            // TODO: remove hardcoded author & published
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    likedByMe = false,
                    published = "now"
                )
            ) + posts
            data.value = posts
            sync()
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
        sync()
    }

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
        sync()
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync()
    }

    override fun sharsById(id: Long) {
        TODO("Not yet implemented")
    }

    private fun sync() {
        context.filesDir.resolve(postFileName).writer().use {
            it.write(gson.toJson(posts))
        }
        context.filesDir.resolve(nextIdKey).writer().use {
            it.write(gson.toJson(nextId))
        }
    }
}