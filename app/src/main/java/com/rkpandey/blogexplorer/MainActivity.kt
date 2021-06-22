package com.rkpandey.blogexplorer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rkpandey.blogexplorer.databinding.ActivityMainBinding
import com.rkpandey.blogexplorer.detail.DetailActivity
import com.rkpandey.blogexplorer.models.Post

private const val TAG = "MainActivity"
const val EXTRA_POST_ID = "EXTRA_POST_ID"
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var blogPostAdapter: BlogPostAdapter
    private val blogPosts = mutableListOf<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.posts.observe(this, Observer { posts ->
            Log.i(TAG, "Number of posts: ${posts.size}")
            val numElements = blogPosts.size
            blogPosts.clear()
            blogPosts.addAll(posts)
            blogPostAdapter.notifyDataSetChanged()
            binding.rvPosts.smoothScrollToPosition(numElements)
        })
        viewModel.isLoading.observe(this, Observer { isLoading ->
            Log.i(TAG, "isLoading $isLoading")
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage == null) {
                binding.tvError.visibility = View.GONE
            } else {
                binding.tvError.visibility = View.VISIBLE
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })

        blogPostAdapter = BlogPostAdapter(this, blogPosts, object : BlogPostAdapter.ItemClickListener {
            override fun onItemClick(post: Post) {
                // navigate to new activity
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(EXTRA_POST_ID, post.id)
                startActivity(intent)
            }
        })
        binding.rvPosts.adapter = blogPostAdapter
        binding.rvPosts.layoutManager = LinearLayoutManager(this)

        binding.button.setOnClickListener {
            viewModel.getPosts()
        }
    }
}