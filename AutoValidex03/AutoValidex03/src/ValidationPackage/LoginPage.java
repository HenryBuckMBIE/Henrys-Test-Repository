package ValidationPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class LoginPage {
		
	// Modal Window
	public static WebElement headerPopUp(WebDriver driver) {
		WebElement element = driver.findElement(By.xpath("//button[text()='Close']"));
		return element;
	}

	// Cookie  
		public static WebElement CookiePopUp(WebDriver driver) {
		WebElement element = driver.findElement(By.xpath("//a[@class='cc_btn cc_btn_accept_all']"));
		
		return element;
		}
	
	// Login Email
		public static WebElement LoginEmail(WebDriver driver) {
			WebElement element = driver.findElement(By.name("email"));
			return element;
			}
	
	// Login Password
		public static WebElement LoginPassword(WebDriver driver) {
			WebElement element = driver.findElement(By.name("password"));
			return element;
			}

		public static WebElement LoginButton(WebDriver driver) {
			WebElement element = driver.findElement(By.xpath("//*[@class='form-control btn btn-primary']"));
			return element;
		}


		
}
