package com.umtest.mobileapps.testcases;

import java.io.IOException;

import com.umtest.mobileapps.pagefunction.MobileAppBundlePurchaseFuncs;
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
import com.umtest.umb.pagefunction.UMBLoginLogoutFuncs;
import com.umtest.umb.pagefunction.UMBUSSDFuncs;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;
import com.umtest.umb.testcases.umbCreditTransfer;

public class MobileAppPrepaidCreditTransfer extends DriverFactory {
	private RemoteWebDriver driver;
	private RemoteWebDriver driverApp;
	private RemoteWebDriver driverChrome;
	private RemoteWebDriver driverSelfCare;
	private RemoteWebDriver driverSelfCareReceiver;
	private RemoteWebDriver driverChromeUMB;

	MobileAppLoginFuncs mobileAppLoginFuncs;
	MobileAppUtilFuncs mobileAppUtilFuncs;
	UMBLoginLogoutFuncs umbLoginLogout;
	UMBVerificationFuncs umbVerification;
	UMBVerificationFuncs umbFuncs;
	UMBVerificationFuncs umbFuncsReceiver;
	SelfcareVerificationFuncs selfcareVerify;
	SelfcareVerificationFuncs selfcareFuncs;
	SelfcareVerificationFuncs selfcareFuncsReceiver;

	ApplicationUtil appUtil;
	private static Logger logger = LogManager.getLogger(MobileAppPrepaidCreditTransfer.class);

	@BeforeClass
	public void initialiseObj() throws IOException {
		/*driver = getDriver("chrome");
		*//*umbLoginLogout = new UMBLoginLogoutFuncs(driver);*/

	
		MainUtil.APPLICATION_NAME = "MOBILE APP";
	}

	@Test(description = "MobileApp Credit Transfer")
	@Parameters({"category","Sender_msisdn","Receiver_msisdn","transfer_amount"})
	public void creditTransfer(String category, String Sender_msisdn, String Receiver_msisdn, String transfer_amount) {


		try {
			/*######################################################################################################################################################*/
			driverApp = getDriver("MOBILEAPP", "Android");
			driverChrome = getDriver("chrome");
			driverSelfCare = getDriver("SelfCareChrome");
			driverChromeUMB = getDriver("ChromeUMB");
			driverSelfCareReceiver = getDriver("SelfCareReceiverChrome");

			mobileAppLoginFuncs = new MobileAppLoginFuncs(driverApp);
			mobileAppUtilFuncs = new MobileAppUtilFuncs(driverApp);
			umbLoginLogout = new UMBLoginLogoutFuncs(driverChrome);
			umbFuncs=new UMBVerificationFuncs(driverChrome);
			selfcareFuncs = new SelfcareVerificationFuncs(driverSelfCare);
			umbFuncsReceiver=new UMBVerificationFuncs(driverChromeUMB);
			selfcareFuncsReceiver = new SelfcareVerificationFuncs(driverSelfCareReceiver);
			/*######################################################################################################################################################*/

			String mainBalance = null;
			String creditBalance = null;
			String planType = "Prepaid";
			MainUtil.dictionary.put("MSISDN", Sender_msisdn);
			MainUtil.dictionary.put("RECEIVER_MSISDN", Receiver_msisdn);
			//MainUtil.dictionary.put("OPERATION_TYPE", "CREDIT_TRANSFER");
			MainUtil.dictionary.put("CATEGORY", category);
			MainUtil.dictionary.put("TRANSFER_AMOUNT", transfer_amount);
			
			/*Get main balance and Credit Balance*/
			ExtentTestNGITestListener.createNode("Mobile App CREDIT TRANSFER");

			mainBalance = umbFuncs.getBalance(planType, "BalanceCheck");
			MainUtil.dictionary.put("CURRENT_BALANCE", mainBalance);
			MainUtil.dictionary.put("MSISDN", Receiver_msisdn);
			MainUtil.dictionary.put("MSISDN_REC", Receiver_msisdn);
			creditBalance = umbFuncs.getBalance(planType, "CreditTransfer BalCheck");
			MainUtil.dictionary.put("CREDIT_AMOUNT", creditBalance);
			MainUtil.dictionary.put("MSISDN", Sender_msisdn);

			mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

			ApplicationUtil.getReceiverEndDate(MainUtil.dictionary.get("MSISDN_REC"));

			Boolean flag = mobileAppUtilFuncs.performCreditShare();

//			boolean flag = true;

			if(flag) {
				/*Verify balance for MSISDN A*/
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
				/*Verify UMB*/
				ExtentTestNGITestListener.createNode("UMB Credit Share Balance Verification (Sender)");
				umbFuncs.umbVerification(planType,MainUtil.dictionary.get("CATEGORY"));

				/*Verify Selfcare*/
				ExtentTestNGITestListener.createNode("Selfcare Balance Verification (Sender)");
				selfcareFuncs.verifySelfcare(planType);

				/*Verify Mobile App*/
				ExtentTestNGITestListener.createNode("Mobile App Credit Share Balance Verification (Sender)");

				mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));
				/*MobileAppUtilFuncs mobileAppUtilFuncs1 = new MobileAppUtilFuncs(driver);*/
				mobileAppUtilFuncs.verifyCreditShareBalance();

				/*Verify balance for MSISDN B*/
				MainUtil.dictionary.put("MSISDN", Receiver_msisdn);
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

				/*Verify UMB*/
				ExtentTestNGITestListener.createNode("UMB Credit Share Balance Verification (Receiver)");
				umbFuncsReceiver.umbVerification(planType,MainUtil.dictionary.get("CATEGORY"));

				/*Verify Selfcare*/
				ExtentTestNGITestListener.createNode("Selfcare Balance Verification (Receiver)");
				selfcareFuncsReceiver.verifySelfcare(planType);

				/*Verify Mobile App*/
				ExtentTestNGITestListener.createNode("Mobile App Credit Share Balance Verification (Receiver)");
				mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));
				mobileAppUtilFuncs.verifyCreditShareBalance();
			}

		}  catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured during bundle purchase  :" + e);
		}
	}
}