package com.umtest.mobileapps.pageobject;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.FindBy;

public class MobileAppUtilPage extends MainUtil {

	private static AndroidDriver<?> driver;

	public MobileAppUtilPage(AndroidDriver<?> driver) {
		this.driver = driver;
	}


	@AndroidFindBy(xpath = "//android.widget.TextView[@text='View All Usage Details']")
	private AndroidElement buttonViewUsageDetails;

	@AndroidFindBy(xpath = "(//*[contains(@content-desc,\"usageDetail_cell\")])[1]/android.widget.TextView")
	private AndroidElement firstUsageDetailsCategory;

	@AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,\"usageDetail_cell\")]/android.widget.TextView")
	private WebElement usageDetailsCategory;

	@AndroidFindBy(xpath = "//android.widget.TextView[contains(@content-desc,'text_amount_')]")
	private WebElement labelAccountBalance;

	@AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc='sidemenu_button_menu']/following::android.widget.TextView[contains(@content-desc,'text_titleDesc_')])[1]")
	private WebElement labelPlanName;

	@AndroidFindBy(xpath = "//android.widget.TextView[contains(@content-desc,'text_titleDesc_')]")
	private WebElement labelPlanNameMobApp;

	@AndroidFindBy(xpath = "(//android.widget.TextView[@text='Credit Balance']/preceding::android.widget.TextView[1])[1]")
	private WebElement labelPlanName1;

	@AndroidFindBy(xpath = "//*[@content-desc=\"sidemenu_button_menu\"]/android.widget.ImageView")
	private WebElement MenuIcon;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Top Up & Credit']")
	private WebElement MenuTopUpCredit;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='CreditShare']")
	private WebElement MenuCreditShare;
	
