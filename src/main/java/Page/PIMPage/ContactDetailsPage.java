package Page.PIMPage;

import Page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ContactDetailsPage extends BasePage {
    private By contactDetailsTab = By.xpath("//a[normalize-space()='Contact Details']");
    private By addressField =
            By.xpath("//h6[normalize-space()='Address']");

    private By street1Field =
            By.xpath("//label[normalize-space()='Street 1']/ancestor::div[contains(@class,'oxd-input-group')]//input");

    private By street2Field =
            By.xpath("//label[normalize-space()='Street 2']/ancestor::div[contains(@class,'oxd-input-group')]//input");

    private By cityField =
            By.xpath("//label[normalize-space()='City']/ancestor::div[contains(@class,'oxd-input-group')]//input");

    private By stateField =
            By.xpath("//label[normalize-space()='State/Province']/ancestor::div[contains(@class,'oxd-input-group')]//input");

    private By zipCodeField =
            By.xpath("//label[normalize-space()='Zip/Postal Code']/ancestor::div[contains(@class,'oxd-input-group')]//input");

    private By countryDropdown =
            By.xpath("//label[normalize-space()='Country']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]");

    private By CountryOption(String country) {
        return By.xpath("//div[contains(@class,'oxd-select-option') and normalize-space()='" + country + "']");
    }

    private By homeTelephoneField =
            By.xpath("//label[normalize-space()='Home']/ancestor::div[contains(@class,'oxd-input-group')]//input");

    private By mobileField =
            By.xpath("//label[normalize-space()='Mobile']/ancestor::div[contains(@class,'oxd-input-group')]//input");

    private By workTelephoneField =
            By.xpath("//label[normalize-space()='Work']/ancestor::div[contains(@class,'oxd-input-group')]//input");

    private By workEmailField =
            By.xpath("//label[normalize-space()='Work Email']/ancestor::div[contains(@class,'oxd-input-group')]//input");

    private By otherEmailField =
            By.xpath("//label[normalize-space()='Other Email']/ancestor::div[contains(@class,'oxd-input-group')]//input");

    private By saveButton =
            By.xpath("//button[@type='submit' and normalize-space()='Save']");
    private final By successMessage = By.xpath("//p[normalize-space()='Successfully Updated']");
    private By formLoader = By.cssSelector(".oxd-form-loader");



    public ContactDetailsPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getContactDetailsTab() {
        return findElement(contactDetailsTab);
    }

    public WebElement getAddressField() {
        return findElement(addressField);
    }
    public WebElement getStreet1Field() {
        return findElement(street1Field);
    }
    public WebElement getStreet2Field() {
        return findElement(street2Field);
    }
    public WebElement getCityField() {
        return findElement(cityField);
    }
    public WebElement getStateField() {
        return findElement(stateField);
    }
    public WebElement getZipCodeField() {
        return findElement(zipCodeField);
    }
    public WebElement getCountryDropdown() {
        return findElement(countryDropdown);    }
    public WebElement getCountryOption(String country) {
        return findElement(CountryOption(country));
    }
    public WebElement getHomeTelephoneField() {
        return findElement(homeTelephoneField);}
    public WebElement getMobileField() {
        return findElement(mobileField);}
    public WebElement getWorkTelephoneField() {
        return findElement(workTelephoneField);}
    public WebElement getWorkEmailField() {
        return findElement(workEmailField);}
    public WebElement getOtherEmailField() {
        return findElement(otherEmailField);}
    public WebElement getSaveButton() {
        return findElement(saveButton);}
    public WebElement getSuccessMessage() {
        return findElement(successMessage);
    }
    public WebElement getFormLoader() {
        return findElement(formLoader);
    }

    public void clickContactDetailsTab() {
        getContactDetailsTab().click();
    }

    public void enterAddress(String street1, String street2, String city, String state, String zipCode, String country,
                             String homeTelephone, String mobile, String workTelephone, String workEmail, String otherEmail) {

        getStreet1Field().clear();
        getStreet1Field().sendKeys(street1);

        getStreet2Field().clear();
        getStreet2Field().sendKeys(street2);

        getCityField().clear();
        getCityField().sendKeys(city);

        getStateField().clear();
        getStateField().sendKeys(state);

        getZipCodeField().clear();
        getZipCodeField().sendKeys(zipCode);

        selectCountry(country);

        getHomeTelephoneField().clear();
        getHomeTelephoneField().sendKeys(homeTelephone);

        getMobileField().clear();
        getMobileField().sendKeys(mobile);

        getWorkTelephoneField().clear();
        getWorkTelephoneField().sendKeys(workTelephone);

        getWorkEmailField().clear();
        getWorkEmailField().sendKeys(workEmail);

        getOtherEmailField().clear();
        getOtherEmailField().sendKeys(otherEmail);

        getSaveButton().click();
    }

    public void selectCountry(String country) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(formLoader));

        wait.until(ExpectedConditions.elementToBeClickable(countryDropdown)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(CountryOption(country)));

        getCountryOption(country).click();
    }

    public String getStreet1Value() {
        return getStreet1Field().getAttribute("value");
    }

    public String getStreet2Value() {
        return getStreet2Field().getAttribute("value");
    }

    public String getCityValue() {
        return getCityField().getAttribute("value");
    }

    public String getStateValue() {
        return getStateField().getAttribute("value");
    }

    public String getZipCodeValue() {
        return getZipCodeField().getAttribute("value");
    }

    public String getHomeTelephoneValue() {
        return getHomeTelephoneField().getAttribute("value");
    }

    public String getMobileValue() {
        return getMobileField().getAttribute("value");
    }

    public String getWorkTelephoneValue() {
        return getWorkTelephoneField().getAttribute("value");
    }

    public String getCountryValue() {
        return getCountryDropdown().getText();
    }

    public String getWorkEmailValue() {
        return getWorkEmailField().getAttribute("value");
    }

    public String getOtherEmailValue() {
        return getOtherEmailField().getAttribute("value");
    }
















}
