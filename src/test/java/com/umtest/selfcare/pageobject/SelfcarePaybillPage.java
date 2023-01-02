package com.umtest.selfcare.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;
import org.openqa.selenium.support.ui.Select;

public class SelfcarePaybillPage extends MainUtil {
	
	private RemoteWebDriver driver;

	public SelfcarePaybillPage(RemoteWebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath = "//span[contains(text(),'Bills & Payment')]")
	private WebElement Billandpayment;
	
	@FindBy(xpath = "//a[contains(text(),'Pay Bill')]")
	private WebElement Paybill;

	@FindBy(xpath = "//p[contains(text(),'Pay Bill')]")
	private WebElement QuickPaybill;

	@FindBy(xpath = "//input[@id='amount']")
	private WebElement InsertPaymentAmount;

//	@FindBy(xpath = "//img[@alt='VISA MasterCard' and contains(@class,'um-payment-method-button')]")
	@FindBy(xpath = "//img[@alt='visa-or-mastercard']")
	private WebElement PaymentMethodVisa;

	@FindBy(xpath = "//img[@alt='VISA MasterCard' and contains(@class,'um-payment-method-button')]")
	private WebElement topUpFriendPaymentMethodVisa;

//	@FindBy(xpath = "//div[@class='box-option']/label[2]")
	@FindBy(xpath = "//img[@alt='visa-or-mastercard']")
	private WebElement PaymentMethodMasterCard;

	@FindBy(xpath = "//img[@alt='VISA MasterCard' and contains(@class,'um-payment-method-button')]")
	private WebElement topUpFriendPaymentMethodMaster;
	
//	@FindBy(xpath = "//div[@class='box-option']/label[3]")
	@FindBy(xpath = "//img[@alt='amex']")
	private WebElement PaymentMethodAmericanExp;

	@FindBy(xpath = "//img[@alt='American Express']")
	private WebElement topUpFriendPaymentMethodAmericanExp;
	
//	@FindBy(xpath = "//input[@id='agreeterm']")
	@FindBy(xpath = "//input[@id='agreeterm']/following-sibling::span[@class='checkmark']")
	private WebElement billpayAgreementcheckbox;
	
//	@FindBy(xpath = "//input[@name='paynow']")
	@FindBy(xpath = "//button[@type='submit']/span")
	private WebElement Paynowbutton;
		
	@FindBy(xpath = "//input[@id='CARDNAME']")
	private WebElement Cardname;
	
	@FindBy(xpath = "//input[@type='radio' and @name='CARD_TYPE' and @value='V']")
	private WebElement CardTypeVisa;
	
	@FindBy(xpath = "//input[@type='radio' and @name='CARD_TYPE' and @value='M']")
	private WebElement CardTypeMaster;
	
	@FindBy(xpath = "//input[@id='CARD_NO1']")
	private WebElement CardNumber1;
	
	@FindBy(xpath = "//input[@id='CARD_NO2']")
	private WebElement CardNumber2;
	
	@FindBy(xpath = "//input[@id='CARD_NO3']")
	private WebElement CardNumber3;
	
	@FindBy(xpath = "//input[@id='CARD_NO4']")
	private WebElement CardNumber4;
	
	@FindBy(xpath = "//select[@id='CARD_EXP_MM']")
	private WebElement CardExpiryMonth;
	
	@FindBy(xpath = "//select[@id='CARD_EXP_YY']")
	private WebElement CardExpiryYear;
	
	@FindBy(xpath = "//input[@id='CARD_CVC']")
	private WebElement CardCVV;
	
	@FindBy(xpath = "//select[@id='CARD_ISSUER_BANK_COUNTRY_CODE']")
	private WebElement CardIssuerBankCountry;

	@FindBy(xpath = "//button[@id='btnSubmit']")
	private WebElement CardSubmitbutton;
	
//	@FindBy(xpath = "//div[@class='invoice_succ_msg']")
	@FindBy(xpath = "//h4[contains(text(),'Payment Status: Success')]")
	private WebElement Successfulmessage;
	
