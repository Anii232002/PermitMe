package com.example.permitme.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.permitme.DataClass.PermissionDetails
import com.example.permitme.DataClass.UserDetailsDataStore
import com.example.permitme.Fragment.UserFragment
import com.example.permitme.Fragment.UserFragmentDirections
import com.example.permitme.adapters.PermissionsAdapter
import com.example.permitme.databinding.FragmentPendingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import android.R

import android.content.SharedPreferences

import android.R.string.no
import android.content.Context


class PendingFragment : Fragment(), PermissionsAdapter.onItemClickListener {
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: FragmentPendingBinding
    private lateinit var mDatabase: DatabaseReference
    private val _mListOfPermissions= ArrayList<PermissionDetails?>()
    private lateinit var userDetailsDataStore: UserDetailsDataStore
    private var email="empty"
    val map:HashMap<String,String> = HashMap()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userDetailsDataStore= UserDetailsDataStore(requireContext())
        mDatabase = FirebaseDatabase.getInstance().getReference("tsec").child("permission")
        binding=FragmentPendingBinding.inflate(inflater)
        binding.pendingPermissionsRv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//                Log.d("SnapList",_mListOfPermissions.toString())
        val adapter= PermissionsAdapter(_mListOfPermissions,this)
        binding.pendingPermissionsRv.adapter=adapter
        _mListOfPermissions.clear()

        var amount=0


//        userDetailsDataStore.preferencesAmountValue.asLiveData().observe(viewLifecycleOwner){
//            amount=it
//        }
//        userDetailsDataStore.preferenceEmailFlow.asLiveData().observe(viewLifecycleOwner){
//            email=it
//        }

        val sharedPref = requireActivity().getSharedPreferences("user_email_pref",Context.MODE_PRIVATE)
        email=sharedPref.getString("user_email","")!!
        amount=sharedPref.getInt("user_value",-1)

        Log.d("AmountEmail", "$amount $email")

        mDatabase.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                    var i=0
                for (data in snapshot.children){
                    val item=data.getValue(PermissionDetails::class.java)

                    //Log.d("itemInsideDataChange",item.toString())

                    Log.d("Email",email+" "+item?.studentemail.equals(email).toString())

                    if(amount==0)
                    {

                        if(item?.status.equals("pending") && item?.studentemail.equals(email))
                        {

                            map.put(i.toString(),data.key.toString())
                           _mListOfPermissions.add(item)
                            adapter.notifyDataSetChanged()
                            i++;

                        }
                    }
                    else{
                        if(item?.status.equals("pending")&& item?.facultyemail.equals(email))
                        {

                           val item=data.getValue(PermissionDetails::class.java)
                            map.put(i.toString(),data.key.toString())
                             _mListOfPermissions.add(item)
                            adapter.notifyDataSetChanged()
                            i++                        }
                    }


                }


            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("error", "loadPost:onCancelled", error.toException())
            }

        })




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
        if(amount==1)
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

    override fun onItemClick(position: Int) {
        val id=map.get(position.toString())

        Log.d("realtimeId",id.toString())
    }


}