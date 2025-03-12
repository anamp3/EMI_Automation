package Accelerators;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.ExceptionHandles;
import Utilities.FrameSwitcher;
import Utilities.Utils;

public class ActionsClass extends Accelerators.BaseClass {

    public static String sTestCaseName;
    public static final String EnvironmentURL = Utils.getProperty("SiteURL");
    public static final String testData = System.getProperty("user.dir")+ Utils.getProperty("ExcelPath");

    public static void selectMonth(String monthValue) {

        String monthName;
        switch(monthValue) {
            case "0": monthName = "January"; break;
            case "1": monthName = "February"; break;
            case "2": monthName = "March"; break;
            case "3": monthName = "April"; break;
            case "4": monthName = "May"; break;
            case "5": monthName = "June"; break;
            case "6": monthName = "July"; break;
            case "7": monthName = "August"; break;
            case "8": monthName = "September"; break;
            case "9": monthName = "October"; break;
            case "10": monthName = "November"; break;
            case "11": monthName = "December"; break;
            default: monthName = monthValue;
        }

        String monthXPath = String.format("//option[contains(text(), '%s')]", monthName);

        WebElement monthElement = driver.findElement(By.xpath(monthXPath));

        monthElement.click();
    }

    public static void selectDay(String dayValue) {
        int day = Integer.parseInt(dayValue);
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("Invalid day value: " + dayValue);
        }

        String formattedDayValue = String.valueOf(day); // "1", "2", ... "31"

        String dayXPath = String.format("(//span[@class='flatpickr-day' and not(contains(@class, 'prevMonthDay')) and not(contains(@class, 'nextMonthDay')) and not(contains(@class, 'flatpickr-disabled')) and text()='%s'])[1]", formattedDayValue);

        WebElement dayElement = driver.findElement(By.xpath(dayXPath));

