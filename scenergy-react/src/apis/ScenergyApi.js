import api from "./api";

const getAllJobPost = async (data) => {
  console.log(data);
  const result = await api.get("jobPost/getAll");
  console.log(result);
  return result.data;
};
export { getAllJobPost };
