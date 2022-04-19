package com.example.permitme.Fragment

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.permitme.DataClass.FacultyDataClass
import com.example.permitme.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.ktx.Firebase

class Faculty_Ad : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var  query : Query
    lateinit var emailid : TextInputLayout
    lateinit var pass : TextInputLayout
    private lateinit var mAuth: FirebaseAuth

    lateinit var username : TextInputLayout
    lateinit var b: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_faculty__add)
        database = FirebaseDatabase.getInstance()
        mAuth = Firebase.auth
        emailid = findViewById(R.id.email_faculty)
        pass = findViewById(R.id.pass_faculty)
        b = findViewById(R.id.create_faculty)
        username = findViewById(R.id.username_faculty)
        b.setOnClickListener(View.OnClickListener { view->
            val faculty =  FacultyDataClass(
                username.editText?.text.toString().trim(),
                emailid.editText?.text.toString().trim(),
                pass.editText?.text.toString().trim(),
                "tsec",
                null,"faculty",null,1)
            val user = FirebaseAuth.getInstance().currentUser
            val uid = user?.uid
            reference = database.getReference("users")

            if (uid != null) {
                reference.push().setValue(faculty)
            }

            if (uid != null) {

                reference = database.getReference("tsec").child("faculty")
                reference.push().setValue(faculty)
            }
            this?.let {
                mAuth.createUserWithEmailAndPassword(
                    emailid.editText?.text.toString().trim(),
                    pass.editText?.text.toString().trim()
                )
                    .addOnCompleteListener(this) { task ->
                        println("createAdmin" + emailid.editText?.text.toString().trim())
                        if (task.isSuccessful) {
                            Toast.makeText(this,"Task Successful", Toast.LENGTH_LONG).show()
                            val fragment: Fragment = Faculty_Admin()
                            val fragmentManager = this.supportFragmentManager
                            val fragmentTransaction = fragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.faculty_add,fragment)
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()

                        } else {
                            Toast.makeText(this,"Task UnSuccessful", Toast.LENGTH_LONG).show()
                        }
                    }
            }



        })
    }




}