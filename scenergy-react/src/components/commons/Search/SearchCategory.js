import styles from "./SearchCategory.module.css";
import arrowUp from "../../../assets/arrow_up.png";
import arrowDown from "../../../assets/arrow_down.png";
import { useState } from "react";

const SearchCategory = () => {
  const [expanded, setExpanded] = useState(false);
  const handleExpand = () => {
    setExpanded(!expanded);
  };

  return (
    <div>
      <div className={styles.FieldInput}>
        <input type="text" id="search" placeholder="검색어를 입력해주세요." />
      </div>
      <div className={styles.FieldCategoryGlobal}>
        <button
          className={`${styles.FieldCategory} ${expanded && styles.expanded}`}
          onClick={handleExpand}
        >
          <div className={styles.Container}>
            <div className={styles.CategoryContainer}>
              <div className={styles.Categoryitem}>
                <p>카테고리</p>
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
        </button>
      </div>
    </div>
  );
};
export default SearchCategory;
