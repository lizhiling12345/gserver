package com.texcnc.gserver.common;

import android.app.Activity;

public class GlobalContext {

	private static Activity _activity;
	
	public static void setActivity(Activity activity ){
		_activity =activity;
	}
	
	public static Activity getActivity(){
		return _activity;
	}
	
}
