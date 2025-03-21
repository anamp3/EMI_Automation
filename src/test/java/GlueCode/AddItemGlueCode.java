package GlueCode;

import Accelerators.ActionsClass;
import PageObjects.AddItemObjects;
import PageObjects.LoginObjects;
import Utilities.ExceptionHandles;
import Utilities.Utils;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;

public class AddItemGlueCode {
    int waitTime = 5;

    @When("I click the Add to cart button of the Sauce Labs Backpack item")
    public void i_click_the_add_to_cart_button_of_the_sauce_labs_backpack_item() {
        By object = AddItemObjects.slBackpack_btn;
        String obj_name = "Sauce Labs Backpack";

        if (!ActionsClass.isElementVisible(object, obj_name) && ActionsClass.waitForElementToBeVisible(object, waitTime)) {
            ExceptionHandles.HandleAssertion(obj_name + " is not visible");
        } else
            ActionsClass.clickOnElement(object, obj_name);
    }

    @Then("the shopping cart badge appears")
    public void the_shopping_cart_badge_appears() {
        By object = AddItemObjects.cartBadge;
        String obj_name = "Cart Counter Badge";

        if (!ActionsClass.isElementVisible(object, obj_name) && ActionsClass.waitForElementToBeVisible(object, waitTime)) {
            ExceptionHandles.HandleAssertion(obj_name + " is not visible");
        }
    }

    @When("I click the Cart Icon")
    public void i_click_the_cart_icon() {
        By object = AddItemObjects.shoppingCart;
        String obj_name = "Shopping Cart icon";

        if (!ActionsClass.isElementVisible(object, obj_name) && ActionsClass.waitForElementToBeVisible(object, waitTime)) {
            ExceptionHandles.HandleAssertion(obj_name + " is not visible");
        } else
            ActionsClass.clickOnElement(object, obj_name);
    }

    @Then("I am directed to the Your Cart {string} page")
    public void i_am_directed_to_the_your_cart_page(String title) {
        By object = AddItemObjects.cartTitle;
        String obj_name = "Your Cart Title";
        String obj_value = Utils.getProperty(title);

        if (!ActionsClass.isElementVisible(object, obj_name) && ActionsClass.waitForElementToBeVisible(object, waitTime)) {
            ExceptionHandles.HandleAssertion(obj_name + " is not visible");
        } else {
            String extractedValue = ActionsClass.getElementText(object, obj_name);
            if (!extractedValue.equalsIgnoreCase(obj_value)) {
                ExceptionHandles.HandleAssertion("Extracted and given values are not the same");
            }
        }
    }

    @And("I am able to see the Sauce Labs Backpack {string} data")
    public void i_am_able_to_see_the_sauce_labs_backpack_data(String heading) {
        By container_obj = AddItemObjects.cartItemContainer;
        String container_objName = "Cart Items Container";

        By backPack_obj = AddItemObjects.slBackpack_hdng;
        String backPack_objName = "Sauce Labs Backpack Heading";
        String backPack_value = Utils.getProperty(heading);

        if (!ActionsClass.isElementVisible(container_obj, container_objName) && ActionsClass.waitForElementToBeVisible(container_obj, waitTime)) {
            ExceptionHandles.HandleAssertion(container_objName + " is not visible");
        }

        if (!ActionsClass.isElementVisible(backPack_obj, backPack_objName) && ActionsClass.waitForElementToBeVisible(backPack_obj, waitTime)) {
            ExceptionHandles.HandleAssertion(backPack_objName + " is not visible");
        } else {
            String extractedValue = ActionsClass.getElementText(backPack_obj, backPack_objName);
            if (!extractedValue.equalsIgnoreCase(backPack_value)) {
                ExceptionHandles.HandleAssertion("Extracted and given values are not the same");
            }
        }
    }

    @When("I click the Sorting Dropdown")
    public void i_click_the_sorting_dropdown() {
        By object = LoginObjects.sorting_drp;
        String obj_name = "Sorting Dropdown";

        if (!ActionsClass.isElementVisible(object, obj_name) && ActionsClass.waitForElementToBeVisible(object, waitTime)) {
            ExceptionHandles.HandleAssertion(obj_name + " is not visible");
        } else
            ActionsClass.clickOnElement(object, obj_name);
    }

    @And("I click the Price Low to High option")
    public void i_click_the_price_low_to_high_option() {
        By object = AddItemObjects.priceLohi;
        String obj_name = "Price (low to high) Option";

        if (!ActionsClass.isElementVisible(object, obj_name) && ActionsClass.waitForElementToBeVisible(object, waitTime)) {
            ExceptionHandles.HandleAssertion(obj_name + " is not visible");
        } else
            ActionsClass.clickOnElement(object, obj_name);
    }

    @Then("the items are sorted by their prices in ascending order")
    public void the_items_are_sorted_by_their_prices_in_ascending_order() {
        By price_objs = AddItemObjects.prices;
        String price_names = "Item Prices";

        By[] elements = ActionsClass.GetElementObjects(price_objs, price_names);
        String lowestPrice = ActionsClass.getElementText(elements[0], price_names);
        String sliced_LP = lowestPrice.substring(1,5);
        double num = Double.parseDouble(sliced_LP);

        for (int i = 0; i < elements.length; i++){
            String followingPrices = ActionsClass.getElementText(elements[i], price_names);
            String sliced_LPs = followingPrices.substring(1,5);
            double nums = Double.parseDouble(sliced_LPs);

            if(num > nums){
               ExceptionHandles.HandleAssertion("The first sorted price is greater than the number "+ i+1 + " sorted prce");
            }
        }
    }

}
