package com.umtest.umrex.pagefunction;

import com.umtest.testframe.lib.*;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.umtest.umrex.pageobject.UMREXRegistrationPage;

import java.io.IOException;
import java.time.Duration;

import static com.umtest.testframe.lib.RandomDataGenerator.randomChar;
import static com.umtest.testframe.listener.ExtentTestNGITestListener.getTest;

public class UMREXPrepaidRegistrationFuncs extends UMREXRegistrationPage {

	private static Logger logger = LogManager.getLogger(UMREXPrepaidRegistrationFuncs.class);
	private AndroidDriver driver;

	public UMREXPrepaidRegistrationFuncs(RemoteWebDriver driver) {
		super((AndroidDriver) driver);
		this.driver = (AndroidDriver) driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);

	}


	public boolean doPrepaidRegistration(String registrationType, String identificationType) {

		boolean methodReturn = false;

		try {

			clickElement(getButtonPrepaid(), "Prepaid Button", driver);
			clickElement(getButtonScanID(), "Scan ID Button", driver);

			enterCustomerInformation(identificationType);
			ApplicationUtil.getMSISDNFromSIMTable("PREPAID", MainUtil.dictionary.get("PLAN_NAME"));
			enterMSISDNToRegisterFromSIMDetailsTable();
			selectPreferredLanguageEnglish();
			/*clickContinue();*/


			if (registrationType.equalsIgnoreCase("BUNDLE")) {
				clickElement(getButtonTopUpAddOn(), "Topup Add On Link", driver);
				clickElement(getTabLinkAddOn(), "Tab Add On", driver);
				String updatedBundleName;

				if (dictionary.get("BUNDLE_NAME").contains("UMI") || dictionary.get("BUNDLE_NAME").contains("umi")) {
					selectAddOnPackage(dictionary.get("BUNDLE_NAME"));

					updatedBundleName=dictionary.get("BUNDLE_NAME");
					updatedBundleName=updatedBundleName.replaceAll("\\s+","");
					dictionary.put("BUNDLE_NAME",updatedBundleName);

				}else if (dictionary.get("BUNDLE_NAME").contains("GX") || dictionary.get("BUNDLE_NAME").contains("gx")) {
					selectAddOnProduct("GX/U Plans");
					selectAddOnPackage(dictionary.get("BUNDLE_NAME"));

					updatedBundleName=dictionary.get("BUNDLE_NAME");
					updatedBundleName=updatedBundleName.replaceAll("\\s+","");
					dictionary.put("BUNDLE_NAME",updatedBundleName);

				}else if (dictionary.get("BUNDLE_NAME").contains("MB") || dictionary.get("BUNDLE_NAME").contains("mb")) {
					selectAddOnProduct("MB");
					selectAddOnPackage(dictionary.get("BUNDLE_NAME"));

					 updatedBundleName=dictionary.get("BUNDLE_NAME");
					updatedBundleName=updatedBundleName.replace("Prepaid","");
					updatedBundleName=updatedBundleName.replaceAll("\\s+","");
					dictionary.put("BUNDLE_NAME",updatedBundleName);
				}else if (dictionary.get("BUNDLE_NAME").equalsIgnoreCase("U25") || dictionary.get("BUNDLE_NAME").equalsIgnoreCase("U35") || dictionary.get("BUNDLE_NAME").contains("u")) {
					selectAddOnProduct("GX/U Plans");
					selectAddOnPackage(dictionary.get("BUNDLE_NAME"));

					updatedBundleName = dictionary.get("BUNDLE_NAME");
					updatedBundleName = updatedBundleName.replace("Prepaid", "");
					updatedBundleName = updatedBundleName.replaceAll("\\s+", "");
					dictionary.put("BUNDLE_NAME", updatedBundleName);
				}else {
					selectAddOnProduct("EPIKKK");
					updatedBundleName=dictionary.get("BUNDLE_NAME");
					updatedBundleName=updatedBundleName.replaceAll("\\s+","");
					dictionary.put("BUNDLE_NAME",updatedBundleName);
				}

				sendKeys(getTextboxAddOnTopUpAmount(), dictionary.get("TOPUP_AMOUNT")+"00", "Add-On Topup Amount", "", driver);
				clickElement(getButtonSave(), "SAVE Button", driver);
				clickElement(getButtonContinue(), "CONTINUE Button", driver);
				dictionary.put("PURCHASE_PASSWORD", PropertyHelper.getENVProperties("UMREX_PURCHASE_PASSWORD"));
				sendKeys(getTextboxTopupPassword(), dictionary.get("PURCHASE_PASSWORD"), "Password", "", driver);
			//	clickContinue();
			//	Thread.sleep(10000);

			} else if (registrationType.equalsIgnoreCase("TOPUP")) {
				clickElement(getButtonTopUpAddOn(), "Topup Add On Link", driver);
				sendKeys(getTextboxTopUpAmountAddOn(), dictionary.get("TOPUP_AMOUNT")+"00", "Topup Amount", "", driver);
				clickElement(getButtonSave(), "SAVE Button", driver);
				clickElement(getButtonContinue(), "CONTINUE Button", driver);
				dictionary.put("PURCHASE_PASSWORD", PropertyHelper.getENVProperties("UMREX_PURCHASE_PASSWORD"));
				sendKeys(getTextboxTopupPassword(), dictionary.get("PURCHASE_PASSWORD"), "Password", "", driver);
			//	clickContinue();
			//	Thread.sleep(10000);
			} /*else {
				clickContinue();
			}*/

			clickContinue();

			Thread.sleep(20000);
			boolean checkFlag1 = checkForText("", getTextThankYouYourOrderIsReceived(), "Thank you", "Thank you (Registration Order Success Message)", driver);
			boolean checkFlag2= checkForText("", getTextYourOrderIsReceived(), "Your order is received.", "Your order is received. (Registration Order Success Message)", driver);

			if (checkFlag1 && checkFlag2) {
				ApplicationUtil.insertAccountIntoDB("PREPAID","UMREX",registrationType);
				ApplicationUtil.updateSIMStatusInSIMDetails(MainUtil.dictionary.get("SIM_NO"));
				methodReturn = true;
			/*	clickElement(getButtonOK(), "Ok Button", driver);
				clickElement(getButtonNO(), "No Button", driver);*/
			/*} else {
				clickElement(getButtonOK(), "Ok Button", driver);*/
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid registration  :" + e);
		}
		return methodReturn;
	}

	public boolean doPrepaidRegistrationMaxLineCheck(String identificationType) {

		boolean methodReturn = false;

		try {

			clickElement(getButtonPrepaid(), "Prepaid Button", driver);
			clickElement(getButtonScanID(), "Scan ID Button", driver);

			if (identificationType.equalsIgnoreCase("MALAYSIAN")) {
				clickElement(getButtonMyKad(), "MyKad Button", driver);
			} else {
				clickElement(getButtonPassport(), "Passport Button", driver);
			}

			clickElement(getButtonScan(), "Scan Button", driver);

			logger.info("Generating Customer Details For UMREX");

			if (identificationType.equals("MALAYSIAN")) {
				MainUtil.dictionary.put("CUSTOMER_ID_TYPE", "MYKAD");
				MainUtil.dictionary.put("CUSTOMER_ID", "900101101010");

			} else {
				MainUtil.dictionary.put("CUSTOMER_ID_TYPE", "PASSPORT");
				MainUtil.dictionary.put("CUSTOMER_ID", "T1245787");
			}

			MainUtil.dictionary.put("CUSTOMER_NAME", "TEST AUTO MAXLINECHECK");
			MainUtil.dictionary.put("CUSTOMER_DOB_DD", "01");
			MainUtil.dictionary.put("CUSTOMER_DOB_MM", "01");
			MainUtil.dictionary.put("CUSTOMER_DOB_YYYY", "1990");

			sendKeys(getTextboxIDNumber(), dictionary.get("CUSTOMER_ID"), "Customer ID", "", driver);
			sendKeys(getTextboxName(), dictionary.get("CUSTOMER_NAME"), "Customer Name", "", driver);
			clickElement(getSpinnerGender(), "Gender Spinner", driver);
			clickElement(getSelectMale(), "Select Male", driver);
			sendKeys(getDateDD(), dictionary.get("CUSTOMER_DOB_DD"), "Customer DOB Date", "", driver);
			sendKeys(getDateMM(), dictionary.get("CUSTOMER_DOB_MM"), "Customer DOB Month", "", driver);
			sendKeys(getDateYYYY(), dictionary.get("CUSTOMER_DOB_YYYY"), "Customer DOB Year", "", driver);
			clickElement(getSpinnerNationality(), "Nationality Spinner", driver);
			scrollUDLR(driver, 0, "U");
			clickElement(getSelectAntartica(), "Select Antartica", driver);
			scrollUDLR(driver, 0, "U");
			clickElement(getButtonContinue(), "Continue Button", driver);

			Thread.sleep(5000);
			boolean checkFlag = checkForText("", getTextMaxLineCheck(), "Customer has reached maximum Prepaid lines limit.", "MAX LINE CHECK - Message", driver);

			if (checkFlag) {
				methodReturn = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid registration max line check :" + e);
		}
		return methodReturn;
	}

	public void fillInCustomerDetails(String registrationType) {
		try {

			if(registrationType.equalsIgnoreCase("PASSPORT"))
			{
			sendKeys(getTextboxIDNumber(), dictionary.get("CUSTOMER_ID"), "Customer ID", "", driver);
			sendKeys(getTextboxName(), dictionary.get("CUSTOMER_NAME"), "Customer Name", "", driver);
			clickElement(getSpinnerGender(), "Gender Spinner", driver);
			clickElement(getSelectMale(), "Select Male", driver);
			sendKeys(getDateDD(), dictionary.get("CUSTOMER_DOB_DD"), "Customer DOB Date", "", driver);
			sendKeys(getDateMM(), dictionary.get("CUSTOMER_DOB_MM"), "Customer DOB Month", "", driver);
			sendKeys(getDateYYYY(), dictionary.get("CUSTOMER_DOB_YYYY"), "Customer DOB Year", "", driver);
			clickElement(getSpinnerNationality(), "Nationality Spinner", driver);
			scrollUDLR(driver, 0, "U");
			clickElement(getSelectAntartica(), "Select Antartica", driver);
			scrollUDLR(driver, 0, "U");
			clickElement(getButtonContinue(), "Continue Button", driver);
			Thread.sleep(3000);
			scrollUDLRFromBelowHalfScreen(driver, 0, "U");
			sendKeys(getTextboxAddress(), dictionary.get("CUSTOMER_ADDRESS"), "Customer Address", "", driver);
			scrollUDLRFromBelowHalfScreen(driver, 0, "U");
			scrollUDLRFromBelowHalfScreen(driver, 0, "U");
			sendKeys(getTextboxCity(), dictionary.get("CUSTOMER_CITY"), "Customer City", "", driver);
			sendKeys(getTextboxState(), dictionary.get("CUSTOMER_STATE"), "Customer State", "", driver);
			//scrollUDLRFromBelowHalfScreen(driver, 0, "U");
			sendKeys(getTextboxPostcode(), dictionary.get("CUSTOMER_POSTCODE"), "Customer Postcode", "", driver);
			sendKeys(getTextboxEmailID(), "chandra.k@u.com.my", "Customer Email ID", "", driver);
			//sendKeys(getTextboxEmailID(), dictionary.get("CUSTOMER_EMAIL"), "Customer Email ID", "", driver);
		//	sendKeys(getTextboxAlternateContact(), dictionary.get("CUSTOMER_NUMBER"), "Customer Contact Number", "", driver);
			scrollUDLRFromBelowHalfScreen(driver, 0, "U");
			clickElement(getButtonContinue(), "Continue Button", driver);
			}else
			{

			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while filling up customer details  :" + e);
		}
	}
	
	public boolean doPrepaidPhoneBundleRegistration(String registrationType, String identificationType) {

		boolean methodReturn = false;

		try {

			scrollUDLR(driver, 0, "U");
			clickElement(getButtonPrepaidPhoneBundle(), "Prepaid Phone Bundle Button", driver);
			clickElement(getButtonScanIDPostpaid(), "Scan ID Button", driver);
			
			RandomDataGenerator.generateCustomerDataUMREX(identificationType);
			ApplicationUtil.getMSISDNFromSIMTable("POSTPAID", MainUtil.dictionary.get("PLAN_NAME"));
			ApplicationUtil.getIMEINo();
			
			fillInCustomerDetailsPhoneBundle(identificationType);
					
			clickElement(getButtonAddPrincipalLine(), "Add Pricipal Line Button", driver);
			
			/*if (dictionary.get("MSISDN") != null || dictionary.get("MSISDN") != "NA") {
				
				clickElement(getRadioButtonDealerPool(), "Dealer Pool", driver);
				sendKeys(getTextboxSearchMSISDN(), dictionary.get("MSISDN").substring(4, dictionary.get("MSISDN").length()), "Search MSISDN", "", driver);
			}*/
			//clickElement(getRadioButtonDealerPool(), "Dealer Pool", driver);
			//clickElement(getButtonSearch(), "Search Button", driver);
			//clickElement(getButtonOK(), "OK Button", driver);
			clickElement(getButtonFirstMSISDN(), "Select MSISDN", driver);
			//clickElement(getButtonOK(), "OK Button", driver);
			dictionary.put("MSISDN", getButtonFirstMSISDN().getText());
			System.out.println("MSISDN : "+dictionary.get("MSISDN"));
			clickElement(getButtonContinue(), "Continue Button", driver);
			
			clickElement(selectPostpaidPlan(MainUtil.dictionary.get("BUNDLE_NAME")), "Select Plan", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			
			sendKeys(getTextboxIMEI(), dictionary.get("IMEI_NO"), "IMEI No", "", driver);
			clickElement(getButtonVerify(), "Verify Button", driver);
			Thread.sleep(5000);
			
			clickElement(getDropdownPromotion(), "Promo Dropdown", driver);
			clickElement(getSelectFirstPromotion(), "Select First", driver);
			Thread.sleep(5000);
			clickElement(getButtonContinue(), "Continue Button", driver);
			
			sendKeys(getTextboxSIMNo(), dictionary.get("SIM_NO"), "SIM", "", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
		
			getLabelSubscriptionSummary();
			scrollUDLR(driver, 0, "U");
			dictionary.put("TOTAL_AMOUNT", getLabelTotalAmountPhoneBundle().getText());
			
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			
			clickElement(getSpinnerEventType(), "Event Type", driver);
			clickElement(selectEvenType("Dealer Outlet"), "Dealer Outlet", driver);
			
			sendKeys(getTextboxEventPostcode(), "55100", "Event Postcode", "", driver);
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
			
			Thread.sleep(20000);
			
			clickElement(getLabelMakePayment(), "Make Payment Label", driver);
			
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			
			
			clickElement(getButtonAddPaymentMethod(), "Add Payment Button", driver);
			clickCashButton(driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			
			sendKeys(getTextboxPurchasePasswordPhoneBundle(), "55100", "Event Postcode", "", driver);

			boolean checkFlag = checkForText("", getLabelSuccessMessagePostpaid(), "Thank you for your order", "Success Message", driver);
			
			if (checkFlag == true) {
				ApplicationUtil.insertAccountIntoDB("POSTPAID","UMREX",registrationType);
				ApplicationUtil.updateSIMStatusInSIMDetails(MainUtil.dictionary.get("SIM_NO"));
				ApplicationUtil.updateIMEIStatusInIMEIDetails(MainUtil.dictionary.get("IMEI_NO"));
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
	
	public void fillInCustomerDetailsPhoneBundle(String registrationType) {
		try {

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
			
			compareInString(getLabelDDMFCheck().getText(), "PASS", "DDMF Check", driver);
			compareInString(getLabelAgeValidationStatus().getText(), "PASS", "Age Validation", driver);
			compareInString(getLabelInternalBlacklistStatus().getText(), "PASS", "Internal Blacklist", driver);
			
			clickElement(getButtonContinue(), "Continue Button", driver);
			
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getSpinnerTitle(), "Title Spinner", driver);
			scrollUDLR(driver, 0, "U");
			clickElement(getSelectMR(), "Mr", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			sendKeys(getTextboxAddressPostpaid(), dictionary.get("CUSTOMER_ADDRESS"), "Customer Address", "", driver);
			sendKeys(getTextboxPostcodePostpaid(), dictionary.get("CUSTOMER_POSTCODE"), "Customer Postcode", "", driver);
			scrollUDLR(driver, 0, "U");
			sendKeys(getTextboxAlternateNumberArea(), dictionary.get("CUSTOMER_NUMBER").substring(0, 4), "Customer Number Area", "", driver);
			sendKeys(getTextboxAlternateNumber(), dictionary.get("CUSTOMER_NUMBER").substring(4, dictionary.get("CUSTOMER_NUMBER").length()), "Customer Number", "", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while filling up customer details  :" + e);
		}
	}

	public void scanSIMPack(String MSISDN) {
		try {

			clickElement(getButtonScanSimPack(), "Scan Sim Pack Button", driver);
			clickElement(getButtonEnterSimPackManually(), "Enter Sim Pack Manually Button", driver);
			sendKeys(getTextboxMobileNumber(), MSISDN.substring(1, MSISDN.length()), "Mobile Number", "", driver);
			clickElement(getButtonSIMNext(), "Next Button", driver);



		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while scanning sim pack  :" + e);
		}
	}

	public void enterSubscriptionDetails(String MSISDN) {
		try {
			sendKeys(getTextboxMSISDN(), MSISDN.substring(1, MSISDN.length()),"MSISDN","", driver);
			clickElement(getButtonValidate(), "Validate Button", driver);
			if( !isElementDisplayed(getButtonReset())){
				logger.info("ERROR: Test Data appears invalid. ");
				clickElement(getButtonContinue(), "Next Button", driver);
			}
			clickElement(getButtonContinue(), "Next Button", driver);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while entering Subscription Details  :" + e);
		}
	}
	
	public void clickCashButton(AndroidDriver driver)
	{
		Dimension size = driver.manage().window().getSize();
		
		logger.info(size.width  + "::::::" + size.height);
		
		int x = (int) (size.width * 0.50);
		int y = (int) (size.height * 0.48);
		
		logger.info(x + "::::::" + y);
		
		TouchAction action= new TouchAction(driver);
		action.press(PointOption.point(x, y)).release().perform();
	}

	public boolean doPrepaid_Maxlinecheck(String registrationType, String identificationType) {

		boolean methodReturn = false;

		try {
			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("EXISTINGMSISDN"));
			clickElement(getButtonPostpaid(), "Postpaid Button", driver);
			if (identificationType.equals("MALAYSIAN")) {
				clickElement(getbuttonReadIDPostpaid(), "Read ID Button", driver);
			} else {
				clickElement(getButtonScanIDPostpaid(), "Scan ID Button", driver);
			}

			ApplicationUtil.getMSISDNFromSIMTable("POSTPAID", MainUtil.dictionary.get("PLAN_NAME"));

			UMREXPostpaidRegistrationFuncs postpaidFuncs=new UMREXPostpaidRegistrationFuncs(driver);

			postpaidFuncs.fillInCustomerDetails_Existingline(identificationType);

			clickElement(getButtonAddPrincipalLine(), "Add Pricipal Line Button", driver);

			compareInString(getmaxlinemessage().getText(), "Sorry, you are not allowed to add new principal line due to exceeding its maximum number of principal lines.", "MaxLine check Status", driver);

			clickElement(getButtonOK(), "Ok Button", driver);


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid registration  :" + e);
		}
		return methodReturn;
	}



	public void enterCustomerInformation(String custIdentificationType) {
		try {

			if (custIdentificationType.equalsIgnoreCase("MYKAD")) {
				getDefaultCustomerInfo("src\\test\\resources\\testData\\customerInfoMYKAD.json");

				clickMYKADButton();
				clickSCANButton();
				enterCustomerID(dictionary.get("CUSTOMER_ID"));
				enterCustomerName(dictionary.get("CUSTOMER_NAME"));
				selectGender(dictionary.get("CUSTOMER_GENDER"));
				enterDateOfBirth(dictionary.get("CUSTOMER_DOB_DD"),dictionary.get("CUSTOMER_DOB_MM"),dictionary.get("CUSTOMER_DOB_YYYY"));
				enterAddress(dictionary.get("CUSTOMER_ADDRESS"));

				scrollUDLR(driver, 0, "U");
				scrollUDLR(driver, 0, "U");
				scrollUDLR(driver, 0, "U");
				scrollUDLR(driver, 0, "U");

				enterPostCode(dictionary.get("CUSTOMER_POSTCODE"));
				enterCity(dictionary.get("CUSTOMER_CITY"));
				enterState(dictionary.get("CUSTOMER_STATE"));

				clickContinue();

//				waitForMessageToDisappear("Loading",driver);

				scrollUDLRFromBelowHalfScreen(driver, 0, "U");
				scrollUDLRFromBelowHalfScreen(driver, 0, "U");
				scrollUDLRFromBelowHalfScreen(driver, 0, "U");
				scrollUDLRFromBelowHalfScreen(driver, 0, "U");

				enterEmailID(dictionary.get("CUSTOMER_EMAIL"));

//				waitForMessageToDisappear("Loading",driver);
				MainUtil.dictionary.put("IDENTIFICATION_TYPE","MYKAD");
			} else {
				getDefaultCustomerInfo("src\\test\\resources\\testData\\customerInfoPASSPORT.json");

				clickPASSPORTButton();
				clickSCANButton();

				enterCustomerID(dictionary.get("CUSTOMER_ID"));
				enterCustomerName(dictionary.get("CUSTOMER_NAME"));
				selectGender(dictionary.get("CUSTOMER_GENDER"));
				enterDateOfBirth(dictionary.get("CUSTOMER_DOB_DD"),dictionary.get("CUSTOMER_DOB_MM"),dictionary.get("CUSTOMER_DOB_YYYY"));
				selectNationalityAlbania();   //Hardcoded to select 'Albania' as Nationality always as it appears first in the list of countries.
				scrollUDLR(driver, 0, "U");

				clickContinue();

				Thread.sleep(10000);
				waitForMessageToDisappear("Loading",driver);

				scrollUDLRFromBelowHalfScreen(driver, 0, "U");

				enterAddressPassport(dictionary.get("CUSTOMER_ADDRESS"));

				scrollUDLRFromBelowHalfScreen(driver, 0, "U");

				enterCity(dictionary.get("CUSTOMER_CITY"));
				enterState(dictionary.get("CUSTOMER_STATE"));

				scrollUDLRFromBelowHalfScreen(driver, 0, "U");

				enterPostCode(dictionary.get("CUSTOMER_POSTCODE"));

				scrollUDLRFromBelowHalfScreen(driver, 0, "U");

				enterEmailID(dictionary.get("CUSTOMER_EMAIL"));

				scrollUDLRFromBelowHalfScreen(driver, 0, "U");
				MainUtil.dictionary.put("IDENTIFICATION_TYPE","PASSPORT");
			}

			clickContinue();

			/*clickElement(getButtonScan(), "Scan Button", driver);


			if(custIdentificationType.equalsIgnoreCase("MALAYSIAN")) {

				MainUtil.dictionary.put("CUSTOMER_ID_TYPE", "MYKAD");
				MainUtil.dictionary.put("CUSTOMER_ID", "900101"+randomChar("randomnumber#6"));

				enterCustomerID(dictionary.get("CUSTOMER_ID"));
				enterCustomerName(dictionary.get("CUSTOMER_NAME"));

				sendKeys(getTextboxName(), dictionary.get("CUSTOMER_NAME"), "Customer Name", "", driver);
				clickElement(getSpinnerGender(), "Gender Spinner", driver);
				clickElement(getSelectMale(), "Select Male", driver);
				sendKeys(getDateDD(), dictionary.get("CUSTOMER_DOB_DD"), "Customer DOB Date", "", driver);
				sendKeys(getDateMM(), dictionary.get("CUSTOMER_DOB_MM"), "Customer DOB Month", "", driver);
				sendKeys(getDateYYYY(), dictionary.get("CUSTOMER_DOB_YYYY"), "Customer DOB Year", "", driver);
				sendKeys(getTextboxAddress(), dictionary.get("CUSTOMER_ADDRESS"), "Customer Address", "", driver);
				sendKeys(getTextboxPostcode(), dictionary.get("CUSTOMER_POSTCODE"), "Customer Postcode", "", driver);
				sendKeys(getTextboxCity(), dictionary.get("CUSTOMER_CITY"), "Customer City", "", driver);
				sendKeys(getTextboxState(), dictionary.get("CUSTOMER_STATE"), "Customer State", "", driver);

				clickElement(getButtonContinue(), "Continue Button", driver);

				scrollUDLRFromBelowHalfScreen(driver, 0, "U");
				sendKeys(getTextboxEmailID(), "chandra.k@u.com.my", "Customer Email ID", "", driver);

				clickElement(getButtonContinue(), "Continue Button", driver);

			} else{

				MainUtil.dictionary.put("CUSTOMER_ID_TYPE", "PASSPORT");
				MainUtil.dictionary.put("CUSTOMER_ID", "T"+randomChar("randomnumber#7"));

				enterCustomerID(dictionary.get("CUSTOMER_ID"));


				sendKeys(getTextboxName(), dictionary.get("CUSTOMER_NAME"), "Customer Name", "", driver);
				clickElement(getSpinnerGender(), "Gender Spinner", driver);
				clickElement(getSelectMale(), "Select Male", driver);

				sendKeys(getDateDD(), dictionary.get("CUSTOMER_DOB_DD"), "Customer DOB Date", "", driver);
				sendKeys(getDateMM(), dictionary.get("CUSTOMER_DOB_MM"), "Customer DOB Month", "", driver);
				sendKeys(getDateYYYY(), dictionary.get("CUSTOMER_DOB_YYYY"), "Customer DOB Year", "", driver);

				clickElement(getSpinnerNationality(), "Nationality Spinner", driver);
				scrollUDLR(driver, 0, "U");
				clickElement(getSelectAntartica(), "Select Antartica", driver);
				scrollUDLR(driver, 0, "U");
				clickElement(getButtonContinue(), "Continue Button", driver);
				Thread.sleep(3000);

				scrollUDLRFromBelowHalfScreen(driver, 0, "U");

				sendKeys(getTextboxAddress(), dictionary.get("CUSTOMER_ADDRESS"), "Customer Address", "", driver);

				scrollUDLRFromBelowHalfScreen(driver, 0, "U");
				scrollUDLRFromBelowHalfScreen(driver, 0, "U");

				sendKeys(getTextboxCity(), dictionary.get("CUSTOMER_CITY"), "Customer City", "", driver);
				sendKeys(getTextboxState(), dictionary.get("CUSTOMER_STATE"), "Customer State", "", driver);
				sendKeys(getTextboxPostcode(), dictionary.get("CUSTOMER_POSTCODE"), "Customer Postcode", "", driver);

				sendKeys(getTextboxEmailID(), "chandra.k@u.com.my", "Customer Email ID", "", driver);
				//sendKeys(getTextboxEmailID(), dictionary.get("CUSTOMER_EMAIL"), "Customer Email ID", "", driver);
				//	sendKeys(getTextboxAlternateContact(), dictionary.get("CUSTOMER_NUMBER"), "Customer Contact Number", "", driver);
				scrollUDLRFromBelowHalfScreen(driver, 0, "U");
				clickElement(getButtonContinue(), "Continue Button", driver);
*/


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while entering up Customer details  :" + e);
		}
	}

	public void getDefaultCustomerInfo(String sTestDataFile) throws IOException, ParseException {

		JSONObject oCustInfo=JSONReader.loadJSONTestDataFile(sTestDataFile);

		MainUtil.dictionary.put("CUSTOMER_ID", oCustInfo.get("custIDStartDigits").toString()+randomChar("randomnumber#6"));
		MainUtil.dictionary.put("CUSTOMER_NAME",oCustInfo.get("custNameStartChars").toString()+randomChar("randomchar#5"));
		MainUtil.dictionary.put("CUSTOMER_GENDER",oCustInfo.get("custGender").toString());
		MainUtil.dictionary.put("CUSTOMER_DOB_DD",oCustInfo.get("custDOBDD").toString());
		MainUtil.dictionary.put("CUSTOMER_DOB_MM",oCustInfo.get("custDOBMM").toString());
		MainUtil.dictionary.put("CUSTOMER_DOB_YYYY",oCustInfo.get("custDOBYYYY").toString());
		MainUtil.dictionary.put("CUSTOMER_ADDRESS",oCustInfo.get("custAddress").toString());
		MainUtil.dictionary.put("CUSTOMER_CITY",oCustInfo.get("custCity").toString());
		MainUtil.dictionary.put("CUSTOMER_STATE",oCustInfo.get("custState").toString());
		MainUtil.dictionary.put("CUSTOMER_POSTCODE",oCustInfo.get("custPostCode").toString());
		MainUtil.dictionary.put("CUSTOMER_EMAIL",oCustInfo.get("custEmail").toString());
		MainUtil.dictionary.put("CUSTOMER_NUMBER",oCustInfo.get("custNumber").toString());
		if(oCustInfo.containsKey("custNationality")){
			MainUtil.dictionary.put("CUSTOMER_NATIONALITY",oCustInfo.get("custNationality").toString());
		}
	}

	public void enterMSISDNToRegisterFromSIMDetailsTable() throws Exception {

	//	ApplicationUtil.getMSISDNFromSIMTable("PREPAID", MainUtil.dictionary.get("PLAN_NAME"));

		//MainUtil.dictionary.put("MSISDN","601139280482");

		String sMSISDN1=MainUtil.dictionary.get("MSISDN");

		String sMSISDNUpdated=sMSISDN1.substring(2,sMSISDN1.length());

		sendKeys(getTextboxMSISDN(), sMSISDNUpdated,"MSISDN","", driver);

		enterMSISDN(sMSISDNUpdated);

		clickValidateButton();

		if( !isElementDisplayed(getButtonReset())){
			logger.info("ERROR: Test Data appears invalid. ");
			clickElement(getButtonScan(), "Next Button", driver);
		}
	}


}
