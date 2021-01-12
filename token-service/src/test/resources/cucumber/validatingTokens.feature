Feature: Validation of tokens

  Scenario: Validation of an unused token
    Given the customer with id "1234"
    And the customer with id "1234" has a token with id "xyz"
    And the token is unused
    When a token with id "xyz" is received
    And the token matches the customer "1234"
    Then the token is invalidated
    And the customer "1234" is returned

  Scenario: Validation of a used token
    Given the customer with id "1234"
    And the customer with id "1234" has a token with id "xyz"
    And the token is used
    When a token with id "xyz" is received
    And the token matches the customer "1234"
    Then an exception is returned with the message "This token has already been used"

  Scenario: Validation of an unknown token
    When a token with id "abc" is received
    Then an exception is returned with the message "This token does not match any customer"