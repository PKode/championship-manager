Feature: Gestion des championnats
  Scenario: Créer un championnat
    When l'utilisateur crée le championnat 'Ligue 1 Conforama'
    Then l'utilisateur récupère le championnat 'Ligue 1 Conforama'
