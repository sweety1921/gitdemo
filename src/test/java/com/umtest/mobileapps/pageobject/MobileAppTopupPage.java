package com.umtest.mobileapps.pageobject;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MobileAppTopupPage extends MainUtil {

	private AndroidDriver<?> driver;

	public MobileAppTopupPage(AndroidDriver<?> driver) {
		this.driver = driver;
	}

	
	@AndroidFindBy(accessibility = "button_Top Up")
	private AndroidElement buttonTopUp;
/*
	@AndroidFindBy(xpath = "//android.view.View[@content-desc=\"button_Top Up\"]/android.view.View")
	private AndroidElement buttonTopUp;*/
	
	@AndroidFindBy(xpath = "//android.view.View[@content-desc=\"button_Pay Now\"]/android.widget.TextView")
	private AndroidElement buttonPayNow;
	
	@AndroidFindBy(accessibility = "goLife_button_Visa / Mastercard")
	private AndroidElement buttonVisaMasterCard;
	
	@AndroidFindBy(xpath = "//android.view.View[@content-desc=\"Proceed\"]/android.widget.TextView")
	private AndroidElement buttonProceed;
	
	@AndroidFindBy(accessibility = "Top Up Pin")
	private AndroidElement buttonTopUpPin;
	
	@AndroidFindBy(accessibility = "textField_Enter your 14-Digit Top Up PIN")
	private AndroidElement textboxTopUpPIN;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"button_Top Up Now\"]/android.widget.TextView")
	private AndroidElement buttonTopUpNow;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='OK']")
	private AndroidElement buttonOK;

//	@AndroidFindBy(xpath = "//android.widget.TextView[@text='OK']/preceding::android.widget.TextView[contains(@text,'Top Up successful')]")
@AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Top Up successful')]")
	private AndroidElement textTopUpSuccessMessage;

	@AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Sorry, the voucher you applied is not valid. Please try again with a different voucher')]")
	private AndroidElement textErrorMessageInvalidMessage;

	public AndroidElement getButtonTopUp() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonTopUp);
	}
	
	public AndroidElement selectTopUpAmount(String topupAmount) {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, "//android.view.View[@content-desc=\"cell_RM"+topupAmount+"\"]/android.widget.TextView");
	}

	public AndroidElement getButtonPayNow() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonPayNow);
	}
	
	public AndroidElement getButtonVisaMasterCard() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonVisaMasterCard);
	}
	
	public AndroidElement getButtonProceed() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonProceed);
	}
	
	public AndroidElement getButtonTopUpPin() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonTopUpPin);
	}
	
	public AndroidElement getTextboxTopUpPIN() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, textboxTopUpPIN);
	}
	
	public AndroidElement getButtonTopUpNow() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonTopUpNow);
	}
	
	public AndroidElement getButtonOK() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonOK);
	}

	public String getErrorMessageInvalidTopUpVoucher(){
		return AppWait.waitForElementToBeClickable(driver, textErrorMessageInvalidMessage).getText();
	}

	public boolean isErrorMessageForInvalidTopUpPINDisplayed(){
		return AppWait.waitForElementToBeClickable(driver, textErrorMessageInvalidMessage).isDisplayed();
	}

	public boolean isTopUpSuccessMessageDisplayed(){
		return AppWait.waitForElementToBeClickable(driver, textTopUpSuccessMessage).isDisplayed();
	}
		
}
