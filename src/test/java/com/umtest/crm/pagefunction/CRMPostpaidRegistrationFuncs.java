package com.umtest.crm.pagefunction;

import com.umtest.crm.pageobject.CRMCustomer360Page;
import com.umtest.crm.pageobject.CRMPostpaidRegistrationPage;
import com.umtest.selfcare.pagefunction.SelfcareCreditShareFuncs;
import com.umtest.testframe.lib.*;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.time.Duration;

import static com.umtest.testframe.lib.JSONReader.parseJSONFile;
import static com.umtest.testframe.lib.RandomDataGenerator.randomChar;

public class CRMPostpaidRegistrationFuncs extends CRMPostpaidRegistrationPage {
    private static Logger logger = LogManager.getLogger(CRMPostpaidRegistrationFuncs.class);
    private RemoteWebDriver driver;

    public CRMPostpaidRegistrationFuncs(RemoteWebDriver driver) {
        super(driver);
        this.driver =  driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);
    }



    public boolean doCRMPostpaidRegistration(String registrationType, String identificationType) {

        boolean methodReturn = false;

        try {
            CustomerAccountcreation(identificationType);
            //RandomDataGenerator.generateCustomerDataUMREX(identificationType);
            ApplicationUtil.getMSISDNFromSIMTable("POSTPAID", MainUtil.dictionary.get("PLAN_NAME"));

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
                    if (getAccountpage().isDisplayed()) {
                        javaScriptClickElement(getAccountpage(), "Account button", driver);
                        break;
                    }
                }while(getAccountpage().isDisplayed());
            }catch(Exception e){
                e.printStackTrace();
            }

            //clickElement(getAccountpage(), "Account button", driver);
            Thread.sleep(2000);
            clickElement(getAddAcount(), "Add Account button", driver);

            //sendKeys(getcompanyname(), "Test", "company Name", "", driver);

            clickElement(getaccountmanagernameremove(), "account manager name removal", driver);

            sendKeys(getemailid(), dictionary.get("CUSTOMER_EMAIL"), "Email Address", "", driver);

            clickElement(getOKbutton(), "OK button", driver);

            clickElement(getsucessOKbutton(), "Sucess OK button", driver);

            clickElement(getOKbuttonaccountinfo(), "OK button", driver);

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

            //clickElement(getServicenumberbtn(), "ServiceNumber button", driver);

            sendKeys(getenterServicenumber(), dictionary.get("MSISDN"), "MSISDN Number", "", driver);

            clickElement(getQuerybutton(), "Query button", driver);

            driver.findElementByXPath("//div[@class='number-card-box js-item-nbr-box']//following-sibling::div//*[@title='"+dictionary.get("MSISDN")+"']").click();

            clickElement(getQueryokbutton(), "Query button", driver);
            Thread.sleep(1000);
            System.out.println(dictionary.get("SIM_NO").substring(0, (dictionary.get("SIM_NO").length()-2)));
            sendKeys(getSimserialnumber(), dictionary.get("SIM_NO").substring(0, (dictionary.get("SIM_NO").length()-2)), "SIM Number", "", driver);
            Thread.sleep(1000);
            driver.findElementByXPath("//*[@title='SIM Serial Number']").click();

            clickElement(getNextbutton(), "Next button", driver);

            clickElement(getOKbuttonvalidation(), "OK button", driver);

            String filePath = System.getProperty("user.dir") + "/src/test/resources/testData/customerInfoMYKAD.json";
