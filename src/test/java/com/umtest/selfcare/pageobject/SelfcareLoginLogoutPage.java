package com.umtest.selfcare.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;

import io.appium.java_client.android.AndroidElement;

public class SelfcareLoginLogoutPage extends MainUtil {

	private RemoteWebDriver driver;

	public SelfcareLoginLogoutPage(RemoteWebDriver driver) {
		this.driver = driver;
	}


	@FindBy(xpath = "//a[contains(text(),'Click here to retrieve your PIN')]")
	private WebElement buttonRetrievePIN;

	@FindBy(id = "msisdn")
	private WebElement textboxMSISDN;

	@FindBy(xpath = "//*[@id='forgotPass']/div[3]/div/button")
	//@FindBy(xpath = "//input[@type='submit' and @value='SUBMIT']")
	private WebElement buttonSubmit;

	//@FindBy(xpath = "//input[@value='Close']")
	@FindBy(xpath = "//*[@id=\"messages\"]/div/div/svg")
	private WebElement buttonClose;

	@FindBy(xpath = "//a[contains(@class,'selfcare-nav-link')]")
	private WebElement buttonSelfcareLogin;

	@FindBy(id = "password")
	private WebElement textboxPIN;

	//@FindBy(id = "btn_login_submit")
	@FindBy(xpath = "//*[@id=\"login_form\"]/div[4]/div/button")
	private WebElement buttonLogin;
	//@FindBy(xpath = "//tbody/tr[5]/td[2]/input[1]")

	@FindBy(xpath = "//button/span[contains(text(),'Login')]")
	private WebElement selfCareLoginButton;


	/*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	//Addded by Chandra Kalagotla
	@FindBy(xpath = "(//div[contains(text(),'Credit Balance') and @class='balance_title']/following::div[@class='value'])[1]")
	private WebElement textCreditBalance;

	@FindBy(xpath = "(//div[contains(text(),'CreditShare Balance') and @class='balance_title']/following::div[@class='value'])[1]")
	private WebElement textCreditShareBalance;

	@FindBy(xpath = "(//div[contains(text(),'Credit Balance')]/following::div[@class='due'])[1]")
	private WebElement textExpiryDate;

	@FindBy(xpath = "(//td[contains(text(),'Current Rate Plan')]/following::td[1])[1]")
	private WebElement textRatePlanName;
	/*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/

	//@FindBy(xpath = "//div[@class='menu my_account expand']")
	@FindBy(xpath = "//span[contains(text(),'Manage Account')]")
	private WebElement buttonManageAccount;

	@FindBy(xpath = "//a[contains(text(),'Account Details')]")
	private WebElement buttonAccountDetails;

	@FindBy(xpath = "//p[contains(text(),'Expiry Date:')]")
	private WebElement labelExpiryDate;

	@FindBy(xpath = "//th[contains(text(),'Current Rate Plan')]/following-sibling::td")
	private WebElement labelPlanName;



	//@FindBy(xpath="//a[text()='Logout']")
	@FindBy(xpath="//span[contains(text(),'Logout')]")
	private WebElement buttonLogout;


	@FindBy(xpath="//p[contains(text(),'Mobile Number')]//following::h4")
	//@FindBy(xpath="//*[@id=\"page-account-details\"]/section[1]/div/div[1]/div/h4")
	private WebElement MobileNumber;

	@FindBy(xpath="(//span[@class='value'])[1]")
	private WebElement BSCSAccountNumber;

	@FindBy(xpath="(//span[@class='value'])[2]")
	private WebElement DateRegister;

	//@FindBy(xpath="//span[@class='value Active']")
	@FindBy(xpath="//span[contains(text(),'Active')]")
	private WebElement Status;

/*	@FindBy(xpath="//div[@class='table_plan selfcare_table']/table/tbody/tr[2]/td[2]")
	private WebElement CurrentRatePlan;*/

	//@FindBy(xpath="(//th[contains(text(),'Current Rate Plan')]/following-sibling::td")
	@FindBy(xpath="(//*[@id=\"page-account-details\"]/section[2]/div/table/tbody/tr[2]/td")
	private WebElement CurrentRatePlan;

	@FindBy(xpath="(//th[contains(text(),'Credit Limit')]/following-sibling::td")
	//@FindBy(xpath="//*[@id=\"page-account-details\"]/section[2]/div/table/tbody/tr[4]/td")
	private WebElement CreditLimit;

