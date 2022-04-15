package com.example.permitme.Fragment

import android.content.ContentValues
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


class UserLogin : Fragment() {
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
        val currentUser = mAuth.currentUser
        if(currentUser != null){
            findNavController().navigate(R.id.action_userLogin_to_userFragment)
//            startActivity(Intent(this@UserLogin.requireContext(),User::class.java))
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
//        username = view.findViewById(R.id.username_create_admin)


        login.setOnClickListener(View.OnClickListener {
            activity?.let {
                mAuth.signInWithEmailAndPassword(emailid.editText?.text.toString().trim(), pass.editText?.text.toString().trim())
                    .addOnCompleteListener(it) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(ContentValues.TAG, "signInWithEmail:success")
                            val user = mAuth.currentUser
                           findNavController().navigate(R.id.action_userLogin_to_userFragment)
                                //startActivity(Intent(this@UserLogin.requireContext(),User::class.java))
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(context, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()

                        }
                    }
            }

        })
        return view
    }

}