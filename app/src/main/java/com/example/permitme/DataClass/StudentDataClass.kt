package com.example.permitme.DataClass

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class StudentDataClass(val username: String? = null,
                       val email: String? = null,
                       val password: String? = null,
                       val institute:String?=null,
                       val imageUrl:String?=null,
                       val position:String?=null)  {


}