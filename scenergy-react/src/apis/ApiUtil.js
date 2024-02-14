import axios from "axios";
import {jwtDecode} from "jwt-decode";

class ApiUtil {
  static BASE_URL = process.env.REACT_APP_API_URL


    //토큰에서 userId 가져오기
    static getUserIdFromToken = () => {
      const token = this.getToken(); //Bearer 제거
        if (!token) return null;

    try {
      const decode = jwtDecode(token);
      // console.log(decode);
      return decode.sub; //id
    } catch (error) {
      console.error("토큰 티코드 안댐", error);
      return null;
    }
  };

  static getToken = () => {
    return localStorage.getItem("token") || "";
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
      try {
          let axiosResponse = await axios.get(ApiUtil.BASE_URL + url, {
              params: params,
              headers: this.#getHeader(),
          });
          console.log("axiosResponse", axiosResponse);
          return axiosResponse;
      } catch (error) {
          console.error("error", error);
          throw error;
      }
  };

  static post = async (url, data) => {

    try {
        let axiosResponse = await axios.post(ApiUtil.BASE_URL + url, data, {
        headers: this.#getHeader(),
      });
        return axiosResponse;
    } catch (error) {
        console.error("error", error);
      throw error;
    }

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
