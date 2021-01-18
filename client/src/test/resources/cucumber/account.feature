# primary-author: Troels (s161791)
# co-author: Daniel (s151641)

Feature: Account management

  # Register account in dtupay
  # Register account that already exists

  # Get account by id that exists

  # Get account by cpr that exists
  # Get account by id that does not exist
  # Get account by cpr that does not exist


  Scenario Outline: Successful registration in DTUPay: Register account
    Given a new account with cpr <cpr>, first name <first_name>, last name <last_name> and an initial balance of <initial_balance>
    When the account is registered
    Then the registration should be successful
    And the new account should exist in the system with correct information

    Examples:
      | cpr             | first_name | last_name   | initial_balance |
      | "001122-XXXX"   | "Michael"  | "Hardy"     | 0               |
      | "221100-XXXX"   | "Elyse"    | "Williams"  | 50              |

  Scenario: Unsuccessful registration in DTUPay: Account already exists
    Given a new account with cpr "001122-XXXX", first name "Michael", last name "Hardy" and an initial balance of 50
    When the account is registered
    Then the registration should be successful
    Then the account is registered again
    Then the registration should be unsuccessful








#  Scenario: Unsuccessful registration in DTUPay: Register user
#    Given a registered merchant with cpr "001122-XXXX" who has been to numerolog
#    And wants to change their name to first name "Mogens" and last name "Ukkerholt"
#    Then the system should update the information accordingly
#
#  Scenario: Retire merchant in DTUPay
#    Given a registered merchant with cpr "001122-XXXX" who is tired of DTUPay
#    And wants to retire their account
#    Then their information should be deleted


