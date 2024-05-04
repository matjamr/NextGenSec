import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-place-details',
  templateUrl: './place-details.component.html',
  styleUrl: './place-details.component.css'
})
export class PlaceDetailsComponent implements OnInit{

  placeName: string = '';

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.placeName = this.route.snapshot.paramMap.get('placeName')!;
    console.log(this.placeName);
  }
}
