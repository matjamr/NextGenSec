import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {SelectionModel} from "@angular/cdk/collections";
import {Observable, of, Subscription} from "rxjs";

export interface ConfigurableTableTemplate {
  columnTitle: string,
  displayedColumn: string
}

export interface ConfigurableTableActionButtons<T> {
  buttonTitle: string,
  action: (data: T) => any
}

export interface RowActionButton<T> {
  iconName: string,
  tooltip: string,
  action: (data: T) => any
}

@Component({
  selector: 'app-configurable-table',
  templateUrl: './configurable-table.component.html',
  styleUrls: ['./configurable-table.component.scss']
})
export class ConfigurableTableComponent implements OnInit, OnDestroy {
  private dataSubscription: Subscription = new Subscription();
  displayedColumns: string[] = [];

  @Input()
  elementData: Observable<any[]> = of([]);

  @Input()
  tableTemplate: ConfigurableTableTemplate[] = []

  @Input()
  nestedObjectRenderingMethod?: (data: any) => string;

  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  selection = new SelectionModel<any>(true, []);

  @Input()
  checkboxSupport = true;

  @Input()
  addButtonAction: any;

  @Input()
  rowActionButtons: RowActionButton<any>[] = []

  @Input()
  onRemoveClick: any | null = () => {};

  ngOnInit() {
    this.dataSubscription = this.elementData.subscribe(data => {
      this.dataSource.data = data;
      this.updateDisplayedColumns();
    });
  }

  ngOnDestroy() {
    this.dataSubscription.unsubscribe();
  }

  updateDisplayedColumns() {
    this.displayedColumns = this.tableTemplate.map(data => data.displayedColumn);

    if(this.addButtonAction !== null) {
      this.displayedColumns = [...this.displayedColumns, 'additionalData']
    }

    if(this.rowActionButtons.length > 0) {
      this.displayedColumns = [...this.displayedColumns, 'rowActionButtons']
    }

    if(this.checkboxSupport) {
      this.displayedColumns = ['select', ...this.displayedColumns];
    }
  }

  isAnySelected(): boolean {
    return this.selection.selected.length > 0;
  }

  onRemove() {
    this.onRemoveClick(this.selection.selected);
    this.selection.clear();
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  masterToggle() {
    // @ts-ignore
    this.isAllSelected() ? this.selection.clear() : this.dataSource.data.forEach(row => this.selection.select(row));
  }

  isCheckboxSupported() {
    return this.checkboxSupport
  }
}
