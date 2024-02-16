import React, {useRef, useState} from "react";
import styles from "./VideoUploadModal.module.css";
import videoUploadImage from "../../assets/VideoUpload/VideoUpload.png";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCircleInfo} from "@fortawesome/free-solid-svg-icons";
import videoPostApi from "../../apis/VideoPostApi";
import ApiUtil from "../../apis/ApiUtil";

const VideoUpload = () => {
  // 장르와 악기 카테고리 목록
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

  // 선택 장르와 악기 상태
  const [selectedGenres, setSelectedGenres] = useState([]);
  const [selectedInstruments, setSelectedInstruments] = useState([]);

  // 카테고리 선택 시 처리하는 함수
  const handleCategorySelect = (
    category,
    selectedCategory,
    setSelectedCategory,
  ) => {
    const isSelected = selectedCategory.includes(category);

    if (isSelected) {
      // 이미 선택된 경우 해제
      setSelectedCategory((prevSelected) =>
        prevSelected.filter((item) => item !== category),
      );
    } else {
      // 선택되지 않은 경우 추가
      setSelectedCategory((prevSelected) => [...prevSelected, category]);
    }
  };

  // 장르 선택을 위한 함수
  const handleGenreSelect = (event) => {
    let willCheck = event.target.checked;
    let id = parseInt(event.target.value);

    if (willCheck) {
      setSelectedGenres([...selectedGenres, id]);
      return;
    }
    let filter = selectedGenres.filter((genre) => genre !== id);
    setSelectedGenres(filter);
  };

  // 악기 선택을 위한 함수
  const handleInstrumentSelect = (event) => {
    let willCheck = event.target.checked;
    let id = parseInt(event.target.value);

    if (willCheck) {
      setSelectedInstruments([...selectedInstruments, id]);
      return;
    }
    let filter = selectedInstruments.filter((el) => el !== id);
    setSelectedInstruments(filter);
  };

  const [selectedVideo, setSelectedVideo] = useState(null);
  const [videoDetails, setVideoDetails] = useState({
    title: "" /*제목*/,
    musicTitle: "" /*연주곡 또는 원제목*/,
    artist: "" /*연주곡 원작자*/,
    description: "" /*설명*/,
  });
  const [thumbnail, setThumbnail] = useState(null);
  const [thumbnailPreview, setThumbnailPreview] = useState(null);

  // 파일 입력(input) 엘리먼트에 접근하기 위한 ref
  const fileInputRef = useRef(null); //동영상
  const thumbnailInputRef = useRef(null); // 썸네일 파일 입력(input) 엘리먼트에 접근하기 위한 ref

  const handleIconClick = () => {
    // 파일 입력(input) 엘리먼트 클릭
    fileInputRef.current.click();
  };

  /*썸네일 */
  const handleThumbnailBtnClick = () => {
    if (thumbnailInputRef.current) {
      thumbnailInputRef.current.click();
    }
  };

  const handleVideoChange = (event) => {
    const file = event.target.files[0];
    setSelectedVideo(file);
  };

  const handleThumbnailChange = (event) => {
    const thumbnailFile = event.target.files[0];
    setThumbnail(thumbnailFile);

    // 썸네일 미리보기 생성
    const reader = new FileReader();
    reader.onloadend = () => {
      setThumbnailPreview(reader.result);
    };
    if (thumbnailFile) {
      reader.readAsDataURL(thumbnailFile);
    }
  };

  // 업로드 버튼 누른 후 로직
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [showErrorModal, setShowErrorModal] = useState(false);

  const handleUpload = async () => {
    // Handle video upload, details
    console.log("Video uploaded:", selectedVideo); //동영상
    console.log("Thumbnail uploaded:", thumbnail); //썸네일
    console.log("Video details:", videoDetails); //세부정보
    console.log("Video category genres:", selectedGenres); //검색 카테고리 (장르)
    console.log("Video category instruments:", selectedInstruments); //검색 카테고리 (악기)

    /**🍞🍞🍞🍞🍞🍞🍞🍞🍞🍞🍞🍞🍞🍞여기입니다!!!!!!!🍞🍞🍞🍞🍞🍞🍞🍞🍞🍞🍞🍞🍞🍞🍞🍞🍞🍞 */
    try {
      const thumbnailFormData = new FormData();
      thumbnailFormData.append("file", thumbnail);
      console.log(thumbnailFormData.get("file"));
      const postThumbNailRes =
        await videoPostApi.uploadThumnail(thumbnailFormData);
      console.log(postThumbNailRes);

      const justVideoFormData = new FormData();
      justVideoFormData.append("file", selectedVideo);
      const postJustVideoRes =
        await videoPostApi.uploadVideo(justVideoFormData);
      console.log(postJustVideoRes);

      const formData = new FormData();
      // 멀티파트 파일 형식으로 데이터 추가
      formData.append("videoUrlPath", postJustVideoRes.data.data);
      formData.append("thumbnailUrlPath", postThumbNailRes.data.data);

      // 나머지 필요한 데이터 추가
      formData.append("userId", ApiUtil.getUserIdFromToken());
      formData.append("postTitle", videoDetails.title);
      formData.append("postContent", videoDetails.description);
      selectedGenres.forEach((genre) => formData.append("genreTags[]", genre));
      selectedInstruments.forEach((instrument) =>
          formData.append("instrumentTags[]", instrument),
      );
      formData.append("videoTitle", videoDetails.musicTitle);
      formData.append("videoArtist", videoDetails.artist);
      console.log("비디오 업로드 할거야!");
      // 비디오 업로드 API 호출
      const response = await videoPostApi.uploadVideoPost(formData);
      console.log(response);
      // const response = await videoPostApi.uploadVideo(formData);

      // 업로드 성공시
      setShowSuccessModal(true);
    } catch (error) {
      // 업로드 실패시
      setShowErrorModal(true);
    }
  };

  const closeSuccessModal = () => {
    setShowSuccessModal(false);

    // 성공 모달이 닫힐 때, 동영상 업로드 창으로 돌아가기
    setSelectedVideo(null);
  };

  const closeErrorModal = () => {
    setShowErrorModal(false);
  };

  return (
    <div className={styles.videoUploadContainer}>
      <h2 className={styles.title}>동영상 업로드</h2>
      <hr />

      {selectedVideo ? (
        <div className={styles.uploadSection}>
          <video controls width="400">
            <source src={URL.createObjectURL(selectedVideo)} type="video/mp4" />
            Your browser does not support the video tag.
          </video>
        </div>
      ) : (
        <div className={styles.uploadSection}>
          <label>
            <img
              src={videoUploadImage}
              alt="동영상 업로드"
              className={styles.videoUploadIcon}
              onClick={handleIconClick}
            />
            {/* 파일 입력(input) 엘리먼트 */}
            <input
              type="file"
              accept="video/*"
              onChange={handleVideoChange}
              ref={fileInputRef}
              className={styles.fileInput}
            />
          </label>
          {/* 파일 선택 버튼 */}
          <button className={styles.fileSelectButton} onClick={handleIconClick}>
            동영상 파일 선택
          </button>

          <div className={styles.VideoUploadWarn}>
            <p>
              불법촬영물 게재시 삭제 조치되고 관련 법에 따라 처벌 받을 수
              있습니다.
            </p>
            <p>
              타인의 저작권 또는 개인 정보 보호 권리를 침해해서는 안 됩니다.
            </p>
          </div>
        </div>
      )}

      {selectedVideo && (
        <>
          <hr />
          <div className={styles.videoDetail}>
            <h2 style={{ textAlign: "center" }}>세부정보</h2>
            <div className={styles.videoDetailInputDiv}>
              <div>
                <label>
                  <p>제목 (필수항목)</p>
                  <input
                    className={styles.DetailInputBox}
                    type="text"
                    value={videoDetails.title}
                    onChange={(e) =>
                      setVideoDetails({
                        ...videoDetails,
                        title: e.target.value,
                      })
                    }
                  />
                </label>
              </div>

              <div>
                <label>
                  <p>연주곡 또는 원제목 (필수항목)</p>
                  <input
                    className={styles.DetailInputBox}
                    type="text"
                    value={videoDetails.musicTitle}
                    onChange={(e) =>
                      setVideoDetails({
                        ...videoDetails,
                        musicTitle: e.target.value,
                      })
                    }
                  />
                </label>
              </div>

              <div>
                <label>
                  <p> 연주곡 원작자 (필수항목)</p>
                  <input
                    className={styles.DetailInputBox}
                    type="text"
                    value={videoDetails.artist}
                    onChange={(e) =>
                      setVideoDetails({
                        ...videoDetails,
                        artist: e.target.value,
                      })
                    }
                  />
                </label>
              </div>

              <div>
                <label>
                  <p>설명</p>
                  <textarea
                    className={styles.DetailInputBox}
                    value={videoDetails.description}
                    onChange={(e) =>
                      setVideoDetails({
                        ...videoDetails,
                        description: e.target.value,
                      })
                    }
                  />
                </label>
              </div>
            </div>

            <div className={styles.searchCategory}>
              <h3>
                검색 카테고리
                <FontAwesomeIcon
                  icon={faCircleInfo}
                  className={styles.infomationIcon}
                />
              </h3>

              <p className={styles.searchCategoryText}>
                영상과 관련한 카테고리를 선택해주세요. 선택한 카테고리는
                시청자의 빠른 검색에 도움이 될 것입니다.
              </p>
              {/* 장르 선택 */}
              <div className={styles.categoryDiv}>
                <h4>장르</h4>
                <div className={styles.categoryCheckboxContainer}>
                  {genres.map((genre) => (
                    <label key={genre.id}>
                      <input
                        className={styles.categoryGenre}
                        type="checkbox"
                        value={genre.id}
                        // checked={selectedGenres.includes(genre)}
                        checked={selectedGenres.some(
                          (selectedGenre) => selectedGenre === genre.id,
                        )}
                        onChange={(event) => handleGenreSelect(event)}
                      />
                      {genre.name}
                    </label>
                  ))}
                </div>

                {/* 악기 선택 */}
                <h4>악기</h4>
                <div className={styles.categoryCheckboxContainer}>
                  {instruments.map((instrument) => (
                    <label key={instrument.id}>
                      <input
                        type="checkbox"
                        value={instrument.id}
                        checked={selectedInstruments.some(
                          (selectedInstrument) =>
                            selectedInstrument === instrument.id,
                        )}
                        // checked={selectedInstruments.includes(instrument)}
                        onChange={(event) => handleInstrumentSelect(event)}
                      />
                      {instrument.name}
                    </label>
                  ))}
                </div>
              </div>
            </div>
          </div>

          <hr />

          <div className={styles.uploadSectionThumbnail}>
            <label>
              <h2>썸네일</h2>

              <p> 동영상의 내용을 알려주는 사진을 선택하거나 업로드하세요. </p>
              <p className={styles.ThumbnailInfoText}>
                시청자의 시선을 사로잡을만한 이미지를 사용해 보세요.
              </p>
              <input
                type="file"
                accept="image/*"
                onChange={handleThumbnailChange}
                ref={thumbnailInputRef}
                className={styles.fileInput}
              />
            </label>
            {thumbnailPreview && (
              <img
                src={thumbnailPreview}
                alt="썸네일 미리보기"
                className={styles.thumbnailPreview}
              />
            )}

            {/* 파일 선택 버튼 */}
            <button
              className={styles.fileSelectButton}
              onClick={handleThumbnailBtnClick}
            >
              썸네일 파일 선택
            </button>
          </div>

          <hr />

          <div className={styles.uploadSection}>
            <p>
              불법촬영물 게재시 삭제 조치되고 관련 법에 따라 처벌 받을 수
              있습니다.
            </p>
            <p>
              타인의 저작권 또는 개인 정보 보호 권리를 침해해서는 안 됩니다.
            </p>
            <button className={styles.uploadButton} onClick={handleUpload}>
              업로드
            </button>
          </div>

          {/* 성공 모달 */}
          {showSuccessModal && (
            <div className={`${styles.modal} ${styles.successModal}`}>
              <div
                className={`${styles.modalContent} ${styles.successModalContent}`}
              >
                {/*<span className={styles.closeModal} onClick={closeSuccessModal}>&times;</span>*/}
                <p>업로드가 성공적으로 완료되었습니다.</p>
                <p>프로필에서 확인할 수 있습니다.</p>
                <button
                  className={styles.closeModal}
                  onClick={closeSuccessModal}
                >
                  확인
                </button>
              </div>
            </div>
          )}

          {/* 에러 모달 */}
          {showErrorModal && (
            <div className={`${styles.modal} ${styles.errorModal}`}>
              <div
                className={`${styles.modalContent} ${styles.errorModalContent}`}
              >
                {/*<span className={styles.closeModal} onClick={closeErrorModal}>&times;</span>*/}
                <p>업로드 중 오류가 발생했습니다. 다시 시도해주세요.</p>
                <button className={styles.closeModal} onClick={closeErrorModal}>
                  확인
                </button>
              </div>
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default VideoUpload;