	@FindBy(xpath = "//body//tr[3]")
	private WebElement PaymentAmount;
	
	@FindBy(xpath = "//body//tr[4]")
	private WebElement PaymentDescription;
	
	@FindBy(xpath = "//body//tr[5]")
	private WebElement paymentNumber;

	@FindBy(xpath = "//a[contains(text(),'Top Up Now')]")
	private WebElement TopupNowbutton;
	
//	@FindBy(xpath = "//*[@id=\"topuppayment_form\"]/div[2]/div[1]/label[1]")
	@FindBy(xpath = "//span[text()='RM 10' and @class='label']")
	private WebElement topUpValue10button;
	
//	@FindBy(xpath = "//*[@id=\"topuppayment_form\"]/div[2]/div[1]/label[2]")
	@FindBy(xpath = "//span[text()='RM 30' and @class='label']")
	private WebElement topUpValue30button;
	
//	@FindBy(xpath = "//*[@id=\"topuppayment_form\"]/div[2]/div[1]/label[3]")
	@FindBy(xpath = "//span[text()='RM 50' and @class='label']")
	private WebElement topUpValue50button;
	
//	@FindBy(xpath = "//*[@id=\"topuppayment_form\"]/div[2]/div[1]/label[4]")
	@FindBy(xpath = "//span[text()='RM 100' and @class='label']")
	private WebElement topUpValue100button;
	
//	@FindBy(xpath = "//div[contains(text(),'Top Up My Account')]")
	@FindBy(xpath = "//span[contains(text(),'Top Up My Account')]")
	private WebElement buttonTopupMyAccount;
	
//	@FindBy(xpath = "//*[@id=\"main_block\"]/div/div/div[1]/div/div[2]/div[2]/ul/li[2]/a")
	@FindBy(xpath = "//a[text()='Top Up' and @class='menu-item-submenu-item']")
	private WebElement buttonTopup;
	
//	@FindBy(xpath = "//a[@class='menu topup_forfriend']")
	@FindBy(xpath = "//span[contains(text(),'Top Up For A Friend')]")
	private WebElement buttonTopupforfriend;

	@FindBy(xpath = "//input[@id='telNoFront' and @type='tel']")
	private WebElement buttonplaceholderfirst;
	
	@FindBy(xpath = "//input[@id='telNoBack' and @type='tel']")
	private WebElement buttonplaceholderlast;
	
	@FindBy(xpath = "//input[@id='emailInput' and @type='email']")
	private WebElement buttonEmailAddress;
	
	@FindBy(xpath = "//div[@id='reloadOptions']//div[1]//label[1]")
	private WebElement Reloadoption10button;
	
	@FindBy(xpath = "//div[@id='reloadOptions']//div[2]//label[1]")
	private WebElement Reloadoption30button;
	
	@FindBy(xpath = "//div[@id='reloadOptions']//div[3]//label[1]")
	private WebElement Reloadoption50button;
	
	@FindBy(xpath = "//div[@id='reloadOptions']//div[4]//label[1]")
	private WebElement Reloadoption100button;
	
	@FindBy(xpath = "//div[contains(@class,'top-up-form-row value-option')]//div[1]//label[1]")
	private WebElement ReloadTypeVisabutton;
	
	@FindBy(xpath = "//div[contains(@class,'top-up-form-row value-option')]//div[2]//label[1]")
	private WebElement ReloadTypeMasterbutton;
	
	@FindBy(xpath = "//div[contains(@class,'top-up-form-row value-option')]//div[3]//label[1]")
	private WebElement ReloadTypeAmExbutton;

	@FindBy(xpath = "//div[contains(@class,'terms-big')]")
	private WebElement Termsandconditioncheckboxbkp;

	@FindBy(xpath = "//div[contains(@class,'terms-big') and contains(text(),'I have read and agreed to the Terms and Conditions')]")
	private WebElement Termsandconditioncheckbox;

	@FindBy(xpath = "//button[contains(text(),'Next')]")
	private WebElement Nextbutton;
	
