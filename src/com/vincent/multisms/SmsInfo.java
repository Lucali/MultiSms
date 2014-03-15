package com.vincent.multisms;

import java.io.Serializable;

public class SmsInfo implements Serializable{
	
	/**
	 * ʵ�����кŵĽӿڣ�������intent֮�䴫��contactpeople��Ϣ��
	 */
	private static final long serialVersionUID = 1L;
	private String person;
	private String date;
	private String body;
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return "SmsInfo [person=" + person + ", date=" + date + ", body="
				+ body + "]";
	}

	public SmsInfo() {
		super();
	}
	public SmsInfo(String person, String date, String body) {
		super();
		this.person = person;
		this.date = date;
		this.body = body;
	}

	
	



}
