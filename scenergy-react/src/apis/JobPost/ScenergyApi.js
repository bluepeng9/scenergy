import api from "../api";

const getAllJobPost = async (data) => {
  console.log(data);
  const result = await api.get("jobPost/getAll");
  console.log(result);
  return result.data;
};

//create 만들어주세요 ScenergyField에서 사용중
//postData 들어올거임!
const createJobPost = async (postData) => {
  /*
    //북마크 글작성할때 필요없음!
  *  userId:2,
    title: "",
    content: "",
    expirationDate: "2024.03.01 이런식으로 나오게 해주세요",
    isActive:true,//이거 default값
    peopleRecruited: 0,
    genreTags: [],
    instrumentTags: [],
    locationTags: [],

   */
};

export { getAllJobPost, createJobPost };
