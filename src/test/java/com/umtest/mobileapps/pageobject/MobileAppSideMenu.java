package com.umtest.mobileapps.pageobject;


import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;

public class MobileAppSideMenu extends MainUtil {

	private RemoteWebDriver driver;

	public MobileAppSideMenu(RemoteWebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);
	}

	@AndroidFindBy(xpath = "//android.view.View[@content-desc='sidemenu_button_menu']")
	private AndroidElement sideMenuButton;

	@AndroidFindBy(xpath = "//android.view.View[@content-desc=\"menu_cell_My Account \"]")
	private AndroidElement menulinkMyAccount;

	@AndroidFindBy(xpath = "//android.view.View[@content-desc=\"menu_cellParentSwitch Plan\"]")
	private AndroidElement menulinkSwitch_MyAccount;

	public void clickSideMenu(){
		clickElement(sideMenuButton, "Side Menu Button", driver);
	}

	public void clickMyAccount(){
		clickElement(menulinkMyAccount, "My Account", driver);
	}

	public void clickSwitchPlan(){
		clickElement(menulinkSwitch_MyAccount, "Switch Plan", driver);
	}

	public void navigateToSwitchPlan(){
		clickMyAccount();
		clickSwitchPlan();
	}
	

}
