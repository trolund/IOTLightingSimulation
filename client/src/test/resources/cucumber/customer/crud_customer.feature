Feature: Customer functionality

  # create, read
  Scenario Outline: Register customer in DTUPay
    Given a person with cpr <cpr>
    And first name <first_name>, last name <last_name>
    When they get registered in the system with balance <initial_balance>
    Then they should have an account with balance <initial_balance>
    And correct cpr, first name and last name

    Examples:
      | cpr             | first_name | last_name   | initial_balance |
      | "001122-XXXX"   | "Michael"  | "Hardy"     | 0               |
      | "221100-XXXX"   | "Elyse"    | "Williams"  | 50              |

  # update
  Scenario: Update customer information in DTUPay
    Given a registered customer with cpr "001122-XXXX" who has been to numerolog
    And wants to change their name to first name "Mogens" and last name "Ukkerholt"
    Then the system should update the information accordingly

  # retire/delete
  Scenario: Retire customer in DTUPay
    Given a registered customer with cpr "001122-XXXX" who is tired of DTUPay
    And wants to retire their account
    Then their information should be deleted