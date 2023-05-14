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

        ArrayList<String> cellKeys = new ArrayList<>();
        for (XWPFTableCell cell: templateRow.getTableCells()) {
            cellKeys.add(cell.getText());
        }
//        ArrayList<String> cellKeys = new ArrayList<String>(){{
//            add("##stt##");
//            add("##ten_san_pham##");
//            add("##dvt##");
//            add("##sl##");
//            add("##don_gia##");
//            add("##thanh_tien##");
//        }};
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

}

