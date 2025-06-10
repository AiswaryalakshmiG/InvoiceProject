package com.invoice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.CSVWriter;

public class Main {
	public static void main(String[] args) throws IOException {
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
		item2.setUnitPrice(120.50);

		Invoice invoice1 = new Invoice();
		invoice1.setInvoiceNumber("INV-1001");
		invoice1.setDate("2025-06-09");
		invoice1.setDueDate("2025-06-16");
		invoice1.setBillingTo(billing1);
		invoice1.setItems(Arrays.asList(item1, item2));
		invoice1.setNotes("Thank you for your business!");

		BillingInfo billing2 = new BillingInfo();
		billing2.setName("John");
		billing2.setAddress("11 Street");
		billing2.setCity("India");
		billing2.setState("TN");
		billing2.setZip("60007");

		Item item3 = new Item();
		item3.setDescription("Bag");
		item3.setQuantity(1);
		item3.setUnitPrice(120);

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

		for (Invoice src : invoice) {
			InvoiceTarget target = new InvoiceTarget();
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
			for (Item item : src.getItems()) {
				ItemTarget targetItem = new ItemTarget();
				targetItem.setCode(item.getDescription());
				targetItem.setQty(item.getQuantity());
				targetItem.setPrice((int) item.getUnitPrice());
				int amount = item.getQuantity() * (int) item.getUnitPrice();
				targetItem.setAmount(amount);
				total += amount;
				itemTarget.add(targetItem);
			}

			target.setItems(itemTarget);
			target.setTotalAmount(total);
			target.setDescription("Thank you for your business!");
			invoiceTarget.add(target);
		}

		ObjectMapper mappersrc = new ObjectMapper();
		mappersrc.enable(SerializationFeature.INDENT_OUTPUT);
		String source = mappersrc.writeValueAsString(invoice);
		System.out.println("The Source JSON is: " + source);

		ObjectMapper mappertrg = new ObjectMapper();
		mappertrg.enable(SerializationFeature.INDENT_OUTPUT);
		String target = mappertrg.writeValueAsString(invoiceTarget);
		System.out.println("\n \n The Target JSON is: " + target);

		String filePath = "resources//source.csv";
		File CSVFile = new File(filePath);
		if (!CSVFile.exists())
			CSVFile.createNewFile();

		try (CSVWriter writer = new CSVWriter(new FileWriter(CSVFile))) {
			String[] header = { "invoiceNumber", "Date", "DueDate", "BillingName", "BillingAddress", "BillingCity",
					"BillingState", "BillingZip", "ItemDescription", "ItemQuantity", "ItemUnitPrice", "Notes" };
			writer.writeNext(header);

			for (Invoice csv : invoice) {
				for (Item item : csv.getItems()) {
					String[] data = { csv.getInvoiceNumber(), csv.getDate(), csv.getDueDate(),
							csv.getBillingTo().getName(), csv.getBillingTo().getAddress(), csv.getBillingTo().getCity(),
							csv.getBillingTo().getState(), csv.getBillingTo().getZip(), item.getDescription(),
							String.valueOf(item.getQuantity()), String.valueOf(item.getUnitPrice()), csv.getNotes() };
					writer.writeNext(data);
				}
			}

			System.out.println("csv file converted successfully" + CSVFile.getAbsolutePath());

		}

		String excelPath = "resources//source.xlsx";
		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Invoice Data");

		String[] header = { "invoiceNumber", "Date", "DueDate", "BillingName", "BillingAddress", "BillingCity",
				"BillingState", "BillingZip", "ItemDescription", "ItemQuantity", "ItemUnitPrice", "Notes" };

		Row headerRow = sheet.createRow(0);
		for (int i = 0; i < header.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(header[i]);
		}

		int rowNum = 1;
		for (Invoice excel : invoice) {
			for (Item item : excel.getItems()) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(excel.getInvoiceNumber());
				row.createCell(1).setCellValue(excel.getDate());
				row.createCell(2).setCellValue(excel.getDueDate());
				row.createCell(3).setCellValue(excel.getBillingTo().getName());
				row.createCell(4).setCellValue(excel.getBillingTo().getAddress());
				row.createCell(5).setCellValue(excel.getBillingTo().getCity());
				row.createCell(6).setCellValue(excel.getBillingTo().getState());
				row.createCell(7).setCellValue(excel.getBillingTo().getZip());
				row.createCell(8).setCellValue(item.getDescription());
				row.createCell(9).setCellValue(item.getQuantity());
				row.createCell(10).setCellValue(item.getUnitPrice());
				row.createCell(11).setCellValue(excel.getNotes());

			}
		}

		for (int i = 0; i < header.length; i++) {
			sheet.autoSizeColumn(i);
		}
		try (FileOutputStream fileOut = new FileOutputStream(excelPath)) {
			workbook.write(fileOut);
			workbook.close();
			System.out.println("Excel file converted successfully" + excelPath);
		}
	}
}