//	@AndroidFindBy(xpath = "//android.view.ViewGroup[2]/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.TextView[1]")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='CreditShare Balance']//preceding-sibling::android.widget.TextView")
	private WebElement labelCreditShareBal;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[2]/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.TextView[1]")
	private WebElement labelCreditBal;
	
	@AndroidFindBy(xpath = "//*[@content-desc=\"Transfer Credit\"]")
	private WebElement buttonTransferCredit1;

	/*@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Transfer Credit\"]/android.view.View")
	private WebElement buttonTransferCredit1;*/
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Request Credit\"]/android.widget.TextView")
	private WebElement buttonReqCredit;
	
	@AndroidFindBy(xpath = "//android.widget.EditText[@content-desc=\"credit_textfield_contact\"]")
	private WebElement textReceiverMsisdn;

	@AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"Transfer Credit\"])[2]/android.widget.TextView")
	private WebElement buttonTransferCredit2;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Transfer']")
	private WebElement buttonTransferConfirm;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='OK']")
	private WebElement buttonOK;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"cell_RM1\"]")
	private WebElement buttonRM1;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"cell_RM3\"]")
	private WebElement buttonRM3;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"cell_RM5\"]")
	private WebElement buttonRM5;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"cell_RM10\"]")
	private WebElement buttonRM10;
	

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Bills and Payment']")
	private WebElement MenuBillsandPayment;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Bill History']")
	private WebElement MenuBillHistory;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Services']")
	private WebElement MenuServices;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='GoLife']")
	private WebElement MenuGoLife;
	
	/*@AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"goLife_button_viewDetails\"])[2]/android.widget.TextView")
	private WebElement ButtonGoLife5;
	
	@AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"goLife_button_viewDetails\"])[4]/android.widget.TextView")
	private WebElement ButtonGoLife10;*/

	@AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'golife_cell[1]_ GoLife 5')]/child::android.view.ViewGroup[contains(@content-desc,'goLife_button_viewDetails')]")
	private WebElement ButtonGoLife5;

	@AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'golife_cell[2]_ GoLife 10')]/child::android.view.ViewGroup[contains(@content-desc,'goLife_button_viewDetails')]")
	private WebElement ButtonGoLife10;
	
	@AndroidFindBy(xpath = "@AndroidFindBy(xpath = \"//android.widget.TextView[@text='Subscribe']\")")
	private WebElement ButtonSubscribe;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='goLife_textField_email']")
	private WebElement Emailaddress;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='goLife_textField_confirmEmail']")
	private WebElement ConfirmEmailaddress;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Next']")
	private WebElement Nextbutton;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Yes']")
	private WebElement Yesbutton;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='I confirm that the information above is true, complete and acurate and agree thatthe information above shall be provided by U Mobile Sdn Bjd to Sun Life Malaysia Assurance Berhad for the purpose of my GOLIFE 5 / GOLIFE 10 application.']")
	private WebElement Confirmcheckbox;
		
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Please click the tick box if you agree to accept the terms and conditions above']")
	private WebElement Termandconditioncheckbox;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Thank you for subscribing']")
	private WebElement Successmessage;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Close']")
	private WebElement Closebutton;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='View Certificate']")
	private WebElement ViewCertificatebutton;
	
	@AndroidFindBy(xpath = "//android.view.View[@text='Upgrade Plan']")
	private WebElement Upgradeplandesc;

	@AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"button_linkTarget_Upgrade Plan\"]")
	private WebElement Upgradeplanbutton;
	
	@AndroidFindBy(xpath = "//android.view.View[@content-desc=\"Change Your Plan Now\"]")
	private WebElement ChangePlanNowbutton;
	
	@AndroidFindBy(xpath = "//android.widget.Button[@text='Change Plan']")
	private WebElement Proceedbutton;
	
	@AndroidFindBy(xpath = "//*[@text='Time Remaining']")
	private WebElement Currntplanname;
	
	@AndroidFindBy(xpath = "//android.widget.Button[@resource-id='crpPlanBoxesNext']")
	private WebElement crpplanboxnextbutton;
	
	@AndroidFindBy(xpath = "//android.widget.Button[@resource-id='crpPlanBoxesPrev']")
	private WebElement crpplanboxPrevbutton;
	
	@AndroidFindBy(xpath = "//*[@text='Monthly Fee']")
	private WebElement monthlyfee;

	@AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='verifyPin_textField_mobile']")
	private WebElement textboxEnterPin;

	@AndroidFindBy(xpath = "//*[@content-desc='verifyPin_button']")
	private WebElement buttonVerifyPin;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='ALL SERVICES']")
	private WebElement labelALLSERVICES;

	@AndroidFindBy(xpath = "//*[@content-desc='services_Add-Ons']")
	private WebElement buttonTextAddOns;


	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Request Successful']")
	private WebElement textSuccessMessage;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Your request is being processed. A confirmation SMS will be sent to you.']")
	private WebElement textYourRequestProcessedMessage;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Mobile App PIN Verification']")
	private WebElement mobAppPinVerification;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Continue']")
	private WebElement continueButton;


//android.view.ViewGroup[@content-desc="sidemenu_button_menu"]



/*
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Top Up & Credit']")
	private AndroidElement MenuTopUpCredit;*/

	//    TOP UP FOR FRIENDS  screen objects

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Top Up For Friends']")
	private AndroidElement subMenuTopUpForAFriend;

	@AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='PrePhoneNo']")
	private AndroidElement textboxPhoneNoPrefix;

	/*@AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='PrePhoneNo' and contains(@text,'01X')]")
	private AndroidElement textboxPhoneNoPrefix;
	*/

	@AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='PhoneNo']")
	private AndroidElement textboxPhoneNo;
