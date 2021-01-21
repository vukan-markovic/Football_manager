import { Injectable } from '@angular/core';
import { Tim } from '../models/tim';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { TimDTO } from '../dto/timDTO';

@Injectable()
export class TimService {
  private readonly API_URL = 'http://localhost:8083/tim/';
  private timDTO: TimDTO;
  dataChange: BehaviorSubject<Tim[]> = new BehaviorSubject<Tim[]>([]);

  constructor(private httpClient: HttpClient) { }

  public getAllTim(): Observable<Tim[]> {
    this.httpClient.get<Tim[]>(this.API_URL).subscribe(data => {
      this.dataChange.next(data);
    },
      (error: HttpErrorResponse) => {
        console.log(error.name + ' ' + error.message);
      });

    return this.dataChange.asObservable();
  }

  public deleteTim(id: number): void {
    this.httpClient.delete(this.API_URL + id).subscribe();
  }

  public addTim(tim: Tim): void {
    this.httpClient.post(this.API_URL, this.timEntityToDTO(tim)).subscribe();
  }

  public updateTim(tim: Tim): void {
    this.httpClient.put(this.API_URL, this.timEntityToDTO(tim)).subscribe();
  }

  private timEntityToDTO(tim: Tim) {
    this.timDTO = new TimDTO();
    this.timDTO.id = tim.id;
    this.timDTO.ligaId = tim.liga.id;
    this.timDTO.naziv = tim.naziv;
    this.timDTO.osnovan = tim.osnovan;
    this.timDTO.sediste = tim.sediste;
    return this.timDTO;
  }
}
