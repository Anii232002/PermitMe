package com.example.permitme.Fragment

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.permitme.R
import com.example.permitme.databinding.ActivityFacultyPermissionDescBinding
import com.example.permitme.databinding.ActivityStudentPermissionDecBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class faculty_permission_desc : AppCompatActivity() {

    private lateinit var binding: ActivityFacultyPermissionDescBinding
    private lateinit var database: DatabaseReference
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacultyPermissionDescBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_faculty_permission_desc)


        val accept : Button = findViewById(R.id.tdaccept)
        val deny : Button = findViewById(R.id.tddeny)
        val notify : Button = findViewById(R.id.tdpending)
        val name : TextView = findViewById(R.id.tdname)
        val desc : TextView = findViewById(R.id.tddesc)
        val org : TextView = findViewById(R.id.tdclass)
        val button:Button=findViewById(R.id.tdviewdoc)

        val commentBox:EditText=findViewById(R.id.tdcomment)





        Toast.makeText(this, "yo " + intent.getStringExtra("name"), Toast.LENGTH_SHORT).show()
        name.text = intent.getStringExtra("name")
        desc.text = intent.getStringExtra("desc")
        org.text = intent.getStringExtra("org")

        val doc_url=intent.getStringExtra("doc_url")
        //binding.tdtitle.text = intent.getStringExtra("title")

        val id = intent.getStringExtra("id")

        button.setOnClickListener {

            Log.d("DownloadUrl",doc_url.toString())
            if (!doc_url.isNullOrEmpty()){
                Toast.makeText(this,"Download started",Toast.LENGTH_SHORT)
                download(doc_url, Environment.DIRECTORY_DOWNLOADS)
            }
            else{
                Toast.makeText(this,"No document found",Toast.LENGTH_SHORT)
            }

        }



        accept.setOnClickListener(View.OnClickListener { view ->

            database = FirebaseDatabase.getInstance().getReference("tsec")
            database.child("permission").child(id.toString()).child("status").setValue("accepted")
            Toast.makeText(this,"Permission Accepted",Toast.LENGTH_SHORT).show()
        })

        deny.setOnClickListener(View.OnClickListener { view ->

            database = FirebaseDatabase.getInstance().getReference("tsec")
            database.child("permission").child(id.toString()).child("status").setValue("rejected")
            Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show()
        })

        notify.setOnClickListener(View.OnClickListener { view ->
            val comment=commentBox.text.toString()

            database = FirebaseDatabase.getInstance().getReference("tsec")
            database.child("permission").child(id.toString()).child("status").setValue("pending")
            if (!comment.isEmpty()){
                database.child("permission").child(id.toString()).child("comments").setValue(comment)
            }
            Toast.makeText(this,"Permission pending",Toast.LENGTH_SHORT).show()
        })

//        binding.tdaccept.setOnClickListener(View.OnClickListener {
//
//            database = FirebaseDatabase.getInstance().getReference("tsec")
//            database.child("permission").child(id.toString()).child("status").setValue("accept")
//            Toast.makeText(this,"Permission Accepted",Toast.LENGTH_SHORT).show()
//
//        })
//        binding.tddeny.setOnClickListener(View.OnClickListener {
//            database = FirebaseDatabase.getInstance().getReference("tsec")
//            database.child("permission").child(id.toString()).child("status").setValue("deny")
//            Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show()
//        })
//        binding.tdpending.setOnClickListener(View.OnClickListener {
//            database = FirebaseDatabase.getInstance().getReference("tsec")
//            database.child("permission").child(id.toString()).child("status").setValue("pending")
//            Toast.makeText(this,"Permission Notified",Toast.LENGTH_SHORT).show()
//        })

    }

    private fun download(url:String,destinationDirectory:String){
        val download=getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val uri= Uri.parse(url)

        val request= DownloadManager.Request(uri)

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalFilesDir(this,destinationDirectory,"pdf"+System.currentTimeMillis()+".pdf")

        download.enqueue(request)

    }
}