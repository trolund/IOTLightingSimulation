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
    Then an exception is returned with the message "Customer (1234) has 2 tokens, and cannot request more."

  Scenario: Customer not found
    Given no customer exists with id "2345"
    When the customer requests 2 tokens
    Then an exception is returned with the message "Customer (2345) was not found."

  Scenario: Delete customer
    Given the customer with id "1234"
    When the customer is deleted
    Then the customer is not found