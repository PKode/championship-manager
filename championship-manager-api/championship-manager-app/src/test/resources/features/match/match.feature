Feature: Matchs

  Scenario: Afficher les matchs d'une équipe pour la saison en cours
    Given l'utilisateur crée le championnat avec les informations suivantes
      | nom     |
      | Ligue 1 |
    And l'utilisateur crée les équipes avec les informations suivantes
      | nom  | championnatNom | championnatId        |
      | PSG  | Ligue 1        | ${LAST_CHAMPIONNAT_ID} |
      | OM   | Ligue 1        | ${LAST_CHAMPIONNAT_ID} |
      | OL   | Ligue 1        | ${LAST_CHAMPIONNAT_ID} |
      | ASSE | Ligue 1        | ${LAST_CHAMPIONNAT_ID} |
    When l'utilisateur génère le calendrier du championnat '${LAST_CHAMPIONNAT_ID}' commençant le '01/09/2020'
    And l'utilisateur modifie les matchs suivants
      | domicile | butDomicile | butExterieur | exterieur |
      | PSG      | 3           | 0            | OM        |
      | PSG      | 2           | 1            | OL        |
      | PSG      | 4           | 2            | ASSE      |
      | OM       | 1           | 3            | PSG       |
      | OM       | 2           | 2            | OL        |
      | OM       | 0           | 1            | ASSE      |
      | OL       | 1           | 4            | PSG       |
      | OL       | 1           | 1            | OM        |
      | OL       | 0           | 1            | ASSE      |
      | ASSE     | 0           | 3            | PSG       |
      | ASSE     | 0           | 0            | OM        |
      | ASSE     | 3           | 3            | OL        |
    And l'utilisateur génère le calendrier du championnat '${LAST_CHAMPIONNAT_ID}' commençant le '01/09/2021'
    And l'utilisateur affiche le calendrier du championnat '${LAST_CHAMPIONNAT_ID}' pour la saison 2020
    And l'utilisateur modifie les matchs suivants
      | domicile | butDomicile | butExterieur | exterieur |
      | PSG      | 2           | 0            | OM        |
    And l'utilisateur affiche le calendrier du championnat '${LAST_CHAMPIONNAT_ID}' pour la saison 2020
    Then l'utilisateur affiche les matchs de l'équipe 'PSG' pour la saison en cours
      | domicile | butDomicile | butExterieur | exterieur |
      | PSG      | 2           | 0            | OM        |
      | PSG      |             |              | OL        |
      | PSG      |             |              | ASSE      |
      | OM       |             |              | PSG       |
      | OL       |             |              | PSG       |
      | ASSE     |             |              | PSG       |

