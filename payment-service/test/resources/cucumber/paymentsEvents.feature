# author Troels (s161791)

Feature: Payment

  Scenario: Payment most fail because of wrong bank id's
    When a colleague working with accounts is done he notify me with "PaymentAccountsFailed"
    When i receive a message "PaymentAccountsFailed" it means that one or both accounts does not exist in the bank
    Then i notify the merchant that payment has failed

  Scenario: Payment most fail because of a invalid token
    When i receive "TokenValidationFailed"
    Then i have to notify the parties that the transaction failed with "PaymentFailed"

  Scenario: Payment process can continue when token is confirmed to be valid and bank do as it should
    When i receive "TokenValidationSuccessful"
    Then i notify bank that the money should be moved
    When the bank have confirmed the transaction
    Then i have to notify the parties that the money as been moved by sending "PaymentSuccessful"

  Scenario: Payment process can continue when token is confirmed to be valid but the bank's IT system is down
    When i receive "TokenValidationSuccessful"
    Then i notify bank that the money should be moved
    When the bank denies the transaction
    Then i have to notify the parties that the transaction failed with "PaymentFailed"