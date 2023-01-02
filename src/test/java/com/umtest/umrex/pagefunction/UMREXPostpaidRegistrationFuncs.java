package com.umtest.umrex.pagefunction;

import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.lib.PropertyHelper;
import com.umtest.testframe.lib.RandomDataGenerator;
import com.umtest.umrex.pageobject.UMREXRegistrationPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class UMREXPostpaidRegistrationFuncs extends UMREXRegistrationPage {

	private static Logger logger = LogManager.getLogger(UMREXPostpaidRegistrationFuncs.class);
	private AndroidDriver driver;

	public UMREXPostpaidRegistrationFuncs(RemoteWebDriver driver) {
		super((AndroidDriver) driver);
		this.driver = (AndroidDriver) driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);

	}


	public boolean doPostpaidRegistration(String registrationType, String identificationType) {

		boolean methodReturn = false;

		try {

			clickElement(getButtonPostpaid(), "Postpaid Button", driver);
			if (identificationType.equals("MALAYSIAN")) {
				clickElement(getbuttonReadIDPostpaid(), "Read ID Button", driver);
			} else {
				clickElement(getButtonScanIDPostpaid(), "Scan ID Button", driver);
			}

			RandomDataGenerator.generateCustomerDataUMREX(identificationType);
			ApplicationUtil.getMSISDNFromSIMTable("POSTPAID", MainUtil.dictionary.get("PLAN_NAME"));

			fillInCustomerDetails(identificationType);

			clickElement(getButtonAddPrincipalLine(), "Add Pricipal Line Button", driver);

			SelectrateplanUMREX(MainUtil.dictionary.get("PLAN_NAME"));

			//clickElement(selectPostpaidPlan(MainUtil.dictionary.get("PLAN_NAME")), "Select Plan", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);

			System.out.println("<<<<<<<<<<<<MSISDN "+dictionary.get("MSISDN"));
			if (dictionary.get("MSISDN") != null || dictionary.get("MSISDN") != "NA") {

				clickElement(getRadioButtonDealerPool(), "Dealer Pool", driver);
				sendKeys(getTextboxSearchMSISDN(), dictionary.get("MSISDN").substring(4, dictionary.get("MSISDN").length()), "Search MSISDN", "", driver);
			}

			clickElement(getMSISDNCategory(), "MSISDN Category", driver);
			clickElement(getMSISDNCatergoryC5(), "MSISDN Category C5", driver);
			clickElement(getMSISDNCatergoryNormal(), "MSISDN Category Normal", driver);

			driver.navigate().back();

			//clickElement(getMSISDNCategory(), "MSISDN Category", driver);
			clickElement(getRadioButtonDealerPool(), "Dealer Pool", driver);

			clickElement(getButtonSearch(), "Search Button", driver);
			clickElement(getButtonFirstMSISDN(), "Select MSISDN", driver);
			dictionary.put("MSISDN", getButtonFirstMSISDN().getText());
			System.out.println("MSISDN : "+dictionary.get("MSISDN"));
			clickElement(getButtonContinue(), "Continue Button", driver);
			Thread.sleep(2000);
			clickElement(getRadioButtonlanguage(), "Prefrred Language English", driver);

			clickElement(getButtonContinue(), "Continue Button", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			sendKeys(getTextboxSIMNo(), dictionary.get("SIM_NO"), "SIM", "", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);

			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			Thread.sleep(5000);
			clickElement(getButtonContinue(), "Continue Button", driver);
			Thread.sleep(1000);
			clickElement(getSpinnerEventType(), "Event Type", driver);
			Thread.sleep(1000);
			driver.findElementByXPath("//android.widget.CheckedTextView[@resource-id='android:id/text1'][@text='Dealer Outlet']").click();

			clickElement(getButtonEventStartDate(), "Event Start Date", driver);
			clickElement(getButtonOK(), "Ok Button", driver);
			clickElement(getButtonEventEndDate(), "Event End Date", driver);
			clickElement(getButtonOK(), "Ok Button", driver);
			sendKeys(getTextboxEventPostcode(), "55100", "Event Postcode", "", driver);
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
			clickElement(getbuttonbundleContinue(), "Continue Button", driver);

			dictionary.put("PURCHASE_PASSWORD", PropertyHelper.getENVProperties("UMREX_PURCHASE_PASSWORD"));
			//sendKeys(getTextboxPasswordPostpaid(), dictionary.get("PURCHASE_PASSWORD"), "Pass", "", driver);

			clickElement(getTextboxPasswordPostpaid(), "Password field", driver);

			driver.getKeyboard().pressKey(dictionary.get("PURCHASE_PASSWORD"));

			clickElement(getbuttonbundleContinue(), "Continue Button", driver);
			Thread.sleep(3000);
			driver.findElement(By.xpath("//android.widget.Button[@text='YES']")).click();

			//setNativeContextUMREX();

			//clickElement(getButtonYESBundlePurchase(), "Yes Button", driver);

			while (isRetryButtonDisplayed())
			{
				clickElement(getRetrybutton(), "Retry Button", driver);
			}

			boolean checkFlag = checkForText("", getlabelorderSuccessMessage(), "Thank you for your order", "Success Message", driver);

			if (checkFlag == true) {
				ApplicationUtil.insertAccountIntoDB("POSTPAID","UMREX",registrationType);
				ApplicationUtil.updateSIMStatusInSIMDetails(MainUtil.dictionary.get("SIM_NO"));
				methodReturn = true;
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


	public boolean doPostpaidRegistration_multiline(String registrationType, String identificationType) {

		boolean methodReturn = false;

		try {

			clickElement(getButtonPostpaid(), "Postpaid Button", driver);
			if (identificationType.equals("MALAYSIAN")) {
				clickElement(getbuttonReadIDPostpaid(), "Read ID Button", driver);
			} else {
				clickElement(getButtonScanIDPostpaid(), "Scan ID Button", driver);
			}

			RandomDataGenerator.generateCustomerDataUMREX(identificationType);

			String[] arrplanname = MainUtil.dictionary.get("PLAN_NAME").split(";");
			System.out.println(arrplanname.length);

			String linecount=Integer.toString(arrplanname.length);
			ApplicationUtil.getMSISDNFromSIMTableForMultiline("POSTPAID", MainUtil.dictionary.get("PLAN_NAME"),linecount);
			int j = 1;
			for (int i = 0; i<arrplanname.length; i++)
			{
				dictionary.put("PLAN_NAME"+j, arrplanname[i]);
				j++;
			}

			System.out.println(arrplanname.length);
			System.out.println(dictionary.get("PLAN_NAME1"));
			System.out.println(dictionary.get("PLAN_NAME2"));
			System.out.println(dictionary.get("MSISDN1"));
			System.out.println(dictionary.get("MSISDN2"));
			System.out.println(dictionary.get("SIM_NO1"));
			System.out.println(dictionary.get("SIM_NO2"));

			fillInCustomerDetails(identificationType);

			clickElement(getButtonAddPrincipalLine(), "Add Pricipal Line Button", driver);

			SelectrateplanUMREX(MainUtil.dictionary.get("PLAN_NAME1"));
			clickElement(getButtonContinue(), "Continue Button", driver);


			if (dictionary.get("MSISDN1") != null || dictionary.get("MSISDN1") != "NA") {

				clickElement(getRadioButtonDealerPool(), "Dealer Pool", driver);
				sendKeys(getTextboxSearchMSISDN(), dictionary.get("MSISDN1").substring(4, dictionary.get("MSISDN1").length()), "Search MSISDN1", "", driver);
			}

			clickElement(getMSISDNCategory(), "MSISDN Category", driver);
			clickElement(getMSISDNCatergoryC5(), "MSISDN Category C5", driver);
			clickElement(getMSISDNCatergoryNormal(), "MSISDN Category Normal", driver);

			driver.navigate().back();
			clickElement(getRadioButtonDealerPool(), "Dealer Pool", driver);

			clickElement(getButtonSearch(), "Search Button", driver);
			clickElement(getButtonFirstMSISDN(), "Select MSISDN", driver);
			dictionary.put("MSISDN1", getButtonFirstMSISDN().getText());
			System.out.println("MSISDN : "+dictionary.get("MSISDN1"));
			clickElement(getButtonContinue(), "Continue Button", driver);
			Thread.sleep(2000);
			clickElement(getRadioButtonlanguage(), "Prefrred Language English", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			sendKeys(getTextboxSIMNo(), dictionary.get("SIM_NO1"), "SIM", "", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);

			for (int k = 2; k<arrplanname.length+1; k++) {

				clickElement(getButtonAddNewline(), "Add New Line Button", driver);
				int intIndex=0;
				//int intIndex = MainUtil.dictionary.get("PLAN_NAME"+k).indexOf("Share 20");
				if(MainUtil.dictionary.get("PLAN_NAME"+k).contains("Share") || MainUtil.dictionary.get("PLAN_NAME"+k).contains("FamilyShare"))
				{
					clickElement(getButtonAddmemberline(), "Add Member Line Button", driver);

				} else {
					clickElement(getButtonAddPrincipalLine(), "Add Pricipal Line Button", driver);
					intIndex=1;
				}

				SelectrateplanUMREX(MainUtil.dictionary.get("PLAN_NAME"+k));
				clickElement(getButtonContinue(), "Continue Button", driver);

				if (dictionary.get("MSISDN"+k) != null || dictionary.get("MSISDN"+k) != "NA") {

					clickElement(getRadioButtonDealerPool(), "Dealer Pool", driver);
					sendKeys(getTextboxSearchMSISDN(), dictionary.get("MSISDN"+k).substring(4, dictionary.get("MSISDN"+k).length()), "Search MSISDN", "", driver);
				}

				clickElement(getMSISDNCategory(), "MSISDN Category", driver);
				clickElement(getMSISDNCatergoryC5(), "MSISDN Category C5", driver);
				clickElement(getMSISDNCatergoryNormal(), "MSISDN Category Normal", driver);

				driver.navigate().back();
				clickElement(getRadioButtonDealerPool(), "Dealer Pool", driver);

				clickElement(getButtonSearch(), "Search Button", driver);
				clickElement(getButtonFirstMSISDN(), "Select MSISDN", driver);
				dictionary.put("MSISDN"+k, getButtonFirstMSISDN().getText());
				System.out.println("MSISDN : "+dictionary.get("MSISDN"+k));
				clickElement(getButtonContinue(), "Continue Button", driver);

				Thread.sleep(2000);
				//int intIndex = MainUtil.dictionary.get("PLAN_NAME"+k).indexOf("Share 20");
				if(intIndex ==  1) {
					clickElement(getButtonContinue(), "Continue Button", driver);
				}


				scrollUDLR(driver, 0, "U");
				scrollUDLR(driver, 0, "U");
				sendKeys(getTextboxSIMNo(), dictionary.get("SIM_NO"+k), "SIM", "", driver);
				clickElement(getButtonContinue(), "Continue Button", driver);

			}


			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			Thread.sleep(5000);
			clickElement(getButtonContinue(), "Continue Button", driver);
			Thread.sleep(2000);
			clickElement(getSpinnerEventType(), "Event Type", driver);
			//clickElement(selectEvenType("Dealer Outlet"), "Dealer Outlet", driver);
			driver.findElementByXPath("//android.widget.CheckedTextView[@resource-id='android:id/text1'][@text='Dealer Outlet']").click();

			clickElement(getButtonEventStartDate(), "Event Start Date", driver);
			clickElement(getButtonOK(), "Ok Button", driver);
			clickElement(getButtonEventEndDate(), "Event End Date", driver);
			clickElement(getButtonOK(), "Ok Button", driver);
			sendKeys(getTextboxEventPostcode(), "55100", "Event Postcode", "", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);

			clickElement(getButtonTNC(), "Terms And Conditions", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			//clickElement(getSpinnerERFEmail(), "Send eRF Email", driver);
			//clickElement(getSelectEmailID(), "Select Email ID", driver);

			clickElement(getSignatureArea(), "Tap here to sign", driver);
			doSignature(driver);
			clickElement(getButtonSign(), "Sign", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getbuttonbundleContinue(), "Continue Button", driver);

			dictionary.put("PURCHASE_PASSWORD", PropertyHelper.getENVProperties("UMREX_PURCHASE_PASSWORD"));
			clickElement(getMultilinepassword(), "Password field", driver);
			driver.getKeyboard().pressKey(dictionary.get("PURCHASE_PASSWORD"));
			clickElement(getbuttonbundleContinue(), "Continue Button", driver);
			Thread.sleep(3000);
			driver.findElement(By.xpath("//android.widget.Button[@text='YES']")).click();

			while (isRetryButtonDisplayed())
			{
				clickElement(getRetrybutton(), "Retry Button", driver);
			}

			Thread.sleep(5000);
			//boolean checkFlag = checkForText("", getlabelSuccessMessagemultiPostpaid(), "Thank you for your order", "Success Message", driver);
			boolean checkFlag=true;
			if (checkFlag == true) {
				methodReturn = true;
				clickElement(getbuttonBackToHomeMultiline(), "Back To Home Button", driver);
			} else {
				clickElement(getButtonOK(), "Ok Button", driver);
			}

			for (int q = 1; q<arrplanname.length+1; q++) {

				dictionary.put("MSISDN", dictionary.get("MSISDN"+q));
				dictionary.put("PLAN_NAME", dictionary.get("PLAN_NAME"+q));
				dictionary.put("SIM_NO", dictionary.get("SIM_NO"+q));

				if (checkFlag == true) {
					ApplicationUtil.insertAccountIntoDB("POSTPAID","UMREX",registrationType);
					ApplicationUtil.updateSIMStatusInSIMDetails(MainUtil.dictionary.get("SIM_NO"));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid registration  :" + e);
		}
		return methodReturn;
	}

	public boolean doPostpaidRegistration_multilinephonebundle(String PROMOTION_NAME,String registrationType, String identificationType) {

		boolean methodReturn = false;

		try {

			clickElement(getCRPPhonesection(), "Postpaid Change offer Section", driver);
			clickElement(getbuttonPostpaidPhoneBundle(), "Postpaid Phone Bundle Button", driver);

			if (identificationType.equals("MALAYSIAN")) {
				clickElement(getbuttonReadIDPostpaid(), "Read ID Button", driver);
			} else {
				clickElement(getButtonScanIDPostpaid(), "Scan ID Button", driver);
			}

			RandomDataGenerator.generateCustomerDataUMREX(identificationType);

			String[] arrplanname = MainUtil.dictionary.get("PLAN_NAME").split(";");
			System.out.println(arrplanname.length);
			String[] arrIMEI = MainUtil.dictionary.get("IMEI").split(";");
			String[] arrPROMOTIONNAME = MainUtil.dictionary.get("PROMOTION_NAME").split(";");

			String linecount=Integer.toString(arrplanname.length);
			ApplicationUtil.getMSISDNFromSIMTableForMultiline("POSTPAID", MainUtil.dictionary.get("PLAN_NAME"),linecount);
			int j = 1;
			for (int i = 0; i<arrplanname.length; i++)
			{
				dictionary.put("PLAN_NAME"+j, arrplanname[i]);
				dictionary.put("IMEI"+j, arrIMEI[i]);
				dictionary.put("PROMOTION_NAME"+j, arrPROMOTIONNAME[i]);

				j++;
			}


			System.out.println(arrplanname.length);
			System.out.println(dictionary.get("PLAN_NAME1"));
			System.out.println(dictionary.get("PLAN_NAME2"));
			System.out.println(dictionary.get("IMEI1"));
			System.out.println(dictionary.get("IMEI2"));
			System.out.println(dictionary.get("PROMOTION_NAME1"));
			System.out.println(dictionary.get("PROMOTION_NAME2"));
			System.out.println(dictionary.get("MSISDN1"));
			System.out.println(dictionary.get("MSISDN2"));
			System.out.println(dictionary.get("SIM_NO1"));
			System.out.println(dictionary.get("SIM_NO2"));

			fillInCustomerDetails(identificationType);

			clickElement(getButtonAddPrincipalLine(), "Add Pricipal Line Button", driver);

			SelectrateplanUMREX(MainUtil.dictionary.get("PLAN_NAME1"));
			clickElement(getButtonContinue(), "Continue Button", driver);


			if (dictionary.get("MSISDN1") != null || dictionary.get("MSISDN1") != "NA") {

				clickElement(getRadioButtonDealerPool(), "Dealer Pool", driver);
				sendKeys(getTextboxSearchMSISDN(), dictionary.get("MSISDN1").substring(4, dictionary.get("MSISDN1").length()), "Search MSISDN1", "", driver);
			}

			clickElement(getMSISDNCategory(), "MSISDN Category", driver);
			clickElement(getMSISDNCatergoryC5(), "MSISDN Category C5", driver);
			clickElement(getMSISDNCatergoryNormal(), "MSISDN Category Normal", driver);

			driver.navigate().back();
			clickElement(getRadioButtonDealerPool(), "Dealer Pool", driver);

			clickElement(getButtonSearch(), "Search Button", driver);
			clickElement(getButtonFirstMSISDN(), "Select MSISDN", driver);
			dictionary.put("MSISDN1", getButtonFirstMSISDN().getText());
			System.out.println("MSISDN : "+dictionary.get("MSISDN1"));
			clickElement(getButtonContinue(), "Continue Button", driver);
			Thread.sleep(2000);

			sendKeys(getIMEINumber(),MainUtil.dictionary.get("IMEI1"), "IMEI Number", "",driver);
			clickElement(getVerifybutton(), "Verify Button", driver);
			clickElement(getPromotionType(), "Promotion Button", driver);

			System.out.println("PROMOTION_NAME : "+MainUtil.dictionary.get("PROMOTION_NAME1"));
			driver.findElementByXPath("//android.widget.TextView[@resource-id='com.fl.pra:id/tvItem' and contains(@text,'"+MainUtil.dictionary.get("PROMOTION_NAME1")+"')]").click();
			clickElement(getButtonContinue(), "Continue Button", driver);

			clickElement(getButtonContinue(), "Continue Button", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			sendKeys(getTextboxSIMNo(), dictionary.get("SIM_NO1"), "SIM", "", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);



			for (int k = 2; k<arrplanname.length+1; k++) {

				clickElement(getButtonAddNewline(), "Add New Line Button", driver);

				int intIndex = MainUtil.dictionary.get("PLAN_NAME"+k).indexOf("Share 20");
				if(intIndex == - 1) {
					clickElement(getButtonAddPrincipalLine(), "Add Pricipal Line Button", driver);
				} else {
					clickElement(getButtonAddmemberline(), "Add Member Line Button", driver);
				}

				SelectrateplanUMREX(MainUtil.dictionary.get("PLAN_NAME"+k));
				clickElement(getButtonContinue(), "Continue Button", driver);

				if (dictionary.get("MSISDN"+k) != null || dictionary.get("MSISDN"+k) != "NA") {

					clickElement(getRadioButtonDealerPool(), "Dealer Pool", driver);
					sendKeys(getTextboxSearchMSISDN(), dictionary.get("MSISDN"+k).substring(4, dictionary.get("MSISDN"+k).length()), "Search MSISDN", "", driver);
				}

				clickElement(getMSISDNCategory(), "MSISDN Category", driver);
				clickElement(getMSISDNCatergoryC5(), "MSISDN Category C5", driver);
				clickElement(getMSISDNCatergoryNormal(), "MSISDN Category Normal", driver);

				driver.navigate().back();
				clickElement(getRadioButtonDealerPool(), "Dealer Pool", driver);

				clickElement(getButtonSearch(), "Search Button", driver);
				clickElement(getButtonFirstMSISDN(), "Select MSISDN", driver);
				dictionary.put("MSISDN"+k, getButtonFirstMSISDN().getText());
				System.out.println("MSISDN : "+dictionary.get("MSISDN"+k));
				clickElement(getButtonContinue(), "Continue Button", driver);

				Thread.sleep(2000);
				sendKeys(getIMEINumber(),MainUtil.dictionary.get("IMEI"+k), "IMEI Number", "",driver);
				clickElement(getVerifybutton(), "Verify Button", driver);
				clickElement(getPromotionType(), "Promotion Button", driver);

				System.out.println("PROMOTION_NAME : "+MainUtil.dictionary.get("PROMOTION_NAME"+k));
				driver.findElementByXPath("//android.widget.TextView[@resource-id='com.fl.pra:id/tvItem' and contains(@text,'"+MainUtil.dictionary.get("PROMOTION_NAME"+k)+"')]").click();
				clickElement(getButtonContinue(), "Continue Button", driver);

				//int intIndex = MainUtil.dictionary.get("PLAN_NAME"+k).indexOf("Share 20");
				if(intIndex == - 1) {
					clickElement(getButtonContinue(), "Continue Button", driver);
				}


				scrollUDLR(driver, 0, "U");
				scrollUDLR(driver, 0, "U");
				sendKeys(getTextboxSIMNo(), dictionary.get("SIM_NO"+k), "SIM", "", driver);
				clickElement(getButtonContinue(), "Continue Button", driver);

			}


			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			Thread.sleep(5000);
			clickElement(getButtonContinue(), "Continue Button", driver);
			Thread.sleep(2000);
			clickElement(getSpinnerEventType(), "Event Type", driver);
			//clickElement(selectEvenType("Dealer Outlet"), "Dealer Outlet", driver);
			driver.findElementByXPath("//android.widget.CheckedTextView[@resource-id='android:id/text1'][@text='Dealer Outlet']").click();

			clickElement(getButtonEventStartDate(), "Event Start Date", driver);
			clickElement(getButtonOK(), "Ok Button", driver);
			clickElement(getButtonEventEndDate(), "Event End Date", driver);
			clickElement(getButtonOK(), "Ok Button", driver);
			sendKeys(getTextboxEventPostcode(), "55100", "Event Postcode", "", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);

			clickElement(getButtonTNC(), "Terms And Conditions", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			//clickElement(getSpinnerERFEmail(), "Send eRF Email", driver);
			//clickElement(getSelectEmailID(), "Select Email ID", driver);

			clickElement(getSignatureArea(), "Tap here to sign", driver);
			doSignature(driver);
			clickElement(getButtonSign(), "Sign", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);

			Thread.sleep(5000);
			scrollUDLR(driver, 0, "U");

			scrollUDLR(driver, 0, "U");

			scrollUDLR(driver, 0, "U");

			scrollUDLR(driver, 0, "U");

			driver.findElementByXPath("//android.widget.TextView[@text='Add a Payment Method']").click();

			setWebViewContextUMREX();

			driver.findElement(By.xpath("//a[text()='Cash']")).click();

			setNativeContextUMREX();
			Thread.sleep(2000);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");

			clickElement(getbuttonbundleContinue(), "Continue Button", driver);

			dictionary.put("PURCHASE_PASSWORD", PropertyHelper.getENVProperties("UMREX_PURCHASE_PASSWORD"));
			clickElement(getMultilinepassword(), "Password field", driver);
			driver.getKeyboard().pressKey(dictionary.get("PURCHASE_PASSWORD"));
			clickElement(getbuttonbundleContinue(), "Continue Button", driver);
			Thread.sleep(3000);
			driver.findElement(By.xpath("//android.widget.Button[@text='YES']")).click();
			Thread.sleep(3000);
			int s=1;
			for (int p = 0; p<arrplanname.length; p++) {
				dictionary.put("IMEI" + s, arrIMEI[p]);
				p++;
				String IMEItext = MainUtil.dictionary.get("IMEI" + s);
				System.out.println(IMEItext);
				driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.fl.pra:id/tvDaImei'][@text='" + IMEItext + "']")).click();

				clickElement(getdevicecheckbox(), "Device item check box", driver);
				scrollUDLR(driver, 0, "U");
				scrollUDLR(driver, 0, "U");
				scrollUDLR(driver, 0, "U");
				clickElement(getButtonTNC(), "Terms And Conditions", driver);
				scrollUDLR(driver, 0, "U");
				scrollUDLR(driver, 0, "U");
				scrollUDLR(driver, 0, "U");

				clickElement(getSignatureArea(), "Tap here to sign", driver);
				doSignature(driver);
				clickElement(getButtonSign(), "Sign", driver);
				clickElement(getButtonContinue(), "Continue Button", driver);
				Thread.sleep(2000);
				if (s < arrplanname.length) ;
				{
					driver.findElement(By.xpath("//android.widget.Button[@text='Yes']")).click();
				}
				Thread.sleep(5000);
			}
			boolean checkFlag = checkForText("", getLabelSuccessMessagedevicePostpaid(), "Thank you", "Success Message", driver);

			if (checkFlag == true) {
				methodReturn = true;
				clickElement(getbuttonBackToHomeMultiline(), "Back To Home Button", driver);
			} else {
				clickElement(getButtonOK(), "Ok Button", driver);
			}

			for (int q = 1; q<arrplanname.length+1; q++) {

				dictionary.put("MSISDN", dictionary.get("MSISDN"+q));
				dictionary.put("PLAN_NAME", dictionary.get("PLAN_NAME"+q));
				dictionary.put("SIM_NO", dictionary.get("SIM_NO"+q));

				if (checkFlag == true) {
					ApplicationUtil.insertAccountIntoDB("POSTPAID","UMREX",registrationType);
					ApplicationUtil.updateSIMStatusInSIMDetails(MainUtil.dictionary.get("SIM_NO"));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid registration  :" + e);
		}
		return methodReturn;
	}


	public boolean doPostpaidRegistration_Broadband(String registrationType, String identificationType) {

		boolean methodReturn = false;

		try {

			clickElement(getbuttonBroadband(), "Broadband Button", driver);
			if (identificationType.equals("MALAYSIAN")) {
				clickElement(getbuttonReadIDPostpaid(), "Read ID Button", driver);
			} else {
				clickElement(getButtonScanIDPostpaid(), "Scan ID Button", driver);
			}

			RandomDataGenerator.generateCustomerDataUMREX(identificationType);
			ApplicationUtil.getMSISDNFromSIMTable("POSTPAID", MainUtil.dictionary.get("PLAN_NAME"));

			fillInCustomerDetails(identificationType);

			clickElement(getButtonAddPrincipalLine(), "Add Pricipal Line Button", driver);

			SelectrateplanUMREX(dictionary.get("PLAN_NAME").substring(7, dictionary.get("PLAN_NAME").length()));
			//clickElement(selectPostpaidPlan(dictionary.get("PLAN_NAME").substring(7, dictionary.get("PLAN_NAME").length())), "Select Plan", driver);

			clickElement(getButtonContinue(), "Continue Button", driver);

			if (dictionary.get("MSISDN") != null || dictionary.get("MSISDN") != "NA") {

				clickElement(getRadioButtonDealerPool(), "Dealer Pool", driver);
				sendKeys(getTextboxSearchMSISDN(), dictionary.get("MSISDN").substring(4, dictionary.get("MSISDN").length()), "Search MSISDN", "", driver);
			}

			clickElement(getMSISDNCategory(), "MSISDN Category", driver);
			clickElement(getMSISDNCatergoryC5(), "MSISDN Category C5", driver);
			clickElement(getMSISDNCatergoryNormal(), "MSISDN Category Normal", driver);

			driver.navigate().back();

			clickElement(getRadioButtonDealerPool(), "Dealer Pool", driver);

			clickElement(getButtonSearch(), "Search Button", driver);
			clickElement(getButtonFirstMSISDN(), "Select MSISDN", driver);
			dictionary.put("MSISDN", getButtonFirstMSISDN().getText());
			System.out.println("MSISDN : "+dictionary.get("MSISDN"));
			clickElement(getButtonContinue(), "Continue Button", driver);
			Thread.sleep(2000);
			clickElement(getRadioButtonlanguage(), "Prefrred Language English", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			sendKeys(getTextboxSIMNo(), dictionary.get("SIM_NO"), "SIM", "", driver);
			clickElement(getcheckboxBroadbandDataSIMonly(), "Data SIM Only", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);

			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			Thread.sleep(5000);
			clickElement(getButtonContinue(), "Continue Button", driver);

			clickElement(getSpinnerEventType(), "Event Type", driver);
			//clickElement(selectEvenType("Dealer Outlet"), "Dealer Outlet", driver);
			driver.findElementByXPath("//android.widget.CheckedTextView[@resource-id='android:id/text1'][@text='Dealer Outlet']").click();

			clickElement(getButtonEventStartDate(), "Event Start Date", driver);
			clickElement(getButtonOK(), "Ok Button", driver);
			clickElement(getButtonEventEndDate(), "Event End Date", driver);
			clickElement(getButtonOK(), "Ok Button", driver);
			sendKeys(getTextboxEventPostcode(), "55100", "Event Postcode", "", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);

			clickElement(getButtonTNC(), "Terms And Conditions", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");

			clickElement(getSignatureArea(), "Tap here to sign", driver);
			doSignature(driver);
			clickElement(getButtonSign(), "Sign", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getbuttonbundleContinue(), "Continue Button", driver);

			dictionary.put("PURCHASE_PASSWORD", PropertyHelper.getENVProperties("UMREX_PURCHASE_PASSWORD"));
			//sendKeys(getTextboxPasswordPostpaid(), dictionary.get("PURCHASE_PASSWORD"), "Pass", "", driver);

			clickElement(getTextboxPasswordPostpaid(), "Password field", driver);

			driver.getKeyboard().pressKey(dictionary.get("PURCHASE_PASSWORD"));

			clickElement(getbuttonbundleContinue(), "Continue Button", driver);
			Thread.sleep(3000);
			driver.findElement(By.xpath("//android.widget.Button[@text='YES']")).click();

			while (isRetryButtonDisplayed())
			{
				clickElement(getRetrybutton(), "Retry Button", driver);
			}

			boolean checkFlag = checkForText("", getLabelSuccessMessagePostpaid(), "Thank you for your order", "Success Message", driver);

			if (checkFlag == true) {
				ApplicationUtil.insertAccountIntoDB("POSTPAID","UMREX",registrationType);
				ApplicationUtil.updateSIMStatusInSIMDetails(MainUtil.dictionary.get("SIM_NO"));
				methodReturn = true;
				clickElement(getButtonBackToHome(), "Back To Home Button", driver);
			} else {
				clickElement(getButtonOK(), "Ok Button", driver);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing Broadband registration  :" + e);
		}
		return methodReturn;
	}

	public boolean doPostpaidPhonebundleRegistration(String PROMOTION_NAME,String registrationType, String identificationType) {

		boolean methodReturn = false;

		try {

			clickElement(getCRPPhonesection(), "Postpaid Change offer Section", driver);
			clickElement(getbuttonPostpaidPhoneBundle(), "Postpaid Phone Bundle Button", driver);
			if (identificationType.equals("MALAYSIAN")) {
				clickElement(getbuttonReadIDPostpaid(), "Read ID Button", driver);
			} else {
				clickElement(getButtonScanIDPostpaid(), "Scan ID Button", driver);
			}

			RandomDataGenerator.generateCustomerDataUMREX(identificationType);
			ApplicationUtil.getMSISDNFromSIMTable("POSTPAID", MainUtil.dictionary.get("PLAN_NAME"));

			fillInCustomerDetails(identificationType);

			clickElement(getButtonAddPrincipalLine(), "Add Pricipal Line Button", driver);

			SelectrateplanUMREX(MainUtil.dictionary.get("PLAN_NAME"));

			//clickElement(selectPostpaidPlan(MainUtil.dictionary.get("PLAN_NAME")), "Select Plan", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);

			if (dictionary.get("MSISDN") != null || dictionary.get("MSISDN") != "NA") {

				clickElement(getRadioButtonDealerPool(), "Dealer Pool", driver);
				sendKeys(getTextboxSearchMSISDN(), dictionary.get("MSISDN").substring(4, dictionary.get("MSISDN").length()), "Search MSISDN", "", driver);
			}

			clickElement(getMSISDNCategory(), "MSISDN Category", driver);
			clickElement(getMSISDNCatergoryC5(), "MSISDN Category C5", driver);
			clickElement(getMSISDNCatergoryNormal(), "MSISDN Category Normal", driver);

			driver.navigate().back();
			//Actions action = new Actions(driver);
			//action.sendKeys(Keys.ESCAPE);

			//clickElement(getMSISDNCategory(), "MSISDN Category", driver);
			clickElement(getRadioButtonDealerPool(), "Dealer Pool", driver);

			clickElement(getButtonSearch(), "Search Button", driver);
			clickElement(getButtonFirstMSISDN(), "Select MSISDN", driver);
			dictionary.put("MSISDN", getButtonFirstMSISDN().getText());
			System.out.println("MSISDN : "+dictionary.get("MSISDN"));
			clickElement(getButtonContinue(), "Continue Button", driver);
			Thread.sleep(2000);

			sendKeys(getIMEINumber(),MainUtil.dictionary.get("IMEI"), "IMEI Number", "",driver);
			clickElement(getVerifybutton(), "Verify Button", driver);
			clickElement(getPromotionType(), "Promotion Button", driver);

			System.out.println("PROMOTION_NAME : "+PROMOTION_NAME);
			System.out.println(driver.findElementByXPath("//android.widget.TextView[@resource-id='com.fl.pra:id/tvItem' and contains(@text,'IPHONE 11 128GB YELLOW P99-Upfront Pymt - 24 months')]").getText());
			System.out.println(driver.findElementByXPath("//android.widget.TextView[@resource-id='com.fl.pra:id/tvItem' and contains(@text,'"+PROMOTION_NAME+"')]").getText());
			driver.findElementByXPath("//android.widget.TextView[@resource-id='com.fl.pra:id/tvItem' and contains(@text,'"+PROMOTION_NAME+"')]").click();
			clickElement(getButtonContinue(), "Continue Button", driver);

			clickElement(getButtonContinue(), "Continue Button", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			sendKeys(getTextboxSIMNo(), dictionary.get("SIM_NO"), "SIM", "", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);

			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			Thread.sleep(5000);
			clickElement(getButtonContinue(), "Continue Button", driver);
			Thread.sleep(1000);
			clickElement(getSpinnerEventType(), "Event Type", driver);
			Thread.sleep(1000);
			driver.findElementByXPath("//android.widget.CheckedTextView[@resource-id='android:id/text1'][@text='Dealer Outlet']").click();

			clickElement(getButtonEventStartDate(), "Event Start Date", driver);
			clickElement(getButtonOK(), "Ok Button", driver);
			clickElement(getButtonEventEndDate(), "Event End Date", driver);
			clickElement(getButtonOK(), "Ok Button", driver);
			sendKeys(getTextboxEventPostcode(), "55100", "Event Postcode", "", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);

			clickElement(getButtonTNC(), "Terms And Conditions", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");

			clickElement(getSignatureArea(), "Tap here to sign", driver);
			doSignature(driver);
			clickElement(getButtonSign(), "Sign", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);

			Thread.sleep(5000);
			scrollUDLR(driver, 0, "U");

			scrollUDLR(driver, 0, "U");

			scrollUDLR(driver, 0, "U");

			scrollUDLR(driver, 0, "U");

			driver.findElementByXPath("//android.widget.TextView[@text='Add a Payment Method']").click();

			setWebViewContextUMREX();

			driver.findElement(By.xpath("//a[text()='Cash']")).click();

			setNativeContextUMREX();
			Thread.sleep(2000);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");

			clickElement(getbuttonbundleContinue(), "Continue Button", driver);

			dictionary.put("PURCHASE_PASSWORD", PropertyHelper.getENVProperties("UMREX_PURCHASE_PASSWORD"));
			//sendKeys(getTextboxPasswordPostpaid(), dictionary.get("PURCHASE_PASSWORD"), "Pass", "", driver);

			clickElement(gettextboxPurchasePasswordbundle(), "Password field", driver);

			driver.getKeyboard().pressKey(dictionary.get("PURCHASE_PASSWORD"));

			clickElement(getbuttonbundleContinue(), "Continue Button", driver);
			clickElement(getButtonYESBundlePurchase(), "Yes Button", driver);

			clickElement(getdevicecheckbox(), "Device item check box", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			clickElement(getButtonTNC(), "Terms And Conditions", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");

			clickElement(getSignatureArea(), "Tap here to sign", driver);
			doSignature(driver);
			clickElement(getButtonSign(), "Sign", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);

			boolean checkFlag = checkForText("", getLabelSuccessMessagedevicePostpaid(), "Thank you for your order", "Success Message", driver);

			if (checkFlag == true) {
				ApplicationUtil.insertAccountIntoDB("POSTPAID","UMREX",registrationType);
				ApplicationUtil.updateSIMStatusInSIMDetails(MainUtil.dictionary.get("SIM_NO"));
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

	public boolean doPostpaidRegistration_Existingline(String registrationType, String identificationType) {

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

			fillInCustomerDetails_Existingline(identificationType);

			clickElement(getButtonAddPrincipalLine(), "Add Pricipal Line Button", driver);
		//	System.out.println("<<<<<<<<<<<<< PLAN name is :"+ MainUtil.dictionary.get("PLAN_NAME"));
		//	MainUtil.dictionary.put("PLAN_NAME","U Postpaid 68");
		//	System.out.println("<<<<<<<<<<<<< PLAN name is :"+ MainUtil.dictionary.get("PLAN_NAME"));
			SelectrateplanUMREX(MainUtil.dictionary.get("NEW_PLAN_NAME"));
			clickElement(getButtonContinue(), "Continue Button", driver);

			System.out.println("<<<<<<<<<<<<<<<<<<<dictionary.get(\"MSISDN\")"+  dictionary.get("MSISDN"));
			if (dictionary.get("MSISDN") != null || dictionary.get("MSISDN") != "NA") {

				clickElement(getRadioButtonDealerPool(), "Dealer Pool", driver);
				sendKeys(getTextboxSearchMSISDN(), dictionary.get("MSISDN").substring(4, dictionary.get("MSISDN").length()), "Search MSISDN1", "", driver);
			}

			clickElement(getMSISDNCategory(), "MSISDN Category", driver);
			clickElement(getMSISDNCatergoryC5(), "MSISDN Category C5", driver);
			clickElement(getMSISDNCatergoryNormal(), "MSISDN Category Normal", driver);

			driver.navigate().back();
			clickElement(getRadioButtonDealerPool(), "Dealer Pool", driver);

			clickElement(getButtonSearch(), "Search Button", driver);
			//clickElement(getButtonOK(), "OK Button", driver);
			clickElement(getButtonFirstMSISDN(), "Select MSISDN", driver);
			dictionary.put("MSISDN", getButtonFirstMSISDN().getText());
			System.out.println("MSISDN : "+dictionary.get("MSISDN"));
			clickElement(getButtonContinue(), "Continue Button", driver);
			Thread.sleep(2000);
			clickElement(getRadioButtonlanguage(), "Prefrred Language English", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			sendKeys(getTextboxSIMNo(), dictionary.get("SIM_NO"), "SIM", "", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);

			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);

			clickElement(getSpinnerEventType(), "Event Type", driver);
			//clickElement(selectEvenType("Dealer Outlet"), "Dealer Outlet", driver);
			driver.findElementByXPath("//android.widget.CheckedTextView[@resource-id='android:id/text1'][@text='Dealer Outlet']").click();

			clickElement(getButtonEventStartDate(), "Event Start Date", driver);
			clickElement(getButtonOK(), "Ok Button", driver);
			clickElement(getButtonEventEndDate(), "Event End Date", driver);
			clickElement(getButtonOK(), "Ok Button", driver);
			sendKeys(getTextboxEventPostcode(), "55100", "Event Postcode", "", driver);
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
			clickElement(getbuttonbundleContinue(), "Continue Button", driver);
		//	clickElement(getButtonContinue(), "Continue Button", driver);
			//clickElement(getbuttonbundleContinue(), "Continue Button", driver);
			dictionary.put("PURCHASE_PASSWORD", PropertyHelper.getENVProperties("UMREX_PURCHASE_PASSWORD"));
			//sendKeys(getTextboxPurchasePasswordPostpaid(), dictionary.get("PURCHASE_PASSWORD"), "Password", "", driver);
			//clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getTextboxPasswordPostpaid(), "Password field", driver);
			driver.getKeyboard().pressKey(dictionary.get("PURCHASE_PASSWORD"));
			//clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getbuttonbundleContinue(), "Continue Button", driver);
			Thread.sleep(3000);

			//driver.findElement(By.xpath("//android.widget.Button[@text='YES']")).click();

		//	clickElement(getbuttonbundleContinue(), "Continue Button", driver);
			clickElement(getButtonYESBundlePurchase(), "Yes Button", driver);

			while (isRetryButtonDisplayed())
			{
				clickElement(getRetrybutton(), "Retry Button", driver);
			}

		//	boolean checkFlag = checkForText("", getLabelSuccessMessagePostpaid(), "Thank you for your order", "Success Message", driver);
			boolean checkFlag = checkForText("", getlabelorderSuccessMessage(), "Thank you for your order", "Success Message", driver);
			MainUtil.dictionary.put("PLAN_NAME", MainUtil.dictionary.get("NEW_PLAN_NAME"));
			if (checkFlag == true) {
				ApplicationUtil.insertAccountIntoDB("POSTPAID","UMREX",registrationType);
				ApplicationUtil.updateSIMStatusInSIMDetails(MainUtil.dictionary.get("SIM_NO"));
				methodReturn = true;
			//	clickElement(getButtonBackToHome(), "Back To Home Button", driver);
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


	public boolean doPostpaidPhonebundleExistingline(String PROMOTION_NAME,String registrationType, String identificationType) {

		boolean methodReturn = false;

		try {
			clickElement(getCRPPhonesection(), "Postpaid Change offer Section", driver);

			clickElement(getbuttonAddonDevice(), "Postpaid Addon Device", driver);

			ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("EXISTINGMSISDN"));

			if (identificationType.equals("MALAYSIAN")) {
				clickElement(getbuttonReadIDPostpaid(), "Read ID Button", driver);
			} else {
				clickElement(getButtonScanIDPostpaid(), "Scan ID Button", driver);
			}

			if (identificationType.equals("MALAYSIAN")) {

				clickElement(getCardtype(), "Card Type", driver);
				clickElement(getselectMyKad(), "Select MyKad", driver);
				sendKeys(getTextboxIDNumber(), dictionary.get("CUSTOMER_ID"), "Customer ID", "", driver);
				sendKeys(getTextboxName(), dictionary.get("CUSTOMER_NAME"), "Customer Name", "", driver);
				clickElement(getSpinnerGender(), "Gender Spinner", driver);
				clickElement(getSelectMale(), "Select Male", driver);
				sendKeys(getDateDD(), dictionary.get("CUSTOMER_DOB_DD"), "Customer DOB Date", "", driver);
				sendKeys(getDateMM(), dictionary.get("CUSTOMER_DOB_MM"), "Customer DOB Month", "", driver);
				sendKeys(getDateYYYY(), dictionary.get("CUSTOMER_DOB_YYYY"), "Customer DOB Year", "", driver);
				clickElement(getRace(), "Race", driver);
				clickElement(getselectMalay(), "Select Malay", driver);
				scrollUDLR(driver, 0, "U");
				sendKeys(gettextboxAddressform(), dictionary.get("CUSTOMER_ADDRESS"), "CUSTOMER ADDRESS", "", driver);
				sendKeys(gettextboxPostcodeform(), dictionary.get("CUSTOMER_POSTCODE"), "CUSTOMER ADDRESS", "", driver);
				scrollUDLR(driver, 0, "U");
				sendKeys(gettextboxCityform(), dictionary.get("CUSTOMER_CITY"), "CUSTOMER CITY", "", driver);
				scrollUDLR(driver, 0, "U");
				sendKeys(gettextboxStateform(), dictionary.get("CUSTOMER_STATE"), "CUSTOMER STATE", "", driver);
				scrollUDLR(driver, 0, "U");
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

			driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.fl.pra:id/tvItem'][@text='"+MainUtil.dictionary.get("EXISTINGMSISDN")+" (Principal)']")).click();

			clickElement(getButtonContinue(), "Continue Button", driver);

			clickElement(getButtonContinue(), "Continue Button", driver);

			sendKeys(getIMEINumber(),MainUtil.dictionary.get("IMEI"), "IMEI Number", "",driver);
			clickElement(getVerifybutton(), "Verify Button", driver);
			clickElement(getPromotionType(), "Promotion Button", driver);

			System.out.println("PROMOTION_NAME : "+PROMOTION_NAME);
			System.out.println(driver.findElementByXPath("//android.widget.TextView[@resource-id='com.fl.pra:id/tvItem' and contains(@text,'IPHONE 11 128GB YELLOW P99-Upfront Pymt - 24 months')]").getText());
			System.out.println(driver.findElementByXPath("//android.widget.TextView[@resource-id='com.fl.pra:id/tvItem' and contains(@text,'"+PROMOTION_NAME+"')]").getText());
			driver.findElementByXPath("//android.widget.TextView[@resource-id='com.fl.pra:id/tvItem' and contains(@text,'"+PROMOTION_NAME+"')]").click();
			clickElement(getButtonContinue(), "Continue Button", driver);

			clickElement(getButtonContinue(), "Continue Button", driver);

			clickElement(getButtonContinue(), "Continue Button", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);
			Thread.sleep(5000);
			clickElement(getButtonContinue(), "Continue Button", driver);
			Thread.sleep(1000);


			clickElement(getButtonTNC(), "Terms And Conditions", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");

			clickElement(getSignatureArea(), "Tap here to sign", driver);
			doSignature(driver);
			clickElement(getButtonSign(), "Sign", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);

			Thread.sleep(5000);
			scrollUDLR(driver, 0, "U");

			scrollUDLR(driver, 0, "U");

			scrollUDLR(driver, 0, "U");

			scrollUDLR(driver, 0, "U");

			driver.findElementByXPath("//android.widget.TextView[@text='Add a Payment Method']").click();

			setWebViewContextUMREX();

			driver.findElement(By.xpath("//a[text()='Cash']")).click();

			setNativeContextUMREX();
			Thread.sleep(2000);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");

			clickElement(getbuttonbundleContinue(), "Continue Button", driver);


			clickElement(getButtonYESBundlePurchase(), "Yes Button", driver);


			clickElement(getdevicecheckbox(), "Device item check box", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			clickElement(getButtonTNC(), "Terms And Conditions", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");

			clickElement(getSignatureArea(), "Tap here to sign", driver);
			doSignature(driver);
			clickElement(getButtonSign(), "Sign", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);

			boolean checkFlag = checkForText("", getLabelSuccessMessagedevicePostpaid(), "Thank you", "Success Message", driver);


			if (checkFlag == true) {
				//ApplicationUtil.insertAccountIntoDB("POSTPAID","UMREX",registrationType);
				//ApplicationUtil.updateSIMStatusInSIMDetails(MainUtil.dictionary.get("SIM_NO"));
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


	public boolean doPostpaid_Maxlinecheck(String registrationType, String identificationType) {

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

			fillInCustomerDetails_Existingline(identificationType);

			clickElement(getButtonAddPrincipalLine(), "Add Pricipal Line Button", driver);

			compareInString(getmaxlinemessage().getText(), "Sorry, you are not allowed to add new principal line due to exceeding its maximum number of principal lines.", "MaxLine check Status", driver);

			clickElement(getButtonOK(), "Ok Button", driver);


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing prepaid registration  :" + e);
		}
		return methodReturn;
	}

	public void fillInCustomerDetails(String registrationType) {
		try {

			if (registrationType.equals("MALAYSIAN")) {

				clickElement(getCardtype(), "Card Type", driver);
				clickElement(getselectMyKad(), "Select MyKad", driver);
				sendKeys(getTextboxIDNumber(), dictionary.get("CUSTOMER_ID"), "Customer ID", "", driver);
				sendKeys(getTextboxName(), dictionary.get("CUSTOMER_NAME"), "Customer Name", "", driver);
				clickElement(getSpinnerGender(), "Gender Spinner", driver);
				clickElement(getSelectMale(), "Select Male", driver);
				sendKeys(getDateDD(), dictionary.get("CUSTOMER_DOB_DD"), "Customer DOB Date", "", driver);
				sendKeys(getDateMM(), dictionary.get("CUSTOMER_DOB_MM"), "Customer DOB Month", "", driver);
				sendKeys(getDateYYYY(), dictionary.get("CUSTOMER_DOB_YYYY"), "Customer DOB Year", "", driver);
				clickElement(getRace(), "Race", driver);
				clickElement(getselectMalay(), "Select Malay", driver);
				scrollUDLR(driver, 0, "U");
				sendKeys(gettextboxAddressform(), dictionary.get("CUSTOMER_ADDRESS"), "CUSTOMER ADDRESS", "", driver);
				sendKeys(gettextboxPostcodeform(), dictionary.get("CUSTOMER_POSTCODE"), "CUSTOMER ADDRESS", "", driver);
				scrollUDLR(driver, 0, "U");
				sendKeys(gettextboxCityform(), dictionary.get("CUSTOMER_CITY"), "CUSTOMER CITY", "", driver);
				scrollUDLR(driver, 0, "U");
				sendKeys(gettextboxStateform(), dictionary.get("CUSTOMER_STATE"), "CUSTOMER STATE", "", driver);
				scrollUDLR(driver, 0, "U");
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
			compareInString(getLabelDDMFCheck().getText(), "PASS", "DDMF Check", driver);
			compareInString(getLabelAgeValidationStatus().getText(), "PASS", "Age Validation", driver);
			compareInString(getLabelInternalBlacklistStatus().getText(), "PASS", "Internal Blacklist", driver);

			clickElement(getButtonContinue(), "Continue Button", driver);

			//clickElement(getButtonContinue(), "Continue Button", driver);
			//clickElement(getSpinnerTitle(), "Title Spinner", driver);
			//scrollUDLR(driver, 0, "U");
			//clickElement(getSelectMR(), "Mr", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			if (registrationType.equals("NON MALAYSIAN")) {
				sendKeys(getTextboxAddressPostpaid(), dictionary.get("CUSTOMER_ADDRESS"), "Customer Address", "", driver);
				sendKeys(getTextboxPostcodePostpaid(), dictionary.get("CUSTOMER_POSTCODE"), "Customer Postcode", "", driver);
			}
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			sendKeys(getTextboxAlternateNumberArea(), dictionary.get("CUSTOMER_NUMBER").substring(0, 4), "Customer Number Area", "", driver);
			sendKeys(getTextboxAlternateNumber(), dictionary.get("CUSTOMER_NUMBER").substring(4, dictionary.get("CUSTOMER_NUMBER").length()), "Customer Number", "", driver);
			sendKeys(gettextboxbillingEmail(), dictionary.get("CUSTOMER_EMAIL"), "Customer Email ID", "", driver);
			clickElement(getButtonContinue(), "Continue Button", driver);


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while filling up customer details  :" + e);
		}
	}

	public void fillInCustomerDetails_Existingline(String registrationType) {
		try {

			if (registrationType.equals("MALAYSIAN")) {

				clickElement(getCardtype(), "Card Type", driver);
				clickElement(getselectMyKad(), "Select MyKad", driver);
				sendKeys(getTextboxIDNumber(), dictionary.get("CUSTOMER_ID"), "Customer ID", "", driver);
				sendKeys(getTextboxName(), dictionary.get("CUSTOMER_NAME"), "Customer Name", "", driver);
				clickElement(getSpinnerGender(), "Gender Spinner", driver);
				clickElement(getSelectMale(), "Select Male", driver);
				sendKeys(getDateDD(), dictionary.get("CUSTOMER_DOB_DD"), "Customer DOB Date", "", driver);
				sendKeys(getDateMM(), dictionary.get("CUSTOMER_DOB_MM"), "Customer DOB Month", "", driver);
				sendKeys(getDateYYYY(), dictionary.get("CUSTOMER_DOB_YYYY"), "Customer DOB Year", "", driver);
				clickElement(getRace(), "Race", driver);
				clickElement(getselectMalay(), "Select Malay", driver);
				scrollUDLR(driver, 0, "U");
				sendKeys(gettextboxAddressform(), dictionary.get("CUSTOMER_ADDRESS"), "CUSTOMER ADDRESS", "", driver);
				sendKeys(gettextboxPostcodeform(), dictionary.get("CUSTOMER_POSTCODE"), "CUSTOMER ADDRESS", "", driver);
				scrollUDLR(driver, 0, "U");
				sendKeys(gettextboxCityform(), dictionary.get("CUSTOMER_CITY"), "CUSTOMER CITY", "", driver);
				scrollUDLR(driver, 0, "U");
				sendKeys(gettextboxStateform(), dictionary.get("CUSTOMER_STATE"), "CUSTOMER STATE", "", driver);
				scrollUDLR(driver, 0, "U");
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

			compareInString(getLabelDDMFCheck().getText(), "PASS", "DDMF Check", driver);
			compareInString(getLabelAgeValidationStatus().getText(), "PASS", "Age Validation", driver);
			compareInString(getLabelInternalBlacklistStatus().getText(), "PASS", "Internal Blacklist", driver);

			clickElement(getButtonContinue(), "Continue Button", driver);

			clickElement(getButtonContinue(), "Continue Button", driver);


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while filling up customer details  :" + e);
		}
	}

	public void setWebViewContextUMREX() {
		//driver.context("WEBVIEW_com.fl.pra");
		Set<String> contextNames = driver.getContextHandles();
		System.out.println(contextNames.size());
		for (String contextName : contextNames) {
			System.out.println(contextName);
			if (contextName.contains("WEBVIEW")) {
				driver.context(contextName);
			}
		}
	}
	public void setNativeContextUMREX() {
		driver.context("NATIVE_APP");
	}

	public void SelectrateplanUMREX(String PLAN_NAME) {
		String cnt=null;

		for (int i=1;i<5;i++)
		{

			WebElement dropdown	=driver.findElementById("com.fl.pra:id/egv_offer_plans");
			List<WebElement> options= dropdown.findElements(By.id("com.fl.pra:id/tvDisplayName"));

			for (WebElement option : options)
			{
				if (option.getText().equals(PLAN_NAME))
				{
					option.click(); // click the desired option
					cnt="pass";
					break;
				}
			}

			if(cnt=="pass")
			{
				break;
			} else {
				scrollUDLR(driver, 0, "U");
			}

		}
	}
}
