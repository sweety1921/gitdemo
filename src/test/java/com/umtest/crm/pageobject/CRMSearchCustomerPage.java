package com.umtest.crm.pageobject;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

public class CRMSearchCustomerPage extends MainUtil {
    private RemoteWebDriver driver;

    public CRMSearchCustomerPage(RemoteWebDriver driver) {
        this.driver = driver;
    }

    @FindBy(xpath = "//span[text()='Search Customer']")
    private WebElement searchCustomerTitle;

    @FindBy(xpath = "//input[@placeholder='Customer Name / ID Number / Service Number / Old BRN']")
    private WebElement fuzzySearchTextField;

    @FindBy(xpath = "//img[@class='icon-big-font']")
    private WebElement fuzzySearchMagnifyingGlass;

    @FindBy(xpath = "//h4[text()='Select Customer']")
    private WebElement selectCustomerPopUpTitle;

    @FindBy(xpath = "//button[text()='OK']")
    private WebElement selectCustomerPopUpOkBtn;

    @FindBy(xpath = "//button[text()='Cancel']")
    private WebElement selectCustomerPopUpCancelBtn;

    @FindBy(xpath = "//span[@class='js-view-360-text']")
    private WebElement threeSixtydegreeViewHyperLink;

    @FindBy(xpath = "//div[@class='add-cust-btn js-add-cust-btn']/img")
    private WebElement addCustomerButton;

    @FindBy(xpath = "//div[@class='personal-customer customer-icon-con']")
    private WebElement personalCustomerIcon;

    @FindBy(xpath = "//div[contains(text(),'Read Card')]/parent::div/following-sibling::div[@class='panel-body']//input[@placeholder='---Please Select---']")
    private WebElement personalCustomerIdType;

    @FindBy(xpath = "//input[@name='custName']")
    private WebElement customerName;

    @FindBy(xpath = "//input[@class='form-control cert-nbr-flag']")
    private WebElement customerIdNumber;

    @FindBy(xpath = "//label[@title='Race']/following-sibling::div/div[@class='input-group ']/input")
    private WebElement raceTextField;

    @FindBy(xpath = "//label[@title='Customer Preferred Language']/following-sibling::div/div[@class='input-group ']/input")
    private WebElement customerPreferredLanguage;

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

    @FindBy(xpath = "//input[@name='mobileAreaCode']")
    private WebElement mobileAreaCode;

    @FindBy(xpath = "//label[@title='Customer Phone 1']")
    private WebElement customerPhoneLabel;

    @FindBy(xpath = "//input[@name='mobilePhone']")
    private WebElement customerPhoneNumber;

    @FindBy(xpath = "//button[contains(text(),'OK')]")
    private WebElement personalCustomerOkButton;

    @FindBy(xpath = "//div[contains(text(),'The data is incomplete, Please check and input again.')]")
    private WebElement warningMessage;

    @FindBy(xpath = "//button[@class='btn btn-warning btn-min-width']")
    private WebElement warningOkButton;

    @FindBy(xpath = "//div[contains(text(),'is successfully created.')]")
    private WebElement customerCreationSuccessMsg;

    @FindBy(xpath = "//label[@title='Customer Preferred Language']/following-sibling::div//span[@class='input-group-addon']")
    private WebElement custPreferredLangDropDown;

    @FindBy(xpath = "//h4[text()='Success']/ancestor::div[@class='modal-header ui-draggable-handle']/following-sibling::div//button[contains(text(),'OK')]")
    private WebElement successMsgOkBtn;

    @FindBy(xpath = "//span[@class='iconfont pto-search js-search-menu-icon search-menu-icon-um']")
    private WebElement searchMenuMagnifyingGlass;

    @FindBy(xpath = "//input[@id='searchMenuInput']")
    private WebElement searchMenuTextField;

    @FindBy(xpath = "//span[@title='Starter Pack Activation Order']")
    private WebElement starterPackActivationOrder;

    @FindBy(xpath = "//span[@class='iconfont icon-menu-list portal__nav_icon js-menu']")
    private WebElement iconMenuList;

    @FindBy(xpath = "//span[@title='IT Center Administrator Portal ']")
    private WebElement itCenterAdminPortal;

    @FindBy(xpath = "//span[contains(text(),'Order Center')]")
    private WebElement orderCenter;

    @FindBy(xpath = "//div[@class='ui-tabs-panel']//input[@placeholder='Customer Name / ID Number / Service Number / Old BRN']")
    private WebElement starterPackFuzzySearch;

