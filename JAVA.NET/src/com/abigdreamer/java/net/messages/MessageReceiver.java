package com.abigdreamer.java.net.messages;

import com.abigdreamer.java.net.util.Mapx;

public abstract class MessageReceiver {
	public String[] getMessageTypeNames() {
		return MessageBus.getMessageNames(this);
	}

	public abstract Mapx receive(Message paramMessage);
}