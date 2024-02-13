import axios from "axios";

class ApiUtil {
  static BASE_URL = process.env.REACT_APP_API_URL

  static getToken = () => {
      return "Bearer " + localStorage.getItem("token") || "";
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
    return await axios.post(ApiUtil.BASE_URL + "/video-post" + url, data, {
      headers: this.#getHeader(),
    });
  };
  static formDataPost = async (url, data) => {
    return await axios.post(ApiUtil.BASE_URL + "/video-post" + url, data, {
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
