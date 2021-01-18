#Feature: RabbitMQ messages
#    Scenario: Receive request to create an account
#        Given a message containing an account creation request is waiting
#        When the request to create is handled
#        Then the account service creates the account
#
#    Scenario: Receive request to disable an account
#        Given that a user with CPR "555555-5555" has been created
#        And the account is active
#        When the request to disable the account is handled
#        Then the account is disabled
#
#    Scenario: Receive request for details of an existing user
#        Given that a user with CPR "555555-5555" has been created
#        When an external service requests the details of the user with CPR "555555-5555" 
#        Then the details of the user is sent in a response
#        And the response status code is 200 OK
#
#    Scenario: Receive request for details of a non-existent user
#        Given: that a user with CPR "444444-4444" does not exist
#        When: an external service requests the details of the user with CPR "444444-4444" 
#        Then: a response containing an error message is sent
#        And: the response status code is 404 not found
