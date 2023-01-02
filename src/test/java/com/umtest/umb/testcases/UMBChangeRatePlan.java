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

public class UMBChangeRatePlan extends DriverFactory{
	private RemoteWebDriver driver;
	private RemoteWebDriver driverSelfcare;
	private RemoteWebDriver driverUMB;
	private RemoteWebDriver driverMobileApp;
	UMBLoginLogoutFuncs umbLoginLogout;
	UMBVerificationFuncs umbVerification;
	UMBUSSDFuncs umbUSSDFunc;
	ApplicationUtil appUtil;
	MobileAppUtilFuncs mobileAppUtilFuncs;
	private static Logger logger = LogManager.getLogger(UMBChangeRatePlan.class);
	

	@BeforeClass
	public void initialiseObj() throws IOException {
		driver = getDriver("chrome");
		umbLoginLogout = new UMBLoginLogoutFuncs(driver);
		umbVerification = new UMBVerificationFuncs(driver);
		umbUSSDFunc = new UMBUSSDFuncs(driver);
	
		
		MainUtil.APPLICATION_NAME = "UMB";
	}
	
	@Test(description = "UMB Change Rate Plan")
	@Parameters({"category","newplanName","msisdn","planType","testDataFile"})
	public void changePlan(String category, String newplanName, String msisdn, String planType, String testDataFile) {
		
		
		try {
			String mainBalance = null;
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("CATEGORY", category);
			MainUtil.dictionary.put("NEWPLAN_NAME", newplanName);
			MainUtil.dictionary.put("PLAN_TYPE", planType);
			MainUtil.dictionary.put("TEST_DATA_FILE", testDataFile);
			MainUtil.dictionary.put("ACCOUNT_TYPE", planType);
			/*Purchase Bundle*/
			
			ExtentTestNGITestListener.createNode("UMB CHANGE RATE PLAN");
			if(planType.equalsIgnoreCase("Prepaid")) {
				mainBalance = umbVerification.getBalance(planType, "BalanceCheck");
				MainUtil.dictionary.put("CURRENT_MAIN_BALANCE", mainBalance);
			}
			
			Boolean flag = umbVerification.performUMBCRP(planType,category);
    		
    		//Boolean flag = true;
    		
			if (flag == true) {
				Thread.sleep(30000);
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

				ExtentTestNGITestListener.createNode("UMB Verification");
				driverUMB = getDriver("chrome");
				UMBVerificationFuncs umbVerify = new UMBVerificationFuncs(driverUMB);
				umbVerify.umbVerification(MainUtil.dictionary.get("PLAN_TYPE"), "PLAN");


//				ExtentTestNGITestListener.createNode("Selfcare Verification");
//				driverSelfcare = getDriver("chrome");
//				SelfcareVerificationFuncs selfcareVerify = new SelfcareVerificationFuncs(driverSelfcare);
//				selfcareVerify.verifySelfcare(MainUtil.dictionary.get("PLAN_TYPE"));



				ExtentTestNGITestListener.createNode("Mobile App Verification");

				driverMobileApp = getDriver("MOBILEAPP", "Android");
				//mobileApploginFuncs = new MobileAppLoginFuncs(driverMobileApp);
				//mobileApploginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

				mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
				//mobileAppUtilFuncs.verifyMobileApp(MainUtil.dictionary.get("REGISTRATION_TYPE")+"_POSTPAID");
				MainUtil.dictionary.put("PLAN_NAME", MainUtil.dictionary.get("NEWPLAN_NAME")) ;
				mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TEST_DATA_FILE"));
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured during Change rate plan  :" + e);
		}
	}
}

