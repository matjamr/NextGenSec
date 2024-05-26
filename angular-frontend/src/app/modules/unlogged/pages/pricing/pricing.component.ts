import {Component} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ChartDataset, ChartOptions, ChartType} from "chart.js";

@Component({
  selector: 'app-pricing',
  templateUrl: './pricing.component.html',
  styleUrl: './pricing.component.css'
})
export class PricingComponent {
  pricingForm: FormGroup;
  estimatedIncome: number = 0;
  barChartOptions: ChartOptions = {
    responsive: true,
  };
  barChartLabels = ['Income Estimation'];
  barChartType: ChartType = 'bar';
  barChartLegend = true;
  barChartData: ChartDataset[] = [
    { data: [], label: 'Estimated Income' }
  ];

  constructor(private fb: FormBuilder) {
    this.pricingForm = this.fb.group({
      timePeriod: [''],
      biometricAuth: false,
      twoFactorAuth: false,
      selectedDevice: ['']
    });
  }

  calculateIncome() {
    const { timePeriod, biometricAuth, twoFactorAuth, selectedDevice } = this.pricingForm.value;
    this.estimatedIncome = 100; // Base value
    if (biometricAuth) this.estimatedIncome += 50;
    if (twoFactorAuth) this.estimatedIncome += 30;
    if (selectedDevice === 'smartphone') this.estimatedIncome += 20;

    // Adjust chart data based on the calculations
    this.barChartData[0].data = [this.estimatedIncome];
  }
}
