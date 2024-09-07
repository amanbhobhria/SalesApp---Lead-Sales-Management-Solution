package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class DashboardBean(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String, // Datafound
    @SerializedName("0")
    val x0: String // leads
) {
    data class Data(
        @SerializedName("booked")
        val booked: String, // 0
        @SerializedName("call_scheduled")
        val callScheduled: String, // 0
        @SerializedName("cancelled")
        val cancelled: String, // 0
        @SerializedName("completed")
        val completed: String, // 0
        @SerializedName("converted_leads")
        val convertedLeads: String, // 0
        @SerializedName("day_status")
        val dayStatus: Int, // 1
        @SerializedName("executive_count")
        val executiveCount: Int, // 0
        @SerializedName("interested_leads")
        val interestedLeads: String, // 0
        @SerializedName("monthly_sale")
        val monthlySale: Int, // 0
        @SerializedName("new_leads")
        val newLeads: String, // 7
        @SerializedName("Partial")
        val partial: String, // 0
        @SerializedName("pending_leads")
        val pendingLeads: String, // 0
        @SerializedName("processing_leads")
        val processingLeads: String, // 0
        @SerializedName("sm_newLeads")
        val smNewLeads: String, // 0
        @SerializedName("total_leads")
        val totalLeads: Int, // 7
        @SerializedName("visit_done")
        val visitDone: String, // 0
        @SerializedName("visit_scheduled")
        val visitScheduled: String, // 0
        @SerializedName("yearly_sale")
        val yearlySale: Int // 0
    )
}