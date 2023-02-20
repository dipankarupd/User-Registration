package com.example.firebase

import android.provider.ContactsContract.CommonDataKinds.Email

data class Students
    (
    val url : String = "",
    val userId : String = "",
    val name : String = "",
    val email: String = "",
    val phNum : Long = 0
    )