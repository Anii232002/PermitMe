package com.example.permitme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.permitme.Fragment.UserFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class Home : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun onStart() {
        super.onStart()
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        database = FirebaseDatabase.getInstance()
        reference = database.getReference()
        if(currentUser != null){

//            reference.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                    for (data in dataSnapshot.children) {
//
//                        if (data.child("users").child(currentUser.uid).exists()) {
//                            System.out.println("hi "+data.child(currentUser.uid).child("position").getValue())
//                           if(data.child("users").child(currentUser.uid).child("position").getValue()=="faculty")
//                           {
//                               val i = Intent(applicationContext, UserFragment::class.java)
//                               startActivity(i)
//
//                           }
//                            else if(data.child("users").child(currentUser.uid).child("position").getValue()=="student"){
//                               val i = Intent(applicationContext,UserFragment::class.java)
//                               startActivity(i)
//                           }
//                            else if(data.child("users").child(currentUser.uid).child("position").getValue()=="admin")
//                           {
//                               val i = Intent(applicationContext,Admin::class.java)
//                               startActivity(i)
//                           }
//
//                        } else {
//                            //do something if not exists
//                            Toast.makeText(applicationContext,"No Accounts found with this credentials", Toast.LENGTH_LONG).show()
//
//                        }
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
//                }
//
//
//            })

        }
    }
}