import ApiUtil from "./ApiUtil";

class VideoPostApi {
  baseUrl = "/video-post";
  /**
   * 비디오 포스트 업로드
   * @param data
   * @returns {Promise<AxiosResponse<any>>}
   */
  uploadVideoPost = async (data) => {
    return await ApiUtil.post(`${this.baseUrl}/upload/video-post`, {
      userId: data.userId,
      postTitle: data.postTitle,
      postContent: data.postContent,
      genreTags: data.genreTags,
      instrumentTags: data.instrumentTags,
      videoUrlPath: data.videoUrlPath,
      thumbnailUrlPath: data.thumbnailUrlPath,
      videoTitle: data.videoTitle,
      videoArtist: data.videoArtist,
    });
  };
  /**
   * 비디오 포스트 섬네일 업로드 TODO: multifile 업로드에서 오류가 발생할 가능성 있음. header의 type을             "Content-Type": "application/json", 을 사용 가능할지
   *
   * @param data
   * @returns {Promise<AxiosResponse<any>>}
   */
  uploadThumnail = async (data) => {
    const formData = new FormData();
    formData.append("thumbnail", data.thumbnail);
    return await ApiUtil.post(`${this.baseUrl}/upload/thumbnail`, formData);
  };
  uploadVideo = async (data) => {
    const formData = new FormData();
    formData.append("justVideo", data.video);
    return await ApiUtil.post(`${this.baseUrl}/upload/video`, formData);
  };

  searchVideoPost = async (data) => {
    return await ApiUtil.post(`${this.baseUrl}/search`, {
      word: data.word,
      gt: data.genreTags,
      it: data.instrumentTags,
    });
  };

  getVideoPost = async (data) => {
    return await ApiUtil.get(`${this.baseUrl}`, {
      id: data.postVideoId,
    });
  };

  listVideoPosts = async (data) => {
    return await ApiUtil.get(`${this.baseUrl}/list`, {});
  };

  listFollowingVideoPosts = async (data) => {
    return await ApiUtil.get(`${this.baseUrl}/list/following`, {
      id: data.userId,
    });
  };

  deleteVideoPost = async (data) => {
    return await ApiUtil.delete(`${this.baseUrl}/delete`, {
      id: data.postVideoId,
    });
  };

  updateVideoPost = async (data) => {
    return await ApiUtil.put(`${this.baseUrl}/update`, {
      postVideoId: data.postVideoId,
      postTitle: data.postTitle,
      postContent: data.postContent,
      genreTags: data.genreTags,
      instrumentTags: data.instrumentTags,
      videoUrlPath: data.videoUrlPath,
      thumbnailUrlPath: data.thumbnailUrlPath,
      videoTitle: data.videoTitle,
      videoArtist: data.videoArtist,
    });
  };
}

let videoPostApi = new VideoPostApi();

export default videoPostApi;
