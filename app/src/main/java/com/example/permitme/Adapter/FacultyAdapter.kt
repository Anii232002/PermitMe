package com.example.permitme.Adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.permitme.DataClass.FacultyDataClass
import com.example.permitme.DataClass.StudentDataClass
import com.example.permitme.R


class FacultyAdapter(context: Context?, resource: Int, objects: List<FacultyDataClass?>?) :
    ArrayAdapter<FacultyDataClass?>(context!!, resource, objects as MutableList<FacultyDataClass?>) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView =
                (context as Activity).layoutInflater.inflate(R.layout.admin_user_item, parent, false)
        }
        val photoImageView = convertView!!.findViewById<View>(R.id.user_image) as ImageView
        val usernameTextView = convertView.findViewById<View>(R.id.user_username) as TextView

        val user: FacultyDataClass? = getItem(position)
        if (user != null) {
            usernameTextView.setText(user.username)
        }
//        val isPhoto = message.getPhotoUrl() != null
//        if (isPhoto) {
//            messageTextView.visibility = View.GONE
//            photoImageView.visibility = View.VISIBLE
////            Glide.with(photoImageView.context)
////                .load(message.getPhotoUrl())
////                .into(photoImageView)
//        } else {
//            messageTextView.visibility = View.VISIBLE
//            photoImageView.visibility = View.GONE
//            messageTextView.setText(message.getText())
//        }
        if (user != null) {
            usernameTextView.setText(user.username)
        }
        return convertView
    }
}