package com.example.permitme.Fragment

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.permitme.DataClass.UserDetailsDataStore
import com.example.permitme.R
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


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
    private lateinit var userDetailsDataStore:UserDetailsDataStore


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
        userDetailsDataStore= UserDetailsDataStore(requireContext())

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
//                                        if(data.child("check").value==1)
//                                        {
//                                            if (user != null) {
//                                                reference.child(user.uid).child("uid").setValue(user.uid)
//
//                                            }
//                                        }
                                        Toast.makeText(context,"You are being signed in!",Toast.LENGTH_LONG).show()
                                        //0-> student   1->faculty
                                        var id = 1
                                        if(data.child("position").value=="student")
                                        {
                                           id = 0

                                        }

                                        val sharedPref = requireActivity().getSharedPreferences("user_email_pref",
                                            Context.MODE_PRIVATE)
                                        val editor = sharedPref.edit()
                                        editor.putString("user_email", emailid.editText?.text.toString())
                                        editor.putInt("user_value",id)
                                        editor.apply()

//                                        lifecycleScope.launch {
//                                            userDetailsDataStore.writeEmail(emailid.editText?.text.toString(),requireContext())
//                                            userDetailsDataStore.writeAmount(id,requireContext())
//                                        }

                                        val action = UserLoginDirections.actionUserLoginToUserFragment().setMyArg(id).setEmail(emailid.editText?.text.toString().trim())
                                        findNavController().navigate(action)



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