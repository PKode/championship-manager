Feature: Gestion des championnats

  Scenario: Créer un championnat
    When l'utilisateur crée les championnats avec les informations suivantes
      | nom               |
      | Ligue 1 Conforama |
    Then l'utilisateur affiche les championnats
      | Ligue 1 Conforama |
