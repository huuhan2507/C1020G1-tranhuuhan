import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-rate',
  templateUrl: './rate.component.html',
  styleUrls: ['./rate.component.scss']
})
export class RateComponent implements OnInit {
  onRateChange(value) {
    console.log(value);
  }
  constructor() { }

  ngOnInit(): void {
  }

}
