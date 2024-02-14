import styles from "./SearchDefaultResult.module.css";

const SearchDefaultResult = ({ searchVideoPosts, isLoading }) => {
  return (
    <>
      {isLoading
        ? Array.from({ length: 6 }).map((_, index) => (
            <div key={index} className={styles.searchPageVideoResult}>
              <div
                className={`${styles.searchPageVideoHeader} ${styles.skeleton}`}
              >
                <div className={styles.searchPageVideoSkeleton}></div>
                <div
                  className={styles.searchPageVideoInfoSkeleton}
                  style={{ width: "70%" }}
                ></div>
              </div>
              <div className={styles.searchPageVideoItem}>
                <div className={styles.searchPageVideoSkeleton}></div>
              </div>
              <div className={styles.searchPageVideoInfoContainer}>
                <div
                  className={styles.searchPageVideoInfoSkeleton}
                  style={{ width: "90%" }}
                ></div>
                <div
                  className={styles.searchPageVideoInfoSkeleton}
                  style={{ width: "80%" }}
                ></div>
              </div>
            </div>
          ))
        : searchVideoPosts.map((result) => (
            <div>
              <div className={styles.searchPageVideoResult}>
                <div className={styles.searchPageVideoHeader}>
                  <div>이미지</div>
                  <div>유저 닉네임</div>
                </div>
                <div className={styles.searchPageVideoItem}>
                  <div className={styles.searchPageVideo}></div>
                </div>
                <div className={styles.searchPageVideoInfoContainer}>
                  <h3>영상 제목</h3>
                  <p>곡 정보</p>
                </div>
              </div>
              <div className={styles.searchPageVideoResult}>
                <div className={styles.searchPageVideoHeader}>
                  <div>이미지</div>
                  <div>유저 닉네임</div>
                </div>
                <div className={styles.searchPageVideoItem}>
                  <div className={styles.searchPageVideo}></div>
                </div>
                <div className={styles.searchPageVideoInfoContainer}>
                  <h3>영상 제목</h3>
                  <p>곡 정보</p>
                </div>
              </div>
              <div className={styles.searchPageVideoResult}>
                <div className={styles.searchPageVideoHeader}>
                  <div>이미지</div>
                  <div>유저 닉네임</div>
                </div>
                <div className={styles.searchPageVideoItem}>
                  <div className={styles.searchPageVideo}></div>
                </div>
                <div className={styles.searchPageVideoInfoContainer}>
                  <h3>영상 제목</h3>
                  <p>곡 정보</p>
                </div>
              </div>
            </div>
          ))}
    </>
  );
};

export default SearchDefaultResult;
