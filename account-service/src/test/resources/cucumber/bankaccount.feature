#Feature: Bank accounts
#    Scenario: Fetching a non-existent bank account
#        Given that the bank account with CPR "5555555-5555" does not exist with the bank
#        When requesting to get the details for the account with CPR "555555-5555"
#        Then the account is not modified 
#
#    Scenario: Creating a new bank account
#        Given that the account object has not been initialized
#        When requesting to create a new account with the bank for first and last name "Bjarne" "Ivertsen", CPR "123456-7890" and balance 5000
#        Then the bank account is created with bank account ID set
#        And the first name, last name, CPR and initial balance are set
#
#    Scenario: Creating a duplicate bank account
#        Given that a bank account belonging to CPR "555555-5555" exists with the bank
#        When requesting to create a new account with the bank with CPR "555555-5555"
#        Then the bank account is not created and the bank account ID is not set
#
#    Scenario: Fetching a bank account by CPR number
#        Given that the account object has not been initialized
#        And that a bank account belonging to CPR "555555-5555" exists with the bank
#        When requesting to get the account details for CPR "555555-5555"
#        Then the account is initialized with a copy of the details and CPR "555555-5555"
