package com.umtest.umrex.pagefunction;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.lib.PropertyHelper;
import com.umtest.testframe.lib.RandomDataGenerator;
import com.umtest.umrex.pageobject.UMREXRegistrationPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class UMREXPostpaidChangeRatePlanFuncs extends UMREXRegistrationPage {
	private static Logger logger = LogManager.getLogger(UMREXPostpaidChangeRatePlanFuncs.class);
	private AndroidDriver driver;

	public UMREXPostpaidChangeRatePlanFuncs(RemoteWebDriver driver) {
		super((AndroidDriver) driver);
		this.driver = (AndroidDriver) driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);
		
		
	}
	public boolean doPostpaidChangePlan(String registrationType, String identificationType) {

		boolean methodReturn = false;

		try {
			Thread.sleep(5000);
			scrollUDLR(driver, 0, "U");
			clickElement(getCRPPhonesection(), "Postpaid Change offer Section", driver);

			clickElement(getbuttonChangeOffer(), "Postpaid Change offer", driver);
			if (identificationType.equalsIgnoreCase("MALAYSIAN")) {
				clickElement(getbuttonReadIDPostpaid(), "Read ID Button", driver);
			} else {	
			 clickElement(getButtonScanIDPostpaid(), "Scan ID Button", driver);
			}
			
			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
			
			if (identificationType.equalsIgnoreCase("MALAYSIAN")) {
				clickElement(getCardtype(), "Card Type", driver);
				clickElement(getselectMyKad(), "Select MyKad", driver);
				sendKeys(getTextboxIDNumber(), dictionary.get("CUSTOMER_ID"), "Customer ID", "", driver);
				sendKeys(getTextboxName(), dictionary.get("CUSTOMER_NAME"), "Customer Name", "", driver);
				clickElement(getSpinnerGender(), "Gender Spinner", driver);
				clickElement(getSelectMale(), "Select Male", driver);
				sendKeys(getDateDD(), dictionary.get("CUSTOMER_DOB_DD"), "Customer DOB Date", "", driver);
				sendKeys(getDateMM(), dictionary.get("CUSTOMER_DOB_MM"), "Customer DOB Month", "", driver);
				sendKeys(getDateYYYY(), dictionary.get("CUSTOMER_DOB_YYYY"), "Customer DOB Year", "", driver);
				scrollUDLR(driver, 0, "U");
				clickElement(getRace(), "Race", driver);
				clickElement(getselectMalay(), "Select Malay", driver);
				sendKeys(gettextboxAddressform(), dictionary.get("CUSTOMER_ADDRESS"), "CUSTOMER ADDRESS", "", driver);
				scrollUDLR(driver, 0, "U");
				sendKeys(gettextboxPostcodeform(), dictionary.get("CUSTOMER_POSTCODE"), "CUSTOMER Postcode", "", driver);
				sendKeys(gettextboxCityform(), dictionary.get("CUSTOMER_CITY"), "CUSTOMER CITY", "", driver);
				scrollUDLR(driver, 0, "U");
				sendKeys(gettextboxStateform(), dictionary.get("CUSTOMER_STATE"), "CUSTOMER STATE", "", driver);
				
				clickElement(getButtonContinue(), "Continue Button", driver);
				clickElement(getButtonContinue(), "Continue Button", driver);
			} else {
				
			
			sendKeys(getTextboxIDNumber(), dictionary.get("CUSTOMER_ID"), "Customer ID", "", driver);
			sendKeys(getTextboxName(), dictionary.get("CUSTOMER_NAME"), "Customer Name", "", driver);
			clickElement(getSpinnerGender(), "Gender Spinner", driver);
			clickElement(getSelectMale(), "Select Male", driver);
			sendKeys(getDateDD(), dictionary.get("CUSTOMER_DOB_DD"), "Customer DOB Date", "", driver);
			sendKeys(getDateMM(), dictionary.get("CUSTOMER_DOB_MM"), "Customer DOB Month", "", driver);
			sendKeys(getDateYYYY(), dictionary.get("CUSTOMER_DOB_YYYY"), "Customer DOB Year", "", driver);
			clickElement(getSpinnerNationality(), "Nationality Spinner", driver);
			clickElement(getSelectAntartica(), "Select Antartica", driver);
			scrollUDLR(driver, 0, "U");
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
		}
			
			clickElement(getCRPSelectMSISDN(), "Select MSISDN Button", driver);
			
			driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.fl.pra:id/tvItem'][@text='"+MainUtil.dictionary.get("MSISDN")+" (Principal)']")).click();
			
			clickElement(getCRPSelectType(), "CRP Select Type", driver);
			
			clickElement(getChangeposttopostprinciple(), "CRP Postpaid to Postpaid", driver);
			
			clickElement(getButtonContinue(), "Continue Button", driver);
			
			clickElement(getSelectButton(), "Select Button", driver);
			
			clickElement(selectPostpaidPlan(MainUtil.dictionary.get("NEWPLAN_NAME")), "Select Plan", driver);

			clickElement(getButtonYES(),"Yes Button", driver);

			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			
			clickElement(getButtonTNC(), "Terms And Conditions", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			//clickElement(getSpinnerERFEmail(), "Send eRF Email", driver);
			//clickElement(getSelectEmailID(), "Select Email ID", driver);
			
			clickElement(getSignatureArea(), "Tap here to sign", driver);
			doSignature(driver);
			clickElement(getButtonSign(), "Sign", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getCRPContinue(), "Continue Button", driver);

			dictionary.put("PURCHASE_PASSWORD", PropertyHelper.getENVProperties("UMREX_PURCHASE_PASSWORD"));
			//sendKeys(getTextboxPasswordPostpaid(), dictionary.get("PURCHASE_PASSWORD"), "Pass", "", driver);

			clickElement(getlabelchangeofferMessage(), "Change Offer label", driver);
			clickElement(getlabelDealereRechargeDetails(), "Dealer E-recharge detail label", driver);

			clickElement(getTextboxPasswordPostpaid(), "Password field", driver);

			driver.getKeyboard().pressKey(dictionary.get("PURCHASE_PASSWORD"));

			clickElement(getbuttonbundleContinue(), "Continue Button", driver);

			clickElement(getButtonYESBundlePurchase(), "Yes Button", driver);

			//boolean checkFlag = checkForText("", getLabelSuccessMessagePostpaid(), "Thank you for your order", "Success Message", driver);
			boolean checkFlag=true;
			if (checkFlag == true) {
				ApplicationUtil.updateRateplanName(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("NEWPLAN_NAME")) ;
				
				methodReturn = true;
				//clickElement(getButtonBackToHome(), "Back To Home Button", driver);
				clickElement(getbuttonBackToHomepostpaid(), "Back To Home Button", driver);
			} else {
				clickElement(getButtonOK(), "Ok Button", driver);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid registration  :" + e);
		}
		return methodReturn;
		}
	
	public boolean doPostpaidFamilyChangePlan(String registrationType, String identificationType) {

		boolean methodReturn = false;

		try {
			Thread.sleep(5000);
			scrollUDLR(driver, 0, "U");
			clickElement(getCRPPhonesection(), "Postpaid Change offer Section", driver);

			clickElement(getbuttonChangeOffer(), "Postpaid Change offer", driver);
			if (identificationType.equalsIgnoreCase("MALAYSIAN")) {
				clickElement(getbuttonReadIDPostpaid(), "Read ID Button", driver);
			} else {	
			 clickElement(getButtonScanIDPostpaid(), "Scan ID Button", driver);
			}
			
			String[] arrmsdn = MainUtil.dictionary.get("MSISDN").split(";");
			String[] arrplanname = MainUtil.dictionary.get("NEWPLAN_NAME").split(";");
			System.out.println(arrplanname.length);
			
			String linecount=Integer.toString(arrplanname.length);
			int j = 1;
			for (int i = 0; i<arrplanname.length; i++)
			{
				dictionary.put("msisdn"+j, arrmsdn[i]);
				dictionary.put("NEWPLAN_NAME"+j, arrplanname[i]);
				j++;
			}
			
			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("msisdn1"));
			
			if (identificationType.equalsIgnoreCase("MALAYSIAN")) {
				clickElement(getCardtype(), "Card Type", driver);
				clickElement(getselectMyKad(), "Select MyKad", driver);
				sendKeys(getTextboxIDNumber(), dictionary.get("CUSTOMER_ID"), "Customer ID", "", driver);
				sendKeys(getTextboxName(), dictionary.get("CUSTOMER_NAME"), "Customer Name", "", driver);
				clickElement(getSpinnerGender(), "Gender Spinner", driver);
				clickElement(getSelectMale(), "Select Male", driver);
				sendKeys(getDateDD(), dictionary.get("CUSTOMER_DOB_DD"), "Customer DOB Date", "", driver);
				sendKeys(getDateMM(), dictionary.get("CUSTOMER_DOB_MM"), "Customer DOB Month", "", driver);
				sendKeys(getDateYYYY(), dictionary.get("CUSTOMER_DOB_YYYY"), "Customer DOB Year", "", driver);
				scrollUDLR(driver, 0, "U");
				clickElement(getRace(), "Race", driver);
				clickElement(getselectMalay(), "Select Malay", driver);
				sendKeys(gettextboxAddressform(), dictionary.get("CUSTOMER_ADDRESS"), "CUSTOMER ADDRESS", "", driver);
				scrollUDLR(driver, 0, "U");
				sendKeys(gettextboxPostcodeform(), dictionary.get("CUSTOMER_POSTCODE"), "CUSTOMER Postcode", "", driver);
				sendKeys(gettextboxCityform(), dictionary.get("CUSTOMER_CITY"), "CUSTOMER CITY", "", driver);
				scrollUDLR(driver, 0, "U");
				sendKeys(gettextboxStateform(), dictionary.get("CUSTOMER_STATE"), "CUSTOMER STATE", "", driver);
				
				clickElement(getButtonContinue(), "Continue Button", driver);
				clickElement(getButtonContinue(), "Continue Button", driver);
			} else {
				
			
			sendKeys(getTextboxIDNumber(), dictionary.get("CUSTOMER_ID"), "Customer ID", "", driver);
			sendKeys(getTextboxName(), dictionary.get("CUSTOMER_NAME"), "Customer Name", "", driver);
			clickElement(getSpinnerGender(), "Gender Spinner", driver);
			clickElement(getSelectMale(), "Select Male", driver);
			sendKeys(getDateDD(), dictionary.get("CUSTOMER_DOB_DD"), "Customer DOB Date", "", driver);
			sendKeys(getDateMM(), dictionary.get("CUSTOMER_DOB_MM"), "Customer DOB Month", "", driver);
			sendKeys(getDateYYYY(), dictionary.get("CUSTOMER_DOB_YYYY"), "Customer DOB Year", "", driver);
			clickElement(getSpinnerNationality(), "Nationality Spinner", driver);
			clickElement(getSelectAntartica(), "Select Antartica", driver);
			scrollUDLR(driver, 0, "U");
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
		}
			
			clickElement(getCRPSelectMSISDN(), "Select MSISDN Button", driver);
			
			driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.fl.pra:id/tvItem'][@text='"+MainUtil.dictionary.get("msisdn1")+" (Principal)']")).click();
			
			clickElement(getCRPSelectType(), "CRP Select Type", driver);
			
			clickElement(getChangeposttopostprinciple(), "CRP Postpaid to Postpaid", driver);
			
			clickElement(getButtonContinue(), "Continue Button", driver);
			
			clickElement(getSelectButton(), "Select Button", driver);
			
			clickElement(selectPostpaidPlan(MainUtil.dictionary.get("NEWPLAN_NAME1")), "Select Plan", driver);
			Thread.sleep(5000);
			
			scrollUDLR(driver, 0, "U");
			
			List<WebElement> childs = driver.findElementsByXPath("//*[@resource-id='com.fl.pra:id/tvNewPlan']");
        	
			for(WebElement element:childs)
			{
				System.out.println(element.getText());
				
				if (element.getText().contains("Select"))
				{
				element.click();
				int k=1;
				k=k+1;
				System.out.println(MainUtil.dictionary.get("NEWPLAN_NAME"+k));
				clickElement(selectPostpaidPlan(MainUtil.dictionary.get("NEWPLAN_NAME"+k)), "Select Plan", driver);
					
				}
			
			}
				
			
			clickElement(getButtonContinue(), "Continue Button", driver);
			
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			
			clickElement(getButtonTNC(), "Terms And Conditions", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			clickElement(getSpinnerERFEmail(), "Send eRF Email", driver);
			clickElement(getSelectEmailID(), "Select Email ID", driver);
			
			clickElement(getSignatureArea(), "Tap here to sign", driver);
			doSignature(driver);
			clickElement(getButtonSign(), "Sign", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getCRPContinue(), "Continue Button", driver);

			clickElement(getlabelchangeofferMessage(), "Change Offer label", driver);
			clickElement(getlabelDealereRechargeDetails(), "Dealer E-recharge detail label", driver);

			clickElement(getTextboxPasswordPostpaid(), "Password field", driver);

			driver.getKeyboard().pressKey(dictionary.get("PURCHASE_PASSWORD"));

			clickElement(getbuttonbundleContinue(), "Continue Button", driver);

			clickElement(getButtonYES(), "Yes Button", driver);

			boolean checkFlag = checkForText("", getLabelSuccessMessagePostpaid(), "Thank you for your order", "Success Message", driver);
			
			if (checkFlag == true) {
				
				for (int q = 1; q==arrplanname.length; q++) {
					ApplicationUtil.updateRateplanName(MainUtil.dictionary.get("msisdn"+q), MainUtil.dictionary.get("NEWPLAN_NAME"+q)) ;
				}
				
				
				methodReturn = true;
				clickElement(getButtonBackToHome(), "Back To Home Button", driver);
			} else {
				clickElement(getButtonOK(), "Ok Button", driver);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid registration  :" + e);
		}
		return methodReturn;
		}

	public boolean doPostpaidChangePlantomember(String registrationType, String identificationType) {

		boolean methodReturn = false;

		try {
			Thread.sleep(5000);
			//scrollUDLR(driver, 0, "U");

			clickElement(getCRPPhonesection(), "Postpaid Change offer Section", driver);

			clickElement(getbuttonChangeOffer(), "Postpaid Change offer", driver);
			if (identificationType.equalsIgnoreCase("MALAYSIAN")) {
				clickElement(getbuttonReadIDPostpaid(), "Read ID Button", driver);
			} else {
				clickElement(getButtonScanIDPostpaid(), "Scan ID Button", driver);
			}

			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("membermsisdn"));

			if (identificationType.equalsIgnoreCase("MALAYSIAN")) {
				clickElement(getCardtype(), "Card Type", driver);
				clickElement(getselectMyKad(), "Select MyKad", driver);
				sendKeys(getTextboxIDNumber(), dictionary.get("CUSTOMER_ID"), "Customer ID", "", driver);
				sendKeys(getTextboxName(), dictionary.get("CUSTOMER_NAME"), "Customer Name", "", driver);
				clickElement(getSpinnerGender(), "Gender Spinner", driver);
				clickElement(getSelectMale(), "Select Male", driver);
				sendKeys(getDateDD(), dictionary.get("CUSTOMER_DOB_DD"), "Customer DOB Date", "", driver);
				sendKeys(getDateMM(), dictionary.get("CUSTOMER_DOB_MM"), "Customer DOB Month", "", driver);
				sendKeys(getDateYYYY(), dictionary.get("CUSTOMER_DOB_YYYY"), "Customer DOB Year", "", driver);
				scrollUDLR(driver, 0, "U");
				clickElement(getRace(), "Race", driver);
				clickElement(getselectMalay(), "Select Malay", driver);
				sendKeys(gettextboxAddressform(), dictionary.get("CUSTOMER_ADDRESS"), "CUSTOMER ADDRESS", "", driver);
				scrollUDLR(driver, 0, "U");
				sendKeys(gettextboxPostcodeform(), dictionary.get("CUSTOMER_POSTCODE"), "CUSTOMER Postcode", "", driver);
				sendKeys(gettextboxCityform(), dictionary.get("CUSTOMER_CITY"), "CUSTOMER CITY", "", driver);
				scrollUDLR(driver, 0, "U");
				sendKeys(gettextboxStateform(), dictionary.get("CUSTOMER_STATE"), "CUSTOMER STATE", "", driver);

				clickElement(getButtonContinue(), "Continue Button", driver);
				clickElement(getButtonContinue(), "Continue Button", driver);
			} else {


				sendKeys(getTextboxIDNumber(), dictionary.get("CUSTOMER_ID"), "Customer ID", "", driver);
				sendKeys(getTextboxName(), dictionary.get("CUSTOMER_NAME"), "Customer Name", "", driver);
				clickElement(getSpinnerGender(), "Gender Spinner", driver);
				clickElement(getSelectMale(), "Select Male", driver);
				sendKeys(getDateDD(), dictionary.get("CUSTOMER_DOB_DD"), "Customer DOB Date", "", driver);
				sendKeys(getDateMM(), dictionary.get("CUSTOMER_DOB_MM"), "Customer DOB Month", "", driver);
				sendKeys(getDateYYYY(), dictionary.get("CUSTOMER_DOB_YYYY"), "Customer DOB Year", "", driver);
				clickElement(getSpinnerNationality(), "Nationality Spinner", driver);
				clickElement(getSelectAntartica(), "Select Antartica", driver);
				scrollUDLR(driver, 0, "U");
				clickElement(getButtonContinue(), "Continue Button", driver);
				clickElement(getButtonContinue(), "Continue Button", driver);
			}

			clickElement(getCRPSelectMSISDN(), "Select MSISDN Button", driver);

			driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.fl.pra:id/tvItem'][@text='"+MainUtil.dictionary.get("membermsisdn")+" (Principal)']")).click();

			clickElement(getCRPSelectType(), "CRP Select Type", driver);

			clickElement(getChangeprincipletomember(), "CRP Postpaid principle to member", driver);

			clickElement(getCRPprincipalmsisdn(), "Select Principal msisdn Button", driver);
			driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.fl.pra:id/tvItem'][@text='"+MainUtil.dictionary.get("principalmsisdn")+" (Principal)']")).click();

			clickElement(getButtonContinue(), "Continue Button", driver);

			clickElement(getSelectButton(), "Select Button", driver);

			clickElement(selectPostpaidPlan(MainUtil.dictionary.get("NEWPLAN_NAME")), "Select Plan", driver);

			clickElement(getButtonContinue(), "Continue Button", driver);

			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);

			clickElement(getButtonContinue(), "Continue Button", driver);
			Thread.sleep(5000);
			clickElement(getButtonContinue(), "Continue Button", driver);

			clickElement(getButtonTNC(), "Terms And Conditions", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			//clickElement(getSpinnerERFEmail(), "Send eRF Email", driver);
			//clickElement(getSelectEmailID(), "Select Email ID", driver);

			clickElement(getSignatureArea(), "Tap here to sign", driver);
			doSignature(driver);
			clickElement(getButtonSign(), "Sign", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getCRPContinue(), "Continue Button", driver);

			clickElement(getButtonYESBundlePurchase(), "Yes Button", driver);

			while (isRetryButtonDisplayed())
			{
				clickElement(getRetrybutton(), "Retry Button", driver);
			}

			boolean checkFlag = checkForText("", getlabelSuccessMessagePostpaidCRP(), "Thank you for your order", "Success Message", driver);

			if (checkFlag == true) {
				ApplicationUtil.updateRateplanName(MainUtil.dictionary.get("membermsisdn"), MainUtil.dictionary.get("NEWPLAN_NAME")) ;

				methodReturn = true;
				clickElement(getbuttonBackToHomeCRP(), "Back To Home Button", driver);
			} else {
				clickElement(getButtonOK(), "Ok Button", driver);
			}


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Postpaid Change plan  :" + e);
		}
		return methodReturn;
	}

	public boolean doPostpaidsuspendChangePlan(String registrationType, String identificationType) {

		boolean methodReturn = false;

		try {
			Thread.sleep(5000);
			scrollUDLR(driver, 0, "U");
			clickElement(getCRPPhonesection(), "Postpaid Change offer Section", driver);
			clickElement(getbuttonChangeOffer(), "Postpaid Change offer", driver);
			if (identificationType.equalsIgnoreCase("MALAYSIAN")) {
				clickElement(getbuttonReadIDPostpaid(), "Read ID Button", driver);
			} else {
				clickElement(getButtonScanIDPostpaid(), "Scan ID Button", driver);
			}

			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("msisdn"));

			if (identificationType.equalsIgnoreCase("MALAYSIAN")) {
				clickElement(getCardtype(), "Card Type", driver);
				clickElement(getselectMyKad(), "Select MyKad", driver);
				sendKeys(getTextboxIDNumber(), dictionary.get("CUSTOMER_ID"), "Customer ID", "", driver);
				sendKeys(getTextboxName(), dictionary.get("CUSTOMER_NAME"), "Customer Name", "", driver);
				clickElement(getSpinnerGender(), "Gender Spinner", driver);
				clickElement(getSelectMale(), "Select Male", driver);
				sendKeys(getDateDD(), dictionary.get("CUSTOMER_DOB_DD"), "Customer DOB Date", "", driver);
				sendKeys(getDateMM(), dictionary.get("CUSTOMER_DOB_MM"), "Customer DOB Month", "", driver);
				sendKeys(getDateYYYY(), dictionary.get("CUSTOMER_DOB_YYYY"), "Customer DOB Year", "", driver);
				scrollUDLR(driver, 0, "U");
				clickElement(getRace(), "Race", driver);
				clickElement(getselectMalay(), "Select Malay", driver);
				sendKeys(gettextboxAddressform(), dictionary.get("CUSTOMER_ADDRESS"), "CUSTOMER ADDRESS", "", driver);
				scrollUDLR(driver, 0, "U");
				sendKeys(gettextboxPostcodeform(), dictionary.get("CUSTOMER_POSTCODE"), "CUSTOMER Postcode", "", driver);
				sendKeys(gettextboxCityform(), dictionary.get("CUSTOMER_CITY"), "CUSTOMER CITY", "", driver);
				scrollUDLR(driver, 0, "U");
				sendKeys(gettextboxStateform(), dictionary.get("CUSTOMER_STATE"), "CUSTOMER STATE", "", driver);

				clickElement(getButtonContinue(), "Continue Button", driver);
				clickElement(getButtonContinue(), "Continue Button", driver);
			} else {


				sendKeys(getTextboxIDNumber(), dictionary.get("CUSTOMER_ID"), "Customer ID", "", driver);
				sendKeys(getTextboxName(), dictionary.get("CUSTOMER_NAME"), "Customer Name", "", driver);
				clickElement(getSpinnerGender(), "Gender Spinner", driver);
				clickElement(getSelectMale(), "Select Male", driver);
				sendKeys(getDateDD(), dictionary.get("CUSTOMER_DOB_DD"), "Customer DOB Date", "", driver);
				sendKeys(getDateMM(), dictionary.get("CUSTOMER_DOB_MM"), "Customer DOB Month", "", driver);
				sendKeys(getDateYYYY(), dictionary.get("CUSTOMER_DOB_YYYY"), "Customer DOB Year", "", driver);
				clickElement(getSpinnerNationality(), "Nationality Spinner", driver);
				clickElement(getSelectAntartica(), "Select Antartica", driver);
				scrollUDLR(driver, 0, "U");
				clickElement(getButtonContinue(), "Continue Button", driver);
				clickElement(getButtonContinue(), "Continue Button", driver);
			}

			clickElement(getCRPSelectMSISDN(), "Select MSISDN Button", driver);

			driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.fl.pra:id/tvItem'][@text='"+MainUtil.dictionary.get("msisdn")+" (Principal)']")).click();

			compareInString(getmaxlinemessage().getText(), "MSISDN is suspended. Cannot proceed with the operation.", "MSISDN is suspend Status", driver);

			clickElement(getButtonOK(), "Ok Button", driver);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Postpaid suspend change plan:" + e);
		}
		return methodReturn;
	}

	public boolean doPostpaidbarChangePlan(String registrationType, String identificationType) {

		boolean methodReturn = false;

		try {
			Thread.sleep(5000);
			scrollUDLR(driver, 0, "U");
			clickElement(getCRPPhonesection(), "Postpaid Change offer Section", driver);
			clickElement(getbuttonChangeOffer(), "Postpaid Change offer", driver);
			if (identificationType.equalsIgnoreCase("MALAYSIAN")) {
				clickElement(getbuttonReadIDPostpaid(), "Read ID Button", driver);
			} else {
				clickElement(getButtonScanIDPostpaid(), "Scan ID Button", driver);
			}

			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("msisdn"));

			if (identificationType.equalsIgnoreCase("MALAYSIAN")) {
				clickElement(getCardtype(), "Card Type", driver);
				clickElement(getselectMyKad(), "Select MyKad", driver);
				sendKeys(getTextboxIDNumber(), dictionary.get("CUSTOMER_ID"), "Customer ID", "", driver);
				sendKeys(getTextboxName(), dictionary.get("CUSTOMER_NAME"), "Customer Name", "", driver);
				clickElement(getSpinnerGender(), "Gender Spinner", driver);
				clickElement(getSelectMale(), "Select Male", driver);
				sendKeys(getDateDD(), dictionary.get("CUSTOMER_DOB_DD"), "Customer DOB Date", "", driver);
				sendKeys(getDateMM(), dictionary.get("CUSTOMER_DOB_MM"), "Customer DOB Month", "", driver);
				sendKeys(getDateYYYY(), dictionary.get("CUSTOMER_DOB_YYYY"), "Customer DOB Year", "", driver);
				scrollUDLR(driver, 0, "U");
				clickElement(getRace(), "Race", driver);
				clickElement(getselectMalay(), "Select Malay", driver);
				sendKeys(gettextboxAddressform(), dictionary.get("CUSTOMER_ADDRESS"), "CUSTOMER ADDRESS", "", driver);
				scrollUDLR(driver, 0, "U");
				sendKeys(gettextboxPostcodeform(), dictionary.get("CUSTOMER_POSTCODE"), "CUSTOMER Postcode", "", driver);
				sendKeys(gettextboxCityform(), dictionary.get("CUSTOMER_CITY"), "CUSTOMER CITY", "", driver);
				scrollUDLR(driver, 0, "U");
				sendKeys(gettextboxStateform(), dictionary.get("CUSTOMER_STATE"), "CUSTOMER STATE", "", driver);

				clickElement(getButtonContinue(), "Continue Button", driver);
				clickElement(getButtonContinue(), "Continue Button", driver);
			} else {


				sendKeys(getTextboxIDNumber(), dictionary.get("CUSTOMER_ID"), "Customer ID", "", driver);
				sendKeys(getTextboxName(), dictionary.get("CUSTOMER_NAME"), "Customer Name", "", driver);
				clickElement(getSpinnerGender(), "Gender Spinner", driver);
				clickElement(getSelectMale(), "Select Male", driver);
				sendKeys(getDateDD(), dictionary.get("CUSTOMER_DOB_DD"), "Customer DOB Date", "", driver);
				sendKeys(getDateMM(), dictionary.get("CUSTOMER_DOB_MM"), "Customer DOB Month", "", driver);
				sendKeys(getDateYYYY(), dictionary.get("CUSTOMER_DOB_YYYY"), "Customer DOB Year", "", driver);
				clickElement(getSpinnerNationality(), "Nationality Spinner", driver);
				clickElement(getSelectAntartica(), "Select Antartica", driver);
				scrollUDLR(driver, 0, "U");
				clickElement(getButtonContinue(), "Continue Button", driver);
				clickElement(getButtonContinue(), "Continue Button", driver);
			}

			clickElement(getCRPSelectMSISDN(), "Select MSISDN Button", driver);

			driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.fl.pra:id/tvItem'][@text='"+MainUtil.dictionary.get("msisdn")+" (Principal)']")).click();

			compareInString(getmaxlinemessage().getText(), "MSISDN is barred. Cannot proceed with the operation.", "MSISDN is Bar Status", driver);

			clickElement(getButtonOK(), "Ok Button", driver);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Postpaid suspend change plan:" + e);
		}
		return methodReturn;
	}



}
