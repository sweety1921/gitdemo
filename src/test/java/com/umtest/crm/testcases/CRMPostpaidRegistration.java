package com.umtest.crm.testcases;

import com.umtest.crm.pagefunction.CRMLoginLogoutFuncs;
import com.umtest.crm.pagefunction.CRMPostpaidRegistrationFuncs;
import com.umtest.crm.pagefunction.CRMPostpaidVerificationFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppLoginFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppUtilFuncs;
import com.umtest.selfcare.pagefunction.SelfcareVerificationFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
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

public class CRMPostpaidRegistration extends DriverFactory {
        private RemoteWebDriver driver;
        private RemoteWebDriver driverChrome;

        private RemoteWebDriver driverZsmart;
        private RemoteWebDriver driverMobileApp;
        private RemoteWebDriver driverSelfcare;
        private RemoteWebDriver driverUMB;
        private RemoteWebDriver driverUMREXPORTAL;

        CRMLoginLogoutFuncs loginFuncs;
        CRMPostpaidRegistrationFuncs postpaidFuncs;
        CRMPostpaidVerificationFuncs crmverificationFunc;
        UMBVerificationFuncs umbFuncs;
        SelfcareVerificationFuncs selfcareFuncs;
        MobileAppLoginFuncs mobileApploginFuncs;
        MobileAppUtilFuncs mobileAppUtilFuncs;

        ExtentTestNGITestListener ex;

        private static Logger logger = LogManager.getLogger(com.umtest.crm.testcases.CRMPostpaidRegistration.class);

        @BeforeClass
        public void initialiseObj() throws IOException {

		/*driver = getDriver("UMREX", "Android");
		loginFuncs = new UMREXLoginLogoutFuncs(driver);
		postpaidFuncs = new UMREXPostpaidRegistrationFuncs(driver);*/

            MainUtil.APPLICATION_NAME = "CRM";
        }

        @Test(description = "CRM Postpaid Registration")
        @Parameters({"planName", "accountType", "Amount","testDataFile"})
        public void ZsmartRegistrationPostpaid(String planName, String accountType, String Amount, String testDataFile) {

            try {

			//postpaidFuncs = new UMREXPostpaidRegistrationFuncs(driver);

                ExtentTestNGITestListener.createNode("CRM Postpaid Registration");
                MainUtil.dictionary.put("PLAN_NAME", planName);
                MainUtil.dictionary.put("REGISTRATION_TYPE", "PLAN");
                MainUtil.dictionary.put("ACCOUNT_TYPE", accountType);
                MainUtil.dictionary.put("TEST_DATA_FILE", testDataFile);
                MainUtil.dictionary.put("Amount", Amount);

                if (System.getProperty("simType") == null || System.getProperty("simType").equalsIgnoreCase("PHYSICAL")) {
                    MainUtil.dictionary.put("SIM_TYPE", "PHYSICAL");
                } else {
                    MainUtil.dictionary.put("SIM_TYPE", "DUMMY");
                }

                //*Launch UMREX*//*
//                driverZsmart = getDriver("CRM");
//                loginFuncs = new CRMLoginLogoutFuncs(driverZsmart);
//                loginFuncs.loginWCTCRM();
//
//                //*Perform Postpaid Registration*//*
//                postpaidFuncs = new CRMPostpaidRegistrationFuncs(driverZsmart);
//                boolean statusFlag = postpaidFuncs.doCRMPostpaidRegistration(MainUtil.dictionary.get("REGISTRATION_TYPE"),"MYKAD");
                //loginFuncs.logoutCRM();

                Thread.sleep(30000);

                if (statusFlag == true) {
                    ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

                    ExtentTestNGITestListener.createNode("CRM Verification");
                    loginFuncs.loginWCTCRM();
                    //driverZsmart = getDriver("CRM");
                    crmverificationFunc=new CRMPostpaidVerificationFuncs(driverZsmart);
                    String msisdn = MainUtil.dictionary.get("MSISDN");
                    crmverificationFunc.searchMSISDNAndNavigateToThreeSixtyDegreeView(msisdn,driverZsmart);
                    crmverificationFunc.verifyCustomerAndServiceDetailsZSmart(MainUtil.dictionary.get("TEST_DATA_FILE"), driverZsmart);
                    crmverificationFunc.SMSVerification(MainUtil.dictionary.get("PLAN_NAME"),"Subscribed",driverZsmart);
                    crmverificationFunc.SMSVerification(MainUtil.dictionary.get("PLAN_NAME"),"Welcome",driverZsmart);
                    DriverFactory.quitCRM(driverZsmart);

                    ExtentTestNGITestListener.createNode("UMB Verification");

                    driverUMB = getDriver("UMB");
                    umbFuncs=new UMBVerificationFuncs(driverUMB);
                    umbFuncs.umbVerification("POSTPAID", "PLAN");
                    DriverFactory.quitUMB(driverUMB);

                    ExtentTestNGITestListener.createNode("Selfcare Verification");

                    driverSelfcare = getDriver("SELFCARE");
                    selfcareFuncs = new SelfcareVerificationFuncs(driverSelfcare);
                    selfcareFuncs.verifySelfcare("POSTPAID");

                    ExtentTestNGITestListener.createNode("Mobile App Verification");

                    driverMobileApp = getDriver("MOBILEAPP", "Android");
                    //mobileApploginFuncs = new MobileAppLoginFuncs(driverMobileApp);
                    //mobileApploginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

                    mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
                    //mobileAppUtilFuncs.verifyMobileApp(MainUtil.dictionary.get("REGISTRATION_TYPE")+"_POSTPAID");
                    mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TEST_DATA_FILE"));
                }


            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Exception occured while doing prepaid registration  :" + e);
            }
        }



