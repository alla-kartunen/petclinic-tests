package testsData;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.List;

public class GetData {

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

    public Object[][] ownersWithSameLastnameData(String methodName) {
        ExcelReader excelReader = new ExcelReader();
        excelReader.setExcelFile("OwnersWithSameLastname");
        List rowsNo = excelReader.getRowContains(methodName, 5);
        Object[][] allOwners = excelReader.getArrayForMultiDataProvider(rowsNo);
        verifyThatOwnersHaveSameLastname(allOwners);
        return allOwners;
    }

    public void verifyThatOwnersHaveSameLastname(Object[][] allOwners) {
        String lastName = allOwners[0][1].toString();

        int listLength = allOwners.length;

        for (int i = 1; i < listLength; i++) {
            if (!((allOwners[i][1]).equals(lastName))) {
                throw new SkipException(
                        "Incorrect data! Data in Excel file must contains same lastname for all owners in list.");
            }
        }
    }


}
