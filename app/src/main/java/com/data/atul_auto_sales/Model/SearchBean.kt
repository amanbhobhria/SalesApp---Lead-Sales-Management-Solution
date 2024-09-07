package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class SearchBean(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("leads")
        val leads: List<Lead>
    ) {
        data class Lead(
            @SerializedName("address")
            val address: Any, // null
            @SerializedName("advance_payment_document")
            val advancePaymentDocument: Any, // null
            @SerializedName("allocated_date")
            val allocatedDate: Any, // null
            @SerializedName("app_city")
            val appCity: Any, // null
            @SerializedName("app_contact")
            val appContact: Any, // null
            @SerializedName("app_doa")
            val appDoa: Any, // null
            @SerializedName("app_dob")
            val appDob: Any, // null
            @SerializedName("app_name")
            val appName: Any, // null
            @SerializedName("architect")
            val architect: String, // Uttar Pradesh
            @SerializedName("budget")
            val budget: Any, // null
            @SerializedName("campaign")
            val campaign: Any, // null
            @SerializedName("catg_id")
            val catgId: Int, // 1
            @SerializedName("city")
            val city: String, // Chittoor
            @SerializedName("classification")
            val classification: Any, // null
            @SerializedName("client_id")
            val clientId: Int, // 8
            @SerializedName("conversion_type")
            val conversionType: Any, // null
            @SerializedName("drawing_upload")
            val drawingUpload: Any, // null
            @SerializedName("email")
            val email: Any, // null
            @SerializedName("field3")
            val field3: Any, // null
            @SerializedName("field4")
            val field4: Any, // null
            @SerializedName("final_price")
            val finalPrice: Any, // null
            @SerializedName("gst")
            val gst: Any, // null
            @SerializedName("id")
            val id: Int, // 26
            @SerializedName("is_allocated")
            val isAllocated: Int, // 0
            @SerializedName("is_interested_allocated")
            val isInterestedAllocated: Int, // 0
            @SerializedName("last_comment")
            val lastComment: String, // remark
            @SerializedName("lead_date")
            val leadDate: String, // 2024-01-2008:33:25
            @SerializedName("name")
            val name: String, // MrAdesh
            @SerializedName("notes")
            val notes: Any, // null
            @SerializedName("number")
            val number: String, // 8146653301
            @SerializedName("phone")
            val phone: Any, // null
            @SerializedName("plumber")
            val plumber: String, // AndamanNicobar
            @SerializedName("production_document")
            val productionDocument: Any, // null
            @SerializedName("project_id")
            val projectId: Any, // null
            @SerializedName("projects")
            val projects: Any, // null
            @SerializedName("property_stage")
            val propertyStage: String, // Foundation
            @SerializedName("quote_approval")
            val quoteApproval: Int, // 0
            @SerializedName("quote_document")
            val quoteDocument: Any, // null
            @SerializedName("quotes_status")
            val quotesStatus: Any, // null
            @SerializedName("remind_date")
            val remindDate: Any, // null
            @SerializedName("remind_time")
            val remindTime: Any, // null
            @SerializedName("sales_manager_allocate")
            val salesManagerAllocate: Int, // 0
            @SerializedName("sales_manager_id")
            val salesManagerId: Any, // null
            @SerializedName("size")
            val size: Any, // null
            @SerializedName("source")
            val source: String, // Google
            @SerializedName("state")
            val state: String, // AndhraPradesh
            @SerializedName("status")
            val status: String, // NEWLEAD
            @SerializedName("sub_catg_id")
            val subCatgId: Int, // 1
            @SerializedName("type")
            val type: String, // Residential
            @SerializedName("updated_date")
            val updatedDate: Any, // null
            @SerializedName("user_id")
            val userId: Int, // 21
            @SerializedName("whatsapp_no")
            val whatsappNo: Any // null
        )
    }
}