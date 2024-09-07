package com.data.atul_auto_sales.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.data.atul_auto_sales.Adapter.AllStatusAdapter
import com.data.atul_auto_sales.Adapter.CommonFieldDrawerAdapter
import com.data.atul_auto_sales.ApiHelper.ApiController
import com.data.atul_auto_sales.ApiHelper.ApiResponseListner
import com.data.atul_auto_sales.Model.*
import com.data.atul_auto_sales.R
import com.data.atul_auto_sales.Utills.GeneralUtilities
import com.data.atul_auto_sales.Utills.PrefManager
import com.data.atul_auto_sales.Utills.RvClickListner
import com.data.atul_auto_sales.Utills.RvStatusClickListner
import com.data.atul_auto_sales.Utills.SalesApp
import com.data.atul_auto_sales.Utills.Utility
import com.data.atul_auto_sales.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants
import java.util.Locale

class DashboardActivity : AppCompatActivity(), ApiResponseListner {
    private lateinit var apiClient: ApiController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var rcNav: RecyclerView
    lateinit var rcStatus: RecyclerView
    lateinit var llLeadManage: LinearLayout
    lateinit var ivDownArrow: ImageView


    lateinit var rcMaster: RecyclerView
    lateinit var llMaster: LinearLayout
    lateinit var ivDownArrowMaster: ImageView


    lateinit var rcBeforeConfim: RecyclerView
    lateinit var llBeforeConfirmation: LinearLayout
    lateinit var ivDownArrow1: ImageView


    lateinit var rcAfterConfim: RecyclerView
    lateinit var llAfterConfirmation: LinearLayout
    lateinit var ivDownArrow2: ImageView


    lateinit var rcReport: RecyclerView
    lateinit var llReport: LinearLayout
    lateinit var ivDownArrow3: ImageView


    lateinit var llLogout: LinearLayout
    lateinit var navTextView: TextView

    private var currentLoc: String? = null
    private val permissionId = 2
    var list: List<Address>? = null
    var isActive = true
    var dayStatus = 5
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        //  val navView: NavigationView = binding.navView
        val navBottomView: BottomNavigationView = binding.appBarMain.bottomNavView

        val headerView: View = binding.navView.getHeaderView(0)
        rcNav = headerView.findViewById<RecyclerView>(R.id.rcNaDrawer)
        rcStatus = headerView.findViewById<RecyclerView>(R.id.rcStatus)
        llLeadManage = headerView.findViewById<LinearLayout>(R.id.llLeadManage)
        llLogout = headerView.findViewById<LinearLayout>(R.id.llLogout)
        ivDownArrow = headerView.findViewById<ImageView>(R.id.ivDownArrow)
        navTextView = headerView.findViewById<TextView>(R.id.navEmailTxt)


        val email = PrefManager.getString(ApiContants.EmailAddress, "User Email")
        navTextView.text = email






        rcMaster = headerView.findViewById<RecyclerView>(R.id.rcMaster)
        llMaster = headerView.findViewById<LinearLayout>(R.id.llMaster)
        ivDownArrowMaster = headerView.findViewById<ImageView>(R.id.ivDownArrowMaster)

        rcBeforeConfim = headerView.findViewById<RecyclerView>(R.id.rcBeforeConfim)
        llBeforeConfirmation = headerView.findViewById<LinearLayout>(R.id.llBeforeConfirmation)
        ivDownArrow1 = headerView.findViewById<ImageView>(R.id.ivDownArrow1)


        rcAfterConfim = headerView.findViewById<RecyclerView>(R.id.rcAfterConfim)
        llAfterConfirmation = headerView.findViewById<LinearLayout>(R.id.llAfterConfirmation)
        ivDownArrow2 = headerView.findViewById<ImageView>(R.id.ivDownArrow2)


        rcReport = headerView.findViewById<RecyclerView>(R.id.rcReport)
        llReport = headerView.findViewById<LinearLayout>(R.id.llReport)
        ivDownArrow3 = headerView.findViewById<ImageView>(R.id.ivDownArrow3)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        binding.appBarMain.appbarLayout.ivMenu.setOnClickListener {
            drawerLayout.open()
        }

