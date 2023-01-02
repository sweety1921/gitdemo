package com.umtest.mobileapps.pageobject;

import com.umtest.mobileapps.pagefunction.MobileAppUtilFuncs;
import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import com.umtest.testframe.lib.ApplicationUtil;

import java.time.Duration;
import java.util.List;

public class MobileAppSwitchPlanPage extends MainUtil {
	private static Logger logger = LogManager.getLogger(MobileAppSwitchPlanPage.class);
	private RemoteWebDriver driver;
	private MobileAppSideMenu sideMenu;

	public MobileAppSwitchPlanPage(RemoteWebDriver driver) {
		this.driver = (AndroidDriver) driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);
		sideMenu=new MobileAppSideMenu(driver);
	}

	//String varXPathLabelCurrentPlan="//android.widget.TextView[@text='xxxxCurrentPlanNamexxxx' and @index=1]";


	String varXPathLabelCurrentPlan="(//android.widget.TextView[@text='Your Current Plan is']/following::android.widget.TextView[@text='xxxxCurrentPlanNamexxxx' and @index=1])[1]";
	String varXPathLabelNewPlan="(//android.widget.TextView[@text='Switch my Prepaid Plan']/following::android.widget.TextView[@text='xxxxNewPlanNamexxxx'])[1]";

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='New U Prepaid Plan']")
	private AndroidElement labelCurrentPlan;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Unlimited Funz Plan']")
	private AndroidElement labelNewPlan;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Submit']")
	private AndroidElement submitButton;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Confirm']")
	private AndroidElement confirmButton;

	@AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'You have requested to switch to')]")
	private AndroidElement textConfirmMessage;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Success' and @index='1']")
	private AndroidElement textSuccessMessage;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Thank you! Your request is being processed. You will receive confirmation via SMS']")
	private AndroidElement textSuccessThankYouMessage;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='OK' and @index='1']")
	private AndroidElement OKButton;

	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Submit\"]/android.widget.TextView")
	private WebElement switchPlanSubmitButton;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Confirm']")
	private WebElement switchPlanConfirmButton;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='OK']")
	private WebElement switchPlanOkButton;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Thank you! Your request is being processed. You will receive confirmation via SMS']")
	private WebElement switchPlanSuccessMsg;

	@AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"button_linkTarget_Upgrade Plan\"]")
	private WebElement Upgradeplanbutton;

	public void clickSubmit(){
		clickElement(submitButton, "Submit Button", driver);
	}

	public void clickConfirm(){
		clickElement(confirmButton, "Confirm Button", driver);
	}

	public boolean isCurrentPlanNameDisplayed(String planName){
       return verifyElementExistUsingXpathString(varXPathLabelCurrentPlan.replace("xxxxCurrentPlanNamexxxx",planName),"Current Plan Name",driver);
	}

	public boolean isNewPlanNameDisplayed(String newPlanName){
		return verifyElementExistUsingXpathString(varXPathLabelNewPlan.replace("xxxxNewPlanNamexxxx",newPlanName),"New Plan Name",driver);
	}

	public boolean isConfirmationMessageDisplayed(){
		return isElementDisplayed(AppWait.waitForElementToBeClickable(driver, textConfirmMessage));
	}

	public boolean verifySuccessMessage(){
		return isElementDisplayed(AppWait.waitForElementToBeClickable(driver, textSuccessMessage));
	}

	public boolean verifySuccessThankYouMessage(){
		return isElementDisplayed(textSuccessThankYouMessage);
	}

	public void clickOKButton(){
		clickElement(OKButton, "OK Button", driver);
	}

	public AndroidElement getSwitchPlanSubmitButton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, switchPlanSubmitButton);
	}

	public AndroidElement getSwitchPlanConfirmButton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, switchPlanConfirmButton);
	}

	public AndroidElement getSwitchPlanOkButton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, switchPlanOkButton);
	}

	public AndroidElement getSwitchPlanSuccessMsg() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, switchPlanSuccessMsg);
	}

	public String getSwitchPlanSuccessMessage(){
		String sXpathSuccessMsg="//android.widget.TextView[@text='Thank you! Your request is being processed. You will receive confirmation via SMS']";
		return getTextUsingXpath(sXpathSuccessMsg,"Success message",driver);
	}

	public String getSuccessTitle(){
		String sXpathSuccessTitle="//android.widget.TextView[@text='Success']";
		return getTextUsingXpath(sXpathSuccessTitle,"Success title",driver);
	}

	public AndroidElement getUpgradeplanbutton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Upgradeplanbutton);
	}

	public boolean doSwitchPlan(String currentPlanName, String newPlanName){

		boolean flag = false;
		try {

//			sideMenu.clickSideMenu();
//			sideMenu.navigateToSwitchPlan();
//
//			Assert.assertTrue(isCurrentPlanNameDisplayed(currentPlanName),"Current Plan Name NOT displayed - '"+currentPlanName+"'");
//			Assert.assertTrue(isNewPlanNameDisplayed(newPlanName),"New Plan Name NOT displayed - '"+newPlanName+"'");
//
//			scrollUDLR((MobileDriver)driver, 0, "U");
//			scrollUDLR((MobileDriver)driver, 0, "U");
//			scrollUDLR((MobileDriver)driver, 0, "U");
//
//			clickSubmit();
//			captureAppScreenshot("Info: Capture Image", driver);
//			Assert.assertTrue(isConfirmationMessageDisplayed(),"Confirmation Message NOT displayed");
//
//			clickConfirm();
//			captureAppScreenshot("Info: Capture Image", driver);
//			Assert.assertTrue(verifySuccessMessage(),"Success Message NOT displayed");
//			Assert.assertTrue(verifySuccessThankYouMessage(),"Success. Thank You... Message NOT displayed");
//
//			clickOKButton();
//			captureAppScreenshot("Info: Capture Image", driver);
//			flag=true;
//
//			ApplicationUtil.updateRateplanNameUpdated(MainUtil.dictionary.get("MSISDN"), newPlanName) ;

			clickElement(getUpgradeplanbutton(), "Click Upgrade Plan Link", driver);

			scrollUDLR((MobileDriver)driver, 0, "U");
			scrollUDLR((MobileDriver)driver, 0, "U");
			scrollUDLR((MobileDriver)driver, 0, "U");

			clickElement(getSwitchPlanSubmitButton(), "Click Submit Button", driver);
			clickElement(getSwitchPlanConfirmButton(), "Click Confirm Button", driver);
			Thread.sleep(10000);
			MainUtil.compareInString(getSuccessTitle(),"Success","Success Title", driver);
			MainUtil.compareInString(getSwitchPlanSuccessMessage(),"Thank you! Your request is being processed. You will receive confirmation via SMS","Success Message", driver);
			captureAppScreenshot("Info: Capture Image", driver);
			clickElement(getSwitchPlanOkButton(), "Click on Ok Button", driver);
			Thread.sleep(10000);
			flag=true;

			ApplicationUtil.updateRateplanNameUpdated(MainUtil.dictionary.get("MSISDN"), newPlanName) ;
			MainUtil.dictionary.put("CURRENT_MAIN_BALANCE",dictionary.get("MAIN_BALANCE"));
			MainUtil.dictionary.put("PLAN_NAME", MainUtil.dictionary.get("NEW_PLAN_NAME"));
			System.out.println(MainUtil.dictionary.get("NEWPLAN_NAME"));
			MainUtil.dictionary.put("NEWPLAN_NAME", MainUtil.dictionary.get("NEW_PLAN_NAME"));
			ApplicationUtil.updateCRPDetails(MainUtil.dictionary.get("MSISDN"));

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while performing mobile apps prepaid switch plan :" + e);
		}
		return flag;
	}
		
}
