package com.example.firebase.UserRegistration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebase.R
import kotlinx.android.synthetic.main.activity_first_page.*

class FirstPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)

        signIn.setOnClickListener {
            // to be implemented:
            val intent: Intent = Intent(this@FirstPage,SignIn::class.java)
            startActivity(intent)
        }

        signUp.setOnClickListener {
            // to be implemented:

            val intent: Intent = Intent(this@FirstPage,SignUp::class.java)
            startActivity(intent)
        }
    }
}