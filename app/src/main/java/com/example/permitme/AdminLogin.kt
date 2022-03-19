package com.example.permitme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class AdminLogin : Fragment() {

     lateinit var create : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_login, container, false)
        create = view.findViewById(R.id.create)
        create.setOnClickListener(View.OnClickListener { view->
            findNavController().navigate(R.id.action_adminLogin_to_createAdmin)
        })
        return view
    }


}