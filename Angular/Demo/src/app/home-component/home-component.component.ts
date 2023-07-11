import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'home-component',
  templateUrl: './home-component.component.html',
  styleUrls: ['./home-component.component.css']
})
export class HomeComponentComponent implements OnInit {
  ngOnInit(): void {
    console.log("Init Called in Home Component");
  }

  @Input()
  homeValue:string='';

  @Output()
  hey=new EventEmitter();

  onClick(){
    this.homeValue='value in Home Component';
    this.hey.emit(this.homeValue);
  }

  

}
