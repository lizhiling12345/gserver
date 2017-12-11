package com.texcnc.gserver.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class MyThread extends Thread {  
    private byte byteBuffer[] = new byte[1024];  
    private OutputStream outsocket;  
    private ByteArrayOutputStream myoutputstream;  
    private String ipname;  
  
    public MyThread(ByteArrayOutputStream myoutputstream, String ipname) {  
        this.myoutputstream = myoutputstream;  
        this.ipname = ipname;  
        try {  
            myoutputstream.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    public void run() {  
        try {  
            // 将图像数据通过Socket发送出去  
            Socket tempSocket = new Socket(ipname, 6000);  
            outsocket = tempSocket.getOutputStream();  
            ByteArrayInputStream inputstream = new ByteArrayInputStream(  
                    myoutputstream.toByteArray());  
            int amount;  
            while ((amount = inputstream.read(byteBuffer)) != -1) {  
                outsocket.write(byteBuffer, 0, amount);  
            }  
            myoutputstream.flush();  
            myoutputstream.close();  
            tempSocket.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
}  
