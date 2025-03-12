package Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;

public class FrameSwitcher {
    private static HashMap<String, String> iFrames = new HashMap<String, String>();

    public static void SwitchToFrameByXpath(WebDriver driver, String iFrameName)
    {
        WebElement frame = driver.findElement(By.xpath(iFrameName));
        driver.switchTo().frame(frame);
    }

    public static void SwitchToDefault(WebDriver driver)
    {
        driver.switchTo().defaultContent();
    }

    public static void SwitchToFrame(WebDriver driver, String iFrameId)
    {
        WebElement frame = driver.findElement(By.id(iFrameId));
        driver.switchTo().frame(frame);
    }

    public static void SwitchToFrameByName(WebDriver driver, String iFrameName)
    {
        WebElement frame = driver.findElement(By.name(iFrameName));
        driver.switchTo().frame(frame);
    }

    public static void switchToSavedFrame(WebDriver driver, String iFrameId)
    {
        driver.switchTo().frame(GetOrSetFrame(driver, iFrameId));
    }

    private static String GetOrSetFrame(WebDriver driver, String iFrameId)
    {
        if(iFrames.containsKey(iFrameId))
        {
            return iFrames.get(iFrameId);
        } else
        {
            iFrames.put(iFrameId, iFrameId);
            return iFrames.get(iFrameId);
        }
    }
}
