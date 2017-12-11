package com.texcnc.gserver.common.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.IOUtils;

import com.texcnc.gserver.common.GlobalContext;
import com.texcnc.gserver.common.util.DialogUtil;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

/**
 * 连接步骤，先扫描设备，连接执行的设备，底层会自动调用手机系统的配对程序，配对成功后，记录好对象就可以后续使用
 * 只需要手机向单片机发送指令，通过配对成功的对象，获取socket即可进行通讯
 */
public class BluetoothUtil {
	
	private final UUID MY_UUID = UUID.fromString("abcd1234-ab12-ab12-ab12-abcdef123456");//随便定义一个
	private static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
	private static List<Map<String,Object>> listDevice;
	private static List<BluetoothDevice> listBluetoothDevice;
	
	public static BluetoothSocket bluetoothSocket;
	private BluetoothSocket clientSocket;
	
	public List<Map<String,Object>> scanDevice() {
		
	
		if(listDevice!=null && listDevice.size()>0){
			return listDevice;
		}
		
		listDevice =new ArrayList<Map<String,Object>>();
		listBluetoothDevice =new ArrayList<BluetoothDevice>();
		
		try {
			
			BluetoothAdapter bluetoothAdapter =BluetoothAdapter.getDefaultAdapter();

			if( bluetoothAdapter==null || !bluetoothAdapter.isEnabled() ){
				DialogUtil.showInfo("Not Enabled.");
				return listDevice;
			}
			
			if( bluetoothAdapter!=null && bluetoothAdapter.isEnabled() ){
				
				
		        IntentFilter bluetoothFilter = new IntentFilter();
		        bluetoothFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		        bluetoothFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
		        GlobalContext.getActivity().registerReceiver(BluetoothReciever, bluetoothFilter);

		        //蓝牙扫描相关设备
		        IntentFilter btDiscoveryFilter = new IntentFilter();
		        btDiscoveryFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		        btDiscoveryFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		        btDiscoveryFilter.addAction(BluetoothDevice.ACTION_FOUND);
		        btDiscoveryFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		        GlobalContext.getActivity().registerReceiver(BTDiscoveryReceiver, btDiscoveryFilter);
		
		        int initialBTState = bluetoothAdapter.getState();
		        printBTState(initialBTState); // 初始时蓝牙状态
		        
		      //打印处当前已经绑定成功的蓝牙设备
		        Set<BluetoothDevice> bts = bluetoothAdapter.getBondedDevices();
		        Iterator<BluetoothDevice> iterator  = bts.iterator();
		        while(iterator.hasNext()){
		            BluetoothDevice bluetoothDevice = iterator.next() ;
		            
	            	Map<String,Object> mapDevice =new HashMap<String, Object>();
	            	mapDevice.put("name", bluetoothDevice.getName());
	            	mapDevice.put("address", bluetoothDevice.getAddress());
	            	listDevice.add(mapDevice);
	            	listBluetoothDevice.add(bluetoothDevice);
	            	
		            Log.d("BluetoothUtil" , " Name : " + bluetoothDevice.getName() + " Address : "+ bluetoothDevice.getAddress() ); ;
		            Log.d("BluetoothUtil", "Device class" + bluetoothDevice.getBluetoothClass());    
		        }
		        
                if (!bluetoothAdapter.isDiscovering()){
                    Log.i("BluetoothUtil", "btCancelDiscovery ### the bluetooth dont't discovering");
                    bluetoothAdapter.startDiscovery();
                }
		        
		        BluetoothDevice findDevice =  bluetoothAdapter.getRemoteDevice("00:11:22:33:AA:BB");
		        
		        Log.d("BluetoothUtil", "findDevice Name : " + findDevice.getName() + "  findDevice Address : "+ findDevice.getAddress() ); ;
		        Log.d("BluetoothUtil", "findDevice class" + findDevice.getBluetoothClass()); 
		        
//				Set<BluetoothDevice> listBluetoothDevice =bluetoothAdapter.getBondedDevices();
//				for(BluetoothDevice bluetoothDevice : listBluetoothDevice){
//	            	Map<String,Object> mapDevice =new HashMap<String, Object>();
//	            	mapDevice.put("name", bluetoothDevice.getName());
//	            	mapDevice.put("address", bluetoothDevice.getAddress());
//	            	listDevice.add(mapDevice);
//				}
					
//				bluetoothAdapter.startDiscovery();
	//	
//				BroadcastReceiver mReceiver = new BroadcastReceiver() {  
//				    public void onReceive(Context context, Intent intent) {  
//				        String action = intent.getAction();  
//				        //找到设备  
//				        if (BluetoothDevice.ACTION_FOUND.equals(action)) {  
//				            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);  
//				  
//				            if (device.getBondState() != BluetoothDevice.BOND_BONDED) {  
//				            	Map<String,Object> mapDevice =new HashMap<String, Object>();
//				            	mapDevice.put("name", device.getName());
//				            	mapDevice.put("name", device.getAddress());
//				            	listDevice.add(mapDevice);
	//	
//				            }  
//				        }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
//				        	//搜索完成  
//				            
//				        }  
//				    }  
//				};  
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		

		
		return listDevice;
	}

	
    //蓝牙开个状态以及扫描状态的广播接收器
    private BroadcastReceiver BluetoothReciever = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // TODO Auto-generated method stub
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction()))
            {
                Log.d("BluetoothUtil", "### Bluetooth State has changed ##");

                int btState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.STATE_OFF);

