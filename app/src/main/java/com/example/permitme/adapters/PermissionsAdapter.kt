package com.example.permitme.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.permitme.DataClass.PermissionDetails
import com.example.permitme.R
import com.google.android.material.textview.MaterialTextView

class PermissionsAdapter(val permissionsList: ArrayList<PermissionDetails?>) : RecyclerView.Adapter<PermissionsAdapter.PermissionsAdapterViewHolder>() {

    class PermissionsAdapterViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val nameTextView=itemView.findViewById<MaterialTextView>(R.id.user_name)
        val orgTextView=itemView.findViewById<MaterialTextView>(R.id.user_group)

        fun bind(item:PermissionDetails?){
           nameTextView.text=item!!.name
            orgTextView.text=item.org


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PermissionsAdapterViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.items_list,parent,false)

        return PermissionsAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: PermissionsAdapterViewHolder, position: Int) {
        val item=permissionsList[position]

        Log.d("single",item.toString())

        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return permissionsList.size
    }
}