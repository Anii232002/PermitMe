package com.example.permitme.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.permitme.DataClass.PermissionDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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
    private var imgPath:String =""
    private var docPath:String =""
    private lateinit var reference: DatabaseReference
    private lateinit var database: FirebaseDatabase


     fun uploadPermissions(senderemail:String,fid:String?,name:String,title:String,org:String,desc:String){
         val user = FirebaseAuth.getInstance().currentUser
         val uid = user?.uid

         database = FirebaseDatabase.getInstance();
         reference = database.getReference().child("tsec").child("faculty")

         // viewModel.uploadPermissions(name,org,assignee,path)
         val permission = PermissionDetails(
             senderemail,fid,name,title,org,"pending",imgPath,docPath,desc
         )
         //in place of facultyid place the id of faculty which is chosen in spinner

         reference = database.getReference("tsec").child("permission")


         if (uid != null) {
             reference.push().setValue(permission)

         }
    }

    fun uploadDocs(docName:String,doc: Uri){
        val pdfRef=storageRefs.child("PDFs/"+docName)

        val uploadTask=pdfRef.putFile(doc)



        uploadTask.addOnSuccessListener {
            Log.d("SuccessUpload","uploaded successfully")
           pdfRef.downloadUrl.addOnSuccessListener {
               docPath=it.toString()
           }


        }
            .addOnFailureListener{
                Log.d(TAG,"Upload failed,"+it)
            }

//            .addOnProgressListener {
//                progress.value= (100*it.bytesTransferred)/it.totalByteCount
//            }


        pdfRef.downloadUrl.addOnSuccessListener {
            Log.d("Dowmload",it.toString())
            docPath=it.toString()
        }
//
//        val task=uploadTask.continueWithTask{task->
//            if (!task.isSuccessful){
//                task.exception?.let {
//                    throw it
//                }
//            }
//            pdfRef.downloadUrl
//        }.addOnCompleteListener {
//            task->
//            if (task.isSuccessful){
//                path=task.result.toString()
//            }
//        }

    }

    fun uploadImage(imgName:String,imgUri:Uri){
        val imgRef=storageRefs.child("Images/"+imgName)

        val uploadTask=imgRef.putFile(imgUri)


        uploadTask.addOnSuccessListener {
            Log.d("Done","Image uploaded")
        }

        imgRef.downloadUrl.addOnSuccessListener {
            imgPath=it.toString()
        }


    }


}