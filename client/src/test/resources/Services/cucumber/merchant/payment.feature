Feature: Payment

  Background:
    Given a new customer with cpr "001122-XXXX", first name "Michael",
    And last name "Hardy", a balance of 1000
    Then when the customer is created
    Then the new customer exists in the system
    Given a new merchant with cpr "221100-XXXX", first name "Elyse",
    And last name "Williams", a balance of 1000
    Then when the merchant is created
    Then the new merchant exists in the system


  Scenario Outline: Successful Payment
    When the merchant initiates a payment for <amount> by the customer
    Then the customer should have a balance of <c_end_bal> left
    And the merchant should have a balance of <m_end_bal> left
    And the latest transaction contain the amount <amount> for both accounts
    And the latest transaction related to the customer contain balance <c_end_bal>
    And the latest transaction related to the merchant contain balance <m_end_bal>

    Examples:
      | amount | c_end_bal | m_end_bal |
      | 10     | 990       | 1010      |
      | 25     | 975       | 1025      |
      | 0      | 1000      | 1000      |
      | 500    | 500       | 1500      |




# Scenario: Customer not found
#    Given a wrong customer "?"
#    And a correct merchant ""
#    When the merchant initiates a payment for 10 by the customer
#    Then an error message "Customer (2954f5aa-25ce-47ca-8371-615070ead10a) is not found!" is returned

  #Scenario: Merchant not found
    # Given a customer with id "2954f5aa-25ce-47ca-8371-615070ead10a"
  #  And a merchant with id "bf4c54c3-d3dd-4595-95ed-4b6ac99b4ba4"
  #  When the merchant initiates a payment for 10 by the customer
  #  Then an error message "Merchant (bf4c54c3-d3dd-4595-95ed-4b6ac99b4ba4) is not found!" is returned