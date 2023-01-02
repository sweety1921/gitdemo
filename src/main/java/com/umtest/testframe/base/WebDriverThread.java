package com.umtest.testframe.base;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.ImmutableMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.umtest.testframe.lib.MainUtil;
import com.umtest.testframe.lib.PropertyHelper;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class WebDriverThread {

	private static String workspace = System.getProperty("user.dir");
	private RemoteWebDriver driver;
	private static final String CHROME_DRIVER_STR = "webdriver.chrome.driver";
	private static Logger logger = LogManager.getLogger(WebDriverThread.class);


	/**
	 * Driver instantiation module for WEB
	 *
	 * @param browserName Name of the driver ex: CHROME1,CHROME2,FIREFOX1
	 * @return Return the initiated driver
	 */
	RemoteWebDriver getDriver(String browserName) {
		logger.info(System.getProperty("os.name"));
		try {
			if (DriverFactory.getDriverPool().get(browserName) == null) {
				if (System.getProperty("location").equalsIgnoreCase("local") || System.getProperty("location").equalsIgnoreCase("remote")) {
					if ("firefox".equals(System.getProperty("browser"))) {
						FirefoxOptions firefoxOptions = new FirefoxOptions().setProfile(new FirefoxProfile());
						firefoxOptions.setAcceptInsecureCerts(true);
						System.setProperty("webdriver.gecko.driver",
								Paths.get(workspace, "src", "main", "resources", "geckodriver").toString());
						driver = new FirefoxDriver(firefoxOptions);
						driver.manage().deleteAllCookies();
						driver.manage().window().maximize();
					} else if ("chrome".equals(System.getProperty("browser"))) {
						System.setProperty(CHROME_DRIVER_STR,
								System.getProperty("user.dir") +"\\drivers\\chromedriver.exe");
						ChromeOptions chromeOptions = new ChromeOptions();
						chromeOptions.addArguments("--no-sandbox");

						if ("headless".equals(System.getProperty("state")))
							chromeOptions.addArguments("--headless");
						chromeOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
								UnexpectedAlertBehaviour.ACCEPT);
						chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
						chromeOptions.addArguments("ignore-certificate-errors");
						chromeOptions.addArguments("disable-popup-blocking");
						chromeOptions.addArguments("window-size=1920,1080");
						driver = new ChromeDriver(chromeOptions);
						driver.manage().deleteAllCookies();
					}
					else if ("ie".equals(System.getProperty("browser"))) {
						System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") +"\\drivers\\ie\\iedriver.exe");
						driver=new InternetExplorerDriver();
						driver.manage().deleteAllCookies();
					}
				} else {
					if ("firefox".equals(System.getProperty("browser"))) {
						FirefoxOptions firefoxOptions = new FirefoxOptions().setProfile(new FirefoxProfile());
						firefoxOptions.setAcceptInsecureCerts(true);
						System.setProperty("webdriver.gecko.driver",
								Paths.get(workspace, "src", "main", "resources", "geckodriver.exe").toString());
						driver = new RemoteWebDriver(new URL(PropertyHelper.getProperties("REMOTE_HUB_URL")),
								firefoxOptions);
						driver.manage().window().maximize();
					} else if ("chrome".equals(System.getProperty("browser"))) {
						System.setProperty(CHROME_DRIVER_STR,
								Paths.get(workspace, "src", "main", "resources", "chromedriver.exe").toString());
						ChromeOptions chromeOptions = new ChromeOptions();
						if ("headless".equals(System.getProperty("state")))
							chromeOptions.addArguments("headless");
						chromeOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
								UnexpectedAlertBehaviour.ACCEPT);
						chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
						chromeOptions.addArguments("ignore-certificate-errors");
						chromeOptions.addArguments("disable-popup-blocking");
						chromeOptions.addArguments("start-maximized");

						driver = new RemoteWebDriver(new URL(MainUtil.HubUrl), chromeOptions);

						logger.info("Execution Processed To Hub : " + MainUtil.HubUrl);
					}
				}
				DriverFactory.getDriverPool().put(browserName, driver);
				driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			logger.error("Error creating the driver", e);
		}
		return driver;
	}

	/**
	 * Driver instantiation module for mobile (Android)
	 // * @param appName     Provide the mobile app name
	 // * @param appPackage  Provide the mobile app package name
	 // * @param appActivity Provide the app activity name
	 // * @param deviceName  Provide the node name where it is has to be run
	 * @return Return the initiated driver
	 */
	RemoteWebDriver getDriver(String appName, String platformName) {

		try {
			DesiredCapabilities androidDcap = new DesiredCapabilities();
			if (System.getProperty("location").equalsIgnoreCase("local") || System.getProperty("location").equalsIgnoreCase("remote")) {

				androidDcap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2");

				if(System.getProperty("location").equalsIgnoreCase("remote")){
					androidDcap.setCapability(MobileCapabilityType.DEVICE_NAME, "3PPTEST-ANDROID10-EMU1");
				} else
				{
					androidDcap.setCapability("deviceName", "Android");
				}

				androidDcap.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
				//androidDcap.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
				androidDcap.setCapability(MobileCapabilityType.NO_RESET, true);
				androidDcap.setCapability(MobileCapabilityType.FULL_RESET, false);
				androidDcap.setCapability(AndroidMobileCapabilityType.NO_SIGN, true);
				androidDcap.setCapability(AndroidMobileCapabilityType.APP_WAIT_DURATION, 30000);
				androidDcap.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
				androidDcap.setCapability("appium:chromedriverExecutable", System.getProperty("user.dir") +"\\drivers\\chromedriver.exe");

				androidDcap.setCapability("newCommandTimeout", 30000);
				androidDcap.setCapability("app", System.getProperty("user.dir") +"\\apk\\"+appName+".apk");
				androidDcap.setCapability("autoGrantPermissions", true);
				androidDcap.setCapability("appPackage", PropertyHelper.getProperties(appName+"_PACKAGE"));
				androidDcap.setCapability("appActivity", PropertyHelper.getProperties(appName+"_ACTIVITY"));

				driver = new AndroidDriver<>(new URL("http://127.0.0.1:4651/wd/hub"), androidDcap);
				logger.info("Session ID of Android("+System.getProperty("location")+"): " + driver.getSessionId());
			} else if (System.getProperty("location").equalsIgnoreCase("DeviceFarm")) {
				driver = new AndroidDriver<>(new URL("http://127.0.0.1:4753/wd/hub"), androidDcap);
				logger.info("Session ID of Android: " + driver.getSessionId());
			}
		} catch (WebDriverException e) {
			logger.error("Driver instantiating failed or app is not installed", e);
		} catch (MalformedURLException e) {
			logger.error(e);
		}
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		DriverFactory.getDriverPool().put(appName, driver);
		return driver;
	}


	/**
	 * Driver instantiation module for mobile (Android)
	 *
	 * @param appName     Provide the mobile app name
	 * @param appPackage  Provide the mobile app package name
	 * @param appActivity Provide the app activity name
	 * @param deviceName  Provide the node name where it is has to be run
	 * @param fileName    Provide the device serial number
	 * @return Return the initiated driver
	 */
	RemoteWebDriver getDriver(String appName, String appPackage, String appActivity, String deviceName,
							  String fileName) {
		DesiredCapabilities androidDcap = new DesiredCapabilities();
		androidDcap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2");
		androidDcap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		androidDcap.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
		androidDcap.setCapability(MobileCapabilityType.NO_RESET, true);
		androidDcap.setCapability(MobileCapabilityType.FULL_RESET, false);
		androidDcap.setCapability(AndroidMobileCapabilityType.NO_SIGN, true);
		androidDcap.setCapability(AndroidMobileCapabilityType.APP_WAIT_DURATION, 120);
		androidDcap.setCapability("deviceName", deviceName);
		androidDcap.setCapability("browserName", "");
		androidDcap.setCapability("newCommandTimeout", 1500);
		androidDcap.setCapability("appPackage", appPackage);
		androidDcap.setCapability("appActivity", appActivity);
		androidDcap.setCapability("autoAcceptAlerts", true);
		androidDcap.setCapability("autoDismissAlerts", true);
		androidDcap.setCapability("app", System.getProperty("user.dir") + "/src/main/resources" + fileName);
		try {
			if (System.getProperty("location").equalsIgnoreCase("local")) {
				driver = new AndroidDriver<>(new URL("http://127.0.0.1:4651/wd/hub"), androidDcap);
				logger.info("Session ID of Android: " + driver.getSessionId());
			} else if (System.getProperty("location").equalsIgnoreCase("remote")) {
				driver = new AndroidDriver<>(new URL(PropertyHelper.getProperties("REMOTE_HUB_URL")), androidDcap);
				logger.info("Session ID of Android: " + driver.getSessionId());
			}
		} catch (WebDriverException e) {
			logger.error("Driver instantiating failed or app is not installed", e);
		} catch (MalformedURLException e) {
			logger.error(e);
		}
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		DriverFactory.getDriverPool().put(appName, driver);
		return driver;
	}

	/**
	 * Driver instantiation module for mobile (iOS)
	 *
	 * @param appName    Provide the mobile app name
	 * @param uuid       Provide the uuid
	 * @param bundleId   Provide the bundle ID of the app
	 * @param xcodeOrgId Provide the xcode org ID
	 * @param timeout
	 * @return returns an object iOS driver
	 */
	RemoteWebDriver getDriver(String appName, String uuid, String bundleId, String xcodeOrgId, int timeout) {
		DesiredCapabilities ioscap = new DesiredCapabilities();
		ioscap.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, 8100);
		ioscap.setCapability(IOSMobileCapabilityType.BUNDLE_ID, bundleId);
		ioscap.setCapability(IOSMobileCapabilityType.XCODE_ORG_ID, xcodeOrgId);
		ioscap.setCapability(IOSMobileCapabilityType.XCODE_SIGNING_ID, "iPhone Developer");
		ioscap.setCapability("automationName", "XCUITest");
		ioscap.setCapability("platformName", "iOS");
		ioscap.setAcceptInsecureCerts(true);
		ioscap.acceptInsecureCerts();
		ioscap.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 60000);
		ioscap.setCapability(IOSMobileCapabilityType.COMMAND_TIMEOUTS, 60000);
		ioscap.setCapability(IOSMobileCapabilityType.WDA_LAUNCH_TIMEOUT, 1200000);
		ioscap.setCapability(IOSMobileCapabilityType.WDA_CONNECTION_TIMEOUT, 1200000);
		try {
			if (System.getProperty("location").equalsIgnoreCase("local")) {
				driver = new IOSDriver<>(new URL("http://127.0.0.1:4651/wd/hub"), ioscap);
			} else {
				driver = new IOSDriver<>(new URL(PropertyHelper.getProperties("REMOTE_HUB_URL")), ioscap);
			}
		} catch (Exception e) {
			logger.error("IOSDriver Driver instantiating failed", e);
		}
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		DriverFactory.getDriverPool().put(appName, driver);
		return driver;
	}


	/**
	 * Quit driver
	 */
	void quitDriver() {

		try {
			logger.info("quitDriver");
			DriverFactory.getDriverPool().entrySet();
			if (!DriverFactory.getDriverPool().entrySet().isEmpty()) {
				for (Map.Entry<String, RemoteWebDriver> driverInstance : DriverFactory.getDriverPool().entrySet()) {
					if (driverInstance.getValue() != null) {
						if ((driverInstance.getValue() instanceof AndroidDriver))
							try {
								driverInstance.getValue().quit();
							} catch (WebDriverException wb) {
								logger.info("Session is already terminated");
							}
						else if (driverInstance.getValue() instanceof RemoteWebDriver) {
							try {
								driverInstance.getValue().quit();
							} catch (WebDriverException wb) {
								logger.info("Session is already terminated");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception in closing the driver \n", e);
		}
	}
}
