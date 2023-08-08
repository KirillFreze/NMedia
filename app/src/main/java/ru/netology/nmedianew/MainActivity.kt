package ru.netology.nmedianew

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import ru.netology.nmedianew.databinding.ActivityMainBinding
import ru.netology.nmedianew.dto.Post

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.setOnClickListener {  }
        binding.like.setOnClickListener {  }

        val post = Post(
            1L,
            "Нетология. Университет интернет-профессий будущего",
            "21 мая в 18:36",
            "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            false,
            999,
            1099
        )
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likeCount.text = rounding(post.likes)
            shareCount.text =rounding(post.shars)
            if (post.likeddByMe) {
                like.setImageResource(R.drawable.baseline_favorite_24_red)

            }

            like.setOnClickListener {
                post.likeddByMe = !post.likeddByMe
                if (post.likeddByMe) post.likes++ else post.likes--
                like.setImageResource(if (post.likeddByMe) R.drawable.baseline_favorite_24_red else R.drawable.ic_baseline_favorite_24)
                likeCount.text = rounding(post.likes)
            }
            share.setOnClickListener {
                post.shars++
                shareCount.text = rounding(post.shars)
            }
            avatar.setOnClickListener {  }
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


}

