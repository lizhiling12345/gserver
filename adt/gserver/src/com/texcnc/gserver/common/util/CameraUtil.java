package com.texcnc.gserver.common.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.texcnc.gserver.R;
import com.texcnc.gserver.common.GlobalContext;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * 要能获取到每一帧的图片
 * 一般是根据语音来触发获取图像，再用于图像识别
 * 也有可能是一直获取图片，每秒识别，自动驾驶用到
 */
public class CameraUtil {

	public static List<byte[]> listStream =new ArrayList<byte[]>();
	public static byte[] currentBytes =null;
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static Uri fileUri;
	
	   public static final int MEDIA_TYPE_IMAGE = 1;
	    public static final int MEDIA_TYPE_VIDEO = 2;
	    
	public static boolean checkCameraHardware(Context context) {
	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
	        // this device has a camera
	        return true;
	    } else {
	        // no camera on this device
	        return false;
	    }
	}
	
	public static Camera getCameraInstance(){
	    Camera c = null;
	    try {
	        c = Camera.open(); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return c; // returns null if camera is unavailable
	}
	
	public static Camera.CameraInfo getCameraInfo(int cameraId) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, cameraInfo);
        return cameraInfo;
    }
	
	public static Map<String,Object> startCameraIntent() {
		// create Intent to take a picture and return control to the calling application
	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

	    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

	    // start the image capture Intent
	    GlobalContext.getActivity().startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	    
	    return null;
    }
	
	public static Map<String,Object> startCamera() {

		boolean isPreview = false; // 是否在浏览中  
		
		try {
			
			SurfaceView sView = (SurfaceView) GlobalContext.getActivity().findViewById(R.id.sView); // 获取界面中SurfaceView组件  
			SurfaceHolder surfaceHolder = sView.getHolder(); // 获得SurfaceView的SurfaceHolder  
	  
	        // 为surfaceHolder添加一个回调监听器  
//	        surfaceHolder.addCallback(new Callback() {  
//	            @Override  
//	            public void surfaceChanged(SurfaceHolder holder, int format,  
//	                    int width, int height) {  
//	            }  
//	  
//	            @Override  
//	            public void surfaceCreated(SurfaceHolder holder) {  
////	                initCamera(); // 打开摄像头  
//	            }  
//	  
//	            @Override  
//	            public void surfaceDestroyed(SurfaceHolder holder) {  
//	                // 如果camera不为null ,释放摄像头  
////	                if (camera != null) {  
////	                    if (isPreview)  
////	                        camera.stopPreview();  
////	                    camera.release();  
////	                    camera = null;  
////	                }  
////	                System.exit(0);  
//	            }  
//	        });  
	        // 设置该SurfaceView自己不维护缓冲  
	        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);  
	        
	        int screenWidth, screenHeight;  
	        screenWidth = 640;  
	        screenHeight = 480;  
	        
	    	Camera camera = Camera.open();
	        if (camera != null ) {  
	            try {  
	                Camera.Parameters parameters = camera.getParameters();  
	                parameters.setPreviewSize(screenWidth, screenHeight); // 设置预览照片的大小  
	                parameters.setPreviewFpsRange(20, 30); // 每秒显示20~30帧  
	                parameters.setPictureFormat(ImageFormat.NV21); // 设置图片格式  
	                parameters.setPictureSize(screenWidth, screenHeight); // 设置照片的大小  
	                // camera.setParameters(parameters); // android2.3.3以后不需要此行代码  
	                camera.setPreviewDisplay(surfaceHolder); // 通过SurfaceView显示取景画面  
	                camera.setPreviewCallback(new StreamIt("192.1.1.1")); // 设置回调的类  
	                camera.startPreview(); // 开始预览  
	                camera.autoFocus(null); // 自动对焦  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	            isPreview = true;  
	        }  
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        
	
		
		
		
		
	    return null;
    }
	
	
	/** Create a file Uri for saving an image or video */
	public static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}
	
	   public static File getOutputMediaFile(int type) {
	        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	                Environment.DIRECTORY_PICTURES), "Camera App");
	        // This location works best if you want the created images to be shared
	        // between applications and persist after your app has been uninstalled.

	        // Create the storage directory if it does not exist
	        if (! mediaStorageDir.exists()){
	            if (! mediaStorageDir.mkdirs()){
	                Log.d("linc", "failed to create directory");
	                return null;
	            }
	        }

	        // Create a media file name
	        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	        File mediaFile;
	        if (type == MEDIA_TYPE_IMAGE){
	            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	                    "ZIMG_"+ timeStamp + ".jpg");
	        } else if(type == MEDIA_TYPE_VIDEO) {
	            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	                    "ZVID_"+ timeStamp + ".mp4");
	        } else {
	            return null;
	        }

	        return mediaFile;
	    }
	   
}
