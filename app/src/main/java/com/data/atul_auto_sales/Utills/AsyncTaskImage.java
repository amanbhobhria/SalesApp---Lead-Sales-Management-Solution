package com.data.atul_auto_sales.Utills;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTaskImage extends AsyncTask<String, String, File> {
    URL ImageUrl = null;
    InputStream is = null;
    Bitmap bmImg = null;
    File file;
    FileComplete fileListner;
    String path_external = Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg";

    public AsyncTaskImage(FileComplete fileListner) {
        this.fileListner = fileListner;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }
    @Override
    protected File doInBackground(String... strings) {
        try {
            ImageUrl = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) ImageUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bmImg = BitmapFactory.decodeStream(is, null, options);
            bmImg.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            file = new File(Environment.getExternalStorageDirectory() + File.separator + System.currentTimeMillis()+".jpg");
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.flush();
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
    @Override
    protected void onPostExecute(File bitmap) {
        super.onPostExecute(bitmap);

        fileListner.onPreparedFile(bitmap);



    }
}
