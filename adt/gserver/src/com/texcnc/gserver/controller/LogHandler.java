package com.texcnc.gserver.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;

import android.util.Log;

import com.texcnc.gserver.common.bluetooth.BluetoothUtil;
import com.texcnc.gserver.common.util.JsonUtil;
import com.texcnc.gserver.common.util.LogUtil;
import com.texcnc.gserver.common.web.BaseHandler;
import com.yanzhenjie.andserver.RequestHandler;
import com.yanzhenjie.andserver.util.HttpRequestParser;

public class LogHandler extends BaseHandler implements RequestHandler {

	@Override
	public void handle(HttpRequest request, HttpResponse response,
			HttpContext context) throws HttpException, IOException {
		Map<String, String> params = HttpRequestParser.parse(request);
		String method = params.get("m");

		try {
			if ("show".equals(method)) {
				List<String> listLog = LogUtil.listLog();
				StringBuffer sbResult = new StringBuffer("");
				for (String strTemp : listLog) {
					sbResult.append(strTemp);
					sbResult.append("</br>");
				}
				LogUtil.removeLog();
				writeToStreamForHtml(response, sbResult.toString());

			}

		} catch (Exception e) {
			Log.d("server", e.getMessage());
			writeToStreamForHtml(response, e.getMessage());
		}

	}

}