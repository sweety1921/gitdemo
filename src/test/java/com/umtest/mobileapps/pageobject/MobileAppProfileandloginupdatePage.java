package com.umtest.mobileapps.pageobject;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MobileAppProfileandloginupdatePage extends MainUtil {
	
	private AndroidDriver<?> driver;

	public MobileAppProfileandloginupdatePage(AndroidDriver<?> driver) {
		this.driver = driver;
	}
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"newLogin_button_forgotPin\"]")
	private AndroidElement buttonforgotpin;
	
	@AndroidFindBy(xpath ="//android.widget.EditText[@content-desc=\"forgotPin_textField_mobile\"]")
	private AndroidElement forgotpinmsisdn;
	
	@AndroidFindBy(xpath ="//android.view.ViewGroup[@content-desc=\"forgotPin_button_navigateResetPin\"]/android.view.ViewGroup/android.widget.ImageView")
	private AndroidElement forgotpinsubmit;
	
	
	@AndroidFindBy(xpath ="//android.widget.TextView[@text='Yes']")
	private AndroidElement forgotpinConfirm;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"sidemenu_button_menu\"]/android.widget.ImageView")
	private AndroidElement MenuIcon;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Settings']")
	private AndroidElement MenuSettings;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"button_Change PIN\"]/android.widget.TextView")
	private AndroidElement buttonChangePin;
	
	@AndroidFindBy(xpath = "//android.widget.EditText[@content-desc=\"textField_Current PIN\"]")
	private AndroidElement CurrentPin;
	
	@AndroidFindBy(xpath = "//android.widget.EditText[@content-desc=\"textField_New PIN\"]")
	private AndroidElement NewPin;
	
	@AndroidFindBy(xpath = "//android.widget.EditText[@content-desc=\"textField_Confirm New PIN\"]")
	private AndroidElement ConfirmNewPin;
	
