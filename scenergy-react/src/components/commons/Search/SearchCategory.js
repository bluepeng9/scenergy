import styles from "./SearchCategory.module.css";
import arrowUp from "../../../assets/arrow_up.png";
import arrowDown from "../../../assets/arrow_down.png";
import { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";

const SearchCategory = ({ onSearch }) => {
  const [expanded, setExpanded] = useState(false);
  const [searchInput, setSearchInput] = useState("");
  const [searchSelect, setSearchSelect] = useState({
    genres: [],
    locations: [],
    instruments: [],
  });

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
  const handleExpand = () => {
    setExpanded(!expanded);
  };

  const handleSearchInputChange = (e) => {
    setSearchInput(e.target.value);
  };

  const handleSearchSelectChange = (categoryType, selectedItem) => {
    const isAlreadySelected = searchSelect[categoryType].some(
      (item) => item.id === selectedItem.id,
    );

    if (isAlreadySelected) {
      // 이미 선택된 항목을 배열에서 제거합니다.
      setSearchSelect({
        ...searchSelect,
        [categoryType]: searchSelect[categoryType].filter(
          (item) => item.id !== selectedItem.id,
        ),
      });
    } else {
      // 새로운 항목을 배열에 추가합니다.
      setSearchSelect({
        ...searchSelect,
        [categoryType]: [...searchSelect[categoryType], selectedItem],
      });
    }

    console.log(searchSelect);
  };

  const handleSearchOpen = () => {
    if (onSearch) {
      const mappedSearchParams = {
        gt: searchSelect.genres.map((genre) => genre.id), // 장르 ID 배열
        lt: searchSelect.locations.map((location) => location.id), // 위치 ID 배열
        it: searchSelect.instruments.map((instrument) => instrument.id), // 악기 ID 배열
      };
      onSearch(searchInput, mappedSearchParams);
      setSearchInput("");
      console.log(searchInput);
      console.log(mappedSearchParams);
    }
  };

  const isSelectedStyle = (categoryType, itemId) => {
    return searchSelect[categoryType].some((item) => item.id === itemId)
      ? { backgroundColor: "white", color: "black" }
      : {};
  };

  return (
    <div>
      <div className={styles.FieldInputContainer}>
        <div className={styles.FieldInput}>
          <input
            type="text"
            id="search"
            placeholder="검색어를 입력해주세요."
            value={searchInput}
            onChange={handleSearchInputChange}
          />
          <div className={styles.searchIcon} onClick={handleSearchOpen}>
            <FontAwesomeIcon icon={faMagnifyingGlass} />
          </div>
        </div>
      </div>
      <div className={styles.FieldCategoryGlobal}>
        <div
          className={`${styles.FieldCategory} ${expanded && styles.expanded}`}
        >
          <div className={styles.Container}>
            <div className={styles.CategoryContainer}>
              <div className={styles.Categoryitem}>
                <p>카테고리를 선택해주세요.</p>
                <hr className={styles.hritem} />
              </div>
              <div className={styles.Categoryetc}>
                <img
                  className={styles.arrowImg}
                  onClick={handleExpand}
                  src={expanded ? arrowUp : arrowDown}
                  alt="아래 화살표"
                />
              </div>
            </div>
            {expanded && (
              <div className={styles.CategoryDropdownContainer}>
                <div className={styles.CategoryDropdown}>
                  <div className={styles.categoryName}>장르</div>
                  <div className={styles.CategoryDropdownItem}>
                    {genres.map((genre) => (
                      <div
                        className={styles.CategoryPick}
                        key={genre.id}
                        style={isSelectedStyle("genres", genre.id)}
                        onClick={() =>
                          handleSearchSelectChange("genres", genre)
                        }
                      >
                        {genre.name}
                      </div>
                    ))}
                  </div>
                  <div className={styles.categoryName}>지역</div>
                  <div
                    className={styles.CategoryDropdownLocationItem}
                    id="categoryLocation"
                  >
                    {locations.map((location) => (
                      <div
                        className={styles.CategoryPick}
                        key={location.id}
                        style={isSelectedStyle("locations", location.id)}
                        onClick={() =>
                          handleSearchSelectChange("locations", location)
                        }
                      >
                        {location.name}
                      </div>
                    ))}
                  </div>
                  <div className={styles.categoryName}>악기</div>
                  <div className={styles.CategoryDropdownItem}>
                    {instruments.map((instrument) => (
                      <div
                        className={styles.CategoryPick}
                        key={instrument.id}
                        style={isSelectedStyle("instruments", instrument.id)}
                        onClick={() =>
                          handleSearchSelectChange("instruments", instrument)
                        }
                      >
                        {instrument.name}
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};
export default SearchCategory;
