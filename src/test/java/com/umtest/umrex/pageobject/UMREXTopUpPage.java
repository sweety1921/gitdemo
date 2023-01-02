package com.umtest.umrex.pageobject;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.lib.PropertyHelper;
import com.umtest.umrex.pagefunction.UMREXPrepaidTopUpFuncs;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.reporters.jq.Main;

import java.nio.channels.ScatteringByteChannel;

public class UMREXTopUpPage extends MainUtil {

	private AndroidDriver<?> driver;
	private static Logger logger = LogManager.getLogger(UMREXTopUpPage.class);

	public UMREXTopUpPage(AndroidDriver<?> driver) {
		this.driver = driver;
	}


	@AndroidFindBy(id = "com.fl.pra:id/topup_password")
	private AndroidElement textboxTopUpPassword;

	@AndroidFindBy(id = "com.fl.pra:id/topup_msisdn")
	private AndroidElement textboxTopUpMSISDN;

	@AndroidFindBy(id = "com.fl.pra:id/topup_amount")
	private AndroidElement textboxTopUpAmount;

	@AndroidFindBy(id = "com.fl.pra:id/topup_btn")
	private AndroidElement buttonTOPUP;

	@AndroidFindBy(id = "android:id/message")
	private AndroidElement textSuccessMessage;

	@AndroidFindBy(id = "android:id/message")
	private AndroidElement textDialogMessage;

	/*@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='android:id/message']")
	private AndroidElement textDialogMessage;*/

	@AndroidFindBy(id = "android:id/message")
	private AndroidElement textConfirmMessage;

	@AndroidFindBy(xpath = "//android.widget.Button[@resource-id='android:id/button1' and @text='CONFIRM']")
	private AndroidElement buttonTopUpCONFIRMMessage;

	//@AndroidFindBy(xpath = "//android.widget.Button[@resource-id='android:id/button1' and @text='Ok']")
	@AndroidFindBy(id = "android:id/button1")
	private AndroidElement buttonOKSuccessMessage;

	public AndroidElement getTextboxTopUpPassword() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, textboxTopUpPassword);
	}

	public AndroidElement getTextboxTopUpMSISDN() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, textboxTopUpMSISDN);
	}

	public AndroidElement getTextboxTopUpAmount() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, textboxTopUpAmount);
	}

	public AndroidElement getButtonTopUp() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonTOPUP);
	}

	public void enterTopUpPassword(String sTopUpPassword) {
		sendKeys(textboxTopUpPassword, sTopUpPassword, "Top Up Password", "", driver);
	}

	public void enterMSISDN(String sMSISDN) {
		sendKeys(textboxTopUpMSISDN, sMSISDN, "MSISDN", "", driver);
	}

	public void enterTopUpAmount(String sTopUpAmount) {
		sendKeys(textboxTopUpAmount, sTopUpAmount, "Top Up Amount", "", driver);
	}

	public void clickTopUpButton() {
		clickElement(buttonTOPUP, "Topup Button", driver);
	}

	public void clickCONFIRMButton() {
		clickElement(buttonTopUpCONFIRMMessage, "CONFIRM Button", driver);
	}

	public void clickOKButtonOnSuccessMessage() {
		clickElement(AppWait.waitForElementToBeClickable(driver, buttonOKSuccessMessage), "OK Button", driver);
	}

	public String getConfirmationMessage() {
		return getText(AppWait.waitForElementToBeClickable(driver, textConfirmMessage));
	}

	public String getSuccessMessage() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return getText(AppWait.waitForElementToBeClickable(driver, textSuccessMessage));
	}

	public boolean verifyConfirmMessage() {
		String sExpectedConfirmMessage = "You are about to top up RM" + MainUtil.dictionary.get("TOPUP_AMOUNT") + ".00 to +" + getFormattedMSISDN(MainUtil.dictionary.get("MSISDN")) + ".";
		return MainUtil.checkForText("", textConfirmMessage, sExpectedConfirmMessage, "Top Up Confirm Message", driver);
	}

	public String getFormattedMSISDN(String sMSISDN) {
		return sMSISDN.substring(0, 4) + "-" + sMSISDN.substring(4, sMSISDN.length());
	}

	public boolean verifySuccessMessage() {
		try {
			Thread.sleep(15000);
			String sActualSuccessMessage = getSuccessMessage();
			String str1 = "Top Up success to " + getFormattedMSISDN(MainUtil.dictionary.get("MSISDN")) + " RM " + MainUtil.dictionary.get("TOPUP_AMOUNT");

			String strDateTime = StringUtils.substringBetween(sActualSuccessMessage, str1+".00 ", "Trans.ID").trim();
			String strTransID = StringUtils.substringBetween(sActualSuccessMessage, "Trans.ID", ". Your latest account").trim();
			String[] aAccountBalance = sActualSuccessMessage.split("Your latest account balance");
			String strAccountBalance = aAccountBalance[1].trim();

			String strFormattedExpectedSuccessMessage = "Top Up success to " + getFormattedMSISDN(MainUtil.dictionary.get("MSISDN")) + " RM " + MainUtil.dictionary.get("TOPUP_AMOUNT")+".00 " + strDateTime + " Trans.ID " + strTransID + ". Your latest account balance " + strAccountBalance;

			MainUtil.dictionary.put("EXP_TRANSACTION_ID", strTransID);
			MainUtil.dictionary.put("EXP_TRANSACTION_DATE_TIME", strDateTime);
			MainUtil.dictionary.put("EXP_CLOSING_BALANCE", strAccountBalance);

			return MainUtil.checkForText("", textSuccessMessage, strFormattedExpectedSuccessMessage, "Top Up Success message", driver);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while verifying Prepaid Top Up Success Message :" + e);
			return false;
		}


	}

	public boolean verifyTopUpErrorMessage(String sExpectedErrorMessage) {
		try {
			Thread.sleep(5000);
			String actualMessage = textDialogMessage.getText();
			return MainUtil.compareInString(actualMessage,sExpectedErrorMessage,"Top Up Error/Warning Message",driver);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while verifying Prepaid Top Up Error Message :" + e);
			return false;
		}
	}



	
}
