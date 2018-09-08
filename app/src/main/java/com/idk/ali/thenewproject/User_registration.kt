package com.idk.ali.thenewproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.android.gms.tasks.OnCompleteListener
import android.app.ProgressDialog
import android.view.View
import android.widget.*
import com.idk.ali.thenewproject.R.id.regbtn
import com.idk.ali.thenewproject.R.id.textView6
import java.util.*


class User_registration : AppCompatActivity() {

    lateinit var mregusername: EditText
    lateinit var mregemail: EditText
    lateinit var mregpassword: EditText
    lateinit var mregbtn: Button
    lateinit var mProgressbar: ProgressDialog
    lateinit var mAuth: FirebaseAuth
    lateinit var mDatabase: DatabaseReference
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)

        var mLanguage = findViewById(R.id.spinner) as Spinner
        val languages = arrayOf("English", "Arabic")


        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
            spinner.adapter = arrayAdapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    Toast.makeText(this@User_registration, getString(R.string.selected_item) + " " + languages[position], Toast.LENGTH_SHORT).show()

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected

                }
            }
        }
        
        
        mAuth = FirebaseAuth.getInstance();
        //mDatabase = FirebaseDatabase.getInstance().getReference("Users")

        mregusername = findViewById(R.id.regusername)
        mregemail = findViewById(R.id.regemail)
        mregpassword = findViewById(R.id.regpassword)
        mregbtn = findViewById(R.id.regbtn)
        mProgressbar = ProgressDialog(this)
        mregbtn.setOnClickListener {
            val name = mregusername.text.toString().trim()
            val email = mregemail.text.toString().trim()
            val password = mregpassword.text.toString().trim()
            if (TextUtils.isEmpty(name)) {
                mregusername.error = "Enter name"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(email)) {
                mregemail.error = "Enter email"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                mregpassword.error = "Enter password"
                return@setOnClickListener
            }
            creatUser(name, email, password)
        }
    }
private fun creatUser(name: String, email: String, password: String) {
        mProgressbar.setMessage("Please wait..")
        mProgressbar.show()
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, StartActivity::class.java)
                        startActivity(intent)
                        val currentUser = FirebaseAuth.getInstance().currentUser
                        val uid = currentUser!!.uid//create user id
                        val userMap = HashMap<String, String>()
                        userMap["name"] = name
                        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid)
                        mDatabase.setValue(userMap).addOnCompleteListener(OnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(applicationContext, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        })
                    } else {
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        mProgressbar.dismiss()
                    }
                }
    }
}

   
