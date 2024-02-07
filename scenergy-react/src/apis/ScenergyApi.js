import axios from "axios";
import api from "./api";

export const scenergy = {
  getAll: (data) => {
    return {
      IsActive: "active",
      nickname: "허허",
      title: "허허허허",
      content: "드럼 1명 구합니다",
      expirationDate: "2024-03-01",
      totalCount: "34",
    };
    return api.get("jobPost/getAll");
  },
};
