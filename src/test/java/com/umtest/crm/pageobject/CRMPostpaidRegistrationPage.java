package com.umtest.crm.pageobject;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

public class CRMPostpaidRegistrationPage extends MainUtil {

	private RemoteWebDriver driver;

	public CRMPostpaidRegistrationPage(RemoteWebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath = "//div[@class='add-cust-btn js-add-cust-btn']")
	private WebElement buttonaddcustomer;

	@FindBy(xpath = "//div[@class='personal-customer customer-icon-con']")
	private WebElement buttonPersonalcustomer;

	@FindBy(xpath = "//*[@class='input-group col-md-8 col-sm-8']//input[@class='form-control']")
	private WebElement IDtype;

	@FindBy(xpath = "//*[@name='custName']")
	private WebElement Customername;

	@FindBy(xpath = "//*[@name='certNbr']")
	private WebElement idnumber;

	@FindBy(xpath = "//*[@class='col-md-6 js-race']//input[@class='form-control']")
	private WebElement race;

	@FindBy(xpath = "//span[@class='glyphicon glyphicon-new-window']")
	private WebElement customeraddressbtn;

	@FindBy(xpath = "//*[@name='PostSplitcode']")
	private WebElement postcode;

	@FindBy(xpath = "//*[@name='addSplitress']")
	private WebElement address;

	@FindBy(xpath = "//button[@class='btn btn-primary btn-min-width js-ok']")
	private WebElement buttonOK;

	@FindBy(xpath = "//*[@class='iconfont icon-upload-alt']")
	private WebElement Uploaddoc;

	@FindBy(xpath = "//div[@class='modal-body common-entry-pop-body']")
	private WebElement personalcustomerpopup;

	@FindBy(xpath = "//label[@title='Customer Preferred Language']/following-sibling::div/div[@class='input-group ']/input")
	private WebElement customerPreferredLanguage;

	@FindBy(xpath = "//div[@class='add-cust-btn js-add-cust-btn']/img")
	private WebElement addCustomerButton;

	@FindBy(xpath = "//div[@class='personal-customer customer-icon-con']")
	private WebElement personalCustomerIcon;


	@FindBy(xpath = "//div[contains(text(),'Read Card')]/parent::div/following-sibling::div[@class='panel-body']//input[@placeholder='---Please Select---']")
	private WebElement personalCustomerIdType;

	@FindBy(xpath = "//input[@name='custName']")
	private WebElement customerName;

	@FindBy(xpath = "//label[@title='Race']/following-sibling::div/div[@class='input-group ']/input")
	private WebElement raceTextField;


	@FindBy(xpath = "//form[@class='form-horizontal js-cust-form']//input[@name='address']/parent::div//span[@class='glyphicon glyphicon-new-window']")
	private WebElement customerAddressEditIcon;

	@FindBy(xpath = "//input[@name='PostSplitcode']")
	private WebElement postCode;

	@FindBy(xpath = "//label[@title='Postcode']")
	private WebElement postCodeTitle;

	@FindBy(xpath = "//input[@name='addSplitress']")
	private WebElement addressTextField;

	@FindBy(xpath = "//div[@class='modal-footer']//button[contains(text(),'OK')]")
	private WebElement enterAddressOkButton;

	@FindBy(xpath = "//input[@type='file' and @name='file']")
	private WebElement customerIdCopyUploadIcon;

	@FindBy(xpath = "//input[@class='form-control cert-nbr-flag']")
	private WebElement customerIdNumber;

	@FindBy(xpath = "//input[@name='mobileAreaCode']")
	private WebElement mobileAreaCode;

	@FindBy(xpath = "//button[contains(text(),'OK')]")
	private WebElement personalCustomerOkButton;

	@FindBy(xpath = "//button[@class='btn btn-warning btn-min-width']")
	private WebElement warningOkButton;

	@FindBy(xpath = "//div[contains(text(),'is successfully created.')]")
	private WebElement customerCreationSuccessMsg;

	@FindBy(xpath = "//h4[text()='Success']/ancestor::div[@class='modal-header ui-draggable-handle']/following-sibling::div//button[contains(text(),'OK')]")
	private WebElement successMsgOkBtn;

