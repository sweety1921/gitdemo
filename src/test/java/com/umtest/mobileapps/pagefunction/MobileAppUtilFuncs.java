package com.umtest.mobileapps.pagefunction;

import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.ExtentScreenCapture;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.lang.reflect.Method;

import com.umtest.mobileapps.pageobject.MobileAppUtilPage;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.listener.ExtentTestNGITestListener;

import java.time.Duration;

import static com.umtest.testframe.base.DriverFactory.getDriver;
import static com.umtest.testframe.lib.JSONReader.parseJSONFile;
import static com.umtest.testframe.listener.ExtentTestNGITestListener.getTest;

public class MobileAppUtilFuncs extends MobileAppUtilPage {

	private static Logger logger = LogManager.getLogger(MobileAppUtilFuncs.class);
	private static AndroidDriver driver;
	private RemoteWebDriver driverApp;

	public static RemoteWebDriver driverMobileApp;
	MobileAppLoginFuncs loginFuncs;

	MobileAppLoginFuncs mobileAppLoginFuncs;

	public MobileAppUtilFuncs(RemoteWebDriver driver) {
		super((AndroidDriver) driver);
		this.driver = (AndroidDriver) driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);

	}


//###############################################################################################################################################################################

	public void verifyMobileAppBundlePurchase(String sMSISDN, String sAccountType, String sTestDataFile) {
		try {

			loginFuncs = new MobileAppLoginFuncs(driver);
			loginFuncs.loginMobileApp(sMSISDN);

			if(sAccountType.equalsIgnoreCase("PREPAID")) {
				verifyBalanceAndPlanName();
			}

			scrollUDLR((MobileDriver) driver, 0, "U");

			if (verifyElementIsDisplayed(getButtonViewUsageDetails(), "View Usage Details Button", driver)) {
				clickElement(getButtonViewUsageDetails(), "View Usage Details Button", driver);
			} else {
				scrollUDLR((MobileDriver) driver, 0, "U");
				clickElement(getButtonViewUsageDetails(), "View Usage Details Button", driver);
			}

			//getFirstUsageDetailsCategory().click();
			Thread.sleep(1000);

			captureAppScreenshot("Screen Capture : Service Categories", driver);
			verifyServiceDetailsMobileApp(sTestDataFile,(AndroidDriver) driver);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying mobile apps  :" + e);
		}finally {
			DriverFactory.quitMobileApp(driver);
		}
	}

	public static synchronized void verifyServiceDetailsMobileApp(String sTestDataFile, AndroidDriver driver) throws Exception {

		JSONObject joBundle;
		JSONArray jaServiceCategories;
		JSONObject joServiceCategory;
		JSONArray jaServices;

		String sServiceCategoryType;

		JSONObject joServiceItems;
		String sServiceName;
		String sServiceQuota;
		String sServiceUsage;
		String sServiceValidity;

		joBundle = parseJSONFile("src/test/resources/testData/"+sTestDataFile);

		jaServiceCategories = (JSONArray) joBundle.get("serviceCategory");

		int i = jaServiceCategories.size();
		clickServiceCategory("Data");
		for (int iVar1 = 0; iVar1 < jaServiceCategories.size(); iVar1++) {

			joServiceCategory = (JSONObject) jaServiceCategories.get(iVar1);

			sServiceCategoryType = joServiceCategory.get("type").toString();

			if (isServiceCategoryExists(sServiceCategoryType)) {

				clickServiceCategory(sServiceCategoryType);

				jaServices = (JSONArray) joServiceCategory.get("services");

				for (int iVar2 = 0; iVar2 < jaServices.size(); iVar2++) {

					joServiceItems = (JSONObject) jaServices.get(iVar2);

					sServiceName = joServiceItems.get("serviceName").toString();
					scrollUDLR(driver, 0, "U");
					if(!isServiceCardExists(sServiceName)) {
						//scrollUDLR(driver, 0, "U");

						/*while (!isButtonUpgradePlanExists()) {
							scrollUDLR(driver, 0, "U");
						}*/

						int iCtr = 0;
						while (!isServiceCardExists(sServiceName) && iCtr < 8) {
							scrollUDLR(driver, 0, "U");
							iCtr++;
						}
					}

					if (isServiceCardExists(sServiceName)) {
						//placeServiceCardInCenterOfScreen(sServiceName);

						sServiceQuota = joServiceItems.get("quota").toString();
						sServiceUsage = joServiceItems.get("usage").toString();
						sServiceValidity = joServiceItems.get("validity").toString();

						MainUtil.captureAppScreenshot("Capture Screen - Service : "+sServiceName, driver);

						MainUtil.compareInString(getTextServiceName(sServiceName),sServiceName,"***SERVICE NAME", driver);
						MainUtil.compareInString(getTextServiceQuota(sServiceName),sServiceQuota,"Service Quota", driver);

						/*if(!sServiceUsage.equalsIgnoreCase("NA")){
							MainUtil.compareInString(getTextServiceUsage(sServiceName),sServiceUsage,"Service Usage", driver);
						}

						if(!sServiceValidity.equalsIgnoreCase("NA")){
							MainUtil.compareInString(getTextServiceValidity(sServiceName),sServiceValidity,"Service Validity " + sServiceName, driver);
						}*/
					}else{
						MainUtil.compareInString("Service NOT DISPLAYED",sServiceName, "Service" + sServiceName, driver);
					}

				}
				scrollUDLR(driver, 0, "U");
				//scrollUDLR(driver, 0, "U");

				while (!isButtonUpgradePlanExists()){
					scrollUDLR(driver, 0, "D");
				}
				clickServiceCategory(sServiceCategoryType);

			} else {
				MainUtil.compareInString("",sServiceCategoryType, "Service Category " + sServiceCategoryType + " NOT DISPLAYED", driver);
			}
		}
	}

