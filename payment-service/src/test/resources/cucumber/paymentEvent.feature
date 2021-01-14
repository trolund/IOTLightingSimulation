Feature: Service 2 feature

  Scenario: demo
  	Given a token "42"
  	Then I have sent event "validateToken"
  	Then I receive event "TokenValidationSuccessful"
