package com.invoice;

import java.util.List;

public class InvoiceTarget {
 private String invNumber;
 private String createDate;
 private String dueDate;
 private String billingName;
 private String billingAddress;
 private String billingCity;
 private String billingState;
 private String billingZip;
 private List<ItemTarget> items;
 private int totalAmount;
 private String description;
public String getInvNumber() {
	return invNumber;
}
public void setInvNumber(String invNumber) {
	this.invNumber = invNumber;
}
public String getCreateDate() {
	return createDate;
}
public void setCreateDate(String createDate) {
	this.createDate = createDate;
}
public String getDueDate() {
	return dueDate;
}
public void setDueDate(String dueDate) {
	this.dueDate = dueDate;
}
public String getBillingName() {
	return billingName;
}
public void setBillingName(String billingName) {
	this.billingName = billingName;
}
public String getBillingAddress() {
	return billingAddress;
}
public void setBillingAddress(String billingAddress) {
	this.billingAddress = billingAddress;
}
public String getBillingCity() {
	return billingCity;
}
public void setBillingCity(String billingCity) {
	this.billingCity = billingCity;
}
public String getBillingState() {
	return billingState;
}
public void setBillingState(String billingState) {
	this.billingState = billingState;
}
public String getBillingZip() {
	return billingZip;
}
public void setBillingZip(String billingZip) {
	this.billingZip = billingZip;
}
public List<ItemTarget> getItems() {
	return items;
}
public void setItems(List<ItemTarget> items) {
	this.items = items;
}
public int getTotalAmount() {
	return totalAmount;
}
public void setTotalAmount(int totalAmount) {
	this.totalAmount = totalAmount;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
}
