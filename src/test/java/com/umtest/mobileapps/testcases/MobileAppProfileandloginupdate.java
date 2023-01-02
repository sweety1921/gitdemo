package com.umtest.mobileapps.testcases;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.umtest.mobileapps.pagefunction.MobileAppLoginFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppProfileandloginupdateFuncs;
import com.umtest.selfcare.pagefunction.SelfcareVerificationFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.listener.ExtentTestNGITestListener;

public class MobileAppProfileandloginupdate extends DriverFactory {

	public static RemoteWebDriver driverMobileApp;
	public static RemoteWebDriver driverSelfcare;

	MobileAppLoginFuncs loginFuncs;
	MobileAppProfileandloginupdateFuncs utilFuncs;
	SelfcareVerificationFuncs selfcareFuncs;
	ExtentTestNGITestListener ex;

	private static Logger logger = LogManager.getLogger(MobileAppChangeplan.class);

	@BeforeClass
	public void initialiseObj() throws IOException {
		MainUtil.APPLICATION_NAME = "MOBILEAPP";
	}

	@Test(description = "Mobile App ResetPIN")
	@Parameters({"msisdn"})
	public void MobileAppPostpaidResetPIN(String msisdn) {

		try {
			ExtentTestNGITestListener.createNode("Do Mobile App Reset PIN");
			String pin = ApplicationUtil.getSelfcareLoginPin(msisdn);
			MainUtil.dictionary.put("MSISDN", msisdn);

			driverMobileApp = getDriver("MOBILEAPP", "Android");
			utilFuncs = new MobileAppProfileandloginupdateFuncs(driverMobileApp);
			boolean status = utilFuncs.Resetpin();

			if (status) {
				/*Verify Mobile App*/
				ExtentTestNGITestListener.createNode("Mobile App Verification");

				/*Launch Mobile App*/
				driverMobileApp = getDriver("MOBILEAPP", "Android");

				String newpin = ApplicationUtil.getSelfcareLoginPin(MainUtil.dictionary.get("MSISDN"));

				if (pin!=newpin)
				{
					MainUtil.compareInString(newpin,newpin, "Verify the PIN Reset Successful", driverMobileApp);
				}	else {
					MainUtil.compareInString(pin,newpin, "Verify the PIN Reset UnSuccessful", driverMobileApp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing MobileApp Resetpin  :" + e);
		}
	}

	@Test(description = "Mobile App ResetPIN")
	@Parameters({"msisdn","NewPin"})
	public void MobileAppPostpaidChangePIN(String msisdn, String NewPin) {

		try {
			ExtentTestNGITestListener.createNode("Do Mobile App Change PIN");
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("NewPin", NewPin);
			String pin = MainUtil.dictionary.get("NewPin");

			/*Launch Mobile App*/
			driverMobileApp = getDriver("MOBILEAPP", "Android");
			loginFuncs = new MobileAppLoginFuncs(driverMobileApp);
			loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

			utilFuncs = new MobileAppProfileandloginupdateFuncs(driverMobileApp);
			boolean status = utilFuncs.Changepin();

			if (status) {
				/*Verify Mobile App*/
				ExtentTestNGITestListener.createNode("Mobile App Verification");

				driverMobileApp = getDriver("MOBILEAPP", "Android");

				String newpinnumber = ApplicationUtil.getSelfcareLoginPin(MainUtil.dictionary.get("MSISDN"));
					if (!pin.equalsIgnoreCase(newpinnumber))
				{
					MainUtil.compareInString(pin,MainUtil.dictionary.get("NewPin"), "Verify the PIN Changed UnSuccessful", driverMobileApp);
				}	else {
					MainUtil.compareInString(newpinnumber,newpinnumber, "Verify the PIN Changed Successful", driverMobileApp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while doing MobileApp ChangePin  :" + e);
		}
	}

	@Test(description = "Mobile App UpdateProfile")
	@Parameters({"msisdn","EMAILID"})
	public void MobileAppPostpaidUpdateProfile(String msisdn, String EMAILID) {

		try {
			ExtentTestNGITestListener.createNode("Do Mobile App Update Profile");
			String pin = ApplicationUtil.getSelfcareLoginPin(msisdn);
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("EMAILID", EMAILID);

			/*Launch Mobile App*/
			driverMobileApp = getDriver("MOBILEAPP", "Android");
			loginFuncs = new MobileAppLoginFuncs(driverMobileApp);
			loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

			utilFuncs = new MobileAppProfileandloginupdateFuncs(driverMobileApp);
			boolean status = utilFuncs.UpdateProfile();

			if (status) {
				/*Verify Selfcare App*/
				ExtentTestNGITestListener.createNode("Selfcare Verification");

				driverSelfcare = getDriver("SELFCARE");
				selfcareFuncs = new SelfcareVerificationFuncs(driverSelfcare);
				selfcareFuncs.verifySelfcareProfile();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while doing MobileApp Update Profile :" + e);
		}
	}

	@Test(description = "Mobile App ResetPIN")
	@Parameters({"msisdn"})
	public void MobileAppPostpaidIDDIRActivation(String msisdn) {

		try {
			ExtentTestNGITestListener.createNode("Do Mobile App IDD IR Activation");
			String pin = ApplicationUtil.getSelfcareLoginPin(msisdn);
			MainUtil.dictionary.put("MSISDN", msisdn);

			/*Launch Mobile App*/
			driverMobileApp = getDriver("MOBILEAPP", "Android");
			loginFuncs = new MobileAppLoginFuncs(driverMobileApp);
			loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

			utilFuncs = new MobileAppProfileandloginupdateFuncs(driverMobileApp);
			boolean status = utilFuncs.IDDIRActivation();

			if (status) {
				/*Verify Selfcare App*/
				ExtentTestNGITestListener.createNode("Selfcare Verification");

				driverSelfcare = getDriver("SELFCARE");
				selfcareFuncs = new SelfcareVerificationFuncs(driverSelfcare);
				selfcareFuncs.verifySelfcareIDDIRStatus();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing MobileApp IDD IR Activation  :" + e);
		}
	}
}
