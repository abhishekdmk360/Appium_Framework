package org.abhishek.pageObjects.android;

import java.util.List;

import org.abhishek.utils.AndroidActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CartPage extends AndroidActions {
	
AndroidDriver driver;
	
	public CartPage(AndroidDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/productPrice")
	private List<WebElement> productList;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/totalAmountLbl")
	private WebElement totalAmount;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/termsButton")
	private WebElement terms;
	
	@AndroidFindBy(id = "android:id/button1")
	private WebElement acceptButton;
	
	@AndroidFindBy(className = "android.widget.CheckBox")
	private WebElement checkbox;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnProceed")
	private WebElement proceed;
	
	public List<WebElement> getProductList()
	{
		return productList;
	}
	
	public double getProductsSum()
	{
		double sum = 0;
		for(WebElement priceElement: productList)
		{
			String amountString = priceElement.getText();
			Double price = getFormattedPrice(amountString);
			sum += price;
		}
		return sum;
	}
	
	public double getTotalAmountDisplayed()
	{
		return getFormattedPrice(totalAmount.getText());
	}
	
	public void acceptTermsConditions()
	{
		longPressAction(terms);
		acceptButton.click();
	}
	
	public void submitOrder()
	{
		checkbox.click();
		proceed.click();
	}

}
