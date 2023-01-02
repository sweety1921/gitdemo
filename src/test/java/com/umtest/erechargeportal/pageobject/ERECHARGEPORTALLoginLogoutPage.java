package com.umtest.erechargeportal.pageobject;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.lib.PropertyHelper;
import com.umtest.umb.pagefunction.UMBLoginLogoutFuncs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ERECHARGEPORTALLoginLogoutPage extends MainUtil {

	private RemoteWebDriver driver;
	private static Logger logger = LogManager.getLogger(ERECHARGEPORTALLoginLogoutPage.class);

	public ERECHARGEPORTALLoginLogoutPage(RemoteWebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "login-username")
	private WebElement textboxUsername;

	@FindBy(id = "login-password")
	private WebElement textboxPassword;

	@FindBy(id = "login-button")
	private WebElement buttonLogin;

	@FindBy(id = "logout-btn")
	private WebElement buttonLogOut;

	public WebElement getTextboxUsername() {
		return AppWait.waitForElementToBeClickable(driver, textboxUsername);
	}
	public WebElement getTextboxPassword() {
		return textboxPassword;
	}
	public WebElement getButtonLogin() {
		return buttonLogin;
	}
	public WebElement getButtonLogout() {
		return buttonLogOut;
	}

	public void login() {
		try {
			driver.manage().deleteAllCookies();
			launchURL(PropertyHelper.getENVProperties("ERECHARGE_PORTAL_URL"), driver);
			dictionary.put("ERECHARGE_PORTAL_USERNAME", PropertyHelper.getENVProperties("ERECHARGE_PORTAL_USERNAME"));
			sendKeys(getTextboxUsername(), MainUtil.dictionary.get("ERECHARGE_PORTAL_USERNAME"), "Username", "", driver);
			dictionary.put("ERECHARGE_PORTAL_PASSWORD", PropertyHelper.getENVProperties("ERECHARGE_PORTAL_PASSWORD"));
			sendKeys(getTextboxPassword(), MainUtil.dictionary.get("ERECHARGE_PORTAL_PASSWORD"), "Password", "", driver);
			clickElement(getButtonLogin(), "Login Button", driver);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while login to eRecharge Portal  :" + e);
		}
	}

	public void logout() {
		try {
			clickElement(getButtonLogout(), "Logout Button", driver);
			//driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while logging out of eRecharge Portal  :" + e);
		}
	}


}
