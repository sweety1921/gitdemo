package com.umtest.umrex.pagefunction;

import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.lib.PropertyHelper;
import com.umtest.testframe.lib.RandomDataGenerator;
import com.umtest.umrex.pageobject.UMREXRegistrationPage;
import com.umtest.umrex.pageobject.UMREXTopUpPage;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.time.Duration;

public class UMREXPrepaidTopUpFuncs extends UMREXTopUpPage {

	private static Logger logger = LogManager.getLogger(UMREXPrepaidTopUpFuncs.class);
	private AndroidDriver driver;

	public UMREXPrepaidTopUpFuncs(RemoteWebDriver driver) {
		super((AndroidDriver) driver);
		this.driver = (AndroidDriver) driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);

	}

	public boolean doPrepaidTopUp() {
		boolean methodReturn = false;

		try {
			enterTopUpPassword(PropertyHelper.getENVProperties("UMREX_PURCHASE_PASSWORD"));
            enterMSISDN(MainUtil.dictionary.get("MSISDN") );
            enterTopUpAmount(MainUtil.dictionary.get("TOPUP_AMOUNT")+"00");
            clickTopUpButton();

			Assert.assertTrue(verifyConfirmMessage(),"Confirmation Message 'You are about to ...'");
			clickCONFIRMButton();
			Thread.sleep(30000);

			Assert.assertTrue(verifySuccessMessage(),"Success Message 'Top Up success to ...'");
            clickOKButtonOnSuccessMessage();

			if(!System.getProperty("env").equalsIgnoreCase("prod")) {
				ApplicationUtil.updateAccountInfoAfterTopup(dictionary.get("MSISDN"));
			}


			methodReturn=true;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while doing prepaid top up  :" + e);
		}
		return methodReturn;
	}

	public boolean doPrepaidTopUpActionForErrorMessage(String sErrorMessage) {
		boolean methodReturn = false;

		try {
			enterTopUpPassword(PropertyHelper.getENVProperties("UMREX_PURCHASE_PASSWORD"));
			enterMSISDN(MainUtil.dictionary.get("MSISDN") );
			enterTopUpAmount(MainUtil.dictionary.get("TOPUP_AMOUNT")+"00");
			clickTopUpButton();

			Assert.assertTrue(verifyConfirmMessage(),"Confirmation Message 'You are about to ...'");
			clickCONFIRMButton();

			Thread.sleep(10000);

			Assert.assertTrue(verifyTopUpErrorMessage(sErrorMessage),"Error/Warning Message 'The same transaction was performed within the last 5 minutes to the number. ...'");
			clickOKButtonOnSuccessMessage();

			//ApplicationUtil.updateAccountInfoAfterTopup(dictionary.get("MSISDN"));
			methodReturn=true;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while doing prepaid top up  :" + e);
		}
		return methodReturn;
	}

	
}
