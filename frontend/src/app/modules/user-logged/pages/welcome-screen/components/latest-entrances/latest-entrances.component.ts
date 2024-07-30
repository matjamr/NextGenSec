import {Component} from '@angular/core';

export interface Entrance {
  placeName: string;
  method: string;
  date: string;
}

const ELEMENT_DATA: Entrance[] = [
  {placeName: 'Miejsce A', method: 'METHODA A', date: 'DATA ABCDDEF'}
];

@Component({
  selector: 'app-latest-entrances',
  templateUrl: './latest-entrances.component.html',
  styleUrl: './latest-entrances.component.scss'
})
export class LatestEntrancesComponent {
  displayedColumns: string[] = ['placeName', 'method', 'date'];
  dataSource = ELEMENT_DATA;
}
