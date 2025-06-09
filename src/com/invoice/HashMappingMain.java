package com.invoice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class HashMappingMain {
	public static void main(String[] args) throws Exception {
		List<Map<String, Object>> invoiceList = new ArrayList<>();

		Map<String, Object> billing1 = new HashMap<>();
		billing1.put("name", "John Doe");
		billing1.put("address", "123 Main Street");
		billing1.put("city", "New York");
		billing1.put("state", "NY");
		billing1.put("zip", "10001");

		Map<String, Object> item1 = new HashMap<>();
		item1.put("description", "Web Design Services");
		item1.put("quantity", 10);
		item1.put("unit_price", 50);

		Map<String, Object> item2 = new HashMap<>();
		item2.put("description", "Hosting (1 year)");
		item2.put("quantity", 1);
		item2.put("unit_price", 120);

		List<Map<String, Object>> items1 = Arrays.asList(item1, item2);

		Map<String, Object> invoice1 = new HashMap<>();
		invoice1.put("invoice_number", "INV-1001");
		invoice1.put("date", "2025-06-09");
		invoice1.put("due_date", "2025-06-16");
		invoice1.put("billing_to", billing1);
		invoice1.put("items", items1);
		invoice1.put("notes", "Thank you for your business!");

		Map<String, Object> billing2 = new HashMap<>();
		billing2.put("name", "John");
		billing2.put("address", "11 Street");
		billing2.put("city", "India");
		billing2.put("state", "TN");
		billing2.put("zip", "60007");

		Map<String, Object> item3 = new HashMap<>();
		item3.put("description", "Bag");
		item3.put("quantity", 1);
		item3.put("unit_price", 120);

		List<Map<String, Object>> items2 = Arrays.asList(item3);

		Map<String, Object> invoice2 = new HashMap<>();
		invoice2.put("invoice_number", "INV-1002");
		invoice2.put("date", "2025-07-09");
		invoice2.put("due_date", "2025-07-26");
		invoice2.put("billing_to", billing2);
		invoice2.put("items", items2);
		invoice2.put("notes", "Thank you for your business!");

		invoiceList.add(invoice1);
		invoiceList.add(invoice2);

		List<Map<String, Object>> invoiceTargetList = new ArrayList<>();
		for (Map<String, Object> src : invoiceList) {
			Map<String, Object> target = new HashMap<>();
			target.put("invNumber", src.get("invoice_number"));
			target.put("createDate", src.get("date"));
			target.put("dueDate", src.get("due_date"));

			@SuppressWarnings("unchecked")
			Map<String, Object> billing = (Map<String, Object>) src.get("billing_to");
			if (billing != null) {
				target.put("billingName", billing.get("name"));
				target.put("billingAddress", billing.get("address"));
				target.put("billingCity", billing.get("city"));
				target.put("billingState", billing.get("state"));
				target.put("billingZip", billing.get("zip"));
			}
 			List<Map<String, Object>> itemTargetList = new ArrayList<>();
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> items = (List<Map<String, Object>>) src.get("items");
			for (Map<String, Object> item : items) {
				Map<String, Object> targetItem = new HashMap<>();
				int quantity = (int) item.get("quantity");
				double unit_price = Double.valueOf(item.get("unit_price").toString());
				targetItem.put("code", item.get("description"));
				targetItem.put("qty", quantity);
				targetItem.put("price", (int) unit_price);
				int amount = quantity * (int) unit_price;
				targetItem.put("amount", amount);
 				itemTargetList.add(targetItem);
			}
			
			 	
			target.put("item", itemTargetList);
			//target.put("totalAmount", total);
			target.put("description", src.get("notes"));
			invoiceTargetList.add(target);
		}

		ObjectMapper mappersrc = new ObjectMapper();
		mappersrc.enable(SerializationFeature.INDENT_OUTPUT);
		String source = mappersrc.writeValueAsString(invoiceList);
		System.out.println("The Source JSON is: " + source);

		ObjectMapper mappertrg = new ObjectMapper();
		mappertrg.enable(SerializationFeature.INDENT_OUTPUT);
		String target = mappertrg.writeValueAsString(invoiceTargetList);
		System.out.println("\n \n The Source JSON using HashMapping is: " + target);

	}

}
