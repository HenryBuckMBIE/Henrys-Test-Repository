import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.*;


public class AV02MainClass {

	public static void main(String[] args) throws InterruptedException {
		
		String resultString;
		resultString = new String("");
		
		String fileName;
		fileName = new String("");
		
		String path;
		path = new String("");
		
		String fullPath;
		fullPath = ("");
		
		Scanner scanner = null;
		
		boolean isValid;
		isValid = false;
		
		String errorText;
		errorText = "";
		
		
			
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Selenium\\Cromedriver\\chromedriver_win32\\chromedriver.exe");
	    WebDriver driver = new ChromeDriver();
        driver.get("https://aunz.validex.net/en/login");
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        driver.manage().window().maximize();
        
        // close modal window  
        driver.findElement(By.xpath("//button[text()='Close']")).click();
        System.out.println("Modal Window Closed\n");
        
        // close cookie acceptance
        //driver.findElement(By.className("a.cc_btn cc_btn_accept_all")).click();
        driver.findElement(By.xpath("//a[@class='cc_btn cc_btn_accept_all']")).click();
        System.out.println("Cookie Accepted\n");
        Thread.sleep(5000);
              
        // Log in
        driver.findElement(By.name("email")).sendKeys("Henry.Buck@qualit.co.nz");
        driver.findElement(By.name("password")).sendKeys("************");
        driver.findElement(By.xpath("//*[@class='form-control btn btn-primary']")).click();
        System.out.println("Logged in\n");
        Thread.sleep(5000);
       
        // Get window handle for later
        String originalHandle = driver.getWindowHandle();
     
        // Open List of Files to be tested
        try {
			scanner = new Scanner(new File("C:\\Henry\\e-Invoice\\Automation_Project\\TestFiles\\TestFileList.txt"));
			System.out.println("File List Located\n");
		} catch (FileNotFoundException e) {
			System.out.println("could not find Test File List\n");
			driver.quit();
		}
        
        // File Read
        path = "C:\\Henry\\e-Invoice\\Automation_Project\\TestFiles\\";
        fileName = "Valid01.xml";
        fullPath = path+fileName;
        System.out.println(path + "\n");
        System.out.println(fileName + "\n");
        System.out.println(fullPath + "\n");
        
        
        while (scanner.hasNextLine()) {
        	System.out.println("Reading Next Line");
        	fileName=scanner.nextLine();
        	fullPath = path+fileName;
        	//System.out.println(path + "\n");
            //System.out.println(fileName + "\n");
            System.out.println(fullPath + "\n");
        	// Validate
            driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
            //Upload the file from the list
            driver.findElement(By.xpath("//*[@id='fileupload']")).sendKeys(fullPath);
            driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
            //Get validation result (main screen)
            Thread.sleep(5000);
            driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
            resultString = fileName + "  " + driver.findElement(By.xpath("//*[@class='panel-footer']")).getText(); 
            System.out.println( "Code line 97 resultString = " +resultString);
            
            // Check if Valid (no errors were detected)   
            // If Valid the message "No issues detected" is displayed
            if (resultString.contains("detected")) {  //true
                System.out.println("yes we found you");
                isValid = true;
            }
            else {
            	System.out.println("Must be an error as the word detected was not found" + "\n");
            	System.out.println("Attempting to open the report tab");
               	// if invalid then open report tab
            	isValid = false;
            	System.out.println("false\n");
            	// CLICK REPORT BUTTON
            	driver.findElement(By.xpath("//*[@class='btn btn-default vdx-view-report-btn']")).click();
                //get error text  
            	driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
            	Thread.sleep(5000);
            	
            	
            	// Get error from the results table
            	          	
          
            	//[BROKEN]
            		
            	//count number of rows in error message table

            	
            	System.out.println("Attempting to get the error message");
            	
            	//WebElement element = driver.findElement(By.xpath("//*[@class='table vdx-step-table']/tbody/tr[@id='error-row-0']/td[1]"));
            	//*[@class='table vdx-step-table']/tbody/tr['error-row-0']/td[5]
            	
            	
            	//System.out.println(element.getText());
            	
            	//*[@id="error-row-0"]/td[5]/div
            	//*[@id="error-row-0"]
            	//*[@id="error-row-0"]/td[5]/div/text()
            			//*[@id="error-row-0"]/td[5]/div/text()
            			//*[@id='error-row-0']/td[5]
            	
            	//Dimension rowsNumber = driver.findElement(By.className("table vdx-step-table")).getSize();
            	
            	//		path("//*[@class='table vdx-step-table']/tbody/tr")).getSize();
            	//System.out.println("the table dimension is " + rowsNumber.height + "\n");
            	
            	// NOWT WORKS SO TRY GETTING ALL WEB ELEMENTS
            	List<WebElement> elements = driver.findElements(By.xpath("//"));
            	System.out.print(Integer.toString(elements.size()));
            	for(WebElement el:elements)
            	{
            		System.out.println(el.getTagName() + " : " + el.getText());
            		
            
            	resultString = resultString + errorText;
            	
            	//close report tab when done
            	for(String handle : driver.getWindowHandles()) {
                    if (!handle.equals(originalHandle)) {
                        driver.switchTo().window(handle);
                        driver.close();
                    }
                }
                driver.switchTo().window(originalHandle);
                driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
            	Thread.sleep(5000);
                // Clear Message and result string (main screen)
                driver.findElement(By.xpath("//*[@class='text-muted']")).click();
                resultString = "";
                errorText = "";

            }
            
            	
            // Clear Message and result string (main screen)
           	driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
            Thread.sleep(5000);
            driver.findElement(By.xpath("//*[@class='text-muted']")).click();
            resultString = "";
            errorText = "";
      
        }
        
        
        // End Test
        System.out.println("End of Test");
        
	}
        driver.quit();
	} 
}
