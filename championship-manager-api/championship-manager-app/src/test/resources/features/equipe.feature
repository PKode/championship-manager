Feature: Gestion des équipes

  Scenario: Créer une équipe
    Given l'utilisateur crée les championnats avec les informations suivantes
      | nom     |
      | Ligue 1 |
    When l'utilisateur crée les équipes avec les informations suivantes
      | nom | championnatNom | championnatId        |
      | PSG | Ligue 1        | $LAST_CHAMPIONNAT_ID |
    Then l'utilisateur affiche les équipes
      | PSG |

  Scenario: Supprimer une équipe
    Given l'utilisateur crée les championnats avec les informations suivantes
      | nom     |
      | Ligue 1 |
    When l'utilisateur crée les équipes avec les informations suivantes
      | nom    | championnatNom | championnatId        |
      | Poissy | Ligue 1        | $LAST_CHAMPIONNAT_ID |
    When l'utilisateur supprime l'équipe avec l'id '$LAST_EQUIPE_ID'
    Then l'utilisateur ne retrouve aucune des équipes suivantes dans la liste des équipes
      | nom    |
      | Poissy |
