package com.example.permitme.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.permitme.DataClass.PermissionDetails
import com.example.permitme.R
import com.google.android.material.textview.MaterialTextView

class RejectedAdapter(val permissionsList: MutableList<PermissionDetails>) : RecyclerView.Adapter<RejectedAdapter.PermissionsAdapterViewHolder>() {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position:Int)


    }
    fun setOnItemClickListener(listener: onItemClickListener)
    {
        mListener = listener
    }


    class PermissionsAdapterViewHolder(itemView: View,listener: onItemClickListener):RecyclerView.ViewHolder(itemView){
        val nameTextView=itemView.findViewById<MaterialTextView>(R.id.user_name)
        val orgTextView=itemView.findViewById<MaterialTextView>(R.id.user_group)
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
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

        return PermissionsAdapterViewHolder(view,mListener)
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