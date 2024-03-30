import {Pipe, PipeTransform} from "@angular/core";
import {Place} from "../../../core/models/Place";

@Pipe({
  name: 'supportedMethodsPipe'
})
export class SupportedMethodsPipe implements PipeTransform {
  transform(value: Place[] | any): string[] {
    let ret: string[] = []

    // @ts-ignore
    value.every(place => place.authorizedUsers[0].products.every(product => ret.push(product.name)))

    return ret
  }


}
