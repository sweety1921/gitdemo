package com.umtest.umb.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;

public class UMBLoginLogoutPage extends MainUtil {

	private RemoteWebDriver driver;

	public UMBLoginLogoutPage(RemoteWebDriver driver) {
		this.driver = driver;
	}

	@FindBy(id = "username")
	private WebElement textboxUsername;
	
	@FindBy(id = "password")
	private WebElement textboxPassword;
	
	@FindBy(id = "login-button")
	private WebElement buttonLogin;
	
	@FindBy(xpath = "//a[contains(text(),'Logout')]")
	private WebElement linkLogout;
	
	
	
	public WebElement getTextboxUsername() {
		return AppWait.waitForElementToBeClickable(driver, textboxUsername);
	}
	
	public WebElement getTextboxPassword() {
		return AppWait.waitForElementToBeClickable(driver, textboxPassword);
	}
	
	public WebElement getButtonLogin() {
		return AppWait.waitForElementToBeClickable(driver, buttonLogin);
	}
	
	public WebElement getLinkLogout() {
		return AppWait.waitForElementToBeClickable(driver, linkLogout);
	}
	
}
