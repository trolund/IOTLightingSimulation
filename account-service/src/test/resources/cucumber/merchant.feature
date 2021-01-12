Feature: Merchant
    Scenario: Creating an account
        When a merchant named "Bjarne Ivertsen" with CPR number "123456-7890" requests to create an account
        Then a new merchant account with first name "Bjarne" and last name "Ivertsen" is created
        And the account is not connected to a bank account
        And the account is not disabled

    Scenario: Creating multiple accounts with the same name
        Given: there exists a merchant account with full name "Bjarne Ivertsen"
        When: another merchant named "Bjarne Ivertsen" requests to create an account
        Then: a new account with full name "Bjarne Ivertsen" is created
        And: the identifiers for the two similarly named accounts are distinct

    Scenario: Creating multiple accounts with the same CPR number
        Given: there exists a merchant account with CPR number "123456-7890"
        When: a request to create a new merchant account with CPR number "123456-7890" 
        Then: the account is not created
        And: an duplicate exception is thrown

    Scenario: Creating an account without a name
        When: a merchant requests to create an account without giving a name
        Then: the account is not created
        And: an empty name exception is thrown

    Scenario: Attaching a bank account
        Given: a merchant named "Bjarne Ivertsen" exists in the system
        When: the merchant does not currently have a bank account attached
        And: the merchant attaches a bank account to his merchant account
        Then: the bank account is attached

    Scenario: Replacing a bank account
        Given: a merchant named "Bjarne Ivertsen" exists in the system
        When: the merchant currently has a bank account attached
        And: the merchant attaches a new bank account to his merchant account
        Then: the bank account is replaced

    Scenario: Disabling an account
        Given that the system contains a merchant named "Bjarne Ivertsen" and ID 0
        When the account named "Bjarne Ivertsen" with ID 0 is disabled
        Then the account is disabled
