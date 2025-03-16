package Accelerators;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.ExceptionHandles;
import Utilities.Utils;

public class ActionsClass extends Accelerators.BaseClass {

    public static String sTestCaseName;
    public static final String EnvironmentURL = Utils.getProperty("SiteURL");


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


}
