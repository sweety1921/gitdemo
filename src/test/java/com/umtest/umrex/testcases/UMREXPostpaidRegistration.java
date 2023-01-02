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
import com.umtest.umrex.pagefunction.UMREXPostpaidRegistrationFuncs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class UMREXPostpaidRegistration extends DriverFactory {
	private RemoteWebDriver driver;
	private RemoteWebDriver driverChrome;

	private RemoteWebDriver driverUMREX;
	private RemoteWebDriver driverMobileApp;
	private RemoteWebDriver driverSelfcare;
	private RemoteWebDriver driverUMB;
	private RemoteWebDriver driverUMREXPORTAL;

	UMREXLoginLogoutFuncs loginFuncs;
	UMREXPostpaidRegistrationFuncs postpaidFuncs;
	UMBVerificationFuncs umbFuncs;
	SelfcareVerificationFuncs selfcareFuncs;
	MobileAppLoginFuncs mobileApploginFuncs;
	MobileAppUtilFuncs mobileAppUtilFuncs;

	ExtentTestNGITestListener ex;

	private static Logger logger = LogManager.getLogger(UMREXPostpaidRegistration.class);

	@BeforeClass
	public void initialiseObj() throws IOException {

		driver = getDriver("UMREX", "Android");
		loginFuncs = new UMREXLoginLogoutFuncs(driver);
		postpaidFuncs = new UMREXPostpaidRegistrationFuncs(driver);

		MainUtil.APPLICATION_NAME = "UMREX";
	}

	@Test(description = "UMREX Postpaid Registration")
	@Parameters({"planName", "accountType", "testDataFile"})
	public void UMREXRegistrationPostpaid(String planName, String accountType, String testDataFile) {

		try {
			driver = getDriver("UMREX", "Android");
			loginFuncs = new UMREXLoginLogoutFuncs(driver);
			//postpaidFuncs = new UMREXPostpaidRegistrationFuncs(driver);

			ExtentTestNGITestListener.createNode("UMREX Postpaid Registration");
			MainUtil.dictionary.put("PLAN_NAME", planName);
			MainUtil.dictionary.put("REGISTRATION_TYPE", "PLAN");
			MainUtil.dictionary.put("ACCOUNT_TYPE", accountType);
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

			//*Perform Postpaid Registration*//*
			postpaidFuncs = new UMREXPostpaidRegistrationFuncs(driverUMREX);
			boolean statusFlag = postpaidFuncs.doPostpaidRegistration(MainUtil.dictionary.get("REGISTRATION_TYPE"),"MALAYSIAN");
			loginFuncs.logoutUMREX();


			Thread.sleep(30000);
		//	boolean statusFlag=true;
		//	MainUtil.dictionary.put("MSISDN","60182146964");
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

					driverMobileApp = getDriver("MOBILEAPP", "Android");
					//mobileApploginFuncs = new MobileAppLoginFuncs(driverMobileApp);
					//mobileApploginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

					mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
					//mobileAppUtilFuncs.verifyMobileApp(MainUtil.dictionary.get("REGISTRATION_TYPE")+"_POSTPAID");
					mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TEST_DATA_FILE"));
			}


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid registration  :" + e);
		}
	}

	@Test(description = "UMREX Postpaid Registration MultiLine")
	@Parameters({"planName","accountType","testDataFile"})
	public void UMREXRegistrationPostpaid_Multiline(String planName,String accountType,String testDataFile) {

		try {

			ExtentTestNGITestListener.createNode("UMREX Postpaid multiline Registration");
			MainUtil.dictionary.put("PLAN_NAME", planName);
			MainUtil.dictionary.put("REGISTRATION_TYPE", "PLAN");
			MainUtil.dictionary.put("ACCOUNT_TYPE", accountType);
			MainUtil.dictionary.put("TEST_DATA_FILE", testDataFile);

			if (System.getProperty("simType") == null || System.getProperty("simType").equalsIgnoreCase("PHYSICAL")) {
				MainUtil.dictionary.put("SIM_TYPE", "PHYSICAL");
			} else {
				MainUtil.dictionary.put("SIM_TYPE", "DUMMY");
			}

			//*Launch UMREX*//*
//			driverUMREX = getDriver("UMREX", "Android");
//			loginFuncs = new UMREXLoginLogoutFuncs(driverUMREX);
//			loginFuncs.loginUMREX();

			//*Perform Postpaid Registration - Multiline*//*
//			postpaidFuncs = new UMREXPostpaidRegistrationFuncs(driverUMREX);
//			boolean statusFlag = postpaidFuncs.doPostpaidRegistration_multiline(MainUtil.dictionary.get("REGISTRATION_TYPE"),"MALAYSIAN");
//			loginFuncs.logoutUMREX();

			Thread.sleep(60000);
			MainUtil.dictionary.put("MSISDN1","60182146982");
			MainUtil.dictionary.put("MSISDN2","60182146983");
			MainUtil.dictionary.put("SIM_NO1","896018120157414394");
			MainUtil.dictionary.put("SIM_NO2","896018120157414395");
			String[] arrplan_name = MainUtil.dictionary.get("PLAN_NAME").split(";");
			String[] testData_File = MainUtil.dictionary.get("TEST_DATA_FILE").split(";");
			boolean statusFlag =true;
			System.out.println(arrplan_name.length);
			int r=0;
			for (int q = 1; q<arrplan_name.length+1; q++) {

				MainUtil.dictionary.put("MSISDN", MainUtil.dictionary.get("MSISDN"+q));
				MainUtil.dictionary.put("PLAN_NAME", MainUtil.dictionary.get("PLAN_NAME"+q));
				MainUtil.dictionary.put("SIM_NO", MainUtil.dictionary.get("SIM_NO"+q));
				MainUtil.dictionary.put("TEST_DATA_FILE", testData_File[r]);

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

					driverMobileApp = getDriver("MOBILEAPP", "Android");
					//mobileApploginFuncs = new MobileAppLoginFuncs(driverMobileApp);
					//mobileApploginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

					mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
					//mobileAppUtilFuncs.verifyMobileApp(MainUtil.dictionary.get("REGISTRATION_TYPE")+"_POSTPAID");
					mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TEST_DATA_FILE"));
				}
				r++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing postpaid registration  :" + e);
		}
	}



	@Test(description = "UMREX Postpaid Broadband Registration")
	@Parameters({"planName","accountType","testDataFile"})
	public void UMREXRegistrationPostpaid_Broadband(String planName,String accountType,String testDataFile) {

		try {

			ExtentTestNGITestListener.createNode("UMREX Postpaid Broadband Registration");
			MainUtil.dictionary.put("PLAN_NAME", planName);
			MainUtil.dictionary.put("REGISTRATION_TYPE", "PLAN");
			MainUtil.dictionary.put("ACCOUNT_TYPE", accountType);
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

			//*Perform Postpaid Registration - Broadband*//*
			postpaidFuncs = new UMREXPostpaidRegistrationFuncs(driverUMREX);
			boolean statusFlag = postpaidFuncs.doPostpaidRegistration_Broadband(MainUtil.dictionary.get("REGISTRATION_TYPE"),"MALAYSIAN");
			loginFuncs.logoutUMREX();

			Thread.sleep(60000);

			if (statusFlag == true) {

				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

				ExtentTestNGITestListener.createNode("UMB Verification");

				driverUMB = getDriver("UMB");
				umbFuncs=new UMBVerificationFuncs(driverUMB);
				umbFuncs.umbVerification("POSTPAID", "PLAN");

			/*	ExtentTestNGITestListener.createNode("Selfcare Verification");

				driverSelfcare = getDriver("SELFCARE");
				selfcareFuncs = new SelfcareVerificationFuncs(driverSelfcare);
				selfcareFuncs.verifySelfcare("POSTPAID");*/

				ExtentTestNGITestListener.createNode("Mobile App Verification");

				driverMobileApp = getDriver("MOBILEAPP", "Android");
				//mobileApploginFuncs = new MobileAppLoginFuncs(driverMobileApp);
				//mobileApploginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

				mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
				//mobileAppUtilFuncs.verifyMobileApp(MainUtil.dictionary.get("REGISTRATION_TYPE")+"_POSTPAID");
				mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TEST_DATA_FILE"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid registration  :" + e);
		}
	}

	@Test(description = "UMREX Postpaid Phone bundle Registration")
	@Parameters({"planName","IMEI","Promotionname","accountType","testDataFile"})
	public void UMREXRegistrationPostpaid_Phonebundle(String planName,String IMEI, String Promotionname, String accountType, String testDataFile) {

		try {

			ExtentTestNGITestListener.createNode("UMREX Postpaid Registration Phone bundle");

			MainUtil.dictionary.put("PLAN_NAME", planName);
			MainUtil.dictionary.put("REGISTRATION_TYPE", "PLAN");
			MainUtil.dictionary.put("IMEI", IMEI);
			MainUtil.dictionary.put("PROMOTION_NAME", Promotionname);
			MainUtil.dictionary.put("ACCOUNT_TYPE", accountType);
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

			//*Perform Postpaid Registration - Phone Bundle*//*
			postpaidFuncs = new UMREXPostpaidRegistrationFuncs(driverUMREX);
			boolean statusFlag = postpaidFuncs.doPostpaidPhonebundleRegistration(MainUtil.dictionary.get("PROMOTION_NAME"),MainUtil.dictionary.get("REGISTRATION_TYPE"),"MALAYSIAN");
			loginFuncs.logoutUMREX();

			Thread.sleep(60000);
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

				driverMobileApp = getDriver("MOBILEAPP", "Android");
				//mobileApploginFuncs = new MobileAppLoginFuncs(driverMobileApp);
				//mobileApploginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

				mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
				//mobileAppUtilFuncs.verifyMobileApp(MainUtil.dictionary.get("REGISTRATION_TYPE")+"_POSTPAID");
				mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TEST_DATA_FILE"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Postpaid phone bundle registration  :" + e);
		}
	}

	@Test(description = "UMREX Postpaid Registration Existing Line")
	@Parameters({"planName","existingmsisdn","accountType","testDataFile"})
	public void UMREXRegistrationPostpaid_Existingline(String planName,String existingmsisdn,String accountType,String testDataFile) {

		try {

			ExtentTestNGITestListener.createNode("UMREX Postpaid Registration for existing line");
			MainUtil.dictionary.put("NEW_PLAN_NAME", planName);
			MainUtil.dictionary.put("EXISTINGMSISDN", existingmsisdn);
			MainUtil.dictionary.put("REGISTRATION_TYPE", "PLAN");
			MainUtil.dictionary.put("ACCOUNT_TYPE", accountType);
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

			//*Perform Postpaid Registration - Existing Line*//*
			postpaidFuncs = new UMREXPostpaidRegistrationFuncs(driverUMREX);
			boolean statusFlag = postpaidFuncs.doPostpaidRegistration_Existingline(MainUtil.dictionary.get("REGISTRATION_TYPE"),"MALAYSIAN");
			loginFuncs.logoutUMREX();

			Thread.sleep(60000);

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

				driverMobileApp = getDriver("MOBILEAPP", "Android");
				//mobileApploginFuncs = new MobileAppLoginFuncs(driverMobileApp);
				//mobileApploginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

				mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
				//mobileAppUtilFuncs.verifyMobileApp(MainUtil.dictionary.get("REGISTRATION_TYPE")+"_POSTPAID");
				mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TEST_DATA_FILE"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid registration  :" + e);
		}
	}

	@Test(description = "UMREX Postpaid Phone bundle Registration")
	@Parameters({"planName","IMEI","Promotionname","existingmsisdn","accountType","testDataFile"})
	public void UMREXPostpaidPhonebundle_Existingline(String planName,String IMEI, String Promotionname, String existingmsisdn, String accountType, String testDataFile) {

		try {

			ExtentTestNGITestListener.createNode("UMREX Postpaid Registration Phone bundle");
			MainUtil.dictionary.put("PLAN_NAME", planName);
			MainUtil.dictionary.put("REGISTRATION_TYPE", "PLAN");
			MainUtil.dictionary.put("IMEI", IMEI);
			MainUtil.dictionary.put("PROMOTION_NAME", Promotionname);
			MainUtil.dictionary.put("EXISTINGMSISDN", existingmsisdn);
			MainUtil.dictionary.put("ACCOUNT_TYPE", accountType);
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

			//*Perform Postpaid Registration - Phone Bundle Existing Line*//*
			postpaidFuncs = new UMREXPostpaidRegistrationFuncs(driverUMREX);
			boolean statusFlag = postpaidFuncs.doPostpaidPhonebundleExistingline(MainUtil.dictionary.get("PROMOTION_NAME"),MainUtil.dictionary.get("REGISTRATION_TYPE"),"MALAYSIAN");
			loginFuncs.logoutUMREX();

			Thread.sleep(60000);
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

				driverMobileApp = getDriver("MOBILEAPP", "Android");
				//mobileApploginFuncs = new MobileAppLoginFuncs(driverMobileApp);
				//mobileApploginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

				mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
				//mobileAppUtilFuncs.verifyMobileApp(MainUtil.dictionary.get("REGISTRATION_TYPE")+"_POSTPAID");
				mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TEST_DATA_FILE"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Postpaid phone bundle registration  :" + e);
		}
	}

	@Test(description = "UMREX Postpaid Registration MultiLine Phone Bundle")
	@Parameters({"planName","IMEI","Promotionname","accountType","testDataFile"})
	public void UMREXRegistrationPostpaid_Multilinephonebundle(String planName,String IMEI, String Promotionname, String accountType, String testDataFile) {

		try {

			ExtentTestNGITestListener.createNode("UMREX Postpaid multiline Registration");
			MainUtil.dictionary.put("PLAN_NAME", planName);
			MainUtil.dictionary.put("REGISTRATION_TYPE", "PLAN");
			MainUtil.dictionary.put("PROMOTION_NAME", Promotionname);
			MainUtil.dictionary.put("IMEI", IMEI);
			MainUtil.dictionary.put("ACCOUNT_TYPE", accountType);
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

			//*Perform Postpaid Registration - Multi-line Phone Bundle*//*
			postpaidFuncs = new UMREXPostpaidRegistrationFuncs(driverUMREX);
			boolean statusFlag = postpaidFuncs.doPostpaidRegistration_multilinephonebundle(MainUtil.dictionary.get("PROMOTION_NAME"),MainUtil.dictionary.get("REGISTRATION_TYPE"),"MALAYSIAN");
			loginFuncs.logoutUMREX();

			Thread.sleep(90000);
			String[] arrplan_name = MainUtil.dictionary.get("PLAN_NAME").split(";");
			System.out.println(arrplan_name.length);

			for (int q = 1; q<arrplan_name.length+1; q++) {

				MainUtil.dictionary.put("MSISDN", MainUtil.dictionary.get("MSISDN"+q));
				MainUtil.dictionary.put("PLAN_NAME", MainUtil.dictionary.get("PLAN_NAME"+q));
				MainUtil.dictionary.put("SIM_NO", MainUtil.dictionary.get("SIM_NO"+q));

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

					driverMobileApp = getDriver("MOBILEAPP", "Android");
					//mobileApploginFuncs = new MobileAppLoginFuncs(driverMobileApp);
					//mobileApploginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

					mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
					//mobileAppUtilFuncs.verifyMobileApp(MainUtil.dictionary.get("REGISTRATION_TYPE")+"_POSTPAID");
					mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TEST_DATA_FILE"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid registration  :" + e);
		}
	}

	@Test(description = "UMREX Postpaid Max Line Check")
	@Parameters({"existingmsisdn"})
	public void UMREXPostpaid_Maxlinecheck(String existingmsisdn) {

		try {

			ExtentTestNGITestListener.createNode("UMREX Postpaid Maxline check");
			MainUtil.dictionary.put("EXISTINGMSISDN", existingmsisdn);
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

			//*Perform Postpaid Registration - Max Line Check*//*
			postpaidFuncs = new UMREXPostpaidRegistrationFuncs(driverUMREX);
			postpaidFuncs.doPostpaid_Maxlinecheck(MainUtil.dictionary.get("REGISTRATION_TYPE"),"MALAYSIAN");
			loginFuncs.logoutUMREX();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Maxline Check  :" + e);
		}
	}

}
