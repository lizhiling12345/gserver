package com.texcnc.gserver.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;

import android.util.Log;

import com.texcnc.gserver.common.util.CameraUtil;
import com.texcnc.gserver.common.util.JsonUtil;
import com.yanzhenjie.andserver.RequestHandler;
import com.yanzhenjie.andserver.util.HttpRequestParser;

public class CameraHandler implements RequestHandler{


    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        Map<String, String> params = HttpRequestParser.parse(request);
        String method =params.get("m");

        try {
			
        	 if ( "list".equals(method) ) {
             	
        		 CameraUtil.startCamera();
               	
               	StringBuffer sbHtml =new StringBuffer();

               	writeToStreamForHtml(response,sbHtml.toString());
                   
                   
               }else if ( "show".equals(method) ) {
  
            	   response.setHeader("Content-Type","image/jpg");//设置响应的媒体类型，这样浏览器会识别出响应的是图片
            	   ByteArrayEntity httpEntity = new ByteArrayEntity(CameraUtil.currentBytes);

            	   response.setEntity(httpEntity);
       
               } else {
  
               }
        	 
		} catch (Exception e) {
	           Log.d("server", e.getMessage());
			writeToStreamForHtml(response,e.getMessage());
		}
        
       
        
        
    }
    
    
	public void writeToStreamForHtml(HttpResponse response,String strHtml){
		try {
            StringEntity stringEntity = new StringEntity(strHtml, "utf-8");
            response.setEntity(stringEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
    
}