// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 29.7.2023
// Description	: to store order

package model;

import java.sql.Date;
import java.util.ArrayList;

import jakarta.json.bind.annotation.JsonbDateFormat;

public class Order {
	private int orderid;
	
	@JsonbDateFormat(value = "yyyy-MM-dd")
	private Date orderdate;
	private double amount;
	private int gst;
	private double totalamount;
	private String orderstatus;
	private String deliveryaddress;
	private int memberid;
	private String token;
	private String invoiceid;
	private ArrayList<OrderItem> orderitems;
	
	public String getInvoiceid() {
		return invoiceid;
	}

	public void setInvoiceid(String invoiceid) {
		this.invoiceid = invoiceid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ArrayList<OrderItem> getOrderitems() {
		return orderitems;
	}

	public void setOrderitems(ArrayList<OrderItem> orderitems) {
		this.orderitems = orderitems;
	}

	public int getOrderid() {
		return orderid;
	}
	
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	
	public Date getOrderdate() {
		return orderdate;
	}
	
	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public int getGst() {
		return gst;
	}
	
	public void setGst(int gst) {
		this.gst = gst;
	}
	
	public double getTotalamount() {
		return totalamount;
	}
	
	public void setTotalamount(double totalamount) {
		this.totalamount = totalamount;
	}
	
	public String getOrderstatus() {
		return orderstatus;
	}
	
	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}
	
	public String getDeliveryaddress() {
		return deliveryaddress;
	}
	
	public void setDeliveryaddress(String deliveryaddress) {
		this.deliveryaddress = deliveryaddress;
	}
	
	public int getMemberid() {
		return memberid;
	}
	
	public void setMemberid(int memberid) {
		this.memberid = memberid;
	}
}