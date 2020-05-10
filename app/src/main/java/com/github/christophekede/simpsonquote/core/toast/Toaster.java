package com.github.christophekede.simpsonquote.core.toast;

import android.content.Context;
import android.widget.Toast;

public class Toaster {

    public Toaster(){

    }


    public void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }
}
