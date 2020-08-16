Feature: Génération d'un calendrier

  Scenario: Générer le calendrier d'un championnat
    Given l'utilisateur crée le championnat avec les informations suivantes
      | nom     |
      | Ligue 1 |
    And l'utilisateur crée les équipes avec les informations suivantes
      | nom  | championnatNom | championnatId        |
      | PSG  | Ligue 1        | $LAST_CHAMPIONNAT_ID |
      | OM   | Ligue 1        | $LAST_CHAMPIONNAT_ID |
      | OL   | Ligue 1        | $LAST_CHAMPIONNAT_ID |
      | ASSE | Ligue 1        | $LAST_CHAMPIONNAT_ID |
    When l'utilisateur génère le calendrier du championnat '$LAST_CHAMPIONNAT_ID' commençant le '01/09/2020'
    Then le calendrier de la saison 2020 du championnat '$LAST_CHAMPIONNAT_ID' comporte 6 journées et 12 matchs
