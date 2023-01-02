package com.umtest.umrex.testcases;

import java.io.IOException;

import com.umtest.crm.pagefunction.CRMLoginLogoutFuncs;
import com.umtest.crm.pagefunction.CRMRegistrationPrepaidFuncs;
import com.umtest.crm.pagefunction.CRMSearchCustomerFuncs;
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
import com.umtest.umrexportal.pagefunction.UMREXPortalFuncs;

public class UMREXPrepaidRegistration extends DriverFactory {

	private RemoteWebDriver driverUMREX;
	private RemoteWebDriver driverMobileApp;
	private RemoteWebDriver driverSelfcare;
	private RemoteWebDriver driverUMB;
	private RemoteWebDriver driverUMREXPORTAL;
	private RemoteWebDriver driverCRM;

	UMREXLoginLogoutFuncs loginFuncs;
	UMREXPrepaidRegistrationFuncs prepaidFuncs;
	UMBVerificationFuncs umbVerify;
	UMBVerificationFuncs umbFuncs;
	UMREXPortalFuncs umrexPortalVerify;
	MobileAppLoginFuncs mobileAppLoginFuncs;
	MobileAppUtilFuncs mobileAppUtilFuncs;
	SelfcareVerificationFuncs selfcareVerify;
	SelfcareVerificationFuncs selfcareFuncs;
	CRMLoginLogoutFuncs crmLoginLogOutFuncs;
	CRMRegistrationPrepaidFuncs crmRegistrationPrepaidFuncs;
	CRMSearchCustomerFuncs crmSearchCustomerFuncs;

	ExtentTestNGITestListener ex;

	private static Logger logger = LogManager.getLogger(UMREXPrepaidRegistration.class);

	@BeforeClass
	public void initialiseObj() throws IOException {
		MainUtil.APPLICATION_NAME = "UMREX";
	}

