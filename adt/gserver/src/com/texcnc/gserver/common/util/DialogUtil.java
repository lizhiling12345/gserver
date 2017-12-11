package com.texcnc.gserver.common.util;


import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

public class DialogUtil {


	
    public static void showInfo(String strInfo){
//        Toast.makeText(_activity, strInfo, Toast.LENGTH_LONG).show();
//    	new AlertDialog.Builder(_activity).setTitle("信息").setMessage(strInfo).setPositiveButton("确定", null).show();  
    	Log.i("DialogUtil", strInfo);
    }
	
}
