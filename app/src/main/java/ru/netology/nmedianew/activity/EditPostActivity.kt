package ru.netology.nmedianew.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.content.Intent
import ru.netology.nmedianew.databinding.ActivityEditPostBinding


class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditPostBinding.inflate(layoutInflater)
        val et = intent.getStringExtra(Intent.EXTRA_TEXT)
        //println(et)
        setContentView(binding.root)
        binding.editPost.requestFocus()
        //binding.editPost.setText(intent?.getStringExtra(Intent.EXTRA_TEXT))
        binding.editPost.setText(et)
        binding.okEdit.setOnClickListener {
            val intent = Intent()
            if (binding.editPost.text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = binding.editPost.text.toString()
                intent.putExtra(Intent.EXTRA_TEXT, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }

    }
}