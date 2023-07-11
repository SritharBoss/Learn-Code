import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'titlecase'
})
export class TitlecasePipe implements PipeTransform {

  transform(value:string): any {
    
    let words=value.split(' ');

    if(!value) return null;

    for(let i=0;i<words.length;i++){

      if(i!=0 && isPreposition(words[i])){
        words[i] = words[i].toLowerCase();
      }else{
        words[i] = words[i].substring(0,1).toUpperCase().concat(words[i].substring(1).toLowerCase())
      }

    }

    return words.join(' ');

    function isPreposition(word:string){

      let prep=['of','the','is','not']
      return prep.includes(word.toLocaleLowerCase());
    }

  }

}
