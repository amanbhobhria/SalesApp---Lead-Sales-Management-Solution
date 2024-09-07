package com.data.atul_auto_sales.Utills;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.TaskStackBuilder;
//import android.content.Context;
//import android.content.Intent;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Build;
//import android.util.Log;
//
//import androidx.core.app.NotificationCompat;
//import androidx.core.content.ContextCompat;
//
//import com.data.atul_auto_sales.Activity.DashboardActivity;
//import com.data.atul_auto_sales.R;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//import com.google.gson.Gson;
//
public class MyFirebaseMessagingService{
// extends FirebaseMessagingService {

// //   AppPreferences appPreferences;
//    int id = 0;
//    boolean silentType=true;
//    Uri soundUri;
//    @Override
//    public void onNewToken(String s) {
//        super.onNewToken(s);
//        Log.e("newToken", s);
//     //   getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", s).apply();
//
//    }
//
//    @Override
//    public void
//    onMessageReceived(RemoteMessage remoteMessage) {
//      //  appPreferences = new AppPreferences(getApplicationContext());
//        // First case when notifications are received via
//        // data event
//        // Here, 'title' and 'message' are the assumed names
//        // of JSON
//        // attributes. Since here we do not have any data
//        // payload, This section is commented out. It is
//        // here only for reference purposes.
//        id = (int) (System.currentTimeMillis() * (int) (Math.random() * 100));
//        Log.d("zxczx", remoteMessage.getSenderId());
//        Log.d("zxczx", new Gson().toJson(remoteMessage));
//        //remoteMessage.getSenderId();
//        /*if(remoteMessage.getData().size()>0){
//            showNotification(remoteMessage.getData().get("title"),
//                          remoteMessage.getData().get("message"));
//        }*/
//
//        // Second case when notification payload is
//        // received.
//        if (remoteMessage.getNotification() != null) {
//            // Since the notification is received directly
//            // from FCM, the title and the body can be
//            // fetched directly as below.
//
//            showNotification(
//                    remoteMessage.getNotification().getTitle(),
//                    remoteMessage.getNotification().getBody());
//
//           /* if (appPreferences.getBooleanValue("NotificationOn") == true) {
//                showNotification(
//                        remoteMessage.getNotification().getTitle(),
//                        remoteMessage.getNotification().getBody());
//            }*/
//
//        }
//    }
//
//    // Method to get the custom Design for the display of
//    // notification.
//    /*private RemoteViews getCustomDesign(String title,
//                                        String message)
//    {
//        RemoteViews remoteViews = new RemoteViews(
//                getApplicationContext().getPackageName(),
//                R.layout.notification);
//        remoteViews.setTextViewText(R.id.title, title);
//        remoteViews.setTextViewText(R.id.message, message);
//        remoteViews.setImageViewResource(R.id.icon,
//                R.drawable.gfg);
//        return remoteViews;
//    }*/
//
//    // Method to display the notifications
//
//    public void showNotification(String title,
//                                 String message) {
//        // Pass the intent to switch to the MainActivity
//        String channel_id = "Sales Text";
//
//        Intent resultIntent = new Intent(this, DashboardActivity.class);
//// Create the TaskStackBuilder and add the intent, which inflates the back stack
//        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addNextIntentWithParentStack(resultIntent);
//// Get the PendingIntent containing the entire back stack
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(0,
//                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
//
//  //      Intent intent= new Intent(this, DashboardActivity.class);
//        // Assign channel ID
//        // Here FLAG_ACTIVITY_CLEAR_TOP flag is set to clear
//        // the activities present in the activity stack,
//        // on the top of the Activity that is to be launched
// //       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        // Pass the intent to PendingIntent to start the
//        // next Activity
//   //     PendingIntent pendingIntent= PendingIntent.getActivity(this, id, intent,PendingIntent.FLAG_ONE_SHOT);
//        soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//  /*      if (appPreferences.getBooleanValue("NotificationSound") == true) {
//        //    soundUri= Settings.System.DEFAULT_NOTIFICATION_URI;
//            soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        silentType=true;
//        }else {
//            silentType=false;
//            soundUri=null;
//
//        }*/
//
//        Intent intent = new Intent(this, DashboardActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
//
//        NotificationCompat.Builder builder
//                = new NotificationCompat
//                .Builder(getApplicationContext(),
//                channel_id)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText(message))
//                .setSmallIcon(R.drawable.app_logo)
//                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
//                .setAutoCancel(true)
//                .setSilent(silentType)
//                .setSound(soundUri)
//                .setCategory(Notification.CATEGORY_REMINDER)
//                .setPriority(NotificationManager.IMPORTANCE_HIGH)
//                .setVibrate(new long[]{1000, 1000, 1000,
//                        1000, 1000})
//                .setOnlyAlertOnce(true)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager
//                = (NotificationManager) getSystemService(
//                Context.NOTIFICATION_SERVICE);
//        // Check if the Android Version is greater than Oreo
//        if (Build.VERSION.SDK_INT
//                >= Build.VERSION_CODES.O) {
//            NotificationChannel notificationChannel
//                    = new NotificationChannel(
//                    channel_id, "web_app",
//                    NotificationManager.IMPORTANCE_HIGH);
//            notificationManager.createNotificationChannel(
//                    notificationChannel);
//        }
//        notificationManager.notify(id, builder.build());
//    }
}
