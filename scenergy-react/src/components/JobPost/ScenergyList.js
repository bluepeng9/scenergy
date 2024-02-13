import ScenergyListItem from "../commons/ScenergyList/ScenergyListItem";
import styles from "./ScenergyList.module.css";
import { useEffect, useState } from "react";
import jobPostApi from "../../apis/JobPost/JobPostApi";
import ReactPaginate from "react-paginate";
import searchApi from "../../apis/SearchApi";

const ScenergyList = ({ onOpenModal, refresh, userId, searchParams, mode }) => {
  console.log(userId);
  const [jobPosts, setJobPosts] = useState([]);
  const [pageNumber, setPageNumber] = useState(0);
  const postsPerPage = 6; // 한 페이지에 6개
  const pagesVisited = pageNumber * postsPerPage;

  useEffect(() => {
    const findMode = async () => {
      try {
        let response;
        if (mode === "search" && searchParams) {
          const { searchInput, searchSelect } = searchParams;
          const data = {
            name: searchInput,
            gt: searchSelect?.gt?.map((id) => Number(id)) || [],
            it: searchSelect?.it?.map((id) => Number(id)) || [],
            lt: searchSelect?.lt?.map((id) => Number(id)) || [],
          };
          response = await searchApi.searchJobPost(data);
        } else {
          switch (mode) {
            case "allPosts":
              const data = {
                name: "",
                gt: [],
                it: [],
                lt: [],
              };
              response = await searchApi.searchJobPost(data);
              break;
            case "myPosts":
              response = await jobPostApi.getAllMyJobPost(userId);
              break;
            case "bookmarks":
              response = await jobPostApi.getAllBookMark(userId);
              break;
            case "myApplies":
              response = await jobPostApi.getAllMyApply(userId);
              break;
            default:
              const data1 = {
                name: "",
                gt: [],
                it: [],
                lt: [],
              };
              response = await searchApi.searchJobPost(data1);
          }
        }
        // API 호출 결과로 상태 업데이트
        setJobPosts(response.data.data || response.data); // response 구조에 따라 적절히 접근
        console.log("searchParam:", searchParams);
        console.log(response.data);
        console.log(userId);
      } catch (error) {
        console.error("API 에러", error);
      }
    };

    findMode();
  }, [refresh, userId, mode, searchParams]);

  const displayPosts = jobPosts
    .slice(pagesVisited, pagesVisited + postsPerPage)
    .map((post) => (
      <ScenergyListItem
        key={post.jobPostId}
        jobPostId={post.jobPostId}
        jobstate={post.isActive}
        nickname={post.userDto.nickname}
        title={post.title}
        content={post.content}
        peopleRecruited={post.peopleRecruited}
        totalCount={post.totalApplicant}
        expirationDate={post.expirationDate}
        onOpenModal={() => onOpenModal(post)}
        writerId={post.userDto.userId}
      />
    ));

  const pageCount = Math.ceil(jobPosts.length / postsPerPage);

  const changePage = ({ selected }) => {
    setPageNumber(selected);
  };

  const renderNoPostsMessage = () => {
    if (mode === "search") {
      return <div className={styles.NoPostsMessage}>검색 결과가 없습니다.</div>;
    } else {
      return (
        <div className={styles.NoPostsMessage}>등록된 게시글이 없습니다.</div>
      );
    }
  };

  return (
    <div className={styles.ListGlobalContainer}>
      <div className={styles.ListGlobal}>
        <div className={styles.ListContainer}>{displayPosts}</div>
        <ReactPaginate
          className={styles.ListPaginate}
          previousLabel={"이전"}
          nextLabel={"다음"}
          pageCount={pageCount}
          onPageChange={changePage}
          containerClassName={styles.paginationBttns}
          previousLinkClassName={styles.previousBttn}
          nextLinkClassName={styles.nextBttn}
          disabledClassName={styles.paginationDisabled}
          activeClassName={styles.paginationActive}
        />
      </div>
    </div>
  );
};
export default ScenergyList;
