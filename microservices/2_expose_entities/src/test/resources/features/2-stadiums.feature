Feature: Stadiums

  Scenario: can't get the stadiums list if unauthenticated
    When user get stadiums
    Then the request 'FAILS'

  Scenario: can get the stadiums list if authenticated
    Given user is authenticated with mail "filipe@mail.co" and id "2"
    When user get stadiums
    Then the request 'IS SUCCESSFUL'
    Then response is not empty

  Scenario: can't update a stadium if unauthenticated
    When user update stadium 1 with the following attributes
      | name   | location | numberOfPlaces  |
      | Parc des princes | Switzerland  | 25000 |
    Then the request 'FAILS'

  Scenario: can't update the stadium if authenticated but not admin
    Given user is authenticated with mail "filipe@mail.co" and id "2"
    When user update stadium 1 with the following attributes
      | name   | location | numberOfPlaces  |
      | Parc des princes | Switzerland  | 25000 |
    Then the request 'FAILS'

  Scenario: can update a stadium if authenticated and admin
    Given admin is authenticated with mail "admin@mail.co" and id "1"
    When user update stadium 1 with the following attributes
      | name   | location | numberOfPlaces  |
      | Parc des princes | Switzerland  | 25000 |
    Then the request 'IS SUCCESSFUL'

  Scenario: can't add a stadium if unauthenticated
    When user add stadium with following attributes
      | name   | location | numberOfPlaces  |
      | Parc des princes | Switzerland  | 25000 |
    Then the request 'FAILS'

  Scenario: can't add the stadium if authenticated but not admin
    Given user is authenticated with mail "filipe@mail.co" and id "2"
    When user add stadium with following attributes
      | name   | location | numberOfPlaces  |
      | Parc des princes | Switzerland  | 25000 |
    Then the request 'FAILS'

  Scenario: can add a stadium if authenticated and admin
    Given admin is authenticated with mail "admin@mail.co" and id "1"
    When user add stadium with following attributes
      | name   | location | numberOfPlaces  |
      | Parc des princes | Switzerland  | 25000 |
    Then the request 'IS SUCCESSFUL'
    Then we can get this stadium
    Then we can delete this stadium
