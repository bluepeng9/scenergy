import ApiUtil from "../ApiUtil";

class ProfileMiddleApi {
  baseUrl = "/portfolio";

  updateAllPortfolios = async (data) => {
    console.log("updateAllPortfolios data")
    console.log(data)
    return await ApiUtil.put(`${this.baseUrl}/update-all`, {
      userId: data.userId,
      portfolioId: data.portfolioId,
      description: data.description,
      educations: data.educations,
      etcs: data.etcs,
      experiences: data.experiences,
      honors: data.honors
    });
  };
  createPortfolio = async (data) => {
    return await ApiUtil.post(`${this.baseUrl}/create-port`, {
      userId: data.userId,
    });
  };
  readPortfolios = async (data) => {
    return await ApiUtil.get(`${this.baseUrl}/read`, {
      userId: data.userId,
    });
  };

  deleteAllPortfolios = async (data) => {
    return await ApiUtil.post(`${this.baseUrl}/delete-all`, {
      userId: data.userId,
      portfolioId: data.portfolioId,
    });
  };
}

let profileMiddleApi = new ProfileMiddleApi();

export default profileMiddleApi;
