package com.lzc.jiaowaimai.activity.bean;

import android.graphics.Bitmap;

public class CurrentUser
{
	String userid;
	Bitmap userpic;
	String username;
	String password;
	String phone;
	int balance;
	String addressid;
	String redpackageid;
	public String getUserid()
	{
		return userid;
	}
	public void setUserid(String userid)
	{
		this.userid = userid;
	}
	public Bitmap getUserpic()
	{
		return userpic;
	}
	public void setUserpic(Bitmap userpic)
	{
		this.userpic = userpic;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public int getBalance()
	{
		return balance;
	}
	public void setBalance(int balance)
	{
		this.balance = balance;
	}
	public String getAddressid()
	{
		return addressid;
	}
	public void setAddressid(String addressid)
	{
		this.addressid = addressid;
	}
	public String getRedpackageid()
	{
		return redpackageid;
	}
	public void setRedpackageid(String redpackageid)
	{
		this.redpackageid = redpackageid;
	}
	
}
