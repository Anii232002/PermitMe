package com.example.permitme.ui.main

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.permitme.Fragment.UserFragment
import com.example.permitme.Fragment.UserFragmentDirections
import com.example.permitme.Fragment.UserLoginDirections
import com.example.permitme.R
import com.example.permitme.databinding.FragmentPendingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class PendingFragment : Fragment() {
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: FragmentPendingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentPendingBinding.inflate(inflater)
        database = FirebaseDatabase.getInstance();
//        val currentUser = mAuth.currentUser
//        if (currentUser != null) {
//            reference = database.getReference().child("users")
//
//        }
        if((parentFragment as UserFragment).amount==1)
        {
            binding.floatingActionButton.hide()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener {
            val action = UserFragmentDirections.actionUserFragmentToCreatePermission().setSenderemail((parentFragment as UserFragment).email)
            findNavController().navigate(action)
        }
    }


}