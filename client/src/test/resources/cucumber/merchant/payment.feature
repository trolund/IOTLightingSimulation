Feature: Payment

  Background:
    Given a new customer with cpr "001122-XXXX", first name "Michael", last name "Hardy" and a balance of 1000
    When the customer is created
    Then the new customer exists in the system
    And the customer requests 5 tokens
    Given a new merchant with cpr "221100-XXXX", first name "Elyse", last name "Williams" and a balance of 1000
    When the merchant is created
    Then the new merchant exists in the system


  Scenario Outline: Successful Payment
    When the merchant initiates a payment for <amount> by the customer
    Then the merchant asks for a token from the customer
    And the payment is successful
    Then the customer should have a balance of <c_end_bal> left
    And the merchant should have a balance of <m_end_bal> left
    Then the latest transaction contain the amount <amount> for both accounts
    And the latest transaction related to the customer contain balance <c_end_bal>
    And the latest transaction related to the merchant contain balance <m_end_bal>

    Examples:
      | amount | c_end_bal | m_end_bal |
      | 10     | 990       | 1010      |
      | 25     | 975       | 1025      |
      | 0      | 1000      | 1000      |
      | 500    | 500       | 1500      |


  Scenario: Customer accountId not found
   Given a customer with accountId "does-not-exist" that does not exist in the system
   When the merchant initiates a payment for 10 by the customer
   Then the payment is unsuccessful


#  Scenario: Merchant accountID not found
#    Given a merchant with accountId "does-not-exist" that does not exist in the system
#    When the merchant initiates a payment for 10 by the customer
#    Then the payment is unsuccessful