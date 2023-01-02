package com.umtest.isd.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;

public class isdLoginLogoutPage extends MainUtil{

	private RemoteWebDriver driver;

	public isdLoginLogoutPage(RemoteWebDriver driver) {
		this.driver = driver;
	}
	@FindBy(name = "username")
	private WebElement textUsername;
	
	@FindBy(name = "password")
	private WebElement textPassword;
	
	@FindBy(xpath = "//button[@class='btn btn-lg btn-success btn-block']")
	private WebElement buttonLogin;
	
	@FindBy(xpath = "//a[contains(text(),'Logout')]")
	private WebElement buttonLogout;
	
	
	
	public WebElement getTextUsername() {
		return AppWait.waitForElementToBeClickable(driver, textUsername);
	}
	
	public WebElement getTextPassword() {
		return AppWait.waitForElementToBeClickable(driver, textPassword);
	}
	
	public WebElement getButtonLogin() {
		return AppWait.waitForElementToBeClickable(driver, buttonLogin);
	}
	
	public WebElement getButtonLogout() {
		return AppWait.waitForElementToBeClickable(driver, buttonLogout);
	}
}
