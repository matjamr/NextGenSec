import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Subscription} from "rxjs";
import {PlaceService} from "../../../../core/services/place/place.service";
import {UserService} from "../../../../core/services/user/user.service";

@Component({
  selector: 'app-choose-level',
  templateUrl: './choose-level.component.html',
  styleUrls: ['./choose-level.component.scss']
})
export class ChooseLevelComponent implements OnInit, OnDestroy {
  private subscription: Subscription = new Subscription();

  constructor(
    private router: Router,
    private placeService: PlaceService,
    private userService: UserService
  ) {
  }

  ngOnInit(): void {
    this.subscription.add(this.placeService.getPlacesByUser().subscribe(
      places => {
        if (places && places.length > 0) {
          const targetRole = places[0]?.authorizedUsers![0]?.assignmentRole === "ADMIN" ? "admin" : "user";
          localStorage.setItem("role", targetRole);
          this.router.navigate([`/${targetRole}`]);
        } else {
          this.router.navigate([`/user`]).then(() => {
            console.log(`Navigated to /user`);
          })
        }
      },
      error => {
        console.error('Error fetching places:', error);
      }
    ));
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
