package com.umtest.testframe.lib;

import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;

public class AppInstallUninstall {

    private static Logger logger = LogManager.getLogger(AppInstallUninstall.class);

    public static boolean unInstallApp(String appPackage, RemoteWebDriver driver) {
        if (((AndroidDriver)driver).isAppInstalled(appPackage)) {
            logger.info("Uinstalling application: " + appPackage);
            return ((AndroidDriver)driver).removeApp(appPackage);
        } else {
            logger.error("App is not uninstalled");
            return false;
        }
    }

    public static void installApp(String fileName, RemoteWebDriver driver, String appPackage) {
        if (((AndroidDriver)driver).isAppInstalled(appPackage)) {
            logger.info(appPackage + " application already installed");
        } else {
            logger.info("App is not installed");
            ((AndroidDriver)driver).installApp(System.getProperty("user.dir") + "/src/main/resources/" + fileName);
        }
    }

    public static void pushFile(RemoteWebDriver driver, String fileName) throws IOException {
        ((AndroidDriver) driver).pushFile("/mnt/sdcard/ytlapps/" + fileName, new File(System.getProperty("user.dir") + "/src/main/resources/" + fileName));
    }
}
