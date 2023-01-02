package com.umtest.mobileapps.pagefunction;

import com.umtest.testframe.base.DriverFactory;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.umtest.mobileapps.pageobject.MobileAppBunldePurchasePage;
import com.umtest.mobileapps.pageobject.MobileAppTopupPage;
import com.umtest.mobileapps.testcases.MobileAppBundlePurchase;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MobileAppBundlePurchaseFuncs extends MobileAppBunldePurchasePage {

	private static Logger logger = LogManager.getLogger(MobileAppBundlePurchaseFuncs.class);
	private AndroidDriver driver;

	public MobileAppBundlePurchaseFuncs(RemoteWebDriver driver) {
		super((AndroidDriver) driver);
		this.driver = (AndroidDriver) driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);

	}

	public void statusTest(){
		DriverFactory.quitMobileApp(driver);
	}


	public boolean doBundlePurchase(String bundleName) throws SQLException, ClassNotFoundException {
		boolean returnStatus = false;

		try {
			if (MainUtil.dictionary.get("ACCOUNT_TYPE").equalsIgnoreCase("PREPAID")) {
				MobileAppUtilFuncs utilFuncs = new MobileAppUtilFuncs(driver);
				utilFuncs.storeMobileAppBalace();
			}

			clickElement(getButtonPurchaseAddonPlusButton(),"Add-Ons",driver);
			waitForAddOnsPopUpToLoad();

			//clickElement(selectGX(), "GX Button", driver);

			if (bundleName.toUpperCase().contains("GX")) {
				clickElement(selectGX(), "GX Button", driver);
			} else if (bundleName.toUpperCase().contains("UMI")) {
				clickElement(selectUMI(), "UMI Button", driver);
			} else if (bundleName.toUpperCase().contains("MB")) {
				clickElement(selectMB(), "MB Button", driver);
			} else if (bundleName.toUpperCase().contains("EPIKKK")) {
				clickElement(selectEpikkk(), "EPIKKK Button", driver);
			} else if (bundleName.toUpperCase().contains("GT")) {
				selectEpikkk().click();
				clickElement(selectUnlimitedCall(), "Unlimited Call Button", driver);
			} else if (bundleName.toUpperCase().contains("GAMEONZ")) {
				selectEpikkk().click();
				clickElement(selectGameOnz(), "Game Onz Button", driver);
			}else if (bundleName.toUpperCase().contains("Booster")) {
			//	selectEpikkk().click();
				clickElement(getselectBooster(), "Booster Button", driver);
			} else if (bundleName.toUpperCase().contains("SMS PLUS")) {
				clickElement(getselectSMSPlus(), "SMS Plus Button", driver);
			} else if (bundleName.toUpperCase().contains("TALK PLUS")) {
				clickElement(getselectTalkPlus(), "Talk Plus Button", driver);
			}

			String buyNowXpath ="(//*[contains(@content-desc,'addOns_"+bundleName+"')]/child::*[@content-desc='button_Buy Now'])[1]";

			int intVar=0;

			while (verifyElementExistUsingXpathString(buyNowXpath, "Buy Now Button", driver)  == false && intVar<30) {
				scrollUDLRMobileApp(driver, 1, "U");
				intVar++;
			}

			if(verifyElementExistUsingXpathString(buyNowXpath, "Buy Now Button", driver)  == false){
				return false;
			}

			clickElementUsingXpathString(buyNowXpath, bundleName+" Buy Now", driver);
			clickElement(getButtonConfirm(), "Confirm Button", driver);

			Thread.sleep(5000);
			if (verifyElementExistUsingXpathString(getButtonContinueString(), "Continue Button", driver)) {
				clickElement(getButtonContinue(), "Continue Button", driver);
			}

			Thread.sleep(5000);
			boolean checkFlag = checkForText("", getRequestSubmittedMessage(), "Request Submitted", "Request Submitted message", driver);

			if (checkFlag) {
				clickElement(getButtonClose(), "Close Button", driver);
				Thread.sleep(2000);
				if(bundleName.equalsIgnoreCase("MB-WEEK")){
					dictionary.put("BUNDLE_NAME", "MBWEEK");
				}else{
					dictionary.put("BUNDLE_NAME", bundleName.replaceAll("\\s",""));
				}
				if (MainUtil.dictionary.get("ACCOUNT_TYPE").equalsIgnoreCase("PREPAID")) {
					ApplicationUtil.getBundleDetails("PREPAID", MainUtil.dictionary.get("BUNDLE_NAME"));
					ApplicationUtil.getUMBDetails("PREPAID", MainUtil.dictionary.get("CATEGORY"));
					String bundlePurchaseDate = MainUtil.getBundlePurchaseDate();
					String bundleExpiry = MainUtil.calculateBundleExpiry(MainUtil.dictionary.get("BUNDLE_NAME"));
					String updatedBalance = MainUtil.calculateBalance(MainUtil.dictionary.get("CURRENT_MAIN_BALANCE"), MainUtil.dictionary.get("PRICE"));

					MainUtil.dictionary.put("UPDATED_BALANCE", updatedBalance);
					MainUtil.dictionary.put("UPDATED_BUNDLE_EXPIRYDATE", bundleExpiry);

					ApplicationUtil.updateAccInfoAfterBundle("PREPAID", MainUtil.dictionary.get("MSISDN"), updatedBalance, MainUtil.dictionary.get("BUNDLE_NAME"), bundlePurchaseDate, bundleExpiry);
					returnStatus = true;
				} else {
					String updatedBalance = null;
					//dictionary.put("BUNDLE_NAME", bundleName.replaceAll("\\s",""));
					String bundlePurchaseDate = MainUtil.getBundlePurchaseDate();
					String bundleExpiry = MainUtil.calculateBundleExpiry(MainUtil.dictionary.get("BUNDLE_NAME"));
					ApplicationUtil.updateAccInfoAfterBundle("POSTPAID", MainUtil.dictionary.get("MSISDN"), updatedBalance, MainUtil.dictionary.get("BUNDLE_NAME"), bundlePurchaseDate, bundleExpiry);
					returnStatus = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while doing bundle purchase" + e);
		}finally {
			DriverFactory.quitMobileApp(driver);
		}

		return returnStatus;
	}

	
	public boolean doBundlePurchase_Postpaid(String bundleName) {

		boolean returnStatus = false;

		try {
			
			MobileAppUtilFuncs utilFuncs = new MobileAppUtilFuncs(driver);
			utilFuncs.storeMobileAppBalace();

			clickElement(getButtonPurchaseAddon(), "Addon Button", driver);
			Thread.sleep(2000);


			if (bundleName.toUpperCase().contains("Booster")) {
				clickElement(getselectBooster(), "Booster Button", driver);

			} else if (bundleName.toUpperCase().contains("SMS Plus")) {		
				clickElement(getselectSMSPlus(), "SMS Plus Button", driver);

			} else if (bundleName.toUpperCase().contains("Talk Plus")) {
				clickElement(getselectTalkPlus(), "Talk Plus Button", driver);
			
			}

			String buyNowXpath = "//android.view.ViewGroup[contains(@content-desc,'addOns_"+bundleName+"')]//android.view.ViewGroup[contains(@content-desc,'button_Buy Now')]";

			while (verifyElementExistUsingXpathString(buyNowXpath, "Buy Now Button", driver)  == false) {
				scrollUDLRMobileApp(driver, 1, "U");
			}

			clickElementUsingXpathString(buyNowXpath, bundleName+" Buy Now", driver);
			clickElement(getButtonConfirm(), "Confirm Button", driver);

			Thread.sleep(1000);
			if (verifyElementExistUsingXpathString(getButtonContinueString(), "Continue Button", driver)) {
				clickElement(getButtonContinue(), "Continue Button", driver);
			}


			Thread.sleep(2000);
			boolean checkFlag = checkForText("", getSuccessMessage(), "Successful", "Success Message", driver);
			//boolean checkFlag = true;
			
			
			if (checkFlag) {
				clickElement(getButtonClose(), "Close Button", driver);
				
				String updatedBalance = null;
				dictionary.put("BUNDLE_NAME", bundleName.replaceAll("\\s",""));
				String bundlePurchaseDate = MainUtil.getBundlePurchaseDate();
				String bundleExpiry = MainUtil.calculateBundleExpiry(MainUtil.dictionary.get("BUNDLE_NAME"));
				ApplicationUtil.updateAccInfoAfterBundle("POSTPAID", MainUtil.dictionary.get("MSISDN"), updatedBalance, MainUtil.dictionary.get("BUNDLE_NAME"), bundlePurchaseDate, bundleExpiry);
				returnStatus = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Bundle Purchase" + e);
		}
		return returnStatus;
	}
	
	public boolean doGolifePurchase(String Golife) {

		boolean returnStatus = false;

		try {
			
			clickElement(getMenuIcon(), "Click Menu Icon", driver);
			scrollUDLR(driver, 0, "U");
			clickElement(getMenuServices(), "Click Services button", driver);
			scrollUDLR(driver, 0, "U");
			clickElement(getMenuGoLife(), "Click Golife button", driver);
				
			scrollUDLR(driver, 0, "U");
			String Insuranceplanname;
			if (Golife.equals("GOLIFE5")) {
				Insuranceplanname = "GoLife 5";
				clickElement(getButtonGoLife5(), "Click Golife5 insurance Section", driver);
			} else {
				Insuranceplanname = "GoLife 10";
				clickElement(getButtonGoLife10(), "Click Golife10 insurance Section", driver);
			}
			
			clickElement(getButtonSubscribe(), "Click Subscriber Button", driver);
			Thread.sleep(5000);
			scrollUDLR(driver, 0, "U");
			
			String email = driver.findElementByXPath("//android.widget.EditText[@content-desc='goLife_textField_email']").getAttribute("text");
        	System.out.println(email);
        	
        	sendKeys(getConfirmEmailaddress(), email, "MSISDN", "", driver);
			
        	clickElement(getNextbutton(), "Click Next Button", driver);
        	
        	clickElement(getYesbutton(), "Click Yes Radio Button", driver);
        	
        	clickElement(getConfirmcheckbox(), "Click Confirm Checkbox Button", driver);
        	
        	clickElement(getNextbutton(), "Click Next Button", driver);
        	
        	clickElement(getTermandconditioncheckbox(), "Click Terms&condition Checkbox Button", driver);
        	
        	clickElement(getButtonSubscribe(), "Click Subscriber Button", driver);
        	
        	clickElement(getYesbutton(), "Click Yes Radio Button", driver);
        	
        	Thread.sleep(5000);
        	
						
			if (verifyElementExistUsingXpathString(getGolifesuccessMessage(), "getGolife success Message", driver)) {
				clickElement(getClosebutton(), "Click Close Button", driver);
				
				clickElement(getMenuGoLife(), "Click Golife button", driver);
				
				scrollUDLR(driver, 0, "U");
				
				if (Golife.equals("GOLIFE5")) {
					Insuranceplanname = "GoLife 5";
					clickElement(getButtonGoLife5(), "Click Golife5 insurance Section", driver);
				} else {
					Insuranceplanname = "GoLife 10";
					clickElement(getButtonGoLife10(), "Click Golife10 insurance Section", driver);
				}
				
				clickElement(getViewCertificatebutton(), "Click View Certificate button", driver);
				
				for (int i =3;i<7;i++) {
					
					List<WebElement> childs = driver.findElementsByXPath("//android.view.ViewGroup[2]/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup["+i+"]");
					for(WebElement element:childs)
				     {
						
						if (i==3) {
							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
					        LocalDateTime todaysDate =  LocalDateTime.now();
					        String subscribtionDate = dtf.format(todaysDate);
							compareInString(element.findElement(By.xpath("(//*[@class='android.widget.TextView'])[1]")).getText(), "Date of Subscription", "Date of Subscription description", driver);
							compareInString(element.findElement(By.xpath("(//*[@class='android.widget.TextView'])[2]")).getText(), subscribtionDate, "Date of Subscription", driver);
						} else if (i==4) {
							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
					        LocalDateTime todaysDate =  LocalDateTime.now();
					        LocalDateTime currentmonth = todaysDate.plusMonths(1);
					        LocalDateTime currentday = todaysDate.plusDays(30);
				            String Nextchargingdate = dtf.format(currentmonth);
				            System.out.println(Nextchargingdate);
							compareInString(element.findElement(By.xpath("(//*[@class='android.widget.TextView'])[1]")).getText(), "Date of next charging", "Date of Next Charging description", driver);
							compareInString(element.findElement(By.xpath("(//*[@class='android.widget.TextView'])[2]")).getText(), Nextchargingdate, "Date of Next Charging", driver);
						} else if (i==5) {
							compareInString(element.findElement(By.xpath("(//*[@class='android.widget.TextView'])[1]")).getText(), "Plan", "Plan description", driver);
							compareInString(element.findElement(By.xpath("(//*[@class='android.widget.TextView'])[2]")).getText(), Insuranceplanname, "Plan Name", driver);
						} else {
							compareInString(element.findElement(By.xpath("(//*[@class='android.widget.TextView'])[1]")).getText(), "Status", "Status description", driver);
							compareInString(element.findElement(By.xpath("(//*[@class='android.widget.TextView'])[2]")).getText(), "ACTIVE", "Plan Status", driver);
						}
				     }
				}
				returnStatus = true;
			}
        	
        	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Golife Purchase" + e);
		}
		return returnStatus;
	}


	
}
