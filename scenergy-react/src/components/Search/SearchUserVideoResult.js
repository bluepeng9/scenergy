import styles from "./SearchUserVideoResult.module.css";
import basic_img from "../../assets/basic_profile.png";

const SearchUserVideoResult = ({ searchUsers, searchQuery }) => {
  console.log(searchUsers);
  return (
    <div className={styles.searchPageUserContainer}>
      <h3>{searchQuery ? `'${searchQuery}' 관련 검색 결과` : "유저추천"}</h3>
      <div className={styles.searchPageUserResultContainer}>
        {searchUsers.map((result) => (
          <div key={result.userId} className={styles.searchPageUserResult}>
            <div className={styles.searchPageUserResultItem}>
              <a href={`/profile/${result.userId}`}>
                <img
                  className={styles.searchUserImg}
                  src={result.url ? result.url : basic_img}
                  alt="프로필 이미지"
                />
                <span>{result.nickName}</span>
              </a>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
export default SearchUserVideoResult;
