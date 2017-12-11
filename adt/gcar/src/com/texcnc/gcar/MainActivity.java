package com.texcnc.gcar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import qrx.bt.c.DeviceListActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	private Button On,Off,Visible,list;

	   private Set<BluetoothDevice> pairedDevices;
	   private ListView lv;
	   
	   private static final String TAG = "DeviceListActivity";
	   private BluetoothAdapter bluetoothAdapter;
	   private ArrayAdapter<String> mNewDevicesArrayAdapter;
	   private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	   private List<String> newlstDevices = new ArrayList();
	   

	   private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {  
         public void onReceive(Context context, Intent intent) {  
        	 
        	 try{
        		 
        		 
        		 String action = intent.getAction();  
                 //找到设备  
                 if (BluetoothDevice.ACTION_FOUND.equals(action)) {  
                     BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);  
           
                     if (device.getBondState() != BluetoothDevice.BOND_BONDED) {  
                         Log.v("", "find device:" + device.getName()  + device.getAddress());  
                         Toast.makeText(MainActivity.this,"find device:" + device.getName()  + device.getAddress(),Toast.LENGTH_SHORT).show();
                     }  
                     
                     Toast.makeText(MainActivity.this,"find device:" + device.getName()  + device.getAddress(),Toast.LENGTH_SHORT).show();
                     
                     
                     
                     if (device.getBondState() != 12)
                     {
                       String str3 = device.getName() + "\n" + device.getAddress();
                       if (newlstDevices.indexOf(str3) == -1)
                       {
                         newlstDevices.add(str3);
                       }
                     }
                     
                 }  else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED  .equals(action)) {  
                	 //搜索完成  
                	 Toast.makeText(MainActivity.this,"搜索完成  ",Toast.LENGTH_SHORT).show();
                	 
                	 
                     ListView listViewDevices = (ListView)findViewById(R.id.listViewDevices);
                     listViewDevices.setAdapter(
                     		new ArrayAdapter<String>(MainActivity.this,
                     				android.R.layout.simple_expandable_list_item_1,
                     				newlstDevices));
                     listViewDevices.setOnItemClickListener(mDeviceClickListener);
                 }  
                 
 			} catch (Exception e) {
				Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
			}
            
         }  
     };  
		   
     
     
     private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener()
     {
       public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
       {
    	   
    	   try{
    		   
        	   bluetoothAdapter.cancelDiscovery();
               String str1 = ((TextView)paramView).getText().toString();
               String str2 = str1.substring(-17 + str1.length());
               
               Toast.makeText(MainActivity.this,str1,Toast.LENGTH_SHORT).show();
               Toast.makeText(MainActivity.this,str2,Toast.LENGTH_SHORT).show();
               
               Intent localIntent = new Intent();
               localIntent.putExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS, str2);
               
               setResult(-1, localIntent);
               finish();
               
			} catch (Exception e) {
				Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
			}

       }
     };
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        try {
			
        	
//          BroadcastReceiver mReceiver = new BroadcastReceiver() {  
//          public void onReceive(Context context, Intent intent) {  
//              String action = intent.getAction();  
//              //找到设备  
//              if (BluetoothDevice.ACTION_FOUND.equals(action)) {  
//                  BluetoothDevice device = intent  
//                          .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);  
//        
//                  if (device.getBondState() != BluetoothDevice.BOND_BONDED) {  
//        
//                      Log.v(TAG, "find device:" + device.getName()  
//                              + device.getAddress());  
//                  }  
//              }  
//              //搜索完成  
//              else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED  
//                      .equals(action)) {  
//                  setTitle("搜索完成");  
//                  if (mNewDevicesAdapter.getCount() == 0) {  
//                      Log.v(TAG, "find over");  
//                  }  
//              }  
//          }  
//      };  
      
      
    
            ListView listViewDevices = (ListView)findViewById(R.id.listViewDevices);
            listViewDevices.setOnItemClickListener(mDeviceClickListener);

      
      //得到按钮实例
      Button btnScan = (Button)findViewById(R.id.btnScan);
      //设置监听按钮点击事件
      btnScan.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
        	  
        	  try {
				
        		  
        		  //得到textview实例
                  TextView textView1 = (TextView)findViewById(R.id.textView1);
                  //弹出Toast提示按钮被点击了
                  Toast.makeText(MainActivity.this,"Clicked",Toast.LENGTH_SHORT).show();
                  //读取strings.xml定义的interact_message信息并写到textview上
                  
//                  textView1.setText("FUFUUF");
//                  
//                  BA = BluetoothAdapter.getDefaultAdapter();
    //
//                 
//                  
//                  if( BA==null ){
//                  	 textView1.setText("BA NULL");
//                  }else{
//                  	
//                  	 pairedDevices = BA.getBondedDevices();
    //
//                       textView1.setText("FUF33");
//                       
//                       
//                       String str="0";
//                       if( pairedDevices!=null ){
//                           for(BluetoothDevice bt : pairedDevices){
//                          	 str=str + bt.getName();
//                           }
//                       }
    //
//                
//                       textView1.setText(str);
//                  }
                 

//                  IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);  
//                  registerReceiver(mReceiver, filter);  
//                  filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);  
//                  registerReceiver(mReceiver, filter)
                  
                  
                  newlstDevices.clear();
                  

                  
//                  mPairedDevicesArrayAdapter = new ArrayAdapter(this, 2130903043);
//                  mNewDevicesArrayAdapter = new ArrayAdapter(this, 2130903043, newlstDevices);
                  
                  
//                  localListView1.setAdapter(mPairedDevicesArrayAdapter);

                  
                  
                  bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                  
                  if(  bluetoothAdapter==null ){
                  	Toast.makeText(MainActivity.this,"BluetoothAdapter is null",Toast.LENGTH_SHORT).show();
                  	return;
                  }
                  
//                  IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);  
                  IntentFilter intentFilterFOUND = new IntentFilter("android.bluetooth.device.action.FOUND");
                  registerReceiver(broadcastReceiver, intentFilterFOUND);
                  IntentFilter intentFilterFINISHED = new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
                  registerReceiver(broadcastReceiver, intentFilterFINISHED);
      
                  if (bluetoothAdapter.isDiscovering()){
                  	bluetoothAdapter.cancelDiscovery();
                  }
                  
                  bluetoothAdapter.startDiscovery();
                  
			} catch (Exception e) {
				Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
			}
             
              
          }
      });
      

      
      
		} catch (Exception e) {
			Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
		}
        

        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
