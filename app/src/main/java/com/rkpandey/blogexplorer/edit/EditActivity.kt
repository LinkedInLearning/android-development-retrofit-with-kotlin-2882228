package com.rkpandey.blogexplorer.edit

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rkpandey.blogexplorer.databinding.ActivityEditBinding
import com.rkpandey.blogexplorer.detail.EXTRA_POST
import com.rkpandey.blogexplorer.models.Post

private const val TAG = "EditActivity"
class EditActivity : AppCompatActivity() {
    private lateinit var viewModel: EditViewModel
    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val post = intent.getSerializableExtra(EXTRA_POST) as Post
        title = "Editing Post #${post.id}"
        binding.etTitle.setText(post.title)
        binding.etContent.setText(post.body)

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

        binding.btnUpdatePut.setOnClickListener {
            Log.i(TAG, "Update via PUT")
            viewModel.updatePost(post.id,
                Post(
                    post.userId,
                    post.id,
                    binding.etTitle.text.toString(),
                    binding.etContent.text.toString()
                )
            )
        }

        binding.btnUpdatePatch.setOnClickListener {
            Log.i(TAG, "Update via PATCH")
            viewModel.patchPost(post.id, binding.etTitle.text.toString(), binding.etContent.text.toString())
        }

        binding.btnDelete.setOnClickListener {
            Log.i(TAG, "DELETE")
            viewModel.deletePost(post.id)
        }
    }
}