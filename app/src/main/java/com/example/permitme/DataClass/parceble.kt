package com.example.permitme.DataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class parceble(
    val name:String?=null,val title:String?=null,val org:String?=null, val status:String?=null,val doc_url:String?=null,
    val description: String?=null,val userid: String?=null) :Parcelable