    @FindBy(xpath = "//div[@class='ui-tabs-panel']//img[@class='icon-big-font']")
    private WebElement starterPackFuzzySearchBtn;

    @FindBy(xpath = "//input[@placeholder='Service Number / SIM Serial Number']")
    private WebElement addServiceNumberTextField;

    @FindBy(xpath = "//button[@class='btn btn-primary js-addbyAccNbrandICCID']")
    private WebElement serviceNumberAddButton;

    @FindBy(xpath = "//button[contains(text(),'Active')]")
    private WebElement activeButton;

    // Starter pack Activation page related elements

    @FindBy(xpath = "//button[contains(text(),'Next')]")
    private WebElement nextButton;

    @FindBy(xpath = "//button[@class='btn btn-primary js-ok']")
    private WebElement orderValidationResultsOkBtn;

    @FindBy(xpath = "//span[contains(text(),'Has confirmed the order with customer?')]/preceding-sibling::div/input")
    public WebElement confirmCheckBox;

    @FindBy(xpath = "//span[contains(text(),'Has confirmed the order with customer?')]")
    public WebElement confirmOrderText;

    @FindBy(xpath = "//button[@class='btn btn-primary js-btn-next']")
    private WebElement confirmNextBtn;

    @FindBy(xpath = "//label[contains(text(),'TERMS & CONDITIONS')]")
    private WebElement termsAndConditionText;

    @FindBy(xpath = "//input[@class='js-check-bypass']")
    private WebElement bypassAcknowledgeCheckBox;

    @FindBy(xpath = "//button[@class='btn btn-primary js-btn-pay']")
    private WebElement payButton;

    @FindBy(xpath = "//span[contains(text(),'Starter Pack Activation')]/parent::div/span[@class='da-val']")
    private WebElement starterPackMSISDN;

    @FindBy(xpath = "//div[contains(@class,'oe-print-td')]/span[contains(text(),'Unlimited Funz')]")
    private WebElement planName;

    @FindBy(xpath = "//h4[contains(text(),'Submit Successfully！')]")
    private WebElement successMessage;

    @FindBy(xpath = "//button[@id='back-oe-intro']")
    private WebElement confirmButton;

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

    @FindBy(xpath = "//*[@class='icon iconfont icon-gene-help js-Account-Manager-Name-Tip']//parent::label//parent::div//following-sibling::input")
    public WebElement accountmanagernameremove;

    @FindBy(xpath = "//div[@class='modal-footer detail-modal-footer']//following-sibling::button[@class='btn btn-primary btn-min-width js-ok']")
    private WebElement OKbuttonaccountinfo;

    @FindBy(xpath = "//button[@class='btn btn-primary js-ok']")
    private WebElement OKbuttonvalidation;

    @FindBy(xpath = "//button[@class='btn btn-success btn-min-width']")
    private WebElement sucessOKbutton;

    @FindBy(xpath = "//div[@class='form-group required js-email']//following-sibling::div//following-sibling::input")
    private WebElement emailid;

    @FindBy(xpath = "//div[@class='btn-toolbar pull-right']//following-sibling::button[@class='btn btn-primary btn-min-width js-ok']")
    private WebElement OKbutton;

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

    @FindBy(xpath = "//button[@id='next-btn-intro']")
    private WebElement confirmNextbutton;

    @FindBy(xpath = "//h4[contains(text(),'Submit Successfully！')]")
    private WebElement labelorderSuccessMessage;

    @FindBy(xpath = "//button[@id='back-oe-intro']")
    private WebElement Confirmbutton;

    public WebElement getSearchCustomerPageTitle() {
        return AppWait.waitForElementForVisibility(driver, searchCustomerTitle);
    }

    public WebElement getFuzzySearchTextField() {
        return AppWait.waitForElementToBeClickable(driver, fuzzySearchTextField);
    }

    public WebElement getFuzzySearchMagnifyingGlass() {
        return AppWait.waitForElementToBeClickable(driver, fuzzySearchMagnifyingGlass);
    }

    public WebElement getSelectCustomerPopupTitle() {
        return AppWait.waitForElementForVisibility(driver, selectCustomerPopUpTitle);
    }

    public WebElement getSelectCustomerPopupOkBtn() {
        return AppWait.waitForElementToBeClickable(driver, selectCustomerPopUpOkBtn);
    }

    public WebElement getSelectCuatomerPopupCamcelBtn() {
        return AppWait.waitForElementToBeClickable(driver, selectCustomerPopUpCancelBtn);
    }

