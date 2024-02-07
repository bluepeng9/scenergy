import axios from "axios";

export const fetchMyFollowings = (fromUserId) => {
  return apiClient.get("/follow/followings", { params: fromUserId });
};
