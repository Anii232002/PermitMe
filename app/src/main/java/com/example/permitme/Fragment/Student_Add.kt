package com.example.permitme.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.permitme.DataClass.StudentDataClass
import com.example.permitme.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.ktx.Firebase

class Student_Add : Fragment() {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var  query : Query
    lateinit var emailid : TextInputLayout
    lateinit var pass : TextInputLayout
    lateinit var b: FloatingActionButton
    private lateinit var mAuth: FirebaseAuth
    lateinit var username : TextInputLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_student__add, container, false)
        mAuth = Firebase.auth
        emailid = v.findViewById(R.id.email_student)
        pass = v.findViewById(R.id.pass_student)
       b = v.findViewById(R.id.create_student)
        username = v.findViewById(R.id.username_student)
        database = FirebaseDatabase.getInstance()
        b.setOnClickListener(View.OnClickListener { view->
            val student =  StudentDataClass(
                username.editText?.text.toString().trim(),
                emailid.editText?.text.toString().trim(),
                pass.editText?.text.toString().trim(),
                "tsec",
                null,"student")
            val user = FirebaseAuth.getInstance().currentUser
            val uid = user?.uid
            reference = database.getReference("users")

            if (uid != null) {
                reference.child(uid).setValue(student)
            }

            if (uid != null) {
                reference.child("student").child(uid).setValue(student)
            }
            activity?.let {
                mAuth.createUserWithEmailAndPassword(
                    emailid.editText?.text.toString().trim(),
                    pass.editText?.text.toString().trim()
                )
                    .addOnCompleteListener(it) { task ->
                        println("createAdmin" + emailid.editText?.text.toString().trim())
                        if (task.isSuccessful) {
                            Toast.makeText(context,"Task Successful",Toast.LENGTH_LONG).show()
                            val fragment: Fragment = Student()
                            val fragmentManager = requireActivity().supportFragmentManager
                            val fragmentTransaction = fragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.student_add,fragment)
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()
                        } else {
                            Toast.makeText(context,"Task UnSuccessful",Toast.LENGTH_LONG).show()
                        }
                    }
            }

            val fragment: Fragment = Student()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.student_add,fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        })

        return v;
    }


}