    public WebElement getThreeSixtydegreeViewHyperLink() {
        return AppWait.waitForElementToBeClickable(driver, threeSixtydegreeViewHyperLink);
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

    public WebElement getCustomerIdNumberTextField() {
        return AppWait.waitForElementToBeClickable(driver, customerIdNumber);
    }

    public WebElement getRaceTextField() {
        return AppWait.waitForElementToBeClickable(driver, raceTextField);
    }

    public WebElement getCustomerLanguageTestField() {
        return AppWait.waitForElementToBeClickable(driver, customerPreferredLanguage);
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

    public WebElement getMobileAreaCode() {
        return AppWait.waitForElementToBeClickable(driver, mobileAreaCode);
    }

    public WebElement getCustomerPhoneLabel() {
        return AppWait.waitForElementToBeClickable(driver, customerPhoneLabel);
    }

    public WebElement getCustomerPhoneNumber() {
        return AppWait.waitForElementToBeClickable(driver, customerPhoneNumber);
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

    public WebElement getCustPreferredLangDropDown() {
        return AppWait.waitForElementToBeClickable(driver, custPreferredLangDropDown);
    }

    public WebElement getSuccessMsgOkBtn() {
        return AppWait.waitForElementToBeClickable(driver, successMsgOkBtn);
    }

    public WebElement getSearchMenuMagnifyingGlass() {
        return AppWait.waitForElementToBeClickable(driver, searchMenuMagnifyingGlass);
    }

    public WebElement getStarterPackActivationOrder() {
        return AppWait.waitForElementToBeClickable(driver, starterPackActivationOrder);
    }

    public WebElement getSearchMenuTextField() {
        return AppWait.waitForElementToBeClickable(driver, searchMenuTextField);
    }

    public WebElement getIconMenuList() {
        return AppWait.waitForElementToBeClickable(driver, iconMenuList);
    }

    public WebElement getItCenterAdminPortal() {
        return AppWait.waitForElementToBeClickable(driver, itCenterAdminPortal);
    }

    public WebElement getOrderCenter() {
        return AppWait.waitForElementToBeClickable(driver, orderCenter);
    }

    public WebElement getStarterPackFuzzySearch() {
        return AppWait.waitForElementToBeClickable(driver, starterPackFuzzySearch);
    }

    public WebElement getStarterPackFuzzySearchBtn() {
        return AppWait.waitForElementToBeClickable(driver, starterPackFuzzySearchBtn);
    }

    public WebElement getAddServiceNumberTextField() {
        return AppWait.waitForElementToBeClickable(driver, addServiceNumberTextField);
    }

    public WebElement getServiceNumberAddButton() {
        return AppWait.waitForElementToBeClickable(driver, serviceNumberAddButton);
    }

    public WebElement getActiveButton() {
        return AppWait.waitForElementToBeClickable(driver, activeButton);
    }

    //Starter pack activation order related element methods

    public WebElement getNextButton() {
        return AppWait.waitForElementForVisibility(driver, nextButton);
    }

    public WebElement getOrderValidationResultsOkBtn() {
        return AppWait.waitForElementForVisibility(driver, orderValidationResultsOkBtn);
    }

    public WebElement getConfirmCheckBox() {
        return AppWait.waitForElementForVisibility(driver, confirmCheckBox);
    }

    public WebElement getConfirmOrderText() {
        return AppWait.waitForElementForVisibility(driver, confirmOrderText);
    }

    public WebElement getConfirmNextBtn() {
        return AppWait.waitForElementForVisibility(driver, confirmNextBtn);
    }

    public WebElement getTermsAndConditionText() {
        return AppWait.waitForElementForVisibility(driver, termsAndConditionText);
    }

    public WebElement getBypassAcknowledgeCheckBox() {
        return AppWait.waitForElementForVisibility(driver, bypassAcknowledgeCheckBox);
    }

    public WebElement getPayButton() {
        return AppWait.waitForElementForVisibility(driver, payButton);
    }

    public WebElement getStarterPackMSISDN() {
        return AppWait.waitForElementForVisibility(driver, starterPackMSISDN);
    }

    public WebElement getPlanName() {
        return AppWait.waitForElementForVisibility(driver, planName);
    }

    public WebElement getSuccessMessage() {
        return AppWait.waitForElementForVisibility(driver, successMessage);
    }

    public WebElement getConfirmButton() {
        return AppWait.waitForElementForVisibility(driver, confirmButton);
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

    public WebElement getaccountmanagernameremove() {
        return AppWait.waitForElementForVisibility(driver, accountmanagernameremove);
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

    public WebElement getConfirmbutton() {
        return AppWait.waitForElementToBeClickable(driver, Confirmbutton);
    }
}
