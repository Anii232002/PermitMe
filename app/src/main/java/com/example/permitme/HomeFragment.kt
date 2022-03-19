package com.example.permitme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment

import androidx.navigation.fragment.findNavController


class HomeFragment : Fragment() {
   lateinit var user : Button
    lateinit var admin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val v =inflater.inflate(R.layout.fragment_home, container, false)
        user = v.findViewById(R.id.user_home)
        admin = v.findViewById(R.id.admin_home)
        user.setOnClickListener(View.OnClickListener { view->


            findNavController().navigate(R.id.action_homeFragment_to_userLogin)

        })
        admin.setOnClickListener(View.OnClickListener { view->
            findNavController().navigate(R.id.action_homeFragment_to_adminLogin)
        })
        return v;
    }


}