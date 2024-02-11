import api from "../api";
import ApiUtil from "../ApiUtil";
import { post } from "axios";

const getAllJobPost = async (data) => {
  console.log(data);
  const result = await api.get("jobPost/getAll");
  console.log(result);
  return result.data;
};

//create 만들어주세요 ScenergyField에서 사용중
//postData 들어올거임!
// const createJobPost = async (postData) => {
//   /*
//     //북마크 글작성할때 필요없음!
//   *  userId:2,
//     title: "",
//     content: "",
//     expirationDate: "2024.03.01 이런식으로 나오게 해주세요",
//     isActive:true,//이거 default값
//     peopleRecruited: 0,
//
//     //아직 태그 id별로 정해진게 확정이 아니라고 해서 일단 놔둘게
//     genreTags: [],
//     instrumentTags: [],
//     locationTags: [],
//
//    */
// };

const createJobPost = async (postData) => {
  console.log(postData);
  return await ApiUtil.post("jobPost", {
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

export { getAllJobPost, createJobPost };