        handleRecyclerDrawer()
        handleRcMaster()
        handleRecyclerBeforConfirm()
        handleRecyclerAfterConfirm()
        handleRecyclerReports()
        getLocation()
        apiGetStatus()


        //   binding.appBarMain.appbarLayout.switchDayStart="Day Start"
        binding.appBarMain.appbarLayout.ivLogout.setOnClickListener {
            apiCallLogout()
        }

        llLogout.setOnClickListener {
            apiCallLogout()
        }

        Log.d("token>>>>>", PrefManager.getString(ApiContants.AccessToken, ""))

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        /* appBarConfiguration = AppBarConfiguration(
             setOf(
                 R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
             ), drawerLayout
         )*/
        Log.d("asdad", PrefManager.getString(ApiContants.dayStatus, ""))

        if (PrefManager.getString(ApiContants.dayStatus, "").equals("start") || dayStatus == 1) {
            binding.appBarMain.appbarLayout.switchDayStart.isChecked = true
            //    Toast.makeText(this@DashboardActivity, "rr", Toast.LENGTH_SHORT).show()
            binding.appBarMain.appbarLayout.switchDayStart.text = "Day Start"
        }
        else {
            //     Toast.makeText(this@DashboardActivity, "werwe", Toast.LENGTH_SHORT).show()
            binding.appBarMain.appbarLayout.switchDayStart.isChecked = false
            binding.appBarMain.appbarLayout.switchDayStart.text = "Day End"
        }

