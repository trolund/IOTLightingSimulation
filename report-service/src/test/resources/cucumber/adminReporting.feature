Feature: Manager of DTU Pay
  Scenario: Report of transactions
    Given a list of transactions
      | amount | balance | creditor | debtor | description | time       | token |
      | 10     | 1000    | 1234     | 2345   | test        | 2021-01-01 | abc   |
      | 30     | 970     | 1234     | 2345   | test2       | 2021-01-03 | abcd  |
      | 60     | 100     | 12345    | 23456  | test3       | 2021-01-04 | abcde |
    When a request to see all transactions is made
    Then the list of transactions is shown
      | amount | balance | creditor | debtor | description | time       | token |
      | 10     | 1000    | 1234     | 2345   | test        | 2021-01-01 | abc   |
      | 30     | 970     | 1234     | 2345   | test2       | 2021-01-03 | abcd  |
      | 60     | 100     | 12345    | 23456  | test3       | 2021-01-04 | abcde |

  Scenario: Summary of transactions
    Given a list of transactions
      | amount | balance | creditor | debtor | description | time       | token |
      | 10     | 1000    | 1234     | 2345   | test        | 2021-01-01 | abc   |
      | 30     | 970     | 1234     | 2345   | test2       | 2021-01-03 | abcd  |
      | 60     | 100     | 12345    | 23456  | test3       | 2021-01-04 | abcde |
    When a request for summary is made
    Then a summary is made based on the transactions
      | amount | balance | creditor | debtor | description | time       | token |
      | 10     | 1000    | 1234     | 2345   | test        | 2021-01-01 | abc   |
      | 30     | 970     | 1234     | 2345   | test2       | 2021-01-03 | abcd  |
      | 60     | 100     | 12345    | 23456  | test3       | 2021-01-04 | abcde |
