package com.example.permitme.Fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.permitme.DataClass.parceble
import com.example.permitme.R
import com.example.permitme.databinding.ActivityStudentPermissionDecBinding
import com.example.permitme.ui.main.PendingFragment

class student_permission_dec : AppCompatActivity() {

    private lateinit var binding: ActivityStudentPermissionDecBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentPermissionDecBinding.inflate(layoutInflater)
        setContentView(binding.root)



//        var permission = intent.getStringExtra("permission")






        binding.sdname.text = intent.getStringExtra("name")
        binding.sddesc.text = intent.getStringExtra("desc")
        binding.sdclass.text = intent.getStringExtra("org")
        binding.sdtitle.text = intent.getStringExtra("title")

        var a : TextView = findViewById(R.id.sdaccepted)
        var p : TextView = findViewById(R.id.sdpending)
        var d : TextView = findViewById(R.id.sddenied)

        if(intent.getStringExtra("status") == "accept"){
            p.visibility = View.INVISIBLE
            d.visibility = View.INVISIBLE
        }
        else if(intent.getStringExtra("status") == "pending"){
            a.visibility = View.INVISIBLE
            d.visibility = View.INVISIBLE
        }
        else{
            a.visibility = View.INVISIBLE
            p.visibility = View.INVISIBLE
        }

    }
}