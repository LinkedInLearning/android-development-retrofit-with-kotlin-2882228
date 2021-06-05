package com.rkpandey.blogexplorer.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rkpandey.blogexplorer.R
import com.rkpandey.blogexplorer.databinding.ActivityDetailBinding
import com.rkpandey.blogexplorer.edit.EditActivity

const val EXTRA_POST = "EXTRA_POST"
private const val TAG = "DetailActivity"
class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        val postId = 1
        viewModel.isLoading.observe(this, Observer { isLoading ->
            binding.detailProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.clContent.visibility = if (isLoading) View.GONE else View.VISIBLE
        })
        viewModel.post.observe(this, Observer { post ->
            binding.tvPostId.text = "Post #${post.id}"
            binding.tvTitle.text = post.title
            binding.tvBody.text = post.body
        })

        viewModel.user.observe(this, Observer { user ->
            binding.tvUserName.text = user.name
            binding.tvUserEmail.text = user.email
            binding.tvUsername.text = user.username
            binding.tvWebsite.text = user.website
        })

        viewModel.getPostDetails(postId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.miEdit) {
            Log.i(TAG, "Navigate to edit screen")
            viewModel.post.observe(this, Observer { post ->
                val intent = Intent(this, EditActivity::class.java)
                // intent.putExtra(EXTRA_POST, post)
                startActivity(intent)
            })
        }
        return super.onOptionsItemSelected(item)
    }
}