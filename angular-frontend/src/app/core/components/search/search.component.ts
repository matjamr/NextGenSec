import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {debounceTime, delay, filter, map, ReplaySubject, Subject, takeUntil, tap} from "rxjs";
import {Searchable} from "../../models/interfaces";

export interface Bank {
  id: string;
  name: string;
}

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent<T extends Searchable> implements OnInit, OnDestroy{

  @Input()
  placeholder: string = 'Search...'

  @Input()
  data: T[] | null = [];

  @Input()
  renderView: (data: T) => string = (data) => '';

  @Input()
  searchPredicate: (data: T, search: string) => boolean = (data, search) => false;

  @Input()
  dataFormControl: FormControl<T | null> = new FormControl<T | null>(null);
  selectedDataFormControl: FormControl<string | null> = new FormControl<string>('');
  searching = false;
  filteredData: ReplaySubject<T[]> = new ReplaySubject<T[]>(1);
  protected _onDestroy = new Subject<void>();

  ngOnInit() {
    this.selectedDataFormControl.valueChanges
      .pipe(
        filter(search => !!search),
        tap(() => this.searching = true),
        takeUntil(this._onDestroy),
        debounceTime(200),
        map(search => {
          if (!this.data) {
            return [];
          }

          return this.data.filter(record => this.searchPredicate(record, search!));
        }),
        delay(500),
        takeUntil(this._onDestroy)
      )
      .subscribe(filteredBanks => {
          this.searching = false;
          this.filteredData.next(filteredBanks);
        },
        error => {
          this.searching = false;
        });

  }

  ngOnDestroy() {
    this._onDestroy.next();
    this._onDestroy.complete();
  }
}
