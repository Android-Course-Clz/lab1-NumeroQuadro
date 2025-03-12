package com.example.lab_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lab_1.R
import com.example.lab_1.model.Post
import android.graphics.drawable.Drawable
import coil.load
import coil.request.ImageRequest
import coil.request.ErrorResult
import coil.request.SuccessResult
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class PostAdapter : ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffCallback()) {
    var onLikeClickListener: ((Post) -> Unit)? = null
    var onCommentClickListener: ((Post) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val postText: TextView = itemView.findViewById(R.id.post_text)
        private val postImage: ImageView = itemView.findViewById(R.id.post_image)
        private val likesCount: TextView = itemView.findViewById(R.id.likes_count)
        private val commentsCount: TextView = itemView.findViewById(R.id.comments_count)
        private val btnLike: Button = itemView.findViewById(R.id.btn_like)
        private val btnComment: Button = itemView.findViewById(R.id.btn_comment)

        fun bind(post: Post) {
            postText.text = post.text

            if (post.imageUrl != null) {
                postImage.visibility = View.VISIBLE
                postImage.load(post.imageUrl) {
                    crossfade(true)
                    listener(
                        onError = { request, error: coil.request.ErrorResult ->
                            error.throwable.printStackTrace()
                        },
                        onSuccess = { request, result: coil.request.SuccessResult ->
                            // Image loaded successfully
                        }
                    )
                }
            } else {
                postImage.visibility = View.GONE
            }

            likesCount.text = "${post.likesCount} likes"
            commentsCount.text = "${post.commentsCount} comments"

            if (post.isLiked) {
                btnLike.setBackgroundColor(itemView.context.getColor(android.R.color.holo_red_light))
            } else {
                btnLike.setBackgroundResource(android.R.color.transparent)
            }

            btnLike.setOnClickListener {
                onLikeClickListener?.invoke(post)
            }

            btnComment.setOnClickListener {
                onCommentClickListener?.invoke(post)
            }
        }
    }

    class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }
}