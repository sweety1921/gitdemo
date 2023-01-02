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

public class UMBCreditSOS extends DriverFactory {
	private RemoteWebDriver driver;
	UMBLoginLogoutFuncs umbLoginLogout;
	UMBVerificationFuncs umbVerification;
	SelfcareVerificationFuncs selfcareVerify;
	MobileAppLoginFuncs mobileAppLoginFuncs;
	private static Logger logger = LogManager.getLogger(UMBCreditSOS.class);

	@BeforeClass
	public void initialiseObj() throws IOException {
		driver = getDriver("chrome");
		umbLoginLogout = new UMBLoginLogoutFuncs(driver);
		umbVerification = new UMBVerificationFuncs(driver);
		MainUtil.APPLICATION_NAME = "UMB";
	}

	@Test(description = "Extend Account Validity")
	@Parameters({"msisdn","category","credit_amount","identificationType","planName","topupAmount"})
	public void creditSOS(String msisdn,String category,String credit_amount, String identificationType, String planName, String topupAmount) {
		
		try {
			String mainBalance = null;
			String planType = "Prepaid";
		
					
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("CATEGORY", category);
			MainUtil.dictionary.put("CREDIT_AMOUNT", credit_amount);
			MainUtil.dictionary.put("IDENTIFICATION_TYPE",identificationType);
			MainUtil.dictionary.put("PLAN_NAME",planName);
			MainUtil.dictionary.put("TOPUP_AMOUNT",topupAmount);
			
			
				/*Get main balance and Credit Balance*/
				ApplicationUtil.getPrepaidAccount(MainUtil.dictionary.get("MSISDN"));
				//ApplicationUtil.getPrepaidAccount(planName);
				ExtentTestNGITestListener.createNode("UMB CREIT SOS: GET MAIN BALANCE");
				mainBalance = umbVerification.getBalance(planType, "BalanceCheck");
				MainUtil.dictionary.put("CURRENT_MAIN_BALANCE", mainBalance);
			
			
			/*Perform Top-up*/
			ExtentTestNGITestListener.createNode("DO " +category+" via UMB");
			Boolean flag = umbVerification.performCreditSOS(planType,category);
			
			
			if (flag) {
				
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
				ExtentTestNGITestListener.createNode("UMB Verification");
				driver = getDriver("chrome"); 
				UMBVerificationFuncs umbVerify = new UMBVerificationFuncs(driver);
				umbVerify.umbVerification("PREPAID",category);

				ExtentTestNGITestListener.createNode("Selfcare Verification");
				driver = getDriver("chrome");
				SelfcareVerificationFuncs selfcareVerify = new SelfcareVerificationFuncs(driver);
				selfcareVerify.verifySelfcare("PREPAID");

				ExtentTestNGITestListener.createNode("Mobile App Verification");
				driver = getDriver("MOBILEAPP", "Android");
				MobileAppUtilFuncs utilFuncs = new MobileAppUtilFuncs(driver);
				MobileAppLoginFuncs loginFuncs = new MobileAppLoginFuncs(driver);
				loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));
				utilFuncs.verifyBalanceAndPlanName();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing UMB Credit SOS  :" + e);
		}
	}
	
}
