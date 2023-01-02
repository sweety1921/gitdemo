package com.umtest.mobileapps.pagefunction;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.umtest.mobileapps.pageobject.MobileAppLoginPage;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.PropertyHelper;
import com.umtest.testframe.listener.ExtentTestNGITestListener;

import java.time.Duration;

public class MobileAppLoginFuncs extends MobileAppLoginPage {

    private static Logger logger = LogManager.getLogger(MobileAppLoginFuncs.class);
    private AndroidDriver driver;

    public MobileAppLoginFuncs(RemoteWebDriver driver) {
        super((AndroidDriver) driver);
        this.driver = (AndroidDriver) driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);

    }


    public void loginMobileApp_bkp(String msisdn) {
        try {

            logger.info("#########################################################################################################################################");
            logger.info("******                 LAUNCHING MOBILEAPP ["+System.getProperty("env").toUpperCase()+"("+System.getProperty("mobileAppEnv")+")] ******");
            logger.info("#########################################################################################################################################");

            ((AndroidDriver) driver).resetApp();
            Thread.sleep(2000);
            ((AndroidDriver) driver).launchApp();
            Thread.sleep(5000);

            //clickOkButtonIfExistsSorryServiceUnavailableError();
            handleNewUpdatesDialog();
           //selectENV();

            String pin = ApplicationUtil.getSelfcareLoginPin(msisdn);

            if (pin == null) {
                clickElement(getButtonFirstTimeLogin(), "First Time Login Button", driver);
                sendKeys(getTextboxFirstTimeLoginMobileNumber(), msisdn, "Mobile Number", "", driver);
                clickElement(getButtonLoginFirstTime(), "First Time Login 'Continue' Button", driver);
                sendKeys(getTextboxPIN(), ApplicationUtil.getSelfcareLoginPin(msisdn), "PIN", "", driver);
            } else {
                clickElement(getButtonLogin(), "Login Button", driver);
                sendKeys(getTextboxMobileNumber(), msisdn, "Mobile Number", "", driver);
                sendKeys(getTextboxPIN(), pin, "PIN", "", driver);
            }

            Thread.sleep(3000);

            hideKeyboard(driver);
            Thread.sleep(3000);

            if (verifyElementIsDisplayed(getButtonWelcomeScreenCancel(), "Welcome Screen", driver)) {
                clickElement(getButtonWelcomeScreenCancel(), "Welcome Screen Cancel Button", driver);
            }

            ignoreUpdateNowDialog();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred while logging to Mobile App  :" + e);
        }
    }


    public boolean loginMobileApp(String sMSISDN) {
        try {

            logger.info("#########################################################################################################################################");
            logger.info("******                 LAUNCHING MOBILEAPP ["+System.getProperty("env").toUpperCase()+"("+PropertyHelper.getProperties("MOBILEAPP_ENV")+")] ******");
            logger.info("#########################################################################################################################################");

            ((AndroidDriver) driver).resetApp();
            Thread.sleep(1000);
            ((AndroidDriver) driver).launchApp();
            Thread.sleep(1000);

           // handleNewUpdatesDialog();

            selectENV(System.getProperty("env"));

            String pin = ApplicationUtil.getSelfcareLoginPin(sMSISDN);

            if (pin == null) {
                clickLoginButton();
                clickFirstTimeLoginButton();
                enterFirstTimeLoginMobileNumber(sMSISDN);
                clickContinueFirstTimeLoginButton();
                pin = ApplicationUtil.getSelfcarefirsttimeLoginPin(sMSISDN);
                sendKeys(getOPTPIN(), pin, "OTP", "", driver);
                enterNewpin("123456");
                enterNewPinconfirm("123456");
                clickcreatepinbutton();
                ((AndroidDriver) driver).resetApp();
                Thread.sleep(1000);
                ((AndroidDriver) driver).launchApp();
                Thread.sleep(1000);
                selectENV(System.getProperty("env"));
                clickLoginButton();
                enterMSISDN(sMSISDN);
                enterPIN(sMSISDN);
            } else {
                clickLoginButton();
                enterMSISDN(sMSISDN);
                enterPIN(sMSISDN);
            }

            hideKeyboard(driver);
            Thread.sleep(5000);
            clickCancelOnWelcomeScreen();
            captureAppVersion(driver);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred while logging to Mobile App  :" + e);
            return false;
        }
    }

    public void ignoreUpdateNowDialog() throws InterruptedException {
        if(isUpdateNowButtonExists()){
            clickUpdateNowButton();
            Thread.sleep(2000);
            clickBackArrowMyProfile();
        }
    }

    public void selectENV(String env) {
        try {
               clickElement(getButtonENVSetting(), "Environment Setting Button", driver);
               // clickEnvSettingsButton();
                Thread.sleep(200);

                if(PropertyHelper.getProperties("MOBILEAPP_ENV").equalsIgnoreCase("Stage 3")) {
                    clickElement(getSelectStage3(), "Stage 3", driver);
                    System.out.println("Mobile App - Environment Selected : "+PropertyHelper.getENVProperties("MOBILEAPP_ENV"));
                    Thread.sleep(200);
                    clickElement(getButtonOK(), "OK Button", driver);
                }else
                {
                    System.out.println("******INVALID Mobile App Environment Selected : "+System.getProperty("mobileapp.env"));
                }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while selecting environment "+env+"  :" + e);
        }
    }

    public void clickEnvSettingsButton(){
        pressKeyTAB(driver);
        pressKeyENTER(driver);
        System.out.println("Clicked on 'Environment Settings' button");
    }

   /* public void clickCancelOnWelcomeScreen() throws InterruptedException {
            clickElement(getButtonWelcomeScreenCancel(), "Welcome Screen Cancel Button", driver);
    }*/

    public void clickCancelOnWelcomeScreen() throws InterruptedException {
        String sTutorialsXPath = "//*[@content-desc=\"tutorials_button_close\"]";

        AppWait.visibilityOfElementPresent(driver,By.xpath(sTutorialsXPath),"Welcome Screen Cancel (cross mark)",25);

        if (isElementExists(By.xpath(sTutorialsXPath), driver)) {
            clickElement(getButtonWelcomeScreenCancel(), "Welcome Screen Cancel Button", driver);
        }
    }

    public void captureAppVersion(AndroidDriver driver) throws InterruptedException {

        clickElement(getSideMenu(),"Side Menu",driver);
        captureAppScreenshot("Mobile App Version Screen Capture", driver);
        clickElement(getMenuHome(),"Home Menu",driver);
        getSideMenu();
    }

}
