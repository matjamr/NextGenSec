import { Navigate } from "react-router-dom";
import {useAuth} from "../context/oauth2/AuthProvider";

export const ProtectedRoute = ({ children }) => {
  const { token, verify } = useAuth();
  if (token == null) {
    oauthSignIn()
  } else {
    verify(token)
  }

  console.log(children)
  return children;
};

function oauthSignIn() {
  var oauth2Endpoint = 'https://accounts.google.com/o/oauth2/v2/auth';

  var form = document.createElement('form');
  form.setAttribute('method', 'GET'); 
  form.setAttribute('action', oauth2Endpoint);

  var params = {'client_id': '404881859099-qqru8bpuf4r4qhsv355q488ccmio44ee.apps.googleusercontent.com',
    'redirect_uri': 'http://localhost:3000/finishLogin',
    'response_type': 'id_token token',
    'scope': 'https://www.googleapis.com/auth/drive.metadata.readonly',
    'include_granted_scopes': 'true',
    'state': 'pass-through value',
    'nonce': 'n-0S6_WzA2Mj'};

  for (var p in params) {
    var input = document.createElement('input');
    input.setAttribute('type', 'hidden');
    input.setAttribute('name', p);
    input.setAttribute('value', params[p]);
    form.appendChild(input);
  }

  document.body.appendChild(form);
  form.submit();
}

export { oauthSignIn }
