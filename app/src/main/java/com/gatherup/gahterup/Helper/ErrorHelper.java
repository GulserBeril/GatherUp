package com.gatherup.gahterup.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ErrorHelper {

    public  static void saveError(Context context, Exception e){
        Log.e("ERROR", e.getMessage());
        Toast.makeText(context, "Hata Oluştu", Toast.LENGTH_SHORT).show();
    }
}
