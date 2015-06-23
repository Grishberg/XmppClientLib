package com.grishberg.xmpp;

/**
 * Created by G on 23.06.15.
 */
public class XmlBuilder {
	private StringBuilder body;
	public XmlBuilder(){
		body = new StringBuilder();
	}

	public XmlBuilder getConnectPacket(String server){
		body.append("<stream:stream xmlns=\"jabber:client to=\"");
		body.append(server);
		body.append("\" version=\"1.0\" xmlns:stream=\"http://etherx.jabber.org/streams\" >");
		return this;
	}

	public byte[] build(){
		byte[] result = null;
		try
		{
			result = body.toString().getBytes("UTF-8");
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
