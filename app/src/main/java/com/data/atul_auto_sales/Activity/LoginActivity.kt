package com.data.atul_auto_sales.Activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.data.atul_auto_sales.ApiHelper.ApiController
import com.data.atul_auto_sales.ApiHelper.ApiResponseListner
import com.data.atul_auto_sales.Model.LoginBean
import com.data.atul_auto_sales.R
import com.data.atul_auto_sales.Utills.GeneralUtilities
import com.data.atul_auto_sales.Utills.PrefManager
import com.data.atul_auto_sales.Utills.Utility
import com.data.atul_auto_sales.databinding.ActivityLoginBinding

import com.data.atul_auto_sales.viewmodelactivity.LoginViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
//import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants
import java.util.Locale

class LoginActivity : AppCompatActivity() , ApiResponseListner {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var apiClient: ApiController
    lateinit var  loginViewModel:LoginViewModel
    var activity: Activity = this
    private var currentLoc: String?=null
    private val permissionId = 2
    var tokenValue=""

    private lateinit var mFusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
      //  loginViewModel = ViewModelProvider(this, ViewModelFactory(this)).get(LoginViewModel::class.java )
//        getToken()
//binding.loginViewModel=loginViewModel
 //       binding.lifecycleOwner=this
     //   getLocation()

        binding.btnLogin.setOnClickListener {
    doLogin()
}

    }

    private fun doLogin() {
        if (TextUtils.isEmpty(binding.editMobNo.text.toString())) {
            Utility.showSnackBar(this, "Please enter mobile number")
        } else if (binding.editMobNo.text.toString().length < 10) {
            Utility.showSnackBar(this, "Please enter valid mobile number")
        } else if (TextUtils.isEmpty(binding.editPassword.text.toString())) {
            Utility.showSnackBar(this, "Please enter password")
        } else {
        //    loginViewModel.apiCallLogin()
         apiCallLogin()
        }
    }

    fun apiCallLogin() {
        apiClient = ApiController(activity, this)
        val params = Utility.getParmMap()
        params["mobile"] = binding.editMobNo.text.toString()
        params["password"] = binding.editPassword.text.toString()
//        params["firebase_token"] =tokenValue.trim()

        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.login, params)

    }

    override fun success(tag: String?, jsonElement: JsonElement) {
        try {
            apiClient.progressView.hideLoader()
            if (tag == ApiContants.login) {
                val loginModel = apiClient.getConvertIntoModel<LoginBean>(jsonElement.toString(), LoginBean::class.java)
                if (loginModel.error==false) {
                    PrefManager.putString(ApiContants.AccessToken, loginModel.data.token )
                    PrefManager.putString(ApiContants.mobileNumber, binding.editMobNo.text.toString())
                    PrefManager.putString(ApiContants.password,  binding.editPassword.text.toString() )
                    PrefManager.putString(ApiContants.EmailAddress,  loginModel.data.email)
                    Toast.makeText(activity, loginModel.msg, Toast.LENGTH_SHORT).show()
                    GeneralUtilities.launchActivity(this, DashboardActivity::class.java)
                    finishAffinity()
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


    class ViewModelFactory(val context:LoginActivity):
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                LoginViewModel(context) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }


    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }
    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: List<Address>? =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        Log.d("zxxzv","Lat"+ Gson().toJson(list?.get(0)?.latitude))
                        Log.d("zxxzv","Long"+ Gson().toJson(list?.get(0)?.longitude))
                        Log.d("zxxzv", Gson().toJson(list?.get(0)?.countryName))
                        Log.d("zxxzv", Gson().toJson(list?.get(0)?.locality))
                        Log.d("zxxzv", Gson().toJson(list?.get(0)?.getAddressLine(0)))

                        currentLoc=list?.get(0)?.getAddressLine(0)
                        /*    mainBinding.apply {
                                tvLatitude.text = "Latitude\n${list[0].latitude}"
                                tvLongitude.text = "Longitude\n${list[0].longitude}"
                                tvCountryName.text = "Country Name\n${list[0].countryName}"
                                tvLocality.text = "Locality\n${list[0].locality}"
                                tvAddress.text = "Address\n${list[0].getAddressLine(0)}"
                            }*/
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }else{
            //  checkPermissions()
        }
    }


//    fun getToken(){
//        FirebaseMessaging.getInstance().token.addOnSuccessListener { token: String ->
//            if (!TextUtils.isEmpty(token)) {
//              tokenValue = token
//                Log.d("tokenData", "retrieve token successful : $token")
//            } else {
//                Log.w("tokenData", "token should not be null...")
//            }
//        }.addOnFailureListener { e: Exception? -> }.addOnCanceledListener {}
//            .addOnCompleteListener { task: Task<String> ->
//                Log.v(
//                    "tokenData",
//                    "This is the token : " + task.result
//                )
//            }
//    }
}
