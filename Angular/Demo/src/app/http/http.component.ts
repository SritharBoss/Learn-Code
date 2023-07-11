import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { map } from 'rxjs';

@Component({
  selector: 'app-http',
  templateUrl: './http.component.html',
  styleUrls: ['./http.component.css']
})
export class HttpComponent implements OnInit {

  url='https://jsonplaceholder.typicode.com/users'
  users:any;

  constructor(http:HttpClient) {
    http.get<any[]>(this.url).subscribe(response=>{
      console.log(response)
      this.users=response;
    })
    document.querySelectorAll<HTMLElement>(".date").forEach(a=>{a.style.backgroundColor="red"})
  }
  
  ngOnInit(): void {
  }

  myFun(value:string){
    console.log((<any[]>this.users).filter(a=>{
      return (<string>a.email).includes(value);
    }))
    
  }

}
