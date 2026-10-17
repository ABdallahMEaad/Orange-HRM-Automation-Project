package Page.ReportsPage;

import Page.BasePage;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class ReportsPage extends BasePage {

    private static final Logger log = LogManager.getLogger(ReportsPage.class);

    private static final String ADD_FORM_GROUP =
            "/ancestor::div[contains(@class,'oxd-input-group')][1]";

    private final By pimModule = By.xpath("//a[contains(@href,'/pim/viewPim')]");
    private final By reportsMenu = By.xpath(
            "//*[contains(@class,'oxd-topbar-body-nav-tab-item') and normalize-space()='Reports']");

    private final By addReportButton = By.xpath(
            "//div[contains(@class,'orangehrm-header-container')]//button[normalize-space()='Add']");
    private final By addReportTitle = By.xpath(
            "//h6[contains(@class,'orangehrm-main-title') and normalize-space()='Add Report']");
    private final By addReportNameInput = By.xpath(
            "//label[normalize-space()='Report Name']" + ADD_FORM_GROUP + "//input");
    private final By saveReportButton = By.xpath("//button[@type='submit' and normalize-space()='Save']");
    private final By savedToast = By.xpath("//p[normalize-space()='Successfully Saved']");

    private final By reportNameAutocomplete = By.cssSelector(
            "div.oxd-table-filter input[placeholder='Type for hints...']");
    private final By searchReportButton = By.xpath(
            "//div[contains(@class,'oxd-table-filter')]//button[normalize-space()='Search']");
    private final By generateButton = By.xpath(
            "//button[@type='submit' and (normalize-space()='View' or normalize-space()='Search')]");

    private final By reportTitle = By.cssSelector("h6.oxd-text.oxd-text--h6.orangehrm-main-title");
    private final By reportArea = By.xpath(
            "//div[contains(@class,'oxd-report-table')] | //revo-grid");
    private final By reportBodyRows = By.xpath("//div[@role='row'][.//div[@role='cell']]");
    private final By exportControl = By.xpath(
            "//button[contains(normalize-space(),'Export') or contains(normalize-space(),'Download') " +
                    "or .//i[contains(@class,'bi-download')]]");
    private final By expandReportButton = By.xpath(
            "//button[.//i[contains(@class,'bi-arrows-fullscreen') or contains(@class,'bi-fullscreen') " +
                    "or contains(@class,'bi-arrows-angle-expand') or contains(@class,'bi-arrow-left') " +
                    "or contains(@class,'bi-chevron-left') or contains(@class,'bi-caret-left')]]");

    private static final String PRINT_SHORTCUT_SCRIPT = loadPrintShortcutScript();

    private static String loadPrintShortcutScript() {
        try (InputStream in = ReportsPage.class.getResourceAsStream("/print-shortcut.js")) {
            if (in == null) {
                throw new IllegalStateException("print-shortcut.js was not found in src/main/resources");
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read print-shortcut.js", e);
        }
    }

    private By reportRow(String reportName) {
        return By.xpath("//div[contains(@class,'oxd-table-card')]" +
                "[.//div[@role='cell' and normalize-space(.)=" + xpathLiteral(reportName) + "]]");
    }

    private By reportViewButton(String reportName) {
        return By.xpath("//div[contains(@class,'oxd-table-card')]" +
                "[.//div[@role='cell' and normalize-space(.)=" + xpathLiteral(reportName) + "]]" +
                "//i[contains(@class,'bi-file-text-fill') or contains(@class,'bi-table')]/ancestor::button[1]");
    }

    private By autocompleteOption(String text) {
        return By.xpath("//div[@role='listbox']//span[normalize-space()=" + xpathLiteral(text) + "]");
    }

    private By filterDropdown(String label) {
        return By.xpath("//label[normalize-space()=" + xpathLiteral(label) + "]" +
                "/following::div[contains(@class,'oxd-select-text')][1]");
    }

    private By filterLabel(String label) {
        return By.xpath("//label[normalize-space()=" + xpathLiteral(label) + "]");
    }

    private By dropdownOption(String option) {
        return By.xpath("//div[@role='listbox']//span[normalize-space()=" + xpathLiteral(option) + "]");
    }

    private By selectOf(String label) {
        return By.xpath("//label[normalize-space()=" + xpathLiteral(label) + "]" + ADD_FORM_GROUP +
                "//div[contains(@class,'oxd-select-text')]");
    }

    private By plusButtonOf(String label) {
        return By.xpath("//label[normalize-space()=" + xpathLiteral(label) + "]" +
                "/ancestor::div[contains(@class,'orangehrm-report-criteria')][1]" +
                "//button[contains(@class,'orangehrm-report-icon')]");
    }

    private By addedDisplayField(String field) {
        return By.xpath("//*[normalize-space(text())=" + xpathLiteral(field) + "]" +
                "[not(ancestor::div[@role='listbox'])]" +
                "[not(contains(@class,'oxd-select-text-input'))]");
    }

    public ReportsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Navigate to PIM -> Reports")
    public void openReports() {
        log.info("Navigating to PIM -> Reports");
        clickElement(pimModule);
        clickElement(reportsMenu);
        findElement(reportNameAutocomplete);
    }

    @Step("Search for report: {reportName}")
    public void searchReport(String reportName) {
        WebElement input = findElement(reportNameAutocomplete);
        clearAndType(input, reportName);
        clickElement(autocompleteOption(reportName));
        clickElement(searchReportButton);
    }

    public boolean isReportListed(String reportName) {
        return isElementPresent(reportRow(reportName), Duration.ofSeconds(20));
    }

    @Step("Open report: {reportName}")
    public void openReport(String reportName) {
        clickElement(reportViewButton(reportName));
    }

    public boolean hasFilter(String label) {
        return isElementPresent(filterLabel(label), Duration.ofSeconds(5));
    }

    @Step("Apply filter {label} = {option}")
    public void selectFilter(String label, String option) {
        findElement(filterDropdown(label)).click();
        clickElement(dropdownOption(option));
    }

    @Step("Generate report")
    public void clickGenerate() {
        clickElement(generateButton);
    }

    public boolean isReportTitleDisplayed(String expectedTitle) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(30)).until(d ->
                    d.findElements(reportTitle).stream()
                            .anyMatch(e -> e.isDisplayed() && normalizeSpaces(e.getText()).contains(expectedTitle)));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isReportAreaDisplayed() {
        return isElementPresent(reportArea, Duration.ofSeconds(30));
    }

    public boolean reportContainsText(String text) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                    ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), text));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public int getReportRowCount() {
        return driver.findElements(reportBodyRows).size();
    }

    public boolean isExportAvailable() {
        return isElementPresent(exportControl, Duration.ofSeconds(5));
    }

    public WebElement getExportControl() {
        return findElement(exportControl);
    }

    @Step("Click Add to create a new report")
    public void clickAddReport() {
        clickElement(addReportButton);
        findElement(addReportTitle);
    }

    public boolean isAddReportFormDisplayed() {
        return isElementPresent(addReportTitle, Duration.ofSeconds(10));
    }

    @Step("Enter report name: {reportName}")
    public void enterReportName(String reportName) {
        findElement(addReportNameInput).sendKeys(reportName);
    }

    private void chooseFromDropdown(String label, String option) {
        clickElement(selectOf(label));
        clickElement(dropdownOption(option));
    }

    @Step("Add selection criteria: {criteria}")
    public void addSelectionCriteria(String criteria) {
        chooseFromDropdown("Selection Criteria", criteria);
        clickElement(plusButtonOf("Selection Criteria"));
        log.info("Selection criteria '{}' added", criteria);
    }

    @Step("Set the Include option: {option}")
    public void selectInclude(String option) {
        chooseFromDropdown("Include", option);
    }

    @Step("Add display field {field} from group {group}")
    public void addDisplayField(String group, String field) {
        chooseFromDropdown("Select Display Field Group", group);
        chooseFromDropdown("Select Display Field", field);
        clickElement(plusButtonOf("Select Display Field"));
    }

    public boolean isDisplayFieldAdded(String field) {
        return isElementPresent(addedDisplayField(field), Duration.ofSeconds(10));
    }

    @Step("Save the report")
    public void saveReport() {
        clickElement(saveReportButton);
    }

    public boolean isReportSavedMessageDisplayed() {
        return isElementPresent(savedToast, Duration.ofSeconds(15));
    }

    public boolean isExpandArrowAvailable() {
        return isElementPresent(expandReportButton, Duration.ofSeconds(10));
    }

    @Step("Click the arrow to open the report in the whole page")
    public void clickExpandArrow() {
        clickElement(expandReportButton);
    }

    @Step("Install the Ctrl+B print shortcut")
    public void installPrintShortcut() {
        ((JavascriptExecutor) driver).executeScript(PRINT_SHORTCUT_SCRIPT);
    }

    public void setDocumentTitle(String title) {
        ((JavascriptExecutor) driver).executeScript("document.title = arguments[0];", title);
    }

    @Step("Press Ctrl+B")
    public void pressPrintShortcut() {
        new Actions(driver)
                .keyDown(Keys.CONTROL)
                .sendKeys("b")
                .keyUp(Keys.CONTROL)
                .perform();
    }
}
