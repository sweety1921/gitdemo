package com.umtest.mobileapps.pageobject;

import org.openqa.selenium.WebElement;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MobileAppBunldePurchasePage extends MainUtil {

	private AndroidDriver<?> driver;

	public MobileAppBunldePurchasePage(AndroidDriver<?> driver) {
		this.driver = driver;
	}

	
	/*@AndroidFindBy(xpath = "(//android.widget.Button[@content-desc=\"MultiBar, tab, 2 of 3\"]//*)[3]")
	private AndroidElement buttonPurchaseAddon;*/

	@AndroidFindBy(xpath = "(//android.widget.Button[@content-desc=\"MultiBar, tab, 2 of 3\"]//*)[3]")
	private AndroidElement buttonPurchaseAddon;

	//android.view.View[@content-desc='services_Add-Ons']

	@AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='title_addOns']")
	private AndroidElement labelAddOnsPopUp;

	@AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc='button_Buy Now'])[1]")
	private AndroidElement labelAddOnsPopUpContents;

	//@AndroidFindBy(xpath = "(//android.widget.TextView[@text='Add-Ons']/following::android.widget.HorizontalScrollView[1]/descendant::android.widget.TextView[contains(@text,'GX')])[1]")
	@AndroidFindBy(xpath = "//android.widget.HorizontalScrollView//android.widget.TextView[contains(@text,'GX')]")
	private AndroidElement selectGX;

	//@AndroidFindBy(xpath = "(//android.widget.TextView[@text='Add-Ons']/following::android.widget.HorizontalScrollView[1]/descendant::android.widget.TextView[contains(@text,'UMI')])[1]")
	@AndroidFindBy(xpath = "//android.widget.HorizontalScrollView//android.widget.TextView[contains(@text,'UMI')]")
	private AndroidElement selectUMI;

	//@AndroidFindBy(xpath = "(//android.widget.TextView[@text='Add-Ons']/following::android.widget.HorizontalScrollView[1]/descendant::android.widget.TextView[contains(@text,'MB')])[1]")
	@AndroidFindBy(xpath = "//android.widget.HorizontalScrollView//android.widget.TextView[contains(@text,'MB')]")
	private AndroidElement selectMB;

	//@AndroidFindBy(xpath = "(//android.widget.TextView[@text='Add-Ons']/following::android.widget.HorizontalScrollView[1]/descendant::android.widget.TextView[contains(@text,'Epikkk')])[1]")
	@AndroidFindBy(xpath = "//android.widget.HorizontalScrollView//android.widget.TextView[contains(@text,'Epikkk')]")
	private AndroidElement selectEpikkk;

	//@AndroidFindBy(xpath = "(//android.widget.TextView[@text='Add-Ons']/following::android.widget.HorizontalScrollView[1]/descendant::android.widget.TextView[contains(@text,'Unlimited Call')])[1]")
	@AndroidFindBy(xpath = "//android.widget.HorizontalScrollView//android.widget.TextView[contains(@text,'Unlimited Call')]")
	private AndroidElement selectUnlimitedCall;

	//@AndroidFindBy(xpath = "(//android.widget.TextView[@text='Add-Ons']/following::android.widget.HorizontalScrollView[1]/descendant::android.widget.TextView[contains(@text,'Game-Onz')])[1]")
	@AndroidFindBy(xpath = "//android.widget.HorizontalScrollView//android.widget.TextView[contains(@text,'Game-Onz')]")
	private AndroidElement selectGameOnz;

	//@AndroidFindBy(xpath = "(//android.widget.TextView[@text='Add-Ons']/following::android.widget.HorizontalScrollView[1]/descendant::android.widget.TextView[contains(@text,'Booster')])[1]")
	@AndroidFindBy(xpath = "//android.widget.HorizontalScrollView//android.widget.TextView[contains(@text,'Booster')]")
	private AndroidElement selectBooster;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Confirm']")
	private AndroidElement buttonConfirm;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Continue']")
	private AndroidElement buttonContinue;
	
	private String buttonContinueString = "//android.widget.TextView[@text='Continue']";
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Successful']")
	private AndroidElement successMessage;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Request Submitted']")
	private AndroidElement requestSubmittedMessage;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Close']")
	private AndroidElement buttonClose;
	
	@AndroidFindBy(xpath = "//android.widget.HorizontalScrollView//android.widget.TextView[contains(@text,'SMS Plus')]")
	private AndroidElement selectSMSPlus;
	
	@AndroidFindBy(xpath = "//android.widget.HorizontalScrollView//android.widget.TextView[contains(@text,'Talk Plus')]")
	private AndroidElement selectTalkPlus;
	
	@AndroidFindBy(xpath = "//*[@content-desc=\"sidemenu_button_menu\"]/android.widget.ImageView")
	private AndroidElement MenuIcon;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Services']")
	private AndroidElement MenuServices;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='GoLife']")
	private AndroidElement MenuGoLife;
	
	@AndroidFindBy(xpath = "(//*[@content-desc=\"goLife_button_viewDetails\"])[2]/android.widget.TextView")
	private AndroidElement ButtonGoLife5;
	
	@AndroidFindBy(xpath = "(//*[@content-desc=\"goLife_button_viewDetails\"])[4]/android.widget.TextView")
	private AndroidElement ButtonGoLife10;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Subscribe']")
	private AndroidElement ButtonSubscribe;
	
	@AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='goLife_textField_email']")
	private AndroidElement Emailaddress;
	
	@AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='goLife_textField_confirmEmail']")
	private AndroidElement ConfirmEmailaddress;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Next']")
	private AndroidElement Nextbutton;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Yes']")
	private AndroidElement Yesbutton;
							
	@AndroidFindBy(xpath = "//*[@content-desc=\"goLife_checkbox_declaration_[object Object]\"]/android.widget.TextView")
	private AndroidElement Confirmcheckbox;
		
	@AndroidFindBy(xpath = "//*[@content-desc=\"goLife_checkbox_agree\"]/android.widget.TextView")
	private AndroidElement Termandconditioncheckbox;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Thank you for subscribing']")
	private AndroidElement Successmessage;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Close']")
	private AndroidElement Closebutton;
	
	private String GolifesuccessMessage = "//android.widget.TextView[@text='Thank you for subscribing']";
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='View Certificate']")
	private WebElement ViewCertificatebutton;

	/*@AndroidFindBy(xpath = "//android.widget.TextView[@text='Add-ons']")
	private WebElement buttonTextAddOns;*/

	@AndroidFindBy(xpath = "//*[@content-desc='services_Add-Ons']")
	private WebElement buttonTextAddOns;

	@AndroidFindBy(xpath = "(//android.widget.Button[@content-desc=\"MultiBar, tab, 2 of 3\"]/descendant::android.widget.ImageView)[1]")
	private WebElement buttonAddOnsPlusButton;

	public AndroidElement getButtonAddOns() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonTextAddOns);
	}
	
	public AndroidElement getButtonPurchaseAddon() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonPurchaseAddon);
	}

	public AndroidElement getButtonPurchaseAddonPlusButton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonAddOnsPlusButton);
	}


	public AndroidElement waitForLabelAddOnsPopUp() {

		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, labelAddOnsPopUp);
	}


	public AndroidElement waitForAddOnsPopUpToLoad() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, labelAddOnsPopUpContents);
	}



	public AndroidElement selectGX() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, selectGX);
	}
	
	public AndroidElement selectUMI() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, selectUMI);
	}
	
	public AndroidElement selectMB() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, selectMB);
	}
	
	public AndroidElement selectEpikkk() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, selectEpikkk);
	}
	
	public AndroidElement selectUnlimitedCall() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, selectUnlimitedCall);
	}
	
	public AndroidElement selectGameOnz() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, selectGameOnz);
	}
	
	public AndroidElement getButtonConfirm() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonConfirm);
	}
	
	public AndroidElement getButtonContinue() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonContinue);
	}
	
	public String getButtonContinueString() {
		return buttonContinueString;
	}
	
	public AndroidElement getSuccessMessage() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, successMessage);
	}

	public AndroidElement getRequestSubmittedMessage() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, requestSubmittedMessage);
	}
	
	public AndroidElement getselectBooster() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, selectBooster);
	}
	
	public AndroidElement getButtonClose() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, buttonClose);
	}
	
	public AndroidElement getselectSMSPlus() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, selectSMSPlus);
	}
	
	public AndroidElement getselectTalkPlus() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, selectTalkPlus);
	}
	
	public AndroidElement getMenuServices() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, MenuServices);
	}
	
	public AndroidElement getMenuGoLife() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, MenuGoLife);
	}
	
	public AndroidElement getButtonGoLife5() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, ButtonGoLife5);
	}
	
	public AndroidElement getButtonGoLife10() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, ButtonGoLife10);
	}
				
	public AndroidElement getMenuIcon() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, MenuIcon);
	}
	
	public AndroidElement getButtonSubscribe() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, ButtonSubscribe);
	}
	
	public AndroidElement getEmailaddress() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Emailaddress);
	}
	
	public AndroidElement getConfirmEmailaddress() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, ConfirmEmailaddress);
	}
	
	public AndroidElement getNextbutton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Nextbutton);
	}
	
	public AndroidElement getYesbutton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Yesbutton);
	}
	
	public AndroidElement getConfirmcheckbox() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Confirmcheckbox);
	}
	
	public AndroidElement getTermandconditioncheckbox() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Termandconditioncheckbox);
	}
			
	public AndroidElement getSuccessmessage() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Successmessage);
	}
	
	public AndroidElement getClosebutton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, Closebutton);
	}
	
	public String getGolifesuccessMessage() {
		return GolifesuccessMessage;
	}
	
	public AndroidElement getViewCertificatebutton() {
		return (AndroidElement) AppWait.waitForElementToBeClickable(driver, ViewCertificatebutton);
	}
}
