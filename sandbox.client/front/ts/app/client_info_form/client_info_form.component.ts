import {Component} from "@angular/core";
import {HttpService} from "../HttpService";

@Component({
  selector: 'client-info-form-component',
  template: require('./client_info_form.component.html'),
  styles: [require('./client_info_form.component.css')],
})

export class ClientInfoFormComponent {

  constructor(private httpService: HttpService) {}


  close() {
    document.getElementById("myModal").style.display = "none"
  }

}