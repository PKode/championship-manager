Feature: Gestion des équipes

  Scenario: Créer une équipe
    Given l'utilisateur crée le championnat avec les informations suivantes
      | nom     |
      | Ligue 1 |
    When l'utilisateur crée les équipes avec les informations suivantes
      | nom | championnatNom | championnatId          |
      | PSG | Ligue 1        | ${LAST_CHAMPIONNAT_ID} |
    Then l'utilisateur affiche les équipes
    """
    [
      {
        "nom": "PSG",
        "id": ${LAST_EQUIPE_ID},
        "championnat": {
          "id": ${LAST_CHAMPIONNAT_ID},
          "nom": "Ligue 1"
        }
      }
    ]
    """

  Scenario: Modifier une équipe
    Given l'utilisateur crée le championnat avec les informations suivantes
      | nom     |
      | Ligue 1 |
    And l'utilisateur crée les équipes avec les informations suivantes
      | nom | championnatNom | championnatId          |
      | PSG | Ligue 1        | ${LAST_CHAMPIONNAT_ID} |
    When l'utilisateur modifie les équipes avec les informations suivantes
      | nom                 | championnatNom | championnatId          |
      | Paris Saint Germain | Ligue 1        | ${LAST_CHAMPIONNAT_ID} |
    Then l'utilisateur affiche les équipes
    """
    [
      {
        "nom": "Paris Saint Germain",
        "id": ${LAST_EQUIPE_ID},
        "championnat": {
          "id": ${LAST_CHAMPIONNAT_ID},
          "nom": "Ligue 1"
        }
      }
    ]
    """

  Scenario: Supprimer une équipe
    Given l'utilisateur crée le championnat avec les informations suivantes
      | nom     |
      | Ligue 1 |
    When l'utilisateur crée les équipes avec les informations suivantes
      | nom    | championnatNom | championnatId          |
      | Poissy | Ligue 1        | ${LAST_CHAMPIONNAT_ID} |
    When l'utilisateur supprime l'équipe avec l'id '${LAST_EQUIPE_ID}'
    Then l'utilisateur ne retrouve aucune des équipes suivantes dans la liste des équipes
      | nom    |
      | Poissy |
