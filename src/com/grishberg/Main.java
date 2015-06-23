package com.grishberg;

import com.grishberg.xmpp.XmppClient;

public class Main {

    public static void main(String[] args) {
	// write your code here
        XmppClient client   = new XmppClient("jabber.ru",5222,null,null);
		client.connect("rebbe2015@jabber.ru","Qqwe!123");
		client.release();
    }
}
