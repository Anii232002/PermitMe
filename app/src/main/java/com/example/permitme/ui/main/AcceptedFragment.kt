package com.example.permitme.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.permitme.DataClass.PermissionDetails
import com.example.permitme.DataClass.UserDetailsDataStore
import com.example.permitme.DataClass.parceble
import com.example.permitme.Fragment.UserFragment
import com.example.permitme.Fragment.faculty_permission_desc
import com.example.permitme.Fragment.student_permission_dec
import com.example.permitme.R
import com.example.permitme.adapters.AcceptedAdapter
import com.example.permitme.adapters.PermissionsAdapter
import com.example.permitme.databinding.FragmentAcceptedBinding
import com.example.permitme.databinding.FragmentPendingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class AcceptedFragment : Fragment(), PermissionsAdapter.onItemClickListener {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: FragmentAcceptedBinding
    private lateinit var mDatabase: DatabaseReference
    private val _maccepted= ArrayList<PermissionDetails?>()
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
        binding= FragmentAcceptedBinding.inflate(inflater)
        userDetailsDataStore= UserDetailsDataStore(requireContext())
        mDatabase = FirebaseDatabase.getInstance().getReference("tsec").child("permission")

        binding.acceptedPermissionsRv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//                Log.d("SnapList",_mListOfPermissions.toString())
        val adapter=PermissionsAdapter(_maccepted,this@AcceptedFragment)
        binding.acceptedPermissionsRv.adapter=adapter
        _maccepted.clear()

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

                        if (item?.status.equals("accepted") && item?.studentemail.equals(email)) {

                            map.put(i.toString(), data.key.toString())
                            _maccepted.add(item)
                            adapter.notifyDataSetChanged()
                            i++;


                        }
                    } else {
                        if (item?.status.equals("accepted") && item?.facultyemail.equals(email)) {

                            val item = data.getValue(PermissionDetails::class.java)
                            map.put(i.toString(), data.key.toString())
                            _maccepted.add(item)
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
            val per = parceble(_maccepted[position]?.name.toString(),
                _maccepted[position]?.title.toString(),
                _maccepted[position]?.org.toString(),
                _maccepted[position]?.status.toString(),
                _maccepted[position]?.doc_url.toString(),
                _maccepted[position]?.description.toString(),
                id)
            intent.putExtra("name",_maccepted[position]?.name.toString())
            intent.putExtra("title",_maccepted[position]?.title.toString())
            intent.putExtra("desc",_maccepted[position]?.description.toString())
            intent.putExtra("status",_maccepted[position]?.status.toString())
            intent.putExtra("org",_maccepted[position]?.org.toString())
            intent.putExtra("id",id)
            startActivity(intent)

        }
        else{

                val intent = Intent(requireContext(), faculty_permission_desc::class.java)
                val per = parceble(_maccepted[position]?.name.toString(),
                    _maccepted[position]?.title.toString(),
                    _maccepted[position]?.org.toString(),
                    _maccepted[position]?.status.toString(),
                    _maccepted[position]?.doc_url.toString(),
                    _maccepted[position]?.description.toString(),
                    id)
            Toast.makeText(context, "Hello" + _maccepted[position]?.name.toString(), Toast.LENGTH_SHORT).show()
                intent.putExtra("name",_maccepted[position]?.name.toString())
                intent.putExtra("title",_maccepted[position]?.title.toString())
                intent.putExtra("desc",_maccepted[position]?.description.toString())
                intent.putExtra("status",_maccepted[position]?.status.toString())
                intent.putExtra("org",_maccepted[position]?.org.toString())
                intent.putExtra("id",id)
                startActivity(intent)

        }

        Log.d("realtimeId",id.toString())
    }


}