//                driver.findElement(By.xpath("//input[@type='file' and @name='file']"));
            Thread.sleep(3000);
            uploadFile(filePath, getCustomerIdCopyUploadIcon());

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
                ApplicationUtil.insertAccountIntoDB("POSTPAID", "UMREX", registrationType);
                ApplicationUtil.updateSIMStatusInSIMDetails(MainUtil.dictionary.get("SIM_NO"));
                clickElement(getConfirmbutton(), "Next button", driver);
                methodReturn = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while doing prepaid registration  :" + e);
        }
        return methodReturn;
    }

    public boolean doCRMFSPPostpaidRegistration(String registrationType, String identificationType) {

        boolean methodReturn = false;

        try {
            CustomerAccountcreation(identificationType);

            String[] arrplanname = MainUtil.dictionary.get("PLAN_NAME").split(";");
            System.out.println(arrplanname.length);

            String linecount=Integer.toString(arrplanname.length);
            ApplicationUtil.getMSISDNFromSIMTableForMultiline("POSTPAID", MainUtil.dictionary.get("PLAN_NAME"),linecount);
            int j = 1;
            for (int i = 0; i<arrplanname.length; i++)
            {
                dictionary.put("PLAN_NAME"+j, arrplanname[i]);
                j++;
            }

            javaScriptClickElement(getThreeSixtydegreeViewHyperLink(),"360 view",driver);

            Thread.sleep(2000);

            javaScriptClickElement(getBtnorderentry(),"Order Entry",driver);
            Thread.sleep(2000);
            javaScriptClickElement(getgoshopping(),"Order Entry",driver);

            sendKeys(getSearchplanname(), dictionary.get("SearchplanName"), "Plan Name", "", driver);
            getSearchplanname().sendKeys(Keys.RETURN);

            Thread.sleep(2000);
            //clickElement(getorderbutton(), "Order Button", driver);

            clickElement(getorderbuttonFSP(dictionary.get("SearchplanName")),"Order Button", driver);

            try {
                do {
                    if (getAccountpage().isDisplayed()) {
                        javaScriptClickElement(getAccountpage(), "Account button", driver);
                        break;
                    }
                }while(getAccountpage().isDisplayed());
            }catch(Exception e){
                e.printStackTrace();
            }

            //clickElement(getAccountpage(), "Account button", driver);
            Thread.sleep(2000);
            clickElement(getAddAcount(), "Add Account button", driver);

            //sendKeys(getcompanyname(), "Test", "company Name", "", driver);

            //clickElement(getaccountmanagernameremove(), "account manager name removal", driver);
            Thread.sleep(1000);
            try {
                do {
                    if (getemailid().isDisplayed()) {
                        sendKeys(getemailid(), dictionary.get("CUSTOMER_EMAIL"), "Email Address", "", driver);
                        break;
                    }
                }while(getemailid().isDisplayed());
            }catch(Exception e){
                e.printStackTrace();
            }

            clickElement(getOKbutton(), "OK button", driver);

            clickElement(getsucessOKbutton(), "Sucess OK button", driver);

            clickElement(getOKbuttonaccountinfo(), "OK button", driver);

            Thread.sleep(2000);

            clickElement(getfspaddbutton(), "Add FSP Button", driver);

            clickElement(getfspPrinciplebutton(), "Add Principle Button", driver);
            if(MainUtil.dictionary.get("PLAN_NAME").contains("Family")) {
                if(arrplanname.length==2) {
                    clickElement(getfspfamilybutton(), "Add Member Button", driver);
                } else if (arrplanname.length==3) {
                    clickElement(getfspfamilybutton(), "Add Member Button", driver);
                    clickElement(getfspfamilybutton(), "Add Member Button", driver);
                } else if (arrplanname.length==4) {
                    clickElement(getfspfamilybutton(), "Add Member Button", driver);
                    clickElement(getfspfamilybutton(), "Add Member Button", driver);
                }
            } else {
                if (arrplanname.length == 2) {
                    clickElement(getfspmemberbutton(), "Add Member Button", driver);
                } else if (arrplanname.length == 3) {
                    clickElement(getfspmemberbutton(), "Add Member Button", driver);
                    clickElement(getfspmemberbutton(), "Add Member Button", driver);
                } else if (arrplanname.length == 4) {
                    clickElement(getfspmemberbutton(), "Add Member Button", driver);
                    clickElement(getfspmemberbutton(), "Add Member Button", driver);
                }
            }

            clickElement(getfspOKbutton(), "Add OK Button", driver);

            FSPaccountdetailform();

            //clickElement(getfspOKbutton(), "Add OK Button", driver);
            Thread.sleep(3000);

            clickElement(getNextbutton(), "Next button", driver);

            clickElement(getOKbuttonvalidation(), "OK button", driver);

           /* String filePath = System.getProperty("user.dir") + "/src/test/resources/testData/customerInfoMYKAD.json";
            Thread.sleep(3000);
            uploadFile(filePath, getCustomerIdCopyUploadIcon());*/
            Thread.sleep(1000);
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
            clickElement(getConfirmbutton(), "Next button", driver);

            for (int q = 1; q<arrplanname.length+1; q++) {

                dictionary.put("MSISDN", dictionary.get("MSISDN"+q));
                dictionary.put("PLAN_NAME", dictionary.get("PLAN_NAME"+q));
                dictionary.put("SIM_NO", dictionary.get("SIM_NO"+q));

                if (checkFlag == true) {
                    ApplicationUtil.insertAccountIntoDB("POSTPAID","CRM",registrationType);
                    ApplicationUtil.updateSIMStatusInSIMDetails(MainUtil.dictionary.get("SIM_NO"));

                    methodReturn = true;
                }

            }



        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while doing prepaid registration  :" + e);
        }
        return methodReturn;
    }

    public void CustomerAccountcreation(String custIdentificationType) throws IOException, ParseException {
        try {
            if (custIdentificationType.equalsIgnoreCase("MYKAD")) {
                getDefaultCustomerInfo("src\\test\\resources\\testData\\customerInfoMYKAD.json");
                javaScriptClickElement(getAddCustomerButton(), "Add Customer button", driver);
                javaScriptClickElement(getPersonalCustomerIcon(), "Personal Customer Icon", driver);
                sendKeys(getPersonalCustomerIdType(), "MyKad", "MyKad Customer ID", "", driver);
                pressEnterKey(getPersonalCustomerIdType());
                Thread.sleep(2000);
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
//                driver.findElement(By.xpath("//input[@type='file' and @name='file']"));
                Thread.sleep(3000);
                uploadFile(filePath, getCustomerIdCopyUploadIcon());
                sendKeys(getMobileAreaCode(), "60", "Mobile Area code", "", driver);
//                javaScriptClickElement(getCustomerPhoneLabel(), "Customer Phone label", driver);
//                sendKeys(getCustomerPhoneNumber(), "1139291218", "Customer Phone number", "", driver);
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
//              sendKeys(getCustomerLanguageTestField(), "English", "Race", "", driver);
//              action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
//              action.sendKeys(Keys.PAGE_DOWN).build().perform();
//              javaScriptClickElement(getCustPreferredLangDropDown(), "Preffered Language drop down", driver);
//              action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
//              action.sendKeys(Keys.PAGE_DOWN).build().perform();
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

    public void FSPaccountdetailform() throws Exception {

        String[] arrplanname = MainUtil.dictionary.get("PLAN_NAME").split(";");
        System.out.println(arrplanname.length);

        int j = 1;
        for (int i = 0; i<arrplanname.length; i++)
        {
            dictionary.put("PLAN_NAME"+j, arrplanname[i]);
            j++;
        }

        String idnum= driver.findElementByXPath("//*[contains(text(),'"+MainUtil.dictionary.get("PLAN_NAME1")+"')]//parent::a").getAttribute("href");
        System.out.println(idnum);
        System.out.println(idnum.split("#", 2)[1]);
        int k = 1;
        for (int h = 0; h<arrplanname.length; h++)
        {
            String intidnum=idnum.split("#", 2)[1];
            long principalpgeas=Long.parseLong(intidnum)+h;
            String principalpge=Long.toString(principalpgeas);
            System.out.println(principalpge);
            MainUtil.dictionary.put("MSISDN", MainUtil.dictionary.get("MSISDN"+(h+1)));
            MainUtil.dictionary.put("PLAN_NAME", MainUtil.dictionary.get("PLAN_NAME"+(h+1)));
            MainUtil.dictionary.put("SIM_NO", MainUtil.dictionary.get("SIM_NO"+(h+1)));

            driver.findElementByXPath("//*[contains(text(),'"+MainUtil.dictionary.get("PLAN_NAME")+"')]//parent::a[@href='#"+principalpge+"']").click();

            try {
                do {
                    if (getFSPservicenumber(principalpge).isDisplayed()) {
                    javaScriptClickElement(getFSPservicenumber(principalpge), "Service number button", driver);
                    break;
                    }
                    }while(getFSPservicenumber(principalpge).isDisplayed());
                }catch(Exception e){
            e.printStackTrace();
            }

            sendKeys(getenterServicenumber(), dictionary.get("MSISDN"), "MSISDN Number", "", driver);

            clickElement(getQuerybutton(), "Query button", driver);

            driver.findElementByXPath("//div[@class='number-card-box js-item-nbr-box']//following-sibling::div//*[@title='"+dictionary.get("MSISDN")+"']").click();

            clickElement(getQueryokbutton(), "Query button", driver);
            Thread.sleep(1000);
            System.out.println(dictionary.get("SIM_NO").substring(0, (dictionary.get("SIM_NO").length()-2)));
            sendKeys(getFSPICCIDnumber(principalpge), dictionary.get("SIM_NO").substring(0, (dictionary.get("SIM_NO").length()-2)), "SIM Number", "", driver);
            Thread.sleep(1000);
            clickElement(getFSPICCIDtxt(principalpge), "Query button", driver);
            }
        }
    }
