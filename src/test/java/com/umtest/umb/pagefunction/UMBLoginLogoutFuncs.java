package com.umtest.umb.pagefunction;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.lib.PropertyHelper;
import com.umtest.umb.pageobject.UMBLoginLogoutPage;

import java.time.Duration;

public class UMBLoginLogoutFuncs extends UMBLoginLogoutPage {

    private static Logger logger = LogManager.getLogger(UMBLoginLogoutFuncs.class);
    private RemoteWebDriver driver;

    public UMBLoginLogoutFuncs(RemoteWebDriver driver) {
        super(driver);
        this.driver =  driver;
        PageFactory.initElements(driver, this);

    }

    public void loginUMB() {
        try {

            logger.info("***************************************************");
            logger.info("******       LAUNCHING UMB ["+System.getProperty("env").toUpperCase()+"]      ******");
            logger.info("***************************************************");

            driver.manage().deleteAllCookies();
        	MainUtil.launchURL(PropertyHelper.getENVProperties("UMB_URL"), driver);
            dictionary.put("USERNAME", PropertyHelper.getENVProperties("UMB_USERNAME"));
            sendKeys(getTextboxUsername(), dictionary.get("USERNAME"), "Username", "", driver);
            dictionary.put("PASSWORD", PropertyHelper.getENVProperties("UMB_PASSWORD"));
            sendKeys(getTextboxPassword(), dictionary.get("PASSWORD"), "Password", "", driver);
            clickElement(getButtonLogin(), "Login Button", driver);
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while logging to UMB  :" + e);
        }
    }
    
    public void logoutUMB() {
        try {
            clickElement(getLinkLogout(), "Logout Button", driver);
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while logging out to UMB  :" + e);
        }
    }
}
