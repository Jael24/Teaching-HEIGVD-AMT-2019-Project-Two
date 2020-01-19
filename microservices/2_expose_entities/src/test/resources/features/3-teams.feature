Feature: Teams

  Scenario: can't get the teams list if unauthenticated
    When user get teams
    Then the request 'FAILS'

  Scenario: can get the teams list if authenticated
    Given user is authenticated with mail "filipe@mail.co" and id "2"
    When user get teams
    Then the request 'IS SUCCESSFUL'
    Then response is not empty

  Scenario: can't update a team if unauthenticated
    When user update team 1 with the following attributes
      | name   | country |
      | Parc des princes | Switzerland  |
    Then the request 'FAILS'

  Scenario: can't update the team if authenticated but not admin
    Given user is authenticated with mail "filipe@mail.co" and id "2"
    When user update team 1 with the following attributes
      | name   | country |
      | Parc des princes | Switzerland  |
    Then the request 'FAILS'

  Scenario: can update a team if authenticated and admin
    Given admin is authenticated with mail "admin@mail.co" and id "1"
    When user update team 1 with the following attributes
      | name   | country |
      | Parc des princes | Switzerland  |
    Then the request 'IS SUCCESSFUL'

  Scenario: can't add a team if unauthenticated
    When user add team with following attributes
      | name   | country |
      | Parc des princes | Switzerland  |
    Then the request 'FAILS'

  Scenario: can't add the team if authenticated but not admin
    Given user is authenticated with mail "filipe@mail.co" and id "2"
    When user add team with following attributes
      | name   | country |
      | Parc des princes | Switzerland  |
    Then the request 'FAILS'

  Scenario: can add a team if authenticated and admin
    Given admin is authenticated with mail "admin@mail.co" and id "1"
    When user add team with following attributes
      | name   | country |
      | Parc des princes | Switzerland  |
    Then the request 'IS SUCCESSFUL'
    Then we can get this team
    Then we can delete this team