	@FindBy(xpath = "//div[contains(text(),'The data is incomplete, Please check and input again.')]")
	private WebElement warningMessage;

	@FindBy(xpath = "//input[@name='mobilePhone']")
	private WebElement customerPhoneNumber;

	@FindBy(xpath = "//span[@class='js-view-360-text']")
	private WebElement threeSixtydegreeViewHyperLink;

	@FindBy(xpath = "//div[@class='order-entry-btn-div']")
	private WebElement Btnorderentry;

	@FindBy(xpath = "//span[@class='btn-col js-go-shopping']")
	private WebElement goshopping;

	@FindBy(xpath = "//input[contains(@placeholder,'Offer Name')]")
	private WebElement Searchplanname;

	@FindBy(xpath = "//button[contains(text(),'Order')]")
	private WebElement orderbutton;

	@FindBy(xpath = "//span[@class='glyphicon glyphicon-new-window']")
	private WebElement Accountpage;

	@FindBy(xpath = "//a[@class='js-account-add']")
	private WebElement AddAcount;

	@FindBy(xpath = "//*[contains(@name,'company')]")
	private WebElement companyname;

	@FindBy(xpath = "//div[@class='form-group required js-email']//following-sibling::div//following-sibling::input")
	private WebElement emailid;

	@FindBy(xpath = "//div[@class='btn-toolbar pull-right']//following-sibling::button[@class='btn btn-primary btn-min-width js-ok']")
	private WebElement OKbutton;

	@FindBy(xpath = "//button[@class='btn btn-success btn-min-width']")
	private WebElement sucessOKbutton;

	@FindBy(xpath = "//div[@class='col-xs-12 col-sm-8 col-md-4 js-port-in-number-parent']//following-sibling::span//*[@class='iconfont icon-option-horizontal']")
	private WebElement Servicenumberbtn;

	@FindBy(xpath = "//input[contains(@placeholder,'Please enter keyword query')]")
	private WebElement enterServicenumber;

	@FindBy(xpath = "//button[contains(text(),'Query')]")
	private WebElement Querybutton;

	@FindBy(xpath = "//button[@id='select-sim-ok-intro']")
	private WebElement Queryokbutton;

	@FindBy(xpath = "//*[@title='SIM Serial Number']//following-sibling::div//following-sibling::input")
	private WebElement Simserialnumber;

	@FindBy(xpath = "//button[@id='next-btn-intro']")
	private WebElement Nextbutton;

	@FindBy(xpath = "//div[@class='icheckbox']")
	private WebElement customercheckbox;

	@FindBy(xpath = "//button[@id='next-btn-intro']")
	private WebElement customernextbtn;

	@FindBy(xpath = "//input[@class='js-check-bypass']")
	private WebElement bypasscheckbox;

	@FindBy(xpath = "//button[@id='next-btn-intro']")
	private WebElement acknextbutton;

	@FindBy(xpath = "//button[@id='pay-btn-intro']")
	private WebElement paybutton;

	@FindBy(xpath = "//button[contains(text(),'Cash')]")
	private WebElement Cashbutton;

	@FindBy(xpath = "//label[contains(text(),'Received Amount')]//following-sibling::div//following-sibling::input")
	private WebElement amountreceived;

	@FindBy(xpath = "//button[@class='btn btn-primary btn-min-width js-payment']")
	private WebElement paymentbutton;

	@FindBy(xpath = "//div[@class='modal-footer detail-modal-footer']//following-sibling::button[@class='btn btn-primary btn-min-width js-ok']")
	private WebElement OKbuttonaccountinfo;

	@FindBy(xpath = "//button[@class='btn btn-primary js-ok']")
	private WebElement OKbuttonvalidation;

	@FindBy(xpath = "//button[@id='next-btn-intro']")
	private WebElement confirmNextbutton;

	@FindBy(xpath = "//h4[contains(text(),'Submit Successfully！')]")
	private WebElement labelorderSuccessMessage;

	@FindBy(xpath = "//button[@id='back-oe-intro']")
	private WebElement Confirmbutton;

