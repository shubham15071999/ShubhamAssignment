package Assignment;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
public class Fitpeo {

	public static void main(String[] args) throws Exception {      
       WebDriver driver= new ChromeDriver();
       driver.manage().window().maximize();
       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
       driver.get("https://www.fitpeo.com/revenue-calculator");
      driver.findElement(By.xpath("//div[text()='Revenue Calculator']")).click();
      Thread.sleep(4000);
       JavascriptExecutor jse = (JavascriptExecutor)driver;
       double zoomFactor=1.5;
       jse.executeScript("document.body.style.transform = 'scale(" + zoomFactor + ")';");

       jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//h4[.='Medicare Eligible Patients']")));
       Thread.sleep(1000);
       WebElement slider=driver.findElement(By.xpath("//span[contains(@class,'MuiSlider-thumb MuiSlider-thumbSizeMedium')]/input"));
       WebElement sliderHandle =driver.findElement(By.xpath("//span[contains(@class,'MuiSlider-root MuiSlider-colorPrimary MuiSlider-sizeMedium css-16i48op')]"));

       int minValue = Integer.parseInt(slider.getAttribute("min"));
       System.out.println(minValue);
       double maxValue = Integer.parseInt(slider.getAttribute("max"));
       double desiredValue = 820;
       double divide=((desiredValue/maxValue)*100)-1;
       Actions actions = new Actions(driver);
       actions.clickAndHold(sliderHandle).moveByOffset(-(int)divide, 0).perform();
       // Optionally, verify the slider value after moving it (for input[type='range'])
       WebElement currentValue = driver.findElement(By.xpath("//input[contains(@class,'MuiInputBase-input')]"));
       
       currentValue.getAttribute("value");
       driver.findElement(By.xpath("//h4[.='Medicare Eligible Patients']")).click();
       currentValue.clear();
       Thread.sleep(1000);
       jse.executeScript("arguments[0].value = '560';", currentValue);
       Thread.sleep(2000);
       driver.findElement(By.xpath("//h4[.='Medicare Eligible Patients']")).click();
       String sliderHandleValue =driver.findElement(By.xpath("//span[contains(@class,'MuiSlider-root MuiSlider-colorPrimary MuiSlider')]//input")).getAttribute("value");
       System.out.println("Scrollimg of the slider after add value in the textbox: " + sliderHandleValue);
       Thread.sleep(2000);
       jse.executeScript("document.body.style.transform = 'scale(" + 1 + ")';");
       Thread.sleep(2000);
       driver.findElement(By.xpath("//h4[.='Medicare Eligible Patients']")).click();
       List<WebElement> names = driver.findElements(By.xpath("//div[contains(@class,'MuiBox-root')]/label/preceding-sibling::p[2]"));
       double count=0;
       for(int i=0;i<names.size();i++) {
    	  String name= names.get(i).getText();
    	  if(name.equalsIgnoreCase("CPT-99091") || name.equalsIgnoreCase("CPT-99453") || name.equalsIgnoreCase("CPT-99454") || name.equalsIgnoreCase("CPT-99474")) {
    		  driver.findElement(By.xpath("//div[contains(@class,'MuiBox-root')]/p[.='"+name+"']/following-sibling::label/span/input")).click();
    		double num=Double.parseDouble(driver.findElement(By.xpath("//div[contains(@class,'MuiBox-root')]/p[.='"+name+"']/following-sibling::label/span[2]")).getText());
    		  System.out.println(num);
    		  try {
     		 if( driver.findElement(By.xpath("//p[.='"+name+"']/following-sibling::div//span[.='Recurring in 30 days']")).isDisplayed()) {
         		count=count+num*820;
     		 }
    		  System.out.println(count);
    		  }
              catch(Exception e) {
              }
       }
	}    System.out.println(count);
	String reimbursementActual=driver.findElement(By.xpath("//p[.='Total Recurring Reimbursement for all Patients Per Month']/following-sibling::p")).getText();
	reimbursementActual=reimbursementActual.replace("$", "");
	double d=Double.parseDouble(reimbursementActual);
	if(count==d) {
		System.out.println("pass");
	}
	driver.quit();
	}
	}
