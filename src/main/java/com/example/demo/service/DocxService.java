package com.example.demo.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DocxService {


    public void addTableRow(XWPFTable table, int templateRowPosition, JsonArray values) {
        // Get the existing row to clone its style
        XWPFTableRow templateRow = table.getRow(templateRowPosition); // Assuming the first row is the template row
        ArrayList<String> cellKeys = new ArrayList<String>(){{
            add("##stt##");
            add("##ten_san_pham##");
            add("##dvt##");
            add("##sl##");
            add("##don_gia##");
            add("##thanh_tien##");
        }};
        for (int i = 1; i < values.size(); i++) {
            JsonObject value = values.get(i).getAsJsonObject();
            XWPFTableRow newRow = new XWPFTableRow(templateRow.getCtRow(), table);
            table.addRow(newRow, i);
            for (int j = 0; j < newRow.getTableCells().size(); j++) {
                XWPFTableCell cell = newRow.getCell(j);
                CTTc newCellCTTc = cell.getCTTc();
                for (CTP ctp : newCellCTTc.getPList()) {
                    for (CTR ctr : ctp.getRList()) {
                        for (CTText ctText : ctr.getTList()) {
                            if (value.get(cellKeys.get(j)) != null) {
                                ctText.setStringValue(value.get(cellKeys.get(j)).getAsString());
                            }
                        }
                    }
                }
            }
        }
    }


//    public void mappingValues(XWPFTable table, JsonArray values) {
//        for (int i = 1; i < values.size() + 1; i++) {
//            XWPFTableRow row = table.getRow(i);
//            for (XWPFTableCell cell : row.getTableCells()) {
//                CTTc newCellCTTc = cell.getCTTc();
//                for (CTP ctp: newCellCTTc.getPList()) {
//                    for (CTR ctr: ctp.getRList()) {
//                        for (CTText ctText: ctr.getTList()) {
//                            System.out.println("dat with ctext = "+ ctText.getStringValue());
//                            ctText.setStringValue(UUID.randomUUID().toString());
//                        }
//                    }
//                }
//            }
//        }
//    }

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

