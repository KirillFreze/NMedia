package ru.netology.nmedianew.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.netology.nmedianew.dao.PostDao
import ru.netology.nmedianew.dto.Post
import ru.netology.nmedianew.entity.PostEntity

//class PostRepositoryRoomImpl(
//    private val dao: PostDao,
//) : PostRepository {
//    override fun getAll() = dao.getAll().map { list ->
//        list.map {
//            it.toDto()
//        }
//    }
//
//    override fun likeById(id: Long) {
//        dao.likeById(id)
//    }
//
//    override fun save(post: Post) {
//        dao.save(PostEntity.fromDto(post))
//    }
//
//    override fun removeById(id: Long) {
//        dao.removeById(id)
//    }
//
//    override fun sharsById(id: Long) {
//        dao.sharsById(id)
//    }
//}