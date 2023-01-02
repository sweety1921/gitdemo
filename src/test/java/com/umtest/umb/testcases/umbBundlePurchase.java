package com.umtest.umb.testcases;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.beust.jcommander.Parameter;
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

import io.appium.java_client.android.AndroidDriver;

public class umbBundlePurchase extends DriverFactory {
	private RemoteWebDriver driver;
	private RemoteWebDriver driverMobileApp;
	private RemoteWebDriver driverSelfcare;

	UMBLoginLogoutFuncs umbLoginLogout;
	UMBVerificationFuncs umbVerification;
	UMBUSSDFuncs umbUSSDFunc;
	ApplicationUtil appUtil;
	MobileAppUtilFuncs mobileAppUtilFuncs;
	private static Logger logger = LogManager.getLogger(umbBundlePurchase.class);
	

	@BeforeClass
	public void initialiseObj() throws IOException {
		driver = getDriver("chrome");
		umbLoginLogout = new UMBLoginLogoutFuncs(driver);
		umbVerification = new UMBVerificationFuncs(driver);
		umbUSSDFunc = new UMBUSSDFuncs(driver);
	
		
		MainUtil.APPLICATION_NAME = "UMB";
	}
	
	@Test(description = "UMB Bundle Purchase")
	@Parameters({"bundleName","msisdn","planType","testDataFile"})
	public void bundlePurchase(String bundleName, String msisdn, String planType,String testDataFile) {
		try {
			String mainBalance = null;
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("OPERATION_TYPE", "BUNDLE");
			MainUtil.dictionary.put("TEST_DATA_FILE", testDataFile);
			MainUtil.dictionary.put("ACCOUNT_TYPE", planType);

			/*Purchase Bundle*/
	//		ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
			ExtentTestNGITestListener.createNode("GET ACCOUNT BALANCE");
			if(planType.equalsIgnoreCase("PREPAID")) {
				mainBalance = umbVerification.getBalance(planType, "BalanceCheck");
				MainUtil.dictionary.put("CURRENT_BALANCE", mainBalance);
				String[] arrBundle = bundleName.split(":");
	    		MainUtil.dictionary.put("CATEGORY", arrBundle[0]);
	    		MainUtil.dictionary.put("BUNDLE_NAME", arrBundle[1]);
			}else {
				MainUtil.dictionary.put("CATEGORY", bundleName);
	    		MainUtil.dictionary.put("BUNDLE_NAME", bundleName);
			}
			
			ExtentTestNGITestListener.createNode("UMB PURCHASE BUNDLE");
    		Boolean flag = umbVerification.bundlePurchaseUMB(planType);
//    		Boolean flag = true;
			Thread.sleep(30000);
    		if(flag) {
    		ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN")); 
    		/*Verify UMB*/
    		ExtentTestNGITestListener.createNode("UMB Bundle Verification");
			umbVerification.umbVerification(planType,MainUtil.dictionary.get("OPERATION_TYPE"));
			
			/*Verify Mobile App*/
				ExtentTestNGITestListener.createNode("Mobile App Verification");

				driverMobileApp = getDriver("MOBILEAPP", "Android");
				//mobileApploginFuncs = new MobileAppLoginFuncs(driverMobileApp);
				//mobileApploginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

				mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
				//mobileAppUtilFuncs.verifyMobileApp(MainUtil.dictionary.get("REGISTRATION_TYPE")+"_POSTPAID");
				mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TEST_DATA_FILE"));

			if(planType.equalsIgnoreCase("Prepaid")) {
				//Verify Selfcare
				ExtentTestNGITestListener.createNode("Selfcare Balance Verification");
				driver = getDriver("chrome");
				SelfcareVerificationFuncs selfcareVerify = new SelfcareVerificationFuncs(driver);
				selfcareVerify.verifySelfcare(planType);
				
				//Verify ISD
				ExtentTestNGITestListener.createNode("ISD Subscriber Info Verification");
				driver = getDriver("chrome");
				isdSubscriberInfoFunc isdVerification = new isdSubscriberInfoFunc(driver);
				isdVerification.verifySubscriberInfo();
				}
    		}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured during bundle purchase  :" + e);
		}
	}
}


