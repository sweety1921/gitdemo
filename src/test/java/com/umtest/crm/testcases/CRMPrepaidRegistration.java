package com.umtest.crm.testcases;
import com.umtest.crm.pagefunction.CRMLoginLogoutFuncs;
import com.umtest.crm.pagefunction.CRMRegistrationPrepaidFuncs;
import com.umtest.crm.pagefunction.CRMSearchCustomerFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppUtilFuncs;
import com.umtest.selfcare.pagefunction.SelfcareVerificationFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.listener.ExtentTestNGITestListener;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;
import com.umtest.umrex.pagefunction.UMREXLoginLogoutFuncs;
import com.umtest.umrex.pagefunction.UMREXPrepaidRegistrationFuncs;
import com.umtest.umrex.testcases.UMREXPrepaidRegistration;
import com.umtest.umrexportal.pagefunction.UMREXPortalFuncs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.reporters.jq.Main;

import java.io.IOException;

public class CRMPrepaidRegistration extends DriverFactory {

    private RemoteWebDriver driverZsmart;
    private RemoteWebDriver driverCRM;
    private RemoteWebDriver driverMobileApp;
    private RemoteWebDriver driverSelfcare;
    private RemoteWebDriver driverUMB;
    private RemoteWebDriver driverUMREXPORTAL;

    CRMSearchCustomerFuncs zsmartFuncs;
    CRMLoginLogoutFuncs crmLoginLogOutFuncs;
    UMBVerificationFuncs umbFuncs;
    SelfcareVerificationFuncs selfcareFuncs;
    MobileAppUtilFuncs mobileAppUtilFuncs;
    ExtentTestNGITestListener ex;
    CRMSearchCustomerFuncs crmSearchCustomerFuncs;
    CRMRegistrationPrepaidFuncs crmRegistrationPrepaidFuncs;
    private static Logger logger = LogManager.getLogger(UMREXPrepaidRegistration.class);

    @BeforeClass
    public void initialiseObj() throws IOException {
        MainUtil.APPLICATION_NAME = "UMREX";
    }

    @Test(description = "ZSMART Prepaid Registration")
    @Parameters({"planName","testDataFile","custIdentificationType","cycleEndDate"})
    public void ZSMARTRegistrationPrepaid(String planName, String testDataFile, String custIdentificationType, String cycleEndDate) {
        try {
            //#################################################################################################################################

            MainUtil.dictionary.put("PLAN_NAME", planName);
            MainUtil.dictionary.put("REGISTRATION_TYPE", "PLAN");
            MainUtil.dictionary.put("CUSTOMER_IDENTIFICATION_TYPE", custIdentificationType);
            MainUtil.dictionary.put("TEST_DATA_FILE",testDataFile);
            MainUtil.dictionary.put("CYCLE_ENDDATE",cycleEndDate);

            //#################################################################################################################################
            /* ZSmart Prepaid Registration*/
            ExtentTestNGITestListener.createNode("ZSMART Prepaid Registration");

            driverZsmart = getDriver("driverZSMART");
            crmLoginLogOutFuncs = new CRMLoginLogoutFuncs(driverZsmart);
            zsmartFuncs = new CRMSearchCustomerFuncs(driverZsmart);
            crmLoginLogOutFuncs.loginWCTCRM();
            zsmartFuncs.personalCustomerRegistration(custIdentificationType);
            boolean statusFlag = zsmartFuncs.starterPackActivationOrder(MainUtil.dictionary.get("REGISTRATION_TYPE"));

//            boolean statusFlag = true;
//            MainUtil.dictionary.put("MSISDN","601139277199");

            if (statusFlag == true) {
                Thread.sleep(30000); // Mandatory wait time for Registration Details to update in UMREX PORTAL

                ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
//                MainUtil.dictionary.put("CYCLE_END_DATE", "01/08/2022");

                ExtentTestNGITestListener.createNode("ZSmart Verfication");
                driverCRM = getDriver("CRM");
                crmLoginLogOutFuncs = new CRMLoginLogoutFuncs(driverCRM);
                crmSearchCustomerFuncs = new CRMSearchCustomerFuncs(driverCRM);
                crmRegistrationPrepaidFuncs = new CRMRegistrationPrepaidFuncs(driverCRM);
                crmLoginLogOutFuncs.loginWCTCRM();
                String msisdn = MainUtil.dictionary.get("MSISDN");
                crmSearchCustomerFuncs.searchMSISDNAndNavigateToThreeSixtyDegreeView(msisdn,driverCRM);
                crmRegistrationPrepaidFuncs.verifyCustomerAndServiceDetailsZSmart(testDataFile, driverCRM);
                crmRegistrationPrepaidFuncs.SMSVerification(MainUtil.dictionary.get("PLAN_NAME"),"Subscribed",driverCRM);
                crmRegistrationPrepaidFuncs.SMSVerification(MainUtil.dictionary.get("PLAN_NAME"),"Activation",driverCRM);

                /*Verify UMB*/
                ExtentTestNGITestListener.createNode("UMB Verification");

                driverUMB = getDriver("UMB");
                umbFuncs = new UMBVerificationFuncs(driverUMB);
                umbFuncs.umbVerification("PREPAID", "BUNDLE");

                /*Verify Selfcare*/
                ExtentTestNGITestListener.createNode("Selfcare Verification");

                driverSelfcare = getDriver("SELFCARE");
                selfcareFuncs = new SelfcareVerificationFuncs(driverSelfcare);
                selfcareFuncs.verifySelfcare("PREPAID");

                /*Verify Mobile App*/
                ExtentTestNGITestListener.createNode("Mobile App Verification");

                driverMobileApp = getDriver("MOBILEAPP", "Android");
                mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
                mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TEST_DATA_FILE"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while doing ZSmart prepaid registration  :" + e);
        }
    }

