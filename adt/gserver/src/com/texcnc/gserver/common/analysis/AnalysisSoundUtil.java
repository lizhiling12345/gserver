package com.texcnc.gserver.common.analysis;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Base64;
import android.util.Log;

import com.texcnc.gserver.R;
import com.texcnc.gserver.common.GlobalContext;
import com.texcnc.gserver.common.util.JsonUtil;
import com.texcnc.gserver.common.util.RemoteUtil;

/**
 * 语音识别
 *
 */
public class AnalysisSoundUtil {


    private static final String serverURL = "http://vop.baidu.com/server_api";
    private static final String serverURLTSN = "http://tsn.baidu.com/text2audio";
    
    

    	
    private static String token = "";
    private static final String testFileName = "test.pcm";
    //put your own params here
    private static final String apiKey = "OwOjlNSQ1sFQsd4KYwj0pYqj";
    private static final String secretKey = "82c61d6ad31d68478204788067184fce";
    private static final String cuid = "9642863";

    public static void runMain() throws Exception {
        getToken();
//        method1();
        method2();
    }

    private static void getToken() throws Exception {
        String getTokenURL = "https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials" + 
            "&client_id=" + apiKey + "&client_secret=" + secretKey;
        HttpURLConnection conn = (HttpURLConnection) new URL(getTokenURL).openConnection();
       String strResponse =printResponse(conn);
       Map<String,Object> mapResponse =JsonUtil.parseJsonStrToMap(strResponse);
        token = mapResponse.get("access_token").toString();
    }

    private static void method1() throws Exception {
//        File pcmFile = new File(testFileName);
        HttpURLConnection conn = (HttpURLConnection) new URL(serverURL).openConnection();

        // construct params
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("format", "pcm");
        params.put("rate", 8000);
        params.put("channel", "1");
        params.put("token", token);
        params.put("cuid", cuid);
        
        byte[] bytesFile=loadFile();
        		
        params.put("len", bytesFile.length);

        String strSpeech =Base64.encodeToString(bytesFile,Base64.DEFAULT);
       
//        System.out.println("method1 - "+bytesFile.length);
//        System.out.println("method1 - "+strSpeech);
        
        params.put("speech", strSpeech);
    
        // add request header
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        conn.setDoInput(true);
        conn.setDoOutput(true);

        // send request
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(JsonUtil.entityToJsonStr(params));
        wr.flush();
        wr.close();

        printResponse(conn);
    }

    private static void method2() throws Exception {

        HttpURLConnection conn = (HttpURLConnection) new URL(serverURL
                + "?cuid=" + cuid + "&token=" + token).openConnection();

        // add request header
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "audio/pcm; rate=8000");

        conn.setDoInput(true);
        conn.setDoOutput(true);

        // send request
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.write(loadFile());
        wr.flush();
        wr.close();

