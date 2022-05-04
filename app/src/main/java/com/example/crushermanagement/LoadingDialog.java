package com.example.crushermanagement;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class LoadingDialog {
    public LoadingDialog(Context context) {
        this.context = context;
    }

    Dialog dialog;
    Context context;

    public  void ShowDialog(String title){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.create();
        dialog.show();
    }

    public  void HideDialog(){
        dialog.dismiss();
    }
}
