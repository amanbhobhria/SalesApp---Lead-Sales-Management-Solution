package com.data.atul_auto_sales.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.data.atul_auto_sales.Model.LeadDetailBean
import com.data.atul_auto_sales.R
import com.data.atul_auto_sales.Utills.GeneralUtilities
import com.data.atul_auto_sales.Utills.RvStatusClickListner
import com.stpl.antimatter.Utils.ApiContants
import java.io.OutputStream


class DocumentListAdapter(var context: Activity, var list: List<LeadDetailBean.Data.CustomProduct>, var rvClickListner: RvStatusClickListner) : RecyclerView.Adapter<DocumentListAdapter.MyViewHolder>(){
    val out: OutputStream?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_productcustlist, parent, false)
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

        holder.tvProductName.text= list[position].productName.toString()
        holder.tvQty.text= list[position].qty.toString()
        holder.tvProductPrice.text= list[position].price.toString()
        holder.tvDate.text=list[position].createdAt.toString()
        if (list[position].drawingDocuments.isNullOrEmpty()){
            holder.tvDownload.visibility=View.GONE
        }else{
            holder.tvDownload.visibility=View.VISIBLE
        }
holder.tvDownload.setOnClickListener {
   GeneralUtilities.downloadUrl(context,ApiContants.downloadUrl+list[position].drawingDocuments)
   // GeneralUtilities.down(context,ApiContants.downloadUrl+list[position].drawingDocuments)
//https://atulautomotive.online/uploaded_documents/3865820653991691-lead-ex-2.png
}
       // holder.ivImage.setImageDrawable(context.resources.getDrawable(list[position].drawableId))

      /*  if ("Retailer"=="Retailer"){
      //      holder.itemView.visibility=View.GONE
        }*/


        holder.itemView.setOnClickListener {
          //  rvClickListner.clickPos(list[position].status,list[position].id)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tvProductName: TextView = itemview.findViewById(R.id.tvProductName)
       val tvQty: TextView = itemview.findViewById(R.id.tvQty)
       val tvProductPrice: TextView = itemview.findViewById(R.id.tvProductPrice)
       val tvDate: TextView = itemview.findViewById(R.id.tvDate)
       val tvDownload: TextView = itemview.findViewById(R.id.tvDownload)

    }

}