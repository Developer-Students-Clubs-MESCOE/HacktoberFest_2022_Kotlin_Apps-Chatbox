package com.example.chatbox.daos

import com.example.chatbox.models.Post
import com.example.chatbox.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {

    val db = FirebaseFirestore.getInstance()     //got db
    val postCollections = db.collection("posts")
    val auth = Firebase.auth  //for getting current user

    fun addPost(text:String) {

          val currentUserId = auth.currentUser!!.uid      //for getting current user , !!ensures its non null

          GlobalScope.launch {
              val userDao = UserDao()
              val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!
              val currentTime = System.currentTimeMillis()
              val post = Post(text,user,currentTime)

              postCollections.document().set(post)    //post collection goes into document
          }

    }

    fun getPostById(postId: String): Task<DocumentSnapshot> {
        return  postCollections.document(postId).get()
    }

    fun updateLikes(postId:String) {

        GlobalScope.launch {

            val currentUserId = auth.currentUser!!.uid
            val post = getPostById(postId).await().toObject(Post::class.java)!!
            val isLiked = post.likedBy.contains(currentUserId)

            if(isLiked) {
                post.likedBy.remove(currentUserId)
            } else {
                post.likedBy.add(currentUserId)
            }
            postCollections.document(postId).set(post)
        }
    }

}