        binding.appBarMain.appbarLayout.switchDayStart.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.appBarMain.appbarLayout.switchDayStart.text = "Day Start"
                getLocation()
                apiCallDayStatus(ApiContants.startDay)
                PrefManager.putString(ApiContants.dayStatus, "start")
            } else {
                binding.appBarMain.appbarLayout.switchDayStart.text = "Day End"
                getLocation()

                apiCallDayStatus(ApiContants.endDay)
                PrefManager.putString(ApiContants.dayStatus, "end")
            }
            /*  Toast.makeText(this@DashboardActivity, message.toString(),
                  Toast.LENGTH_SHORT).show()*/
        }
        //  setupActionBarWithNavController(navController, appBarConfiguration)
        navBottomView.setupWithNavController(navController)

        llLeadManage.setOnClickListener(View.OnClickListener {
            if (isActive) {
                isActive = false
                ivDownArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                llLeadManage.setBackgroundColor(getResources().getColor(R.color.lightgray))
                rcStatus.setVisibility(View.VISIBLE)

            } else {
                isActive = true
                ivDownArrow.setImageResource(R.drawable.baseline_arrow_forward_ios_24)
                llLeadManage.setBackgroundColor(getResources().getColor(R.color.white))
                rcStatus.setVisibility(View.GONE)

            }
        })

        llMaster.setOnClickListener(View.OnClickListener {
            if (isActive) {
                isActive = false
                ivDownArrowMaster.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                llMaster.setBackgroundColor(getResources().getColor(R.color.lightgray2))
                rcMaster.setVisibility(View.VISIBLE)

            } else {
                isActive = true
                ivDownArrowMaster.setImageResource(R.drawable.baseline_arrow_forward_ios_24)
                rcMaster.setVisibility(View.GONE)
                llMaster.setBackgroundColor(getResources().getColor(R.color.white))



            }
        })

        llBeforeConfirmation.setOnClickListener(View.OnClickListener {
            if (isActive) {
                isActive = false
                ivDownArrow1.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                llBeforeConfirmation.setBackgroundColor(getResources().getColor(R.color.lightgray2))
                rcBeforeConfim.setVisibility(View.VISIBLE)

            } else {
                isActive = true
                ivDownArrow1.setImageResource(R.drawable.baseline_arrow_forward_ios_24)
                rcBeforeConfim.setVisibility(View.GONE)
                llBeforeConfirmation.setBackgroundColor(getResources().getColor(R.color.white))

            }
        })

        llAfterConfirmation.setOnClickListener(View.OnClickListener {
            if (isActive) {
                isActive = false
                ivDownArrow2.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                rcAfterConfim.setVisibility(View.VISIBLE)
                llAfterConfirmation.setBackgroundColor(getResources().getColor(R.color.lightgray2))

            } else {
                isActive = true
                ivDownArrow2.setImageResource(R.drawable.baseline_arrow_forward_ios_24)
                rcAfterConfim.setVisibility(View.GONE)
                llAfterConfirmation.setBackgroundColor(getResources().getColor(R.color.white))

            }
        })

        llReport.setOnClickListener(View.OnClickListener {
            if (isActive) {
                isActive = false
                ivDownArrow3.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                rcReport.setVisibility(View.VISIBLE)
                llReport.setBackgroundColor(getResources().getColor(R.color.white))

            } else {
                isActive = true
                ivDownArrow3.setImageResource(R.drawable.baseline_arrow_forward_ios_24)
                rcReport.setVisibility(View.GONE)
                llReport.setBackgroundColor(getResources().getColor(R.color.white))
            }
        })

    }

    fun apiCallDayStatus(dayStatus: String) {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(this, this)
        val params = Utility.getParmMap()
        params["last_location"] = "${list?.get(0)?.latitude},${list?.get(0)?.latitude}"
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(dayStatus, params)

    }

    fun apiGetStatus() {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(this, this)
        val params = Utility.getParmMap()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.GetStatus, params)

    }

    fun apiCallLogout() {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(this, this)
        val params = Utility.getParmMap()
        params["mobile"] = PrefManager.getString(ApiContants.mobileNumber, "")
        params["password"] = PrefManager.getString(ApiContants.password, "")
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.logout, params)

    }

    override fun success(tag: String?, jsonElement: JsonElement) {
        try {
            apiClient.progressView.hideLoader()
            if (tag == ApiContants.logout) {
                val baseResponseBean = apiClient.getConvertIntoModel<BaseResponseBean>(
                    jsonElement.toString(),
                    BaseResponseBean::class.java
                )
                Toast.makeText(this, baseResponseBean.msg, Toast.LENGTH_SHORT).show()
                PrefManager.clear()
                GeneralUtilities.launchActivity(this, LoginActivity::class.java)
                finishAffinity()
            }

            if (tag == ApiContants.GetStatus) {
                val allStatusBean = apiClient.getConvertIntoModel<GetAllStatusBean>(
                    jsonElement.toString(),
                    GetAllStatusBean::class.java
                )
                //   Toast.makeText(this, allStatusBean.msg, Toast.LENGTH_SHORT).show()
                if (allStatusBean.error==false) {
                    dayStatus = allStatusBean.dayStatus
                    handleRcStatus(allStatusBean.data)
                }

            }

            if (tag == ApiContants.startDay) {
                val dayStatusBean = apiClient.getConvertIntoModel<StartDayBean>(
                    jsonElement.toString(),
                    StartDayBean::class.java
                )
                if (dayStatusBean.error==false) {
                    Utility.showSnackBar(this, dayStatusBean.msg)
                }
            }
            if (tag == ApiContants.endDay) {
                val dayStatusBean = apiClient.getConvertIntoModel<EndDayBean>(
                    jsonElement.toString(),
                    EndDayBean::class.java
                )
                if (dayStatusBean.error==false) {
                    Utility.showSnackBar(this, dayStatusBean.msg)
                }
            }
        }catch (e:Exception){
            Log.d("error>>",e.localizedMessage)
        }

    }

    override fun failure(tag: String?, errorMessage: String) {
        apiClient.progressView.hideLoader()
        // Toast.makeText(this, "4", Toast.LENGTH_SHORT).show()
        Utility.showSnackBar(this, errorMessage)
    }

    fun handleRecyclerDrawer() {
        rcNav.layoutManager = LinearLayoutManager(this)
        var mAdapter = CommonFieldDrawerAdapter(this, getMenus(), object :
            RvClickListner {
            override fun clickPos(pos: Int) {
                if (pos == 0) {
                    //    startActivity(Intent(this))
                    binding.drawerLayout.closeDrawers()
                } else if (pos == 3) {
                    startActivity(Intent(this@DashboardActivity, AddLeadActivity::class.java))

                    //   startActivity(Intent(this@DashboardActivity, OrderHistoryActivity::class.java))
                    binding.drawerLayout.closeDrawers()
                } else if (pos == 4) {


                } else if (pos == 5) {
                    apiCallLogout()
                    //   startActivity(Intent(this@DashboardActivity, OrderHistoryActivity::class.java))
                    binding.drawerLayout.closeDrawers()
                }
            }

        })
        rcNav.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false

    }

    private fun getMenus(): ArrayList<MenuModelBean> {
        var menuList = ArrayList<MenuModelBean>()
        menuList.add(MenuModelBean(0, "Home", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(3, "Add Lead", "", R.drawable.ic_dashbord))
        //  menuList.add(MenuModelBean(4, data.get(0).status, "", R.drawable.ic_logout_24))
        //     menuList.add(MenuModel(3, "My Order", ""))

        return menuList
    }

    fun handleRcMaster() {
        rcMaster.layoutManager = LinearLayoutManager(this)
        var mAdapter = CommonFieldDrawerAdapter(this, getMaster(), object :
            RvClickListner {
            override fun clickPos(pos: Int) {
                if (pos == 1) {
                    //  startActivity(Intent(this@DashboardActivity, MyCartActivity::class.java))
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            InsertArchitureActivity::class.java
                        )
                    )
                } else if (pos == 2) {
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            InsertInstallerActivity::class.java
                        )
                    )

                }else if (pos == 3) {
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            InsertClientActivity::class.java
                        )
                    )
                }
                binding.drawerLayout.closeDrawers()
            }

        })
        rcMaster.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false

    }

    private fun getMaster(): ArrayList<MenuModelBean> {
        var menuList = ArrayList<MenuModelBean>()
        //     menuList.add(MenuModelBean(1, "Insert Architect", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(2, "Insert Installer", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(3, "Insert Client", "", R.drawable.ic_dashbord))

        return menuList
    }

    fun handleRcStatus(data: List<GetAllStatusBean.Data>) {
        rcStatus.layoutManager = LinearLayoutManager(this)
        var mAdapter = AllStatusAdapter(this, data, object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {
                if (pos==0){
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            AddLeadActivity::class.java
                        )
                    )
                }else{
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            AllLeadActivity::class.java
                        ).putExtra("leadStatus", status)
                    )
                }

                binding.drawerLayout.closeDrawers()
            }
        })
        rcStatus.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false

    }

    fun handleRecyclerBeforConfirm() {
        rcBeforeConfim.layoutManager = LinearLayoutManager(this)
        var mAdapter = CommonFieldDrawerAdapter(this, getMenusConfirm(), object :
            RvClickListner {
            override fun clickPos(pos: Int) {
                if (pos==0){
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            QuoteLeadActivity::class.java
                        ).putExtra("leadStatus", "Requested Quote")
                    )
                }else if (pos==1){
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            QuoteLeadActivity::class.java
                        ).putExtra("leadStatus", "Generated Quote")
                    )
                }else if (pos==2){
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            QuoteLeadActivity::class.java
                        ).putExtra("leadStatus", "Quote Approval")
                    )
                }else if (pos==3){
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            QuoteLeadActivity::class.java
                        ).putExtra("leadStatus", "Coordinator Approval")
                    )
                }else if (pos==4){
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            QuoteLeadActivity::class.java
                        ).putExtra("leadStatus", "Awaiting for Amount Approval")
                    )
                }
            }
        })
        rcBeforeConfim.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false

    }

    private fun getMenusConfirm(): ArrayList<MenuModelBean> {
        var menuList = ArrayList<MenuModelBean>()
        menuList.add(MenuModelBean(0, "Quote Pending with Designer", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(1, "Quote Send by Designer", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(2, "Pending Client/Dealer Approval", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(3, "Account Approval", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(4, "Awaiting for Amount Approval", "", R.drawable.ic_dashbord))
        return menuList
    }

    fun handleRecyclerAfterConfirm() {
        rcAfterConfim.layoutManager = LinearLayoutManager(this)
        var mAdapter = CommonFieldDrawerAdapter(this, getMenusAfter(), object :
            RvClickListner {
            override fun clickPos(pos: Int) {
                if (pos==0){
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            QuoteLeadActivity::class.java
                        ).putExtra("leadStatus", "New Job Work")
                    )
                }else if (pos==1){
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            QuoteLeadActivity::class.java
                        ).putExtra("leadStatus", "Production Document Uploaded")
                    )
                }else if (pos==2){
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            QuoteLeadActivity::class.java
                        ).putExtra("leadStatus", "Pending Production")
                    )
                }else if (pos==3){
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            QuoteLeadActivity::class.java
                        ).putExtra("leadStatus", "Production in Process")
                    )
                }else if (pos==5){
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            QuoteLeadActivity::class.java
                        ).putExtra("leadStatus", "Production Complete")
                    )
                }else if (pos==6){
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            QuoteLeadActivity::class.java
                        ).putExtra("leadStatus", "Awaiting for Final Payment")
                    )
                }else if (pos==8){
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            QuoteLeadActivity::class.java
                        ).putExtra("leadStatus", "Awaiting for Dispatch")
                    )
                }else if (pos==9){
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            QuoteLeadActivity::class.java
                        ).putExtra("leadStatus", "Dispatched")
                    )
                }else if (pos==10){
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            QuoteLeadActivity::class.java
                        ).putExtra("leadStatus", "Delivered")
                    )
                }else if (pos==11){
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            QuoteLeadActivity::class.java
                        ).putExtra("leadStatus", "Production Document Uploaded")
                    )
                }
            }
        })
        rcAfterConfim.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false
    }

    private fun getMenusAfter(): ArrayList<MenuModelBean> {
        var menuList = ArrayList<MenuModelBean>()
        menuList.add(MenuModelBean(0, "Pending Production Document(LW Designer)", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(1, "Production Document Approval(LW Coordinator)", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(2, "Pending Production", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(3, "Production in Process", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(4, "Production Partial Completed", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(5, "Production Completed", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(6, "Pending for Final Payment", "", R.drawable.ic_dashbord))
        //   menuList.add(MenuModelBean(7, "Final Quote Approval", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(8, "Pending Dispatch", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(9, "Dispatched", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(10, "Delivered", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(11, "Production Document Uploaded", "", R.drawable.ic_dashbord))
        return menuList
    }

    fun handleRecyclerReports() {
        rcReport.layoutManager = LinearLayoutManager(this)
        var mAdapter = CommonFieldDrawerAdapter(this, getMenusReports(), object :
            RvClickListner {
            override fun clickPos(pos: Int) {
                if (pos==12){
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            AttndanceReportActivity::class.java
                        )
                    )
                    binding.drawerLayout.closeDrawers()
                }
            }
        })
        rcReport.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false

    }

    private fun getMenusReports(): ArrayList<MenuModelBean> {
        var menuList = ArrayList<MenuModelBean>()
        menuList.add(MenuModelBean(0, "Dealer Report", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(1, "Architect Report", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(2, "Installer Report", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(3, "Sales Manager Report", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(4, "Sales Executive Report", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(5, "Location Report", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(6, "Source Report", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(7, "Client Report", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(8, "Category Report", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(9, "Sub-Category Report", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(10, "Product Report", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(11, "Inventory Report", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(12, "Attendance Report", "", R.drawable.ic_dashbord))

        return menuList
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        list =

                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        Log.d("zxxzv", "Lat" + Gson().toJson(list?.get(0)?.latitude))
                        Log.d("zxxzv", "Long" + Gson().toJson(list?.get(0)?.longitude))
                        Log.d("zxxzv", Gson().toJson(list?.get(0)?.countryName))
                        Log.d("zxxzv", Gson().toJson(list?.get(0)?.locality))
                        Log.d("zxxzv", Gson().toJson(list?.get(0)?.getAddressLine(0)))

                        currentLoc = list?.get(0)?.getAddressLine(0)
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
        } else {
            //  checkPermissions()
        }
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            return true
        }
        return false
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

}