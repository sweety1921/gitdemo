package com.umtest.mobileapps.pagefunction;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.umtest.mobileapps.pageobject.MobileAppPaybillPage;
import com.umtest.testframe.lib.MainUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class MobileAppPaybillFuncs extends MobileAppPaybillPage {
	private static Logger logger = LogManager.getLogger(MobileAppPaybillFuncs.class);
	private AndroidDriver driver;

	public MobileAppPaybillFuncs(RemoteWebDriver driver) {
		super((AndroidDriver) driver);
		this.driver = (AndroidDriver) driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);

	}
	
	public boolean doPaybill(String CardType) {

		boolean returnStatus = false;

		try {

			clickElement(getMenuIcon(), "Click Menu Icon", driver);
			clickElement(getMenuBillsandPayment(), "Click on Bills & Payment menu", driver);
			
			clickElement(getPaybill(), "Click on Pay Bill menu", driver);
			
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			
			sendKeys(getInsertPaymentAmount(),MainUtil.dictionary.get("AMOUNT") , "Enter Amount","", driver);
			clickElement(getPayNowButton(), "Click on Pay Now Button", driver);
						
			if (CardType.equals("Visa")){
        		clickElement(getVisaMasterCard(), "Payment Method Visa", driver);
        	} else if (CardType.equals("Master")){
        		clickElement(getVisaMasterCard(), "Payment Method Master", driver);
        	} else if (CardType.equals("AmericanExpress")){
        		clickElement(getAmericanExpressCard(), "Payment Method AmericanExp", driver);
        	} else {
        		clickElement(getOnlineBanking(), "Payment Method OnlineBanking", driver);
        	}
			
			clickElement(getProceedButton(), "Click on Proceed Button", driver);
			
			scrollUDLR(driver, 0, "U");
			
			sendKeys(getCARDNAME(),"Test" , "Enter Name","", driver);
			
			if (CardType.equals("Visa")){
        		clickElement(getCardTypeVisa(), "Payment Method Visa", driver);
        	} else {
        		clickElement(getCardTypeMaster(), "Payment Method Master", driver);
        	}
			
			sendKeys(getCardNumber1(), MainUtil.dictionary.get("Cardnumber").substring(0, 4), "Card Name", "", driver);
        	sendKeys(getCardNumber2(), MainUtil.dictionary.get("Cardnumber").substring(4, 8), "Card Name", "", driver);
        	sendKeys(getCardNumber3(), MainUtil.dictionary.get("Cardnumber").substring(8, 12), "Card Name", "", driver);
        	sendKeys(getCardNumber4(), MainUtil.dictionary.get("Cardnumber").substring(12, 16), "Card Name", "", driver);
			
        	clickElement(getCardExpiryMonth(), "Card Expiry Months", driver);
        	scrollUDLR(driver, 0, "U");
        	clickElement(selectEvenType("November"), "Months", driver);
        	
        	clickElement(getCardExpiryYear(), "Card Expiry Years", driver);
        	clickElement(selectEvenType("2021"), "Years", driver);
        	
        	sendKeys(getCardCVV(), "123", "CVV Number", "", driver);
        	
        	clickElement(getCardIssuerBankCountry(), "Card Issuer Country", driver);
        	clickElement(selectEvenType("Malaysia"), "Country", driver);
        	
        	clickElement(getCardSubmitbutton(), "Card Submit Button", driver);
        	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Paybill" + e);
		}
		return returnStatus;
	}
}
