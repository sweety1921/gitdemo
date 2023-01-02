package com.umtest.testframe.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.umtest.testframe.lib.SQLConnectionHelper;
import com.umtest.testframe.listener.ExtentTestNGITestListener;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;


public class DriverFactory {

	private static List<WebDriverThread> webDriverThreadPool = Collections.synchronizedList(new ArrayList<WebDriverThread>());
	private static ThreadLocal<WebDriverThread> driverThread;
	private static Logger logger = LogManager.getLogger(DriverFactory.class);
	protected static Map<String, RemoteWebDriver> driverPool;

	@BeforeSuite
	public static void instantiateDriverObject() throws IOException {
		logger.info("Instantiating Driver Object");
		File dir = new File("extentreport");
		dir.mkdir();
		ExtentTestNGITestListener.getExtent();
		driverThread = ThreadLocal.withInitial(() -> {
			WebDriverThread webDriverThread = new WebDriverThread();
			webDriverThreadPool.add(webDriverThread);
			return webDriverThread;
		});
	}

	public static RemoteWebDriver getDriver(String browserName) {
		logger.info("Getting WebDriver");
		driverThread.get().getDriver(browserName);
		return getDriverPool().get(browserName);
	}

	public static RemoteWebDriver getDriver(String appName, String platformName) {
		driverThread.get().getDriver(appName, platformName);
		return getDriverPool().get(appName);
	}

	public static RemoteWebDriver getDriver(String appName, String appPackage, String appActivity, String deviceName, String fileName) {
		driverThread.get().getDriver(appName, appPackage, appActivity, deviceName, fileName);
		return getDriverPool().get(appName);
	}

	public static RemoteWebDriver getDriver(String appName, String uuid, String bundleId, String xcodeOrgId, int timeout) {
		driverThread.get().getDriver(appName, uuid, bundleId, xcodeOrgId, timeout);
		return getDriverPool().get(appName);
	}

	public static Map<String, RemoteWebDriver> getDriverPool() {
		if (driverPool == null) {
			driverPool = new HashMap<>();
		}
		return driverPool;
	}

	@AfterMethod
	public static void closeDriverObjects() throws IOException {
		logger.info("Terminating Driver Session - After Test");
		try {
			for (WebDriverThread webDriverThread : webDriverThreadPool) {
				if (webDriverThread != null) {
					webDriverThread.quitDriver();
				} else {
					logger.info("No Active Driver Session");
				}
			}
			SQLConnectionHelper.closeDBConnPool();
			driverPool.remove("MOBILEAPP");
			driverPool.remove("SELFCARE");
			driverPool.remove("UMB");
			driverPool.remove("UMREX");
			driverPool.remove("UMREXPORTAL");
			driverPool.remove("chrome");
			driverThread.remove();
		} catch (Exception e) {
			logger.error("Error Occured While Terminating Driver", e);
		}
	}

	@BeforeMethod
	public static void printTestName(ITestContext method) throws IOException {
		System.out.println("***********************************************************************************************************************");
		System.out.println("*******                        Test Name: "+method.getCurrentXmlTest().getName());
		System.out.println("***********************************************************************************************************************");
	}

	public static void quitUMREX(RemoteWebDriver driver){
		logger.info("Terminating UMREX Driver Session");
		try {
			driver.quit();
			getDriverPool().remove("UMREX");
		} catch (Exception e) {
			logger.error("Error Occured While Terminating UMREX Driver", e);
		}
	}

	public static void quitMobileApp(RemoteWebDriver driver){
		logger.info("Terminating Mobile App Driver Session");
		try {
			driver.quit();
			getDriverPool().remove("MOBILEAPP");
		} catch (Exception e) {
			logger.error("Error Occured While Terminating Mobile App Driver", e);
		}
	}

	public static void quitSelfcare(RemoteWebDriver driver){
		logger.info("Terminating Selfcare Driver Session");
		try {
			driver.quit();
			getDriverPool().remove("SELFCARE");
		} catch (Exception e) {
			logger.error("Error Occured While Terminating Selfcare Driver", e);
		}
	}

	public static void quitCRM(RemoteWebDriver driver){
		logger.info("Terminating CRM Driver Session");
		try {
			driver.quit();
			getDriverPool().remove("CRM");
		} catch (Exception e) {
			logger.error("Error Occured While Terminating CRM Driver", e);
		}
	}

	public static void quitUMB(RemoteWebDriver driver){
		logger.info("Terminating UMB Driver Session");
		try {
			driver.quit();
			getDriverPool().remove("UMB");

		} catch (Exception e) {
			logger.error("Error Occured While Terminating UMB Driver", e);
		}
	}

	public static void quitUMREXPortal(RemoteWebDriver driver){
		logger.info("Terminating UMREX PORTAL Driver Session");
		try {
			driver.quit();
			getDriverPool().remove("UMREXPORTAL");

		} catch (Exception e) {
			logger.error("Error Occured While Terminating UMREX PORTAL Driver", e);
		}
	}
}