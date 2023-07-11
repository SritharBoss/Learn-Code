import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  inputValue = "";

  list = {
    firstName: 'Srithar',
    lastname: 'Boss',
    age: 25,
    money: 6540000,
    rating: 7.55486,
    goodGuy: true,
    createdDate: new Date()
  }

  arr = [{ id: 1 }, { id: 2 }, { id: 3 }];


  onClick(e:Event,val: any) {
    console.log((e.target as HTMLInputElement).value);
  }

  file: any;
  fileChanged(e: any) {
    console.log(e)
    this.file = e.target.files[0];
    console.log(typeof e);
    this.uploadDocument();
  }

  uploadDocument() {
    let fileReader = new FileReader();
    fileReader.onload = (e) => {
      console.log(fileReader.result);
    }
    fileReader.readAsText(this.file);
}

}

