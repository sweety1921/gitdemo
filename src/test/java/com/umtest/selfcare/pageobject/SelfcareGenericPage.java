package com.umtest.selfcare.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;

public class SelfcareGenericPage extends MainUtil {

	private RemoteWebDriver driver;

	public SelfcareGenericPage(RemoteWebDriver driver) {
		this.driver = driver;
	}
	@FindBy(xpath = "//p[contains(text(),'Change PIN')]")
	private WebElement ChangePin;

	@FindBy(xpath = "//input[@id='oldpassword']")
	private WebElement Oldpassword;

	@FindBy(xpath = "//input[@id='newPassword']")
	private WebElement Newpassword;

	@FindBy(xpath = "//input[@id='rnewpin']")
	private WebElement retyprNewpassword;

	@FindBy(xpath = "//button[@id='change_pin']")
	private WebElement buttonChangePin;

	@FindBy(xpath = "//tbody/tr[1]/td[1]/input[1]")
	private WebElement buttonClose;

	@FindBy(xpath = "//a[contains(text(),'Roaming and IDD Services Activation')]")
	private WebElement RoamingandIDDService;

	@FindBy(xpath = "//a[contains(text(),'Check Status')]")
	private WebElement CheckStatus;

	@FindBy(xpath = "//form[1]/div[1]/div[1]/div[2]/div[2]/label[1]/i[1]")
	private WebElement Termsandcondition;

	@FindBy(xpath = "//button[contains(text(),'Activate Now')]")
	private WebElement ActivateNow;

//	@FindBy(xpath = "//div[@class='menu my_account expand']")
	@FindBy(xpath = "//div[contains(@class,'menu-item')]/span[contains(text(),'Manage Account')]")
	private WebElement buttonManageAccount;


	@FindBy(xpath = "//div[@class='menu my_bill expand']")
	private WebElement Billandpayment;

	@FindBy(xpath = "//div[@class='menu my_bill expand']//a[contains(text(),'E-Billing')]")
	private WebElement EBill;

	@FindBy(xpath = "//button[contains(text(),'Switch to e-billing')]")
	private WebElement SwitchEBillbutton;

	@FindBy(xpath = "//input[@id='email']")
	private WebElement Selfcareebillemail;


	@FindBy(xpath = "//input[@id='change_limit']")
	private WebElement Selfcareebillsubmit;

	@FindBy(xpath = "//div[@class='menu my_bill expand']//a[contains(text(),'Auto Debit')]")
	private WebElement Autodebit;

	@FindBy(xpath = "//a[contains(text(),\"Let's Go\")]")
	private WebElement buttonLetsGo;

	@FindBy(xpath = "//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/div[1]/div[4]/div[1]/div[1]/div[1]/div[1]/div[2]/input[1]")
	private WebElement Autodebitcardnumber;

	@FindBy(xpath = "//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/div[1]/div[4]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/input[1]")
	private WebElement Autodebitcardname;

	@FindBy(xpath = "//div[@id='select-month']")
	private WebElement buttonmonth;

	@FindBy(xpath = "//body/div[@id='menu-month']/div[2]/ul[1]/li[13]")
	private WebElement Selectmonth;

	@FindBy(xpath = "//div[@id='select-year']")
	private WebElement buttonyear;

	@FindBy(xpath = "//body/div[@id='menu-year']/div[2]/ul[1]/li[10]")
	private WebElement Selectyear;

	@FindBy(xpath = "//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/div[1]/div[4]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/input[1]")
	private WebElement CVVnumber;

	@FindBy(xpath = "//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/div[1]/div[5]/div[1]/div[1]/span[1]/span[1]/input[1]")
	private WebElement Confirmcheckbox;

	@FindBy(xpath = "//span[contains(text(),'Submit')]")
	private WebElement buttonSubmit;

	@FindBy(xpath = "//a[contains(text(),'Personal Details')]")
	private WebElement ButtonPersonalDetails;

	@FindBy(xpath = "//span[contains(text(),'Edit Personal Details')]")
	private WebElement ButtonEDITPersonalDetails;

	@FindBy(xpath = "//input[@id='AddressStreet']")
	private WebElement textAddress1;

	@FindBy(xpath = "//input[@id='AddressNote1']")
	private WebElement textAddress2;

	@FindBy(xpath = "//input[@id='Zipcode']")
	private WebElement textZipcode;

	@FindBy(xpath = "//ul[@id='ui-id-1']")
	private WebElement selectZipcode;

	@FindBy(xpath = "//ul[@id='ui-id-3']")
	private WebElement selectPrefixHomePhone;

	@FindBy(xpath = "//input[@id='EmailAddress']")
	private WebElement textEmailAddress;

	@FindBy(xpath = "//input[@id='PrePixelHomePhoneNo']")
	private WebElement textprehomenumber;

	@FindBy(xpath = "//input[@id='PixelHomePhoneNo']")
	private WebElement texthomenumber;

	@FindBy(xpath = "//input[@id='PreFaxNo']")
	private WebElement textprefaxnumber;

	@FindBy(xpath = "//input[@id='FaxNo']")
	private WebElement textFaxnumber;

	@FindBy(xpath = "//input[@id='PreOffPhoneNo']")
	private WebElement textpreoffnumber;

	@FindBy(xpath = "//input[@id='agreeterm']/following-sibling::span[@class='checkmark']")
	private WebElement textagreement;

	@FindBy(xpath = "//span[contains(text(),'Save Personal Details')]")
	private WebElement Savepersonaldetail;

	@FindBy(xpath = "//h3[contains(text(),'Success')]")
	private WebElement textsucessmsg;

	@FindBy(xpath = "//tbody/tr[1]/td[1]/input[1]")
	private WebElement textclosebtn;

