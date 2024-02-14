import styles from "./SearchUserVideoResult.module.css";

const SearchUserVideoResult = ({ searchUsers }) => {
  return (
    <div className={styles.searchPageUserContainer}>
      <h3>관련 사용자</h3>
      <div className={styles.searchPageUserResultContainer}>
        {searchUsers.map((result, index) => (
          <div className={styles.searchPageUserResult}>
            <a key={index} href={`/profile/${result.userId}`}>
              <span>
                {result.url} {result.nickName}
              </span>
            </a>
          </div>
        ))}
      </div>
    </div>
  );
};
export default SearchUserVideoResult;
