package com.umtest.selfcare.testcases;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class BSO_HealthCheck_Selfcare extends DriverFactory {
	private RemoteWebDriver driver;
	private RemoteWebDriver driverChrome;
	
	
	SelfcareLoginLogoutFuncs loginFuncs;
	SelfcarePaybillFuncs Selfcare_Funcs;
	
	ExtentTestNGITestListener ex;

	private static Logger logger = LogManager.getLogger(BSO_HealthCheck_Selfcare.class);

	@BeforeClass
	public void initialiseObj() throws IOException {
		driver = getDriver("chrome");
		loginFuncs = new SelfcareLoginLogoutFuncs(driver);
		Selfcare_Funcs = new SelfcarePaybillFuncs(driver);
		
		MainUtil.APPLICATION_NAME = "SELFCARE";
	}

	@Test(description = "Selfcare Paybill")
	@Parameters({"msisdn","Amount","pin"})
	public void BSOHealthCheck_SelfcarePayBill(String msisdn,String Amount, String pin) {
		
		try {
			ExtentTestNGITestListener.createNode("Selfcare Login Check");
			MainUtil.dictionary.put("Amount", Amount);
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("pin", pin);
			
			loginFuncs.loginSelfcare(MainUtil.dictionary.get("MSISDN"));

			MainUtil.verifyElementDisplayed(loginFuncs.getmenuButtonManageAccount(),"Manage Account",driver);
			MainUtil.verifyElementDisplayed(loginFuncs.getMenuButtonBillsPayment(),"Bill & Payment",driver);
			MainUtil.verifyElementDisplayed(loginFuncs.getmenuButtonMyContents(),"My Contents",driver);
			MainUtil.verifyElementDisplayed(loginFuncs.getmenuButtonGoLife(),"GO LIFE",driver);
			MainUtil.verifyElementDisplayed(loginFuncs.getmenuButtonPrihatin(),"Jaringan PRIHATIN Programme",driver);
			MainUtil.verifyElementDisplayed(loginFuncs.getmenuButtonDigitalGoodsServices(),"Digital Goods & Services",driver);
			MainUtil.verifyElementDisplayed(loginFuncs.getButtonLogout(),"Log Out",driver);

			ExtentTestNGITestListener.createNode("Selfcare Paybill Check");

			String sExpectedPaymentURL="https://ebpg.maybank.com/BPG/admin/payment/PaymentWindow.jsp";

			Selfcare_Funcs.VeerifyPostpaidPayBillPage(sExpectedPaymentURL);

		//	loginFuncs.logoutSelfcare();

			}catch (Exception e) {
				e.printStackTrace();
				logger.error("Exception occured while doing Selfcare PayBill :" + e);
		}
	}
		
	
	@Test(description = "Selfcare App Topup")
	@Parameters({"planName","topupType","topupAmount","Cardnumber","msisdn"})
	public void SelfcareAppTopup(String planName, String topupType, String topupAmount, String Cardnumber, String msisdn) {
		
		try {
			
			ExtentTestNGITestListener.createNode("Do Selfcare App Prepaid Top");
					
			MainUtil.dictionary.put("PLAN_NAME", planName);
			MainUtil.dictionary.put("TOPUP_TYPE", topupType);
			MainUtil.dictionary.put("TOPUP_AMOUNT", topupAmount);
			MainUtil.dictionary.put("Card_Number", Cardnumber);
			MainUtil.dictionary.put("MSISDN", msisdn);
			
			ApplicationUtil.getPrepaidAccount(planName);
			
			loginFuncs.loginSelfcare(MainUtil.dictionary.get("MSISDN"));
			boolean status = Selfcare_Funcs.doTopup(MainUtil.dictionary.get("TOPUP_TYPE"), MainUtil.dictionary.get("TOPUP_AMOUNT"),MainUtil.dictionary.get("Card_Number"));
		
			if (status) {
				
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
				ExtentTestNGITestListener.createNode("Mobile App Verification");
				driver = getDriver("MOBILEAPP", "Android");
				MobileAppLoginFuncs mobileAppLoginFuncs = new MobileAppLoginFuncs(driver);
				mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));
				MobileAppUtilFuncs utilFuncs = new MobileAppUtilFuncs(driver);
				utilFuncs.verifyMobileApp("TOPUP_ONLY_PREPAID");
				
				
				ExtentTestNGITestListener.createNode("UMB Verification");
				driverChrome = getDriver("chrome"); UMBVerificationFuncs
				umbVerify = new UMBVerificationFuncs(driverChrome);
				umbVerify.umbVerification("PREPAID","");


				ExtentTestNGITestListener.createNode("Selfcare Verification");
				driverChrome = getDriver("chrome");
				SelfcareVerificationFuncs selfcareVerify = new SelfcareVerificationFuncs(driverChrome);
				selfcareVerify.verifySelfcare("PREPAID");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid Topup  :" + e);
		}
	}
	
	@Test(description = "Selfcare App Topup")
	@Parameters({"planName","topupType","topupAmount","msisdn","topupmsisdn"})
	public void SelfcareAppTopupForFriend(String planName, String topupType, String topupAmount, String msisdn, String topupmsisdn) {
		
		try {
			
			ExtentTestNGITestListener.createNode("Do Selfcare App Prepaid Top");
			
			MainUtil.dictionary.put("PLAN_NAME", planName);
			MainUtil.dictionary.put("TOPUP_TYPE", topupType);
			MainUtil.dictionary.put("TOPUP_AMOUNT", topupAmount);
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("topupmsisdn", topupmsisdn);
			
			ApplicationUtil.getPrepaidAccount(planName);
			
			loginFuncs.loginSelfcare(MainUtil.dictionary.get("topupmsisdn"));
			Selfcare_Funcs.storeSelfcareAppFriendBalace();
			loginFuncs.logoutSelfcare();
			loginFuncs.loginSelfcare(MainUtil.dictionary.get("MSISDN"));
			boolean status = Selfcare_Funcs.doTopupForFriend(MainUtil.dictionary.get("TOPUP_TYPE"), MainUtil.dictionary.get("TOPUP_AMOUNT"));
			
			if (status) {
				
				ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("topupmsisdn"));
				ExtentTestNGITestListener.createNode("Mobile App Verification");
				driver = getDriver("MOBILEAPP", "Android");
				MobileAppLoginFuncs mobileAppLoginFuncs = new MobileAppLoginFuncs(driver);
				mobileAppLoginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));
				MobileAppUtilFuncs utilFuncs = new MobileAppUtilFuncs(driver);
				utilFuncs.verifyMobileApp("TOPUP_ONLY_PREPAID");
				
				
				ExtentTestNGITestListener.createNode("UMB Verification");
				driverChrome = getDriver("chrome"); UMBVerificationFuncs
				umbVerify = new UMBVerificationFuncs(driverChrome);
				umbVerify.umbVerification("PREPAID","PLAN");


				ExtentTestNGITestListener.createNode("Selfcare Verification");
				driverChrome = getDriver("chrome");
				SelfcareVerificationFuncs selfcareVerify = new SelfcareVerificationFuncs(driverChrome);
				selfcareVerify.verifySelfcare("PREPAID");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid registration  :" + e);
		}
	}
}
