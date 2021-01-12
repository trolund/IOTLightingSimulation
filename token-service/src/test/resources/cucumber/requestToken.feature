Feature: Customer requests tokens

  Scenario: Tokens given
    Given the customer with id "1234"
    And the customer has 0 tokens
    When the customer requests 3 tokens
    Then the customer owns 3 tokens

  Scenario: Tokens denied
    Given the customer with id "1234"
    And the customer has 2 tokens
    When the customer requests 2 tokens
    Then an exception is returned with the message "1234 has 2 tokens, and cannot request more."