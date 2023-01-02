package com.umtest.crm.testcases;

import com.umtest.crm.pagefunction.CRMCustomerSearchPage;
import com.umtest.crm.pagefunction.CRMLoginLogoutFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.listener.ExtentTestNGITestListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class BSO_Health_Check_CRM extends DriverFactory {

	private RemoteWebDriver driverIE;
	CRMLoginLogoutFuncs loginFuncs;
	CRMCustomerSearchPage crmCustomerSearchPage;

	ExtentTestNGITestListener ex;

	private static Logger logger = LogManager.getLogger(BSO_Health_Check_CRM.class);

	@BeforeClass
	public void initialiseObj() throws Exception {
		MainUtil.APPLICATION_NAME = "CRM";
	}

	@Test(description = "Mobile App Bundle Purchase")
	@Parameters({"msisdn"})
	public void SearchCustomerCRM(String msisdn) {
		try {

			/*######################################################################################################################################################*/
			driverIE = getDriver("ie");
			loginFuncs = new CRMLoginLogoutFuncs(driverIE);
			/*######################################################################################################################################################*/

			MainUtil.dictionary.put("MSISDN", msisdn);

			loginFuncs.loginCRM();

			ExtentTestNGITestListener.createNode("Do Mobile App Bundle Purchase");

			//boolean status = bundlePurchaseFuncs.doBundlePurchase(sBundleName);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid bundle purchase  :" + e);
		}
	}


}
