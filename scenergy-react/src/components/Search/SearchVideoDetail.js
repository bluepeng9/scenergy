import basic_profile from "../../assets/basic_profile.png";
import styles from "./SearchVideoDetail.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCompactDisc,
  faHeadphones,
  faMicrophoneLines,
  faMusic,
  faTimes,
} from "@fortawesome/free-solid-svg-icons";
import { faPaperPlane, faThumbsUp } from "@fortawesome/free-regular-svg-icons";
const SearchVideoDetail = ({ onClick, searchVideoPosts }) => {
  const genres = [
    { id: 1, name: "팝" },
    { id: 2, name: "발라드" },
    { id: 3, name: "인디" },
    { id: 4, name: "힙합" },
    { id: 5, name: "락" },
    { id: 6, name: "R&B" },
    { id: 7, name: "재즈" },
    { id: 8, name: "클래식" },
    { id: 9, name: "그 외" },
  ];
  const instruments = [
    { id: 1, name: "기타" },
    { id: 2, name: "베이스" },
    { id: 3, name: "드럼" },
    { id: 4, name: "키보드" },
    { id: 5, name: "보컬" },
    { id: 6, name: "그 외" },
  ];

  const locations = [
    { id: 1, name: "서울" },
    { id: 2, name: "인천" },
    { id: 3, name: "대전" },
    { id: 4, name: "부산" },
    { id: 5, name: "울산" },
    { id: 6, name: "대구" },
    { id: 7, name: "광주" },
    { id: 8, name: "경기" },
    { id: 9, name: "강원" },
    { id: 10, name: "충북" },
    { id: 11, name: "충남" },
    { id: 12, name: "전북" },
    { id: 13, name: "전남" },
    { id: 14, name: "경북" },
    { id: 15, name: "경남" },
    { id: 16, name: "세종" },
    { id: 17, name: "제주" },
  ];

  const handleModalChange = () => {
    onClick();
  };

  const handleContainerClick = (e) => {
    e.stopPropagation();
  };
  return (
    <div className={styles.videoDetailBack} onClick={handleModalChange}>
      <div
        className={styles.videoDetailContainer}
        onClick={handleContainerClick}
      >
        <div className={styles.videoDetailLeft}>
          <div className={styles.videoDetailHeader}>
            <div className={styles.videoDetailProfile}>
              <img src={basic_profile} />
              <p>{searchVideoPosts?.writer}</p>
            </div>
            <button
              className={styles.modalCloseButton}
              onClick={handleModalChange}
            >
              <FontAwesomeIcon icon={faTimes} />
            </button>
          </div>
          <div className={styles.videoDetailvideo}>
            <video controls width="350">
              <source
                src={searchVideoPosts?.video?.videoUrlPath}
                type="video/mp4"
              />
            </video>
          </div>
          <div className={styles.videoDetailFooter}>
            <div className={styles.videoDetailBtn}>
              <FontAwesomeIcon icon={faThumbsUp} />
              <FontAwesomeIcon icon={faPaperPlane} />
            </div>
            <div className={styles.videoDetailLike}>
              <p>좋아요</p>
              {/*<p>get좋아요cntㅎㅐ서 출력출력</p>*/}
            </div>
          </div>
        </div>
        <div className={styles.videoDetailContentContainer}>
          <div className={styles.videoDetailContentTitle}>
            <div className={styles.videoDetailTitle}>
              <p>{searchVideoPosts?.title}</p>
            </div>
            <div className={styles.videoDetatilContent}>
              <p>{searchVideoPosts?.content}</p>
            </div>
          </div>
          <div className={styles.videoDetailRight}>
            <div className={styles.videoDetailTagsContainer}>
              <div className={styles.videoDetailGenre}>
                <p>장르</p>
                <div className={styles.videoDetailTagItem}>
                  {searchVideoPosts?.genreTags.map((tag) => {
                    const genre = genres.find((g) => g.id === tag.genreTagId);
                    return (
                      <span key={tag.genreTagId} className={styles.genreTag}>
                        {genre ? genre.name : ""}
                      </span>
                    );
                  })}
                </div>
              </div>
              <div className={styles.videoDetailInst}>
                <p>악기</p>
                <div className={styles.videoDetailTagItem}>
                  {searchVideoPosts?.instrumentTags.map((tag) => {
                    const instrument = instruments.find(
                      (i) => i.id === tag.instrumentTagId,
                    );
                    return (
                      <span key={tag.genreTagId} className={styles.genreTag}>
                        {instrument ? instrument.name : ""}
                      </span>
                    );
                  })}
                </div>
              </div>
            </div>
            <div className={styles.videoDetailMusicInfoContainer}>
              <div className={styles.videoDetailMusicInfo}>
                <p>곡 정보</p>
              </div>
              <div className={styles.videoDetailInfoFooter}>
                <div className={styles.videoDetailArtist}>
                  <FontAwesomeIcon icon={faHeadphones} />
                  <p>{searchVideoPosts?.video.artist}</p>
                </div>
                <div className={styles.videoDetailMusic}>
                  <FontAwesomeIcon icon={faCompactDisc} />
                  <p>{searchVideoPosts?.video.musicTitle}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SearchVideoDetail;
