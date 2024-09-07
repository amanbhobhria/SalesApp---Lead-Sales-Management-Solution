package com.data.atul_auto_sales.Activity

import android.app.Activity
import android.app.DatePickerDialog
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
import com.data.atul_auto_sales.Model.BaseResponseBean
import com.data.atul_auto_sales.Model.CityBean
import com.data.atul_auto_sales.R
import com.data.atul_auto_sales.Utills.ConnectivityListener
import com.data.atul_auto_sales.Utills.GeneralUtilities
import com.data.atul_auto_sales.Utills.SalesApp
import com.data.atul_auto_sales.Utills.Utility
import com.data.atul_auto_sales.databinding.ActivityInsertarchitureBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants
import java.util.Calendar

class InsertArchitureActivity : AppCompatActivity(), ApiResponseListner,
    GoogleApiClient.OnConnectionFailedListener,
    ConnectivityListener.ConnectivityReceiverListener {
    private lateinit var binding: ActivityInsertarchitureBinding
    private lateinit var apiClient: ApiController
   // var date: OnDateSetListener? = null
    var myReceiver: ConnectivityListener? = null
    var stateName = ""
    var activity: Activity = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insertarchiture)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        myReceiver = ConnectivityListener()
        binding.igToolbar.tvTitle.text="Architect"
        binding.igToolbar.ivMenu.setImageDrawable(resources.getDrawable(R.drawable.ic_back_black))
        binding.igToolbar.ivMenu.setOnClickListener { finish() }
        binding.igToolbar.ivLogout.visibility=View.GONE
        binding.igToolbar.switchDayStart.visibility=View.GONE

        setState()

        binding.editDOB.setOnClickListener(View.OnClickListener {
            val c = Calendar.getInstance()
            val year = c[Calendar.YEAR]
            val month = c[Calendar.MONTH]
            val day = c[Calendar.DAY_OF_MONTH]
            val datePickerDialog = DatePickerDialog(
                this@InsertArchitureActivity,
                { view, year, monthOfYear, dayOfMonth ->
                    //  dob.setText(dateofnews);
                    val dateofnews = "${dayOfMonth.toString()+ "/" + (monthOfYear + 1).toString() + "/" + year}"

                    binding.editDOB.setText(dateofnews)
                },
                year, month, day
            )
            datePickerDialog.show()
        })


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
            } else if (TextUtils.isEmpty(binding.editDOB.text.toString())) {
                Utility.showSnackBar(this, "Please select dob")
            } else if (TextUtils.isEmpty(binding.editAddress.text.toString())) {
                Utility.showSnackBar(this, "Please Enter address")
            } else if (TextUtils.isEmpty(binding.stateselector.text.toString())) {
                Utility.showSnackBar(this, "Please select state")
            } else if (TextUtils.isEmpty(binding.cityselector.text.toString())) {
                Utility.showSnackBar(this, "Please select city")
            } else {
                //    loginViewModel.apiCallLogin()
                apiInsertArchiture()
            }
        }

    }

    fun apiInsertArchiture() {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(activity, this)
        val params = Utility.getParmMap()
        params["name"] = binding.editName.text.toString()
        params["number"] = binding.editMobNo.text.toString()
        params["dob"] = binding.editDOB.text.toString()
        params["address"] = binding.editAddress.text.toString()
        params["state"] = binding.stateselector.text.toString()
        params["city"] = binding.cityselector.text.toString()

        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.insertArchitect, params)

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
            if (tag == ApiContants.insertArchitect) {
                val baseResponseBean = apiClient.getConvertIntoModel<BaseResponseBean>(
                    jsonElement.toString(),
                    BaseResponseBean::class.java
                )
                Toast.makeText(this,baseResponseBean.msg,Toast.LENGTH_SHORT).show()

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
                        this@InsertArchitureActivity,
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
                  this@InsertArchitureActivity,
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
