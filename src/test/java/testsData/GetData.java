package testsData;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.List;

public class GetData {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @DataProvider
    public Object[][] ownerTestData(Method method) {
        ExcelReader excelReader = new ExcelReader();
        excelReader.setExcelFile("Owner");
        List rowsNo = excelReader.getRowContains(method.getName(), 5);
        return excelReader.getArrayForDataProvider(rowsNo);
    }

    @DataProvider
    public Object[][] ownerWithPetsTestData(Method method) {
        ExcelReader excelReader = new ExcelReader();
        excelReader.setExcelFile("OwnerWithPets");
        List rowsNo = excelReader.getRowContains(method.getName(), 8);
        return excelReader.getArrayForDataProvider(rowsNo);
    }

    public Object[][] ownersWithTheSameLastNameData(String methodName) {
        ExcelReader excelReader = new ExcelReader();
        excelReader.setExcelFile("OwnersWithSameLastName");
        List rowsNo = excelReader.getRowContains(methodName, 5);
        Object[][] allOwners = excelReader.getArrayForMultiDataProvider(rowsNo);
        verifyThatOwnersHaveTheSameLastName(allOwners);
        return allOwners;
    }

    public void verifyThatOwnersHaveTheSameLastName(Object[][] allOwners) {
        String lastName = allOwners[0][1].toString();
        for (int i = 1; i < allOwners.length; i++) {
            if (!((allOwners[i][1]).equals(lastName))) {
                String message = "Incorrect data! Data in Excel file must contain same last name for all owners in list.";
                logger.error(message);
                throw new SkipException(message);
            }
        }
    }


}
