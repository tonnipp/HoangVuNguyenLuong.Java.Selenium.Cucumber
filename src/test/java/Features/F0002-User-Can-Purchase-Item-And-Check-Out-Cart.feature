Feature: User-Can-Purchase-Item-And-Check-Out-Cart
  Scenario: User choose item and checkout cart
    Given User login to system
    When User navigate successfully to "home page"
    Then User navigate to "shopping cart page" by URL
    When User navigate successfully to "shopping cart page"
    Then User verify there is 0 product in shopping cart page
    When User navigate back to "home page"
