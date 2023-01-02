package com.umtest.isd.pagefunction;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.umtest.isd.pageobject.isdSubscriberInfoPage;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.umb.pagefunction.UMBLoginLogoutFuncs;

public class isdSubscriberInfoFunc extends isdSubscriberInfoPage{

	private RemoteWebDriver driver;
	private static Logger logger = LogManager.getLogger(UMBLoginLogoutFuncs.class);
	public isdSubscriberInfoFunc(RemoteWebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public void verifySubscriberInfo() {
		
		try {
			
			isdLoginLogoutFunc isdlogin =new isdLoginLogoutFunc(driver);
			isdlogin.isdLogin();
			clickElement(getMenuUMBandBLC(), "Click the UMB & BLC Menu", driver);
			clickElement(getMenuBLCSMTool(), "", driver);
			fetchOptionFromDropDown(getdropdownOption(), "exists", driver);
			sendKeys(getTextMSISDN(), MainUtil.dictionary.get("MSISDN"), "Enter MSISDN", "", driver);
			clickElement(getButtonSubmit(), "Click on Submit button", driver);
			scrollToWebElement(getTableHeader(), driver);
			
			WebElement tableElements = driver.findElement(By.xpath("//table[@class='table table-bordered table-hover']"));
			List<WebElement> tData = tableElements.findElements(By.tagName("td"));
			for ( WebElement i : tData ) {
				  logger.info(i.getText());
				  
				}
			String operationState = gettableData1().getText();
			String msisdn = gettableData3().getText();
			String bundleSNCode = gettableData4().getText();
			String bundleName = (gettableData5().getText()).replaceAll(" ", "");
			String purchaseDate = gettableData6().getText();
			String ExpiryDate = gettableData7().getText();
			String autoFlag = gettableData9().getText();
			String renewalState = gettableData10().getText();
			String processState = gettableData11().getText();
			String StatusCode = gettableData12().getText();
			String FreeCount = gettableData13().getText();
			String active = gettableData14().getText();
			String bundletype = gettableData15().getText();
			String subscriptiondate = gettableData16().getText();
			
			String expectedPurchaseDate = changeDateFormat(MainUtil.dictionary.get("PURCHASE_DATE"), "yyyy-mm-dd");
			String expectedExpiryDate = changeDateFormat(MainUtil.dictionary.get("BUNDLE_EXPIRY_DATE"), "yyyy-mm-dd");
			
			MainUtil.compareInString(operationState, "SUCCESS", "Verify operation status", driver);
			MainUtil.compareInString(msisdn, MainUtil.dictionary.get("MSISDN"), "Verify msisdn", driver);
			MainUtil.compareInString(bundleSNCode, ApplicationUtil.getPrepaidBundleSNCode(MainUtil.dictionary.get("BUNDLE_NAME")), "Verify Bundle Sn Code", driver);
			MainUtil.compareInString(bundleName, MainUtil.dictionary.get("BUNDLE_NAME"), "Verify Bundle name", driver);
			MainUtil.compareInString(purchaseDate, expectedPurchaseDate, "Verify the purhase date", driver);
			MainUtil.compareInString(ExpiryDate, expectedExpiryDate, "Verify Expiry Date", driver);
			MainUtil.compareInString(autoFlag, "Y", "Verify Auto Flag status", driver);
			MainUtil.compareInString(renewalState, "PreExpiry_Notif1", "Verify Renewal state", driver);
			MainUtil.compareInString(processState, "Open", "Verify Process state", driver);
			MainUtil.compareInString(StatusCode, "Inserted", "Verify Status Code", driver);
			MainUtil.compareInString(FreeCount, "0", "Verify Free Count", driver);
			MainUtil.compareInString(active, "Y", "Verify Active Status", driver);
		//	MainUtil.compareInString(bundletype, , "Verify operation status", driver);
			MainUtil.compareInString(subscriptiondate, expectedPurchaseDate, "Verify Subscription Date", driver);
			
			isdlogin.isdLogout();
			
		}catch(Exception e) {
			
		}
	}
	
}
