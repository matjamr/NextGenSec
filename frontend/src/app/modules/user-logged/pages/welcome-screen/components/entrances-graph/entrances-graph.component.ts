import {AfterViewInit, Component} from '@angular/core';
import {Chart, registerables} from "chart.js";

@Component({
  selector: 'app-entrances-graph',
  templateUrl: './entrances-graph.component.html',
  styleUrl: './entrances-graph.component.scss'
})
export class EntrancesGraphComponent implements AfterViewInit {

  constructor() {
    Chart.register(...registerables);
  }

  ngAfterViewInit(): void {
    const ctx = document.getElementById('mockGraph') as HTMLCanvasElement;
    new Chart(ctx, {
      type: 'line',
      data: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
        datasets: [{
          label: 'Mock Data',
          data: [65, 59, 80, 81, 56, 55, 40],
          fill: false,
          borderColor: 'rgb(75, 192, 192)',
          tension: 0.1
        }]
      }
    });
  }
}
