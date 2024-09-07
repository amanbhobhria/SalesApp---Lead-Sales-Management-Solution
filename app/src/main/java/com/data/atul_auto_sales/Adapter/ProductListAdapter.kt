package com.data.atul_auto_sales.Adapter

import android.app.Activity
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.data.atul_auto_sales.Model.MultipleProductBean
import com.data.atul_auto_sales.R
import com.data.atul_auto_sales.Utills.RvStatusClickListner


class ProductListAdapter(var context: Activity,val way:String, var list: MutableList<MultipleProductBean>, var rvClickListner: RvStatusClickListner) : RecyclerView.Adapter<ProductListAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_dataproduct_list, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setIsRecyclable(false)

   /*     holder.tvAdd.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(20f))
        holder.tvQtyAdd.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(100f))
        holder.tvQtyMinus.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(100f))
        holder.tvQty.background = RoundView(Color.TRANSPARENT, RoundView.getRadius(20f), true, R.color.orange)
        holder.tvOff.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(20f))
      */
        if (way.equals("MultipleProduct")){
            holder.ivImage.visibility = View.GONE
        }else{
           holder.ivImage.visibility = View.VISIBLE
            val myBitmap = BitmapFactory.decodeFile(list[position].image)
            holder.ivImage.setImageBitmap(myBitmap)
        }

        holder.tvCatName.text="Category Name : "+ list[position].catName
        holder.tvSubCatName.text= "SubCategory Name : "+list[position].subCatName
        holder.tvQty.text= "Qty : "+list[position].qty.toString()

       // holder.ivImage.setImageDrawable(context.resources.getDrawable(list[position].drawableId))

      /*  if ("Retailer"=="Retailer"){
      //      holder.itemView.visibility=View.GONE
        }*/

        holder.itemView.setOnClickListener {
            rvClickListner.clickPos(list[position].prod_name,0)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tvCatName: TextView = itemview.findViewById(R.id.tvCatName)
       val tvSubCatName: TextView = itemview.findViewById(R.id.tvSubCatName)
       val tvQty: TextView = itemview.findViewById(R.id.tvQty)
       val ivImage: ImageView = itemview.findViewById(R.id.ivImage)
    }

}