# primary-author: Troels (s161791)
# co-author: Daniel (s151641)

Feature: Account

  # Register user in dtupay
  # Register user that already exists

  # Get user by id that exists

  # Get user by cpr that exists
  # Get user by id that does not exist
  # Get user by cpr that does not exist


  Scenario Outline: Successful registration in DTUPay: Register user
    Given a new user with cpr <cpr>, first name <first_name>, last name <last_name> and a balance of <initial_balance>
    When the user is registered
    Then the registration should be successful
    And the new user should exist in the system with the correct variables

    Examples:
      | cpr             | first_name | last_name   | initial_balance |
      | "001122-XXXX"   | "Michael"  | "Hardy"     | 0               |
      | "221100-XXXX"   | "Elyse"    | "Williams"  | 50              |

  Scenario: Unsuccessful registration in DTUPay: User already exists
    Given a new user with cpr "001122-XXXX", first name "Michael", last name "Hardy" and a balance of 50
    When the user is registered
    Then the user is registered again
    And the registration should be unsuccessful










  Scenario: Unsuccessful registration in DTUPay: Register user
    Given a registered merchant with cpr "001122-XXXX" who has been to numerolog
    And wants to change their name to first name "Mogens" and last name "Ukkerholt"
    Then the system should update the information accordingly

  Scenario: Retire merchant in DTUPay
    Given a registered merchant with cpr "001122-XXXX" who is tired of DTUPay
    And wants to retire their account
    Then their information should be deleted