	public WebElement getBillandpayment() {
		return AppWait.waitForElementToBeClickable(driver, Billandpayment);
	}

	public WebElement getEBill() {
		return AppWait.waitForElementToBeClickable(driver, EBill);
	}

	public WebElement getSwitchEBillbutton() {
		return AppWait.waitForElementToBeClickable(driver, SwitchEBillbutton);
	}

	public WebElement getSelfcareebillemail() {
		return AppWait.waitForElementToBeClickable(driver, Selfcareebillemail);
	}

	public WebElement getSelfcareebillsubmit() {
		return AppWait.waitForElementToBeClickable(driver, Selfcareebillsubmit);
	}

	public WebElement getButtonManageAccount() {
		return AppWait.waitForElementToBeClickable(driver, buttonManageAccount);
	}

	public WebElement getChangePin() {
		return AppWait.waitForElementToBeClickable(driver, ChangePin);
	}

	public WebElement getOldpassword() {
		return AppWait.waitForElementToBeClickable(driver, Oldpassword);
	}

	public WebElement getNewpassword() {
		return AppWait.waitForElementToBeClickable(driver, Newpassword);
	}

	public WebElement getretyprNewpassword() {
		return AppWait.waitForElementToBeClickable(driver, retyprNewpassword);
	}

	public WebElement getbuttonChangePin() {
		return AppWait.waitForElementToBeClickable(driver, buttonChangePin);
	}

	public WebElement getbuttonClose() {
		return AppWait.waitForElementToBeClickable(driver, buttonClose);
	}

	public WebElement getRoamingandIDDService() {
		return AppWait.waitForElementToBeClickable(driver, RoamingandIDDService);
	}

	public WebElement getCheckStatus() {
		return AppWait.waitForElementToBeClickable(driver, CheckStatus);
	}

	public WebElement getTermsandcondition() {
		return AppWait.waitForElementToBeClickable(driver, Termsandcondition);
	}

	public WebElement getActivateNow() {
		return AppWait.waitForElementToBeClickable(driver, ActivateNow);
	}


	public WebElement getAutodebit() {
		return AppWait.waitForElementToBeClickable(driver, Autodebit);
	}

	public WebElement getbuttonLetsGo() {
		return AppWait.waitForElementToBeClickable(driver, buttonLetsGo);
	}

	public WebElement getAutodebitcardnumber() {
		return AppWait.waitForElementToBeClickable(driver, Autodebitcardnumber);
	}

	public WebElement getAutodebitcardname() {
		return AppWait.waitForElementToBeClickable(driver, Autodebitcardname);
	}

	public WebElement getbuttonmonth() {
		return AppWait.waitForElementToBeClickable(driver, buttonmonth);
	}

	public WebElement getSelectmonth() {
		return AppWait.waitForElementToBeClickable(driver, Selectmonth);
	}

	public WebElement getbuttonyear() {
		return AppWait.waitForElementToBeClickable(driver, buttonyear);
	}

	public WebElement getSelectyear() {
		return AppWait.waitForElementToBeClickable(driver, Selectyear);
	}

	public WebElement getCVVnumber() {
		return AppWait.waitForElementToBeClickable(driver, CVVnumber);
	}

	public WebElement getConfirmcheckbox() {
		return AppWait.waitForElementToBeClickable(driver, Confirmcheckbox);
	}

	public WebElement getbuttonSubmit() {
		return AppWait.waitForElementToBeClickable(driver, buttonSubmit);
	}

	public WebElement getButtonPersonalDetails() {
		return AppWait.waitForElementToBeClickable(driver, ButtonPersonalDetails);
	}

	public WebElement getButtonEDITPersonalDetails() {
		return AppWait.waitForElementToBeClickable(driver, ButtonEDITPersonalDetails);
	}

	public WebElement gettextAddress1() {
		return AppWait.waitForElementToBeClickable(driver, textAddress1);
	}

	public WebElement gettextAddress2() {
		return AppWait.waitForElementToBeClickable(driver, textAddress2);
	}

	public WebElement gettextZipcode() {
		return AppWait.waitForElementToBeClickable(driver, textZipcode);
	}

	public WebElement gettextEmailAddress() {
		return AppWait.waitForElementToBeClickable(driver, textEmailAddress);
	}

	public WebElement gettextprehomenumber() {
		return AppWait.waitForElementToBeClickable(driver, textprehomenumber);
	}

	public WebElement gettexthomenumber() {
		return AppWait.waitForElementToBeClickable(driver, texthomenumber);
	}

	public WebElement gettextprefaxnumber() {
		return AppWait.waitForElementToBeClickable(driver, textprefaxnumber);
	}

	public WebElement gettextFaxnumber() {
		return AppWait.waitForElementToBeClickable(driver, textFaxnumber);
	}

	public WebElement getselectZipcode() {
		return AppWait.waitForElementToBeClickable(driver, selectZipcode);
	}

	public WebElement getSelectPrefixHomePhone() {
		return AppWait.waitForElementToBeClickable(driver, selectPrefixHomePhone);
	}

	public WebElement gettextpreoffnumber() {
		return AppWait.waitForElementToBeClickable(driver, textpreoffnumber);
	}

	public WebElement gettextagreement() {
		return AppWait.waitForElementToBeClickable(driver, textagreement);
	}

	public WebElement getSavepersonaldetail() {
		return AppWait.waitForElementToBeClickable(driver, Savepersonaldetail);
	}

	public WebElement gettextsucessmsg() {
		return AppWait.waitForElementToBeClickable(driver, textsucessmsg);
	}

	public WebElement gettextclosebtn() {
		return AppWait.waitForElementToBeClickable(driver, textclosebtn);
	}
}
