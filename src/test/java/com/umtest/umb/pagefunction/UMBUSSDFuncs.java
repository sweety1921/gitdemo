package com.umtest.umb.pagefunction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.umb.pageobject.UMBDialSimulatorPage;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import java.time.Duration;

public class UMBUSSDFuncs extends UMBDialSimulatorPage {

    private static Logger logger = LogManager.getLogger(UMBUSSDFuncs.class);
    private RemoteWebDriver driver;

    public UMBUSSDFuncs(RemoteWebDriver driver) {
    	super(driver);
		this.driver =  driver;
		PageFactory.initElements(driver, this);

    }

    //Perform USSD operation and verify output
    public String DialForPurchase() {

       	String USSDResponse = null;
        try {
            clickElement(getlinkSimulationAndTracingMenu(),"Click on Simulation And Tracing Menu", driver);
          	clickElement(getlinkSimulation(),"Click on Simulation link", driver);
        	clickElement(getLinkPhonesim(),"Click on Phonesim link", driver);
        	sendKeys(gettextboxMSISDN(), dictionary.get("MSISDN"),"", "Enter MSISDN", driver);
//        	sendKeys(getTextboxUSSDCode(), dictionary.get("USSD_CODE"),"", "Enter USSD Code", driver);
//        	clickElement(getbuttonSend(),"Click on Send", driver);
            dialTheOptionCodes(dictionary.get("USSD_CODE"));

        	USSDResponse = gettextareaUSSDResponse().getText();
        	if(USSDResponse.contains("系统繁忙。请稍后再试")) {
        		ResetUMBScreen();
        		sendKeys(gettextboxMSISDN(), dictionary.get("MSISDN"),"", "Enter MSISDN", driver);
            	sendKeys(getTextboxUSSDCode(), dictionary.get("USSD_CODE"),"", "Enter USSD Code", driver);
            	clickElement(getbuttonSend(),"Click on Send", driver);
            	USSDResponse = gettextareaUSSDResponse().getText();
        	}
        	logger.info(USSDResponse);


        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred while performing UMB USSD operation  :" + e);
        }

        return USSDResponse;
    }

    //Perform USSD operation and verify output
    public String DialForCRPCode() {

        String USSDResponse = null;
        try {
            clickElement(getlinkSimulation(),"Click on Simulation link", driver);
            clickElement(getLinkPhonesim(),"Click on Phonesim link", driver);
            sendKeys(gettextboxMSISDN(), dictionary.get("MSISDN"),"", "Enter MSISDN", driver);
            sendKeys(getTextboxUSSDCode(), dictionary.get("USSD_CODE"),"", "Enter USSD Code", driver);

            clickElement(getbuttonSend(),"Click on Send", driver);

            USSDResponse = gettextareaUSSDResponse().getText();
            String ErrMsg = "系统繁忙。请稍后再试";
            if(USSDResponse.contains("系统繁忙。请稍后再试") || USSDResponse.contains("operation timeout") ) {
                ResetUMBScreen();
                sendKeys(gettextboxMSISDN(), dictionary.get("MSISDN"),"", "Enter MSISDN", driver);
                sendKeys(getTextboxUSSDCode(), dictionary.get("USSD_CODE"),"", "Enter USSD Code", driver);
                clickElement(getbuttonSend(),"Click on Send", driver);
                USSDResponse = gettextareaUSSDResponse().getText();
            }
            logger.info(USSDResponse);


        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while performing UMB USSD operation  :" + e);
        }

        return USSDResponse;
    }

    //Perform USSD clear screen operation
    public void ResetUMBScreen() {
        try {
          	clickElement(getbuttonClear(),"Click on Reset USSD button", driver);
        	logger.info("USSD screen cleared");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while clearing USSD Screen in UMB  :" + e);
        }
    }

    public String DialForUnsubscribeAllBundles(String sPlanType, String sMSISDN) {

        String USSDResponse = null;
        try {

            clickElement(getlinkSimulationAndTracingMenu(),"Click on Simulation And Tracing Menu", driver);
            clickElement(getlinkSimulation(),"Click on Simulation link", driver);
            clickElement(getLinkPhonesim(),"Click on Phonesim link", driver);

            String[] sCategory={"UMIUnsubscribe","GXUnsubscribe","MBUnsubscribe","GTUnsubscribe","GameOnzUnsubscribe","EPIKKKUnsubscribe","CIUnsubscribe"};
            String[] sCategoryDialCode={"*458*3*1*4*1#","*458*1*4*1#","*458*2*4*1#","*458*3*6*4*1*1#","*458*3*5*4*1#","*458*3*4*3*1#","*458*3*9*4*1#"};

            for(int iVar1=0;iVar1<sCategory.length;iVar1++) {

                sendKeys(gettextboxMSISDN(), sMSISDN, "", "Enter MSISDN", driver);
                sendKeys(getTextboxUSSDCode(), sCategoryDialCode[iVar1], "", "Enter USSD Code", driver);
                clickElement(getbuttonSend(), "Click on Send", driver);

                USSDResponse = gettextareaUSSDResponse().getText();

                if (USSDResponse.contains("系统繁忙。请稍后再试")) {
                    ResetUMBScreen();
                    sendKeys(gettextboxMSISDN(), sMSISDN, "", "Enter MSISDN", driver);
                    sendKeys(getTextboxUSSDCode(), sCategoryDialCode[iVar1], "", "Enter USSD Code", driver);
                    clickElement(getbuttonSend(), "Click on Send", driver);
                    USSDResponse = gettextareaUSSDResponse().getText();
                }

                logger.info("USSD Response : "+USSDResponse);
                ResetUMBScreen();

                Thread.sleep(5000);

                logger.info("===> '"+sCategory[iVar1]+"' Unsubscribe Successful");
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred while performing UMB USSD operation  :" + e);
        }

        return USSDResponse;
    }

    public void dialTheOptionCodes(String ussdCode) {
        try {
            String[] arrOfStr = ussdCode.split("\\*");
            for(int i=1;i<arrOfStr.length;i++){
                sendKeys(getTextboxUSSDCode(), arrOfStr[i].replaceAll("#",""),"", "Enter USSD Code", driver);
                clickElement(getbuttonSend(),"Click on Send", driver);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while entering USSD Screen in UMB  :" + e);
        }
    }
}
