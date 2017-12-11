package com.texcnc.gserver.common.util;

import java.io.ByteArrayOutputStream;

import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;

public class StreamIt implements Camera.PreviewCallback {  
    private String ipname;  
  
    public StreamIt(String ipname) {  
        this.ipname = ipname;  
    }  
  
    @Override  
    public void onPreviewFrame(byte[] data, Camera camera) {  
        Size size = camera.getParameters().getPreviewSize();  
        try {  
            // 调用image.compressToJpeg（）将YUV格式图像数据data转为jpg格式  
            YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width,   size.height, null);  
            if (image != null) {  
                ByteArrayOutputStream outstream = new ByteArrayOutputStream();  
                image.compressToJpeg(new Rect(0, 0, size.width, size.height),   80, outstream);  
                
                CameraUtil.currentBytes =outstream.toByteArray();
//                CameraUtil.listStream.add(outstream.toByteArray());
                
                outstream.close();
//                outstream.flush();  
//                System.out.println(outstream.toByteArray().length);
                
                
                // 启用线程将图像数据发送出去  
//                Thread th = new MyThread(outstream, ipname);  
//                th.start();  
            }  
        } catch (Exception ex) {  
            Log.e("Sys", "Error:" + ex.getMessage());  
        }  
    }  
}  