	@FindBy(xpath="//div[contains(text(),'balance')]//following::div[1]")
	private WebElement CurrentBalance;



	//@FindBy(xpath = "//div[@class='menu my_bill expand']//a[contains(text(),'Payment History')]")
	@FindBy(xpath = "//a[contains(text(),'Payment History')]")
	private WebElement PaymentHistory;

	@FindBy(xpath = "//a[contains(text(),'Change Rate Plan')]")
	private WebElement ChangeRatePlan;

	@FindBy(xpath = "//span[contains(text(),'Change Your Plan Now')]")
	private WebElement Changeyourplannowbutton;

	@FindBy(xpath = "//span[contains(text(),'Change Plan')]")
	private WebElement Proceedbutton;

	@FindBy(xpath = "//div[@class='crp-confirm-inline-inputs']")
	private WebElement CRPConfirmCheckbox;

	@FindBy(xpath = "//button[@name='confirmBtn']")
	private WebElement CRPConfirmbutton;

	@FindBy(xpath = "//span[contains(text(),'Bills & Payment')]")
	private WebElement Billandpayment;

	@FindBy(xpath = "//strong[contains(text(),'MYR')]")
	private WebElement labelAccountBalance;

	@FindBy(xpath = "//a[contains(text(),'Update now')]")
	private WebElement buttonUpdatenow;

	@FindBy(xpath = "//select[@id='IDType']")
	private WebElement IDtypecombobox;

	@FindBy(xpath = "//option[contains(text(),'NRIC No (New)')]")
	private WebElement SelectIDtypeNRIC;

	@FindBy(xpath = "//option[contains(text(),'Passport No.')]")
	private WebElement SelectIDtypePassport;

	@FindBy(xpath = "//input[@id='IDNo']")
	private WebElement TypeIDNo;

	@FindBy(xpath = "//input[@id='FName']")
	private WebElement custname;

	@FindBy(xpath = "//select[@id='Race']")
	private WebElement Race;

	@FindBy(xpath = "//tbody/tr[2]/td[2]/select[1]/option[2]")
	private WebElement SelectRace;

	@FindBy(xpath = "//input[@id='EmailAddress']")
	private WebElement EmailAddress;

	@FindBy(xpath = "//input[@id='PreOffPhoneNo']")
	private WebElement PreOffPhoneNo;

	@FindBy(xpath = "//input[@id='OffPhoneNo']")
	private WebElement OffPhoneNo;

	@FindBy(xpath = "//input[@id='agreeterm']")
	private WebElement agreeterm;

	@FindBy(xpath = "//input[@id='save_contact']")
	private WebElement save_contact;

	@FindBy(xpath = "//a[contains(text(),'Personal Details')]")
	private WebElement ButtonPersonalDetails;

//	@FindBy(xpath = "//a[contains(text(),'EDIT PERSONAL DETAILS')]")
	@FindBy(xpath = "//span[contains(text(),'Edit Personal Details')]")
	private WebElement ButtonEDITPersonalDetails;

	@FindBy(xpath = "//input[@id='AddressStreet']")
	private WebElement textAddress1;

	@FindBy(xpath = "//input[@id='AddressNote1']")
	private WebElement textAddress2;

	@FindBy(xpath = "//input[@id='Zipcode']")
	private WebElement textZipcode;

	@FindBy(xpath = "//input[@id='EmailAddress']")
	private WebElement textEmailAddress;

	@FindBy(css = "#PrePixelHomePhoneNo")
//	@FindBy(xpath = "//input[@id='PrePixelAltPhoneNo']")
	private WebElement textprehomenumber;

	@FindBy(xpath="//*[@id='page-personal-details']/div[2]/table/tbody/tr[11]/td[2]")
	private WebElement homephonenumber;

	@FindBy(css = "#PixelHomePhoneNo")
//	@FindBy(xpath = "//input[@id='PixelAltPhoneNo']")
	private WebElement texthomenumber;

	@FindBy(xpath = "//input[@id='PreFaxNo']")
	private WebElement textprefaxnumber;

	@FindBy(xpath = "//input[@id='FaxNo']")
	private WebElement textFaxnumber;

	@FindBy(xpath = "//a[contains(text(),'Roaming and IDD Services Activation')]")
	private WebElement buttonRoamingandIDDService;

	@FindBy(xpath = "//a[contains(text(),'Check Status')]")
	private WebElement buttonCheckStatus;

