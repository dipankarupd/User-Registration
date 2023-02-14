package com.example.firebase

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_add.view.*

class ViewAdapter(val item : ArrayList<Students>,val context : Context) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {


        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        val holder= ItemViewHolder(view)

        return holder

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.nameTv.text = item[position].name.toString()
        holder.emailTv.text = item[position].email.toString()
        holder.phoneTv.text = item[position].phNum.toString()

        holder.layout.setOnClickListener {
            val intent = Intent(context,Update :: class.java)
            intent.putExtra("userID",item[position].userId)
            intent.putExtra("username", item[position].name)
            intent.putExtra("useremail", item[position].email)
            intent.putExtra("usernumber", item[position].phNum)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }

}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var nameTv : TextView = itemView.findViewById(R.id.textName)
    var emailTv : TextView = itemView.findViewById(R.id.textEmail)
    var phoneTv : TextView = itemView.findViewById(R.id.textNumber)
    var layout : ConstraintLayout = itemView.findViewById(R.id.viewlayout)
}

