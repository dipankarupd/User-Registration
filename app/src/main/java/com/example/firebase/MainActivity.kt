package com.example.firebase

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.UserRegistration.FirstPage
import com.google.firebase.auth.FirebaseAuth
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


        // delete the data when swiping right
        ItemTouchHelper (object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id = adapter.getItem(viewHolder.adapterPosition)
                reference.child("userId").removeValue()
            }

        }).attachToRecyclerView(recyclerview)
    }

    // retriving the database from web
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

    //creating the logout option menu:
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logout ->{

                Log.d("LogOut", "Pugyo")
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("Confirm Logout")
                dialog.setIcon(R.drawable.ic_baseline_warning_24)
                dialog.setMessage("if you want to logout, press yes")
                dialog.setNegativeButton("No" , DialogInterface.OnClickListener {dialog, which ->
                    dialog.cancel()
                })

                dialog.setPositiveButton("Yes", DialogInterface.OnClickListener {dialog, which ->

                    val intent : Intent = Intent(this@MainActivity , FirstPage :: class.java)
                    startActivity(intent)
                    Toast.makeText(applicationContext, "Logout Successful", Toast.LENGTH_SHORT).show()
                })
                dialog.show()

            }
        }
        return true
    }
}