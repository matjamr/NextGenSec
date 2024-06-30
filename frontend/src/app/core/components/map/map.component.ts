import {AfterViewInit, Component, ElementRef, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import * as L from 'leaflet';
import {PositionServiceService} from "../../services/position-service/position-service.service";
import {Subscription} from "rxjs";

const iconRetinaUrl = 'assets/marker-icon-2x.png';
const iconUrl = 'assets/marker-icon.png';
const shadowUrl = 'assets/marker-shadow.png';
const iconDefault = L.icon({
  iconRetinaUrl,
  iconUrl,
  shadowUrl,
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  tooltipAnchor: [16, -28],
  shadowSize: [41, 41]
});
L.Marker.prototype.options.icon = iconDefault;

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('map') mapContainer!: ElementRef;
  @Input() items: MapItem[] = [];
  subscriptions: Subscription[] = [];
  position$ = this.positionService.getPosition();
  mapPins$ = this.positionService.getMapPins();

  constructor(
    private positionService: PositionServiceService
  ) {
  }

  ngOnInit(): void {
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  ngAfterViewInit(): void {
    let map = L.map(this.mapContainer.nativeElement);

    this.position$.subscribe((position) => {
      if (position !== null) {
        const {latitude, longitude} = position.coords;

        map.setView([latitude, longitude], 13);

        this.items.forEach(item => {
          if (item.address?.latitude && item.address?.longitude) {
            L.marker([item.address.latitude, item.address.longitude]).addTo(map);
          }
        });

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          attribution: '© OpenStreetMap contributors'
        }).addTo(map);
      }
    }, () => {
      map.setView([51.505, -0.09], 13);
    });

    this.mapPins$.subscribe(mapPins => {
      map.eachLayer((layer) => {
        if (layer instanceof L.Marker) {
          map.removeLayer(layer);
        }
      });

      mapPins.forEach(item => {
        if (item.address.latitude && item.address.longitude) {
          L.marker([item.address.latitude, item.address.longitude]).addTo(map);
        }
      });
    })
  }
}

export interface MapItem {
  id: string;
  placeName: string;
  address: {
    id: string;
    latitude: number;
    longitude: number;
  };
}
