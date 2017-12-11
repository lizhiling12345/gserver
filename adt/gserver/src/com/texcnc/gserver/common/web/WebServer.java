package com.texcnc.gserver.common.web;

import java.util.Map;
import android.content.res.AssetManager;
import android.util.Log;

import com.texcnc.gserver.controller.BluetoothHandler;
import com.texcnc.gserver.controller.CameraHandler;
import com.texcnc.gserver.controller.FileHandler;
import com.texcnc.gserver.controller.LogHandler;
import com.texcnc.gserver.controller.RecordHandler;
import com.texcnc.gserver.controller.SoundHandler;
import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;
import com.yanzhenjie.andserver.website.AssetsWebsite;
import com.yanzhenjie.andserver.website.WebSite;

public class WebServer {

	private static WebServer webServer;

	private WebServer() {

	}

	public static WebServer getInstance() {
		if (webServer == null) {
			webServer = new WebServer();
		}
		return webServer;
	}

	public Map<String, Object> startServer(AssetManager assetManager) {
		// 启动服务器

		WebSite webSite = new AssetsWebsite(assetManager, "");

		AndServer andServer = new AndServer.Build()
				.port(10080)
				// 设置端口号
				.timeout(10 * 1000)
				// 设置连接超时时间
				.website(webSite)
				.registerHandler("log", new LogHandler())
				.registerHandler("bluetooth", new BluetoothHandler())
				.registerHandler("file", new FileHandler())
				.registerHandler("camera", new CameraHandler())
				.registerHandler("sound", new SoundHandler())
				.registerHandler("record", new RecordHandler())
				.listener(serverListener).build();
		// 创建服务器
		Server server = andServer.createServer();
		// 启动服务器
		server.start();

		return null;
	}

	private Server.Listener serverListener = new Server.Listener() {
		@Override
		public void onStarted() {
			Log.d("WebServer", "WebServer start");
		}

		@Override
		public void onStopped() {
			Log.d("WebServer", "WebServer stop");
		}

		@Override
		public void onError(Exception e) {
			e.printStackTrace();
			Log.d("WebServer", "WebServer error");
		}
	};

}
