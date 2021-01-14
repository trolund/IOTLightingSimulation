Feature: TokenEventService

  Scenario: Validate token success
    When I validate a token "42"
    Then I have sent event "validateToken"
    When I receive event "TokenValidationSuccessful" with token "42"
    Then token validation is successful

  Scenario: Validate token failure
    When I validate a token "42"
    Then I have sent event "validateToken"
    When I receive event "TokenValidationFailed"
    Then token validation has failed

