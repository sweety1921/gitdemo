package com.umtest.crm.pageobject;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

public class CRMLoginLogoutPage extends MainUtil {

	private RemoteWebDriver driver;

	public CRMLoginLogoutPage(RemoteWebDriver driver) {
		this.driver = driver;
	}

	@FindBy(id = "UserAccount")
	private WebElement textboxUserName;

	@FindBy(id = "UserPwd")
	private WebElement textboxPassword;

	@FindBy(id = "UserVertifyCodev")
	private WebElement textboxVerifyNumber;

	@FindBy(id = "vertifyCodeImg")
	private WebElement imgVerifyNumber;

	@FindBy(id = "Login")
	private WebElement btnLogin;

	@FindBy(xpath = "//input[@id='inputUserName']")
	private WebElement userNameTextBox;

	@FindBy(xpath = "//input[@id='inputPasswd']")
	private WebElement passWordTextBox;

	@FindBy(xpath = "//button[@id='btnLogin']")
	private WebElement signInButton;

	@FindBy(xpath = "//span[contains(text(),'Org Name')]")
	private WebElement orgNameCOlumn;

	@FindBy(xpath = "//button[contains(text(),'OK')]")
	private WebElement okButton;

	@FindBy(xpath = "//td[contains(text(),'Test ISD Branch - Management')]")
	public WebElement Testisdmanagment;

	public WebElement getTextboxUserName() {
		return AppWait.waitForElementToBeClickable(driver, textboxUserName);
	}

	public WebElement getTextboxPassword() {
		return AppWait.waitForElementToBeClickable(driver, textboxPassword);
	}

	public WebElement getTextboxVerifyNumber() {
		return AppWait.waitForElementToBeClickable(driver, textboxVerifyNumber);
	}

	public WebElement getImageVerifyNumber() {
		return AppWait.waitForElementToBeClickable(driver, imgVerifyNumber);
	}

	public WebElement getButtonLogin() {
		return AppWait.waitForElementToBeClickable(driver, btnLogin);
	}

	public WebElement getWCTTextboxUserName() {
		return AppWait.waitForElementToBeClickable(driver, userNameTextBox);
	}

	public WebElement getWCTPassWordUserName() {
		return AppWait.waitForElementToBeClickable(driver, passWordTextBox);
	}

	public WebElement getWCTButtonLogin() {
		return AppWait.waitForElementToBeClickable(driver, signInButton);
	}

	public WebElement getOrgNameColumn() {
		return AppWait.waitForElementToBeClickable(driver, orgNameCOlumn);
	}

	public WebElement getTestisdmanagment() {
		return AppWait.waitForElementForVisibility(driver, Testisdmanagment);
	}

	public WebElement getOkButton() {
		return AppWait.waitForElementToBeClickable(driver, okButton);
	}
}
