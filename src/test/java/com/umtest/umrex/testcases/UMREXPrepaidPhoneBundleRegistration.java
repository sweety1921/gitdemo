package com.umtest.umrex.testcases;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.umtest.mobileapps.pagefunction.MobileAppLoginFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppUtilFuncs;
import com.umtest.selfcare.pagefunction.SelfcareVerificationFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.listener.ExtentTestNGITestListener;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;
import com.umtest.umrex.pagefunction.UMREXLoginLogoutFuncs;
import com.umtest.umrex.pagefunction.UMREXPrepaidRegistrationFuncs;

public class UMREXPrepaidPhoneBundleRegistration extends DriverFactory {
	private RemoteWebDriver driver;
	private RemoteWebDriver driverChrome;

	UMREXLoginLogoutFuncs loginFuncs;
	UMREXPrepaidRegistrationFuncs prepaidFuncs;

	ExtentTestNGITestListener ex;

	private static Logger logger = LogManager.getLogger(UMREXPrepaidPhoneBundleRegistration.class);

	@BeforeClass
	public void initialiseObj() throws IOException {

		driver = getDriver("UMREX", "Android");
		loginFuncs = new UMREXLoginLogoutFuncs(driver);
		prepaidFuncs = new UMREXPrepaidRegistrationFuncs(driver);

		MainUtil.APPLICATION_NAME = "UMREX";
	}

	@Test(description = "UMREX Prepaid Phone Bundle Registration")
	@Parameters({"planName","bundleName"})
	public void UMREXRegistrationPrepaidPhoneBundle(String planName, String bundleName) {

		try {

			ExtentTestNGITestListener.createNode("UMREX Prepaid Phone Bundle Registration");
			MainUtil.dictionary.put("PLAN_NAME", planName);
			MainUtil.dictionary.put("BUNDLE_NAME", bundleName);
			MainUtil.dictionary.put("REGISTRATION_TYPE", "PHONE_BUNDLE");

			
			loginFuncs.loginUMREX();
			boolean statusFlag = prepaidFuncs.doPrepaidPhoneBundleRegistration(MainUtil.dictionary.get("REGISTRATION_TYPE"),"NON MALAYSIAN");
			loginFuncs.logoutUMREX();
					
			if (statusFlag == true) {

				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

				ExtentTestNGITestListener.createNode("UMB Verification");
				driverChrome = getDriver("chrome");
				UMBVerificationFuncs umbVerify = new UMBVerificationFuncs(driverChrome);
				umbVerify.umbVerification("PREPAID","BUNDLE");


				ExtentTestNGITestListener.createNode("Selfcare Verification");
				driverChrome = getDriver("chrome");
				SelfcareVerificationFuncs selfcareVerify = new SelfcareVerificationFuncs(driverChrome);
				selfcareVerify.verifySelfcare("PREPAID");


				ExtentTestNGITestListener.createNode("Mobile App Verification");
				driver = getDriver("MOBILEAPP", "Android");
				MobileAppLoginFuncs mobileAppLoginFuncs = new MobileAppLoginFuncs(driver);
				mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

				MobileAppUtilFuncs mobileAppUtilFuncs = new MobileAppUtilFuncs(driver);
				mobileAppUtilFuncs.verifyMobileApp(MainUtil.dictionary.get("BUNDLE_PREPAID"));
			}			


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid phone bundle registration  :" + e);
		}
	}
	
}
