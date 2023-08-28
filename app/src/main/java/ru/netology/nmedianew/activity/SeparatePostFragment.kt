package ru.netology.nmedianew.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.PopupMenu
import android.widget.ScrollView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import ru.netology.nmedianew.R
import ru.netology.nmedianew.adapter.OnInteractionListener
import ru.netology.nmedianew.adapter.PostsAdapter
import ru.netology.nmedianew.databinding.CardPostBinding
import ru.netology.nmedianew.dto.Post
import ru.netology.nmedianew.viewmodel.PostViewModel


class SeparatePostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = CardPostBinding.inflate(layoutInflater)
        val viewModel: PostViewModel by activityViewModels()
        val idPost = arguments?.getInt("idPost")
        val data = viewModel.data.value?.filter { it.id == idPost?.toLong() }?.last()

        binding.author.text = data?.author
        binding.published.text = data?.published

        binding.content.text = data?.content
        if (data != null) {
            binding.share.text = rounding(data.shars)
        }
        binding.like.isChecked = data?.likeddByMe == true

        if (data != null) {
            binding.like.text = rounding(data.likes)
        }

        binding.menu.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.menu_options)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.remove -> {
                            if (data != null) {
                                viewModel.removeById(data.id)
                            }
                            findNavController().navigateUp()
                            true
                        }

                        R.id.edit -> {

                            findNavController().navigate(
                                R.id.action_separatePostFragment_to_editPostFragment,
                                bundleOf("content" to data?.content)
                            )
                            if (data != null) {
                                viewModel.edit(data)
                            }
                            true
                        }

                        else -> false
                    }
                }
            }.show()
            binding.like.setOnClickListener {
                if (data != null) {
                    viewModel.likeById(data.id)

                    binding.like.text = rounding(data.likes)

                }
            }
            binding.share.setOnClickListener {
                if (data != null) {
                    shareNew(data)
                    data.shars = data.shars++
                    binding.share.text = rounding(data.shars)
                }

            }
        }
        return binding.root
    }

    private fun shareNew(post: Post) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, post.content)
            type = "text/plain"
        }

        val shareIntent =
            Intent.createChooser(intent, getString(R.string.chooser_share_post))
        startActivity(shareIntent)
    }


}