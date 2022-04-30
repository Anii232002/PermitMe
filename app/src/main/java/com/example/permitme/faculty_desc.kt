package com.example.permitme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.permitme.adapters.AcceptedAdapter
import com.google.firebase.database.DatabaseReference


class faculty_desc : Fragment() {

    lateinit var database: DatabaseReference
    lateinit var accept : Button
    lateinit var reject : Button
    lateinit var notify : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_faculty_desc, container, false)

        accept = view.findViewById(com.example.permitme.R.id.tdaccept)
        reject = view.findViewById(com.example.permitme.R.id.tddeny)
        notify = view.findViewById(com.example.permitme.R.id.tdpending)

        accept.setOnClickListener(View.OnClickListener{

        })

        reject.setOnClickListener(View.OnClickListener{

        })

        notify.setOnClickListener(View.OnClickListener{

        })



        return view
    }
}