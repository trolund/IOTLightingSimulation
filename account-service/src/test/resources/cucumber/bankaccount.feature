Feature: Bank accounts
    Scenario: Fetching a non-existent bank account
        When a request to get the account information related to CPR "555555-5555"
        Then an error message is returned

    Scenario: Creating a new bank account
        When requesting to create a new account with the bank for a person
        And the first name is "Bjarne"
        And the last name is "Ivertsen"
        And the CPR number is "123456-7890"
        And the initial balance is set to 5000
        Then the bank account is created with the ID given in the response
        And the first name, last name, CPR and initial balance are set

    Scenario: Creating a duplicate bank account
        Given that a bank account has already been created for CPR "555555-5555"
        When requesting to create a new account with the bank for a person
        And the CPR number is "555555-5555"
        Then the bank account is not created
        And an exception is thrown containing the error message from the response

    Scenario: Fetching a bank account by CPR number
        Given that a bank account belonging to CPR "555555-5555" exists
        When requesting the account details for the account is sent
        Then the account details belonging to the CPR number are retrieved from the banking server

    Scenario: Deleting an existing bank account
        Given that a bank account belonging to CPR "555555-5555" has been previously created
        When a request to delete the account is sent to the banking server
        Then the account is successfully deleted from the server

    Scenario: Deleting an non-existent bank account
        Given that a bank account belonging to CPR "555555-5555" does not exist
        When a request to delete the account is sent to the banking server
        Then an error message is sent with the response
