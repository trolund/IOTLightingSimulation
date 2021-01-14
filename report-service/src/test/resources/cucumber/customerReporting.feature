Feature: Customer reporting
  Scenario: Customer requests a list of transactions
    Given a list of customer transactions
      | amount | balance | creditor | debtor | description | time       | token |
      | 10     | 1000    | 1234     | 2345   | test        | 2021-01-01 | abc   |
      | 30     | 970     | 1234     | 2345   | test2       | 2021-01-03 | abcd  |
      | 60     | 910     | 1234     | 23456  | test3       | 2021-01-04 | abcde |
    When the customer requests all their transactions
    And a list of the customer transactions are returned
    Then the customers transactions are displayed

  Scenario: Customer requests transactions in a given time period
    Given a list of customer transactions
      | amount | balance | creditor | debtor | description | time       | token |
      | 10     | 1000    | 1234     | 2345   | test        | 2021-01-01 | abc   |
      | 30     | 970     | 1234     | 2345   | test2       | 2021-01-03 | abcd  |
      | 60     | 910     | 1234     | 23456  | test3       | 2021-01-04 | abcde |
    When the customer requests their transactions between "3 Jan" and "4 Jan"
    And a list of the customer transactions are returned
    Then the customers transactions are displayed between "3 Jan" and "4 Jan"