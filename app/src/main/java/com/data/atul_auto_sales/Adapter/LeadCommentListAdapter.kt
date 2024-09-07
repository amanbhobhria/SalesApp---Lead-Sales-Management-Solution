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


class LeadCommentListAdapter(var context: Activity, var list: List<LeadDetailBean.Data.LeadComment>, var rvClickListner: RvStatusClickListner) : RecyclerView.Adapter<LeadCommentListAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_lead_comment, parent, false)
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

       /* comment": "",
        "status": "PROCESSING",
        "remind_date": "",
        "remind_time": "",
        "user_id": 36,
        "created_date": "2024-01-31 15:39:31",
        "bank_name": null,
        "utr_number": null,
        "amount": "0.00",
        "advanced_payment_file": null,
        "production_document": null,
        "quote_document": null*/


        holder.status.text= list[position].status.toString()
      /*  if (list[position].bankName.toString()!=null){
            holder.tvbank_name.text= list[position].bankName.toString()
        }*/
       /* if (list[position].utrNumber.toString()!=null){
            holder.tvutr_number.text= list[position].utrNumber.toString()
        }*/

     //   holder.tvamount.text=list[position].amount.toString()
        if (list[position].comment==null){

        }else{
            holder.comment.text=list[position].comment.toString()
        }

        if (list[position].remindDate==null){

        }else{
            holder.tvReminingDate.text=list[position].remindDate.toString()
        }
        if (list[position].remindTime==null){

        }else{
            holder.tvReminingTime.text=list[position].remindTime.toString()
        }

        holder.tvcreated_date.text=list[position].createdDate.toString()




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
        val status: TextView = itemview.findViewById(R.id.status)
        val comment: TextView = itemview.findViewById(R.id.comment)
        val tvReminingDate: TextView = itemview.findViewById(R.id.tvReminingDate)
        val tvReminingTime: TextView = itemview.findViewById(R.id.tvReminingTime)
        val tvamount: TextView = itemview.findViewById(R.id.tvamount)
        val tvcreated_date: TextView = itemview.findViewById(R.id.tvcreated_date)


    }

}