package com.example.firebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import org.w3c.dom.Text

class ForgotPassword : DialogFragment() {
    val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var smbt: Button // Declare smbt as a private property

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        smbt = view.findViewById(R.id.smbt) // Initialize smbt in onViewCreated()

        smbt.setOnClickListener {
            val userMail = reEmail.text.toString()
            auth.sendPasswordResetEmail(userMail)
            finish.setText("The password reset mail is sent to $userMail.")
            returnBtn.isVisible = true
        }

        returnBtn.setOnClickListener {
            dialog!!.dismiss()
        }
    }
}
