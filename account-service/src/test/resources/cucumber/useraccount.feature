Feature: Account
    Scenario: User creating an account
        When a user with first name "Bjarne", last name "Ivertsen", CPR "123456-7890" requests to create an account
        Then a new account with first name "Bjarne", last name "Ivertsen" and CPR "123456-7890" is created
        And a unique identifier for the account is some string
        And the account is not disabled
        And the account has a bank account attached

    Scenario: Attempt to create an account with an empty name
        When a user requests to create an account without supplying a name
        Then the account is not created
        And an empty name exception is thrown

    Scenario: Attempt to create an account with an empty CPR number
        When a user requests to create an account giving "" as the CPR number
        Then the account is not created
        And a missing CPR exception is thrown

    Scenario: Disabling an account
        Given that the system contains an account with CPR "123456-7890"
        When the account CPR "123456-7890" is requested to be disabled
        Then the account is disabled
