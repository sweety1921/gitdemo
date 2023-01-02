package com.umtest.selfcare.pagefunction;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import com.umtest.selfcare.pageobject.SelfcareLoginLogoutPage;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SelfcareFuncs extends SelfcareLoginLogoutPage {

	private static Logger logger = LogManager.getLogger(SelfcareFuncs.class);
	private RemoteWebDriver driver;
		
	public SelfcareFuncs(RemoteWebDriver driver) {
		super(driver);
		this.driver =  driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);
	}

			
	public boolean PostpaidChangePlan() {

		boolean methodReturn = false;

		try {
			clickElement(getButtonManageAccount(), "Manage Account Button", driver);
        	clickElement(getChangeRatePlan(), "Change Rate Plan Button", driver);
			
        	clickElement(getChangeyourplannowbutton(), "Change Your Plan Now Button", driver);
        	clickElement(getProceedbutton(), "Proceed Button", driver);
        	String cnt="";
        	for (int i = 1; i<10; i++) {
        		
        		List<WebElement> childs = driver.findElementsByXPath("//*[@id=\"crpPlanBoxesDragger\"]/div[3]");
        	
        			for(WebElement element:childs)
        			{
        				String txt = element.getText();
        				//System.out.println(txt);
        				if (txt.contains("P99")) {
        					System.out.println(i);
        					System.out.println(element.getText());
        				    cnt="1";
        				    int j=i+1;
        					//break;
        				    driver.findElementByXPath("//*[@id=\"crpPlanBoxesDragger\"]/div[3]/div/div/div["+j+"]/div/div[1]/div[3]/a").click();
        					
        				}	
        				
        			}
        
        			if (cnt.equals("1")) {
        				break;
        			}
        			driver.findElementByXPath("//*[@id=\"crpPlanBoxesNext\"]").click();
        			
        			
        	}
        	
        	
        	clickElement(getCRPConfirmCheckbox(), "CRP Confirm checkbox", driver);
        	
        	clickElement(getCRPConfirmbutton(), "CRP Confirm Button", driver);
        	
        	methodReturn = true;
        			
        	ApplicationUtil.updateRateplanName(MainUtil.dictionary.get("MSISDN"), MainUtil.dictionary.get("NewRatePlanFullname")) ;
        	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while doing change plan :" + e);
		}
		return methodReturn;
	}
			
}
