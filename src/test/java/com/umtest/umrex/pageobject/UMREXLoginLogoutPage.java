package com.umtest.umrex.pageobject;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class UMREXLoginLogoutPage extends MainUtil {

	private AndroidDriver<?> driver;

	public UMREXLoginLogoutPage(AndroidDriver<?> driver) {
		this.driver = driver;
	}

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='UAT']")
	private AndroidElement envSpinnerUAT;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='PIXEL']")
	private AndroidElement envSpinnerPIXEL;

	@AndroidFindBy(id = "com.fl.pra:id/agentCode")
	private AndroidElement textboxUsername;
	
	@AndroidFindBy(id = "com.fl.pra:id/password")
	private AndroidElement textboxPassword;

	@AndroidFindBy(id = "com.fl.pra:id/login_btn")
	private AndroidElement buttonLogin;
	
	@AndroidFindBy(accessibility = "Open navigation drawer")
	private AndroidElement burgerButtonMenu;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Bypass Setting']")
	private AndroidElement optionBypassSetting;
	
	@AndroidFindBy(id = "com.fl.pra:id/scScanId")
	private AndroidElement toggleBypassScanID;
	
	@AndroidFindBy(id = "com.fl.pra:id/scReadId")
	private AndroidElement toggleBypassReadID;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Logout']")
	private AndroidElement buttonLogout;
	
	@AndroidFindBy(id = "android:id/button1")
	private AndroidElement buttonYES;

	@AndroidFindBy(id = "android:id/button1")
	private AndroidElement dialogLoading;

	public AndroidElement getEnvSpinnerUAT() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, envSpinnerUAT);
	}

	public AndroidElement getEnvSpinnerPIXEL() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, envSpinnerPIXEL);
	}

		public AndroidElement getTextboxUsername() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, textboxUsername);
	}
	
	public AndroidElement getTextboxPassword() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, textboxPassword);
	}
	
	public AndroidElement getButtonLogin() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonLogin);
	}
	
	public AndroidElement getBurgerButtonMenu() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, burgerButtonMenu);
	}
	
	public AndroidElement getOptionBypassSetting() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, optionBypassSetting);
	}
	
	public AndroidElement getToggleBypassScanID() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, toggleBypassScanID);
	}
	
	public AndroidElement getToggleBypassReadID() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, toggleBypassReadID);
	}
	
	public AndroidElement getButtonLogout() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonLogout);
	}
	
	public AndroidElement getButtonYES() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonYES);
	}
	
}
