import styles from "./FollowVideo.module.css";
const FollowVideo = () => {
  const dummyPosts = Array.from({ length: 10 }, (_, index) => ({
    id: index,
    title: `팔로우한 사용자의 게시물 ${index + 1}`,
  }));
  return (
    <div className={styles.followVideoContainer}>
      {dummyPosts.map((post) => (
        <div key={post.id} className={styles.videoContain}>
          <h2>{post.title}</h2>
          {/* 게시물 내용이나 이미지 등을 여기에 추가 */}
        </div>
      ))}
    </div>
  );
};

export default FollowVideo;
