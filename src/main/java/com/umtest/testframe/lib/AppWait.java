package com.umtest.testframe.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;



public class AppWait {
    private static Wait<RemoteWebDriver> wait;
    private static Logger logger = LogManager.getLogger(AppWait.class);
    private static final int DEFAULT_WAIT_4_ELEMENT = 10;
    private static final int DEFAULT_WAIT_4_PAGE = 20;

    private AppWait() {
        logger.debug("Inside the Appwait class");
    }

    /**
     * This method returns instance of webdriver fluent wait
     *
     * @param driver Pass the driver
     * @return wait
     */
    private static Wait<RemoteWebDriver> getWait(RemoteWebDriver driver) {
        if (wait == null) {
            wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(TimeoutException.class);
        }
        return wait;
    }

    /*private static Wait<RemoteWebDriver> getWait(RemoteWebDriver driver) {
        if (wait == null) {
            wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(20)).pollingEvery(Duration.ofSeconds(3))
                    .ignoring(NoSuchElementException.class);
        }
        return wait;
    }*/

    /**
     * This method is to wait for element to be visible
     *
     * @param driver  Pass the driver
     * @param element Pass the element for which you want to wait for visibility
     * @return element
     */
    public static synchronized WebElement waitForElementForVisibility(RemoteWebDriver driver, WebElement element) {
        try {
            logger.info("Wait for element " + element + " visibility");
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            getWait(driver).until(ExpectedConditions.visibilityOf(element));
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_ELEMENT, TimeUnit.SECONDS);
        } catch (NoSuchElementException e) {
            logger.error(MainUtil.ProjectConst.ELEMENTNOTFOUNDEXCEPTIONTXT, e);
        }
        return element;
    }

    /**
     * This method is to wait for element to be visible
     *
     * @param driver Pass the driver
     * @param xpath  Pass the xpath for which you want to wait for visibility
     * @return webelement
     */
    public static synchronized WebElement waitForElementForVisibility(RemoteWebDriver driver, String xpath) {
        WebElement element = null;
        try {
            logger.info("Wait for element " + element + " visibility");
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            element = getWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_ELEMENT, TimeUnit.SECONDS);
        } catch (NoSuchElementException e) {
            logger.error(MainUtil.ProjectConst.ELEMENTNOTFOUNDEXCEPTIONTXT, e);
        }
        return element;
    }

    /**
     * This method is to wait for element to be clickable
     *
     * @param driver  Pass the driver
     * @param element Pass the element for which you want to wait to be clickable
     * @return element
     */
    public static synchronized WebElement waitForElementToBeClickable(RemoteWebDriver driver, WebElement element) {
        try {
            logger.info("Wait for element" + element + " to be clickable");
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            getWait(driver).until(ExpectedConditions.elementToBeClickable(element));
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_ELEMENT, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error(MainUtil.ProjectConst.ELEMENTNOTFOUNDEXCEPTIONTXT, e);
        }
        return element;
    }

    /**
     * This method is to wait for element to be clickable
     *
     * @param driver Pass the driver
     * @param xpath  Pass the xpath for which you want to wait to be clickable
     * @return element
     */
    public static synchronized WebElement waitForElementToBeClickable(RemoteWebDriver driver, String xpath) {
        WebElement element = null;
        try {
            logger.info("Wait for element" + element + " to be clickable");
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            element = getWait(driver).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_ELEMENT, TimeUnit.SECONDS);
        } catch (NoSuchElementException e) {
            logger.error(MainUtil.ProjectConst.ELEMENTNOTFOUNDEXCEPTIONTXT, e);
        }
        return element;
    }

    /**
     * This method is to wait for text to be present in element
     *
     * @param driver  Pass the driver
     * @param element Pass the xpath for which we want to check the text
     * @param text    Pass the text we want to compare
     * @return element
     */
    public static synchronized WebElement waitFortextToBePresentInElement(RemoteWebDriver driver, WebElement element,
                                                                          String text) {
        try {
            logger.info("Wait for text to be present in element" + element);
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            getWait(driver).until(ExpectedConditions.textToBePresentInElement(element, text));
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_ELEMENT, TimeUnit.SECONDS);
        } catch (NoSuchElementException e) {
            logger.error(MainUtil.ProjectConst.ELEMENTNOTFOUNDEXCEPTIONTXT, e);
        }
        return element;
    }

    /**
     * This method is to wait for list of web elements
     *
     * @param driver   The driver object to use to perform this operation
     * @param elements Pass the element for which we want to wait
     * @return List
     */
    public static synchronized List<WebElement> waitFluentlyForElements(RemoteWebDriver driver,
                                                                        List<WebElement> elements) {
        try {
            logger.info("Wait fluently for elements");
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            wait.until(ExpectedConditions.visibilityOfAllElements(elements));
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS);
        } catch (NoSuchElementException e) {
            logger.error(e);
        }
        return elements;
    }

    /**
     * This method is to wait for element to be disappear
     *
     * @param driver           The driver object to use to perform this operation
     * @param by               selector to find the element
     * @param timeOutInSeconds The time in seconds to wait until returning a failure
     * @return returns boolean
     */

    /* This method waits for a particular element to disappear */
    public static synchronized boolean waitForElementToDisappear(RemoteWebDriver driver, By by, int timeOutInSeconds) {
        boolean result;
        try {
            logger.info("Wait for element to disappear");
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            result = wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS);
        } catch (NoSuchElementException e) {
            return false;
        }
        return result;
    }

    /**
     * Wait for the element to be present in the DOM, regardless of being
     * displayed or not. And returns the first WebElement using the given
     * method.
     *
     * @param driver           Pass the instance of the remotewebdriver
     * @param by               Pass the element by using By operator
     * @param timeOutInSeconds Enter timeout in seconds
     * @return returns the element after meeting condition
     */
    public static synchronized WebElement waitForElementPresence(RemoteWebDriver driver, final By by, int timeOutInSeconds) {
        WebElement element = null;
        try {
            logger.info("Wait for element presence");
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS);
        } catch (NoSuchElementException e) {
            logger.error(e);
        }
        return element;
    }

    /**
     * Wait for the webelement list to be present in the DOM, regardless of
     * being displayed or not. Returns all elements within the current page DOM.
     *
     * @param driver           Pass the instance of the remotewebdriver
     * @param by               Pass the element by using By operator
     * @param timeOutInSeconds Enter timeout in seconds
     * @return returns the element after meeting condition
     */
    public static synchronized List<WebElement> waitForListElementsPresent(RemoteWebDriver driver, final By by,
                                                                           int timeOutInSeconds) {
        List<WebElement> elements;
        try {
            logger.info("Wait for list elements present");
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.until((new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driverObject) {
                    return areElementsPresent((RemoteWebDriver) driverObject, by);
                }
            }));
            elements = driver.findElements(by);
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); // reset
            // implicitlyWait
            return elements; // return the element
        } catch (NoSuchElementException e) {
            /**/
        }
        return Collections.emptyList();
    }

    /**
     * Wait for an element to appear on the refreshed web-page. And returns the
     * first WebElement using the given method. This method is to deal with
     * dynamic pages. Some sites I (Mark) have tested have required a page
     * refresh to add additional elements to the DOM. Generally you (Chon)
     * wouldn't need to do this in a typical AJAX scenario.
     *
     * @param driver          The driver object to use to perform this element search
     * @param by               selector to find the element
     * @param timeOutInSeconds The time in seconds to wait until returning a failure
     * @return WebElement the first WebElement using the given method, or
     * null(if the timeout is reached)
     */
    public static synchronized WebElement waitForElementRefresh(RemoteWebDriver driver, final By by, int timeOutInSeconds) {
        WebElement element;
        try {
            logger.info("Wait for element refresh");
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
            // implicitlyWait()
            new WebDriverWait(driver, timeOutInSeconds) {
            }.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driverObject) {
                    driverObject.navigate().refresh(); // refresh the page
                    // ****************
                    return isElementPresentAndDisplayed((RemoteWebDriver) driverObject, by);
                }
            });
            element = driver.findElement(by);
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); // reset
            // implicitlyWait
            return element; // return the element
        } catch (NoSuchElementException e) {
            /**/
        }
        return null;
    }

    /**
     * Wait for the Text to be present in the given element, regardless of being
     * displayed or not.
     *
     * @param driver           The driver object to be used to wait and find the element
     * @param by               selector of the given element, which should contain the text
     * @param text             The text we are looking
     * @param timeOutInSeconds The time in seconds to wait until returning a failure
     * @return boolean
     */
    public static synchronized boolean waitForTextPresent(RemoteWebDriver driver, final By by, final String text,
                                                          int timeOutInSeconds) {
        boolean isPresent = false;
        try {
            logger.info("Wait for text present");
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
            // implicitlyWait()
            new WebDriverWait(driver, timeOutInSeconds) {
            }.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driverObject) {
                    return isTextPresent((RemoteWebDriver) driverObject, by, text); // is
                    // the
                    // Text
                    // in the
                    // DOM
                }
            });
            isPresent = isTextPresent(driver, by, text);
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); // reset
            // implicitlyWait
            return isPresent;
        } catch (NoSuchElementException e) {
            /**/
        }
        return false;
    }

    /**
     * @param driver           The driver object to use to perform this element search
     * @param element          selector of the given element, which should contain the text
     * @param text             The text we are looking
     * @param timeOutInSeconds The time in seconds to wait until returning a failure
     * @return boolean
     */
    public static synchronized boolean waitForTextPresent(RemoteWebDriver driver, final WebElement element,
                                                          final String text, int timeOutInSeconds) {
        boolean isPresent;
        try {
            logger.info("Wait for text present");
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            new WebDriverWait(driver, timeOutInSeconds) {
            }.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driverObject) {
                    return isTextPresent(element, text);
                }
            });
            isPresent = isTextPresent(element, text);
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS);
            return isPresent;
        } catch (NoSuchElementException e) {
            /**/
        }
        return false;
    }

    /**
     * This method is to check whether the element located is visible or not
     *
     * @param driver           The driver object to use to perform this element search
     * @param by               selector to find the element
     * @param timeOutInSeconds The time in seconds to wait until returning a failure
     * @return boolean true or false(condition fail, or if the timeout is
     * reached)
     */

    public static synchronized boolean visibilityOfElementPresent(RemoteWebDriver driver, final By by, int timeOutInSeconds) {
        try {
            logger.info("Visibility of element present");
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS);
            return true;
        } catch (TimeoutException e) {
            logger.error("******* Element is not visible *******");
        }
        return false;
    }

    /**
     * @param driver           The driver object to use to perform this element search
     * @param elementName          selector of the given element, which should contain the text
     * @param timeOutInSeconds The time in seconds to wait until returning a failure
     * @return boolean, whether the webelement is present or not
     */
    public static synchronized boolean visibilityOfElementPresent(RemoteWebDriver driver, By by,String elementName,
                                                                  int timeOutInSeconds) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
        // implicitlyWait()
        try {
            logger.info("Visibility of element " + elementName + " present");
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); // reset
            // implicitlyWait
            return true;
        } catch (TimeoutException e) {
            logger.error("******* Element is not visible *******");
        }
        return false;
    }


    /**
     * @param driver           The driver object to use to perform this element search
     * @param element          selector of the given element, which should contain the text
     * @param timeOutInSeconds The time in seconds to wait until returning a failure
     * @return boolean, whether the webelement is present or not
     */
    public static synchronized boolean visibilityOfElementPresent(RemoteWebDriver driver, WebElement element,
                                                                  int timeOutInSeconds) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
        // implicitlyWait()
        try {
            logger.info("Visibility of element " + element + " present");
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.until(ExpectedConditions.visibilityOf(element));
            driver.manage().timeouts().implicitlyWait(timeOutInSeconds, TimeUnit.SECONDS); // reset
            // implicitlyWait
            return true;
        } catch (TimeoutException e) {
            logger.error("******* Element is not visible *******");
        }
        return false;
    }

    /**
     * Waits for the Condition of JavaScript.
     *
     * @param driver           The driver object to be used to wait and find the element
     * @param javaScript       "The javaScript condition we are waiting.
     * @param timeOutInSeconds The time in seconds to wait until returning a failure
     * @return boolean true or false(condition fail, or if the timeout is
     * reached)
     **/
    public static synchronized boolean waitForJavaScriptCondition(RemoteWebDriver driver, final String javaScript, int timeOutInSeconds) {
        boolean jscondition;
        try {
            logger.info("Wait for java scriptcondition");
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
            // implicitlyWait()
            new WebDriverWait(driver, timeOutInSeconds) {
            }.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driverObject) {
                    return (Boolean) ((JavascriptExecutor) driverObject).executeScript(javaScript);
                }
            });
            jscondition = (Boolean) ((JavascriptExecutor) driver).executeScript(javaScript);
            driver.manage().timeouts().implicitlyWait(timeOutInSeconds, TimeUnit.SECONDS); // reset
            // implicitlyWait
            return jscondition;
        } catch (NoSuchElementException e) {
        }
        return false;
    }

    /**
     * Waits for the completion of Ajax jQuery processing by checking "return
     * jQuery.active == 0" condition.
     *
     * @param driver           Pass the driver
     * @param timeOutInSeconds The time in seconds to wait until returning a failure
     * @return returns boolean
     */
    public static synchronized boolean waitForJQueryProcessing(RemoteWebDriver driver, int timeOutInSeconds) {
        boolean jQcondition = false;
        try {
            logger.info("Wait for jquery processing");
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
            // implicitlyWait()
            new WebDriverWait(driver, timeOutInSeconds) {
            }.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driverObject) {
                    return (Boolean) ((JavascriptExecutor) driverObject).executeScript("return jQuery.active == 0");
                }
            });
            jQcondition = (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active == 0");
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS);
            return jQcondition;
        } catch (Exception e) {
        }
        return jQcondition;
    }

    /**
     * Checks if the text is present in the element.
     * @param driver Pass the remotewebdriver
     * @param by Pass the element which you want to search
     * @param text Pass the text you want to check the presence for
     * @return boolean true or false
     */
    private static synchronized boolean isTextPresent(RemoteWebDriver driver, By by, String text) {
        try {
            logger.info("Is text present");
            return driver.findElement(by).getText().contains(text);
        } catch (NullPointerException e) {
            /**/
        }
        return false;
    }

    /**
     * Checks if the text is present in the element.
     * @param element Pass the element
     * @param text    Pass the text against which you want to verify
     * @return boolean true or false
     */
    private static synchronized boolean isTextPresent(WebElement element, String text) {
        try {
            logger.info("Is text present");
            return element.getText().contains(text);
        } catch (NullPointerException e) {
            /**/
        }
        return false;
    }

    /**
     * Checks if the element is in the DOM, regardless of being displayed or not.
     * @param driver The driver object to use to perform this element search
     * @param by     selector to find the element
     * @return boolean true or false
     */
    public static synchronized boolean isElementPresent(RemoteWebDriver driver, By by) {
        try {
            logger.info("Is eleent present");
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            /**/
        }
        return false;
    }

    /**
     * Checks if the List<WebElement> are in the DOM, regardless of being
     * displayed or not.
     *
     * @param driver The driver object to use to perform this element search
     * @param by     selector to find the element
     * @return boolean true or false
     */
    private static synchronized boolean areElementsPresent(RemoteWebDriver driver, By by) {
        try {
            logger.info("Are elements present");
            driver.findElements(by);
            return true;
        } catch (NoSuchElementException e) {
            /**/
        }
        return false;
    }

    /**
     * Checks if the elment is in the DOM and displayed.
     * @param driver Pass the remotewebdriver
     * @param by Pass the element that you want to check the presence for
     * @return boolean true or false
     */
    public static synchronized boolean isElementPresentAndDisplayed(RemoteWebDriver driver, By by) {
        try {
            logger.info("Is eleent present and displayed");
            return driver.findElement(by).isDisplayed();
        } catch (NoSuchElementException e) {
            /**/
        }
        return false;
    }

    /**
     * Checks if the element is in the DOM and displayed.
     *
     * @param webElement Pass the element, which we want to verify that it is present and displayed
     * @return boolean
     */

    public static synchronized boolean isElementPresentAndDisplayed(WebElement webElement) {
        try {
            logger.info("Is eleent present and displayed");
            return webElement.isDisplayed();
        } catch (NoSuchElementException e) {
            /**/
        }
        return false;
    }

    /**
     * Checks if the element is in the DOM
     *
     * @param driver    Pass the driver
     * @param byLocator Pass the locator to find the element
     * @return boolean
     */

    public static synchronized boolean isElementPresent1(RemoteWebDriver driver, By byLocator) {
        if (driver.findElement(byLocator) != null) {
            return true;
        } else {
            return false;
        }
    }
}
