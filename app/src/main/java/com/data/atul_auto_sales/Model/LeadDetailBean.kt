package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class LeadDetailBean(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("address")
        val address: String, // address
        @SerializedName("advance_payment_document")
        val advancePaymentDocument: Any, // null
        @SerializedName("allocated_date")
        val allocatedDate: String, // 2024-01-31 17:20:59
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
        val architect: String, // Avesh Parashar
        @SerializedName("budget")
        val budget: Any, // null
        @SerializedName("campaign")
        val campaign: Any, // null
        @SerializedName("category")
        val category: String, // Bunglow
        @SerializedName("catg_id")
        val catgId: Int, // 1
        @SerializedName("city")
        val city: String, // Saharanpur
        @SerializedName("classification")
        val classification: Any, // null
        @SerializedName("client_address")
        val clientAddress: String, // sector 21
        @SerializedName("client_city")
        val clientCity: String, // Panchkula
        @SerializedName("client_id")
        val clientId: Int, // 6
        @SerializedName("client_name")
        val clientName: String, // Clikzop
        @SerializedName("client_number")
        val clientNumber: String, // 9876787676
        @SerializedName("conversion_type")
        val conversionType: String, // Completed
        @SerializedName("custom_product")
        val customProduct: List<CustomProduct>,
        @SerializedName("dealer")
        val dealer: Any, // null
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
        val gst: String,
        @SerializedName("id")
        val id: Int, // 5
        @SerializedName("inquiry")
        val inquiry: List<Inquiry>,
        @SerializedName("is_allocated")
        val isAllocated: Int, // 1
        @SerializedName("is_interested_allocated")
        val isInterestedAllocated: Int, // 0
        @SerializedName("last_comment")
        val lastComment: String, // done
        @SerializedName("lead_comment")
        val leadComment: List<LeadComment>,
        @SerializedName("lead_date")
        val leadDate: String, // 2024-01-31 17:00:27
        @SerializedName("lead_product")
        val leadProduct: List<LeadProduct>,
        @SerializedName("lead_remarks")
        val leadRemarks: List<LeadRemark>,
        @SerializedName("lead_rfq_img")
        val leadRfqImg: List<LeadRfqImg>,
        @SerializedName("name")
        val name: Any, // null
        @SerializedName("notes")
        val notes: Any, // null
        @SerializedName("phone")
        val phone: Any, // null
        @SerializedName("plumber")
        val plumber: Any, // null
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
        val remindDate: String, // 2024-02-01
        @SerializedName("remind_time")
        val remindTime: String, // 17:16
        @SerializedName("sales_manager_allocate")
        val salesManagerAllocate: Int, // 1
        @SerializedName("sales_manager_id")
        val salesManagerId: Int, // 36
        @SerializedName("size")
        val size: Any, // null
        @SerializedName("source")
        val source: String, // Facebook Paid
        @SerializedName("state")
        val state: String, // Uttar Pradesh
        @SerializedName("status")
        val status: String, // Dispatched
        @SerializedName("sub_category")
        val subCategory: String, // Single Storey
        @SerializedName("sub_catg_id")
        val subCatgId: Int, // 1
        @SerializedName("type")
        val type: String, // Residential
        @SerializedName("updated_date")
        val updatedDate: String, // 2024-01-31 18:42:40
        @SerializedName("user_id")
        val userId: Int, // 29
        @SerializedName("whatsapp_no")
        val whatsappNo: Any // null
    ) {
        data class CustomProduct(
            @SerializedName("category")
            val category: String, // uPVC
            @SerializedName("created_at")
            val createdAt: String, // 2024-01-31 17:51:12
            @SerializedName("delivery_remarks")
            val deliveryRemarks: String, // asdfasdf
            @SerializedName("drawing_documents")
            val drawingDocuments: String, // ../uploaded_documents/7296299341215619-lead-ex.png
            @SerializedName("id")
            val id: Int, // 1
            @SerializedName("is_delivered")
            val isDelivered: Int, // 1
            @SerializedName("lead_id")
            val leadId: Int, // 5
            @SerializedName("price")
            val price: String, // 200.00
            @SerializedName("product")
            val product: String, // Glass
            @SerializedName("product_name")
            val productName: String, // Glass
            @SerializedName("production_done")
            val productionDone: Int, // 1
            @SerializedName("qty")
            val qty: Int, // 2
            @SerializedName("remarks")
            val remarks: String, // asdfasdf
            @SerializedName("updated_at")
            val updatedAt: String // 2024-01-31 18:42:29
        )

        data class Inquiry(
            @SerializedName("created_at")
            val createdAt: String, // 2024-01-31 17:11:23
            @SerializedName("glass_color")
            val glassColor: String, // White
            @SerializedName("glass_thickness")
            val glassThickness: String, // 40mm
            @SerializedName("hardware_specification")
            val hardwareSpecification: String, // hardware specification 
            @SerializedName("id")
            val id: Int, // 3
            @SerializedName("lead_id")
            val leadId: Int, // 5
            @SerializedName("no_of_days")
            val noOfDays: Int, // 0
            @SerializedName("product")
            val product: String, // Aluminum
            @SerializedName("profile_color")
            val profileColor: String, // White
            @SerializedName("profile_name")
            val profileName: String, // profile color
            @SerializedName("qty")
            val qty: Int, // 0
            @SerializedName("remarks")
            val remarks: String, // remarks
            @SerializedName("solution_type")
            val solutionType: String
        )

        data class LeadComment(
            @SerializedName("advanced_payment_file")
            val advancedPaymentFile: String, // ../uploaded_documents/3323097533960511-lead-ex-2.png
            @SerializedName("amount")
            val amount: String, // 0.00
            @SerializedName("bank_name")
            val bankName: String, // SBI
            @SerializedName("comment")
            val comment: String, // g y
            @SerializedName("created_date")
            val createdDate: String, // 2024-01-31 17:01:29
            @SerializedName("id")
            val id: Int, // 5
            @SerializedName("lead_id")
            val leadId: Int, // 5
            @SerializedName("production_document")
            val productionDocument: Any, // null
            @SerializedName("quote_document")
            val quoteDocument: Any, // null
            @SerializedName("remind_date")
            val remindDate: String,
            @SerializedName("remind_time")
            val remindTime: String,
            @SerializedName("status")
            val status: String, // PROCESSING
            @SerializedName("user_id")
            val userId: Int, // 36
            @SerializedName("utr_number")
            val utrNumber: String // utr
        )

        data class LeadProduct(
            @SerializedName("created_at")
            val createdAt: String, // 2024-01-31 17:51:12
            @SerializedName("delivery_remarks")
            val deliveryRemarks: String, // asdf
            @SerializedName("drawing_documents")
            val drawingDocuments: Any, // null
            @SerializedName("id")
            val id: Int, // 1
            @SerializedName("is_delivered")
            val isDelivered: Int, // 1
            @SerializedName("lead_id")
            val leadId: Int, // 5
            @SerializedName("price")
            val price: String, // 200.00
            @SerializedName("product_id")
            val productId: Int, // 1
            @SerializedName("product_name")
            val productName: String, // Wall to Glass Hinge Two Way
            @SerializedName("qty")
            val qty: Int, // 2
            @SerializedName("updated_at")
            val updatedAt: String // 2024-01-31 18:42:28
        )

        data class LeadRemark(
            @SerializedName("advanced_payment_file")
            val advancedPaymentFile: String, // ../uploaded_documents/3323097533960511-lead-ex-2.png
            @SerializedName("amount")
            val amount: String, // 0.00
            @SerializedName("bank_name")
            val bankName: String, // SBI
            @SerializedName("comment")
            val comment: String, // g y
            @SerializedName("created_date")
            val createdDate: String, // 2024-01-31 17:01:29
            @SerializedName("id")
            val id: Int, // 5
            @SerializedName("lead_id")
            val leadId: Int, // 5
            @SerializedName("production_document")
            val productionDocument: Any, // null
            @SerializedName("quote_document")
            val quoteDocument: Any, // null
            @SerializedName("remind_date")
            val remindDate: String,
            @SerializedName("remind_time")
            val remindTime: String,
            @SerializedName("status")
            val status: String, // PROCESSING
            @SerializedName("user_id")
            val userId: Int, // 36
            @SerializedName("utr_number")
            val utrNumber: String // utr
        )

        data class LeadRfqImg(
            @SerializedName("careated_at")
            val careatedAt: String, // 2024-01-31 17:12:26
            @SerializedName("id")
            val id: Int, // 11
            @SerializedName("img")
            val img: String, // ../uploaded_documents/393380-title-(1).jpg
            @SerializedName("lead_id")
            val leadId: Int, // 5
            @SerializedName("remarks")
            val remarks: String // hh
        )
    }
}