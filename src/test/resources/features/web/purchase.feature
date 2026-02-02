@regression @purchase
Feature: Phone Product Purchase and Checkout
  As a user
  I want to purchase a phone product
  So that I can complete the checkout process

  Background:
    Given user navigates to DemoBlaze homepage

  Scenario Outline: Add phone product to cart and complete checkout
    When user clicks on "Phones" category
    And user clicks on "<productName>" product
    And user clicks on Add to cart button
    Then user should see "Product added" alert message
    When user clicks on Cart
    Then user should see product "<productName>" in cart
    When user clicks on Place Order button
    And user fills order details with name "<name>", country "<country>", city "<city>", card "<card>", month "<month>", year "<year>"
    And user clicks on Purchase button
    Then user should see "Thank you for your purchase!" success message
    And user clicks OK on success message

    Examples:
      | productName          | name          | country   | city       | card             | month | year |
      | Nexus 6              | Carol Davis   | France    | Paris      | 3333444455556666 | 09    | 2027 |
      | Samsung galaxy s7    | David Lee     | Japan     | Tokyo      | 4444555566667777 | 11    | 2025 |
