package com.data.atul_auto_sales.Activity

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.data.atul_auto_sales.Adapter.*
import com.data.atul_auto_sales.ApiHelper.ApiController
import com.data.atul_auto_sales.ApiHelper.ApiResponseListner
import com.data.atul_auto_sales.Model.AllLeadDataBean
import com.data.atul_auto_sales.Model.LeadDetailBean
import com.data.atul_auto_sales.R
import com.data.atul_auto_sales.Utills.ConnectivityListener
import com.data.atul_auto_sales.Utills.GeneralUtilities
import com.data.atul_auto_sales.Utills.RvStatusClickListner
import com.data.atul_auto_sales.Utills.SalesApp
import com.data.atul_auto_sales.Utills.Utility
import com.data.atul_auto_sales.databinding.ActivityAllLeadBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants

class AllLeadActivity : AppCompatActivity(), ApiResponseListner,
    GoogleApiClient.OnConnectionFailedListener,
    ConnectivityListener.ConnectivityReceiverListener {
    private lateinit var binding: ActivityAllLeadBinding
    private lateinit var apiClient: ApiController
    var myReceiver: ConnectivityListener? = null
    var activity: Activity = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_lead)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        myReceiver = ConnectivityListener()

        binding.igToolbar.tvTitle.text = "All Lead"
        binding.igToolbar.ivMenu.setImageDrawable(resources.getDrawable(R.drawable.ic_back_black))
        binding.igToolbar.ivMenu.setOnClickListener { finish() }
        binding.igToolbar.ivLogout.visibility = View.GONE
        binding.igToolbar.switchDayStart.visibility = View.GONE

        intent.getStringExtra("leadStatus")?.let { apiAllLead(it) }

    }

    fun apiAllLead(status: String) {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(this, this)
        val params = Utility.getParmMap()
        params["status"] = status
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.AllLeadData, params)

    }

    fun handleAllLead(data: List<AllLeadDataBean.Data>) {
        binding.rcAllLead.layoutManager = LinearLayoutManager(this)
        var mAdapter = AllLeadAdapter(this, data, intent.getStringExtra("leadStatus"), object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {
                apiLeadDetail(pos)
            }
        })
        binding.rcAllLead.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false

    }

    override fun success(tag: String?, jsonElement: JsonElement?) {
        try {
            apiClient.progressView.hideLoader()
            if (tag == ApiContants.AllLeadData) {
                val allLeadDataBean = apiClient.getConvertIntoModel<AllLeadDataBean>(
                    jsonElement.toString(),
                    AllLeadDataBean::class.java
                )
                //   Toast.makeText(this, allStatusBean.msg, Toast.LENGTH_SHORT).show()
                if (allLeadDataBean.error==false) {
                    handleAllLead(allLeadDataBean.data)
                }

            }
            if (tag == ApiContants.LeadDetail) {
                val leadDeatilBean = apiClient.getConvertIntoModel<LeadDetailBean>(
                    jsonElement.toString(),
                    LeadDetailBean::class.java
                )
                //   Toast.makeText(this, allStatusBean.msg, Toast.LENGTH_SHORT).show()
                if (leadDeatilBean.error==false) {
                    setLeadDetailDialog(leadDeatilBean.data)
                }

            }

        }catch (e:Exception){
            Log.d("error>>",e.localizedMessage)
        }

    }

    override fun failure(tag: String?, errorMessage: String) {
        apiClient.progressView.hideLoader()
        Utility.showSnackBar(this, errorMessage)
    }

    fun apiLeadDetail(leadID: Int) {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(this, this)
        val params = Utility.getParmMap()
        params["lead_id"] = leadID.toString()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.LeadDetail, params)
    }

    fun setLeadDetailDialog(data: LeadDetailBean.Data) {
        val builder = AlertDialog.Builder(this,R.style.DialogFullscreen)
            .create()
        val dialog = layoutInflater.inflate(R.layout.dailog_leaddetail,null)
        val  ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val tvStatus = dialog.findViewById<TextView>(R.id.tvStatus)
        val tvType = dialog.findViewById<TextView>(R.id.tvType)
        val tvCategory = dialog.findViewById<TextView>(R.id.tvCategory)
        val tvSubCategory = dialog.findViewById<TextView>(R.id.tvSubCategory)
        val tvPlumber = dialog.findViewById<TextView>(R.id.tvPlumber)
        val tvArchitect = dialog.findViewById<TextView>(R.id.tvArchitect)
        val tvPropertyStage = dialog.findViewById<TextView>(R.id.tvPropertyStage)
        val tvSource = dialog.findViewById<TextView>(R.id.tvSource)
        val tvState = dialog.findViewById<TextView>(R.id.tvState)
        val tvCity = dialog.findViewById<TextView>(R.id.tvCity)
        val tvGST = dialog.findViewById<TextView>(R.id.tvGST)
        val tvAddress = dialog.findViewById<TextView>(R.id.tvAddress)
        val tvDealer = dialog.findViewById<TextView>(R.id.tvDealer)
        val tvClient = dialog.findViewById<TextView>(R.id.tvClient)
        val tvClientNumber = dialog.findViewById<TextView>(R.id.tvClientNumber)
        val tvClientAddress = dialog.findViewById<TextView>(R.id.tvClientAddress)
        val rcLeadProdtList = dialog.findViewById<RecyclerView>(R.id.rcLeadProdtList)
        val rcCustmProdtList = dialog.findViewById<RecyclerView>(R.id.rcCustmProdtList)
        val rcCommentList = dialog.findViewById<RecyclerView>(R.id.rcCommentList)
        val rcLProdtList = dialog.findViewById<RecyclerView>(R.id.rcLProdtList)
        val rcCProdtList = dialog.findViewById<RecyclerView>(R.id.rcCProdtList)
        val rcDocumentList = dialog.findViewById<RecyclerView>(R.id.rcDocumentList)
        builder.setView(dialog)
        builder.show()
        ivClose.setOnClickListener {
            builder.dismiss()
        }

        tvStatus.setText(data.status)
        tvType.setText(data.type)
        tvCategory.setText(data.category.toString())
        tvSubCategory.setText(data.subCategory.toString())
        //  tvPlumber.setText(data.plumber.toString())
        tvArchitect.setText(data.architect)
        tvPropertyStage.setText(data.propertyStage)
        tvSource.setText(data.source)
        tvState.setText(data.state)
        tvCity.setText(data.city)
        tvClient.setText(data.clientName)
        tvClientNumber.setText(data.clientNumber)
        if (!data.clientAddress.isNullOrEmpty()) tvClientAddress.setText(data.clientAddress)
        if (!data.gst.isNullOrEmpty()) tvGST.setText(data.gst)

        //   tvAddress.setText(data.address.toString())
        //  tvDealer.setText(data.dealer.toString())

        if (!data.inquiry.isNullOrEmpty()) handleLeadInquiryList(rcLeadProdtList, data.inquiry)
        if (!data.leadRfqImg.isNullOrEmpty()) handleLeadRfqImgList(rcCustmProdtList, data.leadRfqImg)
        if (!data.leadComment.isNullOrEmpty()) handleLeadCommentList(rcCommentList, data.leadComment)
        if (!data.leadProduct.isNullOrEmpty()) handleLeadProductList(rcLProdtList, data.leadProduct)
        if (!data.customProduct.isNullOrEmpty())  handleCustomProductList(rcCProdtList, data.customProduct)
     //   if (!data.customProduct.isNullOrEmpty())  handleDocumentList(rcDocumentList, data.customProduct)

    }

    fun handleLeadInquiryList(
        rcLeadProdtList: RecyclerView,
        leadProduct: List<LeadDetailBean.Data.Inquiry>
    ) {
        rcLeadProdtList.layoutManager = LinearLayoutManager(this)
        var mAdapter = LeadProdctListAdapter(this, leadProduct, object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {

            }
        })
        rcLeadProdtList.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false

    }

    fun handleLeadRfqImgList(
        rcCustmProdtList: RecyclerView,
        leadProduct: List<LeadDetailBean.Data.LeadRfqImg>
    ) {
        rcCustmProdtList.layoutManager = LinearLayoutManager(this)
        var mAdapter = CustomProductListAdapter(this, leadProduct, object :
            RvStatusClickListner {
            override fun clickPos(imgUrl: String, pos: Int) {
                openFullScreenDialog(imgUrl)
            }
        })
        rcCustmProdtList.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false

    }

    fun handleLeadCommentList(
        rcCommentList: RecyclerView,
        leadProduct: List<LeadDetailBean.Data.LeadComment>
    ) {
        rcCommentList.layoutManager = LinearLayoutManager(this)
        var mAdapter = LeadCommentListAdapter(this, leadProduct, object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {

            }
        })
        rcCommentList.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false

    }

    fun handleLeadProductList(
        rcLeadProdtList: RecyclerView,
        leadProduct: List<LeadDetailBean.Data.LeadProduct>
    ) {
        rcLeadProdtList.layoutManager = LinearLayoutManager(this)
        var mAdapter = LeadMultiProdListAdapter(this, leadProduct, object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {

            }
        })
        rcLeadProdtList.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false

    }

    fun handleCustomProductList(
        rcCustmProdtList: RecyclerView,
        leadProduct: List<LeadDetailBean.Data.CustomProduct>
    ) {
        rcCustmProdtList.layoutManager = LinearLayoutManager(this)
        var mAdapter = CustomProdListAdapter(this, leadProduct, object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {

            }
        })
        rcCustmProdtList.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false

    }

    fun handleDocumentList(
        rcDocumentList: RecyclerView,
        leadProduct: List<LeadDetailBean.Data.CustomProduct>
    ) {
        rcDocumentList.layoutManager = LinearLayoutManager(this)
        var mAdapter = DocumentListAdapter(this, leadProduct, object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {

            }
        })
        rcDocumentList.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false

    }

    private fun openFullScreenDialog(imgUrl: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.fullscreen_dailog)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()

        val ivFullImage = dialog.findViewById<ImageView>(R.id.ivFullImage)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val ivDownload = dialog.findViewById<ImageView>(R.id.ivDownload)
        //     ivClose.background = RoundView(Color.BLACK, RoundView.getRadius(100f))
        ivClose.setOnClickListener {
            dialog.dismiss() }
        ivDownload.setOnClickListener {
            GeneralUtilities.downloadUrl(activity,ApiContants.downloadUrl+imgUrl)
        }

        Glide.with(this)
            .load(ApiContants.BaseUrl+imgUrl)
            .into(ivFullImage)

    }
    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()
        GeneralUtilities.unregisterBroadCastReceiver(this, myReceiver)
    }

    override fun onResume() {
        GeneralUtilities.registerBroadCastReceiver(this, myReceiver)
        SalesApp.setConnectivityListener(this)
        super.onResume()
    }

    override fun onNetworkConnectionChange(isconnected: Boolean) {
        ApiContants.isconnectedtonetwork = isconnected
        GeneralUtilities.internetConnectivityAction(this, isconnected)
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}

}
