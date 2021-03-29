Feature: Turn ON and OFF lamps

  Background: Lamp have been connected
    Given a list of test devices
      | id | name    | intensity | color         | isOn | group             |
      | 10 | Test_1  | 10        | (100,80,250)  | ON   | ["Stue", "Hus"]   |
      | 30 | Test_2  | 40        | (100,100,100) | OFF  | ["Stue", "Hus"]   |
      | 60 | Test_3  | 80        | (100,50,150)  | ON   | ["kokken", "Hus"] |
      | 70 | Test_4  | 80        | (100,50,150)  | ON   | ["Hus"]           |
      | 80 | Test_5  | 80        | (100,50,150)  | ON   | []                |
      | 90 | Test_6  | 80        | (100,50,150)  | ON   | ["Hus"]           |
    Then I connect the all lamps

  Scenario: Turn ON lamp
    Given a list of lamps
      | id | name    |
      | 10 | Test_1  |
      | 30 | Test_2  |
      | 60 | Test_3  |
      | 70 | Test_4  |
      | 80 | Test_5  |
      | 90 | Test_6  |
    Then i turn "ON" all lamps
    Then i lookup all lamps
    And then i check all lamps is know "ON"
    Then disconnect all devices

  Scenario: Turn OFF lamp
    Given a list of lamps
      | id | name    |
      | 10 | Test_1  |
      | 30 | Test_2  |
      | 60 | Test_3  |
      | 70 | Test_4  |
      | 80 | Test_5  |
      | 90 | Test_6  |
    Then i turn "OFF" all lamps
    Then i lookup all lamps
    And then i check all lamps is know "OFF"
    Then disconnect all devices

  Scenario: Change the intensities
    Given a list of lamps
      | id | name    | intensity |
      | 10 | Test_1  | 25        |
      | 30 | Test_2  | 50        |
      | 60 | Test_3  | 75        |
      | 70 | Test_4  | 0         |
      | 80 | Test_5  | 100       |
      | 90 | Test_6  | 80        |
    Then i set the intensity of all lamps
    Then i lookup all lamps
    And then i check all lamps is know have the new intensity
    Then disconnect all devices