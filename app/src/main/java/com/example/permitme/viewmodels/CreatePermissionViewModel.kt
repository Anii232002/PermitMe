package com.example.permitme.viewmodels

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
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
import android.R.id




class CreatePermissionViewModel : ViewModel() {



    private val TAG: String?="Document added"
    val db=Firebase.firestore
    val storage= Firebase.storage
    val storageRefs=storage.reference
    private var imgPath:String =""
    private var docPath:String =""
    private lateinit var reference: DatabaseReference
    private lateinit var database: FirebaseDatabase

    var id=""


     fun uploadPermissions(senderemail:String,fid:String?,name:String,title:String,org:String,desc:String){
         val user = FirebaseAuth.getInstance().currentUser
         val uid = user?.uid

         database = FirebaseDatabase.getInstance();
        // reference = database.getReference().child("tsec").child("faculty")

         // viewModel.uploadPermissions(name,org,assignee,path)

         Log.d("imgPathBeforeUpload",imgPath)

         val permission = PermissionDetails(
             senderemail,fid,name,title,org,"pending",imgPath,docPath,desc
         )
         //in place of facultyid place the id of faculty which is chosen in spinner

         reference = database.getReference("tsec").child("permission")


         if (uid != null) {
             val key=reference.push()
             key.setValue(permission)
             id=key.key.toString()


         }
    }

    fun uploadDocs(docName:String,doc: String){
        val pdfRef=storageRefs.child("PDFs/"+docName)

        val uri= doc.toUri()

        val uploadTask=pdfRef.putFile(uri)



        uploadTask.addOnSuccessListener {
            Log.d("SuccessUpload","uploaded successfully")
           pdfRef.downloadUrl.addOnSuccessListener {
               docPath=it.toString()

               val values: HashMap<String, Any> = HashMap()
               values["doc_url"] =docPath

               reference.child(id).updateChildren(values)
           }


        }
            .addOnFailureListener{
                Log.d(TAG,"Upload failed,"+it)
            }

//

    }

    fun uploadImage(imgName:String,imgUri:String){
        val imgRef=storageRefs.child("Images/"+imgName)
        val uri= imgUri.toUri()
        val uploadTask=imgRef.putFile(uri)


        uploadTask.addOnSuccessListener {
            Log.d("Done","Image uploaded")

            imgRef.downloadUrl.addOnSuccessListener {
                imgPath=it.toString()
                Log.d("icon",imgPath)

                val values: HashMap<String, Any> = HashMap()
                values["icon"] = imgPath

                reference.child(id).updateChildren(values)
            }


         }

//        val task=uploadTask.continueWithTask {
//            if (!it.isSuccessful){
//                it.exception?.let {
//                    throw it
//                }
//            }
//
//            imgRef.downloadUrl
//
//        }.addOnCompleteListener {
//            if (it.isSuccessful){
//                imgPath=it.result.toString()
//            }
//        }









    }


}