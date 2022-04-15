package com.example.permitme.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.io.FileInputStream

class CreatePermissionViewModel : ViewModel() {

    private val TAG: String?="Document added"
    val db=Firebase.firestore
    val storage= Firebase.storage
    val storageRefs=storage.reference


     fun uploadPermissions(name:String,org:String,assignee:String,path:String){
        val permission= hashMapOf("name" to name,"org" to org,"assignee" to assignee,"path" to path)

        db.collection("Pending Permissions")
            .add(permission)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot added with ID: ${it.id}")
            }
            .addOnFailureListener {
                Log.w(TAG, "Error adding document", it)
            }
    }

    fun uploadDocs(docName:String,doc: Uri):String{
        val pdfRef=storageRefs.child("PDFs/"+docName)



        val uploadTask=pdfRef.putFile(doc)

        var path=""

        uploadTask.addOnSuccessListener {
            path= it.metadata!!.path
        }
            .addOnFailureListener{
                Log.d(TAG,"Upload failed,"+it)
            }

        return path
    }


}