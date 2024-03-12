import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { PlaceService } from "../../../../core/services/place/place.service";

@Component({
  selector: 'app-choose-level',
  templateUrl: './choose-level.component.html',
  styleUrls: ['./choose-level.component.css']
})
export class ChooseLevelComponent implements OnInit, OnDestroy {
  private subscription: Subscription = new Subscription();

  constructor(
    private router: Router,
    private placeService: PlaceService
  ) {}

  ngOnInit(): void {
    this.subscription.add(this.placeService.getPlacesByUser().subscribe(data => {
      if (data && data.length > 0) {
        const targetRole = data[0]?.authorizedUsers[0]?.assignmentRole === "ADMIN" ? "admin" : "user";
          localStorage.setItem("role", targetRole);
          window.location.href = "http://localhost:4200/" + targetRole;

      } else {
        this.router.navigate([`/user`]).then(() => {
          console.log(`Navigated to /user`);
        })
      }
    }));
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