	@FindBy(xpath = "//input[@placeholder='Customer Name / ID Number / Service Number / Old BRN']")
	private WebElement fuzzySearchTextField;

	@FindBy(xpath = "//img[@class='icon-big-font']")
	private WebElement fuzzySearchMagnifyingGlass;

	@FindBy(xpath = "//button[text()='OK']")
	private WebElement selectCustomerPopUpOkBtn;

	@FindBy(xpath = "//li[@class='tabs-bundle-intro ui-state-default']")
	public WebElement fspaddbutton;

	@FindBy(xpath = "//span[contains(text(),'Share')]//parent::td//parent::tr//button[@class='btn btn-default js-plus']")
	public WebElement fspmemberbutton;

	@FindBy(xpath = "//span[contains(text(),'Family')]//parent::td//parent::tr//button[@class='btn btn-default js-plus']")
	public WebElement fspfamilybutton;

	@FindBy(xpath = "//span[contains(text(),'Postpaid')]//parent::td//parent::tr//button[@class='btn btn-default js-plus']")
	public WebElement fspPrinciplebutton;

	@FindBy(xpath = "//button[@id='bundle-member-intro']")
	public WebElement fspOKbutton;

	//@FindBy(xpath = "//*[@class='icon iconfont icon-gene-help js-Account-Manager-Name-Tip']//parent::label//parent::div//following-sibling::input//following-sibling::span[@class='form-control-feedback form-clear-input']")
	@FindBy(xpath = "//*[@class='icon iconfont icon-gene-help js-Account-Manager-Name-Tip']//parent::label//parent::div//following-sibling::input")
	public WebElement accountmanagernameremove;

	public WebElement getConfirmbutton() {
		return AppWait.waitForElementToBeClickable(driver, Confirmbutton);
	}

	public WebElement getbuttonaddcustomer() {
		return AppWait.waitForElementToBeClickable(driver, buttonaddcustomer);
	}

	public WebElement getbuttonPersonalcustomer() {
		return AppWait.waitForElementToBeClickable(driver, buttonPersonalcustomer);
	}

	public WebElement getIDtype() {
		return AppWait.waitForElementToBeClickable(driver, IDtype);
	}

	public WebElement getCustomername() {
		return AppWait.waitForElementToBeClickable(driver, Customername);
	}

	public WebElement getidnumber() {
		return AppWait.waitForElementToBeClickable(driver, idnumber);
	}

	public WebElement getrace() {
		return AppWait.waitForElementToBeClickable(driver, race);
	}

	public WebElement getCustomerLanguageTestField() {
		return AppWait.waitForElementToBeClickable(driver, customerPreferredLanguage);
	}

	public WebElement getcustomeraddressbtn() {
		return AppWait.waitForElementToBeClickable(driver, customeraddressbtn);
	}

	public WebElement getpostcode() {
		return AppWait.waitForElementToBeClickable(driver, postcode);
	}

	public WebElement getaddress() {
		return AppWait.waitForElementToBeClickable(driver, address);
	}

	public WebElement getbuttonOK() {
		return AppWait.waitForElementToBeClickable(driver, buttonOK);
	}

	public WebElement getUploaddoc() {
		return AppWait.waitForElementToBeClickable(driver, Uploaddoc);
	}

	public WebElement getpersonalcustomerpopup() {
		return AppWait.waitForElementToBeClickable(driver, personalcustomerpopup);
	}

	public WebElement getAddCustomerButton() {
		return AppWait.waitForElementToBeClickable(driver, addCustomerButton);
	}


	public WebElement getPersonalCustomerIcon() {
		return AppWait.waitForElementToBeClickable(driver, personalCustomerIcon);
	}

	public WebElement getPersonalCustomerIdType() {
		return AppWait.waitForElementToBeClickable(driver, personalCustomerIdType);
	}

	public WebElement getCustomerNameTextField() {
		return AppWait.waitForElementToBeClickable(driver, customerName);
	}

	public WebElement getRaceTextField() {
		return AppWait.waitForElementToBeClickable(driver, raceTextField);
	}

