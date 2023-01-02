package com.umtest.mobileapps.testcases;

/*import com.umtest.erechargeportal.pagefunction.ERECHARGEPORTALGenericFuncs;
import com.umtest.erechargeportal.pageobject.ERECHARGEPORTALTransactionsPage;*/

import com.umtest.mobileapps.pagefunction.MobileAppBundlePurchaseFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppLoginFuncs;
import com.umtest.selfcare.pagefunction.SelfcareGolifePurchaseFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.listener.ExtentTestNGITestListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MobileAppGoLifePurchase extends DriverFactory {

	private RemoteWebDriver driverMobileApp;
	private RemoteWebDriver selfcareDriver;


	MobileAppLoginFuncs loginFuncs;
	MobileAppBundlePurchaseFuncs bundlePurchaseFuncs;
	SelfcareGolifePurchaseFuncs selfcareGolifeVerify;
	ExtentTestNGITestListener ex;

	private static Logger logger = LogManager.getLogger(MobileAppGoLifePurchase.class);

	@BeforeClass
	public void initialiseObj() throws Exception {
		MainUtil.APPLICATION_NAME = "MOBILEAPP";
	}


	@Test(description = "Mobile App Golife Purchase")
	@Parameters({"msisdn","Golife"})
	public void MobileAppPurchaseGolife(String msisdn, String Golife) {

		try {

			ExtentTestNGITestListener.createNode("Purchase Golife insurance from MobileApp");
			MainUtil.dictionary.put("Golife", Golife);
			MainUtil.dictionary.put("MSISDN", msisdn);

			driverMobileApp = getDriver("MOBILEAPP", "Android");
			loginFuncs = new MobileAppLoginFuncs(driverMobileApp);
			loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

			bundlePurchaseFuncs = new MobileAppBundlePurchaseFuncs(driverMobileApp);
			boolean status = bundlePurchaseFuncs.doGolifePurchase(MainUtil.dictionary.get("Golife"));

			if (status) {

				ExtentTestNGITestListener.createNode("Selfcare Verification");
				selfcareDriver = getDriver("chrome");
				selfcareGolifeVerify = new SelfcareGolifePurchaseFuncs(selfcareDriver);
				selfcareGolifeVerify.verifySelfcareGolife(MainUtil.dictionary.get("Golife"));

			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing MobileAPP Golife Purchase  :" + e);
		}
	}

}
