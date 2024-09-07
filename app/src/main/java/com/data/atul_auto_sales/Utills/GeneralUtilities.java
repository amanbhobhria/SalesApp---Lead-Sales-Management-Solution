package com.data.atul_auto_sales.Utills;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.transition.Fade;
import android.transition.Slide;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.SimpleColorFilter;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;
import com.data.atul_auto_sales.Activity.NoInternetActivity;
import com.data.atul_auto_sales.BuildConfig;
import com.data.atul_auto_sales.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GeneralUtilities {
    private Context context;

    private static Dialog dialog;

    public static Intent intent;

    public GeneralUtilities(Context context) {
        this.context = context;
    }

    public static void down(Context context,String url){
        DownloadManager manager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        long reference = manager.enqueue(request);
    }
    public static void downloadUrl(Context context,String url){
       String mimetype = MimeTypeMap.getFileExtensionFromUrl(url);

        File file = new File(url);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setMimeType(mimetype);
      //  request.addRequestHeader("User-Agent", userAgent);
        request.setDescription("Downloading...");
        request.setTitle(Uri.fromFile(file).getLastPathSegment());
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, Uri.fromFile(file).getLastPathSegment());
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }
    public static Uri getImageUri(Context inContext,Bitmap imageBitmap ) {
        Bitmap outImg = Bitmap.createScaledBitmap(imageBitmap, 1000, 1000, true);
        String path=MediaStore.Images.Media.insertImage(inContext.getContentResolver(),outImg,"Title",null);
        return  Uri.parse(path);
    }

    public static String getRealPathFromURII(Context context,Uri uri) {
        String path = "";
        if (context.getContentResolver() != null) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }
    private static void downloadFile(String url, File outputFile) {
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
            fos.write(buffer);
            fos.flush();
            fos.close();
        } catch(FileNotFoundException e) {
            return; // swallow a 404
        } catch (IOException e) {
            return; // swallow a 404
        }
    }
    public static Typeface getRegularTypeFace(Context context) {
        Typeface t = Typeface.createFromAsset(context.getAssets(), "fonts/" + "OpenSans-Regular" + ".ttf");

        return t;

    }

    public static  String updateLabel(String myCalendar) {
        String outputText = "";
        try {
            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            Date date = inputFormat.parse(myCalendar);
            outputText = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputText;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
    public static int getScreenWidth(Context mActivity) {
        int width;
        Point point = new Point();
        Display display = ((Activity) mActivity).getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT < 13) {
            width = display.getWidth();
        } else {
            display.getSize(point);
            width = point.x;
        }
        return width;
    }

    public static Dialog fullScreenDailog(Integer layoutId,int dialogFullscreen, Activity activity) {
        Dialog dialog = new Dialog(activity,dialogFullscreen);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layoutId);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = (int) (size.x * 0.94);
        int height = (int) (size.y * 0.94);
        dialog.show();
        dialog.getWindow().setLayout(width, height);
        return dialog;
    }
    public static Dialog openDailog(Integer layoutId, Activity activity) {
        Dialog dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layoutId);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }


    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    public Boolean isConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public int getScreenWidth() {
        int width;
        Point point = new Point();
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT < 13) {
            width = display.getWidth();
        } else {
            display.getSize(point);
            width = point.x;
        }
        return width;
    }

    public static void shareIntent(Context mActivity, String shareText) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        sendIntent.setType("text/plain");
        //startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
        mActivity.startActivity(Intent.createChooser(sendIntent, "Share Transfer Details"));
    }

    public static void appShare(Context mActivity){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, com.data.atul_auto_sales.R.string.app_name);
            String shareMessage= "\nLet me recommend you this application\n\n";
          //  shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            mActivity.startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }

    public static void shareContent(Context mActivity,String text){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            String shareMessage= "\nLet me recommend you this application\n\n"+"MobileNo : "+text;
            shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            mActivity.startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }

    public int getScreenHeight() {
        Point point = new Point();
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT < 13) {
            return display.getHeight();
        } else {
            display.getSize(point);
            return point.y;
        }
    }

    public static Rect locateView(View v) {
        int[] loc_int = new int[2];
        if (v == null) return null;
        try {
            v.getLocationOnScreen(loc_int);
        } catch (NullPointerException e) {
            return null;
        }
        Rect position = new Rect();
        position.left = loc_int[0];
        position.top = loc_int[1];
        position.right = position.left + v.getWidth();
        position.bottom = position.top + v.getHeight();
        return position;
    }


    public void showAlertDialog(final String title, final String message, final String buttonName) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(
                        context, R.style.AlertDialogCustom)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(buttonName, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });
    }


    public static byte[] getBytes(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        } else {
            byte[] b = null;
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
                b = byteArrayOutputStream.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return b;
        }
    }

    public void getDisplayHeight(Context context, double heightpercentage, double widthpercentage, View view) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int finalheight = (int) (height * heightpercentage);
        int finalwidth = (int) (width * widthpercentage);
        view.getLayoutParams().width = width;
        view.getLayoutParams().height = height;
    }

    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /*  public static void showDialog(Context context,String message)
      {
          dialog=new ProgressDialog(context);
          dialog.setMessage(message);
          dialog.setCancelable(false);
          dialog.show();;
      }*/
    public static void hideDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isAndroid6() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isAndroid7() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    public static boolean isAndroid8() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }


    // Validations of email address,url
    public static boolean isValidEmail(String email) {
        return !(email == null || email.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean isValidURL(String url) {
        return !(url == null || url.isEmpty() || Patterns.WEB_URL.matcher(url).matches());
    }

    public static boolean isBlankText(String text) {
        return !(text == null || text.isEmpty());
    }

    public static boolean isValidMobile(String mobile) {
        String rgexp = "^[0-9]*$";
        Matcher matcher = Pattern.compile(rgexp).matcher(mobile);
        if (mobile == null || mobile.isEmpty()) {
            return false;
        } else if (!matcher.find()) {
            return false;
        } else if (mobile.length() < 10 || mobile.length() > 10) {
            return false;
        } else if (!(mobile.startsWith("6") || mobile.startsWith("7") || mobile.startsWith("8") || mobile.startsWith("9"))) {
            return false;
        }
        return true;
    }

    public static void showSnackBar(Context context, View view, String message, int colorid, int textcolorid) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, colorid));
        TextView textView = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(context, textcolorid));
        snackbar.show();
    }

    public static void showErrorSnackBar(Context context, View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.errorcolor));
        TextView textView = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        textView.setMaxLines(5);
        snackbar.show();
    }

    public static void showNormalSnackBar(Context context, View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.normalcolor));
        TextView textView = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setMaxLines(5);
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        snackbar.show();
    }

    public static void launchclearbackActivity(AppCompatActivity context, Class target) {
        Intent intent = new Intent(context, target);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        context.finish();
        //   AnimationHelper.sli(context);
        //context.overridePendingTransition(R.anim.slide_from_right,R.anim.slide_from_left);
    }

    public static void launchActivity(AppCompatActivity context, Class target) {
        Intent intent = new Intent(context, target);
        context.startActivity(intent);
        //   AnimationHelper.fade(context);

        //  context.overridePendingTransition(R.anim.slide_from_right,R.anim.slide_from_left);
    }

    public static void showLog(String heading, String message) {
        Log.d(heading, message);
    }

    // Making notification bar transparent
    public static void makeNotificationBarTransperent(AppCompatActivity context) {
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    public static void setCustomAnimation(Context context, View view, int animationid) {
        Animation animationUtils = AnimationUtils.loadAnimation(context, animationid);
        view.startAnimation(animationUtils);

    }

    public static void setDisplayMatrix(Context context, View view, double widthpercentage, double heightpercentage) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int manualheight = (int) (height * heightpercentage);
        int manualwidth = (int) (width * widthpercentage);
        if (heightpercentage != 1)
            view.getLayoutParams().height = manualheight;
        if (widthpercentage != 1)
            view.getLayoutParams().width = manualwidth;
    }

    public static void setNormalRipple(Context context, View view) {
        int[] attrs = new int[]{com.google.android.material.R.attr.selectableItemBackground};
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        int backgroundResource = typedArray.getResourceId(0, 0);
        view.setBackgroundResource(backgroundResource);
    }

    public static void setFragment(Context appCompatActivity, Fragment targetFragment, int containerId) {
        Fragment fragment = targetFragment;
        androidx.fragment.app.FragmentTransaction transaction = ((AppCompatActivity) appCompatActivity).getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    public static void callFragment(Context appCompatActivity, Fragment targetFragment, Bundle bundle, int containerId) {
        Fragment fragment = targetFragment;
        fragment.setArguments(bundle);
        androidx.fragment.app.FragmentTransaction transaction = ((AppCompatActivity) appCompatActivity).getSupportFragmentManager().beginTransaction();
    //    transaction.setCustomAnimations(R.anim.right_in, R.anim.left_in, R.anim.left_out, R.anim.right_out);
   //     transaction.setCustomAnimations(R.anim.right_in, R.anim.left_in, R.anim.left_out, R.anim.right_out);
        transaction.replace(containerId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
      //  transaction.commitAllowingStateLoss();
    }

    public static void setRecyclerView(Context context, RecyclerView recyclerView, int type) {
        if (type == 1) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);
        } else if (type == 2) {

        }
    }

    public static String loadJSONFromAssets(Context context) {
        String json = null;
        try {
            InputStream im = context.getResources().getAssets().open("country_phones.json");
            int size = im.available();
            byte[] buffer = new byte[size];
            im.read(buffer);
            im.close();
            json = new String(buffer, "UTF-8");


        } catch (Exception e) {
            return null;
        }
        return json;
    }

