package com.umtest.testframe.lib;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.umtest.testframe.listener.ExtentTestNGITestListener;

public class ExtentScreenCapture {
    private ExtentScreenCapture() {
        /* Adding a private constructor */
    }

    private static Logger logger = LogManager.getLogger(ExtentScreenCapture.class);

    /**
     * This method to capture screenshot for pass
     *
     * @param result Pass the driver(Pass/Fail)
     * @param driver Pass the driver
     * @return returns mediaModel object
     */
    public synchronized static MediaEntityModelProvider captureSrceenPass(ITestResult result, RemoteWebDriver driver) {
        MediaEntityModelProvider mediaModel = null;
        String screenshotName = result.getMethod().getMethodName();
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        // after execution, you should see a folder "FailedTestsScreenshots"
        // under src folder
        String destination = ExtentTestNGITestListener.passLocation + "/" + screenshotName + dateName + ".png";
        MainUtil.Image_Path = destination;
        File finalDestination = new File(destination);
        try {
            FileUtils.copyFile(source, finalDestination);
            mediaModel = MediaEntityBuilder.createScreenCaptureFromPath(ExtentTestNGITestListener.htmlpassLocation + "/" + screenshotName + dateName + ".png").build();
        } catch (IOException e) {
            logger.error("Error: \n", e);
        }
        return mediaModel;
    }

    /**
     * This method to capture screenshot for fail
     *
     * @param result Pass the result (pass/fail)
     * @param driver Pass the driver
     * @return returns mediaModel object
     */
    public synchronized static MediaEntityModelProvider captureSrceenFail(ITestResult result, RemoteWebDriver driver) {
        MediaEntityModelProvider mediaModel = null;
        String screenshotName = result.getMethod().getMethodName();
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        // after execution, you should see a folder "FailedTestsScreenshots"
        // under src folder
        String destination = ExtentTestNGITestListener.failLocation + "/" + screenshotName + dateName + ".png";
        MainUtil.Image_Path = destination;
        File finalDestination = new File(destination);
        try {
            FileUtils.copyFile(source, finalDestination);
            mediaModel = MediaEntityBuilder.createScreenCaptureFromPath(ExtentTestNGITestListener.htmlfailLocation + "/" + screenshotName + dateName + ".png").build();
        } catch (IOException e) {
            logger.error("Error: \n", e);
        }
        return mediaModel;
    }
    
    

    /**
     * This method to capture screenshot for pass
     *
     * @param urlname Pass the app url name
     * @param driver  Pass the driver
     * @return returns mediaModel object
     */
    public static synchronized MediaEntityModelProvider captureSrceenPass(String urlname, RemoteWebDriver driver) {
        MediaEntityModelProvider mediaModel = null;
        String screenshotName = urlname.replaceAll(":", "_").replaceAll(" ", "_").trim().toLowerCase() + "_";
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        // after execution, you should see a folder "FailedTestsScreenshots"
        // under src folder
        String destination = ExtentTestNGITestListener.passLocation + "/" + screenshotName + dateName + ".png";
        MainUtil.Image_Path = destination;
        File finalDestination = new File(destination);
        try {
            FileUtils.copyFile(source, finalDestination);
            mediaModel = MediaEntityBuilder.createScreenCaptureFromPath(ExtentTestNGITestListener.htmlpassLocation + "/" + screenshotName + dateName + ".png").build();
        } catch (IOException e) {
            Reporter.log(e.getMessage());
        }
        return mediaModel;
    }

    /**
     * This method to capture screenshot for fail
     *
     * @param urlname Pass the app url name
     * @param driver  Pass the driver
     * @return returns mediaModel object
     */
    public static synchronized MediaEntityModelProvider captureSrceenFail(String urlname, RemoteWebDriver driver) {
        MediaEntityModelProvider mediaModel = null;
        // WebDriver driver = DriverFactory.getDriver();
        String screenshotName = urlname.replaceAll(":", "_").replaceAll(" ", "_").trim().toLowerCase() + "_";
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        // after execution, you should see a folder "FailedTestsScreenshots"
        // under src folder
        String destination = ExtentTestNGITestListener.failLocation + "/" + screenshotName + dateName + ".png";
        MainUtil.Image_Path = destination;
        File finalDestination = new File(destination);
        try {
            FileUtils.copyFile(source, finalDestination);
            mediaModel = MediaEntityBuilder.createScreenCaptureFromPath(ExtentTestNGITestListener.htmlfailLocation + "/" + screenshotName + dateName + ".png").build();
        } catch (IOException e) {

            Reporter.log(e.getMessage());
        }
        return mediaModel;
    }
}
