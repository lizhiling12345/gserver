package com.texcnc.gserver.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Environment;
import android.util.Log;

public class FileUtil {


	    
	 /** 
     * 获取外置SD卡路径 
     * @return  应该就一条记录或空 
     */  
    public static List<String> getExtSDCardPath()  
    {  
        List<String> lResult = new ArrayList<String>();  
        try {  
            Runtime rt = Runtime.getRuntime();  
            Process proc = rt.exec("mount");  
            InputStream is = proc.getInputStream();  
            InputStreamReader isr = new InputStreamReader(is);  
            BufferedReader br = new BufferedReader(isr);  
            String line;  
            while ((line = br.readLine()) != null) {  
                if (line.contains("extSdCard"))  
                {  
                    String [] arr = line.split(" ");  
                    String path = arr[1];  
                    File file = new File(path);  
                    if (file.isDirectory())  
                    {  
                        lResult.add(path);  
                    }  
                }  
            }  
            isr.close();  
        } catch (Exception e) {  
        }  
        return lResult;  
    }  
    
    
 
    
}