                printBTState(btState);
            }
            else if(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(intent.getAction()))
            {
            	Log.d("BluetoothUtil", "### ACTION_SCAN_MODE_CHANGED##");
                int cur_mode_state = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.SCAN_MODE_NONE);
                int previous_mode_state = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_SCAN_MODE, BluetoothAdapter.SCAN_MODE_NONE);
                
                Log.d("BluetoothUtil", "### cur_mode_state ##" + cur_mode_state + " ~~ previous_mode_state" + previous_mode_state);
                
            }
        }

    };

    //蓝牙扫描时的广播接收器
    private BroadcastReceiver BTDiscoveryReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // TODO Auto-generated method stub
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(intent.getAction()))
            {
            	Log.d("BluetoothUtil", "### BT ACTION_DISCOVERY_STARTED ##");
            }
            else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.getAction()))
            {
            	Log.d("BluetoothUtil", "### BT ACTION_DISCOVERY_FINISHED ##");
            }
            else if(BluetoothDevice.ACTION_FOUND.equals(intent.getAction()))
            {
            	Log.d("BluetoothUtil", "### BT BluetoothDevice.ACTION_FOUND ##");
                
                BluetoothDevice btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                
            	Map<String,Object> mapDevice =new HashMap<String, Object>();
            	mapDevice.put("name", btDevice.getName());
            	mapDevice.put("address", btDevice.getAddress());
            	listDevice.add(mapDevice);
               	listBluetoothDevice.add(btDevice);
               	
                if(btDevice != null)
                	Log.d("BluetoothUtil" , "Name : " + btDevice.getName() + " Address: " + btDevice.getAddress());
                    
            }
            else if(BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(intent.getAction()))
            {
            	Log.d("BluetoothUtil", "### BT ACTION_BOND_STATE_CHANGED ##");
                
                int cur_bond_state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.BOND_NONE);
                int previous_bond_state = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.BOND_NONE);
                Log.d("BluetoothUtil", "### cur_bond_state ##" + cur_bond_state + " ~~ previous_bond_state" + previous_bond_state);
                
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);  
                switch (device.getBondState()) {  
                case BluetoothDevice.BOND_BONDING:  
                    Log.d("BlueToothTestActivity", "正在配对......");  
                    break;  
                case BluetoothDevice.BOND_BONDED:  
                    Log.d("BlueToothTestActivity", "完成配对");  
                    connect(device);//连接设备  
                    break;  
                case BluetoothDevice.BOND_NONE:  
                    Log.d("BlueToothTestActivity", "取消配对");  
                default:  
                    break;  
                } 
                
               
            }
        }

    };
    
    private void printBTState(int btState)
    {
        switch (btState)
        {
            case BluetoothAdapter.STATE_OFF:
            	DialogUtil.showInfo("蓝牙状态:已关闭");
            	Log.d("BluetoothUtil", "BT State ： BluetoothAdapter.STATE_OFF ###");
                break;
            case BluetoothAdapter.STATE_TURNING_OFF:
            	DialogUtil.showInfo("蓝牙状态:正在关闭");
            	Log.d("BluetoothUtil", "BT State :  BluetoothAdapter.STATE_TURNING_OFF ###");
                break;
            case BluetoothAdapter.STATE_TURNING_ON:
            	DialogUtil.showInfo("蓝牙状态:正在打开");
            	Log.d("BluetoothUtil", "BT State ：BluetoothAdapter.STATE_TURNING_ON ###");
                break;
            case BluetoothAdapter.STATE_ON:
            	DialogUtil.showInfo("蓝牙状态:已打开");
            	Log.d("BluetoothUtil", "BT State ：BluetoothAdapter.STATE_ON ###");
                break;
            default:
                break;
        }
    }
    
    private void connect(BluetoothDevice btDev) {  
        UUID uuid = UUID.fromString(SPP_UUID);  
        try {  
        	bluetoothSocket = btDev.createInsecureRfcommSocketToServiceRecord(uuid);  
            Log.d("connect - ", "开始连接...");  
            bluetoothSocket.connect();  
            
            ReceiveThread receiveThread = new ReceiveThread(bluetoothSocket);
            receiveThread.start();
            
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
    
    
	public Map<String,Object> connectDevice(String address) {
		
		try {
			
			for( BluetoothDevice bluetoothDevice : listBluetoothDevice ){
				if( address.equals( bluetoothDevice.getAddress() ) ){
					Method createBondMethod = BluetoothDevice.class.getMethod("createBond");  
	                Log.d("BlueToothTestActivity", "开始配对");  
	                Boolean returnValue = (Boolean) createBondMethod.invoke(bluetoothDevice);
	                
	                Log.d("BluetoothUtil", returnValue.toString());

				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();  
		}

		
		
		return null;
	}
    
	public Map<String,Object> sendData(String address,String data) {
		
		try {

			OutputStream os;//输出流

			
//			if (clientSocket == null) {
//                //创建客户端蓝牙Socket
//                clientSocket = bluetoothDeviceHC.createRfcommSocketToServiceRecord(MY_UUID);
//                //开始连接蓝牙，如果没有配对则弹出对话框提示我们进行配对
//                clientSocket.connect();
//                //获得输出流（客户端指向服务端输出文本）
//                os = clientSocket.getOutputStream();
//              
//                if (os != null) {
//                    //往服务端写信息
//                	String info ="ONA";
//                	
//                	 System.out.println(info);
//                	 
//                    os.write(info.getBytes("utf-8"));
//                    
//                   
//                }
//                
//                is =clientSocket.getInputStream();
//                String result = IOUtils.toString(is, "UTF-8");
//                System.out.println("RE - "+result);
//                
//            }
			
		//获得输出流（客户端指向服务端输出文本）
		    if( bluetoothSocket==null ){
		    	BluetoothDevice bluetoothDeviceHC =null;
				for( BluetoothDevice bluetoothDevice : listBluetoothDevice ){
					if( address.equals( bluetoothDevice.getAddress() ) ){
						bluetoothDeviceHC =bluetoothDevice;
					}
				}
				
//	    		bluetoothSocket = bluetoothDeviceHC.createRfcommSocketToServiceRecord(MY_UUID);
	    		bluetoothSocket = bluetoothDeviceHC.createInsecureRfcommSocketToServiceRecord(MY_UUID);
	    		bluetoothSocket.connect();
		    	
		      
		    	
//		    	Method m = BluetoothDevice.class.getMethod( "createRfcommSocket", new Class[]{int.class});
//		    	bluetoothSocket = (BluetoothSocket) m.invoke( bluetoothDeviceHC, Integer.valueOf( 1));
	
		    }
		    
	
			os = bluetoothSocket.getOutputStream();
      
        
          if (os != null) {
              //往服务端写信息
          	 System.out.println("sendData - "+data);

             os.write(data.getBytes("utf-8"));

          }
          
//          is =bluetoothSocket.getInputStream();
//          String result = IOUtils.toString(is, "UTF-8");
//          System.out.println("RE - "+result);

		} catch (Exception e) {
			e.printStackTrace();  
		}

		
		
		return null;
	}
	
}
