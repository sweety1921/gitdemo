package com.umtest.selfcare.testcases;

import com.umtest.mobileapps.pagefunction.MobileAppLoginFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppUtilFuncs;
import com.umtest.selfcare.pagefunction.SelfcareLoginLogoutFuncs;
import com.umtest.selfcare.pagefunction.SelfcarePaybillFuncs;
import com.umtest.selfcare.pagefunction.SelfcareVerificationFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.APIRequestLibrary;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.lib.PropertyHelper;
import com.umtest.testframe.listener.ExtentTestNGITestListener;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class SelfcarePrepaidTopUp extends DriverFactory {
	private RemoteWebDriver driver;
	private RemoteWebDriver driverApp;
	private RemoteWebDriver driverChrome;
	private RemoteWebDriver driverChromeUMB;

	SelfcareLoginLogoutFuncs selfcareLoginFuncs;
	SelfcarePaybillFuncs SelfcareFuncs;
	SelfcareVerificationFuncs selfcareVerifyFuncs;
	MobileAppLoginFuncs mobileAppLoginFuncs;
	MobileAppUtilFuncs mobileAppUtilFuncs;
	UMBVerificationFuncs umbVerifyFuncs;
	APIRequestLibrary selfcareAPI;
	
	ExtentTestNGITestListener ex;

	private static Logger logger = LogManager.getLogger(SelfcarePrepaidTopUp.class);

	@BeforeClass
	public void initialiseObj() throws IOException {
		MainUtil.APPLICATION_NAME = "SELFCARE";
	}

	
	@Test(description = "Selfcare App Topup")
	@Parameters({"planName","topupPaymentMethodType","topupAmount","Cardnumber","msisdn","identificationType"})
	public void SelfcareAppTopup(String planName, String topupPaymentMethodType, String topupAmount, String Cardnumber, String msisdn, String identificationType) {
		
		try {

			/*######################################################################################################################################################*/
			driverApp = getDriver("MOBILEAPP", "Android");
			driverChrome = getDriver("chrome");
			driverChromeUMB = getDriver("UMB");

			selfcareLoginFuncs = new SelfcareLoginLogoutFuncs(driverChrome);
			SelfcareFuncs = new SelfcarePaybillFuncs(driverChrome);
			selfcareVerifyFuncs = new SelfcareVerificationFuncs(driverChrome);
			umbVerifyFuncs=new UMBVerificationFuncs(driverChromeUMB);
			selfcareAPI=new APIRequestLibrary();
			/*######################################################################################################################################################*/

			ExtentTestNGITestListener.createNode("Do Selfcare App Prepaid Top");

			MainUtil.dictionary.put("PLAN_NAME", planName);
			MainUtil.dictionary.put("TOPUP_PAYMENT_METHOD_TYPE", topupPaymentMethodType);
			MainUtil.dictionary.put("TOPUP_AMOUNT", topupAmount);
			MainUtil.dictionary.put("Card_Number", Cardnumber);
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("IDENTIFICATION_TYPE",identificationType);
			MainUtil.dictionary.put("topupmsisdn", msisdn);

			selfcareAPI.syncSubscriberBalanceAndExpiryDate(MainUtil.dictionary.get("MSISDN"));

			ApplicationUtil.getPrepaidAccountByMSISDN(msisdn);

			selfcareLoginFuncs.loginSelfcare(MainUtil.dictionary.get("MSISDN"));
			boolean status = SelfcareFuncs.doTopup(MainUtil.dictionary.get("TOPUP_PAYMENT_METHOD_TYPE"), MainUtil.dictionary.get("TOPUP_AMOUNT"),MainUtil.dictionary.get("Card_Number"));
			//boolean status=true;

			if (status) {
				
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

				if (PropertyHelper.getProperties("SELFCARE_CHANNEL_VERIFICATION_FLAG").equals("true")) {
					ExtentTestNGITestListener.createNode("Selfcare Verification");
					selfcareVerifyFuncs.verifySelfcare("PREPAID");
				}

				if (PropertyHelper.getProperties("MOBILEAPP_CHANNEL_VERIFICATION_FLAG").equals("true")) {
					ExtentTestNGITestListener.createNode("Mobile App Verification");
					mobileAppLoginFuncs = new MobileAppLoginFuncs(driverApp);
					mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("topupmsisdn"));

					mobileAppUtilFuncs = new MobileAppUtilFuncs(driverApp);
					mobileAppUtilFuncs.verifyMobileAppPrepaidTopUp();

				}

				if (PropertyHelper.getProperties("UMB_CHANNEL_VERIFICATION_FLAG").equals("true")) {
					ExtentTestNGITestListener.createNode("UMB Verification");
					umbVerifyFuncs.umbVerification("PREPAID", "");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid Topup  :" + e);
		}
	}
	
	@Test(description = "Selfcare App Topup")
	@Parameters({"planName","topupType","topupAmount","msisdn","topupmsisdn","identificationType"})
	public void SelfcareAppTopupForFriend(String planName, String topupType, String topupAmount, String msisdn, String topupmsisdn, String identificationType) {
		
		try {
			/*######################################################################################################################################################*/
			driverApp = getDriver("MOBILEAPP", "Android");
			driverChrome = getDriver("chrome");
			driverChromeUMB = getDriver("UMB");

			selfcareLoginFuncs = new SelfcareLoginLogoutFuncs(driverChrome);
			SelfcareFuncs = new SelfcarePaybillFuncs(driverChrome);
			selfcareVerifyFuncs = new SelfcareVerificationFuncs(driverChrome);
			umbVerifyFuncs = new UMBVerificationFuncs(driverChromeUMB);

			selfcareAPI=new APIRequestLibrary();
			/*######################################################################################################################################################*/

			ExtentTestNGITestListener.createNode("Pre-Test: Data SetUp and Management");
			
			MainUtil.dictionary.put("PLAN_NAME", planName);
			MainUtil.dictionary.put("TOPUP_TYPE", topupType);
			MainUtil.dictionary.put("TOPUP_AMOUNT", topupAmount);
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("topupmsisdn", topupmsisdn);
			MainUtil.dictionary.put("IDENTIFICATION_TYPE",identificationType);

			selfcareAPI.syncSubscriberBalanceAndExpiryDate(MainUtil.dictionary.get("MSISDN"));
			selfcareAPI.syncSubscriberBalanceAndExpiryDate(MainUtil.dictionary.get("topupmsisdn"));

			selfcareAPI.getSubscriberBalanceAndExpiryDate(MainUtil.dictionary.get("topupmsisdn"));

			String sMainBalanceTopUpMSISDN=MainUtil.dictionary.get("API_BALANCE");
			String sCurrentActiveEndDateTopUpMSISDN=MainUtil.dictionary.get("API_ACCT_EXPIRY_DATE");
			String internetExpiryDate = MainUtil.getCurrentDatePlusDays(5);

			MainUtil.dictionary.put("CURRENT_MAIN_BALANCE",sMainBalanceTopUpMSISDN);
			MainUtil.dictionary.put("CURRENT_ACTIVE_END_DATE",sCurrentActiveEndDateTopUpMSISDN);
			MainUtil.dictionary.put("INTERNET_EXPIRY_DATE",internetExpiryDate);

			ExtentTestNGITestListener.createNode("Perform Selfcare App Prepaid Top");

			ApplicationUtil.getPrepaidAccountByMSISDN(msisdn);

			selfcareLoginFuncs.loginSelfcare(MainUtil.dictionary.get("MSISDN"));
			boolean status = SelfcareFuncs.doTopupForFriend(MainUtil.dictionary.get("TOPUP_TYPE"), MainUtil.dictionary.get("TOPUP_AMOUNT"));
			selfcareLoginFuncs.logoutSelfcare();
//			boolean status = true;

			if (status) {
				
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("topupmsisdn"));

				if (PropertyHelper.getProperties("SELFCARE_CHANNEL_VERIFICATION_FLAG").equals("true")) {
					ExtentTestNGITestListener.createNode("Selfcare Verification");
					selfcareVerifyFuncs.verifySelfcare("PREPAID");
				}

				if (PropertyHelper.getProperties("MOBILEAPP_CHANNEL_VERIFICATION_FLAG").equals("true")) {
					ExtentTestNGITestListener.createNode("Mobile App Verification");

					mobileAppLoginFuncs = new MobileAppLoginFuncs(driverApp);
					mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("topupmsisdn"));

					mobileAppUtilFuncs = new MobileAppUtilFuncs(driverApp);
					mobileAppUtilFuncs.verifyMobileAppPrepaidTopUp();
					/*mobileAppUtilFuncs.verifyMobileAppPrepaidTopUp(MainUtil.dictionary.get("topupmsisdn"), driverApp);*/
				}

				if (PropertyHelper.getProperties("UMB_CHANNEL_VERIFICATION_FLAG").equals("true")) {
					ExtentTestNGITestListener.createNode("UMB Verification");
					umbVerifyFuncs.umbVerification("PREPAID", "PLAN");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid registration  :" + e);
		}
	}
}
