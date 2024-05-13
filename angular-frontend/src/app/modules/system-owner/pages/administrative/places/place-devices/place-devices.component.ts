import {Component} from '@angular/core';
import {filter, map, Observable} from "rxjs";
import {Place} from "../../../../../../core/models/Place";
import {ActivatedRoute} from "@angular/router";
import {PlaceService} from "../../../../../../core/services/place/place.service";
import {FormBuilder} from "@angular/forms";
import {Device} from "../../../../../../core/models/Device";
import {User} from "../../../../../../core/models/User";
import {
  ConfigurableTableTemplate,
  RowActionButton
} from "../../../../../../core/components/configurable-table/configurable-table.component";
import {log} from "@angular-devkit/build-angular/src/builders/ssr-dev-server";

interface PlaceDevice {
  deviceName: string;
  productName: string;
  device: Device;
}

@Component({
  selector: 'app-place-devices',
  templateUrl: './place-devices.component.html',
  styleUrl: './place-devices.component.css'
})
export class PlaceDevicesComponent {
  placeName: string;
  devices$: Observable<PlaceDevice[]>;

  constructor(private route: ActivatedRoute, private placeService: PlaceService, private fb: FormBuilder) {
    this.placeName = this.route.snapshot.paramMap.get('placeName')!;
    this.devices$ = this.placeService.getPlaceById(this.placeName)
      .pipe(map(place => (place.devices || []).map(device => ({
        deviceName: device.deviceName,
        productName: device.product.name,
        device
      }))));
  }

  tableTemplate: ConfigurableTableTemplate[] = [
    {columnTitle: 'Device Name', displayedColumn: 'deviceName'},
    {columnTitle: 'Product Name', displayedColumn: 'productName'}
  ]

  rowActionButtons: RowActionButton<any>[] = [
    {
      iconName: 'info',
      action: (elem: any) => console.log('aaa'),
      tooltip: 'place details'
    }
  ]

  addButtonAction = () => {
    console.log('Add admin');
  }

  onRemove = (places: Place[]) => {
    console.log('Remove admins');
  }
}
