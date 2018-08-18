package com.app.heads.Utils;

import android.app.Activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.app.heads.R;


public class No_InternetDialog {
    public Dialog dialog;
    public Activity mActivity;
    public Context mContext;
    public TextView head_textview,instruction_textview;
    public Button ok_btn;

    public No_InternetDialog(Context context, Activity mActivity) {
        this.mContext = context;
        this.mActivity = mActivity;
    }

    public void showDialog() {
        dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.no_internet);
        setViews(dialog);
        dialog.setCancelable(false);
        if (mActivity != null) {
            dialog.show();
        }
    }

    public void setViews(Dialog dialog) {
        head_textview = dialog.findViewById(R.id.record_txt);
        instruction_textview = dialog.findViewById(R.id.instruction);
        ok_btn = dialog.findViewById(R.id.button_ok);
        head_textview.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.BOLD));
        instruction_textview.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));
        ok_btn.setTypeface(Typeface.create("MontserratAlternates-Medium.otf", Typeface.NORMAL));

    }

    public void hideDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
