package com.example.firebase.UserRegistration

import android.app.Notification.Action
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.widget.addTextChangedListener
import com.example.firebase.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.item_view.view.*

val auth : FirebaseAuth = FirebaseAuth.getInstance()

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.title = "Sign Up"

        btnSignUp.setOnClickListener {
            // to be implemented
            val email = signUpEmail.text.toString()
            val password = signUpPw.text.toString()
            signUpWithFirebase(email,password)
        }
    }

    fun signUpWithFirebase (email: String, password : String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Successfully created account", Toast.LENGTH_SHORT).show()

                    // go to sign in page:
                    val intent : Intent = Intent(this@SignUp , SignIn :: class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}