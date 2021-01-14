Feature: Merchant reporting
  Scenario: Merchant requests a list of transactions
    Given a list of merchant transactions
      | amount | balance | creditor | debtor | description | time       | token |
      | 10     | 1000    | 1234     | 2345   | test        | 2021-01-01 | abc   |
      | 30     | 970     | 1234     | 2345   | test2       | 2021-01-03 | abcd  |
      | 60     | 910     | 123      | 2345   | test3       | 2021-01-04 | abcde |
    When the merchant requests all their transactions
    And a list of the merchant transactions are returned
    Then the merchant transactions are displayed

  Scenario: Merchant requests transactions in a given time period
    Given a list of merchant transactions
      | amount | balance | creditor | debtor | description | time       | token |
      | 10     | 1000    | 1234     | 2345   | test        | 2021-01-01 | abc   |
      | 30     | 970     | 1234     | 2345   | test2       | 2021-01-03 | abcd  |
      | 60     | 910     | 123      | 2345   | test3       | 2021-01-04 | abcde |
    When the merchant requests their transactions between "3 Jan" and "4 Jan"
    And a list of the merchant transactions are returned
    Then the merchant transactions are displayed between "3 Jan" and "4 Jan"