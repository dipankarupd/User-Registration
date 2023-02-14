package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var reference = database.getReference().child("Students")

    var studentList = ArrayList<Students>()
    lateinit var adapter : ViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview.layoutManager = LinearLayoutManager(this)

        addButton.setOnClickListener {

            val intent = Intent(this, Add :: class.java)
            startActivity(intent)
        }
        retriveDataFromDatabase()
    }
    fun retriveDataFromDatabase () {

        reference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                studentList.clear()

                for (eachUser in snapshot.children) {
                    val student = eachUser.getValue(Students :: class.java)

                    if (student != null) {
                        studentList.add(student)
                    }
                    adapter = ViewAdapter(studentList,this@MainActivity)
                    recyclerview.adapter = adapter

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Cannot perform the addition operation", Toast.LENGTH_SHORT).show()
            }

        })
    }
}