package com.invoice;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
public class Main {
public static void main(String [] args) throws JsonProcessingException {
	List<Invoice> invoice = new ArrayList<>();
	
	
	BillingInfo billing1 = new BillingInfo();
	billing1.setName("John Doe");
	billing1.setAddress("123 Main Street");
	billing1.setCity("New York");
	billing1.setState("NY");
	billing1.setZip("10001");
	
	
	Item item1 = new Item();
	item1.setDescription("Web Design Services");
	item1.setQuantity(10);
	item1.setUnitPrice(50);
	
	Item item2 = new Item();
	item2.setDescription("Hosting (1 year)");
	item2.setQuantity(1);
	item2.setUnitPrice(120);
	
	
	Invoice invoice1 = new Invoice();
	invoice1.setInvoiceNumber("INV-1001");
	invoice1.setDate("2025-06-09");
	invoice1.setDueDate("2025-06-16");
	invoice1.setBillingTo(billing1);
	invoice1.setItems(Arrays.asList(item1,item2));
	invoice1.setNotes("Thank you for your business!");
	
	
	
	
	BillingInfo billing2 = new BillingInfo();
	billing2.setName("John");
	billing2.setAddress("11 Street");
	billing2.setCity("India");
	billing2.setState("TN");
	billing2.setZip("60007");
	
	
	Item item3 = new Item();
	item1.setDescription("Bag");
	item1.setQuantity(1);
	item1.setUnitPrice(120);
	

	Invoice invoice2 = new Invoice();
	invoice2.setInvoiceNumber("INV-1002");
	invoice2.setDate("2025-07-09");
	invoice2.setDueDate("2025-07-26");
	invoice2.setBillingTo(billing2);
	invoice2.setItems(Arrays.asList(item3));
	invoice2.setNotes("Thank you for your business!");
	
	invoice.add(invoice1);
	invoice.add(invoice2);
	
	List<InvoiceTarget> invoiceTarget = new ArrayList<>();
	
	for(Invoice src : invoice) {
		InvoiceTarget target= new InvoiceTarget();
		target.setInvNumber(src.getInvoiceNumber());
		target.setCreateDate(src.getDate());
		target.setDueDate(src.getDueDate());
		target.setBillingName(src.getBillingTo().getName());
		target.setBillingAddress(src.getBillingTo().getAddress());
		target.setBillingCity(src.getBillingTo().getCity());
		target.setBillingState(src.getBillingTo().getState());
		target.setBillingZip(src.getBillingTo().getZip());
		
		int total = 0;
		List<ItemTarget> itemTarget = new ArrayList<>();
		for(Item item : src.getItems()) {
			ItemTarget targetItem = new ItemTarget();
			targetItem.setCode(item.getDescription());
			targetItem.setQty(item.getQuantity());
			targetItem.setPrice((int)item.getUnitPrice());
			int amount = item.getQuantity() *(int)item.getUnitPrice();
			targetItem.setPrice(amount);
			total += amount;
			itemTarget.add(targetItem);
		}
		
		target.setItems(itemTarget);
		target.setTotalAmount(total);
		invoiceTarget.add(target);
	}
	
	ObjectMapper mapper = new ObjectMapper();
	mapper.enable(SerializationFeature.INDENT_OUTPUT);
	String source= mapper.writeValueAsString(invoiceTarget);
	System.out.println(source);
	

	
}

}
