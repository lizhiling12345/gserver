package qrx.bt.c;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.UUID;

public class BluetoothService
{
  private static String BufferRead;
  private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
  public static String ReadMsg = "00";
  public static ConnectedThread connectedThread;
//  private ConnectThread connectThread;//AAAA
  private final BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
  private final Handler mHandler;

  static
  {
    BufferRead = "00";
  }

  public BluetoothService(Context paramContext, Handler paramHandler)
  {
    this.mHandler = paramHandler;
  }

  public void cancelThread()
  {
    if (connectedThread != null)
    {
      connectedThread.cancel();
      connectedThread = null;
    }
  }

  public void connect(BluetoothDevice paramBluetoothDevice)
  {
    if (connectedThread != null)
    {
      connectedThread.cancel();
      connectedThread = null;
    }
    //AAAA
//    this.connectThread = new ConnectThread(paramBluetoothDevice);
//    this.connectThread.start();
  }



  public class ConnectedThread extends Thread
  {
    private  InputStream mmInStream;
    private  OutputStream mmOutStream;
    private  BluetoothSocket mmSocket;

    public ConnectedThread(BluetoothSocket arg2)
    {
    	
    	this.mmSocket = arg2;
    	
//      Object localObject;
//      this.mmSocket = localObject;
//      InputStream localInputStream = null;
//      try
//      {
//        localInputStream = localObject.getInputStream();
//        OutputStream localOutputStream2 = localObject.getOutputStream();
//        localOutputStream1 = localOutputStream2;
//        this.mmInStream = localInputStream;
//        this.mmOutStream = localOutputStream1;
//        return;
//      }
//      catch (IOException localIOException)
//      {
//        while (true)
//          OutputStream localOutputStream1 = null;
//      }
    }

    public void cancel()
    {
      try
      {
        this.mmSocket.close();
        return;
      }
      catch (IOException localIOException)
      {
      }
    }

    public void run()
    {
      byte[] arrayOfByte = new byte[1024];
      try
      {
        while (true)
          this.mmInStream.read(arrayOfByte);
      }
      catch (IOException localIOException)
      {
      }
    }

    public void write(byte[] paramArrayOfByte)
    {
      try
      {
        this.mmOutStream.write(paramArrayOfByte);
        return;
      }
      catch (IOException localIOException)
      {
      }
    }
  }
}
