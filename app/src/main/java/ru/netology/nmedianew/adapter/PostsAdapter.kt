package ru.netology.nmedianew.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedianew.R
import ru.netology.nmedianew.databinding.CardPostBinding

import ru.netology.nmedianew.dto.Post
import ru.netology.nmedianew.rounding

typealias OnLikeListener = (post: Post) -> Unit

class PostsAdapter(private val onLikeListener: OnLikeListener) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }


}

class PostDiffCallback : DiffUtil.ItemCallback<Post>(){
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }


}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likeCount.text = rounding(post.likes)
            shareCount.text = rounding(post.shars)

            if (post.likeddByMe) {
                like.setImageResource(R.drawable.baseline_favorite_24_red)
            } else like.setImageResource(R.drawable.ic_baseline_favorite_24)
            like.setOnClickListener{
                if (post.likeddByMe) post.likes-- else post.likes++
                onLikeListener(post)
            }
            share.setOnClickListener {
                post.shars++
                shareCount.text = rounding(post.shars)
            }
        }
    }
}