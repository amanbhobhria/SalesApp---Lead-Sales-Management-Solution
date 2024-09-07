package com.data.atul_auto_sales.Activity

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.data.atul_auto_sales.Adapter.ProductListAdapter
import com.data.atul_auto_sales.ApiHelper.ApiController
import com.data.atul_auto_sales.ApiHelper.ApiResponseListner
import com.data.atul_auto_sales.Model.*
import com.data.atul_auto_sales.R
import com.data.atul_auto_sales.Utills.*
import com.data.atul_auto_sales.Utills.GeneralUtilities.getPath
import com.data.atul_auto_sales.databinding.ActivityAddLeadBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants
import okhttp3.MultipartBody
import java.io.File


class AddLeadActivity : AppCompatActivity(), ApiResponseListner,
    GoogleApiClient.OnConnectionFailedListener,
    ConnectivityListener.ConnectivityReceiverListener {
    private lateinit var binding: ActivityAddLeadBinding
    private lateinit var apiClient: ApiController
    var myReceiver: ConnectivityListener? = null
    var activity: Activity = this
    var projectType = "Commercial"
    var categoryName = ""
    var subCategoryName = ""
    var productName = ""

    var customProdCatName =""
    var customProductCatID = 0
    var customSubCatName = ""


    var installerName = ""
    var dealerName = ""
    var propertyStageName = ""
    var stateName = ""
    var cityName = ""
    var projectCatID = 0
    var productCategoryID = 0
    var projectSubCatID = 0
    var clientID = 0
    var productID = 0


    val PERMISSION_CODE = 12345
    val CAMERA_PERMISSION_CODE1 = 123
    var SELECT_PICTURES1 = 1
    var file2: File? = null
    var type = arrayOf("Residential", "Commercial")
    val list: MutableList<MultipleProductBean> = ArrayList()
    val cutomProdList: MutableList<MultipleProductBean> = ArrayList()
    val cutomProdImgList: MutableList<CustProdImgBean> = ArrayList()
    val cutomFinalList: MutableList<Any> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_lead)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        myReceiver = ConnectivityListener()

        binding.igToolbar.tvTitle.text = "Add Lead"
        binding.igToolbar.ivMenu.setImageDrawable(resources.getDrawable(R.drawable.ic_back_black))
        binding.igToolbar.ivMenu.setOnClickListener { finish() }
        binding.igToolbar.ivLogout.visibility = View.GONE
        binding.igToolbar.switchDayStart.visibility = View.GONE
      //  requestPermission()
        setSourceData()
     //   setProductCat()
     //   setCustomProdCat()
        setState()
        setArchitect()
        setDealer()
     //   setInstaller()
        setPropertyStage()
        setClient()
        typeMode()
        apiCatory(projectType)

        binding.stateDealer.isEnabled = false

        /*   if (checkBox.isChecked()){
            tvAcceptPolicy.setEnabled(true);
            tvAcceptPolicy.setAlpha(1f);
        }else {

        }*/
        binding.btnAadharFront.setOnClickListener {
            openCameraDialog(SELECT_PICTURES1,CAMERA_PERMISSION_CODE1)

        }
        binding.dealerCheck.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (binding.dealerCheck.isChecked()) {
                binding.stateDealer.isEnabled = true
            } else {
                binding.stateDealer.isEnabled = false
            }
        })

        binding.btnAddProduct.setOnClickListener {
            if (TextUtils.isEmpty(categoryName)) {
                Utility.showSnackBar(this, "Please select category")
            } else if (TextUtils.isEmpty(subCategoryName)) {
                Utility.showSnackBar(this, "Please select subcategory")
            } else if (TextUtils.isEmpty(productID.toString())) {
                Utility.showSnackBar(this, "Please select product")
            } else if (TextUtils.isEmpty(binding.editQty.text.toString())) {
                Utility.showSnackBar(this, "Enter qty")
            } else {
                addDataList()
            }

        }
        binding.btnAddCustProduct.setOnClickListener {
            if (TextUtils.isEmpty(customProdCatName)) {
                Utility.showSnackBar(this, "Please Select Custom Category")
            } else if (TextUtils.isEmpty(customSubCatName)) {
                Utility.showSnackBar(this, "Please Select Custom Subcategory")
            } else if (TextUtils.isEmpty(binding.editCustmProdName.text.toString())) {
                Utility.showSnackBar(this, "Enter Custom Product Name")
            } else if (TextUtils.isEmpty(binding.editCustmQty.text.toString())) {
                Utility.showSnackBar(this, "Enter qty")
            } else if (file2==null) {
                Utility.showSnackBar(this, "Please Select Image")
            } else {
                addDataCustmProdtList()
            }

        }
        //  loginViewModel = ViewModelProvider(this, ViewModelFactory(this)).get(LoginViewModel::class.java )

