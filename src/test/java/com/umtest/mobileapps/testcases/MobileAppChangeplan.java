package com.umtest.mobileapps.testcases;

import java.io.IOException;

import com.umtest.mobileapps.pagefunction.MobileAppProfileandloginupdateFuncs;
import com.umtest.mobileapps.pageobject.MobileAppSwitchPlanPage;
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

public class MobileAppChangeplan extends DriverFactory {
	private RemoteWebDriver driver;
	private RemoteWebDriver driverChrome;

	public static RemoteWebDriver driverMobileApp;
	public static RemoteWebDriver driverSelfcare;
	public static RemoteWebDriver driverUMB;

	MobileAppLoginFuncs loginFuncs;
	MobileAppUtilFuncs utilFuncs;
	MobileAppUtilFuncs mobileAppUtilFuncs;
	UMBVerificationFuncs umbFuncs;
	SelfcareVerificationFuncs selfcareFuncs;
	MobileAppSwitchPlanPage switchPlan;
	ExtentTestNGITestListener ex;

	private static Logger logger = LogManager.getLogger(MobileAppChangeplan.class);

	@BeforeClass
	public void initialiseObj() throws IOException {
		/*driver = getDriver("MOBILEAPP", "Android");
		loginFuncs = new MobileAppLoginFuncs(driver);
		utilFuncs = new MobileAppUtilFuncs(driver);
		switchPlan=new MobileAppSwitchPlanPage(driver);*/
		MainUtil.APPLICATION_NAME = "MOBILEAPP";
	}

	@Test(description = "Mobile App Changeplan")
	@Parameters({"NewplanName","NewRatePlanFullname","msisdn"})
	public void MobileAppChangeRatePlan(String NewplanName,String NewRatePlanFullname, String msisdn) {
		
		try {
			
			ExtentTestNGITestListener.createNode("Do Mobile App Postpaid Change Plan");
					
			MainUtil.dictionary.put("PLAN_NAME", NewplanName);
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("NewRatePlanFullname", NewRatePlanFullname);

			/*Launch Mobile App*/
			driverMobileApp = getDriver("MOBILEAPP", "Android");
			loginFuncs = new MobileAppLoginFuncs(driverMobileApp);
			loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

			utilFuncs = new MobileAppUtilFuncs(driverMobileApp);
			boolean status = utilFuncs.doChangerateplan(MainUtil.dictionary.get("PLAN_NAME"));
			
			if (status == true) {
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

				ExtentTestNGITestListener.createNode("UMB Verification");

				driverUMB = getDriver("UMB");
				umbFuncs = new UMBVerificationFuncs(driverUMB);
				umbFuncs.umbVerification("POSTPAID", "PLAN");

				ExtentTestNGITestListener.createNode("Selfcare Verification");

				driverSelfcare = getDriver("SELFCARE");
				selfcareFuncs = new SelfcareVerificationFuncs(driverSelfcare);
				selfcareFuncs.verifySelfcare("POSTPAID");

				ExtentTestNGITestListener.createNode("Mobile App Verification");

				driverMobileApp = getDriver("MOBILEAPP", "Android");
				loginFuncs = new MobileAppLoginFuncs(driverMobileApp);
				loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));
				mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
				mobileAppUtilFuncs.verifyMobileApp(MainUtil.dictionary.get("REGISTRATION_TYPE")+"_POSTPAID");
			}	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing postpaid ChangeRateplan  :" + e);
		}
	}

	@Test(description = "Mobile App Prepaid Switch Plan")
	@Parameters({"currentPlanName","newPlanName","msisdn","testDataFile"})
	public void MobileAppSwitchPlanPrepaid(String currentPlanName,String newPlanName, String msisdn, String testDataFile) {

		try {
			ExtentTestNGITestListener.createNode("Do Mobile App Prepaid Switch Plan");

			MainUtil.dictionary.put("PLAN_NAME", currentPlanName);
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("NEW_PLAN_NAME", newPlanName);
			MainUtil.dictionary.put("TEST_DTA_FILE",testDataFile);

			/*Launch Mobile App*/
			driverMobileApp = getDriver("MOBILEAPP", "Android");
			loginFuncs = new MobileAppLoginFuncs(driverMobileApp);
			loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
			switchPlan=new MobileAppSwitchPlanPage(driverMobileApp);
			boolean status = switchPlan.doSwitchPlan(MainUtil.dictionary.get("PLAN_NAME"),MainUtil.dictionary.get("NEW_PLAN_NAME"));

			if (status == true) {
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

				ExtentTestNGITestListener.createNode("UMB Verification");

				driverUMB = getDriver("UMB");
				umbFuncs = new UMBVerificationFuncs(driverUMB);
				umbFuncs.umbVerification("PREPAID", "PLAN");

				ExtentTestNGITestListener.createNode("Selfcare Verification");

				driverSelfcare = getDriver("SELFCARE");
				selfcareFuncs = new SelfcareVerificationFuncs(driverSelfcare);
				selfcareFuncs.verifySelfcare("PREPAID");

				ExtentTestNGITestListener.createNode("Mobile App Verification");

				driverMobileApp = getDriver("MOBILEAPP", "Android");
				loginFuncs = new MobileAppLoginFuncs(driverMobileApp);
				loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

				mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
//				mobileAppUtilFuncs.verifyMobileApp(MainUtil.dictionary.get("REGISTRATION_TYPE")+"_PREPAID");
				mobileAppUtilFuncs.verifyMobileApplication(MainUtil.dictionary.get("TEST_DTA_FILE"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while doing prepaid Switch Plan  :" + e);
		}
	}
}
		
