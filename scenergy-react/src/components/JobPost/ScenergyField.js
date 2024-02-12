import styles from "./ScenergyField.module.css";
import SearchCategory from "../commons/Search/SearchCategory";
import ScenergyList from "./ScenergyList";
import Dialog from "../commons/Dialog/Dialog";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFeather, faTimes } from "@fortawesome/free-solid-svg-icons";
import { useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import jobPostApi from "../../apis/JobPost/JobPostApi";
import { useScenergyPost } from "../../contexts/ScenergyPostContext";
import searchApi from "../../apis/SearchApi";
import ApiUtil from "../../apis/ApiUtil";

const ScenergyField = ({ onOpenModal }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [endDate, setEndDate] = useState(new Date());
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [peopleRecruited, setPeopleRecruited] = useState(1);
  const [selectedGenre, setSelectedGenre] = useState([]);
  const [selectedInstrument, setSelectedInstrument] = useState([]);
  const [selectedLocation, setSelectedLocation] = useState([]);
  const [refreshList, setRefreshList] = useState(false);
  const [selectedExp, setSelectedExp] = useState("전체");
  const { addScenergyPost } = useScenergyPost();
  const userId = ApiUtil.getUserIdFromToken();

  const handleOpenChange = () => {
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setSelectedInstrument([]);
    setSelectedGenre([]);
    setSelectedLocation([]);
    setPeopleRecruited(1);
    setContent("");
    setTitle("");
    setEndDate(new Date());
  };

  const handleSearch = async (searchTerm) => {
    try {
      const data = { name: searchTerm, gt: [], it: [], lt: [] };
      const response = await searchApi.searchJobPost(data);
      console.log("시너지 검색 완", response);
    } catch (error) {
      console.error("시너지 검색 실패", error);
    }
  };

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

  const handleGenreChange = (e) => {
    const value = parseInt(e.target.value, 10);
    if (!selectedGenre.includes(value)) {
      setSelectedGenre([...selectedGenre, value]);
    }
    console.log(selectedGenre);
  };
  const handleInstChange = (e) => {
    const value = parseInt(e.target.value, 10);
    if (!selectedInstrument.includes(value)) {
      setSelectedInstrument([...selectedInstrument, value]);
    }
    console.log(selectedInstrument);
  };
  const handleLocationChange = (e) => {
    const value = parseInt(e.target.value, 10);
    if (!selectedLocation.includes(value)) {
      setSelectedLocation([...selectedLocation, value]);
    }
    console.log(selectedLocation);
  };

  const handleSubmit = () => {
    const postData = {
      userId: 2,
      title,
      content,
      expirationDate: endDate,
      peopleRecruited,
      bookmark: 0,
      isActive: "active",
      genreTags: selectedGenre,
      instrumentTags: selectedInstrument,
      locationTags: selectedLocation,
    };

    jobPostApi
      .createJobPost(postData)
      .then((response) => {
        console.log("글작성 성공", response);
        addScenergyPost(response);
        handleCloseModal();
        setRefreshList((prev) => !prev);
      })
      .catch((error) => {
        console.error("글 작성 실패", error);
      });
    console.log(postData);
  };

  return (
    <div className={styles.FieldGlobal}>
      <div className={styles.FieldHeader}>
        <div className={styles.FieldTitle}>
          <h2>시너지</h2>
        </div>
      </div>
      <div className={styles.FieldSearch}>
        <SearchCategory onSearch={handleSearch} />
      </div>
      <div className={styles.FieldItemsGlobal}>
        <div className={styles.FieldWrite}>
          <button className={styles.FieldWriteBtn} onClick={handleOpenChange}>
            <FontAwesomeIcon icon={faFeather} />
            <p>글쓰기</p>
          </button>
          {isModalOpen && (
            <Dialog title="글 작성하기" onClose={handleCloseModal}>
              <div className={styles.scenergyModalGlobal}>
                <div className={styles.scenergyPostHeader}>
                  <p>제목</p>
                  <input
                    type="text"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                  />
                </div>
                <div className={styles.scenergyPostSelect}>
                  <div className={styles.scenergyPostItem}>
                    <div className={styles.scenergyPost}>
                      <div className={styles.scenergyOption}>
                        <p>지역</p>
                        <select onChange={handleLocationChange}>
                          {locations.map((location) => (
                            <option key={location.id} value={location.id}>
                              {location.name}
                            </option>
                          ))}
                        </select>
                      </div>
                      <div className={styles.selectedItem}>
                        {selectedLocation.map((id) => {
                          const location = locations.find(
                            (location) => location.id === id,
                          );
                          return (
                            <div
                              key={id}
                              className={styles.selectedTag}
                              onClick={() =>
                                setSelectedLocation(
                                  selectedLocation.filter(
                                    (selectedId) => selectedId !== id,
                                  ),
                                )
                              }
                            >
                              {location.name}&nbsp;
                              <FontAwesomeIcon icon={faTimes} />
                            </div>
                          );
                        })}
                      </div>
                    </div>
                  </div>
                  <div className={styles.scenergyPostItem}>
                    <div className={styles.scenergyPost}>
                      <div className={styles.scenergyOption}>
                        <p>장르</p>
                        <select onChange={handleGenreChange}>
                          {genres.map((genre) => (
                            <option key={genre.id} value={genre.id}>
                              {genre.name}
                            </option>
                          ))}
                        </select>
                      </div>
                      <div className={styles.selectedItem}>
                        {selectedGenre.map((id) => {
                          const genre = genres.find((genre) => genre.id === id);
                          return (
                            <div
                              key={id}
                              className={styles.selectedTag}
                              onClick={() =>
                                setSelectedGenre(
                                  selectedGenre.filter(
                                    (selectedId) => selectedId !== id,
                                  ),
                                )
                              }
                            >
                              {genre.name}
                              <FontAwesomeIcon icon={faTimes} />
                            </div>
                          );
                        })}
                      </div>
                    </div>
                  </div>
                  <div className={styles.scenergyPostItem}>
                    <div className={styles.scenergyPost}>
                      <div className={styles.scenergyOption}>
                        <p>악기</p>
                        <select onChange={handleInstChange}>
                          {instruments.map((instrument) => (
                            <option key={instrument.id} value={instrument.id}>
                              {instrument.name}
                            </option>
                          ))}
                        </select>
                      </div>
                      <div className={styles.selectedItem}>
                        {selectedInstrument.map((id) => {
                          const instrument = instruments.find(
                            (instrument) => instrument.id === id,
                          );
                          return (
                            <div
                              key={id}
                              className={styles.selectedTag}
                              onClick={() =>
                                setSelectedInstrument(
                                  selectedInstrument.filter(
                                    (selectedId) => selectedId !== id,
                                  ),
                                )
                              }
                            >
                              {instrument.name}
                              <FontAwesomeIcon icon={faTimes} />
                            </div>
                          );
                        })}
                      </div>
                    </div>
                  </div>
                </div>
                <div className={styles.scenergyPostNumber}>
                  <div className={styles.scenergyPostItem}>
                    <div className={styles.scenergyPostExp}>
                      <p>모집 기간</p>
                      <DatePicker
                        className={styles.customDatePicker}
                        selected={endDate}
                        dateFormat="yyyy.MM.dd"
                        onChange={(date) => setEndDate(date)}
                        minDate={new Date()}
                        maxDate={
                          new Date(
                            new Date().setFullYear(
                              new Date().getFullYear() + 1,
                            ),
                          )
                        }
                      />
                    </div>
                  </div>
                  <div>
                    <p>모집 인원</p>
                    <input
                      type="number"
                      value={peopleRecruited}
                      min="1"
                      onChange={(e) =>
                        setPeopleRecruited(parseInt(e.target.value))
                      }
                    />
                  </div>
                </div>
                <div className={styles.scenergyContentContainer}>
                  <div className={styles.scenergyContent}>
                    <p>내용 작성</p>
                    <textarea
                      value={content}
                      onChange={(e) => setContent(e.target.value)}
                    />
                  </div>
                </div>
                <div className={styles.scenergyPostBtn}>
                  <button onClick={handleSubmit}>작성하기</button>
                </div>
              </div>
            </Dialog>
          )}
        </div>
        <div className={styles.FieldSelectContainer}>
          <select className={styles.FiledStateSelect}>
            <option>전체</option>
            <option>모집</option>
            <option>종료</option>
          </select>
        </div>
      </div>
      <div className={styles.FieldList}>
        <ScenergyList onOpenModal={onOpenModal} refresh={refreshList} />
      </div>
    </div>
  );
};
export default ScenergyField;
