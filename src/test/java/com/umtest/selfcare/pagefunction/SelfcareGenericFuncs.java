package com.umtest.selfcare.pagefunction;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.umtest.selfcare.pageobject.SelfcareGenericPage;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;


public class SelfcareGenericFuncs extends SelfcareGenericPage {

	private static Logger logger = LogManager.getLogger(SelfcareGenericFuncs.class);
	private RemoteWebDriver driver;

	public SelfcareGenericFuncs(RemoteWebDriver driver) {
		super(driver);
		this.driver =  driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);
	}

	public boolean Postpaidchangepin(String msisdn,String NewPin) {

		boolean methodReturn = false;

		try {
			clickElement(getChangePin(), "Change Pin", driver);
			String pin = ApplicationUtil.getSelfcareLoginPin(msisdn);
			sendKeys(getOldpassword(), pin, "PIN", "", driver);
			sendKeys(getNewpassword(), NewPin, "New PIN", "", driver);
			sendKeys(getretyprNewpassword(), NewPin, "Retry New PIN", "", driver);

			clickElement(getbuttonChangePin(), "Change Pin button", driver);

			clickElement(getbuttonClose(), "Close button", driver);
			methodReturn = true;



		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Change Pin :" + e);
		}
		return methodReturn;
	}

	public boolean PostpaidRoamingandIDDactivation(String msisdn) {

		boolean methodReturn = false;

		try {
			clickElement(getButtonManageAccount(), "Manage Account Button", driver);
			clickElement(getRoamingandIDDService(), "Roaming and IDD service", driver);
			clickElement(getCheckStatus(), "Check Status", driver);
			clickElement(getTermsandcondition(), "Terms and condition ", driver);
			clickElement(getActivateNow(), "Activate Now", driver);

			clickElement(getbuttonClose(), "Close button", driver);
			methodReturn = true;


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Roaming and IDD service :" + e);
		}
		return methodReturn;
	}

	public boolean PostpaidEBillingactivation(String msisdn) {

		boolean methodReturn = false;

		try {
			clickElement(getBillandpayment(), "Bill and Payment Button", driver);
			clickElement(getEBill(), "EBilling service", driver);
			clickElement(getSwitchEBillbutton(), "SwitchEbill Button", driver);
			sendKeys(getSelfcareebillemail(), "AUTOTEST@UM.COM", "Enter Email for Ebill", "", driver);

			clickElement(getSelfcareebillsubmit(), "Submit button ", driver);

			methodReturn = true;


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Ebill :" + e);
		}
		return methodReturn;
	}

	public boolean PostpaidAutodebitactivation(String msisdn) {

		boolean methodReturn = false;

		try {
			clickElement(getBillandpayment(), "Bill and Payment Button", driver);
			clickElement(getAutodebit(), "EBilling service", driver);
			clickElement(getbuttonLetsGo(), "SwitchEbill Button", driver);
			sendKeys(getAutodebitcardnumber(), MainUtil.dictionary.get("CardNumber"), "Enter CardNumber for AutoDebit", "", driver);

			sendKeys(getAutodebitcardname(), "TestAuto", "Enter CardName for AutoDebit", "", driver);

			clickElement(getbuttonmonth(), "Month Button", driver);

			clickElement(getSelectmonth(), "Select Month", driver);

			clickElement(getbuttonyear(), "Year Button", driver);

			clickElement(getSelectyear(), "Select Year", driver);

			sendKeys(getCVVnumber(), "CVVNumber", "Enter CVVNumber for AutoDebit", "", driver);

			clickElement(getConfirmcheckbox(), "Confirm Checkbox", driver);

			clickElement(getbuttonSubmit(), "Submit button ", driver);

			methodReturn = true;


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Autodebit :" + e);
		}
		return methodReturn;
	}


	public boolean UpdateProfile(String msisdn) {

		boolean methodReturn = false;

		try {

			clickElement(getButtonManageAccount(), "Manage Account Button", driver);
			clickElement(getButtonPersonalDetails(), "Personal Details Button", driver);
			clickElement(getButtonEDITPersonalDetails(), "Edit Personal Details Button", driver);
			sendKeys(gettextAddress1(), MainUtil.dictionary.get("Address1"), "Enter Address line1", "", driver);
//			sendKeys(gettextAddress2(), MainUtil.dictionary.get("Address2"), "Enter Address line2", "", driver);
			sendKeys(gettextZipcode(), MainUtil.dictionary.get("Zip"), "Enter ZipCode", "", driver);
			clickElement(getselectZipcode(), "Zip Code Select", driver);
			sendKeys(gettextEmailAddress(), MainUtil.dictionary.get("EMAILID"), "Enter Email", "", driver);
//			sendKeys(gettextpreoffnumber(), "01", "home Phone number", "",driver);
			sendKeys(gettextprehomenumber(), MainUtil.dictionary.get("Homenumber").substring(0,4), "home Phone number", "",driver);
			clickElement(getSelectPrefixHomePhone(), "Prefix Home Phone number Select", driver);
			sendKeys(gettexthomenumber(), MainUtil.dictionary.get("Homenumber").substring(4), "home Phone number", "", driver);
//			sendKeys(gettextprefaxnumber(), "", "Fax Phone number", "",driver);
//			sendKeys(gettextFaxnumber(), "", "Fax Phone number", "", driver);
			clickElement(gettextagreement(), "Click on agreement checkbox", driver);
			clickElement(getSavepersonaldetail(), "Click on Save Personal detail button", driver);
			compareInString(gettextsucessmsg().getText(), "Success", "Success Msg ", driver);
//			clickElement(gettextclosebtn(), "Click on Close button", driver);
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ESCAPE).build().perform();
			methodReturn = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Autodebit :" + e);
		}
		return methodReturn;
	}
}
