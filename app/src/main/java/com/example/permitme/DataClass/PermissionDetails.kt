package com.example.permitme.DataClass

import android.graphics.drawable.Drawable

data class PermissionDetails(
    val studentemail:String, val facultyemail: String?,val title:String?,val org:String?, val status:String, val icon: Drawable?,
    val doc_url:String, val description: String)