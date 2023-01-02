package com.umtest.mobileapps.testcases;

import com.umtest.mobileapps.pagefunction.MobileAppBundlePurchaseFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppLoginFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppUtilFuncs;
import com.umtest.selfcare.pagefunction.SelfcareVerificationFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.APIRequestLibrary;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.lib.PropertyHelper;
import com.umtest.testframe.listener.ExtentTestNGITestListener;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MobileAppBundlePurchase extends DriverFactory {
	
	public static RemoteWebDriver driverMobileApp;
	public static RemoteWebDriver driverSelfcare;
	public static RemoteWebDriver driverUMB;

	MobileAppLoginFuncs loginFuncs;
	MobileAppUtilFuncs mobileAppUtilFuncs;
	MobileAppBundlePurchaseFuncs bundlePurchaseFuncs;
	UMBVerificationFuncs umbFuncs;
	SelfcareVerificationFuncs selfcareFuncs;
	ExtentTestNGITestListener ex;

	APIRequestLibrary utilityAPI;

	private static Logger logger = LogManager.getLogger(MobileAppBundlePurchase.class);

	@BeforeClass
	public void initialiseObj() throws Exception {
		MainUtil.APPLICATION_NAME = "MOBILEAPP";
	}

	@Test(description = "Mobile App Bundle Purchase")
	@Parameters({"msisdn", "bundleName", "accountType", "testDataFile"})
	public void purchaseBundle(String msisdn, String bundleName, String accountType, String testDataFile) {
		try {

			/*######################################################################################################################################################*/

			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("BUNDLE_NAME", bundleName);
			MainUtil.dictionary.put("ACCOUNT_TYPE", accountType);
			MainUtil.dictionary.put("TEST_DATA_FILE", testDataFile);

			//*######################################################################################################################################################*//*

			if (accountType.equalsIgnoreCase("PREPAID")) {
				ExtentTestNGITestListener.createNode("Unsubscribe Bundle Purchase (if any)");

				driverUMB = getDriver("UMB");
				umbFuncs = new UMBVerificationFuncs(driverUMB);
				utilityAPI = new APIRequestLibrary();
				umbFuncs.doUnsubscribeAllBundles(MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("MSISDN"), driverUMB);
				utilityAPI.syncSubscriberBalanceAndExpiryDate(MainUtil.dictionary.get("MSISDN"));
			}

			ExtentTestNGITestListener.createNode("Perform Bundle Purchase - Mobile App");

			MainUtil.dictionary.put("BUNDLE_NAME", bundleName);

			String sBundleName = MainUtil.dictionary.get("BUNDLE_NAME");

			if (sBundleName.equalsIgnoreCase("MBWEEK")) {
				sBundleName = "MB-WEEK";
			}

			/*Launch Mobile App*/
			driverMobileApp = getDriver("MOBILEAPP", "Android");
			loginFuncs = new MobileAppLoginFuncs(driverMobileApp);
			loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

			/*Purchase Bundle*/
			System.out.println("<<<<<<<<<<<<<<<sBundleName" + sBundleName);
			bundlePurchaseFuncs = new MobileAppBundlePurchaseFuncs(driverMobileApp);
			boolean status = bundlePurchaseFuncs.doBundlePurchase(sBundleName);

			if (status) {
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
				MainUtil.dictionary.put("BUNDLE_NAME", bundleName);
				if (status && PropertyHelper.getProperties("MOBILEAPP_CHANNEL_VERIFICATION_FLAG").equals("true")) {
					ExtentTestNGITestListener.createNode("Mobile App Verification");

					driverMobileApp = getDriver("MOBILEAPP", "Android");
					mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
					mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TEST_DATA_FILE"));
				}

				if (status && PropertyHelper.getProperties("UMB_CHANNEL_VERIFICATION_FLAG").equals("true")) {
					ExtentTestNGITestListener.createNode("UMB Verification");

					driverUMB = getDriver("UMB");
					umbFuncs = new UMBVerificationFuncs(driverUMB);
					umbFuncs.umbVerification(MainUtil.dictionary.get("ACCOUNT_TYPE"), "BUNDLE");
				}

				if (status && PropertyHelper.getProperties("SELFCARE_CHANNEL_VERIFICATION_FLAG").equals("true")) {
					ExtentTestNGITestListener.createNode("Selfcare Verification");

					driverSelfcare = getDriver("SELFCARE");
					selfcareFuncs = new SelfcareVerificationFuncs(driverSelfcare);
					selfcareFuncs.verifySelfcare(MainUtil.dictionary.get("ACCOUNT_TYPE"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid bundle purchase  :" + e);
		}
	}

	@Test(description = "Mobile App Bundle Purchase - Booster")
	@Parameters({"msisdn", "bundleName", "accountType", "testDataFile"})
	public void PurchaseBooster(String msisdn, String newBundleName, String accountType, String testDataFile) {
		try {

			ExtentTestNGITestListener.createNode("Do Unsubscription - All Bundles");

			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("BUNDLE_NAME", newBundleName);
			MainUtil.dictionary.put("ACCOUNT_TYPE", accountType);
			MainUtil.dictionary.put("TEST_DATA_FILE", testDataFile);

			ExtentTestNGITestListener.createNode("Do Mobile App Booster Purchase");

			/*Launch Mobile App*/
			driverMobileApp = getDriver("MOBILEAPP", "Android");
			loginFuncs = new MobileAppLoginFuncs(driverMobileApp);
			loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

			MainUtil.dictionary.put("BUNDLE_NAME", newBundleName);

			String sBundleName = MainUtil.dictionary.get("BUNDLE_NAME");

			if (sBundleName.equalsIgnoreCase("MBWEEK")) {
				sBundleName = "MB-WEEK";
			}

			/*Purchase Bundle*/
			bundlePurchaseFuncs = new MobileAppBundlePurchaseFuncs(driverMobileApp);
			boolean status = bundlePurchaseFuncs.doBundlePurchase(sBundleName);

			if (status) {
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
				Thread.sleep(5000);
			}

			if (status && PropertyHelper.getProperties("MOBILEAPP_CHANNEL_VERIFICATION_FLAG").equals("true")) {
				ExtentTestNGITestListener.createNode("Mobile App Verification");

				driverMobileApp = getDriver("MOBILEAPP", "Android");
				mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
				mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TEST_DATA_FILE"));
			}

			if (status && PropertyHelper.getProperties("UMB_CHANNEL_VERIFICATION_FLAG").equals("true")) {
				ExtentTestNGITestListener.createNode("UMB Verification");

				driverUMB = getDriver("UMB");
				umbFuncs = new UMBVerificationFuncs(driverUMB);
				umbFuncs.umbVerification(MainUtil.dictionary.get("ACCOUNT_TYPE"), "BUNDLE");
			}

			if (status && PropertyHelper.getProperties("SELFCARE_CHANNEL_VERIFICATION_FLAG").equals("true")) {
				ExtentTestNGITestListener.createNode("Selfcare Verification");

				driverSelfcare = getDriver("SELFCARE");
				selfcareFuncs = new SelfcareVerificationFuncs(driverSelfcare);
				selfcareFuncs.verifySelfcare(MainUtil.dictionary.get("ACCOUNT_TYPE"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid bundle purchase  :" + e);
		}
	}


	@Test(description = "Mobile App Bundle Purchase")
	@Parameters({"msisdn", "bundleName", "testDataFile"})
	public void repurchasePrepaidBundle(String msisdn, String bundleName, String testDataFile) throws Exception {

		/*######################################################################################################################################################*//*
		driverMobileApp = getDriver("MOBILEAPP", "Android");
		driverChrome = getDriver("chrome");

		loginFuncs = new MobileAppLoginFuncs(driverMobileApp);
		mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
		bundlePurchaseFuncs = new MobileAppBundlePurchaseFuncs(driverMobileApp);
		umbFuncs=new UMBVerificationFuncs(driverChrome);
		selfcareFuncs = new SelfcareVerificationFuncs(driverChrome);
		*//*######################################################################################################################################################*/

		MainUtil.dictionary.put("MSISDN", msisdn);
		MainUtil.dictionary.put("BUNDLE_NAME", bundleName);
		MainUtil.dictionary.put("ACCOUNT_TYPE", "PREPAID");

		MainUtil.dictionary.put("TESTDATA_FILE1", testDataFile);
		String testDataFile2 = testDataFile.replace("purchase1", "purchase2");
		String testDataFile3 = testDataFile.replace("purchase1", "purchase3");
		MainUtil.dictionary.put("TESTDATA_FILE2", testDataFile2);
		MainUtil.dictionary.put("TESTDATA_FILE3", testDataFile3);

		boolean flagBundlePurchase1;
		boolean flagBundlePurchase2 = false;
		boolean flagBundlePurchase3 = false;

		RemoteWebDriver driverMobileAppLogin1;
		RemoteWebDriver driverMobileAppLogin2;
		RemoteWebDriver driverMobileAppLogin3;

		RemoteWebDriver driverMobileAppVerify1;
		RemoteWebDriver driverMobileAppVerify2;
		RemoteWebDriver driverMobileAppVerify3;

		String sBundleName = "";

		try {

			ExtentTestNGITestListener.createNode("Do Unsubscription - Current Mobile App Bundle");

			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

			driverUMB = getDriver("UMB");
			umbFuncs = new UMBVerificationFuncs(driverUMB);
			umbFuncs.doUnsubscribeAllBundles(MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("MSISDN"), driverUMB);

			ExtentTestNGITestListener.createNode("Do Mobile App Bundle Purchase 1");

			utilityAPI = new APIRequestLibrary();
			utilityAPI.syncTestDataInfoWithCRM(MainUtil.dictionary.get("MSISDN"));

			/*LAUNCH MOBILE APP*/
			driverMobileAppLogin1 = getDriver("MOBILEAPP", "Android");
			loginFuncs = new MobileAppLoginFuncs(driverMobileAppLogin1);

			if (loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"))) {

				sBundleName = MainUtil.dictionary.get("BUNDLE_NAME");

				/*PERFORM BUNDLE PURCHASE*/
				bundlePurchaseFuncs = new MobileAppBundlePurchaseFuncs(driverMobileAppLogin1);
				flagBundlePurchase1 = bundlePurchaseFuncs.doBundlePurchase(bundleName);

				if (flagBundlePurchase1) {
					ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

					ExtentTestNGITestListener.createNode("Mobile App Verification - Purchase 1");

					driverMobileAppVerify1 = getDriver("MOBILEAPP", "Android");
					mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileAppVerify1);
					mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TESTDATA_FILE1"));

					ExtentTestNGITestListener.createNode("Do Mobile App Bundle Purchase 2");

					/*LAUNCH MOBILE APP*/
					driverMobileAppLogin2 = getDriver("MOBILEAPP", "Android");
					loginFuncs = new MobileAppLoginFuncs(driverMobileAppLogin2);

					if (loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"))) {

						bundlePurchaseFuncs = new MobileAppBundlePurchaseFuncs(driverMobileAppLogin2);
						flagBundlePurchase2 = bundlePurchaseFuncs.doBundlePurchase(bundleName);

						if (flagBundlePurchase2) {
							ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

							ExtentTestNGITestListener.createNode("Mobile App Verification - Purchase 2");

							driverMobileAppVerify2 = getDriver("MOBILEAPP", "Android");
							mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileAppVerify2);
							mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TESTDATA_FILE2"));

							ExtentTestNGITestListener.createNode("Do Mobile App Bundle Purchase 3");

							/*LAUNCH MOBILE APP*/
							driverMobileAppLogin3 = getDriver("MOBILEAPP", "Android");
							loginFuncs = new MobileAppLoginFuncs(driverMobileAppLogin3);

							if (loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"))) {

								bundlePurchaseFuncs = new MobileAppBundlePurchaseFuncs(driverMobileAppLogin3);
								flagBundlePurchase3 = bundlePurchaseFuncs.doBundlePurchase(bundleName);

								if (flagBundlePurchase3) {
									ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

									ExtentTestNGITestListener.createNode("Mobile App Verification - Purchase 3");

									driverMobileAppVerify3 = getDriver("MOBILEAPP", "Android");
									mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileAppVerify3);
									mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TESTDATA_FILE3"));
								}
							}
						}
					}

					if (flagBundlePurchase1 && flagBundlePurchase2 && flagBundlePurchase3) {

						ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

						if (PropertyHelper.getProperties("UMB_CHANNEL_VERIFICATION_FLAG").equals("true")) {
							/*VERIFY UMB*/
							ExtentTestNGITestListener.createNode("UMB Verification");

							driverUMB = getDriver("UMB");
							umbFuncs = new UMBVerificationFuncs(driverUMB);
							umbFuncs.umbVerification("PREPAID", "BUNDLE");
						}

						if (PropertyHelper.getProperties("SELFCARE_CHANNEL_VERIFICATION_FLAG").equals("true")) {
							/*VERIFY SELFCARE*/
							ExtentTestNGITestListener.createNode("Selfcare Verification");

							driverSelfcare = getDriver("SELFCARE");
							selfcareFuncs = new SelfcareVerificationFuncs(driverSelfcare);
							selfcareFuncs.verifySelfcare("PREPAID");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid bundle repurchase  :" + e);
		}
	}

	@Test(description = "Mobile App Bundle Cross Purchase")
	@Parameters({"msisdn","bundleName1","bundleName2","testDataFile"})
	public void crossPurchasePrepaidBundles(String msisdn, String newBundleName1, String newBundleName2,String testDataFile) {
		try {

			/*######################################################################################################################################################*/

			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("ACCOUNT_TYPE", "Prepaid");
			MainUtil.dictionary.put("TESTDATA_FILE", testDataFile);

			RemoteWebDriver driverMobileAppLogin1;
			RemoteWebDriver driverMobileAppLogin2;
			RemoteWebDriver driverMobileAppVerify1;

			/*######################################################################################################################################################*/

			ExtentTestNGITestListener.createNode("Do Unsubscription - Current Mobile App Bundle");

			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

			driverUMB = getDriver("UMB");
			umbFuncs=new UMBVerificationFuncs(driverUMB);
			umbFuncs.doUnsubscribeAllBundles(MainUtil.dictionary.get("ACCOUNT_TYPE"),MainUtil.dictionary.get("MSISDN"), driverUMB);

			ExtentTestNGITestListener.createNode("Do Mobile App Bundle Purchase (Bundle Name - "+newBundleName1+")");

			utilityAPI=new APIRequestLibrary();
			utilityAPI.syncTestDataInfoWithCRM(MainUtil.dictionary.get("MSISDN"));

			MainUtil.dictionary.put("BUNDLE_NAME", newBundleName1);

			String sBundleName=MainUtil.dictionary.get("BUNDLE_NAME");

			if (sBundleName.equalsIgnoreCase("MBWEEK")){
				sBundleName="MB-WEEK";
			}

			/*LAUNCH MOBILE APP*/
			driverMobileAppLogin1 = getDriver("MOBILEAPP", "Android");
			loginFuncs = new MobileAppLoginFuncs(driverMobileAppLogin1);

			if(loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"))) {

				bundlePurchaseFuncs = new MobileAppBundlePurchaseFuncs(driverMobileAppLogin1);
				boolean flagBundlePurchase1 = bundlePurchaseFuncs.doBundlePurchase(sBundleName);

				ExtentTestNGITestListener.createNode("Do Mobile App Bundle Cross-Purchase (Bundle Name - "+newBundleName2+")");

				MainUtil.dictionary.put("BUNDLE_NAME", newBundleName2);

				sBundleName=MainUtil.dictionary.get("BUNDLE_NAME");
				if (sBundleName.equalsIgnoreCase("MBWEEK")){
					sBundleName="MB-WEEK";
				}

				driverMobileAppLogin2 = getDriver("MOBILEAPP", "Android");
				loginFuncs = new MobileAppLoginFuncs(driverMobileAppLogin2);

				if(loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"))) {

					bundlePurchaseFuncs = new MobileAppBundlePurchaseFuncs(driverMobileAppLogin2);
					boolean flagBundlePurchase2 = bundlePurchaseFuncs.doBundlePurchase(sBundleName);

					if (flagBundlePurchase1 && flagBundlePurchase2) {

						ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

						ExtentTestNGITestListener.createNode("Mobile App Verification - 2nd Bundle Purchase");

						driverMobileAppVerify1 = getDriver("MOBILEAPP", "Android");
						mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileAppVerify1);
						mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TESTDATA_FILE"));

						if (PropertyHelper.getProperties("UMB_CHANNEL_VERIFICATION_FLAG").equals("true")) {
							/*VERIFY UMB*/
							ExtentTestNGITestListener.createNode("UMB Verification");

							driverUMB = getDriver("UMB");
							umbFuncs = new UMBVerificationFuncs(driverUMB);
							umbFuncs.umbVerification("PREPAID", "BUNDLE");
						}

						if (PropertyHelper.getProperties("SELFCARE_CHANNEL_VERIFICATION_FLAG").equals("true")) {
							/*VERIFY SELFCARE*/
							ExtentTestNGITestListener.createNode("Selfcare Verification");

							driverSelfcare = getDriver("SELFCARE");
							selfcareFuncs = new SelfcareVerificationFuncs(driverSelfcare);
							selfcareFuncs.verifySelfcare("PREPAID");
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid bundle purchase  :" + e);
		}
	}

}
