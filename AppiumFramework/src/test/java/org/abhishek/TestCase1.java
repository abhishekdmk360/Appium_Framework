package org.abhishek;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.abhishek.pageObjects.android.CartPage;
import org.abhishek.pageObjects.android.ProductCatalogue;
import org.abhishek.testUtils.AndroidBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class TestCase1 extends AndroidBaseTest{
	
	@BeforeMethod
	public void preSetup()
	{
		//Note SplashActivity is not working , in backend. Need to find a solution. Nor MainActivity working.
		formPage.setActivity();
	}
	
	@Test(dataProvider = "getData", groups = {"smoke"})
	public void e2e_flow(HashMap<String, String> input) throws InterruptedException
	{
		formPage.setNameField(input.get("name"));
		formPage.setGender(input.get("gender"));
		formPage.setCountry(input.get("country"));
		ProductCatalogue productCatalogue = formPage.submitForm();;
		productCatalogue.addItemToCartByIndex(0);
		productCatalogue.addItemToCartByIndex(0);
		CartPage cartPage = productCatalogue.goToCartPage();
		
		
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.textToBe(By.id("com.androidsample.generalstore:id/toolbar_title"), "Cart"));
		
		double totalSum = cartPage.getProductsSum();
		double displayFormattedSum = cartPage.getTotalAmountDisplayed();
		Assert.assertEquals(displayFormattedSum, totalSum);
		cartPage.acceptTermsConditions();
		cartPage.submitOrder();
		
		Thread.sleep(8000);
		
	}
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		//return new Object[][] {{"Abhishek","Male","Argentina"},{"Roshni","Female","Argentina"}};
		
		List<HashMap<String, String>> data = getJsonData(System.getProperty("user.dir")+"\\src\\test\\java\\org\\abhishek\\testData\\eCommerce.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}

}
