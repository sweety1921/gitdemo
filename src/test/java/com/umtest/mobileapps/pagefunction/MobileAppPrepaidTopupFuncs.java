package com.umtest.mobileapps.pagefunction;

import com.umtest.testframe.lib.ExtentScreenCapture;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.umtest.mobileapps.pageobject.MobileAppTopupPage;
import com.umtest.testframe.lib.ApplicationUtil;
import java.time.Duration;
import java.util.Set;

import static com.umtest.testframe.base.DriverFactory.getDriver;
import static com.umtest.testframe.listener.ExtentTestNGITestListener.getTest;

public class MobileAppPrepaidTopupFuncs extends MobileAppTopupPage {

	private static Logger logger = LogManager.getLogger(MobileAppPrepaidTopupFuncs.class);
	private AndroidDriver driver;

	public MobileAppPrepaidTopupFuncs(RemoteWebDriver driver) {
		super((AndroidDriver) driver);
		this.driver = (AndroidDriver) driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);

	}



	public boolean doTopupForAFriend(String topupAmount) {

		boolean returnStatus = false;

		try {
			MobileAppUtilFuncs utilFuncs = new MobileAppUtilFuncs(driver);
			utilFuncs.storeMobileAppBalace();
			utilFuncs.getMenuIcon().click();

			clickElement(utilFuncs.getMenuIcon(), "Side Menu", driver);
			clickElement(utilFuncs.getMenuTopUpCredit(), "Top Up & Credit Menu", driver);
			scrollUDLR(driver, 0, "U");
			clickElement(utilFuncs.getSubMenuTopUpForAFriend(), "Top Up For A Friend Sub-Menu", driver);
			//utilFuncs.getTextboxPhoneNoPrefix().clear();
			sendKeys(utilFuncs.getTextboxPhoneNoPrefix(), "018", "Phone No. Prefix", "", driver);
			//utilFuncs.getTextboxPhoneNo().clear();
			sendKeys(utilFuncs.getTextboxPhoneNo(), "2147029", "Phone No", "", driver);
		//	utilFuncs.getEmailaddress().clear();
			sendKeys(utilFuncs.getTextboxSenderEmailID(), "chandra.k@u.com.my", "Sendder Email Address", "", driver);

			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");

			clickElement(utilFuncs.getCheckboxTermsAndConditions(), "Terms and Conditions Checkbox", driver);
			clickElement(utilFuncs.getButtonNEXT(), "NEXT Button", driver);
			clickElement(utilFuncs.getButtonPAYNOW(), "PAY NOW Button", driver);
			Thread.sleep(5000);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");

			setWebViewContextMobileApp();


			sendKeys(utilFuncs.getTextboxCardHolderName(), "Chandra Kalagotla", "Card Holder Name", "", driver);
			sendKeys(utilFuncs.getTextboxCardNo1(), "1111", "Card No1", "", driver);
			sendKeys(utilFuncs.getTextboxCardNo2(), "1112", "Card No2", "", driver);
			sendKeys(utilFuncs.getTextboxCardNo3(), "1113", "Card No3", "", driver);
			sendKeys(utilFuncs.getTextboxCardNo4(), "1114", "Card No4", "", driver);



			clickElement(getButtonTopUpPin(), "Topup Pin", driver);
				//ApplicationUtil.getTopUpPin(topupAmount);

				sendKeys(getTextboxTopUpPIN(), "93344604738967", "PIN", "", driver);
				//sendKeys(getTextboxTopUpPIN(), dictionary.get("TOPUP_PIN"), "PIN", "", driver);
				clickElement(getButtonTopUpNow(), "Topup Pin", driver); Thread.sleep(3000);
				clickElement(getButtonOK(), "OK Button", driver);

				boolean status = true;

				if (status) {
					ApplicationUtil.updateTopupPinStatusInTable(dictionary.get("TOPUP_PIN"));
					ApplicationUtil.updateAccountInfoAfterTopup(dictionary.get("MSISDN"));
					returnStatus = true;
				}




		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing topup" + e);
		}
		return returnStatus;
	}

	public boolean doTopup(String topupType, String topupAmount) {

		boolean returnStatus = false;

		try {
			MobileAppUtilFuncs utilFuncs = new MobileAppUtilFuncs(driver);
			utilFuncs.storeMobileAppBalace();
			clickElement(getButtonTopUp(), "Top Up Button", driver);

			if (topupType.equalsIgnoreCase("PIN")) {

				clickElement(getButtonTopUpPin(), "Top Up Pin", driver);
				ApplicationUtil.getTopUpPin(topupAmount);

				sendKeys(getTextboxTopUpPIN(), dictionary.get("TOPUP_PIN"), "PIN", "", driver);
				clickElement(getButtonTopUpNow(), "Top Up Pin", driver);
				Thread.sleep(3000);

				if (isTopUpSuccessMessageDisplayed()) {
					clickElement(getButtonOK(), "OK Button", driver);
					ApplicationUtil.updateTopupPinStatusInTable(dictionary.get("TOPUP_PIN"));
					ApplicationUtil.updateAccountInfoAfterTopup(dictionary.get("MSISDN"));
					returnStatus = true;
				}

				System.out.println("Wait");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing topup" + e);
		}
		return returnStatus;
	}

	public boolean topupWithInvalidPIN(String sTopUpINVALIDPIN) {

		boolean returnStatus = false;
		/*String sActErrorMessage;*/

		try {
				clickElement(getButtonTopUp(), "Topup Button", driver);
				clickElement(getButtonTopUpPin(), "Topup Pin", driver);
				sendKeys(getTextboxTopUpPIN(), sTopUpINVALIDPIN, "PIN", "", driver);
				clickElement(getButtonTopUpNow(), "Topup Pin", driver);
				Thread.sleep(3000);

				captureAppScreenshot("Info: Capture Image", driver);

				return isErrorMessageForInvalidTopUpPINDisplayed();

			//	sActErrorMessage=getErrorMessageInvalidTopUpVoucher();

				//clickElement(getButtonOK(), "OK Button", driver);
				//compareWholeString(sActErrorMessage,sExpErrorMessage,"Invalid Top Up Error Message");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing topup" + e);
		}
		return returnStatus;
	}


	public void setWebViewContextMobileApp() {

		driver.context("WEBVIEW_com.omesti.myumobile");

		/*Set<String> contextName = driver.getContextHandles();
		System.out.println(contextName);
		for (String contexts : contextName) {
			System.out.println(contexts);
		}*/
	}

	public void setNativeContextMobileApp() {
		driver.context("NATIVE_APP");
	}

}
