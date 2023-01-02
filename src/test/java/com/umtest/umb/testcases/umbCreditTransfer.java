package com.umtest.umb.testcases;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.umtest.isd.pagefunction.isdSubscriberInfoFunc;
import com.umtest.mobileapps.pagefunction.MobileAppLoginFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppUtilFuncs;
import com.umtest.selfcare.pagefunction.SelfcareVerificationFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.listener.ExtentTestNGITestListener;
import com.umtest.umb.pagefunction.UMBLoginLogoutFuncs;
import com.umtest.umb.pagefunction.UMBUSSDFuncs;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;

public class umbCreditTransfer extends DriverFactory {
	private RemoteWebDriver driver;
	UMBLoginLogoutFuncs umbLoginLogout;
	UMBVerificationFuncs umbVerification;
	SelfcareVerificationFuncs selfcareVerify;
	MobileAppLoginFuncs mobileAppLoginFuncs;
	
	ApplicationUtil appUtil;
	private static Logger logger = LogManager.getLogger(umbCreditTransfer.class);


	@BeforeClass
	public void initialiseObj() throws IOException {
		driver = getDriver("chrome");
		umbLoginLogout = new UMBLoginLogoutFuncs(driver);
		umbVerification = new UMBVerificationFuncs(driver);
			MainUtil.APPLICATION_NAME = "UMB";
	}

	@Test(description = "UMB Credit Transfer")
	@Parameters({"category","Sender_msisdn","Receiver_msisdn","transfer_amount"})
	public void creditTransfer(String category, String Sender_msisdn, String Receiver_msisdn, String transfer_amount) {


		try {
			String mainBalance = null;
			String creditBalance = null;
			String planType = "Prepaid";
			MainUtil.dictionary.put("MSISDN", Sender_msisdn);
			MainUtil.dictionary.put("RECEIVER_MSISDN", Receiver_msisdn);
			//MainUtil.dictionary.put("OPERATION_TYPE", "CREDIT_TRANSFER");
			MainUtil.dictionary.put("CATEGORY", category);
			MainUtil.dictionary.put("TRANSFER_AMOUNT", transfer_amount);
			
			/*Get main balance and Credit Balance*/
			ExtentTestNGITestListener.createNode("UMB CREDIT TRANSFER: GET MAIN & CREDIT SHARE BALANCE");
			mainBalance = umbVerification.getBalance(planType, "BalanceCheck");
			MainUtil.dictionary.put("CURRENT_BALANCE", mainBalance);
			
			MainUtil.dictionary.put("MSISDN", Receiver_msisdn);
			creditBalance = umbVerification.getBalance(planType, "CreditTransfer BalCheck");
			MainUtil.dictionary.put("CREDIT_AMOUNT", creditBalance);
			MainUtil.dictionary.put("MSISDN", Sender_msisdn);
		    Boolean flag = umbVerification.performCreditTransfer(planType,category);

		//	Boolean flag = true;

			if(flag) {
				/*Verify balance for MSISDN A*/
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN")); 
				/*Verify UMB*/
				ExtentTestNGITestListener.createNode("UMB Credit Share Balance Verification (Sender)");
				umbVerification.umbVerification(planType,MainUtil.dictionary.get("CATEGORY"));
			/*	Verify Selfcare*/
				ExtentTestNGITestListener.createNode("Selfcare Balance Verification (Sender)");
				driver = getDriver("chrome");
				selfcareVerify = new SelfcareVerificationFuncs(driver);
				selfcareVerify.verifySelfcare(planType);
				/*Verify Mobile App*/
				ExtentTestNGITestListener.createNode("Mobile App Credit Share Balance Verification (Sender)");
				driver = getDriver("MOBILEAPP", "Android");
				mobileAppLoginFuncs = new MobileAppLoginFuncs(driver);
				mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));
				MobileAppUtilFuncs mobileAppUtilFuncs = new MobileAppUtilFuncs(driver);
				mobileAppUtilFuncs.verifyCreditShareBalance();

				/*Verify balance for MSISDN B*/
				MainUtil.dictionary.put("MSISDN", Receiver_msisdn);
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN")); 
				/*Verify UMB*/
				ExtentTestNGITestListener.createNode("UMB Credit Share Balance Verification (Receiver)");
				umbVerification.umbVerification(planType,MainUtil.dictionary.get("CATEGORY"));	
				/*Verify Selfcare*/
				ExtentTestNGITestListener.createNode("Selfcare Balance Verification (Sender)");
				driver = getDriver("chrome");	
				selfcareVerify = new SelfcareVerificationFuncs(driver);
				selfcareVerify.verifySelfcare(planType);
				/*Verify Mobile App*/
				ExtentTestNGITestListener.createNode("Mobile App Credit Share Balance Verification (Receiver)");
				driver = getDriver("MOBILEAPP", "Android");
				mobileAppLoginFuncs = new MobileAppLoginFuncs(driver);
				mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));
				MobileAppUtilFuncs mobileAppUtilFuncs1 = new MobileAppUtilFuncs(driver);
				mobileAppUtilFuncs1.verifyCreditShareBalance();

			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured during bundle purchase  :" + e);
		}
	}
}