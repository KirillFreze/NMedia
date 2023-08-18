package ru.netology.nmedianew

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
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
        val adapter = PostsAdapter (object: OnInteractionListener{
            override fun onLike(post: Post) {
               viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            val newPost = posts.size > adapter.currentList.size
            adapter.submitList(posts){
                if (newPost){
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }
        viewModel.edited.observe(this){
            val text = it.content
            if (it.id != 0L){
                binding.content.setText(it.content)
                binding.content.focusAndShowKeyboard()
                binding.group.visibility = View.VISIBLE
                binding.editText.text = it.content
            }
            binding.close.setOnClickListener {
                viewModel.changeContentAndSave(text)
                binding.group.visibility = View.GONE
                binding.content.setText("")
                binding.content.clearFocus()
                AndroidUtils.hideKeyboard(it)

            }



        }
        binding.save.setOnClickListener {
            val text = binding.content.text.toString()
            if (text.isEmpty()){
                Toast.makeText(this, R.string.error_empty_cotent, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            viewModel.changeContentAndSave(text)

            binding.content.setText("")
            binding.content.clearFocus()
            AndroidUtils.hideKeyboard(it)
            binding.group.visibility = View.GONE
        }

    }




    }
    fun rounding (count: Int) : String{

        val result = when (count){
            in 1000..1099 -> "1K"
            in 1100..9999 ->{
                val oneFigure : Int = count/1000
                val twoFigure : Int = (count/100)%10
                return "$oneFigure.$twoFigure K"}
            in 10000..999999 -> {
                val oneFigure : Int = count/1000
                return "$oneFigure K"
            }
            in 1000000..1099999 -> "1M"
            in 1099999..99999999 ->{
                val oneFigure = count/100000
                val twoFigure = (count/10000)%10
                return "$oneFigure.$twoFigure M"
            }
            else -> count.toString()
        }



        return result
    }




