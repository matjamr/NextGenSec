import { Component } from '@angular/core';
import {Place} from "../../../../core/models/Place";

@Component({
  selector: 'app-place-info',
  templateUrl: './place-info.component.html',
  styleUrls: ['./place-info.component.css']
})
export class PlaceInfoComponent {
  // @ts-ignore
  place: Place = {
      "id": 1,
      "placeName": "Cosa",
      "emailPlace": "jeba123awdawaawdaaasdasdawawddadwaaaadadsa1adawna.edasdulica@gmail.com",
      "address": {
        "id": 1,
        "streetName": "ulica",
        "postalCode": "41-625",
        "city": "city"
      },
      "authorizedUsers": [
        {
          "id": 1,
          "user": {
            "id": 1,
            "email": "jamr.mat@gmail.com"
          },
          "assignmentRole": "USER",
          "products": [
            {
              "id": 1,
              "name": "Facial recognition",
              "description": "description sample",
              "monthlyPrice": 10.0,
              "imgIds": []
            },
            {
              "id": 1,
              "name": "Facial recognition",
              "description": "description sample",
              "monthlyPrice": 10.0,
              "imgIds": []
            }
          ]
        },
        {
          "id": 2,
          "user": {
            "id": 2,
            "email": "modkil265@gmail.com"
          },
          "assignmentRole": "ADMIN",
          "products": []
        }
      ]
  }


  constructor() {}

  ngOnInit(): void {
    // Fetch place data here
  }
}
