package com.umtest.selfcare.pagefunction;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.umtest.selfcare.pageobject.SelfcareLoginLogoutPage;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.lib.PropertyHelper;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SelfcareLoginLogoutFuncs extends SelfcareLoginLogoutPage {

	private static Logger logger = LogManager.getLogger(SelfcareLoginLogoutFuncs.class);
	private RemoteWebDriver driver;

	public SelfcareLoginLogoutFuncs(RemoteWebDriver driver) {
		super(driver);
		this.driver =  driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);

	}

	public void loginSelfcare(String msisdn) {
		try {
			logger.info("***************************************************");
			logger.info("******       LAUNCHING SELFCARE ["+System.getProperty("env").toUpperCase()+"]      ******");
			logger.info("***************************************************");

			driver.manage().deleteAllCookies();
			MainUtil.launchURL(PropertyHelper.getENVProperties("SELFCARE_URL"), driver);
			String pin = ApplicationUtil.getSelfcareLoginPin(msisdn);
			//String pin=null;
			if (pin == null) {
				clickElement(getButtonRetrievePIN(), "PIN Retrieve Button", driver);
				sendKeys(getTextboxMSISDN(), msisdn.trim(), "MSISDN", "", driver);
				clickElement(getButtonSubmit(), "Submit Button", driver);

				Thread.sleep(3000);
				pin = ApplicationUtil.getSelfcarefirsttimeLoginPin(msisdn);
				sendKeys(getOPTPIN(), pin, "OTP", "", driver);
				clickElement(getOPTSUBMIT(), "Selfcare OTPSubmit Button", driver);

				sendKeys(getNewPin(), "123456", "NewPin", "", driver);
				sendKeys(getConfirmNewPin(), "123456", "ConfirmNewPIN", "", driver);
				clickElement(getSuccessfulsubmit(), "Selfcare SuccessfulSubmit Button", driver);
				clickElement(getclickLoginbutton(), "Selfcare Login Button", driver);

				pin = ApplicationUtil.getSelfcareLoginPin(msisdn);
				//clickElement(getButtonSelfcareLogin(), "Selfcare Login Button", driver);
			}

			sendKeys(getTextboxMSISDN(), msisdn, "MSISDN", "", driver);
			sendKeys(getTextboxPassword(), pin, "PIN", "", driver);
			clickElement(getButtonLogin(), "Login Button", driver);

			if (verifyElementExistUsingXpathString("//*[contains(text(),'We are sorry!')]", "FirstTime Not complete", driver)) {
				//clickElement(getButtonClose(), "Close Button", driver);
				Actions action = new Actions(driver);
				action.sendKeys(Keys.ESCAPE).build().perform();

				/*clickElement(getButtonRetrievePIN(), "PIN Retrieve Button", driver);
				sendKeys(getTextboxMSISDN(), msisdn, "MSISDN", "", driver);
				clickElement(getButtonSubmit(), "Submit Button", driver);
				clickElement(getButtonClose(), "Close Button", driver);
				pin = ApplicationUtil.getSelfcareLoginPin(msisdn);
				clickElement(getButtonSelfcareLogin(), "Selfcare Login Button", driver);*/

				clickElement(getButtonRetrievePIN(), "PIN Retrieve Button", driver);
				sendKeys(getTextboxMSISDN(), msisdn, "MSISDN", "", driver);
				clickElement(getButtonSubmit(), "Submit Button", driver);
				//clickElement(getButtonClose(), "Close Button", driver);
				pin = ApplicationUtil.getSelfcarefirsttimeLoginPin(msisdn);
				sendKeys(getOPTPIN(), pin, "OTP", "", driver);
				clickElement(getOPTSUBMIT(), "Selfcare OTPSubmit Button", driver);

				sendKeys(getNewPin(), "123456", "NewPin", "", driver);
				sendKeys(getConfirmNewPin(), "123456", "ConfirmNewPIN", "", driver);
				clickElement(getSuccessfulsubmit(), "Selfcare SuccessfulSubmit Button", driver);
				clickElement(getclickLoginbutton(), "Selfcare Login Button", driver);

				pin = ApplicationUtil.getSelfcareLoginPin(msisdn);

				sendKeys(getTextboxMSISDN(), msisdn, "MSISDN", "", driver);
				sendKeys(getTextboxPassword(), pin, "PIN", "", driver);
				clickElement(getButtonLogin(), "Login Button", driver);

			}

			if(!System.getProperty("env").equalsIgnoreCase("prod")) {
				ApplicationUtil.getAccountDetails(msisdn);
				if (verifyElementExistUsingXpathString("//a[contains(text(),'Update now')]", "Update Now Button", driver)) {
					clickElement(getbuttonUpdatenow(), "Update Now Button", driver);
					clickElement(getIDtypecombobox(), "IDType Button", driver);
					if (MainUtil.dictionary.get("CUSTOMER_ID_TYPE").contains("NRIC")) {
						clickElement(getSelectIDtypeNRIC(), "Select ID", driver);
					} else {
						clickElement(getSelectIDtypePassport(), "Select ID", driver);
					}

					sendKeys(getTypeIDNo(), MainUtil.dictionary.get("CUSTOMER_ID"), "ID Number", "", driver);
					sendKeys(getcustname(), MainUtil.dictionary.get("CUSTOMER_NAME"), "CustomerName", "", driver);
					clickElement(getprofileconfirmcheckbox(), "Confirm Checkbox", driver);
					clickElement(getprofileacknowledgebutton(), "acknowledge button", driver);
					clickElement(getGenderMalebutton(), "GenderMale button", driver);
					clickElement(getNationalitybutton(), "Nationality button", driver);
					if (MainUtil.dictionary.get("CUSTOMER_ID_TYPE").contains("NRIC")) {
						clickElement(getSelectNationality("Malaysia"), "Nationality Select", driver);
					} else {
						clickElement(getSelectNationality("Albania"), "Nationality Select", driver);
					}

					clickElement(getRace(), "Race Button", driver);
					clickElement(getSelectRace(), "Select Race", driver);
					sendKeys(getEmailAddress(), MainUtil.dictionary.get("CUSTOMER_EMAIL"), "Type Email id", "", driver);
					sendKeys(getPreOffPhoneNo(), dictionary.get("CUSTOMER_NUMBER").substring(1, 4), "pre off Phone Number", "", driver);
					sendKeys(getOffPhoneNo(), dictionary.get("CUSTOMER_NUMBER").substring(4), "off phone no", "", driver);
					clickElement(getagreeterm(), "Agreement button", driver);
					clickElement(getsave_contact(), "Save contact Button", driver);
					clickElement(getButtonClose(), "Close Button", driver);
				}

				if (verifyElementExistUsingXpathString("//input[@value='Close']", "Service Unavailable Message", driver)) {
					clickElement(getButtonClose(), "Close Button", driver);
					sendKeys(getTextboxMSISDN(), msisdn, "MSISDN", "", driver);
					sendKeys(getTextboxPassword(), pin, "PIN", "", driver);
					clickElement(getButtonLogin(), "Login Button", driver);
					if (verifyElementExistUsingXpathString("//a[contains(text(),'Update now')]", "Update Now Button", driver)) {
						clickElement(getbuttonUpdatenow(), "Update Now Button", driver);
						clickElement(getIDtypecombobox(), "IDType Button", driver);
						if (MainUtil.dictionary.get("CUSTOMER_ID_TYPE").contains("NRIC")) {
							clickElement(getSelectIDtypeNRIC(), "Select ID", driver);
						} else {
							clickElement(getSelectIDtypePassport(), "Select ID", driver);
						}
						sendKeys(getTypeIDNo(), MainUtil.dictionary.get("CUSTOMER_ID"), "ID Number", "", driver);
						sendKeys(getcustname(), MainUtil.dictionary.get("CUSTOMER_NAME"), "CustomerName", "", driver);
						clickElement(getprofileconfirmcheckbox(), "Confirm Checkbox", driver);
						clickElement(getprofileacknowledgebutton(), "acknowledge button", driver);
						clickElement(getGenderMalebutton(), "GenderMale button", driver);
						clickElement(getNationalitybutton(), "Nationality button", driver);
						if (MainUtil.dictionary.get("CUSTOMER_ID_TYPE").contains("NRIC")) {
							clickElement(getSelectNationality("Malaysia"), "Nationality Select", driver);
						} else {
							clickElement(getSelectNationality("Albania"), "Nationality Select", driver);
						}
						clickElement(getRace(), "Race Button", driver);
						clickElement(getSelectRace(), "Select Race", driver);
						sendKeys(getEmailAddress(), MainUtil.dictionary.get("CUSTOMER_EMAIL"), "Type Email id", "", driver);
						sendKeys(getPreOffPhoneNo(), dictionary.get("CUSTOMER_NUMBER").substring(1, 4), "pre off Phone Number", "", driver);
						sendKeys(getOffPhoneNo(), dictionary.get("CUSTOMER_NUMBER").substring(4), "off phone no", "", driver);
						clickElement(getagreeterm(), "Agreement button", driver);
						clickElement(getsave_contact(), "Save contact Button", driver);
						clickElement(getButtonClose(), "Close Button", driver);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while logging to Selfcare  :" + e);
		}
	}

	public void verifySelfcareBalanceAndPlanName() {
		try {

			String sExpectedRatePlanName=mapSelfcareRatePlanNames(MainUtil.dictionary.get("PLAN_NAME"));

			clickElement(getButtonManageAccount(), "Manage Account Button", driver);
			clickElement(getButtonAccountDetails(), "Account Details Button", driver);

			compareInString(getLabelPlanName().getText(), sExpectedRatePlanName, "Plan Name", driver);
			compareInString(getLabelAccountBalance().getText(), "MYR "+MainUtil.dictionary.get("MAIN_BALANCE"), "Account Balance", driver);
//			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//			LocalDateTime cycleEndDate =  LocalDateTime.now();
//			if(MainUtil.dictionary.get("PLAN_NAME").contains("MB40")){
//
//				MainUtil.dictionary.put("CYCLE_END_DATE",dtf.format(cycleEndDate.plusDays(Integer.parseInt(dictionary.get("TOPUP_AMOUNT")))));
//			}
			if(MainUtil.dictionary.get("CYCLE_END_DATE")==null) {
				compareInString(getLabelExpiryDate().getText(), "Expiry Date: " + MainUtil.dictionary.get("ACTIVE_END_DATE"), "Expiry Date", driver);
			}
			else{
				compareInString(getLabelExpiryDate().getText(), "Expiry Date: " + MainUtil.dictionary.get("CYCLE_END_DATE"), "Expiry Date", driver);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying Selfcare Balance & Planname  :" + e);
		}
	}

	public void verifySelfcareAccountBalanceCreditShareBalanceAndPlanName() {
		try {

			String sExpectedRatePlanName=mapSelfcareRatePlanNames(MainUtil.dictionary.get("PLAN_NAME"));

			compareInString(captureCreditBalance(), "MYR "+MainUtil.dictionary.get("MAIN_BALANCE"), "Account Balance", driver);
			compareInString(captureCreditBalanceExpiryDate(), "Expiry Date: "+MainUtil.dictionary.get("ACTIVE_END_DATE"), "Expiry Date", driver);
			compareInString(captureCreditShareBalance(), "MYR "+MainUtil.dictionary.get("CREDIT_AMOUNT"), "Credit Share Balance", driver);

			clickElement(getButtonManageAccount(), "Manage Account Button", driver);
			clickElement(getButtonAccountDetails(), "Account Details Button", driver);

			compareInString(captureRatePlanName(), sExpectedRatePlanName, "Plan Name", driver);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying Selfcare Balance & Planname  :" + e);
		}
	}

	public void verifySelfcarePostpaidplandetail() {
		try {

			clickElement(getButtonManageAccount(), "Manage Account Button", driver);
			clickElement(getButtonAccountDetails(), "Account Details Button", driver);

			compareInString(getMobileNumber().getText(), MainUtil.dictionary.get("MSISDN"), "Mobile Number", driver);
			compareInString(getStatus().getText(), "Active", "Plan Status", driver);
			MainUtil.pressKeyPageDown(driver);
			Thread.sleep(2000);
			//MainUtil.mouseClick(getCurrentRatePlan(),driver);
			WebElement iframeSwitch= driver.findElement(By.xpath("//*[@id=\"page-account-details\"]/section[2]/div"));
			List<WebElement> c = iframeSwitch.findElements(By.xpath("./child::*"));
			// iterate child nodes
			for ( WebElement i : c ) {
				if(i.getText().contains("Current Rate Plan")){
					compareInString(i.getText(), MainUtil.dictionary.get("SELFCARE_PLAN"), "Current Rate Plan", driver);
				}
				System.out.println(i.getText());}

			//compareInString(getCurrentRatePlan().getText(), MainUtil.dictionary.get("SELFCARE_PLAN"), "Current Rate Plan", driver);
			//compareInString(getCreditLimit().getText(), MainUtil.dictionary.get("CREDIT_LIMIT"), "Credit Limit", driver);


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying Selfcare Balance & Planname  :" + e);
		}
	}

	public void logoutSelfcare() {
		try {
			Actions action = new Actions(driver);
			action.sendKeys(Keys.PAGE_DOWN).sendKeys(Keys.PAGE_DOWN).sendKeys(Keys.PAGE_DOWN).build().perform();
			clickElement(getButtonLogout(), "Logout Button", driver);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying logging out from selfcare  :" + e);
		}
	}

	public String mapSelfcareRatePlanNames(String sRatePlanName) {

		if (sRatePlanName.equalsIgnoreCase("Unlimited Funz")) {
			return "Unlimited Funz";
		} else if (sRatePlanName.equalsIgnoreCase("Unlimited Power Plan")) {
			return "Unlimited Power";
		} else if (sRatePlanName.equalsIgnoreCase("MB Prepaid Rate Plan")) {
			return "MB Prepaid";
		} else if (sRatePlanName.equalsIgnoreCase("U Prepaid Plan")) {
			return "U Prepaid Plan";
		} else if (sRatePlanName.equalsIgnoreCase("New U Prepaid Plan")) {
			return "New U Prepaid Plan";
		} else if (sRatePlanName.equalsIgnoreCase("Power Prepaid Plan")) {
			return "Power";
		}else if (sRatePlanName.equalsIgnoreCase("5G Prepaid Plan")) {
			return "5G Prepaid Plan";
		}else
		{
			return "Invalid Rate Plan name";
		}

	}
}


