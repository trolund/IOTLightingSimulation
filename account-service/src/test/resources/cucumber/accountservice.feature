Feature: Account Service
    Scenario: Initializing the account service
        Given a fresh instance of the service has been initialized
        Then the system contains 0 users

    Scenario: Adding a user
        Given a fresh instance of the account service has been initialized
        When a new user is created giving first name, last name and CPR
        Then the user is added to the list of users and the number of users is 1

    Scenario: Adding a user
        Given: a fresh instance of the service has been initialized
        When a new user object is added
        Then the user is added to the list of users and the number of users is 1

    Scenario: User identifiers are dynamic
        Given there exists some users in the system
        When another user is added
        Then the identifier for the new user is distinct from the existing users

    Scenario: Attempting to create multiple accounts with the same CPR number
        Given there exists an account with CPR number "123456-7890"
        When a user requests to create a new account with CPR number "123456-7890" 
        Then the account is not created
        And a duplicate account exception is thrown
