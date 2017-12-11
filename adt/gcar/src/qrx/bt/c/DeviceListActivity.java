package qrx.bt.c;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DeviceListActivity extends Activity
{
  private static final boolean D = true;
  public static String EXTRA_DEVICE_ADDRESS = "device_address";
  private static final String TAG = "DeviceListActivity";
  private BluetoothAdapter mBtAdapter;
  private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener()
  {
    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
      DeviceListActivity.this.mBtAdapter.cancelDiscovery();
      String str1 = ((TextView)paramView).getText().toString();
      String str2 = str1.substring(-17 + str1.length());
      Intent localIntent = new Intent();
      localIntent.putExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS, str2);
      DeviceListActivity.this.setResult(-1, localIntent);
      DeviceListActivity.this.finish();
    }
  };
  private ArrayAdapter<String> mNewDevicesArrayAdapter;
  private ArrayAdapter<String> mPairedDevicesArrayAdapter;
  private final BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      String str1 = paramIntent.getAction();
      if ("android.bluetooth.device.action.FOUND".equals(str1))
      {
        BluetoothDevice localBluetoothDevice = (BluetoothDevice)paramIntent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
        if (localBluetoothDevice.getBondState() != 12)
        {
          String str3 = localBluetoothDevice.getName() + "\n" + localBluetoothDevice.getAddress();
          if (DeviceListActivity.this.newlstDevices.indexOf(str3) == -1)
          {
            DeviceListActivity.this.newlstDevices.add(str3);
            DeviceListActivity.this.mNewDevicesArrayAdapter.notifyDataSetChanged();
          }
        }
      }
      do
      {
        do
          return;
        while (!"android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(str1));
//        DeviceListActivity.this.setProgressBarIndeterminateVisibility(false);//AAAA
//        DeviceListActivity.this.setTitle(2130968579);
      }
      while (DeviceListActivity.this.mNewDevicesArrayAdapter.getCount() != 0);
//      String str2 = DeviceListActivity.this.getResources().getText(2130968581).toString();//AAAA
//      DeviceListActivity.this.mNewDevicesArrayAdapter.add(str2);
    }
  };
  List<String> newlstDevices = new ArrayList();

  private void doDiscovery()
  {
    Log.d("DeviceListActivity", "doDiscovery()");
    setProgressBarIndeterminateVisibility(true);
    setTitle(2130968580);
    findViewById(2131034118).setVisibility(0);
    if (this.mBtAdapter.isDiscovering())
      this.mBtAdapter.cancelDiscovery();
    this.mBtAdapter.startDiscovery();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(5);
    setContentView(2130903042);
    setResult(0);
    ((Button)findViewById(2131034120)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        DeviceListActivity.this.doDiscovery();
        paramView.setVisibility(8);
      }
    });
    this.newlstDevices.clear();
    this.mPairedDevicesArrayAdapter = new ArrayAdapter(this, 2130903043);
    this.mNewDevicesArrayAdapter = new ArrayAdapter(this, 2130903043, this.newlstDevices);
    ListView localListView1 = (ListView)findViewById(2131034117);
    localListView1.setAdapter(this.mPairedDevicesArrayAdapter);
    localListView1.setOnItemClickListener(this.mDeviceClickListener);
    ListView localListView2 = (ListView)findViewById(2131034119);
    localListView2.setAdapter(this.mNewDevicesArrayAdapter);
    localListView2.setOnItemClickListener(this.mDeviceClickListener);
    IntentFilter localIntentFilter1 = new IntentFilter("android.bluetooth.device.action.FOUND");
    registerReceiver(this.mReceiver, localIntentFilter1);
    IntentFilter localIntentFilter2 = new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
    registerReceiver(this.mReceiver, localIntentFilter2);
    this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
    Set localSet = this.mBtAdapter.getBondedDevices();
    if (localSet.size() > 0)
    {
      findViewById(2131034116).setVisibility(0);
      Iterator localIterator = localSet.iterator();
      while (true)
      {
        if (!localIterator.hasNext())
          return;
        BluetoothDevice localBluetoothDevice = (BluetoothDevice)localIterator.next();
        this.mPairedDevicesArrayAdapter.add(localBluetoothDevice.getName() + "\n" + localBluetoothDevice.getAddress());
      }
    }
    String str = getResources().getText(2130968578).toString();
    this.mPairedDevicesArrayAdapter.add(str);
  }

  protected void onDestroy()
  {
    super.onDestroy();
    if (this.mBtAdapter != null)
      this.mBtAdapter.cancelDiscovery();
    unregisterReceiver(this.mReceiver);
  }
}
