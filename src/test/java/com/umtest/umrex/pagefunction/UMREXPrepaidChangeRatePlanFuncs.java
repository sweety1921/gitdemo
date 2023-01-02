package com.umtest.umrex.pagefunction;

import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.umrex.pageobject.UMREXChangeOfferPage;
import com.umtest.umrex.pageobject.UMREXHomePage;
import com.umtest.umrex.pageobject.UMREXRegistrationPage;
import com.umtest.umrex.pageobject.UMREXScanIDReadIDCustomerInfoPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class UMREXPrepaidChangeRatePlanFuncs extends UMREXRegistrationPage {
	private static Logger logger = LogManager.getLogger(UMREXPrepaidChangeRatePlanFuncs.class);
	private AndroidDriver driver;
	private UMREXScanIDReadIDCustomerInfoPage scanID;
	private UMREXHomePage UMREXHome;
	private UMREXChangeOfferPage UMREXChangeOffer;

	public UMREXPrepaidChangeRatePlanFuncs(RemoteWebDriver driver) {
		super((AndroidDriver) driver);
		this.driver = (AndroidDriver) driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);
		
		
	}
	public boolean dopPrepaidChangePlan() {

		boolean methodReturn = false;
		scanID= new UMREXScanIDReadIDCustomerInfoPage(driver);

		try {

			UMREXHome= new UMREXHomePage(driver);
			UMREXChangeOffer=new UMREXChangeOfferPage(driver);

			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
			UMREXHome.clickChangeOfferPhoneBundle();
			UMREXHome.clickChangeOffer();

			if (MainUtil.dictionary.get("IDENTIFICATION_TYPE").equalsIgnoreCase("MYKAD")) {
				UMREXChangeOffer.clickReadID();
				scanID.enterMyKadCustomerInfo();
			} else {
				UMREXChangeOffer.clickScanID();
				scanID.enterPassportCustomerInfo();
			}

			UMREXChangeOffer.clickSelectMSISDN();
			UMREXChangeOffer.selectMSISDN(MainUtil.dictionary.get("MSISDN"));
			UMREXChangeOffer.clickPleaseSelectChangeRatePlanType();
			UMREXChangeOffer.clickChangePrepaidRatePlan();
			UMREXChangeOffer.clickContinue();
			Thread.sleep(3000);

			UMREXChangeOffer.clickSelectNewRatePlan();
			UMREXChangeOffer.clickNewRatePlan(MainUtil.dictionary.get("NEW_PLAN_NAME"));
			UMREXChangeOffer.clickContinue();
			UMREXChangeOffer.clickContinue();
			UMREXChangeOffer.clickContinue();
			UMREXChangeOffer.clickContinue();
			Thread.sleep(15000);
			UMREXChangeOffer.clickContinue();

			UMREXChangeOffer.clickDeclarationCheckbox();

			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");

			UMREXChangeOffer.clickTapHereToSign();
			doSignature(driver);
			UMREXChangeOffer.clickSignButton();

			UMREXChangeOffer.clickContinue();
			UMREXChangeOffer.clickContinue_MakePayment();
			UMREXChangeOffer.clickYES();

			Thread.sleep(10000);
			boolean checkFlag1 = checkForText("", UMREXChangeOffer.getLabelThankYouYourOrderReceivedMessage(), "Thank you for your order", "Thank you for your order(Change Rate Plan Order Success Message)", driver);
			boolean checkFlag2= checkForText("", UMREXChangeOffer.getLabelChangeRatePlanRequestReceivedMessage(), "Your change rate plan request is received.", "Your change rate plan request is received. (Change Rate Plan Order Success Message)", driver);

			if (checkFlag1 == true && checkFlag2 == true) {
				ApplicationUtil.updateRateplanName(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("NEWPLAN_NAME")) ;
				methodReturn = true;
				clickElement(getButtonBackToHome(), "Back To Home Button", driver);
			} else {
				clickElement(getButtonOK(), "Ok Button", driver);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while doing prepaid registration  :" + e);
		}
		return methodReturn;
		}


		public void setWebViewContextUMREX() {
		   driver.context("WEBVIEW_com.fl.pra");
		}

	    public void setNativeContextUMREX() {
		driver.context("NATIVE_APP");
	}
}
