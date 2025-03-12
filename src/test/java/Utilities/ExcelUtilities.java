package Utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;


public class ExcelUtilities {
    private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;


    public static XSSFSheet getSheetObj(String filepath, String sheetName) throws Exception {
        try {
            FileInputStream ExcelFile = new FileInputStream(filepath);
            ExcelWBook = new XSSFWorkbook(ExcelFile);
            ExcelWSheet = ExcelWBook.getSheet(sheetName);
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Class ExcelUtil | Method getRowContains | Exception desc : " + e.getMessage());
        }
        return ExcelWSheet;
    }

    public static String getCellValueOnColumnName(XSSFSheet sheetObject, String ColumnName, int row) throws Exception {
        String cellValue = null;
        try {
            int SheetColCount = sheetObject.getRow(row).getPhysicalNumberOfCells();
            for (int Colcount = 0; Colcount < SheetColCount; Colcount++) {
                Cell currentCell = sheetObject.getRow(0).getCell(Colcount);
                if (currentCell != null && !currentCell.toString().isEmpty()) {
                    if (currentCell.toString().equalsIgnoreCase(ColumnName)) {
                        cellValue = sheetObject.getRow(row).getCell(Colcount).toString();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Class ExcelUtil | Method getRowContains | Exception desc : " + e.getMessage());
        }
        return cellValue;
    }
}
