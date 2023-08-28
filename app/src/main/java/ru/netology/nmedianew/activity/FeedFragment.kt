package ru.netology.nmedianew.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedianew.R

import ru.netology.nmedianew.adapter.OnInteractionListener
import ru.netology.nmedianew.adapter.PostsAdapter
import ru.netology.nmedianew.databinding.FragmentFeedBinding
import ru.netology.nmedianew.dto.Post
import ru.netology.nmedianew.viewmodel.PostViewModel


class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentFeedBinding.inflate(layoutInflater)

        val viewModel: PostViewModel by activityViewModels()

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {


                findNavController().navigate(R.id.action_feedFragment_to_editPostFragment, bundleOf("content" to post.content))
                viewModel.edit(post)


            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }

            override fun onVideo() {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=WhWc3b3KhnY")
                )
                val shareIntent =
                    Intent.createChooser(intent, "getStringR.string.chooser_share_post")
                startActivity(shareIntent)
            }
            override fun onSeparatePost(post: Post) {
                findNavController().navigate(R.id.action_feedFragment_to_separatePostFragment, bundleOf("idPost" to post.id.toInt()))

            }


        })
        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val newPost = posts.size > adapter.currentList.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }



        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)

        }


        return binding.root
    }




}

fun rounding(count: Int): String {

    val result = when (count) {
        in 1000..1099 -> "1K"
        in 1100..9999 -> {
            val oneFigure: Int = count / 1000
            val twoFigure: Int = (count / 100) % 10
            return "$oneFigure.$twoFigure K"
        }

        in 10000..999999 -> {
            val oneFigure: Int = count / 1000
            return "$oneFigure K"
        }

        in 1000000..1099999 -> "1M"
        in 1099999..99999999 -> {
            val oneFigure = count / 100000
            val twoFigure = (count / 10000) % 10
            return "$oneFigure.$twoFigure M"
        }

        else -> count.toString()
    }



    return result
}




