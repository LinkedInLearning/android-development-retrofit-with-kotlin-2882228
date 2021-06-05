package com.rkpandey.blogexplorer.edit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.rkpandey.blogexplorer.databinding.ActivityEditBinding

private const val TAG = "EditActivity"
class EditActivity : AppCompatActivity() {
    private lateinit var viewModel: EditViewModel
    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val postId = 1

        binding.btnUpdatePut.setOnClickListener {
            Log.i(TAG, "Update via PUT")
        }

        binding.btnUpdatePatch.setOnClickListener {
            Log.i(TAG, "Update via PATCH")
            viewModel.patchPost(postId, binding.etTitle.text.toString(), binding.etContent.text.toString())
        }

        binding.btnDelete.setOnClickListener {
            Log.i(TAG, "DELETE")
            viewModel.deletePost(postId)
        }
    }
}