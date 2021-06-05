package com.rkpandey.blogexplorer.edit

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

        viewModel = ViewModelProvider(this).get(EditViewModel::class.java)
        viewModel.post.observe(this, Observer { updatedPost ->
            if (updatedPost == null) {
                binding.clPostResult.visibility = View.GONE
                return@Observer
            }
            binding.tvUpdatedTitle.text = updatedPost.title
            binding.tvUpdatedContent.text = updatedPost.body
            binding.clPostResult.visibility = View.VISIBLE
        })
        viewModel.currentStatus.observe(this, Observer { currentStatus ->
            when (currentStatus) {
                ResultStatus.IDLE -> {
                    binding.tvStatus.text = "Idle"
                    binding.tvStatus.setTextColor(Color.GRAY)
                }
                ResultStatus.WORKING -> {
                    binding.tvStatus.text = "Working..."
                    binding.tvStatus.setTextColor(Color.MAGENTA)
                }
                ResultStatus.SUCCESS -> {
                    binding.tvStatus.text = "Success!"
                    binding.tvStatus.setTextColor(Color.GREEN)
                }
                ResultStatus.ERROR -> {
                    binding.tvStatus.text = "Error :("
                    binding.tvStatus.setTextColor(Color.RED)
                }
                else -> {
                    throw IllegalStateException("Unexpected result state found")
                }
            }
        })

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