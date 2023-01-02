package com.umtest.crm.pagefunction;

import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CRMCustomerSearchPage extends com.umtest.crm.pageobject.CRMCustomerSearchPage {

	private static Logger logger = LogManager.getLogger(CRMCustomerSearchPage.class);
	private AndroidDriver driver;

	public CRMCustomerSearchPage(RemoteWebDriver driver) {
		super((AndroidDriver) driver);
		this.driver = (AndroidDriver) driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);

	}

	public boolean doBundlePurchase(String bundleName) throws SQLException, ClassNotFoundException {
		boolean returnStatus = false;

		try {

			clickElement(getButtonPurchaseAddonPlusButton(),"Add-Ons",driver);
			//clickElement(getButtonAddOns(),"Add-Ons",driver);




		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while doing bundle purchase" + e);
		}
		return returnStatus;
	}

}