        dayElement.click();
    }

    public static List<String> GetDropDownValues(By object) {
        List<String> values = new ArrayList<>();
        try {
            Select dropdown = new Select(driver.findElement(object));
            List<WebElement> options = dropdown.getOptions();

            for (WebElement option : options) {
                values.add(option.getAttribute("value"));
            }
        }
        catch(Exception e) {
            ExceptionHandles.HandleException(e, "Failed to retrieve the value from Element ");
        }
        return values;
    }

    public static void selectDate(String dateExcelValue, By yearElement, By monthsOpenerElement) {
        // Split the date value into day, month, and year
        String[] sliceMonth = dateExcelValue.split("/");
        String rawDate = sliceMonth[0]; // day
        int convertedMonth = Integer.parseInt(sliceMonth[1]); // month
        String year = sliceMonth[2]; // year

        // Click and enter the year in the respective field
        ActionsClass.clickOnElement(yearElement, "Year field clicked");
        ActionsClass.typeInTextBox(yearElement, year, "Year entered");

        // Get the list of available months from the dropdown
        List<String> months = ActionsClass.GetDropDownValues(monthsOpenerElement);

        // Iterate over the months and select the appropriate one
        for (String month : months) {
            int monthInt = Integer.parseInt(month);
            int newConvertedMonth = convertedMonth - 1;
            if (newConvertedMonth == monthInt) {
                ActionsClass.selectMonth(month); // Select the month
                ActionsClass.selectDay(rawDate); // Select the day
//                break; // Exit the loop once the correct month is selected
            }
        }
    }

    public static boolean isElementDisplayed(By object, String elementName) {
        boolean bFlag = false;
        try {
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.MILLISECONDS);
            List<WebElement> elements = driver.findElements(object);
            if (elements.size() > 0 && elements.get(0).isDisplayed()) {
                bFlag = true;
            }
        } catch (Exception e)  {
            ExceptionHandles.HandleException(e, "Unable to check if the element " + elementName + " is visible");
        }

        return bFlag;
    }

    public static boolean isElementFromSheetVisible(String sheetValue, String elementName){
        String objectPath = String.format("(//div[contains(text(), '%s')])[1]", sheetValue);
        boolean bFlag = false;
        try {
            WebElement element = driver.findElement(By.xpath(objectPath));
            if (element.isDisplayed()) {
                System.out.println("The element is displayed.");
                bFlag = true;
            } else {
                System.out.println("The element is not displayed.");
            }
        } catch (Exception e) {
            System.out.println("The element was not found or not displayed.");
        }
        return bFlag;
    }

    public static boolean isElementVisible(By object, String elementName) {
        boolean bFlag = false;
        try {
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.MILLISECONDS);
            if(driver.findElements(object).size() > 0) {
                bFlag = true;
            }
        } catch (Exception e)  {
            ExceptionHandles.HandleException(e, "Unable to check if the element " + elementName + " is visible");
        }
        return bFlag;
    }

    public static boolean IsTextboxEmpty(By object, String sElementName) {
        boolean bFlag = false;
        try {
            if (driver.findElement(object).isDisplayed()) {
                if (driver.findElement(object).getAttribute("value").isEmpty()) {
                    bFlag = true;
                }
            } else {
                bFlag = false;
            }
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Unable to check if the element " + sElementName + " is empty");
        }
        return bFlag;
    }

    public static boolean waitForElementToBeClickable(By Locator, long lTime) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, lTime);
            wait.until(ExpectedConditions.elementToBeClickable(Locator));
            return true;
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to wait for element to be clickable");
            return false;
        }
    }
    public static void refreshPage() {
        driver.navigate().refresh();
    }

    public static boolean waitForElementToBeVisible(By Locator, long lTime) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, lTime);
            wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
            return true;
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to wait for element to be visible");
            return false;
        }
    }
    public static boolean waitForElementToBeInvisible(By Locator, long lTime) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, lTime);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(Locator));
            return true;
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to wait for element to be invisible");
            return false;
        }
    }

    public static void clearTextBox(By object, String elementName) {
        try {
            if (driver.findElement(object).isDisplayed()) {
                driver.findElement(object).clear();
            } else ExceptionHandles.HandleAssertion("Unable to find Element " + elementName);
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to clear the text in the textbox " + elementName);
        }
    }

    public static void pressBackspace(By object, String elementName) {
        try {
            if (driver.findElement(object).isDisplayed()) {
                while(!ActionsClass.IsTextboxEmpty(object, elementName)) {
                    driver.findElement(object).sendKeys(Keys.BACK_SPACE);
                }
            } else ExceptionHandles.HandleAssertion("Unable to find Element " + elementName);
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to clear the text in the textbox " + elementName);
        }
    }

    public static void typeInTextBox(By object, String data, String elementName) {
        try {
            if (driver.findElement(object).isDisplayed()) {
                driver.findElement(object).clear();
                driver.findElement(object).click();
                driver.findElement(object).sendKeys(data);
            } else ExceptionHandles.HandleAssertion("Failed to confirm if " + elementName + " is Displayed");
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to enter data in " + elementName + " textbox");
        }
    }

    public static void typeInTextBoxWithoutClear(By object, String data, String elementName) {
        try {
            if (driver.findElement(object).isDisplayed()) {
                driver.findElement(object).click();
                driver.findElement(object).sendKeys(data);
            } else ExceptionHandles.HandleAssertion("Failed to enter data in " + elementName + " textbox");
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to enter data in " + elementName + " textbox");
        }
    }

    public static void typeInCalander(By object, String data, String elementName) {
        try {
            if (driver.findElement(object).isDisplayed()) {
                driver.findElement(object).click();
                driver.findElement(object).clear();
                driver.findElement(object).sendKeys(data);
                WebElement we = driver.findElement(By.xpath("//div[@class= 'datepicker-days']//td[@class='active day']"));
                we.click();
            } else ExceptionHandles.HandleAssertion("Failed to enter data in " + elementName + " Calendar");
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to enter data in " + elementName + " Calendar");
        }
    }

    public static boolean jsClickOnElement(By object, String elementName) {
        boolean bFlag = false;
        try {
            if (driver.findElement(object).isDisplayed()) {
                bFlag = true;
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", driver.findElement(object));
            } else ExceptionHandles.HandleAssertion("Failed to click on " + elementName);
            Thread.sleep(1000);
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to click on " + elementName);
        }
        return bFlag;
    }

    public static boolean clickOnElement(By object, String elementName) {
        try {
            if (driver.findElement(object).isDisplayed()) {
                Thread.sleep(1000);
                driver.findElement(object).click();
                return true;
            } else ExceptionHandles.HandleAssertion("Failed to click on " + elementName);
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to click on " + elementName);
        }
        return false;
    }

    public static boolean clickOnCheckbox(By object, String elementName) {
        try {
            if (ActionsClass.isElementVisible(object, elementName)) {
                WebElement checkbox = driver.findElement(object);
                if (!checkbox.isSelected()){
                    Thread.sleep(1000);
                    driver.findElement(object).click();
                    return true;
                }
            } else ExceptionHandles.HandleAssertion("Failed to click on " + elementName);
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to click on " + elementName);
        }
        return false;
    }

    public static boolean clickOnToggleOrCheckbox(By object, String elementName, String attribute, String onValue) {
        try {
            if (ActionsClass.isElementVisible(object, elementName)) {
                WebElement element = driver.findElement(object);
                boolean isOn = element.getAttribute(attribute).equals(onValue);

                if (!isOn) {
                    Thread.sleep(1000);
                    element.click();
                    return true;
                }
            } else {
                ExceptionHandles.HandleAssertion("Failed to interact with " + elementName);
            }
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to interact with " + elementName);
        }
        return false;
    }

    public static boolean isTextElementEnabled(By object, String sElementName) {
        try {
            if(driver.findElement(object).isEnabled())
                return true;
        }
        catch(Exception e) {
            ExceptionHandles.HandleException(e, "Unable to confirm if text element is enabled" + sElementName);
        }
        return false;
    }

    public static String getElementText(By object, String elementName) {
        String sText = "";
        try {
            if (driver.findElement(object).isDisplayed()) {
                sText = driver.findElement(object).getText();
            } else ExceptionHandles.HandleAssertion("Failed to get text from " + elementName);
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to get text from " + elementName);
        }
        return sText;
    }

    public static String getElementId(By object, String elementName) {
        String ElementId = "";
        try {
            if (driver.findElement(object).isDisplayed()) {
                ElementId = driver.findElement(object).getAttribute("id");
            } else ExceptionHandles.HandleAssertion("Failed to get text from " + elementName);
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to get text from " + elementName);
        }
        return ElementId;
    }


    public static List<WebElement> GetDropDownListValues(By object, String sElementName) {
        List<WebElement> options = null;
        try {
            Select dropdown = new Select(driver.findElement(object));
            options = dropdown.getOptions();
        }
        catch(Exception e) {
            ExceptionHandles.HandleException(e, "Failed to retrive the value from Element " + sElementName);
        }
        return options;
    }

    public static boolean selectDropdownValue(By object, String value, String elementName){
        List<WebElement> elementList = ActionsClass.GetDropDownListValues(object, elementName);
        boolean found = false;
        try{
            for (WebElement element : elementList){

                if(element.getText().equalsIgnoreCase(value)){
                    found = true;
                    element.click();
                }
            }
        }catch (Exception e){
            ExceptionHandles.HandleException(e, "Data value not found in dropdown: " + elementName);
        }

        return found;
    }

    public static String getDropDownSelectedText(By object, String elementName) {
        String sText = "";
        try {
            if (driver.findElement(object).isDisplayed()) {
                Select sel = new Select(driver.findElement(object));
                sText = sel.getFirstSelectedOption().getText();
            } else ExceptionHandles.HandleAssertion("Failed to get text from " + elementName);
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to get text from " + elementName);
        }
        return sText;
    }

    public static String getTextboxText(By object, String elementName) {
        String sText = "";
        try {
            if (driver.findElement(object).isDisplayed()) {
                sText = driver.findElement(object).getAttribute("value");
            } else ExceptionHandles.HandleAssertion("Failed to get text from " + elementName);
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to get text from " + elementName);
        }
        return sText;
    }

    public static String getNameAttributeValue(By object, String elementName) {
        String sText = "";
        try {
            if (ActionsClass.isElementVisible(object, elementName)) {
                sText = driver.findElement(object).getAttribute("name");
            } else ExceptionHandles.HandleAssertion("Failed to get text from " + elementName);
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to get text from " + elementName);
        }
        return sText;
    }

    public static boolean isMatterListItemFiltered(By object, String comparison, String filterName) {
        List<WebElement> ElementList = null;
        try {
            ElementList = driver.findElements(object);
            if(ElementList.size() >= 2) {
                for(int i =0; i <ElementList.size(); i++) {
                    if(!ElementList.get(i).getText().toLowerCase().contains(comparison.toLowerCase())) {
                        ExceptionHandles.HandleAssertion(comparison + "Filtering did not work correctly");
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to return Filter confirmation of " + filterName);
            return false;
        }
        return true;
    }

    public static Date getDateElement(By object, String elementName) {
        Date dDate = new Date();
        try {
            if (driver.findElement(object).isDisplayed()) {
                dDate = (Date) driver.findElement(object);
            } else ExceptionHandles.HandleAssertion("Failed to get date from " + elementName);
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to get date from " + elementName);
        }
        return dDate;
    }

    public static boolean isDateRageFiltered(Date toDate, Date fromDate, By object,String filterName) {
        try {
            List<WebElement> ElementList = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
            ElementList = driver.findElements(object);
            for (int i = 0; i < ElementList.size(); i++) {
                if(dateFormat.parse(ElementList.get(i).getText()).after(fromDate)
                        || dateFormat.parse(ElementList.get(i).getText()).equals(fromDate)) {
                    if(dateFormat.parse(ElementList.get(i).getText()).before(toDate)
                            || dateFormat.parse(ElementList.get(i).getText()).equals(toDate)) {
                        return true;
                    }
                }
                else return false;
            }
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to filter by" + filterName);
        }
        return false;
    }

    public static void ScrollToElement(By object) {
        try {
            WebElement element = driver.findElement(object);
            JavascriptExecutor je = (JavascriptExecutor) driver;
            je.executeScript("arguments[0].scrollIntoView(true)", element);
        } catch(Exception e) {
            ExceptionHandles.HandleException(e, "Unable to Scroll to element");
        }
    }

    public static String getCurrentDate(String strFormat) {
        String Date = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
            Date dateObj = new Date();
            Date = dateFormat.format(dateObj);
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Unable to get current Date");
        }
        return Date;
    }

    public static void DropDownSelectByVisibleText(By object, String sValue, String sElementName) {
        try {
            Select dropdown = new Select(driver.findElement(object));
            dropdown.selectByVisibleText(sValue);

        } catch(Exception e) {
            ExceptionHandles.HandleException(e, "Failed to select text "+ sValue + " from Element " + sElementName);
        }
    }

    public static void DropDownSelectByIndex(By object, int iValue, String sElementName) {
        try {
            Select dropdown = new Select(driver.findElement(object));
            dropdown.selectByIndex(iValue);
        } catch(Exception e) {
            ExceptionHandles.HandleException(e, "Failed to select index "+ iValue + " from Element " + sElementName);
        }
    }

    public static void SwitchTabs() {
        try {
            Set<String> windows = driver.getWindowHandles();
            String sCurrentHandle = driver.getWindowHandle();
            for (String window:windows)
            {
                if(!sCurrentHandle.equalsIgnoreCase(window))
                {
                    driver.switchTo().window(window);
                }
            }
        } catch(Exception e) {
            ExceptionHandles.HandleException(e, "Unable to Switch Tabs");
        }
    }

    public static void SetCheckbox(By object,String sSelected, String sElementName) {
        try {
            if(isElementVisible(object, sElementName)) {
                WebElement checkbox = driver.findElement(object);

                if(sSelected.equalsIgnoreCase("true")) {
                    if(!IsCheckBoxSelected(object, sElementName)) {
                        checkbox.click();
                    }
                }
                else if (sSelected.equalsIgnoreCase("false")) {
                    if(IsCheckBoxSelected(object, sElementName)) {
                        checkbox.click();
                    }
                }
            }
            else ExceptionHandles.HandleAssertion("Unable to find " + sElementName + " check box");
        } catch(Exception e) {
            ExceptionHandles.HandleException(e, "Failed to select Check box " + sElementName);
        }
    }

    public static void SetMultipleCheckbox(By object,String sSelected, String sElementName) {
        try {
            List<WebElement> elements = driver.findElements(object);
            if(sSelected.equalsIgnoreCase("true")) {
                for(WebElement element : elements) {
                    if(element.getAttribute("auto-value").equalsIgnoreCase("false")) {
                        element.click();
                    }
                }
            }
            else if (sSelected.equalsIgnoreCase("false")) {
                for(WebElement element : elements) {
                    if(element.getAttribute("auto-value").equalsIgnoreCase("true")) {
                        element.click();
                    }
                }
            }
        } catch(Exception e) {
            ExceptionHandles.HandleException(e, "Failed to select Check box " + sElementName);
        }
    }

    public static void SaveViaCtrlS() {
        try {
            Actions action = new Actions(driver);
            action.keyDown(Keys.CONTROL)
                    .sendKeys("s")
                    .keyUp(Keys.CONTROL)
                    .perform();
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to Save Via Ctrl S");
        }
    }

    public static void GoBackViaAltLeftArrow() {
        try {
            Actions action = new Actions(driver);
            action.keyDown(Keys.LEFT_ALT)
                    .sendKeys(Keys.ARROW_LEFT)
                    .keyUp(Keys.LEFT_ALT)
                    .perform();
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to Go Back Via Alt Left Arrow");
        }
    }

    public static void ClearLocalStorage() {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("javascript:localStorage.clear();");
        }
        catch(Exception e) {
            ExceptionHandles.HandleException(e, "Failed to Clear Local Storage");
        }
    }

    public static boolean isButtonEnabled(By object, String sElementName) {
        boolean bFlag = false;
        try {
            if(driver.findElement(object).getAttribute("disabled") == null) {
                bFlag = true;
            }

            return bFlag;
        }
        catch(Exception e) {
            ExceptionHandles.HandleException(e, "Failed to Check if button is enabled " + sElementName);
        }
        return bFlag;
    }

    public static void PressTab() {
        try {
            Actions action = new Actions(driver);
            action.sendKeys(Keys.TAB);
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to Press Tab");
        }
    }

    public static void PressShiftTab(By object) {
        try {
            Toolkit.getDefaultToolkit().getSystemClipboard();
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_SHIFT);
            robot.keyRelease(KeyEvent.VK_TAB);
        } catch(Exception e) {
            ExceptionHandles.HandleException(e, "Unable to Press Shift+Tab");
        }
    }

    public static void PressEnter() {
        try {
            Actions action = new Actions(driver);
            action.sendKeys(Keys.RETURN);
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to Press Enter");
        }
    }

    public static void PressAlt(By object) {
        try {
            driver.findElement(object).sendKeys(Keys.ALT);
        } catch (Exception e){
            ExceptionHandles.HandleException(e, "Failed to Press Alt");
        }
    }

    public static void PressDownArrow() {
        try {
            Actions action = new Actions(driver);
            action.sendKeys(Keys.ARROW_DOWN);
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to Press Down Arrow");
        }
    }

    public static void setFocusToElement(WebElement object) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            ((JavascriptExecutor)driver).executeScript("window.focus();");
            executor.executeScript("arguments[0].focus();", object);
        }
        catch(Exception e){
            ExceptionHandles.HandleException(e, "Failed to Set Focus to WebElement");
        }
    }

    public static boolean IsCheckBoxSelected(By object, String sElementName) {
        String isCheckedAttr = driver.findElement(object).getAttribute("auto-value");
        try {
            if(isElementVisible(object, sElementName) && isCheckedAttr!=null) {
                if(isCheckedAttr.equalsIgnoreCase("true")) {
                    return true;
                }
            }
            else ExceptionHandles.HandleAssertion("Element not found or does not contain auto-value attribute.");
        }
        catch(Exception e) {
            ExceptionHandles.HandleException(e, "Failed to Check if " + sElementName + " is selected");
        }
        return false;
    }

    public static int getNumberOfElements(By elements, String elementName){
        int noOfElements = 0;
        try {
            List <WebElement> list = driver.findElements(elements);
            noOfElements = list.size();
        }
        catch(Exception e) {
            ExceptionHandles.HandleException(e, "Unable to get number of elements " + elementName);
        }
        return noOfElements;
    }

    public static void compareTextInElement(By element, String elementName, String text) {
        try {
            if(!ActionsClass.getTextboxText(element, elementName).equalsIgnoreCase(text)) {
                ExceptionHandles.HandleAssertion(elementName + " Text is incorrect.");
            }
        }
        catch(Exception e) {
            ExceptionHandles.HandleException(e, "Unable to compare text");
        }
    }

    public static void typeInAutoPopulateBox(By object, String data, String elementName) {
        try {
            if (driver.findElement(object).isDisplayed()) {
                driver.findElement(object).clear();
                driver.findElement(object).click();
                driver.findElement(object).sendKeys(data);
                ActionsClass.waitForElementToBeVisible(object, 10);
                if(!ActionsClass.isElementVisible(object, elementName)) {
                    ExceptionHandles.HandleAssertion(elementName + " Not visible");
                } else {
                    driver.findElement(object).click();
                    ActionsClass.PressDownArrow();
                    ActionsClass.PressEnter();
                    ActionsClass.PressTab();
                }
            } else ExceptionHandles.HandleAssertion("Failed to to find " + elementName + " textbox");
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to enter data in " + elementName + " textbox");
        }
    }

    public static boolean isDateSortingCorrect(By object, String Order,  String sElementName) {
        boolean bFlag = false;
        List<Date> dateList = new ArrayList<Date>();
        List<WebElement> ElementList = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM, yyyy HH:mm:ss");
        try {
            ElementList = driver.findElements(object);
            if(ElementList.size() >= 2) {
                for(int i =0; i<ElementList.size(); i++) {
                    dateList.add(dateFormat.parse(ElementList.get(i).getText()));
                }
                if(Order.equalsIgnoreCase("Desc") || Order.equalsIgnoreCase("Descending")) {
                    for(int i =1; i <ElementList.size(); i++) {
                        if(dateList.get(i).after(dateList.get(i-1))) {
                            bFlag = false;
                            break;
                        }
                        else bFlag=true;
                    }
                }else if (Order.equalsIgnoreCase("Asc") || Order.equalsIgnoreCase("Ascending")) {
                    for(int i =1; i <=dateList.size(); i++) {
                        if(dateList.get(i).before(dateList.get(i-1))) {
                            bFlag = false;
                            break;
                        }
                        else bFlag=true;
                    }
                }
                else ExceptionHandles.HandleAssertion("Order was not equal to Asc or Desc.");
            }else ExceptionHandles.HandleAssertion("There is only one or less items in the list.");
        }
        catch(Exception e) {
            ExceptionHandles.HandleException(e, "Failed to confirm Sorting Order");
        }
        return bFlag;
    }

    public static String getPageURL() {
        String url="";
        try {
            url=driver.getCurrentUrl();
        } catch(Exception e) {
            ExceptionHandles.HandleException(e, "Unable to get URL");
        }
        return url;
    }

    public static void copyAndPasteString(String sText) {
        try {
            StringSelection ss = new StringSelection(sText);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch(Exception e) {
            ExceptionHandles.HandleException(e, "Unable to Copy and Paste");
        }
    }

    public static void copyAndPasteStringWithoutEnter(String sText) {
        try {
            StringSelection ss = new StringSelection(sText);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        } catch(Exception e) {
            ExceptionHandles.HandleException(e, "Unable to Copy and Paste");
        }
    }

    public static String RemoveFileExtention(String sDocumentName) {
        try {
            int index = sDocumentName.indexOf(".");
            if(index != -1) {
                sDocumentName = sDocumentName.substring(0, index);
            }
        } catch(Exception e) {
            ExceptionHandles.HandleException(e, "Unable to Remove File Extension");
        }
        return sDocumentName;
    }

    public static void DargAndDropSystemFile(File filePath, By dropArea, int offsetX, int offsetY) {
        try {
            if(filePath.exists()) {
                WebElement target = driver.findElement(dropArea);
                WebDriver driver = ((RemoteWebElement)target).getWrappedDriver();
                JavascriptExecutor jse = (JavascriptExecutor)driver;

                String JS_DROP_FILE =
                        "var target = arguments[0]," +
                                "    offsetX = arguments[1]," +
                                "    offsetY = arguments[2]," +
                                "    document = target.ownerDocument || document," +
                                "    window = document.defaultView || window;" +
                                "" +
                                "var input = document.createElement('INPUT');" +
                                "input.type = 'file';" +
                                //				    "input.style.display = 'none';" +
                                "input.onchange = function () {" +
                                "  var rect = target.getBoundingClientRect()," +
                                "      x = rect.left + (offsetX || (rect.width >> 1))," +
                                "      y = rect.top + (offsetY || (rect.height >> 1))," +
                                "      dataTransfer = { files: this.files };" +
                                "" +
                                "  ['dragenter', 'dragover', 'drop'].forEach(function (name) {" +
                                "    var evt = document.createEvent('MouseEvent');" +
                                "    evt.initMouseEvent(name, !0, !0, window, 0, 0, 0, x, y, !1, !1, !1, !1, 0, null);" +
                                "    evt.dataTransfer = dataTransfer;" +
                                "    target.dispatchEvent(evt);" +
                                "  });" +
                                "" +
                                //				    "  setTimeout(function () { document.body.removeChild(input); }, 25);" +
                                "};" +
                                "document.body.appendChild(input);" +
                                "return input;";

                WebElement input =  (WebElement)jse.executeScript(JS_DROP_FILE, target, offsetX, offsetY);
                Thread.sleep(3000);
                input.sendKeys(filePath.getAbsoluteFile().toString());
                Thread.sleep(2000);
            }
            else ExceptionHandles.HandleAssertion(filePath + " FilePath does not exist.");
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Unable to Drag and Drop File");
        }
    }

    public static boolean isDocumentDownloaded(String DocName) {
        boolean found = false;
        try {
            File folder = new File(System.getProperty("user.dir") + "//src//test//java//Downloads");
            File[] listOfFiles = folder.listFiles();
            File f = null;
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    String fileName = listOfFile.getName();
                    if(fileName.matches(DocName)) {
                        f=new File(fileName);
                        found = true;
                    }
                }
            }
        } catch(Exception e) {
            ExceptionHandles.HandleException(e, "Unable to Download Document.");
        }
        return found;
    }

    public static void DeleteDownloadedDocument(String DocName) {
        try {
            File folder = new File(System.getProperty("user.dir") + "//src//test//java//Downloads");
            File[] listOfFiles = folder.listFiles();
            File f = null;
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    String fileName = listOfFile.getName();
                    String AbsoluteFilePath = listOfFile.getAbsolutePath();
                    if(fileName.matches(DocName)) {
                        f=new File(AbsoluteFilePath);
                        f.delete();
                    }
                }
            }
        } catch(Exception e) {
            ExceptionHandles.HandleException(e, "Unable to Delete Document.");
        }
    }

    public static String GetScreenShot() throws Exception {
        String sScreenShotNameWithPath = null;
        try {
            Date oDate = new Date();
            SimpleDateFormat oSDF = new SimpleDateFormat("yyyyMMddHHmmss");
            String sDate = oSDF.format(oDate);
            File fScreenshot = ((TakesScreenshot) Accelerators.BaseClass.driver).getScreenshotAs(OutputType.FILE);
            sScreenShotNameWithPath = System.getProperty("user.dir")+"\\LexisConveyOnlineData\\target\\cucumber-reports\\Screenshots\\"+"Screenshot_" + sDate + ".png";
            FileUtils.copyFile(fScreenshot, new File(sScreenShotNameWithPath));
        } catch (Exception e) {
            ExceptionHandles.HandleScreenShotException(e, "Unable to take screen shot");
        }

        return sScreenShotNameWithPath;
    }

