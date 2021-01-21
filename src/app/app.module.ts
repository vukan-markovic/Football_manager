import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import {
  MatButtonModule, MatIconModule, MatListModule, MatSidenavModule, MatGridListModule,
  MatExpansionModule, MatTableModule, MatToolbarModule, MatOptionModule, MatSelectModule,
  MatSnackBarModule, MatDialogModule, MatInputModule, MatNativeDateModule,
  MatCheckboxModule, MatDatepickerModule, MatPaginatorModule, MatSortModule, MatTooltipModule,
  MatStepperModule
} from '@angular/material';
import { IgracComponent } from './components/igrac/igrac.component';
import { AboutComponent } from './components/core/about/about.component';
import { TimComponent } from './components/tim/tim.component';
import { NacionalnostComponent } from './components/nacionalnost/nacionalnost.component';
import { LigaComponent } from './components/liga/liga.component';
import { HomeComponent } from './components/core/home/home.component';
import { AuthorComponent } from './components/core/author/author.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { LigaService } from './services/liga.service';
import { IgracService } from './services/igrac.service';
import { NacionalnostService } from './services/nacionalnost.service';
import { TimService } from './services/tim.service';
import { TimDialogComponent } from './components/dialogs/tim-dialog/tim-dialog.component';
import { NacionalnostDialogComponent } from './components/dialogs/nacionalnost-dialog/nacionalnost-dialog.component';
import { LigaDialogComponent } from './components/dialogs/liga-dialog/liga-dialog.component';
import { IgracDialogComponent } from './components/dialogs/igrac-dialog/igrac-dialog.component';
import { DialogButtonsComponent } from './components/dialogs/dialog-buttons/dialog-buttons.component';

const Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'igrac', component: IgracComponent, data: { title: 'Igrac' } },
  { path: 'nacionalnost', component: NacionalnostComponent, data: { title: 'Nacionalnost' } },
  { path: 'tim', component: TimComponent, data: { title: 'Tim' } },
  { path: 'liga', component: LigaComponent, data: { title: 'Liga' } },
  { path: 'home', component: HomeComponent, data: { title: 'Home' } },
  { path: 'about', component: AboutComponent, data: { title: 'About' } },
  { path: 'author', component: AuthorComponent, data: { title: 'Author' } },
  { path: '**', component: PageNotFoundComponent, data: { title: 'Invalid path' } }
];

@NgModule({
  declarations: [
    AppComponent,
    IgracComponent,
    LigaComponent,
    NacionalnostComponent,
    TimComponent,
    HomeComponent,
    AboutComponent,
    AuthorComponent,
    PageNotFoundComponent,
    TimDialogComponent,
    NacionalnostDialogComponent,
    LigaDialogComponent,
    IgracDialogComponent, DialogButtonsComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatIconModule,
    MatSidenavModule,
    MatListModule,
    MatGridListModule,
    MatExpansionModule,
    MatTableModule,
    MatToolbarModule,
    MatSelectModule,
    MatOptionModule,
    HttpClientModule,
    MatSnackBarModule,
    MatDialogModule,
    MatInputModule,
    FormsModule,
    MatDatepickerModule,
    MatCheckboxModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatSortModule,
    MatTooltipModule,
    MatStepperModule,
    RouterModule.forRoot(Routes)
  ],
  entryComponents: [
    TimDialogComponent,
    NacionalnostDialogComponent,
    LigaDialogComponent,
    IgracDialogComponent
  ],
  providers: [LigaService, IgracService, TimService, NacionalnostService],
  bootstrap: [AppComponent]
})
export class AppModule { }
