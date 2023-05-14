package com.example.demo;

import com.example.demo.service.DocxService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        // Load the existing DOCX file from the resources folder
        ClassLoader classLoader = DemoApplication.class.getClassLoader();
        String inputFilePath = classLoader.getResource("document.docx").getFile();
        String valuePath = classLoader.getResource("value.json").getPath();

        // Read the contents of the JSON file
        String jsonContent = new String(Files.readAllBytes(Paths.get(valuePath)));

        JsonObject jsonObject = gson.fromJson(jsonContent, JsonObject.class);
        JsonArray values = jsonObject.getAsJsonArray("values");
        try (FileInputStream fis = new FileInputStream(inputFilePath);
             XWPFDocument document = new XWPFDocument(fis)) {

            XWPFTable table = document.getTables().get(1);

            DocxService docxService = new DocxService();
            docxService.addTableRow(table, 1, values);

            String outputFilePath = classLoader.getResource("").getPath() + "output.docx";

            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                document.write(fos);
            }
        }
    }

}



