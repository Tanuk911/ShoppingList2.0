package com.example.activityonesqlite.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.example.activityonesqlite.R;

public class DialogUtility {

    public interface DialogCallback {
        void onResult(boolean proceed);
    }

    Dialog dialog;
    Context context;

    Button btnOk, btnCancel;

    public DialogUtility(Context context){
        this.context = context;
    }

    public void setAlertDialog(DialogCallback callback){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_alert_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dialog_background));
        dialog.setCancelable(false);
        dialog.show();

        btnCancel = dialog.findViewById(R.id.custom_alert_btnCancel);
        btnOk = dialog.findViewById(R.id.custom_alert_btnSure);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callback.onResult(true);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callback.onResult(false);
            }
        });
    }
}
