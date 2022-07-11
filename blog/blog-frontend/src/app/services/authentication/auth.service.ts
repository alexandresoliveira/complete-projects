import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import jwtDecode from 'jwt-decode';


interface SignInData {
  email: string,
  password: string
}

interface SignUpData extends SignInData {
  name: string,
}

interface SignInDataResult {
  token: string;
  type: string;
}

interface SignUpDataResult {
  name: string;
  email: string;
  authorities: string[];
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient
  ) { }

  async signIn(signInData: SignInData) {
    const result = await this.http.post<SignInDataResult>(`${environment.urlApi}/v1/auth/login`, signInData).toPromise();
    if (result && result.token) {
      window.localStorage.setItem("@!blog::token", result.token);
      return true;
    }
    return false;
  }

  signOut() {
    window.localStorage.removeItem("@!blog::token");
    window.localStorage.removeItem("@!blog::user");
  }

  async signUp(signUpData: SignUpData) {
    try {
      const result = await this.http.post<SignUpDataResult>(`${environment.urlApi}/v1/users/create`, signUpData).toPromise();
      if (result) {
        window.localStorage.setItem("@!blog::user", JSON.stringify(result));
        return await this.signIn({email: signUpData.email, password: signUpData.password});
      }
      return false;
    } catch (error) {
      throw new Error(error);
    }
  }

  getAuthorizationToken() {
    const token = window.localStorage.getItem('@!blog::token');
    return token;
  }

  getTokenExpirationDate(token: string): Date {
    
    const decoded: any = jwtDecode(token);

    if (decoded.exp === undefined) {
      return null;
    }

    const date = new Date(0);
    date.setUTCSeconds(decoded.exp);
    return date;
  }

  isTokenExpired(token?: string): boolean {
    if (!token) {
      return true;
    }

    const date = this.getTokenExpirationDate(token);
    if (date === undefined) {
      return false;
    }

    return !(date.valueOf() > new Date().valueOf());
  }

  isUserLoggedIn() {
    const token = this.getAuthorizationToken();
    if (!token) {
      return false;
    } else if (this.isTokenExpired(token)) {
      return false;
    }

    return true;
  }
  
}
