package api.utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.Arrays;

public class DataProviders {
    String path=System.getProperty("user.dir")+"/src/test/resources/testData/UserData.xlsx";
    ExcelUtil xlUtil;
    @DataProvider(name = "data")
    public String [][] getAllData() throws IOException {
             xlUtil=new ExcelUtil(path);
            int rowCount= xlUtil.getRowCount("Sheet1");
            int columnCount=xlUtil.getCellCount("Sheet1",1);
            String[][] apiData = new String [rowCount][columnCount];
            for(int i=1;i<=rowCount;i++){
                for(int j=0;j<columnCount;j++){
                    apiData[i-1][j]=xlUtil.getCellValue("Sheet1",i,j);
                }
        }
        System.out.println(Arrays.toString(apiData));
        return apiData;
    }

    @DataProvider(name = "Usernames")
    public String[] getUsernames() throws IOException {
        xlUtil=new ExcelUtil(path);
        int rowCount= xlUtil.getRowCount("Sheet1");
        String[] usernames = new String[rowCount];
        for(int i=1;i<=rowCount;i++){
            usernames[i-1]=xlUtil.getCellValue("Sheet1",i,1);
        }
        System.out.println(Arrays.toString(usernames));
        return usernames;
    }

}
