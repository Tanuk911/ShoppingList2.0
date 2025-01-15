package com.example.activityonesqlite.utilites;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.activityonesqlite.R;

import java.lang.reflect.Method;

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
        dialog.setCancelable(true);
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
