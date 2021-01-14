Feature: Validation of tokens

  Scenario: Validation of an unused token
    Given a customer with id "1234"
    And has an unused token
    And the customer receives a token
    When a token is sent to the server
    Then the token is invalidated
    And the customer "1234" is returned

  Scenario: Validation of a used token
    Given a customer with id "1234"
    And has an unused token
    And the customer receives a token
    And the token is deleted
    When a token is sent to the server
    Then a TokenNotFound exception is returned

  Scenario: Customer tries to get while having no tokens
    Given a customer with id "1234"
    When the customer tries to receive a token
    Then a "Customer 1234 has no tokens." exception is returned