//binding.loginViewModel=loginViewModel
        //       binding.lifecycleOwner=this

        binding.btnSubmit.setOnClickListener {
            //   Utility.showSnackBar(this, "Work in progress")
            apiInsertLead()
            /* if (TextUtils.isEmpty(binding.editGSTNo.text.toString())) {
                 Utility.showSnackBar(this, "Please enter Gst Number")
             } else if (TextUtils.isEmpty(binding.editRemark.text.toString())) {
                 Utility.showSnackBar(this, "Please Enter remark")
             } else if (TextUtils.isEmpty(binding.stateselector.text.toString())) {
                 Utility.showSnackBar(this, "Please select state")
             } else if (TextUtils.isEmpty(binding.cityselector.text.toString())) {
                 Utility.showSnackBar(this, "Please select city")
             } else {
                 //    loginViewModel.apiCallLogin()
                 apiInsertLead()
             }*/
        }
    }

    fun typeMode() {
        binding.radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                if (checkedId == R.id.rbResidential) {
                    projectType = "Residential"
                    apiCatory(projectType)
                } else if (checkedId == R.id.rbCommercial) {
                    projectType = "Commercial"
                    apiCatory(projectType)
                } else if (checkedId == R.id.rbOthers) {
                    projectType = "Others"
                    apiCatory(projectType)
                }
            }
        })
    }

    fun apiInsertLead() {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(activity, this)

       /* val params = Utility.getParmMap()
        params["source"] = binding.stateSource.text.toString()
        params["installer"] = binding.stateInstaller.text.toString()
        params["architect"] = binding.stateArchitect.text.toString()
        params["dealer"] = binding.stateDealer.text.toString()
        params["project_type"] = projectType
        params["project_category_id"] = projectCatID.toString()
        params["project_sub_category_id"] = projectSubCatID.toString()
        params["property_stage"] = binding.statePropertyStage.text.toString()
        params["client_id"] = clientID.toString()
        params["remarks"] = binding.editRemark.text.toString()
        params["state"] = binding.stateselector.text.toString()
        params["city"] = binding.cityselector.text.toString()
        params["prod_list"] = Gson().toJson(list)
        params["custom_prod_list"] = Gson().toJson(cutomProdList)
        Log.d("requestParms", Gson().toJson(params))*/

        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        builder.addFormDataPart("source", binding.stateSource.text.toString())
       // builder.addFormDataPart("installer", "")
        builder.addFormDataPart("architect", binding.stateArchitect.text.toString())
        builder.addFormDataPart("dealer", binding.stateDealer.text.toString())
        builder.addFormDataPart("project_type", projectType)
        builder.addFormDataPart("project_category_id", projectCatID.toString())
        builder.addFormDataPart("project_sub_category_id", projectSubCatID.toString())
        builder.addFormDataPart("property_stage", binding.statePropertyStage.text.toString())
        builder.addFormDataPart("client_id", clientID.toString())
        builder.addFormDataPart("state", binding.stateselector.text.toString())
        builder.addFormDataPart("city", binding.cityselector.text.toString())
        builder.addFormDataPart("remarks", binding.editRemark.text.toString())

      //  val filesToUpload = arrayOfNulls<File>(cutomProdImgList.size)
       /* for (i in 0 until cutomProdImgList.size) {
            builder.addFormDataPart("files", "img[" + i + "]"+System.currentTimeMillis(),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), cutomProdImgList.get(i).image))
        }*/

        Log.d("requestParms", Gson().toJson(builder))
        apiClient.progressView.showLoader()
        apiClient.makeCallMultipart(ApiContants.getInsertLead, builder.build())

    }

    fun apiCity(stateName: String) {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(activity, this)
        val params = Utility.getParmMap()
        params["state"] = stateName
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getCity, params)

    }

    fun apiGetProduct(cat: String, subCat: String) {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(activity, this)
        val params = Utility.getParmMap()
        params["category"] = cat
        params["subcategory"] = subCat
        //   apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getProduct, params)

    }

    fun apiProductSubCat(cat: Int) {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(activity, this)
        val params = Utility.getParmMap()
        params["catg_id"] = cat.toString()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getProductSubCategory, params)

    }

    fun apiCustProdSubCat(cat: Int) {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(activity, this)
        val params = Utility.getParmMap()
        params["catg_id"] = cat.toString()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getCustomProdSubCat, params)

    }

    fun apiCatory(type: String) {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(activity, this)
        val params = Utility.getParmMap()
        params["type"] = type
        //    apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getProjectCategory, params)
    }

    fun apiSubCatory(subCatId: Int) {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(activity, this)
        val params = Utility.getParmMap()
        params["catg_id"] = subCatId.toString()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getProjectSubCategory, params)
    }

    override fun success(tag: String?, jsonElement: JsonElement) {
        try {
            apiClient.progressView.hideLoader()

            if (tag == ApiContants.getInsertLead) {
                val baseResponseBean = apiClient.getConvertIntoModel<BaseResponseBean>(
                    jsonElement.toString(),
                    BaseResponseBean::class.java
                )
                Toast.makeText(this, baseResponseBean.msg, Toast.LENGTH_SHORT).show()

                finish()
            }
            if (tag == ApiContants.getProjectCategory) {
                val projectCategoryBean = apiClient.getConvertIntoModel<ProjectCategoryBean>(
                    jsonElement.toString(),
                    ProjectCategoryBean::class.java
                )
                if (projectCategoryBean.error==false){
                    val state = arrayOfNulls<String>(projectCategoryBean.data.size)
                    for (i in projectCategoryBean.data.indices) {
                        //Storing names to string array
                        state[i] = projectCategoryBean.data.get(i).name
                    }

                    binding.stateCategory.setAdapter(
                        ArrayAdapter(
                            this@AddLeadActivity,
                            android.R.layout.simple_list_item_1, state
                        )
                    )

                    binding.stateCategory.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
                        var categoryName = projectCategoryBean.data.get(position).name
                        projectCatID = projectCategoryBean.data.get(position).id
                        Log.d("StateID", "" + categoryName)
                        binding.stateCategory.setText(categoryName)
                        Toast.makeText(
                            applicationContext,
                            binding.stateCategory.getText().toString(),
                            Toast.LENGTH_SHORT
                        ).show()

                        apiCatory(projectType)
                        apiSubCatory(projectCatID)

                    })
                }
            }
            if (tag == ApiContants.getProjectSubCategory) {
                val subCategoryBean = apiClient.getConvertIntoModel<SubCategoryBean>(
                    jsonElement.toString(),
                    SubCategoryBean::class.java
                )
                if (subCategoryBean.error==false){
                    val state = arrayOfNulls<String>(subCategoryBean.data.size)
                    for (i in subCategoryBean.data.indices) {
                        //Storing names to string array
                        state[i] = subCategoryBean.data.get(i).name
                    }

                    binding.stateSubCategory.setAdapter(
                        ArrayAdapter(
                            this@AddLeadActivity,
                            android.R.layout.simple_list_item_1, state
                        )
                    )
                    binding.stateSubCategory.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
                        var subCategoryName = subCategoryBean.data.get(position).name
                        projectSubCatID = subCategoryBean.data.get(position).id

                        Log.d("StateID", "" + subCategoryName)
                        binding.stateSubCategory.setText(subCategoryName)
                        Toast.makeText(
                            applicationContext,
                            binding.stateSubCategory.getText().toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                }

                //  apiSubCatory(projectCatID)

            }
            if (tag == ApiContants.getCity) {
                val cityBean = apiClient.getConvertIntoModel<CityBean>(
                    jsonElement.toString(),
                    CityBean::class.java
                )
                if (cityBean.error==false){
                    val state = arrayOfNulls<String>(cityBean.data.size)
                    for (i in cityBean.data.indices) {
                        //Storing names to string array
                        state[i] = cityBean.data.get(i).city
                    }
                    val adapte1: ArrayAdapter<String?>
                    adapte1 = ArrayAdapter(
                        this@AddLeadActivity,
                        android.R.layout.simple_list_item_1,
                        state
                    )
                    binding.cityselector.setAdapter(adapte1)
                    binding.cityselector.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
                        cityName = cityBean.data.get(position).city
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


            /*    if (tag == ApiContants.getProductSubCategory) {
                   val subProductCatBean = apiClient.getConvertIntoModel<SubProductCatBean>(
                       jsonElement.toString(),
                       SubProductCatBean::class.java
                   )
                   val state = arrayOfNulls<String>(subProductCatBean.data.size)
                   for (i in subProductCatBean.data.indices) {
                       //Storing names to string array
                       state[i] = subProductCatBean.data.get(i).name
                   }
                   val adapte1: ArrayAdapter<String?>
                   adapte1 = ArrayAdapter(
                       this@AddLeadActivity,
                       android.R.layout.simple_list_item_1,
                       state
                   )
                   binding.productSubCategory.setAdapter(adapte1)
                   binding.productSubCategory.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
                       subCategoryName = subProductCatBean.data.get(position).name

                       Log.d("StateID", "" + subCategoryName)
                       binding.productSubCategory.setText(subCategoryName)
                       Toast.makeText(
                           applicationContext,
                           binding.productSubCategory.getText().toString(),
                           Toast.LENGTH_SHORT
                       ).show()
                       apiGetProduct(categoryName, subCategoryName)
                       apiProductSubCat(productCategoryID)
                   })

               }
                 if (tag == ApiContants.getCustomProdSubCat) {
                      val customSubCatBean = apiClient.getConvertIntoModel<CustomSubCatBean>(
                          jsonElement.toString(),
                          CustomSubCatBean::class.java
                      )
                      val state = arrayOfNulls<String>(customSubCatBean.data.size)
                      for (i in customSubCatBean.data.indices) {
                          //Storing names to string array
                          state[i] = customSubCatBean.data.get(i).name
                      }
                      val adapte1: ArrayAdapter<String?>
                      adapte1 = ArrayAdapter(
                          this@AddLeadActivity,
                          android.R.layout.simple_list_item_1,
                          state
                      )
                      binding.CustSubCatProduct.setAdapter(adapte1)
                      binding.CustSubCatProduct.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
                          customSubCatName = customSubCatBean.data.get(position).name

                          Log.d("StateID", "" + customSubCatName)
                          binding.CustSubCatProduct.setText(customSubCatName)
                          Toast.makeText(
                              applicationContext,
                              binding.CustSubCatProduct.getText().toString(),
                              Toast.LENGTH_SHORT
                          ).show()

                          apiCustProdSubCat(customProductCatID)
                      })

                  }
                  if (tag == ApiContants.getProduct) {
                      val productBean = apiClient.getConvertIntoModel<GetProductBean>(
                          jsonElement.toString(),
                          GetProductBean::class.java
                      )
                      val state = arrayOfNulls<String>(productBean.data.size)
                      for (i in productBean.data.indices) {
                          //Storing names to string array
                          state[i] = productBean.data.get(i).name
                      }
                      val adapte1: ArrayAdapter<String?>
                      adapte1 = ArrayAdapter(
                          this@AddLeadActivity,
                          android.R.layout.simple_list_item_1,
                          state
                      )
                      binding.stateProduct.setAdapter(adapte1)
                      binding.stateProduct.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
                          productName = productBean.data.get(position).name
                          productID = productBean.data.get(position).id

                          Log.d("StateID", "" + productID + "")
                          binding.stateProduct.setText(productName)
                          Toast.makeText(
                              applicationContext,
                              binding.stateProduct.getText().toString(),
                              Toast.LENGTH_SHORT
                          ).show()

                          apiGetProduct(categoryName, subCategoryName)
                      })
                  }*/
        }catch (e:Exception){
            Log.d("error>>",e.localizedMessage)
        }


    }

    override fun failure(tag: String?, errorMessage: String) {
        apiClient.progressView.hideLoader()
        Utility.showSnackBar(activity, errorMessage)
    }

    fun setSourceData() {
        val state = arrayOfNulls<String>(SalesApp.sourceList.size)
        for (i in SalesApp.sourceList.indices) {
            state[i] = SalesApp.sourceList.get(i).name
        }

        binding.stateSource.setAdapter(
            ArrayAdapter(
                this@AddLeadActivity,
                android.R.layout.simple_list_item_1, state
            )
        )

        binding.stateSource.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            var sourceName = SalesApp.sourceList.get(position).name

            Log.d("StateID", "" + sourceName)
            binding.stateSource.setText(sourceName)
            Toast.makeText(
                applicationContext,
                binding.stateSource.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()
            setSourceData()

        })
    }

    fun setState() {
        val state = arrayOfNulls<String>(SalesApp.stateList.size)
        for (i in SalesApp.stateList.indices) {
            state[i] = SalesApp.stateList.get(i).state
        }

        binding.stateselector.setAdapter(
            ArrayAdapter(
                this@AddLeadActivity,
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

    fun setInstaller() {
        val state = arrayOfNulls<String>(SalesApp.installerList.size)
        for (i in SalesApp.installerList.indices) {
            state[i] = SalesApp.installerList.get(i).name
        }

        binding.stateInstaller.setAdapter(
            ArrayAdapter(
                this@AddLeadActivity,
                android.R.layout.simple_list_item_1, state
            )
        )

        binding.stateInstaller.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            installerName = SalesApp.installerList.get(position).name

            Log.d("StateID", "" + installerName)
            binding.stateInstaller.setText(installerName)
            Toast.makeText(
                applicationContext,
                binding.stateInstaller.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()
            setInstaller()
        })
    }

    fun setArchitect() {
        val state = arrayOfNulls<String>(SalesApp.architectList.size)
        for (i in SalesApp.architectList.indices) {
            state[i] = SalesApp.architectList.get(i).name
        }

        binding.stateArchitect.setAdapter(
            ArrayAdapter(
                this@AddLeadActivity,
                android.R.layout.simple_list_item_1, state
            )
        )

        binding.stateArchitect.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            installerName = SalesApp.architectList.get(position).name

            Log.d("StateID", "" + installerName)
            binding.stateArchitect.setText(installerName)
            Toast.makeText(
                applicationContext,
                binding.stateArchitect.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()

            setArchitect()
        })
    }

    fun setDealer() {
        val state = arrayOfNulls<String>(SalesApp.dealerList.size)
        for (i in SalesApp.dealerList.indices) {
            state[i] = SalesApp.dealerList.get(i).name
        }

        binding.stateDealer.setAdapter(
            ArrayAdapter(
                this@AddLeadActivity,
                android.R.layout.simple_list_item_1, state
            )
        )

        binding.stateDealer.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            dealerName = SalesApp.dealerList.get(position).name

            Log.d("StateID", "" + dealerName)
            binding.stateDealer.setText(dealerName)
            Toast.makeText(
                applicationContext,
                binding.stateDealer.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()

            setDealer()
        })
    }

    fun setPropertyStage() {
        val state = arrayOfNulls<String>(SalesApp.propertyStageList.size)
        for (i in SalesApp.propertyStageList.indices) {
            state[i] = SalesApp.propertyStageList.get(i).title
        }

        binding.statePropertyStage.setAdapter(
            ArrayAdapter(
                this@AddLeadActivity,
                android.R.layout.simple_list_item_1, state
            )
        )

        binding.statePropertyStage.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            propertyStageName = SalesApp.propertyStageList.get(position).title

            Log.d("StateID", "" + propertyStageName)
            binding.statePropertyStage.setText(propertyStageName)
            Toast.makeText(
                applicationContext,
                binding.statePropertyStage.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()

            setPropertyStage()
        })
    }

    fun setClient() {
        val state = arrayOfNulls<String>(SalesApp.clientList.size)
        for (i in SalesApp.clientList.indices) {
            state[i] = SalesApp.clientList.get(i).name
        }

        binding.stateClient.setAdapter(
            ArrayAdapter(
                this@AddLeadActivity,
                android.R.layout.simple_list_item_1, state
            )
        )
        binding.stateClient.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            var clientName = SalesApp.clientList.get(position).name
            clientID = SalesApp.clientList.get(position).id

            Log.d("StateID", "" + clientName)
            binding.stateClient.setText(clientName)
            Toast.makeText(
                applicationContext,
                binding.stateClient.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()

            setClient()
        })
    }

    fun setProductCat() {
        val state = arrayOfNulls<String>(SalesApp.productCatList.size)
        for (i in SalesApp.productCatList.indices) {
            state[i] = SalesApp.productCatList.get(i).name
        }

        binding.stateProductCategory.setAdapter(
            ArrayAdapter(
                this@AddLeadActivity,
                android.R.layout.simple_list_item_1, state
            )
        )

        binding.stateProductCategory.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            categoryName = SalesApp.productCatList.get(position).name
            productCategoryID = SalesApp.productCatList.get(position).id
            Log.d("StateID", "" + categoryName)
            binding.stateProductCategory.setText(categoryName)
            Toast.makeText(
                applicationContext,
                binding.stateProductCategory.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()
           // apiProductSubCat(productCategoryID)
            setProductCat()
        })
    }

    fun setCustomProdCat() {
        val state = arrayOfNulls<String>(SalesApp.prodCustomCatList.size)
        for (i in SalesApp.prodCustomCatList.indices) {
            state[i] = SalesApp.prodCustomCatList.get(i).name
        }

        binding.stateCustProdCat.setAdapter(
            ArrayAdapter(
                this@AddLeadActivity,
                android.R.layout.simple_list_item_1, state
            )
        )

        binding.stateCustProdCat.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            customProdCatName = SalesApp.prodCustomCatList.get(position).name

            customProductCatID = SalesApp.prodCustomCatList.get(position).id
            Log.d("StateID", "" + customProdCatName)
            binding.stateCustProdCat.setText(customProdCatName)
            Toast.makeText(
                applicationContext,
                binding.stateCustProdCat.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()
          //  apiCustProdSubCat(customProductCatID)
            setCustomProdCat()
        })
    }

    fun addDataList() {
        val params = Utility.getParmMap()
        params["catName"] = categoryName
        params["subCatName"] = subCategoryName
        params["product_id"] = productID.toString()
        params["qty"] = binding.editQty.text.toString()
        val multiple = MultipleProductBean(
            categoryName,
            subCategoryName,
            productName,
            productID.toString(),
            binding.editQty.text.toString(),""
        )
        list.add(multiple)
        Toast.makeText(this@AddLeadActivity, "Product Added Successfully", Toast.LENGTH_SHORT)
            .show()
        binding.editQty.text?.clear()
        Log.d("xzczxcxz", Gson().toJson(list))
        handleProductList(list)

    }

    fun addDataCustmProdtList() {
        val params = Utility.getParmMap()
        params["catName"] = customProdCatName
        params["subCatName"] = customSubCatName
        params["product_name"] =binding.editCustmProdName.text.toString()
        params["qty"] = binding.editCustmQty.text.toString()
        params["file"] = ""
        val multiple = MultipleProductBean(
            customProdCatName,
            customSubCatName,
            binding.editCustmProdName.text.toString(),
            0.toString(),
            binding.editCustmQty.text.toString(),file2?.absolutePath!!
        )

        cutomProdList.add(multiple)
        val custImg = CustProdImgBean(file2?.absolutePath!!)

        cutomProdImgList.add(custImg)

        Toast.makeText(this@AddLeadActivity, "Custom Product Added Successfully", Toast.LENGTH_SHORT)
            .show()
        binding.editCustmQty.text?.clear()

        binding.btnAadharFront.setImageResource(R.drawable.add_file)
        Log.d("xzczxcxz", Gson().toJson(cutomProdList))
        Log.d("xzczxcxz", Gson().toJson(cutomProdImgList))
        cutomFinalList.addAll(cutomProdList)
        cutomFinalList.addAll(cutomProdImgList)
      //  Log.d("saasdf",Gson().toJson(cutomFinalList))
        handleCustomProdList(cutomProdList)

    }

    fun handleProductList(data: MutableList<MultipleProductBean>) {
        binding.rcAllProduct.layoutManager = LinearLayoutManager(this)
        var mAdapter = ProductListAdapter(this,"MultipleProduct", data, object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {

            }
        })
        binding.rcAllProduct.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false

    }

    fun handleCustomProdList(
        data: MutableList<MultipleProductBean>,
    ) {
        binding.rcAllCustmProduct.layoutManager = LinearLayoutManager(this)
        var mAdapter = ProductListAdapter(this,"CustomProduct", data, object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {

            }
        })
        binding.rcAllCustmProduct.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false

    }

    fun ClickPicCamera(CAMERA_PERMISSION_CODE: Int) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_PERMISSION_CODE)
    }

    fun requestPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES),
            PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==PERMISSION_CODE){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED && grantResults[0]== PackageManager.PERMISSION_GRANTED && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission is Granted",Toast.LENGTH_SHORT).show()

            }
            else{
                requestPermission()
            }
        }
    }
    private fun uploadImage(SELECT_PICTURES: Int) {
        val selectImage = Intent()
        selectImage.type = "image/*"
        selectImage.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                selectImage,
                "Select Picture"
            ),
            SELECT_PICTURES
        )
    }

    fun openCameraDialog(SELECT_PICTURES: Int,CAMERA_PERMISSION_CODE: Int) {
        val dialog: Dialog = GeneralUtilities.openBootmSheetDailog(R.layout.dialog_camera, R.style.AppBottomSheetDialogTheme,
            this
        )
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val llInternalPhoto = dialog.findViewById<View>(R.id.llInternalPhoto) as LinearLayout
        val llClickPhoto = dialog.findViewById<View>(R.id.llClickPhoto) as LinearLayout

        llInternalPhoto.setOnClickListener { dialog.dismiss()
            requestPermission()
            uploadImage(SELECT_PICTURES)
        }

        llClickPhoto.setOnClickListener { dialog.dismiss()
            requestPermission()
            ClickPicCamera(CAMERA_PERMISSION_CODE)

        }
        ivClose.setOnClickListener { dialog.dismiss() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK){

            if (requestCode == SELECT_PICTURES1) {
                try {
                    val uri = data!!.data
                    val picturePath: String = getPath(
                        applicationContext, uri)
                    Log.d("Picture Path", picturePath)
                    file2 = File(picturePath)
                    //  selectedImageUri2 = uri
                    //  attatchmentStr2 = file2!!.absolutePath
                    val myBitmap = BitmapFactory.decodeFile(file2!!.absolutePath)
                    binding.btnAadharFront.setImageBitmap(myBitmap)
                    //Toast.makeText(getContext(), ""+picturePath, Toast.LENGTH_SHORT).show();
                } catch (e: java.lang.Exception) {

                    //    /storage/emulated/0/Pictures/Title (11).jpg
                    Log.e("Path Error", e.toString())
                    Toast.makeText(applicationContext, "" + e, Toast.LENGTH_SHORT).show()
                }
            }


            if (requestCode == CAMERA_PERMISSION_CODE1) {
                try {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    binding.btnAadharFront.setImageBitmap(imageBitmap)
                    val tempUri =GeneralUtilities.getImageUri(applicationContext, imageBitmap)
                    file2= File(GeneralUtilities.getRealPathFromURII(this,tempUri))

                    Log.e("Path", file2.toString())

                    //Toast.makeText(getContext(), ""+picturePath, Toast.LENGTH_SHORT).show();
                } catch (e: java.lang.Exception) {
                    Log.e("Path Error", e.toString())
                    Toast.makeText(applicationContext, "" + e, Toast.LENGTH_SHORT).show()
                }
            }


        }
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
