package com.data.atul_auto_sales.Fragment.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.data.atul_auto_sales.Activity.LoginActivity
import com.data.atul_auto_sales.Activity.SearchDataActivity
import com.data.atul_auto_sales.Adapter.DashboardAdapter
import com.data.atul_auto_sales.ApiHelper.ApiController
import com.data.atul_auto_sales.ApiHelper.ApiResponseListner
import com.data.atul_auto_sales.Model.*
import com.data.atul_auto_sales.R
import com.data.atul_auto_sales.Utills.GeneralUtilities
import com.data.atul_auto_sales.Utills.PrefManager
import com.data.atul_auto_sales.Utills.RvClickListner
import com.data.atul_auto_sales.Utills.SalesApp
import com.data.atul_auto_sales.Utills.Utility
import com.data.atul_auto_sales.databinding.FragmentHomeBinding
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants

class HomeFragment : Fragment(), ApiResponseListner {
    private lateinit var apiClient: ApiController
    private var _binding: FragmentHomeBinding? = null
    var newLeads=""
    var pending_leads=""
    var processing_leads=""
    var interested_leads=""
    var call_scheduled=""
    var visit_scheduled=""
    var visit_done=""
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.refreshLayout.setOnRefreshListener {
            apiCallDashboard()
            binding.refreshLayout.isRefreshing=false
        }

        apiCallDashboard()
        apiAllGet()


        binding.ivSearch.setOnClickListener {
            startActivity(
                Intent(context, SearchDataActivity::class.java).putExtra(
                    "searchKey",
                    binding.edSearch.text.toString()
                )
            )
        }
        binding.CardInsert.setOnClickListener {
            callPGURL("https://atulautomotive.online/dealer-signup")

        }
        binding.CardInsert2.setOnClickListener {
            callPGURL("https://atulautomotive.online/architect-signup")
        }

