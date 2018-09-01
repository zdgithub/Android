package com.seven.health.bean;

import java.sql.Date;

public class RegisterBean {
	private int doctor_id;
	private String doctor_date;
	private String user_phone;
	private int half;
	
	
	public RegisterBean() {
		super();
	}

	public RegisterBean(int doctor_id, String doctor_date, String user_phone, int half) {
		super();
		this.doctor_id = doctor_id;
		this.doctor_date = doctor_date;
		this.user_phone = user_phone;
		this.half = half;
	}

	public int getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}

	public String getDoctor_date() {
		return doctor_date;
	}

	public void setDoctor_date(String doctor_date) {
		this.doctor_date = doctor_date;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public int getHalf() {
		return half;
	}

	public void setHalf(int half) {
		this.half = half;
	}
	
	
	
	
	
}
