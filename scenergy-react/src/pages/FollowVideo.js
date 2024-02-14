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
        const videos = response.data.data.list.map((item) => item.video);

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
      {followingVideos.map((video) => (
        <div key={video.id} className={styles.videoContain}>
          <h2>{video.title}</h2>

          {/* 동영상 재생을 위한 비디오 태그 */}
          <video controls width="500">
            <source src={video.videoUrlPath} type="video/mp4" />
            Your browser does not support the video tag.
          </video>

          {/*<video controls width="400">*/}
          {/*  <source type={"video/mp4"} />*/}
          {/*</video>*/}

          {/* 동영상과 관련된 내용을 추가할 수 있습니다. */}
          {/*<p>{video.description}</p>*/}
          {/*<p>장르: {video.genreTags.join(", ")}</p>*/}
          {/*<p>악기: {video.instrumentTags.join(", ")}</p>*/}
        </div>
      ))}
    </div>
  );
};

export default FollowVideo;

// import styles from "./FollowVideo.module.css";
// const FollowVideo = () => {
//   const dummyPosts = Array.from({ length: 10 }, (_, index) => ({
//     id: index,
//     title: `팔로우한 사용자의 게시물 ${index + 1}`,
//   }));
//   return (
//     <div className={styles.followVideoContainer}>
//       {dummyPosts.map((post) => (
//         <div key={post.id} className={styles.videoContain}>
//           <h2>{post.title}</h2>
//           {/* 게시물 내용이나 이미지 등을 여기에 추가 */}
//         </div>
//       ))}
//     </div>
//   );
// };
//
// export default FollowVideo;
