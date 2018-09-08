package com.idk.ali.thenewproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class StartActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        mAuth = FirebaseAuth.getInstance()

    }
    override fun onStart() {
        super.onStart()

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser == null){

            val intent = Intent(this,this::class.java)
            startActivity(intent)
            finish()
        }else{

        }
    }


}
