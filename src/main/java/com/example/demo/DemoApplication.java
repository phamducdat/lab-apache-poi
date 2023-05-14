package com.example.demo;

import com.example.demo.service.DocxService;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws Exception {
		// Load the existing DOCX file from the resources folder
		ClassLoader classLoader = DemoApplication.class.getClassLoader();
		String inputFilePath = classLoader.getResource("document.docx").getFile();

		try (FileInputStream fis = new FileInputStream(inputFilePath);
			 XWPFDocument document = new XWPFDocument(fis)) {

			XWPFTable table = document.getTables().get(1);
			System.out.println("dat with row = " + table.getRows().size());


			DocxService docxService = new DocxService();
			docxService.addTableRow(table,2);

			XWPFTable newTable = document.getTables().get(1);

			System.out.println("dat with row = " + newTable.getRows().size());
//			exportOutputFile(classLoader, document);
		}
	}

	private static void exportOutputFile(ClassLoader classLoader, XWPFDocument document) throws IOException {
		String outputFilePath = classLoader.getResource("").getPath() + "output.docx";

		try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
			document.write(fos);
		}
	}
}



