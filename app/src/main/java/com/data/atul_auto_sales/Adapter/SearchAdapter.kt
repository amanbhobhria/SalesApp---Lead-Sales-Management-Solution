package com.data.atul_auto_sales.Adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.data.atul_auto_sales.Activity.UpdateLeadActivity
import com.data.atul_auto_sales.Model.SearchBean
import com.data.atul_auto_sales.R
import com.data.atul_auto_sales.Utills.RvStatusClickListner


class SearchAdapter(var context: Activity, var list: List<SearchBean.Data.Lead>, var leadStatus:String?, var rvClickListner: RvStatusClickListner) : RecyclerView.Adapter<SearchAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_allleaddata, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setIsRecyclable(false)

   /*     holder.tvAdd.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(20f))
        holder.tvQtyAdd.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(100f))
        holder.tvQtyMinus.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(100f))
        holder.tvQty.background = RoundView(Color.TRANSPARENT, RoundView.getRadius(20f), true, R.color.orange)
        holder.tvOff.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(20f))
        holder.tvAdd.visibility = View.VISIBLE*/

        holder.tvID.text= "Lead ID : "+list[position].id.toString()
        holder.tvSource.text= "Source : "+list[position].source
        holder.tvPlumber.text= "Plumber : "+list[position].plumber
        holder.tvArchitect.text= "Architect : "+list[position].architect
    //    holder.tvClient.text= "Client : "+list[position].c
        holder.tvLeadDate.text= "Date : "+list[position].leadDate

        holder.ivUpdate.setOnClickListener {
          context.startActivity(Intent(context,UpdateLeadActivity::class.java)
               .putExtra("leadID",list[position].id.toString())
               .putExtra("leadStatus",leadStatus.toString()))
        }
       // holder.ivImage.setImageDrawable(context.resources.getDrawable(list[position].drawableId))

      /*  if ("Retailer"=="Retailer"){
      //      holder.itemView.visibility=View.GONE
        }*/


        holder.itemView.setOnClickListener {
            rvClickListner.clickPos(list[position].status,list[position].id)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tvID: TextView = itemview.findViewById(R.id.tvID)
       val tvSource: TextView = itemview.findViewById(R.id.tvSource)
       val tvPlumber: TextView = itemview.findViewById(R.id.tvPlumber)
       val tvArchitect: TextView = itemview.findViewById(R.id.tvArchitect)
       val tvClient: TextView = itemview.findViewById(R.id.tvClient)
       val tvLeadDate: TextView = itemview.findViewById(R.id.tvLeadDate)
       val ivUpdate: ImageView = itemview.findViewById(R.id.ivUpdate)
    }

}