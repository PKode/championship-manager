Feature: Gestion des championnats

  Scenario: Créer un championnat
    When l'utilisateur crée les championnats avec les informations suivantes
      | nom               | id |
      | Ligue 1 Conforama | 1  |
    Then l'utilisateur récupère le championnat 'Ligue 1 Conforama'
