import {Championnat} from "../championnat/championnat";

export class Equipe {
  id?: number;
  nom: string;
  championnat: Championnat;

  constructor(nom: string, championnat: Championnat, id?: number) {
    this.id = id ? id : null;
    this.nom = nom;
    this.championnat = championnat
  }
}
