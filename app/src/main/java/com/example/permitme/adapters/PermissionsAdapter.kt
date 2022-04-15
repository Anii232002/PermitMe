package com.example.permitme.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.permitme.DataClass.PermissionDetails
import com.example.permitme.R

class PermissionsAdapter(val permissionsList:List<PermissionDetails>) : RecyclerView.Adapter<PermissionsAdapter.PermissionsAdapterViewHolder>() {
    class PermissionsAdapterViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(){

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

    }

    override fun getItemCount(): Int {
        return permissionsList.size
    }
}