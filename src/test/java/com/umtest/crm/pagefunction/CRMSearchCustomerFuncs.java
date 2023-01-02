package com.umtest.crm.pagefunction;

import com.umtest.crm.pageobject.CRMSearchCustomerPage;
import com.umtest.crm.pageobject.CRMStarterPackActivationOrderPage;
import com.umtest.selfcare.pagefunction.SelfcareCreditShareFuncs;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.JSONReader;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.umrex.pagefunction.UMREXPrepaidRegistrationFuncs;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

import static com.umtest.testframe.lib.RandomDataGenerator.randomChar;

public class CRMSearchCustomerFuncs extends CRMSearchCustomerPage {

    private static Logger logger = LogManager.getLogger(SelfcareCreditShareFuncs.class);
    private RemoteWebDriver driver;

    public CRMSearchCustomerFuncs(RemoteWebDriver driver) {
        super(driver);
        this.driver =  driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);
    }

    public void searchMSISDNAndNavigateToThreeSixtyDegreeView(String msisdn, RemoteWebDriver driver) throws InterruptedException {
        Thread.sleep(3000);
        javaScriptClickElement(getFuzzySearchTextField(),"Text Field",driver);
        javaScriptEnterText(getFuzzySearchTextField(),driver, msisdn);
        javaScriptClickElement(getFuzzySearchMagnifyingGlass(),"Magnifying Glass",driver);
        Thread.sleep(2000);
        javaScriptClickElement(getSelectCustomerPopupOkBtn(),"Customer Popup Ok Button",driver);
        Thread.sleep(2000);
        javaScriptClickElement(getThreeSixtydegreeViewHyperLink(),"360 view",driver);
        Thread.sleep(2000);
    }

    public void personalCustomerRegistration(String custIdentificationType) throws IOException, ParseException {
        try {
            if (custIdentificationType.equalsIgnoreCase("MYKAD")) {
                getDefaultCustomerInfo("src\\test\\resources\\testData\\customerInfoMYKAD.json");
                javaScriptClickElement(getAddCustomerButton(), "Add Customer button", driver);
                javaScriptClickElement(getPersonalCustomerIcon(), "Personal Customer Icon", driver);
                sendKeys(getPersonalCustomerIdType(), "MyKad", "MyKad Customer ID", "", driver);
                pressEnterKey(getPersonalCustomerIdType());
                sendKeys(getCustomerNameTextField(), dictionary.get("CUSTOMER_NAME"), "Customer Name", "", driver);
                sendKeys(getCustomerIdNumberTextField(), dictionary.get("CUSTOMER_ID"), "Customer ID Number", "", driver);
                sendKeys(getRaceTextField(), "Indian", "Race", "", driver);
                Actions action = new Actions(driver);
                action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
                getCustomerLanguageTestField().clear();
                sendKeys(getCustomerLanguageTestField(), "English", "Race", "", driver);
                action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
                javaScriptClickElement(getCustomerAddressEditIcon(), "Customer Address edit icon", driver);
                sendKeys(getPostCodeTextField(), dictionary.get("CUSTOMER_POSTCODE"), "Postcode", "", driver);
                javaScriptClickElement(getPostCodeTitle(), "Postcode Title", driver);
                sendKeys(getAddressTextField(), dictionary.get("CUSTOMER_ADDRESS"), "Address", "", driver);
                javaScriptClickElement(getEnterAddressOkButton(), "Ok Button", driver);
                action.sendKeys(Keys.PAGE_DOWN).build().perform();
                String filePath = System.getProperty("user.dir") + "/src/test/resources/testData/customerInfoMYKAD.json";
                Thread.sleep(3000);
                uploadFile(filePath, getCustomerIdCopyUploadIcon());
                sendKeys(getMobileAreaCode(), "60", "Mobile Area code", "", driver);
                javaScriptClickElement(getPersonalCustomerOkButton(), "Ok Button", driver);
                try {
                    if (getWarningMessage().isDisplayed()) {
                        javaScriptClickElement(getWarningOkButton(), "Warning Ok button", driver);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                action.sendKeys(Keys.PAGE_UP).build().perform();
                getCustomerLanguageTestField().clear();
                Thread.sleep(3000);
                javaScriptClickElement(getPersonalCustomerOkButton(), "Ok Button", driver);
                sendKeys(getCustomerPhoneNumber(), "1139291218", "Customer Phone number", "", driver);
                javaScriptClickElement(getPersonalCustomerOkButton(), "Ok Button", driver);
                try {
                    do {
                        if (getWarningMessage().isDisplayed()) {
                            javaScriptClickElement(getWarningOkButton(), "Warning Ok button", driver);
                        }
                    }while(getWarningMessage().isDisplayed());
                }catch(Exception e){
                    e.printStackTrace();
                }
                sendKeys(getCustomerLanguageTestField(), "English", "Race", "", driver);
                action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
                action.sendKeys(Keys.PAGE_DOWN).build().perform();
                javaScriptClickElement(getPersonalCustomerOkButton(), "Ok Button", driver);
                compareInString(getCustomerCreationSuccessMsg().getText(),"Customer "+dictionary.get("CUSTOMER_NAME")+" is successfully created.", "Plan Name", driver);
                javaScriptClickElement(getSuccessMsgOkBtn(), "Success popup Ok Button", driver);
            }
        }
        catch(Exception e){
            logger.info(e.getMessage());
            e.printStackTrace();
        }
    }

    public void getDefaultCustomerInfo(String sTestDataFile) throws IOException, ParseException {

        JSONObject oCustInfo= JSONReader.loadJSONTestDataFile(sTestDataFile);

        MainUtil.dictionary.put("CUSTOMER_ID", oCustInfo.get("custIDStartDigits").toString()+randomChar("randomnumber#6"));
        MainUtil.dictionary.put("CUSTOMER_NAME",oCustInfo.get("custNameStartChars").toString()+randomChar("randomchar#5"));
        MainUtil.dictionary.put("CUSTOMER_GENDER",oCustInfo.get("custGender").toString());
        MainUtil.dictionary.put("CUSTOMER_DOB_DD",oCustInfo.get("custDOBDD").toString());
        MainUtil.dictionary.put("CUSTOMER_DOB_MM",oCustInfo.get("custDOBMM").toString());
        MainUtil.dictionary.put("CUSTOMER_DOB_YYYY",oCustInfo.get("custDOBYYYY").toString());
        MainUtil.dictionary.put("CUSTOMER_ADDRESS",oCustInfo.get("custAddress").toString());
        MainUtil.dictionary.put("CUSTOMER_CITY",oCustInfo.get("custCity").toString());
        MainUtil.dictionary.put("CUSTOMER_STATE",oCustInfo.get("custState").toString());
        MainUtil.dictionary.put("CUSTOMER_POSTCODE",oCustInfo.get("custPostCode").toString());
        MainUtil.dictionary.put("CUSTOMER_EMAIL",oCustInfo.get("custEmail").toString());
        MainUtil.dictionary.put("CUSTOMER_NUMBER",oCustInfo.get("custNumber").toString());
        if(oCustInfo.containsKey("custNationality")){
            MainUtil.dictionary.put("CUSTOMER_NATIONALITY",oCustInfo.get("custNationality").toString());
        }
    }

    public boolean starterPackActivationOrder(String registrationType) throws Exception {

        javaScriptClickElement(getIconMenuList(), "Icon Menu List", driver);
        clickElement(getOrderCenter(), "Order Center", driver);
        javaScriptClickElement(getStarterPackActivationOrder(), "Starter Pack Activation Order link", driver);
        javaScriptClickElement(getStarterPackFuzzySearch(),"Text Field",driver);
//        dictionary.put("CUSTOMER_NAME","TEST AUTO TMOPPL");
        javaScriptEnterText(getStarterPackFuzzySearch(),driver, dictionary.get("CUSTOMER_NAME"));
        javaScriptClickElement(getStarterPackFuzzySearchBtn(),"Magnifying Glass",driver);
        Thread.sleep(2000);
        javaScriptClickElement(getSelectCustomerPopupOkBtn(),"Customer Popup Ok Button",driver);
        Thread.sleep(2000);
        ApplicationUtil.getMSISDNFromSIMTable("PREPAID", MainUtil.dictionary.get("PLAN_NAME"));

//        dictionary.put("MSISDN","601139277088");
        sendKeys(getAddServiceNumberTextField(), dictionary.get("MSISDN"), "Service Number", "", driver);
        javaScriptClickElement(getServiceNumberAddButton(),"Add button",driver);
        javaScriptClickElement(getActiveButton(),"Active button",driver);
        Thread.sleep(5000);
        Actions action = new Actions(driver);
        action.sendKeys(Keys.PAGE_DOWN).sendKeys(Keys.PAGE_DOWN).build().perform();
        javaScriptClickElement(getNextButton(),"Next button",driver);
        javaScriptClickElement(getOrderValidationResultsOkBtn(),"Ok button",driver);
        javaScriptClickElement(getConfirmOrderText(),"Confirm order text",driver);
        javaScriptClickElement(getConfirmNextBtn(),"Confirm Next button",driver);
        javaScriptClickElement(getTermsAndConditionText(),"Terms & Condition text",driver);
        action.sendKeys(Keys.PAGE_DOWN).sendKeys(Keys.PAGE_DOWN).build().perform();
        javaScriptClickElement(getBypassAcknowledgeCheckBox(),"ByPass Acknowledge checkbox",driver);
        javaScriptClickElement(getConfirmNextBtn(),"Next button",driver);
        javaScriptClickElement(getPayButton(),"Pay button",driver);
        String msisdnFullText[] = getStarterPackMSISDN().getText().split(":");
        String activationOrderMsisdn = msisdnFullText[0].substring(15);
        compareInString(activationOrderMsisdn.trim(),dictionary.get("MSISDN"), "MSISDN", driver);
        javaScriptClickElement(getConfirmNextBtn(),"Next button",driver);
        boolean checkFlag1 = compareInString(getSuccessMessage().getText().trim(),"Submit Successfully", "Success Message", driver);
        javaScriptClickElement(getConfirmButton(),"Confirm button",driver);
        if (checkFlag1) {
            ApplicationUtil.insertAccountIntoDB("PREPAID","ZSMART",registrationType);
            ApplicationUtil.updateSIMStatusInSIMDetails(MainUtil.dictionary.get("SIM_NO"));
        }
        return true;
    }

    public boolean doCRMLooseSimPrepaidRegistration(String registrationType, String identificationType) {

        boolean methodReturn = false;

        try {
            personalCustomerRegistration(identificationType);
            //RandomDataGenerator.generateCustomerDataUMREX(identificationType);
            ApplicationUtil.getMSISDNFromSIMTable(MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("PLAN_NAME"));

            javaScriptClickElement(getThreeSixtydegreeViewHyperLink(),"360 view",driver);

            Thread.sleep(2000);

            javaScriptClickElement(getBtnorderentry(),"Order Entry",driver);
            Thread.sleep(2000);
            javaScriptClickElement(getgoshopping(),"Order Entry",driver);

            sendKeys(getSearchplanname(), dictionary.get("PLAN_NAME"), "Plan Name", "", driver);
            getSearchplanname().sendKeys(Keys.RETURN);

            Thread.sleep(2000);
            clickElement(getorderbutton(), "Order Button", driver);

            try {
                do {
                    if (getServicenumberbtn().isDisplayed()) {
                        javaScriptClickElement(getServicenumberbtn(), "Service number button", driver);
                        break;
                    }
                }while(getServicenumberbtn().isDisplayed());
            }catch(Exception e){
                e.printStackTrace();
            }

            sendKeys(getenterServicenumber(), dictionary.get("MSISDN"), "MSISDN Number", "", driver);

            clickElement(getQuerybutton(), "Query button", driver);

            driver.findElementByXPath("//div[@class='number-card-box js-item-nbr-box']//following-sibling::div//*[@title='"+dictionary.get("MSISDN")+"']").click();

            clickElement(getQueryokbutton(), "Query button", driver);
            Thread.sleep(5000);
            System.out.println(dictionary.get("SIM_NO").substring(0, (dictionary.get("SIM_NO").length()-2)));
            sendKeys(getSimserialnumber(), dictionary.get("SIM_NO"), "SIM Number", "", driver);
            Thread.sleep(1000);
//            driver.findElementByXPath("//*[@title='SIM Serial Number']").click();

            clickElement(getNextbutton(), "Next button", driver);

            clickElement(getOKbuttonvalidation(), "OK button", driver);
            Thread.sleep(3000);
            clickElement(getcustomercheckbox(), "Checkbox button", driver);

            clickElement(getcustomernextbtn(), "Next button", driver);
            Thread.sleep(2000);

            getbypasscheckbox().sendKeys(Keys.RETURN);

            clickElement(getbypasscheckbox(), "Bypass Checkbox", driver);

            clickElement(getacknextbutton(), "Next button", driver);

            clickElement(getpaybutton(), "Pay button", driver);

            clickElement(getCashbutton(), "Cash button", driver);

            sendKeys(getamountreceived(), dictionary.get("Amount"), "Received Amount", "", driver);

            clickElement(getpaymentbutton(), "Payment button", driver);

            Thread.sleep(2000);
            clickElement(getconfirmNextbutton(), "Next button", driver);

            boolean checkFlag = checkForText("", getlabelorderSuccessMessage(), "Submit Successfully！", "Success Message", driver);
            if (checkFlag == true) {
                ApplicationUtil.insertAccountIntoDB("PREPAID", "UMREX", registrationType);
                ApplicationUtil.updateSIMStatusInSIMDetails(MainUtil.dictionary.get("SIM_NO"));
                clickElement(getConfirmbutton(), "Next button", driver);
                methodReturn = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while doing loose sim Prepaid registration  :" + e);
        }
        return methodReturn;
    }
}