	@FindBy(xpath = "//button[@type='button' and contains(text(),'Submit')]")
	private WebElement TopupSubmitbutton;
	
	@FindBy(xpath = "//div[@class='valigner-col' and contains(text(),'Payment Successful')]")
	private WebElement txtTopUpForFriendSuccessMsg;
	
//	@FindBy(xpath = "//div[contains(text(),'MYR')]")
	@FindBy(xpath = "//h3[contains(text(),'MYR')]")
	private WebElement labelAccountBalance;
	
	public WebElement getBillandpayment() {
		return AppWait.waitForElementToBeClickable(driver, Billandpayment);
	}
	
	public WebElement getPaybill() {
		return AppWait.waitForElementToBeClickable(driver, Paybill);
	}

	public WebElement getQuickPaybill() {
		return AppWait.waitForElementToBeClickable(driver, QuickPaybill);
	}

	public WebElement getInsertPaymentAmount() {
		return AppWait.waitForElementToBeClickable(driver, InsertPaymentAmount);
	}
	
	public WebElement getPaymentMethodVisa() {
		return AppWait.waitForElementToBeClickable(driver, PaymentMethodVisa);
	}

	public WebElement getTopUpFriendPaymentMethodVisa() {
		return AppWait.waitForElementToBeClickable(driver, topUpFriendPaymentMethodVisa);
	}
	
	public WebElement getPaymentMethodMasterCard() {
		return AppWait.waitForElementToBeClickable(driver, PaymentMethodMasterCard);
	}

	public WebElement getTopUpFriendPaymentMethodMaster() {
		return AppWait.waitForElementToBeClickable(driver, topUpFriendPaymentMethodMaster);
	}
	
	public WebElement getPaymentMethodAmericanExp() {
		return AppWait.waitForElementToBeClickable(driver, PaymentMethodAmericanExp);
	}

	public WebElement getTopUpFriendPaymentMethodAmericanExp() {
		return AppWait.waitForElementToBeClickable(driver, topUpFriendPaymentMethodAmericanExp);
	}

	public void selectPaymentMethod(String sTopUpMethod){
		if (sTopUpMethod.equals("Visa")){
			clickElement(getPaymentMethodVisa(), "Payment Method 'Visa'", driver);
		} else if (sTopUpMethod.equals("Master")){
			clickElement(getPaymentMethodMasterCard(), "Payment Method 'Master'", driver);
		} else {
			clickElement(getPaymentMethodAmericanExp(), "Payment Method 'American Express'", driver);
		}
	}

	public void selectPaymentMethodForTopUpForFriend(String sTopUpMethod){
		if (sTopUpMethod.equals("Visa")){
			clickElement(getTopUpFriendPaymentMethodVisa(), "Payment Method 'Visa'", driver);
		} else if (sTopUpMethod.equals("Master")){
			clickElement(getTopUpFriendPaymentMethodMaster(), "Payment Method 'Master'", driver);
		} else {
			clickElement(getTopUpFriendPaymentMethodAmericanExp(), "Payment Method 'American Express'", driver);
		}
	}
	
	public WebElement getbillpayAgreementcheckbox() {
		return AppWait.waitForElementToBeClickable(driver, billpayAgreementcheckbox);
	}

	public void selectAgreementCheckbox(){

		clickElement(getbillpayAgreementcheckbox(), "Select 'Agreement' checkbox", driver);
	}
	
	public WebElement getPaynowbutton() {
		return AppWait.waitForElementToBeClickable(driver, Paynowbutton);
	}

	public void clickPayNowButton(){
		clickElement(getPaynowbutton(), "Click 'Pay Now' button", driver);
	}
	
	public WebElement getCardname() {
		return AppWait.waitForElementToBeClickable(driver, Cardname);
	}

	public void enterNameOnCard(String sCardname){
		sendKeys(getCardname(), sCardname, "Card Name", "", driver);
	}
	
