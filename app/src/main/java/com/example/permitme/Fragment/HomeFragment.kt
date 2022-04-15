package com.example.permitme.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import androidx.navigation.fragment.findNavController
import com.example.permitme.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {
   lateinit var user : Button
    lateinit var admin : Button
   lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth=Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val v =inflater.inflate(R.layout.fragment_home, container, false)
        user = v.findViewById(R.id.user_home)
        admin = v.findViewById(R.id.admin_home)

        val currentUser = mAuth.currentUser


        user.setOnClickListener(View.OnClickListener { view->

            if(currentUser != null){
                findNavController().navigate(R.id.action_homeFragment_to_userFragment)

            }
            else {
                findNavController().navigate(R.id.action_homeFragment_to_userLogin)
            }

        })
        admin.setOnClickListener(View.OnClickListener { view->
            findNavController().navigate(R.id.action_homeFragment_to_adminLogin)
        })
        return v;
    }


}