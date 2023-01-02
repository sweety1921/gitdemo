package com.umtest.umrex.pageobject;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class UMREXChangeOfferPage extends MainUtil {

	private AndroidDriver<?> driver;

	public UMREXChangeOfferPage(AndroidDriver<?> driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);
	}

	private UMREXScanIDReadIDCustomerInfoPage scanID;

    //Step1: Customer Identification
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Scan ID']")
	private AndroidElement buttonScanID;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Read ID']")
	private AndroidElement buttonReadID;
/*
	Step2: Customer Identification --> Scan ID --> Customer Information
	Step3: Customer Identification --> Scan ID --> Customer Information --> Continue (Confirmation)
*/
	//Step4: Subscription Information --> Select MSISDN
	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.fl.pra:id/tvSubsInfo' and @text='Select MSISDN']")
	private AndroidElement buttonSelectMSISDN;

	//Step5: Subscription Information (Dialog) --> 601128204268 (Prepaid)
	private String varXpathMSISDNSubscriptionInfo="//android.widget.TextView[@resource-id='com.fl.pra:id/tvItem' and @text='xxxxMSISDNxxxx (Prepaid)']";

	//Step6: Change Rate Plan Type --> Please Select (link)
	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.fl.pra:id/tvCRPType' and @text='Please Select']")
	private AndroidElement buttonPleaseSelectChangeRatePlan;

	//Step7: Change Rate Plan Type (Dialog)
	@AndroidFindBy(xpath = "//*[@resource-id='com.fl.pra:id/tvTitle' and @text='Change Rate Plan Type']")
	private AndroidElement titleTextChangeRatePlanType;

	//Step8: Change Prepaid Rate Plan(Option)
	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.fl.pra:id/tvName' and @text='Change Prepaid Rate Plan']")
	private AndroidElement linktextChangePrepaidRatePlan;

	//Step9: Click Continue
	@AndroidFindBy(xpath = "//android.widget.Button[@resource-id='com.fl.pra:id/btnContinue' and @text='CONTINUE']")
	private AndroidElement buttonContinue;

	@AndroidFindBy(xpath = "//android.widget.Button[@resource-id='btncontinue' and @text='CONTINUE']")
	private AndroidElement buttonContinueMakePayment;

//	Step10: Select Rate Plan Page --> New Rate Plan --> Select > (link)
    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.fl.pra:id/tvNewPlan' and @text='Select']")
    private AndroidElement linkSelectNewRatePlan;

	//Step11: Select Rate Plan (Dialog) --> i40 Prepaid
	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.fl.pra:id/tvTitle' and @text='Select Rate Plan']")
	private AndroidElement dialogSelectRatePlan;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.fl.pra:id/tvDisplayName' and @text='Power']")
	private AndroidElement linkNewRatePlanPower;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.fl.pra:id/tvDisplayName' and @text='Unlimited Power']")
	private AndroidElement linkNewRatePlanUnlimitedPower;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.fl.pra:id/tvDisplayName' and @text='Unlimited Funz']")
	private AndroidElement linkNewRatePlanUnlimitedFunz;

	@AndroidFindBy(xpath = "//android.widget.Button[@text='YES']")
	private AndroidElement buttonYES;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Your change rate plan request is received.']")
	private AndroidElement labelChangeRatePlanRequestReceived;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Thank you for your order']")
	private AndroidElement textThankYouYourOrderIsReceived;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.fl.pra:id/tvDisplayName' and @text='5G Prepaid Plan']")
	private AndroidElement linkNewRatePlan5GPrepaidPlan;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.fl.pra:id/tvTitle' and @text='Order Confirmation']")
	private AndroidElement titleTextOrderConfirmation;

	@AndroidFindBy(xpath = "//android.widget.CheckBox[@resource-id='com.fl.pra:id/cbTncDeclaration' and @text='I declare and confirm as follows:']")
	private AndroidElement chkboxDeclaration;

//	Step24: Order Confirmation--> Email --> Please Select
	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='android:id/text1' and @text='Please Select']")
	private AndroidElement linkPleaseSelectEmail;

//	Step25: Order Confirmation--> Email --> Please Select --> AUTO202006041616@U.COM.MY
	String varXpathSelectEmail = "//android.widget.CheckedTextView[@resource-id='android:id/text1' and @text='xxxxEMAILIDxxxx']";

//	Step26: Order Confirmation--> Customer Signature --> Tap here to signin
    @AndroidFindBy(xpath = "//android.widget.LinearLayout[@resource-id='com.fl.pra:id/llSignWholeLayout']")
	private AndroidElement linkTapHereToSign;

//	Step27: Sign (Dialog)--> Text Area

//	Step28: Sign (Dialog)--> SIGN Button
    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.fl.pra:id/save_sign_btn' and @text='SIGN']")
	private AndroidElement buttonSign;

/*	Step29: Click Continue [Order Confirmation]*/

/*	Step30: Click Continue [Order Confirmation]*/
	@AndroidFindBy(xpath = "//android.widget.Button[@resource-id='btnaddpayment' and @text='+' and @index='1']")
	private AndroidElement buttonAddPaymentPlusSign;

