
package com.umtest.testframe.lib;

import static com.umtest.testframe.listener.ExtentTestNGITestListener.getTest;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class MainUtil {
	public static Map<String, String> dictionary = new HashMap<>();
	public static String mainWindow = null;
	public static String APPLICATION_NAME;
	public static String Image_Path;
	public static String ENVIRONMENT = System.getProperty("env");
	public static String SIMRECYCLE = System.getProperty("simrecycle");
	public static String dataId = System.getProperty("dataId");
	private static Logger logger = LogManager.getLogger(MainUtil.class);
	public static String HubUrl;
	public static String MCMCREMARKS;
	public static HashMap<Integer, String> details = new HashMap<Integer, String>();

	static Markup m;

	public enum ProjectConst {
		EXCEPTIONTEXT("**** Exception Occurred ****"),
		ELEMENTNOTFOUNDEXCEPTIONTXT("**** Element Not Found or Xpath is NULL ****"),
		EXCEPTIONTEXTMETHOD("**** Exception Occurred in the Method ****"), VALUE(" ======> ");

		private String msg;

		ProjectConst(String s) {
			msg = s;
		}

		public String getMsg() {
			return msg;
		}
	}

	public static synchronized void launchURL(String url, RemoteWebDriver driver) {
		try {
			logger.info("Launching URL");
			driver.get(url);
			getTest().get().pass("URL Launched Successfully", ExtentScreenCapture.captureSrceenPass("URL", driver));
		} catch (Exception e) {
			logger.error("Text not typed");
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD + " " + "launchURL", e);
			getTest().get().fail("URL Launched UnSuccessful", ExtentScreenCapture.captureSrceenFail("URL", driver));
		}
	}

	public static synchronized void pressEnterKey(WebElement element) {
		try {
			logger.info("Key Press Enter");
			element.sendKeys(Keys.ENTER);
		} catch (NoSuchElementException e) {
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD + " " + "pressEnterKey", e);
		}
	}

	public static boolean isElementDisplayed(WebElement element) {
		boolean ElementDisplayed = false;
		try {
			if (element.isDisplayed()) {
				ElementDisplayed = true;
			}
		} catch (Exception ex) {
			ElementDisplayed = false;
			logger.error("Thown Exception");
			logger.error("Element Is Not Visible");
		}
		return ElementDisplayed;
	}

	public static boolean isElementExists(By by, RemoteWebDriver driver) {
		boolean ElementDisplayed = false;

			if (driver.findElements(by).size()!= 0) {
				ElementDisplayed = true;
			} else{
				ElementDisplayed = false;
		}

		return ElementDisplayed;
	}

	public static int getElementYLocation(By by, RemoteWebDriver driver) {
		return driver.findElement(by).getLocation().getY();
	}



	public static synchronized void sendKeys(WebElement element, String text, String elementName, String dictionaryVar,
			RemoteWebDriver driver) {
		try {
			logger.info("Inputing Keys : "+text);
			element.clear();
			element.sendKeys(text);
			if (!(dictionaryVar.equalsIgnoreCase(""))) {
				dictionary.put(dictionaryVar, element.getAttribute("value"));
				logger.info(new StringBuilder("Dictionary======>").append(dictionary.get(dictionaryVar)));
			}
			if (elementName.toLowerCase().contains("password")) {
				getTest().get().pass(elementName + " Typed Successfully ",
						ExtentScreenCapture.captureSrceenPass(elementName, driver));
			} else {
				getTest().get().pass(elementName + " " + text + " Typed Successfully ",
						ExtentScreenCapture.captureSrceenPass(elementName, driver));
			}

		} catch (Exception e) {
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "sendKeys", e);
			if (elementName.toLowerCase().contains("password")) {
				getTest().get().fail(elementName + " Typed UnSuccessfull",
						ExtentScreenCapture.captureSrceenPass(elementName, driver));
			} else {
				getTest().get().fail(elementName + " " + text + " Type UnSuccessfull",
						ExtentScreenCapture.captureSrceenFail(elementName, driver));
			}
		}
	}


	public static synchronized boolean clickElement(WebElement elementToClick, String elementName,
			RemoteWebDriver driver) {
		boolean status = false;
		try {
			logger.info(new StringBuilder("Clicking : ").append(elementName));
			elementToClick.click();
			status = true;
			getTest().get().pass(elementName + " Clicked Successfully",
					ExtentScreenCapture.captureSrceenPass(elementName, driver));
		} catch (UnhandledAlertException e) {
			clickOnAlert("Ok", "Ok on alert", driver);
			getTest().get().pass(elementName + " Clicked Successfully",
					ExtentScreenCapture.captureSrceenPass(elementName, driver));
		} catch (Exception e) {
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + "\n"
					+ ProjectConst.ELEMENTNOTFOUNDEXCEPTIONTXT.getMsg(), e);
			getTest().get().fail(elementName + " Click Unsuccessfull",
					ExtentScreenCapture.captureSrceenFail(elementName, driver));
		}
		return status;
	}
	
	
	public static synchronized boolean clickElementUsingXpathString(String xpathString, String elementName,
			RemoteWebDriver driver) {
		boolean status = false;
		try {
			logger.info(new StringBuilder("Clicking : ").append(elementName));
			driver.findElement(By.xpath(xpathString)).click();
			status = true;
			getTest().get().pass(elementName + " Clicked Successfully",
					ExtentScreenCapture.captureSrceenPass(elementName, driver));
		} catch (UnhandledAlertException e) {
			clickOnAlert("Ok", "Ok on alert", driver);
			getTest().get().pass(elementName + " Clicked Successfully",
					ExtentScreenCapture.captureSrceenPass(elementName, driver));
		} catch (Exception e) {
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + "\n"
					+ ProjectConst.ELEMENTNOTFOUNDEXCEPTIONTXT.getMsg(), e);
			getTest().get().fail(elementName + " Click Unsuccessfull",
					ExtentScreenCapture.captureSrceenFail(elementName, driver));
		}
		return status;
	}
	

	public static synchronized boolean javaScriptClickElement(WebElement elementToClick, String elementName,
			RemoteWebDriver driver) {
		boolean status = false;
		try {
			logger.info(new StringBuilder("Click Element Using Javascript: ").append(elementName));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", elementToClick);
			status = true;
			getTest().get().pass(elementName + " Clicked Successfully",
					ExtentScreenCapture.captureSrceenPass(elementName, driver));
		} catch (UnhandledAlertException e) {
			clickOnAlert("Ok", "Ok On Alert", driver);
			getTest().get().pass(elementName + " Clicked Successfully",
					ExtentScreenCapture.captureSrceenPass(elementName, driver));
		} catch (Exception e) {
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + "\n"
					+ ProjectConst.ELEMENTNOTFOUNDEXCEPTIONTXT.getMsg(), e);
			getTest().get().fail(elementName + " Click Unsuccessful",
					ExtentScreenCapture.captureSrceenFail(elementName, driver));
		}
		return status;
	}

	public static synchronized boolean clickAndOpenInNewTab(WebElement elementToClick, String elementName,
			RemoteWebDriver driver) {
		boolean status = false;
		try {
			logger.info("Click And Open In NewTab");
			Actions action = new Actions(driver);
			action.keyDown(Keys.CONTROL).build().perform();
			elementToClick.click();
			action.keyUp(Keys.CONTROL).build().perform();
			status = true;
			getTest().get().pass(elementName + " Clicked Successfully",
					ExtentScreenCapture.captureSrceenPass(elementName, driver));
		} catch (UnhandledAlertException e) {
			clickOnAlert("Ok", "Ok on alert", driver);
			getTest().get().pass(elementName + " Clicked Successfully",
					ExtentScreenCapture.captureSrceenPass(elementName, driver));
		} catch (Exception e) {
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD + " " + ProjectConst.ELEMENTNOTFOUNDEXCEPTIONTXT, e);
			getTest().get().fail(elementName + " Click Unsuccessful",
					ExtentScreenCapture.captureSrceenFail(elementName, driver));
		}
		return status;
	}

	public static synchronized void switchToNewTab(RemoteWebDriver driver) {
		try {
			ArrayList tabs;
			tabs = new ArrayList(driver.getWindowHandles());
			logger.info(tabs.size());
			driver.switchTo().window((String) tabs.get(0));
		} catch (Exception e) {
			logger.error("Unable to switch to new tab:", e);
		}
	}

	public static synchronized boolean scrollToElementAndClick(WebElement element, String elementName,
			RemoteWebDriver driver) {
		boolean status = false;
		try {
			logger.info(new StringBuilder("Scroll and click: ").append(element));
			WebDriverWait wait = new WebDriverWait(driver, 50);
			Point hoverItem = element.getLocation();
			((JavascriptExecutor) driver).executeScript("return window.title;");
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + (hoverItem.getY() - 400) + ");");
			wait.until(ExpectedConditions.elementToBeClickable(element));
			getTest().get().pass(elementName + " Clicked Successfully",
					ExtentScreenCapture.captureSrceenPass(elementName, driver));
			element.click();
			status = true;
		} catch (Exception e) {
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "scrollToElementAndClick", e);
			getTest().get().fail(elementName + " Click Unsuccessful",
					ExtentScreenCapture.captureSrceenFail(elementName, driver));
			throw e;
		}
		return status;
	}

	public static synchronized String getText(WebElement element) {
		String text = "NA";
		try {
			logger.info(new StringBuilder("Get Text :").append(element));
			text = element.getText();
		} catch (Exception e) {
			logger.error("Element not stored");
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "getText", e);
			throw e;
		}
		return text;
	}

	public static synchronized String getTextUsingXpath(String xpathString, String elementName,
														RemoteWebDriver driver) {
		String text = "NA";
		try {
			logger.info(new StringBuilder("Get Text :").append(elementName));
			text = driver.findElement(By.xpath(xpathString)).getText();
		} catch (Exception e) {
			logger.error("Element not stored");
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "getTextUsingXpath", e);
			throw e;
		}
		return text;
	}


	public static synchronized void selectValueByDropdown(WebElement dropDown, String dropdownValue,
			RemoteWebDriver driver) {
		try {
			logger.info(new StringBuilder("Select value by dropdown: ").append(dropDown));
			WebElement dropdownValues = dropDown;
			Select dropdowm = new Select(dropdownValues);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(dropDown));
			dropdowm.selectByVisibleText(dropdownValue);
			getTest().get().pass("Selected value " + dropdownValue + " from Dropdown Successfully",
					ExtentScreenCapture.captureSrceenPass("selectValueByDropdown", driver));
		} catch (Exception e) {
			logger.error("Dropdown value not found");
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "selectValueByDropdown", e);
			getTest().get().fail("Value not Selected",
					ExtentScreenCapture.captureSrceenFail("selectValueByDropdown", driver));
		}
	}


	public static synchronized String verifyText(WebElement checkForText, String textToCompare,
			String elementName, RemoteWebDriver driver) {
		String successmsg = null;
		String xpathText = null;
		try {
			logger.info(new StringBuilder("Verify Text: ").append(checkForText));
			xpathText = checkForText.getText();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", checkForText,
					"border: 5px solid red;");
			if (xpathText.trim().replaceAll("\\s", "").equalsIgnoreCase(textToCompare.trim().replaceAll("\\s", ""))) {
				logger.info("Text matches with the given text");
				successmsg = xpathText;
				getTest().get()
				.pass(elementName + " Text matches with the given text. Expected Value:" + textToCompare
						+ " Actual Value:" + xpathText,
						ExtentScreenCapture.captureSrceenPass("verifyTextByXpath", driver));
			} else if (xpathText.trim().toLowerCase().matches(textToCompare.trim().toLowerCase())) {
				logger.info("Text matches with the given text");
				successmsg = xpathText;
				getTest().get()
				.pass(elementName + " Text matches with the given text. Expected Value:" + textToCompare
						+ " Actual Value:" + xpathText,
						ExtentScreenCapture.captureSrceenPass("verifyTextByXpath", driver));
			} else {
				logger.info("Text does not match with the given text");
				getTest().get()
				.fail(elementName + " Text does not match with the given text. Expected Value:" + textToCompare
						+ " Actual Value:" + xpathText,
						ExtentScreenCapture.captureSrceenFail("verifyTextByXpath", driver));
			}
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", checkForText, "border: none;");
		} catch (Exception e) {
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "verifyTextByXpath", e);
			getTest().get()
			.fail("Some Exception Occur in verifyTextByXpath for " + elementName + ". Expected Value:"
					+ textToCompare + " Actual Value:" + xpathText,
					ExtentScreenCapture.captureSrceenFail("verifyTextByXpath", driver));
		}
		return successmsg;
	}


	public static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss");
		Date date = new Date();
		return dateFormat.format(date);
	}


	public static synchronized void fetchOptionFromDropDown(WebElement element, String text, RemoteWebDriver driver) {
		try {
			logger.info("Fetch option from dropdown from: " + element);
			List<WebElement> allOptions = element.findElements(By.tagName("option"));
			for (WebElement option : allOptions) {
				if (option.getText().equalsIgnoreCase(text)) {
					option.click();
					break;
				}
			}
			getTest().get().pass("Fetched " + text + " From Dropdown",
					ExtentScreenCapture.captureSrceenPass("fetchOptionFromDropDown", driver));
		} catch (Exception e) {
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "getDateTime", e);
			getTest().get().fail("Some Exception Occured",
					ExtentScreenCapture.captureSrceenFail("fetchOptionFromDropDown", driver));
		}
	}


	public static synchronized boolean checkForText(String dictionaryVar, WebElement element, String data,
			String elementName, AndroidDriver driver) {

		boolean returnFlag = false;

		try {
			logger.info("Check for text in: " + element);
			String actualOutput = element.getText();
			if (!(dictionaryVar.equalsIgnoreCase(""))) {
				dictionary.put(dictionaryVar, actualOutput);
			}
			logger.info(new StringBuilder("**** Check for Text :").append(element));
			if (element.isDisplayed()) {
				if (actualOutput.trim().replaceAll("\\s", "").equalsIgnoreCase(data.trim().replaceAll("\\s", ""))) {
					logger.info("Text matches with the given text");
					returnFlag = true;
					getTest().get().pass(
							"<p style=\"color:green;font-weight:bold\">Text Matches! " + elementName + "</p>  <br><b>Expected:</b> " + data + "\n  <br><b>Actual:</b> " + actualOutput,
							ExtentScreenCapture.captureSrceenPass("checkForText", driver));
				} else if (element.getText().trim().toLowerCase().matches(data.trim().toLowerCase())) {
					logger.info("#### Text Found in the Target location ####");
					returnFlag = true;
					getTest().get().pass(elementName + " Text Found in the Target location",
							ExtentScreenCapture.captureSrceenPass("checkForText", driver));
				} else if (element.getText().trim().equalsIgnoreCase(data.trim())) {
					logger.info("#### Text Found in the Target location ####");
					returnFlag = true;
					getTest().get().pass(elementName + " Text Found in the Target location",
							ExtentScreenCapture.captureSrceenPass("checkForText", driver));
				} else {
					logger.info("#### Text Not found in the Target location ####");
					getTest()
					.get().fail(
							"<p style=\"color:red;font-weight:bold\">Text Does Not Matches " + elementName + "</p>  <br><b>Expected:</b> " + data + "\n  <br><b>Actual:</b> "
									+ actualOutput,
									ExtentScreenCapture.captureSrceenFail("checkForText", driver));
				}
			} else {
				logger.info("#### The Element is not found & Unable to verify Text");
				getTest().get().fail(elementName + "The Element is not found & Unable to verify Text",
						ExtentScreenCapture.captureSrceenFail("checkForText", driver));
			}
		} catch (Exception e) {
			logger.error("#### Error occured while checking the text in the " + elementName);
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "checkForText", e);
			getTest().get().fail("Error occured while checking the text in the " + elementName,
					ExtentScreenCapture.captureSrceenFail("checkForText", driver));
		}
		return returnFlag;
	}


	public static synchronized boolean checkForText(String dictionaryVar, WebElement element, String data, String elementName,
			RemoteWebDriver driver) {
		boolean flag = false;
		try {
			logger.info("Check for text " + data);
			String actualOutput = element.getText();
			if (!(dictionaryVar.equalsIgnoreCase(""))) {
				dictionary.put(dictionaryVar, actualOutput);
			}
			logger.info("### Check for Text: Target element verification first:" + element);
			if (element.isDisplayed()) {
				if (MainUtil.APPLICATION_NAME.equalsIgnoreCase("selfcare")
						|| MainUtil.APPLICATION_NAME.equalsIgnoreCase("zsmart"))
					scrollToWebElement(element, driver);
				else
					scrollToElement(element, driver);
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
						"border: 5px solid red;");
				if (actualOutput.trim().replaceAll("\\s", "").equalsIgnoreCase(data.trim().replaceAll("\\s", ""))) {
					logger.info("Text matches with the given text");
					flag = true;
					getTest().get().pass(
							"Text matches! " + elementName + " - Expected: " + data + "\n - Actual: " + actualOutput,
							ExtentScreenCapture.captureSrceenPass("checkForText", driver));
				} else if (element.getText().trim().toLowerCase().matches(data.trim().toLowerCase())) {
					logger.info("#### Text Found in the Target location ####");
					flag = true;
					getTest().get().pass(elementName + " Text Found in the Target location",
							ExtentScreenCapture.captureSrceenPass("checkForText", driver));
				} else if (element.getText().trim().equalsIgnoreCase(data.trim())) {
					logger.info("#### Text Found in the Target location ####");
					flag = true;
					getTest().get().pass(elementName + " Text Found in the Target location",
							ExtentScreenCapture.captureSrceenPass("checkForText", driver));
				} else {
					logger.info("#### Text Not found in the Target location ####");
					flag = false;
					getTest()
					.get().fail(
							"Text does not matches " + elementName + " - Expected: " + data + "\n - Actual: "
									+ actualOutput,
									ExtentScreenCapture.captureSrceenFail("checkForText", driver));
				}
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "border: none;");
			} else {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
						"border: 5px solid red;");
				logger.info("#### The Element is not found & Unable to verify Text");
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "border: none;");
				flag = false;
				getTest().get().fail(elementName + "The Element is not found & Unable to verify Text",
						ExtentScreenCapture.captureSrceenFail("checkForText", driver));
			}
		} catch (Exception e) {
			logger.error("#### Error occured while checking the text in the " + elementName);
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "checkForText", e);
			getTest().get().fail("Error occured while checking the text in the " + elementName,
					ExtentScreenCapture.captureSrceenFail("checkForText", driver));
		}

		return flag;
	}

	public static synchronized void checkForElement(String dictionaryVar, WebElement element, String elementName,
			RemoteWebDriver driver) {
		try {
			logger.info("Check for the element:" + elementName);
			if (!(dictionaryVar.equalsIgnoreCase("") || dictionaryVar == null)) {
				dictionary.put(dictionaryVar, element.getText());
				logger.info("### Check for Text: Target element verification first:" + element);
				logger.info("### @@@ The element to be found:" + element);
			}
			if (element.isDisplayed()) {
				if (MainUtil.APPLICATION_NAME.equalsIgnoreCase("zsmart")
						|| MainUtil.APPLICATION_NAME.equalsIgnoreCase("selfcare"))
					scrollToWebElement(element, driver);
				else
					scrollToElement(element, driver);
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
						"border: 5px solid red;");
				logger.info("### The Element is verified successfully");
				getTest().get().pass(elementName + "Element is verified successfully",
						ExtentScreenCapture.captureSrceenPass("checkForElement", driver));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "border: none;");
			} else {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
						"border: 5px solid red;");
				logger.info("### The Element is not verified successfully");
				getTest().get().fail(elementName + "Element is not verified successfully",
						ExtentScreenCapture.captureSrceenFail("checkForElement", driver));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "border: none;");
			}
		} catch (NoSuchElementException e) {
			logger.error("### Error occured while checking the element " + elementName);
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + " Error in checkForElement", e);
			getTest().get().fail("Error occured while checking the element ",
					ExtentScreenCapture.captureSrceenFail("checkForElement", driver));
		}
	}

	/**
	 * Method to check whether element is present for android (mobile)
	 *
	 * @param storeVar    Pass the store variable
	 * @param element     Pass the element for which need to check the element exist
	 *                    or not
	 * @param elementName Pass the element name for which need to check the element
	 *                    exist or not
	 * @param driver      Pass the Android driver
	 */
	public static synchronized void checkForElement(String storeVar, WebElement element, String elementName,
			AndroidDriver driver) {
		try {
			logger.info("Check for the element: " + elementName);
			if (!(storeVar.equalsIgnoreCase("") || storeVar == null)) {
				logger.info("line 3511 --inside the if where storevariable is not null");
				dictionary.put(storeVar, element.getText());
				logger.info("### Check for Text: Target element verification first:" + element);
				logger.info("### @@@ The element to be found:" + element);
			}
			if (element.isDisplayed()) {
				logger.info("### The Element is verified successfully");
				getTest().get().pass(elementName + "Element is verified successfully",
						ExtentScreenCapture.captureSrceenPass("checkForElement", driver));

			} else {
				logger.info("### The Element is not verified successfully");
				getTest().get().fail(elementName + "Element is not verified successfully",
						ExtentScreenCapture.captureSrceenFail("checkForElement", driver));
			}
		} catch (Exception e) {
			logger.error("### Error occured while checking the element " + elementName);
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + " Error in checkForElement", e);
			getTest().get().fail("Error occured while checking the element ",
					ExtentScreenCapture.captureSrceenFail("checkForElement", driver));
		}
	}

	/**
	 * Method to check whether element is present for android (mobile)
	 *
	 * @param storeVar    Pass the store variable
	 * @param element     Pass the element for which need to check the element exist
	 *                    or not
	 * @param elementName Pass the element name for which need to check the element
	 *                    exist or not
	 * @param driver      Pass the IOS driver
	 */
	public static synchronized void checkForElement(String storeVar, WebElement element, String elementName,
			IOSDriver driver) {
		try {
			logger.info("Check for the element");
			if (!(storeVar.equalsIgnoreCase("") || storeVar == null)) {
				logger.info("line 3511 --inside the if where storevariable is not null");
				dictionary.put(storeVar, element.getText());
				logger.info("### Check for Text: Target element verification first:" + element);
				logger.info("### @@@ The element to be found:" + element);
			}
			if (element.isDisplayed()) {
				logger.info("### The Element is verified successfully");
				getTest().get().pass(elementName + "Element is verified successfully",
						ExtentScreenCapture.captureSrceenPass("checkForElement", driver));
			} else {
				logger.info("### The Element is not verified successfully");
				getTest().get().fail(elementName + "Element is not verified successfully",
						ExtentScreenCapture.captureSrceenFail("checkForElement", driver));
			}
		} catch (Exception e) {
			logger.error("### Error occured while checking the element " + elementName);
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + " Error in checkForElement", e);
			getTest().get().fail("Error occured while checking the element ",
					ExtentScreenCapture.captureSrceenFail("checkForElement", driver));
		}
	}

	public static synchronized void verifyElementIsNotDisplayed(WebElement element, String ElementName,
			RemoteWebDriver driver) {
		boolean ElementDisplayed = false;
		try {
			if (element.isDisplayed()) {
				ElementDisplayed = true;
			}

		} catch (Exception ex) {
			ElementDisplayed = false;
			System.out.println("Element not present...");
		}
		if (ElementDisplayed == false) {
			getTest().get().pass(ElementName + " Element is not present",
					ExtentScreenCapture.captureSrceenFail(ElementName, driver));
		} else {
			getTest().get().fail(ElementName + " Element is present",
					ExtentScreenCapture.captureSrceenFail(ElementName, driver));
		}
	}

	public static synchronized boolean verifyElementIsDisplayed(WebElement element, String ElementName,
			RemoteWebDriver driver) {
		boolean ElementDisplayed = false;
		try {
			if (element.isDisplayed()) {
				ElementDisplayed = true;
			} 

		} catch (Exception ex) {
			ElementDisplayed = false;
			System.out.println(ElementName+" is not present");
			throw ex;
		}
		return ElementDisplayed;
	}

	public static synchronized boolean verifyElementDisplayed(WebElement element, String ElementName,
																RemoteWebDriver driver) {
		boolean ElementDisplayed = false;
		try {
			if (element.isDisplayed()) {
				ElementDisplayed = true;
				getTest().get().pass(ElementName + " is verified successfully",
						ExtentScreenCapture.captureSrceenPass(ElementName, driver));
			}

		} catch (Exception ex) {
			ElementDisplayed = false;
			System.out.println(ElementName+" is not present");
			getTest().get().fail(ElementName + " is not displayed",
					ExtentScreenCapture.captureSrceenFail(ElementName, driver));
			throw ex;
		}
		return ElementDisplayed;
	}

	public static synchronized boolean verifyElementExistUsingXpathString(String xpath, String ElementName,
			RemoteWebDriver driver) {
		boolean ElementDisplayed = false;
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		try {
			if (driver.findElements(By.xpath(xpath)).size() > 0) {
				ElementDisplayed = true;
			} 

		} catch (Exception ex) {
			ElementDisplayed = false;
			System.out.println(ElementName+" is not present");
			throw ex;
		} finally {
			driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		}
		return ElementDisplayed;
	}

	/**
	 * Method to select/switch to new window
	 *
	 * @param driver pass the driver
	 */
	public static synchronized void selectNewWindow(RemoteWebDriver driver) {
		try {
			logger.info("Switch new window");
			mainWindow = driver.getWindowHandle();
			logger.info("parentWin: " + mainWindow);
			String childWin = null;
			Set<String> winHandles = driver.getWindowHandles();
			Iterator<String> iterator = winHandles.iterator();
			while (iterator.hasNext())
				childWin = iterator.next();
			driver.switchTo().window(childWin);
			getTest().get().pass(" New Window Selected ",
					ExtentScreenCapture.captureSrceenPass("selectNewWindow", driver));
		} catch (Exception e) {
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "Error Selecting the new window", e);
			getTest().get().fail("New Window Nots Selected",
					ExtentScreenCapture.captureSrceenFail("selectNewWindow", driver));
		}
	}

	/**
	 * Method to click on an element and switch back to parent window
	 *
	 * @param element Pass the element of the parent window
	 * @param driver  Pass the driver
	 */
	public static synchronized void clickAndSwitchBack(WebElement element, RemoteWebDriver driver) {
		try {
			logger.info("Click and swich back");
			if (element.isDisplayed()) {
				logger.info("### Eelement is displayed ");
				element.click();
				logger.info("### Click is done successfully");
				driver.switchTo().window(mainWindow);
				logger.info("### Switched to parent window");
				getTest().get().pass("Click is done successfully",
						ExtentScreenCapture.captureSrceenPass("clickAndSwitchBack", driver));
			} else {
				logger.info("### Eelement was not found");
				getTest().get().fail("Eelement was not found",
						ExtentScreenCapture.captureSrceenFail("clickAndSwitchBack", driver));
			}
		} catch (Exception e) {
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg(), e);
			getTest().get().fail("Error Occured in click And Switch Back ",
					ExtentScreenCapture.captureSrceenFail("clickAndSwitchBack", driver));
		}
	}

	/**
	 * Method to compare two strings
	 *
	 * @param actual      pass the actual String to compare
	 * @param expected    pass the expected String
	 * @param driver      pass the driver
	 * @param elementName pass the element name for logging purpose
	 */
	public synchronized static void compareWholeString(String actual, String expected,
			String elementName, RemoteWebDriver driver) {
		try {

			if (actual.replaceAll("\\s+", "").equalsIgnoreCase(expected.replaceAll("\\s+", ""))) {
				logger.info(
						elementName + " comparison is successful - actual : expected -> " + actual + " : " + expected);
				getTest().get().pass(elementName + " Comparison Successful , Actual Value = " + actual
						+ " : Expected Value = " + expected);
			} else {
				logger.info(elementName + " Comparison Is Unsuccessful - Actual : Expected -> " + actual + " : "
						+ expected);
				getTest().get().fail(elementName + " Comparison Unsuccessful , Actual value = " + actual
						+ " : Expected Value = " + expected);
			}
		} catch (Exception e) {
			logger.error("Comparison Unsuccessfull");
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "compareString", e);
		}
	}

	/**
	 * Method to compare two strings
	 *
	 * @param actual      pass the actual String to compare
	 * @param expected    pass the expected String
	 * @param elementName pass the element name for logging purpose
	 */
	public synchronized static void compareWholeString(String actual, String expected,
													   String elementName) {
		try {

			if (actual.replaceAll("\\s+", "").equalsIgnoreCase(expected.replaceAll("\\s+", ""))) {
				logger.info(
						elementName + " comparison is successful - actual : expected -> " + actual + " : " + expected);
				getTest().get().pass(elementName + " Comparison Successful , Actual Value = " + actual
						+ " : Expected Value = " + expected);
			} else {
				logger.info(elementName + " Comparison Is Unsuccessful - Actual : Expected -> " + actual + " : "
						+ expected);
				getTest().get().fail(elementName + " Comparison Unsuccessful , Actual value = " + actual
						+ " : Expected Value = " + expected);
			}
		} catch (Exception e) {
			logger.error("Comparison Unsuccessfull");
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "compareString", e);
		}
	}

	/**
	 * Method to compare two strings
	 *
	 * @param actual      pass the actual String to compare
	 * @param expected    pass the expected String
	 * @param driver      pass the driver
	 * @param elementName pass the element name for logging purpose
	 */
	public synchronized static boolean compareInString(String actual, String expected,
			String elementName, RemoteWebDriver driver) {
		Boolean flag = false;
		try {

			if (actual.replaceAll("\\s+", "").replaceAll("[^a-zA-Z0-9]","").contains(expected.replaceAll("\\s+", "").replaceAll("[^a-zA-Z0-9]",""))) {
				logger.info(
						elementName + " Comparison Is Successful - Actual : Expected -> " + actual + " : " + expected);
				flag = true;
				getTest().get().pass("<b>"+elementName + " Comparison Successful !!</b> <br><b>Actual :</b> " + actual
						+ "<br><b>Expected :</b> " + expected);
			} else {
				logger.info(elementName + " Comparison is unsuccessful - actual : expected -> " + actual + " : "
						+ expected);
				getTest().get().fail("<b>"+elementName + " Comparison Unsuccessful !!</b> <br><b>Actual :</b> " + actual
						+ "<br><b>Expected :</b> " + expected);
			}
		} catch (Exception e) {
			logger.error("Comparison Unsuccessfull");
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "compareString", e);
			flag = false;
		}
		return flag;
	}

	/**
	 * Method to click by Mouse
	 *
	 * @param element pass the element which need to be clicked
	 * @param driver  pass the driver
	 */
	public static synchronized void mouseClick(WebElement element, RemoteWebDriver driver) {
		Actions click = new Actions(driver);
		try {
			logger.info("Mouse click on: " + element);
			click.moveToElement(element).click().perform();
			getTest().get().pass("Mouse Click Successful", ExtentScreenCapture.captureSrceenPass("mouseClick", driver));
		} catch (Exception e) {
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "mouseClick", e);
			getTest().get().fail("Mouse Click UnSuccessful",
					ExtentScreenCapture.captureSrceenFail("mouseClick", driver));
		}
	}

	/**
	 * Method to store path of the image to be stored
	 */
	public static synchronized void storeImagePath() {
		try {
			logger.info("Store image path");
			dictionary.put("IMAGE_TO_STORE", Image_Path);
			getTest().get().pass("storeImagePath : Image Stored Successfully");
		} catch (Exception e) {
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "Error in storeImagePath", e);
			getTest().get().fail("storeImagePath : Image Stored Unsuccessful");
			throw e;
		}
	}

	/**
	 * Method to click on element twice
	 *
	 * @param element pass the elemet which need to double clicked
	 * @param driver  pass the driver
	 */
	public static synchronized void doubleClickOnElement(WebElement element, RemoteWebDriver driver) {
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(element).doubleClick().perform();
		} catch (Exception e) {
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "Error while doubleClickOnElement", e);
			getTest().get().fail("doubleClickOnElement : Double click failed");
			throw e;
		}
	}

	/**
	 * Method to move/scroll/view to the element
	 *
	 * @param elementToClick pass the element to be clicked
	 * @param driver         pass the driver
	 */
	public static synchronized void scrollToElement(WebElement elementToClick, RemoteWebDriver driver) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementToClick);
		} catch (Exception e) {
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "Error while scrollToElement", e);
			getTest().get().fail("scrollToElement:  Scroll to Element failed");
			throw e;
		}
	}

	/**
	 * Method to clear browsing data
	 *
	 * @param driver Pass the driver
	 */
	public static void ClearBrowsingData(RemoteWebDriver driver) {
		try {
			logger.info("clearng the browser");
			driver.get("chrome://settings/clearBrowserData");
			// WebDriverWait wait = new WebDriverWait(driver, 60);
			// WebElement element = driver.findElement(By.cssSelector("*/deep/
			// #clearBrowsingDataConfirm"));
			// wait.until(ExpectedConditions.visibilityOf(element));
			// element.click();
			// wait for the button to be gone before returning
			// wait.until(ExpectedConditions.invisibilityOf(element));
			Actions action = new Actions(driver);
			Thread.sleep(5000);
			action.sendKeys(Keys.ENTER).build().perform();
			Thread.sleep(5000);
			logger.info("clearng the browser done");
		} catch (Exception e) {
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + "ClearBrowsingData", e);
		}
	}

	/**
	 * Method to scroll into the view of a web element
	 *
	 * @param elementToClick pass the element which need to scroll and click
	 * @param driver         pass the driver
	 */
	public static synchronized void scrollToWebElement(WebElement elementToClick, RemoteWebDriver driver) {
		Point hoverItem = elementToClick.getLocation();
		((JavascriptExecutor) driver).executeScript("return window.title;");
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + (hoverItem.getY() - 400) + ");");
	}

	/**
	 * Method to check value not null
	 *
	 * @param storeVar    pass the store variable
	 * @param element     pass the element for which you want to check the value
	 * @param elementName pass the element name for logging purpose
	 * @param driver      pass the driver
	 */
	public static synchronized void checkValueNotNull(String storeVar, WebElement element, String elementName,
			RemoteWebDriver driver) {
		try {
			logger.info("Check for the element");
			if (!(storeVar.equalsIgnoreCase("") || storeVar == null)) {
				logger.info("line 3511 --inside the if where storevariable is not null");
				dictionary.put(storeVar, element.getText());
				logger.info("### Check for Text: Target element verification first:" + element);
				logger.info("### @@@ The element to be found:" + element);
			}
			String textinelement = element.getAttribute("value");
			logger.info("text is " + textinelement);
			if (textinelement != null && !("".equals(textinelement))) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
						"border: 5px solid red;");
				logger.info("### The Element is verified successfully");
				getTest().get().pass(elementName + "Element is verified successfully",
						ExtentScreenCapture.captureSrceenPass("checkForElement", driver));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "border: none;");
			} else {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
						"border: 5px solid red;");
				logger.info("### The Element is not verified successfully");
				getTest().get().fail(elementName + "Element is not verified successfully",
						ExtentScreenCapture.captureSrceenFail("checkForElement", driver));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "border: none;");
			}
		} catch (Exception e) {
			logger.error("### Error occured while checking the element " + elementName);
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + " Error in checkForElement", e);
			getTest().get().fail("Error occured while checking the element ",
					ExtentScreenCapture.captureSrceenFail("checkForElement", driver));
		}
	}

	/**
	 * Method to check text not null
	 *
	 * @param storeVar    Pass the store variable
	 * @param element     Pass the element for which you want to check the value
	 * @param elementName Pass the element name for logging purpose
	 * @param driver      Pass the driver
	 */
	public static synchronized void checkTextNotNull(String storeVar, WebElement element, String elementName,
			RemoteWebDriver driver) {
		try {
			logger.info("Check for the element");
			if (!(storeVar.equalsIgnoreCase("") || storeVar == null)) {
				logger.info("line 3511 --inside the if where storevariable is not null");
				dictionary.put(storeVar, element.getText());
				logger.info("### Check for Text: Target element verification first:" + element);
				logger.info("### @@@ The element to be found:" + element);
			}
			String textinelement = element.getText();
			System.out.println("text is " + textinelement);
			if (textinelement != null && !("".equals(textinelement))) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
						"border: 5px solid red;");
				logger.info("### The Element is verified successfully");
				getTest().get().pass(elementName + "Element is verified successfully",
						ExtentScreenCapture.captureSrceenPass("checkForElement", driver));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "border: none;");
			} else {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
						"border: 5px solid red;");
				logger.info("### The Element is not verified successfully");
				getTest().get().fail(elementName + "Element is not verified successfully",
						ExtentScreenCapture.captureSrceenFail("checkForElement", driver));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "border: none;");
			}
		} catch (Exception e) {
			logger.error("### Error occured while checking the element " + elementName);
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + " Error in checkForElement", e);
			getTest().get().fail("Error occured while checking the element ",
					ExtentScreenCapture.captureSrceenFail("checkForElement", driver));
		}
	}

	/**
	 * Method to click on an alert button
	 *
	 * @param confirmationMessage pass the confirmation message
	 * @param elementName         pass the element where need to click
	 * @param driver              pass the driver
	 */
	public static synchronized String clickOnAlert(String confirmationMessage, String elementName,
			RemoteWebDriver driver) {
		String alertText = null;
		try {
			logger.info("Clicking on Alert");
			Alert alert = driver.switchTo().alert();
			if (confirmationMessage.equalsIgnoreCase("Ok")) {
				alert.accept();
				alertText = alert.getText();
				logger.info("Ok Clicked Successfully");
				getTest().get().pass(elementName + "Element is Selected successfully",
						ExtentScreenCapture.captureSrceenPass("clickOnAlert", driver));
			} else if (confirmationMessage.equalsIgnoreCase("Cancel")) {
				alert.dismiss();
				logger.info("Cancel Clicked Successfully");
				getTest().get().pass(elementName + "Element is selected successfully",
						ExtentScreenCapture.captureSrceenFail("clickOnAlert", driver));
			}
		} catch (Exception e) {
			logger.error("### Error occured while clicking the element " + elementName);
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + " Error in clickOnAlert", e);
			getTest().get().fail("Error occured while clicking the element ",
					ExtentScreenCapture.captureSrceenFail("clickOnAlert", driver));
		}
		return alertText;
	}

	/**
	 * Method to scroll in Android (mobile)
	 *
	 * @param driver  Pass the driver
	 * @param seconds Pass the time for which duration you need to scroll
	 */
	public static synchronized void scroll(MobileDriver driver, int seconds) {
		try {
			logger.info("Scrolling");
			Dimension size = driver.manage().window().getSize();
			logger.info("size ---" + size + "::::;" + size.height + ":::::" + size.width);
			int starty = (int) (size.height * 0.50);
			int endy = (int) (size.height * 0.20);
			int startx = size.getWidth() / 2;
			logger.info(startx + "::::::" + starty + "::::::" + endy + "dfugv");
			TouchAction action = new TouchAction(driver);
			action.press(PointOption.point(startx, starty))
			.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(seconds)))
			.moveTo(PointOption.point(startx, endy)).release().perform();
		} catch (Exception e) {
			logger.info("Not Scrolled");
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + "\n"
					+ ProjectConst.ELEMENTNOTFOUNDEXCEPTIONTXT.getMsg(), e);
		}
	}

	/**
	 * Method to scroll/swipe Up Down left right in mobile
	 *
	 * @param driver    Pass the instance of mobile driver
	 * @param duration  Pass the time for which duration you need to scroll
	 * @param direction Pass the direction
	 */
	public static synchronized void scrollUDLR(MobileDriver driver, int duration, String direction) {
		try {
			logger.info("Scrolling UDLR");
			Thread.sleep(500);
			Dimension size = driver.manage().window().getSize();
			logger.info("size ---" + size + "::::;" + size.height + ":::::" + size.width);
			TouchAction action = new TouchAction(driver);
			int starty = 0;
			int endy = 0;
			int startx = 0;
			switch (direction) {
			case "R":
				starty = (int) (size.height * 0.50);
				endy = (int) (size.width * 0.70);
				startx = size.getWidth() / 2;
				break;
			case "L":
				starty = (int) (size.height * 0.50);
				endy = (int) (size.width * 0.20);
				startx = size.getWidth() / 2;
				break;
			case "U":
				starty = (int) (size.height * 0.50);
				endy = (int) (size.height * 0.20);
				startx = size.getWidth() / 2;
				break;
			case "D":
				starty = (int) (size.height * 0.50);
				endy = (int) (size.height * 0.70);
				startx = size.getWidth() / 2;
				break;
			}
			logger.info(startx + "::::::" + starty + "::::::" + endy + "dfugv");
			action.press(PointOption.point(startx, starty))
			.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(duration)))
			.moveTo(PointOption.point(startx, endy)).release().perform();
		} catch (Exception e) {
			logger.info("Not Scrolled");
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + "\n"
					+ ProjectConst.ELEMENTNOTFOUNDEXCEPTIONTXT.getMsg(), e);
		}
	}

	public static synchronized void longscrollUDLR(MobileDriver driver, int duration, String direction) {
		try {
			logger.info("Scrolling UDLR");
			Thread.sleep(100);
			Dimension size = driver.manage().window().getSize();
			logger.info("size ---" + size + "::::;" + size.height + ":::::" + size.width);
			TouchAction action = new TouchAction(driver);
			int starty = 0;
			int endy = 0;
			int startx = 0;
			switch (direction) {
				case "R":
					starty = (int) (size.height * 0.50);
					endy = (int) (size.width * 0.70);
					startx = size.getWidth() / 2;
					break;
				case "L":
					starty = (int) (size.height * 0.50);
					endy = (int) (size.width * 0.20);
					startx = size.getWidth() / 2;
					break;
				case "U":
					starty = (int) (size.height * 0.50);
					endy = (int) (size.height * 0.20);
					startx = size.getWidth() / 2;
					break;
				case "D":
					starty = (int) (size.height * 0.50);
					endy = (int) (size.height * 0.85);
					System.out.println("endy 0.85");
					startx = size.getWidth() / 2;
					break;
			}
			logger.info(startx + "::::::" + starty + "::::::" + endy + "dfugv");
			action.press(PointOption.point(startx, starty))
					.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(duration)))
					.moveTo(PointOption.point(startx, endy)).release().perform();
		} catch (Exception e) {
			logger.info("Not Scrolled");
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + "\n"
					+ ProjectConst.ELEMENTNOTFOUNDEXCEPTIONTXT.getMsg(), e);
		}
	}

	public static synchronized void scrollUDLRFromBelowHalfScreen(MobileDriver driver, int duration, String direction) {
		try {
			logger.info("Scrolling UDLR");
			Thread.sleep(1000);
			Dimension size = driver.manage().window().getSize();
			logger.info("size ---" + size + "::::;" + size.height + ":::::" + size.width);
			TouchAction action = new TouchAction(driver);
			int starty = 0;
			int endy = 0;
			int startx = 0;
			switch (direction) {
				case "R":
					starty = (int) (size.height * 0.50);
					endy = (int) (size.width * 0.70);
					startx = size.getWidth() / 2;
					break;
				case "L":
					starty = (int) (size.height * 0.50);
					endy = (int) (size.width * 0.20);
					startx = size.getWidth() / 2;
					break;
				case "U":
					starty = (int) (size.height * 0.90);
					endy = (int) (size.height * 0.60);
					startx = size.getWidth() / 2;
					break;
				case "D":
					starty = (int) (size.height * 0.70);
					endy = (int) (size.height * 0.90);
					startx = size.getWidth() / 2;
					break;
			}
			logger.info(startx + "::::::" + starty + "::::::" + endy + "dfugv");
			action.press(PointOption.point(startx, starty))
					.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(duration)))
					.moveTo(PointOption.point(startx, endy)).release().perform();
			Thread.sleep(1000);
		} catch (Exception e) {
			logger.info("Not Scrolled");
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + "\n"
					+ ProjectConst.ELEMENTNOTFOUNDEXCEPTIONTXT.getMsg(), e);
		}
	}



	/*public void swipeScreenWithLogs( MobileDriver driver, Direction dir) {
		System.out.println("swipeScreen(): dir: '" + dir + "'"); // always log your actions

		// Animation default time:
		//  - Android: 300 ms
		//  - iOS: 200 ms
		// final value depends on your app and could be greater
		final int ANIMATION_TIME = 300; // ms

		final int PRESS_TIME = 300; // ms

		int edgeBorder = 10; // better avoid edges
		Point pointStart, pointEnd;
		PointOption pointOptionStart, pointOptionEnd;

		// init screen variables
		Dimension dims = driver.manage().window().getSize();

		// init start point = center of screen
		pointStart = new Point(dims.width / 2, dims.height / 2);

		switch (dir) {
			case DOWN: // center of footer
				pointEnd = new Point(dims.width / 2, dims.height - edgeBorder);
				break;
			case UP: // center of header
				pointEnd = new Point(dims.width / 2, edgeBorder);
				break;
			case LEFT: // center of left side
				pointEnd = new Point(edgeBorder, dims.height / 2);
				break;
			case RIGHT: // center of right side
				pointEnd = new Point(dims.width - edgeBorder, dims.height / 2);
				break;
			default:
				throw new IllegalArgumentException("swipeScreen(): dir: '" + dir.toString() + "' NOT supported");
		}

		// execute swipe using TouchAction
		pointOptionStart = PointOption.point(pointStart.x, pointStart.y);
		pointOptionEnd = PointOption.point(pointEnd.x, pointEnd.y);
		System.out.println("swipeScreen(): pointStart: {" + pointStart.x + "," + pointStart.y + "}");
		System.out.println("swipeScreen(): pointEnd: {" + pointEnd.x + "," + pointEnd.y + "}");
		System.out.println("swipeScreen(): screenSize: {" + dims.width + "," + dims.height + "}");
		try {
			new TouchAction(driver)
					.press(pointOptionStart)
					// a bit more reliable when we add small wait
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
					.moveTo(pointOptionEnd)
					.release().perform();
		} catch (Exception e) {
			System.err.println("swipeScreen(): TouchAction FAILED\n" + e.getMessage());
			return;
		}

		// always allow swipe action to complete
		try {
			Thread.sleep(ANIMATION_TIME);
		} catch (InterruptedException e) {
			// ignore
		}
	}*/

	/**
	 * Method to scroll/swipe Up Down left right in mobile
	 *
	 * @param driver    Pass the instance of mobile driver
	 * @param duration  Pass the time for which duration you need to scroll
	 * @param direction Pass the direction
	 */
	public static synchronized void scrollUDLRMobileApp(MobileDriver driver, int duration, String direction) {
		try {
			logger.info("Scrolling UDLR");
			Thread.sleep(1000);
			Dimension size = driver.manage().window().getSize();
			logger.info("size ---" + size + "::::;" + size.height + ":::::" + size.width);
			TouchAction action = new TouchAction(driver);
			int starty = 0;
			int endy = 0;
			int startx = 0;
			switch (direction) {
			case "R":
				starty = (int) (size.height * 0.50);
				endy = (int) (size.width * 0.70);
				startx = size.getWidth() / 2;
				break;
			case "L":
				starty = (int) (size.height * 0.50);
				endy = (int) (size.width * 0.20);
				startx = size.getWidth() / 2;
				break;
			case "U":
				starty = (int) (size.height * 0.60);
				endy = (int) (size.height * 0.30);
				startx = size.getWidth() / 2;
				break;
			case "D":
				starty = (int) (size.height * 0.50);
				endy = (int) (size.height * 0.70);
				startx = size.getWidth() / 2;
				break;
			}
			logger.info(startx + "::::::" + starty + "::::::" + endy + "dfugv");
			action.press(PointOption.point(startx, starty))
			.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(duration)))
			.moveTo(PointOption.point(startx, endy)).release().perform();
		} catch (Exception e) {
			logger.info("Not Scrolled");
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + "\n"
					+ ProjectConst.ELEMENTNOTFOUNDEXCEPTIONTXT.getMsg(), e);
		}
	}

	/**
	 * Method to scroll/swipe Up Down left right in mobile
	 *
	 * @param driver    Pass the instance of mobile driver
	 * @param duration  Pass the time for which duration you need to scroll
	 * @param direction Pass the direction
	 */
	public static synchronized void scrollUDLRShort(MobileDriver driver, int duration, String direction) {
		try {
			logger.info("Scrolling UDLR");
			Thread.sleep(1000);
			Dimension size = driver.manage().window().getSize();
			logger.info("size ---" + size + "::::;" + size.height + ":::::" + size.width);
			TouchAction action = new TouchAction(driver);
			int starty = 0;
			int endy = 0;
			int startx = 0;
			switch (direction) {
			case "R":
				starty = (int) (size.height * 0.50);
				endy = (int) (size.width * 0.70);
				startx = size.getWidth() / 2;
				break;
			case "L":
				starty = (int) (size.height * 0.50);
				endy = (int) (size.width * 0.20);
				startx = size.getWidth() / 2;
				break;
			case "U":
				starty = (int) (size.height * 0.50);
				endy = (int) (size.height * 0.40);
				startx = size.getWidth() / 2;
				break;
			case "D":
				starty = (int) (size.height * 0.50);
				endy = (int) (size.height * 0.70);
				startx = size.getWidth() / 2;
				break;
			}
			logger.info(startx + "::::::" + starty + "::::::" + endy + "dfugv");
			action.press(PointOption.point(startx, starty))
			.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(duration)))
			.moveTo(PointOption.point(startx, endy)).release().perform();
		} catch (Exception e) {
			logger.info("Not Scrolled");
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + "\n"
					+ ProjectConst.ELEMENTNOTFOUNDEXCEPTIONTXT.getMsg(), e);
		}
	}

	/**
	 * Method to scroll/swipe Up Down left right in mobile
	 *
	 * @param driver    Pass the instance of mobile driver
	 * @param duration  Pass the time for which duration you need to scroll
	 * @param direction Pass the direction
	 */
	public static synchronized void scrollUDLRLong(MobileDriver driver, int duration, String direction) {
		try {
			logger.info("Scrolling UDLR");
			Thread.sleep(1000);
			Dimension size = driver.manage().window().getSize();
			TouchAction action = new TouchAction(driver);
			int starty = 0;
			int endy = 0;
			int startx = 0;
			switch (direction) {
			case "R":
				starty = (int) (size.height * 0.50);
				endy = (int) (size.width * 0.60);
				startx = size.getWidth() / 2;
				break;
			case "L":
				starty = (int) (size.height * 0.50);
				endy = (int) (size.width * 0.30);
				startx = size.getWidth() / 2;
				break;
			case "U":
				starty = (int) (size.height * 0.80);
				endy = (int) (size.height * 0.30);
				startx = size.getWidth() / 2;
				break;
			case "D":
				starty = (int) (size.height * 0.30);
				endy = (int) (size.height * 0.80);
				startx = size.getWidth() / 2;
				break;
			}
			action.press(PointOption.point(startx, starty))
			.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(duration)))
			.moveTo(PointOption.point(startx, endy)).release().perform();
		} catch (Exception e) {
			logger.info("Not Scrolled");
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + "\n"
					+ ProjectConst.ELEMENTNOTFOUNDEXCEPTIONTXT.getMsg(), e);
		}
	}

	/**
	 * Mobile scroll for using JavaScript
	 *
	 * @param driver
	 * @param element
	 */
	public static synchronized void jsMobileScroll(MobileDriver driver, MobileElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "down");
		scrollObject.put("element", ((RemoteWebElement) element).getId());
		js.executeScript("mobile: scroll", scrollObject);
	}

	/**
	 * Method to hide keyboard in mobile
	 *
	 * @param driver pass the driver
	 */
	public static synchronized void hideKeyboard(MobileDriver driver) {
		try {
			logger.info("Hide the keyboard");
			driver.hideKeyboard();
		} catch (Exception e) {
			logger.error("Exception occured while hiding the Keyboard:");
		}
	}

	/**
	 * Method to get element middle point
	 *
	 * @param element pass the element for which you need the middle point
	 * @return a String
	 */
	public static String getElementMiddlePoint(WebElement element) {
		int length = 0;
		int height;
		int middleY = 0;
		try {
			logger.info("Get element from Middle point");
			Point point = element.getLocation();
			length = element.getSize().getWidth();
			height = element.getSize().getHeight();
			int getY = point.getY();
			middleY = (int) (getY + height * 1.5) / 2;
		} catch (Exception e) {
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + ProjectConst.ELEMENTNOTFOUNDEXCEPTIONTXT, e);
		}
		return length + "" + "" + "|" + middleY + "";
	}

	/**
	 * Method to clear data
	 *
	 * @param element Pass the element against which the data has to be cleared
	 */
	public synchronized static void clearData(WebElement element) {
		try {
			logger.info("Clearing the log file");
			element.clear();
		} catch (Exception e) {
			logger.info("Error clearing data");
			throw e;
		}
	}

	public HashMap<String, String> getDetailsKarate() {
		HashMap<String, String> data = new HashMap<String, String>();
		StringBuffer formatSecurityId = new StringBuffer();
		StringBuffer formatYesId = new StringBuffer();
		String dateAndTime = "";
		String dataTime[] = null;
		Date date = new Date();
		dateAndTime = new Timestamp(date.getTime()).toString();
		dataTime = dateAndTime.split(" ");
		formatSecurityId.append("ca");
		formatSecurityId.append(dataTime[0].split("-")[2]);
		formatSecurityId.append(dataTime[0].split("-")[1]);
		formatSecurityId.append(dataTime[0].split("-")[0]);
		formatSecurityId.append(dataTime[1].split(":")[0]);
		formatSecurityId.append(dataTime[1].split(":")[1]);
		formatSecurityId.append(dataTime[1].split(":")[2].split("\\.")[0]);
		formatSecurityId.append(dataTime[1].split("\\.")[1]);
		data.put("customerAccountSecurityId", formatSecurityId.toString());
		logger.info(data);
		return data;
	}

	public static synchronized String extractDigits(String src) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < src.length(); i++) {
			char c = src.charAt(i);
			if (Character.isDigit(c)) {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	/**
	 * Method to check element is not present for web
	 *
	 * @param elementXpath Pass the elements Xpath
	 * @param elementName  Pass the element name
	 * @param driver       Pass the driver
	 */
	public static synchronized void checkElementNotPresent(String elementXpath, String elementName,
			RemoteWebDriver driver) {
		try {
			logger.info("Check for the element:" + elementName);
			if (!(driver.findElements(By.xpath(elementXpath)).size() > 0)) {
				getTest().get().pass(elementName + "Element is not present - successful",
						ExtentScreenCapture.captureSrceenPass("checkElementNotPresent", driver));
			} else {
				WebElement element = driver.findElement(By.xpath(elementXpath));
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
						"border: 5px solid red;");
				logger.info("### The Element is present -  unsuccessful");
				getTest().get().fail(elementName + "The Element is present -  unsuccessful",
						ExtentScreenCapture.captureSrceenFail("checkElementNotPresent", driver));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "border: none;");
			}
		} catch (NoSuchElementException e) {
			logger.error("### Error occured while checking the element " + elementName);
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + " " + " Error in checkElementNotPresent", e);
			getTest().get().fail("Error occured while checking the element ",
					ExtentScreenCapture.captureSrceenFail("checkForElement", driver));
			throw e;
		}
	}

	/**
	 * Method used to customised the report while creating a label in test case id
	 * level
	 *
	 * @param applicationName pass application name
	 * @param JiraID          pass jira id of the test case
	 */
	public static void customisedReport(String applicationName, String JiraID) {
		m = MarkupHelper.createLabel("<a href=\"https://ytlcomms.jira.com/browse/" + JiraID + "\">" + JiraID + "</a>",
				ExtentColor.WHITE);
		getTest().get().info(m);
		String[][] data = { { applicationName, JiraID } };
		m = MarkupHelper.createTable(data);
		getTest().get().assignCategory(m.getMarkup());
	}

	public static synchronized boolean clickByXpathonNewWindow(WebElement elementToClick, String elementName,
			RemoteWebDriver driver) {
		boolean status = false;
		try {
			logger.info("Click by xpath on:" + elementName);
			elementToClick.click();
			status = true;
		} catch (UnhandledAlertException e) {
			clickOnAlert("Ok", "Ok on alert", driver);
			getTest().get().pass(elementName + " Clicked Successfully",
					ExtentScreenCapture.captureSrceenPass(elementName, driver));
		} catch (Exception e) {
			logger.error(ProjectConst.EXCEPTIONTEXTMETHOD.getMsg() + "\n"
					+ ProjectConst.ELEMENTNOTFOUNDEXCEPTIONTXT.getMsg(), e);
			getTest().get().fail(elementName + " Click Unsuccessful",
					ExtentScreenCapture.captureSrceenFail(elementName, driver));
		}
		return status;
	}

	/**
	 * To get the attribute details of the element
	 *
	 * @param element     pass the element
	 * @param elementName pass the element name
	 * @param attribute   get the attribute which we need to take
	 * @param driver      pass the driver
	 * @return returns the value
	 */
	public static String getElementAttributeValue(WebElement element, String elementName, String attribute,
			RemoteWebDriver driver) {
		String status = "false";
		try {
			status = element.getAttribute(attribute);
		} catch (Exception e) {
			logger.error("Exception occured", e);
			status = "false";
		}
		return status;
	}

	public static String replaceParanthesis(String expression) 
	{
		String value;
		String replaceValue;
		String dicValue;
		String result = expression;
		try {
			Matcher match = Pattern.compile("\\$\\{([^}]+)\\}").matcher(expression);
			// Matcher m = p.matcher(expression);
			while(match.find()){
				value = match.group(1);
				replaceValue = match.group();
				dicValue = dictionary.get(value); 
				if (dicValue!=null) {
					//replaceValue = "${"+value+"}";
					
					result = result.replaceAll(Pattern.quote(replaceValue), dicValue);
					// return replaceParanthesis(result);
				}

			}
		}
		catch(Exception e){
			logger.error("Exception occured: " + e);
			result = null;
		}

		return result;
	}

	public static String calculateBalance(String value1, String value2) 
	{
		float intValue1 = Float.parseFloat(value1.trim());
		float intValue2 = Float.parseFloat(value2.trim());
		float calculate = 0;
		String result = null;

		try {
			calculate = (intValue1 -  (intValue2));
			DecimalFormat formatter = new DecimalFormat("#0.00");
			//calculate = formatter.format(calculate);
			result = String.valueOf(formatter.format(calculate));


		} catch (Exception e) {
			logger.error("Exception occured", e);

		}

		return result;
	}


	public void doSignature(AndroidDriver driver) {
		try {
			Dimension size = driver.manage().window().getSize();
			int starty = (int) (size.height * 0.80);
			int endy = (int) (size.height * 0.30);
			int startx = size.getWidth() / 2;

			TouchAction touchAction = new TouchAction(driver);
			touchAction.press(PointOption.point(startx, starty)).moveTo(PointOption.point(startx, endy)).perform();

		} catch (Exception e) {
			logger.error(ProjectConst.VALUE, e);
		}
	}
	
	public static String getBundlePurchaseDate() 
	{

		String bundlePurchaseDate=null;
	
		try {
			Date date = new Date();  
		    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		    bundlePurchaseDate= formatter.format(date);  
		   


			Log.info(bundlePurchaseDate);


		} catch (Exception e) {
			logger.error("Exception occured", e);

		}

		return bundlePurchaseDate;
	}
	
	public static String changeDateFormat(String dateValue,String newFormat) {
		String formattedDate=null;
		
		try {
			
		    SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy"); 
		    SimpleDateFormat formatter2 = new SimpleDateFormat(newFormat);
		    Date date = formatter.parse(dateValue);
		    formattedDate= formatter2.format(date);  
		    Log.info(formattedDate);


		} catch (Exception e) {
			logger.error("Exception occured", e);

		}

		return formattedDate;
	}



	public static String changeDateFormat(Date dateValue,String newFormat) {
		String formattedDate=null;

		try {
			SimpleDateFormat formatter = new SimpleDateFormat(newFormat);
			formattedDate= formatter.format(dateValue);
			Log.info(formattedDate);
		} catch (Exception e) {
			logger.error("Exception occurred", e);

		}

		return formattedDate;
	}


	public static String calculateBundleExpiry(String bundleName) 
	{

		String bundleExpiryDate=null;
		String validity;
		try {
			validity =	ApplicationUtil.getPrepaidBundleValidity(bundleName);
			if(validity != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.DATE, Integer.parseInt(validity));
				bundleExpiryDate = dateFormat.format(c.getTime());
			}
			Log.info(bundleExpiryDate);


		} catch (Exception e) {
			logger.error("Exception occured", e);

		}

		return bundleExpiryDate;
	}

	public static String editUMBActualMessage(String actualMessage, String category) 
	{


		String finalActualMsg=null;
		try {
			if((MainUtil.dictionary.get("BUNDLE_NAME").contains("UMI"))
					||(MainUtil.dictionary.get("BUNDLE_NAME").contains("MB40"))
					||(MainUtil.dictionary.get("BUNDLE_NAME").contains("MB68"))
					||(MainUtil.dictionary.get("BUNDLE_NAME").contains("MB128"))) {
				String[] arrMsg = actualMessage.split("\\(");
				String[] expiryValue = arrMsg[1].split("\\)");
				String[] dateTime = expiryValue[0].split(" ");

				String[] expiryValue2 = arrMsg[2].split("\\)");
				String[] dateTime2 = expiryValue2[0].split(" ");
				finalActualMsg = arrMsg[0] + dateTime[0] + dateTime[1] + expiryValue[1] + dateTime2[0]+dateTime2[1]+expiryValue2[1];	
				
			}else if((MainUtil.dictionary.get("BUNDLE_NAME").contains("U25"))||(MainUtil.dictionary.get("BUNDLE_NAME").contains("U35"))){
				String[] arrMsg = actualMessage.split("\\(");
				String[] expiryValue = arrMsg[1].split("\\)");
				String[] dateTime = expiryValue[0].split(" ");

				finalActualMsg = arrMsg[0] + dateTime[0] + dateTime[1] + expiryValue[1]+"("+arrMsg[2] ;

			}else {
				String[] arrMsg = actualMessage.split("\\(");
				String[] expiryValue = arrMsg[1].split("\\)");
				String[] dateTime = expiryValue[0].split(" ");
				finalActualMsg = arrMsg[0] + dateTime[0] + dateTime[1] + expiryValue[1];

			}



			Log.info(finalActualMsg);


		} catch (Exception e) {
			logger.error("Exception occured", e);
			
		}
		
		return finalActualMsg;
	}


	public static synchronized boolean checkMobileElementDisplayedByXPath(String xpath, String ElementName,
																		  MobileDriver driver) {
		boolean ElementDisplayed = false;
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		try {
			if (driver.findElements(By.xpath(xpath)).size() > 0) {
				ElementDisplayed = true;
			}

		} catch (Exception ex) {
			ElementDisplayed = false;
			System.out.println(ElementName+" is not present");
			throw ex;
		} finally {
			driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		}
		return ElementDisplayed;
	}

	public static void captureAppScreenshot(String sMessage, RemoteWebDriver driver){
		getTest().get().info(sMessage,ExtentScreenCapture.captureSrceenPass(sMessage, driver));
	}

	public void setDriverImplicitWait(int timeInSeconds, RemoteWebDriver driver) {
		driver.manage().timeouts().implicitlyWait(timeInSeconds, TimeUnit.SECONDS);
	}


	public void horizontalScrollAllServices(AndroidDriver driver)
	{

		List<AndroidElement> e=driver.findElements(MobileBy.xpath("//*[contains(@content-desc,'services_')]"));

		AndroidElement element1=e.get(0);
		AndroidElement element3=e.get(2);

		int midOfYCordinate=element1.getLocation().y + (element1.getSize().height/2);

		int firstElementXCordinate=element1.getLocation().x;
		int thirdElementXCordinate=element3.getLocation().x;

		TouchAction touch = new TouchAction(driver);

		touch.press(PointOption.point(thirdElementXCordinate,midOfYCordinate))
		     .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
				.moveTo(PointOption.point(firstElementXCordinate,midOfYCordinate))
				.release()
				.perform();

	}

	public static void pressKeyPageDown(RemoteWebDriver driver) throws InterruptedException {
		Actions action = new Actions(driver);
		action.sendKeys(Keys.PAGE_DOWN).build().perform();
		Thread.sleep(1000);
	}

	public void pressKeyTAB(RemoteWebDriver driver)
	{
		Actions action = new Actions(driver);
		action.sendKeys(Keys.TAB).build().perform();
	}

	public void pressKeyENTER(RemoteWebDriver driver)
	{
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ENTER).build().perform();
	}

	public void switchToTab(int index, RemoteWebDriver driver){

		ArrayList tabs;
		tabs = new ArrayList(driver.getWindowHandles());
		logger.info(tabs.size());
		driver.switchTo().window((String) tabs.get(index));
	}

	public void closeBrowserTab(int index, RemoteWebDriver driver){

		ArrayList tabs;
		tabs = new ArrayList(driver.getWindowHandles());
		logger.info(tabs.size());
		driver.switchTo().window((String) tabs.get(index));
		driver.close();
		driver.switchTo().window((String) tabs.get(0));
	}

	public void waitForMessageToDisappear(String sMessage, RemoteWebDriver driver){
		AppWait.waitForElementToDisappear(driver, By.xpath("//*[contains(text(),'"+sMessage+"')]"),30);
	}


	public static String editUMBCRPList(String responseMessage,String USSDCode)
	{

		//String bundleName = MainUtil.dictionary.get("BUNDLE_NAME");
		String finalActualMsg=null;
		try {
			//if(bundleName.contains("UMI") || bundleName.contains("MB40") || bundleName.contains("MB68") || bundleName.contains("MB128")  ) {
			String[] arr_RatePlans = responseMessage.split("\\r?\\n");
			for(String ratePlan_Value:arr_RatePlans) {
				if(ratePlan_Value.contains(MainUtil.dictionary.get("NEWPLAN_NAME"))){
					String[] arr_RPCode = ratePlan_Value.split(" ");
					MainUtil.dictionary.put("USSD_CODE", USSDCode.substring(1, (USSDCode.length())-1) + "*" + arr_RPCode[0] + "*1#");
					break;
				}
			}



		} catch (Exception e) {
			logger.error("Exception occured", e);

		}
		return finalActualMsg;
	}

	public static void scrollToBottomOfTheWebPage( RemoteWebDriver driver){
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public static void javaScriptEnterText(WebElement element,RemoteWebDriver driver, String inputText){
		((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value','"+inputText+"')", element);
	}

	public static String getCurrentDatePlusDays(int additionalDays){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Using today's date
		c.add(Calendar.DATE, 5); // Adding 5 days
		String output = sdf.format(c.getTime());
		return output;
		}

	public static String getRemainingDaysInCurrentMonth(){
		Calendar cal = Calendar.getInstance();
		int totalDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		String str = formatter.format(date);
		String currentDate[] = str.split("/");
		System.out.print("Current date: "+currentDate[0]);
		int todaysdate = Integer.parseInt(currentDate[0]);
		int remainingDays = totalDays-todaysdate;
		System.out.print("Remaining days: "+String.valueOf(remainingDays));
		return String.valueOf(remainingDays);
	}

	public void uploadFile(String filePath, WebElement element) throws InterruptedException {
		//click on ‘Choose file’ to upload the desired file
		Thread.sleep(3000);
		element.sendKeys(filePath); //Uploading the file using sendKeys
		logger.info("File is Uploaded Successfully");
	}

	public static String calculateExpiryDateAfterCreditTransfer( String endDate, String transferAmount) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date=null;
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Using today's date
		c.add(Calendar.DATE, Integer.parseInt(transferAmount)); // Adding days

		Date date1 = sdf.parse(sdf.format(c.getTime()));
		Date date2 = sdf.parse(endDate);

		System.out.println("date1 : " + sdf.format(date1));
		System.out.println("date2 : " + date2);

		int result = date1.compareTo(date2);
		System.out.println("result: " + result);

		if (result == 0) {
			System.out.println("end Date is equal to calculated end date");
			date = endDate;
		} else if (result > 0) {
			System.out.println("Calculated end date is after actual end date");
			date = String.valueOf(date1);
		} else if (result < 0) {
			System.out.println("Calculated end date is before actual end date");
			date = endDate;
		} else {
			System.out.println("How to get here?");
		}
		return date;
	}
}