/*
	@AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='PhoneNo' and contains(@text,'XXXXXXXX')]")
	private AndroidElement textboxPhoneNo;*/

	@AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='EmailAddress']")
	private AndroidElement textboxSenderEmailID;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Terms and Conditions']")
	private AndroidElement chkboxTermsAndConditions;

	@AndroidFindBy(xpath = "//android.widget.Button[@text='Next']")
	private AndroidElement buttonNEXT;

	@AndroidFindBy(xpath = "//android.widget.Button[@text='Pay Now']")
	private AndroidElement buttonPAYNOW;

	@FindBy(xpath = "//input[@name='CARD_HOLDER_NAME']")
	private WebElement textboxCardHolderName;

	@FindBy(xpath = "//input[@id='CARD_NO1']")
	private WebElement textboxCardNo1;

	@FindBy(xpath = "//input[@id='CARD_NO2']")
	private WebElement textboxCardNo2;

	@FindBy(xpath = "//input[@id='CARD_NO3']")
	private WebElement textboxCardNo3;

	@FindBy(xpath = "//input[@id='CARD_NO4']")
	private WebElement textboxCardNo4;

	@FindBy(xpath = "//android.view.ViewGroup[@content-desc='button_Upgrade Plan']")
	private WebElement buttonUpgradePlan;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Credit Transfer Successful']")
	private WebElement creditTransferSuccessmsg;


	public static boolean isButtonUpgradePlanExists(){
		String sXpathButtonUpgradePlan="//android.view.ViewGroup[@content-desc='button_Upgrade Plan']/android.widget.TextView";
		return isElementExists(By.xpath(sXpathButtonUpgradePlan),driver);
	}

	// SERVICE CATEGORY OBJECTS

	public static boolean isServiceCategoryExists(String sServiceCategoryName){
		String sXpathServiceCategory="//android.view.ViewGroup[@content-desc='usageDetail_cell["+sServiceCategoryName+"]']";
		return isElementExists(By.xpath(sXpathServiceCategory),driver);
	}

	public static void  clickServiceCategory(String sServiceCategoryName){
		clickElementUsingXpathString("//android.view.ViewGroup[@content-desc='usageDetail_cell["+sServiceCategoryName+"]']","Service Category - "+sServiceCategoryName,driver);
	}

	public static boolean isServiceCardExists(String sServiceName){
		String sXpathServiceCard="//android.view.ViewGroup[@content-desc='content_"+sServiceName+"']";
		return isElementExists(By.xpath(sXpathServiceCard),driver);
	}

	public static boolean isServiceTitleExists(String sServiceName){
		String sXpathServiceTitle="//android.view.ViewGroup[@content-desc='content_"+sServiceName+"']/child::android.widget.TextView[@content-desc='text_titleDesc_"+sServiceName+"']";
		return isElementExists(By.xpath(sXpathServiceTitle),driver);
	}

	public static int getServiceTitleYAxisLocation(String sServiceName){
		String sXpathServiceTitle="//android.view.ViewGroup[@content-desc='content_"+sServiceName+"']/child::android.widget.TextView[@content-desc='text_titleDesc_"+sServiceName+"']";
		return  getElementYLocation(By.xpath(sXpathServiceTitle),driver);
	}

	public static String getTextServiceName(String sServiceName){
		String sXpathServiceTitle="//android.view.ViewGroup[@content-desc='content_"+sServiceName+"']/child::android.widget.TextView[@content-desc='text_titleDesc_"+sServiceName+"']";
		return getTextUsingXpath(sXpathServiceTitle,"Service Name",driver);
	}

	public static boolean isServiceQuotaExists(String sServiceName){
		String sXpathServiceQuota="//android.view.ViewGroup[@content-desc='content_"+sServiceName+"']/child::android.widget.TextView[2]";
		return isElementExists(By.xpath(sXpathServiceQuota),driver);
	}

	public static String getTextServiceQuota(String sServiceName){
		String sXpathServiceQuota="//android.view.ViewGroup[@content-desc='content_"+sServiceName+"']/child::android.widget.TextView[2]";
		return getTextUsingXpath(sXpathServiceQuota,"Service Quota",driver);
	}

	public static boolean isServiceUsageExists(String sServiceName){
		String sXpathServiceUsage="//android.view.ViewGroup[@content-desc='content_"+sServiceName+"']/child::android.widget.TextView[3]";
		return isElementExists(By.xpath(sXpathServiceUsage),driver);
	}

	public static String getTextServiceUsage(String sServiceName){
		String sXpathServiceUsage="//android.view.ViewGroup[@content-desc='content_"+sServiceName+"']/child::android.widget.TextView[3]";
		return getTextUsingXpath(sXpathServiceUsage,"Service Usage",driver);
	}

	public static boolean isServiceValidityExists(String sServiceName){
		String sXpathServiceValidity="//android.view.ViewGroup[@content-desc='content_"+sServiceName+"']/child::android.widget.TextView[contains(@text,'Valid')]";
		return isElementExists(By.xpath(sXpathServiceValidity),driver);
	}

	public static String getTextServiceValidity(String sServiceName){
		String sXpathServiceValidity="//android.view.ViewGroup[@content-desc='content_"+sServiceName+"']/child::android.widget.TextView[contains(@text,'Valid')]";
		return getTextUsingXpath(sXpathServiceValidity,"Service Validity",driver);

	}

