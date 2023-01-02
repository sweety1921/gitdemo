package com.umtest.umb.testcases;

import java.io.IOException;

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
import com.umtest.umb.pagefunction.UMBVerificationFuncs;

public class UMBAccValidityExtension extends DriverFactory {
	
	private RemoteWebDriver driver;
	UMBLoginLogoutFuncs umbLoginLogout;
	UMBVerificationFuncs umbVerification;
	SelfcareVerificationFuncs selfcareVerify;
	MobileAppLoginFuncs mobileAppLoginFuncs;
	private static Logger logger = LogManager.getLogger(UMBAccValidityExtension.class);

	@BeforeClass
	public void initialiseObj() throws IOException {
		driver = getDriver("chrome");
		umbLoginLogout = new UMBLoginLogoutFuncs(driver);
		umbVerification = new UMBVerificationFuncs(driver);
		MainUtil.APPLICATION_NAME = "UMB";
	}

	@Test(description = "Extend Account Validity")
	@Parameters({"msisdn","category","extendDays"})
	public void AccValidityExt(String msisdn,String category,String extensionDays) {
		
		try {
			String mainBalance = null;
			String planType = "Prepaid";
		
					
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("CATEGORY", category);
			MainUtil.dictionary.put("EXTENSION_DAYS", extensionDays);
			
			
			
				/*Get main balance and Credit Balance*/
				//ApplicationUtil.getPrepaidAccount1(planName);
				//MainUtil.dictionary.put("MSISDN", "601139176257");
				ExtentTestNGITestListener.createNode("UMB Extend Account Validity: GET MAIN BALANCE");
				mainBalance = umbVerification.getBalance(planType, "BalanceCheck");
				MainUtil.dictionary.put("CURRENT_MAIN_BALANCE", mainBalance);
			
			
			/*Perform Top-up*/
			ExtentTestNGITestListener.createNode("DO " +category+" via UMB");
			Boolean flag = umbVerification.performAccValidityExt(planType,category);
			
			
			if (flag) {
				
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
				ExtentTestNGITestListener.createNode("UMB Verification");
				driver = getDriver("chrome"); 
				UMBVerificationFuncs umbVerify = new UMBVerificationFuncs(driver);
				umbVerify.umbVerification("PREPAID",category);

//				ExtentTestNGITestListener.createNode("Selfcare Verification");
//				driver = getDriver("chrome");
//				SelfcareVerificationFuncs selfcareVerify = new SelfcareVerificationFuncs(driver);
//				selfcareVerify.verifySelfcare("PREPAID");

				ExtentTestNGITestListener.createNode("Mobile App Verification");
				driver = getDriver("MOBILEAPP", "Android");
				MobileAppUtilFuncs utilFuncs = new MobileAppUtilFuncs(driver);
				MobileAppLoginFuncs loginFuncs = new MobileAppLoginFuncs(driver);
				loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));
				utilFuncs.verifyBalanceAndPlanName();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing UMB Account Validity Extension  :" + e);
		}
	}
	
}
