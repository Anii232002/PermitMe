package com.example.permitme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.permitme.DataClass.PermissionDetails
import com.example.permitme.databinding.FragmentAcceptedBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class student_desc : Fragment() {

    private val _maccepted= ArrayList<PermissionDetails?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_student_desc, container, false)

        val name : TextView = view.findViewById(R.id.sdname)
        val desc : TextView = view.findViewById(R.id.sddesc)
        val org : TextView = view.findViewById(R.id.sdclass)

        val args = this.arguments
        name.text = args?.get("Name").toString()
        desc.text = args?.get("Desc").toString()
        org.text = args?.get("Class").toString()

        val p : TextView = view.findViewById(R.id.sdpending)
        val a : TextView = view.findViewById(R.id.sdaccepted)
        val d : TextView = view.findViewById(R.id.sddenied)
        if(args?.get("Name").toString() == "Pending"){
            a.visibility =  View.INVISIBLE
            d.visibility =  View.INVISIBLE
        }
        else if(args?.get("Name").toString() == "Accepted"){
            p.visibility =  View.INVISIBLE
            d.visibility =  View.INVISIBLE
        }
        else {
            p.visibility =  View.INVISIBLE
            a.visibility =  View.INVISIBLE
        }



        return view
    }
}
