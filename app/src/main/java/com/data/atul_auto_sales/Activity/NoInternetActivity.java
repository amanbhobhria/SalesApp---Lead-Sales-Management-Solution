package com.data.atul_auto_sales.Activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;


import com.data.atul_auto_sales.R;
import com.data.atul_auto_sales.Utills.ConnectivityListener;
import com.data.atul_auto_sales.Utills.GeneralUtilities;
import com.data.atul_auto_sales.Utills.SalesApp;

import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;


public class NoInternetActivity extends AppCompatActivity implements ConnectivityListener.ConnectivityReceiverListener {

    Context context;
    ConnectivityListener myReceiver;


    Button button;
    LinearLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(com.data.atul_auto_sales.R.layout.activity_no);

        container=findViewById(R.id.container);
        button=findViewById(R.id.button);

        myReceiver = new ConnectivityListener();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkInterfaceAvailable(context)) {
                    ConnectivityListener.connectivityReceiverListener.onNetworkConnectionChange(true);
                } else {
                    GeneralUtilities.showErrorSnackBar(context, container, "No internet connection");
                }
              /*  final android.os.Handler handler=new android.os.Handler();
                new Thread(new Runnable() {
                    @Override
                    public void run() {


                    }
                }).start();*/

            }
        });

    }

    private boolean isAbleToConnect(String url, int timeout) {
        try {
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(timeout);
            connection.connect();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        // GeneralUtilities.intent=null;
        super.onBackPressed();
    }

    @Override
    protected void onResume() {

        GeneralUtilities.registerBroadCastReceiver(context, myReceiver);
        SalesApp.setConnectivityListener(this);

        super.onResume();
    }



    @Override
    public void onNetworkConnectionChange(boolean isconnected) {

        if (isconnected) {
            GeneralUtilities.intent = null;
            finish();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        GeneralUtilities.intent = null;

        GeneralUtilities.unregisterBroadCastReceiver(context, myReceiver);

    }

    private boolean isNetworkInterfaceAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

}
