package ValidationPackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.util.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class TheMainClass {

	public static void main(String[] args) {
		
		String fileName = "filename";
		String fullPath = "thefullpath";
		String expectedResult = "Valid";
		String actualResult = "Invalid";
		
		int passed = 0;
		int failed = 0;		
		
		// Configurable Items - read from config file
		String configItem = "<c>";
		String email = "";
		String password = "";
		String schematron = "";
		String testFileLocation = "";
		FileInputStream fist = null;
		try {
			fist = new FileInputStream("C:\\e-Invoice\\Automation_Project\\Configuration\\Config.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		Scanner scan = new Scanner(fist);
		int lineNumber = 0;
		while (scan.hasNextLine()) {
	       	configItem=scan.nextLine();
		    if (lineNumber == 0) {
		    	email = configItem;
		    	lineNumber++;
		        }
		    else if  (lineNumber == 1) {
		    	password = configItem;
		    	lineNumber++;
		         }	
		    else if (lineNumber == 2) {
		    	schematron = configItem;
		    	lineNumber++;	    	
		        }
		    else if (lineNumber == 3) {
		    	System.out.println("Read Config File");
		    	lineNumber++;	    	
		        }
		    else {		    		    	
		    	// do nothing else	    	
		        }	
		}
		// [debug]
		System.out.println("Tester Login :" + email);
		//System.out.println("password :" + password);
		System.out.println("schematron :" + schematron);    
		scan.close();	
		
		// Start Browser and run tests		
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Cromedriver\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		//Log in Page - from config file		
	    //  eInvoice , if testing AUNZ Schematron
	    //  PINT     , if testing PEPPOL International Invoice
		// AUNZ
		 if (schematron.equals("eInvoice")) {
			 driver.get("https://aunz.validex.net/en/login");
			 testFileLocation = "C:\\e-Invoice\\Automation_Project\\TestFiles\\TestFileList.txt";
	           }
		 else if  (schematron.equals("PINT")) {
			 driver.get("https://peppol.validex.net/en/login");
			 testFileLocation = "C:\\e-Invoice\\Automation_Project\\TestFiles\\TestFileList.txt";
	           }
		 else {
			 System.out.println("ERROR - Set line 3 in config file to eInvoice or PINT"); 
			 System.out.println("ERROR - check the test files are in the correct location");
		 }
                    
        driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
        driver.manage().window().maximize();
        
        LoginPage.headerPopUp(driver).click();
        LoginPage.CookiePopUp(driver).click();
        LoginPage.LoginEmail(driver).sendKeys(email);
        LoginPage.LoginPassword(driver).sendKeys(password);
        LoginPage.LoginButton(driver).click();
        
        // Validation Page - Code to click the radio button selector
        // Currently Selecting 1st Radio Button Only
        if (schematron.equals("eInvoice")) {
        	ValidationPage.SelectAUNZeInvoice(driver).click();
	           }
		 else if  (schematron.equals("PINT")) {
			 ValidationPage.SelectPINT(driver).click();
	           }
		 else {
			 System.out.println("ERROR - Radio Button Selection not Found"); 
		 }
        
        
        // Validation Page - store the window handle so we can return after closing tabs
    	String mainWindowHandle = "THE-MAIN-HANDLE";
    	for (String winHandle:driver.getWindowHandles()) {
    	mainWindowHandle = winHandle;
    	 System.out.println("mainWindowHandle = " + mainWindowHandle);
    	}
        
        // Open List of Files to be tested
		FileInputStream fis = null;
		try {
			// fis = new FileInputStream("C:\\e-Invoice\\Automation_Project\\TestFiles\\TestFileList.txt");
			fis = new FileInputStream(testFileLocation);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner sc = new Scanner(fis);
		//System.out.println("File List Located\n"); [debug only]
		// File Read
		while (sc.hasNextLine()) {
			// System.out.println("Reading Next Line"); [debug only]
	       	fileName=sc.nextLine();
	       	
	       	// Set the expected result to the filename (without the .xml)
	       	expectedResult = fileName;
	       	if (expectedResult.endsWith(".xml")) {
	       		expectedResult = expectedResult.replace(".xml", "");
	       	    System.out.println("FileName : " + fileName);
	       	}
	       	   	
	       	fullPath = "C:\\e-Invoice\\Automation_Project\\TestFiles\\" + fileName;
			// Test the File
	       	ValidationPage.UploadFile(driver).sendKeys(fullPath);

	       	String resultString = ValidationPage.GetResult(driver).getText();
	        System.out.println("Result = " + resultString);
	            
	        // Open & Close tab to get detailed results
	        // But only if An Issue is detected	 
	        if (resultString.equals("No issues detected")) {
	        	actualResult = "Valid01";
	           	// continue with next test
	           }
	         else {            	
	           	// Click View Report button to open tab
	            ValidationPage.ViewReport(driver).click();
	            // read result
	            String messageText = DetailedReportPage.GetErrorMessage(driver).getText();
	            // System.out.println(fileName + " : " + messageText);	  
	            actualResult = messageText;	            
	        	driver.switchTo().window(mainWindowHandle);
	            }
	         
	            // Result Metrics:
	        	if (actualResult.equals(expectedResult)) {
	        		passed++;
	        		System.out.println(expectedResult + " : " + actualResult + " : This Test Passed");
	        	} else {
	        		failed++;
	        		System.out.println(expectedResult + " : " + actualResult + " : This Test Failed");
	        	} 
	        	
	        	System.out.println("=================================================");	        	
      
	        	// Clear the result to get a new result string (clear button)
	            ValidationPage.ClearResult(driver).click();	
	            
  
		}
			// Final Metrics
			System.out.println("***********************************");
			System.out.println("Passed " + passed );
			System.out.println("Failed " + failed );
			System.out.println("***********************************");
		
			sc.close();
			driver.quit();
	}
}

