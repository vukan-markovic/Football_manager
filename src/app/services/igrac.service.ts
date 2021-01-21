import { Injectable } from '@angular/core';
import { Igrac } from '../models/igrac';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { IgracDTO } from '../dto/igracDTO';

@Injectable()
export class IgracService {
  private readonly API_URL = 'http://localhost:8083/igrac/';
  private readonly API_URL_BYID = 'http://localhost:8083/igraciZaTimId/';
  private igracDTO: IgracDTO;
  dataChange: BehaviorSubject<Igrac[]> = new BehaviorSubject<Igrac[]>([]);

  constructor(private httpClient: HttpClient) { }

  public getIgraceZaTim(idTima): Observable<Igrac[]> {
    this.httpClient.get<Igrac[]>(this.API_URL_BYID + idTima).subscribe(data => {
      this.dataChange.next(data);
    },
      (error: HttpErrorResponse) => {
        console.log(error.name + ' ' + error.message);
      });

    return this.dataChange.asObservable();
  }

  public deleteIgrac(id: number): void {
    this.httpClient.delete(this.API_URL + id).subscribe();
  }

  public addIgrac(igrac: Igrac): void {
    this.httpClient.post(this.API_URL, this.igracEntityToDTO(igrac)).subscribe();
  }

  public updateIgrac(igrac: Igrac): void {
    this.httpClient.put(this.API_URL, this.igracEntityToDTO(igrac)).subscribe();
  }

  private igracEntityToDTO(igrac: Igrac) {
    this.igracDTO = new IgracDTO();
    this.igracDTO.brojReg = igrac.brojReg;
    this.igracDTO.datumRodjenja = igrac.datumRodjenja;
    this.igracDTO.id = igrac.id;
    this.igracDTO.ime = igrac.ime;
    this.igracDTO.nacionalnostId = igrac.nacionalnost.id;
    this.igracDTO.prezime = igrac.prezime;
    this.igracDTO.timId = igrac.tim.id;
    return this.igracDTO;
  }
}
