package com.umtest.umrex.pageobject;

import com.umtest.testframe.lib.AppWait;
import com.umtest.testframe.lib.MainUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class UMREXScanIDReadIDCustomerInfoPage extends MainUtil {

	private AndroidDriver<?> driver;

	public UMREXScanIDReadIDCustomerInfoPage(AndroidDriver<?> driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);
	}

	private String varXpathNationalityInput="//android.widget.CheckedTextView[@resource-id='android:id/text1' and @text='CountryName']";

	@AndroidFindBy(id = "com.fl.pra:id/etIdNumber")
	private AndroidElement textboxIDNumber;
	
	@AndroidFindBy(id = "com.fl.pra:id/etName")
	private AndroidElement textboxName;
	
	@AndroidFindBy(id = "com.fl.pra:id/spGender")
	private AndroidElement spinnerGender;
	
	@AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text='Male']")
	private AndroidElement selectMale;

	@AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text='Female']")
	private AndroidElement selectFemale;
	
	@AndroidFindBy(id = "com.fl.pra:id/etDobDD")
	private AndroidElement dateDD;
	
	@AndroidFindBy(id = "com.fl.pra:id/etDobMM")
	private AndroidElement dateMM;
	
	@AndroidFindBy(id = "com.fl.pra:id/etDobYYYY")
	private AndroidElement dateYYYY;
	
	@AndroidFindBy(id = "com.fl.pra:id/spNationality")
	private AndroidElement spinnerNationality;
	
	@AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text='Antarctica']")
	private AndroidElement selectAntartica;
	
	@AndroidFindBy(id = "com.fl.pra:id/btnContinue")
	private AndroidElement buttonContinue;

	@AndroidFindBy(xpath = "//android.widget.Spinner[@resource-id='com.fl.pra:id/spCardType']")
	private AndroidElement dropdownCardType;

	@AndroidFindBy(xpath = "//android.widget.CheckedTextView[@resource-id='android:id/text1' and @text='MyKad']")
	private AndroidElement selectCardTypeMyKad;

	@AndroidFindBy(xpath = "//android.widget.Spinner[@resource-id='com.fl.pra:id/spRace']")
	private AndroidElement dropdownRace;

	@AndroidFindBy(xpath = "//android.widget.CheckedTextView[@resource-id='android:id/text1' and @text='Malay']")
	private AndroidElement selectRaceMalay;

	@AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='com.fl.pra:id/etAddress1']")
	private AndroidElement textAddress1;

	@AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='com.fl.pra:id/etPostcode']")
	private AndroidElement textPostcode;

	@AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='com.fl.pra:id/etCity']")
	private AndroidElement textCity;


	@AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='com.fl.pra:id/etState']")
	private AndroidElement textState;

	public void enterIDNumber(String sIDNumber){
		sendKeys(textboxIDNumber, sIDNumber, "Customer ID", "", driver);
	}

	public void enterName(String sName){
		sendKeys(textboxName, sName, "Customer Name", "", driver);
	}

	public void selectGenderMale(){
		clickElement(spinnerGender, "Gender Spinner", driver);
		clickElement(selectMale, "Select Male", driver);
	}

	public void selectGenderFemale(){
		clickElement(spinnerGender, "Gender Spinner", driver);
		clickElement(selectFemale, "Select Female", driver);
	}

	public void enterDateOfBirth(String sDate){

		String[]  aDate=sDate.split("/");

		sendKeys(dateDD, aDate[0], "Customer DOB Date", "", driver);
		sendKeys(dateMM, aDate[1], "Customer DOB Month", "", driver);
		sendKeys(dateYYYY, aDate[2], "Customer DOB Year", "", driver);
	}

	public void enterDateOfBirth1(String sDate, String sMonth, String sYear){
		sendKeys(dateDD, sDate, "Customer DOB Date", "", driver);
		sendKeys(dateMM, sMonth, "Customer DOB Month", "", driver);
		sendKeys(dateYYYY, sYear, "Customer DOB Year", "", driver);
	}

	public void selectNationality(String sCountry){
		clickElement(spinnerNationality, "Nationality dropdown", driver);
		clickElementUsingXpathString(varXpathNationalityInput.replace("CountryName",sCountry),"Nationality",driver);
	}

	public void selectRaceMalay(){
		clickElement(dropdownRace, "Race (dropdown)", driver);
		clickElement(selectRaceMalay, "Malay (Race Option)", driver);
	}

	public void selectCardTypeMyKad(){
		clickElement(dropdownCardType, "Card Type (dropdown)", driver);
		clickElement(selectCardTypeMyKad, "MyKad (Card Type Option)", driver);
	}

	public void enterAddress(String sAddress){
		sendKeys(textAddress1, sAddress, "Address", "", driver);
	}

	public void enterPostcode(String sPostcode){
		sendKeys(textPostcode, sPostcode, "Postcode", "", driver);
	}

	public void enterCity(String sCity){
		sendKeys(textCity, sCity, "City", "", driver);
	}


	public void enterState(String sState){
		sendKeys(textState, sState, "State", "", driver);
	}


	public void clickContinue(){
		clickElement(buttonContinue, "Continue Button", driver);
	}

	public void clickContinueConfirmation(){
		clickElement(buttonContinue, "Continue Button (Confirmation)", driver);
	}


	public void enterPassportCustomerInfo(){

		enterIDNumber(dictionary.get("CUSTOMER_ID"));
		enterName(dictionary.get("CUSTOMER_NAME"));
		selectGenderMale();

		enterDateOfBirth("01/01/1980");
		scrollUDLR(driver, 0, "U");
		selectNationality("Albania");
		clickContinue();
		clickContinue();
	}

	public void enterMyKadCustomerInfo(){

		selectCardTypeMyKad();
		enterIDNumber(dictionary.get("CUSTOMER_ID"));
		enterName(dictionary.get("CUSTOMER_NAME"));
		selectGenderMale();
		enterDateOfBirth("01/01/1980");
		selectRaceMalay();
		scrollUDLR(driver, 0, "U");
		scrollUDLR(driver, 0, "U");
		enterAddress("179, Jalan Klang Lama");
		enterPostcode("58100");
		enterCity("Kuala Lumpur");
		enterState("Kuala Lumpur");
		scrollUDLR(driver, 0, "U");
		clickContinue();
		clickContinue();
	}

}
