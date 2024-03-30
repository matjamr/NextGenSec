import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Image} from "../../models/Image";
import {Product} from "../../models/Product";

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(
    private http: HttpClient
  ) { }

  private apiUrl: string =  "http://localhost:8080/api/image"
  private apiSensitiveUrl: string =  "http://localhost:8080/api/user/sensitive"

  public uploadImage(images: File[], products: Product[], selectedProduct: Product | null) {
    const formData = new FormData();
    let idsTab: Image[] = [];
    formData.append('image', images[0]);

    for (let i = 0; i < images.length; i++) {
      const formData = new FormData();

      formData.append('image', images[i]);
      this.http.post<Image>(this.apiUrl, formData, buildHeader()).subscribe(ret => {
        idsTab.push(ret)
        this.http.post<Image>(this.apiSensitiveUrl, {image:{id:ret.id}, product: {id: selectedProduct?.id!!}}, buildHeader()).subscribe(ret => {
          console.log(ret)
        })

      });
    }

    return idsTab;
  }
}

const buildHeader = () => {
  return {
    headers: {
      "token": String(localStorage.getItem("token")),
      "source": String(localStorage.getItem("source"))
    }
  }
}
