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
import com.umtest.selfcare.pagefunction.SelfcareGenericFuncs;
import com.umtest.selfcare.pagefunction.SelfcareLoginLogoutFuncs;
import com.umtest.selfcare.pagefunction.SelfcarePaybillFuncs;
import com.umtest.selfcare.pagefunction.SelfcareVerificationFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.listener.ExtentTestNGITestListener;

public class SelfcareGeneric extends DriverFactory {
	private RemoteWebDriver driver;
	private RemoteWebDriver driverChrome;


	SelfcareLoginLogoutFuncs loginFuncs;
	SelfcareGenericFuncs genericFuncs;

	ExtentTestNGITestListener ex;

	private static Logger logger = LogManager.getLogger(SelfcareGeneric.class);

	@BeforeClass
	public void initialiseObj() throws IOException {
		driver = getDriver("chrome");
		loginFuncs = new SelfcareLoginLogoutFuncs(driver);
		genericFuncs = new SelfcareGenericFuncs(driver);

		MainUtil.APPLICATION_NAME = "SELFCARE";
	}

	@Test(description = "Selfcare ChangePin")
	@Parameters({"msisdn","NewPin"})
	public void SelfcareChangePin(String msisdn,String NewPin) {

		try {
			ExtentTestNGITestListener.createNode("ChangePin from Selfcare");
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("NewPin", NewPin);

			loginFuncs.loginSelfcare(MainUtil.dictionary.get("MSISDN"));
			boolean statusFlag = genericFuncs.Postpaidchangepin(MainUtil.dictionary.get("MSISDN"),MainUtil.dictionary.get("NewPin"));
			loginFuncs.logoutSelfcare();
			System.out.println(statusFlag);
			if (statusFlag == true) {
				String pin = ApplicationUtil.getSelfcareLoginPin(msisdn);
				MainUtil.compareInString(NewPin, pin, "Change Pin", driver);

			}

		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Selfcare PayBill :" + e);
		}
	}

	@Test(description = "Selfcare Roaming and IDD Service")
	@Parameters({"msisdn"})
	public void SelfcareRoamingandIDDService(String msisdn) {

		try {
			ExtentTestNGITestListener.createNode("Selfcare Roaming and IDD Service Activation from Selfcare");
			MainUtil.dictionary.put("MSISDN", msisdn);

			loginFuncs.loginSelfcare(MainUtil.dictionary.get("MSISDN"));
			boolean statusFlag = genericFuncs.PostpaidRoamingandIDDactivation(MainUtil.dictionary.get("MSISDN"));
			loginFuncs.logoutSelfcare();
			System.out.println(statusFlag);
			if (statusFlag == true) {
				String pin = ApplicationUtil.getSelfcareLoginPin(msisdn);
				//MainUtil.compareInString(NewPin, pin, "Change Pin", driver);

			}

		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Selfcare PayBill :" + e);
		}
	}

	@Test(description = "Selfcare Activate E-Billing")
	@Parameters({"msisdn"})
	public void SelfcareActivateEBilling(String msisdn) {

		try {
			ExtentTestNGITestListener.createNode("E-Bill activation from Selfcare");
			MainUtil.dictionary.put("MSISDN", msisdn);

			loginFuncs.loginSelfcare(MainUtil.dictionary.get("MSISDN"));
			boolean statusFlag = genericFuncs.PostpaidEBillingactivation(MainUtil.dictionary.get("MSISDN"));
			loginFuncs.logoutSelfcare();
			System.out.println(statusFlag);
			if (statusFlag == true) {
				String pin = ApplicationUtil.getSelfcareLoginPin(msisdn);
				//MainUtil.compareInString(NewPin, pin, "Change Pin", driver);

			}

		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Selfcare PayBill :" + e);
		}
	}

	@Test(description = "Selfcare Activate AutoDebit")
	@Parameters({"msisdn","Cardnumber"})
	public void SelfcareAutodebit(String msisdn,String Cardnumber) {

		try {
			ExtentTestNGITestListener.createNode("AutoDebit activation from Selfcare");
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("CardNumber", Cardnumber);

			loginFuncs.loginSelfcare(MainUtil.dictionary.get("MSISDN"));
			boolean statusFlag = genericFuncs.PostpaidAutodebitactivation(MainUtil.dictionary.get("MSISDN"));
			loginFuncs.logoutSelfcare();
			System.out.println(statusFlag);
			if (statusFlag == true) {
				String pin = ApplicationUtil.getSelfcareLoginPin(msisdn);
				//MainUtil.compareInString(NewPin, pin, "Change Pin", driver);

			}

		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Selfcare PayBill :" + e);
		}
	}

	@Test(description = "Selfcare Update Profile")
	@Parameters({"msisdn","Email","Address1","Zip","Homenumber"})
	public void SelfcareUpdateProfile(String msisdn,String Email,String Address1,String Zip,String Homenumber) {

		try {
			driver = getDriver("chrome");
			loginFuncs = new SelfcareLoginLogoutFuncs(driver);
			genericFuncs = new SelfcareGenericFuncs(driver);

			ExtentTestNGITestListener.createNode("Update Profile from Selfcare");

			MainUtil.dictionary.put("msisdn", msisdn);
			MainUtil.dictionary.put("EMAILID", Email);
			MainUtil.dictionary.put("Address1", Address1);
//			MainUtil.dictionary.put("Address2", Address2);
			MainUtil.dictionary.put("Zip", Zip);
			MainUtil.dictionary.put("Homenumber", Homenumber);

			loginFuncs.loginSelfcare(MainUtil.dictionary.get("msisdn"));
			boolean statusFlag = genericFuncs.UpdateProfile(MainUtil.dictionary.get("msisdn"));
			loginFuncs.logoutSelfcare();
	//		boolean statusFlag=true;
	//		MainUtil.dictionary.put("MSISDN", "60182146945");
			System.out.println(statusFlag);
			if (statusFlag == true) {
				/*Verify Selfcare App*/
				ExtentTestNGITestListener.createNode("Selfcare Verification");
				driverChrome = getDriver("chrome");
				SelfcareVerificationFuncs selfcareVerify = new SelfcareVerificationFuncs(driverChrome);
				selfcareVerify.verifySelfcareProfile_Selfcare();

			}

		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Selfcare Autodebit :" + e);
		}
	}

}
