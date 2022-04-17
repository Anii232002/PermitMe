package com.example.permitme.DataClass

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class AdminDataClass(val username: String? = null,
                 val email: String? = null,
                 val password: String? = null,
                 val institute:String?=null,
                val imageUrl:String?=null,
                  val position:String?=null,
                          val uid:String?=null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}