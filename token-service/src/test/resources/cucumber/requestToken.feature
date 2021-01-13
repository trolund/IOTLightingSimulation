Feature: Customer requests tokens

  Scenario: Tokens given
    Given the customer with id "1234"
    When the customer requests 3 tokens
    Then the customer owns 3 tokens

  Scenario: Customer has too many tokens to request more
    Given the customer with id "1234"
    And the customer has 2 tokens
    When the customer requests 2 tokens
    Then an exception is returned with the message "Customer (1234) cannot request 2 tokens"
    
  Scenario: Customer tries to request negative tokens
    Given the customer with id "1234"
    When the customer requests -1 tokens
    Then an exception is returned with the message "Customer (1234) cannot request -1 tokens"
    
  Scenario: Customer tries to request too many tokens
    Given the customer with id "1234"
    When the customer requests 10 tokens
    Then an exception is returned with the message "Customer (1234) cannot request 10 tokens"

  Scenario: Customer not found
    Given no customer exists with id "2345"
    When the customer requests 2 tokens
    Then the customer owns 2 tokens

  Scenario: Customer already registered
    Given the customer with id "1234"
    When another customer with id "1234" is registered
    Then an exception is returned with the message "Customer (1234) is already registered."

  Scenario: Delete customer
    Given the customer with id "1234"
    When the customer is deleted
    Then the customer is not found