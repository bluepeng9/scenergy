import ApiUtil from "./ApiUtil";
import apiUtil from "./ApiUtil";

class VideoPostApi {
  baseUrl = "";
  /**
   * 비디오 포스트 업로드
   * @param data
   * @returns {Promise<AxiosResponse<any>>}
   */
  uploadVideoPost = async (data) => {
    return await ApiUtil.post(
      `${this.baseUrl}/video-post/upload/video-post`,
      data,
    );
  };
  /**
   * 비디오 포스트 섬네일 업로드 TODO: multifile 업로드에서 오류가 발생할 가능성 있음. header의 type을             "Content-Type": "application/json", 을 사용 가능할지
   *
   * @param data
   * @returns {Promise<AxiosResponse<any>>}
   */
  uploadThumnail = async (data) => {
    return await ApiUtil.formDataPost(
      `${this.baseUrl}/video-post/upload/thumbnail`,
      data,
    );
  };
  uploadVideo = async (data) => {
    return await ApiUtil.formDataPost(
      `${this.baseUrl}/video-post/upload/video`,
      data,
    );
  };

  searchVideoPost = async (data) => {
    return await ApiUtil.post(`${this.baseUrl}/video-post/search`, {
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

  listVideoPosts = async () => {
    return await ApiUtil.get(`${this.baseUrl}/video-post/list`, {});
  };

  listFollowingVideoPosts = async (data) => {
    return await ApiUtil.get(`${this.baseUrl}/video-post/list/following`, {
      id: data.userId,
    });
  };

  deleteVideoPost = async (data) => {
    return await ApiUtil.delete(`${this.baseUrl}/video-post/delete`, {
      id: data.postVideoId,
    });
  };

  updateVideoPost = async (data) => {
    return await ApiUtil.put(`${this.baseUrl}/video-post/update`, {
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

  getMyVideoPosts = async (data) => {
    return await ApiUtil.get(`${this.baseUrl}/video-post/my-video-posts`, {
      userId: data,
    });
  };
}

let videoPostApi = new VideoPostApi();

export default videoPostApi;
