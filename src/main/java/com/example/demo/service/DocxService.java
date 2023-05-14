package com.example.demo.service;

import org.apache.poi.xwpf.usermodel.*;

public class DocxService {

    public void addTableRow(XWPFTable table, int position) {
        // Get the existing row to clone its style
        XWPFTableRow templateRow = table.getRow(1); // Assuming the first row is the template row



        // Create a new row at the desired position
        XWPFTableRow newRow = table.insertNewTableRow(position);


        // Set the content for each cell
        for (int i = 0; i < templateRow.getTableCells().size(); i++) {
            XWPFTableCell newCell = newRow.createCell();

//            if (i == 0) {
//                // Clone the style of the template row for the first cell
//                newCell.getCTTc().set(templateRow.getCell(i).getCTTc().copy()); // Clone the cell style
//            }
            newCell.getCTTc().set(templateRow.getCell(i).getCTTc().copy());
            // Set the content for the cell
//            if (newCell.getParagraphs().size() == 0) {
//                newCell.addParagraph().createRun().setText(rowData[i]);
//            } else {
//                XWPFParagraph paragraph = newCell.getParagraphs().get(0);
//                if (paragraph.getRuns().size() == 0) {
//                    paragraph.createRun().setText(rowData[i]);
//                } else {
//                    XWPFRun run = paragraph.getRuns().get(0);
//                    run.setText(rowData[i], 0);
//                }
//            }
        }
    }

}