	public WebElement getCardTypeVisa() {
		return AppWait.waitForElementToBeClickable(driver, CardTypeVisa);
	}

	public void selectCardTypeVISA(){
		clickElement(getCardTypeVisa(), "Card Type 'Visa'", driver);
	}
	
	public WebElement getCardTypeMaster() {
		return AppWait.waitForElementToBeClickable(driver, CardTypeMaster);
	}
	
	public WebElement getCardNumber1() {
		return AppWait.waitForElementToBeClickable(driver, CardNumber1);
	}
	
	public WebElement getCardNumber2() {
		return AppWait.waitForElementToBeClickable(driver, CardNumber2);
	}
	
	public WebElement getCardNumber3() {
		return AppWait.waitForElementToBeClickable(driver, CardNumber3);
	}
	
	public WebElement getCardNumber4() {
		return AppWait.waitForElementToBeClickable(driver, CardNumber4);
	}

	public void enterCardNumber(String sCardNumber){
		System.out.println(sCardNumber);
		sendKeys(getCardNumber1(), sCardNumber.substring(0, 4), "Card Number(1-4 digits)", "", driver);
		sendKeys(getCardNumber2(), sCardNumber.substring(4, 8), "Card Number(5-8 digits)", "", driver);
		sendKeys(getCardNumber3(), sCardNumber.substring(8, 12), "Card Number(9-12 digits)", "", driver);
		sendKeys(getCardNumber4(), sCardNumber.substring(12, 16), "Card Number(13-16 digits)", "", driver);
	}
	
	public WebElement getCardExpiryMonth() {
		return AppWait.waitForElementToBeClickable(driver, CardExpiryMonth);
	}

	public void selectCardExpiryMonth(String sMonth) throws InterruptedException {
		clickElement(getCardExpiryMonth(), "Card Expiry Month", driver);
		Select Expirymonth = new Select(driver.findElementByXPath("//select[@id='CARD_EXP_MM']"));
		Thread.sleep(500);
		Expirymonth.selectByVisibleText(sMonth);

	}
	
	public WebElement getCardExpiryYear() {
		return AppWait.waitForElementToBeClickable(driver, CardExpiryYear);
	}

	public void selectCardExpiryYear(String sYear) throws InterruptedException {
		Thread.sleep(200);
		clickElement(getCardExpiryYear(), "Card Expiry Year", driver);
		Select Expiryyear = new Select(driver.findElementByXPath("//select[@id='CARD_EXP_YY']"));
		Thread.sleep(500);
		Expiryyear.selectByVisibleText(sYear);
	}

	public WebElement getCardCVV() {
		return AppWait.waitForElementToBeClickable(driver, CardCVV);
	}

	public void enterCardCVV(String sCVV) throws InterruptedException {
		Thread.sleep(500);
		sendKeys(getCardCVV(), sCVV, "Card CVV", "", driver);
	}

	public WebElement getCardIssuerBankCountry() {
		return AppWait.waitForElementToBeClickable(driver, CardIssuerBankCountry);
	}

	public void selectCardIssuingCountry(String sCountry){

		String sXpathCardIssuerCountry="//select[@id='CARD_ISSUER_BANK_COUNTRY_CODE']";

		clickElement(getCardIssuerBankCountry(), "Card Issuer Bank Country", driver);
		Select oCardIssueCountry = new Select(driver.findElementByXPath(sXpathCardIssuerCountry));
		oCardIssueCountry.selectByVisibleText(sCountry);
	}
	
	public WebElement getCardSubmitbutton() {
		return AppWait.waitForElementToBeClickable(driver, CardSubmitbutton);
	}

	public void clickSubmitButton(){
		clickElement(getCardSubmitbutton(), "Submit Button", driver);
	}
	
	public WebElement getSuccessfulmessage() {
		return AppWait.waitForElementToBeClickable(driver, Successfulmessage);
	}
	
	public WebElement getPaymentAmount() {
		return AppWait.waitForElementToBeClickable(driver, PaymentAmount);
	}

