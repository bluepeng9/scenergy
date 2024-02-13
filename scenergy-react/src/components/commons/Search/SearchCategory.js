import styles from "./SearchCategory.module.css";
import arrowUp from "../../../assets/arrow_up.png";
import arrowDown from "../../../assets/arrow_down.png";
import {useState} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faMagnifyingGlass} from "@fortawesome/free-solid-svg-icons";

const SearchCategory = ({ onSearch }) => {
  const [expanded, setExpanded] = useState(false);
  const [searchInput, setSearchInput] = useState("");
  const handleExpand = () => {
    setExpanded(!expanded);
  };

  const handleSearchInputChange = (e) => {
    setSearchInput(e.target.value);
  };

  const handleSearchOpen = () => {
    if (onSearch) {
      onSearch(searchInput);
      setSearchInput("");
    }
    setSearchInput("");
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
          onClick={handleExpand}
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
                  src={expanded ? arrowUp : arrowDown}
                  alt="아래 화살표"
                />
              </div>
            </div>
            {expanded && (
              <div className={styles.CategoryDropdownContainer}>
                <div className={styles.CategoryDropdown}>
                  <div className={styles.CategoryDropdownItem}>지역</div>
                  <div className={styles.CategoryDropdownItem}>장르</div>
                  <div className={styles.CategoryDropdownItem}>악기</div>
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
