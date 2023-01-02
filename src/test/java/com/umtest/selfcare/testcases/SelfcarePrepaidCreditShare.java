package com.umtest.selfcare.testcases;

import com.umtest.mobileapps.pagefunction.MobileAppLoginFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppUtilFuncs;


import com.umtest.selfcare.pagefunction.SelfcareCreditShareFuncs;
import com.umtest.selfcare.pagefunction.SelfcareLoginLogoutFuncs;
import com.umtest.selfcare.pagefunction.SelfcareVerificationFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.APIRequestLibrary;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.lib.PropertyHelper;
import com.umtest.testframe.listener.ExtentTestNGITestListener;
import com.umtest.umb.pagefunction.UMBLoginLogoutFuncs;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class SelfcarePrepaidCreditShare extends DriverFactory {
	private RemoteWebDriver driver;
	private RemoteWebDriver driverChrome;
	private RemoteWebDriver driverApp;
	private RemoteWebDriver driverChromeUMB;
	
	UMBLoginLogoutFuncs umbLoginLogout;
	UMBVerificationFuncs umbVerification;
	SelfcareLoginLogoutFuncs loginFuncs;
	SelfcareCreditShareFuncs selfcareCreditShareFuncs;
	SelfcareVerificationFuncs selfcareVerifyFuncs;
	MobileAppLoginFuncs mobileAppLoginFuncs;
	MobileAppUtilFuncs mobileAppUtilFuncs;
	public APIRequestLibrary selfcareAPI;

	ExtentTestNGITestListener ex;

	private static Logger logger = LogManager.getLogger(SelfcarePrepaidCreditShare.class);

	@BeforeClass
	public void initialiseObj() throws IOException {

		MainUtil.APPLICATION_NAME = "Selfcare";
	}

	@Test(description = "UMB Credit Transfer")
	@Parameters({"category","Sender_msisdn","Receiver_msisdn","transfer_amount"})
	public void creditTransfer(String category, String Sender_msisdn, String Receiver_msisdn, String transfer_amount) {

		try {

			/*######################################################################################################################################################*/
			driverChrome = getDriver("chrome");
			driverChromeUMB = getDriver("chromeUMB");
			driverApp = getDriver("MOBILEAPP", "Android");
			loginFuncs = new SelfcareLoginLogoutFuncs(driverChrome);
			selfcareCreditShareFuncs = new SelfcareCreditShareFuncs(driverChrome);
			selfcareVerifyFuncs = new SelfcareVerificationFuncs(driverChrome);
			umbVerification = new UMBVerificationFuncs(driverChromeUMB);

			selfcareAPI=new APIRequestLibrary();
			/*######################################################################################################################################################*/

			ExtentTestNGITestListener.createNode("Selfcare CREDIT TRANSFER: GET MAIN & CREDIT SHARE BALANCE");

			String planType = "Prepaid";

			MainUtil.dictionary.put("MSISDN", Sender_msisdn);
			MainUtil.dictionary.put("RECEIVER_MSISDN", Receiver_msisdn);

			MainUtil.dictionary.put("CATEGORY", category);
			MainUtil.dictionary.put("TRANSFER_AMOUNT", transfer_amount);

			selfcareAPI.syncSubscriberBalanceAndExpiryDate(Sender_msisdn);
			selfcareAPI.syncSubscriberCreditShare(Sender_msisdn);

			selfcareAPI.syncSubscriberBalanceAndExpiryDate(Receiver_msisdn);
			selfcareAPI.syncSubscriberCreditShare(Receiver_msisdn);

			/*Get main balance of Sender*/
			ApplicationUtil.getPrepaidMainBalanceAccInfoTable(Sender_msisdn);
			MainUtil.dictionary.put("CURRENT_BALANCE",MainUtil.dictionary.get("MAIN_BALANCE"));

			/*Get Credit Balance of Receiver*/
			ApplicationUtil.getCreditAmountAccInfoTable(Receiver_msisdn);
			MainUtil.dictionary.put("CREDIT_AMOUNT_RECEIVER",MainUtil.dictionary.get("CREDIT_AMOUNT"));

			MainUtil.dictionary.put("MSISDN", Sender_msisdn);

			loginFuncs.loginSelfcare(Sender_msisdn);
			Boolean statusFlag = selfcareCreditShareFuncs.performCreditTransfer(Receiver_msisdn);
			loginFuncs.logoutSelfcare();
//			boolean statusFlag = true;


			if(statusFlag) {
				/*Verify balance for MSISDN A*/
				MainUtil.dictionary.put("MSISDN", Sender_msisdn);
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
				/*Verify UMB*/

				if (PropertyHelper.getProperties("UMB_CHANNEL_VERIFICATION_FLAG").equals("true")) {

					ExtentTestNGITestListener.createNode("UMB Credit Share Balance Verification (Sender)");
					if(MainUtil.dictionary.get("CREDIT_AMOUNT").equalsIgnoreCase("NA")){
						umbVerification.umbVerification(planType, "NoCreditTransfer");
					}
					else {
						umbVerification.umbVerification(planType, MainUtil.dictionary.get("CATEGORY"));
					}
				}

				if (PropertyHelper.getProperties("SELFCARE_CHANNEL_VERIFICATION_FLAG").equals("true")) {
					/*	Verify Selfcare*/
					ExtentTestNGITestListener.createNode("Selfcare Balance Verification (Sender)");
					selfcareVerifyFuncs.verifySelfcarePrepaidBalancesAndExpiryDate();
				}

				if (PropertyHelper.getProperties("MOBILEAPP_CHANNEL_VERIFICATION_FLAG").equals("true")) {

					/*Verify Mobile App*/
					ExtentTestNGITestListener.createNode("Mobile App Credit Share Balance Verification (Sender)");
					mobileAppLoginFuncs = new MobileAppLoginFuncs(driverApp);
					mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

					mobileAppUtilFuncs = new MobileAppUtilFuncs(driverApp);
					mobileAppUtilFuncs.verifyCreditShareBalance();
				}

				/*Verify balance for MSISDN B*/
				MainUtil.dictionary.put("MSISDN", Receiver_msisdn);
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

				if (PropertyHelper.getProperties("UMB_CHANNEL_VERIFICATION_FLAG").equals("true")) {
					/*Verify UMB*/
					ExtentTestNGITestListener.createNode("UMB Credit Share Balance Verification (Receiver)");
					if(MainUtil.dictionary.get("CREDIT_AMOUNT").equalsIgnoreCase("NA")){
						umbVerification.umbVerification(planType, "NoCreditTransfer");
					}
					else {
						umbVerification.umbVerification(planType, MainUtil.dictionary.get("CATEGORY"));
					}
				}

				if (PropertyHelper.getProperties("SELFCARE_CHANNEL_VERIFICATION_FLAG").equals("true")) {
					/*Verify Selfcare*/
					ExtentTestNGITestListener.createNode("Selfcare Balance Verification (Sender)");
					selfcareVerifyFuncs.verifySelfcarePrepaidBalancesAndExpiryDate();
				}

				if (PropertyHelper.getProperties("MOBILEAPP_CHANNEL_VERIFICATION_FLAG").equals("true")) {
					/*Verify Mobile App*/
					ExtentTestNGITestListener.createNode("Mobile App Credit Share Balance Verification (Receiver)");
					mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));
					mobileAppUtilFuncs.verifyCreditShareBalance();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured during bundle purchase  :" + e);
		}
	}
}
