import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MatSnackBar, MAT_DIALOG_DATA } from '@angular/material';
import { Nacionalnost } from 'src/app/models/nacionalnost';
import { NacionalnostService } from 'src/app/services/nacionalnost.service';

@Component({
  selector: 'app-nacionalnost-dialog',
  templateUrl: './nacionalnost-dialog.component.html',
  styleUrls: ['./nacionalnost-dialog.component.css']
})
export class NacionalnostDialogComponent implements OnInit {
  public flag: number;

  constructor(public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<NacionalnostDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Nacionalnost,
    public nacionalnostService: NacionalnostService) { }

  ngOnInit() { }

  public add(): void {
    this.data.id = -1;
    this.nacionalnostService.addNacionalnost(this.data);
    this.snackBar.open("Uspešno dodata nacionalnost: " + this.data.naziv, "U redu", {
      duration: 2500,
    });
  }

  public update(): void {
    this.nacionalnostService.updateNacionalnost(this.data);
    this.snackBar.open("Uspešno modifikovana nacionalnost: " + this.data.id, "U redu", {
      duration: 2500,
    });
  }

  public delete(): void {
    this.nacionalnostService.deleteNacionalnost(this.data.id);
    this.snackBar.open("Uspešno obrisana nacionalnost: " + this.data.id, "U redu", {
      duration: 2500,
    });
  }

  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open("Odustali ste", "U redu", {
      duration: 1000,
    });
  }
}