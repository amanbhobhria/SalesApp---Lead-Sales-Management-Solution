package com.data.atul_auto_sales.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.data.atul_auto_sales.Model.LeadDetailBean
import com.data.atul_auto_sales.R
import com.data.atul_auto_sales.Utills.RvStatusClickListner


class LeadProdctListAdapter(var context: Activity, var list: List<LeadDetailBean.Data.Inquiry>, var rvClickListner: RvStatusClickListner) : RecyclerView.Adapter<LeadProdctListAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_lead_inquiry, parent, false)
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

        holder.tvProductName.text= list[position].product.toString()
        holder.tvQty.text= list[position].qty.toString()
        holder.tvProfileName.text= list[position].profileName.toString()
        holder.tvDate.text=list[position].createdAt.toString()
        holder.tvProfileColor.text=list[position].profileColor.toString()
        holder.tvglass_thickness.text=list[position].glassThickness.toString()
        holder.tvglass_color.text=list[position].glassColor.toString()
        holder.tvhardware_specification.text=list[position].hardwareSpecification.toString()
        holder.tvremarks.text=list[position].remarks.toString()
        holder.tvsolution_type.text=list[position].solutionType.toString()
        holder.tvno_of_days.text=list[position].noOfDays.toString()

       // holder.ivImage.setImageDrawable(context.resources.getDrawable(list[position].drawableId))

      /*  if ("Retailer"=="Retailer"){
      //      holder.itemView.visibility=View.GONE
        }*/


        holder.itemView.setOnClickListener {
         //   rvClickListner.clickPos(list[position].status,list[position].id)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tvProductName: TextView = itemview.findViewById(R.id.tvProductName)
        val tvProfileName: TextView = itemview.findViewById(R.id.tvProfileName)
       val tvQty: TextView = itemview.findViewById(R.id.tvQty)
       val tvDate: TextView = itemview.findViewById(R.id.tvDate)
       val tvProfileColor: TextView = itemview.findViewById(R.id.tvProfileColor)
       val tvglass_thickness: TextView = itemview.findViewById(R.id.tvglass_thickness)
       val tvglass_color: TextView = itemview.findViewById(R.id.tvglass_color)
       val tvhardware_specification: TextView = itemview.findViewById(R.id.tvhardware_specification)
       val tvremarks: TextView = itemview.findViewById(R.id.tvremarks)
       val tvsolution_type: TextView = itemview.findViewById(R.id.tvsolution_type)
       val tvno_of_days: TextView = itemview.findViewById(R.id.tvno_of_days)

    }

}