//Step30: Select Cash
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Cash']")
	private AndroidElement textCashPaymentMethod;

	@AndroidFindBy(xpath = "//android.view.View[@content-desc='Cash']")
	private AndroidElement textCashPaymentMethod1;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Credit Card' and @index='0']")
	private AndroidElement textCreditCardPaymentMethod;






	public void clickScanID(){
		clickElement(buttonScanID,"Scan ID",driver);
	}

	public void clickReadID(){
		clickElement(buttonReadID,"Read ID",driver);
	}

	public void clickSelectMSISDN(){
		clickElement(buttonSelectMSISDN,"Select MSISDN",driver);
	}

	public void selectMSISDN(String sMSISDN){
		clickElementUsingXpathString(varXpathMSISDNSubscriptionInfo.replace("xxxxMSISDNxxxx",sMSISDN),sMSISDN+" (Prepaid)",driver);
	}

	public void clickPleaseSelectChangeRatePlanType(){
		clickElement(buttonPleaseSelectChangeRatePlan,"Please Select (Change Rate Plan Type)",driver);
	}

	public boolean isDialogChangeRatePlanDisplayed(){
		return isElementDisplayed(titleTextChangeRatePlanType);
	}

	public void clickChangePrepaidRatePlan() {
		clickElement(linktextChangePrepaidRatePlan, "Change Prepaid Rate Plan", driver);
	}

	public void clickContinue_SubscriptionInfo() {
		clickElement(buttonContinue, "CONTINUE (Subscription Information)", driver);
	}

	public void clickSelectNewRatePlan() {
		clickElement(linkSelectNewRatePlan, "Select > (New Rate Plan)", driver);
	}

	public boolean isDialogSelectRatePlanDisplayed(){
		return isElementDisplayed(dialogSelectRatePlan);
	}

	public void clickNewRatePlan(String sNewRatePlan) {
		if(sNewRatePlan.equalsIgnoreCase("Power")){
			clickElement(linkNewRatePlanPower, "Power (New Rate Plan)", driver);
		}
		if(sNewRatePlan.equalsIgnoreCase("Unlimited Power")){
			clickElement(linkNewRatePlanUnlimitedPower, "Unlimited Power (New Rate Plan)", driver);
		}

		if(sNewRatePlan.equalsIgnoreCase("Unlimited Funz")) {
			clickElement(linkNewRatePlanUnlimitedFunz, "Unlimited Funz (New Rate Plan)", driver);
		}
		if(sNewRatePlan.equalsIgnoreCase("5G Prepaid Plan")) {
			clickElement(linkNewRatePlan5GPrepaidPlan, "5G Prepaid Plan", driver);
		}
	}

	public void clickContinue_SelectRatePlan() {
		clickElement(buttonContinue, "CONTINUE (Select Rate Plan)", driver);
	}

	public void clickContinue_MakePayment() {
		clickElement(buttonContinueMakePayment, "CONTINUE (Make Payment)", driver);
	}

	public void clickContinue() {
		clickElement(buttonContinue, "CONTINUE", driver);
	}

	public void clickYES() {
		clickElement(buttonYES, "YES", driver);
	}

	public AndroidElement getLabelThankYouYourOrderReceivedMessage() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, textThankYouYourOrderIsReceived);
	}

	public AndroidElement getLabelChangeRatePlanRequestReceivedMessage() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, labelChangeRatePlanRequestReceived);
	}

	public void clickContinue_SelectBundle() {
		clickElement(buttonContinue, "CONTINUE (Select Bundle)", driver);
	}

	public void clickContinue_SubscriptionSummary() {
		clickElement(buttonContinue, "CONTINUE (Subscription Summary)", driver);
	}

	public void clickContinue_SupportingDocuments() {
		clickElement(buttonContinue, "CONTINUE (Supporting Documents)", driver);
	}

	public void clickContinue_OrderSummary() {
		clickElement(buttonContinue, "CONTINUE (Order Summary)", driver);
	}

	public void clickContinue_PaymentSummary() {
		clickElement(buttonContinue, "CONTINUE (Payment Summary)", driver);
	}

	public void clickDeclarationCheckbox() {
		clickElement(chkboxDeclaration, "Declaration Checkbox", driver);
	}

	public void clickPleaseSelectEmail() {
		clickElement(linkPleaseSelectEmail,"Please Select (Email)",driver);
	}

	public void selectEmailID(){

		String anc=varXpathSelectEmail.replace("xxxxEMAILIDxxxx",MainUtil.dictionary.get("email"));
		clickElementUsingXpathString(varXpathSelectEmail.replace("xxxxEMAILIDxxxx",MainUtil.dictionary.get("email")),MainUtil.dictionary.get("email"),driver);
	}

	public void clickTapHereToSign() {
		clickElement(linkTapHereToSign,"Tap here to sign",driver);
	}

	public void clickSignButton() {
		clickElement(buttonSign,"Sign Button",driver);
	}

	public void clickContinue_OrderConfirmation() {
		clickElement(buttonContinue, "CONTINUE (Order Confirmation)", driver);
	}

	public void clickAddPaymentPlusSign() {
		clickElement(buttonAddPaymentPlusSign, "+ (Add Payment - Make Payment Page)", driver);
	}

	public void selectCashPaymentMethod() {
		clickElement(textCashPaymentMethod1, "Cash (Payment Method)", driver);
	}

	public void selectCreditCardPaymentMethod() {
		clickElement(textCreditCardPaymentMethod, "Credit Card (Payment Method)", driver);
	}

}
