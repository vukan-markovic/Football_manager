import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-dialog-buttons',
  templateUrl: './dialog-buttons.component.html'
})
export class DialogButtonsComponent implements OnInit {
  @Input()
  public isValid: boolean;
  @Input()
  public flag: number;

  @Output()
  addEvent = new EventEmitter<void>();
  @Output()
  updateEvent = new EventEmitter<void>();
  @Output()
  deleteEvent = new EventEmitter<void>();
  @Output()
  cancelEvent = new EventEmitter<void>();

  constructor() { }
  ngOnInit() { }

  public add(): void {
    this.addEvent.emit();
  }

  public update(): void {
    this.updateEvent.emit();
  }

  public delete(): void {
    this.deleteEvent.emit();
  }

  public cancel(): void {
    this.cancelEvent.emit();
  }
}
