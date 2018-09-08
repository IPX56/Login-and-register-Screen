package com.idk.ali.thenewproject

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast



class MainActivity : AppCompatActivity() {

    lateinit var mlogin_email : EditText
    lateinit var mlogin_password : EditText
    lateinit var mlogin_but :  Button
    lateinit var mtx_forget : TextView
    lateinit var mcreatuser : TextView
    lateinit var mProgressbar: ProgressDialog
    lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mcreatuser = findViewById(R.id.creatuser)
        mlogin_but = findViewById(R.id.login_but)
        mlogin_email = findViewById(R.id.login_email)
        mlogin_password = findViewById(R.id.login_password)
        mtx_forget = findViewById(R.id.tx_forget)
        mProgressbar = ProgressDialog(this)

        mAuth = FirebaseAuth.getInstance()


        mcreatuser.setOnClickListener {

           val intent = Intent(applicationContext, User_registration::class.java)
            startActivity(intent)
            finish()
        }

        mlogin_but.setOnClickListener{

            val email = mlogin_email.text.toString().trim()
            val password = mlogin_password.text.toString().trim()

            if (TextUtils.isEmpty(email)){

                mlogin_but.error = "Enter Email"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)){

                mlogin_password.error = "Enter Password"
                return@setOnClickListener
            }

            loginUser(email , password)
        }



    }

    private fun loginUser(email:String,password:String) {
        mProgressbar.setMessage("Please wait..")
        mProgressbar.show()

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        mProgressbar.dismiss()

                        val intent = Intent(applicationContext, StartActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Authentication failed.",Toast.LENGTH_SHORT).show()
                    }
                    mProgressbar.dismiss()
                }

    }


}







