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
import com.umtest.umb.pagefunction.UMBUSSDFuncs;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;

public class UMBActivateRoaming extends DriverFactory{
	private RemoteWebDriver driver;
	UMBLoginLogoutFuncs umbLoginLogout;
	UMBVerificationFuncs umbVerification;
	UMBUSSDFuncs umbUSSDFunc;
	ApplicationUtil appUtil;
	private static Logger logger = LogManager.getLogger(UMBActivateRoaming.class);
	

	@BeforeClass
	public void initialiseObj() throws IOException {
		driver = getDriver("chrome");
		umbLoginLogout = new UMBLoginLogoutFuncs(driver);
		umbVerification = new UMBVerificationFuncs(driver);
		umbUSSDFunc = new UMBUSSDFuncs(driver);
	
		
		MainUtil.APPLICATION_NAME = "UMB";
	}
	
	@Test(description = "UMB Change Rate Plan")
	@Parameters({"category","msisdn","planType"})
	public void changePlan(String category, String msisdn, String planType) {
		
		
		try {
			String mainBalance = null;
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("OPERATION_TYPE", category);
			MainUtil.dictionary.put("PLAN_TYPE", planType);
			
			
			/*Purchase Bundle*/
			
			ExtentTestNGITestListener.createNode("UMB CHANGE RATE PLAN");
			Boolean flag = umbVerification.performUMB(planType,category);
    		
    		//Boolean flag = true;
    		
			if (flag == true) {
			
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured during Activate roaming  :" + e);
		}
	}
}
