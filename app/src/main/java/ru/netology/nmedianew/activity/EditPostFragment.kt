package ru.netology.nmedianew.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

import ru.netology.nmedianew.databinding.FragmentEditPostBinding
import ru.netology.nmedianew.util.AndroidUtils
import ru.netology.nmedianew.viewmodel.PostViewModel


class EditPostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditPostBinding.inflate(layoutInflater)
        val viewModel: PostViewModel by activityViewModels()
        val et = arguments?.getString("content")


        binding.editPost.requestFocus()

        if (et != null) {
            binding.editPost.setText(et)
        }
        binding.okEdit.setOnClickListener {
            if (!binding.editPost.text.isNullOrBlank()) {
                val content = binding.editPost.text.toString()
                viewModel.changeContent(content)
            }
            findNavController().navigateUp()
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    AlertDialog.Builder(requireActivity()).apply {
                        setTitle("Заголовок")
                        setMessage("Сообщение")
                        setPositiveButton("Да") { _, _ ->
                            viewModel.clearEdit()
                            AndroidUtils.hideKeyboard(binding.editPost)
                            findNavController().navigateUp()
                        }
                        setNegativeButton("Нет") { _, _ -> }
                        setCancelable(true)
                    }.create().show()
                }
            }
        )
        return binding.root
    }



}