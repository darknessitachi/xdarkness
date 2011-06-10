package com.abigdreamer.java.net.message;

import java.util.ArrayList;

import com.abigdreamer.java.net.data.Transaction;
import com.abigdreamer.java.net.util.Mapx;

public class MessageSender {
	private Mapx map = new Mapx();

	private ArrayList list = new ArrayList();
	private Transaction tran;
	private static long id = System.currentTimeMillis();
	private String messageName;
	private Message message;

	public void addContentVar(String varName, Object value) {
		this.map.put(varName, value);
	}

	public void setTransaction(Transaction tran) {
		this.tran = tran;
	}

	public void send() {
		Message msg = new Message();
		msg.setContent(this.map);
		msg.setTransaction(this.tran);
		msg.setName(getMessageName());
		msg.setID(getMessageName() + id++);
		this.message = msg;
		MessageBus.send(this);
	}

	public Message getMessage() {
		return this.message;
	}

	public void receiveFeedback(Mapx fmap) {
		this.list.add(fmap);
	}

	public Mapx[] getFeedback() {
		Mapx[] arr = new Mapx[this.list.size()];
		for (int i = 0; i < this.list.size(); i++) {
			arr[i] = ((Mapx) this.list.get(i));
		}
		return arr;
	}

	public String getMessageName() {
		return this.messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}
}