package com.umtest.crm.pageobject;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CRMPostpaidVerificationPage extends MainUtil {
    private static RemoteWebDriver driver;

    public CRMPostpaidVerificationPage(RemoteWebDriver driver) {
        this.driver = driver;
    }

    @FindBy(xpath = "//img[@src='cvbs/modules/cust/styles/img/customer360.svg']")
    private WebElement customerThreeSixtyTitle;

    @FindBy(xpath = "//div[@class='cust-main-content row']//label[@title='MyKad']")
    private WebElement idTypeValue;

    @FindBy(xpath = "//div[@class='cust-main-content row']//label[@title='ID Number']/parent::span/following-sibling::span/label")
    private WebElement idNumberValue;

    @FindBy(xpath = "//td[@title='5G Prepaid PlanAgreement: No Agreement']//div[@class='top-name']")
    private WebElement planName;

    @FindBy(xpath = "//div[@class='sub-num']//a")
    private WebElement subscriberMSISDN;

    @FindBy(xpath = "//div[@class='content-box account-bal-box comprivroot']")
    private static List<WebElement> balanceAndFreeBees;

    @FindBy(xpath = "//span[contains(text(),'Balances')]")
    private static WebElement balancesTab;

    @FindBy(xpath = "//li[@title='Account Information']")
    public static WebElement accountInformationCard;

    @FindBy(xpath = "//li[@title='Customer Information']")
    public static WebElement customerInformationCard;

    @FindBy(xpath = "//label[contains(text(),'Customer Name')]/../div/span")
    public static WebElement customerName;

    @FindBy(xpath = "//label[contains(text(),'ID Type')]/../div/span")
    public static WebElement idType;

    @FindBy(xpath = "//label[contains(text(),'ID Number')]/../div/span")
    public static WebElement idNumber;

    @FindBy(xpath = "//label[contains(text(),'Birthday')]/../div/span")
    public static WebElement birthDay;

    @FindBy(xpath = "//label[contains(text(),'Address')]/../div/span")
    public static WebElement address;

    @FindBy(xpath = "//input[@placeholder='Customer Name / ID Number / Service Number / Old BRN']")
    private WebElement fuzzySearchTextField;

    @FindBy(xpath = "//img[@class='icon-big-font']")
    private WebElement fuzzySearchMagnifyingGlass;

    @FindBy(xpath = "//button[text()='OK']")
    private WebElement selectCustomerPopUpOkBtn;

    @FindBy(xpath = "//span[@class='js-view-360-text']")
    private WebElement threeSixtydegreeViewHyperLink;

    @FindBy(xpath = "//li[@title='Interaction History']")
    public static WebElement interactionhistory;

    @FindBy(xpath = "//td[contains(text(),'Welcome')]")
    public static WebElement Welcomesms;

    @FindBy(xpath = "//td[contains(text(),'Subscribed')]")
    public static WebElement Subscribedsms;

    @FindBy(xpath = "//div[@class='input-group js-msg-detail']//following-sibling::div[@name='msgText']")
    public static WebElement Welcomemsgincrm;

    @FindBy(xpath = "//div[contains(text(),'RM0. Giler rewards await you! Manage your account ')]")
    public static WebElement Subscribedsmsincrm;

    @FindBy(xpath = "//td[contains(text(),'Postpaid Booster offer')]")
    private WebElement Booster10GB;

    @FindBy(xpath = "//td[contains(text(),'Postpaid Hotspot Booster offer')]")
    private WebElement HotspotBooster;

    public WebElement getSearchCustomerPageTitle() {
        return AppWait.waitForElementForVisibility(driver, customerThreeSixtyTitle);
    }

    public WebElement getIdTypeValue() {
        return AppWait.waitForElementForVisibility(driver, idTypeValue);
    }

    public WebElement getIdNumberValue() {
        return AppWait.waitForElementForVisibility(driver, idNumberValue);
    }

    public WebElement getPlanName() {
        return AppWait.waitForElementForVisibility(driver, planName);
    }

    public WebElement getSubscriberMSISDN() {
        return AppWait.waitForElementForVisibility(driver, subscriberMSISDN);
    }

    public List<WebElement> getBalanceAndFreeBees() {
        return AppWait.waitFluentlyForElements(driver, balanceAndFreeBees);
    }

    public WebElement getBalancesTab() {
        return AppWait.waitForElementForVisibility(driver, balancesTab);
    }

    public static boolean isServiceCardExists(String sServiceName) {
        String sXpathServiceTitle= "//div[@class='content-box account-bal-box comprivroot']//h4[contains(text(),'"+sServiceName+"')]";
        return isElementExists(By.xpath(sXpathServiceTitle),driver);
    }

    public static String getServiceNameText(String jsonServiceName) {
        String servicenameXpath= "//div[@class='content-box account-bal-box comprivroot']//h4[contains(text(),'"+jsonServiceName+"')]";
        return getTextUsingXpath(servicenameXpath,"Service Name",driver);
    }

    public static String getServiceQuota(String jsonServiceName, String jsonServiceQuota) {
        String serviceQuotaXpath= "//div[@class='content-box account-bal-box comprivroot']//h4[contains(text(),'"+jsonServiceName+"')]//ancestor::div[@class='panel-heading']/following-sibling::div[@class='panel-body']//span[contains(text(),'"+jsonServiceQuota+"')]";
        return getTextUsingXpath(serviceQuotaXpath,"Service Quota",driver);
    }

    public static String getServiceUsage(String jsonServiceName, String jsonServiceUsage) {
        String serviceUsageXpath= "//div[@class='content-box account-bal-box comprivroot']//h4[contains(text(),'"+jsonServiceName+"')]//ancestor::div[@class='panel-heading']/following-sibling::div[@class='panel-body']//span[contains(text(),'"+jsonServiceUsage+"')]";
        return getTextUsingXpath(serviceUsageXpath,"Service Usage",driver);
    }

    public static String getServiceValidity(String jsonServiceName, String jsonServiceUsage) throws ParseException {
        String serviceValidityXpath = "//div[@class='content-box account-bal-box comprivroot']//h4[contains(text(),'" + jsonServiceName + "')]//ancestor::div[@class='panel-heading']/following-sibling::div[@class='panel-body']//span[contains(text(),'" + jsonServiceUsage + "')]/parent::div/following-sibling::div[@class='text-date'][1]";
        String serviceValidityFullText = getTextUsingXpath(serviceValidityXpath, "Service Usage", driver).replaceAll("Validity period: ","");
        String[] serviceValidityDates = serviceValidityFullText.split("~");
        String[] ValidityStartdate = serviceValidityDates[0].split("\\s+");
        String startDate = ValidityStartdate[0];
        String[] ValidityEnddate = serviceValidityDates[1].split("\\s+");
        String endDate = ValidityEnddate[1];
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date start = myFormat.parse(startDate);
        Date end = myFormat.parse(endDate);
        long diff = Math.abs(end.getTime() - start.getTime());
        long diffIndays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        System.out.println("Valid For "+ String.valueOf(diffIndays)+" day(s)");
        return "Valid For "+ String.valueOf(diffIndays)+" day(s)";
    }

    public static WebElement getAccountInformationCard() {
        return AppWait.waitForElementForVisibility(driver, accountInformationCard);
    }

    public static String getServiceUsageRemaining(String jsonServiceName, String jsonServiceUsage) {
        String serviceUsageXpath= "//div[@class='content-box account-bal-box comprivroot']//h4[contains(text(),'"+jsonServiceName+"')]//ancestor::div[@class='panel-heading']/following-sibling::div[@class='panel-body']//span[contains(text(),'"+jsonServiceUsage+"')][1]";
        return getTextUsingXpath(serviceUsageXpath,"Service Usage",driver);
    }

    public static WebElement getCustomerInformationCard() {
        return AppWait.waitForElementForVisibility(driver, customerInformationCard);
    }

    public static WebElement getCustomerName() {
        return AppWait.waitForElementForVisibility(driver, customerName);
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

    public WebElement getThreeSixtydegreeViewHyperLink() {
        return AppWait.waitForElementToBeClickable(driver, threeSixtydegreeViewHyperLink);
    }

    public static WebElement getinteractionhistory() {
        return AppWait.waitForElementForVisibility(driver, interactionhistory);
    }

    public static WebElement getWelcomesms() {
        return AppWait.waitForElementForVisibility(driver, Welcomesms);
    }

    public WebElement getFSPWelcomesms(String msisdn) {
        return AppWait.waitForElementToBeClickable(driver, "//td[contains(text(),'Welcome')]//following-sibling::td[contains(@title,'"+msisdn+"')]");
    }

    public static WebElement getSubscribedsms() {
        return AppWait.waitForElementForVisibility(driver, Subscribedsms);
    }

    public static WebElement getWelcomemsgincrm() {
        return AppWait.waitForElementForVisibility(driver, Welcomemsgincrm);
    }

    public static WebElement getSubscribedsmsincrm() {
        return AppWait.waitForElementForVisibility(driver, Subscribedsmsincrm);
    }

    public static WebElement getIdType() {
        return AppWait.waitForElementForVisibility(driver, idType);
    }

    public static WebElement getIdNumber() {
        return AppWait.waitForElementForVisibility(driver, idNumber);
    }

    public static WebElement getBirthDay() {
        return AppWait.waitForElementForVisibility(driver, birthDay);
    }

    public static WebElement getAddress() {
        return AppWait.waitForElementForVisibility(driver, address);
    }

    public WebElement getBooster10GB() {
        return AppWait.waitForElementToBeClickable(driver, Booster10GB);
    }

    public WebElement getHotspotBooster() {
        return AppWait.waitForElementToBeClickable(driver, HotspotBooster);
    }

//    public static String getServiceCallsUsage(String jsonServiceName, String jsonServiceUsage) {
//        String serviceUsageXpath= "//div[@class='content-box account-bal-box comprivroot']//h4[contains(text(),'"+jsonServiceName+"')]//ancestor::div[@class='panel-heading']/following-sibling::div[@class='panel-body']//span[contains(text(),'"+jsonServiceUsage+"')]";
//        return getTextUsingXpath(serviceUsageXpath,"Service Usage",driver);
//    }
}
