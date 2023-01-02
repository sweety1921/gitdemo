package com.umtest.selfcare.pagefunction;

import com.umtest.testframe.base.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import com.umtest.selfcare.pageobject.SelfcareLoginLogoutPage;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SelfcareVerificationFuncs extends SelfcareLoginLogoutPage {

	private static Logger logger = LogManager.getLogger(SelfcareLoginLogoutFuncs.class);
	private RemoteWebDriver driver;
		
	public SelfcareVerificationFuncs(RemoteWebDriver driver) {
		super(driver);
		this.driver =  driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);
	}


    public void verifySelfcare_New(String sMSISDN, String accountType) {
        try {
        	
        	SelfcareLoginLogoutFuncs loginFuncs = new SelfcareLoginLogoutFuncs(driver);
        	loginFuncs.loginSelfcare(MainUtil.dictionary.get("MSISDN"));
        	
        	if (accountType.toUpperCase().contains("PREPAID")) {
        		loginFuncs.verifySelfcareBalanceAndPlanName();
			}
        	
        	if (accountType.toUpperCase().contains("POSTPAID")) {
        		ApplicationUtil.getPlanDetails_Postpaid(MainUtil.dictionary.get("PLAN_NAME"));
        		loginFuncs.verifySelfcarePostpaidplandetail();
			}
        	
        	loginFuncs.logoutSelfcare();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while verifying Selfcare  :" + e);
        }finally {
			DriverFactory.quitSelfcare(driver);
		}
    }

	public void verifySelfcare(String accountType) {
		try {

			SelfcareLoginLogoutFuncs loginFuncs = new SelfcareLoginLogoutFuncs(driver);
			loginFuncs.loginSelfcare(MainUtil.dictionary.get("MSISDN"));

			if (accountType.toUpperCase().contains("PREPAID")) {
				loginFuncs.verifySelfcareBalanceAndPlanName();
			}

			if (accountType.toUpperCase().contains("POSTPAID")) {
				ApplicationUtil.getPlanDetails_Postpaid(MainUtil.dictionary.get("PLAN_NAME"));
				loginFuncs.verifySelfcarePostpaidplandetail();
			}

			loginFuncs.logoutSelfcare();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying Selfcare  :" + e);
		}finally {
			DriverFactory.quitSelfcare(driver);
		}
	}

	public void verifySelfcarePrepaidBalancesAndExpiryDate() {
		try {

			SelfcareLoginLogoutFuncs loginFuncs = new SelfcareLoginLogoutFuncs(driver);
			loginFuncs.loginSelfcare(MainUtil.dictionary.get("MSISDN"));

			loginFuncs.verifySelfcareAccountBalanceCreditShareBalanceAndPlanName();

			loginFuncs.logoutSelfcare();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying Selfcare  :" + e);
		}
	}



    public void verifyPostpaidBillpayment() {
        try {
        	
        	SelfcareLoginLogoutFuncs loginFuncs = new SelfcareLoginLogoutFuncs(driver);
        	loginFuncs.loginSelfcare(MainUtil.dictionary.get("MSISDN"));
        	
        	        	
        	clickElement(getBillandpayment(), "Bill & Payment Button", driver);
        	clickElement(getPaymentHistory(), "Payment History Button", driver);
            WebElement paymentdetail= driver.findElementByXPath("//tbody/tr[1]");
            
        	//body//tr[2]
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime todaysDate =  LocalDateTime.now();
            String subscribtionDate = dtf.format(todaysDate);
            System.out.println(subscribtionDate);
			System.out.println(paymentdetail.getText());
			compareInString(paymentdetail.getText(), "MYR "+MainUtil.dictionary.get("Amount")+".00", "Payment Amount", driver);
            compareInString(paymentdetail.getText(), subscribtionDate, "Payment Date", driver);

          //div[@class='table_plan table_padding selfcare_table']/tbody/tr	
                    	
        	loginFuncs.logoutSelfcare();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while verifying Selfcare  :" + e);
        }
    }
    
    public void verifySelfcareProfile() {
        try {
        	
        	SelfcareLoginLogoutFuncs loginFuncs = new SelfcareLoginLogoutFuncs(driver);
        	loginFuncs.loginSelfcare(MainUtil.dictionary.get("MSISDN"));
        	
        	clickElement(getButtonManageAccount(), "Manage Account Button", driver);
        	clickElement(getButtonPersonalDetails(), "Personal Details Button", driver);
        	clickElement(getButtonEDITPersonalDetails(), "Edit Personal Details Button", driver);

			compareInString(gettextEmailAddress().getAttribute("value"), MainUtil.dictionary.get("EMAILID"), "Email Address", driver);
        	compareInString(gettextAddress1().getAttribute("value"), "Address1", "Address Line1", driver);

//        	compareInString(gettextAddress2().getAttribute("value"), "Address2", "Address Line2", driver);
        	compareInString(gettextZipcode().getAttribute("value"), "51000", "ZipCode", driver);
        	compareInString(gettextprehomenumber().getAttribute("value"), "60", "home Phone number", driver);
        	compareInString(gettexthomenumber().getAttribute("value"), "182148887", "home Phone number", driver);
//        	compareInString(gettextprefaxnumber().getAttribute("value"), "60", "FAX Phone number", driver);
//        	compareInString(gettextFaxnumber().getAttribute("value"), "182149999", "FAX Phone number", driver);

			/*compareInString(gettextAddress1().getText(), "Address1", "Address Line1", driver);
			compareInString(gettextAddress2().getText(), "Address2", "Address Line2", driver);
			compareInString(gettextZipcode().getText(), "51000", "ZipCode", driver);
			compareInString(gettextprehomenumber().getText(), "018", "home Phone number", driver);
			compareInString(gettexthomenumber().getText(), "2148888", "home Phone number", driver);
			compareInString(gettextprefaxnumber().getText(), "018", "FAX Phone number", driver);
			compareInString(gettextFaxnumber().getText(), "2149999", "FAX Phone number", driver);*/

        	loginFuncs.logoutSelfcare();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while verifying Selfcare  :" + e);
        }
    }

	public void verifySelfcareIDDIRStatus() {
		try {

			SelfcareLoginLogoutFuncs loginFuncs = new SelfcareLoginLogoutFuncs(driver);
			loginFuncs.loginSelfcare(MainUtil.dictionary.get("msisdn"));

			clickElement(getButtonManageAccount(), "Manage Account Button", driver);
			clickElement(getbuttonRoamingandIDDService(), "Roaming and IDD Service Button", driver);
			clickElement(getbuttonCheckStatus(), "Check Status Button", driver);
			String IDDIRstatusmsg = "Your roaming and IDD services have already been activated. Enjoy roaming & the best IDD rates in town!";

			compareInString(getConfirmstatusmessage().getText(), IDDIRstatusmsg, "IDD IR Status check", driver);

			loginFuncs.logoutSelfcare();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying Selfcare  :" + e);
		}
	}

	public void verifySelfcareProfile_Selfcare() {
		try {

			SelfcareLoginLogoutFuncs loginFuncs = new SelfcareLoginLogoutFuncs(driver);
			loginFuncs.loginSelfcare(MainUtil.dictionary.get("msisdn"));

			clickElement(getButtonManageAccount(), "Manage Account Button", driver);
			clickElement(getButtonPersonalDetails(), "Personal Details Button", driver);
			clickElement(getButtonEDITPersonalDetails(), "Edit Personal Details Button", driver);

			compareInString(gettextAddress1().getAttribute("value").toUpperCase(), MainUtil.dictionary.get("Address1").toUpperCase(), "Address Line1", driver);
//			compareInString(gettextAddress2().getAttribute("value").toUpperCase(), MainUtil.dictionary.get("Address2").toUpperCase(), "Address Line2", driver);
			compareInString(gettextZipcode().getAttribute("value"), MainUtil.dictionary.get("Zip"), "ZipCode", driver);
			compareInString(gettextEmailAddress().getAttribute("value"), MainUtil.dictionary.get("EMAILID"), "Verify Email",driver);
			compareInString(gettextprehomenumber().getAttribute("value"), MainUtil.dictionary.get("Homenumber").substring(0,4), "home Phone number", driver);
		compareInString(gettexthomenumber().getAttribute("value"), MainUtil.dictionary.get("Homenumber").substring(4), "home Phone number", driver);

			loginFuncs.logoutSelfcare();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying Selfcare  :" + e);
		}
	}
}
