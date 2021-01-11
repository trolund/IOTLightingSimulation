Feature: Payment

  Scenario: Successful Payment
    Given a customer with id "2954f5aa-25ce-47ca-8371-615070ead10a"
    And a merchant with id "bf4c54c3-d3dd-4595-95ed-4b6ac99b4ba4"
    When the merchant initiates a payment for 20 kr by the customer
    Then the payment is successful

  Scenario: Customer is not known
    Given a customer with id "?"
    And a merchant with id "bf4c54c3-d3dd-4595-95ed-4b6ac99b4ba4"
    When the merchant initiates a payment for 10 kr by the customer
    Then the payment is not successful
    And an error message is returned saying "customer with id ? is unknown"

  Scenario: Merchant is not known
    Given a customer with id "2954f5aa-25ce-47ca-8371-615070ead10a"
    And a merchant with id "?"
    When the merchant initiates a payment for 20 kr by the customer
    Then the payment is not successful
    And an error message is returned saying "merchant with id ? is unknown"

  Scenario: List of transactions
    Given a successful payment of 20 kr from customer "2954f5aa-25ce-47ca-8371-615070ead10a" to merchant "bf4c54c3-d3dd-4595-95ed-4b6ac99b4ba4"
    When the manager asks for a list of transactions
    Then the list contains a transaction where customer "2954f5aa-25ce-47ca-8371-615070ead10a" paid 10 kr to merchant "bf4c54c3-d3dd-4595-95ed-4b6ac99b4ba4"

  Scenario: Successful Payment
    Given the customer "Troels" "Lund" with CPR "061094xxxx" has a bank account
    And the balance of that account is 1000
    And the customer is registered with DTUPay
    And the merchant "Jo" "Kuckles" with CPR number "110561-2741" has a bank account
    And the balance of that account is 2000
    And the merchant is registered with DTUPay
    When the merchant initiates a payment for "10" kr by the customer
    Then the payment is successful
    And the balance of the customer at the bank is 990 kr
    And the balance of the merchant at the bank is 2010 kr