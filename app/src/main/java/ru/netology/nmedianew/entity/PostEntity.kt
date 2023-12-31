package ru.netology.nmedianew.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedianew.dto.Post

@Entity
data class PostEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val authorAvatar: String,
    val published: String,
    val content: String,
    var likedByMe: Boolean,
    var likes: Int
//    var shars: Int,
//    val video: Boolean
)
{
    fun toDto() = Post(id, author,authorAvatar,  published, content, likedByMe, likes)

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(dto.id, dto.author, dto.authorAvatar,  dto.published, dto.content, dto.likedByMe, dto.likes)

    }
}