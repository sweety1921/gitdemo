package com.umtest.umrex.pageobject;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class UMREXHomePage extends MainUtil {

	private AndroidDriver<?> driver;

	public UMREXHomePage(RemoteWebDriver driver) {
		//super((AndroidDriver) driver);
		this.driver = (AndroidDriver) driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);
	}

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.fl.pra:id/tvHomeOptionDesc' and @text='Prepaid']")
	private AndroidElement buttonPrepaid;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.fl.pra:id/tvChangeOfferLabel' and @text='Change Offer & Phone Bundle']")
	private AndroidElement buttonChangeOfferAndPhoneBundle;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.fl.pra:id/tvHomeOptionDesc' and @text='Change Offer']")
	private AndroidElement buttonChangeOffer;

	@AndroidFindBy(xpath = "//android.widget.ImageButton[@resource-id='com.fl.pra:id/home_scan_card_auto']")
	private AndroidElement buttonSCANID;

	@AndroidFindBy(xpath = "//android.widget.ImageButton[@resource-id='com.fl.pra:id/home_scan_card_manual']")
	private AndroidElement buttonREADID;

	@AndroidFindBy(xpath = "//android.widget.ImageButton[@resource-id='com.fl.pra:id/home_transaction_history']")
	private AndroidElement buttonSUBMITTEDRECORD;

	@AndroidFindBy(xpath = "//android.widget.ImageButton[@resource-id='com.fl.pra:id/home_pending_upload']")
	private AndroidElement buttonPENDINGRECORD;

	@AndroidFindBy(xpath = "//android.widget.ImageButton[@resource-id='com.fl.pra:id/home_topup']")
	private AndroidElement buttonTOPUP;

	@AndroidFindBy(xpath = "//android.widget.ImageButton[@resource-id='com.fl.pra:id/home_topup_history']")
	private AndroidElement buttonTOPUPRECORD;

	@AndroidFindBy(xpath = "//android.widget.ImageButton[@resource-id='com.fl.pra:id/home_purchase_record']")
	private AndroidElement buttonPURCHASERECORD;

	public void clickPrepaidButton(){
		clickElement(AppWait.waitForElementToBeClickable(driver, buttonPrepaid),"Prepaid Button", driver);
	}

	public void clickChangeOfferPhoneBundle(){
		clickElement(AppWait.waitForElementToBeClickable(driver, buttonChangeOfferAndPhoneBundle),"Change Offer & Phone Bundle", driver);
	}

	public void clickChangeOffer(){
		clickElement(AppWait.waitForElementToBeClickable(driver, buttonChangeOffer),"Change Offer Button", driver);
	}

	public WebElement getButtonSCANID(){
		return AppWait.waitForElementToBeClickable(driver, buttonSCANID);
	}

	public WebElement getButtonREADID(){
		return AppWait.waitForElementToBeClickable(driver, buttonREADID);
	}

	public WebElement getButtonSUBMITTEDRECORD(){
		return AppWait.waitForElementToBeClickable(driver, buttonSUBMITTEDRECORD);
	}

	public WebElement getButtonPENDINGRECORD(){
		return AppWait.waitForElementToBeClickable(driver, buttonPENDINGRECORD);
	}

	public WebElement getButtonTOPUP(){
		return AppWait.waitForElementToBeClickable(driver, buttonTOPUP);
	}

	public WebElement getButtonTOPUPRECORD(){
		return AppWait.waitForElementToBeClickable(driver, buttonTOPUPRECORD);
	}

	public WebElement getButtonPURCHASERECORD(){
		return AppWait.waitForElementToBeClickable(driver, buttonPURCHASERECORD);
	}

	public void clickTopUpButton(){
		clickElement(AppWait.waitForElementToBeClickable(driver, buttonTOPUP),"Top Up Button", driver);
	}

/*
	public boolean isButtonSCANIDDisplayed(){
		return isElementDisplayed(AppWait.waitForElementToBeClickable(driver, buttonSCANID));
	}

	public boolean isButtonREADIDDisplayed(){
		return isElementDisplayed(AppWait.waitForElementToBeClickable(driver, buttonREADID));
	}

	public boolean isButtonSUBMITTEDRECORDDisplayed(){
		return isElementDisplayed(AppWait.waitForElementToBeClickable(driver, buttonSUBMITTEDRECORD));
	}

	public boolean isButtonPENDINGRECORDDisplayed(){
		return isElementDisplayed(AppWait.waitForElementToBeClickable(driver, buttonPENDINGRECORD));
	}

	public boolean isButtonTOPUPDisplayed(){
		return isElementDisplayed(AppWait.waitForElementToBeClickable(driver, buttonTOPUP));
	}

	public boolean isButtonTOPUPRECORDDisplayed(){
		return isElementDisplayed(AppWait.waitForElementToBeClickable(driver, buttonTOPUPRECORD));
	}

	public boolean isButtonPRURCHASERECORDDisplayed(){
		return isElementDisplayed(AppWait.waitForElementToBeClickable(driver, buttonPURCHASERECORD));
	}*/






}
