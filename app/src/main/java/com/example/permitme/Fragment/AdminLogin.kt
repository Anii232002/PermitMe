package com.example.permitme.Fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.permitme.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class AdminLogin : Fragment() {

     lateinit var create : Button
     lateinit var login : Button

private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    lateinit var emailid : TextInputLayout
    lateinit var pass : TextInputLayout
    lateinit var institute : TextInputLayout
    lateinit var username : TextInputLayout
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_login, container, false)
        create = view.findViewById(R.id.create)
        login = view.findViewById(R.id.adminlogin)
        emailid = view.findViewById(R.id.emailadmin_login)
        pass = view.findViewById(R.id.passadmin_login)
        institute = view.findViewById(R.id.instituteadmin_login)
        mAuth = Firebase.auth
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("tsec").child("admin")
//        username = view.findViewById(R.id.username_create_admin)
        create.setOnClickListener(View.OnClickListener { view->
            findNavController().navigate(R.id.action_adminLogin_to_createAdmin)
        })

        login.setOnClickListener(View.OnClickListener {

           confirmLogin()
        })
        return view
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
                                        val user = mAuth.currentUser
                                        Toast.makeText(context,"You are being signed in!",Toast.LENGTH_LONG).show()
                                        findNavController().navigate(R.id.action_adminLogin_to_admin2)


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