//	public static By toByVal(WebElement we) {
//		String[] data = we.toString().substring(0, we.toString().length() - 1).split(" -> ")[1].split(": ");
//		String locator = data[0];
//		String term = data[1];
//		try {
//			switch (locator) {
//			case "xpath":
//				return By.xpath(term);
//			case "css selector":
//				return By.cssSelector(term);
//			case "id":
//				return By.id(term);
//			case "tag name":
//				return By.tagName(term);
//			case "name":
//				return By.name(term);
//			case "link text":
//				return By.linkText(term);
//			case "class name":
//				return By.className(term);
//			}
//		} catch (Exception e) {
//			ExceptionHandles.HandleException(e, "Unable to locate the value of By");
//		}
//		return (By) we;
//	}

    public static void PressMouseRightclick(WebElement element ) {
        try {
            Actions action =new Actions(Accelerators.BaseClass.driver);
            action.contextClick(element).perform();

        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to mouse Right Click");
        }
    }

    public static void HoverMouse(By object) {
        try {
            Actions action = new Actions(Accelerators.BaseClass.driver);
            WebElement element = driver.findElement(object);
            action.moveToElement(element).perform();
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to hover over the element");
        }
    }

    public static void ZoomIn(int loopCount)
    {
        try {
            Robot robot = new Robot();
            for (int i = 0; i < loopCount; i++) {
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_ADD);
                robot.keyRelease(KeyEvent.VK_ADD);
                robot.keyRelease(KeyEvent.VK_CONTROL);
            }

        }
        catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to Zoom in");
        }

    }

    public static void ZoomOut(int loopCount)
    {
        try {
            Robot robot = new Robot();
            for (int i = 0; i < loopCount; i++) {
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_SUBTRACT);
                robot.keyRelease(KeyEvent.VK_SUBTRACT);
                robot.keyRelease(KeyEvent.VK_CONTROL);
            }
        }
        catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to Zoom Out");
        }

    }

    public static String GetWindowHandle() {
        return driver.getWindowHandle();
    }

    public static void SwitchToCurrentWindow(String windowString) {
        driver.switchTo().window(windowString);
    }

