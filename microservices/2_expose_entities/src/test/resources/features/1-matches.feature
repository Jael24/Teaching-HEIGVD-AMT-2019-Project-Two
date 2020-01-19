Feature: Matches

  Scenario: can't get the matches list if unauthenticated
    When user get matches
    Then the request 'FAILS'

  Scenario: can get the matches list if authenticated
    Given user is authenticated with mail "filipe@mail.co" and id "2"
    When user get matches
    Then the request 'IS SUCCESSFUL'
    Then response is not empty

  Scenario: can't update a match if unauthenticated
    When user update match 5 with the following attributes
      | team1   | score1 | team2  | score2 | location |
      | /teams/2 | 2  | /teams/3  | 4      | /stadiums/5|
    Then the request 'FAILS'

  Scenario: can't update the match if authenticated but false attribute
    Given user is authenticated with mail "filipe@mail.co" and id "2"
    When user update match 5 with the following attributes
      | team1   | score1 | team2  | score2 | location |
      | /teams/2 | 2  | /teams/13  | 4      | /stadiums/5|
    Then the request 'FAILS'

  Scenario: can update a match if authenticated
    Given user is authenticated with mail "filipe@mail.co" and id "2"
    When user get matches
    And user update match 5 with the following attributes
      | team1   | score1 | team2  | score2 | location |
      | /teams/2 | 2  | /teams/3  | 4      | /stadiums/5|
    Then the request 'IS SUCCESSFUL'
      # this is to MANUALLY ensure that when we update a match, the cache gets properly evicted
    Then we can get matches

  Scenario: can't add a match if unauthenticated
    When user add match with following attributes
      | team1   | score1 | team2  | score2 | location |
      | /teams/2 | 2  | /teams/3  | 4      | /stadiums/5|
    Then the request 'FAILS'

  Scenario: can't add the match if authenticated but false attribute
    Given user is authenticated with mail "filipe@mail.co" and id "2"
    When user add match with following attributes
      | team1   | score1 | team2  | score2 | location |
      | /teams/2 | 2  | /teams/13  | 4      | /stadiums/5|
    Then the request 'FAILS'

  Scenario: can add a match if authenticated
    Given user is authenticated with mail "filipe@mail.co" and id "2"
    When user add match with following attributes
      | team1   | score1 | team2  | score2 | location |
      | /teams/2 | 2  | /teams/3  | 4      | /stadiums/5|
    Then the request 'IS SUCCESSFUL'
    Then we can get this match
    Then we can cancel this match
