package com.toshiwa.InternetConnection;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;

import com.toshiwa.toshiwa.R;

public  class DialogNoConnection {

    public static void showDialog(Context context)
    {
        final Dialog welcomeDialog;
        Button mAcceptButton;
        welcomeDialog = new Dialog(context);

        welcomeDialog.setContentView(R.layout.dialog_no_internet);
         mAcceptButton = (Button) welcomeDialog.findViewById(R.id.accept_dialog_btn);

        mAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                welcomeDialog.dismiss();
            }
        });
        welcomeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        welcomeDialog.show();

    }
}
