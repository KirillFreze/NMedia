package ru.netology.nmedianew.dto

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    var likeddByMe: Boolean,
    var likes: Int,
    var shars: Int
)