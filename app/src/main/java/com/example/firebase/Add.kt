package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add.*

class Add : AppCompatActivity() {

    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var reference : DatabaseReference = database.reference.child("Students")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        
        add.setOnClickListener { 
            addUser()

        }
        
        

    }
    fun addUser() {
        val userName : String = name.text.toString()
        val userEmail : String = email.text.toString()
        val userNumber : Long = number.text.toString().toLong()

        // generate unique id from the firebase:
        val id : String = reference.push().key.toString()

        // creating an object of dataclass:
        val student = Students(id,userName,userEmail,userNumber)

        //adding it to the database:
        reference.child(id).setValue(student).addOnCompleteListener { task ->

            if (task.isComplete) {

                Toast.makeText(applicationContext, "User added successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            else {
                Toast.makeText(applicationContext, task.exception.toString(), Toast.LENGTH_SHORT).show()
            }

        }
    }
}