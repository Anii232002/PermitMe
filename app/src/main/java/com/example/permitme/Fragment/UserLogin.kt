package com.example.permitme.Fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.permitme.R
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class UserLogin : Fragment() {
    lateinit var login : Button
    //    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    lateinit var emailid : TextInputLayout
    lateinit var pass : TextInputLayout
    lateinit var switcher: SwitchMaterial
    lateinit var username : TextInputLayout
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        if(currentUser != null){
            findNavController().navigate(R.id.action_userLogin_to_userFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_user_login, container, false)

        login = view.findViewById(R.id.loginuser)
        emailid = view.findViewById(R.id.emailuser_login)
        pass = view.findViewById(R.id.passuser_login)

        mAuth = Firebase.auth
        switcher = view.findViewById(R.id.switcher)
        database = FirebaseDatabase.getInstance();
        switcher.isChecked = true
        reference = database.getReference().child("tsec").child("faculty")
        switcher.setOnCheckedChangeListener { buttonView, isChecked->
            // Responds to switch being checked/unchecked
            if(isChecked)
            {


                reference = database.getReference().child("tsec").child("faculty")
            }
            else
            {

                reference = database.getReference().child("tsec").child("student")
            }
        }
//        username = view.findViewById(R.id.username_create_admin)


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
                    if(data.child("email").value == emailid.editText?.text.toString() &&
                        data.child("password").value == pass.editText?.text.toString()  ) {
                        activity?.let {
                            mAuth.signInWithEmailAndPassword(emailid.editText?.text.toString().trim(), pass.editText?.text.toString().trim())
                                .addOnCompleteListener(it) { task ->
                                    if (task.isSuccessful) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(ContentValues.TAG, "signInWithEmail:success")
                                        val user = mAuth.currentUser
                                        Toast.makeText(context,"You are being signed in!",Toast.LENGTH_LONG).show()
                                        findNavController().navigate(R.id.action_userLogin_to_userFragment)


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                                        Toast.makeText(context, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show()

                                    }
                                }
                        }

                    } else {
                        //do something if not exists
                        Toast.makeText(context,"No Accounts found with this credentials",Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }


}