        binding.tvAllLead.setOnClickListener {
            //   startActivity(Intent(context,AllLeadActivity::class.java).putExtra("leadStatus","0"))
            startActivity(
                Intent(context, SearchDataActivity::class.java).putExtra(
                    "searchKey",
                    binding.edSearch.text.toString()
                )
            )
        }
        //    val textView: TextView = binding.textHome
        /*   homeViewModel.text.observe(viewLifecycleOwner) {
               textView.text = it
           }*/
        return root
    }

    fun apiAllGet() {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(activity, this)
        val params = Utility.getParmMap()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getSource, params)
        apiClient.getApiPostCall(ApiContants.getState, params)
      //  apiClient.getApiPostCall(ApiContants.getPlumber, params)
        apiClient.getApiPostCall(ApiContants.getArchitect, params)
        apiClient.getApiPostCall(ApiContants.getPropertyStage, params)
        apiClient.getApiPostCall(ApiContants.getClient, params)
      //  apiClient.getApiPostCall(ApiContants.getProductCategory, params)
      //  apiClient.getApiPostCall(ApiContants.getCustomProdCat, params)
        apiClient.getApiPostCall(ApiContants.GetDealer, params)

        apiClient.getApiPostCall(ApiContants.GetGlassColor, params)
        apiClient.getApiPostCall(ApiContants.GetProfileColor, params)
        apiClient.getApiPostCall(ApiContants.GetGlassThickness, params)
        apiClient.getApiPostCall(ApiContants.GetProfileName, params)
    }

    fun apiCallDashboard() {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(activity, this)
        val params = Utility.getParmMap()
        params["mobile"] = PrefManager.getString(ApiContants.mobileNumber, "")
        params["password"] = PrefManager.getString(ApiContants.password, "")
        // apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.dashboard, params)

    }


    override fun success(tag: String?, jsonElement: JsonElement) {
try {
    apiClient.progressView.hideLoader()
    if (tag == ApiContants.logout) {
        val baseResponseBean = apiClient.getConvertIntoModel<BaseResponseBean>(
            jsonElement.toString(),
            BaseResponseBean::class.java
        )
        Toast.makeText(activity, baseResponseBean.msg, Toast.LENGTH_SHORT).show()
        PrefManager.clear()
        GeneralUtilities.launchActivity(
            requireContext() as AppCompatActivity?,
            LoginActivity::class.java
        )
        requireActivity().finishAffinity()
    }

    if (tag == ApiContants.dashboard) {
        val dashboardBean = apiClient.getConvertIntoModel<DashboardBean>(
            jsonElement.toString(),
            DashboardBean::class.java
        )
        Toast.makeText(activity, dashboardBean.msg, Toast.LENGTH_SHORT).show()
        if (dashboardBean.error==false) {
            binding.tvMonthlyAmount.text = dashboardBean.data.monthlySale.toString()
            binding.tvAmount.text = dashboardBean.data.yearlySale.toString()
            //  {"data":{"total_leads":0,"new_leads":null,"pending_leads":null,
            //  "processing_leads":null,"interested_leads":null,"call_scheduled":null,
            //  "visit_scheduled":null,"visit_done":null,"executive_count":0,"monthly_sale":0,
            //  "yearly_sale":0,"day_status":0},"error":false,"msg":"Data found","0":"leads"}


            if (dashboardBean.data.newLeads==null){
                newLeads="0"

            }  else{
                newLeads=dashboardBean.data.newLeads
            }
            if (dashboardBean.data.pendingLeads==null){
                pending_leads="0"

            } else{
                pending_leads=dashboardBean.data.pendingLeads
            }
            if (dashboardBean.data.processingLeads==null){
                processing_leads="0"

            }else{
                processing_leads=dashboardBean.data.processingLeads
            }
            if (dashboardBean.data.interestedLeads==null){
                interested_leads="0"

            } else{
                interested_leads=dashboardBean.data.interestedLeads
            }
            if (dashboardBean.data.callScheduled==null){
                call_scheduled="0"

            } else{
                call_scheduled=dashboardBean.data.callScheduled
            }
            if (dashboardBean.data.visitScheduled==null){
                visit_scheduled="0"

            } else{
                visit_scheduled=dashboardBean.data.visitScheduled
            }
            if (dashboardBean.data.visitDone==null){
                visit_done="0"

            }else{
                visit_done=dashboardBean.data.visitDone
            }
            handleRcDashboard(dashboardBean.data)
        }

    }

    if (tag == ApiContants.getSource) {
        val sourceBean = apiClient.getConvertIntoModel<SourceBean>(
            jsonElement.toString(),
            SourceBean::class.java
        )
        if (sourceBean.error==false) {
            SalesApp.sourceList.clear()
            SalesApp.sourceList.addAll(sourceBean.data)
        }
    }

    if (tag == ApiContants.getState) {
        val stateBean = apiClient.getConvertIntoModel<StateBean>(
            jsonElement.toString(),
            StateBean::class.java
        )
        if (stateBean.error==false) {
            SalesApp.stateList.clear()
            SalesApp.stateList.addAll(stateBean.data)
        }
    }

    /*    if (tag == ApiContants.getPlumber) {
            val istallerBean = apiClient.getConvertIntoModel<InstallerBean>(
                jsonElement.toString(),
                InstallerBean::class.java
            )
            SalesApp.installerList.addAll(istallerBean.data)

        }*/

    if (tag == ApiContants.getArchitect) {
        val architectBean = apiClient.getConvertIntoModel<ArchitectBean>(
            jsonElement.toString(),
            ArchitectBean::class.java
        )
        if (architectBean.error==false) {
            SalesApp.architectList.clear()
            SalesApp.architectList.addAll(architectBean.data)
        }
    }

    if (tag == ApiContants.getPropertyStage) {
        val propertyStageBean = apiClient.getConvertIntoModel<PropertyStageBean>(
            jsonElement.toString(),
            PropertyStageBean::class.java
        )
        if (propertyStageBean.error==false) {
            SalesApp.propertyStageList.clear()
            SalesApp.propertyStageList.addAll(propertyStageBean.data)
        }
    }

    if (tag == ApiContants.getClient) {
        val clientBean = apiClient.getConvertIntoModel<ClientBean>(
            jsonElement.toString(),
            ClientBean::class.java
        )
        if (clientBean.error==false){
            SalesApp.clientList.clear()
            SalesApp.clientList.addAll(clientBean.data)
        }

    }

    /* if (tag == ApiContants.getProductCategory) {
         val productCategoryBean = apiClient.getConvertIntoModel<ProductCategoryBean>(
             jsonElement.toString(),
             ProductCategoryBean::class.java
         )
         SalesApp.productCatList.addAll(productCategoryBean.data)
     }
     if (tag == ApiContants.getCustomProdCat) {
         val custProdCatBean = apiClient.getConvertIntoModel<CustProdCatBean>(
             jsonElement.toString(),
             CustProdCatBean::class.java
         )
         SalesApp.prodCustomCatList.addAll(custProdCatBean.data)
     }*/

    if (tag == ApiContants.GetDealer) {
        val dealerBean = apiClient.getConvertIntoModel<DealerBean>(
            jsonElement.toString(),
            DealerBean::class.java
        )
        if (dealerBean.error==false){
            SalesApp.dealerList.clear()
            SalesApp.dealerList.addAll(dealerBean.data)
        }

    }
    if (tag == ApiContants.GetGlassColor) {
        val glassColorBean = apiClient.getConvertIntoModel<GlassColorBean>(
            jsonElement.toString(),
            GlassColorBean::class.java
        )
        if (glassColorBean.error==false) {
            SalesApp.glassColorList.clear()
            SalesApp.glassColorList.addAll(glassColorBean.data)
        }
    }
    if (tag == ApiContants.GetProfileColor) {
        val profileColorBean = apiClient.getConvertIntoModel<ProfileColorBean>(
            jsonElement.toString(),
            ProfileColorBean::class.java
        )
        if (profileColorBean.error==false) {
            SalesApp.profileColorList.clear()
            SalesApp.profileColorList.addAll(profileColorBean.data)
        }

    }
    if (tag == ApiContants.GetGlassThickness) {
        val glassThicknessBean = apiClient.getConvertIntoModel<GlassThicknessBean>(
            jsonElement.toString(),
            GlassThicknessBean::class.java
        )
        if (glassThicknessBean.error==false){
            SalesApp.glassThicknessList.clear()
            SalesApp.glassThicknessList.addAll(glassThicknessBean.data)
        }

    }
    if (tag == ApiContants.GetProfileName) {
        val profileNameBean = apiClient.getConvertIntoModel<ProfileNameBean>(
            jsonElement.toString(),
            ProfileNameBean::class.java
        )
        if (profileNameBean.error==false) {
            SalesApp.profileNameList.clear()
            SalesApp.profileNameList.addAll(profileNameBean.data)
        }
    }
}catch (e:Exception){
    Log.d("error>>",e.localizedMessage)
}

    }

    override fun failure(tag: String?, errorMessage: String) {
        apiClient.progressView.hideLoader()
        Utility.showSnackBar(requireActivity(), errorMessage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun handleRcDashboard(data: DashboardBean.Data) {
        binding.rcDashboard.layoutManager = GridLayoutManager(requireContext(), 2)
        var mAdapter = DashboardAdapter(requireActivity(), getMenus(data), object :
            RvClickListner {
            override fun clickPos(pos: Int) {

            }
        })
        binding.rcDashboard.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false

    }
    private fun getMenus(data: DashboardBean.Data): ArrayList<MenuModelBean> {
        var menuList = ArrayList<MenuModelBean>()
        //NEW LEAD

        menuList.add(MenuModelBean(1, "Add Lead", "", R.drawable.ic_dashbord))
        menuList.add(MenuModelBean(2, "New Lead",newLeads, R.drawable.ic_dashbord))
        menuList.add(
            MenuModelBean(
                3,
                "PENDING",
                pending_leads,
                R.drawable.ic_dashbord
            )
        )
        menuList.add(
            MenuModelBean(
                4,
                "Processing",
                processing_leads,
                R.drawable.ic_dashbord
            )
        )
        menuList.add(
            MenuModelBean(
                5,
                "CALL SCHEDULED",
                call_scheduled,
                R.drawable.ic_dashbord
            )
        )
        menuList.add(
            MenuModelBean(
                6,
                "VISIT SCHEDULED",
                visit_scheduled,
                R.drawable.ic_dashbord
            )
        )
        menuList.add(
            MenuModelBean(
                7,
                "VISIT DONE",
                visit_done,
                R.drawable.ic_dashbord
            )
        )
        menuList.add(MenuModelBean(8, "Lost", visit_done, R.drawable.ic_dashbord))


        //   menuList.add(MenuModelBean(1, "All Lead", data.newLeads.toString(), R.drawable.ic_dashbord))


        /* menuList.add(MenuModelBean(0, "Total Lead", data.totalLeads.toString(), R.drawable.ic_dashbord))
         menuList.add(MenuModelBean(4, "Interested Lead", data.interestedLeads.toString(), R.drawable.ic_dashbord))
         menuList.add(MenuModelBean(6, "SM New Lead", data.smNewLeads.toString(), R.drawable.ic_dashbord))
         menuList.add(MenuModelBean(9, "CONVERTED", data.convertedLeads.toString(), R.drawable.ic_dashbord))
       //  menuList.add(MenuModelBean(10, "Lost Leads", data.lost_leads.toString(), R.drawable.ic_dashbord))
         menuList.add(MenuModelBean(11, "Completed", data.completed.toString(), R.drawable.ic_dashbord))
         menuList.add(MenuModelBean(12, "Cancelled", data.cancelled.toString(), R.drawable.ic_dashbord))
         menuList.add(MenuModelBean(13, "Booked", data.booked.toString(), R.drawable.ic_dashbord))
         menuList.add(MenuModelBean(14, "Partial", data.partial.toString(), R.drawable.ic_dashbord))
         menuList.add(MenuModelBean(15, "Executive Count", data.executiveCount.toString(), R.drawable.ic_dashbord))
         menuList.add(MenuModelBean(16, "Monthly Sale", data.monthlySale.toString(), R.drawable.ic_dashbord))
         menuList.add(MenuModelBean(17, "Yearly Sale", data.yearlySale.toString(), R.drawable.ic_dashbord))
 */

        return menuList
    }

    fun callPGURL(url: String) {

        Log.d("weburl", url)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("com.android.chrome")
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            // Chrome browser presumably not installed so allow user to choose instead
            intent.setPackage(null)
            startActivity(intent)
        }
    }
}