import { Nacionalnost } from './nacionalnost';
import { Tim } from './tim';

export class Igrac {
    id: number;
    ime: string;
    prezime: string;
    brojReg: string;
    datumRodjenja: Date;
    nacionalnost: Nacionalnost;
    tim: Tim;
}