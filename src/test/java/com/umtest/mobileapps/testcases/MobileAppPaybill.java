package com.umtest.mobileapps.testcases;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.umtest.mobileapps.pagefunction.MobileAppLoginFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppPaybillFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppUtilFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.listener.ExtentTestNGITestListener;

public class MobileAppPaybill extends DriverFactory {

	public static RemoteWebDriver driverMobileApp;

	MobileAppLoginFuncs loginFuncs;
	MobileAppPaybillFuncs utilFuncs;
	ExtentTestNGITestListener ex;
	
	private static Logger logger = LogManager.getLogger(MobileAppChangeplan.class);

	@BeforeClass
	public void initialiseObj() throws IOException {
		MainUtil.APPLICATION_NAME = "MOBILEAPP";
	}
	
	@Test(description = "Mobile App PayBill")
	@Parameters({"Amount","msisdn","Cardnumber","CardType"})
	public void MobileAppPostpaidPayBill(String Amount,String msisdn, String Cardnumber,String CardType) {
		
		try {
			
			ExtentTestNGITestListener.createNode("Do Mobile App Prepaid Top");
			MainUtil.dictionary.put("AMOUNT", Amount);
			MainUtil.dictionary.put("MSISDN", msisdn);
			MainUtil.dictionary.put("Cardnumber", Cardnumber);
			MainUtil.dictionary.put("CardType", CardType);

			/*Launch Mobile App*/
			driverMobileApp = getDriver("MOBILEAPP", "Android");
			loginFuncs = new MobileAppLoginFuncs(driverMobileApp);
			loginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

			utilFuncs = new MobileAppPaybillFuncs(driverMobileApp);
			boolean status = utilFuncs.doPaybill(MainUtil.dictionary.get("CardType"));
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while doing MobileApp Paybill  :" + e);
		}
		}
}
