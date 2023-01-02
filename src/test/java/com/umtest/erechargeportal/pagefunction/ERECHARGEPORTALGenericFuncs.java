package com.umtest.erechargeportal.pagefunction;

import com.umtest.erechargeportal.pageobject.ERECHARGEPORTALLoginLogoutPage;
import com.umtest.erechargeportal.pageobject.ERECHARGEPORTALTopUpPage;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.lib.PropertyHelper;
import com.umtest.umb.pagefunction.UMBUSSDFuncs;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;
import com.umtest.umrexportal.pageobject.UMREXPortalPage;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.umtest.testframe.listener.ExtentTestNGITestListener.getTest;

//public class ERECHARGEPORTALGenericFuncs {
 public class ERECHARGEPORTALGenericFuncs extends MainUtil {

	private static Logger logger = LogManager.getLogger(ERECHARGEPORTALGenericFuncs.class);
	private RemoteWebDriver driver;
	ERECHARGEPORTALTopUpPage erechargeportalTopUpPage;
	ERECHARGEPORTALLoginLogoutPage erechargeportalLoginLogoutPage;
	UMBVerificationFuncs umbVerificationFuncs;

	public ERECHARGEPORTALGenericFuncs(RemoteWebDriver driver) {
		this.driver =  driver;
		//PageFactory.initElements(driver, this);
	}

	public void maintainMSISDNBalance(String MSISDN, String sAmount, String sMinAmountCheck, RemoteWebDriver driver) throws Exception {

		 erechargeportalTopUpPage = new ERECHARGEPORTALTopUpPage(driver);
		 erechargeportalLoginLogoutPage = new ERECHARGEPORTALLoginLogoutPage(driver);
		 umbVerificationFuncs =new UMBVerificationFuncs(driver);

		 String sBalanceAndExpiryDate=umbVerificationFuncs.getPrepaidBalanceExpiryDate(MSISDN, driver);
		 String finalBalance;
		 String[] accBalance = sBalanceAndExpiryDate.split("\\r?\\n");
         finalBalance = accBalance[2];
         finalBalance = finalBalance.substring(2);

	}



}
