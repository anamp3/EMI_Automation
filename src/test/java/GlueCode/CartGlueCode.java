package GlueCode;

import Accelerators.ActionsClass;
import PageObjects.AddItemObjects;
import PageObjects.CartObjects;
import Utilities.ExceptionHandles;
import Utilities.Utils;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;

public class CartGlueCode {
    int waitTime = 50;

    @When("I click the Remove button of the Sauce Labs Backpack")
    public void i_click_the_remove_button_of_the_sauce_labs_backpack() {
        By object = CartObjects.slBackpackRemove_btn;
        String obj_name = "Shopping Cart icon";

        if(!ActionsClass.isElementVisible(object, obj_name) && ActionsClass.waitForElementToBeVisible(object, waitTime)){
            ExceptionHandles.HandleAssertion(obj_name + " is not visible");
        }
        else
            ActionsClass.clickOnElement(object, obj_name);
    }

    @Then("the item is removed from the cart")
    public void the_item_is_removed_from_the_cart() {
        By backPack_obj = AddItemObjects.slBackpack_hdng;
        String backPack_objName = "Sauce Labs Backpack Heading";

        if(ActionsClass.isElementVisible(backPack_obj, backPack_objName) && ActionsClass.waitForElementToBeInvisible(backPack_obj, waitTime)){
            ExceptionHandles.HandleAssertion(backPack_objName + " is still visible");
        }
    }
}
