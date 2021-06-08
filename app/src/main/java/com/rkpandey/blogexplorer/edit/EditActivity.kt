package com.rkpandey.blogexplorer.edit

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rkpandey.blogexplorer.MainActivity
import com.rkpandey.blogexplorer.api.RetrofitInstance
import com.rkpandey.blogexplorer.databinding.ActivityEditBinding
import com.rkpandey.blogexplorer.detail.EXTRA_POST
import com.rkpandey.blogexplorer.models.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "EditActivity"
class EditActivity : AppCompatActivity() {
    private lateinit var viewModel: EditViewModel
    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createPost()

        val post = intent.getSerializableExtra(EXTRA_POST) as Post
        title = "Editing Post #${post.id}"
        binding.etTitle.setText(post.title)
        binding.etContent.setText(post.content)

        viewModel = ViewModelProvider(this).get(EditViewModel::class.java)
        viewModel.post.observe(this, Observer { updatedPost ->
            if (updatedPost == null) {
                binding.clPostResult.visibility = View.GONE
                return@Observer
            }
            binding.tvUpdatedTitle.text = updatedPost.title
            binding.tvUpdatedContent.text = updatedPost.content
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
        viewModel.wasDeletionSuccessful.observe(this, Observer { wasDeletionSuccessful ->
            if (wasDeletionSuccessful) {
                Toast.makeText(this, "Deleted post successfully!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
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

    // Sample of how to create our own CoroutineScope to make network requests
    private fun createPost() {
        CoroutineScope(Dispatchers.IO).launch {
            val localNewPost = Post(2, 32, "My post title", "Body of post id #32")
            val newPost = RetrofitInstance.api.createPost(localNewPost)
            Log.i(TAG, "New post $newPost")
            val urlEncodedPost =
                RetrofitInstance.api.createPostUrlEncode(4, "New title", "Post content")
            Log.i(TAG, "URL encoded post: $urlEncodedPost")
        }
    }
}