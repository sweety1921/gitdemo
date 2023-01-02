package com.umtest.crm.testcases;

import com.umtest.crm.pagefunction.CRMBundlePurchaseFuncs;
import com.umtest.crm.pagefunction.CRMLoginLogoutFuncs;
import com.umtest.crm.pagefunction.CRMPostpaidRegistrationFuncs;
import com.umtest.crm.pagefunction.CRMPostpaidVerificationFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppLoginFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppUtilFuncs;
import com.umtest.selfcare.pagefunction.SelfcareVerificationFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.lib.PropertyHelper;
import com.umtest.testframe.listener.ExtentTestNGITestListener;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;
import com.umtest.umrex.pagefunction.UMREXLoginLogoutFuncs;
import com.umtest.umrex.pagefunction.UMREXPostpaidRegistrationFuncs;
import com.umtest.umrex.testcases.UMREXPostpaidRegistration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class CRMBundlePurchase extends DriverFactory {
        private RemoteWebDriver driver;
        private RemoteWebDriver driverChrome;
        private RemoteWebDriver driverPrepaidChrome;

        private RemoteWebDriver driverZsmart;
        private RemoteWebDriver driverMobileApp;
        private RemoteWebDriver driverMobileAppPrepaid;
        private RemoteWebDriver driverSelfcare;
        private RemoteWebDriver driverUMB;
        private RemoteWebDriver driverPrepaidUMB;
        private RemoteWebDriver driverUMREXPORTAL;
        private RemoteWebDriver driverPrepaidZsmart;

        CRMLoginLogoutFuncs loginFuncs;
        CRMLoginLogoutFuncs loginFuncsPrepaid;
        CRMBundlePurchaseFuncs postpaidFuncs;
        CRMPostpaidVerificationFuncs crmverificationFunc;
        UMBVerificationFuncs umbFuncs;
        UMBVerificationFuncs umbFuncsPrepaid;
        SelfcareVerificationFuncs selfcareFuncs;
        MobileAppLoginFuncs mobileApploginFuncs;
        MobileAppUtilFuncs mobileAppUtilFuncs;
        MobileAppUtilFuncs mobileAppUtilFuncsPrepaid;
        CRMBundlePurchaseFuncs prepaidCrmBundlePurchase;

        ExtentTestNGITestListener ex;

        private static Logger logger = LogManager.getLogger(com.umtest.crm.testcases.CRMBundlePurchase.class);

        @BeforeClass
        public void initialiseObj() throws IOException {

		/*driver = getDriver("UMREX", "Android");
		loginFuncs = new UMREXLoginLogoutFuncs(driver);
		postpaidFuncs = new UMREXPostpaidRegistrationFuncs(driver);*/

            MainUtil.APPLICATION_NAME = "CRM";
        }

        @Test(description = "CRM Bundle Purchase")
        @Parameters({"msisdn", "bundleName", "accountType", "testDataFile"})
        public void ZsmartBundlepurchase(String msisdn, String bundleName, String accountType, String testDataFile) {

            try {

			//postpaidFuncs = new UMREXPostpaidRegistrationFuncs(driver);

                ExtentTestNGITestListener.createNode("CRM Bundle Purchase");
                MainUtil.dictionary.put("MSISDN", msisdn);
                MainUtil.dictionary.put("BUNDLE_NAME", bundleName);
                MainUtil.dictionary.put("ACCOUNT_TYPE", accountType);
                MainUtil.dictionary.put("TEST_DATA_FILE", testDataFile);

                //*Launch CRM*//*
                driverZsmart = getDriver("CRM");
                loginFuncs = new CRMLoginLogoutFuncs(driverZsmart);
                loginFuncs.loginWCTCRM();

                //*Perform Bundle Purchase*//*
                postpaidFuncs = new CRMBundlePurchaseFuncs(driverZsmart);
                postpaidFuncs.searchMSISDNAndNavigateToThreeSixtyDegreeView(MainUtil.dictionary.get("MSISDN"),driverZsmart);
                boolean statusFlag = postpaidFuncs.doBundlePurchase(MainUtil.dictionary.get("BUNDLE_NAME"));
                //loginFuncs.logoutCRM();
                DriverFactory.quitCRM(driverZsmart);
                Thread.sleep(10000);

                if (statusFlag == true) {
                    ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
                    driverChrome = getDriver("CRM1");
                    ExtentTestNGITestListener.createNode("CRM Verification");
                   // driverZsmart = getDriver("CRM");
                    loginFuncs = new CRMLoginLogoutFuncs(driverChrome);
                    crmverificationFunc=new CRMPostpaidVerificationFuncs(driverChrome);
                    loginFuncs.loginWCTCRM();
                    crmverificationFunc.searchMSISDNAndNavigateToThreeSixtyDegreeView(msisdn,driverChrome);
                    crmverificationFunc.verifyCustomerAndServiceDetailsZSmart(MainUtil.dictionary.get("TEST_DATA_FILE"), driverChrome);
                    crmverificationFunc.BundleSMSVerification(MainUtil.dictionary.get("BUNDLE_NAME"),driverChrome);
                    DriverFactory.quitCRM(driverChrome);

                    if (statusFlag && PropertyHelper.getProperties("UMB_CHANNEL_VERIFICATION_FLAG").equals("true")) {
                        ExtentTestNGITestListener.createNode("UMB Verification");

                        driverUMB = getDriver("UMB");
                        umbFuncs = new UMBVerificationFuncs(driverUMB);
                        umbFuncs.umbVerification(MainUtil.dictionary.get("ACCOUNT_TYPE"), "BUNDLE");
                    }

                       ExtentTestNGITestListener.createNode("Mobile App Verification");

                        driverMobileApp = getDriver("MOBILEAPP", "Android");
                        mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
                        mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TEST_DATA_FILE"));
                }


            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Exception occured while doing bundle Purchase  :" + e);
            }
        }

    @Test(description = "CRM Bundle Purchase Prepaid")
    @Parameters({"msisdn", "bundleName", "accountType", "testDataFile"})
    public void ZsmartBundlepurchasePrepaid(String msisdn, String bundleName, String accountType, String testDataFile) {

        try {

            ExtentTestNGITestListener.createNode("CRM Bundle Purchase Prepaid");
            MainUtil.dictionary.put("MSISDN", msisdn);
            MainUtil.dictionary.put("BUNDLE_NAME", bundleName);
            MainUtil.dictionary.put("ACCOUNT_TYPE", accountType);
            MainUtil.dictionary.put("TEST_DATA_FILE", testDataFile);

            //*Launch CRM*//*
            driverPrepaidZsmart = getDriver("CRMPrepaid");
            loginFuncsPrepaid = new CRMLoginLogoutFuncs(driverPrepaidZsmart);
            loginFuncsPrepaid.loginWCTCRM();

            //*Perform Bundle Purchase*//*
            prepaidCrmBundlePurchase = new CRMBundlePurchaseFuncs(driverPrepaidZsmart);
            prepaidCrmBundlePurchase.searchMSISDNAndNavigateToThreeSixtyDegreeView(MainUtil.dictionary.get("MSISDN"),driverPrepaidZsmart);
            boolean statusFlag = prepaidCrmBundlePurchase.doPrepaidBundlePurchase(MainUtil.dictionary.get("BUNDLE_NAME"));
            //loginFuncs.logoutCRM();
            DriverFactory.quitCRM(driverPrepaidZsmart);
            Thread.sleep(10000);

            if (statusFlag == true) {
                ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));
                driverPrepaidChrome = getDriver("CRMPrepaid1");
                ExtentTestNGITestListener.createNode("Prepaid CRM Verification");
                // driverZsmart = getDriver("CRM");
                loginFuncsPrepaid = new CRMLoginLogoutFuncs(driverPrepaidChrome);
                crmverificationFunc=new CRMPostpaidVerificationFuncs(driverPrepaidChrome);
                loginFuncsPrepaid.loginWCTCRM();
                crmverificationFunc.searchMSISDNAndNavigateToThreeSixtyDegreeView(msisdn,driverPrepaidChrome);
                crmverificationFunc.verifyCustomerAndServiceDetailsZSmart(MainUtil.dictionary.get("TEST_DATA_FILE"), driverPrepaidChrome);
                crmverificationFunc.BundleSMSVerification(MainUtil.dictionary.get("BUNDLE_NAME"),driverPrepaidChrome);
                DriverFactory.quitCRM(driverPrepaidChrome);

                if (statusFlag && PropertyHelper.getProperties("UMB_CHANNEL_VERIFICATION_FLAG").equals("true")) {
                    ExtentTestNGITestListener.createNode("UMB Verification");

                    driverPrepaidUMB = getDriver("UMBPrepaid");
                    umbFuncsPrepaid = new UMBVerificationFuncs(driverPrepaidUMB);
                    umbFuncsPrepaid.umbVerification(MainUtil.dictionary.get("ACCOUNT_TYPE"), "BUNDLE");
                }

                ExtentTestNGITestListener.createNode("Mobile App Verification");

                driverMobileAppPrepaid = getDriver("MOBILEAPPPrepaid", "Android");
                mobileAppUtilFuncsPrepaid = new MobileAppUtilFuncs(driverMobileAppPrepaid);
                mobileAppUtilFuncsPrepaid.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TEST_DATA_FILE"));
            }


        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while doing bundle Purchase  :" + e);
        }
    }



}