	@Test(description = "UMREX Prepaid Registration")
	@Parameters({"planName","customerIdentificationType","testDataFile"})
	public void UMREXRegistrationPrepaid(String planName,String customerIdentificationType, String testDataFile) {
		try {
			//#################################################################################################################################

			MainUtil.dictionary.put("PLAN_NAME", planName);
			MainUtil.dictionary.put("REGISTRATION_TYPE", "PLAN");
			MainUtil.dictionary.put("CUSTOMER_IDENTIFICATION_TYPE", customerIdentificationType);
			MainUtil.dictionary.put("TEST_DATA_FILE",testDataFile);

			if (System.getProperty("simType") == null || System.getProperty("simType").equalsIgnoreCase("PHYSICAL")) {
				MainUtil.dictionary.put("SIM_TYPE", "PHYSICAL");
			} else {
				MainUtil.dictionary.put("SIM_TYPE", "DUMMY");
			}

			//#################################################################################################################################
			ExtentTestNGITestListener.createNode("UMREX Prepaid Registration");

			//*Launch UMREX*//*
			driverUMREX = getDriver("UMREX", "Android");
			loginFuncs = new UMREXLoginLogoutFuncs(driverUMREX);
			loginFuncs.loginUMREX();

			//*Perform Prepaid Registration*//*
			prepaidFuncs = new UMREXPrepaidRegistrationFuncs(driverUMREX);
			boolean statusFlag = prepaidFuncs.doPrepaidRegistration(MainUtil.dictionary.get("REGISTRATION_TYPE"),MainUtil.dictionary.get("CUSTOMER_IDENTIFICATION_TYPE"));
			loginFuncs.logoutUMREX();

			//statusFlag=false;

			//MainUtil.dictionary.put("MSISDN","601139280482");

			if (statusFlag == true) {
				Thread.sleep(30000); // Mandatory wait time for Registration Details to update in UMREX PORTAL

				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

				/*Verify UMREX Portal*/
				ExtentTestNGITestListener.createNode("UMREX Portal Verfication");

				driverUMREXPORTAL = getDriver("UMREXPORTAL");
				umrexPortalVerify = new UMREXPortalFuncs(driverUMREXPORTAL);
				umrexPortalVerify.verifyPrepaidRegistration(MainUtil.dictionary.get("MSISDN"));

				/*Verify UMB*/
				ExtentTestNGITestListener.createNode("UMB Verification");

				driverUMB = getDriver("UMB");
				umbFuncs=new UMBVerificationFuncs(driverUMB);
				umbFuncs.umbVerification("PREPAID", "BUNDLE");

				/*Verify Selfcare*/
				ExtentTestNGITestListener.createNode("Selfcare Verification");

				driverSelfcare = getDriver("SELFCARE");
				selfcareFuncs = new SelfcareVerificationFuncs(driverSelfcare);
				selfcareFuncs.verifySelfcare("PREPAID");

				/*Verify Mobile App*/
				ExtentTestNGITestListener.createNode("Mobile App Verification");

				driverMobileApp = getDriver("MOBILEAPP", "Android");
				mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
				mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"),MainUtil.dictionary.get("ACCOUNT_TYPE"),MainUtil.dictionary.get("TEST_DATA_FILE"));

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid registration  :" + e);
		}
	}

	@Test(description = "UMREX Prepaid Registration And Topup")
	@Parameters({"planName","topupAmount","msisdn","simno"})
	public void UMREXRegistrationPrepaidTopup(String planName, String topupAmount, String msisdn, String simno) {

		try {

			ExtentTestNGITestListener.createNode("UMREX Prepaid Registration And Topup");
			MainUtil.dictionary.put("PLAN_NAME", planName);
			MainUtil.dictionary.put("TOPUP_AMOUNT", topupAmount);
			MainUtil.dictionary.put("REGISTRATION_TYPE", "TOPUP");
			MainUtil.dictionary.put("MSISDN",msisdn);
			MainUtil.dictionary.put("SIM_NO",simno);

			if (System.getProperty("simType") == null || System.getProperty("simType").equalsIgnoreCase("PHYSICAL")) {
				MainUtil.dictionary.put("SIM_TYPE", "PHYSICAL");
			} else {
				MainUtil.dictionary.put("SIM_TYPE", "DUMMY");
			}

			//*Launch UMREX*//*
			driverUMREX = getDriver("UMREX", "Android");
			loginFuncs = new UMREXLoginLogoutFuncs(driverUMREX);
			loginFuncs.loginUMREX();

			//*Perform Prepaid Registration*//*
			prepaidFuncs = new UMREXPrepaidRegistrationFuncs(driverUMREX);
			boolean statusFlag = prepaidFuncs.doPrepaidRegistration(MainUtil.dictionary.get("REGISTRATION_TYPE"),"MALAYSIAN");
			loginFuncs.logoutUMREX();
			Thread.sleep(60000);
			if (statusFlag) {

				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

				ExtentTestNGITestListener.createNode("UMREX Portal Verfication");

				driverUMREXPORTAL = getDriver("UMREXPORTAL");
				umrexPortalVerify = new UMREXPortalFuncs(driverUMREXPORTAL);
				umrexPortalVerify.verifyPrepaidRegistration(MainUtil.dictionary.get("MSISDN"));

				ExtentTestNGITestListener.createNode("Selfcare Verification");

				driverSelfcare = getDriver("SELFCARE");
				selfcareFuncs = new SelfcareVerificationFuncs(driverSelfcare);
				selfcareFuncs.verifySelfcare("PREPAID");

				ExtentTestNGITestListener.createNode("UMB Verification");

				driverUMB = getDriver("UMB");
				umbFuncs=new UMBVerificationFuncs(driverUMB);
				umbFuncs.umbVerification("PREPAID", "PLAN");

				ExtentTestNGITestListener.createNode("Mobile App Verification");

				driverMobileApp = getDriver("MOBILEAPP", "Android");
				mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
				mobileAppUtilFuncs.verifyMobileAppPrepaidTopUpOnly();
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while doing prepaid registration  :" + e);
		}
	}

	@Test(description = "UMREX Prepaid Registration - Max Line Check")
	@Parameters({"identificationType"})
	public void UMREXRegistrationPrepaidMaxLineCheck(String identificationType) {

		try {

			ExtentTestNGITestListener.createNode("UMREX Prepaid Registration Max Line Check");

			//*Launch UMREX*//*
			driverUMREX = getDriver("UMREX", "Android");
			loginFuncs = new UMREXLoginLogoutFuncs(driverUMREX);
			loginFuncs.loginUMREX();

			prepaidFuncs = new UMREXPrepaidRegistrationFuncs(driverUMREX);
			boolean statusFlag = prepaidFuncs.doPrepaidRegistrationMaxLineCheck(identificationType);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid registration  :" + e);
		}
	}


	@Test(description = "UMREX Prepaid Registration And Bundle Purchase")
	@Parameters({"planName","bundleName","topupAmount","accountType","testDataFile"})
	public void UMREXRegistrationPrepaidBundle(String planName, String bundleName, String topupAmount, String accountType, String testDataFile) {

		try {
			//######################################################################################################

			MainUtil.dictionary.put("PLAN_NAME", planName);
			MainUtil.dictionary.put("BUNDLE_NAME", bundleName);
			MainUtil.dictionary.put("TOPUP_AMOUNT", topupAmount);
			MainUtil.dictionary.put("REGISTRATION_TYPE", "BUNDLE");
			MainUtil.dictionary.put("ACCOUNT_TYPE",accountType);
			MainUtil.dictionary.put("TEST_DATA_FILE",testDataFile);

			MainUtil.dictionary.put("CUSTOMER_IDENTIFICATION_TYPE","MYKAD");

			if (System.getProperty("simType") == null || System.getProperty("simType").equalsIgnoreCase("PHYSICAL")) {
				MainUtil.dictionary.put("SIM_TYPE", "PHYSICAL");
			} else {
				MainUtil.dictionary.put("SIM_TYPE", "DUMMY");
			}

			ExtentTestNGITestListener.createNode("UMREX Prepaid Registration And Bundle Purchase");

			//*Launch UMREX*//*
			driverUMREX = getDriver("UMREX", "Android");
			loginFuncs = new UMREXLoginLogoutFuncs(driverUMREX);
			loginFuncs.loginUMREX();

			//*Perform Prepaid Registration*//*
		    prepaidFuncs = new UMREXPrepaidRegistrationFuncs(driverUMREX);
			boolean statusFlag = prepaidFuncs.doPrepaidRegistration(MainUtil.dictionary.get("REGISTRATION_TYPE"),MainUtil.dictionary.get("CUSTOMER_IDENTIFICATION_TYPE"));
			loginFuncs.logoutUMREX();
			Thread.sleep(60000);
//			MainUtil.dictionary.put("MSISDN", "601139291218");
//			boolean statusFlag =true;
			if (statusFlag == true) {

				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

				/*ExtentTestNGITestListener.createNode("UMREX Portal Verfication");

				driverUMREXPORTAL = getDriver("driverUMREXPORTAL");
				umrexPortalVerify = new UMREXPortalFuncs(driverUMREXPORTAL);
				umrexPortalVerify.verifyPrepaidRegistrationForPurchase(MainUtil.dictionary.get("MSISDN"));*/

				ExtentTestNGITestListener.createNode("Selfcare Verification");

				driverSelfcare = getDriver("SELFCARE");
				selfcareVerify = new SelfcareVerificationFuncs(driverSelfcare);
				selfcareVerify.verifySelfcare("PREPAID");

				ExtentTestNGITestListener.createNode("Mobile App Verification");

				driverMobileApp = getDriver("MOBILEAPP", "Android");
				mobileAppLoginFuncs = new MobileAppLoginFuncs(driverMobileApp);
				mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);

				mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));
				//mobileAppUtilFuncs.verifyMobileApp(MainUtil.dictionary.get("REGISTRATION_TYPE")+"_PREPAID");
				mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TEST_DATA_FILE"));

				ExtentTestNGITestListener.createNode("UMB Verification");
				driverUMB = getDriver("UMB");
				umbVerify = new UMBVerificationFuncs(driverUMB);
				umbVerify.umbVerification("PREPAID","BUNDLE");

				ExtentTestNGITestListener.createNode("ZSmart Verfication");
				driverCRM = getDriver("CRM");
				crmLoginLogOutFuncs = new CRMLoginLogoutFuncs(driverCRM);
				crmSearchCustomerFuncs = new CRMSearchCustomerFuncs(driverCRM);
				crmRegistrationPrepaidFuncs = new CRMRegistrationPrepaidFuncs(driverCRM);
				crmLoginLogOutFuncs.loginWCTCRM();
				String msisdn = MainUtil.dictionary.get("MSISDN");
				crmSearchCustomerFuncs.searchMSISDNAndNavigateToThreeSixtyDegreeView(msisdn,driverCRM);
				crmRegistrationPrepaidFuncs.verifyCustomerAndServiceDetailsZSmart(testDataFile, driverCRM);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid registration  :" + e);
		}
	}


}
