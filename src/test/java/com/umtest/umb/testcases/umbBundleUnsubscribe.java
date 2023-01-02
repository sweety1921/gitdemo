package com.umtest.umb.testcases;

import java.io.IOException;
import java.util.Dictionary;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.listener.ExtentTestNGITestListener;
import com.umtest.umb.pagefunction.UMBLoginLogoutFuncs;
import com.umtest.umb.pagefunction.UMBUSSDFuncs;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;

public class umbBundleUnsubscribe extends DriverFactory{
	private RemoteWebDriver driver;
	UMBLoginLogoutFuncs umbLoginLogout;
	UMBVerificationFuncs umbVerification;
	UMBUSSDFuncs umbUSSDFunc;
	ApplicationUtil appUtil;
	private static Logger logger = LogManager.getLogger(umbBundleUnsubscribe.class);
	

	@BeforeClass
	public void initialiseObj() throws IOException {
		driver = getDriver("chrome");
		umbLoginLogout = new UMBLoginLogoutFuncs(driver);
		umbVerification = new UMBVerificationFuncs(driver);
		umbUSSDFunc = new UMBUSSDFuncs(driver);
	
		
		MainUtil.APPLICATION_NAME = "UMB";
	}
	
	@Test(description = "UMB Bundle Unsubscribe")
	@Parameters({"category","msisdn","planType"})
	public void bundlePurchase(String category, String msisdn, String planType) {
		/*Purchase Bundle*/
		
		ExtentTestNGITestListener.createNode("UMB Bundle Unsubscribe");
		MainUtil.dictionary.put("MSISDN", msisdn);
		MainUtil.dictionary.put("CATEGORY", category);
		umbVerification.bundleUnsubscribe(planType);
		
}
}
