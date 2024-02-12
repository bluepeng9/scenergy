import axios from "axios";
import { jwtDecode } from "jwt-decode";

class ApiUtil {
  static BASE_URL = process.env.REACT_APP_API_URL;

  static getToken = () => {
    let token =
      "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiZXhwIjoxNzEwMDU5MDU0fQ.9DvhTbdKprZKQCTpWdCPLTPpba-f9T10SFtNjDgROovcpHoOB-V9EKixBFjpuVej4ulZvixuNTyfKvqIr4JHKA";
    return token;
  };

  //토큰에서 userId 가져오기
  static getUserIdFromToken = () => {
    const token = this.getToken().split(" ")[1]; //Bearer 제거
    if (!token) return null;

    try {
      const decode = jwtDecode(token);
      console.log(decode);
      return decode.sub; //id
    } catch (error) {
      console.error("토큰 티코드 안댐", error);
      return null;
    }
  };

  static #getHeader = () => {
    let token = this.getToken();
    let header = {
      "Content-Type": "application/json",
    };
    if (token !== "") {
      header["Authorization"] = "Bearer " + token;
    }
    return header;
  };

  static #getFormDataHeader = () => {
    let token = this.getToken();
    let header = {
      "Content-Type": "mutipart/form-data",
    };
    if (token !== "") {
      header["Authorization"] = "Bearer " + token;
    }
    return header;
  };

  static get = async (url, params) => {
    return await axios.get(ApiUtil.BASE_URL + url, {
      params: params,
      headers: this.#getHeader(),
    });
  };

  static post = async (url, data) => {
    return await axios.post(ApiUtil.BASE_URL + url, data, {
      headers: this.#getHeader(),
    });
  };
  static formDataPost = async (url, data) => {
    return await axios.post(ApiUtil.BASE_URL + url, data, {
      headers: this.#getFormDataHeader(),
    });
  };

  static put = async (url, data) => {
    return await axios.put(ApiUtil.BASE_URL + url, data, {
      headers: this.#getHeader(),
    });
  };

  static delete = async (url) => {
    return await axios.delete(ApiUtil.BASE_URL + url, {
      headers: this.#getHeader(),
    });
  };
}

export default ApiUtil;