	public WebElement getCustomerAddressEditIcon() {
		return AppWait.waitForElementToBeClickable(driver, customerAddressEditIcon);
	}

	public WebElement getPostCodeTextField() {
		return AppWait.waitForElementToBeClickable(driver, postCode);
	}

	public WebElement getPostCodeTitle() {
		return AppWait.waitForElementToBeClickable(driver, postCodeTitle);
	}

	public WebElement getAddressTextField() {
		return AppWait.waitForElementToBeClickable(driver, addressTextField);
	}

	public WebElement getEnterAddressOkButton() {
		return AppWait.waitForElementToBeClickable(driver, enterAddressOkButton);
	}

	public WebElement getCustomerIdCopyUploadIcon() {
		return AppWait.waitForElementToBeClickable(driver, customerIdCopyUploadIcon);
	}

	public WebElement getCustomerIdNumberTextField() {
		return AppWait.waitForElementToBeClickable(driver, customerIdNumber);
	}

	public WebElement getMobileAreaCode() {
		return AppWait.waitForElementToBeClickable(driver, mobileAreaCode);
	}

	public WebElement getPersonalCustomerOkButton() {
		return AppWait.waitForElementToBeClickable(driver, personalCustomerOkButton);
	}

	public WebElement getWarningMessage() {
		return AppWait.waitForElementToBeClickable(driver, warningMessage);
	}

	public WebElement getWarningOkButton() {
		return AppWait.waitForElementToBeClickable(driver, warningOkButton);
	}

	public WebElement getCustomerCreationSuccessMsg() {
		return AppWait.waitForElementToBeClickable(driver, customerCreationSuccessMsg);
	}

	public WebElement getThreeSixtydegreeViewHyperLink() {
		return AppWait.waitForElementToBeClickable(driver, threeSixtydegreeViewHyperLink);
	}

	public WebElement getBtnorderentry() {
		return AppWait.waitForElementToBeClickable(driver, Btnorderentry);
	}

	public WebElement getgoshopping() {
		return AppWait.waitForElementToBeClickable(driver, goshopping);
	}

	public WebElement getSearchplanname() {
		return AppWait.waitForElementToBeClickable(driver, Searchplanname);
	}

	public WebElement getorderbutton() {
		return AppWait.waitForElementToBeClickable(driver, orderbutton);
	}

	public WebElement getAccountpage() {
		return AppWait.waitForElementToBeClickable(driver, Accountpage);
	}

	public WebElement getAddAcount() {
		return AppWait.waitForElementToBeClickable(driver, AddAcount);
	}

	public WebElement getcompanyname() {
		return AppWait.waitForElementToBeClickable(driver, companyname);
	}

	public WebElement getemailid() {
		return AppWait.waitForElementToBeClickable(driver, emailid);
	}

	public WebElement getOKbutton() {
		return AppWait.waitForElementToBeClickable(driver, OKbutton);
	}

	public WebElement getServicenumberbtn() {
		return AppWait.waitForElementToBeClickable(driver, Servicenumberbtn);
	}

	public WebElement getenterServicenumber() {
		return AppWait.waitForElementToBeClickable(driver, enterServicenumber);
	}

	public WebElement getQuerybutton() {
		return AppWait.waitForElementToBeClickable(driver, Querybutton);
	}

	public WebElement getQueryokbutton() {
		return AppWait.waitForElementToBeClickable(driver, Queryokbutton);
	}

	public WebElement getSimserialnumber() {
		return AppWait.waitForElementToBeClickable(driver, Simserialnumber);
	}

	public WebElement getNextbutton() {
		return AppWait.waitForElementToBeClickable(driver, Nextbutton);
	}

	public WebElement getcustomercheckbox() {
		return AppWait.waitForElementToBeClickable(driver, customercheckbox);
	}

	public WebElement getcustomernextbtn() {
		return AppWait.waitForElementToBeClickable(driver, customernextbtn);
	}

	public WebElement getbypasscheckbox() {
		return AppWait.waitForElementToBeClickable(driver, bypasscheckbox);
	}

	public WebElement getacknextbutton() {
		return AppWait.waitForElementToBeClickable(driver, acknextbutton);
	}

