import {Component} from '@angular/core';
import {Generator} from "../../../../../../core/components/custom-list/Generator";
import {Place} from "../../../../../../core/models/Place";


@Component({
  selector: 'app-place-shortcut-image',
  templateUrl: './place-shortcut-image.component.html',
  styleUrl: './place-shortcut-image.component.css'
})
export class PlaceShortcutImageComponent implements Generator<Place> {

  generate(data: Place): string {
    return `
       <div class="place-container">
        <div class="place-content">
        <h2>${data.placeName}</h2>
        <img src="${data.image!.url}" alt="${data.placeName}" width="250px">
      </div>
    </div>
    `;
  }
}
