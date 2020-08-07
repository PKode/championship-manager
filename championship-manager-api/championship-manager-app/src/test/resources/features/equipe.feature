Feature: Gestion des équipes

  Scenario: Créer une équipe
    Given l'utilisateur crée les championnats avec les informations suivantes
      | nom     | id |
      | Ligue 1 | 1  |
    #TODO: Get id of created championnat and put it in context ?
    When l'utilisateur crée les équipes avec les informations suivantes
      | nom | championnatNom | championnatId        |
      | PSG | Ligue 1        | $LAST_CHAMPIONNAT_ID |
    Then l'utilisateur affiche les équipes
      | PSG |
