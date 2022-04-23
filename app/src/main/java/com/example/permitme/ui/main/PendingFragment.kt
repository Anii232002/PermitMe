package com.example.permitme.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.permitme.DataClass.PermissionDetails
import com.example.permitme.Fragment.UserFragment
import com.example.permitme.Fragment.UserFragmentDirections
import com.example.permitme.adapters.PermissionsAdapter
import com.example.permitme.databinding.FragmentPendingBinding
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
        mDatabase = FirebaseDatabase.getInstance().getReference("tsec").child("permission")
        binding=FragmentPendingBinding.inflate(inflater)
        val listener=object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children){
                    val item=data.getValue(PermissionDetails::class.java)
                    if((parentFragment as UserFragment).amount==0)
                    {
                        if(item?.status.equals("pending")&& item?.studentemail.equals((parentFragment as UserFragment).email))
                        {


                            item?.let { _mListOfPermissions.add(it) }
                        }
                    }
                    else{
                        if(item?.status.equals("pending")&& item?.facultyemail.equals((parentFragment as UserFragment).email))
                        {
                            val item=data.getValue(PermissionDetails::class.java)

                            item?.let { _mListOfPermissions.add(it) }
                        }
                    }


                }

                binding.pendingPermissionsRv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//                Log.d("SnapList",_mListOfPermissions.toString())
                val adapter=PermissionsAdapter(_mListOfPermissions)
                binding.pendingPermissionsRv.adapter=adapter
                adapter.setOnItemClickListener(object : PermissionsAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        if((parentFragment as UserFragment).amount==0)
                        {

                        }
                        else
                        {

                        }

                    }

                })

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("error", "loadPost:onCancelled", error.toException())
            }

        }

        mDatabase.addValueEventListener(listener)


        // Inflate the layout for this fragment

//        database = FirebaseDatabase.getInstance();
//        binding.pendingPermissionsRv.layoutManager=
//            LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
////        Log.d("SnapList",_mListOfPermissions.toString())
//        val adapter= PermissionsAdapter((parentFragment as UserFragment).mPending
//        )
//        binding.pendingPermissionsRv.adapter=adapter

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
    private fun onListItemClick(position: Int) {
//        Toast.makeText(this, mRepos[position].name, Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





//        viewModel.mListOfPermssions.observe(viewLifecycleOwner, Observer {
//
//        })





        binding.floatingActionButton.setOnClickListener {
            val action = UserFragmentDirections.actionUserFragmentToCreatePermission().setSenderemail((parentFragment as UserFragment).email)
            findNavController().navigate(action)
        }
    }





}