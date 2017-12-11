package com.texcnc.gserver.common.util;

import java.util.LinkedList;
import java.util.Map;

import com.texcnc.gserver.common.analysis.AnalysisSoundUtil;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

/**
 * 要做到能判断一句话是否结束，要通过即是解析语音的分贝
 * 录音是连续的，每一小段判断分贝，如果是有声音，就可以记录数据流，如果距离上次有声音1秒后没声音，认为是结束
 * 如果直接百度的sdk方式，应该可以自动识别是否完整的语句
 */
public class RecordUtil {

	/**
	 * 存放录入字节数组的大小
	 */
	public static LinkedList<byte[]> listBytesGlobal =new LinkedList<byte[]>();
	
	/**
	 * AudioRecord 写入缓冲区大小
	 */
	protected int bufSizeIn;
	/**
	 * 录制音频对象
	 */
	private AudioRecord audioRecord;

	/**
	 * 存放录入字节数组的大小
	 */
	private LinkedList<byte[]> listBytesRecord;
	
	public static LinkedList<byte[]> listBytesPlay;
	
	/**
	 * AudioTrack 播放缓冲大小
	 */
	private int bufSizeOut;
	/**
	 * 播放音频对象
	 */
	private AudioTrack audioTrack;

	/**
	 * 录制音频线程
	 */
	private Thread recordThread;
	/**
	 * 播放音频线程
	 */
	private Thread playThread;
	/**
	 * 让线程停止的标志
	 */
	private boolean flag = true;
	
	public Map<String,Object> startRecord() {
		
		// AudioRecord 得到录制最小缓冲区的大小
		bufSizeIn = AudioRecord.getMinBufferSize(8000,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT);
		// 实例化播放音频对象
		audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 8000,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, bufSizeIn);

		// 实例化一个链表，用来存放字节组数
		listBytesRecord = new LinkedList<byte[]>();
		
		//播放
		listBytesPlay = new LinkedList<byte[]>();
		// AudioTrack 得到播放最小缓冲区的大小
//		bufSizeOut = AudioTrack.getMinBufferSize(8000,
//				AudioFormat.CHANNEL_CONFIGURATION_MONO,
//				AudioFormat.ENCODING_PCM_16BIT);
//		// 实例化播放音频对象
//		audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 8000,
//				AudioFormat.CHANNEL_CONFIGURATION_MONO,
//				AudioFormat.ENCODING_PCM_16BIT, bufSizeOut,
//				AudioTrack.MODE_STREAM);

	        // 声音文件一秒钟buffer的大小  
		bufSizeOut = AudioTrack.getMinBufferSize(8000,  
	                AudioFormat.CHANNEL_CONFIGURATION_STEREO,  
	                AudioFormat.ENCODING_PCM_16BIT);  
	  
		audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, // 指定在流的类型  
	                // STREAM_ALARM：警告声  
	                // STREAM_MUSCI：音乐声，例如music等  
	                // STREAM_RING：铃声  
	                // STREAM_SYSTEM：系统声音  
	                // STREAM_VOCIE_CALL：电话声音  
	                  
				8000,// 设置音频数据的采样率  
	                AudioFormat.CHANNEL_CONFIGURATION_STEREO,// 设置输出声道为双声道立体声  
	                AudioFormat.ENCODING_PCM_16BIT,// 设置音频数据块是8位还是16位  
	                bufSizeOut, AudioTrack.MODE_STREAM);// 设置模式类型，在这里设置为流类型  
	        // AudioTrack中有MODE_STATIC和MODE_STREAM两种分类。  
	        // STREAM方式表示由用户通过write方式把数据一次一次得写到audiotrack中。  
	        // 这种方式的缺点就是JAVA层和Native层不断地交换数据，效率损失较大。  
	        // 而STATIC方式表示是一开始创建的时候，就把音频数据放到一个固定的buffer，然后直接传给audiotrack，  
	        // 后续就不用一次次得write了。AudioTrack会自己播放这个buffer中的数据。  
	        // 这种方法对于铃声等体积较小的文件比较合适。  
		
		// 启动录制线程
		recordThread = new Thread(new RecordSound());
		recordThread.start();
		
		// 启动播放线程
		playThread = new Thread(new PlayRecord());
		playThread.start();

		return null;
	}
	
	
	
	/**
	 * <p>
	 * 文件名称 ：RecordPlayActivity.java
	 * <p>
	 * 内容摘要 ：
	 * <p>
	 * 作者 ：吴辰彪 创建时间 ：2012-7-25 下午01:57:09 描述 :录音线程
	 */
	class RecordSound implements Runnable
	{
		@Override
		public void run()
		{
			
			try {
				
				Log.i("Record", "........recordSound run()......");
				byte[] bytes_pkg;
				// 开始录音
				audioRecord.startRecording();

				// 实例化一个字节数组，长度为最小缓冲区的长度
				byte[] inbytes = new byte[bufSizeIn];
				
				while (flag)
				{

//					listBytesGlobal =listBytes;
					
					if( listBytesGlobal.size()<100 ){

						audioRecord.read(inbytes, 0, bufSizeIn);
						bytes_pkg = inbytes.clone();

						if (listBytesRecord.size() >= 2)
						{
							listBytesRecord.removeFirst();
						}
						listBytesRecord.add(bytes_pkg);
						
						Log.i("Record", "........recordSound bytes_pkg==" + bytes_pkg.length);
						
						listBytesGlobal.add(bytes_pkg);
					}else{
						Thread.sleep(1000*1000);
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

	}

	/**
	 * <p>
	 * 文件名称 ：RecordPlayActivity.java
	 * <p>
	 * 内容摘要 ：
	 * <p>
	 * 作者 ：吴辰彪 创建时间 ：2012-7-25 下午01:57:34 描述 :播放线程
	 */
	class PlayRecord implements Runnable
	{
		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			Log.i("Record", "........playRecord run()......");
			byte[] bytes_pkg = null;
			// 开始播放
			audioTrack.play();
			// 实例化一个长度为播放最小缓冲大小的字节数组
			byte[] outBytes = new byte[bufSizeOut];
			
			while (flag)
			{
				try
				{
					if( listBytesPlay!=null && listBytesPlay.size()>0 ){
						bytes_pkg = listBytesPlay.get(0).clone();
						audioTrack.write(bytes_pkg, 0, bytes_pkg.length);

//						SpeechAnalysisUtil.analysis(bytes_pkg);
						
						listBytesPlay.clear();
					}

				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public Map<String,Object> startRecord02() {
		try {
			
			AnalysisSoundUtil.runMain();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	
}
