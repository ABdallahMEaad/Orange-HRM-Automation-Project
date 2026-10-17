package Page.PIMPage;

import Page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EmergencyContactsPAge extends BasePage {

private By emergencyContactsTab = By.xpath("//a[contains(@href,'/pim/viewEmergencyContacts')]");
private By addEmergencyContactButton = By.xpath("(//button[@type='button'][normalize-space()='Add'])[1]");
private By nameField = By.xpath("//label[normalize-space()='Name']/following::input[1]");
private By relationshipField = By.xpath("//label[normalize-space()='Relationship']/following::input[1]");
private By homeTelephoneField = By.xpath("//label[normalize-space()='Home Telephone']/following::input[1]");
private By mobileField = By.xpath("//label[normalize-space()='Mobile']/following::input[1]");
private By workTelephoneField = By.xpath("//label[normalize-space()='Work Telephone']/following::input[1]");
private By saveButton = By.xpath("//button[@type='submit' and normalize-space()='Save']");
private By successMessage = By.xpath("//p[normalize-space()='Successfully Saved']");
    private By emergencyContactRow(String name) {
        return By.xpath(
                "//div[contains(@class,'oxd-table-card')]" +
                        "[.//div[@role='cell' and normalize-space()='" + name + "']]"
        );
    }

    public WebElement getEmergencyContactRow(String name) {
        return findElement(emergencyContactRow(name));
    }



    public EmergencyContactsPAge(WebDriver driver) {
        super(driver);
    }

    public WebElement getEmergencyContactsTab() {
        return findElement(emergencyContactsTab);
    }
    public WebElement getAddEmergencyContactButton() {
        return findElement(addEmergencyContactButton);
    }
    public WebElement getNameField() {
        return findElement(nameField);  }

    public WebElement getRelationshipField() {
        return findElement(relationshipField);  }
    public WebElement getHomeTelephoneField() {
        return findElement(homeTelephoneField);  }
    public WebElement getMobileField() {
        return findElement(mobileField);  }
    public WebElement getWorkTelephoneField() {
        return findElement(workTelephoneField);  }
    public WebElement getSaveButton() {
        return findElement(saveButton);  }
    public WebElement getSuccessMessage() {
        return findElement(successMessage);  }


    public void clickEmergencyContactsTab() {
        getEmergencyContactsTab().click();
    }

    public void clickAddEmergencyContactButton() {
        getAddEmergencyContactButton().click();
    }
    public void setAddEmergencyContactMethod(String name, String relationship, String homeTelephone, String mobile, String workTelephone) {
        getNameField().sendKeys(name);
        getRelationshipField().sendKeys(relationship);
        getHomeTelephoneField().sendKeys(homeTelephone);
        getMobileField().sendKeys(mobile);
        getWorkTelephoneField().sendKeys(workTelephone);
        getSaveButton().click();
    }

    public String getEmergencyContactNameValue () {
        return getNameField().getAttribute("value");
    }

    public String getEmergencyContactRelationshipValue () {
        return getRelationshipField().getAttribute("value");
    }

    public String getEmergencyContactHomeTelephoneValue () {
        return getHomeTelephoneField().getAttribute("value");
    }

    public String getEmergencyContactMobileValue () {
        return getMobileField().getAttribute("value");
    }

    public String getEmergencyContactWorkTelephoneValue () {
        return getWorkTelephoneField().getAttribute("value");
    }

    public boolean isEmergencyContactDisplayed(
            String name,
            String relationship,
            String homeTelephone,
            String mobile,
            String workTelephone) {

        WebElement row = getEmergencyContactRow(name);

        String rowText = row.getText();

        return rowText.contains(name)
                && rowText.contains(relationship)
                && rowText.contains(homeTelephone)
                && rowText.contains(mobile)
                && rowText.contains(workTelephone);
    }












}
