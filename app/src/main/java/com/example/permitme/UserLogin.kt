package com.example.permitme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.permitme.databinding.FragmentUserLoginBinding


class UserLogin : Fragment() {

    private lateinit var binding:FragmentUserLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentUserLoginBinding.inflate(layoutInflater)
        binding.login.setOnClickListener {
            findNavController().navigate(R.id.action_userLogin_to_user2)
        }
        return binding.root
    }

}