	public String capturePaymentAmount(){
		return getPaymentAmount().getText();
	}
	
	public WebElement getPaymentDescription() {
		return AppWait.waitForElementToBeClickable(driver, PaymentDescription);
	}

	public String capturePaymentDescription(){
		return getPaymentDescription().getText();
	}

	public WebElement getpaymentNumber() {
		return AppWait.waitForElementToBeClickable(driver, paymentNumber);
	}

	public String capturePaymentMobileNumber(){
		return getpaymentNumber().getText();
	}



	
	public WebElement getTopupNowbutton() {
		return AppWait.waitForElementToBeClickable(driver, TopupNowbutton);
	}
	
	public WebElement gettopUpValue10button() {
		return AppWait.waitForElementToBeClickable(driver, topUpValue10button);
	}
	
	public WebElement gettopUpValue30button() {
		return AppWait.waitForElementToBeClickable(driver, topUpValue30button);
	}
	
	public WebElement gettopUpValue50button() {
		return AppWait.waitForElementToBeClickable(driver, topUpValue50button);
	}
	
	public WebElement gettopUpValue100button() {
		return AppWait.waitForElementToBeClickable(driver, topUpValue100button);
	}

	public void selectTopUpValue(String sAmount){

		if (sAmount.equals("10")){
			clickElement(gettopUpValue10button(), "Click Top Up '10' Button", driver);
		} else if (sAmount.equals("30")){
			clickElement(gettopUpValue30button(), "Click Top Up '30' Button", driver);
		} else if (sAmount.equals("50")){
			clickElement(gettopUpValue50button(), "Click Top Up '50' Button", driver);
		} else {
			clickElement(gettopUpValue100button(), "Click Top Up '100' Button", driver);
		}
	}
	
	public WebElement getbuttonTopupMyAccount() {
		return AppWait.waitForElementToBeClickable(driver, buttonTopupMyAccount);
	}

	public void clickTopUpMyAccount(){

		clickElement(getbuttonTopupMyAccount(), "Top Up My Account", driver);
	}
	
	public WebElement getbuttonTopup() {
		return AppWait.waitForElementToBeClickable(driver, buttonTopup);
	}

	public void clickTopUp(){
		clickElement(getbuttonTopup(), "Top Up", driver);
	}
	
	public WebElement getbuttonTopupforfriend() {
		return AppWait.waitForElementToBeClickable(driver, buttonTopupforfriend);
	}

	public void clickTopUpForAFriendButton(){
		clickElement(getbuttonTopupforfriend(), "'Topup For A Friend' button", driver);
	}

	
	public WebElement getbuttonplaceholderfirst() {
		return AppWait.waitForElementToBeClickable(driver, buttonplaceholderfirst);
	}

	public void enterTopUpMSISDN(String sMSISDN){
		sendKeys(getbuttonplaceholderfirst(), (sMSISDN.substring(1, 4)), "U Mobile Number (01x)", "", driver);
		sendKeys(getbuttonplaceholderlast(), (sMSISDN.substring(4)), "U Mobile Number (xxx xxxx)", "", driver);
	}


	
	public WebElement getbuttonplaceholderlast() {
		return AppWait.waitForElementToBeClickable(driver, buttonplaceholderlast);
	}
	
	public WebElement getbuttonEmailAddress() {
		return AppWait.waitForElementToBeClickable(driver, buttonEmailAddress);
	}

	public void enterEmailAddress(String sEMAIL){
		sendKeys(getbuttonEmailAddress(), (sEMAIL), "Email Address", "", driver);
	}
	
	public WebElement getReloadoption10button() {
		return AppWait.waitForElementToBeClickable(driver, Reloadoption10button);
	}
	
	public WebElement getReloadoption30button() {
		return AppWait.waitForElementToBeClickable(driver, Reloadoption30button);
	}
	
	public WebElement getReloadoption50button() {
		return AppWait.waitForElementToBeClickable(driver, Reloadoption50button);
	}
	
	public WebElement getReloadoption100button() {
		return AppWait.waitForElementToBeClickable(driver, Reloadoption100button);
	}
	
