// ProfileVideoList.js
import React, { useEffect, useState } from "react";
import styles from "./ProfileVideoList.module.css";
import videoPostApi from "../../apis/VideoPostApi"; // VideoPostApi 파일의 경로에 따라 수정
import ApiUtil from "../../apis/ApiUtil";
import SearchVideoDetail from "../Search/SearchVideoDetail";
import { useParams } from "react-router-dom";

const ProfileVideoList = () => {
  const [videoList, setVideoList] = useState([]);

  const [selectedVideoPost, setSelectedVideoPost] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const { userId } = useParams();

  useEffect(() => {
    // 서버에서 사용자의 영상 목록을 가져오는 함수
    const fetchVideoList = async () => {
      try {
        // const userId = ApiUtil.getUserIdFromToken(); // 사용자 ID에 맞게 수정
        const response = await videoPostApi.getMyVideoPosts(parseInt(userId));

        // 가져온 영상 목록을 상태에 설정
        setVideoList(response.data.data.list);
        console.log("영상 목록 : " + response.data.data.list);
      } catch (error) {
        console.error("Error fetching video list:", error);
      }
    };

    // 영상 목록 가져오기 함수 호출
    fetchVideoList();
  }, [userId]); // 마운트 시에만 호출

  const handleModalOpen = (videoPost) => {
    setSelectedVideoPost(videoPost);
    setIsModalOpen(true);
  };

  return (
    <div className={styles.videoListContainer}>
      {/*<div className={styles["division-line"]}></div>*/}
      {/*<h1>대표 영상</h1>*/}
      {/*<div className={styles["division-line"]}></div>*/}

      {/* 대표 영상 컴포넌트 또는 대표 영상 목록을 보여주는 UI 추가하기 */}

      <div className={styles["division-line"]}></div>
      <h1 className={styles.videoListTitle}>영상 목록</h1>

      {/* 영상 목록을 보여주는 UI 추가 */}
      <div className={styles.videoGrid}>
        {videoList.map((video) => (
          <div key={video.id} className={styles.videoListItem}>
            {/* 영상 썸네일 또는 동영상 플레이어 추가 */}
            <img
              src={video.video.thumbnailUrlPath} // 예시: video.thumbnailUrlPath가 실제로 사용되는 프로퍼티인지 확인
              alt={video.title}
              className={styles.videoThumbnail}
              onClick={() => handleModalOpen(video)}
            />
            <h4 className={styles.videoTitle}>{video.title}</h4>
            {/* 기타 영상 정보 표시 */}
          </div>
        ))}
      </div>

      {/* 추가한 부분: 모달 */}
      {isModalOpen && (
        <SearchVideoDetail
          onClick={() => setIsModalOpen(false)}
          searchVideoPosts={selectedVideoPost}
        />
      )}
    </div>
  );
};

export default ProfileVideoList;
