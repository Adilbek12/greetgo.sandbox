import {NgModule} from "@angular/core";
import {HttpModule, JsonpModule} from "@angular/http";
import {FormsModule} from "@angular/forms";
import {BrowserModule} from "@angular/platform-browser";
import {RootComponent} from "./root.component";
import {LoginComponent} from "./input/login.component";
import {MainFormComponent} from "./main_form/main_form.component";
import {HttpService} from "./HttpService";
import {ClientEditFormComponent} from "./client_edit_form/client_edit_form.component";
import {ClientsListFormComponent} from "./clients_list_form/clients_list_form.component";

@NgModule({
  imports: [
    BrowserModule, HttpModule, JsonpModule, FormsModule
  ],
  declarations: [
    RootComponent, LoginComponent, MainFormComponent, ClientEditFormComponent, ClientsListFormComponent,
  ],
  bootstrap: [RootComponent],
  providers: [HttpService],
  entryComponents: [],
})
export class AppModule {
}