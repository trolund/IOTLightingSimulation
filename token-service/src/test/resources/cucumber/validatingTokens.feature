Feature: Validation of tokens

  Scenario: Validation of an unused token
    Given a customer with id "4638"
    And has an unused token
    And the customer receives a token
    When a token is sent to the server
    Then the token is invalidated
    And the customer "4638" is returned

  Scenario: Validation of a used token
    Given a customer with id "1348"
    And has an unused token
    And the customer receives a token
    And the token is deleted
    When a token is sent to the server
    Then a TokenNotFound exception is returned

  Scenario: Customer tries to get while having no tokens
    Given a customer with id "1385"
    When the customer tries to receive a token
    Then an event of type "GetTokenFailed" is received
    And a "Customer 1385 has no tokens." exception is returned