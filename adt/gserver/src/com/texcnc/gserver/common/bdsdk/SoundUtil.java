package com.texcnc.gserver.common.bdsdk;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import com.texcnc.gserver.R;
import com.texcnc.gserver.common.GlobalContext;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.SoundPool;

/**
 * 播放声音数据流，主要用于播放百度云的生成的语音pcm格式
 */
public class SoundUtil {


	
	/**
	 * 播放音频对象
	 */
	private static AudioTrack audioTrack;
	
	public Map<String,Object> play() {
		
		try {
			
			MediaPlayer mediaPlayer = new MediaPlayer();
	        mediaPlayer.setDataSource("/mnt/sdcard/bgmusic.mp3");
	        mediaPlayer.prepare(); // 准备.
	        mediaPlayer.start();




	//- SoundPool 声音池, 同时播放多个声音(混合音效). 只允许申请1M的内存空间.

	        SoundPool sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
	        int shoot1id = sp.load("/mnt/sdcard/shoot1.ogg", 1);

	        sp.play(shoot1id, 1f, 1f, 0, 0, 1.0f);
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        
		return null;
	}
	
	public static Map<String,Object> play(InputStream inputStream) {
		
		try {
			
			// AudioTrack 得到播放最小缓冲区的大小
			int bufSizeOut = AudioTrack.getMinBufferSize(8000,
					AudioFormat.CHANNEL_CONFIGURATION_MONO,
					AudioFormat.ENCODING_PCM_16BIT);
			// 实例化播放音频对象
			audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 8000,
					AudioFormat.CHANNEL_CONFIGURATION_MONO,
					AudioFormat.ENCODING_PCM_16BIT, bufSizeOut,
					AudioTrack.MODE_STREAM);
			
			audioTrack.play();
			

	    	ByteArrayOutputStream swapStream = new ByteArrayOutputStream(); 
	    	byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据 
	    	int rc = 0; 
	    	while ((rc = inputStream.read(buff, 0, 100)) > 0) { 
	    	swapStream.write(buff, 0, rc); 
	    	} 
	    	byte[] in_b = swapStream.toByteArray(); //in_b为转换之后的结果 
	    	
	    	System.out.println("play - "+in_b.length);
	    	
			audioTrack.write(in_b, 0, in_b.length);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        
		return null;
	}

}
