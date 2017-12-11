package com.texcnc.gserver;


import com.texcnc.gserver.common.GlobalContext;
import com.texcnc.gserver.common.util.HostUtil;
import com.texcnc.gserver.common.web.WebServer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GlobalContext.setActivity(MainActivity.this);

		//启动服务器
		AssetManager assetManager = getAssets();
		WebServer.getInstance().startServer(assetManager);
		
		WifiManager wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);  
		String strHostIp ="Web Server:"+HostUtil.getWifiIP(wifiMan)+":10080";
		TextView textView = (TextView) findViewById(R.id.textViewInfo); 
		textView.setText(strHostIp);
		 
		
//		 //得到按钮实例
//	      Button buttonExit = (Button)findViewById(R.id.buttonExit);
//	      //设置监听按钮点击事件
//	      buttonExit.setOnClickListener(new View.OnClickListener() {
//	          @Override
//	          public void onClick(View v) {
//	        	  new AlertDialog.Builder(MainActivity.this).setTitle("退出").setMessage("退出").setPositiveButton("确定", null).show();  
//	          }
//	      });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
    
}
