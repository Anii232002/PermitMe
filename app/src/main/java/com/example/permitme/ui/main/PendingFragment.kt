package com.example.permitme.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.permitme.DataClass.PermissionDetails
import com.example.permitme.Fragment.UserFragment
import com.example.permitme.Fragment.UserFragmentDirections
import com.example.permitme.adapters.PermissionsAdapter
import com.example.permitme.databinding.FragmentPendingBinding
import com.example.permitme.viewmodels.DisplayPermissionsViewmodel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class PendingFragment : Fragment() {
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: FragmentPendingBinding
    private lateinit var mDatabase: DatabaseReference
    private val _mListOfPermissions= ArrayList<PermissionDetails?>()



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

        mDatabase = FirebaseDatabase.getInstance().getReference("tsec/permission")

        val listener=object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children){
                    val item=data.getValue(PermissionDetails::class.java)

                    _mListOfPermissions.add(item)

                }

                binding.pendingPermissionsRv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                Log.d("SnapList",_mListOfPermissions.toString())
                val adapter=PermissionsAdapter(_mListOfPermissions)
                binding.pendingPermissionsRv.adapter=adapter


            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("error", "loadPost:onCancelled", error.toException())
            }

        }

        mDatabase.addValueEventListener(listener)



//        viewModel.mListOfPermssions.observe(viewLifecycleOwner, Observer {
//
//        })





        binding.floatingActionButton.setOnClickListener {
            val action = UserFragmentDirections.actionUserFragmentToCreatePermission().setSenderemail((parentFragment as UserFragment).email)
            findNavController().navigate(action)
        }
    }




}