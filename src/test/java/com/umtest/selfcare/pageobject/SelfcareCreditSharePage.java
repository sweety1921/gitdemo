package com.umtest.selfcare.pageobject;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

public class SelfcareCreditSharePage extends MainUtil {

	private RemoteWebDriver driver;

	public SelfcareCreditSharePage(RemoteWebDriver driver) {
		this.driver = driver;
	}
	
//	@FindBy(xpath = "//div[@class='menu credit_transfer expand']")
	@FindBy(xpath = "//span[contains(text(),'CreditShare')]")
	private WebElement buttonCredittransfer;
	
	@FindBy(xpath = "//a[contains(text(),'Transfer Credit')]")
	private WebElement TransferCredit;
	
	public WebElement getbuttonCredittransfer() {
		return AppWait.waitForElementToBeClickable(driver, buttonCredittransfer);
	}

	public void clickCreditTransferMenu(){
		clickElement(getbuttonCredittransfer(), "Credit Transfer Menu link", driver);
	}

	public WebElement getTransferCredit() {
		return AppWait.waitForElementToBeClickable(driver, TransferCredit);
	}

	public void clickCreditTransfer(){
		clickElement(getTransferCredit(), "Transfer Credit", driver);
	}
	
	@FindBy(xpath = "//span[text()='RM 1']")
	private WebElement buttontrsfamount_1;
	
	public WebElement getbuttontrsfamount_1() {
		return AppWait.waitForElementToBeClickable(driver, buttontrsfamount_1);
	}
	
	@FindBy(xpath = "//span[text()='RM 3']")
	private WebElement buttontrsfamount_3;
	
	public WebElement getbuttontrsfamount_3() {
		return AppWait.waitForElementToBeClickable(driver, buttontrsfamount_3);
	}
	
	@FindBy(xpath = "//span[text()='RM 5']")
	private WebElement buttontrsfamount_5;
	
	public WebElement getbuttontrsfamount_5() {
		return AppWait.waitForElementToBeClickable(driver, buttontrsfamount_5);
	}
	
	@FindBy(xpath = "//label[contains(text(),'10')]")
	private WebElement buttontrsfamount_10;
	
	public WebElement getbuttontrsfamount_10() {
		return AppWait.waitForElementToBeClickable(driver, buttontrsfamount_10);
	}

	public void selectTransferAmount(String sAmount) {

		if (sAmount.equals("1")) {
			clickElement(getbuttontrsfamount_1(), "Select Amount 1", driver);
		} else if (sAmount.equals("3")) {
			clickElement(getbuttontrsfamount_3(), "Select Amount 3", driver);
		} else if (sAmount.equals("5")) {
			clickElement(getbuttontrsfamount_5(), "Select Amount 5", driver);
		} else {
			clickElement(getbuttontrsfamount_10(), "Select Amount 5", driver);
		}
	}
	@FindBy(xpath = "//input[@id='PrePhoneNo']")
	private WebElement prephonenumber;

	@FindBy(xpath = "//ul[@id='ui-id-1']")
	private WebElement receiverMobNumberPrefix;

	public WebElement getSelectPrefixHomePhone(){
		return AppWait.waitForElementToBeClickable(driver, receiverMobNumberPrefix);
	}
	
	public WebElement getprephonenumber() {
		return AppWait.waitForElementToBeClickable(driver, prephonenumber);
	}
	
	@FindBy(xpath = "//input[@id='PhoneNo']")
	private WebElement PhoneNo;
	
	public WebElement getPhoneNo() {
		return AppWait.waitForElementToBeClickable(driver, PhoneNo);
	}

	public void enterMobileNumber(String sMSISDN){
		sendKeys(getprephonenumber(), sMSISDN.substring(1, 4), "Pre Mobile Number", "", driver);
		clickElement(getSelectPrefixHomePhone(), "Prefix Home Phone number Select", driver);
		sendKeys(getPhoneNo(), sMSISDN.substring(4), "Mobile Number", "", driver);
	}
	
		
//	@FindBy(xpath = "//input[@name='submitBtn']")
	@FindBy(xpath = "//span[contains(text(),'Transfer Credit')]")
	private WebElement buttontransfercredit;
	
	public WebElement getbuttontransfercredit() {
		return AppWait.waitForElementToBeClickable(driver, buttontransfercredit);
	}

	public void clickTransferCreditButton(){
		clickElement(getbuttontransfercredit(), "Button Transfer Credit", driver);
	}
	
	@FindBy(xpath = "//p[contains(text(),'Your request is currently being processed')]")
	private WebElement sucessmsg;
	
	public WebElement getsucessmsg() {
		return AppWait.waitForElementToBeClickable(driver, sucessmsg);
	}
	
	@FindBy(xpath = "//tbody/tr[1]/td[1]/input[1]")
	private WebElement Closebutton;
	
	public WebElement getClosebutton() {
		return AppWait.waitForElementToBeClickable(driver, Closebutton);
	}

	public void clickCloseButton(){
		clickElement(getClosebutton(), "Button Close", driver);
	}
	
	
}
