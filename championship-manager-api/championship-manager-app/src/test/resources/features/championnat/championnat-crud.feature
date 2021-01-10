Feature: Gestion des championnats

  Scenario: Créer un championnat
    When l'utilisateur crée le championnat avec les informations suivantes
      | nom               |
      | Ligue 1 Conforama |
    Then l'utilisateur retrouve les championnats suivants dans la liste des championnats
    """
    [
      {
        "nom": "Ligue 1 Conforama",
        "id": ${LAST_CHAMPIONNAT_ID},
        "saisons": []
      }
    ]
    """

  Scenario: Modifier un championnat
    Given l'utilisateur crée le championnat avec les informations suivantes
      | nom               |
      | Ligue 1 Conforama |
    When l'utilisateur modifie le championnat avec les informations suivantes
      | nom     | id                     |
      | Ligue 1 | ${LAST_CHAMPIONNAT_ID} |
    Then l'utilisateur retrouve les championnats suivants dans la liste des championnats
    """
    [
      {
        "nom": "Ligue 1",
        "id": ${LAST_CHAMPIONNAT_ID},
        "saisons": []
      }
    ]
    """

  Scenario: Supprimer un championnat
    Given l'utilisateur crée le championnat avec les informations suivantes
      | nom              |
      | Ligue 2 Domino's |
    When l'utilisateur supprime le championnat avec l'id '${LAST_CHAMPIONNAT_ID}'
    Then l'utilisateur ne retrouve aucun des championnats suivants dans la liste des championnats
      | nom              |
      | Ligue 2 Domino's |
