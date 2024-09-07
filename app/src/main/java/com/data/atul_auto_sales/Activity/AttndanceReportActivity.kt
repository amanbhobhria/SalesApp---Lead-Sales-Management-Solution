package com.data.atul_auto_sales.Activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.data.atul_auto_sales.Adapter.AttendanceReportAdapter
import com.data.atul_auto_sales.ApiHelper.ApiController
import com.data.atul_auto_sales.ApiHelper.ApiResponseListner
import com.data.atul_auto_sales.Model.AttendanceReportBean
import com.data.atul_auto_sales.R
import com.data.atul_auto_sales.Utills.*
import com.data.atul_auto_sales.databinding.ActivityAttendanceReportBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants

class AttndanceReportActivity : AppCompatActivity(), ApiResponseListner,
    GoogleApiClient.OnConnectionFailedListener,
    ConnectivityListener.ConnectivityReceiverListener {
    private lateinit var binding: ActivityAttendanceReportBinding
    private lateinit var apiClient: ApiController
    var myReceiver: ConnectivityListener? = null
    var activity: Activity = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_attendance_report)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        myReceiver = ConnectivityListener()

        binding.igToolbar.tvTitle.text = "Attendance Report"
        binding.igToolbar.ivMenu.setImageDrawable(resources.getDrawable(R.drawable.ic_back_black))
        binding.igToolbar.ivMenu.setOnClickListener { finish() }
        binding.igToolbar.ivLogout.visibility = View.GONE
        binding.igToolbar.switchDayStart.visibility = View.GONE

        apiAttendanceReport("","")
    }

    fun apiAttendanceReport(month:String,year:String) {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(this, this)
        val params = Utility.getParmMap()
        params["month"] = month
        params["year"] = year
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.AttendanceReport, params)

    }

    fun handleLeadDetail(data: List<AttendanceReportBean.Data>) {
        binding.rcAttendanceReport.layoutManager = LinearLayoutManager(this)
        var mAdapter = AttendanceReportAdapter(this, data, object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {
          //      apiLeadDetail(pos)
            }
        })
        binding.rcAttendanceReport.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false
    }

    override fun success(tag: String?, jsonElement: JsonElement?) {
        try {
            apiClient.progressView.hideLoader()
            if (tag == ApiContants.AttendanceReport) {
                val attendanceReportBean = apiClient.getConvertIntoModel<AttendanceReportBean>(
                    jsonElement.toString(),
                    AttendanceReportBean::class.java
                )
                //   Toast.makeText(this, allStatusBean.msg, Toast.LENGTH_SHORT).show()
                if (attendanceReportBean.error==false) {
                    handleLeadDetail(attendanceReportBean.data)
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
