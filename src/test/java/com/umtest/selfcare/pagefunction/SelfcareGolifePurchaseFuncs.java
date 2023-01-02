package com.umtest.selfcare.pagefunction;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.umtest.selfcare.pageobject.SelfcareGolifePurchasePage;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SelfcareGolifePurchaseFuncs extends SelfcareGolifePurchasePage {

	private static Logger logger = LogManager.getLogger(SelfcareGolifePurchaseFuncs.class);
	private RemoteWebDriver driver;
		
	public SelfcareGolifePurchaseFuncs(RemoteWebDriver driver) {
		super(driver);
		this.driver =  driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);
	}

	public boolean PurchaseGolife(String GOLIFE) {

		boolean methodReturn = false;

		try {
			
			clickElement(getbuttonGoLIFE(), "GOLIFE Button", driver);
        	clickElement(getbuttonlifeinsurance(), "Life Insurance Button", driver);
        	System.out.println(GOLIFE);
        	
        	String Premiumamount;
        	String Insuranceplanname;
        	if (GOLIFE.equals("GOLIFE5")) {
        	Premiumamount = "RM5.00/month";
        	Insuranceplanname = "GOLIFE 5";
        	clickElement(getGOLIFE5(), "Select GOLIFE5", driver);
        	
        	} else {
        	Premiumamount = "RM10.00/month";
        	Insuranceplanname = "GOLIFE 10";
        	clickElement(getGOLIFE10(), "Select GOLIFE10", driver);
        	}
			Thread.sleep(5000);
        	clickElement(getbuttonNext(), "Click Next Button", driver);
        	//String email = driver.findElementByXPath("//input[@name='glemail1']").getAttribute("value");
        	//String email = getText(getemailaddress());

			String email = driver.findElementByXPath("//input[@id='glemail1']").getAttribute("value");
			System.out.println(email);
			if (email.length()<1) {
				sendKeys(driver.findElementByXPath("//input[@name='glemail1']"), "AUTOTEST@UM.COM", "Email Address", "", driver);
				sendKeys(getconfirmemailaddress(), "AUTOTEST@UM.COM", "MSISDN", "", driver);
			}else{
				sendKeys(getconfirmemailaddress(), email, "MSISDN", "", driver);
			}

        	//sendKeys(getconfirmemailaddress(), email, "MSISDN", "", driver);
        	
        	clickElement(getbuttonYes(), "Click Yes Radio Button", driver);
        	
        	clickElement(getAggrementcheckbox(), "Click Aggrement checkbox", driver);
        	
        	clickElement(getbuttonNext(), "Click Next Button", driver);

			clickElement(getAggrementcheckbox(), "Click Aggrement checkbox", driver);
        	//clickElement(getconfirmcheckbox(), "Click Confirm Checkbox", driver);
        	
        	clickElement(getFinalSubmitbutton(), "Click Submit Button", driver);
        	
        	System.out.println(GOLIFE);
        
        	
        	//clickElement(getbuttonGoLIFE(), "GOLIFE Button", driver);
        	clickElement(getbuttonMyCertificate(), "Life Insurance Button", driver);
        	        	
        	
        	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            LocalDateTime todaysDate =  LocalDateTime.now();
            String subscribtionDate = dtf.format(todaysDate);
            System.out.println(subscribtionDate);
            LocalDateTime currentmonth = todaysDate.plusMonths(1);
           
            String Nextchargingdate = dtf.format(currentmonth);
            System.out.println(Nextchargingdate);
            
            compareInString(getDateofSubscription().getText(), subscribtionDate, "Date of subscription", driver);
            compareInString(getDateofNextCharging().getText(), Nextchargingdate, "Date of Next Charging", driver);
            compareInString(getInsurancePlanname().getText(), Insuranceplanname, "Insurance Plan name", driver);
            compareInString(getInsuranceStatus().getText(), "Active", "Insurance Status", driver);
            compareInString(getDateofCertRenewal().getText(), Nextchargingdate, "Date of Cert renewal", driver);
            compareInString(getPremiumAmount().getText(), Premiumamount, "Premium Amount", driver);
            methodReturn = true;
        	        	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing GOLIFE Purchase :" + e);
		}
		return methodReturn;
	}
	
	
	public boolean PurchaseGolife_Prepaid(String GOLIFE,String Amount,String PaymentType,String Cardnumber) {

		boolean methodReturn = false;

		try {
			
			clickElement(getbuttonGoLIFE(), "GOLIFE Button", driver);
        	clickElement(getbuttonlifeinsurance(), "Life Insurance Button", driver);

        	System.out.println(GOLIFE);
        	System.out.println(Amount);
        	System.out.println(PaymentType);
        	
        	String Premiumamount;
        	String Insuranceplanname;

        	if (GOLIFE.equals("GOLIFE5")) {
        		Premiumamount = "RM5.00/month";
        		Insuranceplanname = "GOLIFE 5";
        		clickElement(getGOLIFE5pripaid(), "Select GOLIFE5", driver);
        	
        	} else {
        		Premiumamount = "RM10.00/month";
        		Insuranceplanname = "GOLIFE 10";
        		clickElement(getGOLIFE10pripaid(), "Select GOLIFE10", driver);
        	}

			if (verifyElementExistUsingXpathString("//span[contains(text(),'Top Up Now')]", "Topupbutton", driver)) {

				clickElement(getTopupNowbutton(), "Click Topup Now Button", driver);

				String email = driver.findElementByXPath("//input[@id='EmailAddress']").getAttribute("value");
				System.out.println(email);
				if (email.length()<1) {
					sendKeys(driver.findElementByXPath("//input[@id='EmailAddress']"), "AUTOTEST@UM.COM", "Email Address", "", driver);
					//sendKeys(getconfirmemailaddress(), "AUTOTEST@UM.COM", "MSISDN", "", driver);
				}

				//sendKeys(driver.findElementByXPath("//input[@id='field-customer-email']"), "AUTOTEST@UM.COM", "MSISDN", "", driver);

				if (Amount.equals("10")){
					clickElement(gettopUpValue10button(), "Click Topup Value Button", driver);
				} else if (Amount.equals("30")){
					clickElement(gettopUpValue30button(), "Click Topup Value Button", driver);
				} else if (Amount.equals("50")){
					clickElement(gettopUpValue50button(), "Click Topup Value Button", driver);
				} else {
					clickElement(gettopUpValue100button(), "Click Topup Value Button", driver);
				}


				if (PaymentType.equals("Visa")){
					clickElement(getPaymentMethodVisa(), "Payment Method Visa", driver);
				} else if (Amount.equals("Master")){
					clickElement(getPaymentMethodMasterCard(), "Payment Method Master", driver);
				} else {
					clickElement(getPaymentMethodAmericanExp(), "Payment Method AmericanExp", driver);
				}

				clickElement(getAggrementcheckbox(), "Click Aggrement checkbox", driver);

				clickElement(getPaynowbutton(), "Click Paynow button", driver);


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
				Expiryyear.selectByVisibleText("2023");

				sendKeys(getCardCVV(), "123", "Card CVV", "", driver);

				clickElement(getCardIssuerBankCountry(), "Card Issuer Bank Country", driver);
				Select Cardissuecountry = new Select(driver.findElementByXPath("//select[@id='CARD_ISSUER_BANK_COUNTRY_CODE']"));
				Cardissuecountry.selectByVisibleText("Malaysia");


				clickElement(getCardSubmitbutton(), "Card Submit Button", driver);

				compareInString(getSuccessfulmessage().getText(), "Payment Status: Success", "Payment done sucessfully", driver);
				compareInString(getPaymentAmount().getText(), "RM "+Amount+".00", "Payment Amount", driver);
				compareInString(getPaymentDescription().getText(), "U Mobile Prepaid Top Up", "Payment Description", driver);
				compareInString(getpaymentNumber().getText(),MainUtil.dictionary.get("msisdn"), "Payment number", driver);

				clickElement(getbuttonContinue(), "Click Continue Button", driver);
			} else {
				clickElement(getbuttonNext(), "Click Next Button", driver);
			}

			String email = driver.findElementByXPath("//input[@id='glemail1']").getAttribute("value");
			System.out.println(email);
			if (email.length()<1) {
				sendKeys(driver.findElementByXPath("//input[@name='glemail1']"), "AUTOTEST@UM.COM", "Email Address", "", driver);
				sendKeys(getconfirmemailaddress(), "AUTOTEST@UM.COM", "MSISDN", "", driver);
			}else{
				sendKeys(getconfirmemailaddress(), email, "MSISDN", "", driver);
			}

			//sendKeys(getconfirmemailaddress(), email, "MSISDN", "", driver);

			clickElement(getbuttonYes(), "Click Yes Radio Button", driver);

			clickElement(getAggrementcheckbox(), "Click Aggrement checkbox", driver);

			clickElement(getbuttonNext(), "Click Next Button", driver);

			clickElement(getAggrementcheckbox(), "Click Aggrement checkbox", driver);
			//clickElement(getconfirmcheckbox(), "Click Confirm Checkbox", driver);

			clickElement(getFinalSubmitbutton(), "Click Submit Button", driver);

			System.out.println(GOLIFE);


			//clickElement(getbuttonGoLIFE(), "GOLIFE Button", driver);
			clickElement(getbuttonMyCertificate(), "Life Insurance Button", driver);


			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
			LocalDateTime todaysDate =  LocalDateTime.now();
			String subscribtionDate = dtf.format(todaysDate);
			System.out.println(subscribtionDate);
			LocalDateTime currentday = todaysDate.plusDays(30);
			LocalDateTime currentmonth = todaysDate.plusMonths(1);

			String Nextchargingdate = dtf.format(currentday);
			String DateofCertRenewal = dtf.format(currentmonth);
			System.out.println(Nextchargingdate);

			compareInString(getDateofSubscription().getText(), subscribtionDate, "Date of subscription", driver);
			compareInString(getDateofNextCharging().getText(), Nextchargingdate, "Date of Next Charging", driver);
			compareInString(getInsurancePlanname().getText(), Insuranceplanname, "Insurance Plan name", driver);
			compareInString(getInsuranceStatus().getText(), "Active", "Insurance Status", driver);
			compareInString(getDateofCertRenewal().getText(), DateofCertRenewal, "Date of Cert renewal", driver);
			compareInString(getPremiumAmount().getText(), Premiumamount, "Premium Amount", driver);
			methodReturn = true;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing GOLIFE Purchase :" + e);
		}
		return methodReturn;
	}

	public boolean PurchaseGolife_Prepaid_BKP(String GOLIFE,String Amount,String PaymentType) {

		boolean methodReturn = false;

		try {

			clickElement(getbuttonGoLIFE(), "GOLIFE Button", driver);
			clickElement(getbuttonlifeinsurance(), "Life Insurance Button", driver);
			System.out.println(GOLIFE);
			System.out.println(Amount);
			System.out.println(PaymentType);

			String Premiumamount;
			String Insuranceplanname;
			if (GOLIFE.equals("GOLIFE5")) {
				Premiumamount = "RM5.00/month";
				Insuranceplanname = "GOLIFE 5";
				clickElement(getGOLIFE5(), "Select GOLIFE5", driver);

			} else {
				Premiumamount = "RM10.00/month";
				Insuranceplanname = "GOLIFE 10";
				clickElement(getGOLIFE10(), "Select GOLIFE10", driver);
			}

			/*if (verifyElementIsDisplayed(getTopupNowbutton(), "Topupbutton",driver)) {


				clickElement(getTopupNowbutton(), "Click Topup Now Button", driver);

				if (Amount.equals("10")){
					clickElement(gettopUpValue10button(), "Click Topup Value Button", driver);
				} else if (Amount.equals("30")){
					clickElement(gettopUpValue30button(), "Click Topup Value Button", driver);
				} else if (Amount.equals("50")){
					clickElement(gettopUpValue50button(), "Click Topup Value Button", driver);
				} else {
					clickElement(gettopUpValue100button(), "Click Topup Value Button", driver);
				}


				if (PaymentType.equals("Visa")){
					clickElement(getPaymentMethodVisa(), "Payment Method Visa", driver);
				} else if (Amount.equals("Master")){
					clickElement(getPaymentMethodMasterCard(), "Payment Method Master", driver);
				} else {
					clickElement(getPaymentMethodAmericanExp(), "Payment Method AmericanExp", driver);
				}

				clickElement(getbillpayAgreementcheckbox(), "Click Aggrement checkbox", driver);

				clickElement(getPaynowbutton(), "Click Paynow button", driver);


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

				compareInString(getSuccessfulmessage().getText(), "Payment Status: Successful", "Payment done sucessfully", driver);
				compareInString(getPaymentAmount().getText(), "Payment Amount : RM "+Amount+".00", "Payment Amount", driver);
				compareInString(getPaymentDescription().getText(), "Payment Description : U Mobile Prepaid Top Up", "Payment Description", driver);
				compareInString(getpaymentNumber().getText(),"Mobile Number : "+MainUtil.dictionary.get("MSISDN"), "Payment number", driver);

				clickElement(getbuttonContinue(), "Click Continue Button", driver);
			} else {
				clickElement(getbuttonNext(), "Click Next Button", driver);
			}*/

			clickElement(getbuttonNext(), "Click Next Button", driver);

			String email = driver.findElementByXPath("//input[@name='glemail1']").getAttribute("value");
			//String email = getText(getemailaddress());
			System.out.println(email);

			sendKeys(getconfirmemailaddress(), email, "MSISDN", "", driver);

			clickElement(getbuttonYes(), "Click Yes Radio Button", driver);

			clickElement(getAggrementcheckbox(), "Click Aggrement checkbox", driver);

			clickElement(getbuttonNextSubmit(), "Click Next Button", driver);

			clickElement(getconfirmcheckbox(), "Click Confirm Checkbox", driver);

			clickElement(getFinalSubmitbutton(), "Click Submit Button", driver);

			System.out.println(GOLIFE);


			//clickElement(getbuttonGoLIFE(), "GOLIFE Button", driver);
			clickElement(getbuttonMyCertificate(), "Life Insurance Button", driver);


			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
			LocalDateTime todaysDate =  LocalDateTime.now();
			String subscribtionDate = dtf.format(todaysDate);
			System.out.println(subscribtionDate);
			LocalDateTime currentday = todaysDate.plusDays(30);
			LocalDateTime currentmonth = todaysDate.plusMonths(1);

			String Nextchargingdate = dtf.format(currentday);
			String DateofCertRenewal = dtf.format(currentmonth);
			System.out.println(Nextchargingdate);

			compareInString(getDateofSubscription().getText(), subscribtionDate, "Date of subscription", driver);
			compareInString(getDateofNextCharging().getText(), Nextchargingdate, "Date of Next Charging", driver);
			compareInString(getInsurancePlanname().getText(), Insuranceplanname, "Insurance Plan name", driver);
			compareInString(getInsuranceStatus().getText(), "Active", "Insurance Status", driver);
			compareInString(getDateofCertRenewal().getText(), DateofCertRenewal, "Date of Cert renewal", driver);
			compareInString(getPremiumAmount().getText(), Premiumamount, "Premium Amount", driver);
			methodReturn = true;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing GOLIFE Purchase :" + e);
		}
		return methodReturn;
	}
	
	
	public void verifySelfcareGolife(String Golife) {
        try {
        	
        	SelfcareLoginLogoutFuncs loginFuncs = new SelfcareLoginLogoutFuncs(driver);
        	loginFuncs.loginSelfcare(MainUtil.dictionary.get("MSISDN"));
        	
        	
        	clickElement(getbuttonGoLIFE(), "GOLIFE Button", driver);
        	clickElement(getbuttonMyCertificate(), "My Certificate Button", driver);
        	System.out.println(Golife);
        	        	
        	String Premiumamount;
        	String Insuranceplanname;
        	if (Golife.equals("GOLIFE5")) {
        	Premiumamount = "RM5.00/month";
        	Insuranceplanname = "GOLIFE 5";
        	//clickElement(getGOLIFE5(), "Select GOLIFE5", driver);
        	
        	} else {
        	Premiumamount = "RM10.00/month";
        	Insuranceplanname = "GOLIFE 10";
        	//clickElement(getGOLIFE10(), "Select GOLIFE10", driver);
        	}
        	
        	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            LocalDateTime todaysDate =  LocalDateTime.now();
            String subscribtionDate = dtf.format(todaysDate);
            System.out.println(subscribtionDate);
            LocalDateTime currentday = todaysDate.plusDays(30);
            LocalDateTime currentmonth = todaysDate.plusMonths(1);
           
            String Nextchargingdate = dtf.format(currentmonth);
            String DateofCertRenewal = dtf.format(currentmonth);
            System.out.println(Nextchargingdate);
            
            compareInString(getDateofSubscription().getText(), subscribtionDate, "Date of subscription", driver);
            compareInString(getDateofNextCharging().getText(), Nextchargingdate, "Date of Next Charging", driver);
            compareInString(getInsurancePlanname().getText(), Insuranceplanname, "Insurance Plan name", driver);
            compareInString(getInsuranceStatus().getText(), "Active", "Insurance Status", driver);
            compareInString(getDateofCertRenewal().getText(), DateofCertRenewal, "Date of Cert renewal", driver);
            compareInString(getPremiumAmount().getText(), Premiumamount, "Premium Amount", driver);
            
        	
        	loginFuncs.logoutSelfcare();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while verifying Selfcare  :" + e);
        }
    }
	
}
