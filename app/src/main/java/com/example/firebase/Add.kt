package com.example.firebase

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add.*
import java.security.Permission
import java.security.Permissions
import java.util.UUID


class Add : AppCompatActivity() {

    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var reference : DatabaseReference = database.reference.child("Students")

    // request activity result launcher:
    lateinit var activityResultLauncher : ActivityResultLauncher<Intent>

    // creating a uri object:
    var uri : Uri? = null

    //creating a cloud storage reference:
    val firebaseStorage : FirebaseStorage = FirebaseStorage.getInstance()
    val imageReference = firebaseStorage.getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        
        add.setOnClickListener { 
            uploadImage()

        }
        
        cancel.setOnClickListener {
            Toast.makeText(this@Add, "Cancelled the Process", Toast.LENGTH_SHORT).show()
            finish()
        }

        userDp.setOnClickListener {
            chooseImage()
        }

        // register the application:
        registerActivityResultLauncher()
    }

    //check if the request is given at the first time iteself or not:
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1 && permissions.isNotEmpty() && permissions[0] == android.Manifest.permission.READ_EXTERNAL_STORAGE) {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            activityResultLauncher.launch(intent)
        }
    }

     fun chooseImage() {

         if(ContextCompat.checkSelfPermission(this@Add,android.Manifest.permission.READ_EXTERNAL_STORAGE)
         != PackageManager.PERMISSION_GRANTED) {

             ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
         }
         else {
             val intent = Intent()
             intent.type = "image/*"
             intent.action = Intent.ACTION_GET_CONTENT
             activityResultLauncher.launch(intent)
         }
    }

    fun uploadImage() {

        add.isClickable = false
        progressBar.visibility = View.VISIBLE

        val userDp = UUID.randomUUID().toString()
        val imageRef = imageReference.child("Images").child(userDp)

        // putting the path of selected img into ref

        uri?.let {
            imageRef.putFile(it).addOnSuccessListener {
                Toast.makeText(this@Add, "Uploaded succesfully", Toast.LENGTH_SHORT).show()

                val uploadDpReference = imageReference.child("Images").child(userDp)
                uploadDpReference.downloadUrl.addOnSuccessListener {

                    val imageUrl = it.toString()
                    addUser(imageUrl)
                }

            }.addOnFailureListener{

            }
        }
        finish()
    }

    fun registerActivityResultLauncher() {

        // registration process:
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->

                val resultCode = result.resultCode
                val imageData = result.data

                if (resultCode == RESULT_OK && imageData != null) {

                    uri = imageData.data

                    // Using the picasso library:

                    uri?.let {
                        Picasso.get().load(it).into(userDp)
                    }

                }

            }
        )
    }

    fun addUser(url : String) {
        val userName : String = name.text.toString()
        val userEmail : String = email.text.toString()
        val userNumber : Long = number.text.toString().toLong()

        // generate unique id from the firebase:
        val id : String = reference.push().key.toString()

        // creating an object of dataclass:
        val student = Students(url,id,userName,userEmail,userNumber)

        //adding it to the database:
        reference.child(id).setValue(student).addOnCompleteListener { task ->

            if (task.isComplete) {

                
                add.isClickable = true
                progressBar.visibility = View.GONE
                Toast.makeText(applicationContext, "User added successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            else {
                Toast.makeText(applicationContext, task.exception.toString(), Toast.LENGTH_SHORT).show()
            }

        }
    }
}