package ru.netology.nmedianew

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.viewModels
import ru.netology.nmedianew.adapter.PostsAdapter
import ru.netology.nmedianew.databinding.ActivityMainBinding
import ru.netology.nmedianew.dto.Post
import ru.netology.nmedianew.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter {
            viewModel.likeById(it.id)
        }
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.list = posts
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




