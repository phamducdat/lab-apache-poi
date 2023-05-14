package com.example.demo.service;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import java.util.List;
import java.util.UUID;

public class DocxService {

    public void addTableRow(XWPFTable table, int position) {
        // Get the existing row to clone its style
        XWPFTableRow templateRow = table.getRow(1); // Assuming the first row is the template row


        // Create a new row at the desired position
        XWPFTableRow newRow = table.insertNewTableRow(position);


        // Set the content for each cell
        for (int i = 0; i < templateRow.getTableCells().size(); i++) {
            XWPFTableCell newCell = newRow.createCell();
            newCell.getCTTc().set(templateRow.getCell(i).getCTTc().copy());
            CTTc newCellCTTc = newCell.getCTTc();
            for (CTP ctp : newCellCTTc.getPList()) {
                for (CTR ctr : ctp.getRList()) {
                    for (CTText ctText : ctr.getTList()) {
                        ctText.setStringValue("datpd");
                    }
                }
            }
        }
    }

    private void replaceTextInParagraphs(List<XWPFParagraph> paragraphs) {
        for (XWPFParagraph paragraph : paragraphs) {
            for (int i = 0; i < paragraph.getRuns().size(); i++) {
                XWPFRun run = paragraph.getRuns().get(i);
                String replacedText = UUID.randomUUID().toString();
                XWPFRun newRun = paragraph.insertNewRun(i + 1);
                newRun.getCTR().set(run.getCTR());
                newRun.setText(replacedText, 0);
                paragraph.removeRun(i);
                i--; // Decrement the index as we just removed a run
            }
        }
    }

}

