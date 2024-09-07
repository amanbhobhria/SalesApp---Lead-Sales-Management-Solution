package com.data.atul_auto_sales.Utills;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



import java.net.URL;
import java.net.URLConnection;

public class ConnectivityListener extends BroadcastReceiver {
    public static ConnectivityReceiverListener connectivityReceiverListener;

    public ConnectivityListener() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            checkConnectivity(context);
        }
    }

    private void checkConnectivity(final Context context) {
      /*  if(!isNetworkInterfaceAvailable(context))
        {
          //  Toast.makeText(context,"You are Offline",Toast.LENGTH_SHORT).show();
            if(connectivityReceiverListener!=null)
            {
                connectivityReceiverListener.onNetworkConnectionChange(false);
            }
            return;
        }else
        {
            connectivityReceiverListener.onNetworkConnectionChange(true);
        }*/
        if (isNetworkInterfaceAvailable(context)) {
            ConnectivityListener.connectivityReceiverListener.onNetworkConnectionChange(true);
        } else {
            ConnectivityListener.connectivityReceiverListener.onNetworkConnectionChange(false);
        }
    }

    private boolean isNetworkInterfaceAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

    //This makes a real connection to an url and checks if you can connect to this url, this needs to be wrapped in a background thread
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



    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChange(boolean isconnected);
    }
}
