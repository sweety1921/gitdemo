package com.umtest.selfcare.testcases;

import java.io.IOException;

import com.umtest.selfcare.pagefunction.SelfcareVerificationFuncs;
import com.umtest.testframe.lib.APIRequestLibrary;
import com.umtest.umb.pagefunction.UMBLoginLogoutFuncs;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.umtest.mobileapps.pagefunction.MobileAppLoginFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppUtilFuncs;
import com.umtest.selfcare.pagefunction.SelfcareGolifePurchaseFuncs;
import com.umtest.selfcare.pagefunction.SelfcareLoginLogoutFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.listener.ExtentTestNGITestListener;

public class SelfcareGolifePurchase extends DriverFactory {
	private RemoteWebDriver driver;
	private RemoteWebDriver driverApp;
	private RemoteWebDriver driverChrome;

	
	SelfcareLoginLogoutFuncs selfcareLoginFuncs;
	SelfcareGolifePurchaseFuncs selfcareFuncs;

	MobileAppLoginFuncs mobileAppLoginFuncs;
	MobileAppUtilFuncs mobileAppUtilFuncs;

	ExtentTestNGITestListener ex;
	APIRequestLibrary api;

	public APIRequestLibrary selfcareAPI;

	private static Logger logger = LogManager.getLogger(SelfcareGolifePurchase.class);

	@BeforeClass
	public void initialiseObj() throws IOException {
		//driver = getDriver("chrome");
		//loginFuncs = new SelfcareLoginLogoutFuncs(driver);
		//Selfcare_Funcs = new SelfcareGolifePurchaseFuncs(driver);
		
		MainUtil.APPLICATION_NAME = "SELFCARE";
	}

	@Test(description = "Selfcare Postpaid Purchase Golife insurance")
	@Parameters({"msisdn","Golife"})
	public void SelfcarePurchaseGolife(String msisdn,String Golife) {
		boolean methodReturn = false;
		try {

			/*######################################################################################################################################################*/
			driverApp = getDriver("MOBILEAPP", "Android");
			driverChrome = getDriver("chrome");

			mobileAppLoginFuncs = new MobileAppLoginFuncs(driverApp);
			mobileAppUtilFuncs = new MobileAppUtilFuncs(driverApp);
			selfcareLoginFuncs=new SelfcareLoginLogoutFuncs(driverChrome);
			selfcareFuncs = new SelfcareGolifePurchaseFuncs(driverChrome);
			selfcareAPI=new APIRequestLibrary();
			/*######################################################################################################################################################*/


			ExtentTestNGITestListener.createNode("Purchase Golife insurance from Selfcare");
			MainUtil.dictionary.put("Golife", Golife);
			MainUtil.dictionary.put("MSISDN", msisdn);

			selfcareLoginFuncs.loginSelfcare(MainUtil.dictionary.get("MSISDN"));
			boolean statusFlag = selfcareFuncs.PurchaseGolife(MainUtil.dictionary.get("Golife"));
			selfcareLoginFuncs.logoutSelfcare();
			
			if (statusFlag == true) {
				ExtentTestNGITestListener.createNode("Mobile App Verification");
				driver = getDriver("MOBILEAPP", "Android");
				//MobileAppLoginFuncs mobileAppLoginFuncs = new MobileAppLoginFuncs(driver);
				mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

				//MobileAppUtilFuncs mobileAppUtilFuncs = new MobileAppUtilFuncs(driver);
				mobileAppUtilFuncs.verifyMobileAppGolifeInsurance(MainUtil.dictionary.get("Golife"));
				
			}
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("Exception occured while doing Selfcare Purchase golife :" + e);
		}
	}
	
	
	@Test(description = "Selfcare Prepaid Purchase Golife insurance")
	@Parameters({"Golife","msisdn","Amount","PaymentType","Cardnumber"})
	public void SelfcarePurchaseGolife_Prepaid(String Golife,String msisdn,String Amount,String PaymentType,String Cardnumber) {
		boolean methodReturn = false;
		try {

			/*######################################################################################################################################################*/
			driverApp = getDriver("MOBILEAPP", "Android");
			driverChrome = getDriver("chrome");

			mobileAppLoginFuncs = new MobileAppLoginFuncs(driverApp);
			mobileAppUtilFuncs = new MobileAppUtilFuncs(driverApp);
			selfcareLoginFuncs=new SelfcareLoginLogoutFuncs(driverChrome);
			selfcareFuncs = new SelfcareGolifePurchaseFuncs(driverChrome);
			selfcareAPI=new APIRequestLibrary();
			/*######################################################################################################################################################*/


			MainUtil.dictionary.put("Golife", Golife);
			MainUtil.dictionary.put("MSISDN", msisdn);

			ExtentTestNGITestListener.createNode("Purchase Golife "+Golife);

			selfcareAPI.syncSubscriberBalanceAndExpiryDate(MainUtil.dictionary.get("MSISDN"));
			//selfcareAPI.postUnsubscribeGolife(MainUtil.dictionary.get("MSISDN"));

			selfcareLoginFuncs.loginSelfcare(MainUtil.dictionary.get("MSISDN"));
			boolean statusFlag = selfcareFuncs.PurchaseGolife_Prepaid(Golife,Amount,PaymentType,Cardnumber);
			selfcareLoginFuncs.logoutSelfcare();

//			boolean statusFlag;
//			statusFlag = true;
			
			if (statusFlag) {
				ExtentTestNGITestListener.createNode("Mobile App Verification");

				mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));
				mobileAppUtilFuncs.verifyMobileAppGolifeInsurance(MainUtil.dictionary.get("Golife"));
			}
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("Exception occured while doing PayBill :" + e);
		}
	}
}
