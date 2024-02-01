import React, {useState, useRef} from 'react';
import styles from './VideoUploadModal.module.css';
import videoUploadImage from '../../assets/VideoUpload/VideoUpload.png';

const VideoUpload = () => {
    // 장르와 악기 카테고리 목록
    const genres = ['팝', '발라드', '인디', '힙합', '락', 'R&B', '재즈', '클래식', '그 외'];
    const instruments = ['기타', '베이스', '드럼', '키보드', '보컬', '그 외'];

    // 선택된 장르와 악기 상태
    const [selectedGenres, setSelectedGenres] = useState([]);
    const [selectedInstruments, setSelectedInstruments] = useState([]);

    // 카테고리 선택 시 처리하는 함수
    const handleCategorySelect = (category, selectedCategory, setSelectedCategory) => {
        const isSelected = selectedCategory.includes(category);

        if (isSelected) {
            // 이미 선택된 경우 해제
            setSelectedCategory((prevSelected) =>
                prevSelected.filter((item) => item !== category)
            );
        } else {
            // 선택되지 않은 경우 추가
            setSelectedCategory((prevSelected) => [...prevSelected, category]);
        }
    };

// 장르 선택을 위한 함수
    const handleGenreSelect = (genre) => {
        handleCategorySelect(genre, selectedGenres, setSelectedGenres);
    };

// 악기 선택을 위한 함수
    const handleInstrumentSelect = (instrument) => {
        handleCategorySelect(instrument, selectedInstruments, setSelectedInstruments);
    };


    const [selectedVideo, setSelectedVideo] = useState(null);
    const [videoDetails, setVideoDetails] = useState({
        title: '', /*제목*/
        musicTitle: '', /*연주곡 또는 원제목*/
        artist: '', /*연주곡 원작자*/
        description: '', /*설명*/
    });
    const [thumbnail, setThumbnail] = useState(null);
    const [thumbnailPreview, setThumbnailPreview] = useState(null);

    // 파일 입력(input) 엘리먼트에 접근하기 위한 ref
    const fileInputRef = useRef(null);

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

    const thumbnailInputRef = useRef(null);

    const handleUpload = () => {
        // TODO: Handle video upload and details submission logic
        console.log('Video uploaded:', selectedVideo); //동영상
        console.log('Thumbnail uploaded:', thumbnail); //썸네일
        console.log('Video details:', videoDetails); //세부정보
        console.log('Video category genres:', selectedGenres); //검색 카테고리 (장르)
        console.log('Video category instruments:', selectedInstruments); //검색 카테고리 (악기)
        // 서버와 통신하고, 동영상 업로드 및 세부 정보 저장하는 로직 추가하기
    };


    return (
        <div className={styles.videoUploadContainer}>
            <h2>동영상 업로드</h2>
            <hr/>

            {selectedVideo ? (
                <div className={styles.uploadSection}>
                    <video controls width="400">
                        <source src={URL.createObjectURL(selectedVideo)} type="video/mp4"/>
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
                        <p>불법촬영물 게재시 삭제 조치되고 관련 법에 따라 처벌 받을 수 있습니다.</p>
                        <p>타인의 저작권 또는 개인 정보 보호 권리를 침해해서는 안 됩니다.</p>
                    </div>
                </div>
            )}

            {selectedVideo && (
                <>
                    <div className={styles.videoDetail}>
                        <h2 style={{textAlign: 'center'}}>세부정보</h2>
                        <div>
                            <label>
                                <p>제목 (필수항목)</p>
                                <input
                                    type="text"
                                    value={videoDetails.title}
                                    onChange={(e) => setVideoDetails({...videoDetails, title: e.target.value})}
                                />
                            </label>
                        </div>

                        <div>
                            <label>
                                <p>연주곡 또는 원제목 (필수항목)</p>
                                <input
                                    type="text"
                                    value={videoDetails.musicTitle}
                                    onChange={(e) => setVideoDetails({...videoDetails, musicTitle: e.target.value})}
                                />
                            </label>
                        </div>

                        <div>
                            <label>
                                <p> 연주곡 원작자 (필수항목)</p>
                                <input
                                    type="text"
                                    value={videoDetails.artist}
                                    onChange={(e) => setVideoDetails({...videoDetails, artist: e.target.value})}
                                />
                            </label>
                        </div>

                        <div>
                            <label>
                                <p>설명</p>
                                <textarea
                                    value={videoDetails.description}
                                    onChange={(e) => setVideoDetails({...videoDetails, description: e.target.value})}
                                />
                            </label>
                        </div>


                        <div className={styles.searchCategory}>
                            <h3>검색 카테고리</h3>

                            <p>영상과 관련한 카테고리를 선택해주세요. 선택한 카테고리는 시청자의 빠른 검색에 도움이 될 것입니다.</p>
                            {/* 장르 선택 */}
                            <p>장르</p>
                            <div className={styles.categoryCheckboxContainer}>
                                {genres.map((genre) => (
                                    <label key={genre}>
                                        <input
                                            type="checkbox"
                                            value={genre}
                                            checked={selectedGenres.includes(genre)}
                                            onChange={() => handleGenreSelect(genre)}
                                        />
                                        {genre}
                                    </label>
                                ))}
                            </div>

                            {/* 악기 선택 */}
                            <p>악기</p>
                            <div className={styles.categoryCheckboxContainer}>
                                {instruments.map((instrument) => (
                                    <label key={instrument}>
                                        <input
                                            type="checkbox"
                                            value={instrument}
                                            checked={selectedInstruments.includes(instrument)}
                                            onChange={() => handleInstrumentSelect(instrument)}
                                        />
                                        {instrument}
                                    </label>
                                ))}
                            </div>

                        </div>

                    </div>


                    <div className={styles.uploadSection}>
                        <label>
                            <h2>썸네일</h2>
                            <p> 동영상의 내용을 알려주는 사진을 선택하거나 업로드하세요. 시청자의 시선을 사로잡을만한 이미지를 사용해 보세요.</p>
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
                        <button className={styles.fileSelectButton} onClick={handleThumbnailBtnClick}>
                            썸네일 파일 선택
                        </button>
                    </div>

                    <div className={styles.uploadSection}>
                        <h2>재생목록</h2>
                        <p>일반 영상목록에 업로드가 됩니다. 대표영상으로 지정하고 싶다면, '프로필 > 대표영상편집'에서 설정할 수 있습니다.</p>
                    </div>

                    <div className={styles.uploadSection}>
                        <p>불법촬영물 게재시 삭제 조치되고 관련 법에 따라 처벌 받을 수 있습니다.</p>
                        <p>타인의 저작권 또는 개인 정보 보호 권리를 침해해서는 안 됩니다.</p>
                        <button className={styles.uploadButton} onClick={handleUpload}>
                            업로드
                        </button>
                    </div>
                </>
            )}
        </div>
    );
};

export default VideoUpload;
