package com.example.permitme.Fragment

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.permitme.DataClass.parceble
import com.example.permitme.R
import com.example.permitme.databinding.ActivityStudentPermissionDecBinding
import com.example.permitme.ui.main.PendingFragment
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.File

class student_permission_dec : AppCompatActivity() {

    private lateinit var binding: ActivityStudentPermissionDecBinding
    val storage=Firebase.storage
    private lateinit var ref:StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentPermissionDecBinding.inflate(layoutInflater)
        setContentView(binding.root)



//        var permission = intent.getStringExtra("permission")





        binding.sdname.text = intent.getStringExtra("name")
        binding.sddesc.text = intent.getStringExtra("desc")
        binding.sdclass.text = intent.getStringExtra("org")
        binding.sdtitle.text = intent.getStringExtra("title")
        val doc_url=intent.getStringExtra("doc_url")



//        if (!doc_url.isNullOrEmpty()) {
//            ref = storage.getReferenceFromUrl(doc_url!!)
//        }
//        else{
//            Toast.makeText(this,"No document found",Toast.LENGTH_SHORT)
//        }


        binding.sdviewdoc.setOnClickListener {
                    if (!doc_url.isNullOrEmpty()){
                        Toast.makeText(this,"Download started",Toast.LENGTH_SHORT)
                        download(doc_url,DIRECTORY_DOWNLOADS)
                    }
            else{
                        Toast.makeText(this,"No document found",Toast.LENGTH_SHORT)
                    }

            }


        var a : TextView = findViewById(R.id.sdaccepted)
        var p : TextView = findViewById(R.id.sdpending)
        var d : TextView = findViewById(R.id.sddenied)

        if(intent.getStringExtra("status") == "accepted"){
            p.visibility = View.INVISIBLE
            d.visibility = View.INVISIBLE
        }
        else if(intent.getStringExtra("status") == "pending"){
            a.visibility = View.INVISIBLE
            d.visibility = View.INVISIBLE
        }
        else{
            a.visibility = View.INVISIBLE
            p.visibility = View.INVISIBLE
        }

    }

    private fun download(url:String,destinationDirectory:String){
        val download=getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val uri= Uri.parse(url)

        val request=DownloadManager.Request(uri)

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalFilesDir(this,destinationDirectory,"pdf"+System.currentTimeMillis()+".pdf")

        download.enqueue(request)

    }
}