Feature: Gestion des championnats

  Scenario: Créer un championnat
    When l'utilisateur crée les championnats avec les informations suivantes
      | nom               |
      | Ligue 1 Conforama |
    Then l'utilisateur retrouve les championnats suivants dans la liste des championnats
    """
    [
      {
        "nom": "Ligue 1 Conforama",
        "id": $LAST_CHAMPIONNAT_ID
      }
    ]
    """

  Scenario: Supprimer un championnat
    Given l'utilisateur crée les championnats avec les informations suivantes
      | nom              |
      | Ligue 2 Domino's |
    When l'utilisateur supprime le championnat avec l'id '$LAST_CHAMPIONNAT_ID'
    Then l'utilisateur ne retrouve aucun des championnats suivants dans la liste des championnats
      | nom              |
      | Ligue 2 Domino's |
