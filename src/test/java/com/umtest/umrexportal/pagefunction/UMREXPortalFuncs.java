package com.umtest.umrexportal.pagefunction;

import com.umtest.testframe.base.DriverFactory;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.lib.PropertyHelper;
import com.umtest.umrexportal.pageobject.UMREXPortalPage;

import static com.umtest.testframe.listener.ExtentTestNGITestListener.getTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UMREXPortalFuncs extends UMREXPortalPage {

	private static Logger logger = LogManager.getLogger(UMREXPortalFuncs.class);
	private RemoteWebDriver driver;

	public UMREXPortalFuncs(RemoteWebDriver driver) {
		super(driver);
		this.driver =  driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);

	}


	public void loginUMREXPortal() {
		try {

			logger.info("***************************************************");
			logger.info("******       LAUNCHING UMREX PORTAL ["+System.getProperty("env").toUpperCase()+"]      ******");
			logger.info("***************************************************");

			MainUtil.launchURL(PropertyHelper.getENVProperties("UMREX_PORTAL_URL"), driver);

			dictionary.put("USERNAME", PropertyHelper.getENVProperties("UMREX_PORTAL_USERNAME"));
            sendKeys(getTextboxUsername(), dictionary.get("USERNAME"), "Username", "", driver);
            dictionary.put("PASSWORD", PropertyHelper.getENVProperties("UMREX_PORTAL_PASSWORD"));
            sendKeys(getTextboxPassword(), dictionary.get("PASSWORD"), "Password", "", driver);
            clickElement(getButtonLogin(), "Login Button", driver);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while logging to UMREX Portal  :" + e);
		}
	}

	public void logoutUMREXPortal() {
		try {
			clickElement(getButtonLogout(), "Logout Button", driver);
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while log out to UMREX Portal  :" + e);
		}
	}


	public void verifyPrepaidRegistration(String msisdn) {
		try {
			
			loginUMREXPortal();
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDateTime todaysDate =  LocalDateTime.now();
			
			String todaysDateString = dtf.format(todaysDate);
			String registeredDateString = dictionary.get("PURCHASE_DATE");
			
			if (todaysDateString.equalsIgnoreCase(registeredDateString)) {
				
				clickElement(getButtonReports(), "Reports Button", driver);
				clickElement(getButtonRegistrationReport(), "Registration Report Button", driver);
				clickElement(getButtonSearch(), "Search Button", driver);
				Thread.sleep(3000);

				String s=getRegistrationStatusRegistrationReportTable(msisdn.substring(0, 4)+"-"+msisdn.substring(4, msisdn.length()));

				System.out.println("s"+s);

				checkForText("",getLabelRegistrationStatus(), "Success", "Registration Status", driver);
			} else {
				getTest().get().pass("UMREX Portal Verification skipped as account is not registered today");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying prepaid registration  :" + e);
		}finally {
			DriverFactory.quitUMREXPortal(driver);
		}
	}

	public void verifyPrepaidRegistrationForPurchase(String msisdn) {
		try {

			loginUMREXPortal();

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDateTime todaysDate =  LocalDateTime.now();

			String todaysDateString = dtf.format(todaysDate);
			String registeredDateString = dictionary.get("PURCHASE_DATE");

			if (todaysDateString.equalsIgnoreCase(registeredDateString)) {
				Thread.sleep(120000);
				clickElement(getButtonReports(), "Reports Button", driver);
				clickElement(getButtonRegistrationReport(), "Registration Report Button", driver);
				clickElement(getButtonSearch(), "Search Button", driver);
				sendKeys(getTextboxSearch(), msisdn.substring(0, 4)+"-"+msisdn.substring(4, msisdn.length()), "MSISDN", "", driver);
				checkForText("",getLabelRegistrationStatus(), "Success", "Registration Status", driver);

				clickElement(getButtonPurchaseReport(), "Purchase Report Button", driver);
				clickElement(getButtonSearch(), "Search Button", driver);
				sendKeys(getTextboxSearch(), msisdn.substring(0, 4)+"-"+msisdn.substring(4, msisdn.length()), "MSISDN", "", driver);
				checkForText("",getLabelRegistrationStatus(), "Success", "Purchase Status", driver);

			} else {

				getTest().get().pass("UMREX Portal Verification skipped as account is not registered today");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying prepaid registration  :" + e);
		}
	}
}
