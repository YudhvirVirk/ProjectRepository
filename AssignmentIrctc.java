package selenium;

import java.io.File;
import java.text.DateFormatSymbols;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AssignmentIrctc {
	private WebDriver driver;
	
	@BeforeMethod
	public void browserLaunch() throws Exception{
		driver=new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	
	@Test
	public void flightEvaluation() throws Exception{
		driver.get("https://www.irctc.co.in/");
	    String parentwindow = driver.getWindowHandle();
	    driver.findElement(By.xpath("//div[@id='bluemenu']//a[text()='Flights']")).click();
	    Set<String> allwindows = driver.getWindowHandles();
	    for(String flightwindow:allwindows) {
	     if(driver.switchTo().window(flightwindow).getTitle().equals("IRCTC Online Passenger Reservation System"))
	     // driver.switchTo().window(flightwindow);
	      break;
	     }
	     System.out.println(driver.getTitle());
	     ExcelData ex=new ExcelData();
	    ex.setExcel("E://Excel Files/profiledata.xls","irctcflight");
	    int row;
	    int col=0;
	    int exc=col;
	    
	    WebDriverWait wait=new WebDriverWait(driver,30);
	   
	    for( row=1;row<=ex.getRowCount();row++)
	    {
	    	
	    //	System.out.println(ex.getCellValue(row,col+1));
	    	//while(exc<ex.getColCount())
	    //	{
	    //	if(ex.getCellValue(row, col).equals("null")){
	    	//	   exc++;
	    	  //}	
	    	        if(ex.isEmpty(row))
	    	        {
	    	        	System.out.println("row empty");
	    	        	continue;
	    	        }
	    	        
	    	        
	    	    
	    	        driver.findElement(By.id("origin")).clear();
	    		    driver.findElement(By.id("origin")).sendKeys(ex.getCellValue(row,col+1));
	    			driver.findElement(By.xpath("//ul[1]/li[@class='ui-menu-item']/a[contains(text(),'"+StringUtils.capitalize(ex.getCellValue(row,col+1))+"')]")).click();
	    			driver.findElement(By.id("destination")).clear();
	    	        driver.findElement(By.id("destination")).sendKeys(ex.getCellValue(row,col+2));
	    	        driver.findElement(By.xpath("//ul[2]/li[@class='ui-menu-item']/a[contains(text(),'"+StringUtils.capitalize(ex.getCellValue(row,col+2))+"')]")).click();
	    	        String[] a=ex.getCellValue(row,col+3).split("/");
	    	        int month=Integer.parseInt(a[1])-1;
	    	        
	    	        
//	    	        driver.findElement(By.xpath("//input[@id='departDate']/preceding::img[@class='ui-datepicker-trigger'][0]")).click();
	    	        driver.findElement(By.xpath("//input[@id='departDate']/following::img[1]")).click();
	    	       while(true){
	    	         String year1 = a[2].length()==4
	    	            ?a[2]
	    	            :"20"+a[2];
	    	            int year=Integer.parseInt(year1);
	    	            if(isElementPresent(By.xpath("//span[text()='"+new DateFormatSymbols().getMonths()[month]+"']/following::span[text()='"+year+"']"))){
	    	             break;
	    	            }
	    	            driver.findElement(By.xpath("//span[text()='Next']")).click();
	    	           }
	    	     System.out.println("hello");
	    	      driver.findElement(By.xpath("//span[text()='"+new DateFormatSymbols().getMonths()[month]+"']/following::table[1]//a[text()='"+a[0]+"']")).click();
	    	      Select dropdown = new Select(driver.findElement(By.xpath("//div[@id='passgrinfo']//select[@class='autocombo' and not(@id='classTypeInt')]")));
	   	        //  dropdown.selectByValue((ex.getCellValue(row,col+3).charAt(0)+"").toUpperCase());
	    	      
	    	      Thread.sleep(3000);
	   	          
	   	        
	   	          
	   	          
	   	       
	   	         if((ex.getCellValue(row,col+4).charAt(0)+"").toUpperCase().equals("Y")) {
	   	         File errorShot=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	   	         FileUtils.copyFile(errorShot,new File("Screen//irctc_of"+row+".png"));
	    		}
	    			
	    }
	    
	    }
	     
	     
	     
	    
	
	public boolean isElementPresent(By locator)
	{
		try{
			driver.findElement(locator);
		  return true;
		}
		catch(Exception e){
		
			return false;
		}
		}
	
	
	
	
	@AfterMethod
	public void browserQuit() throws Exception{
		driver.quit();
	}

}

