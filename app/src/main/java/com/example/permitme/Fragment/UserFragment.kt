package com.example.permitme.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.permitme.DataClass.PermissionDetails
import com.example.permitme.Home
import com.example.permitme.R
import com.example.permitme.adapters.PermissionsAdapter
import com.example.permitme.adapters.ViewPagerAdapter
import com.example.permitme.databinding.FragmentUserBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import io.grpc.NameResolver
import kotlin.properties.Delegates


class UserFragment : Fragment() {

private lateinit var binding:FragmentUserBinding
var amount by Delegates.notNull<Int>()
   var email by Delegates.notNull<String>()
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private var mListOfPermissions:MutableList<PermissionDetails> = mutableListOf<PermissionDetails>()
    var mPending :MutableList<PermissionDetails> = mutableListOf<PermissionDetails>()
    var mAccepted:MutableList<PermissionDetails> = mutableListOf<PermissionDetails>()
     var mRejected:MutableList<PermissionDetails> = mutableListOf<PermissionDetails>()

    private val args: UserFragmentArgs by navArgs<UserFragmentArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentUserBinding.inflate(inflater)
        // Inflate the layout for this fragment

        val topics= listOf("Accepted","Pending","Rejected")

         amount = args.myArg
        email = args.email.toString()
        val viewPager=binding.viewPager
        val tabLayout=binding.tabLayout

        val adapter= ViewPagerAdapter(childFragmentManager,lifecycle)
        viewPager.adapter=adapter

        TabLayoutMediator(tabLayout,viewPager){
                tab,position-> tab.text=topics[position]

        }.attach()
        mDatabase = FirebaseDatabase.getInstance()
        reference = mDatabase.getReference("tsec").child("permission")

//        reference.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (data in snapshot.children){
//                    if(amount==0)
//                    {
//                        if(data.child("studentemail").value.toString()==email)
//                        {
//                            val item=data.getValue(PermissionDetails::class.java)
//
//                            item?.let { mListOfPermissions.add(it) }
//                        }
//                    }
//                    else{
//                        if(data.child("facultyemail").value.toString()==email)
//                        {
//                            val item=data.getValue(PermissionDetails::class.java)
//
//                            item?.let { mListOfPermissions.add(it) }
//                        }
//                    }
//
//
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w("error", "loadPost:onCancelled", error.toException())
//            }
//
//        })
//        mDatabase.addValueEventListener(listener)
        for(i in mListOfPermissions)
        {
            if(i.status=="pending")
            {
                mPending.add(i)
            }
            else if(i.status=="accepted")
            {
                mAccepted.add(i)
            }
            else if(i.status=="rejected")
            {
                mRejected.add(i)
            }
        }



        return binding.root
    }
  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.signout, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.signout -> {

                FirebaseAuth.getInstance()
                    .signOut()
                val i = Intent(context, Home::class.java)
                startActivity(i)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}