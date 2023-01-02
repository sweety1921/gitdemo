package com.umtest.selfcare.testcases;

import java.io.IOException;

import com.umtest.mobileapps.pagefunction.MobileAppBundlePurchaseFuncs;
import com.umtest.testframe.lib.APIRequestLibrary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.umtest.mobileapps.pagefunction.MobileAppLoginFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppUtilFuncs;
import com.umtest.selfcare.pagefunction.SelfcareLoginLogoutFuncs;
import com.umtest.selfcare.pagefunction.SelfcarePaybillFuncs;
import com.umtest.selfcare.pagefunction.SelfcareVerificationFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.listener.ExtentTestNGITestListener;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;

public class SelfcarePaybill extends DriverFactory {
	private RemoteWebDriver driver;
	private RemoteWebDriver driverApp;
	private RemoteWebDriver driverChrome;

	SelfcareLoginLogoutFuncs loginFuncs;
	SelfcarePaybillFuncs Selfcare_Funcs;

	SelfcareLoginLogoutFuncs selfcareLoginFuncs;
	SelfcarePaybillFuncs SelfcareFuncs;
	SelfcareVerificationFuncs selfcareVerifyFuncs;
	MobileAppLoginFuncs mobileAppLoginFuncs;
	MobileAppUtilFuncs mobileAppUtilFuncs;
	UMBVerificationFuncs umbVerifyFuncs;
	APIRequestLibrary selfcareAPI;
	
	ExtentTestNGITestListener ex;

	private static Logger logger = LogManager.getLogger(SelfcarePaybill.class);

	@BeforeClass
	public void initialiseObj() throws IOException {
		driver = getDriver("chrome");
		loginFuncs = new SelfcareLoginLogoutFuncs(driver);
		Selfcare_Funcs = new SelfcarePaybillFuncs(driver);
		
		MainUtil.APPLICATION_NAME = "SELFCARE";
	}

	@Test(description = "Selfcare Paybill")
	@Parameters({"msisdn","Amount","Cardnumber"})
	public void SelfcarePayBill_postpaid(String msisdn,String Amount, String Cardnumber) {
		
		try {
			ExtentTestNGITestListener.createNode("PayBill from Selfcare");
			MainUtil.dictionary.put("Amount", Amount);
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("Cardnumber", Cardnumber);
			
			loginFuncs.loginSelfcare(MainUtil.dictionary.get("MSISDN"));
			boolean statusFlag = Selfcare_Funcs.PostpaidPayBill(Amount,Cardnumber);
			loginFuncs.logoutSelfcare();

			System.out.println(statusFlag);
			if (statusFlag == true) {
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

				ExtentTestNGITestListener.createNode("Selfcare Verification");
				driverChrome = getDriver("chrome");
				SelfcareVerificationFuncs selfcareVerify = new SelfcareVerificationFuncs(driverChrome);
				selfcareVerify.verifyPostpaidBillpayment();
				
				ExtentTestNGITestListener.createNode("Mobile App Verification");
				driver = getDriver("MOBILEAPP", "Android");
				MobileAppLoginFuncs mobileAppLoginFuncs = new MobileAppLoginFuncs(driver);
				mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

				MobileAppUtilFuncs mobileAppUtilFuncs = new MobileAppUtilFuncs(driver);
				mobileAppUtilFuncs.verifyPostpaidbillpayment();
			}
			
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("Exception occured while doing Selfcare PayBill :" + e);
		}
	}
		

}