	public WebElement getpaybutton() {
		return AppWait.waitForElementToBeClickable(driver, paybutton);
	}

	public WebElement getCashbutton() {
		return AppWait.waitForElementToBeClickable(driver, Cashbutton);
	}

	public WebElement getamountreceived() {
		return AppWait.waitForElementToBeClickable(driver, amountreceived);
	}

	public WebElement getpaymentbutton() {
		return AppWait.waitForElementToBeClickable(driver, paymentbutton);
	}

	public WebElement getconfirmNextbutton() {
		return AppWait.waitForElementToBeClickable(driver, confirmNextbutton);
	}

	public WebElement getlabelorderSuccessMessage() {
		return AppWait.waitForElementToBeClickable(driver, labelorderSuccessMessage);
	}

	public WebElement getFuzzySearchTextField() {
		return AppWait.waitForElementToBeClickable(driver, fuzzySearchTextField);
	}

	public WebElement getFuzzySearchMagnifyingGlass() {
		return AppWait.waitForElementToBeClickable(driver, fuzzySearchMagnifyingGlass);
	}

	public WebElement getSelectCustomerPopupOkBtn() {
		return AppWait.waitForElementToBeClickable(driver, selectCustomerPopUpOkBtn);
	}

	public WebElement getfspaddbutton() {
		return AppWait.waitForElementForVisibility(driver, fspaddbutton);
	}

	public WebElement getfspPrinciplebutton() {
		return AppWait.waitForElementForVisibility(driver, fspPrinciplebutton);
	}

	public WebElement getfspmemberbutton() {
		return AppWait.waitForElementForVisibility(driver, fspmemberbutton);
	}

	public WebElement getfspfamilybutton() {
		return AppWait.waitForElementForVisibility(driver, fspfamilybutton);
	}


	public WebElement getfspOKbutton() {
		return AppWait.waitForElementForVisibility(driver, fspOKbutton);
	}

	public WebElement getFSPAccountpage(String accounttype) {
		return AppWait.waitForElementToBeClickable(driver, "//div[@id='"+accounttype+"']//following-sibling::label[@title='Account']//following-sibling::div//following-sibling::span//*[@class='glyphicon glyphicon-new-window']");
	}

	public WebElement getFSPservicenumber(String accounttype) {
		return AppWait.waitForElementToBeClickable(driver, "//div[@id='"+accounttype+"']//following-sibling::label[@title='Service Number']//following-sibling::div//following-sibling::span//*[@class='iconfont icon-option-horizontal']");
	}

	public WebElement getFSPICCIDnumber(String accounttype) {
		return AppWait.waitForElementToBeClickable(driver, "//div[@id='"+accounttype+"']//following-sibling::label[@title='ICCID']//following-sibling::div//following-sibling::input");
	}

	public WebElement getFSPICCIDtxt(String accounttype) {
		return AppWait.waitForElementToBeClickable(driver, "//div[@id='"+accounttype+"']//following-sibling::label[@title='ICCID']");
	}

	public WebElement getaccountmanagernameremove() {
		return AppWait.waitForElementForVisibility(driver, accountmanagernameremove);
	}

	public WebElement getorderbuttonFSP(String FSPplanname) {
		return AppWait.waitForElementToBeClickable(driver, "//div[@title='"+FSPplanname+"']//parent::div//following-sibling::button");
	}

	public WebElement getOKbuttonaccountinfo() {
		return AppWait.waitForElementToBeClickable(driver, OKbuttonaccountinfo);
	}

	public WebElement getOKbuttonvalidation() {
		return AppWait.waitForElementToBeClickable(driver, OKbuttonvalidation);
	}

	public WebElement getsucessOKbutton() {
		return AppWait.waitForElementToBeClickable(driver, sucessOKbutton);
	}

	public WebElement getSuccessMsgOkBtn() {
		return AppWait.waitForElementToBeClickable(driver, successMsgOkBtn);
	}

	public WebElement getCustomerPhoneNumber() {
		return AppWait.waitForElementToBeClickable(driver, customerPhoneNumber);
	}
}
