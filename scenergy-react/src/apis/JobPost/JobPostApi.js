import api from "../api";
import ApiUtil from "../ApiUtil";

class JobPostApi {
  getAllJobPost = async (data) => {
    console.log(data);
    const result = await api.get("/jobPost/getAll");
    console.log(result);
    return result.data;
  };

  createJobPost = async (postData) => {
    console.log(postData);
    return await ApiUtil.post("/jobPost/create", {
      userId: postData.userId,
      title: postData.title,
      content: postData.content,
      expirationDate: postData.expirationDate,
      isActive: postData.isActive,
      peopleRecruited: postData.peopleRecruited,
      genreTags: postData.genreTags,
      instrumentTags: postData.instrumentTags,
      locationTags: postData.locationTags,
    });
  };
  getAllMyApply = async (id) => {
    return await ApiUtil.get(`/jobPost/get/apply/${id}`);
  };

  getAllBookMark = async (id) => {
    return await ApiUtil.get(`/jobPost/get/bookmark/${id}`);
  };

  getAllMyJobPost = async (id) => {
    return await ApiUtil.get(`/jobPost/get/myJobPost/${id}`);
  };

  getJobPost = async (id) => {
    return await ApiUtil.get(`/jobPost/get/${id}`);
  };

  applyJobPost = async (data) => {
    return await ApiUtil.post(`/jobPost/apply`, {
      jobPostId: data.jobPostId,
      userName: data.userName,
    });
  };

  bookMarkJobPost = async (data) => {
    return await ApiUtil.post(`/jobPost/bookmark`, {
      jobPostId: data.jobPostId,
      userName: data.userName,
    });
  };

  updateJobPost = async (id, postData) => {
    return await ApiUtil.post(`/jobPost/${id}`, {
      title: postData.title,
      content: postData.content,
      expirationDate: postData.expirationDate,
      peopleRecruited: postData.peopleRecruited,
      bookMark: postData.bookMark,
      isActive: postData.isActive,
      instrumentTags: postData.instrumentTags,
      locationTags: postData.locationTags,
      genreTags: postData.genreTags,
    });
  };

  deleteJobPost = async (id) => {
    return await ApiUtil.delete(`/jobPost/${id}`);
  };
}

const jobPostApi = new JobPostApi();
export default jobPostApi;
