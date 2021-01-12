Feature: Customer requests tokens

  Scenario: Tokens given
    Given the customer with id "1234"
    And the customer has 0 tokens
    When the customer request 3 tokens
    Then 3 tokens are created
    And the tokens are given to the customer

  Scenario: Tokens denied
    Given the customer with id "1234"
    And the customer has 2 tokens
    When the customer request 2 tokens
    Then an exception is returned with the message "A user can only request tokens when it has 1 or fewer unused tokens"