        printResponse(conn);
    }

    private static String printResponse(HttpURLConnection conn) throws Exception {
        if (conn.getResponseCode() != 200) {
            // request error
        	Log.i("PrintResponse",conn.getResponseCode()+" - "+conn.getResponseMessage());
        	
            return "";
        }
        InputStream is = conn.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        
        Log.i("PrintResponse",response.toString());
//        System.out.println("printResponse - "+response.toString());
       
        return response.toString();
    }

    private static byte[] loadFile() throws IOException {
    	
//        InputStream is = new FileInputStream(file);

    	InputStream inStream = GlobalContext.getActivity().getResources().openRawResource(R.raw.test);  
    	ByteArrayOutputStream swapStream = new ByteArrayOutputStream(); 
    	byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据 
    	int rc = 0; 
    	while ((rc = inStream.read(buff, 0, 100)) > 0) { 
    	swapStream.write(buff, 0, rc); 
    	} 
    	byte[] in_b = swapStream.toByteArray(); //in_b为转换之后的结果 
    	
    	return in_b;
//        long length = file.length();
//        byte[] bytes = new byte[1024];
//
//        int offset = 0;
//        int numRead = 0;
//        while (offset < bytes.length
//                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
//            offset += numRead;
//        }
//
//        if (offset < bytes.length) {
//            is.close();
//            throw new IOException("Could not completely read file ");
//        }
//
//        is.close();
//        return bytes;
    }
    
    
    public static String analysis(byte[] bytes){

    	try {
			
    		if( token==null || "".equals(token) ){
        		getToken();
        	}
            HttpURLConnection conn = (HttpURLConnection) new URL(serverURL
                    + "?cuid=" + cuid + "&token=" + token).openConnection();

            // add request header
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "audio/pcm; rate=8000");

            conn.setDoInput(true);
            conn.setDoOutput(true);

            // send request
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(bytes);
            wr.flush();
            wr.close();

            return printResponse(conn);
            
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    public static String analysis(LinkedList<byte[]> listBytes){

    	try {

    		if( token==null || "".equals(token) ){
        		getToken();
        	}
            HttpURLConnection conn = (HttpURLConnection) new URL(serverURL
                    + "?cuid=" + cuid + "&token=" + token).openConnection();

            // add request header
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "audio/pcm; rate=8000");

            conn.setDoInput(true);
            conn.setDoOutput(true);

            // send request
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
      	  
            for(byte[] bytes : listBytes ){
      		   wr.write(bytes);
     	   }
            
            wr.flush();
            wr.close();

            return printResponse(conn);
            
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    public static String text2Audio(String text){

    	try {
			
    		if( token==null || "".equals(token) ){
        		getToken();
        	}
//            HttpURLConnection conn = (HttpURLConnection) new URL(serverURLTSN
//                    + "?cuid=" + cuid + "&tok=" + token
//                    + "&tex=" + text+ "&lan=zh"
//                    + "&ctp=1").openConnection();

            //Content-Type：audio/mp3
            //Content-Type:application/json
            
            // add request header
//            conn.setRequestMethod("GET");
//
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//
//
//            return printResponse(conn);
            
            RemoteUtil remoteUtil =new RemoteUtil();
//            String str =remoteUtil.invokeUrlForString(RemoteUtil.Method_GET, serverURLTSN
//                    + "?cuid=" + cuid + "&tok=" + token
//                    + "&tex=" + text+ "&lan=zh"
//                    + "&ctp=1", null, null);
            
            
//            tex	必填	合成的文本，使用UTF-8编码，请注意文本长度必须小于1024字节
//            lan	必填	语言选择,填写zh
//            tok	必填	开放平台获取到的开发者 access_token
//            ctp	必填	客户端类型选择，web端填写1
//            cuid	必填	用户唯一标识，用来区分用户，填写机器 MAC 地址或 IMEI 码，长度为60以内
//            spd	选填	语速，取值0-9，默认为5中语速
//            pit	选填	音调，取值0-9，默认为5中语调
//            vol	选填	音量，取值0-9，默认为5中音量
//            per	选填	发音人选择, 0为女声，1为男声，3为情感合成-度逍遥，4为情感合成-度丫丫，默认为普通女声
            
            String url =serverURLTSN
                    + "?cuid=" + cuid + "&tok=" + token
                    + "&tex=" + text+ "&lan=zh"
                    + "&ctp=1&per=3&spd=4&pit=3&vol=9";
            
            System.out.println(url);
            
            
           MediaPlayer mediaPlayer =new MediaPlayer();
           mediaPlayer.setDataSource(url);  

//           mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
           mediaPlayer.prepare();  
           

//           mediaPlayer.setVolume(0.8f,0.8f);
           //开始播放音频  
           mediaPlayer.start();
 
//           mediaPlayer.stop();//停止播放  
//           mediaPlayer.release();//释放资源 
           
//            InputStream inputStream =remoteUtil.invokeUrlForInputStream(RemoteUtil.Method_GET, url, null);
//
//            ByteArrayOutputStream swapStream = new ByteArrayOutputStream(); 
//            byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据 
//            int rc = 0; 
//            while ((rc = inputStream.read(buff, 0, 100)) > 0) { 
//            swapStream.write(buff, 0, rc); 
//            } 
//            byte[] in_b = swapStream.toByteArray(); //in_b为转换之后的结果 
//            
//            RecordUtil.listBytesPlay.add(in_b);

//            SoundUtil.play(inputStream);
            
//            Log.i("PrintResponse",str);
            
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }

    
}
