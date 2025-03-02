package api.utilities;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ExcelUtil {
    String path;
    FileInputStream file;
    FileOutputStream fileOut;
    XSSFWorkbook wbook;
    XSSFSheet sheet;
    XSSFRow row;
    XSSFCell cell;


    public ExcelUtil(String path){
        this.path = path;
    }

    public int getRowCount(String sheetName) throws IOException {
        file=new FileInputStream(path);
        wbook= new XSSFWorkbook(file);
        sheet=wbook.getSheet(sheetName);
        int rowCount=sheet.getLastRowNum();
        wbook.close();
        file.close();
        return rowCount;
    }
    public int getColumnCount(String sheetName) throws IOException {
        file=new FileInputStream(path);
        wbook= new XSSFWorkbook(file);
        sheet=wbook.getSheet(sheetName);
        int columnCount=sheet.getRow(0).getLastCellNum();
        wbook.close();
        file.close();
        return columnCount;
    }

    public int getCellCount(String sheetName,int rowNum) throws IOException {
        file=new FileInputStream(path);
        wbook= new XSSFWorkbook(file);
        sheet=wbook.getSheet(sheetName);
        row=sheet.getRow(rowNum);
        int cellCount=row.getLastCellNum();
        wbook.close();
        file.close();
        return cellCount;
    }

    public String getCellValue(String sheetName,int rowNum,int colNum) throws IOException {
        file=new FileInputStream(path);
        wbook= new XSSFWorkbook(file);
        sheet=wbook.getSheet(sheetName);
        row=sheet.getRow(rowNum);
        cell=row.getCell(colNum);
        DataFormatter formatter=new DataFormatter();
        String cellValue;
        cellValue=formatter.formatCellValue(cell);
        System.out.println(cellValue);
        wbook.close();
        file.close();
        return cellValue;
    }


    public void setCellValue(String data,String sheetName,int rowNum,int colNum) throws IOException {
        File xlsxFile=new File(path);
//        Create file if not exists
        if(!xlsxFile.exists()){
            wbook= new XSSFWorkbook();
            fileOut=new FileOutputStream(path);
            wbook.write(fileOut);
        }
        file=new FileInputStream(path);
        wbook= new XSSFWorkbook(file);
//        Create sheet if not exists
        if(wbook.getSheetIndex(sheetName)==-1){
            wbook.createSheet(sheetName);
        }
        sheet=wbook.getSheet(sheetName);
//         Create row if not exists
        if(sheet.getRow(rowNum)==null){
            sheet.createRow(rowNum);
        }
        row=sheet.getRow(rowNum);
        cell=row.getCell(colNum);
        cell.setCellValue(data);
        wbook.close();
        file.close();
    }
}
