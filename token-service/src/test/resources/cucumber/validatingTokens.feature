Feature: Validation of tokens

  Scenario: Validation of an unused token
    Given a customer with id "1234"
    And has an unused token
    When a token is received
    Then the token is invalidated
    And the customer "1234" is returned

  Scenario: Validation of a used token
    Given a customer with id "1234"
    And has an unused token
    And a token is received
    And the token is invalidated
    When a token is received
    Then a TokenNotFound exception is returned