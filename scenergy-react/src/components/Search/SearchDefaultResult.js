import styles from "./SearchDefaultResult.module.css";
import basic_profile from "../../assets/basic_profile.png";
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
            <div key={result.video.id}>
              <div className={styles.searchPageVideoResult}>
                <div className={styles.searchPageVideoHeader}>
                  {!result.url ? (
                    <img src={basic_profile} className={styles.userImg} />
                  ) : (
                    <img src={result.url} className={styles.userImg} />
                  )}
                  <div>{result.writer}</div>
                </div>
                <div className={styles.searchPageVideoItem}>
                  <img
                    src={result.video.thumbnailUrlPath}
                    className={styles.searchPageVideo}
                  />
                </div>
                <div className={styles.searchPageVideoInfoContainer}>
                  <h3>{result.content}</h3>
                  <p>{result.video.musicTitle}</p>
                </div>
              </div>
            </div>
          ))}
    </>
  );
};

export default SearchDefaultResult;
