package com.example.permitme.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.permitme.DataClass.PermissionDetails
import com.example.permitme.R
import com.google.android.material.textview.MaterialTextView

class PermissionsAdapter(val permissionsList: ArrayList<PermissionDetails?>,val listener: onItemClickListener) : RecyclerView.Adapter<PermissionsAdapter.PermissionsAdapterViewHolder>() {


interface onItemClickListener{
    fun onItemClick(position:Int)


}


    class PermissionsAdapterViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val nameTextView=itemView.findViewById<MaterialTextView>(R.id.user_name)
        val orgTextView=itemView.findViewById<MaterialTextView>(R.id.user_group)
        val iconImageView=itemView.findViewById<ImageView>(R.id.user_image_view)
        val permissionIcon=itemView.findViewById<ImageView>(R.id.permission_status)

        fun bind(item:PermissionDetails?){
           nameTextView.text=item!!.name
            orgTextView.text=item.org
            if (!item.icon.isNullOrEmpty())
            Glide.with(itemView.context).load(item.icon).into(iconImageView)

            if (item.status.equals("accepted")){
                permissionIcon.setImageResource(R.drawable.accepted)
            }
            else if(item.status.equals("rejected")){
                permissionIcon.setImageResource(R.drawable.rejected)
            }


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

        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }



        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return permissionsList.size
    }
}