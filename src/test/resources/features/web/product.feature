@regression @product
Feature: Product Browsing
  As a user
  I want to browse products
  So that I can view and purchase items

  Background:
    Given user navigates to DemoBlaze homepage

  Scenario: View products in Phones category
    When user clicks on "Phones" category
    Then user should see the homepage

  Scenario: View products in Laptops category
    When user clicks on "Laptops" category
    Then user should see the homepage

  Scenario: View products in Monitors category
    When user clicks on "Monitors" category
    Then user should see the homepage