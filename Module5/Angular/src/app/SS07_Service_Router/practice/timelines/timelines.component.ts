import {Component, OnInit} from '@angular/core';
import { DateUtilService} from './date-until/DateUntilService';

@Component({
  selector: 'app-timelines',
  templateUrl: './timelines.component.html',
  styleUrls: ['./timelines.component.scss']
})
export class TimelinesComponent implements OnInit {

  output = '';
  constructor(private dateUtilService: DateUtilService) { }


  ngOnInit(): void {
  }

  onChange(value) {
    this.output = this.dateUtilService.getDiffToNow(value);
  }
}