//###############################################################################################################################################################################


	public void verifyMobileAppPrepaidTopUp() {
		try {

			verifyBalanceAndPlanName();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while verifying mobile apps  :" + e);
		}
	}

	public void verifyUsageDetailsMobileApp(String sTestDataFile) {
		try {

			ExtentTestNGITestListener.createNode("Mobile App Usage Verification");
			scrollUDLR(driver, 0, "U");

			if (verifyElementIsDisplayed(getButtonViewUsageDetails(), "View Usage Details Button", driver)) {
				clickElement(getButtonViewUsageDetails(), "View Usage Details Button", driver);
			} else {
				scrollUDLR(driver, 0, "U");
				clickElement(getButtonViewUsageDetails(), "View Usage Details Button", driver);
			}

			getFirstUsageDetailsCategory().click();
			Thread.sleep(1000);

			getTest().get().pass("Service Categories",
					ExtentScreenCapture.captureSrceenPass("Service-Categories " + dictionary.get("BUNDLE_NAME"), driver));

			verifyServiceDetailsMobileApp(sTestDataFile,driver);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying usage details  :" + e);
		}
	}





























	public void verifyMobileApp(String verificationType) {

		try {
			if (verificationType.toUpperCase().contains("PREPAID")) {
				scrollUDLR(driver, 1, "D");
				verifyBalanceAndPlanName();
			}

			verifyUsageDetails(verificationType);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying mobile apps  :" + e);
		}
	}

	public void verifyMobileAppPrepaidBundle() {
		try {
			scrollUDLR(driver, 1, "D");
			scrollUDLR(driver, 1, "D");
			scrollUDLR(driver, 1, "D");

			verifyBalanceAndPlanName();

			verifyUsageDetailsMobileAppPrepaid();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying mobile apps  :" + e);
		}
	}

	public void verifyMobileAppPrepaidTopUpOnly() {
		try {
			scrollUDLR(driver, 1, "D");
			scrollUDLR(driver, 1, "D");
			scrollUDLR(driver, 1, "D");

			verifyBalanceAndPlanName();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying mobile apps  :" + e);
		}
	}

	public void verifyMobileAppByPurchaseIterationPrepaidBundle(String sIteration) {
		try {
			scrollUDLR(driver, 1, "D");
			scrollUDLR(driver, 1, "D");
			scrollUDLR(driver, 1, "D");
			verifyBalanceAndPlanName();
			verifyUsageDetailsByPurchaseIterationMobileAppPrepaid(sIteration);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying mobile apps  :" + e);
		}
	}


	public void verifyBalanceAndPlanName() {

		try {
			captureAppScreenshot("Capture Screen - Balance and Rate Plan", driver);

			String sExpectedRatePlan=mapMobileAppRatePlanNames(MainUtil.dictionary.get("PLAN_NAME"));

			compareInString(captureAccountBalance(), "RM" + MainUtil.dictionary.get("MAIN_BALANCE"), "Account Balance", driver);
//			compareInString(captureRatePlanName(),sExpectedRatePlan, "Plan Name", driver);
			compareInString(captureRatePlanNameMob(),sExpectedRatePlan, "Plan Name", driver);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while verifying mobile app  :" + e);
		}
	}

	public void verifyBundleName() {

		try {
			compareInString(getLabelAccountBalance().getText(), "RM" + MainUtil.dictionary.get("MAIN_BALANCE"), "Account Balance", driver);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while verifying mobile app  :" + e);
		}
	}

	public void storeMobileAppBalace() {

		try {
			//scrollUDLR(driver, 1, "D");
			scrollUDLR(driver, 1, "D");
			String currentBalance = getLabelAccountBalance().getText();
			MainUtil.dictionary.put("CURRENT_MAIN_BALANCE", currentBalance.substring(2, currentBalance.length()));
			logger.info(MainUtil.dictionary.get("CURRENT_MAIN_BALANCE"));
			captureAppScreenshot("Balance and Plan Name Information", driver);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while storing mail balance  :" + e);
		}
	}


	public void verifyUsageDetails_Vinod(String verificationType) {
		try {

			ExtentTestNGITestListener.createNode("Mobile App Usage Verification");
			if (verifyElementIsDisplayed(getButtonViewUsageDetails(), "View Usage Details Button", driver)) {
				clickElement(getButtonViewUsageDetails(), "View Usage Details Button", driver);
			} else {
				//scrollUDLR(driver, 0, "U");
				clickElement(getButtonViewUsageDetails(), "View Usage Details Button", driver);
			}


			getFirstUsageDetailsCategory().click();
			Thread.sleep(2000);


			ApplicationUtil.verifyMobileAppServiceDetails(verificationType, driver);


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying usage details  :" + e);
		}
	}

	public void verifyUsageDetailsMobileAppPrepaid() {
		try {

			ExtentTestNGITestListener.createNode("Mobile App Usage Verification");
			scrollUDLR(driver, 0, "U");

			if (verifyElementIsDisplayed(getButtonViewUsageDetails(), "View Usage Details Button", driver)) {
				clickElement(getButtonViewUsageDetails(), "View Usage Details Button", driver);
			} else {
				scrollUDLR(driver, 0, "U");
				clickElement(getButtonViewUsageDetails(), "View Usage Details Button", driver);
			}

			getFirstUsageDetailsCategory().click();
			Thread.sleep(1000);

			getTest().get().pass("Service Categories",
					ExtentScreenCapture.captureSrceenPass("Service-Categories " + dictionary.get("BUNDLE_NAME"), driver));

			ApplicationUtil.verifyServiceDetailsMobileAppPrepaid(driver);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying usage details  :" + e);
		}
	}

	public void verifyUsageDetailsMobileAppPrepaid1() {
		try {

			ExtentTestNGITestListener.createNode("Mobile App Usage Verification");
			scrollUDLR(driver, 0, "U");

			if (verifyElementIsDisplayed(getButtonViewUsageDetails(), "View Usage Details Button", driver)) {
				clickElement(getButtonViewUsageDetails(), "View Usage Details Button", driver);
			} else {
				scrollUDLR(driver, 0, "U");
				clickElement(getButtonViewUsageDetails(), "View Usage Details Button", driver);
			}

			getFirstUsageDetailsCategory().click();
			Thread.sleep(1000);

			getTest().get().pass("Service Categories",
					ExtentScreenCapture.captureSrceenPass("Service-Categories " + dictionary.get("BUNDLE_NAME"), driver));

			//verifyServiceDetailsMobileAppPrepaid1(driver);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying usage details  :" + e);
		}
	}




	public void verifyUsageDetailsByPurchaseIterationMobileAppPrepaid(String sIteration) {
		try {

			ExtentTestNGITestListener.createNode("Mobile App Usage Verification");
			scrollUDLR(driver, 0, "U");

			if (verifyElementIsDisplayed(getButtonViewUsageDetails(), "View Usage Details Button", driver)) {
				clickElement(getButtonViewUsageDetails(), "View Usage Details Button", driver);
			} else {
				scrollUDLR(driver, 0, "U");
				clickElement(getButtonViewUsageDetails(), "View Usage Details Button", driver);
			}
			getFirstUsageDetailsCategory().click();
			Thread.sleep(1000);

			getTest().get().pass("Service Categories",
					ExtentScreenCapture.captureSrceenPass("Service-Categories " + dictionary.get("BUNDLE_NAME"), driver));

			ApplicationUtil.verifyServiceDetailsByPurchaseIterationMobileAppPrepaid(driver, sIteration);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying usage details  :" + e);
		}
	}

	public void verifyUsageDetails(String verificationType) {
		try {

			ExtentTestNGITestListener.createNode("Mobile App Usage Verification");
			scrollUDLR(driver, 0, "U");
			if (verifyElementIsDisplayed(getButtonViewUsageDetails(), "View Usage Details Button", driver)) {
				clickElement(getButtonViewUsageDetails(), "View Usage Details Button", driver);
			} else {
				scrollUDLR(driver, 0, "U");
				clickElement(getButtonViewUsageDetails(), "View Usage Details Button", driver);
			}
			getFirstUsageDetailsCategory().click();
			Thread.sleep(1000);

			getTest().get().pass("Service Categories",
					ExtentScreenCapture.captureSrceenPass("Service-Categories " + dictionary.get("BUNDLE_NAME"), driver));

			System.out.println(verificationType);
			ApplicationUtil.verifyMobileAppServiceDetails(verificationType, driver);



		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying usage details  :" + e);
		}
	}


	public boolean performCreditShare() {
		boolean flag = false;
		try {

			//MobileAppUtilPage mobilePage = new MobileAppUtilPage(driver);
			//	Method getterMethod =mobilePage.getClass().getMethod("getButtonRM" + MainUtil.dictionary.get("TRANSFER_AMOUNT"));
			clickElement(getMenuIcon(), "Click Menu Icon", driver);
			clickElement(getMenuTopUpCredit(), "Click on TopUp & Credit menu", driver);
			clickElement(getMenuCreditShare(), "Click on Credit Share icon", driver);
			clickElement(getButtonTransferCredit1(), "Click on Transfer Credit Share button", driver);
			sendKeys(getTextReceiverMsisdn(), MainUtil.dictionary.get("RECEIVER_MSISDN"), "Enter Receiver's MSISDN", "", driver);
			WebElement element = driver.findElement(By.xpath("//*[@content-desc=\"cell_RM" + MainUtil.dictionary.get("TRANSFER_AMOUNT") + "\"]"));

			clickElement(element, "Select Denomination:" + MainUtil.dictionary.get("TRANSFER_AMOUNT"), driver);
			//	clickElement(getButtonDenomination(), "Select Denomination:" + MainUtil.dictionary.get("TRANSFER_AMOUNT") , driver);
			scrollUDLR(driver, 0, "U");
			clickElement(getButtonTransferCredit2(), "Click on Transfer Credit Share button", driver);
			clickElement(getMobAppPinVerification(), "Click on Mobile App PIN Verification radio button", driver);
			clickElement(getContinueButton(), "Click on Continue button", driver);
			enterSixDigitPIN();
			Thread.sleep(3000);

			getTest().get().pass("Request Successful",
					ExtentScreenCapture.captureSrceenPass("Request Success - Screencapture", driver));

//			boolean flag1 = compareInString(getTextSuccessMessage().getText(), "Request Successful", "Confirmation", driver);
			boolean flag1 = compareInString(getCreditTransferSuccessMsg().getText(), "Credit Transfer Successful", "Confirmation", driver);
			boolean flag2 = compareInString(getTextYourRequestProcessedMessage().getText(), "Your request is being processed. A confirmation SMS will be sent to you.", "Your request is being processed...", driver);

			if (flag1 && flag2) {
				String senderChargers = MainUtil.calculateBalance(MainUtil.dictionary.get("TRANSFER_AMOUNT"), "-" + ApplicationUtil.getSenderReceiverFee("sender_sfee", MainUtil.dictionary.get("TRANSFER_AMOUNT")));
				String receivingAmount = MainUtil.calculateBalance(MainUtil.dictionary.get("TRANSFER_AMOUNT"), ApplicationUtil.getSenderReceiverFee("receiver_sfee", MainUtil.dictionary.get("TRANSFER_AMOUNT")));
				String endDate = MainUtil.calculateExpiryDateAfterCreditTransfer(MainUtil.dictionary.get("RECEIVERS_ACTIVE_END_DATE"),MainUtil.dictionary.get("TRANSFER_AMOUNT"));
				MainUtil.dictionary.put("NEW_EXPIRY_DATE_RECEIVER",endDate);
				logger.info(MainUtil.dictionary.get("NEW_EXPIRY_DATE_RECEIVER"));

				ApplicationUtil.updateAccInfoAfterCreditTransfer(MainUtil.dictionary.get("MSISDN"), "main_balance", MainUtil.calculateBalance(MainUtil.dictionary.get("CURRENT_BALANCE"), senderChargers));
				ApplicationUtil.updateAccInfoAfterCreditTransfer(MainUtil.dictionary.get("RECEIVER_MSISDN"), "credit_amount", MainUtil.calculateBalance(MainUtil.dictionary.get("CREDIT_AMOUNT"), "-" + receivingAmount));
				ApplicationUtil.updateAccInfoAfterCreditTransfer(MainUtil.dictionary.get("RECEIVER_MSISDN"), "active_end_date", MainUtil.dictionary.get("NEW_EXPIRY_DATE_RECEIVER"));
				flag = true;
			}


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying mobile apps  :" + e);
		}
		return flag;
	}

	public void verifyCreditShareBalance() {

		try {

			verifyBalanceAndPlanName();
			clickElement(getMenuIcon(), "Click Menu Icon", driver);
			clickElement(getMenuTopUpCredit(), "Click on TopUp & Credit menu", driver);
			clickElement(getMenuCreditShare(), "Click on Credit Share icon", driver);
			Thread.sleep(2000);
			if((MainUtil.dictionary.get("CREDIT_AMOUNT").contains("NA"))||
					(MainUtil.dictionary.get("CREDIT_AMOUNT")==null)){
//				MainUtil.dictionary.put("CREDIT_AMOUNT","0.00");
				logger.info("No need to verify credit share balance for Sender");
			}
			else {
				compareInString(getLabelCreditShareBal().getText(), "RM" + MainUtil.dictionary.get("CREDIT_AMOUNT"), "Credit Share Balance", driver);
				captureAppScreenshot("Screen Capture: Credit Share Balance", driver);
			}
			//compareInString(getLabelPlanName().getText(), MainUtil.dictionary.get("PLAN_NAME"), "Plan Name", driver);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying mobile apps  :" + e);
		}
	}

	public void verifyPostpaidbillpayment() {

		try {

			clickElement(getMenuIcon(), "Click Menu Icon", driver);
			clickElement(getMenuBillsandPayment(), "Click on Bills & Payment menu", driver);
			clickElement(getMenuBillHistory(), "Click on Bill History Menu", driver);
			Thread.sleep(5000);
			driver.findElement(By.xpath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.TextView[1]")).click();
            int j=0;
			for(int i = 1; i<10;i++){
				List<WebElement> childs1 = driver.findElementsByXPath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup["+i+"]/android.widget.TextView[2]");
				for (WebElement element1 : childs1) {
					System.out.println(element1.getText());

					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
					DateTimeFormatter dtfmnt = DateTimeFormatter.ofPattern("dd MMMM");
					LocalDateTime todaysDate = LocalDateTime.now();
					String subscribtionDate = dtf.format(todaysDate);
					String subscribtionmnt = dtfmnt.format(todaysDate);
					System.out.println(subscribtionDate);
					if(element1.getText().contains(subscribtionmnt)) {
						compareInString(element1.getText(), subscribtionDate, "paybill Date", driver);
						j=1;
						break;

					}

				}
				if(j==1){
					List<WebElement> childs = driver.findElementsByXPath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup["+i+"]/android.widget.TextView[1]");
					//List<WebElement> childs1 = driver.findElementsByXPath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.TextView[2]");
					for (WebElement element : childs) {
						System.out.println(element.getText());

						if(element.getText().contains("RM")) {
							compareInString(element.getText(), "RM" + MainUtil.dictionary.get("Amount") + ".00  Paid", "Amount Paid", driver);
						}
						//	compareInString(element.findElement(By.xpath("(//*[@class='android.widget.TextView'])[2]")).getText(), subscribtionDate, "Date of Amount Paid", driver);
					}
					break;
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying mobile apps  :" + e);
		}
	}

	public void verifyMobileAppGolifeInsurance(String Golife) {

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
				scrollUDLR(driver, 0, "U");
				Insuranceplanname = "GoLife 10";
				clickElement(getButtonGoLife10(), "Click Golife10 insurance Section", driver);
			}

			clickElement(getViewCertificatebutton(), "Click View Certificate button", driver);



			for (int i = 3; i < 7; i++) {

				List<WebElement> childs = driver.findElementsByXPath("//android.view.ViewGroup[2]/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[" + i + "]");
				for (WebElement element : childs) {

					if (i == 3) {
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
						LocalDateTime todaysDate = LocalDateTime.now();
						String subscribtionDate = dtf.format(todaysDate);
						compareInString(element.findElement(By.xpath("(//*[@class='android.widget.TextView'])[1]")).getText(), "Date of Subscription", "Date of Subscription description", driver);
						compareInString(element.findElement(By.xpath("(//*[@class='android.widget.TextView'])[2]")).getText(), subscribtionDate, "Date of Subscription", driver);
					} else if (i == 4) {
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
						LocalDateTime todaysDate = LocalDateTime.now();
						LocalDateTime currentmonth = todaysDate.plusMonths(1);
						LocalDateTime currentday = todaysDate.plusDays(30);
						String Nextchargingdate = dtf.format(currentmonth);
						System.out.println(Nextchargingdate);
						compareInString(element.findElement(By.xpath("(//*[@class='android.widget.TextView'])[1]")).getText(), "Date of next charging", "Date of Next Charging description", driver);
						compareInString(element.findElement(By.xpath("(//*[@class='android.widget.TextView'])[2]")).getText(), Nextchargingdate, "Date of Next Charging", driver);
					} else if (i == 5) {
						compareInString(element.findElement(By.xpath("(//*[@class='android.widget.TextView'])[1]")).getText(), "Plan", "Plan description", driver);
						compareInString(element.findElement(By.xpath("(//*[@class='android.widget.TextView'])[2]")).getText(), Insuranceplanname, "Plan Name", driver);
					} else {
						compareInString(element.findElement(By.xpath("(//*[@class='android.widget.TextView'])[1]")).getText(), "Status", "Status description", driver);
						compareInString(element.findElement(By.xpath("(//*[@class='android.widget.TextView'])[2]")).getText(), "ACTIVE", "Plan Status", driver);
					}
				}
			}

			captureAppScreenshot("Capture Screen - View Certificate",driver);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying mobile apps Golife :" + e);
		}
	}

	public boolean doChangerateplan(String newplanname) {
		boolean flag = false;
		try {
			clickElement(getUpgradeplanbutton(), "Click Upgrade Plan Link", driver);
			Thread.sleep(10000);
			clickElement(getUpgradeplandesc(), "Click Upgrade Plan Description", driver);

			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");

			clickElement(getChangePlanNowbutton(), "Click Change Plan Now Button", driver);

			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");

			clickElement(getProceedbutton(), "Click Proceed Button", driver);
			Thread.sleep(5000);

			getCurrntplanname();
			clickElement(getCurrntplanname(), "Click currentplan description", driver);
			clickElement(getCurrntplanname(), "Click currentplan description", driver);

			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");

			driver.findElement(By.xpath("//android.widget.TextView[@text='Suitable Plans For You']")).click();
			//driver.findElement(By.xpath("//android.view.View/android.view.View[1]/android.view.View/android.view.View[1]/android.widget.TextView[1]")).click();
			String planname = driver.findElement(By.xpath("//android.view.View/android.view.View[1]/android.view.View/android.view.View[1]/android.widget.TextView[1]")).getText();
			System.out.println(planname);
			if (planname.contains(newplanname)) {
				driver.findElement(By.xpath("(//android.view.View[@content-desc='Change'])[1]")).click();
			} else {
				for (int t = 1; t < 15; t++) {
					//driver.findElementByXPath("//android.view.View/android.view.View[2]/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]").click();
					//clickElement(getcrpplanboxnextbutton(), "Click Next Button", driver);
					//driver.findElement(By.xpath("//android.widget.TextView[@text='Suitable Plans For You']")).click();
					driver.findElement(By.xpath("//android.view.View/android.view.View[1]/android.view.View/android.view.View[1]/android.widget.TextView[1]")).click();
					String plannm = (driver.findElementByXPath("android.view.View/android.view.View[2]/android.view.View/android.view.View[1]/android.widget.TextView[1]").getText());
					System.out.println(plannm);
					if (plannm.equals(newplanname)) {
						//driver.findElementByXPath("//android.view.View/android.view.View[2]/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]").click();
						System.out.println("test" + plannm);
						driver.findElement(By.xpath("(//android.view.View[@content-desc='Change'])[2]")).click();
						break;
					} else {
						driver.findElementByXPath("//android.view.View/android.view.View[2]/android.view.View/android.view.View[1]/android.widget.TextView[1]").click();
					}
				}
			}

			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			getCurrntplanname();
			clickElement(getCurrntplanname(), "Click currentplan description", driver);
			clickElement(getCurrntplanname(), "Click currentplan description", driver);
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");
			//getmonthlyfee();
			//clickElement(getmonthlyfee(), "Click MonthlyFee description", driver);
			//clickElement(getCurrntplanname(), "Click currentplan description", driver);
			scrollUDLR(driver, 0, "U");


			driver.findElement(By.xpath("//android.widget.TextView[@text='I agree with the']")).click();

			//			.xpath("//android.widget.CheckBox[@text='I agree with the Terms & Conditions set by U Mobile and wish to continue']")).click();
			driver.findElement(By.xpath("//android.widget.Button[@text='Confirm']")).click();
			//	android.widget.TextView[@text='']

			List<WebElement> childs = driver.findElementsByClassName("android.widget.CheckBox");
			for (WebElement element : childs) {
				System.out.println(element.getSize());
				System.out.println(element.getText());
			}

			//ApplicationUtil.updateRateplanName(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("NewRatePlanFullname")) ;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while performing mobile apps changeplan :" + e);
		}
		return flag;
	}

	public void scrollToALLSERVICES() {
		while (isElementAllServicesVisible() == false) {
			scrollUDLR(driver, 1, "U");
			scrollUDLR(driver, 1, "U");
		}
	}



	public static synchronized void verifyServiceDetailsMobileAppBundlePurchase(AndroidDriver driver) throws Exception {

		JSONObject joBundle;
		JSONArray jaServiceCategories;
		JSONObject joServiceCategory;
		JSONArray jaServices;

		String sServiceCategoryType;

		JSONObject joServiceItems;
		String sServiceName;
		String sServiceQuota;
		String sServiceUsage;
		String sServiceValidity;

		joBundle = parseJSONFile("src/test/resources/testData/"+MainUtil.dictionary.get("TESTDATAFILE"));

		jaServiceCategories = (JSONArray) joBundle.get("serviceCategory");

		int i = jaServiceCategories.size();

		for (int iVar1 = 0; iVar1 < jaServiceCategories.size(); iVar1++) {

			joServiceCategory = (JSONObject) jaServiceCategories.get(iVar1);

			sServiceCategoryType = joServiceCategory.get("type").toString();

			if (isServiceCategoryExists(sServiceCategoryType)) {

				clickServiceCategory(sServiceCategoryType);

				jaServices = (JSONArray) joServiceCategory.get("services");

				for (int iVar2 = 0; iVar2 < jaServices.size(); iVar2++) {

					joServiceItems = (JSONObject) jaServices.get(iVar2);

					sServiceName = joServiceItems.get("serviceName").toString();

					if(!isServiceCardExists(sServiceName)) {
						scrollUDLR(driver, 0, "D");

						while (!isButtonUpgradePlanExists()) {
							scrollUDLR(driver, 0, "D");
						}

						int iCtr = 0;
						while (!isServiceCardExists(sServiceName) && iCtr < 8) {
							scrollUDLR(driver, 0, "U");
							iCtr++;
						}
					}

					if (isServiceCardExists(sServiceName)) {
						placeServiceCardInCenterOfScreen(sServiceName);

						sServiceQuota = joServiceItems.get("quota").toString();
						sServiceUsage = joServiceItems.get("usage").toString();
						sServiceValidity = joServiceItems.get("validity").toString();

						MainUtil.captureAppScreenshot("Capture Screen - Service : "+sServiceName, driver);

						MainUtil.compareInString(getTextServiceName(sServiceName),sServiceName,"***SERVICE NAME", driver);
						MainUtil.compareInString(getTextServiceQuota(sServiceName),sServiceQuota,"Service Quota", driver);

						if(!sServiceUsage.equalsIgnoreCase("NA")){
							MainUtil.compareInString(getTextServiceUsage(sServiceName),sServiceUsage,"Service Usage", driver);
						}

						if(!sServiceValidity.equalsIgnoreCase("NA")){
							MainUtil.compareInString(getTextServiceValidity(sServiceName),sServiceValidity,"Service Validity " + sServiceName, driver);
						}
					}else{
						MainUtil.compareInString("Service NOT DISPLAYED",sServiceName, "Service" + sServiceName, driver);
					}

				}
				scrollUDLR(driver, 0, "D");
				scrollUDLR(driver, 0, "D");
				scrollUDLR(driver, 0, "D");
				while (!isButtonUpgradePlanExists()){
					scrollUDLR(driver, 0, "D");
				}
				clickServiceCategory(sServiceCategoryType);

			} else {
				MainUtil.compareInString("",sServiceCategoryType, "Service Category " + sServiceCategoryType + " NOT DISPLAYED", driver);
			}



		}
	}

	public static void placeServiceCardInCenterOfScreen(String sServiceTitle) throws InterruptedException {

		int a=getServiceTitleYAxisLocation(sServiceTitle);
		while(a<550){
			//scrollUDLRShort(driver,0,"U");
			scrollUDLRMobileApp(driver, 1, "U");
			Thread.sleep(1000);
			a=getServiceTitleYAxisLocation(sServiceTitle);
		}

		int b=getServiceTitleYAxisLocation(sServiceTitle);

		while(b>810){
			scrollUDLR(driver,0,"U");
			Thread.sleep(1000);
			/*scrollUDLRShort(driver,0,"U");*/
			b=getServiceTitleYAxisLocation(sServiceTitle);
		}
	}


	public String mapMobileAppRatePlanNames(String sRatePlanName) {

		if (sRatePlanName.equalsIgnoreCase("Unlimited Funz")) {
			return "Unlimited Funz Plan";
		} else if (sRatePlanName.equalsIgnoreCase("Unlimited Power Plan")) {
			return "Unlimited Power Plan";
		} else if (sRatePlanName.equalsIgnoreCase("MB Prepaid Rate Plan")) {
			return "MB Prepaid Rate Plan";
		} else if (sRatePlanName.equalsIgnoreCase("U Prepaid Plan")) {
			return "U Prepaid Plan";
		} else if (sRatePlanName.equalsIgnoreCase("New U Prepaid Plan")) {
			return "New U Prepaid Plan";
		} else if (sRatePlanName.equalsIgnoreCase("Power Prepaid Plan")) {
			return "Power Prepaid Plan";
		} else if (sRatePlanName.contains("5G Prepaid")) {
			return "5G Prepaid";
		} else
		{
			return "Invalid Rate Plan name";
		}

	}

	public void verifyMobileApplication(String sTestDataFile) {

		try {
			if((MainUtil.dictionary.get("PLAN_NAME").toUpperCase()).contains("PREPAID")) {
				verifyBalanceAndPlanName();
			}
			clickElement(getButtonViewUsageDetails(), "View Usage Details Button", driver);
			verifyServiceDetailsMobileApp(sTestDataFile, driver);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying mobile apps  :" + e);
		}
	}

}
