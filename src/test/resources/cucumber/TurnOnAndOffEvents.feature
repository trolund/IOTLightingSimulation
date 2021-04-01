Feature: Basic lamp functionality

  Background: Lamp have been connected
    Given a list of test devices
      | id | name    | intensity | color         | isOn | group             |
      | 10 | Test_1  | 10        | (100,80,250)  | ON   | ["Stue", "Hus"]   |
      | 30 | Test_2  | 40        | (100,100,100) | OFF  | ["Stue", "Hus"]   |
      | 60 | Test_3  | 80        | (100,50,150)  | ON   | ["Kokken", "Hus"] |
      | 70 | Test_4  | 80        | (100,50,150)  | ON   | ["Hus"]           |
      | 80 | Test_5  | 80        | (100,50,150)  | ON   | []                |
      | 90 | Test_6  | 80        | (100,50,150)  | ON   | ["Hus", "Bad"]           |
    Then I connect the all lamps

  Scenario: Get device information
    Given a list of lamps
      | id | name    |
      | 10 | Test_1  |
      | 30 | Test_2  |
      | 60 | Test_3  |
      | 70 | Test_4  |
      | 80 | Test_5  |
      | 90 | Test_6  |
    Then i lookup each lamp
    And then i check all lamps is there
    Then disconnect all devices

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

  Scenario: Change the color
    Given a list of lamps
      | id | name    | color        |
      | 10 | Test_1  | (80,250,200) |
      | 30 | Test_2  | (90,140,140) |
      | 60 | Test_3  | (160,100,150)|
      | 70 | Test_4  | (10,180,230) |
      | 80 | Test_5  | (20,80,220)  |
      | 90 | Test_6  | (40,60,210)  |
    Then i set the color of all lamps
    Then i lookup all lamps
    And then i check all lamps is know have the new color
    Then disconnect all devices

  Scenario: Change the name
    Given a list of lamps
      | id | name        |
      | 10 | Test_1_test |
      | 30 | Test_2_test |
      | 60 | Test_3_test |
      | 70 | Test_4_test |
      | 80 | Test_5_test |
      | 90 | Test_6_test |
    Then i set the name of all lamps
    Then i lookup all lamps
    And then i check all lamps is know have the new name
    Then disconnect all devices

  Scenario: Add group to lamp
    Given a list of lamps
      | id | name     |
      | 10 | [Group2, Group4, Group1] |
      | 30 | [Group1, Group1] |
      | 60 | [Group5] |
      | 70 | [Group4, Group1] |
      | 80 | [Group9, Group5] |
      | 90 | [Group1] |
    Then i add a group to all the lamps
    Then i lookup all lamps
    And then i check all groups are there
    Then disconnect all devices

  Scenario: Remove group to lamp
    Given a list of lamps
      | id | group                  |
      | 10 | ["Stue", "Hus"]        |
      | 30 | ["kokken"]             |
      | 60 | []                     |
      | 70 | ["Hus"]                |
      | 80 | ["Gruppe findes ikke"] |
      | 90 | []                     |
    Then i remove all groups to all the lamps
    Then i lookup all lamps
    And then i check the removed groups are deleted
    Then disconnect all devices

  Scenario: Adjust group intensity
    Given a list of groups
      | groupName | intensity |
      | Stue      | 77        |
      | Kokken    | 88        |
      | Bad       | 99        |
    Then i set the intensity of all lamps in the group
    Then i lookup all lamps
    And then i check that all lamps in the group know have the new intensity
    Then disconnect all devices

  Scenario: Adjust group color
    Given a list of groups
      | groupName | color        |
      | Stue      | (80,250,200) |
      | Kokken    | (80,250,200) |
      | Bad       | (80,250,200) |
    Then i set the color of all lamps in the group
    Then i lookup all lamps
    And then i check that all lamps in the group know have the new color
    Then disconnect all devices

