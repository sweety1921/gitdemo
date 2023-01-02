package com.umtest.umrex.testcases;

import com.umtest.erechargeportal.pageobject.ERECHARGEPORTALLoginLogoutPage;
import com.umtest.erechargeportal.pageobject.ERECHARGEPORTALTransactionsPage;
import com.umtest.mobileapps.pagefunction.MobileAppLoginFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppUtilFuncs;
import com.umtest.selfcare.pagefunction.SelfcareVerificationFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.ExtentScreenCapture;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.listener.ExtentTestNGITestListener;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;
import com.umtest.umrex.pagefunction.UMREXLoginLogoutFuncs;
import com.umtest.umrex.pagefunction.UMREXPrepaidRegistrationFuncs;
import com.umtest.umrex.pagefunction.UMREXPrepaidTopUpFuncs;
import com.umtest.umrex.pageobject.UMREXHomePage;
import com.umtest.umrex.pageobject.UMREXPrepaidRegistrationPage;
import com.umtest.umrexportal.pagefunction.UMREXPortalFuncs;
import com.umtest.umrexportal.pageobject.UMREXPortalTopUpReportsPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.reporters.jq.Main;

import java.io.IOException;

import static com.umtest.testframe.listener.ExtentTestNGITestListener.getTest;

public class UMREXPrepaidTopUp extends DriverFactory {
	private RemoteWebDriver driver;
	private RemoteWebDriver driverAppMobileApp;
	private RemoteWebDriver driverAppUMREX;
	private RemoteWebDriver driverChrome;

	private RemoteWebDriver driverUMREX;
	private RemoteWebDriver driverMobileApp;
	private RemoteWebDriver driverSelfcare;
	private RemoteWebDriver driverUMB;

	private RemoteWebDriver driverUMREXPORTAL;

	UMREXLoginLogoutFuncs umrexLoginFuncs;
	UMREXLoginLogoutFuncs loginFuncs;
	UMREXHomePage umrexHomePage;
	UMREXPrepaidRegistrationPage umrexPrepaidRegistrationPage;
	UMREXPrepaidTopUpFuncs umrexPrepaidTopUpFuncs;
	UMREXPortalTopUpReportsPage umrexPortalFuncs;
	MobileAppLoginFuncs mobileAppLoginFuncs;
	MobileAppUtilFuncs mobileAppUtilFuncs;
	ERECHARGEPORTALLoginLogoutPage erechargeportalLoginLogout;
	ERECHARGEPORTALTransactionsPage erechargePortalFuncs;
	UMBVerificationFuncs umbVerifyFuncs;
	SelfcareVerificationFuncs selfcareVerifyFuncs;

	ExtentTestNGITestListener ex;

	private static Logger logger = LogManager.getLogger(UMREXPrepaidTopUp.class);

	@BeforeClass
	public void initialiseObj() throws IOException {
		MainUtil.APPLICATION_NAME = "UMREX";
	}

