package com.example.chatbox.daos

import com.example.chatbox.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class UserDao {                 //its use is to put data is entries in the user database

   val db = FirebaseFirestore.getInstance()         //we get instance of firebase , access of db
    private val usersCollection = db.collection("users")  //users collection

    fun addUser(user: User?) {
        user?.let {
               GlobalScope.launch(Dispatchers.IO) {
                   usersCollection.document(user.uid).set(it)           //Now this will happen in background thread
               }
                         //This all is happening in main thread
        }
    }

    fun getUserById(uId: String): Task<DocumentSnapshot> {
        return usersCollection.document(uId).get()   //.get() gives task

    }
}