//android.view.ViewGroup[@content-desc='content_UMI20 Data']/child::android.widget.TextView[@content-desc='text_titleDesc_UMI20 Data']
//android.view.ViewGroup[@content-desc='content_UMI20 Data']/child::android.widget.TextView[2]
//android.view.ViewGroup[@content-desc='content_UMI20 Data']/child::android.widget.TextView[3]
//android.view.ViewGroup[@content-desc='content_UMI20 Data']/child::android.widget.TextView[4]


	public WebElement getUpgradeplandesc(){
		return AppWait.waitForElementToBeClickable(driver,Upgradeplandesc);
	}

	public WebElement getTextboxCardHolderName(){
		return AppWait.waitForElementToBeClickable(driver,textboxCardHolderName);
	}

	public WebElement getTextboxCardNo1(){
		return AppWait.waitForElementToBeClickable(driver,textboxCardNo1);
	}

	public WebElement getTextboxCardNo2(){
		return AppWait.waitForElementToBeClickable(driver,textboxCardNo2);
	}

	public WebElement getTextboxCardNo3(){
		return AppWait.waitForElementToBeClickable(driver,textboxCardNo3);
	}

	public WebElement getTextboxCardNo4(){
		return AppWait.waitForElementToBeClickable(driver,textboxCardNo4);
	}

	public AndroidElement getSubMenuTopUpForAFriend() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, subMenuTopUpForAFriend);
	}

	public AndroidElement getTextboxPhoneNoPrefix() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, textboxPhoneNoPrefix);
	}

	public AndroidElement getTextboxPhoneNo() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, textboxPhoneNo);
	}

	public AndroidElement getTextboxSenderEmailID() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, textboxSenderEmailID);
	}

	public AndroidElement getCheckboxTermsAndConditions() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, chkboxTermsAndConditions);
	}

	public AndroidElement getButtonNEXT() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonNEXT);
	}

	public AndroidElement getButtonPAYNOW() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonPAYNOW);
	}

	public boolean isElementAllServicesVisible(){
		return verifyElementExistUsingXpathString("//android.widget.TextView[@text='ALL SERVICES']","ALL SERVICES", driver);
	}

	public AndroidElement getTextSuccessMessage() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, textSuccessMessage);
	}

	public AndroidElement getTextYourRequestProcessedMessage() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, textYourRequestProcessedMessage);
	}


	public AndroidElement getlabelALLServices() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, labelALLSERVICES);
	}

	public AndroidElement getButtonAddOns() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonTextAddOns);
	}

	public AndroidElement getButtonViewUsageDetails() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonViewUsageDetails);
	}

	public AndroidElement getFirstUsageDetailsCategory() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, firstUsageDetailsCategory);
	}

	public List<WebElement> getUsageDetailsCategory() {
		return (List<WebElement>) AppWait.waitForElementToBeClickable(driver, usageDetailsCategory);
	}

	public AndroidElement getLabelAccountBalance() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, labelAccountBalance);
	}

	public String captureAccountBalance(){
		return getLabelAccountBalance().getText();
	}


	public AndroidElement getLabelPlanName() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, labelPlanName);
	}

	public AndroidElement getLabelPlanNameMob() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, labelPlanNameMobApp);
	}

	public String captureRatePlanName(){
		return getLabelPlanName().getText();
	}

	public String captureRatePlanNameMob(){
		return getLabelPlanNameMob().getText();
	}

	public AndroidElement getMenuIcon() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, MenuIcon);
	}
	
	public AndroidElement getMenuTopUpCredit() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, MenuTopUpCredit);
	}
	
	public AndroidElement getMenuCreditShare() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, MenuCreditShare);
	}
	
	public AndroidElement getLabelCreditShareBal() {
		return (AndroidElement) AppWait.waitForElementForVisibility(driver, labelCreditShareBal);
	}
	
	public AndroidElement getLabelCreditBal() {
		return (AndroidElement) AppWait.waitForElementForVisibility(driver, labelCreditBal);
	}
	
	public AndroidElement getButtonTransferCredit1() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonTransferCredit1);
	}
	
	public AndroidElement getButtonReqCredit() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonReqCredit);
	}
	
	public AndroidElement getTextReceiverMsisdn() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, textReceiverMsisdn);
	}
	
	public AndroidElement getButtonTransferCredit2() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonTransferCredit2);
	}
	
	public AndroidElement getButtonTransferConfirm() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonTransferConfirm);
	}
	
	public AndroidElement getButtonOK() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonOK);
	}
	
	public AndroidElement getButtonDenomination() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, "//android.view.ViewGroup[@content-desc=\"cell_RM"+ MainUtil.dictionary.get("TRANSFER_AMOUNT") +"\"]");
	}
	
	public AndroidElement getButtonRM1() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonRM1);
	}
	
	
	public AndroidElement getButtonRM3() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonRM3);
	}
	
	public AndroidElement getButtonRM5() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonRM5);
	}
	
	public AndroidElement getButtonRM10() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonRM10);
	}
	
	public AndroidElement getMenuBillsandPayment() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, MenuBillsandPayment);
	}
			
	public AndroidElement getMenuBillHistory() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, MenuBillHistory);
	}
	
	public AndroidElement getMenuServices() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, MenuServices);
	}
	
	public AndroidElement getMenuGoLife() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, MenuGoLife);
	}
	
	public AndroidElement getButtonGoLife5() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, ButtonGoLife5);
	}
	
	public AndroidElement getButtonGoLife10() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, ButtonGoLife10);
	}
	
	public AndroidElement getButtonSubscribe() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, ButtonSubscribe);
	}
	
	public AndroidElement getEmailaddress() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Emailaddress);
	}
	
	public AndroidElement getConfirmEmailaddress() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, ConfirmEmailaddress);
	}
	
	public AndroidElement getNextbutton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Nextbutton);
	}
	
	public AndroidElement getYesbutton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Yesbutton);
	}
	
	public AndroidElement getConfirmcheckbox() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Confirmcheckbox);
	}
	
	public AndroidElement getTermandconditioncheckbox() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Termandconditioncheckbox);
	}
	
	public AndroidElement getSuccessmessage() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Successmessage);
	}
	
	public AndroidElement getClosebutton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Closebutton);
	}
	
	public AndroidElement getViewCertificatebutton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, ViewCertificatebutton);
	}
	
	public AndroidElement getUpgradeplanbutton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Upgradeplanbutton);
	}
	
	public AndroidElement getChangePlanNowbutton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, ChangePlanNowbutton);
	}
	
	public AndroidElement getProceedbutton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Proceedbutton);
	}
	
	public AndroidElement getCurrntplanname() {
		return (AndroidElement) AppWait.waitForElementForVisibility(driver, Currntplanname);
	}
	
	public AndroidElement getcrpplanboxnextbutton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, crpplanboxnextbutton);
	}
	
	public AndroidElement getcrpplanboxPrevbutton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, crpplanboxPrevbutton);
	}
	
	public AndroidElement getmonthlyfee() {
		return (AndroidElement) AppWait.waitForElementForVisibility(driver, monthlyfee);
	}

	public void enterSixDigitPIN(){
		sendKeys(textboxEnterPin,MainUtil.dictionary.get("selfcarepin"),"Enter your 6 digit PIN","",driver);
		//clickElement(buttonVerifyPin,"PIN (Submit Button)",driver);
	}

	public AndroidElement getMobAppPinVerification() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, mobAppPinVerification);
	}

	public AndroidElement getContinueButton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, continueButton);
	}

	public AndroidElement getCreditTransferSuccessMsg() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, creditTransferSuccessmsg);
	}
}
