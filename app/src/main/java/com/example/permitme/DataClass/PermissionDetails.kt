package com.example.permitme.DataClass

import android.graphics.drawable.Drawable

data class PermissionDetails(
    val studentemail:String?=null, val facultyemail: String?=null,val name:String?=null,val title:String?=null,val org:String?=null, val status:String?=null, val icon: String?=null,
    val doc_url:String?=null, val description: String?=null,val comments:String?="")