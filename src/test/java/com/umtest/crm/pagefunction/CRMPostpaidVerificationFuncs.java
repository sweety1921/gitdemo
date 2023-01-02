package com.umtest.crm.pagefunction;

import com.umtest.crm.pageobject.CRMCustomer360Page;
import com.umtest.crm.pageobject.CRMPostpaidVerificationPage;
import com.umtest.selfcare.pagefunction.SelfcareCreditShareFuncs;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.umb.pagefunction.UMBLoginLogoutFuncs;
import com.umtest.umb.pagefunction.UMBUSSDFuncs;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;

import static com.umtest.testframe.lib.JSONReader.parseJSONFile;

public class CRMPostpaidVerificationFuncs extends CRMPostpaidVerificationPage {
    private static Logger logger = LogManager.getLogger(CRMPostpaidVerificationFuncs.class);
    private RemoteWebDriver driver;

    public CRMPostpaidVerificationFuncs(RemoteWebDriver driver) {
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

    public static synchronized void verifyCustomerAndServiceDetailsZSmart(String sTestDataFile, RemoteWebDriver driver) throws Exception {

        JSONObject joBundle;
        JSONArray jaServiceCategories;
        JSONObject joServiceCategory;
        JSONArray jaServices;
        String sServiceCategoryType;
        JSONObject joServiceItems;
        String jsonServiceName;
        String jsonServiceQuota;
        String jsonServiceUsage;
        String jsonServiceValidity;

        Thread.sleep(3000);
        clickElement(getCustomerInformationCard(), "Customer Information", driver);

        MainUtil.compareInString(getCustomerName().getText(), dictionary.get("CUSTOMER_NAME"), "***CUSTOMER NAME", driver);
        MainUtil.compareInString(getIdType().getText().toUpperCase(), dictionary.get("CUSTOMER_IDENTIFICATION_TYPE"), "***ID TYPE", driver);
        MainUtil.compareInString(getIdNumber().getText(), dictionary.get("CUSTOMER_ID"), "***CUSTOMER ID", driver);
        MainUtil.compareInString(getBirthDay().getText(), (dictionary.get("CUSTOMER_DOB_YYYY")+"-"+dictionary.get("CUSTOMER_DOB_MM")
                +"-"+dictionary.get("CUSTOMER_DOB_DD")), "***CUSTOMER DOB", driver);
        MainUtil.compareInString(getAddress().getText().toUpperCase(), (dictionary.get("CUSTOMER_ADDRESS").toUpperCase()
                +" "+dictionary.get("CUSTOMER_POSTCODE")+" "+dictionary.get("CUSTOMER_CITY")+","+
                dictionary.get("CUSTOMER_STATE")+" MALAYSIA"), "***CUSTOMER ADDRESS", driver);

        clickElement(getAccountInformationCard(), "Account Information", driver);
        scrollToBottomOfTheWebPage(driver);
        joBundle = parseJSONFile("src/test/resources/testData/"+sTestDataFile);
        jaServiceCategories = (JSONArray) joBundle.get("serviceCategory");
        for (int iVar1 = 0; iVar1 < jaServiceCategories.size(); iVar1++) {
            joServiceCategory = (JSONObject) jaServiceCategories.get(iVar1);
                jaServices = (JSONArray) joServiceCategory.get("services");

                for (int iVar2 = 0; iVar2 < jaServices.size(); iVar2++) {
                    joServiceItems = (JSONObject) jaServices.get(iVar2);
                    jsonServiceName = joServiceItems.get("serviceName").toString();

                    if (isServiceCardExists(jsonServiceName)) {
                        jsonServiceQuota = joServiceItems.get("quota").toString();
                        jsonServiceUsage = joServiceItems.get("usage").toString();
                        jsonServiceValidity = joServiceItems.get("validity").toString();

                        MainUtil.captureAppScreenshot("Capture Screen - Service : "+jsonServiceName, driver);
                        MainUtil.compareInString(getServiceNameText(jsonServiceName),jsonServiceName,"***SERVICE NAME", driver);

                        if(jsonServiceQuota.equalsIgnoreCase("Unlimited")) {
                            if (jsonServiceUsage.contains("GB") || jsonServiceUsage.contains("MB")) {
                                jsonServiceUsage = jsonServiceUsage.replaceAll("My Usage ", "");
                            } else if (jsonServiceName.contains("Calls") || jsonServiceName.contains("Voice") || jsonServiceName.contains("Call")) {
                                jsonServiceUsage = jsonServiceUsage.replaceAll("NA", "0 Second");
                            }
                            MainUtil.compareInString(getServiceQuota(jsonServiceName, jsonServiceQuota), jsonServiceQuota, "***SERVICE QUOTA", driver);
                            MainUtil.compareInString(getServiceUsage(jsonServiceName, jsonServiceUsage), jsonServiceUsage, "***SERVICE Usage", driver);
                            if(!jsonServiceValidity.contains("NA")) {
                                MainUtil.compareInString(getServiceValidity(jsonServiceName, jsonServiceUsage), jsonServiceValidity, "***SERVICE Validity", driver);
                            }
                        }
                        else if(jsonServiceQuota.contains("left")){
                            if(jsonServiceName.contains("Member")){
                                String quota = "30 Minute";
                                MainUtil.compareInString(getServiceQuota(jsonServiceName,quota),jsonServiceQuota,"***SERVICE QUOTA", driver);

                                String[] temp = jsonServiceUsage.split("/");
                                String consumeText = getServiceUsage(jsonServiceName,temp[0].trim()+" Second");
                                String[] remaining = temp[1].split("\\s+");
                                String remainingText = "30 Minute";
                                String remainingUsageUI = getServiceUsageRemaining(jsonServiceName,remainingText);
                                String usageFramedTextUI = consumeText+"/"+remainingUsageUI;
                                MainUtil.compareInString(usageFramedTextUI,jsonServiceUsage,"***SERVICE Usage", driver);

                            } else {
                                String[] serviceQuota = jsonServiceQuota.split("\\s+");
                                String quota = serviceQuota[0] + " " + serviceQuota[1];
                                MainUtil.compareInString(getServiceQuota(jsonServiceName, quota) + " left", jsonServiceQuota, "***SERVICE QUOTA", driver);
                                String[] temp = jsonServiceUsage.split("/");
                                String consumeText = getServiceUsage(jsonServiceName, temp[0].trim() + " MB").replaceAll(" MB", "");
                                String[] remaining = temp[1].split("\\s+");
                                String remainingText = remaining[0].trim() + " " + remaining[1].trim();
                                String remainingUsageUI = getServiceUsageRemaining(jsonServiceName, remainingText);
                                String usageFramedTextUI = consumeText + "/" + remainingUsageUI + " Used";
                                MainUtil.compareInString(usageFramedTextUI, jsonServiceUsage, "***SERVICE Usage", driver);
                                if (!jsonServiceValidity.contains("NA")) {
                                    MainUtil.compareInString(getServiceValidity(jsonServiceName, temp[0].trim() + " MB"), jsonServiceValidity, "***SERVICE Validity", driver);
                                }
                            }
                        }
                    }else{
                        MainUtil.compareInString("Service NOT DISPLAYED",jsonServiceName, "Service" + jsonServiceName, driver);
                    }
                }
        }
    }

    public void SMSVerification(String planname, String category,RemoteWebDriver driver) throws Exception {
        Thread.sleep(3000);
        clickElement(getinteractionhistory(), "interaction History", driver);
        ApplicationUtil.getSMSDetails(planname,category);
        System.out.println(dictionary.get("SMS"));
        if(category.contains("Welcome")) {
            clickElement(getWelcomesms(), "Welcome SMS", driver);
            MainUtil.compareInString(dictionary.get("SMS"), getWelcomemsgincrm().getText(), "***Welcome SMS Validity", driver);
        }else{
            clickElement(getSubscribedsms(), "Subscribed SMS", driver);
            MainUtil.compareInString(dictionary.get("SMS"), getSubscribedsmsincrm().getText(), "***Subscribed SMS Validity", driver);
        }

    }

    public void FSPSMSVerification(String planname, String category,RemoteWebDriver driver) throws Exception {
        Thread.sleep(3000);
        clickElement(getinteractionhistory(), "interaction History", driver);
        ApplicationUtil.getSMSDetails(planname,category);
        System.out.println(dictionary.get("SMS"));
        if(category.contains("Welcome")) {
            clickElement(getFSPWelcomesms(dictionary.get("MSISDN")), "Welcome SMS", driver);
            MainUtil.compareInString(dictionary.get("SMS"), getWelcomemsgincrm().getText(), "***Welcome SMS Validity", driver);
        }

    }

    public void BundleSMSVerification(String planname,RemoteWebDriver driver) throws Exception {
        Thread.sleep(3000);
        clickElement(getinteractionhistory(), "interaction History", driver);
        ApplicationUtil.getbundleSMSDetails(planname);
        System.out.println(dictionary.get("SMS"));

        if(planname.contains("Plus")) {
            List<WebElement> childs = driver.findElementsByXPath("//td[@title='Subscribed Talk Plus / SMS Plus / U Talk Plus / U SMS Plus offer']");
            for(WebElement element:childs)
            {
                element.click();
                if (getWelcomemsgincrm().getText().contains(planname)) {
                    MainUtil.compareInString(dictionary.get("SMS"), getWelcomemsgincrm().getText(), "***Welcome SMS Validity", driver);
                    break;
                }
            }
        }else if(planname.contains("Data Booster 10GB")){
            clickElement(getBooster10GB(), "Subscribed SMS", driver);
            MainUtil.compareInString(dictionary.get("SMS"), getWelcomemsgincrm().getText(), "***Subscribed SMS Validity", driver);
        }else if(planname.contains("Hotspot Booster 5GB")) {
            clickElement(getHotspotBooster(), "Subscribed SMS", driver);
            MainUtil.compareInString(dictionary.get("SMS"), getWelcomemsgincrm().getText(), "***Subscribed SMS Validity", driver);
        }
    }
}
