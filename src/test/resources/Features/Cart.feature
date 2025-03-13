Feature: Cart

  Background: The user logs in the system
    Given I am displayed with the Swag Labs title "SwagLabsTitle" data
    And The Login button is visible
    When I enter then Standard Username "StandardUsername" data
    And I enter the Password "Password" data
    And I click the Login button
    Then I am displayed with the Products "ProductsHeading" heading data
    And The filter dropdown
  @Add
  Scenario: The User Adds An Item To The Cart
    When I click the Add to cart button of the Sauce Labs Backpack item
    Then the shopping cart badge appears
    When I click the Cart Icon
    Then I am directed to the Your Cart "YourCart" page
    And I am able to see the Sauce Labs Backpack "SauceLabsBackpack" data