import React, { useState, useEffect } from "react";
import styles from "./FollowVideo.module.css";
import videoPostApi from "../apis/VideoPostApi";
import ApiUtil from "../apis/ApiUtil";

const FollowVideo = () => {
  // API에서 받아온 영상 데이터를 저장할 상태
  console.log("홈화면 1");
  const [followingVideos, setFollowingVideos] = useState([]);

  useEffect(() => {
    // API 호출 함수를 정의
    const fetchFollowingVideos = async () => {
      try {
        // 사용자 ID를 적절한 값으로 교체
        console.log("사용자 ID 전");
        const userId = ApiUtil.getUserIdFromToken();
        console.log("유저 아이디 : " + userId);
        const response = await videoPostApi.listFollowingVideoPosts({ userId });

        // 정확한 응답 데이터 구조에 따라 수정
        const videos = response.data.data.list.map((item) => item);
        console.log("videos : " + videos.title);

        // 상태 업데이트
        setFollowingVideos(videos);
      } catch (error) {
        console.error("영상 데이터를 불러오는 중 에러 발생:", error);
      }
    };

    // 함수 호출
    fetchFollowingVideos();
  }, []); // 두 번째 매개변수로 빈 배열을 전달하여 컴포넌트가 마운트될 때 한 번만 실행

  return (
    <div className={styles.followVideoContainer}>
      {/* 영상 데이터를 반복하여 화면에 렌더링 */}
      {followingVideos.map((item) => (
        <div key={item.id} className={styles.videoContain}>
          <div className={styles.videoDiv}>
            {/* 왼쪽에 동영상 */}
            <div className={styles.videoContent}>
              <video controls width="450">
                <source src={item.video.videoUrlPath} type="video/mp4" />
                Your browser does not support the video tag.
              </video>
            </div>

            {/* 오른쪽에 영상 정보 */}
            <div className={styles.videoInfo}>
              <h2>{item.title}</h2> {/*제목*/}
              <hr />
              <p>{item.content}</p> {/*상세내용*/}
              <h2 className={styles.videoHr}>곡 정보</h2>
              <hr />
              <p>{item.video.artist}</p>
              <p>{item.video.musicTitle}</p>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};

export default FollowVideo;
