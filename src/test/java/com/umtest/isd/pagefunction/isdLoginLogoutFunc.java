package com.umtest.isd.pagefunction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.umtest.isd.pageobject.isdLoginLogoutPage;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.lib.PropertyHelper;
import com.umtest.umb.pagefunction.UMBLoginLogoutFuncs;

public class isdLoginLogoutFunc extends isdLoginLogoutPage {
	private static Logger logger = LogManager.getLogger(UMBLoginLogoutFuncs.class);
	private RemoteWebDriver driver;

	public isdLoginLogoutFunc(RemoteWebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void isdLogin() {
		try {
			MainUtil.launchURL(PropertyHelper.getENVProperties("ISD_URL"), driver);
			sendKeys(getTextUsername(), PropertyHelper.getENVProperties("ISD_UName"), "Enter Username","", driver);
			sendKeys(getTextPassword(), PropertyHelper.getENVProperties("ISD_Password"), "Enter Password","", driver);
			clickElement(getButtonLogin(), "Click on Login", driver);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("Login to ISD application failed :" + e);
		}
	}

	public void isdLogout() {
		try {


			clickElement(getButtonLogout(), "Click on Logout", driver);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("Logout from ISD application failed :" + e);
		}
	}



}
