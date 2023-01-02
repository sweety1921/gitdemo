package com.umtest.testframe.lib;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * PageLoadHelper
 *
 * Created by Muthulakshmi on 7/4/18.
 */
public class PageLoadHelper {
    public static PageLoadHelper isLoaded() {
        PageLoadHelper loadHelper = new PageLoadHelper();
        return loadHelper;
    }


    public PageLoadHelper isElementClickable(RemoteWebDriver driver, By by) {
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(by));
            return this;
        } catch (WebDriverException e) {
            throw new Error("Element is not clickable");
        }
    }

    public PageLoadHelper isElementVisible(RemoteWebDriver driver, By by) {
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(by));
            return this;
        } catch (WebDriverException e) {
            throw new Error("Element is not visible");
        }
    }
}
