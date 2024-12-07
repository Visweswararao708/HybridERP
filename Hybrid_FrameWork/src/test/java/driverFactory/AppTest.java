package driverFactory;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class AppTest {
String inputpath = "./Fileinput/DataEngine.xlsx";
String outputpath=".//FileOutput/HybridResults.xlsx";
String TCsheet = "MasterTestCases";
ExtentReports reports;
ExtentTest logger;
WebDriver driver;
@Test
public void startTest() throws Throwable
{
	String Module_Status ="";
	String Module_New = "";
	// create object for excelfile util class
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	// count no of rows
	int rc =xl.rowCount(TCsheet);
	// iterate all rows 
	for(int i=1;i<=rc;i++)
	{
		if(xl.getCellData(TCsheet, i, 2).equalsIgnoreCase("Y"))
		{
			// read corresponding sheet from TCsheet
			String TCModule = xl.getCellData(TCsheet, i, 1);
			// Define path of html
			reports = new ExtentReports("./target/Reorts/"+TCModule+FunctionLibrary.generateDate()+".html");
			logger= reports.startTest(TCModule);
			// iterate all rows in TCModule
			for(int j=1;j<=xl.rowCount(TCModule);j++)
			{
				// read each cell from TCModule
				String Description = xl.getCellData(TCModule, j, 0);
				String ObjectType = xl.getCellData(TCModule, j, 1);
				String LType = xl.getCellData(TCModule, j, 2);
				String LValue = xl.getCellData(TCModule, j, 3);
				String TestData = xl.getCellData(TCModule, j, 4);
				try {
					if(ObjectType.equalsIgnoreCase("startTest"))
					{
						FunctionLibrary.startBrowser();
						logger.log(LogStatus.INFO,Description);
					}
					if(ObjectType.equalsIgnoreCase("openUrl"))
					{
						FunctionLibrary.openUrl();
						logger.log(LogStatus.INFO,Description);
					}
					if(ObjectType.equalsIgnoreCase("waitForElement"))
					{
						FunctionLibrary.waitForElement(LType, LValue, TestData);
						logger.log(LogStatus.INFO,Description);
					}
					if(ObjectType.equalsIgnoreCase("typeAction"))
					{
						FunctionLibrary.typeAction(LType, LValue, TestData);
						logger.log(LogStatus.INFO,Description);
					}
					if(ObjectType.equalsIgnoreCase("clickAction"))
					{
						FunctionLibrary.clickAction(LType, LValue);
						logger.log(LogStatus.INFO,Description);
					}
					if(ObjectType.equalsIgnoreCase("validateTitle"))
					{
						FunctionLibrary.validateTitle(TestData);
						logger.log(LogStatus.INFO,Description);
					}
					if(ObjectType.equalsIgnoreCase("closeBrowser"))
					{
						FunctionLibrary.closeBrowser();
						logger.log(LogStatus.INFO,Description);
					}
					if(ObjectType.equalsIgnoreCase("dropDownAction"))
					{
						FunctionLibrary.dropDownAction(LType, LValue, TestData);
						logger.log(LogStatus.INFO,Description);
					}
					if(ObjectType.equalsIgnoreCase("captureStock"))
					{
						FunctionLibrary.captureStock(LType, LValue);
						logger.log(LogStatus.INFO,Description);
					}
					if(ObjectType.equalsIgnoreCase("stockTable"))
					{
						FunctionLibrary.stockTable();
						logger.log(LogStatus.INFO,Description);
					}
					if(ObjectType.equalsIgnoreCase("captureSup"))		
					{
						FunctionLibrary.captureSup(LType, LValue, TestData);
						logger.log(LogStatus.INFO,Description);
					}
					if(ObjectType.equalsIgnoreCase("supplierTable"))
					{
						FunctionLibrary.supplierTable();
						logger.log(LogStatus.INFO,Description);
					}
					if(ObjectType.equalsIgnoreCase("captureCus"))
					{
						FunctionLibrary.captureCus(LType, LValue, TestData);
						logger.log(LogStatus.INFO,Description);
					}
					if(ObjectType.equalsIgnoreCase("customerTable"))		
					{
						FunctionLibrary.customerTable();
						logger.log(LogStatus.INFO,Description);
					}
					// write as pass in to status cell in TCModule
					xl.setCellData(TCModule, j, 5, "Pass", outputpath);
					logger.log(LogStatus.PASS,Description);
					Module_Status = "True";
				} catch (Exception e) {
				  System.out.println(e.getMessage());
				// write as fail in to status cell in TCModule
				  xl.setCellData(TCModule, j, 5, "Fail", outputpath);
				  logger.log(LogStatus.FAIL,Description);
				  Module_New = "False";
				}
				if(Module_Status.equalsIgnoreCase("True"))
				{
					// write as pass in to TCsheet
					xl.setCellData(TCsheet, i, 3, "Pass", outputpath);
				}
				if(Module_New.equalsIgnoreCase("False"))
				{
					xl.setCellData(TCsheet, i, 3, "False", outputpath);
				}
				reports.endTest(logger);
				reports.flush();
			}
		}
		else 
		{
			// write as blocked in to TC sheet which testcase flag to N
			xl.setCellData(TCsheet, i, 3, "Blocked", outputpath);
		}
	}


}
}
