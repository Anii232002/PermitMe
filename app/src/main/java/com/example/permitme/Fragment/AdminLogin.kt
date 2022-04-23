package com.example.permitme.Fragment

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.permitme.DataClass.FacultyDataClass
import com.example.permitme.DataClass.InstituteDataClass
import com.example.permitme.R
import com.example.permitme.databinding.FragmentAdminLoginBinding
import com.example.permitme.databinding.FragmentCreatePermissionBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase

class AdminLogin : Fragment() {

     lateinit var create : Button
     lateinit var login : Button

private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    lateinit var emailid : TextInputLayout
    lateinit var pass : TextInputLayout
    private lateinit var binding: FragmentAdminLoginBinding
    lateinit var username : TextInputLayout
    private lateinit var mAuth: FirebaseAuth
    val list1 = mutableListOf<String>()
    private lateinit var fid: String
    private val TAG:String= "AdminLogin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
        reference = database.getReference().child("institute")
        reference
            //here ou will get all faculty ...so for their username ..use user.username
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val user = snapshot.getValue(InstituteDataClass::class.java)

                        if (user != null) {
                            System.out.println("bye"+user.name)
                            list1.add(user.name.toString())
                        }
                        /*  if (user != null) {
                              user.name?.let { it1 -> list1.add(it1) }
                          }*/
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminLoginBinding.inflate(inflater)
        return binding.root
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Inflate the layout for this fragment
        super.onViewCreated(view, savedInstanceState)

        create = view.findViewById(R.id.create)
        fid = "tsec"
        login = view.findViewById(R.id.adminlogin)
        emailid = view.findViewById(R.id.emailadmin_login)
        pass = view.findViewById(R.id.passadmin_login)

        mAuth = Firebase.auth

//        username = view.findViewById(R.id.username_create_admin)
        create.setOnClickListener { view ->

            findNavController().navigate(R.id.action_adminLogin_to_createAdmin)
        }
        System.out.println("HI"+list1)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, list1)
        binding.actv1.setAdapter(adapter)
        binding.actv1.setOnItemClickListener { parent, view, position, id ->
            val item: String =
                parent.getItemAtPosition(position).toString()
            fid = item
        }
        login.setOnClickListener(View.OnClickListener {
          reference = database.getReference(fid).child("admin")
           confirmLogin()
        })

    }
    private fun confirmLogin() {
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (data in dataSnapshot.children) {
//                    Log.d("Hello",data.child(emailid.editText?.text.toString().trim()).toString())
                     if(data.child("email").value == emailid.editText?.text.toString().trim()) {
                        activity?.let {
                            mAuth.signInWithEmailAndPassword(emailid.editText?.text.toString().trim(), pass.editText?.text.toString().trim())
                                .addOnCompleteListener(it) { task ->
                                    if (task.isSuccessful) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success")

                                        Toast.makeText(context,"You are being signed in!",Toast.LENGTH_LONG).show()
                                        val action = AdminLoginDirections.actionAdminLoginToAdmin2().setInstitute(fid)
                                        findNavController().navigate(action)


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                                        Toast.makeText(context, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show()

                                    }
                                }
                        }

                    } else {
                        //do something if not exists
//                        Toast.makeText(context,"No Accounts found with this credentials",Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }


}