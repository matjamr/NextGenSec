import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService {
  oauth2Endpoint: string = "https://accounts.google.com/o/oauth2/v2/auth"

  auth(isLogin: boolean) {
    let params = {
      'client_id': '815525200901-5cvm0rdumhlk9sp152o9frf73t5cnkrq.apps.googleusercontent.com',
      'redirect_uri': 'http://localhost:4200/finishLogin?register='+!isLogin,
      'response_type': 'id_token token',
      'scope': 'https://www.googleapis.com/auth/drive.metadata.readonly',
      'include_granted_scopes': 'true',
      'state': 'EMPTY',
      'nonce': 'n-0S6_WzA2Mj'
    };

    var form = document.createElement('form');
    form.setAttribute('method', 'GET');
    form.setAttribute('action', this.oauth2Endpoint);

    for (var p in params) {
      var input = document.createElement('input');
      input.setAttribute('type', 'hidden');
      input.setAttribute('name', p);
      // @ts-ignore
      input.setAttribute('value', params[p]);
      form.appendChild(input);
    }

    document.body.appendChild(form);
    form.submit();
  }
}
