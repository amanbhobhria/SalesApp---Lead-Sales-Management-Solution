package com.data.atul_auto_sales.Utills;

import android.content.Context;
import android.content.IntentSender;
import android.widget.Toast;

import com.data.atul_auto_sales.Activity.SplashActivity;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.stpl.antimatter.Utils.ApiContants;


public class GoogleInAppUpdate {

    private AppUpdateManager appUpdateManager;
    private Context context;

    public GoogleInAppUpdate(Context context, AppUpdateManager appUpdateManager) {
        this.context = context;
        this.appUpdateManager = appUpdateManager;
    }

    public void checkForAppUpdate() {
        // Creates instance of the manager.
        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                // Request the update.
                if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    // Start an update.
                    startAppUpdateImmediate(appUpdateInfo);
                }
            } else {
             //   ((SplashActivity) context).activityTask();
            }
        });
    }

    private void startAppUpdateImmediate(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    // The current activity making the update request.
                    ((SplashActivity) context),
                    // Include a request code to later monitor this update request.
                    ApiContants.REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void checkNewAppVersionState() {
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {
                            //FLEXIBLE:
                            // If the update is downloaded but not installed,
                            // notify the user to complete the update.
                            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                            }

                            //IMMEDIATE:
                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                startAppUpdateImmediate(appUpdateInfo);
                            }
                        });
    }

}
