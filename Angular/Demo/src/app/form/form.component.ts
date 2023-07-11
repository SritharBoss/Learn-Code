import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'form-component',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent {

  firstName:string='';
  lastName:string='';
  email:string='';
  dob:any;

  log(args:any){
    console.log(args);
  }

}
