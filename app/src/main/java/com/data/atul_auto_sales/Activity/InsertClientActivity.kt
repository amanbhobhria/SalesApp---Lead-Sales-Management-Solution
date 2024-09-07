package com.data.atul_auto_sales.Activity

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.data.atul_auto_sales.ApiHelper.ApiController
import com.data.atul_auto_sales.ApiHelper.ApiResponseListner
import com.data.atul_auto_sales.Model.CityBean
import com.data.atul_auto_sales.Model.InsertClientBean
import com.data.atul_auto_sales.R
import com.data.atul_auto_sales.Utills.ConnectivityListener
import com.data.atul_auto_sales.Utills.GeneralUtilities
import com.data.atul_auto_sales.Utills.SalesApp
import com.data.atul_auto_sales.Utills.Utility
import com.data.atul_auto_sales.databinding.ActivityInsertclientBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants

class InsertClientActivity : AppCompatActivity(), ApiResponseListner,
    GoogleApiClient.OnConnectionFailedListener,
    ConnectivityListener.ConnectivityReceiverListener {
    private lateinit var binding: ActivityInsertclientBinding
    private lateinit var apiClient: ApiController
    var myReceiver: ConnectivityListener? = null
    var stateName = ""
    var activity: Activity = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insertclient)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        myReceiver = ConnectivityListener()
        binding.igToolbar.tvTitle.text="Insert Client"
        binding.igToolbar.ivMenu.setImageDrawable(resources.getDrawable(R.drawable.ic_back_black))
        binding.igToolbar.ivMenu.setOnClickListener { finish() }
        binding.igToolbar.ivLogout.visibility=View.GONE
        binding.igToolbar.switchDayStart.visibility=View.GONE

        setState()
        //  loginViewModel = ViewModelProvider(this, ViewModelFactory(this)).get(LoginViewModel::class.java )

//binding.loginViewModel=loginViewModel
        //       binding.lifecycleOwner=this

        binding.btnSubmit.setOnClickListener {
            if (TextUtils.isEmpty(binding.editName.text.toString())) {
                Utility.showSnackBar(this, "Please enter name")
            } else if (TextUtils.isEmpty(binding.editMobNo.text.toString())) {
                Utility.showSnackBar(this, "Please enter mobile number")
            } else if (binding.editMobNo.text.toString().length < 10) {
                Utility.showSnackBar(this, "Please enter valid mobile number")
            } else if (TextUtils.isEmpty(binding.stateselector.text.toString())) {
                Utility.showSnackBar(this, "Please select state")
            } else if (TextUtils.isEmpty(binding.cityselector.text.toString())) {
                Utility.showSnackBar(this, "Please select city")
            } else {
                apiInsertClient()
            }
        }
    }

    fun apiInsertClient() {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(activity, this)
        val params = Utility.getParmMap()
        params["name"] = binding.editName.text.toString()
        params["number"] = binding.editMobNo.text.toString()
        params["active"] = "1"
        params["state"] = binding.stateselector.text.toString()
        params["city"] = binding.cityselector.text.toString()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.insertClient, params)

    }

    fun apiCity(stateName: String) {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(activity, this)
        val params = Utility.getParmMap()
        params["state"] = stateName

        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getCity, params)

    }

    override fun success(tag: String?, jsonElement: JsonElement) {
        try {
            apiClient.progressView.hideLoader()
            if (tag == ApiContants.insertClient) {
                val insertClientBean = apiClient.getConvertIntoModel<InsertClientBean>(
                    jsonElement.toString(),
                    InsertClientBean::class.java
                )
                Toast.makeText(this,insertClientBean.msg,Toast.LENGTH_SHORT).show()

                finish()
            }

            if (tag == ApiContants.getCity) {
                val cityBean = apiClient.getConvertIntoModel<CityBean>(
                    jsonElement.toString(),
                    CityBean::class.java
                )
                if (cityBean.error==false) {
                    val state = arrayOfNulls<String>(cityBean.data.size)
                    for (i in cityBean.data.indices) {
                        //Storing names to string array
                        state[i] = cityBean.data.get(i).city
                    }
                    val adapte1: ArrayAdapter<String?>
                    adapte1 = ArrayAdapter(
                        this@InsertClientActivity,
                        android.R.layout.simple_list_item_1,
                        state
                    )
                    binding.cityselector.setAdapter(adapte1)
                    binding.cityselector.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
                        var cityName = cityBean.data.get(position).city
                        binding.cityselector.setText(cityName)
                        Log.d("StateID", "" + cityName)
                        Toast.makeText(
                            applicationContext,
                            binding.cityselector.getText().toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        apiCity(stateName)
                    })
                }

            }
        }catch (e:Exception){
            Log.d("error>>",e.localizedMessage)
        }

    }

    override fun failure(tag: String?, errorMessage: String) {

        apiClient.progressView.hideLoader()

        Utility.showSnackBar(activity, errorMessage)
    }


    fun setState(){
        val state = arrayOfNulls<String>(SalesApp.stateList.size)
        for (i in SalesApp.stateList.indices) {
            state[i] = SalesApp.stateList.get(i).state
        }

          binding.stateselector.setAdapter(
              ArrayAdapter(
                  this@InsertClientActivity,
                  android.R.layout.simple_list_item_1, state
              )
          )

        binding.stateselector.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
             stateName = SalesApp.stateList.get(position).state

            Log.d("StateID", "" + stateName)
            binding.stateselector.setText(stateName)
            Toast.makeText(
                applicationContext,
                binding.stateselector.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()

            setState()
            apiCity(stateName)
        })
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
