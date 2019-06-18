import { Component, OnInit, ViewChild } from '@angular/core';
import { Nacionalnost } from '../../models/nacionalnost';
import { NacionalnostService } from '../../services/nacionalnost.service';
import { MatDialog, MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { NacionalnostDialogComponent } from '../dialogs/nacionalnost-dialog/nacionalnost-dialog.component';

@Component({
  selector: 'app-nacionalnost',
  templateUrl: './nacionalnost.component.html',
  styleUrls: ['./nacionalnost.component.css']
})
export class NacionalnostComponent implements OnInit {
  displayedColumns = ['naziv', 'skracenica', 'actions'];
  dataSource: MatTableDataSource<Nacionalnost>;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(public dialog: MatDialog, public nacionalnostService: NacionalnostService) { }

  public loadData() {
    this.nacionalnostService.getAllNacionalnost().subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.sortingDataAccessor = (data, property) => {
         return data[property].toLocaleLowerCase();
      };

      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    })
  }

  ngOnInit() {
    this.loadData();
  }

  public openDialog(flag: number, id: number, naziv: string, skracenica: string) {
    const dialogRef = this.dialog.open(NacionalnostDialogComponent,
      { data: { id: id, naziv: naziv, skracenica: skracenica } }
    );
    dialogRef.componentInstance.flag = flag;
    dialogRef.afterClosed().subscribe(result => {
      if (result == 1)
        this.loadData();
    });
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}