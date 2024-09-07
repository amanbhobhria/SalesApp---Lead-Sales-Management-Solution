package com.data.atul_auto_sales.ApiHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.data.atul_auto_sales.Model.BaseResponseBean;
import com.data.atul_auto_sales.Utills.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import com.stpl.antimatter.Network.NoConnectivityException;
import com.stpl.antimatter.Utils.ApiContants;


import java.util.Map;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class ApiController {
    private final ApiService apiClient;
    Context context;
    ApiResponseListner responseListner;
    public ProgressView progressView;

    public ApiController(Context context, ApiResponseListner responseListner) {
        this.context = context;
        this.responseListner = responseListner;
        apiClient = Utility.Companion.getApiClient(context);
        progressView = new ProgressView(context);
    }


    @SuppressLint("CheckResult")
    public void getApiCall(final String tag, String parm) {
        Single<JsonElement> call = apiClient.getCall(ApiContants.BaseUrl + tag + parm);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<JsonElement>() {

                    @Override
                    public void onSuccess(JsonElement jsonElement) {
                        BaseResponseBean baseResponse = getConvertIntoModel(jsonElement.toString(), BaseResponseBean.class);

                        if (baseResponse.getError()==false) {
                            responseListner.success(tag, jsonElement);
                        } else {
                            responseListner.failure(tag, baseResponse.getMsg());
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        if (t instanceof NoConnectivityException) {
                            responseListner.failure(tag, ApiContants.NoInternetConnection);
                        } else {
                            responseListner.failure(tag, t.getMessage());
                        }

                    }
                });

    }


    @SuppressLint("CheckResult")
    public void getOtpApiCall(final String tag, String number, String otpMsg) {

        /*String url="http://103.129.97.36/index.php/smsapi/httpapi/?uname=reinventtechno&password=2468&sender=ANTMTR&receiver="
                +number+"&route=TA&msgtype=1&sms="+otpMsg;*/

        String url = "http://login.bharatdeal.com/rest/services/sendSMS/sendGroupSms?" +
                "AUTH_KEY=cb2e97c56a89498846f48b9d51ccd3&message=" + otpMsg + "&senderId=DEMOOS&routeId=1&" +
                "mobileNos=" + number + "&smsContentType=english";

        Single<JsonElement> call = apiClient.getOtpApi(url);

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<JsonElement>() {

                    @Override
                    public void onSuccess(JsonElement jsonElement) {

                        if (jsonElement != null) {
                            responseListner.success(tag, jsonElement);
                        } else {
                            responseListner.failure(tag, jsonElement.toString());
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        if (t instanceof NoConnectivityException) {
                            responseListner.failure(tag, ApiContants.NoInternetConnection);
                        } else {
                            responseListner.failure(tag, t.getMessage());
                        }

                    }
                });
    }


    @SuppressLint("CheckResult")
    public void getApiPostCall(final String tag, Map<String, String> parm) {

        Single<JsonElement> call = apiClient.postCall(ApiContants.BaseUrl + tag, parm);

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<JsonElement>() {
                    @Override
                    public void onSuccess(JsonElement jsonElement) {

                        Log.e("onSuccess: ", jsonElement.toString());
                        BaseResponseBean baseResponse = getConvertIntoModel(jsonElement.toString(), BaseResponseBean.class);

                        if (baseResponse.getError()==false) {
                            responseListner.success(tag, jsonElement);
                        } else {
                            responseListner.failure(tag, baseResponse.getMsg());
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        if (t instanceof NoConnectivityException) {
                            responseListner.failure(tag, ApiContants.NoInternetConnection);
                        } else {
                            responseListner.failure(tag, t.getMessage());
                        }

                    }
                });

    }

    @SuppressLint("CheckResult")
    public void makeCallMultipart(final String tag, MultipartBody parm) {


        Single<JsonElement> call = apiClient.makeMutlipartCall(ApiContants.BaseUrl + tag, parm);

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<JsonElement>() {

                    @Override
                    public void onSuccess(JsonElement jsonElement) {

                        Log.e("onSuccess: ", jsonElement.toString());
                        BaseResponseBean baseResponse = getConvertIntoModel(jsonElement.toString(), BaseResponseBean.class);

                        if (baseResponse.getError()==false) {
                            responseListner.success(tag, jsonElement);
                        } else {
                            responseListner.failure(tag, baseResponse.getMsg());
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        if (t instanceof NoConnectivityException) {
                            responseListner.failure(tag, ApiContants.NoInternetConnection);
                        } else {
                            responseListner.failure(tag, t.getMessage());
                        }

                    }
                });

    }

    public <T> T getConvertIntoModel(String gsonStr, Class<T> modelClass) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return gson.fromJson(gsonStr, modelClass);
    }
}
