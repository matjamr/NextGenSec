import {
  AfterViewInit,
  Component,
  ElementRef,
  Input,
  OnDestroy,
  OnInit,
  ViewChild,
  ViewEncapsulation
} from '@angular/core';
import * as L from 'leaflet';
import {PositionServiceService} from '../../services/position-service/position-service.service';
import {Subscription} from 'rxjs';

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
  styleUrls: ['./map.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class MapComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('map') mapContainer!: ElementRef;
  @Input() items: MapItem[] = [];
  subscriptions: Subscription[] = [];
  position$ = this.positionService.getPosition();
  mapPins$ = this.positionService.getMapPins();
  map!: L.Map;

  constructor(private positionService: PositionServiceService) {}

  ngOnInit(): void {}

  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  ngAfterViewInit(): void {
    this.map = L.map(this.mapContainer.nativeElement, { attributionControl: false });

    this.position$.subscribe(
      position => {
        if (position !== null) {
          const { latitude, longitude } = position.coords;

          this.map.setView([latitude, longitude], 13);

          L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '© OpenStreetMap contributors'
          }).addTo(this.map);

          this.addMarkers(this.items);
        }
      },
      () => {
        this.map.setView([51.505, -0.09], 13);
      }
    );

    this.subscriptions.push(
      this.mapPins$.subscribe(mapPins => {
        this.clearMarkers();
        this.addMarkers(mapPins);
        this.focusOnPoints(mapPins);
      })
    );
  }

  clearMarkers() {
    this.map.eachLayer(layer => {
      if (layer instanceof L.Marker) {
        this.map.removeLayer(layer);
      }
    });
  }

  addMarkers(mapItems: MapItem[]) {
    mapItems.forEach(item => {
      if (item.address?.latitude && item.address?.longitude) {
        const marker = L.marker([item.address.latitude, item.address.longitude]).addTo(this.map);
        marker.bindPopup(`<b>${item.placeName}</b><br>${item.address.id}`).openPopup();
      }
    });
  }

  focusOnPoints(mapItems: MapItem[]) {
    const markers = mapItems.map(item => L.marker([item.address.latitude, item.address.longitude]));
    const group = L.featureGroup(markers);
    this.map.fitBounds(group.getBounds(), {
      animate: true,
      duration: 1 // Duration of the animation in seconds
    });
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
