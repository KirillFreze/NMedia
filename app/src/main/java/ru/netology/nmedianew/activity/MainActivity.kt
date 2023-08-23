package ru.netology.nmedianew.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import ru.netology.nmedianew.R
import ru.netology.nmedianew.adapter.OnInteractionListener
import ru.netology.nmedianew.adapter.PostsAdapter
import ru.netology.nmedianew.databinding.ActivityMainBinding
import ru.netology.nmedianew.dto.Post
import ru.netology.nmedianew.util.AndroidUtils
import ru.netology.nmedianew.util.focusAndShowKeyboard
import ru.netology.nmedianew.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        val editPostLauncher =
            registerForActivityResult(EditPostResultContract()) { result ->
                result ?: return@registerForActivityResult
                viewModel.changeContentAndSave(result)

            }
        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {


                editPostLauncher.launch(post.content)
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


        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            val newPost = posts.size > adapter.currentList.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }

        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContentAndSave(result)

        }

        binding.fab.setOnClickListener {
            newPostLauncher.launch()
        }


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




