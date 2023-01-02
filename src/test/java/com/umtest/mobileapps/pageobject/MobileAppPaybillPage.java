package com.umtest.mobileapps.pageobject;

import org.openqa.selenium.WebElement;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MobileAppPaybillPage extends MainUtil {

	private AndroidDriver<?> driver;

	public MobileAppPaybillPage(AndroidDriver<?> driver) {
		this.driver = driver;
	}

	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"sidemenu_button_menu\"]/android.widget.ImageView")
	private WebElement MenuIcon;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Bills and Payment']")
	private WebElement MenuBillsandPayment;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Pay Bill']")
	private WebElement Paybill;

	/*@AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='textfield_totalAmountToPay']")
	private WebElement InsertPaymentAmount;*/

	@AndroidFindBy(xpath = "//android.widget.EditText[@text='0.00']")
	private WebElement InsertPaymentAmount;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Pay Now']")
	private WebElement PayNowButton;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Visa / Mastercard']")
	private WebElement VisaMasterCard;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='American Express']")
	private WebElement AmericanExpressCard;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Online Banking']")
	private WebElement OnlineBanking;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Proceed']")
	private WebElement ProceedButton;
	
	@AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='CARDNAME']")
	private WebElement CARDNAME;
	
	@AndroidFindBy(xpath = "//td[1]//table[1]//tbody[1]//tr[1]//td[1]//input[1]")
	private WebElement CardTypeVisa;
	
	@AndroidFindBy(xpath = "//td[2]//table[1]//tbody[1]//tr[1]//td[1]//input[1]")
	private WebElement CardTypeMaster;
	
	@AndroidFindBy(xpath = "//input[@id='CARD_NO1']")
	private WebElement CardNumber1;
	
	@AndroidFindBy(xpath = "//input[@id='CARD_NO2']")
	private WebElement CardNumber2;
	
	@AndroidFindBy(xpath = "//input[@id='CARD_NO3']")
	private WebElement CardNumber3;
	
	@AndroidFindBy(xpath = "//input[@id='CARD_NO4']")
	private WebElement CardNumber4;
	
	@AndroidFindBy(xpath = "//select[@id='CARD_EXP_MM']")
	private WebElement CardExpiryMonth;
	
	@AndroidFindBy(xpath = "//select[@id='CARD_EXP_YY']")
	private WebElement CardExpiryYear;
	
	@AndroidFindBy(xpath = "//input[@id='CARD_CVC']")
	private WebElement CardCVV;
	
	@AndroidFindBy(xpath = "//select[@id='CARD_ISSUER_BANK_COUNTRY_CODE']")
	private WebElement CardIssuerBankCountry;

	@AndroidFindBy(xpath = "//button[@id='btnSubmit']")
	private WebElement CardSubmitbutton;
	
	public AndroidElement getMenuBillsandPayment() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, MenuBillsandPayment);
	}
	
	public AndroidElement getMenuIcon() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, MenuIcon);
	}
	
	public AndroidElement getPaybill() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Paybill);
	}
	
	public AndroidElement getInsertPaymentAmount() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, InsertPaymentAmount);
	}
	
	public AndroidElement getPayNowButton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, PayNowButton);
	}
	
	public AndroidElement getVisaMasterCard() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, VisaMasterCard);
	}
	
	public AndroidElement getAmericanExpressCard() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, AmericanExpressCard);
	}
	
	public AndroidElement getOnlineBanking() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, OnlineBanking);
	}
	
	public AndroidElement getProceedButton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, ProceedButton);
	}
	
	public AndroidElement getCARDNAME() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, CARDNAME);
	}
	
	public AndroidElement getCardTypeVisa() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, CardTypeVisa);
	}
	
	public AndroidElement getCardTypeMaster() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, CardTypeMaster);
	}
	
	public AndroidElement getCardNumber1() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, CardNumber1);
	}
	
	public AndroidElement getCardNumber2() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, CardNumber2);
	}
	
	public AndroidElement getCardNumber3() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, CardNumber3);
	}
	
	public AndroidElement getCardNumber4() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, CardNumber4);
	}
	
	public AndroidElement getCardExpiryMonth() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, CardExpiryMonth);
	}
	
	public AndroidElement getCardExpiryYear() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, CardExpiryYear);
	}
	
	public AndroidElement getCardCVV() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, CardCVV);
	}
	
	public AndroidElement getCardIssuerBankCountry() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, CardIssuerBankCountry);
	}
	
	public AndroidElement getCardSubmitbutton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, CardSubmitbutton);
	}
	
	public AndroidElement selectEvenType(String eventType) {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, "//android.widget.CheckedTextView[@resource-id='android:id/text1'][@text='"+eventType+"']");
	}
}
