package com.example.permitme.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.permitme.DataClass.PermissionDetails
import com.example.permitme.Fragment.UserFragment
import com.example.permitme.R
import com.example.permitme.adapters.PermissionsAdapter
import com.example.permitme.adapters.RejectedAdapter
import com.example.permitme.databinding.FragmentAcceptedBinding
import com.example.permitme.databinding.FragmentPendingBinding
import com.example.permitme.databinding.FragmentRejectedBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class RejectedFragment : Fragment(), PermissionsAdapter.onItemClickListener {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: FragmentRejectedBinding
    private lateinit var mDatabase: DatabaseReference
    private val _mrejected= ArrayList<PermissionDetails?>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentRejectedBinding.inflate(inflater)
        mDatabase = FirebaseDatabase.getInstance().getReference("tsec/permission")

        binding.rejectedPermissionsRv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//                Log.d("SnapList",_mListOfPermissions.toString())
        val adapter=PermissionsAdapter(_mrejected,this@RejectedFragment)
        binding.rejectedPermissionsRv.adapter=adapter

        val listener=object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children){

                    val item=data.getValue(PermissionDetails::class.java)
                    if((parentFragment as UserFragment).amount==0)
                    {
                        if(item?.status.equals("rejected")&& item?.studentemail.equals((parentFragment as UserFragment).email))
                        {


                            item?.let { _mrejected.add(it) }
                        }
                    }
                    else{
                        if(item?.status.equals("rejected")&& item?.facultyemail.equals((parentFragment as UserFragment).email))
                        {
                            val item=data.getValue(PermissionDetails::class.java)

                            item?.let { _mrejected.add(it) }
                        }
                    }


                }



            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("error", "loadPost:onCancelled", error.toException())
            }

        }

        mDatabase.addValueEventListener(listener)
        return binding.root

    }

    override fun onItemClick(position: Int) {

    }


}