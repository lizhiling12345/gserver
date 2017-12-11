package com.texcnc.gserver.common.bdsdk;

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

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.texcnc.gserver.R;
import com.texcnc.gserver.common.GlobalContext;
import com.texcnc.gserver.common.util.JsonUtil;
import com.texcnc.gserver.common.util.LogUtil;
import com.texcnc.gserver.common.util.RemoteUtil;

/**
 * 语音识别
 * 
 */
public class AnalysisSoundUtil {

	public static Map<String, Object> analysis() {

		LogUtil.addLog("AnalysisSoundUtil.analysis start");
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		EventManager asr = EventManagerFactory.create(GlobalContext.getActivity(), "asr"); // this是Activity或其它Context类

		EventListener yourListener = new EventListener() {
			@Override
			public void onEvent(String name, String params, byte[] data,
					int offset, int length) {
				if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_READY)) {
					// 引擎就绪，可以说话，一般在收到此事件后通过UI通知用户可以说话了
					
					LogUtil.addLog("引擎就绪，可以说话，一般在收到此事件后通过UI通知用户可以说话了");
					
				}
				if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_FINISH)) {
					// 识别结束

					// 解析数据流，可以调用http接口
					LogUtil.addLog("解析数据流，可以调用http接口"+data);
				}
				// ... 支持的输出事件和事件支持的事件参数见“输入和输出参数”一节
			}
		};
		asr.registerListener(yourListener);

		return mapResult;
	}

}
