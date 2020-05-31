export class Championnat {
  id?: number;
  nom: string;

  constructor(nom: string, id?: number) {
    this.id = id ? id : null;
    this.nom = nom;
  }
}
