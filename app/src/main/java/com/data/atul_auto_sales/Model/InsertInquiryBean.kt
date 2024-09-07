package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class InsertInquiryBean(
    @SerializedName("data")
    val `data`: List<Any>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String // Save Successfully
)