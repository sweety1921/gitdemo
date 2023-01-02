package com.umtest.mobileapps.pagefunction;

import java.time.Duration;

import com.umtest.mobileapps.pageobject.MobileAppLoginPage;
import com.umtest.testframe.lib.PropertyHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.umtest.mobileapps.pageobject.MobileAppProfileandloginupdatePage;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class MobileAppProfileandloginupdateFuncs extends MobileAppProfileandloginupdatePage {

	private static Logger logger = LogManager.getLogger(MobileAppLoginFuncs.class);
    private AndroidDriver driver;

    public MobileAppProfileandloginupdateFuncs(RemoteWebDriver driver) {
        super((AndroidDriver) driver);
        this.driver = (AndroidDriver) driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);
    }
    
    public boolean Resetpin() {

		boolean returnStatus = false;

		try {
			((AndroidDriver) driver).resetApp();
    		((AndroidDriver) driver).launchApp();
			Thread.sleep(4000);
			selectENV(System.getProperty("env"));
			clickLoginButton();
    		clickElement(getbuttonforgotpin(), "Forgot PIN Button", driver);
    		sendKeys(getforgotpinmsisdn(), MainUtil.dictionary.get("MSISDN"), "Mobile Number", "", driver);
//    		clickElement(getforgotpinsubmit(), "Forgot PIN Submit", driver);
			clickElement(getSendOtpButton(), "Send OTP click", driver);
			String pin = ApplicationUtil.getSelfcarefirsttimeLoginPin(MainUtil.dictionary.get("MSISDN"));
			sendKeys(getOPTPIN(), pin, "OTP", "", driver);
			enterNewpin("112233");
			enterNewPinconfirm("112233");
			clickcreatepinbutton();
//    		clickElement(getforgotpinConfirm(), "Forgot PIN Confirm", driver);

    		returnStatus = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while doing Paybill" + e);
		}
		return returnStatus;
	}
    
    public boolean Changepin() {

		boolean returnStatus = false;

		try {
			
			clickElement(getMenuIcon(), "Click Menu Icon", driver);
			clickElement(getMenuSettings(), "Click on Settings menu", driver);
						
    		clickElement(getbuttonChangePin(), "Change PIN Button", driver);
    		sendKeys(getCurrentPin(), ApplicationUtil.getSelfcareLoginPin(MainUtil.dictionary.get("MSISDN")), "Current PIN", "", driver);
			sendKeys(getNewPin(), MainUtil.dictionary.get("NewPin"), "New PIN", "", driver);
			sendKeys(getConfirmNewPin(), MainUtil.dictionary.get("NewPin"), "Confirm New PIN", "", driver);
//    		clickElement(getforgotpinsubmit(), "Forgot PIN Submit", driver);
//    		clickElement(getforgotpinConfirm(), "Forgot PIN Confirm", driver);
			clickElement(getUpdatePin(), "Update Pin", driver);
    		returnStatus = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Paybill" + e);
		}
		return returnStatus;
	}
    
    
    public boolean UpdateProfile() {

		boolean returnStatus = false;

		try {
			
			clickElement(getMenuIcon(), "Click Menu Icon", driver);
			clickElement(getMenuMYACCOUNT(), "Click on MYACCOUNT menu", driver);
			clickElement(getMenuMYPROFILE(), "Click on MYProfile menu", driver);
						
    		clickElement(getbuttonEDIT(), "Click on EDIT Button", driver);
    		
    		sendKeys(getEMAILID(), MainUtil.dictionary.get("EMAILID"), "Enter Email", "", driver);
    		
    		//clickElement(getGENDER(), "Click on Gender Button", driver);
    		
    		//clickElement(getGendermale(), "Click on Male Button", driver);
			scrollUDLR(driver, 1, "U");
			scrollUDLR(driver, 1, "U");
    		clickElement(getMYADDRESS(), "Click on My Address link", driver);
			scrollUDLR(driver, 1, "U");
    		sendKeys(getADDRESSLINE1(), "Address1", "Enter Address1", "", driver);
    		sendKeys(getADDRESSLINE2(), "Address2", "Enter Address2", "", driver);
			scrollUDLR(driver, 1, "U");
    		sendKeys(getPOSTCODE(), "51000", "Enter Postcode", "", driver);

			scrollUDLR(driver, 1, "U");
			scrollUDLR(driver, 1, "U");
    		clickElement(getALTERNATECONTACT(), "Click on Alternate Contact link", driver);

			scrollUDLR(driver, 1, "U");
			scrollUDLR(driver, 1, "U");
    		
    		//sendKeys(getALTERNATECONTACTNUMBER(), "60182147777", "Enter Alternate contact number", "", driver);
    		
//    		sendKeys(getHOMECONTACTNUMBER(), "60182148888", "Enter Home contact number", "", driver);
//
//    		sendKeys(getFAXCONTACTNUMBER(), "60182149999", "Enter Fax contact number", "", driver);
    		
    		clickElement(getbuttonSAVE(), "Click on Save Button", driver);
    		
    		clickElement(getbuttonAgree(), "Click on Agree Button", driver);
    		
    		clickElement(getbuttonClose(), "Click on Close Button", driver);
    		    		    		
    		returnStatus = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Paybill" + e);
		}
		return returnStatus;
	}
    
    public boolean IDDIRActivation() {

		boolean returnStatus = false;

		try {
			
			clickElement(getMenuIcon(), "Click Menu Icon", driver);
			clickElement(getMenuSettings(), "Click on Settings menu", driver);
						
    		clickElement(getbuttonRoamingandIDDActivation(), "Roaming and IDD Activation", driver);
						
    		clickElement(getButtoncheckstatus(), "Click on CheckStatus Button", driver);
    		
    		clickElement(getCheckboxtermsandcondition(), "Click on Checkbox terms and condition Checkbox", driver);
    		clickElement(getbuttonActivateNow(), "Click on Activate Now Button", driver);
    		
    		returnStatus = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Paybill" + e);
		}
		return returnStatus;
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
}
