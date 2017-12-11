package com.texcnc.gserver.common.util;

import java.util.ArrayList;
import java.util.List;

public class LogUtil {

	private static List<String> list_log =new ArrayList<String>();
	
	public static List<String> listLog(){
		return list_log;
	}
	
	public static void addLog(String logInfo){
		list_log.add(logInfo);
	}
	
	public static void removeLog(){
		list_log.clear();
	}
	
}
