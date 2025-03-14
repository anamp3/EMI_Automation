Feature: Cart

  Background: The User Has Added An Item To The Cart
    Given I am displayed with the Swag Labs title "SwagLabsTitle" data
    And The Login button is visible
    When I enter then Standard Username "StandardUsername" data
    And I enter the Password "Password" data
    And I click the Login button
    Then I am displayed with the Products "ProductsHeading" heading data
    And The filter dropdown
    When I click the Add to cart button of the Sauce Labs Backpack item
    Then the shopping cart badge appears
    When I click the Cart Icon
    Then I am directed to the Your Cart "YourCart" page
    And I am able to see the Sauce Labs Backpack "SauceLabsBackpack" data

  Scenario: The user Removes The Item From The Cart
    When I click the Remove button of the Sauce Labs Backpack
    Then the item is removed from the cart

