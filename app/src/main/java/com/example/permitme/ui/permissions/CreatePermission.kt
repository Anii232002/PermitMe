package com.example.permitme.ui.permissions

import android.R.attr
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.permitme.R
import com.example.permitme.databinding.FragmentCreatePermissionBinding
import com.example.permitme.viewmodels.CreatePermissionViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.provider.OpenableColumns

import android.R.attr.data
import android.database.Cursor
import android.net.Uri
import java.io.File


class CreatePermission : Fragment() {

    private lateinit var binding: FragmentCreatePermissionBinding
    private lateinit var mAuth:FirebaseAuth
    private lateinit var currentUser:FirebaseUser
    private val viewModel:CreatePermissionViewModel by viewModels()
    private lateinit var docs:File
    private lateinit var uri:Uri
    private lateinit var displayName:String
    private val PICK_PDF_FILE=1

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

        binding.lnr1.setOnClickListener {
            selectDocs()
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
            if (binding.assigneeTextInputEdittext.text!!.isEmpty()){
                Snackbar.make(view,"Enter an assignee",Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val name = binding.nameTextInputEdittext.text.toString()
            val org = binding.orgTextInputEdittext.text.toString()
            val assignee = binding.assigneeTextInputEdittext.text.toString()

            val path=viewModel.uploadDocs(displayName,uri)

            viewModel.uploadPermissions(name,org,assignee,path)
        }
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
             uri= data.data!!
            val uriString: String = uri.toString()
             docs= File(uriString)
            val path: String = docs.getAbsolutePath()
             displayName = ""

            if (uriString.startsWith("content://")) {
                var cursor: Cursor? = null
                try {
                    cursor = requireActivity().contentResolver.query(uri, null, null, null, null)
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
    }


}