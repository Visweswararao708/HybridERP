package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import freemarker.core.ReturnInstruction.Return;

public class FunctionLibrary {
	public static Properties conpro;
	public static WebDriver driver;
	// method for launching browser
	public static WebDriver startBrowser() throws Throwable, Throwable
	{
		conpro = new Properties();
		// load property file
		conpro.load(new FileInputStream("./PropertyFiles/Envinorment.Properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if (conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		else 
		{
			Reporter.log("Browser value is not matching",true);
		}
		return driver;
	}
	public static void openUrl()
	{
		driver.get(conpro.getProperty("Url"));
	}
	// method for wait for any element
	public static void waitForElement(String LocatorType,String LocatorValue,String Wait)
	{
		WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Wait)));
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			// wait until element is visible
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			// wait until element is visible
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			// wait until element is visible
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
	}
		public static void typeAction(String LocatorType,String LocatorValue,String TestData)
		{
			if(LocatorType.equalsIgnoreCase("name"))
			{
				driver.findElement(By.name(LocatorValue)).clear();
				driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
			}
			if(LocatorType.equalsIgnoreCase("xpath"))
			{
				driver.findElement(By.xpath(LocatorValue)).clear();
				driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
			}
			if(LocatorType.equalsIgnoreCase("id"))
			{
				driver.findElement(By.id(LocatorValue)).clear();
				driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
			}
		}
		// methofd for buttons,links,radiobuttons,checkboxes and images
		public static void clickAction(String LocatorType,String LOcatorValue)
		{
			if(LocatorType.equalsIgnoreCase("xpath"))
			{
             driver.findElement(By.xpath(LOcatorValue)).click();
			}
			if(LocatorType.equalsIgnoreCase("name"))
			{
             driver.findElement(By.name(LOcatorValue)).click();
			}
			if(LocatorType.equalsIgnoreCase("id"))
			{
             driver.findElement(By.id(LOcatorValue)).sendKeys(Keys.ENTER);
			}
		}
// method for validate title
public static void validateTitle(String Expected_Title)
{
	String Actual_Title = driver.getTitle();
	try {
		 Assert.assertEquals(Actual_Title, Expected_Title,"Title is not matching");
	} catch (AssertionError e) {
       System.out.println(e.getMessage());
	}
}
public static void closeBrowser()
{
	driver.quit();
}
// method for any listbox
public static void dropDownAction(String LocatorType,String LocatorValue,String TestData)
{
	if(LocatorType.equalsIgnoreCase("xapth"))
	{
		// convert test data in to integer
		int value = Integer.parseInt(TestData);
		Select element = new Select(driver.findElement(By.xpath(LocatorValue)));
		element.selectByIndex(value);
	}
}
// capture stock number and write in to notepad under capture data
public static void captureStock(String LocatorType,String LocatorValue)throws Throwable
{
	String stockNumber = "";
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		stockNumber = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		stockNumber = driver.findElement(By.name(LocatorValue)).getAttribute("value");
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		stockNumber = driver.findElement(By.id(LocatorValue)).getAttribute("value");
	}
	// create notepad and write stocknumber
	FileWriter fw = new FileWriter("./CaptureData/stocknum.txt");
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(stockNumber);
	bw.flush();
	bw.close();
}
// method for verify stock number in table
public static void stockTable()throws Throwable
{
	// read stock number from notepad
	FileReader fr = new FileReader("./CaptureData/stocknum.txt");
	BufferedReader br = new BufferedReader(fr);
	String Exp_Data = br.readLine();
	if(!driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).isDisplayed());
	driver.findElement(By.xpath(conpro.getProperty("Search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).sendKeys("Exp_Data");
	driver.findElement(By.xpath(conpro.getProperty("Search-button"))).click();
	// capture stock number from table
	String Act_Data = driver.findElement(By.xpath("//table[@cl/ass='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
	Reporter.log(Exp_Data+"    "+Act_Data,true);
	try {
		Assert.assertEquals(Exp_Data, Act_Data,"Stock is not matching");
	} catch (AssertionError e) {
		System.out.println(e.getMessage());
	}
}
// method for capture supplier number in to notepad
public static void captureSup(String LocatorType,String LoctorValue,String TestData)throws Throwable
{
	// capture supplier number
	String supplierNum = "";
	if(LocatorType.equalsIgnoreCase("xapth"))
	{
		supplierNum = driver.findElement(By.xpath(LoctorValue)).getAttribute("value");
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		supplierNum = driver.findElement(By.name(LoctorValue)).getAttribute("value");
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		supplierNum = driver.findElement(By.id(LoctorValue)).getAttribute("value");
	}
	// create notepad in to capturedata folder and write
	FileWriter fw = new FileWriter("./CaptureData/suppliernum.txt");
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(supplierNum);
	bw.close();
}
// method for verify supplier number in table
public static void supplierTable()throws Throwable
{
	FileReader fr = new FileReader("./CaptureData/suppliernum.txt");
	BufferedReader br = new BufferedReader(fr);
	String Exp_Data = br.readLine();
	if(!driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).isDisplayed())
		driver.findElement(By.xpath(conpro.getProperty("Search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).sendKeys("Exp_Data");
	driver.findElement(By.xpath(conpro.getProperty("Search-button"))).click();
	Thread.sleep(3000);
	String Act_Data = driver.findElement(By.xpath("//[@class='table ewTable'/tbody/tr[1]/td[6]/div/span/span")).getText();
	Reporter.log(Exp_Data+"    "+Act_Data,true);
	try {
		Assert.assertEquals(Exp_Data, Act_Data,"Supplier Number Not Matching");
	} catch (AssertionError e) {
		System.out.println(e.getMessage());
	}
}
// method for capture number in to notepad
public static void captureCus(String LocatorType,String LocatorValue,String TestData)throws Throwable
{
	String customerNum = "";
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		customerNum = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		customerNum = driver.findElement(By.name(LocatorValue)).getAttribute("value");
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		customerNum = driver.findElement(By.id(LocatorValue)).getAttribute("value");
	}
	FileWriter fw = new FileWriter("./CaptureData/customernum.txt");
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(customerNum);
	bw.flush();
	bw.close();
}
// method for reading customer number notepad and verify in table
public static void customerTable()throws Throwable
{
	FileReader fr = new FileReader("./CaptureData/customernum.txt");
	BufferedReader br = new BufferedReader(fr);
	String Exp_Data = br.readLine();
	if(!driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).isDisplayed());
	driver.findElement(By.xpath(conpro.getProperty("Search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).sendKeys("Exp_Data");
	driver.findElement(By.xpath(conpro.getProperty("Search-button"))).click();
	Thread.sleep(3000);
	String Act_Data = driver.findElement(By.xpath("//table[@cl/ass='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
	Reporter.log(Exp_Data+"    "+Act_Data,true);
	try {
		Assert.assertEquals(Exp_Data, Act_Data,"Customer Number Not Matching");
	} catch (AssertionError e) {
	System.out.println(e.getMessage());
	}
}
// method for time stamp
public static String generateDate()
{
	Date date = new Date();
	DateFormat df = new SimpleDateFormat("YYYY_MM_DD");
    return df.format(date);
	
}
}






