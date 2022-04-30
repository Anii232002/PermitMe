package com.example.permitme.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.permitme.DataClass.PermissionDetails
import com.example.permitme.DataClass.UserDetailsDataStore
import com.example.permitme.DataClass.parceble
import com.example.permitme.Fragment.UserFragment
import com.example.permitme.Fragment.faculty_permission_desc
import com.example.permitme.Fragment.student_permission_dec
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
    private lateinit var userDetailsDataStore: UserDetailsDataStore
    private var email="empty"
    val map:HashMap<String,String> = HashMap()
    var amount =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentRejectedBinding.inflate(inflater)
        userDetailsDataStore= UserDetailsDataStore(requireContext())
        mDatabase = FirebaseDatabase.getInstance().getReference("tsec/permission")

        binding.rejectedPermissionsRv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//                Log.d("SnapList",_mListOfPermissions.toString())
        val adapter=PermissionsAdapter(_mrejected,this@RejectedFragment)
        binding.rejectedPermissionsRv.adapter=adapter
        _mrejected.clear()

        val sharedPref = requireActivity().getSharedPreferences("user_email_pref", Context.MODE_PRIVATE)
        email=sharedPref.getString("user_email","")!!
        amount=sharedPref.getInt("user_value",-1)

        Log.d("AmountEmail", "$amount $email")

        mDatabase.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                var i = 0
                for (data in snapshot.children) {
                    val item = data.getValue(PermissionDetails::class.java)

                    //Log.d("itemInsideDataChange",item.toString())

                    Log.d("Email", email + " " + item?.studentemail.equals(email).toString())

                    if (amount == 0) {

                        if (item?.status.equals("pending") && item?.studentemail.equals(email)) {

                            map.put(i.toString(), data.key.toString())
                            _mrejected.add(item)
                            adapter.notifyDataSetChanged()
                            i++;


                        }
                    } else {
                        if (item?.status.equals("pending") && item?.facultyemail.equals(email)) {

                            val item = data.getValue(PermissionDetails::class.java)
                            map.put(i.toString(), data.key.toString())
                            _mrejected.add(item)
                            adapter.notifyDataSetChanged()
                            i++
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("error", "loadPost:onCancelled", error.toException())
            }

        })

        return binding.root

    }

    override fun onItemClick(position: Int) {
        val id= map[position.toString()]
        if(amount == 0) {

            val intent = Intent(requireActivity(), student_permission_dec::class.java)
            val per = parceble(_mrejected[position]?.name.toString(),
                _mrejected[position]?.title.toString(),
                _mrejected[position]?.org.toString(),
                _mrejected[position]?.status.toString(),
                _mrejected[position]?.doc_url.toString(),
                _mrejected[position]?.description.toString(),
                id)
            intent.putExtra("name",_mrejected[position]?.name.toString())
            intent.putExtra("title",_mrejected[position]?.title.toString())
            intent.putExtra("desc",_mrejected[position]?.description.toString())
            intent.putExtra("status",_mrejected[position]?.status.toString())
            intent.putExtra("org",_mrejected[position]?.org.toString())
            intent.putExtra("id",id)
            startActivity(intent)

        }
        else{

                val intent = Intent(requireContext(), faculty_permission_desc::class.java)
                val per = parceble(_mrejected[position]?.name.toString(),
                    _mrejected[position]?.title.toString(),
                    _mrejected[position]?.org.toString(),
                    _mrejected[position]?.status.toString(),
                    _mrejected[position]?.doc_url.toString(),
                    _mrejected[position]?.description.toString(),
                    id)

                intent.putExtra("name",_mrejected[position]?.name.toString())
                intent.putExtra("title",_mrejected[position]?.title.toString())
                intent.putExtra("desc",_mrejected[position]?.description.toString())
                intent.putExtra("status",_mrejected[position]?.status.toString())
                intent.putExtra("org",_mrejected[position]?.org.toString())
                intent.putExtra("id",id)
                startActivity(intent)

        }

        Log.d("realtimeId",id.toString())
    }
}