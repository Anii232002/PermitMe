package com.example.permitme.ui.permissions

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.permitme.DataClass.FacultyDataClass
import com.example.permitme.DataClass.PermissionDetails
import com.example.permitme.databinding.FragmentCreatePermissionBinding
import com.example.permitme.viewmodels.CreatePermissionViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

import java.io.File
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.permitme.R


class CreatePermission : Fragment() {

    private lateinit var binding: FragmentCreatePermissionBinding
    private lateinit var mAuth:FirebaseAuth
    private lateinit var currentUser:FirebaseUser
    private val viewModel:CreatePermissionViewModel by viewModels()
    private lateinit var docs:File
    private var uri:String=""
    private var displayName:String=""
    private var imageUri:String=""
    private val PICK_PDF_FILE=1
    private val PICK_IMAGE=2
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var fid: String
    private lateinit var senderemail: String
    private val args: CreatePermissionArgs by navArgs<CreatePermissionArgs>()
    val list = mutableListOf<String>()
    var hashMap : HashMap<String, String>
            = HashMap<String, String> ()






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth= FirebaseAuth.getInstance()
        currentUser= mAuth.currentUser!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreatePermissionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = Firebase.auth
        fid = "Sir"
        database = FirebaseDatabase.getInstance();
        senderemail = args.senderemail.toString()


        binding.lnr1.setOnClickListener {
            selectDocs()
        }

        binding.profilePhotoImage.setOnClickListener {
            selectImage()
        }
        reference = database.getReference().child("tsec").child("faculty")
             reference
            //here ou will get all faculty ...so for their username ..use user.username
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val user = snapshot.getValue(
                            FacultyDataClass::class.java
                        )

                        if (user != null) {
                            user.username?.let { it1 -> list.add(it1) }
                        }
                        if (user != null) {
                            user.username?.let { it1 -> hashMap.put(it1,
                                user.email as String
                            ) }
                        }
                        //user.username for username of faculty
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        val adapter = ArrayAdapter(requireContext(), com.example.permitme.R.layout.list_item, list)
        binding.actv.setAdapter(adapter)
        binding.actv.setOnItemClickListener { parent, view, position, id ->
            val item: String =
                parent.getItemAtPosition(position).toString()
            fid = item
        }

        binding.submitButton.setOnClickListener {

            if (binding.nameTextInputEdittext.text!!.isEmpty()){
                Snackbar.make(view,"Enter a name",Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.orgTextInputEdittext.text!!.isEmpty()){
                Snackbar.make(view,"Enter an organisation",Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//            if (binding.assigneeTextInputEdittext.text!!.isEmpty()){
//                Snackbar.make(view,"Enter an assignee",Snackbar.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }

            val name = binding.nameTextInputEdittext.text.toString()
            val org = binding.orgTextInputEdittext.text.toString()
            val desc=binding.descTextInputEdittext.text.toString()
            val title=binding.titleTextInputEdittext.text.toString()


            val user = FirebaseAuth.getInstance().currentUser





            viewModel.uploadPermissions(user!!.email.toString(),hashMap.get(fid),name,title,org,desc)
            viewModel.uploadImage(name+"",imageUri)
            viewModel.uploadDocs(displayName,uri)

            findNavController().navigate(R.id.action_createPermission_to_userFragment)


        }
    }

    private fun selectImage() {
        val getIntent = Intent(Intent.ACTION_GET_CONTENT)
        getIntent.type = "image/*"

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = "image/*"

        val chooserIntent = Intent.createChooser(getIntent, "Select Image")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

        startActivityForResult(chooserIntent, PICK_IMAGE)
    }


    private fun selectDocs() {
        val intent=Intent()
        intent.setType("application/pdf")
        intent.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECT"),PICK_PDF_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==PICK_PDF_FILE && resultCode==RESULT_OK && data!=null && data.data!=null){
             uri= data.data.toString()
            val uriString: String = uri.toString()
             docs= File(uriString)
            val path: String = docs.getAbsolutePath()
             displayName = ""

            if (uriString.startsWith("content://")) {
                var cursor: Cursor? = null
                try {
                    cursor = requireActivity().contentResolver.query(uri.toUri(), null, null, null, null)
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName =
                            cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                } finally {
                    cursor!!.close()
                }
            } else if (uriString.startsWith("file://")) {
                displayName = docs.getName()
            }
            binding.uploadTextView.text=displayName
        }

        else if(requestCode==PICK_IMAGE && resultCode== RESULT_OK && data!=null && data.data!=null){
            imageUri=data.data.toString()
            val uriString=imageUri.toString()
            docs= File(uriString)
            displayName = ""

//            if (uriString.startsWith("content://")) {
//                var cursor: Cursor? = null
//                try {
//                    cursor = requireActivity().contentResolver.query(uri, null, null, null, null)
//                    if (cursor != null && cursor.moveToFirst()) {
//                        displayName =
//                            cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
//                    }
//                } finally {
//                    cursor!!.close()
//                }
//            } else if (uriString.startsWith("file://")) {
//                displayName = docs.getName()
//            }


            Glide.with(this).load(imageUri).into(binding.profilePhotoImage)

        }
    }


}