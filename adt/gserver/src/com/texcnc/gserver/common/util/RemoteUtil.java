package com.texcnc.gserver.common.util;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.internal.http.multipart.MultipartEntity;

public class RemoteUtil {


	public static String Method_GET ="GET";
	public static String Method_POST ="POST";
	public static String Method_DELETE ="DELETE";
	public static String Method_PUT ="PUT";
	public static String Charset_Header ="utf-8";//utf-8 gb2312 gbk
	public static String Charset_Result ="utf-8";
	
	private HttpClient httpClient;
	private HttpClient httpClientSSL;
	
	public RemoteUtil(){
		httpClient = new DefaultHttpClient();
	}

	private HttpClient getHttpClient(String strURL){
		if( strURL!=null && !strURL.equals( "" ) ){
			if( strURL.toUpperCase().indexOf("HTTPS")>-1 ){
				return httpClientSSL;
			}
		}
		return httpClient;
	}

	
	
	public String invokeUrlForString(String strMethod,String strURL,Map<String, String> mapParam,String strEntity){
		
		try {
			
			String strURLAll = strURL;
			HttpResponse response =null;
			
			if( Method_GET.equals( strMethod ) ){
				
				HttpGet httpGet = new HttpGet(strURLAll);   
//				httpGet.setHeader("Content-Type", "application/json; charset="+Charset_Header);
				response = this.getHttpClient(strURLAll).execute(httpGet);
				
			}else if( Method_DELETE.equals( strMethod ) ){
				
				HttpDelete httpDelete = new HttpDelete(strURLAll);  
//				httpGet.setHeader("Content-Type", "application/json; charset="+Charset_Header);
				response = this.getHttpClient(strURLAll).execute(httpDelete);
				
			}else if( Method_PUT.equals( strMethod ) ){
				
				HttpPut httpPut = new HttpPut(strURLAll);  
//				httpGet.setHeader("Content-Type", "application/json; charset="+Charset_Header);
				response = this.getHttpClient(strURLAll).execute(httpPut);
				
			}else{
				HttpPost httpPost = new HttpPost(strURLAll);
				
				if( strEntity!=null && !"".equals( strEntity ) ){
					StringEntity stringEntity = new StringEntity(strEntity,Charset_Header);
					stringEntity.setContentType("application/json;charset="+Charset_Header);
					httpPost.setEntity(stringEntity);
				}else{
					List<NameValuePair> listParam =new ArrayList<NameValuePair>();
					if( mapParam!=null ){
						for (Map.Entry<String, String> entry : mapParam.entrySet()) {
							listParam.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
						}
					}
					UrlEncodedFormEntity urlEncodedFormEntity =new UrlEncodedFormEntity(listParam,Charset_Header);
					httpPost.setEntity(urlEncodedFormEntity);
				}
				response = this.getHttpClient(strURLAll).execute(httpPost);
			}
			
			InputStream inputStream = null;
			StringBuffer sb = new StringBuffer("");
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					
					String strTemp =EntityUtils.toString(entity);
					sb.append( strTemp );
					
//					inputStream = entity.getContent();
//					byte b[] = new byte[1024];
//					
//					int i = 0;
//					while ((i = inputStream.read(b)) != -1) {
//						sb.append(new String(b, 0, i));
//					}
				}
			}else if (HttpStatus.SC_INTERNAL_SERVER_ERROR == response.getStatusLine().getStatusCode()){
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					
					String strTemp =EntityUtils.toString(entity);
					sb.append( strTemp );
					
//					inputStream = entity.getContent();
//					byte b[] = new byte[1024];
//					
//					int i = 0;
//					while ((i = inputStream.read(b)) != -1) {
//						sb.append(new String(b, 0, i,Charset_Result));
//					}
				}
			}else{
				System.out.println("Exception - "+response.getStatusLine().getStatusCode());
			}
			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public String invokeUrlForStringContentTypeJson(String strMethod,String strURL,Map<String, String> mapParam,String strEntity){
		
		try {
			
			String strURLAll = strURL;
			HttpResponse response =null;
			
			if( Method_GET.equals( strMethod ) ){
				
				HttpGet httpGet = new HttpGet(strURLAll);   
				httpGet.setHeader("Content-Type", "application/json;charset="+Charset_Header);
				response = this.getHttpClient(strURLAll).execute(httpGet);
				
			}else if( Method_DELETE.equals( strMethod ) ){
				
				HttpDelete httpDelete = new HttpDelete(strURLAll);  
				httpDelete.setHeader("Content-Type", "application/json;charset="+Charset_Header);
				response = this.getHttpClient(strURLAll).execute(httpDelete);
				
			}else if( Method_PUT.equals( strMethod ) ){
				
				HttpPut httpPut = new HttpPut(strURLAll);  
				httpPut.setHeader("Content-Type", "application/json;charset="+Charset_Header);
				response = this.getHttpClient(strURLAll).execute(httpPut);
				
			}else{
				HttpPost httpPost = new HttpPost(strURLAll);
				
				if( strEntity!=null && !"".equals( strEntity ) ){
					StringEntity stringEntity = new StringEntity(strEntity,Charset_Header);
					stringEntity.setContentType("application/json;charset="+Charset_Header);
					httpPost.setEntity(stringEntity);
				}else{
					List<NameValuePair> listParam =new ArrayList<NameValuePair>();
					if( mapParam!=null ){
						for (Map.Entry<String, String> entry : mapParam.entrySet()) {
							listParam.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
						}
					}
					UrlEncodedFormEntity urlEncodedFormEntity =new UrlEncodedFormEntity(listParam,Charset_Header);
					urlEncodedFormEntity.setContentType("application/json;charset="+Charset_Header);
					httpPost.setEntity(urlEncodedFormEntity);
				}
				response = this.getHttpClient(strURLAll).execute(httpPost);
			}
			
			InputStream inputStream = null;
			StringBuffer sb = new StringBuffer("");
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					
					String strTemp =EntityUtils.toString(entity);
					sb.append( strTemp );

				}
			}else if (HttpStatus.SC_INTERNAL_SERVER_ERROR == response.getStatusLine().getStatusCode()){
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					
					String strTemp =EntityUtils.toString(entity);
					sb.append( strTemp );

				}
			}else{
				System.out.println("Exception - "+response.getStatusLine().getStatusCode());
			}
			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	

	public String invokeUrlForStringTokenHeader(String strMethod,String strURL,Map<String, String> mapParam,String strEntity,Map<String, String> mapHeader){
		
		try {
			
			String strURLAll = strURL;
			HttpResponse response =null;
			
			if( Method_GET.equals( strMethod ) ){
				
				HttpGet httpGet = new HttpGet(strURLAll);   
				for( Entry<String,String> entryTemp : mapHeader.entrySet()  ){
					httpGet.setHeader(entryTemp.getKey(), entryTemp.getValue());
				}			
				response = this.getHttpClient(strURLAll).execute(httpGet);
				
			}else if( Method_DELETE.equals( strMethod ) ){
				
				HttpDelete httpDelete = new HttpDelete(strURLAll);  
				for( Entry<String,String> entryTemp : mapHeader.entrySet()  ){
					httpDelete.setHeader(entryTemp.getKey(), entryTemp.getValue());
				}
				response = this.getHttpClient(strURLAll).execute(httpDelete);
				
			}else if( Method_PUT.equals( strMethod ) ){
				
				HttpPut httpPut = new HttpPut(strURLAll);  
				for( Entry<String,String> entryTemp : mapHeader.entrySet()  ){
					httpPut.setHeader(entryTemp.getKey(), entryTemp.getValue());
				}
				response = this.getHttpClient(strURLAll).execute(httpPut);
				
			}else{
				HttpPost httpPost = new HttpPost(strURLAll);

				if( strEntity!=null && !"".equals( strEntity ) ){
					StringEntity stringEntity = new StringEntity(strEntity,Charset_Header);
					stringEntity.setContentType("application/json;charset="+Charset_Header);
					httpPost.setEntity(stringEntity);
				}else{
					List<NameValuePair> listParam =new ArrayList<NameValuePair>();
					if( mapParam!=null ){
						for (Map.Entry<String, String> entry : mapParam.entrySet()) {
							listParam.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
						}
					}
					UrlEncodedFormEntity urlEncodedFormEntity =new UrlEncodedFormEntity(listParam,Charset_Header);
					httpPost.setEntity(urlEncodedFormEntity);
				}
				
				for( Entry<String,String> entryTemp : mapHeader.entrySet()  ){
					httpPost.setHeader(entryTemp.getKey(), entryTemp.getValue());
				}
				
				response = this.getHttpClient(strURLAll).execute(httpPost);
			}
			
			InputStream inputStream = null;
			StringBuffer sb = new StringBuffer("");
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					
					String strTemp =EntityUtils.toString(entity);
					sb.append( strTemp );
					
//					inputStream = entity.getContent();
//					byte b[] = new byte[1024];
//					
//					int i = 0;
//					while ((i = inputStream.read(b)) != -1) {
//						sb.append(new String(b, 0, i));
//					}
				}
			}else if (HttpStatus.SC_INTERNAL_SERVER_ERROR == response.getStatusLine().getStatusCode()){
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					
					String strTemp =EntityUtils.toString(entity);
					sb.append( strTemp );
					
//					inputStream = entity.getContent();
//					byte b[] = new byte[1024];
//					
//					int i = 0;
//					while ((i = inputStream.read(b)) != -1) {
//						sb.append(new String(b, 0, i,Charset_Result));
//					}
				}
			}else{
				System.out.println("Exception - "+response.getStatusLine().getStatusCode());
			}
			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Map invokeUrlForJsonMap(String strMethod,String strURL,Map<String, String> mapParam,String strEntity){
		Map mapResult =null;
		String strResult =this.invokeUrlForString(strMethod, strURL, mapParam,strEntity);
		
//		LogUtil.logDebug("invokeUrlForJsonMap - "+strResult, RemoteContext.class.getSimpleName());
		
		if( strResult!=null && !"".equals( strResult ) ){
			mapResult =JsonUtil.parseJsonStrToMap(strResult);
		}
		return mapResult;
	}
	
	public List invokeUrlForJsonList(String strMethod,String strURL,Map<String, String> mapParam,String strEntity){
		List listResult =null;
		String strResult =this.invokeUrlForString(strMethod, strURL, mapParam,strEntity);
		if( strResult!=null && !"".equals( strResult ) ){
			listResult =JsonUtil.parseJsonStrToList(strResult);
		}
		return listResult;
	}
	
	public JSONArray invokeUrlForJsonArray(String strMethod,String strURL,Map<String, String> mapParam,String strEntity) {
		JSONArray jSONArray =null;
			String strResult =this.invokeUrlForString(strMethod, strURL, mapParam,strEntity);
			if( strResult!=null && !"".equals( strResult ) ){
				jSONArray =jSONArray.parseArray(strResult);
			}
			return jSONArray;	
	}
	
	
	public JSONObject invokeUrlForJsonObject(String strMethod,String strURL,Map<String, String> mapParam,String strEntity){
		JSONObject jSONObject =null;
		String strResult =this.invokeUrlForString(strMethod, strURL, mapParam,strEntity);
		if( strResult!=null && !"".equals( strResult ) ){
			jSONObject =JSONObject.parseObject(strResult);
		}
		return jSONObject;	
	}
	
	
	public InputStream invokeUrlForInputStream(String strMethod,String strURL,Map<String, String> mapParam){
			
		try {
			
			String strURLAll = strURL;
			HttpResponse response =null;
			
			if( Method_GET.equals( strMethod ) ){
				
				HttpGet httpGet = new HttpGet(strURLAll);   
				response = this.getHttpClient(strURLAll).execute(httpGet);
				
			}else{
				HttpPost httpPost = new HttpPost(strURLAll);
				List<NameValuePair> listParam =new ArrayList<NameValuePair>();
				if( mapParam!=null ){
					for (Map.Entry<String, String> entry : mapParam.entrySet()) {
						listParam.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
					}
				}
				UrlEncodedFormEntity urlEncodedFormEntity =new UrlEncodedFormEntity(listParam,Charset_Header);
				httpPost.setEntity(urlEncodedFormEntity);

				response = this.getHttpClient(strURLAll).execute(httpPost);
			}
			
			InputStream inputStream = null;
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				
				System.out.println("invokeUrlForInputStream - "+response.getHeaders("Content-Type")[0]);
				
				HttpEntity entity = response.getEntity();
				if (entity != null) {

					if( "audio/mp3".equals( response.getHeaders("Content-Type")[0].getValue() ) ){
						inputStream = entity.getContent();
						return inputStream;
					}else{
						String strTemp =EntityUtils.toString(entity);
						System.out.println("invokeUrlForInputStream - "+strTemp);
						return null;
					}
					
				}

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
