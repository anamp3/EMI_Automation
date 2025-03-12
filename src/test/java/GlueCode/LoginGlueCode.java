package GlueCode;

import Accelerators.ActionsClass;
import PageObjects.LoginObjects;
import Utilities.ExceptionHandles;
import Utilities.Utils;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;

public class LoginGlueCode {
    @Given("I am displayed with the Swag Labs title {string} data")
    public void i_am_displayed_with_the_swag_labs_title_data(String title) {
        By object = LoginObjects.swagLabs_title;
        String obj_name = Utils.getProperty(title);

        if(!ActionsClass.isElementVisible(object, obj_name)){
            ExceptionHandles.HandleAssertion(obj_name + "is not visible");
        }

        String objectName = ActionsClass.getElementText(object, obj_name);
        if(!objectName.equalsIgnoreCase(obj_name)){
            ExceptionHandles.HandleAssertion("Extracted and given title data is not the same");
        }
    }

    @And("The Login button is visible")
    public void the_login_button_is_visible() {
        By object = LoginObjects.login_btn;
        String obj_name = "Login Button";

        if(!ActionsClass.isElementVisible(object, obj_name)){
            ExceptionHandles.HandleAssertion(obj_name + "is not visible");
        }
    }

    @When("I enter then Standard Username {string} data")
    public void i_enter_then_standard_username_data(String standard) {
        By object = LoginObjects.username_txt;
        String object_value = Utils.getProperty(standard);
        String obj_name = "Standard User";

        if(!ActionsClass.isElementVisible(object, obj_name)){
            ExceptionHandles.HandleAssertion(obj_name + "is not visible");
        }
        else{
            ActionsClass.typeInTextBox(object, object_value, obj_name);
        }
    }

    @And("I enter the Password {string} data")
    public void i_enter_the_password_data(String password) {
        By object = LoginObjects.password_txt;
        String object_value = Utils.getProperty(password);
        String obj_name = "Password";

        if(!ActionsClass.isElementVisible(object, obj_name)){
            ExceptionHandles.HandleAssertion(obj_name + "is not visible");
        }
        else{
            ActionsClass.typeInTextBox(object, object_value, obj_name);
        }
    }

    @And("I click the Login button")
    public void i_click_the_login_button() {
        By object = LoginObjects.login_btn;
        String obj_name = "Login Button";

        if(!ActionsClass.isElementVisible(object, obj_name)){
            ExceptionHandles.HandleAssertion(obj_name + "is not visible");
        }
        else{
            ActionsClass.clickOnElement(object, obj_name);
        }
    }

    @Then("I am displayed with the Products {string} heading data")
    public void i_am_displayed_with_the_products_heading_data(String products) {
        By object = LoginObjects.product_title;
        String obj_name = Utils.getProperty(products);

        if(!ActionsClass.isElementVisible(object, obj_name)){
            ExceptionHandles.HandleAssertion(obj_name + "is not visible");
        }

        String objectName = ActionsClass.getElementText(object, obj_name);
        if(!objectName.equalsIgnoreCase(obj_name)){
            ExceptionHandles.HandleAssertion("Extracted and given title data is not the same");
        }
    }

    @Then("The filter dropdown")
    public void the_filter_dropdown() {
        By object = LoginObjects.product_title;
        String obj_name = "sorting Dropdown";

        if(!ActionsClass.isElementVisible(object, obj_name)){
            ExceptionHandles.HandleAssertion(obj_name + "is not visible");
        }
    }
}
