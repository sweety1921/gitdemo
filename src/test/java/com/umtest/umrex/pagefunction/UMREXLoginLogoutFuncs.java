package com.umtest.umrex.pagefunction;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.umtest.testframe.lib.PropertyHelper;
import com.umtest.umrex.pageobject.UMREXLoginLogoutPage;

import java.time.Duration;

public class UMREXLoginLogoutFuncs extends UMREXLoginLogoutPage {

    private static Logger logger = LogManager.getLogger(UMREXLoginLogoutFuncs.class);
    private AndroidDriver driver;

    public UMREXLoginLogoutFuncs(RemoteWebDriver driver) {
        super((AndroidDriver) driver);
        this.driver = (AndroidDriver) driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);

    }


    public void loginUMREX() {
        try {

            logger.info("*****************************************************");
            logger.info("******                 LAUNCHING UMREX ["+System.getProperty("env").toUpperCase()+"] ******");
            logger.info("*****************************************************");

            ((AndroidDriver) driver).resetApp();
        	Thread.sleep(5000);
    		((AndroidDriver) driver).launchApp();
            Thread.sleep(5000);

            if(!System.getProperty("env").equalsIgnoreCase("prod")) {
                selectENV(System.getProperty("env"));
            }

            dictionary.put("USERNAME", PropertyHelper.getENVProperties("UMREX_USERNAME"));
            dictionary.put("PASSWORD", PropertyHelper.getENVProperties("UMREX_PASSWORD"));

            sendKeys(getTextboxUsername(), dictionary.get("USERNAME"), "Username", "", driver);
            sendKeys(getTextboxPassword(), dictionary.get("PASSWORD"), "Password", "", driver);
            hideKeyboard(driver);
            clickElement(getButtonLogin(), "Login Button", driver);
            if(!System.getProperty("env").equalsIgnoreCase("prod")) {
                setBypassSetting();
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while logging to UMREX  :" + e);
        }
    }
    
    public void logoutUMREX() {
        try {
        	clickElement(getBurgerButtonMenu(), "Menu Button", driver);
        	clickElement(getButtonLogout(), "Logout Button", driver);
        	clickElement(getButtonYES(), "Yes Button", driver);
        	
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while logging to UMREX  :" + e);
        }
    }
    
    public void setBypassSetting() {
        try {
            clickElement(getBurgerButtonMenu(), "Menu Button", driver);
            clickElement(getOptionBypassSetting(), "Bypass Setting", driver);
            clickElement(getToggleBypassScanID(), "Bypass Scan ID", driver);
            clickElement(getToggleBypassReadID(), "Bypass Read ID", driver);
            driver.pressKey(new KeyEvent(AndroidKey.BACK));
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while doing Bypass Setting  :" + e);
        }
    }
    
    public void selectENV(String env) {
        try {
            if (env.equalsIgnoreCase("uat")) {
            	clickElement(getEnvSpinnerUAT(), "UAT", driver);
                logger.info("UMREX Environment Selected: "+env.toUpperCase());
			} else if (env.equalsIgnoreCase("PIXEL")){
                clickElement(getEnvSpinnerPIXEL(), "PIXEL", driver);
                logger.info("UMREX Environment Selected: "+env.toUpperCase());
			}
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while selecting environment '"+env+"'  :" + e);
        }
    }


    public void waitForLoadingDialogToDisappear(){

    }

}
