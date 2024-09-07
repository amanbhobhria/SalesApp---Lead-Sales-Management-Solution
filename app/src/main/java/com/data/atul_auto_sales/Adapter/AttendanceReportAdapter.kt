package com.data.atul_auto_sales.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.data.atul_auto_sales.Model.AttendanceReportBean
import com.data.atul_auto_sales.R
import com.data.atul_auto_sales.Utills.RvStatusClickListner


class AttendanceReportAdapter(var context: Activity, var list: List<AttendanceReportBean.Data>, var rvClickListner: RvStatusClickListner) : RecyclerView.Adapter<AttendanceReportAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_attendance_report, parent, false)
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

        holder.tvDate.text= list[position].date
        holder.tvStartTime.text=list[position].startTime
        holder.tvEndTime.text= list[position].endTime
        holder.tvWorkHr.text=list[position].workedHours
        holder.tvStartLoc.text=list[position].startLocation
     //   holder.tvEndLoc.text= list[position].endLocation.toString()

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
        val tvDate: TextView = itemview.findViewById(R.id.tvDate)
       val tvStartTime: TextView = itemview.findViewById(R.id.tvStartTime)
       val tvEndTime: TextView = itemview.findViewById(R.id.tvEndTime)
       val tvWorkHr: TextView = itemview.findViewById(R.id.tvWorkHr)
       val tvStartLoc: TextView = itemview.findViewById(R.id.tvStartLoc)
       val tvEndLoc: TextView = itemview.findViewById(R.id.tvEndLoc)
    }

}