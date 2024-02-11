// ProfileVideoList.js
import React, { useState, useEffect } from "react";
import styles from "./ProfileVideoList.module.css";
import videoPostApi from "../../apis/VideoPostApi"; // VideoPostApi 파일의 경로에 따라 수정

const ProfileVideoList = () => {
  const [videoList, setVideoList] = useState([]);

  useEffect(() => {
    // 서버에서 사용자의 영상 목록을 가져오는 함수
    const fetchVideoList = async () => {
      try {
        const userId = "eodms4334@naver.com"; // 사용자 ID에 맞게 수정
        const response = await videoPostApi.getVideoListByUserId(userId);

        // 가져온 영상 목록을 상태에 설정
        setVideoList(response.data);
      } catch (error) {
        console.error("Error fetching video list:", error);
      }
    };

    // 영상 목록 가져오기 함수 호출
    fetchVideoList();
  }, []); // 마운트 시에만 호출

  return (
    <div className={styles.videoListContainer}>
      <div className={styles["division-line"]}></div>
      <h1>대표 영상</h1>
      <div className={styles["division-line"]}></div>

      {/* 대표 영상 컴포넌트 또는 대표 영상 목록을 보여주는 UI 추가하기 */}

      <div className={styles["division-line"]}></div>
      <h1>영상 목록</h1>

      {/* 영상 목록을 보여주는 UI 추가 */}
      {videoList.map((video) => (
        <div key={video.id} className={styles.videoListItem}>
          {/* 영상 썸네일 또는 동영상 플레이어 추가 */}
          <p>{video.title}</p>
          {/* 기타 영상 정보 표시 */}
        </div>
      ))}
    </div>
  );
};

export default ProfileVideoList;
