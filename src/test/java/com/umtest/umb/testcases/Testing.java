package com.umtest.umb.testcases;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.umtest.mobileapps.pagefunction.MobileAppLoginFuncs;
import com.umtest.mobileapps.pagefunction.MobileAppUtilFuncs;
import com.umtest.selfcare.pagefunction.SelfcareVerificationFuncs;
import com.umtest.testframe.base.DriverFactory;
import com.umtest.testframe.lib.ApplicationUtil;
import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.listener.ExtentTestNGITestListener;
import com.umtest.umb.pagefunction.UMBLoginLogoutFuncs;
import com.umtest.umb.pagefunction.UMBVerificationFuncs;

public class Testing extends DriverFactory{
	private RemoteWebDriver driver;
	UMBLoginLogoutFuncs umbLoginLogout;
	UMBVerificationFuncs umbVerification;
	SelfcareVerificationFuncs selfcareVerify;
	MobileAppLoginFuncs mobileAppLoginFuncs;

	private static Logger logger = LogManager.getLogger(Testing.class);

	@BeforeClass
	public void initialiseObj() throws IOException {
		driver = getDriver("chrome");
		umbLoginLogout = new UMBLoginLogoutFuncs(driver);
		umbVerification = new UMBVerificationFuncs(driver);
		MainUtil.APPLICATION_NAME = "UMB";
	}

	@Test(description = "UMB TOP-UP SELF/TOP-UP FOR FRIEND")
	/*@Parameters({"planName","category","Receiver_msisdn","topupAmount"})*/
	public void TopUpUMB() {
		
		WebElement quantityInsert;
		WebElement addToCart;
		WebElement quantityCheck;
		WebElement cartCount;
		try {
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		    driver.get("https://www.bigbasket.com/pd/10000415/bb-royal-rice-raw-sona-masoori-5-kg-bag/?nc=as&q=rice");
		    driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		    
	
		//When
			quantityInsert = driver.findElement(By.name("qty"));
			quantityInsert.click();
			quantityInsert.clear();
			quantityInsert.sendKeys("3");
		  //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		
			addToCart = driver.findElement(By.xpath("//div[contains(@class,'Cs6YO')]"));
			addToCart.click();
			driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		  	
		// String quantityCheck = driver.findElement(By.xpath("//div[contains(@class,'2Uh70')]/div/div")).getText();
		     quantityCheck = driver.findElement(By.xpath("//div[@class='_2MEFP']"));
		     String var = quantityCheck.getAttribute("value");
		     //String var1 = quantityCheck.getAttribute("xpath");
		     String var2 = quantityCheck.getText();
		     String var3 = quantityCheck.getAttribute("innerText");
			    
		    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		   
		    System.out.println(var);
		    //System.out.println(var1);
		    System.out.println(var2);
		    System.out.println(var3);
		    
		    //System.out.println(quantityCheck.getAttribute("innerHTML"));

		    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		    
		    cartCount = driver.findElement(By.xpath("//div[contains(@class,'1laAA')]/div/span[2]"));
		    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		    System.out.println(cartCount.getText());

		    driver.close();
			driver.quit();

		
	} catch (Exception e) {
		e.printStackTrace();
		logger.error("Exception occured while doing UMB Top-up  :" + e);
	}
	

}
}