//	@AndroidFindBy(xpath = "//android.view.View[@content-desc=\"button_Update PIN\"]/android.widget.TextView")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Update PIN\"]")
	private AndroidElement UpdatePin;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='PIN changed successfully']")
	private AndroidElement Pinchangedmsg;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Logout']")
	private AndroidElement buttonlogout;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"button_EDIT\"]/android.widget.TextView")
	private AndroidElement buttonEDIT;
	
	@AndroidFindBy(xpath = "//android.widget.EditText[contains(@content-desc,'profile_InputText_EMAIL ID')]")
	private AndroidElement EMAILID;
	
	@AndroidFindBy(xpath = "//android.view.View[@content-desc=\"profile_Picker_GENDER\"]/android.view.ViewGroup/android.widget.EditText")
	private AndroidElement GENDER;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"parent_My Address\"]/android.widget.TextView")
	private AndroidElement MYADDRESS;
	
	@AndroidFindBy(xpath = "//android.widget.EditText[contains(@content-desc,'profile_InputText_ADDRESS LINE 1')]")
	private AndroidElement ADDRESSLINE1;
	
	@AndroidFindBy(xpath = "//android.widget.EditText[contains(@content-desc,'profile_InputText_ADDRESS LINE 2')]")
	private AndroidElement ADDRESSLINE2;
	
	@AndroidFindBy(xpath = "//android.widget.EditText[contains(@content-desc,'profile_InputText_POSTCODE')]")
	private AndroidElement POSTCODE;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"parent_Alternate Contact\"]/android.widget.TextView")
	private AndroidElement ALTERNATECONTACT;
	
	@AndroidFindBy(xpath = "//android.widget.EditText[contains(@content-desc,'profile_InputText_ALTERNATE CONTACT NUMBER')]")
	private AndroidElement ALTERNATECONTACTNUMBER;
	
	@AndroidFindBy(xpath = "//android.widget.EditText[contains(@content-desc,'profile_InputText_HOME CONTACT NUMBER')]")
	private AndroidElement HOMECONTACTNUMBER;
	
	@AndroidFindBy(xpath = "//android.widget.EditText[contains(@content-desc,'profile_InputText_FAX CONTACT NUMBER')]")
	private AndroidElement FAXCONTACTNUMBER;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'button_SAVE')]/android.widget.TextView")
	private AndroidElement buttonSAVE;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Agree']")
	private AndroidElement buttonAgree;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Close']")
	private AndroidElement buttonClose;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"menu_cell_My Account \"]/android.widget.TextView")
	private AndroidElement MenuMYACCOUNT;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='My Profile']")
	private AndroidElement MenuMYPROFILE;
	
	@AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text='Female']")
	private AndroidElement GenderFemale;
	
	@AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text='Male']")
	private AndroidElement Gendermale;
	
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Check Status']")
	private AndroidElement Buttoncheckstatus;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Roaming and IDD Activation']")
	private AndroidElement buttonRoamingandIDDActivation;
	
	@AndroidFindBy(xpath = "//android.widget.Button[@text='Activate Now']")
	private AndroidElement buttonActivateNow;
	
	@AndroidFindBy(xpath = "//android.widget.CheckBox[@resource-id='iddir_input']")
	private AndroidElement Checkboxtermsandcondition;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Login']")
	private AndroidElement buttonLogin;

	@AndroidFindBy(xpath ="//android.view.ViewGroup[@content-desc=\"Send OTP\"]/android.widget.TextView")
	private AndroidElement sendOtpButton;

	@AndroidFindBy(xpath = "//*[@content-desc='button_environmentSettings']")
	private AndroidElement buttonENVSetting;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Stage 3']")
	private AndroidElement selectStage3;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='OK']")
	private AndroidElement buttonOK;

	@AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='verifyOtp_testfield_mobile']")
	private AndroidElement OPTPIN;

	@AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='newPin_textfield_mobile']")
	private AndroidElement newpin;

	@AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='confirmPin_textfield_mobile']")
	private AndroidElement Confirmpin;

	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Create\"]/android.widget.TextView")
	private AndroidElement buttoncreatepin;

	public AndroidElement getbuttonforgotpin() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonforgotpin);
	}
	
	public AndroidElement getforgotpinmsisdn() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, forgotpinmsisdn);
	}
	
	public AndroidElement getforgotpinsubmit() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, forgotpinsubmit);
	}
	
	public AndroidElement getforgotpinConfirm() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, forgotpinConfirm);
	}
	
	public AndroidElement getMenuIcon() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, MenuIcon);
	}
	
	public AndroidElement getMenuSettings() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, MenuSettings);
	}
	
	public AndroidElement getbuttonChangePin() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonChangePin);
	}
	
	public AndroidElement getCurrentPin() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, CurrentPin);
	}
	
	public AndroidElement getNewPin() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, NewPin);
	}
	
	public AndroidElement getConfirmNewPin() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, ConfirmNewPin);
	}
	
	public AndroidElement getUpdatePin() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, UpdatePin);
	}
	
	public AndroidElement getPinchangedmsg() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Pinchangedmsg);
	}
	
	public AndroidElement getbuttonlogout() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonlogout);
	}
	
	public AndroidElement getbuttonEDIT() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonEDIT);
	}
	
	public AndroidElement getEMAILID() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, EMAILID);
	}
	
	public AndroidElement getGENDER() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, GENDER);
	}
	
	public AndroidElement getMYADDRESS() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, MYADDRESS);
	}
	
	public AndroidElement getADDRESSLINE1() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, ADDRESSLINE1);
	}
	
	public AndroidElement getADDRESSLINE2() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, ADDRESSLINE2);
	}
	
	public AndroidElement getPOSTCODE() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, POSTCODE);
	}
	
	public AndroidElement getALTERNATECONTACT() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, ALTERNATECONTACT);
	}
	
	public AndroidElement getALTERNATECONTACTNUMBER() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, ALTERNATECONTACTNUMBER);
	}
	
	public AndroidElement getHOMECONTACTNUMBER() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, HOMECONTACTNUMBER);
	}
	
	public AndroidElement getFAXCONTACTNUMBER() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, FAXCONTACTNUMBER);
	}
	
	public AndroidElement getbuttonSAVE() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonSAVE);
	}
	
	public AndroidElement getbuttonAgree() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonAgree);
	}
	
	public AndroidElement getbuttonClose() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonClose);
	}
	
	public AndroidElement getMenuMYACCOUNT() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, MenuMYACCOUNT);
	}
	
	public AndroidElement getMenuMYPROFILE() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, MenuMYPROFILE);
	}
	
	public AndroidElement getGenderFemale() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, GenderFemale);
	}
	
	public AndroidElement getGendermale() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Gendermale);
	}
	
	public AndroidElement getButtoncheckstatus() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Buttoncheckstatus);
	}
	
	public AndroidElement getbuttonRoamingandIDDActivation() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonRoamingandIDDActivation);
	}
	
	public AndroidElement getbuttonActivateNow() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonActivateNow);
	}
	
	public AndroidElement getCheckboxtermsandcondition() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Checkboxtermsandcondition);
	}

	public AndroidElement getButtonLogin() {
		return buttonLogin;
	}

	public void clickLoginButton(){
		clickElement(getButtonLogin(), "'Login' button", driver);
	}

	public AndroidElement getSendOtpButton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, sendOtpButton);
	}

	public AndroidElement getOPTPIN() {
		return OPTPIN;
	}

	public AndroidElement getnewpin() {
		return newpin;
	}

	public void enterNewpin(String newpinnum) {
		sendKeys(getnewpin(), newpinnum, "pin Number", "", driver);
	}

	public AndroidElement getConfirmpin() {
		return Confirmpin;
	}

	public void enterNewPinconfirm(String newpinconfirm) {
		sendKeys(getConfirmpin(), newpinconfirm, "pin Number", "", driver);
	}

	public AndroidElement getcreatepin() {
		return buttoncreatepin;
	}
	public void clickcreatepinbutton() {
		clickElement(getcreatepin(), "First Time Login 'Continue' button", driver);
	}

	public AndroidElement getSelectStage3() {
		return selectStage3;
	}

	public AndroidElement getButtonOK() {
		return buttonOK;
	}

	public AndroidElement getButtonENVSetting() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonENVSetting);
	}
	
}
