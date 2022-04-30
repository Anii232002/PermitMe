package com.example.permitme.Fragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
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



        Toast.makeText(this, "yo " + intent.getStringExtra("name"), Toast.LENGTH_SHORT).show()
        name.text = intent.getStringExtra("name")
        desc.text = intent.getStringExtra("desc")
        org.text = intent.getStringExtra("org")
        //binding.tdtitle.text = intent.getStringExtra("title")

        val id = intent.getStringExtra("id")



        accept.setOnClickListener(View.OnClickListener { view ->

            database = FirebaseDatabase.getInstance().getReference("tsec")
            database.child("permission").child(id.toString()).child("status").setValue("accept")
            Toast.makeText(this,"Permission Accepted",Toast.LENGTH_SHORT).show()
        })

        deny.setOnClickListener(View.OnClickListener { view ->

            database = FirebaseDatabase.getInstance().getReference("tsec")
            database.child("permission").child(id.toString()).child("status").setValue("deny")
            Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show()
        })

        notify.setOnClickListener(View.OnClickListener { view ->

            database = FirebaseDatabase.getInstance().getReference("tsec")
            database.child("permission").child(id.toString()).child("status").setValue("pending")
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
}