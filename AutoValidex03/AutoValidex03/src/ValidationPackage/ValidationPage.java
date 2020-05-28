package ValidationPackage;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ValidationPage {


	public static WebElement UploadFile(WebDriver driver) {

		 WebElement element = driver.findElement(By.xpath("//*[@id='fileupload']"));
			return element;
		
	}

	
	public static WebElement GetResult(WebDriver driver) {
	
		// Read high level result - wait until view report button displays/enabled
		WebDriverWait  wait = new WebDriverWait(driver,20);
		WebElement viewResultButton;
		viewResultButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='btn btn-default vdx-view-report-btn']")));
		
		WebElement element = driver.findElement(By.xpath("//*[@class='panel-footer']"));
		String Text = element.getText();
		int tempCounter = 0;
		
		while (tempCounter <3) {
			try {
				element = driver.findElement(By.xpath("//*[@class='panel-footer']"));
				Text = element.getText();
				if (Text.equals("Waiting...")) {
					tempCounter =1;
				}
				else if (Text.equals("Uploading...")) {
					tempCounter =2;
				}
				else {
					tempCounter = 3;
				}
			}
			finally {
					driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
				}
				
		}
			
		element = driver.findElement(By.xpath("//*[@class='panel-footer']"));
		// System.out.println("finally we got result = " + Text + "\n"); [debug only]
		return element;
	}
	

	public static WebElement ClearResult(WebDriver driver) {

		WebElement element = driver.findElement(By.xpath("//*[@class='text-muted']"));
		return element;
	}


	public static WebElement ViewReport(WebDriver driver) {

		WebElement element = driver.findElement(By.xpath("//*[@class='btn btn-default vdx-view-report-btn']"));
		return element;
	}


 	 
 	// AUNZ eCredit Note
 	 
 	   //*[@value="doc/xml/ubl2/crdnot/en16931intl/peppolbillingv3intl/aunztcn"]
 	 
 	 
 	// AUNZ Selfbilling e-invoice
 	 
 	   //*[@value="doc/xml/ubl2/inv/en16931intl/peppolbillingv3intl/aunzsbinv"]
 	 
 	 
 	// AUNZ Self billing e-Credit Note
 	 
 	   //*[@value="doc/xml/ubl2/crdnot/en16931intl/peppolbillingv3intl/aunzsbcn"]
 	 



	public static WebElement SelectAUNZeInvoice(WebDriver driver) {
		// DEBUG
		// Find elements
		// WebElement element = driver.findElement(By.id("//*[@type='radio']"));
		
		//List<WebElement> radioButtonList = driver.findElements(By.id("//*[@type='radio']"));
		//int count = radioButtonList.size();
		//for (int i=0; i<count; i++) {
		//	String radioButtonValue = ((WebElement) radioButtonList.get(i)).getAttribute("Value");
		//	System.out.println("Radio Button Value = " + radioButtonValue +  "\n");
		//}
				
		// Select AUNZ eInvoice
 		//WebElement element = driver.findElement(By.xpath("//*[@id='myBtn1']"));
		//WebElement element = driver.findElement(By.xpath("//*[@id='myModal1']"));
		//WebElement element = driver.findElement(By.xpath("//*[@value='doc/xml/ubl2/inv/en16931']"));
		WebElement element = driver.findElement(By.xpath("//*[@value='doc/xml/ubl2/inv/en16931intl/peppolbillingv3intl/aunztinv101']"));

 		System.out.println("AUNZeInvoice Selected" + "\n");
		
		return element;
	}


	public static WebElement SelectPINT(WebDriver driver) {
		WebElement element = driver.findElement(By.xpath("//*[@value='doc/xml/ubl2/inv/pint']"));

 		System.out.println("AUNZeInvoice Selected" + "\n");
		
		return element;
	}

}
