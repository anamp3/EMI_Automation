package Accelerators;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import Utilities.ExceptionHandles;


public class BaseClass {

    public static WebDriver driver;
    public static String sBrowserName;
    public static String sBrowserVersion;

    public static WebDriver OpenBrowser() {
        sBrowserName = "Chrome";
        try {
            if (sBrowserName.equalsIgnoreCase("Chrome")) {

                ChromeOptions options = new ChromeOptions();
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("download.default_directory", System.getProperty("user.dir") + "\\src\\test\\java\\Downloads");
                options.setExperimentalOption("prefs", prefs);
                options.addArguments("--incognito");
                System.setProperty("webdriver.chrome.driver", "src/test/java/Drivers/chromedriver.exe");
                driver = new ChromeDriver(options);
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
                driver.manage().deleteAllCookies();
                driver.manage().window().maximize();
                sBrowserVersion = options.getVersion();

            } else if (sBrowserName.equalsIgnoreCase("Firefox")) {
                System.setProperty("webdriver.gecko.driver", "Drivers/geckodriver.exe");
                driver = new FirefoxDriver();
                driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
                driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
                driver.manage().window().maximize();
            } else if (sBrowserName.equalsIgnoreCase("IE")) {
                System.setProperty("webdriver.edge.driver", "Drivers/MicrosoftWebDriver.exe");
                driver = new EdgeDriver();
                driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
                driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
                driver.manage().window().maximize();
            }
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Unable to initialise browser " + sBrowserName);
        }
        return driver;
    }

    public static WebDriver tearDown() {

        try {
            driver.quit();
            System.out.println("Browser closed successful");

        } catch (Exception e) {
            ExceptionHandles.HandleException(e, "Unable to quit browser successful");
        }
        return driver;
    }


}
