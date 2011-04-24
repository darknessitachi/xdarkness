package com.xdarkness.framework.messages;

import com.xdarkness.framework.util.Mapx;

public abstract class MessageReceiver {
	public String[] getMessageTypeNames() {
		return MessageBus.getMessageNames(this);
	}

	public abstract Mapx receive(Message paramMessage);
}