    @Test(description = "ZSMART Loose Sim Prepaid Registration")
    @Parameters({"planName","testDataFile","custIdentificationType","accountType","amount","simType","cycleEndDate"})
    public void ZSMARTLooseSimRegistrationPrepaid(String planName, String testDataFile, String custIdentificationType, String accountType, String amount, String simType, String cycleEndDate) {
        try {
            //#################################################################################################################################

            MainUtil.dictionary.put("PLAN_NAME", planName);
            MainUtil.dictionary.put("REGISTRATION_TYPE", "PLAN");
            MainUtil.dictionary.put("CUSTOMER_IDENTIFICATION_TYPE", custIdentificationType);
            MainUtil.dictionary.put("TEST_DATA_FILE",testDataFile);
            MainUtil.dictionary.put("ACCOUNT_TYPE",accountType);
            MainUtil.dictionary.put("SIM_TYPE",simType);
            MainUtil.dictionary.put("Amount",amount);
            MainUtil.dictionary.put("CYCLE_ENDDATE",cycleEndDate);

            //#################################################################################################################################
            /* ZSmart Prepaid Registration*/
            ExtentTestNGITestListener.createNode("ZSMART Prepaid Loose Sim Registration");

            driverZsmart = getDriver("driverZSMART");
            crmLoginLogOutFuncs = new CRMLoginLogoutFuncs(driverZsmart);
            zsmartFuncs = new CRMSearchCustomerFuncs(driverZsmart);
            crmLoginLogOutFuncs.loginWCTCRM();
//            zsmartFuncs.searchMSISDNAndNavigateToThreeSixtyDegreeView("TEST AUTO TQWQUQ",driverZsmart);
//            zsmartFuncs.personalCustomerRegistration(custIdentificationType);
            boolean statusFlag = zsmartFuncs.doCRMLooseSimPrepaidRegistration(MainUtil.dictionary.get("REGISTRATION_TYPE"),MainUtil.dictionary.get("CUSTOMER_IDENTIFICATION_TYPE"));

//            boolean statusFlag = true;
//            MainUtil.dictionary.put("MSISDN","60159806924");

            if (statusFlag == true) {
                Thread.sleep(30000); // Mandatory wait time for Registration Details to update in UMREX PORTAL

                ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
//                MainUtil.dictionary.put("CYCLE_END_DATE", "13/08/2022");

                ExtentTestNGITestListener.createNode("ZSmart Verfication");
                driverCRM = getDriver("CRM");
                crmLoginLogOutFuncs = new CRMLoginLogoutFuncs(driverCRM);
                crmSearchCustomerFuncs = new CRMSearchCustomerFuncs(driverCRM);
                crmRegistrationPrepaidFuncs = new CRMRegistrationPrepaidFuncs(driverCRM);
                crmLoginLogOutFuncs.loginWCTCRM();
                String msisdn = MainUtil.dictionary.get("MSISDN");
                crmSearchCustomerFuncs.searchMSISDNAndNavigateToThreeSixtyDegreeView(msisdn,driverCRM);
                crmRegistrationPrepaidFuncs.verifyCustomerAndServiceDetailsZSmart(testDataFile, driverCRM);
                crmRegistrationPrepaidFuncs.SMSVerification(MainUtil.dictionary.get("PLAN_NAME"),"Subscribed",driverCRM);
                crmRegistrationPrepaidFuncs.SMSVerification(MainUtil.dictionary.get("PLAN_NAME"),"New Connection",driverCRM);

                /*Verify UMB*/
                ExtentTestNGITestListener.createNode("UMB Verification");

                driverUMB = getDriver("UMB");
                umbFuncs = new UMBVerificationFuncs(driverUMB);
                umbFuncs.umbVerification("PREPAID", "BUNDLE");

                /*Verify Selfcare*/
                ExtentTestNGITestListener.createNode("Selfcare Verification");

                driverSelfcare = getDriver("SELFCARE");
                selfcareFuncs = new SelfcareVerificationFuncs(driverSelfcare);
                selfcareFuncs.verifySelfcare("PREPAID");

                /*Verify Mobile App*/
                ExtentTestNGITestListener.createNode("Mobile App Verification");

                driverMobileApp = getDriver("MOBILEAPP", "Android");
                mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
                mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TEST_DATA_FILE"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while doing ZSmart prepaid loose sim registration  :" + e);
        }
    }
}
