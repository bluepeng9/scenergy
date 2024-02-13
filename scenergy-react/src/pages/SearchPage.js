import styles from "./SearchPage.module.css";
import NavBar from "../components/commons/Navbar/Navbar";
import SearchCategory from "../components/commons/Search/SearchCategory";
import { useEffect, useState } from "react";
import videoPostApi from "../apis/VideoPostApi";
import searchApi from "../apis/SearchApi";

const SearchPage = () => {
  const [searchUsers, setSearchUsers] = useState([]);
  const [searchVideoPosts, setSearchVideoPost] = useState([]);

  useEffect(() => {
    const test = async () => {
      //검색 유저 리스트 출력
      console.log((await searchApi.searchUser("name")).data.data);
      const responseUsers = await searchApi.searchUser("name");
      setSearchUsers(responseUsers.data.data);
      //검색 비디오 포스트 출력, default 조회값 없음 -> 모든 영상 출력
      console.log(
        (await videoPostApi.searchVideoPost({ word: "" })).data.data.list,
      );
      const responseVideoPosts = await videoPostApi.searchVideoPost({
        word: "",
      });
      setSearchVideoPost(responseVideoPosts.data.data.list);
    };
    console.log(searchUsers);

    test();
  }, []);
  return (
    <>
      <div className={styles.searchGlobal}>
        <div className={styles.searchNav}>
          <NavBar />
        </div>
        <SearchCategory />
        <div>
          <h3>유저</h3>
          <div>
            {searchUsers.map((result, index) => (
              <a key={index} href={`/profile/${result.userId}`}>
                <span>
                  {result.url} {result.nickName}
                </span>
              </a>
            ))}
          </div>
        </div>
        <div>
          <h3>비디오</h3>
          <ul>
            {searchVideoPosts.map((result, index) => (
              <li key={index}>{result.title}</li>
            ))}
          </ul>
        </div>
      </div>
    </>
  );
};
export default SearchPage;
