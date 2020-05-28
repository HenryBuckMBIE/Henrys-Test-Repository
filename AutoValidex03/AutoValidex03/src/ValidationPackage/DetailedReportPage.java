package ValidationPackage;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DetailedReportPage {
	
	public static WebElement GetErrorMessage(WebDriver driver) {

		// First ensure we are on the correct tab
		String mostRecentWindowHandle = "";
		for (String winHandle:driver.getWindowHandles()) {
			mostRecentWindowHandle = winHandle;
		}
		driver.switchTo().window(mostRecentWindowHandle);
		
		// Count the number of fatal error messages
		List<WebElement> errorCountList = driver.findElements(By.xpath("//*[@class='small vdx-fatal']"));	
		int errorCount = errorCountList.size();		
		System.out.println("The number of ERRORS found = " + errorCount);
		
		// Get the last fatal message
		// Errors can be found by error-row-0 or error-row-1 
		// This also returns warnings!!!!!
		
		List<WebElement> List0 = driver.findElements(By.xpath("//*[@id='error-row-0']/td[2]/div"));
		List<WebElement> List1 = driver.findElements(By.xpath("//*[@id='error-row-1']/td[2]/div"));
		
		int row0Count = List0.size();
		int row1Count = List1.size();
		//int totalCount = row0Count+row1Count;
		
		// Debug
		//System.out.println("The number of row0 found = " + row0Count);
		//System.out.println("The number of row1 found = " + row1Count);
		//System.out.println("Total = " + totalCount);
			
		// This code find the last elements of each type
		// Subtract 1 as the index starts from 0
		WebElement Last0 = List0.get(row0Count-1);	
		WebElement element = Last0;
		if (row1Count > 0) {
			WebElement Last1 = List1.get(row1Count-1);
			element = Last1;
		}
				
			
		// the below never works so forget this
		// Now close this tab
		// driver.close();
		
		return element;		
	}
	
}
