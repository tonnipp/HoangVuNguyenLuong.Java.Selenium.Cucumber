Feature: User-Can-Add-Multiple-Items-To-Shopping-Cart

  Scenario: User can add multiple products to cart
    Given User login to system
    When User navigate successfully to "home page"
    Then User navigate to "shopping cart page" by URL
    When User navigate successfully to "shopping cart page"
    Then User verify there is 0 product in shopping cart page
    When User navigate back to "home page"
    When User add the list of "<product>"
      | product                 |
      | Sauce Labs Backpack     |
      | Sauce Labs Bike Light   |
      | Sauce Labs Bolt T-Shirt |
    When User navigate succesfully to "shopping cart"
    Then User verify there is 3 product in shopping cart
    Then Verify that the list of "<product>" is displayed
      | product                 |
      | Sauce Labs Backpack     |
      | Sauce Labs Bike Light   |
      | Sauce Labs Bolt T-Shirt |
