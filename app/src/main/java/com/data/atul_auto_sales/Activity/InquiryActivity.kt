package com.data.atul_auto_sales.Activity

import android.app.Activity
import android.os.Bundle
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
import com.data.atul_auto_sales.Model.InsertInquiryBean
import com.data.atul_auto_sales.R
import com.data.atul_auto_sales.Utills.ConnectivityListener
import com.data.atul_auto_sales.Utills.GeneralUtilities
import com.data.atul_auto_sales.Utills.SalesApp
import com.data.atul_auto_sales.Utills.Utility
import com.data.atul_auto_sales.databinding.ActivityInquiryBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants

class InquiryActivity : AppCompatActivity(), ApiResponseListner,
    GoogleApiClient.OnConnectionFailedListener,
    ConnectivityListener.ConnectivityReceiverListener {
    private lateinit var binding: ActivityInquiryBinding
    private lateinit var apiClient: ApiController
    var myReceiver: ConnectivityListener? = null
    var productName = ""
    var solutionType = ""
    var glassThickness = ""
    var glassColor = ""
    var profileName = ""
    var profileColor = ""
    var leadID = ""


    var activity: Activity = this
    val productList = arrayOf(
        "uPVC",
        "Aluminum",
        "Glass"
    )
    val solutionTypeList = arrayOf(
        "Only Glass Supply",
        "Glass+Installation",
        "Glass+Hardware+Installation"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inquiry)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        myReceiver = ConnectivityListener()
        binding.igToolbar.tvTitle.text = "Inquiry"
        binding.igToolbar.ivMenu.setImageDrawable(resources.getDrawable(R.drawable.ic_back_black))
        binding.igToolbar.ivMenu.setOnClickListener { finish() }
        binding.igToolbar.ivLogout.visibility = View.GONE
        binding.igToolbar.switchDayStart.visibility = View.GONE
        leadID = intent.getStringExtra("leadID")!!
        setProductName()
        setSolutionType()
        setGlassColor()
        setGlassThickness()
        setProfileColor()
        setProfileName()

        //llGlassInstal=Glass   Only Glass Supply,Glass+Hardware+installtion,Glass+installtion

        //  loginViewModel = ViewModelProvider(this, ViewModelFactory(this)).get(LoginViewModel::class.java )

//binding.loginViewModel=loginViewModel
        //       binding.lifecycleOwner=this


        binding.btnSubmit.setOnClickListener {
            apiInsertInquiry()
        }
    }


    fun apiInsertInquiry() {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(activity, this)
        val params = Utility.getParmMap()
        params["lead_id"] = leadID
        params["product"] = productName
        params["profile_name"] = profileName
        params["profile_color"] = profileColor
        params["glass_thickness"] = glassThickness
        params["glass_color"] = glassColor
        params["solution_type"] = solutionType
        params["hardware_specification"] = binding.editHardware.text.toString()
        params["remarks"] = binding.editRemark.text.toString()
        params["qty"] = binding.editQty.text.toString()
        params["no_of_days"] = binding.editNoDays.text.toString()

        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getInsertInquiry, params)

    }

    override fun success(tag: String?, jsonElement: JsonElement) {
        apiClient.progressView.hideLoader()
        if (tag == ApiContants.getInsertInquiry) {
            val insertInquiryBean = apiClient.getConvertIntoModel<InsertInquiryBean>(
                jsonElement.toString(),
                InsertInquiryBean::class.java
            )
            Toast.makeText(this, insertInquiryBean.msg, Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    override fun failure(tag: String?, errorMessage: String) {

        apiClient.progressView.hideLoader()

        Utility.showSnackBar(activity, errorMessage)
    }


    fun setProductName() {
        val state = arrayOfNulls<String>(productList.size)
        for (i in productList.indices) {
            state[i] = productList.get(i)
        }

        binding.spProduct.setAdapter(
            ArrayAdapter(
                this@InquiryActivity,
                android.R.layout.simple_list_item_1, state
            )
        )

        binding.spProduct.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            productName = productList.get(position)

            Log.d("StateID", "" + productName)
            binding.spProduct.setText(productName)
            Toast.makeText(
                applicationContext,
                binding.spProduct.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()


            if (productName.equals("Glass")){
                binding.inputSolutionType.visibility=View.VISIBLE
            }else{
                binding.inputSolutionType.visibility=View.GONE
            }

            if (productName.equals("uPVC")||productName.equals("Aluminum")){
                binding.inputProfileName.visibility=View.VISIBLE
                binding.inputProfileColor.visibility=View.VISIBLE
                binding.inputGlassThickness.visibility=View.VISIBLE
                binding.inputGlassColor.visibility=View.VISIBLE
                binding.inputHardware.visibility=View.VISIBLE
                binding.inputRemarks.visibility=View.VISIBLE
                binding.inputQty.visibility=View.GONE
                binding.inputNoDays.visibility=View.GONE
            }else{
                binding.inputProfileName.visibility=View.GONE
                binding.inputProfileColor.visibility=View.GONE
                binding.inputGlassThickness.visibility=View.GONE
                binding.inputGlassColor.visibility=View.GONE
                binding.inputHardware.visibility=View.GONE
                binding.inputRemarks.visibility=View.GONE
                binding.inputQty.visibility=View.GONE
                binding.inputNoDays.visibility=View.GONE

            }
            setProductName()

        })
    }

    fun setSolutionType() {
        val state = arrayOfNulls<String>(solutionTypeList.size)
        for (i in solutionTypeList.indices) {
            state[i] = solutionTypeList.get(i)
        }

        binding.spSolutionType.setAdapter(
            ArrayAdapter(
                this@InquiryActivity,
                android.R.layout.simple_list_item_1, state
            )
        )

        binding.spSolutionType.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            solutionType = solutionTypeList.get(position)

            Log.d("StateID", "" + solutionType)
            binding.spSolutionType.setText(solutionType)
            Toast.makeText(
                applicationContext,
                binding.spSolutionType.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()

            setSolutionType()

            if (productName.equals("Glass")&&solutionType.equals("Glass+Hardware+Installation")){
                binding.inputGlassThickness.visibility=View.VISIBLE
                binding.inputGlassColor.visibility=View.VISIBLE
                binding.inputQty.visibility=View.VISIBLE
                binding.inputNoDays.visibility=View.VISIBLE
                binding.inputHardware.visibility=View.VISIBLE
            }else{
                binding.inputGlassThickness.visibility=View.GONE
                binding.inputGlassColor.visibility=View.GONE
                binding.inputQty.visibility=View.GONE
                binding.inputNoDays.visibility=View.GONE
                binding.inputHardware.visibility=View.GONE
            }

             if (productName.equals("Glass")&&solutionType.equals("Glass+Installation")){
                binding.inputGlassThickness.visibility=View.VISIBLE
                binding.inputGlassColor.visibility=View.VISIBLE
                binding.inputQty.visibility=View.VISIBLE
                binding.inputNoDays.visibility=View.VISIBLE
            }else{

             }


              if (productName.equals("Glass")&&solutionType.equals("Only Glass Supply")){
                binding.inputGlassThickness.visibility=View.VISIBLE
                binding.inputGlassColor.visibility=View.VISIBLE
                binding.inputQty.visibility=View.VISIBLE
            }else{

              }
        })
    }

    fun setGlassThickness() {
        val state = arrayOfNulls<String>(SalesApp.glassThicknessList.size)
        for (i in SalesApp.glassThicknessList.indices) {
            state[i] = SalesApp.glassThicknessList.get(i).name
        }

        binding.spGlassThikness.setAdapter(
            ArrayAdapter(
                this@InquiryActivity,
                android.R.layout.simple_list_item_1, state
            )
        )

        binding.spGlassThikness.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            glassThickness = SalesApp.glassThicknessList.get(position).name

            Log.d("StateID", "" + solutionType)
            binding.spGlassThikness.setText(solutionType)
            Toast.makeText(
                applicationContext,
                binding.spGlassThikness.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()

            setGlassThickness()

        })
    }

    fun setGlassColor() {
        val state = arrayOfNulls<String>(SalesApp.glassColorList.size)
        for (i in SalesApp.glassColorList.indices) {
            state[i] = SalesApp.glassColorList.get(i).name
        }

        binding.spGlassColor.setAdapter(
            ArrayAdapter(
                this@InquiryActivity,
                android.R.layout.simple_list_item_1, state
            )
        )

        binding.spGlassColor.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            glassColor = SalesApp.glassColorList.get(position).name

            Log.d("StateID", "" + solutionType)
            binding.spGlassColor.setText(solutionType)
            Toast.makeText(
                applicationContext,
                binding.spGlassColor.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()

            setGlassColor()

        })
    }

    fun setProfileColor() {
        val state = arrayOfNulls<String>(SalesApp.profileColorList.size)
        for (i in SalesApp.profileColorList.indices) {
            state[i] = SalesApp.profileColorList.get(i).name
        }

        binding.spProfileColor.setAdapter(
            ArrayAdapter(
                this@InquiryActivity,
                android.R.layout.simple_list_item_1, state
            )
        )

        binding.spProfileColor.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            profileColor = SalesApp.profileColorList.get(position).name

            Log.d("StateID", "" + profileColor)
            binding.spProfileColor.setText(profileColor)
            Toast.makeText(
                applicationContext,
                binding.spProfileColor.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()

            setProfileColor()

        })
    }

    fun setProfileName() {
        val state = arrayOfNulls<String>(SalesApp.profileNameList.size)
        for (i in SalesApp.profileNameList.indices) {
            state[i] = SalesApp.profileNameList.get(i).name
        }

        binding.spProfileName.setAdapter(
            ArrayAdapter(
                this@InquiryActivity,
                android.R.layout.simple_list_item_1, state
            )
        )

        binding.spProfileName.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            profileName = SalesApp.profileNameList.get(position).name

            Log.d("StateID", "" + profileName)
            binding.spProfileName.setText(profileName)
            Toast.makeText(
                applicationContext,
                binding.spProfileName.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()

            setProfileName()

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
