package com.umtest.mobileapps.pageobject;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;

public class MobileAppLoginPage extends MainUtil {

	private AndroidDriver<?> driver;

	public MobileAppLoginPage(AndroidDriver<?> driver) {
		this.driver = driver;
	}
/*
	@AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='Login' and contains(@text,'Welcome Aboard')]/preceding::android.widget.ImageView[1]")
	private AndroidElement buttonENVSetting;*/

	@AndroidFindBy(xpath = "//*[@content-desc='button_environmentSettings']")
	private AndroidElement buttonENVSetting;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Stage 2']")
	private AndroidElement selectStage2;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Stage 3']")
	private AndroidElement selectStage3;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Stage 1']")
	private AndroidElement selectStage1;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='OK']")
	private AndroidElement buttonOK;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Cancel']")
	private AndroidElement buttonCancel;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Login']")
	private AndroidElement buttonLogin;
	
	@AndroidFindBy(accessibility = "newLogin_textfield_mobile")
	private AndroidElement textboxMobileNumber;
	
	@AndroidFindBy(accessibility = "newLogin_textfield_pin")
	private AndroidElement textboxPIN;

	@AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='verifyOtp_testfield_mobile']")
	private AndroidElement OPTPIN;

	@AndroidFindBy(xpath = "//*[@content-desc=\"tutorials_button_close\"]")
	private AndroidElement buttonWelcomeScreenCancel;

	@AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"button_quickaccess\"])[2]/android.widget.TextView[1]")
	private AndroidElement buttonFirstTimeLogin;

	@AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='firstTimeLogin_testfield_mobile']")
	private AndroidElement textboxFirstTimeLoginMobileNumber;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Continue\"]/android.view.View")
	private AndroidElement buttonLoginButtonFirstTime;

	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Create\"]/android.widget.TextView")
	private AndroidElement buttoncreatepin;

	@AndroidFindBy(xpath = "//*[@content-desc=\"firstTimeLogin_back_button_mobile\"]")
	private AndroidElement buttonLoginButtonFirstTimeBackButton;

	@AndroidFindBy(xpath = "//android.view.ViewGroup[2]/android.view.ViewGroup/android.widget.TextView")
	private AndroidElement dialogboxMessage;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Update Now']")
	private AndroidElement dialogboxButtonUpdateNow;

	private String xPathUpdateNowButton="//android.widget.TextView[@text='Update Now']";

	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='MyProfile_goBack_Button']")
	private AndroidElement backArrowButtonMyProfile1;

	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='MyProfile_goBack_Button']")
	private AndroidElement backArrowButtonMyProfile;

	@AndroidFindBy(accessibility = "sidemenu_button_menu")
	private AndroidElement imgSideMenu;

	@AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Home')]")
	private AndroidElement linkMenuHome;

	@AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='newPin_textfield_mobile']")
	private AndroidElement newpin;

	@AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='confirmPin_textfield_mobile']")
	private AndroidElement Confirmpin;

	public AndroidElement getDialogboxButtonUpdateNow() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, dialogboxButtonUpdateNow);
	}

	public void clickUpdateNowButton(){
		clickElementUsingXpathString(xPathUpdateNowButton,"Update Now (Dialog button)",driver);
	}

	public boolean isUpdateNowButtonExists(){
		return isElementExists(By.xpath(xPathUpdateNowButton),driver);
	}

	public AndroidElement getBackArrowButtonMyProfile() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, backArrowButtonMyProfile);
	}

	public void clickBackArrowMyProfile(){
		String sXpathBackArrowMyProfile="//android.view.ViewGroup[@content-desc='MyProfile_goBack_Button']";
		clickElementUsingXpathString(sXpathBackArrowMyProfile,"Back Arrow (My Profile ccc)",driver);
	}

	public AndroidElement getButtonENVSetting() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonENVSetting);
	}
	
	public AndroidElement getSelectStage2() {
		return selectStage2;
	}

	public AndroidElement getSelectStage3() {
		return selectStage3;
	}

	public AndroidElement getOPTPIN() {
		return OPTPIN;
	}

	public AndroidElement getSelectStage1() {
		return selectStage1;
	}

	public AndroidElement getButtonOK() {
		return buttonOK;
	}

	public AndroidElement getButtonCancel() {
		return buttonCancel;
	}
	
	public AndroidElement getTextboxMobileNumber() {
		return textboxMobileNumber;
	}

	public void enterMSISDN(String sMSISDN) {
		sendKeys(getTextboxMobileNumber(), sMSISDN, "Mobile Number", "", driver);
	}

	public AndroidElement getTextboxPIN() {
		return textboxPIN;
	}

	public void enterPIN(String sMSISDN) throws Exception {
		sendKeys(getTextboxPIN(), ApplicationUtil.getSelfcareLoginPin(sMSISDN), "PIN", "", driver);
	}
	
	public AndroidElement getButtonLogin() {
		return buttonLogin;
	}

	public void clickLoginButton(){
		clickElement(getButtonLogin(), "'Login' button", driver);
	}
	
	public AndroidElement getButtonWelcomeScreenCancel() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonWelcomeScreenCancel);
	}
	
	public AndroidElement getButtonFirstTimeLogin() {
		return buttonFirstTimeLogin;
	}

	public void clickFirstTimeLoginButton(){
		clickElement(getButtonFirstTimeLogin(), "First Time Login button", driver);
	}
	
	public AndroidElement getTextboxFirstTimeLoginMobileNumber() {
		return textboxFirstTimeLoginMobileNumber;
	}

	public void enterFirstTimeLoginMobileNumber(String sMSISDN) {
		sendKeys(getTextboxFirstTimeLoginMobileNumber(), sMSISDN, "Mobile Number", "", driver);
	}

	public AndroidElement getnewpin() {
		return newpin;
	}

	public void enterNewpin(String newpinnum) {
		sendKeys(getnewpin(), newpinnum, "pin Number", "", driver);
	}

	public AndroidElement getConfirmpin() {
		return Confirmpin;
	}

	public void enterNewPinconfirm(String newpinconfirm) {
		sendKeys(getConfirmpin(), newpinconfirm, "pin Number", "", driver);
	}


	public AndroidElement getButtonLoginFirstTime() {
		return buttonLoginButtonFirstTime;
	}
	public void clickContinueFirstTimeLoginButton() {
		clickElement(getButtonLoginFirstTime(), "First Time Login 'Continue' button", driver);
	}

	public AndroidElement getcreatepin() {
		return buttoncreatepin;
	}
	public void clickcreatepinbutton() {
		clickElement(getcreatepin(), "First Time Login 'Continue' button", driver);
	}


	public AndroidElement getBackButtonLoginFirstTime() {
		return buttonLoginButtonFirstTimeBackButton;
	}

	public AndroidElement getDialogboxMessage() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, dialogboxMessage);
	}

	public void clickOkButtonIfExistsSorryServiceUnavailableError() throws InterruptedException {
		System.out.println("Check for Error dialog Sorry, the service is temporarily unavailable... ");
		Thread.sleep(1000);
		setDriverImplicitWait(0, driver);
		if(isElementExists(By.xpath("//android.widget.TextView[@text='OK']"),driver)){
			clickElement(getButtonOK(), "OK Button [Sorry, the service is temporarily unavailable...]", driver);
			System.out.println("Clicked Error dialog button - 'Sorry, the service is temporarily unavailable...'");
		}
		setDriverImplicitWait(60, driver);
	}

	public void handleNewUpdatesDialog() throws InterruptedException {
		System.out.println("New Updates dialog");
		Thread.sleep(2000);
		setDriverImplicitWait(0, driver);
		if(isElementExists(By.xpath("//android.widget.TextView[@text='Download Now']"),driver)){
			captureAppScreenshot("Screen Capture: New Updates dialog",driver);
			clickElement(getButtonCancel(), "Cancel Button", driver);
			System.out.println("Clicked Cancel button - 'New Updates Dialog'");
		}
		setDriverImplicitWait(60, driver);
	}

	/*public void handleClickCancelNewUpdatesDialog() throws InterruptedException {
		System.out.println("New Updates dialog... ");
		Thread.sleep(200);
		setDriverImplicitWait(0, driver);
		if(isElementExists(By.xpath("//android.widget.TextView[@text='Oops, looks like you re on the old version of the app. Update to the new & improved']"),driver)){
			clickElement(getButtonCancel(), "Cancel Button", driver);
			System.out.println("Clicked Cancel button - 'New Updates Dialog..'");
		}
		setDriverImplicitWait(60, driver);
	}*/


	public AndroidElement getSideMenu() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, imgSideMenu);
	}

	public AndroidElement getMenuHome() {
		return linkMenuHome;
	}

	
}
