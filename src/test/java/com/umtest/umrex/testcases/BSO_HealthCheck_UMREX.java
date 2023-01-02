package com.umtest.umrex.testcases;

import com.umtest.erechargeportal.pageobject.ERECHARGEPORTALLoginLogoutPage;
import com.umtest.erechargeportal.pageobject.ERECHARGEPORTALTransactionsPage;
import com.umtest.mobileapps.pagefunction.MobileAppLoginFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppUtilFuncs;
import com.umtest.selfcare.pagefunction.SelfcareVerificationFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.ExtentScreenCapture;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.listener.ExtentTestNGITestListener;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;
import com.umtest.umrex.pagefunction.UMREXLoginLogoutFuncs;
import com.umtest.umrex.pagefunction.UMREXPrepaidTopUpFuncs;
import com.umtest.umrex.pageobject.UMREXHomePage;
import com.umtest.umrex.pageobject.UMREXPrepaidRegistrationPage;
import com.umtest.umrexportal.pageobject.UMREXPortalTopUpReportsPage;
import io.appium.java_client.MobileDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.umtest.testframe.listener.ExtentTestNGITestListener.getTest;

public class BSO_HealthCheck_UMREX extends DriverFactory {
	private RemoteWebDriver driver;
	private RemoteWebDriver driverAppMobileApp;
	private RemoteWebDriver driverAppUMREX;
	private RemoteWebDriver driverChrome;

	UMREXLoginLogoutFuncs umrexLoginFuncs;
	UMREXHomePage umrexHomePage;
	UMREXPrepaidRegistrationPage umrexPrepaidRegistrationPage;
	UMREXPrepaidTopUpFuncs umrexPrepaidTopUpFuncs;

	ExtentTestNGITestListener ex;

	private static Logger logger = LogManager.getLogger(BSO_HealthCheck_UMREX.class);

	@BeforeClass
	public void initialiseObj() throws IOException {
		MainUtil.APPLICATION_NAME = "UMREX";
	}

	@Test(description = "UMREX Prepaid Top Up")
	@Parameters({"msisdn","topUpAmount","planName"})
	public void UMREXHealthCheck(String msisdn, String topUpAmount, String planName) {

		String planType = "Prepaid";
		String mainBalance;

		try {
			/*######################################################################################################################################################*/
			driverAppUMREX = getDriver("UMREX-PROD", "Android");

			umrexLoginFuncs = new UMREXLoginLogoutFuncs(driverAppUMREX);
			umrexHomePage = new UMREXHomePage(driverAppUMREX);
			umrexPrepaidRegistrationPage = new UMREXPrepaidRegistrationPage(driverAppUMREX);
			umrexPrepaidTopUpFuncs = new UMREXPrepaidTopUpFuncs(driverAppUMREX);
			/*######################################################################################################################################################*/

			ExtentTestNGITestListener.createNode("UMREX Login Test");
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("TOPUP_AMOUNT", topUpAmount);

			umrexLoginFuncs.loginUMREX();

			MainUtil.verifyElementDisplayed(umrexHomePage.getButtonSCANID(),"SCAN ID",driverAppUMREX);
			MainUtil.verifyElementDisplayed(umrexHomePage.getButtonREADID(),"READ ID",driverAppUMREX);
			MainUtil.verifyElementDisplayed(umrexHomePage.getButtonSUBMITTEDRECORD(),"SUBMITTED RECORD",driverAppUMREX);
			MainUtil.verifyElementDisplayed(umrexHomePage.getButtonPENDINGRECORD(),"PENDING RECORD",driverAppUMREX);
			MainUtil.verifyElementDisplayed(umrexHomePage.getButtonTOPUP(),"TOP UP",driverAppUMREX);
			MainUtil.verifyElementDisplayed(umrexHomePage.getButtonTOPUPRECORD(),"TOP UP RECORD",driverAppUMREX);
			MainUtil.scrollUDLR((MobileDriver) driverAppUMREX, 0, "U");
			MainUtil.verifyElementDisplayed(umrexHomePage.getButtonPURCHASERECORD(),"PURCHASE RECORD",driverAppUMREX);

			ExtentTestNGITestListener.createNode("UMREX TOP UP Check");

			umrexHomePage.clickTopUpButton();

			boolean prepaidTopUpActionSuccess=umrexPrepaidTopUpFuncs.doPrepaidTopUp();
			Assert.assertTrue(prepaidTopUpActionSuccess,"UMREX Prepaid Top Up Unsuccessful");
			umrexLoginFuncs.logoutUMREX();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while doing prepaid top up  :" + e);
		}
	}

}
