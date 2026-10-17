
package Page.TimePage;

import Page.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TimesheetPage extends BasePage {

    private static final Logger log =
            LogManager.getLogger(TimesheetPage.class);

    private final WebDriverWait wait;
    private final Random random = new Random();

    private final By dateInput =
            By.xpath("//div[contains(@class,'oxd-date-input')]//input");

    private final DateTimeFormatter inputFormatter =
            DateTimeFormatter.ofPattern("yyyy-dd-MM");

    private final DateTimeFormatter displayFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final By editButton =
            By.xpath("//button[contains(@class,'oxd-button--ghost') and normalize-space()='Edit']");

    private final By submitButton =
            By.xpath("//button[contains(@class,'oxd-button--secondary') and normalize-space()='Submit']");

    private final By timesheetTitle =
            By.xpath("//div[contains(@class,'orangehrm-timesheet-header--title')]");

    private final By projectInput =
            By.xpath("//div[contains(@class,'orangehrm-timesheet-body')]//input[@placeholder='Type for hints...']");

    private final By cocaColaOption =
            By.xpath("//div[@role='option']//span[contains(.,'The Coca-Cola Company')]");

    private final By activityDropdown =
            By.xpath("//div[contains(@class,'orangehrm-timesheet-body')]//div[contains(concat(' ',normalize-space(@class),' '),' oxd-select-text ')]");

    private final By activityListbox =
            By.xpath("//div[@role='listbox']");

    private final By activityOptions =
            By.xpath("//div[@role='listbox']//div[@role='option']");

    private final By dayInputs =
            By.xpath("//td[contains(@class,'orangehrm-timesheet-table-body-cell') and contains(@class,'--duration-input')]//input");

    private final By saveButton =
            By.xpath("//button[@type='submit' and normalize-space()='Save']");

    private final By savedHourInputs =
            By.xpath("//div[contains(@class,'orangehrm-timesheet-body')]//tr[1]/td[position()>=3 and position()<=9]//input");

    private final By submittedStatusLabel =
            By.xpath("//*[normalize-space(.)='Status: Submitted']");

    private final By employeeNameInput =
            By.xpath("//div[contains(@class,'oxd-autocomplete-text-input')]//input[@placeholder='Type for hints...']");

    private final By viewEmployeeButton =
            By.xpath("//button[@type='submit' and normalize-space()='View']");

    private final By viewProjectCell =
            By.xpath("//div[contains(@class,'orangehrm-timesheet-body')]//tr[1]/td[1]");

    private final By viewActivityCell =
            By.xpath("//div[contains(@class,'orangehrm-timesheet-body')]//tr[1]/td[2]");

    private final By approveButton =
            By.xpath("//button[contains(@class,'oxd-button--success') and normalize-space()='Approve']");

    private final By approvedStatusLabel =
            By.xpath("//*[normalize-space(.)='Status: Approved']");

    public TimesheetPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebElement getDateInput() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(dateInput)
        );
    }

    public WebElement getEditButton() {
        return wait.until(
                ExpectedConditions.elementToBeClickable(editButton)
        );
    }

    public WebElement getSubmitButton() {
        return wait.until(
                ExpectedConditions.elementToBeClickable(submitButton)
        );
    }

    public WebElement getSaveButton() {
        return wait.until(
                ExpectedConditions.elementToBeClickable(saveButton)
        );
    }

    public WebElement getViewEmployeeButton() {
        return wait.until(
                ExpectedConditions.elementToBeClickable(viewEmployeeButton)
        );
    }

    public WebElement getApproveButton() {
        return wait.until(
                ExpectedConditions.elementToBeClickable(approveButton)
        );
    }

    public List<WebElement> getDayInputs() {
        return wait.until(
                ExpectedConditions.numberOfElementsToBe(dayInputs, 7)
        );
    }

    public String enterRandomHoursForSelectedDay(int dayIndex) {
        List<WebElement> inputs = getDayInputs();

        if (dayIndex < 0 || dayIndex >= inputs.size()) {
            throw new IllegalArgumentException(
                    "Day index must be between 0 and 6."
            );
        }

        int minutes = (random.nextInt(28) + 4) * 15;

        String hours = String.format(
                "%02d:%02d",
                minutes / 60,
                minutes % 60
        );

        WebElement selectedInput = inputs.get(dayIndex);

        selectedInput.click();
        selectedInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        selectedInput.sendKeys(hours);

        log.info(
                "Entered {} hours for day index {}",
                hours,
                dayIndex
        );

        return hours;
    }

    public String enterRandomHoursForToday() {
        int dayIndex = LocalDate.now().getDayOfWeek().getValue() - 1;

        return enterRandomHoursForSelectedDay(dayIndex);
    }

    public void enterHoursForSelectedDay(int dayIndex, String hours) {
        if (hours == null || !hours.matches(
                "^(?:[01]\\d|2[0-3]):[0-5]\\d$"
        )) {
            throw new IllegalArgumentException(
                    "Hours must use HH:mm format, from 00:00 to 23:59."
            );
        }

        List<WebElement> inputs = getDayInputs();

        if (dayIndex < 0 || dayIndex >= inputs.size()) {
            throw new IllegalArgumentException(
                    "Day index must be between 0 and 6."
            );
        }

        WebElement selectedInput = inputs.get(dayIndex);

        selectedInput.click();
        selectedInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        selectedInput.sendKeys(hours);

        log.info(
                "Entered {} for day index {}",
                hours,
                dayIndex
        );
    }

    public List<String> getSavedHours() {
        return wait.until(
                        ExpectedConditions.numberOfElementsToBe(
                                savedHourInputs, 7
                        )
                ).stream()
                .map(element -> element.getAttribute("value"))
                .map(value -> value == null ? "" : value.trim())
                .collect(Collectors.toList());
    }

    public String selectRandomActivity() {
        wait.until(
                ExpectedConditions.elementToBeClickable(activityDropdown)
        ).click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(activityListbox)
        );

        List<WebElement> choices = wait.until(
                        ExpectedConditions.numberOfElementsToBeMoreThan(
                                activityOptions, 0
                        )
                ).stream()
                .filter(element -> {
                    String text = element.getText().trim();
                    return !text.isEmpty()
                            && !text.equals("-- Select --");
                })
                .collect(Collectors.toList());

        if (choices.isEmpty()) {
            throw new IllegalStateException(
                    "No valid activities are available."
            );
        }

        WebElement selected =
                choices.get(random.nextInt(choices.size()));

        String activityName = selected.getText().trim();
        selected.click();

        log.info("Selected activity: {}", activityName);

        return activityName;
    }

    public void enterTimesheetWeek(LocalDate date) {
        WebElement input = getDateInput();

        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(date.format(inputFormatter));
        input.sendKeys(Keys.ENTER);

        closeCalendar();

        log.info("Selected timesheet date: {}", date);
    }

    public void enterCurrentWeek() {
        enterTimesheetWeek(LocalDate.now());
    }

    public boolean isWeekShown(int year, int month, int day) {
        LocalDate monday = LocalDate.of(year, month, day)
                .with(DayOfWeek.MONDAY);

        LocalDate sunday = monday.plusDays(6);

        String value = getDateInput().getAttribute("value");

        String displayed = monday.format(displayFormatter)
                + " to " + sunday.format(displayFormatter);

        String stored = monday.format(inputFormatter)
                + " to " + sunday.format(inputFormatter);

        return value != null
                && (value.equals(displayed) || value.equals(stored));
    }

    public void closeCalendar() {
        try {
            new Actions(driver)
                    .moveToElement(wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    timesheetTitle
                            )
                    ))
                    .click()
                    .perform();

        } catch (TimeoutException e) {
            log.warn(
                    "Timesheet title was not available to close the calendar."
            );
        }
    }

    public void clickEditAndWaitForNavigation() {
        getEditButton().click();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(saveButton),
                ExpectedConditions.visibilityOfElementLocated(dayInputs)
        ));

        log.info("Timesheet edit mode opened.");
    }

    public void selectCocaColaProject() {
        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(projectInput)
        );

        input.clear();
        input.sendKeys("Coca");

        wait.until(
                ExpectedConditions.elementToBeClickable(cocaColaOption)
        ).click();

        log.info("Selected project: The Coca-Cola Company");
    }

    public void clickSaveButton() {
        getSaveButton().click();

        wait.until(
                ExpectedConditions.elementToBeClickable(editButton)
        );

        log.info("Timesheet saved.");
    }

    public void clickSubmitButton() {
        getSubmitButton().click();

        log.info("Timesheet submit action clicked.");
    }

    public void waitForSubmittedStatus() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        submittedStatusLabel
                )
        );
    }

    public boolean isSubmittedStatusDisplayed() {
        return !driver.findElements(submittedStatusLabel).isEmpty();
    }

    public void enterEmployeeName(String name) {
        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        employeeNameInput
                )
        );

        input.clear();
        input.sendKeys(name);

        By option = By.xpath(
                "//div[@role='option']//span[normalize-space()="
                        + xpathLiteral(name) + "]"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(option)
        ).click();

        log.info("Selected employee: {}", name);
    }

    public void clickViewEmployeeButton() {
        getViewEmployeeButton().click();

        log.info("Viewed employee timesheet.");
    }

    public String getViewProjectName() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        viewProjectCell
                )
        ).getText().trim();
    }

    public String getViewActivityName() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        viewActivityCell
                )
        ).getText().trim();
    }

    public void clickApproveButton() {
        getApproveButton().click();

        log.info("Approve action clicked.");
    }

    public void waitForApproval() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        approvedStatusLabel
                )
        );

        log.info("Timesheet approval confirmed.");
    }

    public String getApprovalStatus() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        approvedStatusLabel
                )
        ).getText().replace("Status:", "").trim();
    }
}
