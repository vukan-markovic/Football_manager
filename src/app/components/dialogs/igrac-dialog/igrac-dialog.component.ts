import { Component, OnInit, Inject } from '@angular/core';
import { MatSnackBar, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { IgracService } from 'src/app/services/igrac.service';
import { Igrac } from 'src/app/models/igrac';
import { NacionalnostService } from 'src/app/services/nacionalnost.service';
import { Nacionalnost } from 'src/app/models/nacionalnost';

@Component({
  selector: 'app-igrac-dialog',
  templateUrl: './igrac-dialog.component.html',
  styleUrls: ['./igrac-dialog.component.css']
})
export class IgracDialogComponent implements OnInit {
  nacionalnosti: Nacionalnost[];
  public flag: number;

  constructor(public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<IgracDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Igrac,
    public igracService: IgracService,
    public nacionalnostService: NacionalnostService) { }

  ngOnInit() {
    this.nacionalnostService.getAllNacionalnost().subscribe(nacionalnosti =>
      this.nacionalnosti = nacionalnosti
    );
  }

  public compareTo(a: { id: any; }, b: { id: any; }) {
    if (a != undefined && b != undefined && a != null && b != null) return a.id == b.id;
  }

  public add(): void {
    this.data.id = -1;
    this.igracService.addIgrac(this.data);
    this.snackBar.open("Uspešno dodat igrač", "U redu", { duration: 2500 });
  }

  public update(): void {
    this.igracService.updateIgrac(this.data);
    this.snackBar.open("Uspešno modifikovan igrač", "U redu", { duration: 2500 });
  }

  public delete(): void {
    this.igracService.deleteIgrac(this.data.id);
    this.snackBar.open("Uspešno obrisan igrač", "U redu", { duration: 2500 });
  }

  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open("Odustali ste", "U redu", { duration: 2500 });
  }
}
