package com.hyperaccesss.api;

import java.util.ArrayList;
import com.hyperaccesss.api.Contact;

public class addBulkSms {

	private String Code;
	private String Username;
	private String Password;
	private String Sender;
	private String Sms;

	private ArrayList<Contact> contacts;

	public addBulkSms() {
		super();
		// TODO Auto-generated constructor stub
	}

	public addBulkSms(String code, String username, String password, String sender, String sms) {
		super();
		Code = code;
		Username = username;
		Password = password;
		Sender = sender;
		Sms = sms;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getSender() {
		return Sender;
	}

	public void setSender(String sender) {
		Sender = sender;
	}

	public String getSms() {
		return Sms;
	}

	public void setSms(String sms) {
		Sms = sms;
	}

	public ArrayList<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(ArrayList<Contact> contacts) {
		this.contacts = contacts;
	}

}
