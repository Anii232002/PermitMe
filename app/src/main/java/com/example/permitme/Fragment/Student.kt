package com.example.permitme.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.permitme.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Student : Fragment() {
lateinit var b: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_student_admin, container, false)
        b = v.findViewById(R.id.student_add1)
        b.setOnClickListener(View.OnClickListener {
            val fragment: Fragment = Student_Add()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.student_admin,fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        })
        return v;
    }

}