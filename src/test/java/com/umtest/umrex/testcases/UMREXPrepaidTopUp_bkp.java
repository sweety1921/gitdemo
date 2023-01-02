package com.umtest.umrex.testcases;

import com.umtest.erechargeportal.pageobject.ERECHARGEPORTALLoginLogoutPage;
import com.umtest.erechargeportal.pageobject.ERECHARGEPORTALTransactionsPage;
import com.umtest.mobileapps.pagefunction.MobileAppLoginFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppUtilFuncs;
import com.umtest.selfcare.pagefunction.SelfcareVerificationFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.listener.ExtentTestNGITestListener;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;
import com.umtest.umrex.pagefunction.UMREXLoginLogoutFuncs;
import com.umtest.umrex.pagefunction.UMREXPrepaidTopUpFuncs;
import com.umtest.umrex.pageobject.UMREXHomePage;
import com.umtest.umrex.pageobject.UMREXPrepaidRegistrationPage;
import com.umtest.umrexportal.pageobject.UMREXPortalTopUpReportsPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class UMREXPrepaidTopUp_bkp extends DriverFactory {
	private RemoteWebDriver driver;
	private RemoteWebDriver driverChrome;

	UMBVerificationFuncs umbVerification;
	UMREXLoginLogoutFuncs loginFuncs;
	UMREXHomePage homePage;
	UMREXPrepaidRegistrationPage prepaidRegistrationPage;
	UMREXPrepaidTopUpFuncs prepaidTopUpFuncs;
	UMREXPortalTopUpReportsPage umrexPortal;
	ERECHARGEPORTALLoginLogoutPage erechargeportalLoginLogout;
	ERECHARGEPORTALTransactionsPage erechargeportal;

	ExtentTestNGITestListener ex;

	private static Logger logger = LogManager.getLogger(UMREXPrepaidTopUp_bkp.class);

	/*@BeforeClass
	public void initialiseObj() throws IOException {


		driverChrome = getDriver("chrome");
		//erechargeportalLoginLogout=new ERECHARGEPORTALLoginLogoutPage(driverChrome);
		erechargeportalVerification = new ERECHARGEPORTALTransactionsPage(driverChrome) ;
		umrexPortalTopUpReports = new UMREXPortalTopUpReportsPage(driverChrome);
		MainUtil.APPLICATION_NAME = "UMREX";
	}*/

	/*@Test(description = "UMREX Prepaid Top Up")
	@Parameters({"msisdn","topUpAmount","planName"})
	public void UMREXPrepaidTopUpWithERechargePortal(String msisdn, String topUpAmount, String planName) {

		String planType = "Prepaid";
		String mainBalance;

		try {
			ExtentTestNGITestListener.createNode("UMREX Prepaid Top Up");

			//ExtentTestNGITestListener.createNode("eRecharge Portal Verification");
		//	driverChrome = getDriver("chrome");

			umrexPortalTopUpReports=new UMREXPortalTopUpReportsPage(driverChrome);
			umrexPortalTopUpReports.verifyTopUpReport("601128568044","100.00","21438584","2020-10-05 12:12:53","Success");

			erechargeportalVerification.verifyTopUp("60183102596","RM 159.00","21438828","2020-10-28 22:49:40","RM 3,955.11");
			//erechargeportalTransactions.eRechargePortalVerificationTopUp("60183102596","RM 159.00","21438828","2020-10-15 10:50:11","RM 6,402.90");




		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid top up  :" + e);
		}
	}*/

	@BeforeClass
	public void initialiseObj() throws IOException {

		driver = getDriver("UMREX", "Android");
		driverChrome = getDriver("chrome");
		umbVerification = new UMBVerificationFuncs(driverChrome);
		loginFuncs = new UMREXLoginLogoutFuncs(driver);
		homePage = new UMREXHomePage(driver);
		prepaidRegistrationPage = new UMREXPrepaidRegistrationPage(driver);
		prepaidTopUpFuncs = new UMREXPrepaidTopUpFuncs(driver);
		erechargeportal = new ERECHARGEPORTALTransactionsPage(driverChrome) ;
		umrexPortal = new UMREXPortalTopUpReportsPage(driverChrome);

		MainUtil.APPLICATION_NAME = "UMREX";
	}

	@Test(description = "UMREX Prepaid Top Up")
	@Parameters({"msisdn","topUpAmount","planName"})
	public void UMREXPrepaidTopUp(String msisdn, String topUpAmount, String planName) {

		String planType = "Prepaid";
		String mainBalance;

		try {
			
			ExtentTestNGITestListener.createNode("UMREX Prepaid Top Up");
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("TOPUP_AMOUNT", topUpAmount);
			MainUtil.dictionary.put("PLAN_NAME", planName);

			mainBalance = umbVerification.getBalance(planType, "BalanceCheck");
			MainUtil.dictionary.put("CURRENT_MAIN_BALANCE", mainBalance);

			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
			MainUtil.dictionary.put("PLAN_NAME", planName);

			loginFuncs.loginUMREX();
			homePage.clickPrepaidButton();
			prepaidRegistrationPage.clickTopUpButton();
			boolean prepaidTopUpActionSuccess=prepaidTopUpFuncs.doPrepaidTopUp();
			Assert.assertTrue(prepaidTopUpActionSuccess,"UMREX Prepaid Top Up Unsuccessful");
			loginFuncs.logoutUMREX();

			// Verification Steps
			ExtentTestNGITestListener.createNode("eRecharge Portal Verification");
			driverChrome = getDriver("chrome");
			erechargeportal.verifyTopUp(MainUtil.dictionary.get("MSISDN"),"RM "+MainUtil.dictionary.get("TOPUP_AMOUNT"),MainUtil.dictionary.get("EXP_TRANSACTION_ID"),MainUtil.dictionary.get("EXP_TRANSACTION_DATE_TIME"),MainUtil.dictionary.get("EXP_CLOSING_BALANCE"));

			ExtentTestNGITestListener.createNode("UMREX Portal Verification");
			driverChrome = getDriver("chrome");
		//	umrexPortalTopUpReports=new UMREXPortalTopUpReportsPage(driverChrome);
			umrexPortal.verifyTopUpReport(MainUtil.dictionary.get("MSISDN"),MainUtil.dictionary.get("TOPUP_AMOUNT"),MainUtil.dictionary.get("EXP_TRANSACTION_ID"),MainUtil.dictionary.get("EXP_TRANSACTION_DATE_TIME"),"Success");

			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
			ExtentTestNGITestListener.createNode("UMB Verification");
			driverChrome = getDriver("chrome");
			UMBVerificationFuncs umbVerify = new UMBVerificationFuncs(driverChrome);
			umbVerify.umbVerification("PREPAID","PLAN");

			ExtentTestNGITestListener.createNode("Selfcare Verification");
			driverChrome = getDriver("chrome");
			SelfcareVerificationFuncs selfcareVerify = new SelfcareVerificationFuncs(driverChrome);
			selfcareVerify.verifySelfcare("PREPAID");

			ExtentTestNGITestListener.createNode("Mobile App Verification");
			driver = getDriver("MOBILEAPP", "Android");
			MobileAppLoginFuncs mobileAppLoginFuncs = new MobileAppLoginFuncs(driver);
			mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

			MobileAppUtilFuncs mobileAppUtilFuncs = new MobileAppUtilFuncs(driver);
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
			ExtentTestNGITestListener.createNode("UMREX Prepaid Top Up");
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("TOPUP_AMOUNT", topUpAmount);
			MainUtil.dictionary.put("PLAN_NAME", planName);

			mainBalance = umbVerification.getBalance(planType, "BalanceCheck");
			MainUtil.dictionary.put("CURRENT_MAIN_BALANCE", mainBalance);

			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
			MainUtil.dictionary.put("PLAN_NAME", planName);

			loginFuncs.loginUMREX();
			homePage.clickPrepaidButton();
			prepaidRegistrationPage.clickTopUpButton();
			boolean prepaidTopUpActionSuccess=prepaidTopUpFuncs.doPrepaidTopUp();

			Assert.assertTrue(prepaidTopUpActionSuccess,"UMREX Prepaid Top Up Unsuccessful");

			boolean blnSecondTopUp=prepaidTopUpFuncs.doPrepaidTopUpActionForErrorMessage(sErrorMessage);

			Assert.assertTrue(blnSecondTopUp,"UMREX Prepaid 2nd Top Up Unsuccessful");

			loginFuncs.logoutUMREX();

		/*	// Verification Steps
			ExtentTestNGITestListener.createNode("eRecharge Portal Verification");
			driverChrome = getDriver("chrome");
			erechargeportal.verifyTopUp(MainUtil.dictionary.get("MSISDN"),"RM "+MainUtil.dictionary.get("TOPUP_AMOUNT"),MainUtil.dictionary.get("EXP_TRANSACTION_ID"),MainUtil.dictionary.get("EXP_TRANSACTION_DATE_TIME"),MainUtil.dictionary.get("EXP_CLOSING_BALANCE"));

			ExtentTestNGITestListener.createNode("UMREX Portal Verification");
			driverChrome = getDriver("chrome");
			//	umrexPortalTopUpReports=new UMREXPortalTopUpReportsPage(driverChrome);
			umrexPortal.verifyTopUpReport(MainUtil.dictionary.get("MSISDN"),MainUtil.dictionary.get("TOPUP_AMOUNT"),MainUtil.dictionary.get("EXP_TRANSACTION_ID"),MainUtil.dictionary.get("EXP_TRANSACTION_DATE_TIME"),"Success");

			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
			ExtentTestNGITestListener.createNode("UMB Verification");
			driverChrome = getDriver("chrome");
			UMBVerificationFuncs umbVerify = new UMBVerificationFuncs(driverChrome);
			umbVerify.umbVerification("PREPAID","PLAN");

			ExtentTestNGITestListener.createNode("Selfcare Verification");
			driverChrome = getDriver("chrome");
			SelfcareVerificationFuncs selfcareVerify = new SelfcareVerificationFuncs(driverChrome);
			selfcareVerify.verifySelfcare("PREPAID");

			ExtentTestNGITestListener.createNode("Mobile App Verification");
			driver = getDriver("MOBILEAPP", "Android");
			MobileAppLoginFuncs mobileAppLoginFuncs = new MobileAppLoginFuncs(driver);
			mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

			MobileAppUtilFuncs mobileAppUtilFuncs = new MobileAppUtilFuncs(driver);
			mobileAppUtilFuncs.verifyMobileApp("TOPUP_ONLY_PREPAID");*/

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while doing prepaid top up multiple times within 5 minutes :" + e);
		}
	}

	@Test(description = "UMREX Prepaid Top Up")
	@Parameters({"msisdn","topUpAmount","planName"})
	public void UMREXPrepaidTopUpDeactivatedMSISDN(String msisdn, String topUpAmount, String planName) {

		String planType = "Prepaid";
		String mainBalance;

		try {
			ExtentTestNGITestListener.createNode("UMREX Prepaid Top Up");
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("TOPUP_AMOUNT", topUpAmount);
			MainUtil.dictionary.put("PLAN_NAME", planName);

			mainBalance = umbVerification.getBalance(planType, "BalanceCheck");
			MainUtil.dictionary.put("CURRENT_MAIN_BALANCE", mainBalance);

			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
			MainUtil.dictionary.put("PLAN_NAME", planName);

			loginFuncs.loginUMREX();
			homePage.clickPrepaidButton();
			prepaidRegistrationPage.clickTopUpButton();
			boolean prepaidTopUpActionSuccess=prepaidTopUpFuncs.doPrepaidTopUp();
			Assert.assertTrue(prepaidTopUpActionSuccess,"UMREX Prepaid Top Up Unsuccessful");
			loginFuncs.logoutUMREX();

			// Verification Steps


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while doing prepaid top up for deactivated msisdn :" + e);
		}
	}


	@AfterClass
	public void teardown() throws IOException {
		erechargeportalLoginLogout.logout();
	}



}
