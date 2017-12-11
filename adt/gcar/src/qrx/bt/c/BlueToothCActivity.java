package qrx.bt.c;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.Toast;
import java.io.PrintStream;

public class BlueToothCActivity extends Activity
  implements SensorEventListener
{
  static Boolean Isconnect = Boolean.valueOf(false);
  public static final int MESSAGE_TOAST = 5;
  private static final int REQUEST_CONNECT_DEVICE = 1;
  private static final int REQUEST_ENABLE_BT = 0;
  public static final String TOAST = "toast";
  public static BluetoothDevice device;
  static float sensorValueX;
  static float sensorValueY;
  private Button bluetoothbtn;
  private Button button1;
  private Button button2;
  private Button button3;
  private Button button4;
  private Button button5;
  private Button button6;
  private Button button7;
  private Button button8;
  private Button button9;
  private byte[] byte1 = "ON1".getBytes();
  private byte[] byte2 = "ON2".getBytes();
  private byte[] byte3 = "ON3".getBytes();
  private byte[] byte4 = "ON4".getBytes();
  private byte[] byte5 = "ON5".getBytes();
  private byte[] byte6 = "ON6".getBytes();
  private byte[] byte7 = "ON7".getBytes();
  private byte[] byte8 = "ON8".getBytes();
  private byte[] byte9 = "ON9".getBytes();
  private byte[] bytedown = "ONB".getBytes();
  private byte[] bytef = "ONF".getBytes();
  private byte[] byteleft = "ONC".getBytes();
  private byte[] byteright = "OND".getBytes();
  private byte[] byteup = "ONA".getBytes();
  
  
  private View.OnTouchListener clickListener = new View.OnTouchListener()
  {
    public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
    {
//      if (BluetoothService.connectedThread != null)
//        switch (paramView.getId())
//        {
//        case 2131034124:
//        case 2131034128:
//        case 2131034129:
//        case 2131034131:
//        case 2131034132:
//        case 2131034133:
//        case 2131034134:
//        default:
//        case 2131034135:
//        case 2131034136:
//        case 2131034137:
//        case 2131034138:
//        case 2131034139:
//        case 2131034140:
//        case 2131034141:
//        case 2131034142:
//        case 2131034143:
//        case 2131034130:
//        case 2131034125:
//        case 2131034127:
//        case 2131034126:
//        case 2131034123:
//        }
//      while (true)
//      {
//        return false;
//        switch (paramMotionEvent.getAction())
//        {
//        default:
//          break;
//        case 0:
//          BluetoothService.connectedThread.write(BlueToothCActivity.this.byte1);
//          break;
//        case 1:
//          BluetoothService.connectedThread.write("ONa".getBytes());
//          continue;
//          switch (paramMotionEvent.getAction())
//          {
//          default:
//            break;
//          case 0:
//            BluetoothService.connectedThread.write(BlueToothCActivity.this.byte2);
//            break;
//          case 1:
//            BluetoothService.connectedThread.write("ONb".getBytes());
//            continue;
//            switch (paramMotionEvent.getAction())
//            {
//            default:
//              break;
//            case 0:
//              BluetoothService.connectedThread.write(BlueToothCActivity.this.byte3);
//              break;
//            case 1:
//              BluetoothService.connectedThread.write("ONc".getBytes());
//              continue;
//              switch (paramMotionEvent.getAction())
//              {
//              default:
//                break;
//              case 0:
//                BluetoothService.connectedThread.write(BlueToothCActivity.this.byte4);
//                break;
//              case 1:
//                BluetoothService.connectedThread.write("ONd".getBytes());
//                continue;
//                switch (paramMotionEvent.getAction())
//                {
//                default:
//                  break;
//                case 0:
//                  BluetoothService.connectedThread.write(BlueToothCActivity.this.byte5);
//                  break;
//                case 1:
//                  BluetoothService.connectedThread.write("ONe".getBytes());
//                  continue;
//                  switch (paramMotionEvent.getAction())
//                  {
//                  default:
//                    break;
//                  case 0:
//                    BluetoothService.connectedThread.write(BlueToothCActivity.this.byte6);
//                    break;
//                  case 1:
//                    BluetoothService.connectedThread.write("ONf".getBytes());
//                    continue;
//                    switch (paramMotionEvent.getAction())
//                    {
//                    default:
//                      break;
//                    case 0:
//                      BluetoothService.connectedThread.write(BlueToothCActivity.this.byte7);
//                      break;
//                    case 1:
//                      BluetoothService.connectedThread.write("ONg".getBytes());
//                      continue;
//                      switch (paramMotionEvent.getAction())
//                      {
//                      default:
//                        break;
//                      case 0:
//                        BluetoothService.connectedThread.write(BlueToothCActivity.this.byte8);
//                        break;
//                      case 1:
//                        BluetoothService.connectedThread.write("ONh".getBytes());
//                        continue;
//                        switch (paramMotionEvent.getAction())
//                        {
//                        default:
//                          break;
//                        case 0:
//                          BluetoothService.connectedThread.write(BlueToothCActivity.this.byte9);
//                          break;
//                        case 1:
//                          BluetoothService.connectedThread.write("ONi".getBytes());
//                          continue;
//                          switch (paramMotionEvent.getAction())
//                          {
//                          default:
//                            break;
//                          case 0:
//                            BluetoothService.connectedThread.write(BlueToothCActivity.this.bytedown);
//                            break;
//                          case 1:
//                            BluetoothService.connectedThread.write(BlueToothCActivity.this.bytef);
//                            continue;
//                            switch (paramMotionEvent.getAction())
//                            {
//                            default:
//                              break;
//                            case 0:
//                              BluetoothService.connectedThread.write(BlueToothCActivity.this.byteleft);
//                              break;
//                            case 1:
//                              BluetoothService.connectedThread.write(BlueToothCActivity.this.bytef);
//                              continue;
//                              switch (paramMotionEvent.getAction())
//                              {
//                              default:
//                                break;
//                              case 0:
//                                BluetoothService.connectedThread.write(BlueToothCActivity.this.byteright);
//                                break;
//                              case 1:
//                                BluetoothService.connectedThread.write(BlueToothCActivity.this.bytef);
//                                continue;
//                                switch (paramMotionEvent.getAction())
//                                {
//                                default:
//                                  break;
//                                case 0:
//                                  BluetoothService.connectedThread.write("ONE".getBytes());
//                                  break;
//                                case 1:
//                                  BluetoothService.connectedThread.write(BlueToothCActivity.this.bytef);
//                                  continue;
//                                  switch (paramMotionEvent.getAction())
//                                  {
//                                  default:
//                                    break;
//                                  case 0:
//                                    BluetoothService.connectedThread.write(BlueToothCActivity.this.byteup);
//                                    break;
//                                  case 1:
//                                    BluetoothService.connectedThread.write(BlueToothCActivity.this.bytef);
//                                    continue;
//                                    Toast.makeText(BlueToothCActivity.this.getApplicationContext(), "蓝牙未连接", 1).show();
//                                  }
//                                }
//                              }
//                            }
//                          }
//                        }
//                      }
//                    }
//                  }
//                }
//              }
//            }
//          }
//        }
//      }
//    }
    	return false;
    }
  };
  private ImageButton downbtn;
  private final Handler handler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default:
        return;
      case 5:
      }
      Toast.makeText(BlueToothCActivity.this.getApplicationContext(), paramMessage.getData().getString("toast"), 0).show();
    }
  };
  private ImageButton leftbtn;
  BluetoothAdapter mBluetoothAdapter = null;
  private BluetoothService mBluetoothService;
  private ImageButton rightbtn;
  private SensorManager sensorManager;
  private CheckBox sensorbtn;
  private ImageButton stopbtn;
  private ImageButton upbtn;

  public void onAccuracyChanged(Sensor paramSensor, int paramInt)
  {
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 0)
    {
      if ((paramInt2 == -1) && (this.mBluetoothService == null))
        this.mBluetoothService = new BluetoothService(this, this.handler);
      if (paramInt2 == 0)
      {
        Toast.makeText(this, getText(2130968586).toString(), 1).show();
        finish();
      }
    }
    if ((paramInt1 == 1) && (paramInt2 == -1))
    {
      String str = paramIntent.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
      device = this.mBluetoothAdapter.getRemoteDevice(str);
      System.out.println(str);
      System.out.println(device);
      this.mBluetoothService.connect(device);
      this.sensorbtn.setClickable(true);
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903044);
    getWindow().setFeatureInt(7, 2130903041);
    this.sensorManager = ((SensorManager)getSystemService("sensor"));
    this.button1 = ((Button)findViewById(2131034135));
    this.button2 = ((Button)findViewById(2131034136));
    this.button3 = ((Button)findViewById(2131034137));
    this.button4 = ((Button)findViewById(2131034138));
    this.button5 = ((Button)findViewById(2131034139));
    this.button6 = ((Button)findViewById(2131034140));
    this.button7 = ((Button)findViewById(2131034141));
    this.button8 = ((Button)findViewById(2131034142));
    this.button9 = ((Button)findViewById(2131034143));
    this.leftbtn = ((ImageButton)findViewById(2131034125));
    this.rightbtn = ((ImageButton)findViewById(2131034127));
    this.upbtn = ((ImageButton)findViewById(2131034123));
    this.downbtn = ((ImageButton)findViewById(2131034130));
    this.stopbtn = ((ImageButton)findViewById(2131034126));
    this.sensorbtn = ((CheckBox)findViewById(2131034133));
    this.bluetoothbtn = ((Button)findViewById(2131034134));
    this.button1.setOnTouchListener(this.clickListener);
    this.button2.setOnTouchListener(this.clickListener);
    this.button3.setOnTouchListener(this.clickListener);
    this.button4.setOnTouchListener(this.clickListener);
    this.button5.setOnTouchListener(this.clickListener);
    this.button6.setOnTouchListener(this.clickListener);
    this.button7.setOnTouchListener(this.clickListener);
    this.button8.setOnTouchListener(this.clickListener);
    this.button9.setOnTouchListener(this.clickListener);
    this.rightbtn.setOnTouchListener(this.clickListener);
    this.leftbtn.setOnTouchListener(this.clickListener);
    this.upbtn.setOnTouchListener(this.clickListener);
    this.downbtn.setOnTouchListener(this.clickListener);
    this.stopbtn.setOnTouchListener(this.clickListener);
    this.sensorbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
      public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
      {
        if (paramBoolean)
          if (BluetoothService.connectedThread != null)
          {
            BlueToothCActivity.this.sensorManager.registerListener(BlueToothCActivity.this, BlueToothCActivity.this.sensorManager.getDefaultSensor(1), 0);
            BlueToothCActivity.Isconnect = Boolean.valueOf(true);
//            new BlueToothCActivity.SendDataR(BlueToothCActivity.this).start();
          }
        do
        {
          return;
//          Toast.makeText(BlueToothCActivity.this.getApplicationContext(), "蓝牙未连接", 1).show();
//          return;
//          BlueToothCActivity.Isconnect = Boolean.valueOf(false);
        }
        while (BlueToothCActivity.this.sensorManager == null);
//        BlueToothCActivity.this.sensorManager.unregisterListener(BlueToothCActivity.this);
      }
    });
    this.sensorbtn.setClickable(false);
    this.bluetoothbtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(BlueToothCActivity.this, DeviceListActivity.class);
        BlueToothCActivity.this.startActivityForResult(localIntent, 1);
      }
    });
    this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (this.mBluetoothAdapter == null)
      Toast.makeText(this, "您的手机不支持蓝牙", 1).show();
    if (!this.mBluetoothAdapter.isEnabled())
    {
      startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 0);
      return;
    }
    this.mBluetoothService = new BluetoothService(this, this.handler);
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    paramMenu.add(1, 1, 1, "关于");
    return super.onCreateOptionsMenu(paramMenu);
  }

  protected void onDestroy()
  {
    super.onDestroy();
    Isconnect = Boolean.valueOf(false);
    if (this.sensorManager != null)
      this.sensorManager.unregisterListener(this);
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
//    if ((paramInt == 4) && (paramKeyEvent.getRepeatCount() == 0))
//    {
//      AlertDialog localAlertDialog = new AlertDialog.Builder(this).create();
//      localAlertDialog.setTitle("系统提示");
//      localAlertDialog.setMessage("确定要退出吗？");
//      localAlertDialog.setIcon(17301642);
//      5 local5 = new DialogInterface.OnClickListener(localAlertDialog)
//      {
//        public void onClick(DialogInterface paramDialogInterface, int paramInt)
//        {
//          switch (paramInt)
//          {
//          default:
//            return;
//          case -1:
//            BlueToothCActivity.this.mBluetoothAdapter.disable();
//            System.exit(0);
//            BlueToothCActivity.this.finish();
//            return;
//          case -2:
//          }
//          this.val$isExit.cancel();
//        }
//      };
//      localAlertDialog.setButton("确定", local5);
//      localAlertDialog.setButton2("取消", local5);
//      localAlertDialog.show();
//    }
    return false;
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    Intent localIntent = new Intent();
    localIntent.setClass(this, About.class);
    startActivity(localIntent);
    return super.onOptionsItemSelected(paramMenuItem);
  }

  public void onSensorChanged(SensorEvent paramSensorEvent)
  {
    sensorValueX = paramSensorEvent.values[0];
    sensorValueY = paramSensorEvent.values[1];
  }

  public class SendDataR extends Thread
  {
    public SendDataR()
    {
    }

    public void run()
    {
//      while (true)
//      {
//        if (!BlueToothCActivity.Isconnect.booleanValue())
//          return;
//        if ((BlueToothCActivity.sensorValueX > 5.0F) && (BlueToothCActivity.sensorValueY > -1.0F) && (BlueToothCActivity.sensorValueY < 3.0F))
//          BluetoothService.connectedThread.write(BlueToothCActivity.this.bytedown);
//        try
//        {
//          Thread.sleep(100L);
//          if ((BlueToothCActivity.sensorValueX < 2.0F) && (BlueToothCActivity.sensorValueY > -1.0F) && (BlueToothCActivity.sensorValueY < 3.0F))
//            BluetoothService.connectedThread.write(BlueToothCActivity.this.byteup);
//        }
//        catch (InterruptedException localInterruptedException3)
//        {
//          try
//          {
//            Thread.sleep(100L);
//            if (BlueToothCActivity.sensorValueY < -1.0F)
//              BluetoothService.connectedThread.write(BlueToothCActivity.this.byteleft);
//          }
//          catch (InterruptedException localInterruptedException3)
//          {
//            try
//            {
//              while (true)
//              {
//                while (true)
//                {
//                  Thread.sleep(100L);
//                  if (BlueToothCActivity.sensorValueY <= 3.0F)
//                    break;
//                  BluetoothService.connectedThread.write(BlueToothCActivity.this.byteright);
//                  try
//                  {
//                    Thread.sleep(100L);
//                  }
//                  catch (InterruptedException localInterruptedException1)
//                  {
//                    localInterruptedException1.printStackTrace();
//                  }
//                }
//                break;
//                localInterruptedException4 = localInterruptedException4;
//                localInterruptedException4.printStackTrace();
//              }
//              localInterruptedException3 = localInterruptedException3;
//              localInterruptedException3.printStackTrace();
//            }
//            catch (InterruptedException localInterruptedException2)
//            {
//              while (true)
//                localInterruptedException2.printStackTrace();
//            }
//          }
//        }
//      }
    }
  }
}

