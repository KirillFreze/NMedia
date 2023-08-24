package ru.netology.nmedianew.adapter

import android.content.Intent
import android.net.Uri
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedianew.R
import ru.netology.nmedianew.databinding.CardPostBinding

import ru.netology.nmedianew.dto.Post
import ru.netology.nmedianew.activity.rounding
interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onShare(post: Post) {}
    fun onVideo()


}

class PostsAdapter(private val onInteractionListener: OnInteractionListener) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
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
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content

            share.text = rounding(post.shars)


            like.isChecked = post.likeddByMe
            like.text = rounding(post.likes)
            if (post.video) groupVideo.visibility = View.VISIBLE else groupVideo.visibility = View.GONE
//            like.setOnClickListener{
//
//            }
//            share.setOnClickListener {
//                post.shars++
//                share.text = rounding(post.shars)
//            }
            menu.setOnClickListener{
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu_options)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit ->{

                                onInteractionListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
            like.setOnClickListener {
                onInteractionListener.onLike(post)
            }
            share.setOnClickListener {
                onInteractionListener.onShare(post)
                post.shars++
                share.text = rounding(post.shars)
            }
            play.setOnClickListener {
                onInteractionListener.onVideo()

            }
            video.setOnClickListener {
                onInteractionListener.onVideo()

            }
        }
    }
}