/*    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setUpExitTransition(Context context) {
        Slide slide = (Slide) TransitionInflater.from(context).inflateTransition(R.transition.activity_slide);
        ((AppCompatActivity) context).getWindow().setExitTransition(slide);
        Slide fade = (Slide) TransitionInflater.from(context).inflateTransition(R.transition.activity_slide);
        ((AppCompatActivity) context).getWindow().setEnterTransition(fade);
    }*/

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setUpEnterTransition(Context context) {


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setupWindowAnimations(Context context) {
        Fade fade = new Fade();
        fade.setDuration(1000);
        ((AppCompatActivity) context).getWindow().setEnterTransition(fade);

        Slide slide = new Slide();
        slide.setDuration(1000);
        ((AppCompatActivity) context).getWindow().setReturnTransition(slide);
    }

/*    public static String[] getTransferMethodName(Context context) {
        String name[] = new String[]{context.getResources().getString(R.string.banktransfer), context.getResources().getString(R.string.paypalmethod), context.getResources().getString(R.string.wirecardmethod)};
        return name;

    }

    public static int[] getTransferMethodImage(Context context) {
        int name[] = new int[]{R.drawable.broadband, R.drawable.broadband, R.drawable.broadband};
        return name;

    }*/

   /* public static Fragment getCurrentFragment(Context context) {
        Fragment fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.containerid);
        return fragmentManager;

    }*/

    public static void hideSoftKeyboard(Context activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    public static void showCustomLoader(View sendlottielayout, LottieAnimationView lottieview, View successlayout, View signupbutton) {
        sendlottielayout.setVisibility(View.VISIBLE);
        lottieview.setAnimation("loader.json");
        lottieview.playAnimation();
        successlayout.setVisibility(View.GONE);
        signupbutton.setVisibility(View.GONE);
    }


    public static void hideCustomLoader(View sendlottielayout, View signupbutton, View successlayout) {
        sendlottielayout.setVisibility(View.GONE);
        signupbutton.setVisibility(View.VISIBLE);
        successlayout.setVisibility(View.GONE);
    }

    public static void showSuccessLoader(View sendlottielayout, LottieAnimationView lottieview, View signupbutton, View successlayout) {
        sendlottielayout.setVisibility(View.GONE);
        signupbutton.setVisibility(View.GONE);
        successlayout.setVisibility(View.VISIBLE);
        lottieview.setAnimation("loader_complete.json");
        lottieview.playAnimation();
    }

    /*    public static class MyTextWatcher implements TextWatcher {
            private View view;
            private MyTextWatcher(View view)
            {
                this.view=view;
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                switch (view.getId())
                {
                    case R.id.emailid:
                        validateEmail();
                        break;
                    case R.id.password:
                        validatePassword();
                        break;
                }

            }
        }*/

    public static void sendURL(Activity activity,String URL) {
        try {
            if (TextUtils.isEmpty(URL)) {
                return;
            }
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.sendmailanimationlayout, null, false);
        builder.setView(view);
        dialog = builder.create();
        dialog.setCancelable(false);
        final LottieAnimationView groupcreate = (LottieAnimationView) view.findViewById(R.id.email);

        int yourColor = ContextCompat.getColor(activity,R.color.colorPrimary);
        SimpleColorFilter filter = new SimpleColorFilter(yourColor);
        KeyPath keyPath = new KeyPath("**");
        LottieValueCallback<ColorFilter> callback = new LottieValueCallback<ColorFilter>(filter);
        groupcreate.addValueCallback(keyPath, LottieProperty.COLOR_FILTER, callback);

        groupcreate.setAnimation("loader.json");
        groupcreate.playAnimation();
        //increase animation speed manually
      /*  ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                groupcreate.setProgress((Float) valueAnimator.getAnimatedValue());
            }
        });

        if (groupcreate.getProgress() == 0f) {
            animator.start();
        } else {
            groupcreate.setProgress(0f);
        }*/

        dialog.getWindow().setBackgroundDrawableResource(R.color.transperent);
        dialog.show();

    }

    public static void showDeleteLoader(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.sendmailanimationlayout, null, false);
        builder.setView(view);
        dialog = builder.create();
        dialog.setCancelable(false);
        final LottieAnimationView groupcreate = (LottieAnimationView) view.findViewById(R.id.email);

        groupcreate.setAnimation("deleteanimation.json");
        groupcreate.playAnimation();
        //increase animation speed manually
      /*  ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                groupcreate.setProgress((Float) valueAnimator.getAnimatedValue());
            }
        });

        if (groupcreate.getProgress() == 0f) {
            animator.start();
        } else {
            groupcreate.setProgress(0f);
        }*/

        dialog.getWindow().setBackgroundDrawableResource(R.color.transperent);
        dialog.show();

    }

 /*   public static String getCountryName(Context context, int localcountryid) {
        ArrayList<CountryList> lists = PreferenceManager.getCountryList(context, AppConstant.COUNTRY_LIST);
        if (lists != null) {
            if (lists.size() > 0) {
                for (co.pingmoney.testm.model.country.CountryList list : lists) {
                    if (list.getId() != null) {
                        if (list.getId() == localcountryid) {
                            return list.getName();
                        }
                    }
                }
            }
        }
        return "";
    }*/

   /* public static String getCountryFlag(Context context, int localcountryid) {
        ArrayList<CountryList> lists = PreferenceManager.getCountryList(context, AppConstant.COUNTRY_LIST);
        if (lists != null) {
            if (lists.size() > 0) {
                for (co.pingmoney.testm.model.country.CountryList list : lists) {
                    if (list.getId() != null) {
                        if (list.getId() == localcountryid) {
                            return list.getFlag();
                        }
                    }
                }
            }
        }
        return "";

    }*/

  /*  public static String getCountryCallCode(Context context, int localcountryid) {
        ArrayList<CountryList> lists = PreferenceManager.getCountryList(context, AppConstant.COUNTRY_LIST);
        if (lists != null) {
            if (lists.size() > 0) {
                for (co.pingmoney.testm.model.country.CountryList list : lists) {
                    if (list.getId() != null) {
                        if (list.getId() == localcountryid) {
                            return list.getCallingCode() + "";
                        }
                    }
                }
            }
        }
        return "";
    }
*/
  /*  public static void setDataServerData(Context context, GetProfileResponse body, User user) {
        if (body != null) {
            if (body.getKyc() != null) {
                PreferenceManager.getInstance(context).savelogindetails(AppConstant.KYC_STATUS, body.getKyc() + "");
            }
            if (body.getId() != null) {
                PreferenceManager.getInstance(context).savelogindetails(AppConstant.PROFILE_ID, body.getId() + "");
            }
            if (body.getAddress() != null) {
                PreferenceManager.getInstance(context).savelogindetails(AppConstant.ADDRESS, body.getAddress() + "");

            }
            if (body.getAvatar() != null) {
                PreferenceManager.getInstance(context).savelogindetails(AppConstant.AVATAR, body.getAvatar() + "");

            }
            if (body.getCity() != null) {
                PreferenceManager.getInstance(context).savelogindetails(AppConstant.CITY, body.getCity() + "");

            }
            if (body.getEmail() != null) {
                PreferenceManager.getInstance(context).savelogindetails(AppConstant.EMAIL_ID, body.getEmail() + "");

            }
            if (body.getPhone() != null) {
                PreferenceManager.getInstance(context).savelogindetails(AppConstant.PHONE, body.getPhone() + "");

            }
            if (body.getFirstName() != null) {
                PreferenceManager.getInstance(context).savelogindetails(AppConstant.FIRST_NAME, body.getFirstName() + "");

            }
            if (body.getLastName() != null) {
                PreferenceManager.getInstance(context).savelogindetails(AppConstant.LAST_NAME, body.getLastName() + "");

            }
            if (body.getPostalCode() != null) {
                PreferenceManager.getInstance(context).savelogindetails(AppConstant.POSTAL_CODE, body.getPostalCode() + "");

            }
            if (body.getCountryId() != null) {
                PreferenceManager.getInstance(context).savelogindetails(AppConstant.COUNTRY_ID, body.getCountryId() + "");

            }
            if (body.getKyc() != null) {
                PreferenceManager.getInstance(context).savelogindetails(AppConstant.KYC_STATUS, body.getKyc() + "");
            }
            if (body.getBirthday() != null) {

                String formatstr = "yyyy-MM-dd";
                SimpleDateFormat dateFormat = new SimpleDateFormat(formatstr);

                Date date = null;
                try {
                    date = dateFormat.parse(body.getBirthday());
                    String formatstrr = "dd/MM/yyyy";
                    //  dob=body.getBirthday();
                    SimpleDateFormat dateFormatr = new SimpleDateFormat(formatstrr);


                    PreferenceManager.getInstance(context).savelogindetails(AppConstant.BIRTHDAY, body.getBirthday() + "");

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/




    public static String setCustomDate(String inputdate) {
        String newformateddate = "";
        String toformatstr = "MMM dd, yyyy | hh:mm aa";
        SimpleDateFormat todateFormat = new SimpleDateFormat(toformatstr);
        String fromformatstr = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat fromdateFormat = new SimpleDateFormat(fromformatstr);
        try {
            Date date = fromdateFormat.parse(inputdate);
            newformateddate = todateFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newformateddate;
    }

    public static void saveToClipBoard(Context context, String label, String text) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText(label, text);
        manager.setPrimaryClip(data);
    }
    public static void copyText(Context context,String labelView,String value){
        ClipboardManager clipboard = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(labelView,value);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "Text Copied", Toast.LENGTH_SHORT).show();
    }

    public static Drawable convertBitmapToDrawable(Context context, Bitmap bitmap) {
        /*
            Drawable
                A Drawable is a general abstraction for "something that can be drawn."
                Most often you will deal with Drawable as the type of resource retrieved
                for drawing things to the screen; the Drawable class provides a generic
                API for dealing with an underlying visual resource that may take a variety
                of forms. Unlike a View, a Drawable does not have any facility
                to receive events or otherwise interact with the user.
        */

        /*
            BitmapDrawable
                A Drawable that wraps a bitmap and can be tiled, stretched, or aligned.
                You can create a BitmapDrawable from a file path, an input stream,
                through XML inflation, or from a Bitmap object.
        */

        /*
            public abstract Resources getResources ()
                Return a Resources instance for your application's package.
        */

        Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        return drawable;
    }

    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String filePath;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            filePath = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            filePath = cursor.getString(idx);
            cursor.close();
        }
        return filePath;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

    public static String decimalTwo(Double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        Formatter formatter = new Formatter();
        formatter.format("%.2f", bd.doubleValue());

        return formatter.toString();
    }

    public static Bitmap takeScreenShot(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(true);
        return bitmap;
    }

    public static Bitmap takescreenshotOfRootView(View v) {
        return takeScreenShot(v.getRootView());
    }

    public static void saveScreenShot(Bitmap bitmap, String filename) {
        String path = Environment.getExternalStorageDirectory().toString() + "/" + filename;
        OutputStream outputStream = null;
        File imageFile = new File(path);
        try {
            outputStream = new FileOutputStream(imageFile);
            // choose JPEG format
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);

            outputStream.flush();
        } catch (Exception e) {

        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {

            }
        }

    }

    public static void registerBroadCastReceiver(Context context, BroadcastReceiver receiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(receiver, intentFilter);
    }

    public static void unregisterBroadCastReceiver(Context context, BroadcastReceiver receiver) {
        if (receiver != null)
            context.unregisterReceiver(receiver);
    }

    public static void internetConnectivityAction(Context context, boolean isconnected) {
        if (isconnected) {
            // GeneralUtilities.showNormalSnackBar(context,container,context.getString(R.string.internetconnected));
        } else {
            if (intent == null) {
               intent = new Intent(context, NoInternetActivity.class);
                context.startActivity(intent);
            }
            // GeneralUtilities.showErrorSnackBar(context,container,context.getString(R.string.internetdisconnect));

        }
    }
    public static BottomSheetDialog openBootmSheetDailog(Integer layoutId, int dialogStyle, Activity activity) {
        BottomSheetDialog dialog = new BottomSheetDialog(activity, dialogStyle);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(layoutId);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public static void showAppUpdateDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final LayoutInflater inflater = LayoutInflater.from(context);
        // View view=inflater.inflate(R.layout.sendmailanimationlayout,null,false);
        builder.setTitle(context.getText(R.string.newupdate));
        builder.setMessage(context.getText(R.string.updatemessage));
        ;
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse("market://details?id=" + context.getApplicationContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(goToMarket);
                dialog.dismiss();

            }
        });
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                exit(context);
            }
        });
        dialog = builder.create();

        dialog.setCancelable(false);

        //increase animation speed manually
      /*  ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                groupcreate.setProgress((Float) valueAnimator.getAnimatedValue());
            }
        });

        if (groupcreate.getProgress() == 0f) {
            animator.start();
        } else {
            groupcreate.setProgress(0f);
        }*/


        dialog.show();

    }

    public static void exit(Context context) {
        ((AppCompatActivity) context).finish();

        System.exit(0);
    }

    public static void showSoftKeyBoard(Context context, View view) {
        if (view.requestFocus()) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static void installOtherApp(Context context, String packagename) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + packagename));
        context.startActivity(intent);
    }

}

