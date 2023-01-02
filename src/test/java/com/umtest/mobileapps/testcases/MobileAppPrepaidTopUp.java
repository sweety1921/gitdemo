package com.umtest.mobileapps.testcases;

import java.io.IOException;

import com.umtest.mobileapps.pagefunction.MobileAppBundlePurchaseFuncs;
import com.umtest.testframe.lib.PropertyHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.umtest.mobileapps.pagefunction.MobileAppLoginFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppPrepaidTopupFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppUtilFuncs;
import com.umtest.selfcare.pagefunction.SelfcareVerificationFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.listener.ExtentTestNGITestListener;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;

public class MobileAppPrepaidTopUp extends DriverFactory {

	public static RemoteWebDriver driverMobileApp;
	public static RemoteWebDriver driverSelfcare;
	public static RemoteWebDriver driverUMB;

	MobileAppLoginFuncs loginFuncs;
	MobileAppUtilFuncs mobileAppUtilFuncs;
	MobileAppPrepaidTopupFuncs topupFuncs;
	UMBVerificationFuncs umbFuncs;
	SelfcareVerificationFuncs selfcareFuncs;

	ExtentTestNGITestListener ex;

	private static Logger logger = LogManager.getLogger(MobileAppPrepaidTopUp.class);

	@BeforeClass
	public void initialiseObj() throws IOException {
		MainUtil.APPLICATION_NAME = "MOBILEAPP";
	}

	@Test(description = "Mobile App Topup")
	@Parameters({"msisdn","identificationType","planName","topupType","topupAmount"})
	public void MobileAppPrepaidTopUp(String msisdn, String identificationType, String planName, String topupType, String topupAmount) {
		
		try {

			ExtentTestNGITestListener.createNode("Do Mobile App Prepaid Top");
			MainUtil.dictionary.put("MSISDN",msisdn);
			MainUtil.dictionary.put("IDENTIFICATION_TYPE", identificationType);
			MainUtil.dictionary.put("PLAN_NAME", planName);
			MainUtil.dictionary.put("TOPUP_TYPE", topupType);
			MainUtil.dictionary.put("TOPUP_AMOUNT", topupAmount);
			
			ApplicationUtil.getPrepaidAccountByMSISDN(msisdn);

			/*Launch Mobile App*/
			driverMobileApp = getDriver("MOBILEAPP", "Android");
			loginFuncs = new MobileAppLoginFuncs(driverMobileApp);
			loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

			/*Perform TopUp*/
			topupFuncs = new MobileAppPrepaidTopupFuncs(driverMobileApp);
			boolean status = topupFuncs.doTopup(MainUtil.dictionary.get("TOPUP_TYPE"), MainUtil.dictionary.get("TOPUP_AMOUNT"));

			if(status) {
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
				Thread.sleep(5000);
			}

			if (status && PropertyHelper.getProperties("MOBILEAPP_CHANNEL_VERIFICATION_FLAG").equals("true")) {
				ExtentTestNGITestListener.createNode("Mobile App Verification");

				driverMobileApp = getDriver("MOBILEAPP", "Android");
				loginFuncs = new MobileAppLoginFuncs(driverMobileApp);
				loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

				mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
				mobileAppUtilFuncs.verifyMobileAppPrepaidTopUpOnly();
			}

			if (status && PropertyHelper.getProperties("UMB_CHANNEL_VERIFICATION_FLAG").equals("true")) {
				ExtentTestNGITestListener.createNode("UMB Verification");

				driverUMB = getDriver("UMB");
				umbFuncs = new UMBVerificationFuncs(driverUMB);
				umbFuncs.umbVerification("PREPAID", "PLAN");
			}

			if (status && PropertyHelper.getProperties("SELFCARE_CHANNEL_VERIFICATION_FLAG").equals("true")) {
				ExtentTestNGITestListener.createNode("Selfcare Verification");

				driverSelfcare = getDriver("SELFCARE");
				selfcareFuncs = new SelfcareVerificationFuncs(driverSelfcare);
				selfcareFuncs.verifySelfcare("PREPAID");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid registration  :" + e);
		}
	}

	@Test(description = "Mobile App Topup")
	@Parameters({"msisdn","topupAmount"})
	public void MobileAppPrepaidTopUpForAFriend(String msisdn, String topupAmount) {

		try {

			ExtentTestNGITestListener.createNode("Do Mobile App Prepaid Top");
			MainUtil.dictionary.put("MSISDN",msisdn);
			MainUtil.dictionary.put("TOPUP_AMOUNT", topupAmount);

			//	ApplicationUtil.getPrepaidAccount(planName);

			/*Launch Mobile App*/
			driverMobileApp = getDriver("MOBILEAPP", "Android");
			loginFuncs = new MobileAppLoginFuncs(driverMobileApp);
			loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

			topupFuncs = new MobileAppPrepaidTopupFuncs(driverMobileApp);
			boolean status = topupFuncs.doTopupForAFriend(MainUtil.dictionary.get("TOPUP_AMOUNT"));

			if (status) {

				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

				ExtentTestNGITestListener.createNode("Mobile App Verification");

				driverMobileApp = getDriver("MOBILEAPP", "Android");
				loginFuncs = new MobileAppLoginFuncs(driverMobileApp);
				loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

				mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
				mobileAppUtilFuncs.verifyMobileAppPrepaidTopUpOnly();

				ExtentTestNGITestListener.createNode("UMB Verification");

				driverUMB = getDriver("UMB");
				umbFuncs = new UMBVerificationFuncs(driverUMB);
				umbFuncs.umbVerification("PREPAID", "PLAN");

				ExtentTestNGITestListener.createNode("Selfcare Verification");

				driverSelfcare = getDriver("SELFCARE");
				selfcareFuncs = new SelfcareVerificationFuncs(driverSelfcare);
				selfcareFuncs.verifySelfcare("PREPAID");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid topup for a friend  :" + e);
		}
	}

	@Test(description = "Mobile App Prepaid Top - Invalid Top Up Voucher")
	@Parameters({"msisdn","topupPIN"})
	public void MobileAppTopup_InvalidVoucher(String msisdn, String topupPIN) {

		try {

			ExtentTestNGITestListener.createNode("Mobile App Prepaid Top - Invalid Top Up Voucher");
			MainUtil.dictionary.put("MSISDN",msisdn);

			/*Launch Mobile App*/
			driverMobileApp = getDriver("MOBILEAPP", "Android");
			loginFuncs = new MobileAppLoginFuncs(driverMobileApp);
			loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

			topupFuncs = new MobileAppPrepaidTopupFuncs(driverMobileApp);
			boolean status=topupFuncs.topupWithInvalidPIN(topupPIN);

			Assert.assertTrue(status,"Error Message NOT displayed");
			ExtentTestNGITestListener.putTestPassMessage("Error Message Displayed as expected");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while doing prepaid registration  :" + e);
		}
	}
}
