import { Component, OnInit, ViewChild } from '@angular/core';
import { Tim } from 'src/app/models/tim';
import { TimService } from 'src/app/services/tim.service';
import { MatDialog, MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { TimDialogComponent } from '../dialogs/tim-dialog/tim-dialog.component';
import { Liga } from 'src/app/models/liga';

@Component({
  selector: 'app-tim',
  templateUrl: './tim.component.html',
  styleUrls: ['./tim.component.css']
})
export class TimComponent implements OnInit {
  displayedColumns = ['naziv', 'osnovan', 'sediste', 'liga', 'actions'];
  dataSource: MatTableDataSource<Tim>;
  selektovanTim: Tim;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(public timService: TimService, public dialog: MatDialog) { }

  ngOnInit() {
    this.loadData();
  }

  public loadData() {
    this.timService.getAllTim().subscribe(data => {
      this.dataSource = new MatTableDataSource(data);

      this.dataSource.filterPredicate = (data, filter: string) => {
        const accumulator = (currentTerm, key: string) => {
          return key === 'liga' ? currentTerm + data.liga.naziv : currentTerm + data[key];
        };
        const dataStr = Object.keys(data).reduce(accumulator, '').toLowerCase();
        const transformedFilter = filter.trim().toLowerCase();
        return dataStr.indexOf(transformedFilter) !== -1;
      };

      this.dataSource.sortingDataAccessor = (data, property) => {
        if (property == 'liga') return data.liga.naziv.toLocaleLowerCase();
        else return data[property];
      };

      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  public openDialog(flag: number, id: number, naziv: string, osnovan: Date, sediste: string, liga: Liga) {
    const dialogRef = this.dialog.open(TimDialogComponent,
      { data: { id: id, naziv: naziv, osnovan: osnovan, sediste: sediste, liga: liga } }
    );
    dialogRef.componentInstance.flag = flag;
    dialogRef.afterClosed().subscribe((result: number) => {
      if (result == 1) this.loadData();
    });
  }

  selectRow(row: Tim) {
    this.selektovanTim = row;
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}
