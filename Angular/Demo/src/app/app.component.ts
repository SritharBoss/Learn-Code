import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'Demo';

  ngOnInit(){
    console.log("Init Called in App Component"+this.title);
  }

  onChange(eventArgs:any){
    console.log("On Click Called in App Component : VALUE ==> "+eventArgs);
  }

}
