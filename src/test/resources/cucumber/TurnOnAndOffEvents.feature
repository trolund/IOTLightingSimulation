Feature: Turn ON and OFF lamps

  Background: Lamp have been connected
    Given a list of test devices
      | id | name    | intensity | color         | isOn | group             |
      | 10 | Test_1  | 10        | (100,80,250)  | ON   | ["Stue", "Hus"]   |
      | 30 | Test_2  | 40        | (100,100,100) | OFF  | ["Stue", "Hus"]   |
      | 60 | Test_3  | 80        | (100,50,150)  | ON   | ["kokken", "Hus"] |
    Then I connect the all lamps

  Scenario: Turn ON lamp
    Given a list of lamps
      | id | name    |
      | 10 | Test_1  |
      | 30 | Test_2  |
      | 60 | Test_3  |
    Then i turn "ON" all lamps
    Then i lookup all lamps
    And then i check all lamps is know "ON"
    Then disconnect all devices

  Scenario: turn OFF lamp
    Given a list of lamps
      | id | name    |
      | 10 | Test_1  |
      | 30 | Test_2  |
      | 60 | Test_3  |
    Then i turn "OFF" all lamps
    Then i lookup all lamps
    And then i check all lamps is know "OFF"
    Then disconnect all devices
