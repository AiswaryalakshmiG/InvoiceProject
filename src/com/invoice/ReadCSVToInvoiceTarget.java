package com.invoice;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.CSVReader;

public class ReadCSVToInvoiceTarget {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		String filePath = "resources//source.csv";
		List<InvoiceTarget> invoiceTargetList = new ArrayList<>();
		
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			String[] line;
			boolean isHeader = true;

			while ((line = reader.readNext()) != null) {
				
				if (isHeader) {
					isHeader = false;
					continue;
				}
				
				InvoiceTarget invoiceTarget = new InvoiceTarget();
				invoiceTarget.setInvNumber(line[0]);
				invoiceTarget.setCreateDate(line[1]);
				invoiceTarget.setDueDate(line[2]);
				invoiceTarget.setBillingName(line[3]);
				invoiceTarget.setBillingAddress(line[4]);
				invoiceTarget.setBillingCity(line[5]);
				invoiceTarget.setBillingState(line[6]);
				invoiceTarget.setBillingZip(line[7]);

				ItemTarget item = new ItemTarget();
				item.setCode(line[8]);
				item.setQty(Integer.parseInt(line[9]));
				item.setPrice((int) Double.parseDouble(line[10]));
				item.setAmount(item.getQty() * item.getPrice());

				List<ItemTarget> items = new ArrayList<>();
				items.add(item);

				invoiceTarget.setItems(items);
				invoiceTarget.setTotalAmount(item.getAmount());
				invoiceTarget.setDescription(line[11]);

				invoiceTargetList.add(invoiceTarget);
			}

			ObjectMapper mappertrg = new ObjectMapper();
			mappertrg.enable(SerializationFeature.INDENT_OUTPUT);
			String json = mappertrg.writeValueAsString(invoiceTargetList);
			System.out.println("\n \n The Target JSON is: " + json);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
