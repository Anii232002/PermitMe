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
import com.google.firebase.ktx.Firebase

class AdminLogin : Fragment() {

     lateinit var create : Button
     lateinit var login : Button
//    private lateinit var mAuth: FirebaseAuth
//    private lateinit var database: FirebaseDatabase
//    private lateinit var reference: DatabaseReference
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
        mAuth = Firebase.auth
//        username = view.findViewById(R.id.username_create_admin)
        create.setOnClickListener(View.OnClickListener { view->
            findNavController().navigate(R.id.action_adminLogin_to_createAdmin)
        })

        login.setOnClickListener(View.OnClickListener {
            activity?.let {
                mAuth.signInWithEmailAndPassword(emailid.editText?.text.toString().trim(), pass.editText?.text.toString().trim())
                    .addOnCompleteListener(it) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = mAuth.currentUser
                            findNavController().navigate(R.id.action_adminLogin_to_admin2)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(context, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()

                        }
                    }
            }

        })
        return view
    }


}