Feature: Payment

  Background:
    Given a new customer with cpr "001122-XXXX", first name "Michael", last name "Hardy" and a balance of 1000
    When the customer is registered
    Then the customer registration should be successful
    And the new customer should exist in the system
    Then the customer requests 5 tokens
    Given a new merchant with cpr "221100-XXXX", first name "Elyse", last name "Williams" and a balance of 1000
    When the merchant is registered
    Then the merchant registration should be successful
    Then the new merchant exists in the system

  Scenario Outline: Successful Payment in DTUPay
    When the merchant initiates a payment for <amount> by the customer
    And the merchant asks for a token from the customer
    Then the merchant should receive a token
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


    # these tests have to be rethought. where does the token come in to play?
  Scenario: Unsuccessful payment in DTUPay: Customer not found
   Given a customer with id "does-not-exist" that does not exist in the system
   And a merchant that exists in the system
   When the merchant initiates a payment for 10 by the customer
   Then the payment is unsuccessful

  Scenario: Unsuccessful payment in DTUPay: Merchant not found
    Given a customer that exists in the system
    And a merchant with id "does-not-exist" that does not exist in the system
    When the merchant initiates a payment for 10 by the customer
    Then the payment is unsuccessful

  Scenario: Unsuccessful payment in DTUPay: Customer does not have adequate balance
    Given a customer that exists in the system
    And a merchant that exists in the system
    When the merchant initiates a payment for 2000 by the customer
    Then the payment is unsuccessful

  Scenario: Unsuccessful payment in DTUPay: Merchant initiates negative payment
    Given a customer that exists in the system
    And a merchant that exists in the system
    When the merchant initiates a payment for -2000 by the customer
    Then the payment is unsuccessful