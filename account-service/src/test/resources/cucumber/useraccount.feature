Feature: User Account
    Scenario: Creating a new user account
        When a user with first name "Bjarne", last name "Ivertsen", CPR "123456-7890" requests to create an account
        Then a new account with first name "Bjarne", last name "Ivertsen" and CPR "123456-7890" is created
        And a unique identifier for the account is some string
        And the account has a bank account attached

        #    Scenario: The bank account is related to the user CPR number
        #        Given a user with CPR "555555-5555" has been created
        #        When the bank account is fetched from the server
        #        Then the CPR number of the bank account is same as that of the user
        #
        #    Scenario: Attempt to create an account with an empty name
        #        When a user requests to create an account supplying the name ""
        #        Then the account is not created
        #        And an empty name exception is thrown
        #
        #    Scenario: Attempt to create an account with an empty CPR number
        #        When a user requests to create an account giving "" as the CPR number
        #        Then the account is not created
        #        And a missing CPR exception is thrown
        #
        #    Scenario: Disabling an account
        #        Given that the system contains an account with CPR "123456-7890"
        #        When the account CPR "123456-7890" is requested to be disabled
        #        Then the account is disabled
