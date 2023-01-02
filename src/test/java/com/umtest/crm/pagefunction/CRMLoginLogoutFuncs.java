package com.umtest.crm.pagefunction;

import com.umtest.crm.pageobject.CRMLoginLogoutPage;
import com.umtest.selfcare.pageobject.SelfcareLoginLogoutPage;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.lib.PropertyHelper;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class CRMLoginLogoutFuncs extends CRMLoginLogoutPage {

	private static Logger logger = LogManager.getLogger(CRMLoginLogoutFuncs.class);
	private RemoteWebDriver driver;

	public CRMLoginLogoutFuncs(RemoteWebDriver driver) {
		super(driver);
		this.driver =  driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);

	}
	public void loginCRM() {
		try {
			logger.info("****** LAUNCHING CRM ******");
			driver.manage().deleteAllCookies();
			MainUtil.launchURL(PropertyHelper.getENVProperties("CRM_URL"), driver);

			sendKeys(getTextboxUserName(), PropertyHelper.getENVProperties("CRM_UserName"), "User Name", "", driver);
			sendKeys(getTextboxPassword(), PropertyHelper.getENVProperties("CRM_Password"), "Password", "", driver);
			Thread.sleep(1000);
			driver.manage().window().maximize();
			clickElement(getButtonLogin(), "Login Button", driver);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while logging to CRM  :" + e);
		}
	}

	public void logoutCRM() {
		try {

			clickElement(getButtonLogin(), "Logout Button", driver);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying logging out from CRM  :" + e);
		}
	}

	public void loginWCTCRM() {
		try {
			logger.info("****** LAUNCHING WCT CRM ******");
			driver.manage().deleteAllCookies();
			MainUtil.launchURL(PropertyHelper.getENVProperties("WCTCRMURL"), driver);

			sendKeys(getWCTTextboxUserName(), PropertyHelper.getENVProperties("WCTCRMUserName"), "User Name", "", driver);
			sendKeys(getWCTPassWordUserName(), PropertyHelper.getENVProperties("WCTCRMPassword"), "Password", "", driver);
			clickElement(getWCTButtonLogin(), "Login Button", driver);
			try {
				if (getOrgNameColumn().isDisplayed()) {
					clickElement(getOrgNameColumn(), "Org Name column", driver);
					clickElement(getTestisdmanagment(), "Org Name column", driver);
					clickElement(getOkButton(), "Ok button", driver);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while logging to CRM  :" + e);
		}
	}
}
