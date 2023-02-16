package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_update.*

class Update : AppCompatActivity() {

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val reference = database.getReference().child("Students")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        supportActionBar?.title = "Update Data"

        getSetData()
        update.setOnClickListener {
            updateData()
        }
        cancel.setOnClickListener {
            finish()
        }
    }

    fun getSetData() {

        val username = intent.getStringExtra("username").toString()
        val useremail = intent.getStringExtra("useremail").toString()
        val usernumber = intent.getLongExtra("usernumber",0).toString()

        updatename.setText(username)
        updateemail.setText(useremail)
        updatenumber.setText(usernumber)
    }

    fun updateData() {
        val newname : String = updatename.text.toString()
        val newemail : String = updateemail.text.toString()
        val newnum : Long = updatenumber.text.toString().toLong()
        val id  = intent.getStringExtra("userID").toString()


        // using hashmap:
        val studentMap = mutableMapOf<String,Any>()
        studentMap["userId"] = id
        studentMap["name"] = newname
        studentMap["email"] = newemail
        studentMap["phNum"] = newnum

        reference.child("userId").updateChildren(studentMap).addOnCompleteListener { listener ->

            if (listener.isSuccessful) {
                Toast.makeText(applicationContext, "Updated the data", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}