Feature: Obtain token

  Scenario Outline: Obtaining tokens successfully
    When a customer exists in the system
    And the customer currently have <current_tokens> tokens
    Then the customer requests <request_amount> tokens
    And all the tokens should be unique

    Examples:
      | current_tokens | request_amount    |
      | 0              | 5                 |
      | 1              | 5                 |
      | 2              | 4                 |
      | 5              | 0                 |
      | 6              | 0                 |


  Scenario Outline: Obtaining tokens unsuccessfully
    When a customer exists in the system
    And the customer currently have <current_tokens> tokens
    Then the customer requests <request_amount> tokens
    And all the tokens should be unique

    Examples:
      | current_tokens | request_amount |
      | 6              | 1              |
      | 3              | 5              |


  Scenario Outline: Customer burns a token when they pay
    When a customer exists in the system
    And the customer currently has <tokens_before_pay> tokens left
    When the customer wants to pay with a token
    And the payment is successful
    Then the customer should have <tokens_after_pay> left
    And the used token should be invalidated

    Examples:
      | tokens_before_pay | tokens_after_pay |
      | 1                 | 0                |
      | 4                 | 3                |
      | 6                 | 5                |