	public WebElement getReloadTypeVisabutton() {
		return AppWait.waitForElementToBeClickable(driver, ReloadTypeVisabutton);
	}
	
	public WebElement getReloadTypeMasterbutton() {
		return AppWait.waitForElementToBeClickable(driver, ReloadTypeMasterbutton);
	}
	
	public WebElement getReloadTypeAmExbutton() {
		return AppWait.waitForElementToBeClickable(driver, ReloadTypeAmExbutton);
	}
	
	public WebElement getTermsandconditioncheckbox() {
		return AppWait.waitForElementToBeClickable(driver, Termsandconditioncheckbox);
	}

	public void selectTermsAndConditionsCheckbox(){
		clickElement(getTermsandconditioncheckbox(), "select 'Terms and Conditions' checkbox", driver);
	}
	
	public WebElement getNextbutton() {
		return AppWait.waitForElementToBeClickable(driver, Nextbutton);
	}

	public void clickNextButton() throws InterruptedException {
		clickElement(getNextbutton(), "'Next' button", driver);
		Thread.sleep(2000);
		/*if(isElementDisplayed(getNextbutton())){
			clickElement(getNextbutton(), "'Next' button", driver);
		}*/
	}
	
	public WebElement getTopupSubmitbutton() {
		return AppWait.waitForElementToBeClickable(driver, TopupSubmitbutton);
	}

	public void clickTopUpSubmitButton() throws InterruptedException {
		clickElement(getTopupSubmitbutton(), "'Submit' button", driver);
		Thread.sleep(2000);
		/*if(isElementDisplayed(getTopupSubmitbutton())){
			clickElement(getTopupSubmitbutton(), "'Submit' button", driver);
		}*/
	}

	public WebElement getTopupforfriendsuccessmsg() {
		return AppWait.waitForElementToBeClickable(driver, txtTopUpForFriendSuccessMsg);
	}

	public String captureTopUpForFriendSuccessMessage(){
		return AppWait.waitForElementToBeClickable(driver, txtTopUpForFriendSuccessMsg).getText();
	}
	
	public WebElement getLabelAccountBalance() {
		return AppWait.waitForElementToBeClickable(driver, labelAccountBalance);
	}

	@FindBy(xpath = "//span[@class='um-top-up-amount-button__toggle-main' and @for='value1000']")
	private WebElement btnTopUpAmount10;

	@FindBy(xpath = "//span[@class='um-top-up-amount-button__toggle-main' and @for='value3000']")
	private WebElement btnTopUpAmount30;

	@FindBy(xpath = "//span[@class='um-top-up-amount-button__toggle-main' and @for='value5000']")
	private WebElement btnTopUpAmount50;

	@FindBy(xpath = "//span[@class='um-top-up-amount-button__toggle-main' and @for='value10000']")
	private WebElement btnTopUpAmount100;

	public WebElement getTopUpAmount10() {
		return btnTopUpAmount10;
	}

	public WebElement getTopUpAmount30() {
		return btnTopUpAmount30;
	}

	public WebElement getTopUpAmount50() {
		return btnTopUpAmount50;
	}

	public WebElement getTopUpAmount100() {
		return btnTopUpAmount100;
	}

	public void selectTopUpAmount(String sAmount){

		if (sAmount.equals("10")){
			clickElement(getTopUpAmount10(), "Click Top Up '10' Button", driver);
		} else if (sAmount.equals("30")){
			clickElement(getTopUpAmount30(), "Click Top Up '30' Button", driver);
		} else if (sAmount.equals("50")){
			clickElement(getTopUpAmount50(), "Click Top Up '50' Button", driver);
		} else {
			clickElement(getTopUpAmount100(), "Click Top Up '100' Button", driver);
		}
	}

	public void waitForMessageToDisappear(String sMessage){
		AppWait.waitForElementToDisappear(driver, By.xpath("//*[contains(text(),'"+sMessage+"')]"),30);
	}

}
