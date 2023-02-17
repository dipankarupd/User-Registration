package com.example.firebase.UserRegistration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.firebase.ForgotPwFragment
import com.example.firebase.MainActivity
import com.example.firebase.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignIn : AppCompatActivity() {

    val auth : FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        supportActionBar?.title = "Sign In"

        forgotPw.setOnClickListener {

            Log.d("foooooooooo" , "i")
            var fragmentManager : FragmentManager = supportFragmentManager
            var forgotPwFragment = ForgotPwFragment()
            forgotPwFragment.show(fragmentManager , "ForgotPwFragment")

        }

        btnSignIn.setOnClickListener {

            val email : String = signInEmail.text.toString()
            val password : String = signInPassword.text.toString()
            signInToFirebase(email,password)

        }
    }

    fun signInToFirebase(email : String, password : String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent: Intent = Intent(this@SignIn, MainActivity :: class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }

    }
}