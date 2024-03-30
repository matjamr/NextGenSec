import {Component, Input, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {SelectionModel} from "@angular/cdk/collections";

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
  styleUrl: './configurable-table.component.css'
})
export class ConfigurableTableComponent implements OnInit {
  displayedColumns: string[] = [];

  @Input()
  elementData: any[] = []

  @Input()
  tableTemplate: ConfigurableTableTemplate[] = []

  @Input()
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  selection = new SelectionModel<any>(true, []);

  @Input()
  checkboxSupport = true;

  @Input()
  addButtonAction: any;

  @Input()
  rowActionButtons: RowActionButton<any>[] = []

  ngOnInit() {
    this.dataSource = new MatTableDataSource(this.elementData);
    this.displayedColumns = this.tableTemplate.map(data => data.displayedColumn)

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
