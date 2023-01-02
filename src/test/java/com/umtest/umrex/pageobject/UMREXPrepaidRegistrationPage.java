package com.umtest.umrex.pageobject;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class UMREXPrepaidRegistrationPage extends MainUtil {

	private AndroidDriver<?> driver;

	public UMREXPrepaidRegistrationPage(RemoteWebDriver driver) {
		this.driver = (AndroidDriver) driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);
	}

	@AndroidFindBy(id = "com.fl.pra:id/home_topup")
	private AndroidElement buttonTopUp;

	public AndroidElement getImageButtonTopUp() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonTopUp);
	}

	public void clickTopUpButton(){
		clickElement(AppWait.waitForElementToBeClickable(driver, buttonTopUp),"Top Up Button",driver);
	}
	
}
