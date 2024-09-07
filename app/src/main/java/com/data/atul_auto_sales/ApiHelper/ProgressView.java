package com.data.atul_auto_sales.ApiHelper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.data.atul_auto_sales.R;


public class ProgressView {
    Context context;
    private Dialog dialog;

    public ProgressView(Context context) {
        this.context = context;
        dialog = new Dialog(context, R.style.DialogFragmentTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loader_dialog);
        dialog.setCancelable(false);
    }

    public void showLoader() {
        if (!dialog.isShowing()) {
            dialog.show();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } else {
            dialog.dismiss();
            dialog.show();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public void hideLoader() {
        if (isShowing()) {
            dialog.dismiss();
        }
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

}