    @Test(description = "CRM FSP Postpaid Registration")
    @Parameters({"SearchplanName","planName", "accountType", "Amount","testDataFile"})
    public void ZsmartFamilyRegistrationPostpaid(String SearchplanName,String planName, String accountType, String Amount, String testDataFile) {

        try {

            ExtentTestNGITestListener.createNode("CRM Postpaid Registration");
            MainUtil.dictionary.put("SearchplanName", SearchplanName);
            MainUtil.dictionary.put("PLAN_NAME", planName);
            MainUtil.dictionary.put("REGISTRATION_TYPE", "PLAN");
            MainUtil.dictionary.put("ACCOUNT_TYPE", accountType);
            MainUtil.dictionary.put("TEST_DATA_FILE", testDataFile);
            MainUtil.dictionary.put("Amount", Amount);

            if (System.getProperty("simType") == null || System.getProperty("simType").equalsIgnoreCase("PHYSICAL")) {
                MainUtil.dictionary.put("SIM_TYPE", "PHYSICAL");
            } else {
                MainUtil.dictionary.put("SIM_TYPE", "DUMMY");
            }

            //*Launch UMREX*//*
            driverZsmart = getDriver("CRM");
            loginFuncs = new CRMLoginLogoutFuncs(driverZsmart);
            loginFuncs.loginWCTCRM();

            //*Perform Postpaid Registration*//*
            postpaidFuncs = new CRMPostpaidRegistrationFuncs(driverZsmart);
            boolean statusFlag = postpaidFuncs.doCRMFSPPostpaidRegistration(MainUtil.dictionary.get("REGISTRATION_TYPE"),"MYKAD");
            //loginFuncs.logoutCRM();
            DriverFactory.quitCRM(driverZsmart);

            Thread.sleep(30000);
            MainUtil.dictionary.put("PLAN_NAME", planName);
            String[] arrplan_name = MainUtil.dictionary.get("PLAN_NAME").split(";");
            String[] testData_File = MainUtil.dictionary.get("TEST_DATA_FILE").split(";");
            System.out.println(arrplan_name.length);
            int r=0;
            for (int q = 1; q<arrplan_name.length+1; q++) {

                MainUtil.dictionary.put("MSISDN", MainUtil.dictionary.get("MSISDN" + q));
                MainUtil.dictionary.put("PLAN_NAME", MainUtil.dictionary.get("PLAN_NAME" + q));
                MainUtil.dictionary.put("SIM_NO", MainUtil.dictionary.get("SIM_NO" + q));
                MainUtil.dictionary.put("TEST_DATA_FILE", testData_File[q]);

                if (statusFlag == true) {
                    ApplicationUtil.getAccountDetails(MainUtil.dictionary.get("MSISDN"));

                    driverZsmart = getDriver("CRM");
                    ExtentTestNGITestListener.createNode("CRM Verification");
                    loginFuncs = new CRMLoginLogoutFuncs(driverZsmart);
                    loginFuncs.loginWCTCRM();
                    //driverZsmart = getDriver("CRM");
                    crmverificationFunc = new CRMPostpaidVerificationFuncs(driverZsmart);
                    String msisdn = MainUtil.dictionary.get("MSISDN");
                    crmverificationFunc.searchMSISDNAndNavigateToThreeSixtyDegreeView(msisdn, driverZsmart);
                    crmverificationFunc.verifyCustomerAndServiceDetailsZSmart(MainUtil.dictionary.get("TEST_DATA_FILE"), driverZsmart);
                    crmverificationFunc.SMSVerification(MainUtil.dictionary.get("PLAN_NAME"), "Welcome", driverZsmart);
                    DriverFactory.quitCRM(driverZsmart);

                    ExtentTestNGITestListener.createNode("UMB Verification");

                    driverUMB = getDriver("UMB");
                    umbFuncs = new UMBVerificationFuncs(driverUMB);
                    umbFuncs.umbVerification("POSTPAID", "PLAN");
                    DriverFactory.quitUMB(driverUMB);

                    ExtentTestNGITestListener.createNode("Selfcare Verification");

                    driverSelfcare = getDriver("SELFCARE");
                    selfcareFuncs = new SelfcareVerificationFuncs(driverSelfcare);
                    selfcareFuncs.verifySelfcare("POSTPAID");

                    ExtentTestNGITestListener.createNode("Mobile App Verification");

                    driverMobileApp = getDriver("MOBILEAPP", "Android");
                    //mobileApploginFuncs = new MobileAppLoginFuncs(driverMobileApp);
                    //mobileApploginFuncs.loginMobileApp(MainUtil.dictionary.get("MSISDN"));

                    mobileAppUtilFuncs = new MobileAppUtilFuncs(driverMobileApp);
                    //mobileAppUtilFuncs.verifyMobileApp(MainUtil.dictionary.get("REGISTRATION_TYPE")+"_POSTPAID");
                    mobileAppUtilFuncs.verifyMobileAppBundlePurchase(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("ACCOUNT_TYPE"), MainUtil.dictionary.get("TEST_DATA_FILE"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while doing postpaid registration  :" + e);
        }
    }
}
