package GlueCode;

import Accelerators.ActionsClass;
import PageObjects.CartObjects;
import Utilities.ExceptionHandles;
import Utilities.Utils;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;

public class CartGlueCode {
    int waitTime = 5;

    @When("I click the Add to cart button of the Sauce Labs Backpack item")
    public void i_click_the_add_to_cart_button_of_the_sauce_labs_backpack_item() {
        By object = CartObjects.slBackpack_btn;
        String obj_name = "Sauce Labs Backpack";

        if(!ActionsClass.isElementVisible(object, obj_name) && ActionsClass.waitForElementToBeVisible(object, waitTime)){
            ExceptionHandles.HandleAssertion(obj_name + " is not visible");
        }
        else
            ActionsClass.clickOnElement(object, obj_name);
    }

    @Then("the shopping cart badge appears")
    public void the_shopping_cart_badge_appears() {
        By object = CartObjects.cartBadge;
        String obj_name = "Cart Counter Badge";

        if(!ActionsClass.isElementVisible(object, obj_name) && ActionsClass.waitForElementToBeVisible(object, waitTime)){
            ExceptionHandles.HandleAssertion(obj_name + " is not visible");
        }
    }

    @When("I click the Cart Icon")
    public void i_click_the_cart_icon() {
        By object = CartObjects.shoppingCart;
        String obj_name = "Shopping Cart icon";

        if(!ActionsClass.isElementVisible(object, obj_name) && ActionsClass.waitForElementToBeVisible(object, waitTime)){
            ExceptionHandles.HandleAssertion(obj_name + " is not visible");
        }
        else
            ActionsClass.clickOnElement(object, obj_name);
    }

    @Then("I am directed to the Your Cart {string} page")
    public void i_am_directed_to_the_your_cart_page(String string) {
        By object = CartObjects.cartTitle;
        String obj_name = "Your Cart Title";

        if(!ActionsClass.isElementVisible(object, obj_name) && ActionsClass.waitForElementToBeVisible(object, waitTime)){
            ExceptionHandles.HandleAssertion(obj_name + " is not visible");
        }
    }

    @And("I am able to see the Sauce Labs Backpack {string} data")
    public void i_am_able_to_see_the_sauce_labs_backpack_data(String heading) {
        By container_obj = CartObjects.cartItemContainer;
        String container_objName = "Cart Items Container";

        By backPack_obj = CartObjects.slBackpack_hdng;
        String backPack_objName = "Sauce Labs Backpack Heading";
        String backPack_value = Utils.getProperty(heading);

        if(!ActionsClass.isElementVisible(container_obj, container_objName) && ActionsClass.waitForElementToBeVisible(container_obj, waitTime)){
            ExceptionHandles.HandleAssertion(container_objName + " is not visible");
        }

        if(!ActionsClass.isElementVisible(backPack_obj, backPack_objName) && ActionsClass.waitForElementToBeVisible(backPack_obj, waitTime)){
            ExceptionHandles.HandleAssertion(backPack_objName + " is not visible");
        }
        else{
            String extractedValue = ActionsClass.getElementText(backPack_obj, backPack_objName);
            if(!extractedValue.equalsIgnoreCase(backPack_value)){
                ExceptionHandles.HandleAssertion("Extracted and given values are not the same");
            }
        }
    }
}
