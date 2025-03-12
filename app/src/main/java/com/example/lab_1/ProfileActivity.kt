package com.example.lab_1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lab_1.model.Post
import com.example.lab_1.model.User
import de.hdodenhof.circleimageview.CircleImageView

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImage: CircleImageView
    private lateinit var userName: TextView
    private lateinit var userUsername: TextView
    private lateinit var postsCount: TextView
    private lateinit var followersCount: TextView
    private lateinit var followingCount: TextView
    private lateinit var btnFollow: Button
    private lateinit var btnMessage: Button
    private lateinit var postsRecyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter

    private val user = User(
        id = "1",
        name = "John Doe",
        username = "johndoe",
        avatarUrl = "https://randomuser.me/api/portraits/men/1.jpg",
        followersCount = 1234,
        followingCount = 456,
        postsCount = 78,
        isFollowing = false
    )

    private val posts = mutableListOf(
        Post(
            id = "1",
            userId = "1",
            text = "Just enjoying a beautiful day in the park. #nature #relax",
            imageUrl = "https://raw.githubusercontent.com/NumeroQuadro/goofy-ahh-spotify-plugin/master/server/file-server/static/img/dinosaur.png",
            likesCount = 152,
            commentsCount = 23
        ),
        Post(
            id = "2",
            userId = "1",
            text = "My new home office setup is finally complete!",
            imageUrl = "https://raw.githubusercontent.com/NumeroQuadro/goofy-ahh-spotify-plugin/master/server/file-server/static/img/dinosaur.png",
            likesCount = 89,
            commentsCount = 12
        ),
        Post(
            id = "3",
            userId = "1",
            text = "Some thoughts on the latest tech trends and how they're shaping our future. What do you all think about AI development?",
            imageUrl = null,
            likesCount = 45,
            commentsCount = 32
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initViews()
        setupRecyclerView()
        displayUserData()
        setupClickListeners()
    }

    private fun initViews() {
        profileImage = findViewById(R.id.profile_image)
        userName = findViewById(R.id.user_name)
        userUsername = findViewById(R.id.user_username)
        postsCount = findViewById(R.id.posts_count)
        followersCount = findViewById(R.id.followers_count)
        followingCount = findViewById(R.id.following_count)
        btnFollow = findViewById(R.id.btn_follow)
        btnMessage = findViewById(R.id.btn_message)
        postsRecyclerView = findViewById(R.id.posts_recycler_view)
    }

    private fun setupRecyclerView() {
        postAdapter = PostAdapter()
        postsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ProfileActivity)
            adapter = postAdapter
        }

        postAdapter.submitList(posts)

        postAdapter.onLikeClickListener = { post ->
            val updatedPost = post.copy(
                isLiked = !post.isLiked,
                likesCount = if (post.isLiked) post.likesCount - 1 else post.likesCount + 1
            )

            val updatedPosts = posts.map {
                if (it.id == post.id) updatedPost else it
            }
            posts.clear()
            posts.addAll(updatedPosts)

            postAdapter.submitList(null)
            postAdapter.submitList(posts)

            Toast.makeText(
                this,
                if (updatedPost.isLiked) "Liked post" else "Unliked post",
                Toast.LENGTH_SHORT
            ).show()
        }

        postAdapter.onCommentClickListener = { post ->
            Toast.makeText(this, "Comment on post", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayUserData() {
        Glide.with(this)
            .load(user.avatarUrl)
            .placeholder(R.drawable.ic_like)
            .into(profileImage)

        userName.text = user.name
        userUsername.text = "@${user.username}"

        postsCount.text = user.postsCount.toString()
        followersCount.text = user.followersCount.toString()
        followingCount.text = user.followingCount.toString()

        updateFollowButtonState()
    }

    private fun setupClickListeners() {
        btnFollow.setOnClickListener {
            user.isFollowing = !user.isFollowing
            user.followersCount += if (user.isFollowing) 1 else -1

            followersCount.text = user.followersCount.toString()
            updateFollowButtonState()

            Toast.makeText(
                this,
                if (user.isFollowing) "Following ${user.username}" else "Unfollowed ${user.username}",
                Toast.LENGTH_SHORT
            ).show()
        }


        btnMessage.setOnClickListener {
            Toast.makeText(this, "Message ${user.username}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateFollowButtonState() {
        if (user.isFollowing) {
            btnFollow.text = "Following"
        } else {
            btnFollow.text = "Follow"
        }
    }
}