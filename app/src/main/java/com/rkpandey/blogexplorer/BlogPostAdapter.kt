package com.rkpandey.blogexplorer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rkpandey.blogexplorer.models.Post

class BlogPostAdapter(private val context: Context, private val posts: List<Post>) :
    RecyclerView.Adapter<BlogPostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_blog_post, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvId = itemView.findViewById<TextView>(R.id.tvId)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvBody = itemView.findViewById<TextView>(R.id.tvBlogBody)

        fun bind(post: Post) {
            tvId.text = "Post #${post.id}"
            tvTitle.text = post.title
            tvBody.text = post.body
        }
    }
}