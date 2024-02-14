import styles from "./SearchPage.module.css";
import NavBar from "../components/commons/Navbar/Navbar";
import SearchCategory from "../components/commons/Search/SearchCategory";
import { useEffect, useState } from "react";
import SearchDefaultResult from "../components/Search/SearchDefaultResult";
import videoPostApi from "../apis/VideoPostApi";
import searchApi from "../apis/SearchApi";
import SearchUserResult from "../components/Search/SearchUserVideoResult";
import SearchUserVideoResult from "../components/Search/SearchUserVideoResult";

const SearchPage = () => {
  const [searchInput, setSearchInput] = useState("");
  const [searchUsers, setSearchUsers] = useState([]);
  const [searchVideoPosts, setSearchVideoPost] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [isSearched, setIsSearched] = useState(false);
  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true); // 데이터 요청 전에 로딩 상태를 true로 설정
      try {
        const responseVideoPosts = await videoPostApi.searchVideoPost({
          word: "",
        });
        setSearchVideoPost(responseVideoPosts.data.data.list);
      } catch (error) {
        console.error("데이터 로딩 중 에러 발생:", error);
      } finally {
        setIsLoading(false); // 데이터 요청 후에 로딩 상태를 false로 설정
      }
    };

    fetchData();
  }, []);

  const handleSearch = async (input, { genres, locations, instruments }) => {
    setIsLoading(true);
    setIsSearched(true);
    try {
      const responseVideoPosts = await videoPostApi.searchVideoPost({
        word: input,
        genreTags: genres.map((g) => g.id),
        locationTags: locations.map((l) => l.id),
        instrumentTags: instruments.map((i) => i.id),
      });
      setSearchVideoPost(responseVideoPosts.data.data.list);
    } catch (error) {
      console.error("검색 에러 데이터 로드 에러", error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <>
      <div className={styles.searchPageGlobal}>
        <div className={styles.searchPageNav}>
          <NavBar />
        </div>
        <div className={styles.searchPageBodyContainer}>
          <SearchCategory onSearch={handleSearch} />
          <div className={styles.searchPageResult}>
            {isSearched ? (
              // 검색 결과 렌더링
              <>
                <SearchUserVideoResult searchUsers={searchUsers} />
                <SearchDefaultResult
                  searchVideoPosts={searchVideoPosts}
                  isLoading={isLoading}
                />
              </>
            ) : (
              // 초기 전체 영상 목록 렌더링
              <SearchDefaultResult
                searchVideoPosts={searchVideoPosts}
                isLoading={isLoading}
              />
            )}
          </div>
        </div>
      </div>
    </>
  );
};
export default SearchPage;
