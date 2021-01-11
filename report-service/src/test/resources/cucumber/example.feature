Feature: Example

  Scenario: Check health
    When I check my health
    Then The result should be "I am healthy and ready to work!"

  Scenario: Read example
    When I read my example
    Then The result should be "Example obj"