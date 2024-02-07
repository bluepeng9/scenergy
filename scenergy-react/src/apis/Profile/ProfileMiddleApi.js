// connect to User
import { api } from "../api";
import { useState } from "react";

export const User = {
  user: (data) => {
    return {
      // 학력
      school: "",
      degree: "",
      admissionDate: "",
      graduationDate: "",

      //경력
      company: "",
      position: "",
      startDate: "",
      endDate: "",

      //수상이력
      competition: "",
      organizer: "",
      awardDate: "",
    };
    return api.post("/profile", data);
  },
};
