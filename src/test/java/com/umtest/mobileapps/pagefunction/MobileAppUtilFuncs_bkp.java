package com.umtest.mobileapps.pagefunction;

import com.umtest.testframe.lib.ExtentScreenCapture;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.umtest.mobileapps.pageobject.MobileAppUtilPage;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.listener.ExtentTestNGITestListener;

import java.time.Duration;

import static com.umtest.testframe.lib.JSONReader.parseJSONFile;
import static com.umtest.testframe.listener.ExtentTestNGITestListener.getTest;

public class MobileAppUtilFuncs_bkp extends MobileAppUtilPage {

	private static Logger logger = LogManager.getLogger(MobileAppUtilFuncs_bkp.class);
	private static AndroidDriver driver;

	public MobileAppUtilFuncs_bkp(RemoteWebDriver driver) {
		super((AndroidDriver) driver);
		this.driver = (AndroidDriver) driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);

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

	public void verifyMobileAppPrepaidBundle1() {
		try {
			scrollUDLR(driver, 1, "D");
			scrollUDLR(driver, 1, "D");
			scrollUDLR(driver, 1, "D");

			verifyBalanceAndPlanName();

			verifyUsageDetailsMobileAppPrepaid1();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying mobile apps  :" + e);
		}
	}

	public void verifyMobileAppBundlePurchase() {
		try {
			scrollUDLR(driver, 1, "D");
			scrollUDLR(driver, 1, "D");
			scrollUDLR(driver, 1, "D");

			verifyBalanceAndPlanName();

			verifyUsageDetailsMobileAppPrepaid1();

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

	public void verifyMobileAppNow() {
		try {
			scrollUDLR(driver, 1, "D");
			scrollUDLR(driver, 1, "D");
			Thread.sleep(4000);

			verifyBalanceAndPlanName();
			clickElement(getUpgradeplanbutton(), "Upgrade Plan Button", driver);
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
			compareInString(getLabelAccountBalance().getText(), "RM" + MainUtil.dictionary.get("MAIN_BALANCE"), "Account Balance", driver);
			compareInString(getLabelPlanName().getText(), MainUtil.dictionary.get("PLAN_NAME"), "Plan Name", driver);
			captureAppScreenshot("Capture Screen - Balance and Rate Plan", driver);
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

	public void verifyUsageDetailsMobileApp() {
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

			verifyServiceDetailsMobileApp(driver);

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
			//ApplicationUtil.verifyServiceDetailsMobileAppPrepaid(driver);


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
			enterSixDigitPIN();
			Thread.sleep(3000);

			getTest().get().pass("Request Successful",
					ExtentScreenCapture.captureSrceenPass("Request Success - Screencapture", driver));

			boolean flag1 = compareInString(getTextSuccessMessage().getText(), "Request Successful", "Confirmation", driver);
			boolean flag2 = compareInString(getTextYourRequestProcessedMessage().getText(), "Your request is being processed. A confirmation SMS will be sent to you.", "Your request is being processed...", driver);

			if (flag1 && flag2) {
				String senderChargers = MainUtil.calculateBalance(MainUtil.dictionary.get("TRANSFER_AMOUNT"), "-" + ApplicationUtil.getSenderReceiverFee("sender_sfee", MainUtil.dictionary.get("TRANSFER_AMOUNT")));
				String receivingAmount = MainUtil.calculateBalance(MainUtil.dictionary.get("TRANSFER_AMOUNT"), ApplicationUtil.getSenderReceiverFee("receiver_sfee", MainUtil.dictionary.get("TRANSFER_AMOUNT")));

				ApplicationUtil.updateAccInfoAfterCreditTransfer(MainUtil.dictionary.get("MSISDN"), "main_balance", MainUtil.calculateBalance(MainUtil.dictionary.get("CURRENT_BALANCE"), senderChargers));
				ApplicationUtil.updateAccInfoAfterCreditTransfer(MainUtil.dictionary.get("RECEIVER_MSISDN"), "credit_amount", MainUtil.calculateBalance(MainUtil.dictionary.get("CREDIT_AMOUNT"), "-" + receivingAmount));
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
			compareInString(getLabelCreditShareBal().getText(), "RM" + MainUtil.dictionary.get("CREDIT_AMOUNT"), "Credit Share Balance", driver);
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

			List<WebElement> childs = driver.findElementsByXPath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[2]");
			for (WebElement element : childs) {

				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
				LocalDateTime todaysDate = LocalDateTime.now();
				String subscribtionDate = dtf.format(todaysDate);
				compareInString(element.findElement(By.xpath("(//*[@class='android.widget.TextView'])[1]")).getText(), "RM" + MainUtil.dictionary.get("Amount") + ".00  Paid", "Amount Paid", driver);
				compareInString(element.findElement(By.xpath("(//*[@class='android.widget.TextView'])[2]")).getText(), subscribtionDate, "Date of Amount Paid", driver);
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
						String Nextchargingdate = dtf.format(currentday);
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


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while verifying mobile apps Golife :" + e);
		}
	}

	public boolean doChangerateplan(String newplanname) {
		boolean flag = false;
		try {
			clickElement(getUpgradeplanbutton(), "Click Upgrade Plan Link", driver);

			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");

			clickElement(getChangePlanNowbutton(), "Click Change Plan Now Button", driver);

			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");

			clickElement(getProceedbutton(), "Click Proceed Button", driver);
			//Thread.sleep(10000);


			scrollUDLR(driver, 0, "U");
			scrollUDLR(driver, 0, "U");

			getCurrntplanname();
			clickElement(getCurrntplanname(), "Click currentplan description", driver);
			clickElement(getCurrntplanname(), "Click currentplan description", driver);

			String planname = driver.findElementByXPath("//android.view.View/android.view.View[3]/android.view.View/android.view.View[1]/android.view.View[1]/android.view.View").getText();
			System.out.println(planname);
			if (planname.equals(newplanname)) {
				driver.findElement(By.xpath("(//android.view.View[@content-desc='Change'])[1]")).click();
			} else {
				for (int t = 1; t < 15; t++) {
					clickElement(getcrpplanboxnextbutton(), "Click Next Button", driver);
					String plannm = (driver.findElementByXPath("//android.view.View/android.view.View[4]/android.view.View/android.view.View[1]/android.view.View[1]/android.view.View").getText());
					System.out.println(plannm);
					if (plannm.equals(newplanname)) {
						System.out.println("test" + plannm);
						driver.findElement(By.xpath("(//android.view.View[@content-desc='Change'])[2]")).click();
						break;
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

			//getmonthlyfee();
			//clickElement(getmonthlyfee(), "Click MonthlyFee description", driver);
			//clickElement(getCurrntplanname(), "Click currentplan description", driver);
			scrollUDLR(driver, 0, "U");


			driver.findElement(By.xpath("//android.widget.CheckBox[@text='I agree with the Terms & Conditions set by U Mobile and wish to continue']")).click();

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


	public static synchronized void verifyServiceDetailsMobileApp(AndroidDriver driver) throws Exception {

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



	public static void placeServiceCardInCenterOfScreen(String sServiceTitle){

		int a=getServiceTitleYAxisLocation(sServiceTitle);
		while(a<550){
			scrollUDLRShort(driver,0,"D");
			a=getServiceTitleYAxisLocation(sServiceTitle);
		}

		int b=getServiceTitleYAxisLocation(sServiceTitle);

		while(b>770){
			scrollUDLR(driver,0,"U");
			/*scrollUDLRShort(driver,0,"U");*/
			b=getServiceTitleYAxisLocation(sServiceTitle);
		}


	}

}
