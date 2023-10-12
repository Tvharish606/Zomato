package Base_class_ZMT;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test_scripts extends Base_class_Zomato
{
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface Testrail
	{
		String Id() default "none";
	}
	@Testrail(Id="3")
	@Test(priority = 0)
	public void TestScript1() throws InterruptedException
	{
	WebElement search_textfield = driver.findElement(By.xpath("//input[@placeholder='Search for restaurant, cuisine or a dish']"));
	search_textfield.click();
	search_textfield.sendKeys("Birayani");
	driver.findElement(By.xpath("//div[@class='sc-bYTsla dHqbfv']")).click();
	if(driver.getPageSource().contains("Delivery Restaurants"))
	{
		Assert.assertTrue(true);
	}
}
//	@Test
//	public void Testscript2()
//	{
//		WebElement search_textfield = driver.findElement(By.xpath("//input[@placeholder='Search for restaurant, cuisine or a dish']"));
//		search_textfield.click();
//		search_textfield.sendKeys("veg");
//		driver.findElement(By.xpath("//div[@class='sc-bYTsla dHqbfv']")).click();
//		if(driver.getPageSource().contains("Delivery Restaurants"))
//		{
//			Assert.assertTrue(true);
//		}
//		}
	}
