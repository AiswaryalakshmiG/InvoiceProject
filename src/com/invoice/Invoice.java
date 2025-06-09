package com.invoice;

import java.util.List;
public class Invoice {
	private String invoiceNumber;
	private String date;
	private String dueDate;
	private BillingInfo billingTo;
	private List<Item> items;
	private String notes;
	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public BillingInfo getBillingTo() {
		return billingTo;
	}
	public void setBillingTo(BillingInfo billingTo) {
		this.billingTo = billingTo;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}


}
