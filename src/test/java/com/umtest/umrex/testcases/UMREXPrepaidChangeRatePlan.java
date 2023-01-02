package com.umtest.umrex.testcases;

import com.umtest.mobileapps.pagefunction.MobileAppLoginFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppUtilFuncs;
import com.umtest.selfcare.pagefunction.SelfcareVerificationFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.listener.ExtentTestNGITestListener;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;
import com.umtest.umrex.pagefunction.UMREXLoginLogoutFuncs;
import com.umtest.umrex.pagefunction.UMREXPostpaidChangeRatePlanFuncs;
import com.umtest.umrex.pagefunction.UMREXPostpaidRegistrationFuncs;
import com.umtest.umrex.pagefunction.UMREXPrepaidChangeRatePlanFuncs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class UMREXPrepaidChangeRatePlan extends DriverFactory {
	private RemoteWebDriver driver;
	private RemoteWebDriver driverChrome;

	UMREXLoginLogoutFuncs loginFuncs;
	UMREXPrepaidChangeRatePlanFuncs prepaidcrpfuncs;
	
	
	ExtentTestNGITestListener ex;

	private static Logger logger = LogManager.getLogger(UMREXPrepaidChangeRatePlan.class);

	@BeforeClass
	public void initialiseObj() throws IOException {

		driver = getDriver("UMREX", "Android");
		loginFuncs = new UMREXLoginLogoutFuncs(driver);
		prepaidcrpfuncs = new UMREXPrepaidChangeRatePlanFuncs(driver);
		
		MainUtil.APPLICATION_NAME = "UMREX";
	}


	@Test(description = "UMREX Change Rate Plan - 'Change Prepaid Rate Plan'")
	@Parameters({"newRatePlanName","currentRatePlanName","msisdn","changeRatePlanType","identificationType"})
	public void UMREXPrepaidChangeRatePlan(String newPlanName,String currentPlanName,String msisdn, String changeRatePlanType, String identificationType) {


		try {
			ExtentTestNGITestListener.createNode("UMREX Prepaid Change Rate Plan");
			MainUtil.dictionary.put("NEW_PLAN_NAME", newPlanName);
			MainUtil.dictionary.put("CURRENT_PLAN_NAME", currentPlanName);
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("CHANGE_RATE_PLAN_TYPE", changeRatePlanType);
			MainUtil.dictionary.put("IDENTIFICATION_TYPE", identificationType);

			loginFuncs.loginUMREX();

			boolean statusFlag = prepaidcrpfuncs.dopPrepaidChangePlan();
			loginFuncs.logoutUMREX();

			if (statusFlag == true) {
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

				ExtentTestNGITestListener.createNode("UMB Verification");
				driverChrome = getDriver("chrome");
				UMBVerificationFuncs umbVerify = new UMBVerificationFuncs(driverChrome);
				umbVerify.umbVerification("PREPAID","PLAN");


				ExtentTestNGITestListener.createNode("Selfcare Verification");
				SelfcareVerificationFuncs selfcareVerify = new SelfcareVerificationFuncs(driverChrome);
				selfcareVerify.verifySelfcare("PREPAID");


				ExtentTestNGITestListener.createNode("Mobile App Verification");
				driver = getDriver("MOBILEAPP", "Android");
				MobileAppLoginFuncs mobileAppLoginFuncs = new MobileAppLoginFuncs(driver);
				mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

				MobileAppUtilFuncs mobileAppUtilFuncs = new MobileAppUtilFuncs(driver);
				mobileAppUtilFuncs.verifyMobileAppPrepaidBundle();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while doing Prepaid Change Rate Plan  :" + e);
		}
	}
}