	@Test(description = "UMREX Prepaid Top Up")
	@Parameters({"msisdn","topUpAmount","planName","identificationType"})
	public void UMREXPrepaidTopUp(String msisdn, String topUpAmount, String planName,String identificationType) {

		String planType = "Prepaid";
		String mainBalance;

		try {
			/*######################################################################################################################################################*/
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("TOPUP_AMOUNT", topUpAmount);
			MainUtil.dictionary.put("PLAN_NAME", planName);
			MainUtil.dictionary.put("ACCOUNT_TYPE", planType);
			MainUtil.dictionary.put("IDENTIFICATION_TYPE", identificationType);

			RemoteWebDriver driverUMB2;
			/*######################################################################################################################################################*/


			ExtentTestNGITestListener.createNode("Perform UMREX Prepaid Top Up");

			driverUMB = getDriver("UMB");
			umbVerifyFuncs=new UMBVerificationFuncs(driverUMB);
			mainBalance = umbVerifyFuncs.getBalance(planType, "BalanceCheck");
			MainUtil.dictionary.put("CURRENT_MAIN_BALANCE", mainBalance);

			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
			MainUtil.dictionary.put("PLAN_NAME", planName);

			//*Launch UMREX*//*
			driverUMREX = getDriver("UMREX", "Android");
			loginFuncs = new UMREXLoginLogoutFuncs(driverUMREX);
			umrexHomePage = new UMREXHomePage(driverUMREX);
			umrexPrepaidRegistrationPage = new UMREXPrepaidRegistrationPage(driverUMREX);
			umrexPrepaidTopUpFuncs = new UMREXPrepaidTopUpFuncs(driverUMREX);

			loginFuncs.loginUMREX();
			umrexHomePage.clickPrepaidButton();
			umrexPrepaidRegistrationPage.clickTopUpButton();

			boolean prepaidTopUpActionSuccess=umrexPrepaidTopUpFuncs.doPrepaidTopUp();

			Assert.assertTrue(prepaidTopUpActionSuccess,"UMREX Prepaid Top Up Unsuccessful");
			loginFuncs.logoutUMREX();

			// Verification Steps
			/*
			ExtentTestNGITestListener.createNode("eRecharge Portal Verification");
			erechargePortalFuncs = new ERECHARGEPORTALTransactionsPage(driverChrome);
			erechargePortalFuncs.verifyTopUp(MainUtil.dictionary.get("MSISDN"),"RM "+MainUtil.dictionary.get("TOPUP_AMOUNT")+".00",MainUtil.dictionary.get("EXP_TRANSACTION_ID"),MainUtil.dictionary.get("EXP_TRANSACTION_DATE_TIME"),MainUtil.dictionary.get("EXP_CLOSING_BALANCE"));
*/
			ExtentTestNGITestListener.createNode("UMREX Portal Verification");

			driverUMREXPORTAL = getDriver("UMREXPORTAL");
			umrexPortalFuncs = new UMREXPortalTopUpReportsPage(driverUMREXPORTAL);
			umrexPortalFuncs.verifyTopUpReport(MainUtil.dictionary.get("MSISDN"),MainUtil.dictionary.get("TOPUP_AMOUNT")+".00",MainUtil.dictionary.get("EXP_TRANSACTION_ID"),MainUtil.dictionary.get("EXP_TRANSACTION_DATE_TIME"),"Success");

			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

			ExtentTestNGITestListener.createNode("UMB Verification");

			driverUMB2 = getDriver("UMB");
			umbVerifyFuncs=new UMBVerificationFuncs(driverUMB2);
			umbVerifyFuncs.umbVerification("PREPAID","PLAN");

			ExtentTestNGITestListener.createNode("Selfcare Verification");

			driverSelfcare = getDriver("SELFCARE");
			selfcareVerifyFuncs = new SelfcareVerificationFuncs(driverSelfcare);
			selfcareVerifyFuncs.verifySelfcare("PREPAID");

			ExtentTestNGITestListener.createNode("Mobile App Verification");

			driverMobileApp = getDriver("MOBILEAPP", "Android");
			mobileAppLoginFuncs = new MobileAppLoginFuncs(driverMobileApp);
			mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);

			mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));
			mobileAppUtilFuncs.verifyMobileApp("TOPUP_ONLY_PREPAID");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while doing prepaid top up  :" + e);
		}
	}

	@Test(description = "UMREX Prepaid Top Up Multiple TopUp Within 5 Minutes")
	@Parameters({"msisdn","topUpAmount","planName","errorMessage"})
	public void UMREXPrepaidTopUpMultipleTopUpWithinFiveMinutes(String msisdn, String topUpAmount, String planName, String sErrorMessage) {

		String planType = "Prepaid";
		String mainBalance;

		try {
			/*######################################################################################################################################################*/
			driverAppUMREX = getDriver("UMREX", "Android");

			driverChrome = getDriver("chrome");

			umrexLoginFuncs = new UMREXLoginLogoutFuncs(driverAppUMREX);
			umrexHomePage = new UMREXHomePage(driverAppUMREX);
			umrexPrepaidRegistrationPage = new UMREXPrepaidRegistrationPage(driverAppUMREX);
			umrexPrepaidTopUpFuncs = new UMREXPrepaidTopUpFuncs(driverAppUMREX);
			umbVerifyFuncs = new UMBVerificationFuncs(driverChrome);
			/*######################################################################################################################################################*/

			ExtentTestNGITestListener.createNode("UMREX Prepaid Top Up");
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("TOPUP_AMOUNT", topUpAmount);
			MainUtil.dictionary.put("PLAN_NAME", planName);

			mainBalance = umbVerifyFuncs.getBalance(planType, "BalanceCheck");
			MainUtil.dictionary.put("CURRENT_MAIN_BALANCE", mainBalance);

			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
			MainUtil.dictionary.put("PLAN_NAME", planName);

			umrexLoginFuncs.loginUMREX();
			umrexHomePage.clickPrepaidButton();
			umrexPrepaidRegistrationPage.clickTopUpButton();
			boolean prepaidTopUpActionSuccess=umrexPrepaidTopUpFuncs.doPrepaidTopUp();

			Assert.assertTrue(prepaidTopUpActionSuccess,"UMREX Prepaid 1st Top Up Unsuccessful");

			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
			String sMainBalanceAfterFirstTopUp=MainUtil.dictionary.get("MAIN_BALANCE");

			boolean blnSecondTopUp=umrexPrepaidTopUpFuncs.doPrepaidTopUpActionForErrorMessage(sErrorMessage);
			Assert.assertTrue(blnSecondTopUp,"UMREX Prepaid 2nd Top Up within 5 mins - No Error Displayed");
			umrexLoginFuncs.logoutUMREX();

			// Verification Steps
			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
			String sMainBalanceAfterSecondFailedTopUp=MainUtil.dictionary.get("MAIN_BALANCE");

			Assert.assertEquals(sMainBalanceAfterFirstTopUp,sMainBalanceAfterSecondFailedTopUp,"UMREX Prepaid 2nd Top Up within 5 mins - Main Balance as expected.2nd Top Up Balance not credited to account");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while doing prepaid top up multiple times within 5 minutes :" + e);
		}
	}

	@Test(description = "UMREX Prepaid Top Up")
	@Parameters({"msisdn","topUpAmount","errorMessage"})
	public void UMREXPrepaidTopUpDeactivatedMSISDN(String msisdn, String topUpAmount, String sErrorMessage) {

		/*String planType = "Prepaid";
		String mainBalance;*/

		try {
			/*######################################################################################################################################################*/
			driverAppUMREX = getDriver("UMREX", "Android");
			driverAppMobileApp = getDriver("MOBILEAPP", "Android");
			driverChrome = getDriver("chrome");

			umrexLoginFuncs = new UMREXLoginLogoutFuncs(driverAppUMREX);
			umrexHomePage = new UMREXHomePage(driverAppUMREX);
			umrexPrepaidRegistrationPage = new UMREXPrepaidRegistrationPage(driverAppUMREX);
			umrexPrepaidTopUpFuncs = new UMREXPrepaidTopUpFuncs(driverAppUMREX);
			/*######################################################################################################################################################*/

			ExtentTestNGITestListener.createNode("UMREX Prepaid Top Up");
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("TOPUP_AMOUNT", topUpAmount);

			/*mainBalance = umbVerifyFuncs.getBalance(planType, "BalanceCheck");
			MainUtil.dictionary.put("CURRENT_MAIN_BALANCE", mainBalance);

			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
			MainUtil.dictionary.put("PLAN_NAME", planName);*/

			umrexLoginFuncs.loginUMREX();
			umrexHomePage.clickPrepaidButton();
			umrexPrepaidRegistrationPage.clickTopUpButton();
			boolean prepaidTopUpActionError=umrexPrepaidTopUpFuncs.doPrepaidTopUpActionForErrorMessage(sErrorMessage);
			Assert.assertTrue(prepaidTopUpActionError,"UMREX Prepaid Top Up Unsuccessful");
			umrexLoginFuncs.logoutUMREX();

			getTest().get().pass("Top Up Unsuccessful",
					ExtentScreenCapture.captureSrceenPass("Top Up Unsuccessful", driver));

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while doing prepaid top up for deactivated msisdn :" + e);
		}
	}

/*

	@AfterClass
	public void teardown() throws IOException {
		erechargeportalLoginLogout.logout();
	}
*/



}
