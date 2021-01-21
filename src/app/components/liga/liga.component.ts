import { Component, OnInit, ViewChild } from '@angular/core';
import { Liga } from 'src/app/models/liga';
import { LigaService } from 'src/app/services/liga.service';
import { MatDialog, MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import { LigaDialogComponent } from '../dialogs/liga-dialog/liga-dialog.component';

@Component({
  selector: 'app-liga',
  templateUrl: './liga.component.html',
  styleUrls: ['./liga.component.css']
})
export class LigaComponent implements OnInit {
  displayedColumns = ['naziv', 'oznaka', 'actions'];
  dataSource: MatTableDataSource<Liga>;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(public ligaService: LigaService, public dialog: MatDialog) { }

  ngOnInit() {
    this.loadData();
  }

  public loadData() {
    this.ligaService.getAllLiga().subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.sortingDataAccessor = (data, property) => {
        return data[property].toLocaleLowerCase();
      };

      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  public openDialog(flag: number, id: number, naziv: string, oznaka: string) {
    const dialogRef = this.dialog.open(LigaDialogComponent,
      { data: { id: id, naziv: naziv, oznaka: oznaka } }
    );
    dialogRef.componentInstance.flag = flag;
    dialogRef.afterClosed().subscribe(result => {
      if (result == 1) this.loadData();
    });
  }
}