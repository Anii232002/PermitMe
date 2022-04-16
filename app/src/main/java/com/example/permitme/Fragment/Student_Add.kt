package com.example.permitme.Fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.permitme.DataClass.StudentDataClass
import com.example.permitme.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.ktx.Firebase

class Student_Add : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var  query : Query
    lateinit var userid : TextInputLayout
    lateinit var pass : TextInputLayout
    lateinit var b: MaterialButton
    private lateinit var mAuth: FirebaseAuth
    lateinit var username : TextInputLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_student__add)

        mAuth = Firebase.auth
        userid = findViewById(R.id.email_student)
        pass = findViewById(R.id.pass_student)
        b = findViewById(R.id.create_student)
        username = findViewById(R.id.username_student)
        database = FirebaseDatabase.getInstance()
        b.setOnClickListener(View.OnClickListener { view->
            val student =  StudentDataClass(
                username.editText?.text.toString().trim(),
                userid.editText?.text.toString().trim(),
                pass.editText?.text.toString().trim(),
                "tsec",
                null,"student")
            val user = FirebaseAuth.getInstance().currentUser
            val uid = user?.uid
            reference = database.getReference("users")

            if (uid != null) {
                reference.push().setValue(student)
                reference = database.getReference("tsec").child("student")
                reference.push().setValue(student)
            }

            this?.let {
                mAuth.createUserWithEmailAndPassword(
                    userid.editText?.text.toString().trim(),
                    pass.editText?.text.toString().trim()
                )
                    .addOnCompleteListener(this) { task ->
                        println("createAdmin" + userid.editText?.text.toString().trim())
                        if (task.isSuccessful) {
                            Toast.makeText(this,"Task Successful",Toast.LENGTH_LONG).show()
                            val fragment: Fragment = Student()
                            val fragmentManager = this.supportFragmentManager
                            val fragmentTransaction = fragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.student_add,fragment)
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()
                        } else {
                            Toast.makeText(this,"Task UnSuccessful",Toast.LENGTH_LONG).show()
                        }
                    }
            }

            val fragment: Fragment = Student()
            val fragmentManager = this.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.student_add,fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        })


    }



}