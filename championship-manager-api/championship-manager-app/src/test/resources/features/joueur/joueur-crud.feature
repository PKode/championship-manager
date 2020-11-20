Feature: Gestion des joueurs

  Scenario: Création d'un joueur
    Given l'utilisateur crée le championnat avec les informations suivantes
      | nom     |
      | Serie A |
    And l'utilisateur crée les équipes avec les informations suivantes
      | nom      | championnatNom | championnatId        |
      | Juventus | Serie A        | $LAST_CHAMPIONNAT_ID |
    When l'utilisateur crée le joueur avec les informations suivantes
      | nom     | prenom    | poste | nationalite | dateNaissance | taille | poids | equipe                                   |
      | Ronaldo | Cristiano | ATT   | Portugais   | 05/02/1985    | 187    | 84    | {id:$LAST_EQUIPE_ID, nom:"Juventus"}     |
    Then l'utilisateur affiche les joueurs
    """
    [
      {
        "id": "$LAST_JOUEUR_ID",
        "nom": "Ronaldo",
        "prenom": "Cristiano",
        "poste": "ATT",
        "nationalite": "Portugais",
        "dateNaissance": "05/02/1985",
        "taille": 187,
        "poids": 84,
        "equipe": {
          "id": $LAST_EQUIPE_ID,
          "nom": "Juventus"
        }
      }
    ]
    """
    Then l'utilisateur affiche les joueurs de l'équipe '$LAST_EQUIPE_ID'
    """
    [
      {
        "id": "$LAST_JOUEUR_ID",
        "nom": "Ronaldo",
        "prenom": "Cristiano",
        "poste": "ATT",
        "nationalite": "Portugais",
        "dateNaissance": "05/02/1985",
        "taille": 187,
        "poids": 84,
        "equipe": {
          "id": $LAST_EQUIPE_ID,
          "nom": "Juventus"
        }
      }
    ]
    """

  Scenario: Modifier un joueur
    Given l'utilisateur crée le joueur avec les informations suivantes
      | nom    | prenom | poste | nationalite | dateNaissance | taille | poids |
      | MBappe | Kylian | ATT   | Français    | 20/12/1998    | 178    | 73    |
    When l'utilisateur modifie le joueur avec les informations suivantes
      | id              | nom     | prenom | poste | nationalite | dateNaissance | taille | poids |
      | $LAST_JOUEUR_ID | M'Bappe | Kylian | ATT   | Français    | 20/12/1998    | 178    | 73    |
    Then l'utilisateur affiche les joueurs
    """
    [
      {
        "id": "$LAST_JOUEUR_ID",
        "nom": "M'Bappe",
        "prenom": "Kylian",
        "poste": "ATT",
        "nationalite": "Français",
        "dateNaissance": "20/12/1998",
        "taille": 178,
        "poids": 73
      }
    ]
    """

  Scenario: Supprimer un joueur
    Given l'utilisateur crée le joueur avec les informations suivantes
      | nom      | prenom   | poste | nationalite | dateNaissance | taille | poids |
      | Kerirzin | Pierrick | MO    | Français    | 30/04/1993    | 180    | 73    |
    When l'utilisateur supprime le joueur avec l'id '$LAST_JOUEUR_ID'
    Then l'utilisateur ne retrouve aucun des joueurs suivants dans la liste des joueurs
      | nom      |
      | Kerirzin |
