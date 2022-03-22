package com.example.permitme.Fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.permitme.DataClass.AdminDataClass
import com.example.permitme.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class CreateAdmin : Fragment() {
    lateinit var create:Button
    lateinit var emailid : TextInputLayout
    lateinit var pass : TextInputLayout
    lateinit var institute : TextInputLayout
    lateinit var username : TextInputLayout
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private val TAG = "CreateAdmin"
    var progressDialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_create_admin, container, false)
        mAuth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        create = v.findViewById(R.id.create_create_admin)
        emailid = v.findViewById(R.id.id_create_admin)
        pass = v.findViewById(R.id.pass_create_admin)
        institute = v.findViewById(R.id.institute_create_admin)
        username = v.findViewById(R.id.username_create_admin)
        progressDialog = ProgressDialog(context)
        progressDialog!!.setTitle("Create Account")
        progressDialog!!.setMessage("please wait crating your account...")

        create.setOnClickListener(View.OnClickListener { view->

            activity?.let {
                mAuth.createUserWithEmailAndPassword(emailid.editText?.text.toString().trim(), pass.editText?.text.toString().trim())
                    .addOnCompleteListener(it) { task ->
                        println("createAdmin"+ emailid.editText?.text.toString().trim())
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            Log.d(TAG, emailid.editText?.text.toString().trim())


                            progressDialog!!.show()
                            val admin =  AdminDataClass(
                                username.editText?.text.toString().trim(),
                                emailid.editText?.text.toString().trim(),
                                pass.editText?.text.toString().trim(),
                                institute.editText?.text.toString().trim(),
                                null,"admin")

                            val user = FirebaseAuth.getInstance().currentUser
                            val uid = user?.uid
                            reference = database.getReference("users")
                            if (uid != null) {
                                reference.child(uid).setValue(admin)
                            }
                            reference = database.getReference(institute.editText?.text.toString().trim().toLowerCase())
                            if (uid != null) {
                                reference.child("admin").child(uid).setValue(admin)
                            }


                            progressDialog!!.dismiss()
                            findNavController().navigate(R.id.action_createAdmin_to_admin2)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(context, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()

                        }
                    }
            }

        })
        return v
    }




}