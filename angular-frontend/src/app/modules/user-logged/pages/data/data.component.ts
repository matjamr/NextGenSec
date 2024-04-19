import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {defaultUser, User} from "../../../../core/models/User";
import {UserService} from "../../../../core/services/user/user.service";

@Component({
  selector: 'app-data',
  templateUrl: './data.component.html',
  styleUrls: ['./data.component.scss']
})
export class DataComponent implements OnInit {
  user: User = defaultUser;
  showModal: boolean = false;

  constructor(
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute
  ) {
  }


  ngOnInit(): void {
    this.userService.verifyUser().subscribe(ret => {
      this.user = ret;
    })

    this.route.queryParams
      .subscribe(params => params['add'] ? this.openModal() : this.closeModal());
  }

  openModal() {
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }
}
