package com.grishberg.xmpp;

import com.grishberg.xmpp.listeners.IMessageListener;
import com.grishberg.xmpp.listeners.IOnErrorListener;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by G on 23.06.15.
 */
public class XmppClient {
	private static final int BUFFER_SIZE	= 4096;
	private Socket mSocket;
	private OutputStream		mOutputStream;
	private InputStream			mInputStream;
	private IOnErrorListener 	mErrorListener;
	private IMessageListener	mListener;
	private String				mHost;
	private int					mPort;
	public XmppClient(String host, int port, IMessageListener messageListener, IOnErrorListener errorListener){

		mListener		= messageListener;
		mErrorListener	= errorListener;
		mHost			= host;
		mPort			= port;

	}

	public boolean connect(String login, String password){

		try
		{
			mSocket = new Socket(mHost, mPort);
			mInputStream	= mSocket.getInputStream();
			mOutputStream	= mSocket.getOutputStream();

			byte[] packet;
			XmlBuilder xmlBuilder	= new XmlBuilder();
			packet	= xmlBuilder.getConnectPacket(mHost).build();
			mOutputStream.write(packet);

			int readed		= 0;
			int readedTotal	= 0;

			ArrayList<byte[]> packets = new ArrayList<>(10);
			while(readed > 0){
				byte[] buffer = new byte[BUFFER_SIZE];
				readed = mInputStream.read(buffer);
				readedTotal += readed;
			}

			String data = new String(buf, 0, r);
			log(data);

		} catch (Exception e){
			if(mErrorListener != null){
				mErrorListener.onError(ErrorCodes.ERROR_CONNECTION);
				release();
				return false;
			}

		}
		return true;
	}

	public void release(){
		try {
			mSocket.close();
			mSocket	= null;
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private String packetToString(ArrayList<byte[]> packet, int size){
		byte[] buffer	= new byte[size];
		int destOffset		= 0;
		for(byte[] bytes: packet){
			System.arraycopy(bytes,0, buffer, destOffset, bytes.length );
			destOffset += bytes.length;
		}
		return new String(buffer, 0, size);
	}

	private void log(String str){
		System.out.println(str);
	}
}
