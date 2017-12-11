package com.texcnc.gserver.common.util;

import java.lang.reflect.Field;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class HostUtil {

	/**
	 * 获取ip地址
	 * 
	 * @return
	 */
	public static String getHostIP() {
		String hostIp = null;
		try {
			Enumeration nis = NetworkInterface.getNetworkInterfaces();
			InetAddress ia = null;
			while (nis.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) nis.nextElement();
				Enumeration<InetAddress> ias = ni.getInetAddresses();
				while (ias.hasMoreElements()) {
					ia = ias.nextElement();
					if (ia instanceof Inet6Address) {
						continue;// skip ipv6
					}
					String ip = ia.getHostAddress();
					if (!"127.0.0.1".equals(ip)) {
						hostIp = ia.getHostAddress();
						break;
					}
				}
			}
		} catch (SocketException e) {
			Log.i("yao", "SocketException");
			e.printStackTrace();
		}
		return hostIp;

	}

	public static String getWifiIP(WifiManager wifiMan) {

		WifiInfo info = wifiMan.getConnectionInfo();
		String mac = info.getMacAddress();// 获得本机的MAC地址
		String ssid = info.getSSID();// 获得本机所链接的WIFI名称

		int ipAddress = info.getIpAddress();
		String ipString = "";// 本机在WIFI状态下路由分配给的IP地址

		// 获得IP地址的方法一：
		if (ipAddress != 0) {
			ipString = ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff)
					+ "." + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
		}

		// // 获得IP地址的方法二（反射的方法）：
		// try {
		// Field field = info.getClass().getDeclaredField("mIpAddress");
		// field.setAccessible(true);
		// ipString = (String) field.get(info);
		//
		// if( ipString==null ){
		// ipString ="";
		// }
		//
		// ipString =ipString+" - "+mac+" - "+ssid;
		// return ipString;
		//
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// Log.d("server", e.getMessage());
		// e.printStackTrace();
		// }

		return ipString;
	}
}
