package com.example.android.BluetoothChat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

public class ClsUtils {
	 
    public ClsUtils() {
        // TODO Auto-generated constructor stub
    }
     /** 
     * ���豸��� �ο�Դ�룺platform/packages/apps/Settings.git 
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java 
     */ 
    static public boolean createBond(Class<? extends BluetoothDevice> btClass, BluetoothDevice btDevice)  
    throws Exception  
    {  
         
        Method createBondMethod = btClass.getMethod("createBond");  
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);  
        return returnValue.booleanValue();  
    }  
    
    /** 
     * ���豸������ �ο�Դ�룺platform/packages/apps/Settings.git 
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java 
     */ 
    static public boolean removeBond(Class<? extends BluetoothDevice> btClass, BluetoothDevice btDevice)  
            throws Exception  
    {  
        Method removeBondMethod = btClass.getMethod("removeBond");  
        Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);  
        return returnValue.booleanValue();  
    }  
    
    static public boolean setPin(Class btClass, BluetoothDevice btDevice,  
            String str) throws Exception  
    {  
        try 
        {  
            Method removeBondMethod = btClass.getDeclaredMethod("setPin",  
                    new Class[]  
                    {byte[].class});  
            Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice,  
                    new Object[]  
                    {str.getBytes()});  
            Log.e("returnValue��������", "" + returnValue.booleanValue());  
            return returnValue.booleanValue();  
        }  
        catch (SecurityException e)  
        {  
            // throw new RuntimeException(e.getMessage());  
            e.printStackTrace();  
        }  
        catch (IllegalArgumentException e)  
        {  
            // throw new RuntimeException(e.getMessage());  
            e.printStackTrace();  
        }  
        catch (Exception e)  
        {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return false;  
    
    }  
    
    // ȡ���û�����  
    static public boolean cancelPairingUserInput(Class<?> btClass,  
            BluetoothDevice device)  
    
    throws Exception  
    {  
        Method createBondMethod = btClass.getMethod("cancelPairingUserInput");  
        cancelBondProcess(btClass,device) ; 
        Boolean returnValue = (Boolean) createBondMethod.invoke(device);  
        Log.i("ȡ���Ի���","cancelPairingUserInput"+returnValue.booleanValue());
        return returnValue.booleanValue();  
    }  
    
    // ȡ�����  
    static public boolean cancelBondProcess(Class<?> btClass,  
            BluetoothDevice device)  
    
    throws Exception  
    {  
        Method createBondMethod = btClass.getMethod("cancelBondProcess");  
        Boolean returnValue = (Boolean) createBondMethod.invoke(device);  
        return returnValue.booleanValue();  
    }  
    
    /** 
     * 
     * @param clsShow 
     */ 
    static public void printAllInform(Class<?> clsShow)  
    {  
        try 
        {  
            // ȡ�����з���  
            Method[] hideMethod = clsShow.getMethods();  
            int i = 0;  
            for (; i < hideMethod.length; i++)  
            {  
                Log.e("method name", hideMethod[i].getName() + ";and the i is:" 
                        + i);  
            }  
            // ȡ�����г���  
            Field[] allFields = clsShow.getFields();  
            for (i = 0; i < allFields.length; i++)  
            {  
                Log.e("Field name", allFields[i].getName());  
            }  
        }  
        catch (SecurityException e)  
        {  
            // throw new RuntimeException(e.getMessage());  
            e.printStackTrace();  
        }  
        catch (IllegalArgumentException e)  
        {  
            // throw new RuntimeException(e.getMessage());  
            e.printStackTrace();  
        }  
        catch (Exception e)  
        {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
           
       
    static public boolean pair(String strAddr, String strPsw)  
    {  
        boolean result = false;  
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter  
                .getDefaultAdapter();  
    
        bluetoothAdapter.cancelDiscovery();  
    
        if (!bluetoothAdapter.isEnabled())  
        {  
            bluetoothAdapter.enable();  
        }  
    
         
    
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(strAddr);  
    
        if (device.getBondState() != BluetoothDevice.BOND_BONDED)  
        {  
            try 
            {  
                Log.d("mylog", "NOT BOND_BONDED");  
                boolean flag1=ClsUtils.setPin(device.getClass(), device, strPsw); // �ֻ��������ɼ������  
                boolean flag2=ClsUtils.createBond(device.getClass(), device);  
//                remoteDevice = device; // �����ϾͰ�����豸���󴫸�ȫ�ֵ�remoteDevice 
               
                     result = true;  
               
                
            }  
            catch (Exception e)  
            {  
                // TODO Auto-generated catch block  
    
                Log.d("mylog", "setPiN failed!");  
                e.printStackTrace();  
            } //  
    
        }  
        else 
        {  
            Log.d("mylog", "HAS BOND_BONDED");  
            try 
            {  
                ClsUtils.removeBond(device.getClass(), device);
                //ClsUtils.createBond(device.getClass(), device);  
               boolean flag1= ClsUtils.setPin(device.getClass(), device, strPsw); // �ֻ��������ɼ������  
                boolean flag2=ClsUtils.createBond(device.getClass(), device);  
//                remoteDevice = device; // ����󶨳ɹ�����ֱ�Ӱ�����豸���󴫸�ȫ�ֵ�remoteDevice 
            
                 result = true;  
              
                  
            }  
            catch (Exception e)  
            {  
                // TODO Auto-generated catch block  
                Log.d("mylog", "setPiN failed!");  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }  
 
}
