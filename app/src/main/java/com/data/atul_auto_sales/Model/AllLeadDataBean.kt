package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class AllLeadDataBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
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
        val architect: String, // test
        @SerializedName("budget")
        val budget: Any, // null
        @SerializedName("campaign")
        val campaign: Any, // null
        @SerializedName("catg_id")
        val catgId: Int, // 1
        @SerializedName("city")
        val city: String, // Zirakpur
        @SerializedName("classification")
        val classification: Any, // null
        @SerializedName("client_address")
        val clientAddress: String, // pkl
        @SerializedName("client_city")
        val clientCity: String, // Panchkula
        @SerializedName("client_id")
        val clientId: Int, // 1
        @SerializedName("client_name")
        val clientName: String, // Adesh
        @SerializedName("client_number")
        val clientNumber: String, // 8146653301
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
        val id: Int, // 11
        @SerializedName("is_allocated")
        val isAllocated: Int, // 0
        @SerializedName("is_interested_allocated")
        val isInterestedAllocated: Int, // 0
        @SerializedName("last_comment")
        val lastComment: String, // noremarks
        @SerializedName("lead_date")
        val leadDate: String, // 2024-01-16 15:31:41
        @SerializedName("name")
        val name: Any, // null
        @SerializedName("notes")
        val notes: Any, // null
        @SerializedName("phone")
        val phone: Any, // null
        @SerializedName("plumber")
        val plumber: String, // Test
        @SerializedName("production_document")
        val productionDocument: Any, // null
        @SerializedName("project_id")
        val projectId: Any, // null
        @SerializedName("projects")
        val projects: Any, // null
        @SerializedName("property_stage")
        val propertyStage: String, // 2BHK
        @SerializedName("quote_approval")
        val quoteApproval: Int, // 0
        @SerializedName("quote_document")
        val quoteDocument: Any, // null
        @SerializedName("quotes_status")
        val quotesStatus: String, // 1
        @SerializedName("remind_date")
        val remindDate: Any, // null
        @SerializedName("remind_time")
        val remindTime: Any, // null
        @SerializedName("size")
        val size: Any, // null
        @SerializedName("source")
        val source: String, // Fb
        @SerializedName("state")
        val state: String, // Punjab
        @SerializedName("status")
        val status: String, // NEWLEAD
        @SerializedName("sub_catg_id")
        val subCatgId: Int, // 1
        @SerializedName("type")
        val type: String, // Residential
        @SerializedName("updated_date")
        val updatedDate: String, // 2024-01-1712:37:21
        @SerializedName("user_id")
        val userId: Int, // 14
        @SerializedName("whatsapp_no")
        val whatsappNo: Any // null
    )
}