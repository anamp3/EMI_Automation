package GlueCode;

import Accelerators.ActionsClass;
import PageObjects.LoginObjects;
import Utilities.ExceptionHandles;
import Utilities.Utils;
import io.cucumber.java.en.*;
import org.apache.poi.ss.formula.functions.Log;
import org.openqa.selenium.By;

public class LoginGlueCode {
    int waitTime = 50;

    @Given("I am displayed with the Swag Labs title {string} data")
    public void i_am_displayed_with_the_swag_labs_title_data(String title) {
        By object = LoginObjects.swagLabs_title;
        String obj_name = Utils.getProperty(title);

        if (!ActionsClass.isElementVisible(object, obj_name)) {
            ExceptionHandles.HandleAssertion(obj_name + "is not visible");
        }

        String objectName = ActionsClass.getElementText(object, obj_name);
        if (!objectName.equalsIgnoreCase(obj_name)) {
            ExceptionHandles.HandleAssertion("Extracted and given title data is not the same");
        }
    }

    @And("The Login button is visible")
    public void the_login_button_is_visible() {
        By object = LoginObjects.login_btn;
        String obj_name = "Login Button";

        if (!ActionsClass.isElementVisible(object, obj_name)) {
            ExceptionHandles.HandleAssertion(obj_name + "is not visible");
        }
    }

    @When("I enter then Standard Username {string} data")
    public void i_enter_then_standard_username_data(String standard) {
        By object = LoginObjects.username_txt;
        String object_value = Utils.getProperty(standard);
        String obj_name = "Standard User";

        if (!ActionsClass.isElementVisible(object, obj_name)) {
            ExceptionHandles.HandleAssertion(obj_name + "is not visible");
        } else {
            ActionsClass.typeInTextBox(object, object_value, obj_name);
        }
    }

    @And("I enter the Password {string} data")
    public void i_enter_the_password_data(String password) {
        By object = LoginObjects.password_txt;
        String object_value = Utils.getProperty(password);
        String obj_name = "Password";

        if (!ActionsClass.isElementVisible(object, obj_name)) {
            ExceptionHandles.HandleAssertion(obj_name + "is not visible");
        } else {
            ActionsClass.typeInTextBox(object, object_value, obj_name);
        }
    }

    @And("I click the Login button")
    public void i_click_the_login_button() {
        By object = LoginObjects.login_btn;
        String obj_name = "Login Button";

        if (!ActionsClass.isElementVisible(object, obj_name)) {
            ExceptionHandles.HandleAssertion(obj_name + "is not visible");
        } else {
            ActionsClass.clickOnElement(object, obj_name);
        }
    }

    @Then("I am displayed with the Products {string} heading data")
    public void i_am_displayed_with_the_products_heading_data(String products) {
        By object = LoginObjects.product_title;
        String obj_name = Utils.getProperty(products);

        if (!ActionsClass.isElementVisible(object, obj_name)) {
            ExceptionHandles.HandleAssertion(obj_name + "is not visible");
        }

        String objectName = ActionsClass.getElementText(object, obj_name);
        if (!objectName.equalsIgnoreCase(obj_name)) {
            ExceptionHandles.HandleAssertion("Extracted and given title data is not the same");
        }
    }

    @Then("The filter dropdown")
    public void the_filter_dropdown() {
        By object = LoginObjects.sorting_drp;
        String obj_name = "Sorting Dropdown";

        if (!ActionsClass.isElementVisible(object, obj_name)) {
            ExceptionHandles.HandleAssertion(obj_name + "is not visible");
        }
    }

    @When("I enter then Incorrect Username {string} data")
    public void i_enter_then_incorrect_username_data(String incorrect) {
        By object = LoginObjects.username_txt;
        String object_value = Utils.getProperty(incorrect);
        String obj_name = "Incorrect User";

        if (!ActionsClass.isElementVisible(object, obj_name)) {
            ExceptionHandles.HandleAssertion(obj_name + "is not visible");
        } else {
            ActionsClass.typeInTextBox(object, object_value, obj_name);
        }
    }

    @Then("the Error Message {string} is displayed")
    public void the_error_message_is_displayed(String error) {
        By object = LoginObjects.errorMessage;
        String obj_name = "Error Message";
        String obj_value = Utils.getProperty(error);

        if (!ActionsClass.isElementVisible(object, obj_name) && ActionsClass.waitForElementToBeVisible(object, waitTime)) {
            ExceptionHandles.HandleAssertion(obj_name + " is not visible");
        } else {
            String extractedValue = ActionsClass.getElementText(object, obj_name);
            if (!extractedValue.contains(obj_value)) {
                ExceptionHandles.HandleAssertion("Extracted and given values are not the same");
            }
        }
    }

    @Then("the user is still on the Login Page")
    public void the_user_is_still_on_the_login_page() {
        By object = LoginObjects.login_btn;
        String obj_name = "Login Button";

        if (!ActionsClass.isElementVisible(object, obj_name)) {
            ExceptionHandles.HandleAssertion(obj_name + "is not visible");
        }
    }

}
