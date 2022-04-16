package com.example.permitme.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.permitme.Adapter.StudentAdapter
import com.example.permitme.DataClass.StudentDataClass
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*


class Student : Fragment() {
    lateinit var b: FloatingActionButton
    lateinit var mFirebasedb: FirebaseDatabase
    lateinit var mFirebaseRef: DatabaseReference
    lateinit var mStudentAdapter: StudentAdapter
    lateinit var mMessageListView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(com.example.permitme.R.layout.fragment_student_admin, container, false)
        b = v.findViewById(com.example.permitme.R.id.student_add1)
        mMessageListView = v.findViewById(com.example.permitme.R.id.student_rv)
        mFirebasedb = FirebaseDatabase.getInstance();
        mFirebaseRef = mFirebasedb.getReference().child("tsec").child("student")
        b.setOnClickListener(View.OnClickListener {
            val i = Intent(activity, Student_Add::class.java)
            startActivity(i)

        })
        val students: List<StudentDataClass> = ArrayList()

        mStudentAdapter = StudentAdapter(context,com.example.permitme.R.layout.admin_user_item,students)
        mMessageListView.adapter = mStudentAdapter
        var mChildEventListener = object : ChildEventListener {
            override fun onChildAdded(
                snapshot: DataSnapshot,
                @Nullable previousChildName: String?
            ) {
                val friendlymessage: StudentDataClass? =
                    snapshot.getValue(StudentDataClass::class.java)
                mStudentAdapter.add(friendlymessage)
            }

            override fun onChildChanged(
                snapshot: DataSnapshot,
                @Nullable previousChildName: String?
            ) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(
                snapshot: DataSnapshot,
                @Nullable previousChildName: String?
            ) {
            }

            override fun onCancelled(error: DatabaseError) {}
        }
        mFirebaseRef.addChildEventListener(mChildEventListener)
        return v;
    }
}
//     override fun onPause() {
//        super.onPause()
//        mFirebaseAuth.removeAuthStateListener(mAuthStateListener)
//    }
//
//     override fun onResume() {
//        super.onResume()
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener)
//    }
