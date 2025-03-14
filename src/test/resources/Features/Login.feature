Feature: Login
  Background:
    Given I am displayed with the Swag Labs title "SwagLabsTitle" data
    And The Login button is visible

  Scenario: The Standard User Logs In
    When I enter then Standard Username "StandardUsername" data
    And I enter the Password "Password" data
    And I click the Login button
    Then I am displayed with the Products "ProductsHeading" heading data
    And The filter dropdown

  Scenario: The User Logs In With Incorrect Username
    When I enter then Incorrect Username "IncorrectUsername" data
    And I enter the Password "Password" data
    And I click the Login button
    Then the Error Message "ErrorMessage" is displayed
    And the user is still on the Login Page