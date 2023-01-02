package com.umtest.selfcare.testcases;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.umtest.mobileapps.pagefunction.MobileAppLoginFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppUtilFuncs;
import com.umtest.selfcare.pagefunction.SelfcareFuncs;
import com.umtest.selfcare.pagefunction.SelfcareLoginLogoutFuncs;
import com.umtest.selfcare.pagefunction.SelfcareVerificationFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.listener.ExtentTestNGITestListener;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;

public class SelfcareChangeplan extends DriverFactory {
	private RemoteWebDriver driver;
	private RemoteWebDriver driverChrome;
	
	SelfcareLoginLogoutFuncs loginFuncs;
	SelfcareFuncs Selfcare_Funcs;
	ExtentTestNGITestListener ex;

	private static Logger logger = LogManager.getLogger(SelfcareChangeplan.class);

	@BeforeClass
	public void initialiseObj() throws IOException {
		driver = getDriver("chrome");
		loginFuncs = new SelfcareLoginLogoutFuncs(driver);
		Selfcare_Funcs = new SelfcareFuncs(driver);
		MainUtil.APPLICATION_NAME = "Selfcare";
	}
	
	@Test(description = "Selfcare Change Plan")
	@Parameters({"msisdn","NewRatePlan","NewRatePlanFullname"})
	public void SelfcareChangeplan_Postpaid(String msisdn,String NewRatePlan,String NewRatePlanFullname) {
		try {
			ExtentTestNGITestListener.createNode("PayBill from Selfcare");
			MainUtil.dictionary.put("NewRatePlan", NewRatePlan);
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("NewRatePlanFullname", NewRatePlanFullname);
			
			loginFuncs.loginSelfcare(MainUtil.dictionary.get("MSISDN"));
			boolean statusFlag = Selfcare_Funcs.PostpaidChangePlan();
			//loginFuncs.logoutSelfcare();
			
			if (statusFlag == true) {
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

				ExtentTestNGITestListener.createNode("UMB Verification");
				driverChrome = getDriver("chrome");
				UMBVerificationFuncs umbVerify = new UMBVerificationFuncs(driverChrome);
				umbVerify.umbVerification("POSTPAID","PLAN");


				ExtentTestNGITestListener.createNode("Selfcare Verification");
				driverChrome = getDriver("chrome");
				SelfcareVerificationFuncs selfcareVerify = new SelfcareVerificationFuncs(driverChrome);
				selfcareVerify.verifySelfcare("POSTPAID");


				ExtentTestNGITestListener.createNode("Mobile App Verification");
				driver = getDriver("MOBILEAPP", "Android");
				MobileAppLoginFuncs mobileAppLoginFuncs = new MobileAppLoginFuncs(driver);
				mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

				MobileAppUtilFuncs mobileAppUtilFuncs = new MobileAppUtilFuncs(driver);
				mobileAppUtilFuncs.verifyMobileApp(MainUtil.dictionary.get("REGISTRATION_TYPE")+"_POSTPAID");
			}	
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Selfcare change plan :" + e);
	}
	}
}
