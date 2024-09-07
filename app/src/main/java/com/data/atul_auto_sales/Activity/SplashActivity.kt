package com.data.atul_auto_sales.Activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.data.atul_auto_sales.R
import com.data.atul_auto_sales.Utills.ConnectivityListener
import com.data.atul_auto_sales.Utills.GeneralUtilities
import com.data.atul_auto_sales.Utills.PrefManager
import com.data.atul_auto_sales.Utills.SalesApp
import com.data.atul_auto_sales.databinding.ActivitySplashBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.stpl.antimatter.Utils.ApiContants
import com.stpl.antimatter.Utils.ApiContants.Companion.isconnectedtonetwork

class SplashActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener,
    ConnectivityListener.ConnectivityReceiverListener {
    private lateinit var binding: ActivitySplashBinding
    var myReceiver: ConnectivityListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    setContentView(R.layout.activity_splash)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        myReceiver = ConnectivityListener()
        val animation4 = AnimationUtils.loadAnimation(this, R.anim.slide_up_enter)
        //GeneralUtilities.getInstance().setStatusBarColor(SplashActivity.this, ContextCompat.getColor(context, R.color.colorPrimaryDark));
        binding.imagea.startAnimation(animation4)

        Handler(Looper.getMainLooper()).postDelayed({
            // callNextActivity()
            if (PrefManager.getString(ApiContants.AccessToken, "") != "") {
                GeneralUtilities.launchActivity(this, DashboardActivity::class.java)
                finishAffinity()

            } else {
                GeneralUtilities.launchActivity(this, LoginActivity::class.java)
                finishAffinity()
            }

        }, 2500)
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
        isconnectedtonetwork = isconnected
        GeneralUtilities.internetConnectivityAction(this, isconnected)
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}

}