	@FindBy(xpath = "//p[contains(text(),'Your roaming and IDD services have already been ac')]")
	private WebElement Confirmstatusmessage;

	@FindBy(xpath = "//h1[contains(text(),'Success')]")
	private WebElement CRPSefcaresucessmsg;

	@FindBy(xpath = "//h1[contains(text(),'Sorry!')]")
	private WebElement CRPSefcareSorrymsg;

	@FindBy(xpath = "//a[contains(text(),'Close')]")
	private WebElement CRPSefcareClosebutton;

	@FindBy(xpath = "//input[@id='confirmdata']")
	private WebElement profileconfirmcheckbox;

	@FindBy(xpath = "//tbody/tr[1]/td[1]/input[1]")
	private WebElement profileacknowledgebutton;

	@FindBy(xpath = "//input[@name='Gender'][@value='M']")
	private WebElement GenderMalebutton;

	@FindBy(xpath = "//input[@name='Gender'][@value='F']")
	private WebElement GenderFeMalebutton;

	@FindBy(xpath = "//select[@id='Nationality']")
	private WebElement Nationalitybutton;

	@FindBy(xpath = "//option[contains(text(),'Albania')]")
	private WebElement SelectNationality;

	@FindBy(xpath = "//div[@class='menu my_account expand' and contains(text(),'Manage Account')]")
	private WebElement menuButtonManageAccount;

	@FindBy(xpath = "//div[@class='menu my_bill expand' and contains(text(),'Bills & Payment')]")
	private WebElement menuButtonBillsPayment;

	@FindBy(xpath = "//a[@class='menu my_contents' and contains(text(),'My Contents')]")
	private WebElement menuButtonMyContents;

	@FindBy(xpath = "//div[@class='menu autodebit expand' and contains(text(),'Jaringan PRIHATIN Programme')]")
	private WebElement menuButtonPrihatin;

	@FindBy(xpath = "//a[@class='menu logout' and contains(text(),'Digital Goods & Services')]")
	private WebElement menuButtonDigitalGoodsServices;

	@FindBy(xpath = "//div[@class='menu golife expand' and contains(text(),'GOLIFE')]")
	private WebElement menuButtonGolife;

	@FindBy(xpath = "//*[@id='otp']")
	private WebElement OPTPIN;

	@FindBy(xpath = "//*[@id='new_pin']")
	private WebElement NewPin;

	@FindBy(xpath = "//*[@id='new_pin2']")
	private WebElement ConfirmNewPin;

	//@FindBy(xpath = "//*[@value='SUBMIT']")
	@FindBy(xpath = "//*[@id=\"enterOTP\"]/div[2]/div/button")
	private WebElement OPTSUBMIT;

	@FindBy(xpath = "//*[@id=\"newPIN\"]/div[3]/div/button/span")
	//@FindBy(xpath = "//*[@id=\"successfullySetUpPIN\"]/div/div/svg")
	private WebElement Successfulsubmit;

	@FindBy(xpath = "//button[@type='button']/span[contains(text(),'Login')]")
	private WebElement clickLoginbutton;

	public WebElement getclickLoginbutton() {
		return AppWait.waitForElementToBeClickable(driver, clickLoginbutton);
	}

	public WebElement getHomephonenumber()
	{
		return AppWait.waitForElementToBeClickable(driver, homephonenumber);
	}

	public WebElement getSuccessfulsubmit() {
		return AppWait.waitForElementToBeClickable(driver, Successfulsubmit);
	}

	public WebElement getNewPin() {
		return AppWait.waitForElementToBeClickable(driver, NewPin);
	}
	public WebElement getConfirmNewPin() {
		return AppWait.waitForElementToBeClickable(driver, ConfirmNewPin);
	}

	public WebElement getOPTPIN() {
		return AppWait.waitForElementToBeClickable(driver, OPTPIN);
	}

	public WebElement getOPTSUBMIT() {
		return AppWait.waitForElementToBeClickable(driver, OPTSUBMIT);
	}

	public WebElement getmenuButtonManageAccount() {
		return AppWait.waitForElementToBeClickable(driver, menuButtonManageAccount);
	}

	public WebElement getMenuButtonBillsPayment() {
		return AppWait.waitForElementToBeClickable(driver, menuButtonBillsPayment);
	}

	public WebElement getmenuButtonMyContents() {
		return AppWait.waitForElementToBeClickable(driver, menuButtonMyContents);
	}

