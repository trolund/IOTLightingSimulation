Feature: Account Service
    Scenario: Initializing the account service
        Given a fresh instance of the service has been initialized
        Then the system contains 0 users

    Scenario: Creating a user
        Given there exists 0 users in the system
        When a new user is created
        Then a new user is instantiated
        And the user is added to the list of users
        And the system contains 1 user

    Scenario: User identifiers are dynamic
        Given there exists 1 or more users
        When another user is added
        Then the identifier for the new user is distinct from the existing users

    Scenario: Attempting to create multiple accounts with the same CPR number
        Given there exists an account with CPR number "123456-7890"
        When a user requests to create a new account with CPR number "123456-7890" 
        Then the account is not created
        And a duplicate account exception is thrown
