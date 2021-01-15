Feature: Customer reporting
  Scenario: Customer requests a list of transactions
    Given a list of customer transactions
      | amount | balance | creditor | debtor | description | time       | token |
      | 10     | 1000    | 1234     | 2345   | test        | 2021-01-01 | abc   |
      | 30     | 970     | 1234     | 2345   | test2       | 2021-01-03 | abcd  |
      | 60     | 910     | 12345    | 2345   | test3       | 2021-01-04 | abcde |
    When the customer "1234" requests all their transactions
    Then a customer event "requestAllTransactionsFromPayment" has been sent
    When a customer event "requestAllTransactionsReply" has been sent back
    Then the customers transactions are displayed
      | amount | balance | creditor | debtor | description | time       | token |
      | 10     | 1000    | 1234     | 2345   | test        | 2021-01-01 | abc   |
      | 30     | 970     | 1234     | 2345   | test2       | 2021-01-03 | abcd  |

  Scenario: Customer requests transactions in a given time period
    Given a list of customer transactions
      | amount | balance | creditor | debtor | description | time       | token |
      | 10     | 1000    | 1234     | 2345   | test        | 2021-01-01 | abc   |
      | 30     | 970     | 1234     | 2345   | test2       | 2021-01-03 | abcd  |
      | 60     | 910     | 1234     | 2345   | test3       | 2021-01-04 | abcde |
      | 40     | 870     | 1234     | 2345   | test4       | 2021-01-05 | abcde |
      | 40     | 870     | 1234     | 2345   | test4       | 2021-01-06 | abcde |
    When the customer "1234" requests their transactions between "2021-01-03" and "2021-01-05"
    Then a customer event "requestAllTransactionsFromPayment" has been sent
    When a customer event "requestAllTransactionsReply" has been sent back
    Then the customers transactions are displayed between two days
      | amount | balance | creditor | debtor | description | time       | token |
      | 30     | 970     | 1234     | 2345   | test2       | 2021-01-03 | abcd  |
      | 60     | 910     | 1234     | 2345   | test3       | 2021-01-04 | abcde |
      | 40     | 870     | 1234     | 2345   | test4       | 2021-01-05 | abcde |