	public WebElement getmenuButtonPrihatin() {
		return AppWait.waitForElementToBeClickable(driver, menuButtonPrihatin);
	}

	public WebElement getmenuButtonGoLife() {
		return AppWait.waitForElementToBeClickable(driver, menuButtonGolife);
	}

	public WebElement getmenuButtonDigitalGoodsServices() {
		return AppWait.waitForElementToBeClickable(driver, menuButtonDigitalGoodsServices);
	}


	public WebElement getCRPSefcaresucessmsg() {
		return AppWait.waitForElementToBeClickable(driver, CRPSefcaresucessmsg);
	}

	public WebElement getCRPSefcareSorrymsg() {
		return AppWait.waitForElementToBeClickable(driver, CRPSefcareSorrymsg);
	}

	public WebElement getCRPSefcareClosebutton() {
		return AppWait.waitForElementToBeClickable(driver, CRPSefcareClosebutton);
	}

	public WebElement getButtonRetrievePIN() {
		return AppWait.waitForElementToBeClickable(driver, buttonRetrievePIN);
	}

	public WebElement getTextboxMSISDN() {
		return AppWait.waitForElementToBeClickable(driver, textboxMSISDN);
	}

	public WebElement getButtonSubmit() {
		return AppWait.waitForElementToBeClickable(driver, buttonSubmit);
	}

	public WebElement getButtonClose() {
		return AppWait.waitForElementToBeClickable(driver, buttonClose);
	}

	public WebElement getButtonSelfcareLogin() {
		return AppWait.waitForElementToBeClickable(driver, buttonSelfcareLogin);
	}

	public WebElement getTextboxPassword() {
		return AppWait.waitForElementToBeClickable(driver, textboxPIN);
	}

	public WebElement getButtonLogin() {
		return AppWait.waitForElementToBeClickable(driver, buttonLogin);
	}

	public WebElement getButtonManageAccount() {
		return AppWait.waitForElementToBeClickable(driver, buttonManageAccount);
	}

	public WebElement getCreditShareBalance() {
		return AppWait.waitForElementToBeClickable(driver, textCreditShareBalance);
	}

	public String captureCreditShareBalance() {
		return AppWait.waitForElementToBeClickable(driver, textCreditShareBalance).getText().trim();
	}

	public String captureCreditBalance() {
		return AppWait.waitForElementToBeClickable(driver, textCreditBalance).getText().trim();
	}

	public String captureCreditBalanceExpiryDate() {
		return AppWait.waitForElementToBeClickable(driver, textExpiryDate).getText().trim();
	}

	public String captureRatePlanName() {
		return AppWait.waitForElementToBeClickable(driver, textRatePlanName).getText().trim();
	}

	public WebElement getButtonAccountDetails() {
		return AppWait.waitForElementToBeClickable(driver, buttonAccountDetails);
	}

	public WebElement getLabelExpiryDate() {
		return AppWait.waitForElementToBeClickable(driver, labelExpiryDate);
	}

	public WebElement getLabelPlanName() {
		return AppWait.waitForElementToBeClickable(driver, labelPlanName);
	}

	public WebElement getButtonLogout() {
		return AppWait.waitForElementToBeClickable(driver, buttonLogout);
	}

	public WebElement getMobileNumber() {
		return AppWait.waitForElementToBeClickable(driver, MobileNumber);
	}

	public WebElement getBSCSAccountNumber() {
		return AppWait.waitForElementToBeClickable(driver, BSCSAccountNumber);
	}

	public WebElement getDateRegister() {
		return AppWait.waitForElementToBeClickable(driver, DateRegister);
	}

	public WebElement getStatus() {
		return AppWait.waitForElementToBeClickable(driver, Status);
	}

	public WebElement getCurrentRatePlan() {
		return AppWait.waitForElementToBeClickable(driver, CurrentRatePlan);
	}

	public WebElement getCreditLimit() {
		return AppWait.waitForElementToBeClickable(driver, CreditLimit);
	}

	public WebElement getCurrentBalance() {
		return AppWait.waitForElementToBeClickable(driver, CurrentBalance);
	}

	public WebElement getPaymentHistory() {
		return AppWait.waitForElementToBeClickable(driver, PaymentHistory);
	}

	public WebElement getChangeRatePlan() {
		return AppWait.waitForElementToBeClickable(driver, ChangeRatePlan);
	}

	public WebElement getChangeyourplannowbutton() {
		return AppWait.waitForElementToBeClickable(driver, Changeyourplannowbutton);
	}

