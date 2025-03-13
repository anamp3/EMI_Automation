package PageObjects;

import org.openqa.selenium.By;

public class AddItemObjects {
    public static final By slBackpack_btn = By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']");
    public static final By cartBadge = By.xpath("//span[@class='shopping_cart_badge']");
    public static final By shoppingCart = By.xpath("//a[@class='shopping_cart_link']");

    public static final By cartTitle = By.xpath("//span[@class='title']");
    public static final By cartItemContainer = By.xpath("//div[@class='cart_list']");
    public static final By slBackpack_hdng = By.xpath("//a[@id='item_4_title_link']//div[@class='inventory_item_name']");
}
