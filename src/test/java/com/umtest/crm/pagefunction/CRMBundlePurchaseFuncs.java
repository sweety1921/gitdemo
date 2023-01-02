package com.umtest.crm.pagefunction;

import com.umtest.crm.pageobject.CRMBundlePurchasePage;
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
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.sql.SQLException;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import static com.umtest.testframe.lib.JSONReader.parseJSONFile;

public class CRMBundlePurchaseFuncs extends CRMBundlePurchasePage {
    private static Logger logger = LogManager.getLogger(CRMPostpaidVerificationFuncs.class);
    private RemoteWebDriver driver;

    public CRMBundlePurchaseFuncs(RemoteWebDriver driver) {
        super(driver);
        this.driver =  driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);
    }

    public boolean doBundlePurchase(String bundleName) throws SQLException, ClassNotFoundException {
        boolean returnStatus = false;

        try {

            Thread.sleep(2000);

            javaScriptClickElement(getBtnorderentry(),"Order Entry",driver);
            Thread.sleep(2000);

            clickElement(getOperationbutton(), "Operation Button", driver);

            clickElement(getModifyofferbutton(), "Modify Offer Button", driver);

            Thread.sleep(3000);

            clickElement(getAddbundlebutton(), "Add Button", driver);


            List<WebElement> childs = driver.findElementsByXPath("//span[@class='js-vas-name-container']//following-sibling::span");


            for (Iterator<WebElement> iterator = childs.iterator(); iterator.hasNext();) {

                Actions action = new Actions(driver);
                action.sendKeys(Keys.ARROW_DOWN).build().perform();

                WebElement webElement = (WebElement) iterator.next();
                String value = webElement.getAttribute("title");
                System.out.println("Row value:"+value);
                if (value.contains(bundleName)) {
                    clickElement(getbundleselect(bundleName), "Select Bundle", driver);
                    break;
                }
                driver.findElementByXPath("//span[@class='js-vas-name-container']//following-sibling::span[@title='"+value+"']").click();

                action.sendKeys(Keys.ARROW_DOWN).build().perform();

            }

            clickElement(getOKbundlebutton(), "Add Button", driver);

            clickElement(getNextbutton(), "Next button", driver);

            clickElement(getcustomercheckbox(), "Checkbox button", driver);

            clickElement(getcustomernextbtn(), "Next button", driver);
            Thread.sleep(2000);

            getbypasscheckbox().sendKeys(Keys.RETURN);

            clickElement(getbypasscheckbox(), "Bypass Checkbox", driver);

            clickElement(getacknextbutton(), "Next button", driver);

            clickElement(getpaybutton(), "Pay button", driver);

            Thread.sleep(2000);
            clickElement(getconfirmNextbutton(), "Next button", driver);

            boolean checkFlag = checkForText("", getlabelorderSuccessMessage(), "Submit Successfully！", "Success Message", driver);
            if (checkFlag == true) {
                clickElement(getConfirmbutton(), "Confirm button", driver);
                returnStatus = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while doing Bundle Purchase" + e);
        }
        return returnStatus;
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

    public boolean doPrepaidBundlePurchase(String bundleName){
        boolean returnStatus = false;
        try {
            Thread.sleep(2000);
            javaScriptClickElement(getBtnorderentry(),"Order Entry",driver);
            Thread.sleep(2000);
            clickElement(getOperationbutton(), "Operation Button", driver);
            clickElement(getModifyofferbutton(), "Modify Offer Button", driver);
            Thread.sleep(3000);
            clickElement(getAddbundlebutton(), "Add Button", driver);
            List<WebElement> childs = driver.findElementsByXPath("//span[@class='js-vas-name-container']//following-sibling::span");

            for (Iterator<WebElement> iterator = childs.iterator(); iterator.hasNext();) {
                Actions action = new Actions(driver);
                action.sendKeys(Keys.ARROW_DOWN).build().perform();
                WebElement webElement = (WebElement) iterator.next();
                String value = webElement.getAttribute("title");
                System.out.println("Row value:"+value);
                if (value.contains(bundleName)) {
                    clickElement(getbundleselect(bundleName), "Select Bundle", driver);
                    break;
                }
                driver.findElementByXPath("//span[@class='js-vas-name-container']//following-sibling::span[@title='"+value+"']").click();
                action.sendKeys(Keys.ARROW_DOWN).build().perform();
            }

            clickElement(getOKbundlebutton(), "Ok Button", driver);
            clickElement(getNextbutton(), "Next button", driver);
            clickElement(getcustomercheckbox(), "Checkbox button", driver);
            clickElement(getcustomernextbtn(), "Next button", driver);
            Thread.sleep(2000);
            getbypasscheckbox().sendKeys(Keys.RETURN);
            clickElement(getbypasscheckbox(), "Bypass Checkbox", driver);
            clickElement(getacknextbutton(), "Next button", driver);
            clickElement(getpaybutton(), "Pay button", driver);
            Thread.sleep(2000);
            clickElement(getconfirmNextbutton(), "Next button", driver);
            boolean checkFlag = checkForText("", getlabelorderSuccessMessage(), "Submit Successfully！", "Success Message", driver);
            if (checkFlag == true) {
                clickElement(getConfirmbutton(), "Confirm button", driver);
                returnStatus = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while doing Bundle Purchase" + e);
        }
        return returnStatus;
    }


}
