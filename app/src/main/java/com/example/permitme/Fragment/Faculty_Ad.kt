package com.example.permitme.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.permitme.DataClass.FacultyDataClass
import com.example.permitme.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.ktx.Firebase

class Faculty_Ad : Fragment() {
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

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_faculty__add, container, false)

        database = FirebaseDatabase.getInstance()
        mAuth = Firebase.auth
        emailid = v.findViewById(R.id.email_faculty)
        pass = v.findViewById(R.id.pass_faculty)
        b = v.findViewById(R.id.create_faculty)
        username = v.findViewById(R.id.username_faculty)
        b.setOnClickListener(View.OnClickListener { view->
            val faculty =  FacultyDataClass(
                username.editText?.text.toString().trim(),
                emailid.editText?.text.toString().trim(),
                pass.editText?.text.toString().trim(),
                "tsec",
                null,"faculty")
            val user = FirebaseAuth.getInstance().currentUser
            val uid = user?.uid
            reference = database.getReference("users")

            if (uid != null) {
                reference.child(uid).setValue(faculty)
            }
            reference = database.getReference("tsec")
            if (uid != null) {
                reference.child("faculty").child(uid).setValue(faculty)
            }
            activity?.let {
                mAuth.createUserWithEmailAndPassword(
                    emailid.editText?.text.toString().trim(),
                    pass.editText?.text.toString().trim()
                )
                    .addOnCompleteListener(it) { task ->
                        println("createAdmin" + emailid.editText?.text.toString().trim())
                        if (task.isSuccessful) {
                            Toast.makeText(context,"Task Successful", Toast.LENGTH_LONG).show()
                            val fragment: Fragment = Faculty_Admin()
                            val fragmentManager = requireActivity().supportFragmentManager
                            val fragmentTransaction = fragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.faculty_add,fragment)
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()
                        } else {
                            Toast.makeText(context,"Task UnSuccessful", Toast.LENGTH_LONG).show()
                        }
                    }
            }



        })
        return v;
    }




}