//	public static By toByVal(WebElement we) {
//		String[] data = we.toString().split(" -> ")[1].replaceAll("]$", "").split(": ");
//	    String locator = data[0];
//	    String term = data[1];
//
//	    switch (locator) {
//	        case "xpath":
//	            return By.xpath(term);
//	        case "css selector":
//	            return By.cssSelector(term);
//	        case "id":
//	            return By.id(term);
//	        case "tag name":
//	            return By.tagName(term);
//	        case "name":
//	            return By.name(term);
//	        case "link text":
//	            return By.linkText(term);
//	        case "class name":
//	            return By.className(term);
//	    }
//	    return null;
//	}

    public static String GetLocator(WebElement we) {
        String[] data = we.toString().split(" -> ")[1].replaceAll("]$", "").split(": ");
        String term = data[1];
        return term;
    }

    public static void SleepMethod(int sleepPeriod) throws InterruptedException
    {
        Thread.sleep(sleepPeriod);
    }

    public static void CloseTab()
    {
        driver.close();
    }

    public static String GetClassName(By object, String classString) {
        WebElement element = driver.findElement(object);
        String className = element.getAttribute("class");
        String[] classArray = className.split(" ");
        for(int i = 0; i< classArray.length; i++) {
            if(classArray[i].equals(classString)) {
                return classArray[i];
            }
        }
        return "Class name not found";
    }

    public static boolean HasClassName(By object, String classString) {
        WebElement element = driver.findElement(object);
        String className = element.getAttribute("class");
        String[] classArray = className.split(" ");
        for(int i = 0; i< classArray.length; i++) {
            if(classArray[i].equals(classString)) {
                return true;
            }
        }
        return false;
    }

    public static boolean HasAttributeValue(By object, String AttributeName, String AttributeValue) {
        WebElement element = driver.findElement(object);
        String ValueString = element.getAttribute(AttributeName);
        if(ValueString.equals(AttributeValue)) {
            return true;
        }
        return false;
    }

    public static String GetComparedString(By object, String textString) {
        WebElement element = driver.findElement(object);
        String className = element.getText();
        if(className.contains(textString.trim())) {
            return textString;
        }
        return "Text Not Found";
    }

    public static boolean HasComparedString(By object, String textString) {
        WebElement element = driver.findElement(object);
        String className = element.getText();
        if(className.contains(textString.trim())) {
            return true;
        }
        return false;
    }

    public static String getImageSrc(By object, String elementName) {
        String sText = "";
        try {
            if (driver.findElement(object).isDisplayed()) {
                sText = driver.findElement(object).getAttribute("src");
            } else ExceptionHandles.HandleAssertion("Failed to get image src from " + elementName);
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to get image src from " + elementName);
        }
        return sText;
    }

    public static By[] GetElementObjects(By elements, String elementName){
        List <WebElement> list = null;
        try {
            list = driver.findElements(elements);
        }
        catch(Exception e) {
            ExceptionHandles.HandleException(e, "Unable to get number of elements " + elementName);
        }
        By[] byArray = new By[list.size()];
        for(int i = 0; i<list.size(); i++) {
            By stringPath = By.xpath("(" + GetLocator(list.get(i)) + ")[" + (i+1) + "]");
            byArray[i] = stringPath;
        }
        return byArray;
    }

    public static void jsClickOnElement1(By object, String elementName) {
        try {
            //if (driver.findElement(object).isDisplayed()) {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", driver.findElement(object));
            //} else ExceptionHandles.HandleAssertion("Failed to click on " + elementName);
            Thread.sleep(1000);
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to click on " + elementName);
        }

    }

    public static void SwitchFrameByXpath(By object) {
        try
        {
            WebElement frame = driver.findElement(object);
            driver.switchTo().frame(frame);
        } catch(Exception e)
        {
            ExceptionHandles.HandleException(e,"Failed to switch frame by xpath");
        }
    }


    public static void SwitchFrameById(String iframeId) {
        try
        {
            FrameSwitcher.SwitchToFrame(driver, iframeId);
        } catch(Exception e)
        {
            ExceptionHandles.HandleException(e,"Failed to switch frame by id");
        }
    }

    public static void SwitchBackFromFrame() {
        try
        {
            FrameSwitcher.SwitchToDefault(driver);;
        } catch(Exception e)
        {
            ExceptionHandles.HandleException(e,"Failed to switch back from frame");
        }
    }

    public static void SwitchToDefaultContent() {
        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Failed to switch to default content");
        }
    }

//	public static String RetrievePseudoSelector(String rootClassName, String pseudoSelector, String expectedValue) throws InterruptedException {
//		String script = "return window.getComputedStyle(document.querySelector('." + rootClassName + "'),':" + pseudoSelector + "').getPropertyValue('" + expectedValue + "')";
//		Thread.sleep(3000); // Ensure the page has loaded before executing the script
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		String content = (String) js.executeScript(script);
//
//		// Replace '.expectedContent' with the actual expected content
//		if (content.equals(expectedValue)) {
//		    return content;
//		} else {
//		    return expected;
//		}
//	}

    public static void ClickElementBehindPseudoElement(By object) {
        WebElement checkbox = driver.findElement(object);
        if(!checkbox.isSelected()) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].click();", checkbox);
        }

    }



}
