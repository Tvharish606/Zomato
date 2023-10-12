package Base_class_ZMT;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;


import com.gurock.testrail.APIClient;
import com.gurock.testrail.APIException;

import Base_class_ZMT.Test_scripts.Testrail;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Base_class_Zomato 
{
	public WebDriver driver;
	public static String Testrail_Username="harish.v@3ktechnologies.com";
	public static String Testrail_password="Harish@606";
	public static String testrail_Url="https://testrail3ktechnologies2ndversion.testrail.io/";
	public static String Test_Run_ID = "1";
	public static int TestCase_Pass_Status = 1;
	public static int TestCase_Fail_Status = 5;

	APIClient client;
	
	
	
	@BeforeSuite
	public void createsuite(ITestContext cxt) throws MalformedURLException, IOException, APIException
	{
		client=new APIClient(testrail_Url);
		client.setUser(Testrail_Username);
		client.setPassword(Testrail_password);
		
		Map<String ,Object> data=new HashMap<String, Object>();
		data.put("include_all",true);
		data.put("name","Testrail@123");
		
		JSONObject c=null;
		c=(JSONObject)client.sendPost("add_run/"+Test_Run_ID ,data);
		Long SuiteId=(Long)c.get("Id");
		cxt.setAttribute("Suitee_ID", SuiteId);
		
	}
	
	@BeforeTest
	public void Startup()
	{
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(15,TimeUnit.SECONDS);
		driver.navigate().to("https://www.zomato.com");
	}
	@AfterTest
	public void Teardown()
	{
		driver.close();
		
	}
	@BeforeMethod
	public void Beforetest(ITestContext cxt,Method method) throws NoSuchMethodException, SecurityException
	{
		Method m=Test_scripts.class.getMethod(method.getName());
		if(m.isAnnotationPresent(Testrail.class))
		{
			Testrail ta=m.getAnnotation(Testrail.class);
			cxt.setAttribute("CaseId", ta.Id());
		}
	}
	
	
	@AfterMethod
	public void afterTest(ITestResult result, ITestContext ctx) throws IOException, APIException {

		Map<String, Object> data = new HashMap<String, Object>();

		if(result.isSuccess()) {

			data.put("status_id",TestCase_Pass_Status);

		}

		else{

			data.put("status_id",TestCase_Fail_Status);

			data.put("comment", result.getThrowable().toString());

		}

		String caseId = (String)ctx.getAttribute("caseId");

		Long suiteId = (Long)ctx.getAttribute("suiteId");

		client.sendPost("add_result_for_case/"+suiteId+"/"+caseId,data);
//	public void Aftertest(ITestContext cxt,ITestResult tcx) throws MalformedURLException, IOException, APIException
//	{
//		Map<String,Object> data=new HashMap<String,Object>();
//		if((tcx.isSuccess()))
//		{
//			data.put("status_id",TestCase_Pass_Status);
//		}
//		else{
//			data.put("status_id",TestCase_Fail_Status);
//			data.put("comment", tcx.getThrowable().toString());
//		}
//		String caseId = (String)cxt.getAttribute("caseId");
//		Long suiteId = (Long)cxt.getAttribute("suiteId");
//		client.sendPost("add_result_for_case/"+suiteId+"/"+caseId,data);
//	}
}
}