package kr.go.seoul.noper2.global.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component("excelView")
@RequiredArgsConstructor
public class ExcelView extends AbstractXlsxView {
    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws IOException {
        List<HashMap<String,Object>> list = (List<HashMap<String, Object>>) map.get("list");

        String fileName = "download";
        Sheet sheet = workbook.createSheet("Sheet1");

        Set<String> header = list.get(0).keySet();

        // 1번째 줄 지정
        Row row = sheet.createRow(0);
        // 값 넣기
        int index = 0;
        for (String cNm : header) {
            Cell cell = row.createCell(index);
            cell.setCellValue(cNm);
            cell.setCellStyle(headerStyle(workbook));
            index++;
        }
        // 2번째 줄 지정
        int rowIndex = 1;
        // 값 넣기
        for(HashMap<String, Object> m : list) {
            Row row2 = sheet.createRow(rowIndex);
            index = 0;
            for (String nm : header) {
                Cell cell = row2.createCell(index);
                String value = m.get(nm) != null ? m.get(nm).toString() : "";
                cell.setCellValue(value);
                cell.setCellStyle(valueStyle(workbook, value));
                index++;
            }
            rowIndex++;
        }

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        try (ServletOutputStream output = response.getOutputStream()) {
            workbook.write(output);
            output.flush();
        }
    }

    private CellStyle headerStyle(Workbook workbook) {
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setBold(true);
        headerCellStyle.setFont(font);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return headerCellStyle;
    }
    private CellStyle valueStyle(Workbook workbook, String value) {
        CellStyle valueCellStyle = workbook.createCellStyle();
        // 값이 "-" 이면 중앙정렬
        if (value.equals("-")) {
            valueCellStyle.setAlignment(HorizontalAlignment.CENTER);
        }
        valueCellStyle.setBorderBottom(BorderStyle.THIN);
        valueCellStyle.setBorderTop(BorderStyle.THIN);
        valueCellStyle.setBorderLeft(BorderStyle.THIN);
        valueCellStyle.setBorderRight(BorderStyle.THIN);
        valueCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return valueCellStyle;
    }
}
