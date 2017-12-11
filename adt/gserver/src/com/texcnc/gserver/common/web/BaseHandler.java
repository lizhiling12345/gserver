package com.texcnc.gserver.common.web;

import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;

public class BaseHandler {

	public void writeToStreamForHtml(HttpResponse response, String strHtml) {
		try {
			StringEntity stringEntity = new StringEntity(strHtml, "utf-8");
			response.setEntity(stringEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
