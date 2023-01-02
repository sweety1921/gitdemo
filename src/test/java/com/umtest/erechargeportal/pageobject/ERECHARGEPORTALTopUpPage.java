package com.umtest.erechargeportal.pageobject;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

public class ERECHARGEPORTALTopUpPage extends MainUtil {

	private static Logger logger = LogManager.getLogger(ERECHARGEPORTALLoginLogoutPage.class);
	private RemoteWebDriver driver;

	public ERECHARGEPORTALTopUpPage(RemoteWebDriver driver) {
		this.driver = driver;
	}

    @FindBy(xpath = "(//div[text()='BALANCE']/following-sibling::div[1])[1]")
	private WebElement textDealerBalance;

	@FindBy(id = "qa-add-topup")
	private WebElement buttonAddTopup;

	@FindBy(id = "add-topup-msisdn-input")
	private WebElement addTopupMsisdnInput;

	@FindBy(id = "add-topup-amount-input")
	private WebElement addTopupAmountInput;

	@FindBy(id = "add-topup-password-input")
	private WebElement addTopupPasswordInput;

	@FindBy(id = "add-topup-submit-button")
	private WebElement addTopupSubmitButton;

	@FindBy(id = "add-topup-confirm-y-button")
	private WebElement addTopupConfirmYesButton;

	@FindBy(xpath = "//div[text()='Successful Operation']")
	private WebElement textSuccessfulOperation;

	public WebElement getAddTopupMSISDN() {
		return AppWait.waitForElementToBeClickable(driver, addTopupMsisdnInput);
	}

	public WebElement getAddTopupAmount() {
		return addTopupAmountInput;
	}

	public WebElement getAddTopupPassword() {
		return addTopupPasswordInput;
	}

	public WebElement getAddTopupSubmitButton() {
		return addTopupSubmitButton;
	}

	public WebElement getAddTopupConfirmYButton() {
		return AppWait.waitForElementToBeClickable(driver, addTopupConfirmYesButton);
	}

	public WebElement getTextSuccessfulOperation() {
		return textSuccessfulOperation;
	}


	public boolean doTopUp(String sMSISDN, String sAmount) {

		try {
			clickElement(AppWait.waitForElementToBeClickable(driver, addTopupSubmitButton), "+Top Up Button", driver);
			sendKeys(AppWait.waitForElementToBeClickable(driver, addTopupMsisdnInput), sMSISDN, "MSISDN", "", driver);
			sendKeys(addTopupAmountInput, sAmount, "TopUp Amount", "", driver);
			sendKeys(addTopupPasswordInput, "password", "TopUp Amount", "", driver);
			clickElement(addTopupSubmitButton, "Submit Button", driver);
			clickElement(addTopupConfirmYesButton, "Confirm Yes Button", driver);
			clickElement(addTopupSubmitButton, "Submit Button", driver);

			return verifyElementIsDisplayed(textSuccessfulOperation,"Successful Operation message", driver);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while login to eRecharge Portal  :" + e);
		}
		return false;
	}


	public String getDealerBalance(){
		return getText(textDealerBalance);
	}














}
