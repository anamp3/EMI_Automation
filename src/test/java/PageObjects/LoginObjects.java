package PageObjects;

import org.openqa.selenium.By;

public class LoginObjects {
    public static final By swagLabs_title = By.xpath("//div[@class='login_logo']");
    public static final By username_txt = By.xpath("//input[@id='user-name']");
    public static final By password_txt = By.xpath("//input[@id='password']");
    public static final By login_btn = By.xpath("//input[@id='login-button']");

    public static final By product_title = By.xpath("//span[@class='title']");
    public static final By sorting_drp = By.xpath("//select[@class='product_sort_container']");
}
