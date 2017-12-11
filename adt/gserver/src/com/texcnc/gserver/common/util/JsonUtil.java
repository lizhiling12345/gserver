package com.texcnc.gserver.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtil {

	public static String entityToJsonStr(Object ojbParamIn){
		String strTemp =JSON.toJSONString(ojbParamIn,SerializerFeature.WriteMapNullValue);
		return strTemp;
	}
	
	@SuppressWarnings("rawtypes")
	public static String arrayToJsonStr(List listParamIn){
		String strTemp =JSONArray.toJSONString(listParamIn, SerializerFeature.WriteMapNullValue);
		return strTemp;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object parseJsonStrToEntity(String strParamIn,Class classParamIn){
		Object objTemp =JSONObject.parseObject(strParamIn, classParamIn);
		return objTemp;
	}
	
	
	public static JSONObject parseJsonStrToJson(String strParamIn){
		JSONObject jSONObject =JSONObject.parseObject(strParamIn);
		return jSONObject;
	}
	
	public static JSONArray parseJsonStrToJsonArray(String strParamIn){
		JSONArray jSONArray =JSONArray.parseArray(strParamIn);
		return jSONArray;
	}
	
	@SuppressWarnings("rawtypes")
	public static Map parseJsonStrToMap(String strJson){
		Map mapData =(Map)JSON.parseObject(strJson, java.util.Map.class);
		return mapData;
	}
	
	@SuppressWarnings("rawtypes")
	public static List parseJsonStrToList(String strJson){
		List listResult =(List)JSON.parseObject(strJson, java.util.List.class);
		return listResult;
	}
	
}
