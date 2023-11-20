package ru.netology.nmedianew.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean,
    var likes: Int,
//    var shars: Int,
//    val video: Boolean
)