Feature: Account
    Scenario: User creating an account
        When a user with first name "Bjarne", last name "Ivertsen", CPR "123456-7890" requests an account
        Then a new account with first name "Bjarne", last name "Ivertsen" and CPR "123456-7890" is created
        And a unique ID associated to the account is stored
        And the account is not disabled
        And the account has a bank account attached

    Scenario: Creating multiple accounts with the same CPR number
        Given there exists an account with CPR number "123456-7890"
        When a user requests to create a new account with CPR number "123456-7890" 
        Then the account is not created
        And a duplicate exception is thrown

    Scenario: Creating an account without a name
        When a user requests to create an account without giving a name
        Then the account is not created for the user
        And an empty name exception is thrown

    Scenario: Creating an account without specifying CPR number
        When a user requests to create an account with provding a CPR number
        Then the account is not created for the user
        And a bad CPR exception is thrown

    Scenario: Disabling an account
        Given that the system contains an account with CPR "123456-7890"
        When the account CPR "123456-7890" is requested to be disabled
        Then the account is disabled
