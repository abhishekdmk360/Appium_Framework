package org.abhishek.testUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Properties;

import org.abhishek.pageObjects.android.FormPage;
import org.abhishek.utils.AppiumUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class AndroidBaseTest extends AppiumUtils {
	
	public AndroidDriver driver;
	public AppiumDriverLocalService service;
	public FormPage formPage;
	
	@BeforeClass(alwaysRun = true)
	public void configureAppium() throws URISyntaxException, IOException
	{
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\org\\abhishek\\pageObjects\\resources\\data.properties");
		Properties prop = new Properties();
		prop.load(fis);
		
		String ipAddress = System.getProperty("ipAddress") != null ? System.getProperty("ipAddress") : prop.getProperty("ipAddress");
		String port = prop.getProperty("port");
		
		service = startAppiumServer(ipAddress, Integer.parseInt(port));
		service.start();
		
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName(prop.getProperty("androidDeviceName"));
		options.setChromedriverExecutable("./src/test/java/resources/chromedriver.exe");
		options.setApp("./src/test/java/resources/General-Store.apk");
		
		//driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options); // can be replaced by below line
		driver = new AndroidDriver(service.getUrl(), options);
		
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		formPage = new FormPage(driver);
	}
	
	@AfterClass(alwaysRun = true)
	public void teardown()
	{
		driver.quit();
		service.stop();
	}

}
