package com.umtest.selfcare.pagefunction;

import java.time.Duration;
import java.util.ArrayList;

import com.umtest.testframe.lib.PropertyHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.umtest.selfcare.pageobject.SelfcarePaybillPage;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SelfcarePaybillFuncs extends SelfcarePaybillPage {

	private static Logger logger = LogManager.getLogger(SelfcarePaybillFuncs.class);
	private RemoteWebDriver driver;
		
	public SelfcarePaybillFuncs(RemoteWebDriver driver) {
		super(driver);
		this.driver =  driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);
	}
	
	public boolean VeerifyPostpaidPayBillPage(String sExpectedURL) {

		boolean methodReturn = false;

		try {
			clickElement(getBillandpayment(), "Bill & Payment Button", driver);
        	clickElement(getPaybill(), "Pay Bill Button", driver);
        	
        	//sendKeys(getInsertPaymentAmount(), Amount, "Payment Amount", "", driver);
			
        	clickElement(getPaymentMethodVisa(), "Payment Method Visa", driver);
        	
        	clickElement(getbillpayAgreementcheckbox(), "Bill Payment Agreement", driver);
        	
        	clickElement(getPaynowbutton(), "PayNow Button", driver);

        	Thread.sleep(5000);

        	String sCurrentURL=driver.getCurrentUrl();

			compareInString(sCurrentURL, sExpectedURL, "Payment URL Page", driver);

			MainUtil.verifyElementDisplayed(getCardNumber1(),"Card Number text field",driver);
        	MainUtil.verifyElementDisplayed(getCardSubmitbutton(),"Submit Button",driver);

        	methodReturn = true;
        	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing PayBill :" + e);
		}
		return methodReturn;
	}

	public boolean PostpaidPayBill(String Amount,String Cardnumber) {

		boolean methodReturn = false;

		try {
			//clickElement(getBillandpayment(), "Bill & Payment Button", driver);
			//clickElement(getPaybill(), "Pay Bill Button", driver);

			clickElement(getQuickPaybill(), "Pay Bill Button", driver);

			sendKeys(driver.findElementByXPath("//input[@id='EmailAddress']"), "AUTOTEST@UM.COM", "Email Address", "", driver);
			sendKeys(getInsertPaymentAmount(), Amount, "Payment Amount", "", driver);

			clickElement(getPaymentMethodVisa(), "Payment Method Visa", driver);

			clickElement(getbillpayAgreementcheckbox(), "Bill Payment Agreement", driver);

			clickElement(getPaynowbutton(), "PayNow Button", driver);

			sendKeys(getCardname(), "Test", "Card Name", "", driver);

			clickElement(getCardTypeVisa(), "CartType Visa", driver);

			sendKeys(getCardNumber1(), Cardnumber.substring(0, 4), "Card Name", "", driver);
			sendKeys(getCardNumber2(), Cardnumber.substring(4, 8), "Card Name", "", driver);
			sendKeys(getCardNumber3(), Cardnumber.substring(8, 12), "Card Name", "", driver);
			sendKeys(getCardNumber4(), Cardnumber.substring(12, 16), "Card Name", "", driver);


			clickElement(getCardExpiryMonth(), "Card Expiry Month", driver);
			Select Expirymonth = new Select(driver.findElementByXPath("//select[@id='CARD_EXP_MM']"));
			Expirymonth.selectByVisibleText("November");

			clickElement(getCardExpiryYear(), "Card Expiry Year", driver);
			Select Expiryyear = new Select(driver.findElementByXPath("//select[@id='CARD_EXP_YY']"));
			Expiryyear.selectByVisibleText("2025");

			sendKeys(getCardCVV(), "123", "Card CVV", "", driver);

			clickElement(getCardIssuerBankCountry(), "Card Issuer Bank Country", driver);
			Select Cardissuecountry = new Select(driver.findElementByXPath("//select[@id='CARD_ISSUER_BANK_COUNTRY_CODE']"));
			Cardissuecountry.selectByVisibleText("Malaysia");


			clickElement(getCardSubmitbutton(), "Card Submit Button", driver);


			compareInString(getSuccessfulmessage().getText(), "Payment Status: Success", "Payment done sucessfully", driver);
			compareInString(getPaymentAmount().getText(), "Payment Amount RM "+Amount+".00", "Payment Amount", driver);
			compareInString(getPaymentDescription().getText(), "Payment Description U Mobile Bill Payment", "Payment Description", driver);
			compareInString(getpaymentNumber().getText(),"Mobile Number "+MainUtil.dictionary.get("MSISDN"), "Payment number", driver);
			methodReturn = true;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing PayBill :" + e);
		}
		return methodReturn;
	}
	
	public boolean doTopup(String sPaymentMethodType, String sAmount,String sCardNumber) {

		boolean returnStatus = false;

		try {

			storeSelfcareAppBalace();
			clickTopUpMyAccount();
			clickTopUp();
			selectTopUpValue(sAmount);
			selectPaymentMethod(sPaymentMethodType);
			pressKeyPageDown(driver);
			selectAgreementCheckbox();
			clickPayNowButton();
			enterNameOnCard("Test");
			selectCardTypeVISA();
			enterCardNumber(sCardNumber);
			selectCardExpiryMonth("November");
			selectCardExpiryYear("2023");
			enterCardCVV("123");
			selectCardIssuingCountry("Malaysia");
			clickSubmitButton();

//        	boolean flagTopUpSuccess=compareInString(getSuccessfulmessage().getText(), "Payment Status: Successful", "Payment done sucessfully", driver);
//        	compareInString(getPaymentAmount().getText(), "Payment Amount : RM "+sAmount+".00", "Payment Amount", driver);
//        	compareInString(getPaymentDescription().getText(), "Payment Description : U Mobile Topup Payment", "Payment Description", driver);
//        	compareInString(getpaymentNumber().getText(),"Mobile Number : "+MainUtil.dictionary.get("MSISDN"), "Payment number", driver);
			boolean flagTopUpSuccess=compareInString(getSuccessfulmessage().getText(), "Payment Status: Success", "Payment done sucessfully", driver);
			compareInString(getPaymentAmount().getText(), "Payment Amount RM "+sAmount+".00", "Payment Amount", driver);
			compareInString(getPaymentDescription().getText(), "Payment Description U Mobile Prepaid Top Up", "Payment Description", driver);
			compareInString(getpaymentNumber().getText(),"Mobile Number "+MainUtil.dictionary.get("MSISDN"), "Payment number", driver);

        	if(flagTopUpSuccess) {
				ApplicationUtil.updateAccountInfoAfterTopup(dictionary.get("MSISDN"));
				returnStatus = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing topup" + e);
		}
		return returnStatus;
	}
	
	
	public boolean doTopupForFriend(String sTopUpType, String sAmount) {

		boolean returnStatus = false;

		try {

			clickTopUpForAFriendButton();

			switchToTab(1, driver);

			enterTopUpMSISDN(MainUtil.dictionary.get("topupmsisdn"));
			pressKeyPageDown(driver);
			enterEmailAddress("test@u.com.my");
			selectTopUpAmount(sAmount);
			//selectPaymentMethod(sTopUpType);
			selectPaymentMethodForTopUpForFriend(sTopUpType);
			selectTermsAndConditionsCheckbox();
			pressKeyPageDown(driver);
			clickNextButton();
			waitForMessageToDisappear("Processing please wait...");

			clickTopUpSubmitButton();
//			waitForMessageToDisappear("Please wait while we process your order...");

			enterNameOnCard("Test");
			pressKeyPageDown(driver);
			selectCardTypeVISA();

			enterCardNumber(PropertyHelper.getProperties("VISACARD_NUMBER"));
			selectCardExpiryMonth(PropertyHelper.getProperties("EXPIRY_MONTH"));
			selectCardExpiryYear(PropertyHelper.getProperties("EXPIRY_YEAR"));
			enterCardCVV(PropertyHelper.getProperties("CARD_CVV"));
			selectCardIssuingCountry("Malaysia");
			clickSubmitButton();

			waitForMessageToDisappear("Processing Response From Bank");

			boolean flagTopUpSuccess=compareInString(captureTopUpForFriendSuccessMessage(), "Payment Successful", "Payment done sucessfully", driver);
        	compareInString(capturePaymentAmount(), "RM "+sAmount+".00", "Payment Amount:", driver);
        	compareInString(capturePaymentDescription(), "U Mobile Prepaid Top Up", "Payment Description:", driver);
        	compareInString(capturePaymentMobileNumber(),MainUtil.dictionary.get("topupmsisdn"), "Mobile Number:", driver);

			captureAppScreenshot("Screen Capture: Top Up Success",driver);

			closeBrowserTab(1,driver);

			if(flagTopUpSuccess) {

				dictionary.put("ACTIVE_END_DATE",dictionary.get("CURRENT_ACTIVE_END_DATE"));

				ApplicationUtil.updateAccountInfoAfterTopup(dictionary.get("topupmsisdn"));
				returnStatus = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing topup for a friend" + e);
		}
		return returnStatus;
	}

	public boolean doTopupForFriend_bkp(String topupType, String Amount,String Cardnumber) {

		boolean returnStatus = false;

		try {

			//storeSelfcareAppBalace();

			clickElement(getbuttonTopupforfriend(), "Topup For Friend Button", driver);

			ArrayList tabs;
			tabs = new ArrayList(driver.getWindowHandles());
			logger.info(tabs.size());
			driver.switchTo().window((String) tabs.get(1));

			Actions action = new Actions(driver);
			action.sendKeys(Keys.PAGE_DOWN).build().perform();

			sendKeys(getbuttonplaceholderfirst(), (MainUtil.dictionary.get("topupmsisdn").substring(1, 4)), "topup msisdn", "", driver);

			sendKeys(getbuttonplaceholderlast(), (MainUtil.dictionary.get("topupmsisdn").substring(4)), "topup msisdn", "", driver);

			sendKeys(getbuttonEmailAddress(), (MainUtil.dictionary.get("Email")), "Email Address", "", driver);

			if (Amount.equals("10")){
				clickElement(getReloadoption10button(), "Click Topup Value Button", driver);
			} else if (Amount.equals("30")){
				clickElement(getReloadoption30button(), "Click Topup Value Button", driver);
			} else if (Amount.equals("50")){
				clickElement(getReloadoption50button(), "Click Topup Value Button", driver);
			} else {
				clickElement(getReloadoption100button(), "Click Topup Value Button", driver);
			}


			if (topupType.equals("Visa")){
				clickElement(getReloadTypeVisabutton(), "Payment Method Visa", driver);
			} else if (Amount.equals("Master")){
				clickElement(getReloadTypeMasterbutton(), "Payment Method Master", driver);
			} else {
				clickElement(getReloadTypeAmExbutton(), "Payment Method AmericanExp", driver);
			}

			clickElement(getTermsandconditioncheckbox(), "Click Aggrement checkbox", driver);

			clickElement(getNextbutton(), "Click Next button", driver);

			clickElement(getTopupSubmitbutton(), "Click Submit button", driver);

			sendKeys(getCardname(), "Test", "Card Name", "", driver);

			clickElement(getCardTypeVisa(), "CartType Visa", driver);
			System.out.println(Cardnumber);
			sendKeys(getCardNumber1(), Cardnumber.substring(0, 4), "Card Name", "", driver);
			sendKeys(getCardNumber2(), Cardnumber.substring(4, 8), "Card Name", "", driver);
			sendKeys(getCardNumber3(), Cardnumber.substring(8, 12), "Card Name", "", driver);
			sendKeys(getCardNumber4(), Cardnumber.substring(12, 16), "Card Name", "", driver);


			clickElement(getCardExpiryMonth(), "Card Expiry Month", driver);
			Select Expirymonth = new Select(driver.findElementByXPath("//select[@id='CARD_EXP_MM']"));
			Expirymonth.selectByVisibleText("November");

			clickElement(getCardExpiryYear(), "Card Expiry Year", driver);
			Select Expiryyear = new Select(driver.findElementByXPath("//select[@id='CARD_EXP_YY']"));
			Expiryyear.selectByVisibleText("2020");

			sendKeys(getCardCVV(), "123", "Card CVV", "", driver);

			clickElement(getCardIssuerBankCountry(), "Card Issuer Bank Country", driver);
			Select Cardissuecountry = new Select(driver.findElementByXPath("//select[@id='CARD_ISSUER_BANK_COUNTRY_CODE']"));
			Cardissuecountry.selectByVisibleText("Malaysia");


			clickElement(getCardSubmitbutton(), "Card Submit Button", driver);


			compareInString(getTopupforfriendsuccessmsg().getText(), "Payment Successful", "Payment done sucessfully", driver);
			compareInString(getPaymentAmount().getText(), "Payment Amount : RM "+Amount+".00", "Payment Amount", driver);
			compareInString(getPaymentDescription().getText(), "Payment Description : U Mobile Prepaid Top Up", "Payment Description", driver);
			compareInString(getpaymentNumber().getText(),"Mobile Number : "+MainUtil.dictionary.get("topupmsisdn"), "Payment number", driver);


			ApplicationUtil.updateAccountInfoAfterTopup(dictionary.get("topupmsisdn"));
			returnStatus = true;




		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing topup" + e);
		}
		return returnStatus;
	}
	
	public void storeSelfcareAppBalace() {

		try {
			String currentBalance = getLabelAccountBalance().getText();
			MainUtil.dictionary.put("CURRENT_MAIN_BALANCE",currentBalance.substring(3, currentBalance.length()));
			logger.info(MainUtil.dictionary.get("CURRENT_MAIN_BALANCE"));

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while storing mail balance  :" + e);
		}
	}
	
	public void storeSelfcareAppFriendBalace() {

		try {
			String currentBalance = getLabelAccountBalance().getText();
			MainUtil.dictionary.put("CURRENT_MAIN_BALANCE",currentBalance.substring(3, currentBalance.length()));
			logger.info(MainUtil.dictionary.get("CURRENT_MAIN_BALANCE"));

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while storing mail balance  :" + e);
		}
	}

}
