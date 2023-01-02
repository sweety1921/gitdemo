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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class UMREXPostpaidChangeRatePlan extends DriverFactory {
	private RemoteWebDriver driver;
	private RemoteWebDriver driverChrome;

	UMREXLoginLogoutFuncs loginFuncs;
	UMREXPostpaidChangeRatePlanFuncs postpaidcrpfuncs;

	private RemoteWebDriver driverUMREX;
	private RemoteWebDriver driverMobileApp;
	private RemoteWebDriver driverSelfcare;
	private RemoteWebDriver driverUMB;
	private RemoteWebDriver driverUMREXPORTAL;

	UMBVerificationFuncs umbFuncs;
	SelfcareVerificationFuncs selfcareFuncs;
	MobileAppLoginFuncs mobileApploginFuncs;
	MobileAppUtilFuncs mobileAppUtilFuncs;


	ExtentTestNGITestListener ex;

	private static Logger logger = LogManager.getLogger(UMREXPostpaidChangeRatePlan.class);

	@BeforeClass
	public void initialiseObj() throws IOException {
		MainUtil.APPLICATION_NAME = "UMREX";
	}

	@Test(description = "UMREX Postpaid Change Rate Plan")
	@Parameters({"NewplanName","msisdn","ChangeRateplantype","testDataFile"})
	public void UMREXPostpaidChangePlan(String NewplanName,String msisdn,String ChangeRateplantype,String testDataFile) {

		try {
			ExtentTestNGITestListener.createNode("UMREX Postpaid Registration");
			MainUtil.dictionary.put("NEWPLAN_NAME", NewplanName);
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("ChangeRateplantype", ChangeRateplantype);
			MainUtil.dictionary.put("REGISTRATION_TYPE", "PLAN");
			MainUtil.dictionary.put("TEST_DATA_FILE", testDataFile);

			if (System.getProperty("simType") == null || System.getProperty("simType").equalsIgnoreCase("PHYSICAL")) {
				MainUtil.dictionary.put("SIM_TYPE", "PHYSICAL");
			} else {
				MainUtil.dictionary.put("SIM_TYPE", "DUMMY");
			}

			//*Launch UMREX*//*
			driverUMREX = getDriver("UMREX", "Android");
			loginFuncs = new UMREXLoginLogoutFuncs(driverUMREX);
			loginFuncs.loginUMREX();

			postpaidcrpfuncs = new UMREXPostpaidChangeRatePlanFuncs(driverUMREX);
			boolean statusFlag = postpaidcrpfuncs.doPostpaidChangePlan(MainUtil.dictionary.get("REGISTRATION_TYPE"),"MALAYSIAN");
			loginFuncs.logoutUMREX();

			Thread.sleep(50000);

			if (statusFlag == true) {
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

				ExtentTestNGITestListener.createNode("UMB Verification");

				driverUMB = getDriver("UMB");
				umbFuncs=new UMBVerificationFuncs(driverUMB);
				umbFuncs.umbVerification("POSTPAID", "PLAN");

				ExtentTestNGITestListener.createNode("Selfcare Verification");

				driverSelfcare = getDriver("SELFCARE");
				selfcareFuncs = new SelfcareVerificationFuncs(driverSelfcare);
				selfcareFuncs.verifySelfcare("POSTPAID");

				ExtentTestNGITestListener.createNode("Mobile App Verification");

				driver = getDriver("MOBILEAPP", "Android");
				driverMobileApp = getDriver("MOBILEAPP", "Android");
				mobileApploginFuncs = new MobileAppLoginFuncs(driverMobileApp);
				mobileApploginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

				mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
				//mobileAppUtilFuncs.verifyMobileApp(MainUtil.dictionary.get("REGISTRATION_TYPE")+"_POSTPAID");
				mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TEST_DATA_FILE"));

			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Postpaid change Rate Plan  :" + e);
		}
	}


	@Test(description = "UMREX Postpaid Change Rate Plan")
	@Parameters({"NewplanName","membermsisdn","principalmsisdn","ChangeRateplantype"})
	public void UMREXPostpaidChangePlantomember(String NewplanName,String membermsisdn,String principalmsisdn,String ChangeRateplantype) {

		try {
			ExtentTestNGITestListener.createNode("UMREX Postpaid Registration");
			MainUtil.dictionary.put("NEWPLAN_NAME", NewplanName);
			MainUtil.dictionary.put("membermsisdn", membermsisdn);
			MainUtil.dictionary.put("principalmsisdn", principalmsisdn);
			MainUtil.dictionary.put("ChangeRateplantype", ChangeRateplantype);
			MainUtil.dictionary.put("REGISTRATION_TYPE", "PLAN");

			if (System.getProperty("simType") == null || System.getProperty("simType").equalsIgnoreCase("PHYSICAL")) {
				MainUtil.dictionary.put("SIM_TYPE", "PHYSICAL");
			} else {
				MainUtil.dictionary.put("SIM_TYPE", "DUMMY");
			}

			//*Launch UMREX*//*
			driverUMREX = getDriver("UMREX", "Android");
			loginFuncs = new UMREXLoginLogoutFuncs(driverUMREX);
			loginFuncs.loginUMREX();

			postpaidcrpfuncs = new UMREXPostpaidChangeRatePlanFuncs(driverUMREX);
			boolean statusFlag = postpaidcrpfuncs.doPostpaidChangePlantomember(MainUtil.dictionary.get("REGISTRATION_TYPE"),"MALAYSIAN");
			loginFuncs.logoutUMREX();
			Thread.sleep(50000);

			if (statusFlag == true) {
				MainUtil.dictionary.put("msisdn", membermsisdn);
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("msisdn"));

				ExtentTestNGITestListener.createNode("UMB Verification");

				driverUMB = getDriver("UMB");
				umbFuncs=new UMBVerificationFuncs(driverUMB);
				umbFuncs.umbVerification("POSTPAID", "PLAN");

				ExtentTestNGITestListener.createNode("Selfcare Verification");

				driverSelfcare = getDriver("SELFCARE");
				selfcareFuncs = new SelfcareVerificationFuncs(driverSelfcare);
				selfcareFuncs.verifySelfcare("POSTPAID");

				ExtentTestNGITestListener.createNode("Mobile App Verification");
				driverMobileApp = getDriver("MOBILEAPP", "Android");
				mobileApploginFuncs = new MobileAppLoginFuncs(driverMobileApp);
				mobileApploginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

				mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
				mobileAppUtilFuncs.verifyMobileApp(MainUtil.dictionary.get("REGISTRATION_TYPE")+"_POSTPAID");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Postpaid change Rate Plan  :" + e);
		}
	}

	@Test(description = "UMREX Postpaid Change Rate Plan")
	@Parameters({"NewplanName","msisdn","ChangeRateplantype"})
	public void UMREXPostpaidFamilyChangePlan(String NewplanName,String msisdn,String ChangeRateplantype) {

		try {
			ExtentTestNGITestListener.createNode("UMREX Postpaid Family change rate plan");
			MainUtil.dictionary.put("NEWPLAN_NAME", NewplanName);
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("ChangeRateplantype", ChangeRateplantype);
			MainUtil.dictionary.put("REGISTRATION_TYPE", "PLAN");

			if (System.getProperty("simType") == null || System.getProperty("simType").equalsIgnoreCase("PHYSICAL")) {
				MainUtil.dictionary.put("SIM_TYPE", "PHYSICAL");
			} else {
				MainUtil.dictionary.put("SIM_TYPE", "DUMMY");
			}

			//*Launch UMREX*//*
			driverUMREX = getDriver("UMREX", "Android");
			loginFuncs = new UMREXLoginLogoutFuncs(driverUMREX);
			loginFuncs.loginUMREX();

			postpaidcrpfuncs = new UMREXPostpaidChangeRatePlanFuncs(driverUMREX);
			boolean statusFlag = postpaidcrpfuncs.doPostpaidFamilyChangePlan(MainUtil.dictionary.get("REGISTRATION_TYPE"),"MALAYSIAN");
			loginFuncs.logoutUMREX();

			Thread.sleep(50000);

			if (statusFlag == true) {
				String[] arrmsdn = MainUtil.dictionary.get("msisdn").split(";");
				for (int w = 1; w==arrmsdn.length; w++) {
					ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("msisdn"+w));
				}

				ExtentTestNGITestListener.createNode("UMB Verification");

				driverUMB = getDriver("UMB");
				umbFuncs=new UMBVerificationFuncs(driverUMB);
				umbFuncs.umbVerification("POSTPAID", "PLAN");

				ExtentTestNGITestListener.createNode("Selfcare Verification");

				driverSelfcare = getDriver("SELFCARE");
				selfcareFuncs = new SelfcareVerificationFuncs(driverSelfcare);
				selfcareFuncs.verifySelfcare("POSTPAID");

				ExtentTestNGITestListener.createNode("Mobile App Verification");

				driverMobileApp = getDriver("MOBILEAPP", "Android");
				mobileApploginFuncs = new MobileAppLoginFuncs(driverMobileApp);
				mobileApploginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

				mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
				mobileAppUtilFuncs.verifyMobileApp(MainUtil.dictionary.get("REGISTRATION_TYPE")+"_POSTPAID");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Postpaid change Rate Plan  :" + e);
		}
	}

	@Test(description = "UMREX Postpaid Suspend Perform Change Rate Plan")
	@Parameters({"msisdn"})
	public void UMREXPostpaidsuspendChangePlan(String msisdn) {

		try {
			ExtentTestNGITestListener.createNode("UMREX Postpaid Suspend change rate plan");
			MainUtil.dictionary.put("msisdn", msisdn);

			//*Launch UMREX*//*
			driverUMREX = getDriver("UMREX", "Android");
			loginFuncs = new UMREXLoginLogoutFuncs(driverUMREX);
			loginFuncs.loginUMREX();

			postpaidcrpfuncs = new UMREXPostpaidChangeRatePlanFuncs(driverUMREX);
			boolean statusFlag = postpaidcrpfuncs.doPostpaidsuspendChangePlan(MainUtil.dictionary.get("REGISTRATION_TYPE"),"MALAYSIAN");
			loginFuncs.logoutUMREX();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Postpaid suspend change Rate Plan  :" + e);
		}
	}

	@Test(description = "UMREX Postpaid BAR Perform Change Rate Plan")
	@Parameters({"msisdn"})
	public void UMREXPostpaidbarChangePlan(String msisdn) {

		try {
			ExtentTestNGITestListener.createNode("UMREX Postpaid Suspend change rate plan");
			MainUtil.dictionary.put("msisdn", msisdn);

			//*Launch UMREX*//*
			driverUMREX = getDriver("UMREX", "Android");
			loginFuncs = new UMREXLoginLogoutFuncs(driverUMREX);
			loginFuncs.loginUMREX();

			postpaidcrpfuncs = new UMREXPostpaidChangeRatePlanFuncs(driverUMREX);
			boolean statusFlag = postpaidcrpfuncs.doPostpaidbarChangePlan(MainUtil.dictionary.get("REGISTRATION_TYPE"),"MALAYSIAN");
			loginFuncs.logoutUMREX();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Postpaid suspend change Rate Plan  :" + e);
		}
	}
}
