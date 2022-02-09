package com.example.chatbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatbox.daos.PostDao
import kotlinx.android.synthetic.main.activity_create_post.*

class CreatePostActivity : AppCompatActivity() {

    private lateinit var postDao: PostDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

       postDao = PostDao()

        postButton.setOnClickListener {

            val input =  postInput.text.toString().trim()    //to get input from edittext

            if (input.isNotEmpty()) {

                postDao.addPost(input)

                finish() //it comes back to main Activity

            }

        }
    }
}