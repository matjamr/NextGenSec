import {Component} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {PositionServiceService} from "../../services/position-service/position-service.service";

@Component({
  selector: 'app-filter-dialog',
  templateUrl: './filter-dialog.component.html',
  styleUrl: './filter-dialog.component.scss'
})
export class FilterDialogComponent {
  filterCriteria!: string;


  constructor(
    public dialogRef: MatDialogRef<FilterDialogComponent>,
    private positionService: PositionServiceService
  ) {}

  onCancel(): void {
    this.dialogRef.close();
  }

  onApply(): void {
    this.dialogRef.close(this.filterCriteria);
  }

  onInputChange(event: Event) {
    this.positionService.setKmRange((event.target as HTMLInputElement).valueAsNumber);
  }

  getKmRange() {
    return this.positionService.kmRangeSubject.value;
  }
}
