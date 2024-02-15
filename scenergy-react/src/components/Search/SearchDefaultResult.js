import styles from "./SearchDefaultResult.module.css";
import basic_profile from "../../assets/basic_profile.png";
import { Link } from "react-router-dom";
import { useState } from "react";
import SearchVideoDetail from "./SearchVideoDetail";
const SearchDefaultResult = ({ searchVideoPosts, isLoading }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedVideoPost, setSelectedVideoPost] = useState(null);

  const handleModalOpen = (videoPost) => {
    setSelectedVideoPost(videoPost);
    setIsModalOpen(true);
  };

  return (
    <>
      {isLoading ? (
        <div className={styles.searchPageVideoResultContainer}>
          {Array.from({ length: 6 }).map((_, index) => (
            <div key={index} className={styles.searchPageVideoResult}>
              <div
                className={`${styles.searchPageVideoHeader} ${styles.skeleton}`}
              ></div>
              <div
                className={`${styles.searchPageVideoItem}  ${styles.skeleton}`}
              >
                <div
                  className={`${styles.searchPageVideoSkeleton} ${styles.skeleton}`}
                ></div>
              </div>
              <div className={styles.searchPageVideoInfoContainer}>
                <div
                  className={`${styles.searchPageVideoInfoSkeleton} ${styles.skeleton}`}
                ></div>
                <div
                  className={`${styles.searchPageVideoInfoSkeleton} ${styles.skeleton}`}
                ></div>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <div className={styles.searchPageVideoResultContainer}>
          {searchVideoPosts.map((result) => (
            <div key={result.video.id}>
              <div className={styles.searchPageVideoResult}>
                <Link to={`/profile/${result.userId}`}>
                  <div className={styles.searchPageVideoHeader}>
                    {!result.url ? (
                      <img src={basic_profile} className={styles.userImg} />
                    ) : (
                      <img src={result.url} className={styles.userImg} />
                    )}
                    <div>{result.writer}</div>
                  </div>
                </Link>
                <div className={styles.searchPageVideoItem}>
                  <img
                    src={result.video.thumbnailUrlPath}
                    className={styles.searchPageVideo}
                    onClick={() => handleModalOpen(result)}
                  />
                </div>
                {isModalOpen && (
                  <SearchVideoDetail
                    onClick={() => setIsModalOpen(false)}
                    searchVideoPosts={selectedVideoPost}
                  />
                )}
                <div className={styles.searchPageVideoInfoContainer}>
                  <h3>{result.title}</h3>
                  <p>{result.video.musicTitle}</p>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </>
  );
};

export default SearchDefaultResult;