	public WebElement getProceedbutton() {
		return AppWait.waitForElementToBeClickable(driver, Proceedbutton);
	}

	public WebElement getCRPConfirmCheckbox() {
		return AppWait.waitForElementToBeClickable(driver, CRPConfirmCheckbox);
	}

	public WebElement getCRPConfirmbutton() {
		return AppWait.waitForElementToBeClickable(driver, CRPConfirmbutton);
	}

	public WebElement getBillandpayment() {
		return AppWait.waitForElementToBeClickable(driver, Billandpayment);
	}

	public WebElement getLabelAccountBalance() {
		return AppWait.waitForElementToBeClickable(driver, labelAccountBalance);
	}

	public WebElement getbuttonUpdatenow() {
		return AppWait.waitForElementToBeClickable(driver, buttonUpdatenow);
	}
	public WebElement getIDtypecombobox() {
		return AppWait.waitForElementToBeClickable(driver, IDtypecombobox);
	}
	public WebElement getSelectIDtypeNRIC() {
		return AppWait.waitForElementToBeClickable(driver, SelectIDtypeNRIC);
	}
	public WebElement getSelectIDtypePassport() {
		return AppWait.waitForElementToBeClickable(driver, SelectIDtypePassport);
	}
	public WebElement getTypeIDNo() {
		return AppWait.waitForElementToBeClickable(driver, TypeIDNo);
	}
	public WebElement getcustname() {
		return AppWait.waitForElementToBeClickable(driver, custname);
	}
	public WebElement getRace() {
		return AppWait.waitForElementToBeClickable(driver, Race);
	}
	public WebElement getSelectRace() {
		return AppWait.waitForElementToBeClickable(driver, SelectRace);
	}
	public WebElement getEmailAddress() {
		return AppWait.waitForElementToBeClickable(driver, EmailAddress);
	}
	public WebElement getPreOffPhoneNo() {
		return AppWait.waitForElementToBeClickable(driver, PreOffPhoneNo);
	}
	public WebElement getOffPhoneNo() {
		return AppWait.waitForElementToBeClickable(driver, OffPhoneNo);
	}
	public WebElement getagreeterm() {
		return AppWait.waitForElementToBeClickable(driver, agreeterm);
	}
	public WebElement getsave_contact() {
		return AppWait.waitForElementToBeClickable(driver, save_contact);
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

	public WebElement getbuttonRoamingandIDDService() {
		return AppWait.waitForElementToBeClickable(driver, buttonRoamingandIDDService);
	}

	public WebElement getbuttonCheckStatus() {
		return AppWait.waitForElementToBeClickable(driver, buttonCheckStatus);
	}

	public WebElement getConfirmstatusmessage() {
		return AppWait.waitForElementToBeClickable(driver, Confirmstatusmessage);
	}

	public WebElement getprofileconfirmcheckbox() {
		return AppWait.waitForElementToBeClickable(driver, profileconfirmcheckbox);
	}

	public WebElement getprofileacknowledgebutton() {
		return AppWait.waitForElementToBeClickable(driver, profileacknowledgebutton);
	}

	public WebElement getGenderMalebutton() {
		return AppWait.waitForElementToBeClickable(driver, GenderMalebutton);
	}

	public WebElement getGenderFeMalebutton() {
		return AppWait.waitForElementToBeClickable(driver, GenderFeMalebutton);
	}

	public WebElement getNationalitybutton() {
		return AppWait.waitForElementToBeClickable(driver, Nationalitybutton);
	}

	public WebElement getSelectNationality(String nationalityname) {
		return (WebElement) AppWait.waitForElementToBeClickable(driver, "//option[contains(text(),'"+nationalityname+"')]");
	}

	public WebElement getMobileNumberselfcare(String msisdnnum) {
		return AppWait.waitForElementToBeClickable(driver, "//h4[contains(text(),'"+msisdnnum+"')]");
	}

	public WebElement getCurrentRatePlanSelfcare(String rateplan) {
		return AppWait.waitForElementToBeClickable(driver, "//td[contains(text(),'"+rateplan+"')]");
	}

	public WebElement getCreditLimitselfcare(String creditlimit) {
		return AppWait.waitForElementToBeClickable(driver, "//td[contains(text(),'"+creditlimit+"')]");
	}

	public WebElement getSelfCareLoginButton() {
		return AppWait.waitForElementToBeClickable(driver, selfCareLoginButton);
	}

}
