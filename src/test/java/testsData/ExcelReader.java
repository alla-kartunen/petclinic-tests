package testsData;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellType.STRING;

public class ExcelReader {

    private XSSFSheet excelWSheet;
    private XSSFWorkbook excelWBook;
    private XSSFCell cell;

    public void setExcelFile(String sheetName) {
        try {
            FileInputStream ExcelFile = new FileInputStream("src/test/resources/DataProviderExcel.xlsx");
            excelWBook = new XSSFWorkbook(ExcelFile);
            excelWSheet = excelWBook.getSheet(sheetName);
        } catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
        }
    }

    public List getRowContains(String testName, int colNum) {
        List list = new ArrayList<>();
        int rowCount = excelWSheet.getLastRowNum();

        for (int i = 0; i <= rowCount; i++) {
            String cellData = getCellData(i, colNum);

            if (cellData.equalsIgnoreCase(testName)) {
                list.add(i);
            }
        }
        return list;
    }

    public String getCellData(int rowNum, int colNum) {
        cell = excelWSheet.getRow(rowNum).getCell(colNum);
        cell.setCellType(STRING);
        return cell.getStringCellValue();
    }

    public List[] getRowData(int rowNo) {

        List[] arrayData = new List[1];
        List list = new ArrayList();

        int totalCols = excelWSheet.getRow(rowNo).getPhysicalNumberOfCells() - 1;

        for (int i = 0; i < totalCols; i++) {
            String cellData;
            cellData = getCellData(rowNo, i);
            list.add(cellData);
        }

        arrayData[0] = list;

        return arrayData;
    }

    public Object[][] getArrayForDataProvider(List<Integer> rowsNo) {

        Object[][] dataProviderArray = new Object[rowsNo.size()][];

        for (int i = 0; i < rowsNo.size(); i++) {
            int rowId = rowsNo.get(i);
            dataProviderArray[i] = getRowData(rowId);
        }
        return dataProviderArray;
    }

    public Object[][] getArrayForMultiDataProvider(List<Integer> rowsNo) {

        Object[][] ownersArray = new Object[rowsNo.size()][5];

        for (int i = 0; i < rowsNo.size(); i++) {
            int rowId = rowsNo.get(i);
            int totalCols = excelWSheet.getRow(rowId).getPhysicalNumberOfCells() - 1;

            for (int j = 0; j < totalCols; j++) {
                ownersArray[i][j] = new Object();
                ownersArray[i][j] = getCellData(rowId, j);
            }
        }
        return ownersArray;
    }

}
